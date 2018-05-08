package com.linq.xinansmart.web;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonUtil {

	// 该对象用于封装请求API的参数.
	// 请求时会将该对象转换为JSON格式的数据
	static class JsonRequestParams {
		String Order;
		String userID;
		String userPwd;
		String Data;
		String CheckCode;
	}

	static class RefreshOrder {
		public int Tick;
	}

	static class StrObject {
		public String Value;
	}

	public class OrderFlag {
		// / <summary>
		// / 设备信息更新
		// / </summary>
		public static final String EquipmentInfoUpdate = "Equipment.InfoUpdate";
		// / <summary>
		// / 设备值设置
		// / </summary>
		public static final String EquipmentValueChange = "Equipment.ValueChange";
		// / <summary>
		// / 获取所有设备信息
		// / </summary>
		public static final String EuqipmentGetAllInfo = "Euqipment.GetAllInfo";
		// / <summary>
		// / 刷新所有设备状态
		// / </summary>
		public static final String EquipmentRefreshAllInfo = "Equipment.RefreshAllInfo";
		// / <summary>
		// / 网关信息更新
		// / </summary>
		public static final String NetGateInfoUpdate = "NetGate.InfoUpdate";
		// / <summary>
		// / 获取所有网关信息
		// / </summary>
		public static final String NetGateGetAllInfo = "NetGate.GetAllInfo";
		// / <summary>
		// / 新增设备组
		// / </summary>
		public static final String EquipmentGroupNew = "EquipmentGroup.New";
		// / <summary>
		// / 更新设备组信息
		// / </summary>
		public static final String EquipmentGroupUpdate = "EquipmentGroup.Update";
		// / <summary>
		// / 设备组信息删除
		// / </summary>
		public static final String EquipmentGroupDelete = "EquipmentGroup.Delete";
		// / <summary>
		// / 添加设备组明细
		// / </summary>
		public static final String EquipmentGroupAddDetail = "EquipmentGroup.AddDetail";
		// / <summary>
		// / 删除设备组明细
		// / </summary>
		public static final String EquipmentGroupDeleteDetail = "EquipmentGroup.DeleteDetail";
		// / <summary>
		// / 获取所有设备组
		// / </summary>
		public static final String EquipmentGroupGetAllInfo = "EquipmentGroup.GetAllInfo";
		// / <summary>
		// / 获取所有应用场景
		// / </summary>
		public static final String AppProfileGetAllInfo = "AppProfile.GetAllInfo";
		// / <summary>
		// / 添加应用场景
		// / </summary>
		public static final String AppProfileNew = "AppProfile.New";
		// / <summary>
		// / 更新应用场景信息
		// / </summary>
		public static final String AppProfileUpdate = "AppProfile.Update";
		// / <summary>
		// / 删除应用场景
		// / </summary>
		public static final String AppProfileDelete = "AppProfile.Delete";
		// / <summary>
		// / 添加场景明细
		// / </summary>
		public static final String AppProfileAddDetail = "AppProfile.AddDetail";
		// / <summary>
		// / 编辑场景明细
		// / </summary>
		public static final String AppProfileEditDetail = "AppProfile.EditDetail";
		// / <summary>
		// / 删除场景明细
		// / </summary>
		public static final String AppProfileDeleteDetail = "AppProfile.DeleteDetail";
		// / <summary>
		// / 密码修改
		// / </summary>
		public static final String UserPasswordEdit = "UserPassword.Edit";
		// / <summary>
		// / 使用应用场景
		// / </summary>
		public static final String AppProfileUsing = "AppProfile.Using";
		// / <summary>
		// / 删除离线设备
		// / </summary>
		public static final String RemoveDownlineEquipment = "Equipment.RemoveDownline";

		// / <summary>
		// / 自动升级
		// / </summary>
		public static final String versionUpdate = "APP.VersionUpdate";
	}

	// 将请求的参数封装成JSON的格式
	public static String getJsonRequestParams(String orderFlag, String userID,
			String userPwd, Object Data) {
		try {
			Gson gson = new Gson();
			JsonRequestParams jsonRequestParams = new JsonRequestParams();
			jsonRequestParams.Order = orderFlag;
			jsonRequestParams.userID = userID;
			jsonRequestParams.userPwd = userPwd;
			if (Data == null)
				jsonRequestParams.Data = "";
			else
				jsonRequestParams.Data = gson.toJson(Data);
			jsonRequestParams.CheckCode = "";

			// 将对象转换为JSON数据
			String jsonRequestParamsString = gson.toJson(jsonRequestParams);
			return jsonRequestParamsString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static String ObjectToJson(Object objData) {
		Gson gson = new Gson();
		return gson.toJson(objData);
	}

	public static Object JsonToObject(String jsonText, Type type) {

		Gson gson = new Gson();
		try {
			Object result = gson.fromJson(jsonText, type);
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
