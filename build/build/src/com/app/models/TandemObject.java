package com.app.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@XmlRootElement(name="Object")
public class TandemObject {
	private SimpleStringProperty name;
	private SimpleIntegerProperty version;
	private SimpleBooleanProperty live;
	private SimpleStringProperty location;

	public TandemObject() {
		super();
		this.name = new SimpleStringProperty();
		this.version = new SimpleIntegerProperty();
		this.live = new SimpleBooleanProperty();
		this.location = new SimpleStringProperty();
	}
	
	public TandemObject(String name, int version, String location, boolean live){
		this.name = new SimpleStringProperty(name.toUpperCase());
		this.version = new SimpleIntegerProperty(version);
		this.location = new SimpleStringProperty(location.toUpperCase());
		this.live = new SimpleBooleanProperty(live);
	}

	public String getName() {
		return name.get();
	}

	@XmlElement(name="Name")
	public void setName(String name) {
		this.name.set(name.toUpperCase());
	}

	public SimpleStringProperty nameProperty() {
		return this.name;
	}

	public int getVersion() {
		return version.get();
	}

	@XmlElement(name="Version")
	public void setVersion(int version) {
		this.version.set(version);
	}

	public SimpleIntegerProperty versionProperty() {
		return this.version;
	}

	public boolean getLive() {
		return live.get();
	}

	@XmlElement(name="Live")
	public void setLive(boolean live) {
		this.live.set(live);
	}

	public SimpleBooleanProperty liveProperty() {
		return live;
	}
	
	public String getLocation() {
		return location.get();
	}

	@XmlElement(name="Location")
	public void setLocation(String location) {
		this.location.set(location.toUpperCase());
	}

	public SimpleStringProperty locationProperty() {
		return this.location;
	}

}
