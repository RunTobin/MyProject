package com.linq.xinansmart.model;

import com.j256.ormlite.field.DatabaseField;

public class Mode {

	private int id;

	private String modeCode;// �������

	private int centerCode;// ������--��������ID ������--����ID

	private String modeName;// ��������

	private String stratDate;// ��Ч��ʼ����

	private String endDate;// ��Ч��������

	private String startTime;// ��Ч��ʼʱ��

	private String endTime;// ��Ч����ʱ��

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
