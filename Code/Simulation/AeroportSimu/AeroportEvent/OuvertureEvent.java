package AeroportEvent;

import Aeroport.aeroEngine;
import Aeroport.globalVariables;
import ClasseGenerales.ISimEvent;
import ClasseGenerales.basicIrecordable;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;

public class OuvertureEvent extends ISimEvent{

	private aeroEngine motor;
	
	public OuvertureEvent(LogicalDateTime logicalDateTime,aeroEngine motor) {
		super(logicalDateTime);
		this.motor = motor;
	}
	
	public void Process() {
		@SuppressWarnings("unused")
		String day="";
		this.motor.setJour((this.motor.getJour()+1)%7);
		switch (this.motor.getJour()) {
        case 0:  day = "lundi";
                 break;
        case 1:  day = "mardi";
                 break;
        case 2:  day = "mercredi";
                 break;
        case 3:  day = "jeudi";
                 break;
        case 4:  day = "vendredi";
                 break;
        case 5:  day = "samedi";
                 break;
        case 6:  day = "dimanche";
                 break;
		}
		
		double div = 1/globalVariables.badWeatherRate;
		double p = Math.abs(this.motor.getM().nextInt())%div;
		this.motor.setPluie(p==7);
		ArriverAvionEvent H =new ArriverAvionEvent(this.motor.appartionNextAvion(),this.motor, 0);
		this.motor.getListEvent().add(H);
		
		
		LogicalDuration offset = LogicalDuration.ofHours((long)globalVariables.closingTimeInterval[0]);
		this.motor.getListEvent().add(new FermetureEvent(this.SimulationDate().truncateToDays().add(offset),this.motor));
		
		//Logger	
		this.motor.ecritureLoggerEvenement("Ouverture de l'aéroport",-1);
		this.motor.setNbAvionJour2(0);
		this.motor.setNbAvionJour(0);
		
		if(this.motor.getK()==0){
			ecritureLoggerAeroport(this.motor.getNbPiste(),this.motor.getNbGateWay(), this.motor.getNbTaxiwaysEntree(), this.motor.getNbTaxiWaysSortie());
			this.motor.setK(1);
		}
	}
	
	
	//Logger:
	public static void ecritureLoggerAeroport(int nbPiste,int nbGateWay, int nbTaxiwaysEntree, int nbTaxiWaysSortie){
		String r [] ={"0","0","0","0"};
		String titles [] ={"Nombre de piste","Nombre de GateWay","Nombre de TaxiWay d'entree","Nombre de TaxyWay de sortie"};
			
		r[0] = Integer.toString(nbPiste);
		r[1] = Integer.toString(nbGateWay);
		r[2] = Integer.toString(nbTaxiwaysEntree);
		r[3] = Integer.toString(nbTaxiWaysSortie);
		Logger.Data(new basicIrecordable(titles,r, "Aeroport" ));
	}

}
