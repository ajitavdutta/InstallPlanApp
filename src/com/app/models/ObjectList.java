package com.app.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleStringProperty;

@XmlRootElement(name="Object")
public class ObjectList {
	private SimpleStringProperty name;
	private SimpleStringProperty prod;
	private SimpleStringProperty rel;
	
	public ObjectList(){
		name = new SimpleStringProperty();
		prod = new SimpleStringProperty();
		rel = new SimpleStringProperty();
	}
	
	public ObjectList(String name, String prod, String rel){
		this.name = new SimpleStringProperty(name);
		this.prod = new SimpleStringProperty(prod);
		this.rel = new SimpleStringProperty(rel);
	}

	public String getName() {
		return name.get();
	}

	@XmlElement(name="Name")
	public void setName(String name) {
		this.name.set(name);
	}
	
	public SimpleStringProperty nameProperty(){
		return name;
	}

	public String getProd() {
		return prod.get();
	}

	@XmlElement(name="Prod")
	public void setProd(String prod) {
		this.prod.set(prod);
	}
	
	public SimpleStringProperty prodProperty(){
		return prod;
	}

	public String getRel() {
		return rel.get();
	}

	@XmlElement(name="REL")
	public void setRel(String rel) {
		this.rel.set(rel);
	}
	
	public SimpleStringProperty relProperty(){
		return rel;
	}
	
}
