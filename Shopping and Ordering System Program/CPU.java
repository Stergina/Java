//We define class CPU as a subclass of the HardWare class.
public class CPU extends HardWare
{ 
	//We define the type of each variable of the CPU class.
	private double CPUspeed = -1;  //CPU's speed. The default case is -1.	
	private int CPUcores = -1;  //CPU's number of cores. The default case is -1.	
	private String CPUgraph = "Undefined"; //CPU's graphics type.  The default case is "Undefined".
	private static final String CPUgraph1 = "Embedded Graphics";  //CPU's graphics type "Embedded Graphics".
	private static final String CPUgraph2 = "Non Embedded Graphics";  //CPU's graphics type "Non Embedded Graphics".	

	//We create the "get" and "set" functions in order to access the aforementioned private variables.	
    public double getCPUspeed()
	{
		return CPUspeed;
    }//getCPUspeed
	
	public void setCPUspeed(double speed)
	{
		if (speed==3){
		    this.CPUspeed = 3;
		}else if (speed==3.6){
			this.CPUspeed = 3.6;
		}else if (speed==4){
			this.CPUspeed = 4;
		}else{
			this.CPUspeed = -2;  //In case where CPUspeed is not 3, 3.6, 4 or missing (-1).
		}
	}//setCPUspeed
	
	public int getCPUcores()
	{
		return CPUcores;
    }//getCPUcores
	
	public void setCPUcores(int cores)
	{
		if (cores==6){
		    this.CPUcores = 6;
		}else if (cores==8){
			this.CPUcores = 8;
		}else if (cores==16){
			this.CPUcores = 16;
		}else{
			this.CPUcores = -2;  //In case where CPUcores is not 6, 8, 16 or missing (-1).
		}
	}//setCPUcores
		
	public String getCPUgraph()
	{
		return CPUgraph;
    }//getCPUgraph
	
	public void setCPUgraph(int type)
	{
		if (type==1){
		    this.CPUgraph = CPUgraph1;
		}else if (type==2){
			this.CPUgraph = CPUgraph2;
		}else{
			this.CPUgraph = "Wrong input. Try again.";  //In case where CPUgraph is not Embedded Graphics, Non Embedded Graphics or missing ("Undefined").
		}
	}//setCPUgraph
	
	//We get the description of the CPU by creating the toString function. When a characteristic is missing it will be assigned as
	//"Undefined" whilst if it is wrongly defined it won't be displayed at all.
	@Override
	public String toString()
	{
		boolean bool1 = (getCPUgraph().equals("Embedded Graphics") || getCPUgraph().equals("Non Embedded Graphics"));
		boolean bool2 = (getCPUspeed() == 3 || getCPUspeed() == 3.6 || getCPUspeed() == 4);
		boolean bool3 = (getCPUcores() == 6 || getCPUcores() == 8 || getCPUcores() == 16);
		String result = null;
		
		if (super.toString() != null){
			if ((bool1) && (bool2) && (bool3)){			
				result = super.toString() + "\nSpeed: " + getCPUspeed() + " GHz" + "\nCores: " + getCPUcores() + 
						 "\nGraphics Type: " + getCPUgraph();
			}else if (!bool2){
				if ((!bool1) && (!bool3)){
					result = super.toString();
					if ((getCPUspeed() == -1) && (getCPUcores() == -1) && (getCPUgraph().equals("Undefined"))){
						result = result.concat("\nSpeed: Undefined" + "\nCores: Undefined" + "\nGraphics Type: Undefined");
					}else if ((getCPUspeed() == -1) && (getCPUcores() == -1)){
						result = result.concat("\nSpeed: Undefined" + "\nCores: Undefined");
					}else if ((getCPUspeed() == -1) && (getCPUgraph().equals("Undefined"))){
						result = result.concat("\nSpeed: Undefined" + "\nGraphics Type: Undefined");
					}else if ((getCPUcores() == -1) && (getCPUgraph().equals("Undefined"))){
						result = result.concat("\nCores: Undefined" + "\nGraphics Type: Undefined");
					}else if (getCPUspeed() == -1){
						result = result.concat("\nSpeed: Undefined");
					}else if (getCPUcores() == -1){
						result = result.concat("\nCores: Undefined");
					}else if (getCPUgraph().equals("Undefined")){
						result = result.concat("\nGraphics Type: Undefined");
					}
				}else if (!bool3){
					result = super.toString() + "\nGraphics Type: " + getCPUgraph();
					if ((getCPUspeed() == -1) && (getCPUcores() == -1)){
						result = result.concat("\nSpeed: Undefined" + "\nCores: Undefined");
					}else if (getCPUcores() == -1){
						result = result.concat("\nCores: Undefined");
					}else if (getCPUspeed() == -1){
					result = result.concat("\nSpeed: Undefined");
					}
				}else if (!bool1){
					result = super.toString() + "\nCores: " + getCPUcores();
					if ((getCPUspeed() == -1) && (getCPUgraph().equals("Undefined"))){
						result = result.concat("\nSpeed: Undefined" + "\nGraphics Type: Undefined");
					}else if (getCPUgraph().equals("Undefined")){
						result = result.concat("\nGraphics Type: Undefined");
					}else if (getCPUspeed() == -1){
						result = result.concat("\nSpeed: Undefined");
					}
				}else{
					result = super.toString() + "\nCores: " +  getCPUcores() + "\nGraphics Type: " + getCPUgraph();
					if (getCPUspeed() == -1){
						result = result.concat("\nSpeed: Undefined");
					}
				}	
			}else if (!bool3){
				if (!bool1){
					result = super.toString() + "\nSpeed: " + getCPUspeed();
					if ((getCPUcores() == -1) && (getCPUgraph().equals("Undefined"))){
						result = result.concat("\nCores: Undefined" + "\nGraphics Type: Undefined");
					}else if (getCPUcores() == -1){
						result = result.concat("\nCores: Undefined");
					}else if (getCPUgraph().equals("Undefined")){
						result = result.concat("\nGraphics Type: Undefined");
					}
				}else{
					result = super.toString() + "\nSpeed: " + getCPUspeed() + " GHz" + "\nGraphics Type: " + getCPUgraph();
					if (getCPUcores() == -1){
						result = result.concat("\nCores: Undefined");
					}
				}
			}else if (!bool1){	
				result = super.toString() + "\nSpeed: " + getCPUspeed() + " GHz" + "\nCores: " + getCPUcores();
				if (getCPUgraph().equals("Undefined")){
					result = result.concat("\nGraphics Type: Undefined");
				}
			}
		}else{
			result = "";
		}
		return result;
		
	}//toString
	
}//CPU