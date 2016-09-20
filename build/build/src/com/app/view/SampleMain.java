package com.app.view;

import com.app.Main;

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
		/*PrimeCodeObjectsViewController controller = loader.getController();
		controller.loadData();*/
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

	public static void main(String[] args) {
		Application.launch(args);
	}

}
