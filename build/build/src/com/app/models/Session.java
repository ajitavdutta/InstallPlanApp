package com.app.models;

import javafx.beans.property.SimpleStringProperty;

/**
 * This object will store all the required current session settings which will
 * be shared by all the controllers.
 * 
 * @author Ajitav
 *
 */
public class Session {
	private ApplicationInfo appInfo;
	private InstallConfig installConfig;
	private SimpleStringProperty userName;

	public Session() {
		super();
		setAppInfo(new ApplicationInfo());
		installConfig = new InstallConfig();
		userName = new SimpleStringProperty();
	}

	public InstallConfig getInstallConfig() {
		return installConfig;
	}

	public void setInstallConfig(InstallConfig installConfig) {
		this.installConfig = installConfig;
	}

	public ApplicationInfo getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(ApplicationInfo appInfo) {
		this.appInfo = appInfo;
	}
	
	public String getUserName(){
		return userName.get();
	}
	
	public void setUserName(String userName){
		this.userName.set(userName);
	}
	
	public SimpleStringProperty userNameProperty(){
		return this.userName;
	}

}
