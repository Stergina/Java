//We define class Printer as a subclass of the Peripherals class.
public class Printer extends Peripherals {
    //We define the type of each variable of the Printer class.
    private String PrintTech = "Undefined"; //Printer's technology type. The default case is "Undefined".
    private static final String PrintTech1 = "Laser"; //Printer's technology type "Laser".
    private static final String PrintTech2 = "Inkjet"; //Printer's technology type "Inkjet".
    private String PrintType = "Undefined"; //Printer's type. The default case is "Undefined".
    private static final String PrintType1 = "Colour"; //Printer's type "Colour".
    private static final String PrintType2 = "BlacknWhite"; //Printer's type "BlacknWhite".

    //We create the "get" and "set" functions in order to access the aforementioned private variables.
    public String getPrintTech() {
        return PrintTech;
    } //getPrintTech

    public void setPrintTech(int type) {
        if (type == 1) {
            this.PrintTech = PrintTech1;
        } else if (type == 2) {
            this.PrintTech = PrintTech2;
        } else {
            this.PrintTech = "Wrong number. Try again."; //In case where PrintTech is not Laser, Inkjet or missing ("Undefined").
        }
    } //setPrintTech

    public String getPrintType() {
        return PrintType;
    } //getPrintType

    public void setPrintType(int type) {
        if (type == 1) {
            this.PrintType = PrintType1;
        } else if (type == 2) {
            this.PrintType = PrintType2;
        } else {
            this.PrintType = "Wrong number. Try again."; //In case where PrintType is not Colour, BlacknWhite or missing ("Undefined").
        }
    } //setPrintType

    //We get the description of the Printer by creating the toString function. When a characteristic is missing it will be assigned as
    //"Undefined" whilst if it is wrongly defined it won't be displayed at all.
    @Override
    public String toString() {
        boolean bool1 = (getPrintTech().equals("Laser") || getPrintTech().equals("Inkjet"));
        boolean bool2 = (getPrintType().equals("Colour") || getPrintType().equals("BlacknWhite"));
        String result = null;

        if (super.toString() != null) {
            if ((bool1) && (bool2)) {
                result = super.toString() + "\nTechonology Type: " + getPrintTech() + "\nPrinter Type: " + getPrintType();
            } else if (!bool2) {
                if (!bool1) {
                    result = super.toString();
                    if (getPrintTech().equals("Undefined") && getPrintType().equals("Undefined")) {
                        result = result.concat("\nTechonology Type: Undefined" + "\nPrinter Type: Undefined");
                    } else if (getPrintTech().equals("Undefined")) {
                        result = result.concat("\nTechonology Type: Undefined");
                    } else if (getPrintType().equals("Undefined")) {
                        result = result.concat("\nPrinter Type: Undefined");
                    }
                } else {
                    result = super.toString() + "\nTechonology Type: " + getPrintTech();
                    if (getPrintType().equals("Undefined"))
                        result = result.concat("\nPrinter Type: Undefined");
                }
            } else if (!bool1) {
                result = super.toString() + "\nPrinter Type: " + getPrintType();
                if (getPrintTech().equals("Undefined"))
                    result = result.concat("\nTechonology Type: Undefined");
            }
        } else {
            result = "";
        }
        return result;

    } //toString

} //Printer
