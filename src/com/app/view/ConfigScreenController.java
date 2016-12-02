package com.app.view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.app.models.Session;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConfigScreenController implements Initializable {
	private Session session;
	private Stage stage;

	@FXML
	private TextField txtDevSys;

	@FXML
	private TextField txtProdSys;

	@FXML
	private TextField txtDRSys;

	@FXML
	private CheckBox chkATM;

	@FXML
	private CheckBox chkPOS;

	@FXML
	private CheckBox chkMyTell;

	@FXML
	private TextField txtProdSec;

	@FXML
	private TextField txtProdUser;

	@FXML
	private TextField txtPCDBLoc;

	@FXML
	private Button btnPCDBLoc;

	@FXML
	private TextField txtPDFTpltLoc;

	@FXML
	private Button btnPDFTpltLoc;

	@FXML
	private TitledPane tp1;

	@FXML
	private Accordion accord;

	@FXML
	private Button btnSave;

	public ConfigScreenController(Session session, Stage stage) {
		this.setSession(session);
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		accord.setExpandedPane(tp1);
		loadSessionData();

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				event.consume();
			}
		});
	}

	private void loadSessionData() {
		try {
			Properties prop = new Properties();
			InputStream input = new FileInputStream("config/app-config.properties");
			prop.load(input);
			input.close();

			txtDevSys.setText(prop.getProperty("DEV-SYS"));
			txtProdSys.setText(prop.getProperty("PROD-SYS"));
			txtDRSys.setText(prop.getProperty("DR-SYS"));
			ArrayList<String> products = new ArrayList<>(Arrays.asList(prop.getProperty("B24-PRODUCTS").split(",")));
			chkATM.setSelected(products.contains("ATM"));
			chkPOS.setSelected(products.contains("POS"));
			chkMyTell.setSelected(products.contains("MYTELL"));
			
			txtProdSec.setText(prop.getProperty("PROD-RWEP"));
			txtProdUser.setText(prop.getProperty("PROD-OBJ-USER"));
			txtPCDBLoc.setText(prop.getProperty("XML-PRIMECODE"));
			txtPDFTpltLoc.setText(prop.getProperty("TPLT-FILE"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	private boolean saveSettings(){
		try {
			Properties prop = new Properties();
			InputStream input = new FileInputStream("config/app-config.properties");
			prop.load(input);
			input.close();
			
			prop.setProperty("DEV-SYS", txtDevSys.getText().trim().toUpperCase());
			prop.setProperty("PROD-SYS", txtProdSys.getText().trim().toUpperCase());
			prop.setProperty("DR-SYS", txtDRSys.getText().trim().toUpperCase());
						
			ArrayList<String> prods = new ArrayList<>();
			if (chkATM.isSelected()) prods.add("ATM");
			if (chkPOS.isSelected()) prods.add("POS");
			if (chkMyTell.isSelected()) prods.add("MYTELL");
			
			prop.setProperty("B24-PRODUCTS", StringUtils.join(prods,","));
			prop.setProperty("PROD-RWEP", txtProdSec.getText().trim().toUpperCase());
			prop.setProperty("PROD-OBJ-USER", txtProdUser.getText().trim().toUpperCase());
			prop.setProperty("XML-PRIMECODE", txtPCDBLoc.getText().trim().toUpperCase());
			prop.setProperty("TPLT-FILE", txtPDFTpltLoc.getText().trim().toUpperCase());
						
			FileOutputStream output = new FileOutputStream("config/app-config.properties");
			prop.store(output, null);
			output.close();
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	private boolean isValidData() {
		boolean valid = true;
		String system = new String("(\\\\MEL\\d{2})$");
		String user = new String("((\\d{1,3})\\s?,\\s?(\\d{1,3}))");

		txtDevSys.setText(txtDevSys.getText().trim().toUpperCase());
		txtProdSys.setText(txtProdSys.getText().trim().toUpperCase());
		txtDRSys.setText(txtDRSys.getText().trim().toUpperCase());
		txtProdSec.setText(txtProdSec.getText().trim().toUpperCase());
		txtProdUser.setText(txtProdUser.getText().trim().toUpperCase());

		if (!Pattern.matches(system, txtDevSys.getText())) {

			txtDevSys.setStyle("-fx-text-inner-color: red;");
			valid = false;
		} else {
			txtDevSys.setStyle("-fx-text-inner-color: black;");
		}

		if (!Pattern.matches(system, txtProdSys.getText())) {
			txtProdSys.setStyle("-fx-text-inner-color: red;");
			valid = false;
		} else {
			txtProdSys.setStyle("-fx-text-inner-color: black;");
		}

		if (!Pattern.matches(system, txtDRSys.getText())) {
			txtDRSys.setStyle("-fx-text-inner-color: red;");
			valid = false;
		} else {
			txtDRSys.setStyle("-fx-text-inner-color: black;");
		}

		if (!chkATM.isSelected() && !chkPOS.isSelected() && !chkMyTell.isSelected()) {
			chkATM.setTextFill(Color.web("#da2e10"));
			chkPOS.setTextFill(Color.web("#da2e10"));
			chkMyTell.setTextFill(Color.web("#da2e10"));
			valid = false;
		} else {
			chkATM.setTextFill(Color.web("#000000"));
			chkPOS.setTextFill(Color.web("#000000"));
			chkMyTell.setTextFill(Color.web("#000000"));
		}

		if (!Pattern.matches(user, txtProdUser.getText())) {
			txtProdUser.setStyle("-fx-text-inner-color: red;");
			valid = false;
		} else {
			txtProdUser.setStyle("-fx-text-inner-color: black;");
		}

		if (txtDevSys.getText().equalsIgnoreCase(txtProdSys.getText())
				|| txtDevSys.getText().equalsIgnoreCase(txtDRSys.getText())) {
			txtDevSys.setStyle("-fx-text-inner-color: red;");
			valid = false;
		} else {
			txtDevSys.setStyle("-fx-text-inner-color: black;");
		}

		if (txtProdSys.getText().equalsIgnoreCase(txtDRSys.getText())) {
			txtProdSys.setStyle("-fx-text-inner-color: red;");
			txtDRSys.setStyle("-fx-text-inner-color: red;");
			valid = false;
		} else {
			txtProdSys.setStyle("-fx-text-inner-color: black;");
			txtDRSys.setStyle("-fx-text-inner-color: black;");
		}

		return valid;
	}

	@FXML
	private void saveConfig(ActionEvent event) {
		if (isValidData()) {
			saveSettings();
			stage.close();
		}
	}

	@FXML
	private void discardConfig(ActionEvent event) {
		stage.close();
	}

}
