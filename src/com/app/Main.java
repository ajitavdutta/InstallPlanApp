package com.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.app.models.AppMsgModel;
import com.app.models.PrimeCodeObjects;
import com.app.models.Session;
import com.app.view.SceneSelector;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	private Session session;

	@Override
	public void start(Stage primaryStage) throws IOException {
//		try {
//			loadSessionData(primaryStage);
//			
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainScreen.fxml"));
//			loader.setLocation(getClass().getResource("view/AppScreen.fxml"));
//			loader.setControllerFactory(SceneSelector.createControllerFactory(this.session));
//			AnchorPane root = (AnchorPane) loader.load();
//			final Parent root = (Parent) loader.load();
//
//			final Scene scene = new Scene(root);
//
//			scene.widthProperty().addListener(new ChangeListener<Number>() {
//				@Override
//				public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
//						Number newSceneWidth) {
//					System.out.println("Width: " + newSceneWidth);
//				}
//			});
//			scene.heightProperty().addListener(new ChangeListener<Number>() {
//				@Override
//				public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
//						Number newSceneHeight) {
//					System.out.println("Height: " + newSceneHeight);
//				}
//			});
//
//			loadSessionData(primaryStage);
//			AppScreenController controller = loader.getController();
//			controller.load(session);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//
//			primaryStage.setMaximized(true);
//			primaryStage.setResizable(false);
//			primaryStage.setTitle("Switching Team Install Plan App - " + session.getUser_name());
//			primaryStage.getIcons().add(new Image("file:resources/images/MainAppIcon.png"));
//			primaryStage.setScene(scene);
//			primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(2));
//			primaryStage.minHeightProperty().bind(scene.widthProperty().divide(2));
//			primaryStage.show();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		loadSessionData(primaryStage);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainScreen.fxml"));
		loader.setControllerFactory(SceneSelector.createControllerFactory(session));
		final Parent root = (Parent) loader.load();
		final Scene scene = new Scene(root, 1200, 800);
		primaryStage.setMaximized(true);
		primaryStage.setTitle("Switching Team Install Plan App - " + session.getUser_name());
		primaryStage.getIcons().add(new Image("file:resources/images/MainAppIcon.png"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void loadSessionData(Stage stage) {
		session = new Session();
		try {
			Properties prop = new Properties();
			InputStream input = new FileInputStream("config/app-config.properties");
			prop.load(input);

			
			session.setMainObj(getClass());
			session.setPrimaryStage(stage);
			session.setUser_name(System.getProperty("user.name"));
			session.setXmlPrimeCodeFile(prop.getProperty("PRIMECODE-XML"));
			session.setDevSys(FXCollections.observableArrayList(prop.getProperty("DEV-SYS").split(",")));
			session.setProdSys(prop.getProperty("PROD-SYS"));
			session.setDrSys(prop.getProperty("DR-SYS"));
			session.setXmlPrimeCodeFile(prop.getProperty("XML-PRIMECODE"));
			session.setB24Products(FXCollections.observableArrayList(prop.getProperty("B24-PRODUCTS").split(",")));
			session.setFupCommandFile(prop.getProperty("FUP-OUT-FILE"));

			if (session.getXmlPrimeCodeFile() != null) {
				File xmlFile = new File(session.getXmlPrimeCodeFile());
				if (xmlFile.exists()) {
					JAXBContext jaxbContext = JAXBContext.newInstance(PrimeCodeObjects.class);
					Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
					session.setPrimeCodeObjects(((PrimeCodeObjects) unMarshaller.unmarshal(xmlFile)).getObjects());
				}
			} else {
				session.addMsg(
						new AppMsgModel("error", "PrimeCode XML file not found. PrimeCode Objects will not be loaded.",
								"XML-PRIMECODE not present in app-config.properties file."));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
