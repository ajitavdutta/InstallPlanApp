package com.app.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlRootElement(name = "Step")
public class StepModel {
	private SimpleIntegerProperty sl_no;
	private SimpleStringProperty product;
	private SimpleStringProperty name;
	private SimpleIntegerProperty time_to_complete;
	private SimpleIntegerProperty start_time;
	private SimpleStringProperty date;
	private SimpleStringProperty time;
	private SimpleStringProperty owner;
	private SimpleStringProperty description;
	private ObservableList<ActivityModel> activities;

	public StepModel() {
		super();
		sl_no = new SimpleIntegerProperty();
		product = new SimpleStringProperty();
		name = new SimpleStringProperty();
		time_to_complete = new SimpleIntegerProperty();
		start_time = new SimpleIntegerProperty();
		date = new SimpleStringProperty();
		time = new SimpleStringProperty();
		owner = new SimpleStringProperty();
		description = new SimpleStringProperty();
		activities = FXCollections.observableArrayList();
	}

	public int getSl_no() {
		return sl_no.get();
	}

	@XmlElement(name = "SlNo")
	public void setSl_no(int sl_no) {
		this.sl_no.set(sl_no);
	}
	
	public SimpleIntegerProperty slNoProperty(){
		return sl_no;
	}

	public String getProduct() {
		return product.get();
	}

	@XmlElement(name = "Product")
	public void setProduct(String product) {
		this.product.set(product);
	}
	
	public SimpleStringProperty productProperty(){
		return product;
	}

	public String getName() {
		return name.get();
	}

	@XmlElement(name = "Name")
	public void setName(String name) {
		this.name.set(name);
	}
	
	public SimpleStringProperty nameProperty(){
		return name;
	}

	public int getTime_to_complete() {
		return time_to_complete.get();
	}

	@XmlElement(name = "TimeToComplete")
	public void setTime_to_complete(int time_to_complete) {
		this.time_to_complete.set(time_to_complete);
	}
	
	public SimpleIntegerProperty time_to_completeProperty(){
		return time_to_complete;
	}

	public int getStart_time() {
		return start_time.get();
	}

	@XmlElement(name = "StartTime")
	public void setStart_time(int start_time) {
		this.start_time.set(start_time);
	}
	
	public SimpleIntegerProperty start_timeProperty(){
		return start_time;
	}

	public String getDate() {
		return date.get();
	}

	@XmlElement(name = "Date")
	public void setDate(String date) {
		this.date.set(date);
	}
	
	public SimpleStringProperty dateProperty(){
		return date;
	}

	public String getTime() {
		return time.get();
	}

	@XmlElement(name = "Time")
	public void setTime(String time) {
		this.time.set(time);
	}
	
	public SimpleStringProperty timeProperty(){
		return time;
	}
	
	public String getOwner() {
		return description.get();
	}

	@XmlElement(name = "Owner")
	public void setOwner(String owner) {
		this.owner.set(owner);
	}
	
	public SimpleStringProperty ownerProperty(){
		return owner;
	}

	public String getDescription() {
		return description.get();
	}

	@XmlElement(name = "Description")
	public void setDescription(String description) {
		this.description.set(description);
	}
	
	public SimpleStringProperty descriptionProperty(){
		return description;
	}

	public ObservableList<ActivityModel> getActivities() {
		return activities;
	}

	@XmlElementWrapper(name = "Activity-List")
	@XmlElement(name = "Activity")
	public void setActivities(ObservableList<ActivityModel> activities) {
		this.activities = activities;
	}

}
