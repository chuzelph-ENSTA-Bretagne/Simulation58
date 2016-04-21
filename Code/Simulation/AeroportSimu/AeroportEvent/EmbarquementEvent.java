package AeroportEvent;

import Aeroport.aeroEngine;
import Aeroport.globalVariables;
import Aeroport.Entity.Avion;
import ClasseGenerales.ISimEntity;
import ClasseGenerales.ISimEvent;
import DemandeEvent.DemandeTaxiWayOut;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;

public class EmbarquementEvent extends ISimEvent {

	private aeroEngine motor;
	private ISimEntity gate;
	private Avion avion;
	
	public EmbarquementEvent(LogicalDateTime logicalDateTime, aeroEngine motor, ISimEntity gate, Avion avion) {
		super(logicalDateTime);
		this.setMotor(motor);
		this.setGate(gate);
		this.setAvion(avion);
	}

	public aeroEngine getMotor() {
		return motor;
	}

	public void setMotor(aeroEngine motor) {
		this.motor = motor;
	}
	
	public void Process() {
		
		long tempsatt = (long)globalVariables.boardingPassengersTime[0];		
		LogicalDuration offset = LogicalDuration.ofMinutes(tempsatt);
		this.motor.getListEvent().add(new DemandeTaxiWayOut(this.SimulationDate().add(offset),
				this.motor,this.avion));
		
		//Logger	
		this.motor.ecritureLoggerEvenement("Embarquement des passagers",this.avion.getIdentifier());
		
				
	}

	public Avion getAvion() {
		return avion;
	}

	public void setAvion(Avion avion) {
		this.avion = avion;
	}

	public ISimEntity getGate() {
		return gate;
	}

	public void setGate(ISimEntity gate) {
		this.gate = gate;
	}

}
