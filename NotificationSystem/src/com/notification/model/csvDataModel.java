package com.notification.model;

public class csvDataModel {
	String id;
	String category;
	String data;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public boolean equals(Object obj) {
		csvDataModel cdm = (csvDataModel) obj;
		
		if(cdm.getId().equals(this.getId()) && cdm.getCategory().equals(this.getCategory()) && cdm.getData().equals(this.getData()) ) return true;
		return false;
	}
}
