//We define class HardDisk as a subclass of the HardWare class.
public class HardDisk extends HardWare {
    //We define the type of each variable of the HardDisk class.
    private String DiskType = "Undefined"; //Type of HardDisk. The default case is "Undefined".
    private static final String DiskType1 = "HDD"; //Type "HDD" of HardDisk.
    private static final String DiskType2 = "SSD"; //Type "SSD" of HardDisk.	
    private double DiskSize = -1; //Size of the HardDisk. The default case is -1.
    private int DiskCapacity = -1; //HardDisk's capacity. The default case is -1.

    //We create the "get" and "set" functions in order to access the aforementioned private variables.	
    public String getDiskType() {
        return DiskType;
    } //getDiskType

    public void setDiskType(int type) {
        if (type == 1) {
            this.DiskType = DiskType1;
        } else if (type == 2) {
            this.DiskType = DiskType2;
        } else {
            this.DiskType = "Wrong input. Try again."; //In case where DiskType is not HDD, SSD or missing ("Undefined").
        }
    } //setDiskType

    public double getDiskSize() {
        return DiskSize;
    } //getDiskSize

    public void setDiskSize(double size) {
        if (size == 1.8) {
            this.DiskSize = 1.8;
        } else if (size == 2.5) {
            this.DiskSize = 2.5;
        } else if (size == 3.5) {
            this.DiskSize = 3.5;
        } else {
            this.DiskSize = -2; //In case where DiskSize is not 1.8, 2.5, 3.5 or missing (-1).
        }
    } //setDiskSize

    public int getDiskCapacity() {
        return DiskCapacity;
    } //getDiskCapacity

    public void setDiskCapacity(int capacity) {
        if (capacity == 256) {
            this.DiskCapacity = 256;
        } else if (capacity == 500) {
            this.DiskCapacity = 500;
        } else if (capacity == 750) {
            this.DiskCapacity = 750;
        } else if (capacity == 1000) {
            this.DiskCapacity = 1000;
        } else {
            this.DiskCapacity = -2; //In case where DiskCapacity is not 256, 500, 750, 1000 or missing (-1).
        }
    } //setDiskCapacity

    //We get the description of the HardDisk by creating the toString function. When a characteristic is missing it will be assigned as
    //"Undefined" whilst if it is wrongly defined it won't be displayed at all.
    @Override
    public String toString() {
        boolean bool1 = (getDiskType().equals("HDD") || getDiskType().equals("SSD"));
        boolean bool2 = (getDiskSize() == 1.8 || getDiskSize() == 2.5 || getDiskSize() == 3.5);
        boolean bool3 = (getDiskCapacity() == 256 || getDiskCapacity() == 500 || getDiskCapacity() == 750 || getDiskCapacity() == 1000);
        String result = null;

        if (super.toString() != null) {
            if ((bool1) && (bool2) && (bool3)) {
                result = super.toString() + "\nType: " + getDiskType() + "\nSize: " + getDiskSize() + " inches" +
                    "\nCapacity: " + getDiskCapacity() + " GB";
            } else if (!bool2) {
                if ((!bool1) && (!bool3)) {
                    result = super.toString();
                    if ((getDiskSize() == -1) && (getDiskCapacity() == -1) && (getDiskType().equals("Undefined"))) {
                        result = result.concat("\nType: Undefined" + "\nSize: Undefined" + "\nCapacity: Undefined");
                    } else if ((getDiskSize() == -1) && (getDiskCapacity() == -1)) {
                        result = result.concat("\nSize: Undefined" + "\nCapacity: Undefined");
                    } else if ((getDiskSize() == -1) && (getDiskType().equals("Undefined"))) {
                        result = result.concat("\nType: Undefined" + "\nSize: Undefined");
                    } else if ((getDiskCapacity() == -1) && (getDiskType().equals("Undefined"))) {
                        result = result.concat("\nType: Undefined" + "\nCapacity: Undefined");
                    } else if (getDiskSize() == -1) {
                        result = result.concat("\nSize: Undefined");
                    } else if (getDiskCapacity() == -1) {
                        result = result.concat("\nCapacity: Undefined");
                    } else if (getDiskType().equals("Undefined")) {
                        result = result.concat("\nType: Undefined");
                    }
                } else if (!bool3) {
                    result = super.toString() + "\nType: " + getDiskType();
                    if ((getDiskSize() == -1) && (getDiskCapacity() == -1)) {
                        result = result.concat("\nSize: Undefined" + "\nCapacity: Undefined");
                    } else if (getDiskSize() == -1) {
                        result = result.concat("\nSize: Undefined");
                    } else if (getDiskCapacity() == -1) {
                        result = result.concat("\nCapacity: Undefined");
                    }
                } else if (!bool1) {
                    result = super.toString() + "\nCapacity: " + getDiskCapacity() + " GB";
                    if ((getDiskSize() == -1) && (getDiskType().equals("Undefined"))) {
                        result = result.concat("\nSize: Undefined" + "\nType: Undefined");
                    } else if (getDiskType().equals("Undefined")) {
                        result = result.concat("\nType: Undefined");
                    } else if (getDiskSize() == -1) {
                        result = result.concat("\nSize: Undefined");
                    }
                } else {
                    result = super.toString() + "\nType: " + getDiskType() + "\nCapacity: " + getDiskCapacity() + " GB";
                    if (getDiskSize() == -1) {
                        result = result.concat("\nSize: Undefined");
                    }
                }
            } else if (!bool3) {
                if (!bool1) {
                    result = super.toString() + "\nSize: " + getDiskSize() + " inches";
                    if ((getDiskCapacity() == -1) && (getDiskType().equals("Undefined"))) {
                        result = result.concat("\nType: Undefined" + "\nCapacity: Undefined");
                    } else if (getDiskCapacity() == -1) {
                        result = result.concat("\nCapacity: Undefined");
                    } else if (getDiskType().equals("Undefined")) {
                        result = result.concat("\nType: Undefined");
                    }
                } else {
                    result = super.toString() + "\nType: " + getDiskType() + "\nSize: " + getDiskSize() + " inches";
                    if (getDiskCapacity() == -1) {
                        result = result.concat("\nCapacity: Undefined");
                    }
                }
            } else if (!bool1) {
                result = super.toString() + "\nSize: " + getDiskSize() + " inches" + "\nCapacity: " + getDiskCapacity() + " GB";
                if (getDiskType().equals("Undefined")) {
                    result = result.concat("\nType: Undefined");
                }
            }
        } else {
            result = "";
        }
        return result;

    } //toString

} //HardDisk
