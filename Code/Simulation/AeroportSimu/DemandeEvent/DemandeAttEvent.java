package DemandeEvent;

import Aeroport.aeroEngine;
import Aeroport.classeIntAero;
import Aeroport.Entity.AeroportEntity;
import Aeroport.Entity.Avion;
import AeroportEvent.ApprocheEvent;
import ClasseGenerales.ISimEngine.etat;
import ClasseGenerales.ISimEntity;
import ClasseGenerales.ISimEvent;
import enstabretagne.base.time.LogicalDateTime;

public class DemandeAttEvent extends ISimEvent{

	private aeroEngine motor;
	private Avion avion;
	
	public DemandeAttEvent(LogicalDateTime logicalDateTime,aeroEngine motor, Avion avion) {
		super(logicalDateTime);
		this.setMotor(motor);
		this.setAvion(avion);
		// TODO Auto-generated constructor stub
	}

	
	public void Process() {
		
		// deux cas
		ISimEntity Piste = null;
		ISimEntity taxiwayIn = null;
		Boolean T1=false;
		Boolean T2=false;
		for(AeroportEntity I:this.motor.getListEntityAero()){
			if((I.getIntType2()==classeIntAero.TaxiWayIn) && I.getState()==etat.active){
				T1=true;
				taxiwayIn = I;
			}
		}
		for(AeroportEntity I:this.motor.getListEntityAero()){
			if((I.getIntType2()==classeIntAero.Piste) && I.getState()==etat.active){
				T2=true;
				Piste = I;
			}
		}
		// cas 1: Piste et taxiwayIn disponible
		
		if(T1&T2){
			Piste.setState(etat.idle);
			taxiwayIn.setState(etat.idle);
			
			// enleve l'avion ID si il était dans la liste d'attente
			if(this.motor.presenceAvion(motor.getAvionAttenteAtt(), this.avion)){
				motor.eliminerAvion(motor.getAvionAttenteAtt(), this.avion);
			}
			
			//Logger	
			this.motor.ecritureLoggerEvenement("Debut Approche",this.avion.getIdentifier());
			
			// process standard
			this.motor.getListEvent().add(new ApprocheEvent(this.SimulationDate(),
					this.motor,Piste,taxiwayIn,this.avion));
			
			// indique dans quelle taxiwayIn se dirige l'avion
			
			this.avion.setTaxiWayIn(taxiwayIn);
			this.avion.setFinAttArr(this.SimulationDate());
		}
		

		// cas 2: Avion mis en attente
		
		else{
			if(!motor.presenceAvion(motor.getAvionAttenteAtt(), this.avion)){
				motor.getAvionAttenteAtt().add(this.avion);
				this.avion.setDebutAttArr(this.SimulationDate());
			}
			//Logger	
			this.motor.ecritureLoggerEvenement("Attente pour atterissage",this.avion.getIdentifier());
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
