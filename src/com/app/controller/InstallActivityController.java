package com.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.app.models.InstallPlan;
import com.app.models.StepModel;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class InstallActivityController implements Initializable{
	@FXML
	private TableView<StepModel> tblSteps;
	
	@FXML
	private TableColumn<StepModel, Number> colStepNo;
	
	@FXML
	private TableColumn<StepModel, String> colDate;
	
	@FXML
	private TableColumn<StepModel, String> colTime;
	
	@FXML
	private TableColumn<StepModel, String> colOwner;
	
	@FXML
	private TableColumn<StepModel, String> colDesc;
	
	@FXML
	private TitledPane tPaneStepDetail;
	
	@FXML
	private TextField txtStepNo;
	
	
	private InstallPlan plan;
	private ObservableList<StepModel>steps;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*colStepNo.setCellValueFactory(cellData -> cellData.getValue().slNoProperty());
		colDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		colTime.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
		colOwner.setCellValueFactory(cellData -> cellData.getValue().ownerProperty());
		colDesc.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		tPaneStepDetail.setDisable(true);*/
	}
	
	@FXML
	public void addStep(ActionEvent event){
		tPaneStepDetail.setDisable(false);
		txtStepNo.requestFocus();
	}
	
	public void init(){
		steps = plan.getPriorToInstall();
		tblSteps.setItems(steps);
	}

	public InstallPlan getPlan() {
		return plan;
	}

	public void setPlan(InstallPlan plan) {
		this.plan = plan; 
	}

	public ObservableList<StepModel> getSteps() {
		return steps;
	}

	public void setSteps(ObservableList<StepModel> steps) {
		this.steps = steps;
	}

}
