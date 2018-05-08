package com.tutk.P2PCam264;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

public class DatabaseManager {

	public static final String TABLE_DEVICE = "device";
	public static final String TABLE_SEARCH_HISTORY = "search_history";
	public static final String TABLE_SNAPSHOT = "snapshot";
	public static final String TABLE_REMOVE_LIST = "remove_list";
	public static final String s_GCM_PHP_URL = "http://push.iotcplatform.com/apns/apns.php";
	public static final String s_GCM_SYNC_PHP_URL = "http://push.iotcplatform.com/apns/sync.php";
	public static final String s_Package_name = "com.tutk.p2pcamlive.2(Android)";
	public static final String s_GCM_sender = "935793047540";
	//public static String s_GCM_IMEI = "";
	public static String s_GCM_token;
	public static int n_mainActivity_Status = 0;
	
	private DatabaseHelper mDbHelper;
	private Context mcontext=null;
	public DatabaseManager(Context context) {
		mDbHelper = new DatabaseHelper(context);
		mcontext = context;
	}

	public SQLiteDatabase getReadableDatabase() {
		return mDbHelper.getReadableDatabase();
	}

	
	
	public static String uid_Produce(Context context)
	{
		
		SharedPreferences settings = context.getSharedPreferences("setting", 0);
		if(settings.getString("device_imei","").equals("")==true)
		{
			String serial_number = "";
			if (Build.VERSION.SDK_INT >= 9) 
			{
				serial_number = android.os.Build.SERIAL;
			}
			String mac_address="";
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			if(wifiManager!=null)
			{
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				if(wifiInfo!=null)
				{
					mac_address = wifiInfo.getMacAddress();
				}
			}
			if((serial_number==null||serial_number.equals("")))
			{
				int rendom_number = (int)(Math.random() * 1+1)+(int)(Math.random() * 10+1)+(int)(Math.random() * 100+1)+(int)(Math.random() * 1000+1)+(int)(Math.random() * 10000+1)+(int)(Math.random() * 100000+1)+(int)(Math.random() * 1000000+1)+(int)(Math.random() * 10000000+1);
				if(rendom_number<10000000)
				{
					rendom_number=rendom_number+10000000;
				}
				serial_number=String.valueOf(rendom_number);
				
			}
			if((mac_address==null||mac_address.equals("")))
			{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");

				Date curDate = new Date(System.currentTimeMillis()) ;

				mac_address = formatter.format(curDate);
				//mac_address=String.valueOf((int)(Math.random() * 1+1)+(int)(Math.random() * 10+1)+(int)(Math.random() * 100+1)+(int)(Math.random() * 1000+1)+(int)(Math.random() * 10000+1)+(int)(Math.random() * 100000+1)+(int)(Math.random() * 1000000+1)+(int)(Math.random() * 10000000+1));
			}
			
			mac_address = mac_address.replace(":","");
			String[] new_mac_address = new String[2];
			new_mac_address[0]=mac_address.substring(0,6);
			new_mac_address[1]=mac_address.substring(6);
			String[] new_serial_number = new String[2];
			new_serial_number[0]=serial_number.substring(0,4);
			new_serial_number[1]=serial_number.substring(4);
			settings.edit().putString("device_imei","AN"+new_mac_address[0]+new_serial_number[1]+new_mac_address[1]+new_serial_number[0]).commit();
			
			return "AN"+new_mac_address[0]+new_serial_number[1]+new_mac_address[1]+new_serial_number[0];
		}
		else
		{
			return settings.getString("device_imei","");
		}
			

		
	}

	public static String get_language()
	{
		 return Locale.getDefault().getLanguage();
	}
	
	public static String get_osver()
	{
		 return android.os.Build.VERSION.RELEASE;
	}
	
	public static String get_appver(Context context)
	{
		 String versionName = "";  
	     try 
	     {  
	    	 PackageManager packageManager = context.getPackageManager();  
	         PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);  
	         versionName = packageInfo.versionName;  
	         if (TextUtils.isEmpty(versionName)) 
	         {  
	             return "";  
	         }  
	     }
	     catch (Exception e)
	     {  
	         e.printStackTrace();  
	     }  
	     return versionName;  
	}
	
	public static String get_model()
	{
		 return android.os.Build.MODEL.replace(" ","%20");
	}
	
	
	public long addDevice(String dev_nickname, String dev_uid, String dev_name, String dev_pwd, String view_acc, String view_pwd, int event_notification, int channel) {

		if(mcontext!=null)
		{
			SharedPreferences settings = mcontext.getSharedPreferences("Preference", 0);
			settings.edit().putString(dev_uid,dev_uid).commit();
		}
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("dev_nickname", dev_nickname);
		values.put("dev_uid", dev_uid);
		values.put("dev_name", dev_name);
		values.put("dev_pwd", dev_pwd);
		values.put("view_acc", view_acc);
		values.put("view_pwd", view_pwd);
		values.put("event_notification", event_notification);
		values.put("camera_channel", channel);

		long ret = db.insertOrThrow(TABLE_DEVICE, null, values);
		db.close();

		return ret;
	}
	public long add_remove_list(String dev_uid) 
	{

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("uid", dev_uid);
		long ret = db.insertOrThrow(TABLE_REMOVE_LIST, null, values);
		db.close();
		return ret;
	}
	

	public void delete_remove_list(String dev_uid) 
	{

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		db.delete(TABLE_REMOVE_LIST, "uid = '" + dev_uid + "'", null);
		db.close();
	}

	public long addSnapshot(String dev_uid, String file_path, long time) {

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("dev_uid", dev_uid);
		values.put("file_path", file_path);
		values.put("time", time);

		long ret = db.insertOrThrow(TABLE_SNAPSHOT, null, values);
		db.close();

		return ret;
	}

	public void updateDeviceInfoByDBID(long db_id, String dev_uid, String dev_nickname, String dev_name, String dev_pwd, String view_acc, String view_pwd, int event_notification, int channel) {

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("dev_uid", dev_uid);
		values.put("dev_nickname", dev_nickname);
		values.put("dev_name", dev_name);
		values.put("dev_pwd", dev_pwd);
		values.put("view_acc", view_acc);
		values.put("view_pwd", view_pwd);
		values.put("event_notification", event_notification);
		values.put("camera_channel", channel);
		db.update(TABLE_DEVICE, values, "_id = '" + db_id + "'", null);
		db.close();
	}

	public void updateDeviceAskFormatSDCardByUID(String dev_uid, boolean askOrNot) {

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("ask_format_sdcard", askOrNot ? 1 : 0);
		db.update(TABLE_DEVICE, values, "dev_uid = '" + dev_uid + "'", null);
		db.close();
	}

	public void updateDeviceChannelByUID(String dev_uid, int channelIndex) {

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("camera_channel", channelIndex);
		db.update(TABLE_DEVICE, values, "dev_uid = '" + dev_uid + "'", null);
		db.close();
	}

	public void updateDeviceSnapshotByUID(String dev_uid, Bitmap snapshot) {

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("snapshot", getByteArrayFromBitmap(snapshot));
		db.update(TABLE_DEVICE, values, "dev_uid = '" + dev_uid + "'", null);
		db.close();
	}

	public void updateDeviceSnapshotByUID(String dev_uid, byte[] snapshot) {

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("snapshot", snapshot);
		db.update(TABLE_DEVICE, values, "dev_uid = '" + dev_uid + "'", null);
		db.close();
	}

	public void removeDeviceByUID(String dev_uid) {

		if(mcontext!=null)
		{
			SharedPreferences settings = mcontext.getSharedPreferences("Preference", 0);
			settings.edit().putString(dev_uid,"").commit();
		}
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		db.delete(TABLE_DEVICE, "dev_uid = '" + dev_uid + "'", null);
		db.close();
	}

	public void removeSnapshotByUID(String dev_uid) {

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		db.delete(TABLE_SNAPSHOT, "dev_uid = '" + dev_uid + "'", null);
		db.close();
	}

	public static byte[] getByteArrayFromBitmap(Bitmap bitmap) {

		if (bitmap != null && !bitmap.isRecycled()) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.PNG, 0, bos);
			return bos.toByteArray();
		} else {
			return null;
		}
	}

	public static BitmapFactory.Options getBitmapOptions(int scale) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inSampleSize = scale;

		try {
			BitmapFactory.Options.class.getField("inNativeAlloc").setBoolean(options, true);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return options;
	}

	public static Bitmap getBitmapFromByteArray(byte[] byts) {

		InputStream is = new ByteArrayInputStream(byts);
		return BitmapFactory.decodeStream(is, null, getBitmapOptions(2));
	}

	public long addSearchHistory(String dev_uid, int eventType, long start_time, long stop_time) {

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("dev_uid", dev_uid);
		values.put("search_event_type", eventType);
		values.put("search_start_time", start_time);
		values.put("search_stop_time", stop_time);

		long ret = db.insertOrThrow(TABLE_SEARCH_HISTORY, null, values);
		db.close();

		return ret;
	}

	private class DatabaseHelper extends SQLiteOpenHelper {

		private static final String DB_FILE = "IOTCamViewer.db";
		private static final int DB_VERSION = 7;

		private static final String SQLCMD_CREATE_TABLE_DEVICE = "CREATE TABLE " + TABLE_DEVICE + "(" + "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + "dev_nickname			NVARCHAR(30) NULL, " + "dev_uid				VARCHAR(20) NULL, " + "dev_name				VARCHAR(30) NULL, " + "dev_pwd				VARCHAR(30) NULL, "
				+ "view_acc				VARCHAR(30) NULL, " + "view_pwd				VARCHAR(30) NULL, " + "event_notification 	INTEGER, " + "ask_format_sdcard		INTEGER," + "camera_channel			INTEGER, " + "snapshot				BLOB" + ");";

		private static final String SQLCMD_CREATE_TABLE_SEARCH_HISTORY = "CREATE TABLE " + TABLE_SEARCH_HISTORY + "(" + "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + "dev_uid			VARCHAR(20) NULL, " + "search_event_type	INTEGER, " + "search_start_time	INTEGER, " + "search_stop_time	INTEGER" + ");";

		private static final String SQLCMD_CREATE_TABLE_SNAPSHOT = "CREATE TABLE " + TABLE_SNAPSHOT + "(" + "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + "dev_uid			VARCHAR(20) NULL, " + "file_path			VARCHAR(80), " + "time				INTEGER" + ");";

		private static final String SQLCMD_CREATE_TABLE_REMOVE_LIST = "CREATE TABLE " + TABLE_REMOVE_LIST + "(" + "uid VARCHAR(20) NOT NULL PRIMARY KEY " +")";	
		
		private static final String SQLCMD_DROP_TABLE_DEVICE = "drop table if exists " + TABLE_DEVICE + ";";

		private static final String SQLCMD_DROP_TABLE_SEARCH_HISTORY = "drop table if exists " + TABLE_SEARCH_HISTORY + ";";

		private static final String SQLCMD_DROP_TABLE_SNAPSHOT = "drop table if exists " + TABLE_SNAPSHOT + ";";

		private static final String SQLCMD_DROP_TABLE_REMOVE_LIST = "drop table if exists " + TABLE_REMOVE_LIST + ";";	
		
		public DatabaseHelper(Context context) {
			super(context, DB_FILE, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(SQLCMD_CREATE_TABLE_DEVICE);
			db.execSQL(SQLCMD_CREATE_TABLE_SEARCH_HISTORY);
			db.execSQL(SQLCMD_CREATE_TABLE_SNAPSHOT);
			db.execSQL(SQLCMD_CREATE_TABLE_REMOVE_LIST);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			//db.execSQL(SQLCMD_DROP_TABLE_DEVICE);
			//db.execSQL(SQLCMD_DROP_TABLE_SEARCH_HISTORY);
			//db.execSQL(SQLCMD_DROP_TABLE_SNAPSHOT);
			//db.execSQL(SQLCMD_DROP_TABLE_REMOVE_LIST);
			if(oldVersion == 6)
				db.execSQL(SQLCMD_CREATE_TABLE_REMOVE_LIST);
			
			//onCreate(db);
		}

	}
}
