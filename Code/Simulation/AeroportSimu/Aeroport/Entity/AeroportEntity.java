package Aeroport.Entity;

import java.util.ArrayList;

import Aeroport.classeIntAero;
import ClasseGenerales.ISimEngine;
import ClasseGenerales.ISimEntity;

public class AeroportEntity extends ISimEntity{


	private classeIntAero intType2;
	
	public AeroportEntity(ArrayList<ISimEntity> listEntitySon,
			ISimEngine moteur, int id) {
		super(listEntitySon, moteur, id);
		// TODO Auto-generated constructor stub
	}

	public classeIntAero getIntType2() {
		return intType2;
	}

	public void setIntType2(classeIntAero intType2) {
		this.intType2 = intType2;
	}
	
}
