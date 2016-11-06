package com.app.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InstallConfig {
	private SimpleStringProperty number;
	private SimpleStringProperty primeCodeRef;
	private SimpleStringProperty description;
	private SimpleStringProperty workRequest;
	private ObservableList<RollFile> rollFiles;
	private ObservableList<TandemObject> tandemObjects;
	
	public InstallConfig(){
		super();
		number = new SimpleStringProperty();
		primeCodeRef = new SimpleStringProperty();
		description = new SimpleStringProperty();
		workRequest  = new SimpleStringProperty();
		rollFiles = FXCollections.observableArrayList();
		tandemObjects = FXCollections.observableArrayList();
	}

	public String getNumber() {
		return number.get();
	}

	public void setNumber(String number) {
		this.number.set(number.toUpperCase());
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

	public ObservableList<RollFile> getRollFiles() {
		return rollFiles;
	}

	public void setRollFiles(ObservableList<RollFile> rollFiles) {
		this.rollFiles = rollFiles;
	}

	public ObservableList<TandemObject> getInstallObjects() {
		return tandemObjects;
	}

	public void setInstallObjects(ObservableList<TandemObject> tandemObjects) {
		this.tandemObjects = tandemObjects;
	}
	
}
