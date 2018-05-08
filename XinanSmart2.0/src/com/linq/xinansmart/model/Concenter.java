package com.linq.xinansmart.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class Concenter implements Serializable {

	@DatabaseField(generatedId = true, unique = true)
	int id;
	@DatabaseField
	String name;
	// int type;// 0--局域网 1--互联网

	// String IpAddress;
	@DatabaseField
	int UpRate;// 刷新频率
	@DatabaseField
	int image;
	@DatabaseField
	String User;
	@DatabaseField
	String Password;
	@DatabaseField
	boolean IsshowAll;// 是否显示全部 true--显示全部 设备 false--仅显示在线设备

	public boolean isIsshowAll() {
		return IsshowAll;
	}

	public void setIsshowAll(boolean isshowAll) {
		IsshowAll = isshowAll;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
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

	// public int getType() {
	// return type;
	// }
	//
	// public void setType(int type) {
	// this.type = type;
	// }

	// public String getIpAddress() {
	// return IpAddress;
	// }
	//
	// public void setIpAddress(String ipAddress) {
	// IpAddress = ipAddress;
	// }

	public int getUpRate() {
		return UpRate;
	}

	public void setUpRate(int upRate) {
		UpRate = upRate;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		// sb.append(" ,type=").append(type);
		sb.append(" ,name=").append(name);
		// sb.append(" ,IpAddress=").append(IpAddress);
		sb.append(" ,UpRate=").append(UpRate);
		sb.append(" ,IsshowAll=").append(IsshowAll);
		return sb.toString();
	}

}
