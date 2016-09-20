package com.app.models;

public class DBObjectInfo {
	private int id;
	private String name;
	private String pc_loc;
	private int b24_ver;
	private String reason_;
	
	public DBObjectInfo(int id, String name, String pc_loc, int b24_ver, String reason_){
		this.setId(id);
		this.name = name;
		this.pc_loc = pc_loc;
		this.b24_ver = b24_ver;
		this.reason_ = reason_;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPc_loc() {
		return pc_loc;
	}

	public void setPc_loc(String pc_loc) {
		this.pc_loc = pc_loc;
	}

	public int getB24_ver() {
		return b24_ver;
	}

	public void setB24_ver(int b24_ver) {
		this.b24_ver = b24_ver;
	}

	public String getReason_() {
		return reason_;
	}

	public void setReason_(String reason_) {
		this.reason_ = reason_;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
