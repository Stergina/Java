public class Sale
{
	//We define the type of each variable of the class Sale.
	private int SaleCode;	//Sale code. Technically, it is the unique ID of a sale.
	private String SoldPro;  //Name of the sold product.
	private String CustomerName;  //Name of the customer.
	private String CustomerPhone;  //Phone number of the customer.
	private String SaleDate;  //Date of the sale in "dd/MM/yyyy" format.
	private double FinalPrice;  //Final price of the sold product.

	//We create the "get" and "set" functions in order to access the aforementioned private variables.	  
	public int getSaleCode()
	{		   
		return SaleCode;
	}//getSaleCode
   
	public void setSaleCode(int code)
	{
		this.SaleCode = code;
	}//setSaleCode
   
	public String getSoldPro()
	{
		return SoldPro;
	}//getSoldPro  
   
	public void setSoldPro(String product)
	{
		this.SoldPro = product;
	}//setSoldPro 
   
	public String getCustomerName()
	{
		return CustomerName;
	}//getCustomerName  
   
	public void setCustomerName(String name)
	{
		this.CustomerName = name;
	}//setCustomerName 
   
	public String getCustomerPhone()
	{
		return CustomerPhone;
	}//getCustomerPhone  
   
	public void setCustomerPhone(String phone)
	{
		this.CustomerPhone = phone;
	}//setCustomerPhone 
   
	public String getSaleDate()
	{
		return SaleDate;
	}//getSaleDate   
   
	public void setSaleDate(String date){
		this.SaleDate = date;
	}//setSaleDate 
   
	public double getFinalPrice()
	{
		return FinalPrice;
	}//getFinalPrice 
   
	public void setFinalPrice(double price)
	{
		this.FinalPrice = price;
	}//setFinalPrice
	
	//We get the description of the sale by creating the toString function.
	@Override
	public String toString()
	{
        return "Sale Code: "+ getSaleCode() + "\nProduct Sold: " + getSoldPro() + "\nCustomer's Name: " + getCustomerName() + 
			   "\nCustomer's Phone: " + getCustomerPhone() + "\nSale Date: " + getSaleDate() + "\nFinal Price: " + 
			   Math.round(getFinalPrice()*100.0)/100.0 + " euros";
	}//toString
	
}//Sale   