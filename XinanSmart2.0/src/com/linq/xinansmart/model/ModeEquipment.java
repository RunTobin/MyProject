package com.linq.xinansmart.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ModeEquipment {

	int id;
	
	int equipmentId;//�豸id
	
	String value;//�豸����ֵ
	
	int modeId;//����id
	
	int NetGateCode;//���ر��
	
	int Type;
	
	int EqIndex;//�豸��·��
	
	public int getNetGateCode() {
		return NetGateCode;
	}

	public void setNetGateCode(int netGateCode) {
		NetGateCode = netGateCode;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public int getEqIndex() {
		return EqIndex;
	}

	public void setEqIndex(int eqIndex) {
		EqIndex = eqIndex;
	}

	public int getEquipmentId() {
		return equipmentId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getModeId() {
		return modeId;
	}

	public void setModeId(int modeId) {
		this.modeId = modeId;
	}

	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String result = "";
		result = "id="+id+"equipmentId="+equipmentId+"value="+value+"modeId"+modeId+"NetGateCode"+NetGateCode+"Type"+Type+"EqIndex"+EqIndex;
		return result;
	}
	
	
}
