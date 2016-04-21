package LiberationEvent;

import Aeroport.aeroEngine;
import ClasseGenerales.ISimEvent;
import DemandeEvent.DemandeAttEvent;
import DemandeEvent.DemandeDecEvent;
import enstabretagne.base.time.LogicalDateTime;

public class LiberationPisteEvent extends ISimEvent {

	private aeroEngine motor;
	
	public LiberationPisteEvent(LogicalDateTime logicalDateTime, aeroEngine motor) {
		super(logicalDateTime);
		this.setMotor(motor);
	}

	@Override
	public void Process() {

		//Logger	
		this.motor.ecritureLoggerEvenement("Piste libre, pret pour une nouvelle demande",-1);
						
		// check si avion en attente pour décollage
		if(!motor.getAvionAttenteDec().isEmpty()){


			ISimEvent J =new DemandeDecEvent(this.SimulationDate()
					,this.motor, motor.getAvionAttenteDec().get(0));
			J.Process();						
		}
		
		// check si avion en attente pour l'atterissage
		if(!motor.getAvionAttenteAtt().isEmpty()){
			ISimEvent J =new DemandeAttEvent(this.SimulationDate()
					,this.motor, motor.getAvionAttenteAtt().get(0));
			J.Process();
		}
		
		
		// rien sinon

	}

	public aeroEngine getMotor() {
		return motor;
	}

	public void setMotor(aeroEngine motor2) {
		this.motor = motor2;
	}

}
