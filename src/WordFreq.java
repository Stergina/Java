public class WordFreq 
{
	private String word;
	private int freq;
	
	WordFreq(String word)
	{
		this.word = word;
		freq = 0;
	}

	WordFreq(String word, int freq)
	{
		this.word = word;
		this.freq = freq;
	}
	
	public String key()
	{
		return word;
	}
	
	public String toString()
	{
		return key() + "" + freq;
	}
}