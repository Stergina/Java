import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetBenefit {
    public static void main(String[] args) {
     
        IntQueueImpl<Integer> buyQueue = new IntQueueImpl<>(); //Creates an empty queue for share
        IntQueueImpl<Integer> sellQueue = new IntQueueImpl<>(); //Creates an empty queue for prices
        IntQueueImpl<Integer> queue = new IntQueueImpl<>(); //Creates an empty queue for prices
        IntQueueImpl<Integer> queue2 = new IntQueueImpl<>(); //Creates an empty queue for prices
        StringStackImpl<String> stringStack = new StringStackImpl<String>(); //Creates an empty stack.
        StringStackImpl<Integer> buyStack = new StringStackImpl<Integer>(); //Creates an empty stack.
        StringStackImpl<Integer> sellStack = new StringStackImpl<Integer>(); //Creates an empty stack.
        StringBuilder contentBuilder = new StringBuilder();	//Contains a sequence of characters.
        boolean done = false;
        int counter = 0;
        
        //Reads the text file line by line and saves its contents to the appropriate queue.
        try{
            File file = new File(args[0]);
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            String st; 
            while ((st = br.readLine()) != null) {
            	stringStack.push(st);
                Matcher intMatch = Pattern.compile("-?\\d+").matcher(st);
                
                if (st.charAt(0)=='b') 
                {
                	while (intMatch.find() & done!=true) {
                		int n = Integer.parseInt(intMatch.group());
                		buyQueue.put(n);
                		buyStack.push(n);
                	}
                } else if (st.charAt(0)=='s') {
                	while (intMatch.find()) {
                		int n = Integer.parseInt(intMatch.group());
                		sellQueue.put(n);
                		sellStack.push(n);
                		done = true;
                		break;
                	}
                }
                
                //Reversing queue through a stack.
                while (!buyStack.isEmpty()) { 
                    queue.put(buyStack.peek()); 
                    buyStack.pop(); 
                } 

                //Reversing queue through a stack.
                while (!sellStack.isEmpty()) { 
                    queue2.put(sellStack.peek()); 
                    sellStack.pop(); 
                } 

            }
            br.close();
        } catch (IOException e) {
        	System.out.println(e);
        }
        
        //Calculating profit.
        int sellPrice = sellQueue.peek(); //Number of shares sold.
        int noShares = buyQueue.peek(); //Number of shares bought.
        int profit = sellPrice - noShares;
        sellQueue.get();
        buyQueue.get();
        
        if (profit > 0) {
        	int temp = noShares * (sellQueue.peek() - buyQueue.peek());
        }
      
        
        //Enters the buy of a share in queue.
        String content = contentBuilder.toString();
        Matcher stringMatch = Pattern.compile("-?\\w+").matcher(content);
        Matcher intMatch = Pattern.compile("-?\\d+").matcher(content);

        while (stringMatch.find() & intMatch.find()) {
        	String chars = stringMatch.group();
        	if (chars.contains("buy")) {
            	int n = Integer.parseInt(intMatch.group());
        		buyQueue.put(n);
        	} else {
        		int n = Integer.parseInt(intMatch.group());
        		sellQueue.put(n);
        	}
        } 
    }
}
