//We define class Keyboard as a subclass of the Peripherals class.
public class Keyboard extends Peripherals {
    //We define the type of each variable of the Keyboard class.
    public String KeyCon = "Undefined"; //Keyboard's connection type. The default case is "Undefined".
    private static final String KeyCon1 = "Wired"; //Keyboard's connection type "Wired".
    private static final String KeyCon2 = "Wireless"; //Keyboard's connection type "Wireless".

    //We create the "get" and "set" functions in order to access the aforementioned private variables.	
    public String getKeyCon() {
        return KeyCon;
    } //getKeyCon

    public void setKeyCon(int type) {
        if (type == 1) {
            this.KeyCon = KeyCon1;
        } else if (type == 2) {
            this.KeyCon = KeyCon2;
        } else {
            this.KeyCon = "Wrong input. Try again."; //In case where KeyCon is not Wired, Wireless or missing ("Undefined").
        }
    } //setKeyCon

    //We get the description of the Keyboard by creating the toString function. When a characteristic is missing it will be assigned as
    //"Undefined" whilst if it is wrongly defined it won't be displayed at all.
    @Override
    public String toString() {
        String result = null;

        if (super.toString() != null) {
            if (getKeyCon().equals("Wired") || getKeyCon().equals("Wireless")) {
                result = super.toString() + "\nConnection Type: " + getKeyCon();
            } else {
                result = super.toString();
                if (getKeyCon().equals("Undefined")) {
                    result = result.concat("\nConnection Type: Undefined");
                }
            }
        } else {
            result = "";
        }
        return result;

    } //toString

} //Keyboard
