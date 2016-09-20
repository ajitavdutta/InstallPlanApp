package com.app.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleStringProperty;

@XmlRootElement(name="File")
public class FileList {
	private SimpleStringProperty name;
	private SimpleStringProperty prod;

	public FileList() {
		this.name = new SimpleStringProperty();
		this.prod = new SimpleStringProperty();
	}

	public FileList(String name, String prod) {
		this.name = new SimpleStringProperty(name);
		this.prod = new SimpleStringProperty(prod);
	}

	public String getName() {
		return name.get();
	}

	@XmlElement(name="Name")
	public void setName(String name) {
		this.name.set(name);
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public String getProd() {
		return prod.get();
	}

	@XmlElement(name="Product")
	public void setProd(String prod) {
		this.prod.set(prod);
	}

	public SimpleStringProperty prodProperty() {
		return prod;
	}
}
