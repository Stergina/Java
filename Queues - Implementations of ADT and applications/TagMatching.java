import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Reads an HTML file and decides if the HTML tags are matched and nested.
 */
public class TagMatching
{
	public static void main(String args[])
	{
		StringStackImpl<String> stack = new StringStackImpl<String>(); //Creates an empty stack.
		StringStackImpl<String> newStack = new StringStackImpl<String>(); //Creates another empty stack.
		StringBuilder contentBuilder = new StringBuilder();	//Contains a sequence of characters.

		//Reads the HTML file line by line.
		try {
			File file = new File(args[0]);
			BufferedReader in = new BufferedReader(new FileReader(file)); 
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();		 
		} catch (IOException e) {
			System.out.println(e);
		}

		//Adds only HTML tags to the stack (text is being removed).
		String content = contentBuilder.toString();
		Matcher m = Pattern.compile("\\<(.*?)\\>").matcher(content);
		while(m.find()) 
			stack.push(m.group(1));

		//Variable declaration.
		int slashChar = 0; //HTML tags that contain character "/".
		int nonSlashChar = 0; //HTML tags that don't contain character "/".
		int stackSize = stack.size(); //Size of the full initial stack.

		//Checks if the HTML tags are matched.
		while (stack.isEmpty()!=true) {

			//Saves the first element of the stack to variable stackPeek.
			String stackPeek = stack.peek();

			if (stackPeek.contains("/")) {
				slashChar++; //Increases slashed HTML tags index by one.
				stackPeek = stackPeek.replace("/",""); //replaces character "/" with gap
				stack.pop(); //Pops the first element of the stack.

				//Creates a new stack which is a duplicate of the initial stack.
				String Content = contentBuilder.toString();
				Matcher match = Pattern.compile("\\<(.*?)\\>").matcher(Content);
				while(match.find()) 
					newStack.push(match.group(1));

				//Pops from the duplicated stack, the elements that were popped out from the initial stack.
				while(newStack.size()>stack.size())
					newStack.pop();

				//Finds if HTML tag that was popped out from the initial stack has a match in rest of the stack elements.
				if(newStack.isEmpty()!=true) {
					String tag = newStack.peek();
					while (!stackPeek.equals(tag)) {
						newStack.pop();		
						tag = newStack.peek();
					}	
					//If match if found, we increase non-slashed tags index by one.
					if (stackPeek.equals(tag))
						nonSlashChar++;
				}
			} else {
				stack.pop();
			}

			//Empties the duplicated stack, in order to re-use it again.
			while (newStack.isEmpty()!=true) 
				newStack.pop();		
		}

		//Adds the number of HTML tags that contain "/" and their matches.
		int tagSum = slashChar + nonSlashChar;

		//Adds only HTML tags to the two stacks (text is being removed).
		content = contentBuilder.toString();
		m = Pattern.compile("\\<(.*?)\\>").matcher(content);
		while(m.find()) 
			stack.push(m.group(1));
		
		content = contentBuilder.toString();
		m = Pattern.compile("\\<(.*?)\\>").matcher(content);
		while(m.find()) 
			newStack.push(m.group(1));

		//Finding the number of closing tags.
		int counter = 0;  //Number of closing tags.
		String topStack = stack.peek();

		while (topStack.contains("/")) {
			newStack.pop(); //Pops the first element of the stack.
			topStack = newStack.peek();
			counter++;
		}	

		//Empties the stack.
		while (newStack.isEmpty()!=true) 
			newStack.pop();

		//Variables declaration.
		int matches = 0; //Number of nested matches.
		int times = counter;

		//Checks if the HTML tags are nested.
		while (times > 0) {
			content = contentBuilder.toString();
			m = Pattern.compile("\\<(.*?)\\>").matcher(content);
			while(m.find()) 
				newStack.push(m.group(1));
			
			int i = 1;
			while (i<=times-1) {
				newStack.pop();
				i++;
			}

			String top = newStack.peek();
			top = top.replace("/","");
			newStack.pop();
			
			int j = 1;
			while(j<=counter-times) {
				newStack.pop();
				j++;
			}
			
			if (newStack.size()>0) {
				String bottom = newStack.peek();
			
				if (top.equals(bottom))
				matches++;
			}
			
			while (newStack.isEmpty()!=true) 
				newStack.pop();		
			times--;
		}//nested

		//Printing the appropriate message.
		if (matches==counter & slashChar==nonSlashChar & tagSum==stackSize) {
			System.out.println("The HTML tags are matched and nested!");
		}else if (matches==counter & (slashChar!=nonSlashChar | tagSum!=stackSize)) {
			System.out.println("The HTML tags are nested but non-matched!");
		}else if (matches!=counter & slashChar==nonSlashChar & tagSum==stackSize) {
			System.out.println("The HTML tags are matched but non-nested!");
		}else if (matches!=counter & (slashChar!=nonSlashChar | tagSum!=stackSize)) {
			System.out.println("The HTML tags are non-matched and non-nested!");
		}

	}
}