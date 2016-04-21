package LiberationEvent;

import Aeroport.aeroEngine;
import ClasseGenerales.ISimEvent;
import DemandeEvent.DemandeAttEvent;
import enstabretagne.base.time.LogicalDateTime;

public class LiberationTaxiWayInEvent extends ISimEvent {

	private aeroEngine motor;
	
	public LiberationTaxiWayInEvent(LogicalDateTime logicalDateTime, aeroEngine motor) {
		super(logicalDateTime);
		this.setMotor(motor);
	}

	@Override
	public void Process() {
		
		//Logger	
		this.motor.ecritureLoggerEvenement("TaxiWay d'entrée libre, pret pour une nouvelle demande",-1);
		
		// check si avion en attente pour GateWay
		if(!motor.getAvionAttenteAtt().isEmpty()){

			ISimEvent J =new DemandeAttEvent(this.SimulationDate()
					,this.motor, motor.getAvionAttenteAtt().get(0));
			J.Process();
		}
	}

	public aeroEngine getMotor() {
		return motor;
	}

	public void setMotor(aeroEngine motor2) {
		this.motor = motor2;
	}

}
