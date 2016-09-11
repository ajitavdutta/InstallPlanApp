package com.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.app.test.model.ObjectModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PrimeCodeObjectsViewController implements Initializable{
	@FXML 
	private TextField txtConfigFile;
	
	@FXML 
	private TextField txtFilter;
	
	@FXML 
	private TextField txtName;
	
	@FXML 
	private TextField txtLocation;
	
	@FXML
	private RadioButton rbtnRel5;
	
	@FXML
	private RadioButton rbtnRel6;
	
	@FXML
	private CheckBox chkLive;
	
	@FXML
	private Button btnLoad;
	
	@FXML
	private Button btnNew;
	
	@FXML
	private Button btnEdit;
	
	@FXML
	private Button btnRemove;
	
	@FXML
	private Button btnAdd;
	
	@FXML
	private Button btnOK;
	
	@FXML
	private Button btnCancel;
	
	@FXML
	private TableView<ObjectModel> tblPrimeCodeList;
	
	@FXML
	private TableColumn<ObjectModel, String> colName;
	
	@FXML
	private TableColumn<ObjectModel, String> colLocation;
	
	@FXML
	private TableColumn<ObjectModel, String> colVersion;
	
	@FXML
	private TableColumn<ObjectModel, String> colLive;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
