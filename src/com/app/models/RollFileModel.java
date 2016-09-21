package com.app.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RollFileModel {
	private SimpleStringProperty file;
	private SimpleIntegerProperty prod;
	
	public RollFileModel(String file, int prod){
		super();
		this.file.set(file);
		this.prod.set(prod);
	}

	public String getFile() {
		return file.get();
	}
	
	public SimpleStringProperty fileProperty() {
		return file;
	}

	public void setFile(String file) {
		this.file.set(file);
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
