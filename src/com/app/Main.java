package com.app;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	
	private void letterbox(final Scene scene, final Pane contentPane) {
	    final double initWidth  = scene.getWidth();
	    final double initHeight = scene.getHeight();
	    final double ratio      = initWidth / initHeight;

	    SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
	    scene.widthProperty().addListener(sizeListener);
	    scene.heightProperty().addListener(sizeListener);
	  }
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Get the current monitor screen dimension
			//Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			//final double rem = javafx.scene.text.Font.getDefault().getSize();

			// Step 1 - Load the AnchorPane (Main Screen)
			FXMLLoader loader = new FXMLLoader();
			//loader.setLocation(Main.class.getResource("view/MainScreen.fxml"));
			loader.setLocation(Main.class.getResource("view/AppScreen.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			//MainScreenController controller = loader.getController();
			//AppScreenController controller = loader.getController();

			// Step 2 - Setup the scene and add default style CSS
			Scene scene = new Scene(root);
			//Scene scene = new Scene(root,90 * rem, 50 * rem);
			
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
			
			
			primaryStage.setTitle("Switching Team Install Plan App");
			primaryStage.getIcons().add(new Image("file:resources/images/MainAppIcon.png"));
			//primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.UTILITY);
			primaryStage.setScene(scene);
			//primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(2));
			//primaryStage.minHeightProperty().bind(scene.widthProperty().divide(2));
			primaryStage.show();
			
			letterbox(scene, root);
			//primaryStage.setMaximized(true);
			primaryStage.setFullScreen(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private static class SceneSizeChangeListener implements ChangeListener<Number> {
	    private final Scene scene;
	    private final double ratio;
	    private final double initHeight;
	    private final double initWidth;
	    private final Pane contentPane;

	    public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
	      this.scene = scene;
	      this.ratio = ratio;
	      this.initHeight = initHeight;
	      this.initWidth = initWidth;
	      this.contentPane = contentPane;
	    }

	    @Override
	    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
	      final double newWidth  = scene.getWidth();
	      final double newHeight = scene.getHeight();

	      double scaleFactor =
	          newWidth / newHeight > ratio
	              ? newHeight / initHeight
	              : newWidth / initWidth;

	      if (scaleFactor >= 1) {
	        Scale scale = new Scale(scaleFactor, scaleFactor);
	        scale.setPivotX(0);
	        scale.setPivotY(0);
	        scene.getRoot().getTransforms().setAll(scale);

	        contentPane.setPrefWidth (newWidth  / scaleFactor);
	        contentPane.setPrefHeight(newHeight / scaleFactor);
	      } else {
	        contentPane.setPrefWidth (Math.max(initWidth,  newWidth));
	        contentPane.setPrefHeight(Math.max(initHeight, newHeight));
	      }
	    }
	  }
}
