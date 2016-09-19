package com.app.view;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.app.test.model.ObjectModel;
import com.app.test.model.PrimeCodeObjectModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	private ObjectModel selectedObject = null;
	
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

		txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredObjects.setPredicate(obj -> {
				/*
				 * Check if Release 5 & 6 objects are to be displayed or not
				 */
				if (!chkRel5.isSelected() && !chkRel6.isSelected()) {
					// No objects has to be displayed in the table;
					return false;
				}

				if (newValue == null || newValue.isEmpty()) {
					// There is no text to be searched.
					// Do nothing so that it will display all the objects in the
					// table.
				} else {
					// Filter the data based on the text present in the textbox
					String lowerCaseFilter = newValue.toLowerCase();
					if (obj.getName().toLowerCase().contains(lowerCaseFilter)
							|| obj.getLocation().toLowerCase().contains(lowerCaseFilter)) {
						// The object contains the specified text so display in
						// the Table.
					} else {
						return false;
					}
				}

				// If we reach here that object name/location contains the filter text.
				// Check if it satisfy the other filters.

				// Live Object
				if (chkLiveObj.isSelected() && !obj.getLive()) {
					return false;
				}

				// Release 5 Object
				if (!chkRel5.isSelected() && obj.getVersion() == 5) {
					return false;
				}

				// Release 6 Object
				if (!chkRel6.isSelected() && obj.getVersion() == 6) {
					return false;
				}

				return true;
			});
		});

		chkRel5.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				filteredObjects.setPredicate(obj -> {

					// Release 5
					if (!chkRel5.isSelected() && obj.getVersion() == 5) {
						return false;
					}

					// Release 6
					if (!chkRel6.isSelected() && obj.getVersion() == 6) {
						return false;
					}

					// Live Object
					if (chkLiveObj.isSelected() && !obj.getLive()) {
						return false;
					}

					// Filter text
					if (txtFilter.getText().trim().length() > 0) {
						String lowerCaseFilter = txtFilter.getText().trim().toLowerCase();
						if (obj.getName().toLowerCase().contains(lowerCaseFilter)
								|| obj.getLocation().toLowerCase().contains(lowerCaseFilter)) {
							// The object contains the specified text so display
							// in the Table.
						} else {
							return false;
						}
					}
					return true;
				});

				sortedObjects = new SortedList<>(filteredObjects);
			}
		});

		chkRel6.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				filteredObjects.setPredicate(obj -> {

					// Release 5
					if (!chkRel5.isSelected() && obj.getVersion() == 5) {
						return false;
					}

					// Release 6
					if (!chkRel6.isSelected() && obj.getVersion() == 6) {
						return false;
					}

					// Live Object
					if (chkLiveObj.isSelected() && !obj.getLive()) {
						return false;
					}

					// Filter text
					if (txtFilter.getText().trim().length() > 0) {
						String lowerCaseFilter = txtFilter.getText().trim().toLowerCase();
						if (obj.getName().toLowerCase().contains(lowerCaseFilter)
								|| obj.getLocation().toLowerCase().contains(lowerCaseFilter)) {
							// The object contains the specified text so display
							// in the Table.
						} else {
							return false;
						}
					}
					return true;
				});

				sortedObjects = new SortedList<>(filteredObjects);
			}
		});

		chkLiveObj.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				filteredObjects.setPredicate(obj -> {

					// Release 5
					if (!chkRel5.isSelected() && obj.getVersion() == 5) {
						return false;
					}

					// Release 6
					if (!chkRel6.isSelected() && obj.getVersion() == 6) {
						return false;
					}

					// Live Object
					if (chkLiveObj.isSelected() && !obj.getLive()) {
						return false;
					}

					// Filter text
					if (txtFilter.getText().trim().length() > 0) {
						String lowerCaseFilter = txtFilter.getText().trim().toLowerCase();
						if (obj.getName().toLowerCase().contains(lowerCaseFilter)
								|| obj.getLocation().toLowerCase().contains(lowerCaseFilter)) {
							// The object contains the specified text so display
							// in the Table.
						} else {
							return false;
						}
					}
					return true;
				});

				sortedObjects = new SortedList<>(filteredObjects);
			}
		});

		tblPrimeCodeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ObjectModel>() {

			@Override
			public void changed(ObservableValue<? extends ObjectModel> observable, ObjectModel oldValue,
					ObjectModel newValue) {
				if (tblPrimeCodeList.getSelectionModel().getSelectedItem() != null) {
					selectedObject = tblPrimeCodeList.getSelectionModel().getSelectedItem();
					// System.out.println(selectedObject.getName());
				}

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
					// tblPrimeCodeList.setItems(objects);
					filteredObjects = new FilteredList<>(objects, p -> true);
					sortedObjects = new SortedList<>(filteredObjects);
					sortedObjects.comparatorProperty().bind(tblPrimeCodeList.comparatorProperty());
					tblPrimeCodeList.setItems(sortedObjects);
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

	@FXML
	public void editObject(Event event) {
		if (selectedObject != null) {
			txtName.setEditable(true);
			txtLocation.setEditable(true);
			rbtnRel5.setDisable(false);
			rbtnRel6.setDisable(false);
			chkLive.setDisable(false);
			txtName.requestFocus();
			btnUpdate.setDisable(false);
		}
	}
	
	@FXML
	public void deleteObject(Event event){
		int selectedIndex = tblPrimeCodeList.getSelectionModel().getSelectedIndex();
		if ( selectedIndex >= 0){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(tblPrimeCodeList.getParent().getScene().getWindow());
			alert.setTitle("Delete Object Confirmation");
			alert.setHeaderText( "This action will delete the object \n" + 
								 tblPrimeCodeList.getItems().get(selectedIndex).getLocation() + 
			                     tblPrimeCodeList.getItems().get(selectedIndex).getName() +
			                     "\n form the PrimeCode Object List.");
			alert.setContentText("Do you really want to delete the object?");
			Optional<ButtonType>result = alert.showAndWait();
			if ( result.get() == ButtonType.OK){
				try {
					ObservableList<ObjectModel> list = FXCollections.observableArrayList(tblPrimeCodeList.getItems());
					list.remove(tblPrimeCodeList.getItems().get(selectedIndex));
					sortedObjects = new SortedList<>(list);
					//tblPrimeCodeList.getItems().remove(selectedObject);
					//sortedObjects.remove(tblPrimeCodeList.getItems().get(selectedIndex));
				} catch (Exception e) {
					
				}
			}
		}
	}
}
