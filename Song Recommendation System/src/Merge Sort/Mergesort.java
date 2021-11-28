import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;
import java.util.Comparator;

public class Mergesort 
{   
	public static void main(String[] args) throws FileNotFoundException 
	{
		//The first argument specified by the user is variable k. Variable k denotes the number of best songs to be displayed.
		int k = Integer.parseInt(args[0]);

		//Reads and saves the text file line by line.
		try 
		{
			Path path = Paths.get("./data/file.txt");
			File file = path.toFile();
			BufferedReader in = new BufferedReader(new FileReader(file));
			
			int lineCount = (int) Files.lines(path).count(); //Counts the number of lines in the text file.

			//We check if the number k chosen by the user is less or equal to the number of songs in the text file.
			if(k > lineCount)
			{
				System.out.println("Error! The number selected is larger than the total number of songs in the file.");
				System.exit(0);
			}

			//Inserts the likes of each song to an array.
			int i = 0; //Counter
			String str;
			Integer[] likes = new Integer[lineCount]; //Initializes an array "likes" to store the number of likes for each song.
			String[] title = new String[lineCount]; //Initializes an array "title" to store the elements of each text line.
			while ((str = in.readLine())!=null && i < lineCount) 
			{ 
				title[i] = str;
				int noLikes = 0;
				Pattern p = Pattern.compile("(-?\\d+$)");
				Matcher m = p.matcher(str);
				while (m.find()) 
				{
					String like = m.group();
					noLikes = Integer.parseInt(like); 
					likes[i] = noLikes; //Adds the number of likes for each song to array "likes".
				} 
				i++; 
			}
			DefaultComparator<Integer> comp = new DefaultComparator<Integer>();
			mergeSorter.sort(likes,comp);

			//Sorts the songs based on their max likes.
			String songTitle;
			String curSong = null;
			String[] topk = new String[lineCount];
			for (int x = 0; x < likes.length; x++)
			{
				String max = Integer.toString((int) likes[x]);
				for (int y = 0; y < title.length; y++)
				{
					if (title[y].contains(max)) 
					{
						Pattern p = Pattern.compile("[a-zA-Z\\s]+");
						Matcher m = p.matcher(title[y]);
						while (m.find()) 
						{
							songTitle = m.group();
							songTitle = songTitle.trim();
							if (!songTitle.equals(curSong))
							{
								topk[x] = songTitle;
								break;
							}
						}	
					}
				}	
				curSong = topk[x];
			}
				
			//Sorts the songs based on their max likes and alphabetical order.
			int tempx = 0;
			int tempy = 0;
			String temp = null;
			for (int x = 0; x < likes.length; x++)
			{
				for (int y = x + 1; y < likes.length; y++) 
				{
					if (y!=x && likes[y] == likes[x]) 
					{
						tempx = x;
						tempy = y;
					}
					if (topk[tempx].charAt(0) > topk[tempy].charAt(0))
					{
						temp = topk[tempx];
						topk[tempx] = topk[tempy];
						topk[tempy] = temp;
					}
				}
			}

			//Prints the top k songs.
			System.out.println("The top " + k + " songs are:");
			for (int l = 0; l < k; l++) 
				System.out.println(topk[l]);

			in.close();	//Closes the file reading process.
		} catch (IOException ie) {
			System.out.print(ie.getMessage());
		}
	}
}//Top_k

//Merge-sort class using array. 
class mergeSorter
{
	public static <E> void sort(E[] array, Comparator<? super E> comp) {
		mergeSort(array, 0, array.length - 1, comp);
	}

	private static <E> void mergeSort(E[] array, int lowerIndex, int upperIndex, Comparator<? super E> comp) 
	{
		int low = lowerIndex;
		int upper = upperIndex;
		if (low >= upper) 
			return;

		int mid = (low + upper)/2;
		mergeSort(array, low, mid,comp);
		mergeSort(array, mid + 1, upper,comp);
		int end_low = mid;
		int start_upper = mid + 1;
		while ((lowerIndex <= end_low) && (start_upper <= upper)) 
		{
			if (comp.compare(array[start_upper], array[low]) < 0) {
				low++;
			} else {
				E temp;
				temp = array[start_upper];
				for (int k = start_upper - 1; k >= low; k--) {
					array[k + 1] = array[k];
				}
				array[low] = temp;
				low++;
				end_low++;
				start_upper++;
			}
		}
	}
}//mergeSort