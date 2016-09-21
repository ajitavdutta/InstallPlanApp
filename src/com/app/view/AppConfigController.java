package com.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.app.models.InstallObjectModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class AppConfigController implements Initializable{

	@FXML
	private TableView<InstallObjectModel> tblInstallObjects;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("Width " + tblInstallObjects.getPrefWidth());
	}

}
