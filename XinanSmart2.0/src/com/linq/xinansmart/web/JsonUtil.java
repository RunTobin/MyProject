package com.linq.xinansmart.web;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonUtil {

	// �ö������ڷ�װ����API�Ĳ���.
	// ����ʱ�Ὣ�ö���ת��ΪJSON��ʽ������
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
		// / �豸��Ϣ����
		// / </summary>
		public static final String EquipmentInfoUpdate = "Equipment.InfoUpdate";
		// / <summary>
		// / �豸ֵ����
		// / </summary>
		public static final String EquipmentValueChange = "Equipment.ValueChange";
		// / <summary>
		// / ��ȡ�����豸��Ϣ
		// / </summary>
		public static final String EuqipmentGetAllInfo = "Euqipment.GetAllInfo";
		// / <summary>
		// / ˢ�������豸״̬
		// / </summary>
		public static final String EquipmentRefreshAllInfo = "Equipment.RefreshAllInfo";
		// / <summary>
		// / ������Ϣ����
		// / </summary>
		public static final String NetGateInfoUpdate = "NetGate.InfoUpdate";
		// / <summary>
		// / ��ȡ����������Ϣ
		// / </summary>
		public static final String NetGateGetAllInfo = "NetGate.GetAllInfo";
		// / <summary>
		// / �����豸��
		// / </summary>
		public static final String EquipmentGroupNew = "EquipmentGroup.New";
		// / <summary>
		// / �����豸����Ϣ
		// / </summary>
		public static final String EquipmentGroupUpdate = "EquipmentGroup.Update";
		// / <summary>
		// / �豸����Ϣɾ��
		// / </summary>
		public static final String EquipmentGroupDelete = "EquipmentGroup.Delete";
		// / <summary>
		// / ����豸����ϸ
		// / </summary>
		public static final String EquipmentGroupAddDetail = "EquipmentGroup.AddDetail";
		// / <summary>
		// / ɾ���豸����ϸ
		// / </summary>
		public static final String EquipmentGroupDeleteDetail = "EquipmentGroup.DeleteDetail";
		// / <summary>
		// / ��ȡ�����豸��
		// / </summary>
		public static final String EquipmentGroupGetAllInfo = "EquipmentGroup.GetAllInfo";
		// / <summary>
		// / ��ȡ����Ӧ�ó���
		// / </summary>
		public static final String AppProfileGetAllInfo = "AppProfile.GetAllInfo";
		// / <summary>
		// / ���Ӧ�ó���
		// / </summary>
		public static final String AppProfileNew = "AppProfile.New";
		// / <summary>
		// / ����Ӧ�ó�����Ϣ
		// / </summary>
		public static final String AppProfileUpdate = "AppProfile.Update";
		// / <summary>
		// / ɾ��Ӧ�ó���
		// / </summary>
		public static final String AppProfileDelete = "AppProfile.Delete";
		// / <summary>
		// / ��ӳ�����ϸ
		// / </summary>
		public static final String AppProfileAddDetail = "AppProfile.AddDetail";
		// / <summary>
		// / �༭������ϸ
		// / </summary>
		public static final String AppProfileEditDetail = "AppProfile.EditDetail";
		// / <summary>
		// / ɾ��������ϸ
		// / </summary>
		public static final String AppProfileDeleteDetail = "AppProfile.DeleteDetail";
		// / <summary>
		// / �����޸�
		// / </summary>
		public static final String UserPasswordEdit = "UserPassword.Edit";
		// / <summary>
		// / ʹ��Ӧ�ó���
		// / </summary>
		public static final String AppProfileUsing = "AppProfile.Using";
		// / <summary>
		// / ɾ�������豸
		// / </summary>
		public static final String RemoveDownlineEquipment = "Equipment.RemoveDownline";

		// / <summary>
		// / �Զ�����
		// / </summary>
		public static final String versionUpdate = "APP.VersionUpdate";
	}

	// ������Ĳ�����װ��JSON�ĸ�ʽ
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

			// ������ת��ΪJSON����
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
