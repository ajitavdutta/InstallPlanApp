package com.app.models;

import javafx.stage.Stage;

/**
 * This object will store all the required current session settings which will
 * be shared by all the controllers.
 * 
 * @author duttaaji
 *
 */
public class SessionModel {
	private Stage primaryStage;
	private String user_name; // Current windows login user name.
	/*
	 * Below are the variables that will store all the XML/properties file
	 * location which will have all the required data.
	 */
	private String xmlPrimeCodeFile;
	/*
	 * 
	 */
	
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

}
