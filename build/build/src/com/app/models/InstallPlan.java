package com.app.models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlRootElement(name = "InstallPlan")
public class InstallPlan {
	private SimpleStringProperty sdmNumber;
	private SimpleStringProperty installDate;
	private SimpleStringProperty installTime;
	private SimpleStringProperty sdmSummary;
	private SimpleStringProperty workRequest;
	private SimpleStringProperty primeCodeRef;
	private SimpleStringProperty prodSys;
	private SimpleStringProperty drSys;

	private ObservableList<RollFile> rollFileList;
	private ObservableList<TandemObject> objList;

	public InstallPlan() {
		sdmNumber = new SimpleStringProperty();
		installDate = new SimpleStringProperty();
		installTime = new SimpleStringProperty();
		sdmSummary = new SimpleStringProperty();
		workRequest = new SimpleStringProperty();
		primeCodeRef = new SimpleStringProperty();
		prodSys = new SimpleStringProperty();
		drSys = new SimpleStringProperty();

		rollFileList = FXCollections.observableArrayList();
		objList = FXCollections.observableArrayList();
	}

	public String getSdmNumber() {
		return sdmNumber.get();
	}

	@XmlAttribute(name = "SDM-NUMBER")
	public void setSdmNumber(String sdmNumber) {
		this.sdmNumber.set(sdmNumber);
	}

	public SimpleStringProperty sdmNumberProperty() {
		return sdmNumber;
	}

	public String getInstallDate() {
		return installDate.get();
	}

	@XmlElement(name = "INSTALL-DATE")
	public void setInstallDate(String installDate) {
		this.installDate.set(installDate);
	}

	public SimpleStringProperty installDateProperty() {
		return installDate;
	}

	public String getInstallTime() {
		return installTime.get();
	}

	@XmlElement(name = "INSTALL-TIME")
	public void setInstallTime(String installTime) {
		this.installTime.set(installTime);
	}

	public SimpleStringProperty installTimeProperty() {
		return installTime;
	}

	public String getSdmSummary() {
		return sdmSummary.get();
	}

	@XmlElement(name = "SDM-SUMMARY")
	public void setSdmSummary(String sdmSummary) {
		this.sdmSummary.set(sdmSummary);
	}

	public SimpleStringProperty sdmSummaryProperty() {
		return sdmSummary;
	}

	public String getWorkRequest() {
		return workRequest.get();
	}

	@XmlElement(name = "WORK-REQUEST")
	public void setWorkRequest(String workRequest) {
		this.workRequest.set(workRequest);
	}

	public SimpleStringProperty workRequestProperty() {
		return workRequest;
	}

	public String getPrimeCodeRef() {
		return primeCodeRef.get();
	}

	@XmlElement(name = "PRIMECODE-USERCHANGE")
	public void setPrimeCodeRef(String primeCodeRef) {
		this.primeCodeRef.set(primeCodeRef);
	}

	public SimpleStringProperty primeCodeRefProperty() {
		return primeCodeRef;
	}

	public String getProdSys() {
		return prodSys.get();
	}

	@XmlElement(name = "PROD-SYS")
	public void setProdSys(String prodSys) {
		this.prodSys.set(prodSys);
	}

	public SimpleStringProperty prodSysProperty() {
		return prodSys;
	}

	public String getDrSys() {
		return drSys.get();
	}

	@XmlElement(name = "DR-SYS")
	public void setDrSys(String drSys) {
		this.drSys.set(drSys);
	}

	public SimpleStringProperty drSysProperty() {
		return drSys;
	}

	public ObservableList<RollFile> getRollFileList() {
		return rollFileList;
	}

	@XmlElementWrapper(name = "ROLLFILE-LIST")
	@XmlElement(name = "FILE")
	public void setRollFileList(ObservableList<RollFile> rollFileList) {
		this.rollFileList = rollFileList;
	}

	public ObservableList<TandemObject> getObjList() {
		return objList;
	}

	@XmlElementWrapper(name = "OBJECT-LIST")
	@XmlElement(name = "OBJECT")
	public void setObjList(ObservableList<TandemObject> objList) {
		this.objList = objList;
	}

}
