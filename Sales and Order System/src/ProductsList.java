import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ProductsList {
    //We create an array list "Products" of type Product. 
    protected ArrayList < Product > Products = new ArrayList < Product > ();

    //We create an array list "Availability" to store the available pieces of each product.
    private ArrayList < Integer > Availability = new ArrayList < Integer > ();

    //We create an array list "tokens" to store the tokens that arise by tokenizing a String.
    private ArrayList < String > tokens = new ArrayList < > ();

    //We create an array list "ProductType" to store the type of each product.
    private ArrayList < String > ProductType = new ArrayList < String > ();

    //We create an array list "Names" to store the names of the products.
    private ArrayList < String > Names = new ArrayList < > ();

    int count = 0; //Counting the number of the product in the text file.
    int number; //Used to find the a product of specific name.
    int Pieces; //Counting the available pieces of each product.

    //We create a function that adds a product to the products catalogue. 
    public void addProduct(Product p, int a) {
        Products.add(p);
        Availability.add(a);
    } //addProduct

    //We create a function that lists all the products. 	
    public void listProducts(int a) {
        int count = 1;
        for (int i = 0; i < 2; i++) {
            System.out.println(count + ". " + Products.get(a).getProductName());
            count++;
            a++;
        }
    } //listProducts

    //We create a function that lists a specific product. 	
    public void chooseProducts(int a) {
        System.out.println("Item Type: " + getType(a));
        System.out.println(Products.get(a).toString());
        System.out.println("Pieces: " + getAvailability(a));
    } //chooseProducts

    //We create a function that gives us the availability of a product. 		
    public int getAvailability(int a) {
        return Availability.get(a);

    } //getAvailability

    //We create a function that changes the availability of a product. 	
    public int changeAvailability(int a) {
        int b = Availability.get(a);
        return Availability.set(a, --b);
    } //changeAvailability

    //We create a function that loads the text file that contains the available products. 	
    public void loadFile(String data) {
        File f = null;
        BufferedReader reader = null; //Initializing a BufferedReader in order to read text from the text file. 
        String line;
        Product p = null; //We initialize an object p of type Product.

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
                if (line.trim().equalsIgnoreCase("ProductsList"))
                    line = reader.readLine();
                if (line.trim().equals("{")) { //Start of ProductsList entity.  
                    line = reader.readLine();
                    while (!line.trim().equals("}")) {
                        if (line.length() != 0) {
                            if (line.trim().equalsIgnoreCase("Product"))
                                line = reader.readLine();
                            if (line.trim().equals("{")) { //Start of Product entity.   								
                                line = reader.readLine();
                                count++; //Increasing the number of the product.
                                while (!line.trim().equals("}")) {
                                    if (line.length() != 0) {
                                        //Tokenizing all the contents included in entity Product{...}.
                                        StringTokenizer strtok = new StringTokenizer(line); //Breaks a string into tokens based on a delimiter. 
                                        if (strtok.hasMoreElements() == true) {
                                            tokens.add(strtok.nextToken()); //Tokenizing based on space.
                                            if (strtok.hasMoreElements() == true)
                                                tokens.add(strtok.nextToken("")); //Tokenizing is forced to stop.
                                        }
                                    }
                                    line = reader.readLine();
                                }
                                //Searching all tokens for ProductType. When found, we create the according product instance.
                                for (int i = 0; i < tokens.size() - 1; i++) {
                                    if (tokens.get(i).trim().equalsIgnoreCase("ProductType")) {
                                        tokens.set(i, "ProductType");
                                        //Product type is always the next token after word ProductType.
                                        if (tokens.get(i + 1).trim().equals("Motherboard")) {
                                            p = new Motherboard();
                                        } else if (tokens.get(i + 1).trim().equals("CPU")) {
                                            p = new CPU();
                                        } else if (tokens.get(i + 1).trim().equals("RAM")) {
                                            p = new RAM();
                                        } else if (tokens.get(i + 1).replaceAll("\\s+", " ").trim().equals("Graphics Card")) {
                                            p = new Graphics();
                                        } else if (tokens.get(i + 1).replaceAll("\\s+", " ").trim().equals("Hard Disk")) {
                                            p = new HardDisk();
                                        } else if (tokens.get(i + 1).trim().equals("Monitor")) {
                                            p = new Monitor();
                                        } else if (tokens.get(i + 1).trim().equals("Keyboard")) {
                                            p = new Keyboard();
                                        } else if (tokens.get(i + 1).trim().equals("Mouse")) {
                                            p = new Mouse();
                                        } else if (tokens.get(i + 1).trim().equals("Printer")) {
                                            p = new Printer();
                                        }
                                    } else if (tokens.get(i).trim().equalsIgnoreCase("ProductName")) {
                                        tokens.set(i, "ProductName");
                                    } else if (tokens.get(i).trim().equalsIgnoreCase("ProductPrice")) {
                                        tokens.set(i, "ProductPrice");
                                    }
                                }
                                //If ProductType, ProductName and ProductPrice exist, then we read tokens in order to find the rest of product's characteristics.
                                if ((tokens.contains("ProductType")) && (tokens.contains("ProductName")) && (tokens.contains("ProductPrice"))) {
                                    for (int i = 0; i < tokens.size() - 1; i++) {
                                        //General product characteristics.
                                        if (tokens.get(i).trim().equalsIgnoreCase("ProductName")) {
                                            p.setProductName(tokens.get(i + 1).replaceAll("\\s+", " ").trim());
                                        } else if (tokens.get(i).trim().equalsIgnoreCase("ModelYear")) {
                                            p.setModelYear(Integer.parseInt(tokens.get(i + 1).trim()));
                                        } else if (tokens.get(i).trim().equalsIgnoreCase("Manufacturer")) {
                                            p.setManufacturer(tokens.get(i + 1).replaceAll("\\s+", " ").trim());
                                        } else if (tokens.get(i).equalsIgnoreCase("ProductPrice")) {
                                            p.setProductPrice(Double.parseDouble(tokens.get(i + 1).trim()));
                                        } else if (tokens.get(i).trim().equalsIgnoreCase("Pieces")) {
                                            Pieces = Integer.parseInt(tokens.get(i + 1).trim());
                                            //Motherboard characteristics.
                                        } else if (tokens.get(i).equalsIgnoreCase("MBmemory")) {
                                            if (p instanceof Motherboard)
                                                ((Motherboard) p).setMBmemory(Integer.parseInt(tokens.get(i + 1).trim()));
                                        } else if (tokens.get(i).equalsIgnoreCase("MBports")) {
                                            if (p instanceof Motherboard)
                                                ((Motherboard) p).setMBports(Integer.parseInt(tokens.get(i + 1).trim()));
                                        } else if (tokens.get(i).equalsIgnoreCase("CPUtype")) {
                                            if (p instanceof Motherboard) {
                                                if (tokens.get(i + 1).trim().equals("Intel")) {
                                                    ((Motherboard) p).setCPUtype(1);
                                                } else if (tokens.get(i + 1).trim().equals("AMD")) {
                                                    ((Motherboard) p).setCPUtype(2);
                                                } else {
                                                    ((Motherboard) p).setCPUtype(3);
                                                }
                                            }
                                            //CPU characteristics.
                                        } else if (tokens.get(i).equalsIgnoreCase("CPUspeed")) {
                                            if (p instanceof CPU)
                                                ((CPU) p).setCPUspeed(Double.parseDouble(tokens.get(i + 1).trim()));
                                        } else if (tokens.get(i).equalsIgnoreCase("CPUcores")) {
                                            if (p instanceof CPU)
                                                ((CPU) p).setCPUcores(Integer.parseInt(tokens.get(i + 1).trim()));
                                        } else if (tokens.get(i).equalsIgnoreCase("CPUgraph")) {
                                            if (p instanceof CPU) {
                                                if (tokens.get(i + 1).replaceAll("\\s+", " ").trim().equals("Embedded Graphics")) {
                                                    ((CPU) p).setCPUgraph(1);
                                                } else if (tokens.get(i + 1).replaceAll("\\s+", " ").trim().equals("Non Embedded Graphics")) {
                                                    ((CPU) p).setCPUgraph(2);
                                                } else {
                                                    ((CPU) p).setCPUgraph(3);
                                                }
                                            }
                                            //RAM characteristics.
                                        } else if (tokens.get(i).equalsIgnoreCase("RAMtype")) {
                                            if (p instanceof RAM)
                                                ((RAM) p).setRAMtype(tokens.get(i + 1).trim());
                                        } else if (tokens.get(i).equalsIgnoreCase("RAMsize")) {
                                            if (p instanceof RAM)
                                                ((RAM) p).setRAMsize(Integer.parseInt(tokens.get(i + 1).trim()));
                                        } else if (tokens.get(i).equalsIgnoreCase("RAMfreq")) {
                                            if (p instanceof RAM)
                                                ((RAM) p).setRAMfreq(Integer.parseInt(tokens.get(i + 1).trim()));
                                            //Graphics Card characteristics.
                                        } else if (tokens.get(i).equalsIgnoreCase("Chipset")) {
                                            if (p instanceof Graphics) {
                                                if (tokens.get(i + 1).trim().equals("nVidia")) {
                                                    ((Graphics) p).setChipset(1);
                                                } else if (tokens.get(i + 1).trim().equals("AMD")) {
                                                    ((Graphics) p).setChipset(2);
                                                } else {
                                                    ((Graphics) p).setChipset(3);
                                                }
                                            }
                                        } else if (tokens.get(i).equalsIgnoreCase("GraphMemory")) {
                                            if (p instanceof Graphics)
                                                ((Graphics) p).setGraphMemory(Integer.parseInt(tokens.get(i + 1).trim()));
                                            //Hard Disk characteristics.
                                        } else if (tokens.get(i).equalsIgnoreCase("DiskType")) {
                                            if (p instanceof HardDisk) {
                                                if (tokens.get(i + 1).trim().equals("HDD")) {
                                                    ((HardDisk) p).setDiskType(1);
                                                } else if (tokens.get(i + 1).trim().equals("SSD")) {
                                                    ((HardDisk) p).setDiskType(2);
                                                } else {
                                                    ((HardDisk) p).setDiskType(3);
                                                }
                                            }
                                        } else if (tokens.get(i).equalsIgnoreCase("DiskSize")) {
                                            if (p instanceof HardDisk)
                                                ((HardDisk) p).setDiskSize(Double.parseDouble(tokens.get(i + 1).trim()));
                                        } else if (tokens.get(i).equalsIgnoreCase("DiskCapacity")) {
                                            if (p instanceof HardDisk)
                                                ((HardDisk) p).setDiskCapacity(Integer.parseInt(tokens.get(i + 1).trim()));
                                            //Monitor characteristics.
                                        } else if (tokens.get(i).equalsIgnoreCase("MonTech")) {
                                            if (p instanceof Monitor) {
                                                if (tokens.get(i + 1).trim().equals("LCD")) {
                                                    ((Monitor) p).setMonTech(1);
                                                } else if (tokens.get(i + 1).trim().equals("LED")) {
                                                    ((Monitor) p).setMonTech(2);
                                                } else {
                                                    ((Monitor) p).setMonTech(3);
                                                }
                                            }
                                        } else if (tokens.get(i).equalsIgnoreCase("MonSize")) {
                                            if (p instanceof Monitor)
                                                ((Monitor) p).setMonSize(Double.parseDouble(tokens.get(i + 1).trim()));
                                        } else if (tokens.get(i).equalsIgnoreCase("MonRes")) {
                                            if (p instanceof Monitor)
                                                ((Monitor) p).setMonRes(tokens.get(i + 1).trim());
                                        } else if (tokens.get(i).equalsIgnoreCase("MonPorts")) {
                                            if (p instanceof Monitor)
                                                ((Monitor) p).setMonPorts(tokens.get(i + 1).replaceAll("\\s+", " ").trim());
                                            //Keyboard characteristics.
                                        } else if (tokens.get(i).equalsIgnoreCase("KeyCon")) {
                                            if (p instanceof Keyboard) {
                                                if (tokens.get(i + 1).trim().equals("Wired")) {
                                                    ((Keyboard) p).setKeyCon(1);
                                                } else if (tokens.get(i + 1).trim().equals("Wireless")) {
                                                    ((Keyboard) p).setKeyCon(2);
                                                } else {
                                                    ((Keyboard) p).setKeyCon(3);
                                                }
                                            }
                                            //Mouse characteristics.
                                        } else if (tokens.get(i).equalsIgnoreCase("MouseTech")) {
                                            if (p instanceof Mouse) {
                                                if (tokens.get(i + 1).trim().equals("Laser")) {
                                                    ((Mouse) p).setMouseTech(1);
                                                } else if (tokens.get(i + 1).trim().equals("Optical")) {
                                                    ((Mouse) p).setMouseTech(2);
                                                } else {
                                                    ((Mouse) p).setMouseTech(3);
                                                }
                                            }
                                        } else if (tokens.get(i).equalsIgnoreCase("MouseCon")) {
                                            if (p instanceof Mouse) {
                                                if (tokens.get(i + 1).trim().equals("Wired")) {
                                                    ((Mouse) p).setMouseCon(1);
                                                } else if (tokens.get(i + 1).trim().equals("Wireless")) {
                                                    ((Mouse) p).setMouseCon(2);
                                                } else {
                                                    ((Mouse) p).setMouseCon(3);
                                                }
                                            }
                                            //Printer characteristics.
                                        } else if (tokens.get(i).equalsIgnoreCase("PrintTech")) {
                                            if (p instanceof Printer) {
                                                if (tokens.get(i + 1).trim().equals("Laser")) {
                                                    ((Printer) p).setPrintTech(1);
                                                } else if (tokens.get(i + 1).trim().equals("Inkjet")) {
                                                    ((Printer) p).setPrintTech(2);
                                                } else {
                                                    ((Printer) p).setPrintTech(3);
                                                }
                                            }
                                        } else if (tokens.get(i).equalsIgnoreCase("PrintType")) {
                                            if (p instanceof Printer) {
                                                if (tokens.get(i + 1).trim().equals("Colour")) {
                                                    ((Printer) p).setPrintType(1);
                                                } else if (tokens.get(i + 1).trim().equals("BlacknWhite")) {
                                                    ((Printer) p).setPrintType(2);
                                                } else {
                                                    ((Printer) p).setPrintType(3);
                                                }
                                            }
                                        }
                                    }
                                }
                                //Adding the product in the list if only ProductType, ProductName and ProductPrice exist. If they don't exist then 
                                //we print the according message to the screen.
                                if ((tokens.contains("ProductType")) && (tokens.contains("ProductName")) && (tokens.contains("ProductPrice"))) {
                                    ProductsList.this.addProduct(p, Pieces); //Adding product p and its availability.
                                    for (int i = 0; i < tokens.size() - 1; i++) {
                                        if (tokens.get(i).trim().equalsIgnoreCase("ProductType"))
                                            ProductType.add(tokens.get(i + 1).replaceAll("\\s+", " ").trim());
                                        if (tokens.get(i).trim().equalsIgnoreCase("ProductName"))
                                            Names.add(tokens.get(i + 1).replaceAll("\\s+", " ").trim());
                                    }
                                }
                                tokens.clear(); //Clearing the tokens array list in order to use it for the next Product entity.
                                Pieces = -1; //If availability is undefined then it has value -1.
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

        //Checking if the text file reader has closed correctly.
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("Error closing file!");
        }
    } //loadFile

    //We create a function that returns the type of a specific product.
    public String getType(int i) {
        return ProductType.get(i);
    } //getType

    //We create a function that returns the type of a product by using its name.	
    public String getTypeName(String name) {
        for (int i = 0; i < Products.size(); i++) {
            if (Products.get(i).getProductName() == name)
                number = i;
        }
        return ProductsList.this.getType(number);
    } //getTypeName

    //We create a function that returns the names of all products read by the text file.
    public ArrayList < String > getNames() {
        return Names;
    } //getNames

} //ProductsList
