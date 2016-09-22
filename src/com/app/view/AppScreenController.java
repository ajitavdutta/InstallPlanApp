package com.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class AppScreenController implements Initializable{
	@FXML
	private TabPane tabPane;
	
	@FXML
	private Tab tabConfig;

	@FXML
	private BorderPane apConfigPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			this.tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
			this.tabPane.getStyleClass().add("floating");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("AppConfig.fxml"));
			
			//anchorPane.setPrefWidth(spConfigPane.getPrefWidth());
			apConfigPane.getChildren().clear();
			apConfigPane.setCenter((AnchorPane)loader.load());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
