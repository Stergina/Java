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
import org.apache.lucene.store.Directory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.LMJelinekMercerSimilarity;
import org.apache.lucene.search.similarities.MultiSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.StoredField;

public class resultsBM25LM
{
	public static void main(String[] args) throws IOException, ParseException 
	{
		// Defines where the index is stored - index folder into the project workspace
		String indexLocation = "index";
		Directory dir = FSDirectory.open(Paths.get(indexLocation));	

		// Specifies the analyzer responsible for tokenizing the text
		StandardAnalyzer analyzer = new StandardAnalyzer();

		// Configures the IndexWriter that is used
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

		// Specifies the retrieval models to be used
		Similarity[] sims = {new BM25Similarity(),new LMJelinekMercerSimilarity((float) 0.7)}; 
		iwc.setSimilarity(new MultiSimilarity(sims));

		// Creates a new index in the directory, removing any previously indexed documents
		iwc.setOpenMode(OpenMode.CREATE);

		// Creates the IndexWriter with the configuration as above 
		IndexWriter indexWriter = new IndexWriter(dir, iwc);

		// Parses LISA manipulated data
		createIndex(indexWriter);

		// Closes the indexWriter object
		indexWriter.close();

		// Produces results for the top 20, 30 and 50 documents for each query based on their rank
		int[] topk = {20,30,50};
		for (int k = 0; k < topk.length; k++)
		{
			// Creates a file writer in order to store the results
			String fileName = "results" + topk[k] + "_BM25LM.txt";
			File results = new File(fileName);
			FileOutputStream in = new FileOutputStream(results);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(in));

			// Initializes a string array to store the queries
			String[] queries = new String[35];
			queries = storeQueries();

			// Parses the queries string array
			for (int i = 0; i < queries.length; i++)
			{
				// Initializes a string to store the query
				String qstr =  queries[i];

				// Initializes a QueryParser
				Query q = new QueryParser("text", analyzer).parse(qstr);

				// Stores the k top hits
				int hitsPerPage = topk[k];

				// Creates a reader to access the index
				IndexReader reader = DirectoryReader.open(dir);

				// Creates a searcher in order to search the query in the index
				IndexSearcher searcher = new IndexSearcher(reader);		
				searcher.setSimilarity(new MultiSimilarity(sims));

				// Collects top k hits
				TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
				searcher.search(q, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				// Prints the results into the desired by trec_eval tool format
				for (int j = 0; j < hits.length; ++j) {
					int docId = hits[j].doc;
					Document d = searcher.doc(docId);
					bw.write((i + 1) + " Q0 "+ d.get("id") + " " + (j + 1) + " " + hits[j].score + " STANDARD");
					bw.newLine();
				}		
			}

			// Closes writer buffer
			bw.close();
		}
	}

	/**
	 * Creates a Document object for each different LISA document 
	 * by adding Fields in it and indexes the Document with the IndexWriter
	 */
	public static void createIndex(IndexWriter indexWriter) 
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
			String docID = "";
			String docTitle = "";
			String docAbstract = "";
			int position = 0;

			// Parses the file line by line
			while ((strCurrentLine = br.readLine()) != null) 
			{
				// If line starts with the word "Document" then we are at position 1
				if (strCurrentLine.startsWith("Document")) {
					docID = "";
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

					// Document object's fields initialized
					StringField Id = new StringField("id", docID, Field.Store.YES);
					StoredField Title = new StoredField("title", docTitle);
					TextField Text = new TextField("text", docTitle + docAbstract, Field.Store.NO);

					// Creates a new empty document
					Document doc = new Document();

					// Adds fields to the document
					doc.add(Id);
					doc.add(Title);
					doc.add(Text);

					// Adds document to the Lucene index 
					indexWriter.addDocument(doc);				
				}

				// Case where information represents the document ID (position 1)
				if (position == 1) {
					docID = strCurrentLine.substring(8,strCurrentLine.length()).trim();
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
			FileInputStream fstream = new FileInputStream("./data/queries.txt");
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