//We define class HardWare as a subclass of the Product class.
public class HardWare extends Product
{
	//We define the type of each variable of the class HardWare.
	static private double Discount = 0.25;   //Discount for all HardWare.
  
	//We create function "get" in order to access private variables.	 	 
	public double getDiscount()
	{
		return Discount;
	}//getDiscount
	
}//HardWare