package AeroportEvent;

import Aeroport.aeroEngine;
import Aeroport.globalVariables;
import Aeroport.Entity.Avion;
import ClasseGenerales.ISimEngine.etat;
import ClasseGenerales.ISimEntity;
import ClasseGenerales.ISimEvent;
import DemandeEvent.DemandeDecEvent;
import LiberationEvent.LiberationGateWayEvent;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;

public class RoulementDepEvent extends ISimEvent{

	private ISimEntity taxiwayOut;
	private aeroEngine motor;
	private Avion avion;
	
	public RoulementDepEvent(LogicalDateTime logicalDateTime, aeroEngine motor
			, ISimEntity taxiwayOut, Avion avion) {
		super(logicalDateTime);
		this.taxiwayOut=taxiwayOut;
		this.setMotor(motor);
		this.avion = avion;
	}

	@Override
	public void Process() {
		
		//Liberation du GateWay
		this.avion.getGate().setState(etat.active);
		this.motor.getListEvent().add(new LiberationGateWayEvent(this.SimulationDate(),this.motor));
		
		// Process
		long tmin = (long)globalVariables.drivingTime[0]*60;
		long tmax = (long)globalVariables.drivingTime[1]*60;
		long tempsatt = Math.abs(this.motor.getM().nextLong())%(tmax-tmin) + tmin;
		LogicalDuration offset = LogicalDuration.ofSeconds(tempsatt);
		// Event décollage DemandeDecEvent
		this.motor.getListEvent().add(new DemandeDecEvent(this.SimulationDate().add(offset),this.motor,avion));

		//Logger			
		this.avion.setDebutDecRoulement(this.SimulationDate());
		this.motor.ecritureLoggerEvenement("Libération Gate "+this.avion.getGate().getIdentifier(),this.avion.getIdentifier());


	}

	public ISimEntity gettaxiwayOut() {
		return taxiwayOut;
	}

	public void settaxiwayOut(ISimEntity taxiwayOut) {
		this.taxiwayOut = taxiwayOut;
	}

	public aeroEngine getMotor() {
		return motor;
	}

	public void setMotor(aeroEngine motor) {
		this.motor = motor;
	}

}
