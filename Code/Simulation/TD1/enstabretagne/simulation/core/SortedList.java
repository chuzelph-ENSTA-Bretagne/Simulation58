package enstabretagne.simulation.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;

public class SortedList <T extends Comparable<T>> implements Iterable<T>{

	List <T> l;
	
	public SortedList(){
		l = new ArrayList<T>();
	}
	
	public int size(){
		return l.size();
	}
	
	public void clear(){
		l.clear();
	}
	
	public void add(T element){
		l.add(element);
		Collections.sort(l);
	}
	
	public void remove(T element){
		l.remove(element);
	}
	
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return l.iterator();
	}
	
	public T first(){
		return l.get(0);
	}
	
	public boolean contains(T ev){
		return l.contains(ev);
	}
	
	public T get(int i){
		return this.l.get(i);
	}
	
	public void display(){
		Iterator<T> i = iterator();
		int compteur = 0;
		while(i.hasNext()){
		System.out.println(l.get(compteur));
		compteur++;		
		i.next();
		}
	}
	
	public static void main(String[] args){
		SortedList<LogicalDateTime> sl= new SortedList<LogicalDateTime>();
		LogicalDateTime ldt = new LogicalDateTime("10/12/2015 10:34:47.6789");
		LogicalDateTime ldt1 = new LogicalDateTime("11/12/2015 10:34:47.6789");
		LogicalDateTime ldt2 = new LogicalDateTime("12/12/2015 10:34:47.6789");
		LogicalDateTime ldt3 = new LogicalDateTime("13/12/2015 10:34:47.6789");
		sl.add(ldt);sl.add(ldt1);sl.add(ldt3);sl.add(ldt2);
		sl.display();
		System.out.println(sl.get(1));
		MoreRandom m=new MoreRandom();
		System.out.println(m.nextInt()%120);
	}
}
