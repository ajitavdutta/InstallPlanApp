package com.app.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.app.models.Session;
import com.app.models.TandemObject;
import com.app.util.AppUtility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ObjectAddViewController implements Initializable {

	private Session session;
	private File outFile;
	private BufferedWriter writer;

	@FXML
	private TextArea txtObjects;

	@FXML
	private Button btnCancel;

	public ObjectAddViewController(Session session) {
		this.session = session;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			outFile = new File(session.getFupCommandFile());
			outFile.getParentFile().mkdirs();
			if (!outFile.exists()) {
				outFile.createNewFile();
			}
			writer = new BufferedWriter(new FileWriter(outFile, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleAdd(ActionEvent event) {
		if (!txtObjects.getText().trim().isEmpty() || txtObjects.getText() != null) {
			String objectData = txtObjects.getText().trim();
			String[] objects = objectData.split(",|\\r?\\n");
			processObjects(objects);
		}
	}

	private void processObjects(String[] objects) {
		ArrayList<String> errorFiles = new ArrayList<>();
		ArrayList<String> fupCommands = new ArrayList<>();
		
		for (String obj : objects) {
			obj = obj.toUpperCase();
			if (AppUtility.isValidTandemFileName(obj) > 0) {
				TandemObject primeCodeObject = AppUtility.searchObject(session.getPrimeCodeObjects(),
						AppUtility.getFileName(obj),
						new Integer(AppUtility.getSubVolume(obj).substring(1, 2)).intValue());
				if (primeCodeObject != null) {
					// Object found in the PrimeCode list
					// Add the to the table.
					session.getInstallConfig().getInstallObjects().add(new TandemObject(AppUtility.getFileName(obj),
							primeCodeObject.getVersion(), primeCodeObject.getLocation(), primeCodeObject.getLive()));
					if (primeCodeObject.getLive()) {
						fupCommands.add("FUP INFO " + session.getDevSys().get(0) + "." + obj + ","
								+ session.getProdSys() + ".$DATA01." + AppUtility.getSubVolume(obj) + ".*, SOURCEDATE");
					}
				}

			} else {
				// TODO Need to identify what need to be done with the invalid
				// file name
				errorFiles.add(obj);
			}
		}
		
		if( !fupCommands.isEmpty()){
			try {
				for(String cmd : fupCommands){
					writer.write(cmd);
					writer.newLine();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!errorFiles.isEmpty()) {
			try {
				writer.newLine();
				writer.newLine();
				writer.write("**************** The following files are not present in the PrimeCode.");
				writer.newLine();
				writer.write("**************** Verify the Objects and update the PrimeCode Database.");
				writer.newLine();
				for( String file : errorFiles){
					writer.write(file);
					writer.newLine();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}

		closeWindow((Stage) btnCancel.getScene().getWindow());

	}

	private void closeWindow(Stage stage) {
		try {
			writer.flush();
			writer.close();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleCancel(ActionEvent event) {
		closeWindow((Stage) btnCancel.getScene().getWindow());
	}
}
