package com.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.app.models.Session;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AppScreenController implements Initializable{
	
	@FXML
	private AppConfigController appConfigController;
	
	
	private Session session;
	
	public AppScreenController(Session session){
		this.setSession(session);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
