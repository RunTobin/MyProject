package com.linq.xinansmart.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Mode;
import com.linq.xinansmart.model.ModeEquipment;
import com.linq.xinansmart.web.CommManager;
import com.linq.xinansmart.web.JsonUtil;
import com.linq.xinansmart.web.JsonUtil.OrderFlag;

public class ModeManager {

	private static final String Service_URL = "http://111.38.122.83:2003/XRFAppPlatID.aspx";
	private static ModeManager modemanager = null;
	private CommManager commManager = null;
	private List<Mode> modeList = null;
	private List<ModeEquipment> meqList = null;// ���������µ��豸�б�
	private Concenter concenter = null;
	private List<Equipment> eqList = new ArrayList<Equipment>();

	private ModeManager(Concenter concenter) {
		this.concenter = concenter;
		commManager = CommManager.getInstance();
		eqList = EquipmentManager.getInstance().getAllEquipment(
				concenter.getUser(), concenter.getPassword());

		modeList = getAllProfile(concenter.getUser(), concenter.getPassword());

	};

	public static ModeManager getInstance(Concenter concenter) {
		if (modemanager == null) {
			modemanager = new ModeManager(concenter);
		}
		return modemanager;

	}

	public List<Mode> getModeList() {
		if (modeList == null) {
			modeList = new ArrayList<Mode>();
		}

		return modeList;
	}

	// ��ӳ���
	public Mode AddAppProfile(String number, String name, boolean enable,
			String startDate, String endDate, String startTime, String endTime,
			String description) {
		if (modeList == null) {
			modeList = new ArrayList<Mode>();
		}
		String data = "{\"Number\":\"" + number + "\",\"Name\":\"" + name
				+ "\",\"Enable\":" + enable + ",\"StartDate\":\"" + startDate
				+ "\",\"EndDate\":\"" + endDate + "\",\"StartTime\":\""
				+ startTime + "\",\"EndTime\":\"" + endTime
				+ "\",\"Description\":\"" + description + "\"}";
		Log.e("hahahahahah", data);
		String order = commManager.orderControlJson(data,
				OrderFlag.AppProfileNew, concenter.getUser(),
				concenter.getPassword());
		Log.e("��ӳ�������������������", order);
		String str = null;
		try {
			str = commManager.sendDataPost(order, Service_URL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (str.trim().length() > 0) {
			LinkedHashMap<String, String> profile = new LinkedHashMap<String, String>();
			profile = (LinkedHashMap<String, String>) JsonUtil.JsonToObject(
					str, profile.getClass());
			Mode result = ToMode(profile);
			modeList.add(result);
			return result;
		} else {
			return null;
		}
	}

	public boolean UsingAppProfile(int profileID, String UserId, String Password) {
		String data = "{\"ID\":" + profileID + "}";
		String order = commManager.orderControlJson(data,
				OrderFlag.AppProfileUsing, UserId, Password);
		Log.e("ʹ�ó�������������������", order);
		String str = null;
		try {
			str = commManager.sendDataPost(order, Service_URL).toUpperCase();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (str == "TRUE")
			return true;
		else {
			try {
				throw new Exception(str);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	// ɾ��������Ϣ
	public boolean DeleteAppProfile(int profileID, String UserId,
			String Password) {
		for (int i = 0; i < meqList.size(); i++) {
			ModeEquipment meq = meqList.get(i);
			if (meq.getModeId() == profileID) {
				boolean ok = DeleteAppProfileDetailA(profileID, meq.getId(),
						UserId, Password);
				if (ok == true) {
					meqList.remove(i);

				}
			}
		}
		String data = "{\"ID\":" + profileID + "}";
		String order = commManager.orderControlJson(data,
				OrderFlag.AppProfileDelete, UserId, Password);
		Log.e("ɾ����������������������", order);
		String str = null;
		try {
			str = commManager.sendDataPost(order, Service_URL).toUpperCase();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (str.equals("TRUE")) {
			for (int i = 0; i < modeList.size(); i++) {
				Mode mode = modeList.get(i);
				if (mode.getId() == profileID) {
					modeList.remove(i);
				}
			}
			return true;
		} else {
			try {
				throw new Exception(str);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	public boolean DeleteAppProfileDetailA(final int profileID,
			final int detailID, String UserId, String Password) {
		String data = "{\"ProfileID\":" + profileID + ",\"ID\":" + detailID
				+ "}";
		String order = commManager.orderControlJson(data,
				OrderFlag.AppProfileDeleteDetail, UserId, Password);
		Log.e("ɾ�������и����豸����������������", order);
		String str = null;
		try {
			str = commManager.sendDataPost(order, Service_URL).toUpperCase();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < meqList.size(); i++) {
			ModeEquipment mq = meqList.get(i);
			if (mq.getId() == detailID&&mq.getModeId()==profileID) {
				meqList.remove(i);
			}
		}
		if (str.equals("TRUE"))
			return true;
		else {
			try {
				throw new Exception(str);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	// ��ȡ���г���
	@SuppressWarnings("unchecked")
	public List<Mode> getAllChangeProfile(String UserId, String Password) {
		if (modeList != null) {
			modeList.clear();
		}
		if(meqList!=null){
			meqList.clear();
		}
		
		modeList = new ArrayList<Mode>();
		String order = JsonUtil.getJsonRequestParams(
				OrderFlag.AppProfileGetAllInfo, UserId, Password, null);

		String response = null;
		try {
			response = commManager.sendDataPost(order, Service_URL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if (response != null) {
		// Log.e("content", response);
		// }
		List<LinkedHashMap<String, String>> lstnew = new ArrayList<LinkedHashMap<String, String>>();
		lstnew = (List<LinkedHashMap<String, String>>) JsonUtil.JsonToObject(
				response, lstnew.getClass());
		if (lstnew != null) {
			for (int i = 0; i < lstnew.size(); i++) {
				Mode meinfo = new Mode();
				meinfo = ToMode(lstnew.get(i));
				modeList.add(meinfo);
			}
		}

		return modeList;
	}

	// ��ȡ���г���
	@SuppressWarnings("unchecked")
	public List<Mode> getAllProfile(String UserId, String Password) {
		if (modeList != null) {
			Log.e("content", modeList.size() + "");
			eqList = EquipmentManager.getInstance().getAllEquipment(UserId,
					Password);
			Log.e("�õ���eqList", eqList.size() + "mmmmmmm");
			return modeList;
		}
		modeList = new ArrayList<Mode>();
		String order = JsonUtil.getJsonRequestParams(
				OrderFlag.AppProfileGetAllInfo, UserId, Password, null);
		String response = null;
		try {
			response = commManager.sendDataPost(order, Service_URL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if (response != null) {
		// Log.e("content", response);
		// }
		List<LinkedHashMap<String, String>> lstnew = new ArrayList<LinkedHashMap<String, String>>();
		lstnew = (List<LinkedHashMap<String, String>>) JsonUtil.JsonToObject(
				response, lstnew.getClass());
		if (lstnew != null) {
			for (int i = 0; i < lstnew.size(); i++) {
				Mode meinfo = new Mode();
				meinfo = ToMode(lstnew.get(i));
				modeList.add(meinfo);
			}
		}

		return modeList;
	}

	private List<LinkedHashMap<String, String>> linkmaps;

	public Mode ToMode(LinkedHashMap<String, String> lmap) {
		Mode minfo = new Mode();
		Iterator it = lmap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entity = (Entry) it.next();
			if (entity.getKey().equals("EndDate")) {
				minfo.setEndDate(entity.getValue().toString());
			} else if (entity.getKey().equals("EndTime")) {
				minfo.setEndTime(entity.getValue().toString());
			} else if (entity.getKey().equals("ID")) {
				minfo.setCenterCode((int) (Float.parseFloat(entity.getValue()
						.toString())));
				minfo.setId((int) (Float.parseFloat(entity.getValue()
						.toString())));
			} else if (entity.getKey().equals("Name")) {
				minfo.setModeName(entity.getValue().toString());
			} else if (entity.getKey().equals("Number")) {
				minfo.setModeCode(entity.getValue().toString());
			} else if (entity.getKey().equals("StartDate")) {
				// minfo.setStratDate(entity.getValue().toString());
			} else if (entity.getKey().equals("StartTime")) {
				minfo.setStartTime(entity.getValue().toString());
			} else if (entity.getKey().equals("Detail")) {
				linkmaps = new ArrayList<LinkedHashMap<String, String>>();
				linkmaps = (List<LinkedHashMap<String, String>>) JsonUtil
						.JsonToObject(entity.getValue().toString(),
								linkmaps.getClass());
				getAllModeEquipment();
			} else if (entity.getKey().equals("Enable")) {
				if (Boolean.parseBoolean(entity.getValue().toString()) == true) {
					minfo.setStratDate("TRUE");
				} else {
					minfo.setStratDate("FALSE");
				}
			} else {
				continue;
			}

		}
		return minfo;
	}

	// ��ȡ�������������е��豸
	public List<ModeEquipment> getAllModeEquipment() {
		if (meqList == null) {
			meqList = new ArrayList<ModeEquipment>();
		} else {
			// meqList.clear();
		}
		if(linkmaps!=null){
			for (int i = 0; i < linkmaps.size(); i++) {
				ModeEquipment mequ = new ModeEquipment();
				mequ = ToModeEquipment(linkmaps.get(i));
				meqList.add(mequ);
			}
		}

		return meqList;
	}

	// ModeEquipment����ת��
	public static ModeEquipment ToModeEquipment(
			LinkedHashMap<String, String> lmap) {
		ModeEquipment mequip = new ModeEquipment();
		Iterator it = lmap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entity = (Entry) it.next();
			if (entity.getKey().equals("ID")) {
				mequip.setId((int) (Float.parseFloat(entity.getValue()
						.toString())));
			} else if (entity.getKey().equals("EquimentCode")) {
				mequip.setEquipmentId((int) (Float.parseFloat(entity.getValue()
						.toString())));
			} else if (entity.getKey().equals("EquimentValue")) {
				if (entity.getValue() == null) {
					mequip.setValue("null");
				} else {
					mequip.setValue(entity.getValue().toString());
				}
			} else if (entity.getKey().equals("ProfileID")) {
				mequip.setModeId((int) (Float.parseFloat(entity.getValue()
						.toString())));
			} else if (entity.getKey().equals("NetGateCode")) {
				mequip.setNetGateCode((int) (Float.parseFloat(entity.getValue()
						.toString())));
			} else if (entity.getKey().equals("Type")) {
				mequip.setType((int) (Float.parseFloat(entity.getValue()
						.toString())));
			} else if (entity.getKey().equals("EqIndex")) {
				mequip.setEqIndex((int) (Float.parseFloat(entity.getValue()
						.toString())));
			}

		}
		return mequip;
	}

	// ������Ϣ�޸�
	public boolean UpdateAppProfile(int profileID, String number, String name,
			boolean enable, String startDate, String endDate, String startTime,
			String endTime, String description, String user, String pwd) {

		String data = "{\"ID\":" + profileID + ",\"Number\":\"" + number
				+ "\",\"Name\":\"" + name + "\",\"Enable\":" + enable
				+ ",\"StartDate\":\"" + startDate + "\",\"EndDate\":\""
				+ endDate + "\",\"StartTime\":\"" + startTime
				+ "\",\"EndTime\":\"" + endTime + "\",\"Description\":\""
				+ description + "\"}";
		
//		String data = "{\"Number\":\"" + number + "\",\"Name\":\"" + name
//				+ "\",\"Enable\":" + enable + ",\"StartDate\":\"" + startDate
//				+ "\",\"EndDate\":\"" + endDate + "\",\"StartTime\":\""
//				+ startTime + "\",\"EndTime\":\"" + endTime
//				+ "\",\"Description\":\"" + description + "\"}";
		String order = commManager.orderControlJson(data,
				OrderFlag.AppProfileUpdate, concenter.getUser(),concenter.getPassword());
//		String order = JsonUtil.getJsonRequestParams(
//				OrderFlag.AppProfileUpdate, user, pwd, data);
		
		String str = null;
		try {
			str = commManager.sendDataPost(order, Service_URL).toUpperCase();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.e("�༭������Ϣ����������������", order);
		if (str.equals("TRUE")) {
			
			for (int i = 0; i < modeList.size(); i++) {
				Mode mode = modeList.get(i);
				if (mode.getId() == profileID)
			 {
					mode.setModeCode(number);
					mode.setModeName(name);
				}
			}
			return true;
		} else {
			try {
				throw new Exception(str);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	// �����豸ID��ȡ�豸
	public Equipment getEqById(int eqId) {
		Equipment result = new Equipment();
		for (Equipment eq : eqList) {
			if (eq.getId() == eqId) {
				result = eq;
				break;
			}
		}

		return result;
	}

	public Mode getModeById(int modeId) {
		Mode result = new Mode();
		for (int i = 0; i < modeList.size(); i++) {
			if (modeList.get(i).getId() == modeId) {
				result = modeList.get(i);
			}
		}
		return result;
	}

	public List<ModeEquipment> getMeqByModeId(int modeId) {
		List<ModeEquipment> result = new ArrayList<ModeEquipment>();
		for (int i = 0; i < meqList.size(); i++) {
			ModeEquipment meq = meqList.get(i);
			if (meq.getModeId() == modeId) {
				result.add(meq);
			}
		}
		return result;
	}

	public List<ModeEquipment> getEquipmentByModeId(int modeId) {
		List<ModeEquipment> result = new ArrayList<ModeEquipment>();
		for (int i = 0; i < meqList.size(); i++) {
			if (meqList.get(i).getModeId() == modeId) {
				result.add(meqList.get(i));
			}
		}
		return result;
	}

	// ����NetGateCode��EqIndex��EquimentCode��ȡ�豸
	public Equipment getEquipmentByNee(int NetGateCode, int EqIndex,
			int EquipmentCode) {
		Equipment equip = null;
		Log.e("ѭ����eqList", eqList.size() + "mmmmmmm");
		for (int i = 0; i < eqList.size(); i++) {
			Equipment equipm = eqList.get(i);
			if (equipm.getNcode() == NetGateCode
					&& equipm.getNindex() == EqIndex
					&& equipm.getMachinID() == EquipmentCode) {
				equip = equipm;
				break;
			}
		}
		return equip;
	}

	public void ClearmeqList() {
		meqList.clear();
	}

	// ��ӳ�����ϸ
	public ModeEquipment AddAppProfileDetail(final int profileID,
			final int NetGateCode, final int Type, final int EquimentCode,
			final String EquimentValue, final int EqIndex) {
		ModeEquipment mresult1 = new ModeEquipment();
		String data = "{\"ProfileID\":" + profileID + ",\"NetGateCode\":"
				+ NetGateCode + ",\"Type\":" + Type + ",\"EquimentCode\":"
				+ EquimentCode + ",\"EquimentValue\":\"" + EquimentValue
				+ "\",\"EqIndex\":" + EqIndex + "}";
		String order = commManager.orderControlJson(data,
				OrderFlag.AppProfileAddDetail, concenter.getUser(),
				concenter.getPassword());
		Log.e("��ӳ����и����豸����������������", order);
		String str = null;
		try {
			str = commManager.sendDataPost(order, Service_URL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (str.trim().length() > 0) {

			LinkedHashMap<String, String> prodetail = new LinkedHashMap<String, String>();
			prodetail = (LinkedHashMap<String, String>) JsonUtil.JsonToObject(
					str, prodetail.getClass());
			mresult1 = ToModeEquipment(prodetail);

			meqList.add(mresult1);

		} else {
			mresult1 = null;
		}

		return mresult1;

	}

}
