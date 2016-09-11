package com.app.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.app.test.model.ObjectModel;
import com.app.test.model.PrimeCodeObjectModel;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class PrimeCodeObjectsViewController implements Initializable {
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
	private Button btnUpdate;

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
	private TableColumn<ObjectModel, Integer> colVersion;

	@FXML
	private TableColumn<ObjectModel, String> colLive;

	@FXML
	private CheckBox chkRel5;

	@FXML
	private CheckBox chkRel6;

	@FXML
	private CheckBox chkLiveObj;

	private ToggleGroup rbBtnGrp;

	private ObservableList<ObjectModel> objects;
	private SortedList<ObjectModel> sortedObjects;
	private FilteredList<ObjectModel> filteredObjects;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		objects = FXCollections.observableArrayList();

		colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		colLocation.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
		colVersion.setCellValueFactory(cellData -> cellData.getValue().versionProperty().asObject());
		colLive.setCellValueFactory(cellData -> cellData.getValue().liveProperty().asString());

		tblPrimeCodeList.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showObjectDetail(newValue));

		btnAdd.setDisable(true);
		btnUpdate.setDisable(true);
		txtName.setEditable(false);
		txtLocation.setEditable(false);
		rbtnRel5.setDisable(true);
		rbtnRel6.setDisable(true);
		chkLive.setDisable(true);

		rbBtnGrp = new ToggleGroup();
		rbtnRel5.setToggleGroup(rbBtnGrp);
		rbtnRel6.setToggleGroup(rbBtnGrp);

		loadData();

		filteredObjects = new FilteredList<>(objects, p -> true);
		txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredObjects.setPredicate(obj -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (obj.getName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (obj.getLocation().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}

				return false;
			});
		});

		sortedObjects = new SortedList<>(filteredObjects);
		sortedObjects.comparatorProperty().bind(tblPrimeCodeList.comparatorProperty());
		tblPrimeCodeList.setItems(sortedObjects);
		
		chkRel5.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue){
					filteredObjects.setPredicate(obj -> {
						boolean retVal = false;
						if(obj.getVersion() == 5){
							retVal = true;
						} else if ((chkRel6.isSelected()) && obj.getVersion() == 6 ){
							retVal = true;
						}
						
						if(chkLive.isSelected() && (!obj.getLive())){
							retVal = false;
						}
						
						return retVal;
					});
				}
				
				sortedObjects = new SortedList<>(filteredObjects);
			}
		});

		chkRel6.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				filteredObjects.setPredicate(obj -> {
					if (newValue == null) {
						return true;
					}

					if (chkRel5.isSelected() && (obj.getVersion() == 5)) {
						return true;
					} else if ((!chkRel5.isSelected()) && (obj.getVersion() == 5)) {
						return false;
					}

					if (newValue && (obj.getVersion() == 6)) {
						return true;
					} else if ((!newValue) && (obj.getVersion() == 6)) {
						return false;
					}

					return true;
				});
				sortedObjects = new SortedList<>(filteredObjects);
			}
		});
		

	}

	public void showObjectDetail(ObjectModel data) {
		if (data == null) {
			txtName.clear();
			txtLocation.clear();
			rbtnRel5.setSelected(false);
			rbtnRel6.setSelected(false);
			chkLive.setSelected(false);
		} else {
			txtName.setText(data.getName());
			txtLocation.setText(data.getLocation());
			if (data.getVersion() == 5) {
				rbtnRel5.setSelected(true);
			} else {
				rbtnRel6.setSelected(true);
			}

			chkLive.setSelected(data.getLive());
		}
	}

	public void loadData() {
		try {
			File xmlFile = new File("resources/xml/PrimeCodeData.xml");
			if (xmlFile.exists()) {
				loadObjectsFromFile(xmlFile);
				if (objects != null) {
					tblPrimeCodeList.setItems(objects);
				}
			} else {
				System.out.println("File is not present.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadObjectsFromFile(File file) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(PrimeCodeObjectModel.class);
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			PrimeCodeObjectModel objs = (PrimeCodeObjectModel) unMarshaller.unmarshal(file);
			objects = objs.getObjects();
		} catch (Exception e) {
			// File is empty so return a null
			e.printStackTrace();
		}
	}

}
