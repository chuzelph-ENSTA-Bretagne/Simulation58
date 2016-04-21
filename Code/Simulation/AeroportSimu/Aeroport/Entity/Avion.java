package Aeroport.Entity;

import java.util.ArrayList;

import enstabretagne.base.time.LogicalDateTime;
import Aeroport.classeIntAero;
import ClasseGenerales.ISimEngine;
import ClasseGenerales.ISimEntity;

public class Avion extends AeroportEntity{

	private String Nvol;
	private ISimEntity Gate;
	private ISimEntity TaxiWayIn;
	private ISimEntity TaxiWayOut;
	
	// Temps attente atterissage
	private LogicalDateTime debutAttArr;
	private LogicalDateTime finAttArr;

	// Temps attente taxiWayIn
	private LogicalDateTime debutAtttaxiWayIn;
	private LogicalDateTime finAtttaxiWayIn;
	

	// Temps attente taxiWayOut
	private LogicalDateTime debutAtttaxiWayOut;
	private LogicalDateTime finAtttaxiWayOut;
	
	// Temps attente decollage
	private LogicalDateTime debutAttDep;
	private LogicalDateTime finAttDep;
	
	//Temps arrivee
	private LogicalDateTime debutApproche;
	private LogicalDateTime finArrRoulement;
	
	//Temps depart
	private LogicalDateTime debutDecRoulement;
	private LogicalDateTime finDecollage;
	
	
	public Avion(ArrayList<ISimEntity> listEntitySon, ISimEngine moteur, int id) {
		super(listEntitySon, moteur, id);
		this.setIntType2(classeIntAero.Avion);
		this.setNvol('f' + Integer.toString(this.getIdentifier()%100));
		this.Gate=null;
		this.debutAttArr=null;
		this.debutAttDep=null;
		this.finAttArr=null;
		this.finAttDep=null;
		this.debutApproche=null;
		this.finArrRoulement=null;
		this.debutDecRoulement=null;
		this.finDecollage=null;
	}

	public String getNvol() {
		return Nvol;
	}

	public void setNvol(String nvol) {
		Nvol = nvol;
	}

	public ISimEntity getGate() {
		return Gate;
	}

	public void setGate(ISimEntity gate) {
		Gate = gate;
	}

	public LogicalDateTime getDebutAttArr() {
		return debutAttArr;
	}

	public void setDebutAttArr(LogicalDateTime debutAttArr) {
		this.debutAttArr = debutAttArr;
	}

	public LogicalDateTime getDebutAttDep() {
		return debutAttDep;
	}

	public void setDebutAttDep(LogicalDateTime debutAttDep) {
		this.debutAttDep = debutAttDep;
	}

	public LogicalDateTime getFinAttArr() {
		return finAttArr;
	}

	public void setFinAttArr(LogicalDateTime finAttArr) {
		this.finAttArr = finAttArr;
	}

	public LogicalDateTime getFinAttDep() {
		return finAttDep;
	}

	public void setFinAttDep(LogicalDateTime finAttDep) {
		this.finAttDep = finAttDep;
	}

	public LogicalDateTime getDebutAtttaxiWayIn() {
		return debutAtttaxiWayIn;
	}

	public void setDebutAtttaxiWayIn(LogicalDateTime debutAtttaxiWayIn) {
		this.debutAtttaxiWayIn = debutAtttaxiWayIn;
	}

	public LogicalDateTime getFinAtttaxiWayIn() {
		return finAtttaxiWayIn;
	}

	public void setFinAtttaxiWayIn(LogicalDateTime finAtttaxiWayIn) {
		this.finAtttaxiWayIn = finAtttaxiWayIn;
	}

	public LogicalDateTime getDebutAtttaxiWayOut() {
		return debutAtttaxiWayOut;
	}

	public void setDebutAtttaxiWayOut(LogicalDateTime debutAtttaxiWayOut) {
		this.debutAtttaxiWayOut = debutAtttaxiWayOut;
	}

	public LogicalDateTime getFinAtttaxiWayOut() {
		return finAtttaxiWayOut;
	}

	public void setFinAtttaxiWayOut(LogicalDateTime finAtttaxiWayOut) {
		this.finAtttaxiWayOut = finAtttaxiWayOut;
	}

	public ISimEntity getTaxiWayIn() {
		return TaxiWayIn;
	}

	public void setTaxiWayIn(ISimEntity taxiWayIn) {
		TaxiWayIn = taxiWayIn;
	}

	public ISimEntity getTaxiWayOut() {
		return TaxiWayOut;
	}

	public void setTaxiWayOut(ISimEntity taxiWayOut) {
		TaxiWayOut = taxiWayOut;
	}

	public LogicalDateTime getDebutApproche() {
		return debutApproche;
	}

	public void setDebutApproche(LogicalDateTime debutApproche) {
		this.debutApproche = debutApproche;
	}

	public LogicalDateTime getFinArrRoulement() {
		return finArrRoulement;
	}

	public void setFinArrRoulement(LogicalDateTime finArrRoulement) {
		this.finArrRoulement = finArrRoulement;
	}

	public LogicalDateTime getDebutDecRoulement() {
		return debutDecRoulement;
	}

	public void setDebutDecRoulement(LogicalDateTime debutDecRoulement) {
		this.debutDecRoulement = debutDecRoulement;
	}

	public LogicalDateTime getFinDecollage() {
		return finDecollage;
	}

	public void setFinDecollage(LogicalDateTime finDecollage) {
		this.finDecollage = finDecollage;
	}


}
