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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileWriter;
import java.io.FileReader;
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
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.StoredField;

public class resultsVSM 
{
	public static void main(String[] args) throws IOException, ParseException 
	{
		// Manipulates LISA data in order to bring them in the correct format
		ManipulateData();

		// Manipulates LISA queries in order to bring them in the correct format
		ManipulateQueries();

		// Manipulates LISA results in order to bring them in the correct format
		ManipulateResults();

		// Defines where the index is stored - index folder into the project workspace
		String indexLocation = "index";
		Directory dir = FSDirectory.open(Paths.get(indexLocation));	

		// Specifies the analyzer responsible for tokenizing the text
		StandardAnalyzer analyzer = new StandardAnalyzer();

		// Specifies the retrieval model to be used - Vector Space Model in this case
		Similarity similarity = new ClassicSimilarity();

		// Configures the IndexWriter that is used
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		iwc.setSimilarity(similarity);

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
			String fileName = "results" + topk[k] + ".txt";
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
				searcher.setSimilarity(new ClassicSimilarity());

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
	 * Reads and manipulates LISA data to bring them in the correct format in order to be indexed by Lucene
	 */
	public static void ManipulateData()
	{
		// Try block
		try
		{
			// Locates and stores all LISA data in a file list
			File folder = new File("data");
			File[] listOfFiles = folder.listFiles();

			// Creates a file writer in order to store the manipulated data
			FileWriter stream = new FileWriter("LISA_ALL.txt");
			BufferedWriter bw = new BufferedWriter(stream);

			// Reads LISA data file by file
			for (int i = 0; i < listOfFiles.length; i++) 
			{
				// Creates a file reader
				BufferedReader br = null;
				String strCurrentLine;
				br = new BufferedReader(new FileReader(folder.getAbsoluteFile() + "/" + listOfFiles[i].getName()));

				// Reads the file line by line
				while ((strCurrentLine = br.readLine()) != null) 
				{
					// If a line starts with the word "Document", then the number following corresponds to the document ID
					if (strCurrentLine.startsWith("Document")) {
						bw.write(strCurrentLine);
						bw.newLine();
						bw.write("Title");
						bw.newLine();

						// Replaces empty line with word "Abstract", so everything followed by the line "Abstract" will be the abstract text body
					} else if (strCurrentLine.trim().length()==0) {
						bw.write("Abstract");
						bw.newLine();

						// All rest of the cases regarding line characters
					} else {
						bw.write(strCurrentLine);
						bw.newLine();
					}
				}

				// Closes reader buffer
				br.close();
			}

			// Closes writer buffer
			bw.close();
		}

		// Catch exception block
		catch (Exception e) { 
			System.err.println("Error: " + e.getMessage());		  
		}
	}

	/**
	 * Reads and manipulates the given queries to bring them in the correct format to be stored in an array
	 */
	public static void ManipulateQueries() 
	{
		// Try block
		try
		{
			// Creates a file writer in order to store the manipulated queries
			FileWriter stream = new FileWriter("queries.txt");
			BufferedWriter bw = new BufferedWriter(stream);

			// Creates a file reader
			FileInputStream fstream = new FileInputStream("./queries/LISA.QUE");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strCurrentLine;

			// Reads the query file line by line
			while ((strCurrentLine = br.readLine()) != null) 
			{
				// If the line ends with character "#" that means that the previous part corresponds to the query text body
				if (strCurrentLine.endsWith("#")) {
					bw.write(strCurrentLine.substring(0,strCurrentLine.length() - 1).trim());
					bw.newLine();
					bw.write("#");
					bw.newLine();

					// If the line consists only of numbers then this is the query ID we seek and the lines following are the query text body
				} else if (strCurrentLine.matches("[0-9]+")) {
					bw.write("ID");
					bw.newLine();
					bw.write(strCurrentLine);
					bw.newLine();
					bw.write("Query");
					bw.newLine();

					// All rest of the cases regarding line characters
				} else {
					bw.write(strCurrentLine);
					bw.newLine();
				}
			}

			// Closes reader buffer
			br.close();

			// Closes writer buffer
			bw.close();
		}

		// Catch exception block
		catch (Exception e) { 
			System.err.println("Error: " + e.getMessage());		  
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
			FileInputStream fstream = new FileInputStream("LISA_ALL.txt");
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


	/**
	 * Reads and manipulates the given results to bring them in the correct format to be used by trec eval
	 */
	public static void ManipulateResults() 
	{
		// Try block
		try
		{
			// Creates a file writer in order to store the manipulated queries
			FileWriter stream = new FileWriter("qrels.txt");
			BufferedWriter bw = new BufferedWriter(stream);

			// Creates a file reader
			FileInputStream fstream = new FileInputStream("./results/LISARJ.NUM");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strCurrentLine;

			// Stores help variables which will be used to fix the format of the results file
			int counter = 1;
			String text = "";

			// Creates a string list to store the lines of the file
			String[] str_array = {};
			List<String> list = new ArrayList<String>(Arrays.asList(str_array));
			List<String> q = new ArrayList<String>(Arrays.asList(str_array));
			String[] currentStr, prevStr, queryID;

			// Reads the results file line by line and concatenates document IDs under the correct query
			while ((strCurrentLine = br.readLine()) != null) 
			{		
				// Creates a string array to store line tokens
				String[] tokens = strCurrentLine.split("\\s+");

				// If the first token of the line corresponds to a query ID
				if (tokens[1].equals(String.valueOf(counter)))
				{
					text = strCurrentLine;
					text = text.substring(1,12) + " " + text.substring(32,text.length());
					text = text.replaceAll("\\s+"," ");
					counter++;
				} else {
					text = text + strCurrentLine;
					text = text.replaceAll("\\s+"," ");
				}

				// Adds the text to a list
				list.add(text);			
			}

			// Deletes cases of duplicates lines produced
			for (int i = list.size() - 1; i >= 1; i--)
			{
				currentStr = list.get(i).split("\\s+");
				prevStr = list.get(i-1).split("\\s+");

				if (currentStr[1].equals(prevStr[1]))
				{
					list.remove(i-1);
				}
			}

			// Saves the query IDs in an array list
			for (int i = 0; i < list.size(); i++)
			{
				queryID = list.get(i).split(" ");
				q.add(queryID[1]);
			}

			// Brings the data into the final format to be imported into trec eval
			for (int i = 0; i < list.size(); i++)
			{
				currentStr = list.get(i).split("\\s+");
				for (int j = 2; j < currentStr.length; j++) 
				{
					text = currentStr[1] + " 0 " + currentStr[j] + " 1";
					bw.write(text);
					bw.newLine();
				}
			}

			// Closes reader buffer
			br.close();

			// Closes writer buffer
			bw.close();
		}

		// Catch exception block
		catch (Exception e) { 
			System.err.println("Error: " + e.getMessage());		  
		}
	}
}