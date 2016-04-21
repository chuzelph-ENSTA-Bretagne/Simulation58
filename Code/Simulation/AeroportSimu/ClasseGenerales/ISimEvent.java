package ClasseGenerales;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.ISimulationDateProvider;

public abstract class ISimEvent implements ISimulationDateProvider, Comparable<ISimEvent>{
	
	private  LogicalDateTime simulationDate;
	
	public abstract void Process();
	
	public void resetProcessDate(LogicalDateTime simulationDate){
		this.simulationDate=simulationDate;
	}

	public LogicalDateTime SimulationDate() {
		return simulationDate;
	}

	@Override
	public int compareTo(ISimEvent arg0) {
		return this.simulationDate.compareTo(arg0.SimulationDate());
	}

	public ISimEvent(LogicalDateTime logicalDateTime){
		this.simulationDate = logicalDateTime;
	}
}
