package Aeroport.Entity;

import java.util.ArrayList;

import Aeroport.classeIntAero;
import ClasseGenerales.ISimEngine;
import ClasseGenerales.ISimEntity;

public class TaxiWayIn extends AeroportEntity{

	public TaxiWayIn(ArrayList<ISimEntity> listEntitySon, ISimEngine moteur,int id) {
		super(listEntitySon, moteur, id);
		this.setIntType2(classeIntAero.TaxiWayIn);
	}
	
}
