import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.io.FileWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.classification.utils.DocToDoubleVectorUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;

public class sparseArraysCreation 
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

			// Indexes LISA queries
			storeQueries(indexWriter,type);

			// Closes the indexWriter object
			indexWriter.close();

			// Creates a reader to access the index
			IndexReader reader = DirectoryReader.open(dir);

			// Crates the sparse array which includes documents x terms and
			// the sparse array which includes queries x terms
			sparseFreqArray(reader);

			// Closes reader
			reader.close();
		} 
		catch(Exception e){
			e.printStackTrace();
		}
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
			FileInputStream fstream = new FileInputStream("./data/LISA_ALL.txt");
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
	 * Stores all queries contained in queries.txt in the index
	 */
	public static void storeQueries(IndexWriter indexWriter,FieldType type) 
	{
		// Try block	
		try{
			// Creates a file reader
			FileInputStream fstream = new FileInputStream("./data/queries.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader objReader = new BufferedReader(new InputStreamReader(in));

			// Initializes strings to be used and an integer variable to display
			// the position in which information was found - thus its type
			String strCurrentLine;
			String queryID = "";
			String queryText = "";
			int position = 0;

			// Parses the file line by line
			while ((strCurrentLine = objReader.readLine()) != null) 
			{
				// Case where line equals "ID", then at the next line we are at position 1
				if (strCurrentLine.equals("ID")) {
					queryID = "";
					queryText = "";
					strCurrentLine = objReader.readLine();
					position = 1;
				}

				// Case where line equals "Query", then at the next line we are at position 2
				if (strCurrentLine.equals("Query")) {
					strCurrentLine = objReader.readLine();
					position = 2;
				}

				// Case where we reached the end of the query
				if (strCurrentLine.equals("#")) {

					// Document object's fields initialized
					StringField Id = new StringField("id", queryID, Field.Store.YES);
					Field Text = new Field("text", queryText, type);

					// Creates a new empty document
					Document doc = new Document();

					// Adds fields to the document
					doc.add(Id);
					doc.add(Text);

					// Adds document to the Lucene index 
					indexWriter.addDocument(doc);
				}

				// Case where information represents the Query ID (position 1)
				if (position == 1) {
					queryID = strCurrentLine;
				}

				// Case where information represents the query body (position 2)
				if (position == 2 && !strCurrentLine.equals("#")) {
					queryText = queryText + " " + strCurrentLine;
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
	 * Creates terms.txt file where we store all the terms of the index and
	 * SparseArray.txt file which displays the presence (denoted by 1) of
	 * the term in the document or the absence (denoted by 0). Columns
	 * represent terms and rows represent documents
	 */
	private static void sparseFreqArray(IndexReader reader) throws Exception 
	{
		// Creates a file and a buffer writer in order to store the terms of the index
		FileWriter fwt = new FileWriter("./data/terms.txt");
		BufferedWriter bwt = new BufferedWriter(fwt);

		// Creates a file and a buffer writer in order to store the frequency
		// of terms by document
		File docFile = new File("./data/SparseArray.txt");
		BufferedWriter docBuf = new BufferedWriter(new FileWriter(docFile));

		// Creates a file and a buffer writer in order to store the frequency
		// of terms by query
		File queryFile = new File("./data/QueryArray.txt");
		BufferedWriter queryBuf = new BufferedWriter(new FileWriter(queryFile));

		// Number of terms in the lexicon after analysis of the Field "text"
		Terms fieldTerms = MultiFields.getTerms(reader, "text");  

		// Iterates through the terms of the lexicon and stores them in terms.txt
		TermsEnum it = fieldTerms.iterator();						
		while(it.next() != null) {
			bwt.write(it.term().utf8ToString() + " ");
			bwt.newLine();
		}

		// Closes writer buffer
		bwt.close();

		// Counter that indicates the number of documents in the index
		int counter = reader.maxDoc()-35;

		// Creates TermxDocument.txt file
		if (fieldTerms != null && fieldTerms.size() != -1) 
		{
			// Creates a searcher in order to search the index
			IndexSearcher indexSearcher = new IndexSearcher(reader);

			// Retrieves all documents and queries
			for (ScoreDoc scoreDoc : indexSearcher.search(new MatchAllDocsQuery(), Integer.MAX_VALUE).scoreDocs) {  

				// Documents case
				if (counter > 0) {
					Terms docTerms = reader.getTermVector(scoreDoc.doc, "text");

					// Creates document's vector
					Double[] vector = DocToDoubleVectorUtils.toSparseLocalFreqDoubleArray(docTerms,fieldTerms); 

					// Creates a sparse Double vector given doc and field term vectors using local frequency of the terms in the doc
					NumberFormat nf = new DecimalFormat("0.#");
					for(int i = 0; i<=vector.length-1; i++ ) {
						docBuf.write(nf.format(vector[i])+ " ");
					}
					docBuf.newLine();
					counter--;
				} else {
					// Queries case
					Terms docTerms = reader.getTermVector(scoreDoc.doc, "text");

					// Creates query's vector
					Double[] vector = DocToDoubleVectorUtils.toSparseLocalFreqDoubleArray(docTerms,fieldTerms); 

					// Creates a sparse Double vector given query and field term vectors using local frequency of the terms in the query
					NumberFormat nf = new DecimalFormat("0.#");
					for(int i = 0; i<=vector.length-1; i++ ) {
						queryBuf.write(nf.format(vector[i])+ " ");
					}
					queryBuf.newLine();
					counter--;
				}
			}
		}

		// Closes writer buffers
		docBuf.close();
		queryBuf.close();
	}
}
