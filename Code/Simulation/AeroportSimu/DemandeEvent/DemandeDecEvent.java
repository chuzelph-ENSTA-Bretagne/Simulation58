package DemandeEvent;

import Aeroport.aeroEngine;
import Aeroport.classeIntAero;
import Aeroport.Entity.AeroportEntity;
import Aeroport.Entity.Avion;
import AeroportEvent.DecollageEvent;
import ClasseGenerales.ISimEngine.etat;
import ClasseGenerales.ISimEntity;
import ClasseGenerales.ISimEvent;
import enstabretagne.base.time.LogicalDateTime;

public class DemandeDecEvent extends ISimEvent {

	private aeroEngine motor;
	private Avion avion;
	
	public DemandeDecEvent(LogicalDateTime logicalDateTime, aeroEngine motor, Avion avion) {
		super(logicalDateTime);
		this.setMotor(motor);
		this.setAvion(avion);
	}

	@Override
	public void Process() {
		
		// deux cas
		ISimEntity Piste = null;
		Boolean T1=false;
		for(AeroportEntity I:this.motor.getListEntityAero()){
			if((I.getIntType2()==classeIntAero.Piste) && I.getState()==etat.active){
				T1=true;
				Piste = I;
			}
		}
		
		// cas 1: Piste disponible
		
		if(T1){
			// enleve l'avion ID si il était dans la liste d'attente
			motor.eliminerAvion(motor.getAvionAttenteDec(), this.avion);
						
			// Process standard
			this.avion.setFinAttDep(this.SimulationDate());
			Piste.setState(etat.idle);
			this.motor.getListEvent().add(new DecollageEvent(this.SimulationDate(),
					this.motor, Piste,this.avion));

			//Logger	
			this.motor.ecritureLoggerEvenement("Attribution piste pour décollage",this.avion.getIdentifier());
		}
		
		// cas 2: Avion mis en attente
		
		else{
			if(!motor.presenceAvion(motor.getAvionAttenteDec(), this.avion)){
				motor.getAvionAttenteDec().add(this.avion);
				this.avion.setDebutAttDep(this.SimulationDate());
			}

			//Logger	
			this.motor.ecritureLoggerEvenement("Attente pour décollage",this.avion.getIdentifier());
		}

	}

	public aeroEngine getMotor() {
		return motor;
	}

	public void setMotor(aeroEngine motor) {
		this.motor = motor;
	}

	public Avion getAvion() {
		return avion;
	}

	public void setAvion(Avion avion) {
		this.avion = avion;
	}
	
}
