package AeroportEvent;

import Aeroport.aeroEngine;
import Aeroport.globalVariables;
import Aeroport.Entity.Avion;
import ClasseGenerales.ISimEvent;
import ClasseGenerales.basicIrecordable;
import ClasseGenerales.basicLogicalDateTime;
import DemandeEvent.DemandeAttEvent;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;

public class ArriverAvionEvent extends ISimEvent{

	private aeroEngine motor;
	private int id;
	
	public ArriverAvionEvent(LogicalDateTime logicalDateTime,aeroEngine motor, int id) {
		super(logicalDateTime);
		this.motor = motor;
		this.id = id;
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void Process() {
		
		Avion H =new Avion(null,this.motor, id);
		this.motor.getListAvion().add(H);
		
		// Ajout d'un avion si dans la bonne plage horaire
		LogicalDateTime test = this.motor.appartionNextAvion();
		if(test != null && test.compareTo(
				this.motor.SimulationDate().truncateToDays().add(LogicalDuration.ofHours((long)globalVariables.closingTimeInterval[0])))<0){
			ArriverAvionEvent J=new ArriverAvionEvent(this.motor.appartionNextAvion(),this.motor, this.id+1);
			this.motor.getListEvent().add(J);

		}
		

		//Logger	
		this.motor.ecritureLoggerEvenement("Arrivée d'un Avion",this.id);
		
		// Ajout de l'event atterissage
		this.motor.getListEvent().add(new DemandeAttEvent(this.SimulationDate(),this.motor, H));
		
		this.motor.ecritureLoggerEvenement("Demande Atterissage",this.id);
		
		ecritureLoggerTrancheHoraire(H);
		ecritureLoggerJour(H);
		
		
	}

	
	//Logger:
	public void ecritureLoggerTrancheHoraire(Avion H){
		String r [] ={"0","0","0","0","0","0"};
		String titles [] ={"Temps simulation","Jour","Période","Heure","Tranche horaire","Nombre Avions/Heure"};
		basicLogicalDateTime bldt = new basicLogicalDateTime(this.SimulationDate());
		String date=bldt.toString();
		
		String heuretottab []= date.split(" ");
		String heuretot=heuretottab[1];
		String heuretab []=heuretot.split(":");
		String heure=heuretab[0];
		int heureint=Integer.parseInt(heure);
		int jour=this.motor.getJour();
		
		String tab [] = this.motor.getTablogger();
		
		//Permet obtenir dernier evenement de chaque heure:
		
		if(heureint==Integer.parseInt(tab[3])||Integer.parseInt(tab[3])==0){

			//On met a jour la variable tab:
			//Date simu:
			tab[0]=date;
			
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

			//Periode:
			if(jour==5 || jour==6){
				tab[2]="Weekend";
			}else{
				tab[2]="Semaine";
			}
			
			//Heure:
			tab[3]=heure;

			//Tranche horaire:
			if(heureint>=7 && heureint<10){
				tab[4]="07h-10h";
			}else if(heureint>=10 && heureint<17){
				tab[4]="10h-17h";
			}else if(heureint>=17 && heureint<19){
				tab[4]="17h-19h";
			}else if(heureint>=19 && heureint<22){
				tab[4]="19h-22h";
			}else{
				tab[4]="22h-07h";
			}
			
			//Nombre Avions/Jour:
			this.motor.setNbAvionJour(this.motor.getNbAvionJour()+1);
			tab[5]=Integer.toString(this.motor.getNbAvionJour());
			

			this.motor.setTablogger(tab);
			
		}else{
			
			//On affiche les valeurs dans le logger:
			
			//Date simu:
			r[0] = tab[0];
			//Jour:
			r[1]=tab[1];
			//Periode:
			r[2]=tab[2];
			//Heure:
			r[3]=tab[3];
			//Tranche horaire:
			r[4]=tab[4];
			//Nombre Avions/Jour: tab[5]


			if(Integer.parseInt(tab[3])==7){

				r[5]=tab[5];

			}else{
				//Nombre Avions/Tranches horaire:		
				int nbth=Integer.parseInt(tab[5])-this.motor.getDebutTH();
				r[5]=Integer.toString(nbth);
			}
			
			this.motor.setDebutTH(Integer.parseInt(tab[5]));

			
			//On remet a jour la variable tab:
			
			//Date simu:
			tab[0]=date;
			
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

			//Periode:
			if(jour==5 || jour==6){
				tab[2]="Weekend";
			}else{
				tab[2]="Semaine";
			}
			
			//Heure:
			tab[3]=heure;

			//Tranche horaire:
			if(heureint>=7 && heureint<10){
				tab[4]="07h-10h";
			}else if(heureint>=10 && heureint<17){
				tab[4]="10h-17h";
			}else if(heureint>=17 && heureint<19){
				tab[4]="17h-19h";
			}else if(heureint>=19 && heureint<22){
				tab[4]="19h-22h";
			}else{
				tab[4]="22h-07h";
			}

			//Nombre Avions/Jour:
			this.motor.setNbAvionJour(this.motor.getNbAvionJour()+1);
			tab[5]=Integer.toString(this.motor.getNbAvionJour());
			
			this.motor.setTablogger(tab);
			
			Logger.Data(new basicIrecordable(titles,r, "Frequentation par heure"));	
		}

	}
	
	
	public void ecritureLoggerJour(Avion H){
		String r [] ={"0","0","0","0"};
		String titles [] ={"Temps simulation","Jour","Période","Nombre Avions/Jour"};
		basicLogicalDateTime bldt = new basicLogicalDateTime(this.SimulationDate());
		String date=bldt.toString();
		
		int jour=this.motor.getJour();
		int jourtest=0;
		
		String tab [] = this.motor.getTablogger2();
		
		
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
		
		
		
		//Permet obtenir dernier evenement de chaque jour:
		
		if(jour==jourtest||jourtest==-1){

			//On met a jour la variable tab:
			//Date simu:
			tab[0]=date;
			
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

			//Periode:
			if(jour==5 || jour==6){
				tab[2]="Weekend";
			}else{
				tab[2]="Semaine";
			}
			
			//Nombre Avions/Jour:
			this.motor.setNbAvionJour2(this.motor.getNbAvionJour2()+1);
			tab[3]=Integer.toString(this.motor.getNbAvionJour2());
			
			this.motor.setTablogger2(tab);
			
		}else{
			
			//On affiche les valeurs dans le logger:
			
			//Date simu:
			r[0] = tab[0];
			//Jour:
			r[1]=tab[1];
			//Periode:
			r[2]=tab[2];
			//Nombre Avions/Jour:
			r[3]=tab[3];
			

			//On remet a jour la variable tab:
			
			//Date simu:
			tab[0]=date;
			
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

			//Periode:
			if(jour==5 || jour==6){
				tab[2]="Weekend";
			}else{
				tab[2]="Semaine";
			}
			
			//Nombre Avions/Jour:
			this.motor.setNbAvionJour2(this.motor.getNbAvionJour2()+1);
			tab[3]=Integer.toString(this.motor.getNbAvionJour2());
			
			this.motor.setTablogger2(tab);
			
			Logger.Data(new basicIrecordable(titles,r, "Frequentation par jour"));	
		}

	}
	
	
	//Getter
	public int getId() {
		return id;
	}


	
	

}
