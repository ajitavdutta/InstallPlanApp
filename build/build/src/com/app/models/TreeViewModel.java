package com.app.models;

import com.app.custom.controls.HierarchyData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TreeViewModel implements HierarchyData<TreeViewModel>, Comparable<TreeViewModel> {
	private String data;

	public TreeViewModel(String data) {
        this.data = data;
    }

	private ObservableList<TreeViewModel> children = FXCollections.observableArrayList();

	@Override
	public ObservableList<TreeViewModel> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return data;
	}

	@Override
	public int compareTo(TreeViewModel o) {
		return data.compareTo(o.data);
	}

}
