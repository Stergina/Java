import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class OrdersList
{
	//We create an array list "Orders" of type Order to store all orders.
	protected ArrayList<Order> Orders = new ArrayList<Order>();  
	
	//We create an array list "tokens" to store the tokens of a string.
	private ArrayList<String> tokens = new ArrayList<>();

	//We create an array list "Codes" to store the OrderCode of each order.
	private ArrayList<Integer> Codes = new ArrayList<>();
	
	//We create an array list "ProductType" to store the type of the product.
	private ArrayList<String> ProductType = new ArrayList<String>();

	int nextOrderCode;  //The code of order.
	int number;  //Used to find the order corresponding to a specific OrderCode.
	int count = 0;	//Counting the number of the order in the text file.
	
    //We create a function that adds an order to "Orders" array list. 
    public void addOrder(Order o)
	{
		o.setOrderCode(nextOrderCode);
		Orders.add(o);
		nextOrderCode++;  //Each time an order is added, the order code gets increased by one unit.
	}//addOrder

	//We create a function that returns the size of array list "Orders". 
    public int getOrderSize() 
	{
        return Orders.size();
	}//getOrderSize	
	
	//We create a function that lists all the orders of array list "Orders". 	
	public void listOrders()
	{
		for (int i = 0; i < Orders.size(); i++){
			System.out.println("\n" + Orders.get(i).toString());
		}
		System.out.println("\nTotal number of orders: "+ Orders.size());
    }//listOrders
	
	//We create a function that lists a specific order of array list "Orders". 
	public String lookUpOrders(int code)
	{
		String result = null;
		for (int i = 0; i < Orders.size(); i++){
			if (Orders.get(i).getOrderCode() == code){
				result = Orders.get(i).toString();
				result = result + "\n";
			}
		}
		return result;
	}//lookUpOrders	
	
	//We create a function that displays an order based on its OrderCode.
	public Order getOrder(int code)
	{
		for (int i = 0; i < Orders.size(); i++){
			if (Orders.get(i).getOrderCode() == code)
				number = i;
		}
		return Orders.get(number);
	}//getOrder	

	//We create a function that displays an order.
	public Order getOrderN(int num)
	{
		return Orders.get(num);
	}//getOrderN	
	
	//We create a function that changes the status of a specific order from "Pending" to "Executed". 
	public void changeOrderStatus(int code, int status)
	{
		for (int i = 0; i < Orders.size(); i++){
			if (Orders.get(i).getOrderCode()==code)
				Orders.get(i).setOrderStatus(status);
		}
	}//changeOrderStatus
	
	//We create a function that removes an order from array list "Orders". 	
	public void removeOrder(int code)
	{
		for (int i = 0; i < Orders.size(); i++){
			if (Orders.get(i).getOrderCode()==code)
				Orders.remove(i);
		}
	}//removeOrder
	
	//We create a function that loads the text file that contains the orders. 
	public void loadFile(String data,ProductsList pro)
	{
        File f = null;
        BufferedReader reader = null;  //Reads text from a character-input stream. 
        String line;
		Order o = null;  //We initialize an object o of type Order.

		//Checking if the text file that we want to load exists.			
        try {
            f = new File(data);
        } catch (NullPointerException e) {
            System.err.println("File not found!");
        }

		//Checking if the text file has opened correctly for reading.	
        try {
            reader = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file!");
        }

		//Reading the text file line by line and checking the syntax and if the lines have been read correctly. 
		try{
			line = reader.readLine(); 
			while(line != null){  //End of File.
			    if(line.trim().equalsIgnoreCase("OrdersList")) 
				line = reader.readLine() ; 
				if (line.trim().equals("{")){  //Start of OrdersList entity.
					line = reader.readLine(); 
					while(!line.trim().equals("}")){ 
						if(line.length() != 0){
							if(line.trim().equalsIgnoreCase("Order"))
							line = reader.readLine();  
							count++;  //Increasing the number of the order.
							if (line.trim().equals("{")){  //Start of Order entity.								
								line = reader.readLine();
								o = new Order();  //We create a new object o of type Order.
								while(!line.trim().equals("}")){ 
									if(line.length() != 0){
										//Tokenizing all the contents included in Order{...}.
										StringTokenizer strtok = new StringTokenizer(line);  //Breaks a string into tokens based on a delimiter. 
										if (strtok.hasMoreElements() == true){
											tokens.add(strtok.nextToken());  //Tokenizing based on space.
											if (strtok.hasMoreElements() == true)
											tokens.add(strtok.nextToken(""));	//Tokenizing is forced to stop.
										}
									}
									line = reader.readLine(); 	
								}	
								//We search tokens for the order's characteristics.
								for (int i = 0; i < tokens.size()-1; i++){
									if(tokens.get(i).trim().equalsIgnoreCase("OrderedPro")){
										o.setOrderedPro(tokens.get(i+1).replaceAll("\\s+"," ").trim());
									}else if(tokens.get(i).trim().equalsIgnoreCase("ProductType")){
										ProductType.add(tokens.get(i+1).replaceAll("\\s+"," ").trim());
									}else if(tokens.get(i).trim().equalsIgnoreCase("OrderCode")){
										o.setOrderCode(Integer.parseInt(tokens.get(i+1).trim()));	
										nextOrderCode = o.getOrderCode();
									}else if(tokens.get(i).trim().equalsIgnoreCase("CustomerName")) {
										o.setCustomerName(tokens.get(i+1).replaceAll("\\s+"," ").trim());
									}else if(tokens.get(i).trim().equalsIgnoreCase("CustomerPhone")) {
										o.setCustomerPhone(tokens.get(i+1).trim());										
									}else if(tokens.get(i).equalsIgnoreCase("OrderDate")){
										o.setOrderDate(tokens.get(i+1).replaceAll("\\s+"," ").trim());
									}else if(tokens.get(i).equalsIgnoreCase("ArrivalDate")){
										o.setArrivalDate(tokens.get(i+1).replaceAll("\\s+"," ").trim());											
									}else if(tokens.get(i).equalsIgnoreCase("OrderStatus")){
										if (tokens.get(i+1).trim().equals("Pending")){
											o.setOrderStatus(1); 
										}else if (tokens.get(i+1).trim().equals("Executed")){
											o.setOrderStatus(2);
										}else{
											o.setOrderStatus(3);
										}
									}else if(tokens.get(i).equalsIgnoreCase("FinalPrice")){ 
										o.setFinalPrice(Double.parseDouble(tokens.get(i+1).trim()));	
									}
								}
								if(pro.getNames().contains(o.getOrderedPro())){
									OrdersList.this.addCode(o.getOrderCode());
									OrdersList.this.addOrder(o); //Adding the order o to the list.
								}else{
									System.out.println("\nThe product of order " + count + " is not available!");
								}
								tokens.clear();	//Clearing the tokens array list in order to use it for the next Order entity.
							}	
						}else{
						}
						line = reader.readLine();
					}//while
				}
				line = reader.readLine();
			}//while

		} catch (IOException e)	{
			System.out.println("Error reading line!");
        }

		//Checking if the text file has closed correctly.
        try {
            reader.close();
        } catch (IOException e) {
			System.err.println("Error closing file!");
        }	
    }//loadFile	

	
	//We create a function that reads all the lines of the text file, stores them, substitutes the last 
	//line from the text file with "" and writes it again.
	public void deleteLine(String data) throws IOException
	{
		File f = null;
        BufferedReader reader = null;  //Reads text from a character-input stream. 
		BufferedWriter writer = null;  //Writes text file from a character-input stream.
        String line;
		ArrayList <String> input = new ArrayList <String>();  

		//Checking if the text file that we want to load exists.			
        try {
            f = new File(data);
        } catch (NullPointerException e) {
            System.err.println("File not found!");
        }

		//Checking if the text file has opened correctly for reading.
        try {
            reader = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file!");
        }
		
		//Reading the text file line by line and substituting the last line with "".
		try {
			while ((line = reader.readLine()) != null){
				if (line.contains("}")){
					input.add(line);
					line = reader.readLine(); 
					if (line.contains("}")){
						line = "";
					}
				}
				input.add(line);
			}
		} catch (IOException e) {
			System.out.println("Error reading line!");
        }

		//Checking if the reader has closed correctly.
        try {
            reader.close();
        } catch (IOException e) {
			System.err.println("Error closing file!");
        }
	
		//Checking if the text file has opened correctly for writing.	
		try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,false)));  //false in order to overwrite the contents of the text file.
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file for writing!");
        }

		//Writing the stores lines.			
		for (String lines:input){
			try {
				writer.write(lines);
				writer.newLine();
			} catch (Exception e) {
				System.out.println("Write error!");
			}
		}
		
		//Checking if the writer has closed correctly.
		try{
            writer.close();
        } catch (IOException e) {
            System.err.println("Error closing file!");
			throw new IOException("Failed!");
        }
	}//deleteLine
	
	//We create a function that stores new orders in the already existent text file. 
	public void writeFile(String data,Order or,ProductsList pro) throws IOException 
	{
		File f = null;
        BufferedWriter writer = null;  //Writes text file from a character-input stream.
		//Storing all order's details.
		String[] details = new String[13];  
		details[0] = "\tOrder";
		details[1] = "\t{";
		details[2] = "\t\tOrderCode " + or.getOrderCode();
		details[3] = "\t\tProductType " + pro.getTypeName(or.getOrderedPro());
		details[4] = "\t\tOrderedPro " +  or.getOrderedPro();
		details[5] = "\t\tCustomerName " + or.getCustomerName();			
		details[6] = "\t\tCustomerPhone " + or.getCustomerPhone();
		details[7] = "\t\tOrderDate " + or. getOrderDate();
		details[8] = "\t\tArrivalDate " + or.getArrivalDate();
		details[9] = "\t\tOrderStatus " + or.getOrderStatus();
		details[10] = "\t\tFinalPrice " + Math.round(or.getFinalPrice()*100.0)/100.0;
		details[11] = "\t}";
		details[12] = "}";
		OrdersList.this.deleteLine(data);  //Applying the function deleteLine before writing the new order.
		
		//If the CustomerName or CustomerPhone are missing, then we define them as "Undefined".
		if (or.getCustomerName().isEmpty() && or.getCustomerPhone().isEmpty()){
			details[5] = "\t\tCustomerName Undefined";
			details[6] = "\t\tCustomerPhone Undefined";
		}else if (or.getCustomerName().isEmpty()){
			details[5] = "\t\tCustomerName Undefined";
		}else if (or.getCustomerPhone().isEmpty()){
			details[6] = "\t\tCustomerPhone Undefined";
		}

		//Checking if the text file that we want to load exists.			
        try {
            f = new File(data);
        } catch (NullPointerException e) {
            System.err.println("File not found!");
        }
		
		//Checking if the text file has opened correctly for writing.	
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true)));  //true in order to append the order in the text file.
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file for writing!");
        }
	
		//Writing the order's characteristics in the text file.				
		for (String charac:details){
			try {
				writer.write(charac);
				writer.newLine();
			} catch (IOException e) {
				System.err.println("Write error!");
			}
		}

		//Checking if the writer has closed correctly.		
        try{
            writer.close();
        } catch (IOException e) {
            System.err.println("Error closing file!");
        }
    }//writeFile
				
	//We create a function that returns the type of a product.	
	public String getType(int i)
	{
		return "Item Type: " + ProductType.get(i);
	}//getType

	//We create a function that returns the type of a product.	
	public String getTypeClean(int i)
	{
		return ProductType.get(i);
	}//getTypeClean

	//We create a function that adds the OrderCode in the "Codes" array list.	
	public void addCode(int code)
	{
		Codes.add(code);
	}//addCode
	
	//We create a function that returns the OrderCode of all orders contained in the "Orders" array list.	
	public ArrayList<Integer> returnCodes()
	{
		return Codes;
	}//returnCodes
	
}//OrdersList