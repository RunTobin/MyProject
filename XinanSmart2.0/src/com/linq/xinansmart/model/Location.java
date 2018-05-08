package com.linq.xinansmart.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class Location implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2312312423523L;
	@Override
	public String toString() {
		return "Location [id=" + id + ", name=" + name + ", background="
				+ background + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	@DatabaseField(generatedId = true, unique = true)
	int id;
	
	@DatabaseField
	String name;
	
	@DatabaseField
	String background;
	
	@DatabaseField
	String UID;
	
	@DatabaseField
	String view;
	
	

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	@DatabaseField
	String Concenter;
	
	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getConcenter() {
		return Concenter;
	}

	public void setConcenter(String concenter) {
		Concenter = concenter;
	}

	
	
}
