package ClasseGenerales;

import java.util.ArrayList;

public abstract class ISimEntity {
	
	
	private ClasseGenerales.ISimEngine.etat etat;
	/**
	 * NONE BORN IDLE HELD ACTIVE DEAD
	 * None: état avant création
	 * Born: utilistion du construteur
	 * Idle:
	 * Held:
	 * Active:
	 * Dead: supression de l'élément
	 */

	private ArrayList<ISimEntity> listEntitySon = new ArrayList<ISimEntity>();
	private ISimEngine moteur;
	private int identifier;
	
	public ArrayList<ISimEntity> getListEntitySon() {
		return listEntitySon;
	}

	public ISimEntity(ArrayList<ISimEntity> listEntitySon, ISimEngine moteur,int id){
		this.listEntitySon=listEntitySon;
		this.etat = ClasseGenerales.ISimEngine.etat.none;
		this.moteur=moteur;
		this.identifier=id;
	}
	
	public void addEvent(ISimEvent event) {
		this.moteur.getListEvent().add(event);
	}
	
	/**
	 * change state functions , getter and setter
	 * 
	 */
	
	public void setListEntitySon(ArrayList<ISimEntity> listEntitySon) {
		this.listEntitySon = listEntitySon;
	}
	
	public void initialize(){
		this.etat=ClasseGenerales.ISimEngine.etat.born;
	}

	public void pause(){
		this.etat=ClasseGenerales.ISimEngine.etat.idle;
	}

	public void activate(){
		this.etat=ClasseGenerales.ISimEngine.etat.active;
	}
	
	public void terminate() {
		setState(ClasseGenerales.ISimEngine.etat.dead);
	}
	
	public void deactivate() {
		setState(ClasseGenerales.ISimEngine.etat.born);
	}
	
	public void lock() {
		setState(ClasseGenerales.ISimEngine.etat.held);
	}
	
	public void release() {
		setState(ClasseGenerales.ISimEngine.etat.active);
	}
	
	public ClasseGenerales.ISimEngine.etat getState() {
		return this.etat;
	}

	public void setState(ClasseGenerales.ISimEngine.etat state) {
		this.etat = state;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}


	
	
	
}
