import java.io.*;
import java.util.Arrays;
import java.util.List;

public class mainApp
{
	public static void main(String[] args)
	{
		//List of the stop words.
		List<String> stopWords = Arrays.asList(new String[]{"the"});

		//Creates a new ST object.
		ST tree = new ST();

		File file = new File("./data/file.txt");
		BufferedReader in = null;

		//Reading the text file line by line and checking the syntax and if the lines have been read correctly. 
		try
		{
			in = new BufferedReader(new FileReader(file));

			//Inserts each word in the tree.
			String str = in.readLine();
			while (str != null) 
			{ 
				String[] line = str.split(" ");
				for (int i = 0; i < line.length; i++)
				{
					String word = line[i];
					word = word.toLowerCase();
					word = word.replaceFirst("^[^a-z]*",""); 
					word = word.replaceFirst("[^a-z]*$",""); 
					WordFreq element = new WordFreq(word);

					//Search if the word is contained in the stop words list.
					if (stopWords != null)
						if(!stopWords.contains(word))
							tree.insert(element);
				}
				str = in.readLine();
			}
			in.close();	//Closes the file reading process.
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		PrintStream output = new PrintStream(System.out);
		tree.printTreeAlphabetically(output);
		System.out.println();
		System.out.print("Number of distinct words: " + tree.getDistinctWords()); 
		System.out.println();
		System.out.print("Total number of words: " + tree.getTotalWords()); 
		System.out.println();
		System.out.print("The mean frequency is: " + tree.getMeanFrequency());
	}
}