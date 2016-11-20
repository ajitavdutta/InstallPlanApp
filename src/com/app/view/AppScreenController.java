package com.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.app.models.ApplicationInfo;
import com.app.models.InstallConfig;
import com.app.models.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AppScreenController implements Initializable{
	
	@FXML
	private AppConfigController appConfigController;
	private ApplicationInfo appInfo;
	private InstallConfig installConfig;
	
	private Session session;
	
	public AppScreenController(Session session){
		this.setSession(session);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		appInfo = session.getAppInfo();
		installConfig = session.getInstallConfig();
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	@FXML
	public void editSetings(ActionEvent event){
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfigScreen.fxml"));
			loader.setController(new ConfigScreenController(session, stage));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle("Settings");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(appInfo.getParentStage());
			stage.getIcons().add(new Image("file:resources/images/Settings.png"));
			stage.setResizable(false);
			stage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
