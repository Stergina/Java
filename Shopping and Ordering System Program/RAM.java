//We define class RAM as a subclass of the HardWare class.
public class RAM extends HardWare {
    //We define the type of each variable of the RAM class. 
    private String RAMtype = "Undefined"; //Type of RAM. The default case is "Undefined".
    private int RAMsize = -1; //Size of RAM. The default case is -1.
    private int RAMfreq = -1; //Frequency of RAM. The default case is -1.

    //We create the "get" and "set" functions in order to access the aforementioned private variables.	
    public String getRAMtype() {
        return RAMtype;
    } //getRAMtype

    public void setRAMtype(String type) {
        if (type.equals("DDR2")) {
            this.RAMtype = "DDR2";
        } else if (type.equals("DDR3")) {
            this.RAMtype = "DDR3";
        } else if (type.equals("DDR4")) {
            this.RAMtype = "DDR4";
        } else {
            this.RAMtype = "Wrong input. Try again."; //In case where RAMtype is not DDR2, DDR3, DDR4 or missing ("Undefined").
        }
    } //setRAMtype

    public int getRAMsize() {
        return RAMsize;
    } //getRAMsize

    public void setRAMsize(int size) {
        if (size == 4) {
            this.RAMsize = 4;
        } else if (size == 8) {
            this.RAMsize = 8;
        } else if (size == 16) {
            this.RAMsize = 16;
        } else {
            this.RAMsize = -2; //In case where RAMsize is not 4, 8, 16 or missing (-1).
        }
    } //setRAMsize

    public int getRAMfreq() {
        return RAMfreq;
    } //getRAMfreq

    public void setRAMfreq(int freq) {
        if (freq == 1600) {
            this.RAMfreq = 1600;
        } else if (freq == 2666) {
            this.RAMfreq = 2666;
        } else if (freq == 4600) {
            this.RAMfreq = 4600;
        } else {
            this.RAMfreq = -2; //In case where RAMfreq is not 1600, 2666, 4600 or missing (-1).
        }
    } //setRAMfreq

    //We get the description of the CPU by creating the toString function. When a characteristic is missing it will be assigned as
    //"Undefined" whilst if it is wrongly defined it won't be displayed at all.
    @Override
    public String toString() {
        boolean bool1 = (getRAMtype().equals("DDR2") || getRAMtype().equals("DDR3") || getRAMtype().equals("DDR4"));
        boolean bool2 = (getRAMsize() == 4 || getRAMsize() == 8 || getRAMsize() == 16);
        boolean bool3 = (getRAMfreq() == 1600 || getRAMfreq() == 2666 || getRAMfreq() == 4600);
        String result = null;

        if (super.toString() != null) {
            if ((bool1) && (bool2) && (bool3)) {
                result = super.toString() + "\nType: " + getRAMtype() + "\nSize: " + getRAMsize() + " GB" +
                    "\nFrequency: " + getRAMfreq() + " MHz";
            } else if (!bool2) {
                if ((!bool1) && (!bool3)) {
                    result = super.toString();
                    if ((getRAMsize() == -1) && (getRAMfreq() == -1) && (getRAMtype().equals("Undefined"))) {
                        result = result.concat("\nType: Undefined" + "\nSize: Undefined" + "\nFrequency: Undefined");
                    } else if ((getRAMsize() == -1) && (getRAMfreq() == -1)) {
                        result = result.concat("\nType: " + getRAMtype() + "\nSize: Undefined" + "\nFrequency: Undefined");
                    } else if ((getRAMsize() == -1) && (getRAMtype().equals("Undefined"))) {
                        result = result.concat("\nType: Undefined" + "\nSize: Undefined" + "\nFrequency: " + getRAMfreq());
                    } else if ((getRAMfreq() == -1) && (getRAMtype().equals("Undefined"))) {
                        result = result.concat("\nType: Undefined" + "\nSize: " + getRAMsize() + "\nFrequency: Undefined");
                    } else if (getRAMsize() == -1) {
                        result = result.concat("\nSize: Undefined");
                    } else if (getRAMfreq() == -1) {
                        result = result.concat("\nFrequency: Undefined");
                    } else if (getRAMtype().equals("Undefined")) {
                        result = result.concat("\nType: Undefined");
                    }
                } else if (!bool3) {
                    result = super.toString() + "\nType: " + getRAMtype();
                    if ((getRAMsize() == -1) && (getRAMfreq() == -1)) {
                        result = result.concat("\nSize: Undefined" + "\nFrequency: Undefined");
                    } else if (getRAMsize() == -1) {
                        result = result.concat("\nSize: Undefined");
                    } else if (getRAMfreq() == -1) {
                        result = result.concat("\nFrequency: Undefined");
                    }
                } else if (!bool1) {
                    result = super.toString() + "\nFrequency: " + getRAMfreq() + " MHz";
                    if ((getRAMsize() == -1) && (getRAMtype().equals("Undefined"))) {
                        result = result.concat("\nSize: Undefined" + "\nType: Undefined");
                    } else if (getRAMtype().equals("Undefined")) {
                        result = result.concat("\nType: Undefined");
                    } else if (getRAMsize() == -1) {
                        result = result.concat("\nSize: Undefined");
                    }
                } else {
                    result = super.toString() + "\nType: " + getRAMtype() + "\nFrequency: " + getRAMfreq() + " MHz";
                    if (getRAMsize() == -1) {
                        result = result.concat("\nSize: Undefined");
                    }
                }
            } else if (!bool3) {
                if (!bool1) {
                    result = super.toString() + "\nSize: " + getRAMsize() + " GB";
                    if ((getRAMfreq() == -1) && (getRAMtype().equals("Undefined"))) {
                        result = result.concat("\nFrequency: Undefined" + "\nType: Undefined");
                    } else if (getRAMfreq() == -1) {
                        result = result.concat("\nFrequency: Undefined");
                    } else if (getRAMtype().equals("Undefined")) {
                        result = result.concat("\nType: Undefined");
                    }
                } else {
                    result = super.toString() + "\nCPU's Type: " + getRAMtype() + "\nSize: " + getRAMsize() + " GB";
                    if (getRAMfreq() == -1) {
                        result = result.concat("\nFrequency: Undefined");
                    }
                }
            } else if (!bool1) {
                result = super.toString() + "\nSize: " + getRAMsize() + " GB" + "\nFrequency: " + getRAMfreq() + " MHz";
                if (getRAMtype().equals("Undefined")) {
                    result = result.concat("\nType: Undefined");
                }
            }
        } else {
            result = "";
        }
        return result;

    } //toString

} //RAM
