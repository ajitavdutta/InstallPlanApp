package com.app.view;

import java.io.IOException;
import java.lang.reflect.Constructor;

import com.app.models.Session;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Callback;


public class SceneSelector {
	
	private final Callback<Class<?>, Object> controllerFactory ;
	
	public SceneSelector(final Session session) {
		controllerFactory = createControllerFactory(session);
	}
	
	public void selectScene(String fxmlFile, Scene scene) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		loader.setControllerFactory(controllerFactory);
		scene.setRoot((Parent)loader.load());
	}
	
	public static final Callback<Class<?>, Object> createControllerFactory(final Session session) {
		return new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> type) {
				try {
					for (Constructor<?> constructor : type.getDeclaredConstructors()) {
						if (constructor.getParameterTypes().length == 1 && 
								constructor.getParameterTypes()[0]==Session.class) {
							return constructor.newInstance(session);
						}
					}
					return type.newInstance();
				} catch (Exception exc) {
					exc.printStackTrace();
					return null ;
				}
			}
			
		};
	}
}