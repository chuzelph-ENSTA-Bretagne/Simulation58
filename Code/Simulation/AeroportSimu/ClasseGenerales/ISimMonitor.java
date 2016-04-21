package ClasseGenerales;

import enstabretagne.base.time.LogicalDateTime;

public abstract class ISimMonitor{
	
	private LogicalDateTime depart;
	private LogicalDateTime fin;
	protected ISimEngine motor;
	
		
	public LogicalDateTime getDepart() {
		return depart;
	}

	public void setDepart(LogicalDateTime depart) {
		this.depart = depart;
	}

	public LogicalDateTime getFin() {
		return fin;
	}

	public void setFin(LogicalDateTime fin) {
		this.fin = fin;
	}

	public ISimEngine getMotor() {
		return motor;
	}

	public void setMotor(ISimEngine motor) {
		this.motor = motor;
	}

	public ISimMonitor(LogicalDateTime depart,LogicalDateTime fin){
		this.fin = fin;
		this.depart = depart;
	}
	
	public static LogicalDateTime t(int n) {
		String month = Integer.toString(n);
		while (month.length() < 2)
			month = "0" + month;
		return new LogicalDateTime("01/" + month + "/2015" + " 00:00:00.0000");
	}
	
	public static void main(String[] args){
		/*
		 * Lance la totalité de la simulation
		 * 
		 */
		/*ISimMonitor mon = new ISimMonitor(t(1),t(2)); 
		//mon.motor.getListEvent().add(new EventCoiffeur(new LogicalDateTime("01/01/2015" + " 00:02:00.0000")));
		//mon.motor.getListEvent().add(new EventClient(new LogicalDateTime("01/01/2015" + " 00:01:00.0000"), mon.motor,0));
		mon.motor.initialize();
		mon.motor.resume();
		
		
		//Premier d entre eux: le logger qui ecrit dans la sortie standard
		HashMap<String,HashMap<String,Object>> loggersNames = new HashMap<String,HashMap<String,Object>>();
		loggersNames.put(SysOutLogger.class.getCanonicalName(), new HashMap<String,Object>());
		
		//Deuxieme: le logger qui ecrit dans un fichier excel dont on determine l emplacement (ici dynamiquement via la propriete user.dir)
		HashMap<String,Object> params = new HashMap<String,Object>() ;
		params.put(LoggerParamsNames.DirectoryName.toString(),System.getProperty("user.dir"));
		params.put(LoggerParamsNames.FileName.toString(),"LoggerAndProba.xlxs");
		loggersNames.put(XLSXExcelDataloggerImpl.class.getCanonicalName(),params);
		
	    
		basicLogicalDateTime bldt = new basicLogicalDateTime(t(1));
		Logger.Init(bldt,  loggersNames, true);
		
		while (mon.motor.triggerNextEvent()) {}
	
		
		Logger.Terminate();*/
	}
}
