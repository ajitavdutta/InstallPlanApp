package com.app.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlRootElement(name = "PrimeCodeObjects")
public class PrimeCodeObjects {
	private ObservableList<TandemObject> objects;
	
	public PrimeCodeObjects(){
		this.objects = FXCollections.observableArrayList();
	}
	
	public PrimeCodeObjects(TandemObject object){
		this.objects = FXCollections.observableArrayList();
		objects.add(object);
	}
	
	public ObservableList<TandemObject> getObjects() {
		return objects;
	}

	@XmlElement(name = "Object")
	public void setObjects(ObservableList<TandemObject> objects) {
		this.objects = objects;
	}
	
	public void addObject(TandemObject object){
		//Check if the object is already present or not.
		boolean found = false;
		int i = 0;
		
		while ((!found) && (i < this.objects.size())) {
			found = matchObject(this.objects.get(i), object);
			i++;
		}
		
		if (!found){
			this.objects.add(object);
		}
	}
	
	private boolean matchObject(TandemObject obj1, TandemObject obj2){
		//System.out.println(obj1.getName() + "  " + obj2.getName());
		if( obj1.getName().equalsIgnoreCase(obj2.getName()) &&
		    obj1.getLocation().equalsIgnoreCase(obj2.getLocation()) && 
		    obj1.getVersion() == obj2.getVersion() &&
		    obj1.getLive() == obj2.getLive()){
			return true;
		}
		
		return false;
	}

}
