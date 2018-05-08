package com.tutk.P2PCam264;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PowerManager;

import com.linq.xinansmart.R;
import com.tutk.Logger.Glog;

public class IOTC_GCM_IntentService extends IntentService 
{

	    public IOTC_GCM_IntentService() 
	    {
	    	super("MuazzamService");
	    }
	    public static Context mcontext;
	    public static boolean startRun= false;
		private static PowerManager.WakeLock sWakeLock;
	    private static final Object LOCK = IOTC_GCM_IntentService.class;
		private Thread thread;
	    
	    static void runIntentInService(Context context, Intent intent) 
	    {
	    	synchronized(LOCK) 
	        {
	            if (sWakeLock == null) 
	            {
	                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	                sWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "my_wakelock");
	            }
	        }
	        sWakeLock.acquire();
	        intent.setClassName(context, IOTC_GCM_IntentService.class.getName());
	    	mcontext=context;
	        context.startService(intent);
	    }
	    
	    @Override
	    public final void onHandleIntent(Intent intent) 
	    {
	        try 
	        {
	            String action = intent.getAction();
	            if (action.equals("com.google.android.c2dm.intent.REGISTRATION")) 
	            {
	                handleRegistration(intent);
	            } else if (action.equals("com.google.android.c2dm.intent.RECEIVE")) 
	            {
	            	handleMessage(intent);
	            }
	        } finally 
	        {
	            synchronized(LOCK) 
	            {
	                sWakeLock.release();
	            }
	        }
	    }
	    private void handleRegistration(Intent intent) 
	    {
	        String registrationId = intent.getStringExtra("registration_id");
	        MainActivity.token = registrationId;
	        String error = intent.getStringExtra("error");
	        String unregistered = intent.getStringExtra("unregistered");       
	        if (registrationId != null) 
	        {
	        	thread = new ThreadTPNS(IOTC_GCM_IntentService.this,mcontext,registrationId);
				thread.start();
		        HttpGetTool HttpGetTool=new HttpGetTool(this,IOTC_GCM_IntentService.class.getName());
		        HttpGetTool.execute(com.tutk.P2PCam264.DatabaseManager.s_GCM_PHP_URL+"?cmd=reg_client&token="+registrationId+"&appid="+com.tutk.P2PCam264.DatabaseManager.s_Package_name+"&dev=0"+"&udid="+com.tutk.P2PCam264.DatabaseManager.uid_Produce(mcontext)+"&os=android"+"&lang="+com.tutk.P2PCam264.DatabaseManager.get_language()+"&osver="+com.tutk.P2PCam264.DatabaseManager.get_osver()+"&appver="+com.tutk.P2PCam264.DatabaseManager.get_appver(mcontext)+"&model="+com.tutk.P2PCam264.DatabaseManager.get_model());
		        com.tutk.P2PCam264.DatabaseManager.s_GCM_token=registrationId;
		        MainActivity.check_mapping_list(mcontext);
	        }
	            
	        if (unregistered != null) 
	        {
	            // get old registration ID from shared preferences
	            // notify 3rd-party server about the unregistered ID
	        } 
	            
	        if (error != null) 
	        {

	    		
	        	if ("SERVICE_NOT_AVAILABLE".equals(error)) 
	            { 
			        Glog.I("GCM", "SERVICE_NOT_AVAILABLE");
	            } 
	        	else 
	            {
			        Glog.I("GCM","Received error: " + error);
	            }
	        }
	    }

	    private void handleMessage(Intent intent) 
	    {
	    	
			String dev_uid = intent.getStringExtra("uid");
			String dev_alert = intent.getStringExtra("alert");
	        SharedPreferences settings = getSharedPreferences("Preference", 0);
	        String uid = settings.getString(dev_uid,"");
	        if(uid.equals(""))
	        {
				thread = new ThreadTPNS(mcontext,dev_uid);
				thread.start();
	        	return;
	        }
			if(com.tutk.P2PCam264.DatabaseManager.n_mainActivity_Status==0)
			{
				showNotification(dev_uid,dev_alert);
			}
			else
			{
				Intent mintent=new Intent(MainActivity.class.getName());
				mintent.putExtra("dev_uid", dev_uid);
				mcontext.sendBroadcast(mintent);
			}
	    }
	    
		@SuppressWarnings("deprecation")
		private void showNotification(String dev_uid,String dev_alert) 
		{

			try {

				NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

				Bundle extras = new Bundle();
				extras.putString("dev_uid", dev_uid);
				Intent intent = new Intent(this, MainActivity.class);
				intent.putExtras(extras);

				PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

				Notification notification = new Notification(R.drawable.nty_alert,dev_alert,0);
				notification.flags |= Notification.FLAG_AUTO_CANCEL;
				notification.flags |= Notification.FLAG_NO_CLEAR;
				notification.setLatestEventInfo(this, getText(R.string.app_name),dev_alert, pendingIntent);

				com.tutk.P2PCam264.DatabaseManager SQLManager = new com.tutk.P2PCam264.DatabaseManager(this);
				SQLiteDatabase db = SQLManager.getReadableDatabase();
				Cursor cursor = db.query(DatabaseManager.TABLE_DEVICE, new String[] { "_id", "dev_nickname", "dev_uid", "dev_name", "dev_pwd", "view_acc", "view_pwd", "event_notification", "camera_channel", "snapshot", "ask_format_sdcard" }, null, null, null, null, "_id LIMIT " + MainActivity.CAMERA_MAX_LIMITS);
				int nNotification = -1;
				while (cursor.moveToNext()) {

					String sDev_uid = cursor.getString(2);
					int event_notification = cursor.getInt(7);
					
					if(dev_uid.equalsIgnoreCase(sDev_uid)){
						nNotification = event_notification;
						break;
					}
				}

				cursor.close();
				db.close();
				if (nNotification == 0)
					notification.defaults = Notification.DEFAULT_LIGHTS;
				else if (nNotification == 1)
					notification.defaults = Notification.DEFAULT_SOUND;
				else if (nNotification == 2)
					notification.defaults = Notification.DEFAULT_VIBRATE;
				else
					notification.defaults = Notification.DEFAULT_ALL;

				notification.setLatestEventInfo(this, getText(R.string.app_name),dev_alert, pendingIntent);
				manager.notify(1, notification);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	    
	    
}