package DemandeEvent;

import Aeroport.aeroEngine;
import Aeroport.classeIntAero;
import Aeroport.Entity.AeroportEntity;
import Aeroport.Entity.Avion;
import AeroportEvent.RoulementDepEvent;
import ClasseGenerales.ISimEngine.etat;
import ClasseGenerales.ISimEntity;
import ClasseGenerales.ISimEvent;
import enstabretagne.base.time.LogicalDateTime;

public class DemandeTaxiWayOut extends ISimEvent{

	private aeroEngine motor;
	private Avion avion;
	
	public DemandeTaxiWayOut(LogicalDateTime logicalDateTime, aeroEngine motor, Avion avion) {
		super(logicalDateTime);
		this.setMotor(motor);
		this.setAvion(avion);
	}

	@Override
	public void Process() {
		
		// change le numero de vol
		this.avion.setNvol('S' + Integer.toString(this.avion.getIdentifier()%100));
		
		// deux cas
		ISimEntity taxiwayOut= null;
		Boolean T1=false;
		for(AeroportEntity I:this.motor.getListEntityAero()){
			if((I.getIntType2()==classeIntAero.TaxiWayOut) && I.getState()==etat.active){
				T1=true;
				taxiwayOut = I;
			}
		}
		
		// cas 1: taxiwayOut disponible
		
		if(T1){

			//Logger	
			this.motor.ecritureLoggerEvenement("Debut Roulement Décollage",this.avion.getIdentifier());
			
			// enleve l'avion ID si il était dans la liste d'attente
			motor.eliminerAvion(motor.getAvionAttenteTaxiWayOut(), this.avion);
				
			// Process standard
			this.avion.setFinAtttaxiWayOut(this.SimulationDate());
			this.avion.setTaxiWayOut(taxiwayOut);
			taxiwayOut.setState(etat.idle);
			this.motor.getListEvent().add(new RoulementDepEvent(this.SimulationDate(),
					this.motor,taxiwayOut,this.avion));

		}
		

		//I.setState(etat.idle);
		// cas 2: Avion mis en attente
		
		else{
			if(!motor.presenceAvion(motor.getAvionAttenteTaxiWayOut(), this.avion)){
				motor.getAvionAttenteTaxiWayOut().add(this.avion);
				this.avion.setDebutAtttaxiWayOut(this.SimulationDate());
			}
			//Logger	
			this.motor.ecritureLoggerEvenement("Attente pour TaxiWayOut",this.avion.getIdentifier());
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
