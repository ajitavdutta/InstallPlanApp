package com.app;

import com.app.view.AppScreenController;
import com.app.view.MainScreenController;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Get the current monitor screen dimension
			//Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

			// Step 1 - Load the AnchorPane (Main Screen)
			FXMLLoader loader = new FXMLLoader();
			//loader.setLocation(Main.class.getResource("view/MainScreen.fxml"));
			loader.setLocation(Main.class.getResource("view/AppScreen.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			//MainScreenController controller = loader.getController();
			//AppScreenController controller = loader.getController();

			// Step 2 - Setup the scene and add default style CSS
			Scene scene = new Scene(root);
			
			scene.widthProperty().addListener(new ChangeListener<Number>() {
			    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
			        System.out.println("Width: " + newSceneWidth);
			    }
			});
			scene.heightProperty().addListener(new ChangeListener<Number>() {
			    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
			        System.out.println("Height: " + newSceneHeight);
			    }
			});
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//scene.setUserAgentStylesheet(STYLESHEET_CASPIAN);
			//System.setProperty( "javafx.userAgentStylesheetUrl", "CASPIAN" );
			
			primaryStage.setTitle("Switching Team Install Plan App");
			primaryStage.getIcons().add(new Image("file:resources/images/MainAppIcon.png"));
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.UTILITY);
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(1250.0);
	        primaryStage.setMinHeight(900.0);
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
