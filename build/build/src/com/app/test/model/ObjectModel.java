package com.app.test.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@XmlRootElement(name="Object")
public class ObjectModel {
	
	private SimpleStringProperty name;      // Name of the Object
	private SimpleIntegerProperty version;  // Which BASE24 Release the object belongs to i.e. Rel5 or Rel 6 
	private SimpleStringProperty location;  // Location of the object in PrimeCode
	private SimpleBooleanProperty live;
	
	public ObjectModel(){
		this.name = new SimpleStringProperty();
		this.version = new SimpleIntegerProperty();
		this.location = new SimpleStringProperty();
		this.live = new SimpleBooleanProperty();
	}
	
	public ObjectModel(String name, int version, String location, boolean live){
		this.name = new SimpleStringProperty(name);
		this.version = new SimpleIntegerProperty(version);
		this.location = new SimpleStringProperty(location);
		this.live = new SimpleBooleanProperty(live);
	}
	
	public String getName(){
		return this.name.get();
	}
	
	@XmlElement(name="Name")
	public void setName(String name){
		this.name.set(name);
	}
	
	public SimpleStringProperty nameProperty(){
		return this.name;
	}
	
	public String getLocation(){
		return this.location.get();
	}
	
	@XmlElement(name="Location")
	public void setLocation(String location){
		this.location.set(location);
	}
	
	public SimpleStringProperty locationProperty(){
		return this.location;
	}
	
	public int getVersion(){
		return this.version.get();
	}
	
	@XmlElement(name="Version")
	public void setVersion(int version){
		this.version.set(version);
	}
	
	public SimpleIntegerProperty versionProperty(){
		return version;
	}
	
	public boolean getLive(){
		return this.live.get();
	}
	
	@XmlElement(name="Live")
	public void setLive(boolean live){
		this.live.set(live);
	}
	
	public SimpleBooleanProperty liveProperty(){
		return live;
	}
}
