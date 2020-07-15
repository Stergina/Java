//We define class Graphics as a subclass of the HardWare class.
public class Graphics extends HardWare
{
	//We define the type of each variable of the Graphics class.
	private String Chipset = "Undefined"; //Type of Graphics card. The default case is "Undefined".
	private static final String Chipset1 = "nVidia";  //Type "nVidia" of Graphics card.
	private static final String Chipset2 = "AMD";  //Type "AMD" of Graphics card.
	private int GraphMemory = -1; //Memory of Graphics card. The default case is -1.	

	//We create the "get" and "set" functions in order to access the aforementioned private variables.
	public String getChipset()
	{
		return Chipset;
    }//getChipset
	
	public void setChipset(int type)
	{
		if (type==1){
		    this.Chipset = Chipset1;
		}else if (type==2){
			this.Chipset = Chipset2;
		}else{
			this.Chipset = "Wrong number. Try again.";  //In case where Chipset is not nVidia, AMD or missing ("Undefined").
		}
	}//setChipset
	
	public int getGraphMemory()
	{
		return GraphMemory;
    }//getGraphMemory
	
	public void setGraphMemory(int memory)
	{
		if (memory==2){
		    this.GraphMemory = 2;
		}else if (memory==4){
			this.GraphMemory = 4;
		}else if (memory==6){
			this.GraphMemory = 6;
		}else{
			this.GraphMemory = -2;  //In case where GraphMemory is not 2, 4, 6 or missing (-1).
		}
	}//setGraphMemory
	
	//We get the description of the Graphics card by creating the toString function. When a characteristic is missing it will be assigned as
	//"Undefined" whilst if it is wrongly defined it won't be displayed at all.
	@Override
	public String toString()
	{
		boolean bool1 = (getChipset().equals("nVidia") || getChipset().equals("AMD"));
		boolean bool2 = (getGraphMemory() == 2 || getGraphMemory() == 4 || getGraphMemory() == 6);
		String result = null;
		
		if (super.toString() != null){
			if ((bool1) && (bool2)){			
				result = super.toString() + "\nType: " + getChipset() + "\nMemory Size: " + getGraphMemory() + " GB";
			}else if (!bool2){
				if ((!bool1)){
					result = super.toString();
					if ((getGraphMemory() == -1) && (getChipset().equals("Undefined"))){
						result = result.concat("\nMemory Size: Undefined" + "\nType: Undefined");
					}else if (getGraphMemory() == -1){
						result = result.concat("\nMemory Size: Undefined");
					}else if (getChipset().equals("Undefined")){
						result = result.concat("\nType: Undefined");
					}
				}else{
					result = super.toString() + "\nType: " + getChipset(); 
					if (getGraphMemory() == -1){
						result = result.concat("\nMemory Size: Undefined");
					}
				}
			}else if (!bool1){	
				result = super.toString() + "\nMemory Size: " + getGraphMemory();
				if (getChipset().equals("Undefined")){
					result = result.concat("\nType: Undefined");
				}
			}
		}else{
			result = "";
		}
		return result;

	}//toString
	
}//Graphics