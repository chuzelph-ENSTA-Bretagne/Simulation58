package AeroportEvent;

import Aeroport.aeroEngine;
import Aeroport.globalVariables;
import Aeroport.Entity.Avion;
import ClasseGenerales.ISimEngine.etat;
import ClasseGenerales.ISimEntity;
import ClasseGenerales.ISimEvent;
import LiberationEvent.LiberationTaxiWayInEvent;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;

public class DebarquementEvent extends ISimEvent {

	private ISimEntity gate;
	private aeroEngine motor;
	private Avion avion;
	
	public DebarquementEvent(LogicalDateTime logicalDateTime, aeroEngine motor
			, ISimEntity gate, Avion avion) {
		super(logicalDateTime);
		this.gate=gate;
		this.setMotor(motor);
		this.setAvion(avion);
	}

	@Override
	public void Process() {
		
		// event liberation de l'entitï¿½e taxiwayIn
		this.avion.getTaxiWayIn().setState(etat.active);
		this.motor.getListEvent().add(new LiberationTaxiWayInEvent(this.SimulationDate(),
				this.motor));
		this.avion.setFinArrRoulement(this.SimulationDate());
		// Process Standard
		long tempsatt = (long)(globalVariables.disembarkingPassengersTime[0] + globalVariables.planePreparationTime[0]);
		LogicalDuration offset = LogicalDuration.ofMinutes(tempsatt);		
		this.motor.getListEvent().add(new EmbarquementEvent(this.SimulationDate().add(offset),
				this.motor,  gate,this.avion));
		
		//Logger	
		this.motor.ecritureLoggerEvenement("Débarquement des passagers",this.avion.getIdentifier());

		

	}
	
	public ISimEntity getGate() {
		return gate;
	}

	public void setGate(ISimEntity gate) {
		this.gate = gate;
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
