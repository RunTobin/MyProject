package com.linq.xinansmart.model;

import com.j256.ormlite.field.DatabaseField;


public class Equipment {
	int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private int cid;// 控制中心id
	@DatabaseField
	private int ncode;// 网关id
	@DatabaseField
	private int type;// 设备类型
	@DatabaseField
	private int machinID;// 设备号
	@DatabaseField
	private boolean bOnline;// 在线状态
	@DatabaseField
	private int nindex;// 索引
	@DatabaseField
	private String svalue;// 值
	@DatabaseField
	private int timeTick;// 更新时间
	@DatabaseField
	private int image;// 图标
	@DatabaseField
	private int contrltype;// 控制类型
	@DatabaseField
	private String address;// 位置
	@DatabaseField
	private String scan;// 描述
	@DatabaseField
	private String equCode;

	public String getEquCode() {
		return equCode;
	}

	public void setEquCode(String equCode) {
		this.equCode = equCode;
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

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getNcode() {
		return ncode;
	}

	public void setNcode(int ncode) {
		this.ncode = ncode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMachinID() {
		return machinID;
	}

	public void setMachinID(int machinID) {
		this.machinID = machinID;
	}

	public Boolean getbOnline() {
		return bOnline;
	}

	public void setbOnline(Boolean bOnline) {
		this.bOnline = bOnline;
	}

	public int getNindex() {
		return nindex;
	}

	public void setNindex(int nindex) {
		this.nindex = nindex;
	}

	public String getSvalue() {
		return svalue;
	}

	public void setSvalue(String svalue) {
		this.svalue = svalue;
	}

	public int getTimeTick() {
		return timeTick;
	}

	public void setTimeTick(int timeTick) {
		this.timeTick = timeTick;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public int getContrltype() {
		return contrltype;
	}

	public void setContrltype(int contrltype) {
		this.contrltype = contrltype;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getScan() {
		return scan;
	}

	public void setScan(String scan) {
		this.scan = scan;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		sb.append(" ,name=").append(name);
		sb.append(" ,cid=").append(cid);
		sb.append(" ,ncode=").append(ncode);
		sb.append(" ,type=").append(type);
		sb.append(" ,machinID=").append(machinID);
		sb.append(" ,bOnline=").append(bOnline);
		sb.append(" ,nindex=").append(nindex);
		sb.append(" ,svalue=").append(svalue);
		sb.append(" ,timeTick=").append(timeTick);
		sb.append(" ,image=").append(image);
		sb.append(" ,contrltype=").append(contrltype);
		sb.append(" ,address=").append(address);
		sb.append(" ,scan=").append(scan);
		return sb.toString();
	}

}
