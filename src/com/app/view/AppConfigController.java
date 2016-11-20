package com.app.view;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.app.models.ApplicationInfo;
import com.app.models.InstallConfig;
import com.app.models.RollFile;
import com.app.models.Session;
import com.app.models.TandemObject;
import com.app.util.AppUtility;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AppConfigController implements Initializable {

	@FXML
	private TextField txtNum;

	@FXML
	private DatePicker installDate;

	@FXML
	private ComboBox<String> cmbHour;

	@FXML
	private ComboBox<String> cmbMin;

	@FXML
	private Label lblIST;

	@FXML
	private TextField txtDescr;

	@FXML
	private TextField txtWR;

	@FXML
	private TextField txtPrimeCode;

	@FXML
	private TextField txtProd;

	@FXML
	private TextField txtDR;

	@FXML
	private TextField txtDev;
	/*
	 * FXML variables for Install Objects
	 */
	@FXML
	private TextField txtObjSearch;

	@FXML
	private TableView<TandemObject> tblInstallObjects;

	@FXML
	private TableColumn<TandemObject, String> colObjName;

	@FXML
	private TableColumn<TandemObject, Integer> colObjVersion;

	@FXML
	private TableColumn<TandemObject, String> colObjLive;

	@FXML
	private TableColumn<TandemObject, String> colLocation;

	@FXML
	private Button btnAddObj;

	@FXML
	private Button btnEditObj;

	@FXML
	private Button btnDelObj;

	@FXML
	private Button btnObjClear;
	/*
	 * FXML variables for RollFiles
	 */
	@FXML
	private TextField txtRollFileSearch;

	@FXML
	private TableView<RollFile> tblRollFiles;

	@FXML
	private TableColumn<RollFile, String> colRollFileName;

	@FXML
	private TableColumn<RollFile, Integer> colRollFileProd;

	@FXML
	private Button btnRollFileAdd;

	@FXML
	private Button btnRollFileEdit;

	@FXML
	private Button btnRollFileDel;

	@FXML
	private Button btnRollFileClear;

	@FXML
	private Button btnCancel;

	private Session session;
	private static final String DATE_TIME_FORMAT = "dd-MMM-yyyy HH:mm";
	// private static final String TIME_FORMAT = "HH:mm";
	
	private ObservableList<TandemObject> objects;
	private ObservableList<RollFile> rollFiles;

	private SortedList<TandemObject> sortedObjects;
	private SortedList<RollFile> sortedRollFiles;

	private FilteredList<TandemObject> filteredObjects;
	private FilteredList<RollFile> filteredRollFiles;
	
	private ApplicationInfo appInfo;
	private InstallConfig installConfig;

	public AppConfigController(Session session) {
		this.session = session;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtProd.setEditable(false);
		txtDR.setEditable(false);
		btnEditObj.setDisable(true);
		btnDelObj.setDisable(true);
		btnRollFileDel.setDisable(true);
		btnRollFileEdit.setDisable(true);
		
		populateFromSession();
		screenDateTimeInit();
		installTableInit();
		rollFileTableInit();
	}

	/**
	 * This will initialize the Install Objects table with the default values.
	 * Add search filter and required listeners to the various fields related to
	 * install objects.
	 * 
	 */
	private void installTableInit() {
		try {
			colObjName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
			colObjLive.setCellValueFactory(cellData -> cellData.getValue().liveProperty().asString());
			colObjVersion.setCellValueFactory(cellData -> cellData.getValue().versionProperty().asObject());
			colLocation.setCellValueFactory(cellData -> cellData.getValue().locationProperty());

			//objects = AppUtility.loadPrimeCodeDB(session.getAppInfo().getPCXmlFile());
			objects = installConfig.getObjects();
			filteredObjects = new FilteredList<>(objects, p -> true);

			// Install Object Text filters
			txtObjSearch.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredObjects.setPredicate(object -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					} else {
						String lowerCaseFilter = newValue.toLowerCase();
						if (object.getName().toLowerCase().contains(lowerCaseFilter)) {
							return true;
						}
						return false;
					}
				});
			});

			sortedObjects = new SortedList<>(filteredObjects);
			sortedObjects.comparatorProperty().bind(tblInstallObjects.comparatorProperty());
			tblInstallObjects.setItems(sortedObjects);

			tblInstallObjects.getItems().clear();
			tblInstallObjects.setItems(sortedObjects);

			// Detect double click on the InstallObject Table
			tblInstallObjects.setRowFactory(obj -> {
				TableRow<TandemObject> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						TandemObject rowObj = row.getItem();
						showObjectEditWindow(rowObj);
					}
				});
				return row;
			});

			tblInstallObjects.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
				if (newValue != null) {
					btnEditObj.setDisable(false);
					btnDelObj.setDisable(false);
				} else {
					btnEditObj.setDisable(true);
					btnDelObj.setDisable(true);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	/**
	 * 
	 * This will initialize all the Date and time related Fields with default
	 * values and will also set the required listeners to the fields to detect
	 * value changes.
	 * 
	 */
	private void screenDateTimeInit() {
		// ChangeListeners for Date and Time
		// DatePicker ChangeListener
		installDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				try {
					int hh = new Integer(cmbHour.getValue()).intValue();
					int mm = new Integer(cmbMin.getValue()).intValue();
					LocalTime lt = LocalTime.of(hh, mm);
					LocalDateTime ldt = LocalDateTime.of(newValue, lt);
					lblIST.setText(AppUtility.convertASTtoIST(ldt).format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
							+ "  ( IST )");
				} catch (Exception e) {
					// Set the default value for date and time
					installDate.setValue(LocalDate.now());
					lblIST.setText(AppUtility.convertASTtoIST(LocalDateTime.now())
							.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)) + "  ( IST )");
				}
			}
		});

		// Hour ComboBox ChangeListener
		cmbHour.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					LocalDate ld = installDate.getValue();
					int hh = new Integer(newValue).intValue();
					int mm = new Integer(cmbMin.getValue()).intValue();
					LocalTime lt = LocalTime.of(hh, mm);
					LocalDateTime ldt = LocalDateTime.of(ld, lt);
					lblIST.setText(AppUtility.convertASTtoIST(ldt).format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
							+ "  ( IST )");
				} catch (Exception e) {
					// Set the default value for date and time
					installDate.setValue(LocalDate.now());
					lblIST.setText(AppUtility.convertASTtoIST(LocalDateTime.now())
							.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)) + "  ( IST )");
				}
			}
		});

		// Minute ComboBox ChangeListener
		cmbMin.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					LocalDate ld = installDate.getValue();
					int mm = new Integer(newValue).intValue();
					int hh = new Integer(cmbHour.getValue()).intValue();
					LocalTime lt = LocalTime.of(hh, mm);
					LocalDateTime ldt = LocalDateTime.of(ld, lt);
					lblIST.setText(AppUtility.convertASTtoIST(ldt).format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
							+ "  ( IST )");
				} catch (Exception e) {
					// Set the default value for date and time
					installDate.setValue(LocalDate.now());
					lblIST.setText(AppUtility.convertASTtoIST(LocalDateTime.now())
							.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)) + "  ( IST )");
				}
			}
		});

		// Populate the Hours and Min's in the Hour and Min comboBoxes
		String idx = null;
		int i;
		cmbHour.getItems().clear();
		cmbMin.getItems().clear();

		for (i = 0; i < 24; i++) {
			idx = Integer.toString(i);
			if (idx.length() == 1) {
				idx = "0" + idx;
			}
			cmbHour.getItems().add(idx);
		}

		cmbMin.getItems().clear();
		for (i = 0; i < 60; i++) {
			idx = Integer.toString(i);
			if (idx.length() == 1) {
				idx = "0" + idx;
			}
			cmbMin.getItems().add(idx);
		}

		cmbHour.getSelectionModel().select(2);
		cmbMin.getSelectionModel().select(0);
	}

	/**
	 * 
	 * This will populate the data from the session to the various fields in the
	 * screen.
	 * 
	 * Note - This should be the 1st procedure to call in initialize procedure.
	 */
	private void populateFromSession() {
		appInfo = session.getAppInfo();
		installConfig = session.getInstallConfig();
		
		txtProd.setText(installConfig.getProdSys());
		txtDR.setText(installConfig.getDrSys());
		txtDev.setText(installConfig.getDevSys());
	}

	/**
	 * 
	 */
	private void rollFileTableInit() {
		colRollFileName.setCellValueFactory(cellData -> cellData.getValue().fileProperty());
		colRollFileProd.setCellValueFactory(cellData -> cellData.getValue().prodProperty().asObject());

		rollFiles = session.getInstallConfig().getRollFiles();
		filteredRollFiles = new FilteredList<>(rollFiles, p -> true);
		txtRollFileSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredObjects.setPredicate(file -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				} else {
					String lowerCaseFilter = newValue.toLowerCase();
					if (file.getName().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
				}
				return false;
			});
		});

		sortedRollFiles = new SortedList<>(filteredRollFiles);
		sortedRollFiles.comparatorProperty().bind(tblRollFiles.comparatorProperty());
		tblRollFiles.getItems().clear();
		tblRollFiles.setItems(sortedRollFiles);

		// Dectect double click on RollFile Table
		tblRollFiles.setRowFactory(rollFile -> {
			TableRow<RollFile> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					//RollFile rowObj = row.getItem();
					// showObjectEditWindow(rowObj);
				}
			});
			return row;
		});

		tblRollFiles.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				btnRollFileEdit.setDisable(false);
				btnRollFileDel.setDisable(false);
			} else {
				btnRollFileEdit.setDisable(true);
				btnRollFileDel.setDisable(true);
			}
		});
	}

	private void showObjectEditWindow(TandemObject selObject) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ObjectEditView.fxml"));
			loader.setController(new ObjectEditViewController(session, selObject));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle("Edit Object");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(appInfo.getParentStage());
			stage.getIcons().add(new Image("file:resources/images/edit.png"));
			stage.setResizable(false);
			stage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//private void showRollFileEditWindow(RollFile rFile) {
		// try {
		// Stage stage = new Stage();
		// FXMLLoader loader = new
		// FXMLLoader(getClass().getResource("ObjectEditView.fxml"));
		// loader.setController(new RollFileAddViewController(session, rFile));
		// Parent root = loader.load();
		// stage.setScene(new Scene(root));
		// stage.setTitle("Edit Object");
		// stage.initModality(Modality.APPLICATION_MODAL);
		// stage.initOwner(session.getPrimaryStage());
		// stage.getIcons().add(new Image("file:resources/images/edit.png"));
		// stage.setResizable(false);
		// stage.showAndWait();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	//}

	@FXML
	public void handelAdd(ActionEvent event) {
		if (event.getSource() == btnAddObj) {
			try {
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("ObjectAddView.fxml"));
				loader.setController(new ObjectAddViewController(session));
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.setTitle("Add New Objects");
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.initOwner(appInfo.getParentStage());
				stage.getIcons().add(new Image("file:resources/images/add.png"));
				stage.setResizable(false);
				stage.showAndWait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (event.getSource() == btnRollFileAdd) {
			try {
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("RollFileAddView.fxml"));
				loader.setController(new RollFileAddViewController(session));
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				stage.setTitle("Add New RollFiles");
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.initOwner(appInfo.getParentStage());
				stage.getIcons().add(new Image("file:resources/images/add.png"));
				stage.setResizable(false);
				stage.showAndWait();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void handelDel(ActionEvent event) {
		if (event.getSource() == btnDelObj) {
			TandemObject tandemObject = tblInstallObjects.getSelectionModel().getSelectedItem();
			objects.remove(tandemObject);
			// filteredObjects.remove(tandemObject);
			// sortedObjects.remove(tandemObject);
		}
	}

	@FXML
	public void handelEdit(ActionEvent event) {
		if (event.getSource() == btnEditObj) {
			showObjectEditWindow(tblInstallObjects.getSelectionModel().getSelectedItem());
		} else if (event.getSource() == btnRollFileEdit) {
			try {

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@FXML
	public void handelCancel(ActionEvent event) {
		if (event.getSource() == btnCancel) {
			Stage stage = (Stage) btnCancel.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	public void handleClear(ActionEvent event) {
		if (event.getSource() == btnObjClear) {
			objects.clear();
			tblInstallObjects.getItems().clear();

		} else if (event.getSource() == btnRollFileClear) {
			rollFiles.clear();
			sortedRollFiles.clear();
			tblRollFiles.getItems().clear();
		}
	}

}
