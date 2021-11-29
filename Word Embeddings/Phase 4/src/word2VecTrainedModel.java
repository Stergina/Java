import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.lucene.store.Directory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.ops.transforms.Transforms;

public class word2VecTrainedModel 
{
	public static void main(String[] args) throws IOException, ParseException 
	{
		try {
			// Defines where the index is stored - index folder into the project workspace
			String indexLocation = "index";
			Directory dir = FSDirectory.open(Paths.get(indexLocation));	

			// Specifies the analyzer responsible for tokenizing the text
			StandardAnalyzer analyzer = new StandardAnalyzer();

			// Specifies the retrieval model to be used
			Similarity similarity = new ClassicSimilarity();

			// Configures the IndexWriter that is used
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setSimilarity(similarity);

			// Creates a FieldType object
			FieldType type = new FieldType();

			// Only documents and term frequencies are indexed: positions are omitted
			type.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
			type.setTokenized(true);

			// The field's value should be stored
			type.setStored(true);

			// Field's indexed form should also be stored into term vectors
			type.setStoreTermVectors(true);

			// Creates a new index in the directory, removing any previously indexed documents
			iwc.setOpenMode(OpenMode.CREATE);

			// Creates the IndexWriter with the configuration as above 
			IndexWriter indexWriter = new IndexWriter(dir, iwc);

			// Indexes LISA manipulated data
			createIndex(indexWriter,type);

			// Closes the indexWriter object
			indexWriter.close();

			// Creates a reader to access the index
			IndexReader reader = DirectoryReader.open(dir);

			// Imports the pre-trained model
			File gModel = new File("model/model.bin");
			Word2Vec vec = WordVectorSerializer.readWord2VecModel(gModel);

			// Initializes a string array to store the queries
			String[] queries = new String[35];
			queries = storeQueries();

			// Initializes a hashmap to store the similarity scores
			Map<Integer,Double> scores = new HashMap<Integer,Double>();

			// Produces results for the top 20, 30 and 50 documents for each query based on their rank
			int[] topk = {20,30,50};
			for (int k = 0; k < topk.length; k++)
			{
				// Creates a file writer in order to store the results
				String fileName = "results" + topk[k] + "_TrainedModel.txt";
				File results = new File(fileName);
				FileOutputStream in = new FileOutputStream(results);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(in));

				// Parses the queries string array
				for (int j = 0; j < queries.length; j++)
				{
					// Stores query terms in a string array.
					String[] terms = queries[j].trim().replaceAll("[-:,()]", "").split(" ");	

					// Removes leading dots.
					for (int o = 0; o < terms.length; o++) {
						terms[o] = terms[o].replaceAll("\\.+$", "");
						if (terms[o].startsWith("'") && terms[o].endsWith("'")) {
							terms[o] = terms[o].replaceAll("^'+", "");
							terms[o] = terms[o].replaceAll("'+$", "");
						}
					}

					// Converts query terms to dense average vector.
					INDArray denseQueryVector = toDenseAverageVector(vec, terms);

					// Loops through all documents
					for (int i = 0; i < reader.maxDoc(); i++) {
						Terms docTerms = reader.getTermVector(i, "text");
						TermsEnum it = docTerms.iterator();		
						String[] finalDocTerms = new String[(int) docTerms.size()];
						int counter = 0;

						while(it.next() != null) {
							finalDocTerms[counter] = it.term().utf8ToString().toUpperCase();
							counter++;
						}

						// Converts dosument terms to dense average vector.
						INDArray denseDocumentVector = toDenseAverageVector(vec,finalDocTerms);

						// Calculates cosine similarity score between the current document and video.
						double sim = Transforms.cosineSim(denseQueryVector, denseDocumentVector);

						// Stores scores in the hashmap
						scores.put(i,sim);
					}

					// Sorts hashmap by descending similarity
					Map<Integer, Double> sortedMap = scores.entrySet().stream()
							.sorted(Comparator.comparingDouble(e -> -e.getValue()))
							.collect(Collectors.toMap(
									Map.Entry::getKey,
									Map.Entry::getValue,
									(a, b) -> { throw new AssertionError(); },
									LinkedHashMap::new
									));

					// Prints top 20, 30 and 50 results.
					int counter = 0;
					for (Map.Entry<Integer, Double> entry : sortedMap.entrySet()) {
						if (counter < topk[k]) {
							Integer key = entry.getKey();
							Double value = entry.getValue(); 
							bw.write((j + 1) + " Q0 "+  (key + 1) + " " + (counter + 1) + " " + value + " STANDARD");
							bw.newLine();
							counter++;
						} else {
							break;
						}
					}
				}

				// Closes writer buffer
				bw.close();
			}
		} 
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Turns documents and queries to dense average vectors.
	 */
	public static INDArray toDenseAverageVector(Word2Vec word2Vec, String...terms) {
		return word2Vec.getWordVectorsMean(Arrays.asList(terms));
	}

	/**
	 * Creates a Document object for each different LISA document 
	 * by adding Fields in it and indexes the Document with the IndexWriter
	 */
	private static void createIndex(IndexWriter indexWriter,FieldType type) 
	{
		// Try block	
		try {
			// Creates a file reader
			FileInputStream fstream = new FileInputStream("LISA_ALL.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			// Initializes strings to be used and an integer variable to display
			// the position in which information was found - thus its type
			String strCurrentLine;
			String docTitle = "";
			String docAbstract = "";
			int position = 0;

			// Parses the file line by line
			while ((strCurrentLine = br.readLine()) != null) 
			{
				// If line starts with the word "Document" then we are at position 1
				if (strCurrentLine.startsWith("Document")) {
					docTitle = "";
					docAbstract = "";
					position = 1;
				}

				// If line equals "Title" then we are at position 2
				if (strCurrentLine.equals("Title")) {
					position = 2;
					strCurrentLine = br.readLine();
				}	

				// If line equals "Abstract" then we are at position 3
				if (strCurrentLine.equals("Abstract")) {
					position = 3;
					strCurrentLine = br.readLine();
				}		

				// If line equals "********************************************"
				// then we reached the end of the document
				if (strCurrentLine.equals("********************************************")) {

					// Document object's field initialized
					Field field = new Field("text", docTitle + docAbstract, type);

					// Creates a new empty document
					Document doc = new Document();

					// Adds field to the document
					doc.add(field);

					// Adds document to the Lucene index 
					indexWriter.addDocument(doc);				
				}

				// Case where information represents the document title (position 2)
				if (position == 2) {
					docTitle = docTitle + " " + strCurrentLine;	
				}

				// Case where information represents the document abstract (position 3)
				if (position == 3 && !strCurrentLine.equals("********************************************")) {
					docAbstract = docAbstract + " " + strCurrentLine;
				}			
			}
			// Closes reader buffer
			in.close();
		}

		// Catch exception block
		catch (Exception e) {
			System.err.println("Error: " + e.getMessage());		  
		}
	}

	/**
	 * Stores all queries contained in queries.txt in a string array to be used by Lucene
	 */
	public static String[] storeQueries() 
	{
		// Creates a string array to store all 35 queries
		String[] queries = new String[35];

		// Initializes a counter to go through the array positions
		int counter = 0;

		// Try block	
		try{
			// Creates a file reader
			FileInputStream fstream = new FileInputStream("queries.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader objReader = new BufferedReader(new InputStreamReader(in));

			// Initializes strings to be used and an integer variable to display
			// the position in which information was found - thus its type
			String strCurrentLine;
			String text = "";
			int position = 0;
			strCurrentLine = objReader.readLine();

			// Parses the file line by line
			while ((strCurrentLine = objReader.readLine()) != null) 
			{
				// Case where line equals "ID", then we are at position 1
				if (strCurrentLine.equals("ID")) {
					queries[counter] = text;
					counter++;
					text = "";
					position = 1;
					strCurrentLine = objReader.readLine();
				}

				// Case where line equals "Query", then we are at position 2
				if (strCurrentLine.equals("Query")) {
					position = 2;
					strCurrentLine = objReader.readLine();
				}

				// Case where information represents the query body text
				if (position == 2 && !strCurrentLine.equals("#")) {
					text = text + " " + strCurrentLine;
				}
			}
			queries[counter] = text;
			in.close();
		}

		// Catch exception block
		catch (Exception e) { 
			System.err.println("Error: " + e.getMessage());		  
		}
		return queries;
	}
}