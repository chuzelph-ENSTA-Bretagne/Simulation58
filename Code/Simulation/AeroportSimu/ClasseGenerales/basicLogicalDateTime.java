package ClasseGenerales;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.ISimulationDateProvider;


public class basicLogicalDateTime implements ISimulationDateProvider {

	private LogicalDateTime Idt;
	//constructeur
	public basicLogicalDateTime(LogicalDateTime Idt){
		this.Idt = Idt;
	}
	
	// Getter and setter
	public LogicalDateTime getIdt() {
		return Idt;
	}

	public void setIdt(LogicalDateTime Idt) {
		this.Idt = Idt;
	}

	// fonction indu à implements
	public LogicalDateTime SimulationDate() {
		return Idt;
	}
	
	public String toString(){
		return this.Idt.toString();
	}

}
