package com.app.models;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;

public class InstallConfig {
	private SimpleStringProperty number;
	private SimpleStringProperty primeCodeRef;
	private SimpleStringProperty description;
	private SimpleStringProperty workRequest;
	private SimpleStringProperty prodSys;
	private SimpleStringProperty drSys;
	private SimpleListProperty<String> rollFiles;
	private SimpleListProperty<String> objects;
	
		
	public SimpleStringProperty getNumber() {
		return number;
	}
	public void setNumber(SimpleStringProperty number) {
		this.number = number;
	}
	public SimpleStringProperty getPrimeCodeRef() {
		return primeCodeRef;
	}
	public void setPrimeCodeRef(SimpleStringProperty primeCodeRef) {
		this.primeCodeRef = primeCodeRef;
	}
	public SimpleStringProperty getDescription() {
		return description;
	}
	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}
	public SimpleStringProperty getWorkRequest() {
		return workRequest;
	}
	public void setWorkRequest(SimpleStringProperty workRequest) {
		this.workRequest = workRequest;
	}
	public SimpleStringProperty getProdSys() {
		return prodSys;
	}
	public void setProdSys(SimpleStringProperty prodSys) {
		this.prodSys = prodSys;
	}
	public SimpleStringProperty getDrSys() {
		return drSys;
	}
	public void setDrSys(SimpleStringProperty drSys) {
		this.drSys = drSys;
	}
	public SimpleListProperty<String> getRollFiles() {
		return rollFiles;
	}
	public void setRollFiles(SimpleListProperty<String> rollFiles) {
		this.rollFiles = rollFiles;
	}
	public SimpleListProperty<String> getObjects() {
		return objects;
	}
	public void setObjects(SimpleListProperty<String> objects) {
		this.objects = objects;
	}
}
