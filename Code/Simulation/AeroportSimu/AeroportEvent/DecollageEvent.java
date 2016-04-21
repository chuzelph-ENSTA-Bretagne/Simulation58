package AeroportEvent;

import Aeroport.aeroEngine;
import Aeroport.globalVariables;
import Aeroport.Entity.Avion;
import ClasseGenerales.ISimEngine.etat;
import ClasseGenerales.ISimEntity;
import ClasseGenerales.ISimEvent;
import LiberationEvent.LiberationTaxiWayOutEvent;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;

public class DecollageEvent extends ISimEvent {
	
	private ISimEntity piste;
	private aeroEngine motor;
	private Avion avion;


	public DecollageEvent(LogicalDateTime logicalDateTime, aeroEngine motor
			, ISimEntity piste, Avion avion) {
		super(logicalDateTime);
		this.piste=piste;
		this.setMotor(motor);
		this.setAvion(avion);
	}

	@Override
	public void Process() {
		
		// On libere le taxiWayOut
		this.avion.getTaxiWayOut().setState(etat.active);
		
		// event liberation des entitï¿½es
		this.motor.getListEvent().add(new LiberationTaxiWayOutEvent(this.SimulationDate(),
								this.motor));
		
		// Calcul de l'offset
		long tempsatt = (long)globalVariables.takeOffTime[0];
		LogicalDuration offset = LogicalDuration.ofMinutes(tempsatt);
		
		// Event Destruction avion 
		this.motor.getListEvent().add(new FinDecollageEvent(this.SimulationDate().add(offset),
				this.motor, piste,this.avion));

		//Logger	
		this.motor.ecritureLoggerEvenement("Décollage",this.avion.getIdentifier());
		
	}	

	public ISimEntity getPiste() {
		return piste;
	}

	public void setPiste(ISimEntity piste) {
		this.piste = piste;
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
