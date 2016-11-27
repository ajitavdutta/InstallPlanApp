package com.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.app.models.RollFile;
import com.app.models.Session;
import com.app.util.AppUtility;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class RollFileAddViewController implements Initializable {
	private Session session;
	private ObservableList<RollFile> rFiles;

	@FXML
	private TextArea txtFiles;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnAdd;

	public RollFileAddViewController(Session session) {
		this.session = session;

		// txtFiles.requestFocus();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rFiles = this.session.getInstallConfig().getRollFiles();

	}

	@FXML
	public void handleAdd(ActionEvent event) {
		if (!txtFiles.getText().trim().isEmpty() || txtFiles.getText() != null) {
			String fileString = txtFiles.getText().trim();
			String[] files = fileString.split(",|\\r?\\n");
			processFiles(files);
		}

		((Stage) btnCancel.getScene().getWindow()).close();
	}

	@FXML
	public void handleCancel(ActionEvent event) {
		((Stage) btnCancel.getScene().getWindow()).close();
	}

	private void processFiles(String[] files) {
		for (String file : files) {
			file = file.toUpperCase();
			if (AppUtility.isValidTandemFileName(file) > 0) {
				String subVol = AppUtility.getSubVolume(file);
				
				RollFile rf = new RollFile();
				
				switch (subVol.substring(1, 2)) {
				
				case "A": // ATM
					rf = new RollFile(file, 1);
					break;
					
				case "P": // POS
					rf = new RollFile(file, 2);
					break;

				case "T": // MYTELL
					rf = new RollFile(file, 5);
					break;

				default:
					break;
				}
				if (rf.getFile() != null || !rf.getFile().isEmpty()){
					rFiles.add(rf);
				}
			}
		}
	}

}
