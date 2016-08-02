package com.app.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.app.dao.AppDao;
import com.app.models.InstallPlan;
import com.app.models.ObjectList;
import com.app.util.AppUtility;
import com.app.util.print.InstallPlanHTMLView;
import com.app.util.print.InstallPlanHTMLView.DeviceType;
import com.app.util.print.PDFUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainScreenController implements Initializable {

	@FXML
	private Tab tabConfig;

	@FXML
	private Tab tabPreInstall;

	@FXML
	private Tab tabInstall;

	@FXML
	private Tab tabVerify;

	@FXML
	private Tab tabBackout;

	@FXML
	private TextArea txtFupCmds;

	private Stage primaryStage;
	private InstallPlan plan;

	private File xmlFile;
	private String htmlFileName;
	File pdfFile;
	private String devSys;

	private Connection dbConnection;

	InstallConfigController ICController;
	InstallActivityController preInstallController;
	InstallActivityController postInstallController;
	InstallActivityController backoutController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.dbConnection = AppDao.getDBConnection();
		devSys = AppUtility.getProperty("DEV-SYS");
		FXMLLoader loader = new FXMLLoader();

		plan = new InstallPlan();
		/*
		 * Add listener to specific fields in the Install Plan that affects
		 * other components in the application.
		 */
		plan.primeCodeRefProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				/*
				 * Update the FUP commands if the Objects List is not empty.
				 */
				if (!plan.getObjList().isEmpty()) {
					setFUPComands();
				}
			}
		});

		plan.getObjList().addListener(new ListChangeListener<ObjectList>() {
			@Override
			public void onChanged(Change<? extends ObjectList> c) {
				/*
				 * Update the FUP commands if the SDM Number is set.
				 */
				if ((!plan.getSdmNumber().isEmpty()) || (plan.getSdmNumber() != null)) {
					setFUPComands();
				}
			}
		});

		plan.prodSysProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				/*
				 * if the SDM Number is set and there are objects present in the
				 * Object List, update the FUP commands.
				 */
				if ((!plan.getObjList().isEmpty())
						&& ((!plan.getSdmNumber().isEmpty()) || (plan.getSdmNumber() != null))) {
					setFUPComands();
				}
			}
		});

		AnchorPane pane;

		// Install Config Screen
		try {
			loader.setLocation(getClass().getResource("InstallConfig.fxml"));
			pane = (AnchorPane) loader.load();
			tabConfig.setContent(pane);
			ICController = loader.getController();
			ICController.setPlan(plan);
			ICController.setDbConnection(dbConnection);
			ICController.setPrimaryStage(primaryStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*// Pre-Install Screen
		try {
			loader.setLocation(getClass().getResource("view/InstallActivity.fxml"));
			pane = (AnchorPane) loader.load();
			tabPreInstall.setContent(pane);
			preInstallController = loader.getController();
			preInstallController.setPlan(plan);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	public InstallPlan getInstallPlan() {
		return plan;
	}

	public void setInstallPlan(InstallPlan installPlan) {
		this.plan = installPlan;
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
	public void onSave(ActionEvent event) {
		if (!validateData()) {
			Alert dialog = new Alert(AlertType.ERROR);
			dialog.setHeaderText("Data Validation Failed!!!");
			dialog.setContentText("Please ensure that all the mandatory fields are populated correctly.");
			dialog.show();
		} else {

			if (xmlFile == null) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Install Plan as XML");
				fileChooser.getExtensionFilters().add(new ExtensionFilter("XML", "*.xml"));
				xmlFile = fileChooser.showSaveDialog(primaryStage);
			}

			try {
				if (xmlFile != null) {
					if (!xmlFile.exists()) {
						xmlFile.createNewFile();
					}
					JAXBContext jabxContext = JAXBContext.newInstance(InstallPlan.class);
					Marshaller marshaller = jabxContext.createMarshaller();

					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					marshaller.marshal(plan, xmlFile);
					marshaller.marshal(plan, System.out);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@FXML
	public void onSaveAs(ActionEvent event) {
		if (!validateData()) {
			Alert dialog = new Alert(AlertType.ERROR);
			dialog.setHeaderText("Data Validation Failed!!!");
			dialog.setContentText("Please ensure that all the mandatory fields are populated correctly.");
			dialog.show();
		} else {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Install Plan as XML");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("XML", "*.xml"));
			xmlFile = fileChooser.showSaveDialog(primaryStage);

			try {
				if (xmlFile != null) {
					if (!xmlFile.exists()) {
						xmlFile.createNewFile();
					}
					JAXBContext jabxContext = JAXBContext.newInstance(InstallPlan.class);
					Marshaller marshaller = jabxContext.createMarshaller();

					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					marshaller.marshal(plan, xmlFile);
					marshaller.marshal(plan, System.out);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	public void onPrint(ActionEvent event) {
		if (validateData()) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Install Plan as PDF");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("PDF", "*.pdf"));
			pdfFile = fileChooser.showSaveDialog(primaryStage);
			try {
				if (pdfFile != null) {
					htmlFileName = pdfFile.getParent() + "\\html" + "\\"
							+ pdfFile.getName().substring(0, pdfFile.getName().indexOf(".")) + ".HTML";

					InstallPlanHTMLView htmlView = new InstallPlanHTMLView(DeviceType.BROWSER);
					String htmlContent = htmlView.toHtmlString();
					htmlView.createHTMLFile(htmlFileName);
					updateHtmlTemplate(htmlContent, new File(htmlFileName));

					File html = new File(htmlFileName);
					if (html.exists()) {
						PDFUtil pdfUtil = new PDFUtil();
						pdfUtil.CreateTemplatePDF();
						pdfUtil.createPDFInstallPlan(pdfFile, htmlFileName, plan.getSdmNumber(), plan.getSdmSummary());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void updateHtmlTemplate(String htmlContent, File file) {
		if (file.exists()) {
			file.delete();

			htmlContent = htmlContent.replaceAll("\\$sdm-number", plan.getSdmNumber());
			htmlContent = htmlContent.replaceAll("\\$primecode-userchange", plan.getPrimeCodeRef());

			htmlContent = htmlContent.replaceAll("\\$install-date", plan.getInstallDate());
			htmlContent = htmlContent.replaceAll("\\$install-time", plan.getInstallTime());
			htmlContent = htmlContent.replaceAll("\\$prod-sys", plan.getProdSys());
			htmlContent = htmlContent.replaceAll("\\$dr-sys", plan.getDrSys());

			htmlContent = htmlContent.replaceAll("\\$sdm-summary", plan.getSdmSummary());
			htmlContent = htmlContent.replaceAll("\\$work-request", plan.getWorkRequest());

			FileWriter fWriter = null;
			BufferedWriter writer = null;
			try {
				fWriter = new FileWriter(file);
				writer = new BufferedWriter(fWriter);
				writer.write(htmlContent);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean validateData() {

		if (plan.getSdmNumber() == null || plan.getSdmNumber().trim().length() == 0) {
			return false;
		}

		if (plan.getInstallDate() == null) {
			return false;
		}

		if (plan.getInstallTime() == null || plan.getInstallTime().trim().length() == 0) {
			return false;
		} else {
			Pattern pattern = Pattern.compile("([0-9]{2}):([0-9]{2})");
			Matcher m = pattern.matcher(plan.getInstallTime());
			if (!m.matches()) {
				return false;
			}
		}

		if (plan.getSdmSummary() == null || plan.getSdmSummary().trim().length() == 0) {
			return false;
		}

		if (plan.getWorkRequest() == null || plan.getWorkRequest().trim().length() == 0) {
			return false;
		}

		if (plan.getPrimeCodeRef() == null || plan.getPrimeCodeRef().trim().length() == 0) {
			return false;
		}

		return true;
	}

	@FXML
	public void onFileOpen(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Install Plan");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("XML", "*.xml"));

		try {
			xmlFile = fileChooser.showOpenDialog(primaryStage);
			if (xmlFile != null) {
				JAXBContext jaxbContext = JAXBContext.newInstance(InstallPlan.class);
				Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
				InstallPlan newplan = (InstallPlan) unMarshaller.unmarshal(xmlFile);
				AppUtility.loadInstallPlan(plan, newplan);
				ICController.setPlan(plan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFUPComands() {
		txtFupCmds.clear();
		txtFupCmds.setText(AppUtility.createFUPCommands(plan, devSys));
	}

}
