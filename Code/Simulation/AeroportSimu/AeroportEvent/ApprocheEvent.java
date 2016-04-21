package AeroportEvent;

import Aeroport.aeroEngine;
import Aeroport.globalVariables;
import Aeroport.Entity.Avion;
import ClasseGenerales.ISimEntity;
import ClasseGenerales.ISimEvent;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;

public class ApprocheEvent extends ISimEvent{

	private aeroEngine motor;
	private ISimEntity piste;
	private ISimEntity taxiwayIn;
	private Avion avion;
	
	public ApprocheEvent(LogicalDateTime logicalDateTime,aeroEngine motor, ISimEntity piste, ISimEntity taxiwayIn, Avion avion) {
		super(logicalDateTime);
		this.setMotor(motor);
		this.piste=piste;
		this.taxiwayIn=taxiwayIn;
		this.setAvion(avion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Process() {
		
		// calcul le temps d'approche
		long tmin = (long)globalVariables.approachTime[0]*60;
		long tmax = (long)globalVariables.approachTime[1]*60;
		long tempsatt = Math.abs(this.motor.getM().nextLong())%(tmax-tmin) + tmin;
		LogicalDuration offset;
		if(this.motor.getPluie()){
			offset = LogicalDuration.ofSeconds(2*tempsatt);
		}
		else{
			offset = LogicalDuration.ofSeconds(tempsatt);
		}
		
		// L'avion a fini l'approche et souhaite atterrir
		this.motor.getListEvent().add(new AtterrissageEvent(this.SimulationDate().add(offset)
				,this.motor, piste, taxiwayIn,this.avion));
		
		//Logger	
		this.motor.ecritureLoggerEvenement("Approche pour atterissage",this.avion.getIdentifier());
		this.avion.setDebutApproche(this.SimulationDate());
	}

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
