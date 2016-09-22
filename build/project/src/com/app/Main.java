package com.app;

import com.app.view.AppScreenController;
import com.app.view.MainScreenController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application { 
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Get the current monitor screen dimension
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

			// Step 1 - Load the AnchorPane (Main Screen)
			FXMLLoader loader = new FXMLLoader();
			//loader.setLocation(Main.class.getResource("view/MainScreen.fxml"));
			loader.setLocation(Main.class.getResource("view/AppScreen.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			//MainScreenController controller = loader.getController();
			AppScreenController controller = loader.getController();

			// Step 2 - Setup the scene and add default style CSS
			Scene scene = new Scene(root, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight() - 50);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//scene.setUserAgentStylesheet(STYLESHEET_CASPIAN);
			//System.setProperty( "javafx.userAgentStylesheetUrl", "CASPIAN" );
			
			primaryStage.setTitle("Switching Team Install Plan App");
			primaryStage.getIcons().add(new Image("file:resources/images/MainAppIcon.png"));
			//primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(1000.0);
	        primaryStage.setMinHeight(600.0);
			//primaryStage.setMaximized(true);
			primaryStage.show();
		
			//controller.setPrimaryStage(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}