package com.linq.xinansmart.model;

import com.j256.ormlite.field.DatabaseField;

public class Mode {

	private int id;

	private String modeCode;// 场景编号

	private int centerCode;// 局域网--控制中心ID 互联网--场景ID

	private String modeName;// 场景名称

	private String stratDate;// 用效开始日期

	private String endDate;// 用效结束日期

	private String startTime;// 用效开始时间

	private String endTime;// 用效结束时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModeCode() {
		return modeCode;
	}

	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}

	public int getCenterCode() {
		return centerCode;
	}

	public void setCenterCode(int centerCode) {
		this.centerCode = centerCode;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public String getStratDate() {
		return stratDate;
	}

	public void setStratDate(String stratDate) {
		this.stratDate = stratDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "Mode [id=" + id + ", modeCode=" + modeCode + ", centerCode="
				+ centerCode + ", modeName=" + modeName + ", stratDate="
				+ stratDate + ", endDate=" + endDate + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}

}
