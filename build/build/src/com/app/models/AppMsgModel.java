package com.app.models;

import javafx.beans.property.SimpleStringProperty;

public class AppMsgModel {
	private SimpleStringProperty type;
	private SimpleStringProperty msg;
	private SimpleStringProperty reason;

	public AppMsgModel(String type, String msg, String reason) {
		super();
		this.type = new SimpleStringProperty(type);
		this.msg = new SimpleStringProperty(msg);
		this.reason = new SimpleStringProperty(reason);
	}

	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public String getMsg() {
		return msg.get();
	}

	public void setMsg(String msg) {
		this.msg.set(msg);
	}

	public String getReason() {
		return reason.get();
	}

	public void setReason(String reason) {
		this.reason.set(reason);
	}

}
