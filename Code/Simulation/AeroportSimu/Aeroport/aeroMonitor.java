package Aeroport;

import java.util.HashMap;

import ClasseGenerales.ISimMonitor;
import ClasseGenerales.basicLogicalDateTime;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;
import enstabretagne.base.utility.LoggerParamsNames;
import enstabretagne.base.utility.loggerimpl.SysOutLogger;
import enstabretagne.base.utility.loggerimpl.XLSXExcelDataloggerImpl;

public class aeroMonitor extends ISimMonitor{

	public aeroMonitor(LogicalDateTime depart, LogicalDateTime fin, int nbPiste,
			int nbGateWay, int nbTaxiwaysEntree,int nbTaxiWaysSortie) {
		super(depart, fin);
		this.motor = new aeroEngine( depart,  fin,  nbPiste,
				 nbGateWay,  nbTaxiwaysEntree, nbTaxiWaysSortie);
	}
	
	/**
	 * Méthode qui lance la simulation et iitialise le logger
	 * @param nom
	 */
	public void run(){
		
		// initialisation des variables
		this.getMotor().initialize();
		this.getMotor().resume();
		
		//Initialisation du LOGGER:
		//Premier d entre eux: le logger qui ecrit dans la sortie standard
		HashMap<String,HashMap<String,Object>> loggersNames = new HashMap<String,HashMap<String,Object>>();
		loggersNames.put(SysOutLogger.class.getCanonicalName(), new HashMap<String,Object>());
		
		//Deuxieme: le logger qui ecrit dans un fichier excel dont on determine l emplacement (ici dynamiquement via la propriete user.dir)
		HashMap<String,Object> params = new HashMap<String,Object>() ;
		params.put(LoggerParamsNames.DirectoryName.toString(),System.getProperty("user.dir"));
		
		// nom du fhcier où on écrit
		String titreScenario = globalVariables.scenarioName + ".xlxs";
		params.put(LoggerParamsNames.FileName.toString(),titreScenario);
		loggersNames.put(XLSXExcelDataloggerImpl.class.getCanonicalName(),params);
		
		basicLogicalDateTime bldt = new basicLogicalDateTime(new LogicalDateTime("01/01/2015" + " 00:00:00.0000"));
		Logger.Init(bldt,  loggersNames, true);
		
		// tant qu'il y a des évènements
		while (this.getMotor().triggerNextEvent()) {}
		// on ferme le logger
		Logger.Terminate();
	}


	
	public static void main(String[] args){
		/*
		 * Lance la totalité de la simulation
		 */
		globalVariables.readScript("Scenario_AirportSimulation.txt");
		LogicalDateTime t1 = new LogicalDateTime("01/01/2015" + " 00:00:00.0000");
		LogicalDuration ld = LogicalDuration.ofDay((long)globalVariables.simulationTime);
		LogicalDateTime t2 = t1.add(ld);
		aeroMonitor mon = new aeroMonitor(t1,t2,globalVariables.pistNum,globalVariables.gatesNum,globalVariables.enterTaxiWaysNum,globalVariables.exitTaxiWaysNum); // (t1,t2,nbPistes,nbGateWay,nbTaxiwaysEntree,nbTaxiWaysSortie)

		mon.run();
	}
}
