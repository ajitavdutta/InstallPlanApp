package com.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class AppScreenController implements Initializable{
	@FXML
	private TabPane tabPane;
	
	@FXML
	private Tab tabConfig;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			this.tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
			this.tabPane.getStyleClass().add("floating");
			AnchorPane anchorPane = null;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("AppConfig.fxml"));
			anchorPane = (AnchorPane) loader.load();
			tabConfig.setContent(anchorPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
