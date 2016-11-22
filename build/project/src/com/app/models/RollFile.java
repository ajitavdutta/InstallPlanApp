package com.app.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RollFile {
	private SimpleStringProperty file;
	private SimpleIntegerProperty prod;
	
	public RollFile(){
		super();
		file = new SimpleStringProperty();
		prod = new SimpleIntegerProperty();
	}
	
	public RollFile(String file, int prod){
		super();
		this.file = new SimpleStringProperty(file.toUpperCase());
		this.prod = new SimpleIntegerProperty(prod);
	}

	public String getFile() {
		return file.get();
	}
	
	public SimpleStringProperty fileProperty() {
		return file;
	}

	public void setFile(String file) {
		this.file.set(file.toUpperCase());
	}

	public int getProd() {
		return prod.get();
	}

	public void setProd(int prod) {
		this.prod.set(prod);
	}
	
	public SimpleIntegerProperty prodProperty() {
		return prod;
	}
}
