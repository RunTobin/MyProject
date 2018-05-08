package com.linq.xinansmart.web;



import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonTool {

	// ï¿½Ã¶ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ú·ï¿½×°ï¿½ï¿½ï¿½ï¿½APIï¿½Ä²ï¿½ï¿½ï¿½.
	// ï¿½ï¿½ï¿½ï¿½Ê±ï¿½á½«ï¿½Ã¶ï¿½ï¿½ï¿½×ªï¿½ï¿½ÎªJSONï¿½ï¿½Ê½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
	static class JsonRequestParams {
		String Order;
	}
	
	/*public class OrderFlag 
	{
		public static final String versionUpdate  = "APP.VersionUpdate";
	}*/
	
	//×ªï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ 
	
	
	
	
	
	/**
	 * ´Ë·½·¨½«string×Ö·û´®×ª³ÉJsonRequestParamsÖÐ¶ÔÏóµÄÊôÐÔ ÔÚÍ¨¹ýGson.toJson(Obj)·½·¨×ª³Éjson´®
	 * 
	 * 
	 * @param orderFlag ÃüÁî×Ö Îª×Ö·û´®
	 * @return json ´®
	 */
	public static String getJsonRequestParams(String orderFlag) 
	{
		try 
		{
			Gson gson = new Gson();
			JsonRequestParams jsonRequestParams = new JsonRequestParams();
			jsonRequestParams.Order = orderFlag;
			
			// ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½×ªï¿½ï¿½ÎªJSONï¿½ï¿½ï¿½ï¿½
			String jsonRequestParamsString = gson.toJson(jsonRequestParams);
			return jsonRequestParamsString;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	//json×ªï¿½ï¿½Îªï¿½ï¿½ï¿½ï¿½
	public static String[] JsonToObject(String jsonText, Type type) 
	{

		String[] strs = new String[5];
		String str = jsonText.replaceAll("[{*}]", "");
		strs = str.split(",");
		return strs;
	}
		
}
