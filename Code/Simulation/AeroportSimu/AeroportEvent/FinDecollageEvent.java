package AeroportEvent;

import Aeroport.aeroEngine;
import Aeroport.Entity.Avion;
import ClasseGenerales.ISimEntity;
import ClasseGenerales.ISimEvent;
import ClasseGenerales.basicIrecordable;
import ClasseGenerales.basicLogicalDateTime;
import ClasseGenerales.ISimEngine.etat;
import LiberationEvent.LiberationPisteEvent;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.utility.Logger;

public class FinDecollageEvent extends ISimEvent {

	private ISimEntity piste;
	private aeroEngine motor;
	private Avion avion;
	
	public FinDecollageEvent(LogicalDateTime logicalDateTime, aeroEngine motor, ISimEntity piste, Avion avion) {
		super(logicalDateTime);
		this.piste=piste;
		this.setMotor(motor);
		this.setAvion(avion);
	}

	
	@Override
	public void Process() {
		
		//Libération de la piste
		piste.setState(etat.active);
		
		// event liberation des entitées
		this.motor.getListEvent().add(new LiberationPisteEvent(this.SimulationDate(),
						this.motor));
		
		//Logger	
		this.motor.ecritureLoggerEvenement("Libération de la piste",this.avion.getIdentifier());
		
		this.avion.setFinDecollage(this.SimulationDate());
				
		
		// Event Destruction avion 
		this.motor.getListAvion().remove(this.avion);

		
		//Logger
		ecritureLoggerRetardsHeure(this.avion);
		ecritureLoggerRetardsJour(this.avion);
		this.motor.ecritureLoggerEvenement("Fin decollage",-1);
		

	}

	public void ecritureLoggerRetardsHeure(Avion H){
		String r [] ={"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
		String titles [] ={"Temps simulation","Jour","Temps","Periode","Heure","Nombre Avions par heure","Temps moyen attente arrivée par heure (min)","Temps moyen atente TaxiWayIn par heure(min)","Temps moyen attente TaxiWayOut par heure (min)","Temps moyen attente décollage par heure(min)","Temps moyen d'attente par heure(min)","Temps moyen de retard par heure(min)","Nombre d'avion en retard par heure","Durée moyenne phase d'arrivee","Duree moyenne phase de depart"};
		basicLogicalDateTime bldt = new basicLogicalDateTime(this.SimulationDate());
		String date=bldt.toString();
		
		String heuretottab []= date.split(" ");
		String heuretot=heuretottab[1];
		String heuretab []=heuretot.split(":");
		String heure=heuretab[0];
		int heureint=Integer.parseInt(heure);
		int jour=this.motor.getJour();
		int retardAtt=0;
		int retardDec=0;
				
		String tab [] = this.motor.getTabloggerRetards();
		
		//Permet obtenir dernier evenement de chaque heure:
		
		if(heureint==Integer.parseInt(tab[4])||Integer.parseInt(tab[4])==0){

			tab[0] = date;

			//Jour:
			if(jour==0){
				tab[1]="Lundi";
			}else if(jour==1){
				tab[1]="Mardi";
			}else if(jour==2){
				tab[1]="Mercredi";
			}else if(jour==3){
				tab[1]="Jeudi";
			}else if(jour==4){
				tab[1]="Vendredi";
			}else if(jour==5){
				tab[1]="Samedi";
			}else if(jour==6){
				tab[1]="Dimanche";
			}
			
			//Temps:
			if(this.motor.getPluie()==true){
				tab[2]="Pluie";
			}else{
				tab[2]="Beau Temps";
			}
			
			//Periode:
			if(jour==5 || jour==6){
				tab[3]="Weekend";
			}else{
				tab[3]="Semaine";
			}
			
			//Heure:
			tab[4]=heure;
			
			//Nombre Avions
			this.motor.setNbAvionHeure(this.motor.getNbAvionHeure()+1);
			tab[5]=Integer.toString(this.motor.getNbAvionHeure());
			
			//Attente total arrivée (min):
			if(H.getDebutAttArr()!=null){
				//Attente arrivee:
				retardAtt=(H.getFinAttArr().soustract(H.getDebutAttArr())).getMinutes();
				tab[6] = Integer.toString(Integer.parseInt(tab[6])+retardAtt);
			}
			
			//Attente total TaxiWayIn (min):
			if(H.getDebutAtttaxiWayIn()!=null){
				//Attente TaxiWayIn:
				tab[7] = Integer.toString(Integer.parseInt(tab[7])+(H.getFinAtttaxiWayIn().soustract(H.getDebutAtttaxiWayIn())).getMinutes());
			}
			
			//Attente total TaxiWayOut (min):
			if(H.getDebutAtttaxiWayOut()!=null){
				//Attente TaxiWayOut:
				tab[8] = Integer.toString(Integer.parseInt(tab[8])+(H.getFinAtttaxiWayOut().soustract(H.getDebutAtttaxiWayOut())).getMinutes());
			}
			
			//Attente total décollage (min):
			if(H.getDebutAttDep()!=null){
				//Attente départ:
				retardDec=(H.getFinAttDep().soustract(H.getDebutAttDep())).getMinutes();
				tab[9] = Integer.toString(Integer.parseInt(tab[9])+retardDec);
			}
	        
			
			//Attente total (min):
			tab[10]=Integer.toString(Integer.parseInt(tab[10])+Integer.parseInt(tab[6])+Integer.parseInt(tab[7])+Integer.parseInt(tab[8])+Integer.parseInt(tab[9]));
			
			//Temps total de retard(min):
			tab[11]=Integer.toString(Integer.parseInt(tab[11])+Integer.parseInt(tab[6])+Integer.parseInt(tab[9]));
							
			//Nombre d'avions en retard:
			if(retardAtt+retardDec>5){
				this.motor.setNbAvionRetard(this.motor.getNbAvionRetard()+1); 
			}
			
			tab[12]=Integer.toString(this.motor.getNbAvionRetard());
			
			//Duree total de la phase arrivee:
			tab[13]=Integer.toString(Integer.parseInt(tab[13])+this.avion.getFinArrRoulement().soustract(this.avion.getDebutApproche()).getMinutes());
			
			//Duree total de la phase decollage:
			tab[14]=Integer.toString(Integer.parseInt(tab[14])+this.avion.getFinDecollage().soustract(this.avion.getDebutDecRoulement()).getMinutes());
			
			
			this.motor.setTabloggerRetards(tab);
			
		}else{
			
			//On affiche les valeurs dans le logger:
			
			//Date simu:
			r[0]=tab[0];
			//Jour:
			r[1]=tab[1];
			//Temps:
			r[2]=tab[2];
			//Periode:
			r[3]=tab[3];
			//Heure:
			r[4]=tab[4];
			//Nombre avions:
			r[5]=tab[5];
			//Temps moyen attente arrivee:
			r[6]=Integer.toString(Integer.parseInt(tab[6])/this.motor.getNbAvionHeure());
			//Temps moyen attente TaxiWayIn:
			r[7]=Integer.toString(Integer.parseInt(tab[7])/this.motor.getNbAvionHeure());
			//Temps moyen attente TaxiWayOut:
			r[8]=Integer.toString(Integer.parseInt(tab[8])/this.motor.getNbAvionHeure());
			//Temps moyen attente decollage:
			r[9]=Integer.toString(Integer.parseInt(tab[9])/this.motor.getNbAvionHeure());
			//Temps moyen attente:
			r[10]=Integer.toString(Integer.parseInt(r[6])+Integer.parseInt(r[7])+Integer.parseInt(r[8])+Integer.parseInt(r[9]));
			//Temps moyen retard:
			r[11]=Integer.toString(Integer.parseInt(r[6])+Integer.parseInt(r[9]));
			//Nombre d'avions en retard:
			r[12]=tab[12];
			//Duree moyenne phase d arrivee:
			r[13]=Integer.toString(Integer.parseInt(tab[13])/this.motor.getNbAvionHeure());
			//Duree moyenne phase de depart:
			r[14]=Integer.toString(Integer.parseInt(tab[14])/this.motor.getNbAvionHeure());
			
			
			//On remet a jour la variable tab:
			tab[0] = date;
			
			tab[0]="0";
			tab[1]="0";
			tab[2]="0";
			tab[3]="0";
			tab[4]="0";
			tab[5]="0";
			tab[6]="0";
			tab[7]="0";
			tab[8]="0";
			tab[9]="0";
			tab[10]="0";
			tab[11]="0";
			tab[12]="0";
			tab[13]="0";
			tab[14]="0";
			
			this.motor.setNbAvionHeure(0);
			this.motor.setNbAvionRetard(0);

			//Temps simu:
			tab[0] = date;
			
			//Jour:
			if(jour==0){
				tab[1]="Lundi";
			}else if(jour==1){
				tab[1]="Mardi";
			}else if(jour==2){
				tab[1]="Mercredi";
			}else if(jour==3){
				tab[1]="Jeudi";
			}else if(jour==4){
				tab[1]="Vendredi";
			}else if(jour==5){
				tab[1]="Samedi";
			}else if(jour==6){
				tab[1]="Dimanche";
			}
			
			//Temps:
			if(this.motor.getPluie()==true){
				tab[2]="Pluie";
			}else{
				tab[2]="Beau Temps";
			}
			
			//Periode:
			if(jour==5 || jour==6){
				tab[3]="Weekend";
			}else{
				tab[3]="Semaine";
			}
			
			//Heure:
			tab[4]=heure;

			//Nombre Avions
			this.motor.setNbAvionHeure(this.motor.getNbAvionHeure()+1);
			tab[5]=Integer.toString(this.motor.getNbAvionHeure());

			//Attente total arrivée (min):
			if(H.getDebutAttArr()!=null){
				//Attente arrivee:
				retardAtt=(H.getFinAttArr().soustract(H.getDebutAttArr())).getMinutes();
				tab[6] = Integer.toString(Integer.parseInt(tab[6])+retardAtt);
			}
			
			//Attente total TaxiWayIn (min):
			if(H.getDebutAtttaxiWayIn()!=null){
				//Attente TaxiWayIn:
				tab[7] = Integer.toString(Integer.parseInt(tab[7])+(H.getFinAtttaxiWayIn().soustract(H.getDebutAtttaxiWayIn())).getMinutes());
			}
			
			//Attente total TaxiWayOut (min):
			if(H.getDebutAtttaxiWayOut()!=null){
				//Attente TaxiWayOut:
				tab[8] = Integer.toString(Integer.parseInt(tab[8])+(H.getFinAtttaxiWayOut().soustract(H.getDebutAtttaxiWayOut())).getMinutes());
			}
			
			//Attente total décollage (min):
			if(H.getDebutAttDep()!=null){
				//Attente départ:
				retardDec=(H.getFinAttDep().soustract(H.getDebutAttDep())).getMinutes();
				tab[9] = Integer.toString(Integer.parseInt(tab[9])+retardDec);
			}
	        
			
			//Attente total (min):
			tab[10]=Integer.toString(Integer.parseInt(tab[10])+Integer.parseInt(tab[6])+Integer.parseInt(tab[7])+Integer.parseInt(tab[8])+Integer.parseInt(tab[9]));
			
			//Temps total de retard(min):
			tab[11]=Integer.toString(Integer.parseInt(tab[11])+Integer.parseInt(tab[6])+Integer.parseInt(tab[9]));
							
			//Nombre d'avions en retard:
			if(retardAtt+retardDec>5){
				this.motor.setNbAvionRetard(this.motor.getNbAvionRetard()+1); 
			}
			
			tab[12]=Integer.toString(this.motor.getNbAvionRetard());
			
			//Duree total de la phase arrivee:
			tab[13]=Integer.toString(Integer.parseInt(tab[13])+this.avion.getFinArrRoulement().soustract(this.avion.getDebutApproche()).getMinutes());
			
			//Duree total de la phase decollage:
			tab[14]=Integer.toString(Integer.parseInt(tab[14])+this.avion.getFinDecollage().soustract(this.avion.getDebutDecRoulement()).getMinutes());
			
			
			this.motor.setTabloggerRetards(tab);
			
			Logger.Data(new basicIrecordable(titles,r, "Retards par heure"));	

		}

	}
	
	
	
	public void ecritureLoggerRetardsJour(Avion H){
		String r [] ={"0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
		String titles [] ={"Temps simulation","Jour","Temps","Periode","Nombre Avions par jour","Temps moyen attente arrivée (min) par jour","Temps moyen atente TaxiWayIn par jour(min)","Temps moyen attente TaxiWayOut par jour(min)","Temps moyen attente décollage par jour(min)","Temps moyen d'attente par jour(min)","Temps moyen de retard par jour(min)","Nombre d'avion en retard par jour","Durée moyenne phase d'arrivee","Duree moyenne phase de depart"};
		basicLogicalDateTime bldt = new basicLogicalDateTime(this.SimulationDate());
		String date=bldt.toString();
		
		int jour=this.motor.getJour();
		int jourtest=0;
		int retardAtt=0;
		int retardDec=0;
				
		String tab [] = this.motor.getTabloggerRetards2();
		

		if(tab[1]=="Lundi"){
			jourtest=0;
		}else if (tab[1]=="Mardi"){
			jourtest=1;
		}else if (tab[1]=="Mercredi"){
			jourtest=2;
		}else if (tab[1]=="Jeudi"){
			jourtest=3;
		}else if (tab[1]=="Vendredi"){
			jourtest=4;
		}else if (tab[1]=="Samedi"){
			jourtest=5;
		}else if (tab[1]=="Dimanche"){
			jourtest=6;
		}else{
			jourtest=-1;
		}
		
		
		//Permet obtenir dernier evenement de chaque heure:
		
		if(jour==jourtest||jourtest==-1){

			tab[0] = date;

			//Jour:
			if(jour==0){
				tab[1]="Lundi";
			}else if(jour==1){
				tab[1]="Mardi";
			}else if(jour==2){
				tab[1]="Mercredi";
			}else if(jour==3){
				tab[1]="Jeudi";
			}else if(jour==4){
				tab[1]="Vendredi";
			}else if(jour==5){
				tab[1]="Samedi";
			}else if(jour==6){
				tab[1]="Dimanche";
			}
			
			//Temps:
			if(this.motor.getPluie()==true){
				tab[2]="Pluie";
			}else{
				tab[2]="Beau Temps";
			}
			
			//Periode:
			if(jour==5 || jour==6){
				tab[3]="Weekend";
			}else{
				tab[3]="Semaine";
			}
			
			//Nombre Avions
			this.motor.setNbAvionDecJour(this.motor.getNbAvionDecJour()+1);
			tab[4]=Integer.toString(this.motor.getNbAvionDecJour());
			
			//Attente total arrivée (min):
			if(H.getDebutAttArr()!=null){
				//Attente arrivee:
				retardAtt=(H.getFinAttArr().soustract(H.getDebutAttArr())).getMinutes();
				tab[5] = Integer.toString(Integer.parseInt(tab[5])+retardAtt);
			}
			
			//Attente total TaxiWayIn (min):
			if(H.getDebutAtttaxiWayIn()!=null){
				//Attente TaxiWayIn:
				tab[6] = Integer.toString(Integer.parseInt(tab[6])+(H.getFinAtttaxiWayIn().soustract(H.getDebutAtttaxiWayIn())).getMinutes());
			}
			
			//Attente total TaxiWayOut (min):
			if(H.getDebutAtttaxiWayOut()!=null){
				//Attente TaxiWayOut:
				tab[7] = Integer.toString(Integer.parseInt(tab[7])+(H.getFinAtttaxiWayOut().soustract(H.getDebutAtttaxiWayOut())).getMinutes());
			}
			
			//Attente total décollage (min):
			if(H.getDebutAttDep()!=null){
				//Attente départ:
				retardDec=(H.getFinAttDep().soustract(H.getDebutAttDep())).getMinutes();
				tab[8] = Integer.toString(Integer.parseInt(tab[8])+retardDec);
			}
	        
			
			//Attente total (min):
			tab[9]=Integer.toString(Integer.parseInt(tab[9])+Integer.parseInt(tab[5])+Integer.parseInt(tab[6])+Integer.parseInt(tab[7])+Integer.parseInt(tab[8]));
			
			//Temps total de retard(min):
			tab[10]=Integer.toString(Integer.parseInt(tab[10])+Integer.parseInt(tab[5])+Integer.parseInt(tab[8]));
							
			//Nombre d'avions en retard:
			if(retardAtt+retardDec>5){
				this.motor.setNbAvionDecRetardJour(this.motor.getNbAvionDecRetardJour()+1); 
			}
			
			tab[11]=Integer.toString(this.motor.getNbAvionDecRetardJour());
			
			//Duree total de la phase arrivee:
			tab[12]=Integer.toString(Integer.parseInt(tab[12])+this.avion.getFinArrRoulement().soustract(this.avion.getDebutApproche()).getMinutes());
			
			//Duree total de la phase decollage:
			tab[13]=Integer.toString(Integer.parseInt(tab[13])+this.avion.getFinDecollage().soustract(this.avion.getDebutDecRoulement()).getMinutes());
			
			
			this.motor.setTabloggerRetards2(tab);
			
		}else{
			
			//On affiche les valeurs dans le logger:
			
			//Date simu:
			r[0]=tab[0];
			//Jour:
			r[1]=tab[1];
			//Temps:
			r[2]=tab[2];
			//Periode:
			r[3]=tab[3];
			//Nombre avions:
			r[4]=tab[4];
			//Temps moyen attente arrivee:
			r[5]=Integer.toString(Integer.parseInt(tab[5])/this.motor.getNbAvionDecJour());
			//Temps moyen attente TaxiWayIn:
			r[6]=Integer.toString(Integer.parseInt(tab[6])/this.motor.getNbAvionDecJour());
			//Temps moyen attente TaxiWayOut:
			r[7]=Integer.toString(Integer.parseInt(tab[7])/this.motor.getNbAvionDecJour());
			//Temps moyen attente decollage:
			r[8]=Integer.toString(Integer.parseInt(tab[8])/this.motor.getNbAvionDecJour());
			//Temps moyen attente:
			r[9]=Integer.toString(Integer.parseInt(r[5])+Integer.parseInt(r[6])+Integer.parseInt(r[7])+Integer.parseInt(r[8]));
			//Temps moyen retard:
			r[10]=Integer.toString(Integer.parseInt(r[5])+Integer.parseInt(r[8]));
			//Nombre d'avions en retard:
			r[11]=tab[11];
			//Duree moyenne de la phase arrivee:
			r[12]=Integer.toString(Integer.parseInt(tab[12])/this.motor.getNbAvionDecJour());
			//Duree moyenne de la phase de depart:
			r[13]=Integer.toString(Integer.parseInt(tab[13])/this.motor.getNbAvionDecJour());
			
			//On remet a jour la variable tab:
			tab[0] = date;
			
			tab[0]="0";
			tab[1]="0";
			tab[2]="0";
			tab[3]="0";
			tab[4]="0";
			tab[5]="0";
			tab[6]="0";
			tab[7]="0";
			tab[8]="0";
			tab[9]="0";
			tab[10]="0";
			tab[11]="0";
			tab[12]="0";
			tab[13]="0";
			
			this.motor.setNbAvionDecJour(0);
			this.motor.setNbAvionDecRetardJour(0);
			
			tab[0] = date;

			//Jour:
			if(jour==0){
				tab[1]="Lundi";
			}else if(jour==1){
				tab[1]="Mardi";
			}else if(jour==2){
				tab[1]="Mercredi";
			}else if(jour==3){
				tab[1]="Jeudi";
			}else if(jour==4){
				tab[1]="Vendredi";
			}else if(jour==5){
				tab[1]="Samedi";
			}else if(jour==6){
				tab[1]="Dimanche";
			}
			
			//Temps:
			if(this.motor.getPluie()==true){
				tab[2]="Pluie";
			}else{
				tab[2]="Beau Temps";
			}
			
			//Periode:
			if(jour==5 || jour==6){
				tab[3]="Weekend";
			}else{
				tab[3]="Semaine";
			}
			
			//Nombre Avions
			this.motor.setNbAvionDecJour(this.motor.getNbAvionDecJour()+1);
			tab[4]=Integer.toString(this.motor.getNbAvionDecJour());
			
			//Attente total arrivée (min):
			if(H.getDebutAttArr()!=null){
				//Attente arrivee:
				retardAtt=(H.getFinAttArr().soustract(H.getDebutAttArr())).getMinutes();
				tab[5] = Integer.toString(Integer.parseInt(tab[5])+retardAtt);
			}
			
			//Attente total TaxiWayIn (min):
			if(H.getDebutAtttaxiWayIn()!=null){
				//Attente TaxiWayIn:
				tab[6] = Integer.toString(Integer.parseInt(tab[6])+(H.getFinAtttaxiWayIn().soustract(H.getDebutAtttaxiWayIn())).getMinutes());
			}
			
			//Attente total TaxiWayOut (min):
			if(H.getDebutAtttaxiWayOut()!=null){
				//Attente TaxiWayOut:
				tab[7] = Integer.toString(Integer.parseInt(tab[7])+(H.getFinAtttaxiWayOut().soustract(H.getDebutAtttaxiWayOut())).getMinutes());
			}
			
			//Attente total décollage (min):
			if(H.getDebutAttDep()!=null){
				//Attente départ:
				retardDec=(H.getFinAttDep().soustract(H.getDebutAttDep())).getMinutes();
				tab[8] = Integer.toString(Integer.parseInt(tab[8])+retardDec);
			}
	        
			
			//Attente total (min):
			tab[9]=Integer.toString(Integer.parseInt(tab[9])+Integer.parseInt(tab[5])+Integer.parseInt(tab[6])+Integer.parseInt(tab[7])+Integer.parseInt(tab[8]));
			
			//Temps total de retard(min):
			tab[10]=Integer.toString(Integer.parseInt(tab[10])+Integer.parseInt(tab[5])+Integer.parseInt(tab[8]));
							
			//Nombre d'avions en retard:
			if(retardAtt+retardDec>5){
				this.motor.setNbAvionDecRetardJour(this.motor.getNbAvionDecRetardJour()+1); 
			}
			
			tab[11]=Integer.toString(this.motor.getNbAvionDecRetardJour());
			
			//Duree total de la phase arrivee:
			tab[12]=Integer.toString(Integer.parseInt(tab[12])+this.avion.getFinArrRoulement().soustract(this.avion.getDebutApproche()).getMinutes());
			
			//Duree total de la phase decollage:
			tab[13]=Integer.toString(Integer.parseInt(tab[13])+this.avion.getFinDecollage().soustract(this.avion.getDebutDecRoulement()).getMinutes());
			
			
			this.motor.setTabloggerRetards2(tab);
			
			Logger.Data(new basicIrecordable(titles,r, "Retards par jour"));	

		}

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
