package Aeroport;

public enum classeIntAero{
	Avion (0),
	Piste (1),
	GateWay (2),
	TaxiWayIn (3),
	TaxiWayOut (4);
	private int type;

	
	classeIntAero(int type){
		this.type = type;
	}
	   
	public String toString(){
		String s=null;
		switch(this.type){
			
		case 0:
			s="Avion";
			break;
		case 1:
			s="Piste";
			break;
		case 2:
			s="TaxiWay";
			break;
		case 3:
			s="GateWay";
			break;
		}
		
		    return s;
	}
	
}
