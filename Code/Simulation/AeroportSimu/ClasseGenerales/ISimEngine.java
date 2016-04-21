package ClasseGenerales;

import java.util.ArrayList;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.ISimulationDateProvider;
import enstabretagne.simulation.core.SortedList;
/**
 * @author Philippe
 *	Doit on utiliser ici un thread?
 *si oui, il faut créer un run qui va déroler les fonctions des différentes entités
 *http://alwin.developpez.com/tutorial/JavaThread/
 */
public abstract class ISimEngine implements ISimulationDateProvider{
	

	private SortedList<ISimEvent> listEvent = new SortedList<ISimEvent>();
	private ArrayList<ISimEntity> listEntity = new ArrayList<ISimEntity>();
	private MoreRandom m;
	private LogicalDateTime tempsCourant;
	private LogicalDateTime fin;
	// durée totale de la simulation
	private LogicalDuration dt;
	
	/**
	 * Getter setter
	 */
	
	public SortedList<ISimEvent> getListEvent() {
		return listEvent;
	}

	public void setListEvent(SortedList<ISimEvent> listEvent) {
		this.listEvent = listEvent;
	}

	public ArrayList<ISimEntity> getListEntity() {
		return listEntity;
	}

	public void setListEntity(ArrayList<ISimEntity> listEntity) {
		this.listEntity = listEntity;
	}

	public void setTempsCourant(LogicalDateTime tempsCourant) {
		this.tempsCourant = tempsCourant;
	}

	public LogicalDateTime SimulationDate() {
		return this.tempsCourant;
	}
	
	public MoreRandom getM() {
		return m;
	}

	public void setM(MoreRandom m) {
		this.m = m;
	}

	public LogicalDuration getDt() {
		return dt;
	}

	public LogicalDateTime getFin() {
		return fin;
	}

	public void setFin(LogicalDateTime fin) {
		this.fin = fin;
	}

	public void setDt(LogicalDuration dt) {
		this.dt = dt;
	}
	
	/**
	 * Constructeur
	 * @param fin
	 */
	
	public ISimEngine(LogicalDateTime fin,LogicalDateTime debut){
		this.m=new MoreRandom();
		this.fin = fin;
		this.tempsCourant=debut;
		this.setDt(LogicalDuration.soustract(this.fin,debut));
	}
	
	public void initialize() {
		for (ISimEntity entity : listEntity)
			entity.initialize();
	}
	
	public void pause() {
		for (ISimEntity entity : listEntity)
			entity.pause();
	}
	
	public void resume() {
		for (ISimEntity entity : listEntity)
			entity.activate();
	}
	
	public abstract void init();
	
	public boolean triggerNextEvent() {
		// TODO add maxTime check
		if (listEvent.size() == 0) {
			for (ISimEntity entity : listEntity)
				entity.terminate();
			return false;
		}
		ISimEvent nextEvent = listEvent.first();
		listEvent.remove(nextEvent);
		this.tempsCourant = nextEvent.SimulationDate();
		nextEvent.Process();
		return true;
	}
	
/*	public void parcoursSimulateList(ArrayList<ISimEntity> list){
		int i =0;
		for(i=0;i<list.size();i++){
			// teste la liste d'entité fils. Si non nulle, on applique la même méthode
			if(list.get(i).getListEntitySon()!=null){
				parcoursSimulateList(list.get(i).getListEntitySon());
			}
		}
	}*/

	/**
	 * table des états
	 *
	 */
	
	public enum etat{
		none ("NONE"),
		born ("BORN"),
		idle ("IDLE"),
		held ("HELD"),
		active ("ACTIVE"),
		dead ("DEAD");
		private String Etat;

		
		etat(String Etat){
			this.Etat = Etat;
		}
		   
		public String toString(){
			    return Etat;
		}
		
	}
	
	public enum transition{
		constructor ("CONSTRUCTOR"),
		initialize ("INITIALIZE"),
		activate ("ACTIVATE"),
		pause ("PAUSE"),
		deactivate ("DEACTIVATE"),
		terminate ("TERMINATE");
		private String transition;

		
		transition(String transition){
			this.transition = transition;
		}
		   
		public String toString(){
			    return transition;
		}
		
	}
	

}
