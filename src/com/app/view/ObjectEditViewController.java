package com.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.app.models.Session;
import com.app.models.TandemObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ObjectEditViewController implements Initializable {
	private Session session;
	private TandemObject selObject;
	
	@FXML
	private TextField txtObjName;

	@FXML
	private TextField txtVersion;

	@FXML
	private TextField txtObjLocation;

	@FXML
	private CheckBox chkLive;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnUpdate;

	public ObjectEditViewController(Session session, TandemObject selObject) {
		this.session = session;
		this.selObject = selObject;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// load data from session object
		txtObjName.setText(selObject.getName());
		txtObjLocation.setText(selObject.getLocation());
		txtVersion.setText(new Integer(selObject.getVersion()).toString());
		chkLive.setSelected(selObject.getLive());

		// txtObjName.textProperty().bind(selObject.nameProperty());
		// txtVersion.textProperty().bind(selObject.versionProperty().asString());
		// chkLive.selectedProperty().bind(selObject.liveProperty());
		// txtObjLocation.textProperty().bind(selObject.locationProperty());
	}
	
	private boolean updated(){
		if (!selObject.getName().equalsIgnoreCase(txtObjName.getText()) ||
			!selObject.getLocation().equalsIgnoreCase(txtObjLocation.getText()) ||
			selObject.getVersion() != new Integer(txtVersion.getText()).intValue() ||
			selObject.getLive() != chkLive.isSelected()){
			return true;
		}
		
		return false;
	}

	@FXML
	public void handelUpdate(ActionEvent event) {
		
		if (updated()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("This action will also update the PrimeCode config ");
			alert.setContentText("Are you ok with this?");
			if (alert.showAndWait().get() == ButtonType.OK){
				// Update the primeCode XML database
				selObject.setName(txtObjName.getText().toUpperCase());
				selObject.setLocation(txtObjLocation.getText().toUpperCase());
				selObject.setVersion(new Integer(txtVersion.getText()).intValue());
				selObject.setLive(chkLive.isSelected());
			}
		}
		
		Stage stage = (Stage) btnUpdate.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void handelCancel(ActionEvent event) {
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
	}

}
