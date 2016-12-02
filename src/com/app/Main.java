package com.app;

import com.app.models.Session;
import com.app.util.AppUtility;
import com.app.view.SceneSelector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	private Session session;

	@Override
	public void start(Stage primaryStage) {
		try {
			loadSessionData(primaryStage);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainScreen.fxml"));
			loader.setControllerFactory(SceneSelector.createControllerFactory(session));
			final Parent root = (Parent) loader.load();
			final Scene scene = new Scene(root, 1200, 800);
			
			// Get current screen of the stage      
			//ObservableList<Screen> screens = Screen.getScreensForRectangle(new Rectangle2D(primaryStage.getX(), primaryStage.getY(), primaryStage.getWidth(), primaryStage.getHeight()));

			// Change stage properties
			primaryStage.setMaximized(true);
			/*Rectangle2D bounds = screens.get(0).getVisualBounds();
			primaryStage.setX(bounds.getMinX());
			primaryStage.setY(bounds.getMinY());
			primaryStage.setWidth(bounds.getWidth());
			primaryStage.setHeight(bounds.getHeight());*/
			
			primaryStage.setTitle("Switching Team Install Plan App - " + session.getUserName());
			primaryStage.getIcons().add(new Image("file:resources/images/MainAppIcon.png"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
			//TODO : Add a exception message prompt;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void loadSessionData(Stage stage) {
		session = new Session();
		AppUtility.loadSystemConfig(session);
	}
}
