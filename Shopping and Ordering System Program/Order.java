public class Order {
    //We define the type of each variable of the class Order.
    private int OrderCode; //Order code. Technically, it is the unique ID of an order.
    private String OrderedPro; //Name of the ordered product.
    private String CustomerName; //Name of the customer.
    private String CustomerPhone; //Phone number of the customer.
    private String OrderDate; //Date of the order in "dd/MM/yyyy" format.
    private String ArrivalDate; //Date of the order arrival in "dd/MM/yyyy" format.
    private double FinalPrice; //Final price of the ordered product.
    private String OrderStatus; //Order's status.
    private static final String OrderStatus1 = "Pending"; //Order's status "Pending".
    private static final String OrderStatus2 = "Executed"; //Order's status "Executed".

    //We create the "get" and "set" functions in order to access the aforementioned private variables.	  
    public int getOrderCode() {
        return OrderCode;
    } //getOrderCode   

    public void setOrderCode(int code) {
        this.OrderCode = code;
    } //setOrderCode

    public String getOrderedPro() {
        return OrderedPro;
    } //getOrderedPro   

    public void setOrderedPro(String product) {
        this.OrderedPro = product;
    } //setOrderedPro 

    public String getCustomerName() {
        return CustomerName;
    } //getCustomerName   

    public void setCustomerName(String name) {
        this.CustomerName = name;
    } //setCustomerName 

    public String getCustomerPhone() {
        return CustomerPhone;
    } //getCustomerPhone   

    public void setCustomerPhone(String phone) {
        this.CustomerPhone = phone;
    } //setCustomerPhone 

    public String getOrderDate() {
        return OrderDate;
    } //getOrderDate   

    public void setOrderDate(String orderD) {
        this.OrderDate = orderD;
    } //setOrderDate 

    public String getArrivalDate() {
        return ArrivalDate;
    } //getArrivalDate   

    public void setArrivalDate(String arrivalD) {
        this.ArrivalDate = arrivalD;
    } //setArrivalDate 

    public double getFinalPrice() {
        return FinalPrice;
    } //getFinalPrice  

    public void setFinalPrice(double price) {
        this.FinalPrice = price;
    } //setFinalPrice 

    public String getOrderStatus() {
        return OrderStatus;
    } //getOrderStatus

    public void setOrderStatus(int status) {
        if (status == 1) {
            this.OrderStatus = OrderStatus1;
        } else if (status == 2) {
            this.OrderStatus = OrderStatus2;
        } else {
            this.OrderStatus = "Wrong input";
        }
    } //setOrderStatus

    //We get the description of the order by creating the toString function.
    @Override
    public String toString() {
        return "Order Code: " + getOrderCode() + "\nProduct Ordered: " + getOrderedPro() + "\nCustomer's Name: " + getCustomerName() +
            "\nCustomer's Phone: " + getCustomerPhone() + "\nOrder Date: " + getOrderDate() + "\nArrival Date: " + getArrivalDate() +
            "\nOrder Status: " + getOrderStatus() + "\nFinal Price: " + Math.round(getFinalPrice() * 100.0) / 100.0 + " euros";
    } //toString

} //Order
