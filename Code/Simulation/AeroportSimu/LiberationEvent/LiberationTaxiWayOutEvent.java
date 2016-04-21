package LiberationEvent;

import Aeroport.aeroEngine;
import ClasseGenerales.ISimEvent;
import DemandeEvent.DemandeTaxiWayOut;
import enstabretagne.base.time.LogicalDateTime;

public class LiberationTaxiWayOutEvent extends ISimEvent {

	private aeroEngine motor;
	
	public LiberationTaxiWayOutEvent(LogicalDateTime logicalDateTime, aeroEngine motor) {
		super(logicalDateTime);
		this.setMotor(motor);
	}

	@Override
	public void Process() {
		
		//Logger	
		this.motor.ecritureLoggerEvenement("TaxiWay de sortie libre, pret pour une nouvelle demande",-1);
		
		// check si avion en attente pour sortir
		if(!motor.getAvionAttenteTaxiWayOut().isEmpty()){

			ISimEvent J =new DemandeTaxiWayOut(this.SimulationDate()
					,this.motor, motor.getAvionAttenteTaxiWayOut().get(0));
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
