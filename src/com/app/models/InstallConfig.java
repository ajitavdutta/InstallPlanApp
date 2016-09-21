package com.app.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class InstallConfig {
	private SimpleStringProperty number;
	private SimpleStringProperty primeCodeRef;
	private SimpleStringProperty description;
	private SimpleStringProperty workRequest;
	private SimpleStringProperty prodSys;
	private SimpleStringProperty drSys;
	private SimpleStringProperty devSys;
	private ObservableList<RollFileModel> rollFiles;
	private ObservableList<InstallObjectModel> installObjects;

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

	public SimpleStringProperty getDevSys() {
		return devSys;
	}

	public void setDevSys(SimpleStringProperty devSys) {
		this.devSys = devSys;
	}

	public ObservableList<RollFileModel> getRollFiles() {
		return rollFiles;
	}

	public void setRollFiles(ObservableList<RollFileModel> rollFiles) {
		this.rollFiles = rollFiles;
	}

	public ObservableList<InstallObjectModel> getInstallObjects() {
		return installObjects;
	}

	public void setInstallObjects(ObservableList<InstallObjectModel> installObjects) {
		this.installObjects = installObjects;
	}
	
}
