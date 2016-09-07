package com.app.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.app.custom.controls.TreeViewWithItems;
import com.app.dao.AppDao;
import com.app.models.InstallPlan;
import com.app.models.ObjectList;
import com.app.models.TreeViewModel;
import com.app.util.AppUtility;
import com.app.util.RestrictiveTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class InstallConfigController implements Initializable {

	@FXML
	private RestrictiveTextField txtSDMNumber;

	@FXML
	private DatePicker dpInstallDate;

	@FXML
	private TextField txtInstallTime;

	@FXML
	private TextField txtSDMSummary;

	@FXML
	private TextField txtWorkRequest;

	@FXML
	private RestrictiveTextField txtPrimeCodeRef;

	final ToggleGroup toggleGroup = new ToggleGroup();

	@FXML
	private RadioButton rbMEL05;

	@FXML
	private RadioButton rbMEL10;

	@FXML
	private TextArea txtRollFiles;

	@FXML
	private TreeViewWithItems<TreeViewModel> RollFileTree;

	@FXML
	private TextArea txtObjects;

	@FXML
	private TreeViewWithItems<TreeViewModel> ObjectTree;

	@FXML
	private GridPane gridObjects;

	@FXML
	private GridPane gridRollFile;
	
	private InstallPlan plan;
	private Stage primaryStage;
	private Connection dbConnection;

	private TreeViewModel ObjProdGrp;
	private TreeViewModel ObjNonProdGrp;
	private TreeViewModel ObjProdR5;
	private TreeViewModel ObjProdR6;

	private TreeViewModel fileATM;
	private TreeViewModel filePOS;
	private TreeViewModel fileTPP;
	private TreeViewModel fileOthers;

	private ObservableList<TreeViewModel> objectList;
	private ObservableList<TreeViewModel> rollfileList;

	private ObservableList<ObjectList> objects;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtSDMNumber.setMaxLength(8);
		txtSDMNumber.setRestrict("[0-9]");
		
		txtPrimeCodeRef.setMaxLength(6);
		txtPrimeCodeRef.setRestrict("[0-9]");
		
		plan = new InstallPlan();
		ObjProdGrp = new TreeViewModel("PRODUCTION OBJECTS");
		ObjNonProdGrp = new TreeViewModel("NON-PRODUCTION OBJECTS");

		ObjProdR5 = new TreeViewModel("RELEASE 5");
		ObjProdR6 = new TreeViewModel("RELEASE 6");

		fileATM = new TreeViewModel("ATM");
		filePOS = new TreeViewModel("POS");
		fileTPP = new TreeViewModel("MY TELL");
		fileOthers = new TreeViewModel("OTHERS");

		ObjProdGrp.getChildren().addAll(ObjProdR5, ObjProdR6);

		objectList = FXCollections.observableArrayList(ObjProdGrp, ObjNonProdGrp);
		rollfileList = FXCollections.observableArrayList(fileATM, filePOS, fileTPP, fileOthers);
		objects = FXCollections.observableArrayList();

		rbMEL05.setUserData("\\MEL05");
		rbMEL10.setUserData("\\MEL10");

		rbMEL05.setToggleGroup(toggleGroup);
		rbMEL10.setToggleGroup(toggleGroup);

		plan.setProdSys(rbMEL05.getUserData().toString());
		plan.setDrSys(rbMEL10.getUserData().toString());

		setFieldValidators();
		setFieldBindings();

		ObjectTree = new TreeViewWithItems<TreeViewModel>(new TreeItem<TreeViewModel>());
		RollFileTree = new TreeViewWithItems<TreeViewModel>(new TreeItem<TreeViewModel>());

		gridObjects.add(ObjectTree, 0, 2);
		gridRollFile.add(RollFileTree, 0, 2);

		ObjectTree.setItems(objectList);
		ObjectTree.setShowRoot(false);

		RollFileTree.setItems(rollfileList);
		RollFileTree.setShowRoot(false);

		rbMEL05.setSelected(true);

		String pattern = "dd-MMM-yyyy";
		dpInstallDate.setPromptText(pattern.toLowerCase());
		dpInstallDate.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				}
				return null;
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				}
				return null;

			}
		});
	}

	/**
	 * This will set all the bindings to the InstallPlan Class Object.
	 */
	private void setFieldBindings() {
		txtSDMNumber.textProperty().bindBidirectional(plan.sdmNumberProperty());
		txtInstallTime.textProperty().bindBidirectional(plan.installTimeProperty());
		txtPrimeCodeRef.textProperty().bindBidirectional(plan.primeCodeRefProperty());
		txtSDMSummary.textProperty().bindBidirectional(plan.sdmSummaryProperty());
		txtWorkRequest.textProperty().bindBidirectional(plan.workRequestProperty());

		txtSDMNumber.textProperty().addListener((observable, oldValue, newValue) -> plan.setSdmNumber(newValue));
		txtInstallTime.textProperty().addListener((observable, oldValue, newValue) -> plan.setInstallTime(newValue));
		txtSDMSummary.textProperty().addListener((observable, oldValue, newValue) -> plan.setSdmSummary(newValue));
		txtWorkRequest.textProperty().addListener((observable, oldValue, newValue) -> plan.setWorkRequest(newValue));
		txtPrimeCodeRef.textProperty().addListener((observable, oldValue, newValue) -> plan.setPrimeCodeRef(newValue));

		plan.getObjList().addListener(new ListChangeListener<ObjectList>() {
			@Override
			public void onChanged(Change<? extends ObjectList> c) {
				System.out.println("InstallConfigController - Install Plan Object List changed.");
			}
		});

		objects.addListener(new ListChangeListener<ObjectList>() {

			@Override
			public void onChanged(Change<? extends ObjectList> c) {
				// TODO Auto-generated method stub
				loadObjectTree();
			}

		});

		toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				RadioButton rb = (RadioButton) newValue.getToggleGroup().getSelectedToggle();
				if (toggleGroup.getSelectedToggle() != null) {
					plan.setProdSys(rb.getUserData().toString());

					switch (rb.getUserData().toString()) {
					case "\\MEL05":
						plan.setDrSys("\\MEL10");
						break;

					case "\\MEL10":
						plan.setDrSys("\\MEL05");
						break;

					default:
						break;
					}
				}
			}
		});

		dpInstallDate.setOnAction(event -> {
			plan.setInstallDate(dpInstallDate.getValue().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
		});
	}

	/**
	 * This will add validators to different fields in the screen.
	 */
	private void setFieldValidators() {
		ValidationSupport validationSupport = new ValidationSupport();
		validationSupport.registerValidator(txtSDMNumber, Validator.createEmptyValidator("SDM Number is mandatory."));

		validationSupport.registerValidator(txtPrimeCodeRef,
				Validator.createEmptyValidator("PrimeCode User change is mandatory."));

		validationSupport.registerValidator(dpInstallDate,
				Validator.createEmptyValidator("Install Date is mandatory."));

		validationSupport.registerValidator(txtInstallTime,
				Validator.createEmptyValidator("Install time is mandatory."));

		validationSupport.registerValidator(txtSDMSummary, Validator.createEmptyValidator("SDM Summary is mandatory."));

		validationSupport.registerValidator(txtWorkRequest,
				Validator.createEmptyValidator("Work Request Summary is mandatory."));
	}

	public InstallPlan getPlan() {
		return plan;
	}

	public void setPlan(InstallPlan plan) {
		AppUtility.loadInstallPlan(this.plan, plan);
		// this.plan = plan;
	}

	public Connection getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public boolean isPresent(String value) {
		ObservableList<ObjectList> objList = plan.getObjList();
		String val = AppUtility.getSubVolFileName(value);

		for (ObjectList item : objList) {
			System.out.println(val + " == " + AppUtility.getSubVolFileName(item.getName()));
			if (AppUtility.getSubVolFileName(item.getName()).equalsIgnoreCase(val)) {
				return true;
			}
		}
		return false;
	}

	@FXML
	public void onAddObject(ActionEvent e) {
		String PCObjStmt = "SELECT * FROM APP.PRIMECODE_OBJECTS WHERE NAME='%s'";
		String objList = txtObjects.getText().trim().toUpperCase();
		List<String> objs = new ArrayList<>(Arrays.asList(objList.split("\\s*[\\r?\\n,]\\s*")));

		if (!objs.isEmpty()) {
			objs.forEach(obj -> {
				String name = AppUtility.getFileName(obj);
				try {
					if (name != null) {
						ResultSet rs = AppDao.executeStmt(dbConnection, String.format(PCObjStmt, name));
						while (rs.next()) {
							// System.out.println(obj + " " + rs.getString(4) +
							// " " + rs.getString(5));
							if (rs.getString(4).equalsIgnoreCase("Y") && !isPresent(obj)) {
								objects.add(new ObjectList(obj, rs.getString(4), rs.getString(5)));
							} else {
								objects.add(new ObjectList(obj, "N", ""));
							}
						}
					} else {
						objects.add(new ObjectList(obj, "N", ""));
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			});
		}

		txtObjects.clear();
	}

	@FXML
	public void onObjDelete(ActionEvent e){
		try {
			TreeViewModel item = ObjectTree.getSelectionModel().getSelectedItem().getValue();
			if(!(item.toString().equalsIgnoreCase("PRODUCTION OBJECTS") ||
					item.toString().equalsIgnoreCase("NON-PRODUCTION OBJECTS") ||
					item.toString().equalsIgnoreCase("RELEASE 5") ||
					item.toString().equalsIgnoreCase("RELEASE 6"))){
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText("This will remove the Object '" + item.toString() + "' from the object list.");
				alert.setContentText("Do you still want to delete it?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
				    TreeViewModel parent = ObjectTree.getSelectionModel().getSelectedItem().getParent().getValue();
				    parent.getChildren().remove(item);
				    alert = new Alert(AlertType.INFORMATION);
				    alert.setTitle("Information Dialog");
				    alert.setHeaderText("Delete Confirmation");
				    alert.setContentText("The object '" + item + "' is delete from the object list.");
				    alert.showAndWait();
				}
			}
		} catch (NullPointerException ex) {
		}
	}
	
	@FXML
	private void clearAllObjects(ActionEvent e){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("This will remove all Objects from the object list.");
		alert.setContentText("Do you want to proceed?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    ObjProdR5.getChildren().clear();
		    ObjProdR6.getChildren().clear();
		    ObjNonProdGrp.getChildren().clear();
		    alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("Information Dialog");
		    alert.setHeaderText("Delete Confirmation");
		    alert.setContentText("All objects are deleted from the object list.");
		    alert.showAndWait();
		}
	}

	private void loadObjectTree() {
		ObjProdR5.getChildren().clear();
		ObjProdR6.getChildren().clear();
		ObjNonProdGrp.getChildren().clear();

		objects.forEach(item -> {
			switch (item.getProd()) {
			case "Y":
				if (item.getRel() == "5") {
					ObjProdR5.getChildren().add(new TreeViewModel(item.getName()));
				} else {
					ObjProdR6.getChildren().add(new TreeViewModel(item.getName()));
				}
				break;

			default:
				ObjNonProdGrp.getChildren().add(new TreeViewModel(item.getName()));
				break;
			}
		});
	}

}
