public class Product
{
	//We define the type of each variable of the class Product.
    private String ProductName;  //Name of the product.
    private int ModelYear = -1;  //Year of the model. The default case is -1.	
	private String Manufacturer = "Undefined";  //Brand name of the manufacturer. The default case is "Undefined".
	private double ProductPrice;  //Starting price of the product.
	private String ImagePath;
	
	//We create the "get" and "set" functions in order to access the aforementioned private variables.	  
	public String getProductName()
	{
		return ProductName;
    }//getProductName
	
	public void setProductName(String name)
	{
		this.ProductName = name;
    }//setProductName
	
    public int getModelYear()
	{
		return ModelYear;
    }//getModelYear
	
	public void setModelYear(int year)
	{
		 this.ModelYear = year;
    }//setModelYear	
		
	public String getManufacturer()
	{
		return Manufacturer;
    }//getManufacturer
	
	public void setManufacturer(String brand)
	{
		this.Manufacturer = brand;
    }//setManufacturer	
	
    public double getProductPrice()
	{
		return ProductPrice;
    }//getProductPrice
	
	public void setProductPrice(double price)
	{
		this.ProductPrice = price;
    }//setProductPrice	
	
	public String getImagePath()
	{
		return ImagePath;
    }//getImagePath
	
	public void setImagePath(String path)
	{
		this.ImagePath = path;
    }//setImagePath	
	
	//We get the description of the product by creating the toString function.
	@Override
	public String toString()
	{
		return "Model: " + getProductName() + "\nModel's Year: " + getModelYear() + "\nManufacturer: " + getManufacturer()+
			   "\nStarting Price: " + getProductPrice() + " euros";
	}		
}//Product