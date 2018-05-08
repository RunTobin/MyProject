package com.linq.xinansmart.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Camera;
import android.util.Log;

import com.linq.xinansmart.model.GateWay;
import com.linq.xinansmart.web.CommManager;
import com.linq.xinansmart.web.JsonUtil;
import com.linq.xinansmart.web.JsonUtil.OrderFlag;

public class GateWayManager {

	//private static final String Service_URL = "http://111.38.122.83:2003/XRFAppPlatID.aspx";
	private static final String Service_URL ="http://192.168.1.59/XRFAppPlatID.aspx";
	private List<GateWay> netList = null;
	private static GateWayManager GateWayManager = null;
	private CommManager commManager = null;

	private GateWayManager() {
		commManager = CommManager.getInstance();
	};

	public static GateWayManager getInstance() {
		if (GateWayManager == null) {
			GateWayManager = new GateWayManager();
		}
		return GateWayManager;
	}

	// 获取所有网关信息
	@SuppressWarnings("unchecked")
	public List<GateWay> getAllNetgate(String UserId, String Password) throws IOException {
		List<GateWay> result = null;
		if (netList != null) {
			return netList;
		}
		String order = JsonUtil.getJsonRequestParams(
				OrderFlag.NetGateGetAllInfo, UserId, Password, null);
		
		String response = commManager.sendDataPost(order,
				commManager.Service_URL);
		//Log.e("1111111", response);
		List<LinkedHashMap<String, String>> lstnew = new ArrayList<LinkedHashMap<String, String>>();
		lstnew = (List<LinkedHashMap<String, String>>) JsonUtil.JsonToObject(
				response, lstnew.getClass());
		if (lstnew != null) {
			netList = new ArrayList<GateWay>();
			result = new ArrayList<GateWay>();
			for (int i = 0; i < lstnew.size(); i++) {
				GateWay netgate = new GateWay();
				netgate = ToNetgate(lstnew.get(i));
				result.add(netgate);
			}
			netList = result;
		}
		return result;
	}
	
	// 获取所有网关信息
		@SuppressWarnings("unchecked")
		public List<GateWay> getAllChangeNetgate(String UserId, String Password) {
			List<GateWay> result = null;
			if (netList != null) {
				netList.clear();
			}
			String order = JsonUtil.getJsonRequestParams(
					OrderFlag.NetGateGetAllInfo, UserId, Password, null);
			String response = null;
			try {
				response = commManager.sendDataPost(order,
						commManager.Service_URL);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Log.e("1111111", response);
			List<LinkedHashMap<String, String>> lstnew = new ArrayList<LinkedHashMap<String, String>>();
			lstnew = (List<LinkedHashMap<String, String>>) JsonUtil.JsonToObject(
					response, lstnew.getClass());
			if (lstnew != null) {
				netList = new ArrayList<GateWay>();
				result = new ArrayList<GateWay>();
				for (int i = 0; i < lstnew.size(); i++) {
					GateWay netgate = new GateWay();
					netgate = ToNetgate(lstnew.get(i));
					result.add(netgate);
				}
				netList = result;
			}
			return result;
		}

	public static GateWay ToNetgate(LinkedHashMap<String, String> lmap) {
		GateWay ng = new GateWay();
		Iterator it = lmap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entity = (Entry) it.next();
			if (entity.getKey().equals("Code")) {
				ng.setCode((int) (Float
						.parseFloat(entity.getValue().toString())));// Integer.parseInt(entity.getValue().toString())
			} else if (entity.getKey().equals("ID")) {
				ng.setId((int) (Float.parseFloat(entity.getValue().toString())));
			} else if (entity.getKey().equals("IPAddress")) {
				ng.setIpAddress(entity.getValue().toString());
			} else if (entity.getKey().equals("Name")) {
				ng.setName(entity.getValue().toString());
			} else if (entity.getKey().equals("Number")) {
				ng.setCid((int) (Float.parseFloat(entity.getValue().toString())));
			} else if (entity.getKey().equals("Online")) {
				ng.setIsOnline(Boolean.parseBoolean(entity.getValue()
						.toString()));
			} else if (entity.getKey().equals("Port")) {
				ng.setPort((int) (Float
						.parseFloat(entity.getValue().toString())));
			} else if (entity.getKey().equals("TimeTicks")) {
				ng.setTimeTick((int) (Float.parseFloat(entity.getValue()
						.toString())));
			} else {
				continue;
			}
		}
		return ng;

	}

}
