package com.app.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * This class will be used to store all the application related information and
 * the various methods to update various application information.
 * 
 * @author Ajitav
 *
 */
public class ApplicationInfo {
	private Object parentObj;
	private Stage parentStage;
	private SimpleStringProperty userName;
	private SimpleStringProperty installSaveFile;
	private SimpleStringProperty pcXmlFile;
	private ObservableList<String> products;

	public ApplicationInfo(Object parentObj, Stage parentStage, String userName, String installSaveFile,
			String pcXmlFile, ObservableList<String> products, PrimeCodeObjects pcObjects) {
		this.setParentObj(parentObj);
		this.setParentStage(parentStage);
		this.userName = new SimpleStringProperty(userName);
		this.installSaveFile = new SimpleStringProperty(installSaveFile);
		this.pcXmlFile = new SimpleStringProperty(pcXmlFile);
		this.products = products;
	}

	public ApplicationInfo() {
		this.userName = new SimpleStringProperty();
		this.installSaveFile = new SimpleStringProperty();
		this.pcXmlFile = new SimpleStringProperty();
		this.products = FXCollections.observableArrayList();
	}

	public Object getParentObj() {
		return parentObj;
	}

	public void setParentObj(Object parentObj) {
		this.parentObj = parentObj;
	}

	public Stage getParentStage() {
		return parentStage;
	}

	public void setParentStage(Stage parentStage) {
		this.parentStage = parentStage;
	}

	public String getUserName() {
		return userName.get();
	}

	public void setUserName(String userName) {
		this.userName.set(userName);
	}

	public SimpleStringProperty userNameProperty() {
		return this.userName;
	}

	public String getInstallSaveFile() {
		return installSaveFile.get();
	}

	public void setInstallSaveFile(String installSaveFile) {
		this.installSaveFile.set(installSaveFile);
	}

	public SimpleStringProperty installSaveFileProperty() {
		return this.installSaveFile;
	}

	public String getPCXmlFile() {
		return pcXmlFile.get();
	}

	public void setPCXmlFile(String pcXmlFile) {
		this.pcXmlFile.set(pcXmlFile);
	}

	public SimpleStringProperty pcXmlFileProperty() {
		return this.pcXmlFile;
	}

	public ObservableList<String> getProducts() {
		return products;
	}

	public void setProducts(ObservableList<String> products) {
		this.products = products;
	}

}
