import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SalesList {
    //We create an array list "Sales" of type Sale to store all sales. 
    protected ArrayList < Sale > Sales = new ArrayList < Sale > ();

    //We create an array list "tokens" to store the tokens of a string.
    private ArrayList < String > tokens = new ArrayList < > ();

    //We create an array list "Codes" to store the SaleCode of each sale.
    private ArrayList < Integer > Codes = new ArrayList < > ();

    //We create an array list "ProductType" to store the type of the product.
    private ArrayList < String > ProductType = new ArrayList < String > ();

    int nextSaleCode; //The code of sale.
    int count = 0; //Counting the number of the sale.

    //We create a function that adds a sale to "Sales" array list.
    public void addSale(Sale s) {
        s.setSaleCode(nextSaleCode);
        Sales.add(s);
        nextSaleCode++; //Each time a sale is added, the sale code gets increased by one unit.
    } //addSale

    //We create a function that returns the size of array list "Sales". 
    public int getSaleSize() {
        return Sales.size();
    } //getSaleSize	

    //We create a function that lists all the sales of array list "Sales". 		
    public void listSales() {
        for (int i = 0; i < Sales.size(); i++) {
            System.out.println("\n" + Sales.get(i).toString());
        }
        System.out.println("\nTotal number of sales: " + Sales.size());
    } //listSales

    //We create a function that lists a specific sale of array list "Sales".  
    public String lookUpSales(int code) {
        String result = null;
        for (int i = 0; i < Sales.size(); i++) {
            if (Sales.get(i).getSaleCode() == code) {
                result = Sales.get(i).toString();
                result = result + "\n";
            }
        }
        return result;
    } //lookUpSales

    //We create a function that displays a sale.
    public Sale getSale(int num) {
        return Sales.get(num);
    } //getSale	

    //We create a function that loads the text file that contains the sales. 	
    public void loadFile(String data, ProductsList pro) {
        File f = null;
        BufferedReader reader = null; //Reads text from a character-input stream. 
        String line;
        Sale s = null; //We initialize an object s of type Sale.

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
        try {
            line = reader.readLine();
            while (line != null) { //End of File.  
                if (line.trim().equalsIgnoreCase("SaleList"))
                    line = reader.readLine();
                if (line.trim().equals("{")) { //Start of SaleList entity.
                    line = reader.readLine();
                    while (!line.trim().equals("}")) {
                        if (line.length() != 0) {
                            if (line.trim().equalsIgnoreCase("Sale"))
                                line = reader.readLine();
                            if (line.trim().equals("{")) { //Start of Sale entity. 								
                                line = reader.readLine();
                                count++; //Increasing the number of the sale.
                                s = new Sale(); //We create a new object s of type Sale.
                                while (!line.trim().equals("}")) {
                                    if (line.length() != 0) {
                                        //Tokenizing all the contents included in Sale{...}.
                                        StringTokenizer strtok = new StringTokenizer(line); //Breaks a string into tokens based on a delimiter. 
                                        if (strtok.hasMoreElements() == true) {
                                            tokens.add(strtok.nextToken()); //Tokenizing based on space.
                                            if (strtok.hasMoreElements() == true)
                                                tokens.add(strtok.nextToken("")); //Tokenizing is forced to stop.
                                        }
                                    }
                                    line = reader.readLine();
                                }
                                //We search tokens for the sale's characteristics.
                                for (int i = 0; i < tokens.size() - 1; i++) {
                                    if (tokens.get(i).trim().equalsIgnoreCase("SoldPro")) {
                                        s.setSoldPro(tokens.get(i + 1).replaceAll("\\s+", " ").trim());
                                    } else if (tokens.get(i).trim().equalsIgnoreCase("ProductType")) {
                                        ProductType.add(tokens.get(i + 1).replaceAll("\\s+", " ").trim());
                                    } else if (tokens.get(i).trim().equalsIgnoreCase("SaleCode")) {
                                        s.setSaleCode(Integer.parseInt(tokens.get(i + 1).trim()));
                                        nextSaleCode = s.getSaleCode();
                                    } else if (tokens.get(i).trim().equalsIgnoreCase("CustomerName")) {
                                        s.setCustomerName(tokens.get(i + 1).replaceAll("\\s+", " ").trim());
                                    } else if (tokens.get(i).trim().equalsIgnoreCase("CustomerPhone")) {
                                        s.setCustomerPhone(tokens.get(i + 1).trim());
                                    } else if (tokens.get(i).equalsIgnoreCase("SaleDate")) {
                                        s.setSaleDate(tokens.get(i + 1).replaceAll("\\s+", " ").trim());
                                    } else if (tokens.get(i).equalsIgnoreCase("FinalPrice")) {
                                        s.setFinalPrice(Double.parseDouble(tokens.get(i + 1).trim()));
                                    }
                                }
                                if (pro.getNames().contains(s.getSoldPro())) {
                                    SalesList.this.addCode(s.getSaleCode());
                                    SalesList.this.addSale(s); //Adding the sale s to the list.				
                                } else {
                                    System.out.println("\nThe product of sale " + count + " is not available!");
                                }
                                tokens.clear(); //Clearing the tokens array list in sale to use it for the next Sale entity.
                            }
                        } else {}
                        line = reader.readLine();
                    } //while
                }
                line = reader.readLine();
            } //while

        } catch (IOException e) {
            System.out.println("Error reading line!");
        }

        //Checking if the text file has closed correctly.
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("Error closing file!");
        }
    } //loadFile

    //We create a function that reads all the lines of the text file, stores them, substitutes the last 
    //line from the text file with "" and writes it again. 
    public void deleteLine(String data) throws IOException {
        File f = null;
        BufferedReader reader = null; //Reads text from a character-input stream. 
        BufferedWriter writer = null; //Writes text file from a character-input stream.
        String line;
        ArrayList < String > input = new ArrayList < String > ();

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
            while ((line = reader.readLine()) != null) {
                if (line.contains("}")) {
                    input.add(line);
                    line = reader.readLine();
                    if (line.contains("}")) {
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
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, false))); //false in sale to overwrite the contents of the text file.
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file for writing!");
        }

        //Writing the stores lines.			
        for (String lines: input) {
            try {
                writer.write(lines);
                writer.newLine();
            } catch (Exception e) {
                System.out.println("Write error!");
            }
        }

        //Checking if the writer has closed correctly.
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println("Error closing file!");
            throw new IOException("Failed!");
        }
    } //deleteLine

    //We create a function that stores new sales in the already existent text file. 
    public void writeFile(String data, Sale sa, ProductsList pro) throws IOException {
        File f = null;
        BufferedWriter writer = null; //Writes text file from a character-input stream.
        //Storing sale's details.
        String[] details = new String[11];
        details[0] = "\tSale";
        details[1] = "\t{";
        details[2] = "\t\tSaleCode " + sa.getSaleCode();
        details[3] = "\t\tProductType " + pro.getTypeName(sa.getSoldPro());
        details[4] = "\t\tSoldPro " + sa.getSoldPro();
        details[5] = "\t\tCustomerName " + sa.getCustomerName();
        details[6] = "\t\tCustomerPhone " + sa.getCustomerPhone();
        details[7] = "\t\tSaleDate " + sa.getSaleDate();
        details[8] = "\t\tFinalPrice " + Math.round(sa.getFinalPrice() * 100.0) / 100.0;
        details[9] = "\t}";
        details[10] = "}";
        SalesList.this.deleteLine(data); //Applying the function deleteLine before writing the new sale.

        //If the CustomerName or CustomerPhone are missing, then we define them as "Undefined".
        if (sa.getCustomerName().isEmpty() && sa.getCustomerPhone().isEmpty()) {
            details[5] = "\t\tCustomerName Undefined";
            details[6] = "\t\tCustomerPhone Undefined";
        } else if (sa.getCustomerName().isEmpty()) {
            details[5] = "\t\tCustomerName Undefined";
        } else if (sa.getCustomerPhone().isEmpty()) {
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
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true))); //true in order to append the sale in the text file.
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file for writing!");
        }

        //Writing the sale's characteristics in the text file.				
        for (String charac: details) {
            try {
                writer.write(charac);
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Write error!");
            }
        }

        //Checking if the writer has closed correctly.			
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println("Error closing file!");
        }
    } //writeFile

    //We create a function that returns the type of a product.	
    public String getType(int i) {
        return "Item Type: " + ProductType.get(i);
    } //getType

    //We create a function that returns the type of a product.	
    public String getTypeClean(int i) {
        return ProductType.get(i);
    } //getTypeClean

    //We create a function that adds the SaleCode in the "Codes" array list.	
    public void addCode(int code) {
        Codes.add(code);
    } //addCode

    //We create a function that returns the SaleCode of all sales contained in the "Sales" array list.	
    public ArrayList < Integer > returnCodes() {
        return Codes;
    } //returnCodes

} //SalesList
