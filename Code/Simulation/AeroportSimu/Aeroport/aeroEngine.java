package Aeroport;

import java.util.ArrayList;

import ClasseGenerales.basicIrecordable;
import ClasseGenerales.basicLogicalDateTime;
import Aeroport.Entity.AeroportEntity;
import Aeroport.Entity.Avion;
import Aeroport.Entity.GateWay;
import Aeroport.Entity.Piste;
import Aeroport.Entity.TaxiWayIn;
import Aeroport.Entity.TaxiWayOut;
import AeroportEvent.OuvertureEvent;
import ClasseGenerales.ISimEngine;
import ClasseGenerales.ISimEntity;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;


public class aeroEngine extends ISimEngine{
	
	private int nbPiste;
	private int nbGateWay;
	private int nbTaxiwaysEntree;
	private int nbTaxiWaysSortie;
	private ArrayList<Avion> listAvion = new ArrayList<Avion>();
	private ArrayList<Avion> avionAttenteAtt = new ArrayList<Avion>();
	private ArrayList<Avion> avionAttenteDec = new ArrayList<Avion>();
	private ArrayList<Avion> avionAttenteGateWay = new ArrayList<Avion>();
	private ArrayList<Avion> avionAttenteTaxiWayOut = new ArrayList<Avion>();
	private ArrayList<AeroportEntity> listEntityAero = new ArrayList<AeroportEntity>();
	private int jour;
	private Boolean pluie;
	private int nbAvionRetard;
	private int nbAvion;
	private int tempsTotalAttente;
	private int nbAvionJour;
	private int nbAvionHeure;
	private int nbAvionDecJour;
	private int nbAvionDecRetardJour;
	private String trancheHoraire;
	private int debutTH;
	private int k;
	private int nbAvionJour2;
	private String tablogger []={"0","0","0","0","0","0"};
	private String tablogger2 []={"0","0","0","0"};
	private String tabloggerRetards []={"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
	private String tabloggerRetards2 []={"0","0","0","0","0","0","0","0","0","0","0","0","0","0"};

	public aeroEngine(LogicalDateTime fin, LogicalDateTime debut, int nbPiste,
			int nbGateWay, int nbTaxiwaysEntree,int nbTaxiWaysSortie) {
		super(debut,fin);
		this.nbGateWay = nbGateWay;
		this.nbPiste = nbPiste;
		this.nbTaxiwaysEntree = nbTaxiwaysEntree;
		this.nbTaxiWaysSortie = nbTaxiWaysSortie;
		this.setJour(-1); //On veut commencer un Lundi
		this.setPluie(false);
		this.nbAvion=0;
		this.nbAvionRetard=0;
		this.tempsTotalAttente=0;
		this.nbAvionJour=0;
		this.nbAvionHeure=0;
		this.trancheHoraire=null;
		this.debutTH=0;
		this.nbAvionDecRetardJour=0;
		this.nbAvionDecJour=0;
		this.nbAvionJour2=0;
		this.k=0;
		init();
	}


	public LogicalDuration tconvert(int min , int heure , int jour, int mois) {
		String month = Integer.toString(mois);
		String day = Integer.toString(jour);
		String hour = Integer.toString(heure);
		String mn = Integer.toString(min);
		while (month.length() < 2)
			month = "0" + month;
		while (day.length() < 2)
			day = "0" + day;
		while (hour.length() < 2)
			hour = "0" + hour;
		while (mn.length() < 2)
			mn = "0" + mn;
		LogicalDateTime ldt = new LogicalDateTime(day + "/" + month + "/2015" + " " + hour + ":" + mn + ":00.0000");
		LogicalDateTime ldt2 = new LogicalDateTime( "01/01/2015" + " 00:00:00.0000");
		
		return ldt.soustract(ldt2); 
	}
	
	public void RevisionOuverture(long horaireOuvertureHeure){
		LogicalDuration Ouv = LogicalDuration.ofHours(horaireOuvertureHeure);
		LogicalDateTime Douverture =(this.SimulationDate().truncateToDays()).add(Ouv);
		if(this.SimulationDate().compareTo(Douverture)<0){
			this.getListEvent().add(new OuvertureEvent( Douverture, this));
		}
		else{
			 System.out.println("#########");
			 System.out.println("La date de départ impose de commencer la simulation le lendemain, veuillez changer l'heure de début de la simulation!");
			 System.out.println("#########");
			 LogicalDuration offset = LogicalDuration.ofHours(24).add(LogicalDuration.ofHours(horaireOuvertureHeure));
			 this.getListEvent().add(new OuvertureEvent( this.SimulationDate().truncateToDays().add(offset), this));
		}
	}
	
	public void init() {
			 
		int i;
		for(i=0;i<nbTaxiwaysEntree;i++){
			this.getListEntityAero().add(new TaxiWayIn(null , this,i));
		}
		for(i=0;i<nbTaxiWaysSortie;i++){
			this.getListEntityAero().add(new TaxiWayOut(null , this,i));
		}
		for(i=0;i<nbPiste;i++){
			this.getListEntityAero().add(new Piste(null , this,i));
		}
		for(i=0;i<nbGateWay;i++){
			this.getListEntityAero().add(new GateWay(null , this,i));
		}
		
		this.setAvionAttenteAtt(new ArrayList<Avion>());
		this.setAvionAttenteDec(new ArrayList<Avion>());
		RevisionOuverture((long)globalVariables.closingTimeInterval[1]); //Ouverture de l aeroport a 7h00
		
	}	
	
	public LogicalDateTime appartionNextAvion(){
		LogicalDateTime t=null;
		Boolean T1 = this.SimulationDate().compareTo(
				this.SimulationDate().truncateToDays().add(LogicalDuration.ofHours((long)globalVariables.closingTimeInterval[0])))<=0;
		Boolean T2 = this.SimulationDate().compareTo(
				this.SimulationDate().truncateToDays().add(LogicalDuration.ofHours((long)globalVariables.closingTimeInterval[1])))>=0;
		Boolean T3 = this.SimulationDate().compareTo(
				this.SimulationDate().truncateToDays().add(LogicalDuration.ofHours((long)globalVariables.rushHourInterval[1])))<=0;
		Boolean T4 = this.SimulationDate().compareTo(
				this.SimulationDate().truncateToDays().add(LogicalDuration.ofHours((long)globalVariables.rushHourInterval[2])))<=0;
		Boolean T5 = this.SimulationDate().compareTo(
				this.SimulationDate().truncateToDays().add(LogicalDuration.ofHours((long)globalVariables.rushHourInterval[3])))<=0;
		Boolean T6 = this.SimulationDate().compareTo(
				this.SimulationDate().truncateToDays().add(LogicalDuration.ofHours((long)globalVariables.rushHourInterval[0])))>=0;
		
		if(this.getJour()<5){
			if((T1&&T2)){
				// entre T2 et T3
				if(T6&&T3){
					long tmoy = 3600/(long)globalVariables.planesNumByRushHour;	// Temps moyen entre les arrivÃ©s d'avion en s
					long tempsNewAvion = (long) (tmoy + 120*this.getM().nextGaussian());
					t = this.SimulationDate().add(LogicalDuration.ofSeconds(tempsNewAvion));
				}
				
				// entre !T4 et T5
				else{ 
					if(!T4&&T5){
						long tmoy = 3600/(long)globalVariables.planesNumByRushHour;	// Temps moyen entre les arrivÃ©s d'avion en s
						long tempsNewAvion = (long) (tmoy + 120*this.getM().nextGaussian());
						t = this.SimulationDate().
								add(LogicalDuration.ofSeconds(tempsNewAvion));
					}
					else{
						long tmoy = 3600/(long)globalVariables.planesNumByHour;	// Temps moyen entre les arrivÃ©s d'avion en s
						long tempsNewAvion = (long) (tmoy + 120*this.getM().nextGaussian());
						t = this.SimulationDate().
								add(LogicalDuration.ofSeconds(tempsNewAvion));
					}
				}
			}
		}
		else{
			if((T1&&T2)){
				long tmoy = 3600/(long)globalVariables.planesNumByHourWE;	// Temps moyen entre les arrivÃ©s d'avion en s
				long tempsNewAvion = (long) (tmoy + 120*this.getM().nextGaussian());
				t = this.SimulationDate().
						add(LogicalDuration.ofSeconds(tempsNewAvion));
			}
		}
		if(t==null){
			System.out.println("\npb ici\n");
		}
		return t;
	}
	
	// override the fonction of the main class
	public void initialize() {
		for (ISimEntity entity : getListEntityAero())
			entity.initialize();
	}
	
	public void pause() {
		for (ISimEntity entity : getListEntityAero())
			entity.pause();
	}
	
	public void resume() {
		for (ISimEntity entity : getListEntityAero())
			entity.activate();
	}
	
	public Boolean presenceAvion(ArrayList<Avion> list,Avion avion){
		Avion H = null;
		for(int i = 0;i<list.size();i++){
			H=list.get(i);
			if(H.getIdentifier()==avion.getIdentifier()){
				return true;
			}
		}
		return false;
	}
	
	public Boolean eliminerAvion(ArrayList<Avion> list,Avion avion){
		Avion H = null;
		int indice=0;
		for(int i = 0;i<list.size();i++){
			H=list.get(i);
			if(H.getIdentifier()==avion.getIdentifier()){
				indice=i;
				list.remove(indice);
				return true;
			}
		}
		return false;
	}
	
	//Recupere avion:
	public Avion getAvion(int id){
	    Avion H = null;
	    Avion tmp = null;
	    for(int i = 0;i<this.getListAvion().size();i++){
	    	tmp=this.getListAvion().get(i);
			if(tmp.getIdentifier()==id){
				H=tmp;
			}
		}
	    if(H==null){
	    	Logger.Terminate();
	    	System.out.println(id);
	    	//System.exit(1);
	    }
	    return H;
	}
	
	public String getAvionId(int id){
		Avion H = getAvion(id);
        return H.getNvol();
	}
	
	//Logger
	public void ecritureLoggerEvenement(String event, int id){
		String r [] ={"0","0","0"};
		String titles [] ={"Temps simulation","Identifiant avion","Etat"};
		basicLogicalDateTime bldt = new basicLogicalDateTime(this.SimulationDate());
		r[0] = bldt.toString();
		if(id!=-1){
			r[1] = this.getAvionId(id);
		}else{
			r[1] = Integer.toString(id);
		}
		r[2] = event;
		Logger.Data(new basicIrecordable(titles,r, "Evénemements" ));
	}
	
	
	
		
	/*
	 * Getter and setter
	 */
	
	public ArrayList<Avion> getAvionAttenteDec() {
		return avionAttenteDec;
	}

	public void setAvionAttenteDec(ArrayList<Avion> avionAttenteDec) {
		this.avionAttenteDec = avionAttenteDec;
	}

	public ArrayList<Avion> getAvionAttenteAtt() {
		return avionAttenteAtt;
	}

	public void setAvionAttenteAtt(ArrayList<Avion> avionAttenteAtt) {
		this.avionAttenteAtt = avionAttenteAtt;
	}

	public ArrayList<Avion> getListAvion() {
		return listAvion;
	}

	public void setListAvion(ArrayList<Avion> listAvion) {
		this.listAvion = listAvion;
	}

	public int getJour() {
		return jour;
	}

	public void setJour(int jour) {
		this.jour = jour;
	}

	public Boolean getPluie() {
		return pluie;
	}

	public void setPluie(Boolean pluie) {
		this.pluie = pluie;
	}

	public ArrayList<AeroportEntity> getListEntityAero() {
		return listEntityAero;
	}

	public void setListEntityAero(ArrayList<AeroportEntity> listEntityAero) {
		this.listEntityAero = listEntityAero;
	}
	
	public int getNbAvionRetard() {
		return nbAvionRetard;
	}

	public void setNbAvionRetard(int nbAvionRetard) {
		this.nbAvionRetard = nbAvionRetard;
	}


	public int getTempsTotalAttente() {
		return tempsTotalAttente;
	}


	public void setTempsTotalAttente(int tempsTotalAttente) {
		this.tempsTotalAttente = tempsTotalAttente;
	}


	public int getNbAvion() {
		return nbAvion;
	}


	public void setNbAvion(int nbAvion) {
		this.nbAvion = nbAvion;
	}


	public ArrayList<Avion> getAvionAttenteTaxiWayOut() {
		return avionAttenteTaxiWayOut;
	}


	public void setAvionAttenteTaxiWayOut(ArrayList<Avion> avionAttenteTaxiWayOut) {
		this.avionAttenteTaxiWayOut = avionAttenteTaxiWayOut;
	}


	public ArrayList<Avion> getAvionAttenteGateWay() {
		return avionAttenteGateWay;
	}


	public void setAvionAttenteGateWay(ArrayList<Avion> avionAttenteGateWay) {
		this.avionAttenteGateWay = avionAttenteGateWay;
	}


	public int getNbAvionHeure() {
		return nbAvionHeure;
	}


	public void setNbAvionHeure(int nbAvionHeure) {
		this.nbAvionHeure = nbAvionHeure;
	}


	public int getNbAvionJour() {
		return nbAvionJour;
	}


	public void setNbAvionJour(int nbAvionJour) {
		this.nbAvionJour = nbAvionJour;
	}


	public String getTrancheHoraire() {
		return trancheHoraire;
	}


	public void setTrancheHoraire(String trancheHoraire) {
		this.trancheHoraire = trancheHoraire;
	}


	public String[] getTablogger() {
		return tablogger;
	}


	public void setTablogger(String[] tablogger) {
		this.tablogger = tablogger;
	}


	public int getDebutTH() {
		return debutTH;
	}


	public void setDebutTH(int debutTH) {
		this.debutTH = debutTH;
	}


	public String[] getTabloggerRetards() {
		return tabloggerRetards;
	}


	public void setTabloggerRetards(String[] tabloggerRetards) {
		this.tabloggerRetards = tabloggerRetards;
	}


	public String[] getTablogger2() {
		return tablogger2;
	}


	public void setTablogger2(String[] tablogger2) {
		this.tablogger2 = tablogger2;
	}


	public String[] getTabloggerRetards2() {
		return tabloggerRetards2;
	}


	public void setTabloggerRetards2(String[] tabloggerRetards2) {
		this.tabloggerRetards2 = tabloggerRetards2;
	}


	public int getNbAvionDecJour() {
		return nbAvionDecJour;
	}


	public void setNbAvionDecJour(int nbAvionDecJour) {
		this.nbAvionDecJour = nbAvionDecJour;
	}


	public int getNbAvionDecRetardJour() {
		return nbAvionDecRetardJour;
	}


	public void setNbAvionDecRetardJour(int nbAvionDecRetardJour) {
		this.nbAvionDecRetardJour = nbAvionDecRetardJour;
	}


	public int getNbPiste() {
		return nbPiste;
	}


	public void setNbPiste(int nbPiste) {
		this.nbPiste = nbPiste;
	}


	public int getNbGateWay() {
		return nbGateWay;
	}


	public void setNbGateWay(int nbGateWay) {
		this.nbGateWay = nbGateWay;
	}


	public int getNbTaxiwaysEntree() {
		return nbTaxiwaysEntree;
	}


	public void setNbTaxiwaysEntree(int nbTaxiwaysEntree) {
		this.nbTaxiwaysEntree = nbTaxiwaysEntree;
	}


	public int getNbTaxiWaysSortie() {
		return nbTaxiWaysSortie;
	}


	public void setNbTaxiWaysSortie(int nbTaxiWaysSortie) {
		this.nbTaxiWaysSortie = nbTaxiWaysSortie;
	}


	public int getK() {
		return k;
	}


	public void setK(int k) {
		this.k = k;
	}


	public int getNbAvionJour2() {
		return nbAvionJour2;
	}


	public void setNbAvionJour2(int nbAvionJour2) {
		this.nbAvionJour2 = nbAvionJour2;
	}
	
	
	

}
