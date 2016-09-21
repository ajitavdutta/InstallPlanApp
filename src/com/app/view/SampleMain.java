package com.app.view;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.app.Main;
import com.app.models.SessionModel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SampleMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("PrimeCodeObjectsView.fxml"));

		AnchorPane root = (AnchorPane) loader.load();

		SessionModel session_data = getSessionData(primaryStage);

		PrimeCodeObjectsViewController controller = loader.getController();
		
		if (session_data != null) {
			controller.setSession_data(session_data);
			controller.loadData();
		}

		Scene scene = new Scene(root);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		primaryStage.setTitle("Object Viewer");
		primaryStage.getIcons().add(new Image("file:resources/images/MainAppIcon.png"));
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(750);
		primaryStage.setMinHeight(600.0);
		primaryStage.setMaximized(false);
		primaryStage.show();

	}

	public SessionModel getSessionData(Stage primaryStage) {
		try {
			Properties prop = new Properties();
			InputStream input = new FileInputStream("config/app-config.properties");
			prop.load(input);

			SessionModel session_data = new SessionModel();
			
			session_data.setPrimaryStage(primaryStage);
			session_data.setUser_name(System.getProperty("user.name"));
			session_data.setXmlPrimeCodeFile(prop.getProperty("PRIMECODE-XML"));
			return session_data;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
