package com.app.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InstallConfig {
	private SimpleStringProperty number;
	private SimpleStringProperty primeCodeRef;
	private SimpleStringProperty description;
	private SimpleStringProperty workRequest;
	private SimpleStringProperty prodSys;
	private SimpleStringProperty drSys;
	private SimpleStringProperty devSys;
	private ObservableList<RollFile> rollFiles;
	private ObservableList<TandemObject> objects;
	private SimpleStringProperty fupCmdFile;

	public InstallConfig() {
		super();
		number = new SimpleStringProperty();
		primeCodeRef = new SimpleStringProperty();
		description = new SimpleStringProperty();
		workRequest = new SimpleStringProperty();
		prodSys = new SimpleStringProperty();
		devSys = new SimpleStringProperty();
		drSys = new SimpleStringProperty();
		rollFiles = FXCollections.observableArrayList();
		objects = FXCollections.observableArrayList();
		fupCmdFile = new SimpleStringProperty();
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

	public ObservableList<TandemObject> getObjects() {
		return objects;
	}

	public void setObjects(ObservableList<TandemObject> tandemObjects) {
		this.objects = tandemObjects;
	}

	public void setDevSys(String devSys) {
		this.devSys.set(devSys);
	}

	public String getDevSys() {
		return this.devSys.get();
	}

	public SimpleStringProperty devSysProperty() {
		return this.devSys;
	}

	public void setProdSys(String prodSys) {
		this.prodSys.set(prodSys);
	}

	public String getProdSys() {
		return this.prodSys.get();
	}

	public SimpleStringProperty prodSysProperty() {
		return this.prodSys;
	}

	public void setDrSys(String drSys) {
		this.drSys.set(drSys);
	}

	public String getDrSys() {
		return this.drSys.get();
	}

	public SimpleStringProperty drSysProperty() {
		return this.drSys;
	}

	public String getFUPCmdFile() {
		return fupCmdFile.get();
	}

	public void setFUPCmdFile(String fupCmdFile) {
		this.fupCmdFile.set(fupCmdFile);
	}

	public SimpleStringProperty fupCmdFileProperty() {
		return this.fupCmdFile;
	}
}
