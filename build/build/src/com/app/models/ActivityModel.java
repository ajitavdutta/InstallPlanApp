package com.app.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@XmlRootElement(name="Activity")
public class ActivityModel {
	private SimpleIntegerProperty slNo;
	private SimpleStringProperty activity;
	private SimpleStringProperty output;

	public ActivityModel(int slNo) {
		this.slNo = new SimpleIntegerProperty(slNo);
		activity = new SimpleStringProperty();
		output = new SimpleStringProperty();
	}
	
	public ActivityModel() {
		this.slNo = new SimpleIntegerProperty();
		activity = new SimpleStringProperty();
		output = new SimpleStringProperty();
	}

	public int getSlNo() {
		return slNo.get();
	}

	@XmlElement(name="SlNo")
	public void setSlNo(int slNo) {
		this.slNo.set(slNo);
	}

	public SimpleIntegerProperty slNoProperty() {
		return this.slNo;
	}

	public String getActivity() {
		return activity.get();
	}

	@XmlElement(name="Activity")
	public void setActivity(String activity) {
		this.activity.set(activity);
	}

	public SimpleStringProperty activityProperty() {
		return this.activity;
	}

	public String getOutput() {
		return activity.get();
	}

	@XmlElement(name="Output")
	public void setOutput(String output) {
		this.output.set(output);
	}

	public SimpleStringProperty outputProperty() {
		return this.output;
	}

}
