package com.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class AppScreenController implements Initializable{
	@FXML
	private TabPane tabPane;
	
	@FXML
	private Tab tabConfig;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		this.tabPane.getStyleClass().add("floating");
	}

}
