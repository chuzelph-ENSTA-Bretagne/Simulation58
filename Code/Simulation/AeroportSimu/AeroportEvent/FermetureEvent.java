package AeroportEvent;

import Aeroport.aeroEngine;
import Aeroport.globalVariables;
import ClasseGenerales.ISimEvent;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;

public class FermetureEvent extends ISimEvent{

	private aeroEngine motor;
	
	public FermetureEvent(LogicalDateTime logicalDateTime,aeroEngine motor) {
		super(logicalDateTime);
		this.motor = motor;
		
	}
	
	@Override
	public void Process() {
		
		LogicalDuration offset = LogicalDuration.ofHours(24).add(LogicalDuration.ofHours((long)globalVariables.closingTimeInterval[1]));
		if(this.motor.SimulationDate().truncateToDays().add(offset).compareTo(this.motor.getFin())<0){
			this.motor.getListEvent().add(new OuvertureEvent(this.motor.SimulationDate().truncateToDays().add(offset),this.motor));
		}

		//Logger	
		this.motor.ecritureLoggerEvenement("Fermeture Aéroport",-1);
	}

}
