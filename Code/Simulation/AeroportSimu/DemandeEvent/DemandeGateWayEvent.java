package DemandeEvent;

import Aeroport.aeroEngine;
import Aeroport.classeIntAero;
import Aeroport.Entity.AeroportEntity;
import Aeroport.Entity.Avion;
import AeroportEvent.DebarquementEvent;
import ClasseGenerales.ISimEngine.etat;
import ClasseGenerales.ISimEntity;
import ClasseGenerales.ISimEvent;
import enstabretagne.base.time.LogicalDateTime;

public class DemandeGateWayEvent extends ISimEvent{

	private aeroEngine motor;
	private Avion avion;
	
	public DemandeGateWayEvent(LogicalDateTime logicalDateTime, aeroEngine motor
			, Avion avion) {
		super(logicalDateTime);
		this.motor=motor;
		this.setAvion(avion);
	}

	public void Process() {
		
		// deux cas
		ISimEntity Gate = null;
		Boolean T1=false;
		for(AeroportEntity I:this.motor.getListEntityAero()){
			if((I.getIntType2()==classeIntAero.GateWay) && I.getState()==etat.active){
				T1=true;
				Gate = I;
			}
		}
		
		// cas 1: Gate disponible
		
		if(T1){
			Gate.setState(etat.idle);
			this.avion.setFinAtttaxiWayIn(this.SimulationDate());
			
			// enleve l'avion ID si il était dans la liste d'attente
			motor.eliminerAvion(motor.getAvionAttenteGateWay(), this.avion);
			
			this.motor.getListEvent().add(new DebarquementEvent(this.SimulationDate(),
					this.motor,Gate,avion));
			
			// indique dans quelle taxiwayIn se dirige l'avion
			this.avion.setGate(Gate);
			
			//Logger	
			this.motor.ecritureLoggerEvenement("Va dans le Gateway "+this.avion.getGate().getIdentifier(),this.avion.getIdentifier());
		}
		
		// cas 2: Avion mis en attente
		
		else{
			// Si l'avion n'est pas en attente de GateWay
			if(!motor.presenceAvion(motor.getAvionAttenteGateWay(), this.avion)){
				motor.getAvionAttenteGateWay().add(this.avion);
				this.avion.setDebutAtttaxiWayIn(this.SimulationDate());
			}
			
			//Logger	
			this.motor.ecritureLoggerEvenement("Attente pour GateWay",this.avion.getIdentifier());
		}
		
		
	}

	public Avion getAvion() {
		return avion;
	}

	public void setAvion(Avion avion) {
		this.avion = avion;
	}
}
