package com.linq.xinansmart.web;



import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonTool {

	// �ö������ڷ�װ����API�Ĳ���.
	// ����ʱ�Ὣ�ö���ת��ΪJSON��ʽ������
	static class JsonRequestParams {
		String Order;
	}
	
	/*public class OrderFlag 
	{
		public static final String versionUpdate  = "APP.VersionUpdate";
	}*/
	
	//ת���������� 
	
	
	
	
	
	/**
	 * �˷�����string�ַ���ת��JsonRequestParams�ж�������� ��ͨ��Gson.toJson(Obj)����ת��json��
	 * 
	 * 
	 * @param orderFlag ������ Ϊ�ַ���
	 * @return json ��
	 */
	public static String getJsonRequestParams(String orderFlag) 
	{
		try 
		{
			Gson gson = new Gson();
			JsonRequestParams jsonRequestParams = new JsonRequestParams();
			jsonRequestParams.Order = orderFlag;
			
			// ������ת��ΪJSON����
			String jsonRequestParamsString = gson.toJson(jsonRequestParams);
			return jsonRequestParamsString;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	//jsonת��Ϊ����
	public static String[] JsonToObject(String jsonText, Type type) 
	{

		String[] strs = new String[5];
		String str = jsonText.replaceAll("[{*}]", "");
		strs = str.split(",");
		return strs;
	}
		
}
