import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dynamic_Median 
{
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) 
	{
		//Initializes comparators for each type.
		DefaultComparator<Integer> intComp = new DefaultComparator<Integer>();
		DefaultComparator<String> strComp = new DefaultComparator<String>();

		//The first argument specified by the user is variable k. Variable k denotes the number of best songs to be displayed.
		int k = Integer.parseInt(args[0]);

		//Initializes an integer priority queue of size k to store the median likes.
		PQ<Integer> median = new PQ<Integer>(k,intComp);

		//Initializes a string priority queue of size k to store the song likes sorted.
		PQ<String> medianSorted = new PQ<String>(k);

		//Initializes a string priority queue of size k to store the song description.
		PQ<String> song = new PQ<String>(k,strComp);

		//Initializes a string priority queue of size k to store the top k song titles.
		PQ<String> topk = new PQ<String>(k);

		//Reads and saves the text file line by line.
		try 
		{
			File file = new File(args[1]);
			BufferedReader in = new BufferedReader(new FileReader(file)); 
			Path path = Paths.get(args[1]);
			int lineCount = (int) Files.lines(path).count(); //Counts the number of lines in the text file.

			//We check if the number k chosen by the user is less or equal to the number of songs in the text file.
			if(k > lineCount)
			{
				System.out.println("Error! The number selected is larger than the total number of songs in the file.");
				System.exit(0);
			}

			//Inserts the likes of each song to a priority queue.
			int i = 0; //Counter
			String title;
			while ((title = in.readLine()) != null && i < k) 
			{ 
				int likes = 0;
				Pattern p = Pattern.compile("(-?\\d+$)");
				Matcher m = p.matcher(title);
				while (m.find()) 
				{
					String like = m.group();
					likes = Integer.parseInt(like); 
				} 
				median.insert(likes); //Adds the number of likes for each song to priority queue pqInt.
				song.insert(title); //Adds the title of each song to priority queue pqStr.
				i++; 
			}

			//Sorts the songs based on their max likes.
			int y = 0; //Counter.
			int r = 0;
			String songTitle;
			String curSong = null;
			while(y < k && !median.isEmpty())
			{
				int maxLikes = (int) median.getMax();
				String max = Integer.toString(maxLikes);

				//Checks if the max title contains the max likes.
				for (int u = 1; u <= song.size; u++)
				{
					if (song.get(u).contains(max)) 
					{
						Pattern p = Pattern.compile("[a-zA-Z\\s]+");
						Matcher m = p.matcher(song.get(u));
						while (m.find()) 
						{
							songTitle = m.group();
							songTitle = songTitle.trim();
							if (!songTitle.equals(curSong)) 
							{
								topk.insertAt(songTitle); //Saves the song titles based on the likes.
								medianSorted.insertAt(max); //Saves the likes in descending order.
								song.remove(u);
								u = song.size + 1;
							}
						}	
					}
				}
				curSong = topk.get(r+1);
				r++;
				y++;
			}	

			//Sorts the songs based on their max likes and alphabetical order.
			for (int x = 1; x <= medianSorted.size; x++)
			{
				for (int h = x + 1; h <= medianSorted.size; h++) 
				{
					if (h!=x && medianSorted.get(h).equals(medianSorted.get(x))) 
					{
						Character c1 = topk.get(x).charAt(0);
						Character c2 = topk.get(h).charAt(0);
						int result = c1.compareTo(c2);
						if (result > 0)
							topk.swap(x,h);
					}
				}
			}

			//Finds the median position in the priority queue and its values.
			int medpqSize = medianSorted.size;
			int medPos = 0;
			if (medpqSize %2 ==0)
			{
				medPos = medpqSize/2;
			} else {
				medPos = (medpqSize/2) + 1;
			}

			//Finds the song title that corresponds to the median likes.
			System.out.println("Median = " + medianSorted.get(medPos) + "," + " achieved by Song: " + topk.get(medPos));
			
			
			in.close();	//close the file	 
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
