package com.app.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * This object will store all the required current session settings which will
 * be shared by all the controllers.
 * 
 * @author duttaaji
 *
 */
public class Session {
	private Object mainObj;
	private Stage primaryStage;
	private String user_name; // Current windows login user name.

	private String xmlPrimeCodeFile;
	private String prodSys;
	private String drSys;
	private String fupCommandFile;

	private ObservableList<String> devSys;
	private ObservableList<TandemObject> primeCodeObjects;
	private InstallConfig installConfig;

	// private ObservableList<TandemObject> tandemObjects;
	// private ObservableList<RollFile> rollFiles;
	private ObservableList<AppMsgModel> msg;
	private ObservableList<String> b24Products;

	public Session() {
		super();
		devSys = FXCollections.observableArrayList();
		primeCodeObjects = FXCollections.observableArrayList();
		// tandemObjects = FXCollections.observableArrayList();
		// rollFiles = FXCollections.observableArrayList();
		msg = FXCollections.observableArrayList();
		setB24Products(FXCollections.observableArrayList());
		setInstallConfig(new InstallConfig());
	}

	public Object getMainObj() {
		return mainObj;
	}

	public void setMainObj(Object mainObj) {
		this.mainObj = mainObj;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getXmlPrimeCodeFile() {
		return xmlPrimeCodeFile;
	}

	public void setXmlPrimeCodeFile(String xmlPrimeCodeFile) {
		this.xmlPrimeCodeFile = xmlPrimeCodeFile;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public ObservableList<String> getDevSys() {
		return devSys;
	}

	public void setDevSys(ObservableList<String> devSys) {
		this.devSys = devSys;
	}

	public ObservableList<TandemObject> getPrimeCodeObjects() {
		return primeCodeObjects;
	}

	public void setPrimeCodeObjects(ObservableList<TandemObject> primeCodeObjects) {
		this.primeCodeObjects = primeCodeObjects;
	}

	// public ObservableList<TandemObject> getInstallObjects() {
	// return tandemObjects;
	// }
	//
	// public void setInstallObjects(ObservableList<TandemObject> tandemObjects)
	// {
	// this.tandemObjects = tandemObjects;
	// }
	//
	// public ObservableList<RollFile> getRollFiles() {
	// return rollFiles;
	// }
	//
	// public void setRollFiles(ObservableList<RollFile> rollFiles) {
	// this.rollFiles = rollFiles;
	// }

	public ObservableList<AppMsgModel> getMsg() {
		return msg;
	}

	public void setMsg(ObservableList<AppMsgModel> msg) {
		this.msg = msg;
	}

	public void addMsg(AppMsgModel msg) {
		this.msg.add(msg);
	}

	public String getProdSys() {
		return prodSys;
	}

	public void setProdSys(String prodSys) {
		this.prodSys = prodSys;
	}

	public String getDrSys() {
		return drSys;
	}

	public void setDrSys(String drSys) {
		this.drSys = drSys;
	}

	public ObservableList<String> getB24Products() {
		return b24Products;
	}

	public void setB24Products(ObservableList<String> b24Products) {
		this.b24Products = b24Products;
	}

	public InstallConfig getInstallConfig() {
		return installConfig;
	}

	public void setInstallConfig(InstallConfig installConfig) {
		this.installConfig = installConfig;
	}

	public String getFupCommandFile() {
		return fupCommandFile;
	}

	public void setFupCommandFile(String fupCommandFile) {
		this.fupCommandFile = fupCommandFile;
	}

}
