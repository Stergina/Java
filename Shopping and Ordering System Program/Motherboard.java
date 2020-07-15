//We define class Motherboard as a subclass of the HardWare class.
public class Motherboard extends HardWare
{
	//We define the type of each variable of the Motherboard class.
	private String CPUtype = "Undefined"; //Type of CPU. The default case is "Undefined".
	private static final String CPUtype1 = "Intel";  //Type "Intel" of CPU.
	private static final String CPUtype2 = "AMD";  //Type "AMD" of CPU.	
	private int MBmemory = -1; //Memory of the motherboard. The default case is -1.
	private int MBports = -1; //Number of motherboard's SATA ports. The default case is -1.		

	//We create the "get" and "set" functions in order to access the aforementioned private variables.	
	public String getCPUtype()
	{
		return CPUtype;
    }//getCPUtype
	
	public void setCPUtype(int type)
	{
		if (type==1){
		    this.CPUtype = CPUtype1;
		}else if (type==2){
			this.CPUtype = CPUtype2;
		}else{
			this.CPUtype = "Wrong input. Try again!";  //In case where CPUtype is not Intel, AMD or missing ("Undefined").
		}
	}//setCPUtype
	
	public int getMBmemory()
	{
		return MBmemory;
    }//getMBmemory
	
	public void setMBmemory(int memory)
	{
		if (memory==32){
			this.MBmemory = 32;
		}else if (memory==64){
			this.MBmemory = 64;
		}else if (memory==128){
			this.MBmemory = 128;
		}else{
			this.MBmemory = -2;  //In case where MBmemory is not 32, 64, 128 or missing (-1).
		}
	}//setMBmemory
	
	public int getMBports()
	{
		return MBports;
    }//getMBports
	
	public void setMBports(int ports)
	{
		if (ports==4){
		    this.MBports = 4;
		}else if (ports==6){
			this.MBports = 6;
		}else if (ports==8){
			this.MBports = 8;
		}else{
			this.MBports = -2;  //In case where MBports is not 4, 6, 8 or missing (-1).
		}
	}//setMBports

	//We get the description of the Motherboard by creating the toString function. When a characteristic is missing it will be assigned as
	//"Undefined" whilst if it is wrongly defined it won't be displayed at all.
	@Override
	public String toString()
	{
		boolean bool1 = (getCPUtype().equals("Intel") || getCPUtype().equals("AMD"));
		boolean bool2 = (getMBmemory() == 32 || getMBmemory() == 64 || getMBmemory() == 128);
		boolean bool3 = (getMBports() == 4 || getMBports() == 6 || getMBports() == 8);
		String result = null;
		
		if (super.toString() != null){
			if ((bool1) && (bool2) && (bool3)){			
				result = super.toString() + "\nCPU's type: " + getCPUtype() + "\nMemory Size: " + 
						 getMBmemory() + " GB" + "\nSATA Ports: " + getMBports();
			}else if (!bool2){
				if ((!bool1) && (!bool3)){
					result = super.toString();
					if ((getMBmemory() == -1) && (getMBports() == -1) && (getCPUtype().equals("Undefined"))){
						result = result.concat("\nMemory Size: Undefined" + "\nSATA Ports: Undefined" + "\nCPU's type: Undefined");
					}else if ((getMBmemory() == -1) && (getMBports() == -1)){
						result = result.concat("\nMemory Size: Undefined" + "\nSATA Ports: Undefined");
					}else if ((getMBmemory() == -1) && (getCPUtype().equals("Undefined"))){
						result = result.concat("\nMemory Size: Undefined" + "\nCPU's type: Undefined");
					}else if ((getMBports() == -1) && (getCPUtype().equals("Undefined"))){
						result = result.concat("\nSATA Ports: Undefined" + "\nCPU's type: Undefined");
					}else if (getMBmemory() == -1){
						result = result.concat("\nMemory Size: Undefined");
					}else if (getMBports() == -1){
						result = result.concat("\nSATA Ports: Undefined");
					}else if (getCPUtype().equals("Undefined")){
						result = result.concat("\nCPU's type: Undefined");
					}
				}else if(!bool3){
					result = super.toString() + "\nCPU's type: " + getCPUtype();
					if ((getMBmemory() == -1) && (getMBports() == -1)){
						result = result.concat("\nMemory Size: Undefined" + "\nSATA Ports: Undefined");
					}else if (getMBports() == -1){
						result = result.concat("\nSATA Ports: Undefined");
					}else if (getMBmemory() == -1){
					result = result.concat("\nMemory Size: Undefined");
					}
				}else if (!bool1){
					result = super.toString() + "\nSATA Ports: " + getMBports();
					if ((getMBmemory() == -1) && (getCPUtype().equals("Undefined"))){
						result = result.concat("\nMemory Size: Undefined" + "\nCPU's type: Undefined");
					}else if (getCPUtype().equals("Undefined")){
						result = result.concat("\nCPU's type: Undefined");
					}else if (getMBmemory() == -1){
					result = result.concat("\nMemory Size: Undefined");
					}
				}else{
					result = super.toString() + "\nCPU's type: " + getCPUtype() + "\nSATA Ports: " + getMBports(); 
					if (getMBmemory() == -1){
						result = result.concat("\nMemory Size: Undefined");
					}
				}	
			}else if (!bool3){
				if (!bool1){
					result = super.toString() + "\nMemory Size: " + getMBmemory() + " GB";
					if ((getMBports() == -1) && (getCPUtype().equals("Undefined"))){
						result = result.concat("\nSATA Ports: Undefined" + "\nCPU's type: Undefined");
					}else if (getMBports() == -1){
						result = result.concat("\nSATA Ports: Undefined");
					}else if (getCPUtype().equals("Undefined")){
						result = result.concat("\nCPU's type: Undefined");
					}
				}else{
					result = super.toString() + "\nCPU's type: " + getCPUtype() +  "\nMemory Size: " + getMBmemory() + " GB";
					if (getMBports() == -1){
						result = result.concat("\nSATA Ports: Undefined");
					}
				}
			}else if (!bool1){	
				result = super.toString() + "\nMemory Size: " + getMBmemory() + " GB" + "\nSATA Ports: " + getMBports();
				if (getCPUtype().equals("Undefined")){
					result = result.concat("\nCPU's type: Undefined");
				}
			}
		}else{
			result = "";
		}
		return result;
			
	}//toString
	
}//Motherboard