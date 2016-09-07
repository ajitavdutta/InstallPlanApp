package com.app.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.app.models.ActivityModel;
import com.app.models.InstallPlan;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class StepDetailViewController implements Initializable {
	@FXML
	private TabPane tabActivities;

	@FXML
	private TextField txtStepNo;

	@FXML
	private TextField txtHeading;

	@FXML
	private TextField txtOwner;

	@FXML
	private TextField txtTime;

	@FXML
	private TextArea txtDescr;

	@FXML
	private AnchorPane mainPane;

	private InstallPlan plan;
	private ObservableList<ActivityModel> activities;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabActivities.getTabs().clear();
		addNewTab(tabActivities, "Activity", emptyActivityTab(), true);
		addNewTab(tabActivities, "+", getEmptyTabContent(), false);
		setBehaviourForPlusTabClick(tabActivities);
		setActivities(FXCollections.observableArrayList());
	}

	private Tab addNewTab(final TabPane tabPane, String newTabName, Node newTabContent, boolean isCloseable) {
		Tab newTab = new Tab(newTabName);
		newTab.setContent(newTabContent);
		newTab.setClosable(isCloseable);

		newTab.setOnClosed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (tabPane.getTabs().size() == 2) {
					event.consume();
				}
			}
		});

		tabPane.getTabs().add(newTab);
		return newTab;

	}

	private Node getEmptyTabContent() {
		return new AnchorPane();
	}

	private void setBehaviourForPlusTabClick(final TabPane tabPane) {
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
				if (newTab.getText().equals("+")) {
					Tab addedTab = addNewTab(tabPane, "Activity", emptyActivityTab(), true);
					tabPane.getTabs().remove(addedTab);
					tabPane.getTabs().set(tabPane.getTabs().size() - 1, addedTab);
					tabPane.getTabs().add(newTab);
					tabPane.getSelectionModel().select(addedTab);

				}
			}
		});
	}

	private Node emptyActivityTab() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ActivityScreen.fxml"));
			return (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public InstallPlan getPlan() {
		return plan;
	}

	public void setPlan(InstallPlan plan) {
		this.plan = plan;
	}

	public ObservableList<ActivityModel> getActivities() {
		return activities;
	}

	public void setActivities(ObservableList<ActivityModel> activities) {
		this.activities = activities;
	}

}
