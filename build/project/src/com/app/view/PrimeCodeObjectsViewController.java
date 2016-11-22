package com.app.view;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.app.models.ApplicationInfo;
import com.app.models.Session;
import com.app.models.TandemObject;
import com.app.util.AppUtility;

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
	private TableView<TandemObject> tblPrimeCodeList;

	@FXML
	private TableColumn<TandemObject, String> colName;

	@FXML
	private TableColumn<TandemObject, String> colLocation;

	@FXML
	private TableColumn<TandemObject, Integer> colVersion;

	@FXML
	private TableColumn<TandemObject, String> colLive;

	@FXML
	private CheckBox chkRel5;

	@FXML
	private CheckBox chkRel6;

	@FXML
	private CheckBox chkLiveObj;

	private ToggleGroup rbBtnGrp;

	private ObservableList<TandemObject> objects;
	private SortedList<TandemObject> sortedObjects;
	private FilteredList<TandemObject> filteredObjects;
	private TandemObject selectedObject = null;
	private Session session;
	private ApplicationInfo appInfo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		objects = FXCollections.observableArrayList();

		colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		colLocation.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
		colVersion.setCellValueFactory(cellData -> cellData.getValue().versionProperty().asObject());
		colLive.setCellValueFactory(cellData -> cellData.getValue().liveProperty().asString());

		txtName.textProperty().addListener((observable, oldValue, newValue) -> {
			if (selectedObject != null) {
				updateButtonStatus();
			}
		});

		txtLocation.textProperty().addListener((observable, oldValue, newValue) -> {
			if (selectedObject != null) {
				updateButtonStatus();
			}
		});

		rbtnRel5.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (selectedObject != null) {
				updateButtonStatus();
			}
		});

		rbtnRel6.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (selectedObject != null) {
				updateButtonStatus();
			}
		});

		chkLive.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (selectedObject != null) {
				updateButtonStatus();
			}
		});

		txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
			filterTable();
		});

		chkRel5.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				filterTable();
				sortedObjects = new SortedList<>(filteredObjects);
			}
		});

		chkRel6.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				filterTable();
				sortedObjects = new SortedList<>(filteredObjects);
			}
		});

		chkLiveObj.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				filterTable();
				sortedObjects = new SortedList<>(filteredObjects);
			}
		});

		tblPrimeCodeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TandemObject>() {

			@Override
			public void changed(ObservableValue<? extends TandemObject> observable, TandemObject oldValue,
					TandemObject newValue) {
				if (tblPrimeCodeList.getSelectionModel().getSelectedItem() != null) {
					if (!btnUpdate.isDisabled()) {
						// TODO
						saveObjectUpdate(true);
					}

					selectedObject = tblPrimeCodeList.getSelectionModel().getSelectedItem();
					showObjectDetail(newValue);
				}
			}
		});

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
		// loadData();
	}

	/**
	 * Save the changes made to the object details to the SortedList.
	 * 
	 * @param reqConf
	 *            - Required confirmation flag.
	 */
	private void saveObjectUpdate(boolean reqConf) {
		if (reqConf) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			// alert.initOwner(tblPrimeCodeList.getParent().getScene().getWindow());
			alert.initOwner(appInfo.getParentStage());
			alert.setTitle("Update Confirmation");
			alert.setHeaderText("The Object details have been modified.");
			alert.setContentText("Do you want to save the changes made to the Object?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.CANCEL) {
				btnAdd.setDisable(true);
				btnUpdate.setDisable(true);
				txtName.setEditable(false);
				txtLocation.setEditable(false);
				rbtnRel5.setDisable(true);
				rbtnRel6.setDisable(true);
				chkLive.setDisable(true);
				return;
			}
		}

		selectedObject.setName(txtName.getText());
		selectedObject.setLocation(txtLocation.getText());
		selectedObject.setLive(chkLive.isSelected());
		int version = 0;
		if (rbtnRel5.isSelected()) {
			version = 5;
		} else {
			version = 6;
		}
		selectedObject.setVersion(version);
		btnAdd.setDisable(true);
		btnUpdate.setDisable(true);
		txtName.setEditable(false);
		txtLocation.setEditable(false);
		rbtnRel5.setDisable(true);
		rbtnRel6.setDisable(true);
		chkLive.setDisable(true);
	}

	private void filterTable() {
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
	}

	private void updateButtonStatus() {
		boolean disabled = true;
		if (!selectedObject.getName().equalsIgnoreCase(txtName.getText())) {
			disabled = false;
		}

		if (!txtLocation.getText().equalsIgnoreCase(selectedObject.getLocation())) {
			disabled = false;
		}

		if ((selectedObject.getLive() && !chkLive.isSelected())
				|| (!selectedObject.getLive() && chkLive.isSelected())) {
			disabled = false;
		}

		if ((selectedObject.getVersion() == 5 && rbtnRel6.isSelected())
				|| (selectedObject.getVersion() == 6 && rbtnRel5.isSelected())) {
			disabled = false;
		}

		btnUpdate.setDisable(disabled);
	}

	public void showObjectDetail(TandemObject data) {
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
			objects = AppUtility.loadPrimeCodeDB(appInfo.getPCXmlFile());
			if (objects != null) {
				filteredObjects = new FilteredList<>(objects, p -> true);
				sortedObjects = new SortedList<>(filteredObjects);
				sortedObjects.comparatorProperty().bind(tblPrimeCodeList.comparatorProperty());
				tblPrimeCodeList.setItems(sortedObjects);
			}

		} catch (Exception e) {
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
			// btnUpdate.setDisable(false);
		}
	}

	@FXML
	private void handleUpdate(Event event) {
		saveObjectUpdate(false);
	}

	@FXML
	public void deleteObject(Event event) {
		int selectedIndex = tblPrimeCodeList.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(tblPrimeCodeList.getParent().getScene().getWindow());
			alert.setTitle("Delete Object Confirmation");
			alert.setHeaderText("This action will delete the object \n"
					+ tblPrimeCodeList.getItems().get(selectedIndex).getLocation()
					+ tblPrimeCodeList.getItems().get(selectedIndex).getName() + "\n form the PrimeCode Object List.");
			alert.setContentText("Do you really want to delete the object?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					ObservableList<TandemObject> list = FXCollections.observableArrayList(tblPrimeCodeList.getItems());
					list.remove(tblPrimeCodeList.getItems().get(selectedIndex));
					sortedObjects = new SortedList<>(list);
					tblPrimeCodeList.getItems().clear();
					tblPrimeCodeList.setItems(sortedObjects);
					// tblPrimeCodeList.getItems().remove(selectedObject);
					// sortedObjects.remove(tblPrimeCodeList.getItems().get(selectedIndex));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Session getSession_data() {
		return session;
	}

	public void setSession_data(Session session) {
		this.session = session;
	}
}
