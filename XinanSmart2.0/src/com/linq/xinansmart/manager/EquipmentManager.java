package com.linq.xinansmart.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.R.integer;
import android.util.Log;

import com.linq.xinansmart.R;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.GateWay;
import com.linq.xinansmart.model.Location_equipment;
import com.linq.xinansmart.web.CommManager;
import com.linq.xinansmart.web.JsonUtil;
import com.linq.xinansmart.web.JsonUtil.OrderFlag;

public class EquipmentManager {

	private EquipmentManager equipment;
	private CommManager commManager = null;
	private List<Equipment> eqList = null;

	private static EquipmentManager equipmentManager = null;

	private EquipmentManager() {
		commManager = CommManager.getInstance();
	};

	public static EquipmentManager getInstance() {
		if (equipmentManager == null) {
			equipmentManager = new EquipmentManager();
		}
		return equipmentManager;

	}

	/**
	 * @param UserId
	 *            账号
	 * @param Password
	 *            密码
	 * @return 根据账号和密码返回的所有设备
	 */
	public List<Equipment> getAllEquipment(String UserId, String Password) {
		if (eqList != null) {
			// Log.e("lstmodeEqu", eqList.size() + "mmmmmmm");
			return eqList;
		}
		eqList = new ArrayList<Equipment>();
		String order = JsonUtil.getJsonRequestParams(
				OrderFlag.EuqipmentGetAllInfo, UserId, Password, null);

		String response = null;
		try {
			response = commManager.sendDataPost(order,
					commManager.Service_URL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("reponse", response);
		List<LinkedHashMap<String, String>> lstnew = new ArrayList<LinkedHashMap<String, String>>();
		lstnew = (List<LinkedHashMap<String, String>>) JsonUtil.JsonToObject(
				response, lstnew.getClass());
		if (lstnew != null) {
			for (int i = 0; i < lstnew.size(); i++) {
				Equipment equipment = new Equipment();
				equipment = ToEquipment(lstnew.get(i));
				eqList.add(equipment);
			}
		}

		Log.e("lstmodeEqu", eqList.size() + "mmmmmmm");
		return eqList;
	}

	public List<Equipment> getEquipment(String UserId, String Password) {

		List<Equipment> eqList1 = new ArrayList<Equipment>();
		String order = JsonUtil.getJsonRequestParams(
				OrderFlag.EuqipmentGetAllInfo, UserId, Password, null);

		String response = null;
		try {
			response = commManager.sendDataPost(order,
					commManager.Service_URL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<LinkedHashMap<String, String>> lstnew = new ArrayList<LinkedHashMap<String, String>>();
		lstnew = (List<LinkedHashMap<String, String>>) JsonUtil.JsonToObject(
				response, lstnew.getClass());
		if (lstnew != null) {
			for (int i = 0; i < lstnew.size(); i++) {
				Equipment equipment = new Equipment();
				equipment = ToEquipment(lstnew.get(i));
				eqList1.add(equipment);
			}
		}

		Log.e("lstmodeEqu", eqList.size() + "mmmmmmm");
		return eqList1;
	}

	public List<Equipment> getChangeAllEquipment(String UserId, String Password) {
		if (eqList != null) {
			eqList.clear();

		}
		eqList = new ArrayList<Equipment>();
		String order = JsonUtil.getJsonRequestParams(
				OrderFlag.EuqipmentGetAllInfo, UserId, Password, null);

		String response = null;
		try {
			response = commManager.sendDataPost(order,
					commManager.Service_URL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("reponse", response);
		List<LinkedHashMap<String, String>> lstnew = new ArrayList<LinkedHashMap<String, String>>();
		lstnew = (List<LinkedHashMap<String, String>>) JsonUtil.JsonToObject(
				response, lstnew.getClass());
		if (lstnew != null) {
			for (int i = 0; i < lstnew.size(); i++) {
				Equipment equipment = new Equipment();
				equipment = ToEquipment(lstnew.get(i));
				eqList.add(equipment);
			}
		}

		Log.e("lstmodeEqu", eqList.size() + "mmmmmmm");
		return eqList;
	}

	public static Equipment ToEquipment(LinkedHashMap<String, String> lmap) {
		Equipment equip = new Equipment();
		Iterator it = lmap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entity = (Entry) it.next();
			if (entity.getKey().equals("Code")) {
				equip.setMachinID((int) (Float.parseFloat(entity.getValue()
						.toString())));
			} else if (entity.getKey().equals("Description")) {
				equip.setScan(entity.getValue().toString());
			} else if (entity.getKey().equals("EqIndex")) {
				equip.setNindex((int) (Float.parseFloat(entity.getValue()
						.toString())));
			} else if (entity.getKey().equals("ID")) {
				equip.setId((int) (Float.parseFloat(entity.getValue()
						.toString())));
			} else if (entity.getKey().equals("Name")) {
				equip.setName(entity.getValue().toString());
			} else if (entity.getKey().equals("NetGateCode")) {
				equip.setNcode((int) (Float.parseFloat(entity.getValue()
						.toString())));
			} else if (entity.getKey().equals("Number")) {
				equip.setEquCode(entity.getValue().toString());
			} else if (entity.getKey().equals("Online")) {
				equip.setbOnline(Boolean.parseBoolean(entity.getValue()
						.toString()));
			} else if (entity.getKey().equals("Position")) {
				equip.setAddress(entity.getValue().toString());
			} else if (entity.getKey().equals("TimeTicks")) {
				equip.setTimeTick((int) (Float.parseFloat(entity.getValue()
						.toString())));
			} else if (entity.getKey().equals("Type")) {
				equip.setType((int) (Float.parseFloat(entity.getValue()
						.toString())));
			} else if (entity.getKey().equals("Value")) {
				equip.setSvalue(entity.getValue().toString());
			} else {
				equip.setCid(0);
				equip.setContrltype(0);
				equip.setImage(0);
				continue;
			}
		}
		return equip;
	}

	/**
	 * @param ngate
	 *            网关
	 * @param eList
	 *            所有设备列表
	 * @return 根据网关获得所有设备中关联该网关的设备
	 */
	public List<Equipment> getEquipmentListByNetGate(GateWay ngate,
			List<Equipment> eList) {
		List<Equipment> result = new ArrayList<Equipment>();
		if (eList != null && eList.size() > 0) {
			for (int i = 0; i < eList.size(); i++) {
				if (eList.get(i).getNcode() == ngate.getCode()) {
					result.add(eList.get(i));
					reMovecopy(result);
				}
			}
		}
		return result;
	}

	private void reMovecopy(List<Equipment> listEq) {
		for (int i = 0; i < listEq.size() - 1; i++) {
			for (int j = listEq.size() - 1; j > i; j--) {
				// if (listEq.get(j).getName().equals(listEq.get(i).getName()))
				// {
				if (listEq.get(j).getId() == listEq.get(i).getId()) {
					listEq.remove(j);
				}
			}
		}
	}

	/**
	 * @param crEquipment
	 * @return 获取对应设备的图片
	 */
	public int getImage(Equipment crEquipment) {

		if (crEquipment.getbOnline()) {
			switch (crEquipment.getType()) {
			case 1:
				return R.drawable.kaiguan1;
			case 2:
				return R.drawable.kaiguan2;
			case 3:
				return R.drawable.kaiguan3;
			case 4:
				return R.drawable.chazuo;
			case 5:
				return R.drawable.chazuo;
			case 6:
				return R.drawable.chuanglian;
			case 7:
				return R.drawable.dengpao;
			case 8:
				return R.drawable.dengpaoh;
			case 9:
				return R.drawable.kaiguan1;
			case 10:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.wendu;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.shidu;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.huojin;
				} else if (crEquipment.getNindex() == 4) {
					return R.drawable.ruqin;
				}
			case 11:
				return R.drawable.danhuo;
			case 13:
				return R.drawable.danhuo2_on;
			case 14:
				return R.drawable.danhuo1_on;
			case 15:
				return R.drawable.biansedeng_on;
			case 16:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.wendu;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.shidu;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.pm;
				}
			case 17:
				return R.drawable.ruqinjc_on;

			case 18:
				return R.drawable.huanjinjcy_on;
			case 19:
				return R.drawable.keranjc_on;
			case 20:
				return R.drawable.baiyechuang;
			case 22:
				return R.drawable.yangqion;
			case 23:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.xiwanji;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.zhengkaoji;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.youyanji;
				} else if (crEquipment.getNindex() == 4) {
					return R.drawable.recycle;
				}
			case 24:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.xiwanji;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.zhengkaoji;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.youyanji;
				} else if (crEquipment.getNindex() == 4) {
					return R.drawable.gufengji;
				}
			 case 25:
                 return R.drawable.shuifei;
             case 101:
                 return R.drawable.ludeng;
             case 150:
                 if(crEquipment.getNindex() ==1){
                     return R.drawable.kongqiwendu;
                 }else if (crEquipment.getNindex() ==2){
                     return R.drawable.kongqishudu;
                 }
             case 151:
                 return R.drawable.fengsu;
             case 152:
                 return R.drawable.fengxiang;
             case 153:
                 return R.drawable.yuliang;
             case 154:
                 return R.drawable.light;
             case 155:
                 return R.drawable.ph;
             case 156:
                 return R.drawable.eryanghuatan;
             case 157:
                 return R.drawable.turangshidu;
             case 158:
                 return R.drawable.turangwendu;
             case 159:
                 return R.drawable.chuanglian;
             case 160:
                 return R.drawable.shidu;
             case 161:
                 return R.drawable.light;
             case 162:
                 if (crEquipment.getNindex() == 1){
                     return R.drawable.kongqiwendu;
                 }else if (crEquipment.getNindex() == 2){
                     return R.drawable.kongqishudu;
                 }else if (crEquipment.getNindex() == 3){
                     return R.drawable.turangshidu;
                 }else if (crEquipment.getNindex() == 4){
                     return R.drawable.turangwendu;
                 }else if(crEquipment.getNindex() == 5){
                     return R.drawable.light;
                 }else if(crEquipment.getNindex() == 6){
                     return R.drawable.yuliang;
                 }else if (crEquipment.getNindex() == 7){
                     return R.drawable.fengsu;
                 }else if (crEquipment.getNindex() == 8){
                     return R.drawable.fengxiang;
                 }else if (crEquipment.getNindex() == 9){
                     return R.drawable.daqiyali;
                 }else if(crEquipment.getNindex() == 10){
                     return R.drawable.ph;
                 }else if(crEquipment.getNindex() == 11){
                     return R.drawable.eryanghuatan;
                 }else if (crEquipment.getNindex() == 12){
                     return R.drawable.fuyanglizi;
                 }else if (crEquipment.getNindex() == 13){
                     return R.drawable.pm2;
                 }else if(crEquipment.getNindex() == 14){
                     return R.drawable.turangwendu;
                 }else if(crEquipment.getNindex() == 15){
                     return R.drawable.turangshidu;
                 }else if (crEquipment.getNindex() == 16){
                     return R.drawable.eryanghuatan;
                 }
			
			}
		} else {
			switch (crEquipment.getType()) {
			case 1:
				return R.drawable.kaiguan1_off;
			case 2:
				return R.drawable.kaiguan2_off;
			case 3:
				return R.drawable.kaiguan3_off;
			case 4:
				return R.drawable.chazuo_off;
			case 5:
				return R.drawable.chazuo_off;
			case 6:
				return R.drawable.chuanglian_off;
			case 7:
				return R.drawable.dengpao_off;
			case 8:
				return R.drawable.dengpaoh_off;
			case 9:
				return R.drawable.kaiguan1_off;
			case 10:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.wendu_off;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.shidu_off;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.huojin_off;
				} else if (crEquipment.getNindex() == 4) {
					return R.drawable.ruqin_off;
				}
			case 11:
				return R.drawable.danhuo_off;
			case 13:
				return R.drawable.danhuo2_off;
			case 14:
				return R.drawable.danhuo1_off;
			case 15:
				return R.drawable.biansedeng_off;
			case 16:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.wendu_off;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.shidu_off;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.pmoff;
				}
			case 17:
				return R.drawable.ruqinjc_off;

			case 18:
				return R.drawable.huanjinjcy_off;
			case 19:
				return R.drawable.keranjc_off;
			case 20:
				return R.drawable.baiyechuang_off;
			case 22:
				return R.drawable.yangqioff;
			case 23:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.xiwanji_off;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.zhengkaoji_off;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.youyanji_off;
				} else if (crEquipment.getNindex() == 4) {
					return R.drawable.recycle_off;
				}
			case 24:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.xiwanji_off;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.zhengkaoji_off;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.youyanji_off;
				} else if (crEquipment.getNindex() == 4) {
					return R.drawable.gufengji;
				}

			case 159:
				return R.drawable.chuanglian_off;
			case 160:
				return R.drawable.wendu_off;
			case 161:
				return R.drawable.light_sensor;
			}

		}
		return R.drawable.error;

	}

	public int getImage2(Location_equipment crEquipment) {

		if (crEquipment.getbOnline()) {
			switch (crEquipment.getType()) {
			case 1:
				return R.drawable.kaiguan1;
			case 2:
				return R.drawable.kaiguan2;
			case 3:
				return R.drawable.kaiguan3;
			case 4:
				return R.drawable.chazuo;
			case 5:
				return R.drawable.chazuo;
			case 6:
				return R.drawable.chuanglian;
			case 7:
				return R.drawable.dengpao;
			case 8:
				return R.drawable.dengpaoh;
			case 9:
				return R.drawable.kaiguan1;
			case 10:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.wendu;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.shidu;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.huojin;
				} else if (crEquipment.getNindex() == 4) {
					return R.drawable.ruqin;
				}
			case 11:
				return R.drawable.danhuo;
			case 13:
				return R.drawable.danhuo2_on;
			case 14:
				return R.drawable.danhuo1_on;
			case 15:
				return R.drawable.biansedeng_on;
			case 16:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.wendu;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.shidu;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.pm;
				}
			case 17:
				return R.drawable.ruqinjc_on;

			case 18:
				return R.drawable.huanjinjcy_on;
			case 19:
				return R.drawable.keranjc_on;
			case 20:
				return R.drawable.baiyechuang;
			case 23:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.xiwanji;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.zhengkaoji;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.youyanji;
				} else if (crEquipment.getNindex() == 4) {
					return R.drawable.recycle;
				}
			case 24:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.xiwanji;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.zhengkaoji;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.youyanji;
				} else if (crEquipment.getNindex() == 4) {
					return R.drawable.gufengji;
				}
			 case 25:
                 return R.drawable.shuifei;
             case 101:
                 return R.drawable.ludeng;
             case 150:
                 if(crEquipment.getNindex() ==1){
                     return R.drawable.kongqiwendu;
                 }else if (crEquipment.getNindex() ==2){
                     return R.drawable.kongqishudu;
                 }
             case 151:
                 return R.drawable.fengsu;
             case 152:
                 return R.drawable.fengxiang;
             case 153:
                 return R.drawable.yuliang;
             case 154:
                 return R.drawable.light;
             case 155:
                 return R.drawable.ph;
             case 156:
                 return R.drawable.eryanghuatan;
             case 157:
                 return R.drawable.turangshidu;
             case 158:
                 return R.drawable.turangwendu;
             case 159:
                 return R.drawable.chuanglian;
             case 160:
                 return R.drawable.shidu;
             case 161:
                 return R.drawable.light;
             case 162:
                 if (crEquipment.getNindex() == 1){
                     return R.drawable.kongqiwendu;
                 }else if (crEquipment.getNindex() == 2){
                     return R.drawable.kongqishudu;
                 }else if (crEquipment.getNindex() == 3){
                     return R.drawable.turangshidu;
                 }else if (crEquipment.getNindex() == 4){
                     return R.drawable.turangwendu;
                 }else if(crEquipment.getNindex() == 5){
                     return R.drawable.light;
                 }else if(crEquipment.getNindex() == 6){
                     return R.drawable.yuliang;
                 }else if (crEquipment.getNindex() == 7){
                     return R.drawable.fengsu;
                 }else if (crEquipment.getNindex() == 8){
                     return R.drawable.fengxiang;
                 }else if (crEquipment.getNindex() == 9){
                     return R.drawable.daqiyali;
                 }else if(crEquipment.getNindex() == 10){
                     return R.drawable.ph;
                 }else if(crEquipment.getNindex() == 11){
                     return R.drawable.eryanghuatan;
                 }else if (crEquipment.getNindex() == 12){
                     return R.drawable.fuyanglizi;
                 }else if (crEquipment.getNindex() == 13){
                     return R.drawable.pm2;
                 }else if(crEquipment.getNindex() == 14){
                     return R.drawable.turangwendu;
                 }else if(crEquipment.getNindex() == 15){
                     return R.drawable.turangshidu;
                 }else if (crEquipment.getNindex() == 16){
                     return R.drawable.eryanghuatan;
                 }
			}
		} else {
			switch (crEquipment.getType()) {
			case 1:
				return R.drawable.kaiguan1_off;
			case 2:
				return R.drawable.kaiguan2_off;
			case 3:
				return R.drawable.kaiguan3_off;
			case 4:
				return R.drawable.chazuo_off;
			case 5:
				return R.drawable.chazuo_off;
			case 6:
				return R.drawable.chuanglian_off;
			case 7:
				return R.drawable.dengpao_off;
			case 8:
				return R.drawable.dengpaoh_off;
			case 9:
				return R.drawable.kaiguan1_off;
			case 10:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.wendu_off;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.shidu_off;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.huojin_off;
				} else if (crEquipment.getNindex() == 4) {
					return R.drawable.ruqin_off;
				}

			case 11:
				return R.drawable.danhuo_off;
			case 13:
				return R.drawable.danhuo2_off;
			case 14:
				return R.drawable.danhuo1_off;
			case 15:
				return R.drawable.biansedeng_off;
			case 16:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.wendu_off;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.shidu_off;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.pmoff;
				}
			case 17:
				return R.drawable.ruqinjc_off;

			case 18:
				return R.drawable.huanjinjcy_off;
			case 19:
				return R.drawable.keranjc_off;
			case 20:
				return R.drawable.baiyechuang_off;
			case 23:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.xiwanji_off;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.zhengkaoji_off;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.youyanji_off;
				} else if (crEquipment.getNindex() == 4) {
					return R.drawable.recycle_off;
				}
			case 24:
				if (crEquipment.getNindex() == 1) {
					return R.drawable.xiwanji_off;
				} else if (crEquipment.getNindex() == 2) {
					return R.drawable.zhengkaoji_off;
				} else if (crEquipment.getNindex() == 3) {
					return R.drawable.youyanji_off;
				} else if (crEquipment.getNindex() == 4) {
					return R.drawable.gufengji;
				}
			case 159:
				return R.drawable.chuanglian_off;
			case 160:
				return R.drawable.wendu_off;
			case 161:
				return R.drawable.light_sensor;
			}

		}
		return R.drawable.error;
	}

	/**
	 * @param type
	 *            设备类型
	 * @param nIndex
	 *            设备的索引
	 * @param value
	 *            设备sValue
	 * @return 根据这三个解析 设备的状态
	 */
	public String GetDisplayValue(int type, int nIndex, String value) {
		String result = "";
		if (value.length() == 0)
			return result;

		switch (type) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 159:
		case 11:
			if (value.toUpperCase().equals("TRUE")) {
				result = "开";
			} else {
				result = "关";
			}
			break;
		case 6:
			if (value.toUpperCase().equals("TRUE")) {
				result = "打开";
			} else if (value.toUpperCase().equals("STOP")) {
				result = "停止";
			} else {
				result = "关闭";
			}
			break;
		case 7:
		case 8:
		case 20:
			result = value;
			break;
		case 22:
			result = value + "%";
			break;
		case 15:
			if (value.equals("0")) {
				result = "关";
			} else {
				result = "开";
			}
			break;
		case 9:
			break;
		case 10: {
			switch (nIndex) {
			case 1:
				result = value + "℃";
				break;
			case 2:
				result = value + "%";
				break;
			case 3:
				if (value.toUpperCase().equals("TRUE")) {
					result = "警报";
				} else if (value.toUpperCase().equals("STOP")) {
					result = "禁用";
				} else {
					result = "正常";
				}
				break;
			case 4:
				if (value.toUpperCase().equals("TRUE")) {
					result = "警报";
				} else if (value.toUpperCase().equals("STOP")) {
					result = "禁用";
				} else {
					result = "正常";
				}
				break;
			}
		}
			break;
		case 17: // 入侵报警器
			String[] str = value.split(",");
			if (str.length == 2) {
				if (str[0].toUpperCase().equals("TRUE"))
					result = "警报";
				else {
					if (str[1].toUpperCase().equals("STOP")) {
						result = "全部禁用";
					} else if (str[1].toUpperCase().equals("FORBID"))
						result = "声音禁用";
					else
						result = "正常";
				}
			} else {
				result = "正常";
			}

		case 23: {
			switch (nIndex) {
			case 1: {
				String[] arrValues = value.split(",");
				if (arrValues.length == 3) {
					int nStats = Integer.parseInt(arrValues[0]);
					switch (nStats) {
					case 0:
						result = "关";
						break;
					case 1:
						result = "开";
						break;
					case 2:
						result = "暂停";
						break;
					}
				}
			}
				break;
			case 2: {
				String[] arrValues = value.split(",");
				if (arrValues.length == 6) {
					int nStats = Integer.parseInt(arrValues[0]);
					switch (nStats) {
					case 0:
						result = "关";
						break;
					case 1:
						result = "开";
						break;
					case 2:
						result = "暂停";
						break;
					}
				}
			}
				break;
			case 3: {
				int nStats = Integer.parseInt(value);
				switch (nStats) {
				case 0:
					result = "关";
					break;
				case 1:
					result = "低";
					break;
				case 2:
					result = "中";
					break;
				case 3:
					result = "高";
					break;
				}
			}
				break;
			case 4: {
				int nStats = Integer.parseInt(value);
				switch (nStats) {
				case 0:
					result = "关";
					break;
				case 1:
					result = "开";
					break;
				}
			}
				break;
			}
		}

		case 24: {
			switch (nIndex) {
			case 1: {
				String[] arrValues = value.split(",");
				if (arrValues.length == 2) {
					int nStats = Integer.parseInt(arrValues[0]);
					switch (nStats) {
					case 0:
						result = "关";
						break;
					case 1:
						result = "开";
						break;
					case 2:
						result = "暂停";
						break;
					}
				}

			}
				break;
			case 2: {
				String[] arrValues = value.split(",");
				if (arrValues.length == 3) {
					int nStats = Integer.parseInt(arrValues[0]);
					switch (nStats) {
					case 0:
						result = "关";
						break;
					case 1:
						result = "开";
						break;
					case 2:
						result = "暂停";
						break;
					}
				}
			}
				break;
			case 3: {
				int nStats = Integer.parseInt(value);
				switch (nStats) {
				case 0:
					result = "关";
					break;
				case 1:
					result = "低";
					break;
				case 2:
					result = "中";
					break;
				case 3:
					result = "高";
					break;
				}
			}
				break;
			case 4: {
				int nStats = Integer.parseInt(value);
				switch (nStats) {
				case 0:
					result = "关";
					break;
				case 1:
					result = "开";
					break;
				}
			}
				break;
			}

		}
		 case 25:
			 String[] arrData = value.split(",");
             int nStats0 = Integer.parseInt(arrData[0]);
             int nStats1 = Integer.parseInt(arrData[1]);
             int nStats2 = Integer.parseInt(arrData[2]);
             int nStats3 = Integer.parseInt(arrData[3]);
             int nStats4 = Integer.parseInt(arrData[4]);
             int nStats5 = Integer.parseInt(arrData[5]);
             int nStats6 = Integer.parseInt(arrData[6]);
             int nStats7 = Integer.parseInt(arrData[7]);
             if (arrData.length == 8) {
                 if (nStats0 == nStats1 &&
                         nStats2 == nStats3 &&
                         nStats4 == nStats5 &&
                         nStats6 == nStats7){
                     result = "关";
                 }else {
                     result = "开";
                 }

             }
			break;
		 case 151:
             result = value + "m/s";
             break;
         case 152:
        	  int nValue =Integer.parseInt(value);
              switch (nValue)
              {
                  case 0:
                      result = "北";
                      break;
                  case 1:
                      result = "北偏东";
                      break;
                  case 2:
                      result = "东北";
                      break;
                  case 3:
                      result = "东偏北";
                      break;
                  case 4:
                      result = "东";
                      break;
                  case 5:
                      result = "东偏南";
                      break;
                  case 6:
                      result = "东南";
                      break;
                  case 7:
                      result = "南偏东";
                      break;
                  case 8:
                      result = "南";
                      break;
                  case 9:
                      result = "南偏西";
                      break;
                  case 10:
                      result = "西南";
                      break;
                  case 11:
                      result = "西偏南";
                      break;
                  case 12:
                      result = "西";
                      break;
                  case 13:
                      result = "西偏北";
                      break;
                  case 14:
                      result = "西北";
                      break;
                  case 15:
                      result = "北偏西";
                      break;
              }
              break;
         case 153:
             result = value + "mm";
             break;
         case 154:
             result = value + "Lx";
             break;
         case 155:
             result = value;
             break;
         case 156:
             result = value + "PPM";
             break;
         case 157:
             result = value + "%";
             break;
         case 158:
             result = value + "℃";
             break;
         case 160:
             result = value;
             break;
         case 161:
             result = value;
             break;
         case 162:
             switch (nIndex) {
                 case 1:
                      result = value + "℃";
                     break;
                 case 2:
                     result = value + "%";
                     break;
                 case 3:
                     result = value + "℃";
                     break;
                 case 4:
                     result = value + "%";
                     break;
                 case 5:
                     result = value + "Lx";
                     break;
                 case 6:
                     result = value + "mm";
                     break;
                 case 7:
                     result = value + "m/s";
                     break;
                 case 8:
                	  int mValue =Integer.parseInt(value);
                      switch (mValue)
                      {
                          case 0:
                              result = "北";
                              break;
                          case 1:
                              result = "北偏东";
                              break;
                          case 2:
                              result = "东北";
                              break;
                          case 3:
                              result = "东偏北";
                              break;
                          case 4:
                              result = "东";
                              break;
                          case 5:
                              result = "东偏南";
                              break;
                          case 6:
                              result = "东南";
                              break;
                          case 7:
                              result = "南偏东";
                              break;
                          case 8:
                              result = "南";
                              break;
                          case 9:
                              result = "南偏西";
                              break;
                          case 10:
                              result = "西南";
                              break;
                          case 11:
                              result = "西偏南";
                              break;
                          case 12:
                              result = "西";
                              break;
                          case 13:
                              result = "西偏北";
                              break;
                          case 14:
                              result = "西北";
                              break;
                          case 15:
                              result = "北偏西";
                              break;
                      }
                      break;
                 case 9:
                     result = value + "MPa";
                     break;
                 case 10:
                     result = value ;
                     break;
                 case 11:
                     result = value + "PPM";
                     break;
                 case 12:
                     result = value + "个/cm3";
                     break;
                 case 13:
                     result = value ;
                     break;
                 case 14:
                     result = value + "℃";
                     break;
                 case 15:
                	 result = value +"%rh";
                     break;
                 case 16:
                     result = value + "PPM";
                     break;
             }
             break;
     }
		return result;
	}

	// 给设备赋值
	public boolean SetEquipmentValue(final int NetGateCode, final int type,
			final int code, final String value, final int eqIndex,
			final String user, final String password) {

		Runnable run = new Runnable() {

			@Override
			public void run() {
				String data = "{\"NetGateCode\":" + NetGateCode + ",\"Type\":"
						+ type + ",\"Code\":" + code + ",\"Value\":\"" + value
						+ "\",\"EqIndex\":" + eqIndex + "}";
				String order = commManager.orderControlJson(data,
						OrderFlag.EquipmentValueChange, user, password);
				Log.e("使用设备发送", order);
				String str = null;
				try {
					str = commManager.sendDataPost(order,
							commManager.Service_URL);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.e("使用设备返回", str);
				if (str.equals("TRUE")) {
					for (int i = 0; i < eqList.size(); i++) {
						Equipment equip = eqList.get(i);
						if (equip.getNcode() == NetGateCode
								&& equip.getMachinID() == code
								&& equip.getNindex() == eqIndex) {
							equip.setSvalue(value);
							break;
						}
					}
				}

			}
		};
		Thread thread = new Thread(run);
		thread.start();
		return true;

	}

	// 更新设备信息
	public boolean UpdateEquipmentInfo(int NetGateCode, int type, int code,
			String number, String name, String position, String description,
			int eqIndex, String UserId, String Password) {

		String data = "{\"NetGateCode\":" + NetGateCode + ",\"Type\":" + type
				+ ",\"Code\":" + code + ",\"Number\":\"" + number
				+ "\",\"Name\":\"" + name + "\",\"Position\":\"" + position
				+ "\",\"Description\":\"" + description + "\",\"EqIndex\":"
				+ eqIndex + "}";
		String order = commManager.orderControlJson(data,
				OrderFlag.EquipmentInfoUpdate, UserId, Password);
		String str = null;
		try {
			str = commManager.sendDataPost(order, commManager.Service_URL)
					.toUpperCase();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (str.equals("TRUE")) {
			for (int i = 0; i < eqList.size(); i++) {
				Equipment equip = eqList.get(i);
				if (equip.getNcode() == NetGateCode
						&& equip.getMachinID() == code
						&& equip.getNindex() == eqIndex) {
					equip.setAddress(position);
					equip.setName(name);
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

	// 获取设备的最新状态
	@SuppressWarnings("unchecked")
	public List<Equipment> GetLastEquipmentStatus(int tick, String userId,
			String Password) {
		String data = "{\"Tick\":" + tick + "}";
		String order = commManager.orderControlJson(data,
				OrderFlag.EquipmentRefreshAllInfo, userId, Password);
		String str = null;
		try {
			str = commManager.sendDataPost(order, commManager.Service_URL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (str.trim().length() > 0) {
			List<Equipment> result = new ArrayList<Equipment>();
			List<LinkedHashMap<String, String>> lstnew = new ArrayList<LinkedHashMap<String, String>>();
			lstnew = (List<LinkedHashMap<String, String>>) JsonUtil
					.JsonToObject(str, lstnew.getClass());
			for (int i = 0; i < lstnew.size(); i++) {
				Equipment equipment = new Equipment();
				equipment = ToEquipment(lstnew.get(i));
				RefreshEquipmentById(equipment.getId(), equipment.getbOnline(),
						equipment.getTimeTick(), equipment.getSvalue());
				result.add(equipment);
			}

			return result;
		} else {
			return null;
		}
	}

	// 根据ID刷新
	private void RefreshEquipmentById(int equipmentId, boolean online,
			int tick, String value) {
		for (int i = 0; i < eqList.size(); i++) {
			Equipment eq = eqList.get(i);
			if (eq.getId() == equipmentId) {
				eq.setbOnline(online);
				eq.setSvalue(value);
				eq.setTimeTick(tick);
				break;
			}
		}
	}

	// 根据设备ID获取设备
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

	public List<Equipment> getEqList() {
		return eqList;
	}

}
