package com.tutk.P2PCam264;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tutk.Logger.Glog;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;
import android.util.Log;

public class ThreadTPNS extends Thread {
	IOTC_GCM_IntentService mIotc_GCM_IntentService;
	Context mcontext = null;
	String mRegistrationId = null;
	int type = 0;
	int mReg = 0; // 0 reg; 1 unreg;
	String mUID = "";
	Activity mActivity = null;
	

	public ThreadTPNS(IOTC_GCM_IntentService iotc_GCM_IntentService,Context context,String registrationId) {
//		mActivity = activity;
		mIotc_GCM_IntentService = iotc_GCM_IntentService;
		mcontext = context;
		mRegistrationId = registrationId;
		type = 2;
		mReg = 0;
	}
	public ThreadTPNS(Activity activity,String uid, int t) {
		mActivity = activity;
		mRegistrationId = com.tutk.P2PCam264.DatabaseManager.s_GCM_token;
		type = 1;
		mReg = 2;
		mUID = uid;
	}
	public ThreadTPNS(Context context,String uid, int t) {
		mcontext = context;
		mRegistrationId = com.tutk.P2PCam264.DatabaseManager.s_GCM_token;
		type = 1;
		mReg = 2;
		mUID = uid;
	}
	public ThreadTPNS(Activity activity,String uid) {
		mActivity = activity;
		mRegistrationId = com.tutk.P2PCam264.DatabaseManager.s_GCM_token;
		type = 1;
		mReg = 1;
		mUID = uid;
	}
	public ThreadTPNS(Context context,String uid) {
		mcontext = context;
		mRegistrationId = com.tutk.P2PCam264.DatabaseManager.s_GCM_token;
		type = 1;
		mReg = 1;
		mUID = uid;
	}
	
	@Override
	public void run() {
		
		try{
			
	        InetAddress[] inetAddr = InetAddress.getAllByName("push.iotcplatform.com");

	        String ipAddr = "";
	        String[] ipList = new String[inetAddr.length];
	        
	        for(int j = 0; j < inetAddr.length;j++){
	        	
	        	byte[] address = inetAddr[j].getAddress();
		        for (int i = 0; i < address.length; i++) {
		
		            if (i > 0) {
		                ipAddr += ".";
		            }
		            ipAddr += address[i] & 0xFF;
		        }
		        ipList[j] = ipAddr;
		        ipAddr = "";
	        }
	        
	       
	      //  System.out.println("IP Address  end" + ipList);
	        
	        for(int i = 0;i< ipList.length; i++)
	        {	
	        	boolean checkSend = false;
	        	// http位址
	        	String httpUrl = ipList[i];
	        	// HttpGet連線物件
	        	 Log.e("IP Address  end", httpUrl);
	        	try {

	        	DefaultHttpClient http = new DefaultHttpClient();
	        	HttpGet httpMethod = new HttpGet();
	        	httpMethod.setURI(new URI("http://" + httpUrl + "/apns/apns.php?cmd=hello"));
	        	HttpResponse response = http.execute(httpMethod);
	        	
	        	int responseCode = response.getStatusLine().getStatusCode();
	        	switch(responseCode)
	        	{
	        	    case 200:
	        	        HttpEntity entity = response.getEntity();
			        	if(entity != null)
			        	{
			        		String responseBody = EntityUtils.toString(entity);
			        		 Log.e("responseBody", responseBody);
			        	    String udid = "";
			        	    String appvar="";
			        	    if(mcontext!=null)
			        	    {
			        	    	udid = DatabaseManager.uid_Produce(mcontext);
			        	    	appvar = DatabaseManager.get_appver(mcontext);
			        	    }
			        	    else
			        	    {
			        	    	udid = DatabaseManager.uid_Produce(mActivity);
			        	    	appvar = DatabaseManager.get_appver(mActivity);
			        	    	 Log.e("udid", udid);
			        	    	 Log.e("appvar", appvar);
			        	    }
			        		if(responseBody.indexOf("hi")!=-1)
			        		{
			        			TelephonyManager tm = null;
			        			if(type == 2){
			        				tm = (TelephonyManager) mIotc_GCM_IntentService.getSystemService(Context.TELEPHONY_SERVICE);
			        				mHttpGetTool("http://" + httpUrl + "/apns/apns.php?cmd=reg_client&token="+com.tutk.P2PCam264.DatabaseManager.s_GCM_token+"&appid="+com.tutk.P2PCam264.DatabaseManager.s_Package_name+"&dev=0"+"&udid="+udid+"&os=android"+"&lang="+com.tutk.P2PCam264.DatabaseManager.get_language()+"&osver="+com.tutk.P2PCam264.DatabaseManager.get_osver()+"&appver="+appvar+"&model="+com.tutk.P2PCam264.DatabaseManager.get_model());
			        				mHttpPostsyncApiTool("http://" + httpUrl + "/apns/apns.php",mcontext,udid,DatabaseManager.s_GCM_token,DatabaseManager.s_Package_name,"android");
			        			}else if(type == 1){
			        				if(mReg == 1)
				        				mHttpGetTool("http://" + httpUrl + "/apns/apns.php"+"?cmd=unreg_mapping"+"&appid="+com.tutk.P2PCam264.DatabaseManager.s_Package_name+"&uid="+mUID+"&udid="+udid+"&os=android",mUID);

				        			if(mReg == 2)
				        				mHttpGetTool("http://" + httpUrl + "/apns/apns.php"+"?cmd=reg_mapping"+"&appid="+com.tutk.P2PCam264.DatabaseManager.s_Package_name+"&uid="+mUID+"&udid="+udid+"&os=android");
			        			
			        			}
			        		    checkSend = true;
			        		}
			        	}
	        	        break;
	        	}
	        	

        		} catch (ClientProtocolException e) {
        			Glog.I("tpns", "error"+e.toString());
        			AddRemoveList();

        		} catch (IOException e) {
        			Glog.I("tpns", "error"+e.toString());
        			AddRemoveList();

        		} catch (Exception e) {
        			Glog.I("tpns", "error"+e.toString());
        			AddRemoveList();
        		}
	        	
	        	
	        	if(checkSend == true)
	        		break;
	        }

		}
	    catch (UnknownHostException e) {
	        System.out.println("Host not found: " + e.getMessage());
	    }
	}

	public void AddRemoveList()
	{
		if(mReg == 1)
		{
			try 
			{
				if(mcontext!=null)
				{
					DatabaseManager manager = new DatabaseManager(mcontext);
					manager.add_remove_list(mUID);
				}
				else
				{
					DatabaseManager manager = new DatabaseManager(mActivity);
					manager.add_remove_list(mUID);
				}
			}
			catch (Exception e)
			{
				Glog.I("tpns", "error"+e.toString());
			}
		}
	}

	public void mHttpGetTool(String from,String uid){
		
		String response=null;
		DefaultHttpClient httpClient =new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(from);
	    httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
	    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    
		try 
		{
			response = httpClient.execute(httpGet, responseHandler);
			
			if(from.indexOf("cmd=unreg_mapping")!=-1)
			{
				if(response.indexOf("unregistered successfully.")==-1)
				{
					if(mcontext!=null)
					{
						DatabaseManager manager = new DatabaseManager(mcontext);
						manager.add_remove_list(uid);
					}
					else
					{
						DatabaseManager manager = new DatabaseManager(mActivity);
						manager.add_remove_list(uid);
					}
				}
				else
				{
					if(mcontext!=null)
					{
						DatabaseManager manager = new DatabaseManager(mcontext);
						manager.delete_remove_list(uid);
					}
					else
					{
						DatabaseManager manager = new DatabaseManager(mActivity);
						manager.delete_remove_list(uid);
					}
				}
			}
			
		}
		catch (Exception e)
		{
			response=null;
			System.out.println("error");
		}
	}
	public void mHttpGetTool(String from){
		
		String response=null;
		DefaultHttpClient httpClient =new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(from);
	    httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
	    ResponseHandler<String> responseHandler = new BasicResponseHandler();
	    
		try 
		{
			response = httpClient.execute(httpGet, responseHandler);
			Log.e("response",response);
		}
		catch (Exception e)
		{
			response=null;
			System.out.println("error");
		}
	}
	public void mHttpPostsyncApiTool(String fromURL,Context context,String uid,String token,String appid,String os){
		
		
		
		
		JSONObject SyncApiObject = getSyncApiObject(context,uid,token,appid,os);
	    HttpPost httpRequest;
		httpRequest=new HttpPost(fromURL);
		httpRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
		StringEntity se = null;
		try {
			se = new StringEntity(SyncApiObject.toString());
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		httpRequest.setEntity(se);
    
		try 
		{
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);	
			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			Glog.I("tpns", "syncapi" +" response " +retSrc);
		}
		catch (Exception e)
		{
			Glog.I("tpns", "error"+e.toString());
			System.out.println("error");
		}
	}
	
	private JSONObject getSyncApiObject(Context mContext,String udid,String token,String appid,String os)
	{
        JSONObject jsonObject = new JSONObject();
        try 
        {
        	jsonObject.put("cmd","sync");
			jsonObject.put("udid",udid);
			jsonObject.put("token",token);
			jsonObject.put("appid",appid);
			jsonObject.put("os",os);
			
			JSONArray jsonArray = new JSONArray();
			
			DatabaseManager manager = new DatabaseManager(mContext);
			SQLiteDatabase db = manager.getReadableDatabase();
			Cursor cursor = db.query(DatabaseManager.TABLE_DEVICE, new String[] { "_id", "dev_uid"}, null, null, null, null, "_id LIMIT " + MainActivity.CAMERA_MAX_LIMITS);

			while (cursor.moveToNext()) 
			{
				 JSONObject muidObject = new JSONObject();
				 muidObject.put("uid", cursor.getString(1));
				 muidObject.put("interval","0");
		         jsonArray.put(muidObject);
			}
			cursor.close();
			db.close();
			jsonObject.put("map",jsonArray);

			
		} catch (JSONException e) 
		{
			e.printStackTrace();
		}
		
		return jsonObject;
	}
}
