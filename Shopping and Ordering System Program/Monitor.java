//We define class Monitor as a subclass of the Peripherals class. 
public class Monitor extends Peripherals
{
	//We define the type of each variable of the Monitor class.
	private String MonTech = "Undefined"; //Monitor's type. The default case is "Undefined".
	private static final String MonTech1 = "LCD";  //Monitor type "LCD".
	private static final String MonTech2 = "LED";  //Monitor type "LED".
	private double MonSize = -1;  //Monitor's size. The default case is -1.
	private String MonRes = "Undefined"; //Monitor's resolution. The default case is "Undefined".
	private String MonPorts = "Undefined"; //Monitor's ports. The default case is "Undefined".

	//We create the "get" and "set" functions in order to access the aforementioned private variables.	
	public String getMonTech()
	{
		return MonTech;
    }//getMonTech
	
	public void setMonTech(int type)
	{
		if (type==1){
		    this.MonTech = MonTech1;
		}else if (type==2){
			this.MonTech = MonTech2;
		}else{
			this.MonTech = "Wrong input. Try again.";  //In case where MonTech is not LCD, LED or missing ("Undefined").
		}
	}
	
	public double getMonSize()
	{
		return MonSize;
    }//getMonSize
	
	public void setMonSize(double size)
	{
		if (size==21.5){
			this.MonSize = size;
		}else if (size==24){
			this.MonSize = size;
		}else{
			this.MonSize = -2;  //In case where MonSize is not 21.5, 24 or missing (-1).
		}
	}//setMonSize
	
	public String getMonRes()
	{
		return MonRes;
    }//getMonRes
	
	public void setMonRes(String res)
	{
		if (res.equals("1920x1080")){
			this.MonRes = res;
		}else if (res.equals("1920x1200")){
			this.MonRes = res;
		}else{
			this.MonRes = "Wrong input. Try again.";  //In case where MonRes is not 1920x1080, 1920x1200 or missing ("Undefined").
		}
	}//setMonRes
	
    public String getMonPorts()
	{
		return MonPorts;
    }//getMonPorts
	
	public void setMonPorts(String ports)
	{
		if (ports.equals("Display Port")){
			this.MonPorts = "Display Port";
		}else if (ports.equals("HDMI")){
			this.MonPorts = "HDMI";
		}else if (ports.equals("DVI")){
			this.MonPorts = "DVI";
		}else{
			this.MonPorts = "Wrong input. Try again.";  //In case where MonPorts is not Display Port, HDMI, DVI or missing ("Undefined").
		}
	}//setMonPorts
		
	//We get the description of the Monitor by creating the toString function. When a characteristic is missing it will be assigned as
	//"Undefined" whilst if it is wrongly defined it won't be displayed at all.
	@Override
	public String toString()
	{
		boolean bool1 = (getMonTech().equals("LCD") || getMonTech().equals("LED"));
		boolean bool2 = (getMonSize() == 21.5 || getMonSize() == 24);
		boolean bool3 = (getMonRes().equals("1920x1080") || getMonRes().equals("1920x1200") || getMonRes().equals("Undefined"));
		boolean bool4 = (getMonPorts().equals("Display Port") || getMonPorts().equals("HDMI") || getMonPorts().equals("DVI") || getMonPorts().equals("Undefined"));
		String result = null;
		
		if (super.toString() != null){			
			if ((bool1) && (bool2) && (bool3) && (bool4)){			
				result = super.toString() + "\nType: " + getMonTech() + "\nSize: " + getMonSize() + "\"" + 
						 "\nResolution: " + getMonRes() + "\nPorts: " + getMonPorts();
			}else if (!bool2){
				if ((!bool1) && (!bool3) && (!bool4)){
					result = super.toString();
					if ((getMonTech().equals("Undefined")) && (getMonSize() == -1)){
						result = result.concat("\nType: Undefined" + "\nSize: Undefined");
					}else if (getMonTech().equals("Undefined")){
						result = result.concat("\nType: Undefined");
					}else if (getMonSize() == -1){
						result = result.concat("\nSize: Undefined");
					}
				}else if((!bool1) && (!bool3)){
					result = super.toString() + "\nPorts: " + getMonPorts();
					if ((getMonTech().equals("Undefined")) && (getMonSize() == -1)){
						result = result.concat("\nType: Undefined" + "\nSize: Undefined");
					}else if (getMonTech().equals("Undefined")){
						result = result.concat("\nType: Undefined");
					}else if (getMonSize() == -1){
						result = result.concat("\nSize: Undefined");
					}
				}else if ((!bool1) && (!bool4)){
					result = super.toString() + "\nResolution: " + getMonRes();
					if ((getMonTech().equals("Undefined")) && (getMonSize() == -1)){
						result = result.concat("\nType: Undefined" + "\nSize: Undefined");
					}else if (getMonTech().equals("Undefined")){
						result = result.concat("\nType: Undefined");
					}else if (getMonSize() == -1){
						result = result.concat("\nSize: Undefined");
					}
				}else if (!bool3 && (!bool4)){
					result = super.toString() + "\nType: " + getMonTech();
					if (getMonSize() == -1)
					result = result.concat("\nSize: Undefined");
				}else if (!bool1){	
					result = super.toString() + "\nResolution: " + getMonRes() + "\nPorts: " + getMonPorts();
					if ((getMonTech().equals("Undefined")) && (getMonSize() == -1)){
						result = result.concat("\nType: Undefined" + "\nSize: Undefined");
					}else if (getMonTech().equals("Undefined")){
						result = result.concat("\nType: Undefined");
					}else if (getMonSize() == -1){
						result = result.concat("\nSize: Undefined");
					}
				}else if (!bool3){	
					result = super.toString() + "\nType: " + getMonTech() + "\nPorts: " + getMonPorts();
					if (getMonSize() == -1)
					result = result.concat("\nSize: Undefined");		
				}else if (!bool4){	
					result = super.toString() + "\nType: " + getMonTech() + "\nResolution: " + getMonRes();
					if (getMonSize() == -1)
					result = result.concat("\nSize: Undefined");					
				}else{
					result = super.toString() + "\nType: " + getMonTech() + "\nResolution: " + getMonRes() + "\nPorts: " + getMonPorts();
					if (getMonSize() == -1)
					result = result.concat("\nSize: Undefined");					
				}			 
			}else if (!bool3){
				if((!bool1) && (!bool4)){
					result = super.toString() + "\nSize: " + getMonSize() + "\"";
					if (getMonTech().equals("Undefined"))
					result = result.concat("\nType: Undefined");	
				}else if (!bool1){	
					result = super.toString() + "\nSize: " + getMonSize() + "\"" + "\nPorts: " + getMonPorts();
					if (getMonTech().equals("Undefined"))
					result = result.concat("\nType: Undefined");					
				}else if (!bool4){	
					result = super.toString() + "\nType: " + getMonTech() + "\nSize: " + getMonSize() + "\"";
				}else{
					result = super.toString() + "\nType: " + getMonTech() + "\nSize: " + getMonSize() + "\"" + 
							 "\nPorts: " + getMonPorts();
				}
			}else if (!bool4){	
				if (!bool1){	
					result = super.toString() +  "\nSize: " + getMonSize() + "\"" + "\nResolution: " + getMonRes();
					if (getMonTech().equals("Undefined"))
					result = result.concat("\nType: Undefined");	
				}else{
					result = super.toString() + "\nType: " + getMonTech() + "\nSize: " + getMonSize() + "\"" + 
							 "\nResolution: " + getMonRes();
				}			 
			}else if (!bool1){	
					result = super.toString() + "\nSize: " + getMonSize() + "\"" + "\nResolution: " + getMonRes() + "\nPorts: " + getMonPorts();	
					if (getMonTech().equals("Undefined"))
					result = result.concat("\nType: Undefined");								 
			}
		}else{
			result = "";
		}
		return result;
		
	}//toString

}//Monitor