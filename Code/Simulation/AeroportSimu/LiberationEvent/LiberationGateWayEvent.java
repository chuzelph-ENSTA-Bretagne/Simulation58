package LiberationEvent;

import Aeroport.aeroEngine;
import ClasseGenerales.ISimEvent;
import DemandeEvent.DemandeGateWayEvent;
import enstabretagne.base.time.LogicalDateTime;

public class LiberationGateWayEvent extends ISimEvent{

private aeroEngine motor;
	
	
	public LiberationGateWayEvent(LogicalDateTime logicalDateTime, aeroEngine motor) {
		super(logicalDateTime);
		this.setMotor(motor);
	}

	@Override
	public void Process() {

		//Logger	
		this.motor.ecritureLoggerEvenement("GateWay Libre, pret pour une nouvelle demande",-1);
		
		// check si avion en attente pour GateWay
		if(!motor.getAvionAttenteGateWay().isEmpty() ){
			ISimEvent J =new DemandeGateWayEvent(this.SimulationDate()
					,this.motor, motor.getAvionAttenteGateWay().get(0));
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
