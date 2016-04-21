package AeroportEvent;

import Aeroport.aeroEngine;
import Aeroport.globalVariables;
import Aeroport.Entity.Avion;
import ClasseGenerales.ISimEngine.etat;
import ClasseGenerales.ISimEntity;
import ClasseGenerales.ISimEvent;
import DemandeEvent.DemandeGateWayEvent;
import LiberationEvent.LiberationPisteEvent;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;

public class RoulementArrEvent extends ISimEvent {

	private ISimEntity piste;
	private ISimEntity taxiwayIn;
	private aeroEngine motor;
	private Avion avion;
	
	public RoulementArrEvent(LogicalDateTime logicalDateTime, aeroEngine motor
			, ISimEntity piste, ISimEntity taxiwayIn, Avion avion) {
		super(logicalDateTime);
		this.piste=piste;
		this.taxiwayIn=taxiwayIn;
		this.setMotor(motor);
		this.setAvion(avion);
	}

	@Override
	public void Process() {		

		// On libere la piste
		piste.setState(etat.active);
		// event liberation de la piste
		this.motor.getListEvent().add(new LiberationPisteEvent(this.SimulationDate(),
								this.motor));
		
		// Logger	
		this.motor.ecritureLoggerEvenement("Libération de la piste",this.avion.getIdentifier());
		
		
		
		// Process standard pour l'event dï¿½barquement
		long tmin = (long)globalVariables.drivingTime[0]*60;
		long tmax = (long)globalVariables.drivingTime[1]*60;
		long tempsatt = Math.abs(this.motor.getM().nextLong())%(tmax-tmin) + tmin;
		LogicalDuration offset = LogicalDuration.ofSeconds(tempsatt);
		this.motor.getListEvent().add(new DemandeGateWayEvent(this.SimulationDate().add(offset),
				this.motor, avion));
		
	}

	public ISimEntity getPiste() {
		return piste;
	}

	public void setPiste(ISimEntity piste) {
		this.piste = piste;
	}

	public ISimEntity getTaxiwayIn() {
		return taxiwayIn;
	}

	public void setTaxiwayIn(ISimEntity taxiwayIn) {
		this.taxiwayIn = taxiwayIn;
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
