//We define class Peripherals as a subclass of the Product class.
public class Peripherals extends Product {
    //We define the type of each variable of the class Peripherals.
    static private double Discount = 0.1; //Discount for all Peripherals.   

    //We create function "get" in order to access private variables.	 
    public double getDiscount() {
        return Discount;
    } //getDiscount

} //Peripherals
