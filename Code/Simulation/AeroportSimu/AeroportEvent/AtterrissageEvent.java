package AeroportEvent;

import Aeroport.aeroEngine;
import Aeroport.globalVariables;
import Aeroport.Entity.Avion;
import ClasseGenerales.ISimEntity;
import ClasseGenerales.ISimEvent;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;

public class AtterrissageEvent extends ISimEvent{
	
	
	private ISimEntity piste;
	private ISimEntity taxiwayIn;
	private aeroEngine motor;
	private Avion avion;

	public AtterrissageEvent(LogicalDateTime logicalDateTime,aeroEngine motor, ISimEntity piste, ISimEntity taxiwayIn, Avion avion) {
		super(logicalDateTime);
		this.piste=piste;
		this.taxiwayIn=taxiwayIn;
		this.setMotor(motor);
		this.setAvion(avion);
	}



	public void Process() {
		this.motor.getListEvent().add(new RoulementArrEvent(this.SimulationDate()
				.add(LogicalDuration.ofMinutes((long)globalVariables.landingTime[0])),this.motor,this.piste,taxiwayIn,avion));

		//Logger	
		this.motor.ecritureLoggerEvenement("Atterissage",this.avion.getIdentifier());
	}

	/*
	 * Getter setter
	 */
	
	public aeroEngine getMotor() {
		return motor;
	}

	public void setMotor(aeroEngine motor) {
		this.motor = motor;
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



	public Avion getAvion() {
		return avion;
	}



	public void setAvion(Avion avion) {
		this.avion = avion;
	}

}
