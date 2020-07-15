//We define class Mouse as a subclass of the Peripherals class.
public class Mouse extends Peripherals
{
	//We define the type of each variable of the Mouse class.
	private String MouseTech = "Undefined"; //Mouse's technology type. The default case is "Undefined".
	private static final String MouseTech1 = "Laser";  //Mouse's technology type "Laser".
	private static final String MouseTech2 = "Optical";  //Mouse's technology type "Optical".
	private String MouseCon = "Undefined"; //Mouse's connection type. The default case is "Undefined".
	private static final String MouseCon1 = "Wired";  //Mouse's connection type "Wired".
	private static final String MouseCon2 = "Wireless";  //Mouse's connection type "Wireless".

	//We create the "get" and "set" functions in order to access the aforementioned private variables.	
	public String getMouseTech()
	{
		return MouseTech;
    }//getMouseTech
	
	public void setMouseTech(int type)
	{
		if (type==1){
		    this.MouseTech = MouseTech1;
		}else if (type==2){
			this.MouseTech = MouseTech2;
		}else{
			this.MouseTech = "Wrong input. Try again.";  //In case where MouseTech is not Laser, Optical or missing ("Undefined").
		}
	}//setMouseTech
	
	public String getMouseCon()
	{
		return MouseCon;
    }//getMouseCon
	
	public void setMouseCon(int type)
	{
		if (type==1){
		    this.MouseCon = MouseCon1;
		}else if (type==2){
			this.MouseCon = MouseCon2;
		}else{
			this.MouseCon = "Wrong input. Try again.";  //In case where MouseCon is not Wired, Wireless or missing ("Undefined").
		}
	}//setMouseCon
	
	//We get the description of the Mouse by creating the toString function. When a characteristic is missing it will be assigned as
	//"Undefined" whilst if it is wrongly defined it won't be displayed at all.
	@Override
	public String toString()
	{
		boolean bool1 = (getMouseTech().equals("Laser") || getMouseTech().equals("Optical") || getMouseTech().equals("Undefined"));
		boolean bool2 = (getMouseCon().equals("Wired") || getMouseCon().equals("Wireless") || getMouseCon().equals("Undefined"));
		String result = null;
		
		if (super.toString() != null){
			if ((bool1) && (bool2)){			
				result = super.toString() + "\nTechnology Type: " + getMouseTech() + "\nConnection type: " + getMouseCon();
			}else if (!bool2){
				if (!bool1){
					result = super.toString();
					if (getMouseTech().equals("Undefined") && getMouseCon().equals("Undefined")){
						result = result.concat("\nTechonology Type: Undefined" + "\nConnection type: Undefined");
					}else if (getMouseTech().equals("Undefined")){
						result = result.concat("\nTechonology Type: Undefined");
					}else if (getMouseCon().equals("Undefined")){
						result = result.concat("\nConnection Type: Undefined");
					}
				}else{
					result = super.toString() + "\nTechnology Type: " + getMouseTech();
					if (getMouseCon().equals("Undefined"))
					result = result.concat("\nConnection Type: Undefined");
				}	
			}else if (!bool1){
				result = super.toString() + "\nConnection Type: " + getMouseCon();
				if (getMouseTech().equals("Undefined"))
				result = result.concat("\nTechnology Type: Undefined");
			}
		}else{
			result = "";
		}
		return result;
		
	}//toString
	
}//Mouse