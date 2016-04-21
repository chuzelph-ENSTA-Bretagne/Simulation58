package ClasseGenerales;

import enstabretagne.base.utility.IRecordable;

public class basicIrecordable implements IRecordable {

	
	// Variable:
	private String[] titles;
	private String[] records;
	private String classement;
	
	
	// Constructeur:
	public basicIrecordable(String[] titles,String[] records, String classement){
		this.titles=titles;
		this.records=records;
        this.classement=classement;
	}


	@Override
	public String[] getTitles() {
		// TODO Auto-generated method stub
		return this.titles;
	}


	@Override
	public String[] getRecords() {
		// TODO Auto-generated method stub
		return this.records;
	}


	@Override
	public String getClassement() {
		// TODO Auto-generated method stub
		return this.classement;
	}
	
}
