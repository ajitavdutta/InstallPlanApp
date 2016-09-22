package com.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.app.models.InstallObjectModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AppConfigController implements Initializable{

	@FXML
	private TableView<InstallObjectModel> tblInstallObjects;
	
	@FXML
	private TextField txtObjSearch;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("Width " + tblInstallObjects.getPrefWidth());
		txtObjSearch.setPrefWidth(tblInstallObjects.getPrefWidth());
	}

}
