package Aeroport;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public final class globalVariables {
	
	public static String scenarioName;
	
	public static int gatesNum;
	public static int pistNum;
	public static int enterTaxiWaysNum;
	public static int exitTaxiWaysNum;
	
	public static double[] approachTime = new double[2];
	public static double[] landingTime = new double[2];
	public static double[] drivingTime = new double[2];
	public static double[] disembarkingPassengersTime = new double[2];
	public static double[] planePreparationTime = new double[2];
	public static double[] boardingPassengersTime = new double[2];
	public static double[] takeOffTime = new double[2];
	
	public static double planesNumByHour;
	public static double planesNumByRushHour;
	public static double planesNumByHourWE;
	public static double[] rushHourInterval = new double[4];
	public static double[] closingTimeInterval = new double[2];
	
	public static double badWeatherRate;
	
	public static double simulationTime;
	
	public static void readLine(String[] items){
		if (items[0].equals("ScenarioName")){
			scenarioName = items[1];
		}
		if (items[0].equals("GatesNum")){
			gatesNum = Integer.parseInt(items[1]);
		}
		if (items[0].equals("EnterTaxiWaysNum")){
			enterTaxiWaysNum = Integer.parseInt(items[1]);
		}
		if (items[0].equals("ExitTaxiWaysNum")){
			exitTaxiWaysNum = Integer.parseInt(items[1]);
		}
		if (items[0].equals("PistNum")){
			pistNum = Integer.parseInt(items[1]);
		}
		
		if (items[0].equals("ApproachTime")){
			if (items[1].equals("[")){
				approachTime[0] = Double.parseDouble(items[2]);
				approachTime[1] = Double.parseDouble(items[3]);
			}
			else {
				approachTime[0] = Double.parseDouble(items[1]);
			}
		}
		if (items[0].equals("LandingTime")){
			if (items[1].equals("[")){
				landingTime[0] = Double.parseDouble(items[2]);
				landingTime[1] = Double.parseDouble(items[3]);
			}
			else {
				landingTime[0] = Double.parseDouble(items[1]);
			}
		}
		if (items[0].equals("DrivingTime")){
			if (items[1].equals("[")){
				drivingTime[0] = Double.parseDouble(items[2]);
				drivingTime[1] = Double.parseDouble(items[3]);
			}
			else {
				drivingTime[0] = Double.parseDouble(items[1]);
			}
		}
		if (items[0].equals("DisembarkingPassengersTime")){
			if (items[1].equals("[")){
				disembarkingPassengersTime[0] = Double.parseDouble(items[2]);
				disembarkingPassengersTime[1] = Double.parseDouble(items[3]);
			}
			else {
				disembarkingPassengersTime[0] = Double.parseDouble(items[1]);
			}
		}
		if (items[0].equals("PlanePreparationTime")){
			if (items[1].equals("[")){
				planePreparationTime[0] = Double.parseDouble(items[2]);
				planePreparationTime[1] = Double.parseDouble(items[3]);
			}
			else {
				planePreparationTime[0] = Double.parseDouble(items[1]);
			}
		}
		if (items[0].equals("BoardingPassengersTime")){
			if (items[1].equals("[")){
				boardingPassengersTime[0] = Double.parseDouble(items[2]);
				boardingPassengersTime[1] = Double.parseDouble(items[3]);
			}
			else {
				boardingPassengersTime[0] = Double.parseDouble(items[1]);
			}
		}
		if (items[0].equals("TakeOffTime")){
			if (items[1].equals("[")){
				takeOffTime[0] = Double.parseDouble(items[2]);
				takeOffTime[1] = Double.parseDouble(items[3]);
			}
			else {
				takeOffTime[0] = Double.parseDouble(items[1]);
			}
		}
		
		if (items[0].equals("PlanesNumByHour")){
			planesNumByHour = Double.parseDouble(items[1]);
		}
		if (items[0].equals("PlanesNumByRushHour")){
			planesNumByRushHour = Double.parseDouble(items[1]);
		}
		if (items[0].equals("PlanesNumByHourWE")){
			planesNumByHourWE = Double.parseDouble(items[1]);
		}
		if (items[0].equals("RushHourInterval")){
			for(int i=0; i<items.length; i++){
				if (items[i].equals("[")){
					rushHourInterval[(i-1)/2] = Double.parseDouble(items[i+1]);
					rushHourInterval[(i-1)/2+1] = Double.parseDouble(items[i+2]);
				}	
			}
		}
		if (items[0].equals("ClosingTimeInterval")){
			for(int i=0; i<items.length; i++){
				if (items[i].equals("[")){
					closingTimeInterval[(i-1)/2] = Double.parseDouble(items[i+1]);
					closingTimeInterval[(i-1)/2+1] = Double.parseDouble(items[i+2]);
				}	
			}
		}
		
		if (items[0].equals("BadWeatherRate")){
			badWeatherRate = Double.parseDouble(items[1]);
		}
		if (items[0].equals("SimulationTime")){
			simulationTime = Double.parseDouble(items[1]);
		}
	}
	
	public static void readScript(String fichier){
				
		// p va dire comment on souhaite spliter la ligne du fichier texte
		Pattern p = Pattern.compile(" ");
				
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			
			while ((ligne=br.readLine())!=null){

				String[] items;
				
				// Split de la chaine attention, le nombre doit �tre assez grand pour contenir tous les mots
				items = p.split(ligne, 100);
				
				// Traite les informations de la ligne
				readLine(items);				
			}
			br.close();
		}		
		
		catch (Exception e){
			System.out.println(e.toString());
		}
		
	}
}
