package com.tutk.P2PCam264;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.linq.xinansmart.R;
import com.tutk.IOTC.AVIOCTRLDEFs;
import com.tutk.IOTC.Camera;
import com.tutk.IOTC.IRegisterIOTCListener;
import com.tutk.IOTC.Packet;
import com.tutk.Logger.Glog;

public class AdvancedSettingActivity extends Activity implements IRegisterIOTCListener {

	private static String newPassword;
	private static boolean isModifyPassword = false;
	private static boolean isModifyWiFi = false;

	private LinearLayout pnlVideoQuality;
	private LinearLayout pnlVideoFlip;
	private LinearLayout pnlEnvMode;
	private LinearLayout pnlWiFiSetting;
	private LinearLayout pnlEventSeting;
	private LinearLayout pnlRecordSetting;
	private LinearLayout pnlTimeZone;
	private LinearLayout pnlFormatSDCard;
	private LinearLayout pnlDeviceInfo;

	private Button btnModifySecurityCode;
	private Button btnManageWiFiNetworks;
	private Button btnFormatSDCard;
	private Button btnOK;
	private Button btnCancel;

	private MyCamera mCamera;
	private DeviceInfo mDevice;

	private TextView txtWiFiSSID;
	private TextView txtWiFiStatus;
	private TextView txtDeviceModel;
	private TextView txtDeviceVersion;
	private TextView txtVenderName;
	private TextView txtStorageTotalSize;
	private TextView txtStorageFreeSize;

	private Spinner spinVideoQuality;
	private Spinner spinVideoFlip;
	private Spinner spinEnvironmentMode;
	private Spinner spinMotionDetection;
	private Spinner spinEventNotification;
	private Spinner spinRecordingMode;
	private Spinner spinTimeZone;

	private int mVideoQuality = -1;
	private int mVideoFlip = -1;
	private int mEnvMode = -1;
	private int mRecordType = -1;
	private int mMotionDetection = -1;
	private int mTotalSize = -1;
	private int mPostition = -1;
	private int mtotalMinute = 0;
	private byte[] szTimeZoneString = null;
	private String[] timeZoneList = null;
	private String[] timeZoneNameList = null;
	private long t1 = 0;
	protected ThreadCheck	  m_threadCheck=null;
	boolean stopCheck = true;
	private boolean changeStatus = false;
	private static final int CHECK_STATUS = 0x00001;
	
	private static List<AVIOCTRLDEFs.SWifiAp> m_wifiList = new ArrayList<AVIOCTRLDEFs.SWifiAp>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getText(R.string.dialog_AdvancedSetting));
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.advanced_settings);

		Bundle bundle = this.getIntent().getExtras();
		String devUUID = bundle.getString("dev_uuid");
		String devUID = bundle.getString("dev_uid");

		/* register recvIOCtrl listener */
		for (MyCamera camera : MainActivity.CameraList) {

			if (devUUID.equalsIgnoreCase(camera.getUUID()) && devUID.equalsIgnoreCase(camera.getUID())) {

				mCamera = camera;
				mCamera.registerIOTCListener(this);
				break;
			}
		}

		for (DeviceInfo dev : MainActivity.DeviceList) {

			if (devUUID.equalsIgnoreCase(dev.UUID) && devUID.equalsIgnoreCase(dev.UID)) {
				mDevice = dev;
				break;
			}
		}

		AdvancedSettingActivity.newPassword = mDevice.View_Password;

		/* get view */
		pnlVideoQuality = (LinearLayout) findViewById(R.id.panelVideoQualitySetting);
		pnlVideoFlip = (LinearLayout) findViewById(R.id.panelVideoFlip);
		pnlEnvMode = (LinearLayout) findViewById(R.id.panelEnvironmentMode);
		pnlWiFiSetting = (LinearLayout) findViewById(R.id.panelWiFiSetting);
		pnlEventSeting = (LinearLayout) findViewById(R.id.panelEventSetting);
		pnlRecordSetting = (LinearLayout) findViewById(R.id.panelRecordSetting);
		pnlTimeZone = (LinearLayout) findViewById(R.id.panelTimeZone);
		
		
		pnlFormatSDCard = (LinearLayout) findViewById(R.id.panelFormatSDCard);
		pnlDeviceInfo = (LinearLayout) findViewById(R.id.panelDeviceInfo);

		btnModifySecurityCode = (Button) findViewById(R.id.btnModifySecurityCode);
		btnManageWiFiNetworks = (Button) findViewById(R.id.btnManageWiFiNetworks);
		btnFormatSDCard = (Button) findViewById(R.id.btnFormatSDCard);
		btnOK = (Button) findViewById(R.id.btnOK);
		btnCancel = (Button) findViewById(R.id.btnCancel);

		spinVideoQuality = (Spinner) findViewById(R.id.spinVideoQuality);
		spinVideoFlip = (Spinner) findViewById(R.id.spinVideoFlip);
		spinEnvironmentMode = (Spinner) findViewById(R.id.spinEnvironment);
		spinMotionDetection = (Spinner) findViewById(R.id.spinMotionDetection);
		spinEventNotification = (Spinner) findViewById(R.id.spinEventNotification);
		spinTimeZone = (Spinner) findViewById(R.id.spinTimeZone);
		spinRecordingMode = (Spinner) findViewById(R.id.spinRecordingMode);

		txtWiFiSSID = (TextView) findViewById(R.id.txtWiFiSSID);
		txtWiFiStatus = (TextView) findViewById(R.id.txtWiFiStatus);
		txtDeviceModel = (TextView) findViewById(R.id.txtDeviceModel);
		txtDeviceVersion = (TextView) findViewById(R.id.txtDeviceVersion);
		txtVenderName = (TextView) findViewById(R.id.txtVenderName);
		txtStorageTotalSize = (TextView) findViewById(R.id.txtStorageTotalSize);
		txtStorageFreeSize = (TextView) findViewById(R.id.txtStorageFreeSize);

		/* set listener */
		btnModifySecurityCode.setOnClickListener(btnModifySecurityCodeOnClickListener);
		btnManageWiFiNetworks.setOnClickListener(btnManageWiFiNetworksOnClickListener);
		btnFormatSDCard.setOnClickListener(btnFormatSDCardListener);
		btnOK.setOnClickListener(btnOKOnClickListener);
		btnCancel.setOnClickListener(btnCancelOnClickListener);

		/* set function visibility */
		pnlVideoFlip.setVisibility(mCamera != null && mCamera.getVideoFlipSupported(0) ? View.VISIBLE : View.GONE);
		pnlEnvMode.setVisibility(mCamera != null && mCamera.getEnvironmentModeSupported(0) ? View.VISIBLE : View.GONE);

		initVideoSetting();
		initDeviceInfo();

		if (mCamera != null && mCamera.getWiFiSettingSupported(0)) {
			initWiFiSSID();
			pnlWiFiSetting.setVisibility(View.VISIBLE);
		} else {
			pnlWiFiSetting.setVisibility(View.GONE);
		}

		if (mCamera != null && mCamera.getEventSettingSupported(0)) {
			initMotionDetection();
			initEventNotification();
			pnlEventSeting.setVisibility(View.VISIBLE);
		} else {
			pnlEventSeting.setVisibility(View.GONE);
		}

		if (mCamera != null && mCamera.getRecordSettingSupported(0)) {
			initRecordingMode();
			pnlRecordSetting.setVisibility(View.VISIBLE);
		} else {
			pnlRecordSetting.setVisibility(View.GONE);
		}

//		if (mCamera != null && mCamera.getIsSupportTimeZone() == 1) {
		if(mCamera != null && mCamera.getTimeZone(0)) {
			initTimeZone();
			pnlTimeZone.setVisibility(View.VISIBLE);
		} else {
			pnlTimeZone.setVisibility(View.GONE);
		}
		
		if (mTotalSize >= 0 && mCamera != null && mCamera.getSDCardFormatSupported(0)) {
			pnlFormatSDCard.setVisibility(View.VISIBLE);
		} else {
			pnlFormatSDCard.setVisibility(View.GONE);
		}

		if (mCamera != null && mCamera.getVideoQualitySettingSupport(0)) {
			pnlVideoQuality.setVisibility(View.VISIBLE);
		} else {
			pnlVideoQuality.setVisibility(View.GONE);
		}

		if (mCamera != null && mCamera.getDeviceInfoSupport(0)) {
			pnlDeviceInfo.setVisibility(View.VISIBLE);
		} else {
			pnlDeviceInfo.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			quit(false);
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		Configuration cfg = getResources().getConfiguration();

		if (cfg.orientation == Configuration.ORIENTATION_LANDSCAPE) {
		} else if (cfg.orientation == Configuration.ORIENTATION_PORTRAIT) {
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		// FlurryAgent.onStartSession(this, "Q1SDXDZQ21BQMVUVJ16W");
	}

	@Override
	protected void onStop() {
		stopCheck=true;
		super.onStop();
		// FlurryAgent.onEndSession(this);
	}

	private void initVideoSetting() {

		ArrayAdapter<CharSequence> adapterVideoQuality = ArrayAdapter.createFromResource(this, R.array.video_quality, android.R.layout.simple_spinner_item);
		adapterVideoQuality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinVideoQuality.setAdapter(adapterVideoQuality);
		spinVideoQuality.setSelection(2);
		spinVideoQuality.setEnabled(false);

		ArrayAdapter<CharSequence> adapterVideoFlip = ArrayAdapter.createFromResource(this, R.array.video_flip, android.R.layout.simple_spinner_item);
		adapterVideoFlip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinVideoFlip.setAdapter(adapterVideoFlip);
		spinVideoFlip.setSelection(0);
		spinVideoFlip.setEnabled(false);

		ArrayAdapter<CharSequence> adapterEnvironment = ArrayAdapter.createFromResource(this, R.array.environment_mode, android.R.layout.simple_spinner_item);
		adapterEnvironment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinEnvironmentMode.setAdapter(adapterEnvironment);
		spinEnvironmentMode.setSelection(0);
		spinEnvironmentMode.setEnabled(false);

		if (mCamera != null) {
			mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ, AVIOCTRLDEFs.SMsgAVIoctrlTimeZone.parseContent());
			mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSTREAMCTRL_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetStreamCtrlReq.parseContent(mDevice.ChannelIndex));
			mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_VIDEOMODE_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetVideoModeReq.parseContent(mDevice.ChannelIndex));
			mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_ENVIRONMENT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetEnvironmentReq.parseContent(mDevice.ChannelIndex));
		}

	}

	private void initWiFiSSID() {

		txtWiFiSSID.setText(getText(R.string.tips_wifi_retrieving));
		txtWiFiSSID.setTypeface(null, Typeface.BOLD_ITALIC);
		btnManageWiFiNetworks.setEnabled(false);

		if (mCamera != null) {
			mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTWIFIAP_REQ, AVIOCTRLDEFs.SMsgAVIoctrlListWifiApReq.parseContent());
		}
	}

	private void initMotionDetection() {

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.motion_detection, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinMotionDetection.setAdapter(adapter);
		spinMotionDetection.setEnabled(false);

		if (mCamera != null) {
			mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETMOTIONDETECT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetMotionDetectReq.parseContent(mDevice.ChannelIndex));
		}
	}

	private void initEventNotification() {

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.event_notification, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinEventNotification.setAdapter(adapter);
		spinEventNotification.setSelection(mDevice.EventNotification);
	}

	private void initTimeZone() {

		getTimeZoneCSV();
		
		for(int i=0;i<timeZoneNameList.length;i++)
		{
			String strRecvMsg;
			try {
				strRecvMsg = new String(mCamera.getTimeZoneString(),0, mCamera.getTimeZoneString().length,"utf-8");
				if(strRecvMsg.indexOf(timeZoneNameList[i]) != -1){
					mPostition = i;
				}
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}
		}
		
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item,timeZoneList);	
		adapter.setDropDownViewResource(R.layout.myspinner);
		spinTimeZone.setAdapter(adapter);
		spinTimeZone.setSelection(mPostition);
		spinTimeZone.setEnabled(false);
		spinTimeZone.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				mPostition = position;
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				//showToast("");
				for(int i = 0;i<timeZoneNameList.length;i++)
				{
					String strRecvMsg;
					try {
						strRecvMsg = new String(mCamera.getTimeZoneString(),0, mCamera.getTimeZoneString().length,"utf-8");
						if(strRecvMsg.indexOf(timeZoneNameList[i]) != -1){
							mPostition = i;
						}
					} catch (UnsupportedEncodingException e) {
						
						e.printStackTrace();
					}
				}
			}
		});
		
	}


	private void initRecordingMode() {

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.recording_mode, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinRecordingMode.setAdapter(adapter);
		spinRecordingMode.setEnabled(false);

		if (mCamera != null) {
			mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETRECORD_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetMotionDetectReq.parseContent(mDevice.ChannelIndex));
		}
	}

	private void initDeviceInfo() {

		txtDeviceModel.setText(getText(R.string.tips_wifi_retrieving));
		txtDeviceVersion.setText(getText(R.string.tips_wifi_retrieving));
		txtVenderName.setText(getText(R.string.tips_wifi_retrieving));
		txtStorageTotalSize.setText(getText(R.string.tips_wifi_retrieving));
		txtStorageFreeSize.setText(getText(R.string.tips_wifi_retrieving));

		txtDeviceModel.setTypeface(null, Typeface.BOLD_ITALIC);
		txtDeviceVersion.setTypeface(null, Typeface.BOLD_ITALIC);
		txtVenderName.setTypeface(null, Typeface.BOLD_ITALIC);
		txtStorageTotalSize.setTypeface(null, Typeface.BOLD_ITALIC);
		txtStorageFreeSize.setTypeface(null, Typeface.BOLD_ITALIC);

		if (mCamera != null) {
			mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVINFO_REQ, AVIOCTRLDEFs.SMsgAVIoctrlDeviceInfoReq.parseContent());
		}
	}

	private void quit(boolean isPressOK) {

		int videoQuality = spinVideoQuality.getSelectedItemPosition() + 1;
		int videoFlip = spinVideoFlip.getSelectedItemPosition();
		int envMode = spinEnvironmentMode.getSelectedItemPosition();
		int recType = spinRecordingMode.getSelectedItemPosition();
		int evtNotify = spinEventNotification.getSelectedItemPosition();
		int motionDetection = spinMotionDetection.getSelectedItemPosition();
		int postition = spinTimeZone.getSelectedItemPosition();

		if (isPressOK) {

			mDevice.EventNotification = evtNotify;
			DatabaseManager manager = new DatabaseManager(this);
			manager.updateDeviceInfoByDBID(mDevice.DBID, mDevice.UID, mDevice.NickName, "", "", "admin", mDevice.View_Password, mDevice.EventNotification, mDevice.ChannelIndex);

			if (mCamera != null) {

				if (mVideoQuality != -1 && mVideoQuality != videoQuality) {
					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETSTREAMCTRL_REQ, AVIOCTRLDEFs.SMsgAVIoctrlSetStreamCtrlReq.parseContent(mDevice.ChannelIndex, (byte) videoQuality));
				}

				if (mVideoFlip != -1 && mVideoFlip != videoFlip) {
					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_VIDEOMODE_REQ, AVIOCTRLDEFs.SMsgAVIoctrlSetVideoModeReq.parseContent(mDevice.ChannelIndex, (byte) videoFlip));
				}

				if (mEnvMode != -1 && mEnvMode != envMode) {
					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_ENVIRONMENT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlSetEnvironmentReq.parseContent(mDevice.ChannelIndex, (byte) envMode));
				}

				if (mPostition != -1 && mCamera.getIsSupportTimeZone() == 1) {
					getTimeZonetDate(timeZoneList[mPostition]);
					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_TIMEZONE_REQ, AVIOCTRLDEFs.SMsgAVIoctrlTimeZone.parseContent(268,mCamera.getIsSupportTimeZone(),mtotalMinute,szTimeZoneString));
					try {
						Glog.I("szTimeZoneString", new String(szTimeZoneString,0, szTimeZoneString.length,"utf-8"));
					} catch (UnsupportedEncodingException e) {

						e.printStackTrace();
					}
					Glog.I("","" + mPostition);
				}

				if (mMotionDetection != -1 && mMotionDetection != motionDetection) {

					int md = 0;

					if (spinMotionDetection.getSelectedItemPosition() == 0)
						md = 0;
					else if (spinMotionDetection.getSelectedItemPosition() == 1)
						md = 25;
					else if (spinMotionDetection.getSelectedItemPosition() == 2)
						md = 50;
					else if (spinMotionDetection.getSelectedItemPosition() == 3)
						md = 75;
					else if (spinMotionDetection.getSelectedItemPosition() == 4)
						md = 100;

					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETMOTIONDETECT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlSetMotionDetectReq.parseContent(mDevice.ChannelIndex, md));
				
				}

				if (mRecordType != -1 && mRecordType != recType) {

					int rd = 0;

					if (spinRecordingMode.getSelectedItemPosition() == 0)
						rd = AVIOCTRLDEFs.AVIOTC_RECORDTYPE_OFF;
					else if (spinRecordingMode.getSelectedItemPosition() == 1)
						rd = AVIOCTRLDEFs.AVIOTC_RECORDTYPE_FULLTIME;
					else if (spinRecordingMode.getSelectedItemPosition() == 2)
						rd = AVIOCTRLDEFs.AVIOTC_RECORDTYPE_ALAM;
					else if (spinRecordingMode.getSelectedItemPosition() == 3)
						rd = AVIOCTRLDEFs.AVIOTC_RECORDTYPE_MANUAL;

					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETRECORD_REQ, AVIOCTRLDEFs.SMsgAVIoctrlSetRecordReq.parseContent(mDevice.ChannelIndex, rd));
				}
			}
		}

		if (AdvancedSettingActivity.isModifyPassword) {
			mDevice.View_Password = AdvancedSettingActivity.isModifyPassword ? AdvancedSettingActivity.newPassword : mDevice.View_Password;
			DatabaseManager manager = new DatabaseManager(this);
			manager.updateDeviceInfoByDBID(mDevice.DBID, mDevice.UID, mDevice.NickName, "", "", "admin", mDevice.View_Password, mDevice.EventNotification, mDevice.ChannelIndex);
		}

		if (mCamera != null)
			mCamera.unregisterIOTCListener(this);
		
		boolean isSettingsChanged = isPressOK && ((mVideoQuality != -1 && videoQuality != mVideoQuality) || (mVideoFlip != -1 && videoFlip != mVideoFlip) || (mEnvMode != -1 && envMode != mEnvMode) || (mRecordType != -1 && recType != mRecordType) || (mMotionDetection != -1 && motionDetection != mMotionDetection) || (mPostition != -1 && postition != mPostition));
		boolean isNeedReconnect = isSettingsChanged || AdvancedSettingActivity.isModifyPassword || AdvancedSettingActivity.isModifyWiFi;

		Intent intent = new Intent();
		Bundle extras = new Bundle();
		extras.putBoolean("need_reconnect", isNeedReconnect);
		extras.putBoolean("change_password", AdvancedSettingActivity.isModifyPassword);
		extras.putString("new_password", AdvancedSettingActivity.newPassword);
		intent.putExtras(extras);
		setResult(isPressOK || AdvancedSettingActivity.isModifyPassword || AdvancedSettingActivity.isModifyWiFi ? RESULT_OK : RESULT_CANCELED, intent);
		finish();
	}

	private static String getString(byte[] data) {

		StringBuilder sBuilder = new StringBuilder();

		for (int i = 0; i < data.length; i++) {

			if (data[i] == 0x0)
				break;

			sBuilder.append((char) data[i]);
		}

		return sBuilder.toString();
	}

	private String getVersion(int version) {

		byte[] bytVer = new byte[4];

		StringBuffer sb = new StringBuffer();
		bytVer[3] = (byte) (version);
		bytVer[2] = (byte) (version >>> 8);
		bytVer[1] = (byte) (version >>> 16);
		bytVer[0] = (byte) (version >>> 24);
		sb.append((int) (bytVer[0] & 0xff));
		sb.append('.');
		sb.append((int) (bytVer[1] & 0xff));
		sb.append('.');
		sb.append((int) (bytVer[2] & 0xff));
		sb.append('.');
		sb.append((int) (bytVer[3] & 0xff));

		return sb.toString();
	}

	private void getTimeZoneCSV() {

//		String[] fileName = {"africa.csv","america.csv","asia.csv","europe.csv","oceania.csv"};
		String[] fileName = {"timeZone.csv"};
		int nCount[] = {0,0,0,0,0,0};
		
		for(int i = 1;i<=fileName.length;i++)
		{
			nCount[i] = getConut(fileName[i-1],nCount[i-1]);
		}
		
        timeZoneList = new String[nCount[1]];
        timeZoneNameList = new String[nCount[1]];
		for(int i = 0;i<fileName.length;i++)
		{
			getCSVdata(fileName[i],nCount[i]);
		}
		
//        Arrays.sort(timeZoneList);
//        Arrays.sort(timeZoneNameList);

	}
	
	private int getConut(String sFileName,int count){
		
		try
	    {
			InputStream is = getResources().getAssets().open(sFileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		    try
		    {
		        String line;
		        while ((line = reader.readLine()) != null) 
		        {	
		             String[] RowData = line.split(",");
		             if(!RowData[2].equals("--")){
		            	 count++;
	            	 }
		        }
		    }
		    catch (IOException ex) 
		    {
		        // handle exception
		    }
	    
		    finally
		    {
		        try
		        {
		            is.close();
		            reader.close();
		        }
		        catch (IOException e) 
		        {
		            // handle exception
		        }
		    }
	    } 
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    }
		return count;

	}
	private void getCSVdata(String sFileName,int nCount){
		
		try
	    {
			InputStream is = getResources().getAssets().open(sFileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		    try
		    {

		        String line;
		        
		        while ((line = reader.readLine()) != null) 
		        {
		             String[] RowData = line.split(",");

	            	 if(!RowData[2].equals("--")){
	            		 timeZoneList[nCount] = RowData[1] + "\n" + RowData[2];
	            		 timeZoneNameList[nCount] = RowData[1];
	            		 nCount++;
	            	 }
		        }
		    }
		    catch (IOException ex) 
		    {
		        // handle exception
		    }
	    
		    finally
		    {
		        try
		        {
		            is.close();
		            reader.close();
		        }
		        catch (IOException e) 
		        {
		            // handle exception
		        }
		    }
	    } 
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    }  

	}
	
	private void getTimeZonetDate(String string) {
		String[] getLocation = string.split("\\n");
		szTimeZoneString = getLocation[0].getBytes();
		String nTime = getLocation[1].substring(4);
		
		if(nTime.indexOf("+") != -1){
			
			int nStart = nTime.indexOf("+") + 1;
			int nEnd = nTime.indexOf(":") ;
			int nHour = Integer.parseInt(nTime.substring(nStart, nEnd));
			int nMinute = Integer.parseInt(nTime.substring(nEnd + 1));
			mtotalMinute = nHour*60 + nMinute;

		}else{
			
			int nStart = nTime.indexOf("-") + 1;
			int nEnd = nTime.indexOf(":");
			int nHour = Integer.parseInt(nTime.substring(nStart, nEnd));
			int nMinute = Integer.parseInt(nTime.substring(nEnd + 1));
			mtotalMinute = nHour*(-60) - nMinute;

		}
	}
	
	private View.OnClickListener btnManageWiFiNetworksOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(AdvancedSettingActivity.this, R.style.HoloAlertDialog));
			final AlertDialog dlg = builder.create();
			dlg.setTitle(getText(R.string.dialog_ManagWiFiNetworks));
			dlg.setIcon(android.R.drawable.ic_menu_more);

			LayoutInflater inflater = dlg.getLayoutInflater();
			View view = inflater.inflate(R.layout.manage_device_wifi, null);
			dlg.setView(view);

			final Spinner spinWiFiSSID = (Spinner) view.findViewById(R.id.spinWiFiSSID);
			final TextView txtWiFiSignal = (TextView) view.findViewById(R.id.txtWiFiSignal);
			final TextView txtWiFiSecurity = (TextView) view.findViewById(R.id.txtWiFiSecurity);
			final EditText edtWiFiPassword = (EditText) view.findViewById(R.id.edtWiFiPassword);
			final CheckBox checkBox = (CheckBox) view.findViewById(R.id.chbShowHiddenPassword);
			final Button btnOK = (Button) view.findViewById(R.id.btnOK);
			final Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

			/* set spinner adapter */
			String[] arySSID = new String[m_wifiList.size()];

			for (int i = 0; i < m_wifiList.size(); i++) {
				arySSID[i] = getString(m_wifiList.get(i).ssid);
			}

			/*
			 * if wifi list is empty, all control but cancel button must be
			 * disabled
			 */
			if (m_wifiList.size() == 0) {
				spinWiFiSSID.setEnabled(false);
				btnOK.setEnabled(false);
				checkBox.setEnabled(false);
				edtWiFiPassword.setEnabled(false);
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdvancedSettingActivity.this, android.R.layout.simple_spinner_item, arySSID);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinWiFiSSID.setAdapter(adapter);
			spinWiFiSSID.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

					AVIOCTRLDEFs.SWifiAp wifi = m_wifiList.get(position);

					String security = "";
					if (wifi.enctype == AVIOCTRLDEFs.AVIOTC_WIFIAPENC_INVALID)
						security = "Invalid";
					else if (wifi.enctype == AVIOCTRLDEFs.AVIOTC_WIFIAPENC_NONE)
						security = "None";
					else if (wifi.enctype == AVIOCTRLDEFs.AVIOTC_WIFIAPENC_WEP)
						security = "WEP";
					else if (wifi.enctype == AVIOCTRLDEFs.AVIOTC_WIFIAPENC_WPA2_AES)
						security = "WPA2 AES";
					else if (wifi.enctype == AVIOCTRLDEFs.AVIOTC_WIFIAPENC_WPA2_TKIP)
						security = "WPA2 TKIP";
					else if (wifi.enctype == AVIOCTRLDEFs.AVIOTC_WIFIAPENC_WPA_AES)
						security = "WPA AES";
					else if (wifi.enctype == AVIOCTRLDEFs.AVIOTC_WIFIAPENC_WPA_TKIP)
						security = "WPA TKIP";
					else if (wifi.enctype == AVIOCTRLDEFs.AVIOTC_WIFIAPENC_WPA_PSK_TKIP)
						security = "WPA PSK TKIP";
					else if (wifi.enctype == AVIOCTRLDEFs.AVIOTC_WIFIAPENC_WPA_PSK_AES)
						security = "WPA PSK AES";
					else if (wifi.enctype == AVIOCTRLDEFs.AVIOTC_WIFIAPENC_WPA2_PSK_TKIP)
						security = "WPA2 PSK TKIP";
					else if (wifi.enctype == AVIOCTRLDEFs.AVIOTC_WIFIAPENC_WPA2_PSK_AES)
						security = "WPA2 PSK AES";
					else
						security = "Unknown";

					txtWiFiSecurity.setText(security);
					txtWiFiSignal.setText((int) wifi.signal + " %");
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}

			});

			/* set check box for show / hidden password */
			final float scale = AdvancedSettingActivity.this.getResources().getDisplayMetrics().density;
			checkBox.setPadding(checkBox.getPaddingLeft() + (int) (5.0f * scale), checkBox.getPaddingTop(), checkBox.getPaddingRight(), checkBox.getPaddingBottom());

			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

					if (!isChecked) {
						edtWiFiPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
					} else {
						edtWiFiPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
					}
				}
			});
			/* - */

			btnOK.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					String pwd = edtWiFiPassword.getText().toString();
					AVIOCTRLDEFs.SWifiAp wifi = m_wifiList.get(spinWiFiSSID.getSelectedItemPosition());

					/*
					if (pwd.length() == 0) {
						Toast.makeText(AdvancedSettingActivity.this, getText(R.string.tips_all_field_can_not_empty).toString(), Toast.LENGTH_SHORT).show();
						return;
					}
					*/
					
					if (mCamera != null && wifi != null) {
						MainActivity.noResetWiFi = false; 
						mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETWIFI_REQ, AVIOCTRLDEFs.SMsgAVIoctrlSetWifiReq.parseContent(wifi.ssid, pwd.getBytes(), wifi.mode, wifi.enctype));

						AdvancedSettingActivity.isModifyWiFi = true;

						txtWiFiSSID.setText(getString(wifi.ssid));
						txtWiFiSSID.setTypeface(null, Typeface.BOLD);
						txtWiFiStatus.setText(getText(R.string.tips_wifi_remote_device_connecting));
						t1 = System.currentTimeMillis();
						checkWiFi();

					}
					
					dlg.dismiss();
				}
			});

			btnCancel.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					dlg.dismiss();
				}
			});

			dlg.show();
		}
	};

	private View.OnClickListener btnFormatSDCardListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			new AlertDialog.Builder(AdvancedSettingActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getText(R.string.tips_warning)).setMessage(getText(R.string.tips_format_sdcard_confirm)).setPositiveButton(getText(R.string.ok), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_FORMATEXTSTORAGE_REQ, AVIOCTRLDEFs.SMsgAVIoctrlFormatExtStorageReq.parseContent(0));
				}
			}).setNegativeButton(getText(R.string.cancel), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			}).show();
		}
	};

	private View.OnClickListener btnOKOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			quit(true);
		}
	};

	private View.OnClickListener btnCancelOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			quit(false);
		}
	};

	private View.OnClickListener btnModifySecurityCodeOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(AdvancedSettingActivity.this, R.style.HoloAlertDialog));
			final AlertDialog dlg = builder.create();
			dlg.setTitle(R.string.dialog_ModifySecurityCode);
			dlg.setIcon(android.R.drawable.ic_menu_more);

			LayoutInflater inflater = dlg.getLayoutInflater();
			View view = inflater.inflate(R.layout.modify_security_code, null);
			dlg.setView(view);

			final EditText edtOldPassword = (EditText) view.findViewById(R.id.edtOldPassword);
			final EditText edtNewPassword = (EditText) view.findViewById(R.id.edtNewPassword);
			final EditText edtConfirmPassword = (EditText) view.findViewById(R.id.edtConfirmPassword);
			Button btnOK = (Button) view.findViewById(R.id.btnOK);
			Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
			btnOK.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					String oldPwd = edtOldPassword.getText().toString();
					String newPwd = edtNewPassword.getText().toString();
					String confirmPwd = edtConfirmPassword.getText().toString();

					if (oldPwd.length() == 0 || newPwd.length() == 0 || confirmPwd.length() == 0) {
						Toast.makeText(AdvancedSettingActivity.this, getText(R.string.tips_all_field_can_not_empty).toString(), Toast.LENGTH_SHORT).show();
						return;
					}

					if (!oldPwd.equalsIgnoreCase(mDevice.View_Password)) {
						Toast.makeText(AdvancedSettingActivity.this, getText(R.string.tips_old_password_is_wrong).toString(), Toast.LENGTH_SHORT).show();
						return;
					}

					if (!newPwd.equalsIgnoreCase(confirmPwd)) {
						Toast.makeText(AdvancedSettingActivity.this, getText(R.string.tips_new_passwords_do_not_match).toString(), Toast.LENGTH_SHORT).show();
						return;
					}

					if (mCamera != null)
						mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETPASSWORD_REQ, AVIOCTRLDEFs.SMsgAVIoctrlSetPasswdReq.parseContent(oldPwd, newPwd));

					AdvancedSettingActivity.newPassword = newPwd;
					AdvancedSettingActivity.isModifyPassword = true;

					dlg.dismiss();
				}
			});

			btnCancel.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					dlg.dismiss();
				}
			});

			dlg.show();
		}
	};

	@Override
	public void receiveFrameData(final Camera camera, int sessionChannel, Bitmap bmp) {

	}

	@Override
	public void receiveFrameInfo(final Camera camera, int sessionChannel, long bitRate, int frameRate, int onlineNm, int frameCount, int incompleteFrameCount) {

	}

	@Override
	public void receiveSessionInfo(final Camera camera, int resultCode) {

		Bundle bundle = new Bundle();
		bundle.putString("requestDevice", ((MyCamera) camera).getUUID());

		Message msg = handler.obtainMessage();
		msg.what = resultCode;
		msg.setData(bundle);
		handler.sendMessage(msg);
	}

	@Override
	public void receiveChannelInfo(final Camera camera, int sessionChannel, int resultCode) {

	}

	@Override
	public void receiveIOCtrlData(final Camera camera, int sessionChannel, int avIOCtrlMsgType, byte[] data) {

		if (mCamera == camera) {
			Bundle bundle = new Bundle();
			bundle.putInt("sessionChannel", sessionChannel);
			bundle.putByteArray("data", data);

			Message msg = new Message();
			msg.what = avIOCtrlMsgType;
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			Bundle bundle = msg.getData();
			byte[] data = bundle.getByteArray("data");
			String requestDevice = bundle.getString("requestDevice");

			DeviceInfo device = null;
			MyCamera camera = null;

			for (int i = 0; i < MainActivity.DeviceList.size(); i++) {

				if (MainActivity.DeviceList.get(i).UUID.equalsIgnoreCase(requestDevice)) {
					device = MainActivity.DeviceList.get(i);
					break;
				}
			}

			for (int i = 0; i < MainActivity.CameraList.size(); i++) {

				if (MainActivity.CameraList.get(i).getUUID().equalsIgnoreCase(requestDevice)) {
					camera = MainActivity.CameraList.get(i);
					break;
				}
			}
			switch (msg.what) {

			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETPASSWORD_RESP:

				if (data[0] == 0x00)
					Toast.makeText(AdvancedSettingActivity.this, getText(R.string.tips_modify_security_code_ok).toString(), Toast.LENGTH_SHORT).show();

				break;

			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSTREAMCTRL_RESP:

				int videoQuality = data[4];

				if (videoQuality >= 0 && videoQuality <= 5) {
					spinVideoQuality.setSelection(videoQuality - 1, true);
					spinVideoQuality.setEnabled(true);
					mVideoQuality = videoQuality;
				}

				break;

			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_VIDEOMODE_RESP:

				int videoMode = data[4];

				if (videoMode >= 0 && videoMode <= 3) {
					spinVideoFlip.setSelection(videoMode, true);
					spinVideoFlip.setEnabled(true);
					mVideoFlip = videoMode;
				}

				break;

			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_ENVIRONMENT_RESP:

				int envMode = data[4];

				if (envMode >= 0 && envMode <= 3) {
					spinEnvironmentMode.setSelection(envMode, true);
					spinEnvironmentMode.setEnabled(true);
					mEnvMode = envMode;
				}

				break;

			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTWIFIAP_RESP:

				int cnt = Packet.byteArrayToInt_Little(data, 0);
				int size = AVIOCTRLDEFs.SWifiAp.getTotalSize();

				m_wifiList.clear();
				
				txtWiFiSSID.setText(getText(R.string.none));
				txtWiFiSSID.setTypeface(null, Typeface.BOLD);
				txtWiFiStatus.setText(getText(R.string.tips_wifi_remote_device_timeout));
				
				if (cnt > 0 && data.length >= 40) {

					int pos = 4;

					for (int i = 0; i < cnt; i++) {

						byte[] ssid = new byte[32];
						System.arraycopy(data, i * size + pos, ssid, 0, 32);

						byte mode = data[i * size + pos + 32];
						byte enctype = data[i * size + pos + 33];
						byte signal = data[i * size + pos + 34];
						byte status = data[i * size + pos + 35];

						m_wifiList.add(new AVIOCTRLDEFs.SWifiAp(ssid, mode, enctype, signal, status));

						if (status == 1) {

							txtWiFiSSID.setText(getString(ssid));
							txtWiFiSSID.setTypeface(null, Typeface.BOLD);
							txtWiFiStatus.setText(getText(R.string.tips_wifi_connected));
							
							changeStatus=true;
							stopCheck = true;
							m_threadCheck = null;

						} else if (status == 2) {

							txtWiFiSSID.setText(getString(ssid));
							txtWiFiSSID.setTypeface(null, Typeface.BOLD);
							txtWiFiStatus.setText(getText(R.string.tips_wifi_wrongpassword));
							
							changeStatus=true;
							stopCheck = true;
							m_threadCheck = null;

						} else if (status == 3) {
							
							txtWiFiSSID.setText(getString(ssid));
							txtWiFiSSID.setTypeface(null, Typeface.BOLD);
							txtWiFiStatus.setText(getText(R.string.tips_wifi_weak_signal));
							
							changeStatus=true;
							stopCheck = true;
							m_threadCheck = null;

						} else if (status == 4) {
							
							txtWiFiSSID.setText(getString(ssid));
							txtWiFiSSID.setTypeface(null, Typeface.BOLD);
							txtWiFiStatus.setText(getText(R.string.tips_wifi_ready));

							changeStatus=true;
							stopCheck = true;
							m_threadCheck = null;
						}
					}
				}

				btnManageWiFiNetworks.setEnabled(true);

				break;

			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETRECORD_RESP:

				int recordType = Packet.byteArrayToInt_Little(data, 4);

				if (recordType >= 0 && recordType <= 2) {
					spinRecordingMode.setSelection(recordType);
					spinRecordingMode.setEnabled(true);
					mRecordType = recordType;
				}

				break;

			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETMOTIONDETECT_RESP:

				int sensitivity = Packet.byteArrayToInt_Little(data, 4);

				if (sensitivity == 0) {
					spinMotionDetection.setSelection(0);
					mMotionDetection = 0;
				} else if (sensitivity > 0 && sensitivity <= 35) {
					spinMotionDetection.setSelection(1);
					mMotionDetection = 1;
				} else if (sensitivity > 35 && sensitivity <= 65) {
					spinMotionDetection.setSelection(2);
					mMotionDetection = 2;
				} else if (sensitivity > 65 && sensitivity <= 95) {
					spinMotionDetection.setSelection(3);
					mMotionDetection = 3;
				} else if (sensitivity > 95) {
					spinMotionDetection.setSelection(4);
					mMotionDetection = 4;
				}

				spinMotionDetection.setEnabled(true);

				break;

			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVINFO_RESP:

				byte[] bytModel = new byte[16];
				byte[] bytVender = new byte[16];
				System.arraycopy(data, 0, bytModel, 0, 16);
				System.arraycopy(data, 16, bytVender, 0, 16);

				String model = getString(bytModel);
				String vender = getString(bytVender);
				int version = Packet.byteArrayToInt_Little(data, 32);
				int free = Packet.byteArrayToInt_Little(data, 44);
				mTotalSize = Packet.byteArrayToInt_Little(data, 40);

				txtDeviceModel.setText(model);
				txtDeviceVersion.setText(getVersion(version));
				txtVenderName.setText(vender);
				txtStorageFreeSize.setText(String.valueOf(free) + " MB");

				txtDeviceModel.setTypeface(null, Typeface.NORMAL);
				txtDeviceVersion.setTypeface(null, Typeface.NORMAL);
				txtVenderName.setTypeface(null, Typeface.NORMAL);
				txtStorageTotalSize.setTypeface(null, Typeface.NORMAL);
				txtStorageFreeSize.setTypeface(null, Typeface.NORMAL);
				txtStorageTotalSize.setText(String.valueOf(mTotalSize) + " MB");

				if (mTotalSize > 0 && mCamera.getSDCardFormatSupported(0)) {
					pnlFormatSDCard.setVisibility(View.VISIBLE);
				} else {
					pnlFormatSDCard.setVisibility(View.GONE);
				}

				break;

			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETWIFI_RESP:

				/* retrieve all Wi-fi connections again */
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTWIFIAP_REQ, AVIOCTRLDEFs.SMsgAVIoctrlListWifiApReq.parseContent());
					}
				}, 30000);

				break;

			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_FORMATEXTSTORAGE_RESP:

				if (data[4] == 0)
					Toast.makeText(AdvancedSettingActivity.this, getText(R.string.tips_format_sdcard_success).toString(), Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(AdvancedSettingActivity.this, getText(R.string.tips_format_sdcard_failed).toString(), Toast.LENGTH_SHORT).show();

				break;
				
			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETWIFI_RESP:
				
				size = AVIOCTRLDEFs.SWifiAp.getTotalSize();
				byte[] ssid = new byte[32];
				System.arraycopy(data, 0, ssid, 0, 32);
				
				// byte mode = data[64];
				// byte enctype = data[65];
				// byte signal = data[66];
				byte status = data[67];
				
				if (status == 0) {
					
					txtWiFiSSID.setText(getString(ssid));
					txtWiFiSSID.setTypeface(null, Typeface.BOLD);
					txtWiFiStatus.setText(getText(R.string.tips_wifi_remote_device_timeout));
					changeStatus=true;

				} else if (status == 1) {

					txtWiFiSSID.setText(getString(ssid));
					txtWiFiSSID.setTypeface(null, Typeface.BOLD);
					txtWiFiStatus.setText(getText(R.string.tips_wifi_connected));
					changeStatus=true;
					stopCheck = true;
					m_threadCheck = null;
				} else if (status == 2) {

					txtWiFiSSID.setText(getString(ssid));
					txtWiFiSSID.setTypeface(null, Typeface.BOLD);
					txtWiFiStatus.setText(getText(R.string.tips_wifi_wrongpassword));
					changeStatus=true;
					stopCheck = true;
					m_threadCheck = null;
				} else if (status == 3) {
					
					txtWiFiSSID.setText(getString(ssid));
					txtWiFiSSID.setTypeface(null, Typeface.BOLD);
					txtWiFiStatus.setText(getText(R.string.tips_wifi_weak_signal));
					changeStatus=true;
					stopCheck = true;
					m_threadCheck = null;
				} else if (status == 4) {
					
					txtWiFiSSID.setText(getString(ssid));
					txtWiFiSSID.setTypeface(null, Typeface.BOLD);
					txtWiFiStatus.setText(getText(R.string.tips_wifi_ready));
					changeStatus=true;
					stopCheck = true;
					m_threadCheck = null;
				}
				
				btnManageWiFiNetworks.setEnabled(true);
				
				break;
				
			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETWIFI_RESP_2:
				
				size = AVIOCTRLDEFs.SWifiAp.getTotalSize();
				ssid = new byte[32];
				System.arraycopy(data, 0, ssid, 0, 32);
				
				// mode = data[96];
				// enctype = data[97];
				// signal = data[98];
				status = data[99];
				
				if (status == 0) {
					
					txtWiFiSSID.setText(getString(ssid));
					txtWiFiSSID.setTypeface(null, Typeface.BOLD);
					txtWiFiStatus.setText(getText(R.string.tips_wifi_remote_device_timeout));
					changeStatus=true;
					
				} else if (status == 1) {

					txtWiFiSSID.setText(getString(ssid));
					txtWiFiSSID.setTypeface(null, Typeface.BOLD);
					txtWiFiStatus.setText(getText(R.string.tips_wifi_connected));
					changeStatus=true;
					stopCheck = true;
					m_threadCheck = null;
				} else if (status == 2) {

					txtWiFiSSID.setText(getString(ssid));
					txtWiFiSSID.setTypeface(null, Typeface.BOLD);
					txtWiFiStatus.setText(getText(R.string.tips_wifi_wrongpassword));
					changeStatus=true;
					stopCheck = true;
					m_threadCheck = null;
				} else if (status == 3) {
					
					txtWiFiSSID.setText(getString(ssid));
					txtWiFiSSID.setTypeface(null, Typeface.BOLD);
					txtWiFiStatus.setText(getText(R.string.tips_wifi_weak_signal));
					changeStatus=true;
					stopCheck = true;
					m_threadCheck = null;
				} else if (status == 4) {
					
					txtWiFiSSID.setText(getString(ssid));
					txtWiFiSSID.setTypeface(null, Typeface.BOLD);
					txtWiFiStatus.setText(getText(R.string.tips_wifi_ready));
					changeStatus=true;
					stopCheck = true;
					m_threadCheck = null;
				}
				
				btnManageWiFiNetworks.setEnabled(true);
				
				break;
				
			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_TIMEZONE_RESP:

				if(timeZoneList != null){
					spinTimeZone.setEnabled(true);
				
					for(int i=0;i<timeZoneList.length;i++)
					{
						String strRecvMsg;
						try {
							strRecvMsg = new String(data,0, data.length,"utf-8");
							if(strRecvMsg.indexOf(timeZoneNameList[i]) != -1){
								mPostition = i;
							}
						} catch (UnsupportedEncodingException e) {
	
							e.printStackTrace();
						}
					}
	
					spinTimeZone.setSelection(mPostition, true);
				
				}
				break;
				
			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_TIMEZONE_RESP:
				
				byte[] msgTimeZone2 = new byte[268];
				System.arraycopy(data, 0, msgTimeZone2, 0, data.length);
				
				for(int i=0;i<timeZoneList.length;i++)
				{
					String strRecvMsg;
					try {
						strRecvMsg = new String(msgTimeZone2,0, msgTimeZone2.length,"utf-8");
						if(timeZoneNameList[i].indexOf(strRecvMsg) != -1){
							mPostition = i;
						}
					} catch (UnsupportedEncodingException e) {

						e.printStackTrace();
					}
				}

				spinTimeZone.setSelection(mPostition, true);
				break;
				
			case Camera.CONNECTION_STATE_TIMEOUT:
				
				checkWiFi(camera,device);

				break;			
			case CHECK_STATUS:
				
				txtWiFiSSID.setText(getText(R.string.none));
				txtWiFiSSID.setTypeface(null, Typeface.BOLD);
				txtWiFiStatus.setText(getText(R.string.tips_wifi_remote_device_timeout));
				break;

			}
			
			super.handleMessage(msg);
		}
	};
	
	private void checkWiFi(){
		if(m_threadCheck==null)
		{
			m_threadCheck=new ThreadCheck();
			m_threadCheck.start();

		}
	}
	
	
	class ThreadCheck extends Thread
	{
		public ThreadCheck(){
			stopCheck=false;
		}
		
		public void run() 
		{
			do
			{
				//for Marco的建議50秒再問第二次
				long t2 = System.currentTimeMillis();

				if(t2-t1 > 50000){
					if(!changeStatus){
						handler.postDelayed(new Runnable() {
	
							@Override
							public void run() {
								mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTWIFIAP_REQ, AVIOCTRLDEFs.SMsgAVIoctrlListWifiApReq.parseContent());
							}
						}, 100);
						
					}
				}
				
				if(t2-t1 > 60000){
					if(!changeStatus){
						
						Message msg = new Message();
						msg.what = CHECK_STATUS;
						handler.sendMessage(msg);
						
						changeStatus=false;
						stopCheck = true;
					}
					t1 = System.currentTimeMillis();
				}
				
			}while(!stopCheck);	
		}
	
	}
	
	private void checkWiFi(final Camera camera,final DeviceInfo dev){

		new Thread(new Runnable(){
		    @Override
		    public void run() {
		        int nCount = 0;   
		        while(true){
		            try{

		                Thread.sleep(1000);
		                nCount++;
		            }
		            catch(Exception e){
		                e.printStackTrace();
		            }
		            
		            if(nCount == 30){
		            	MainActivity.noResetWiFi = true; 
		            	camera.disconnect();
		            	camera.connect(dev.UID);
		            	camera.start(Camera.DEFAULT_AV_CHANNEL, dev.View_Account, dev.View_Password);
		            	camera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVINFO_REQ, AVIOCTRLDEFs.SMsgAVIoctrlDeviceInfoReq.parseContent());
		            	camera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetSupportStreamReq.parseContent());
		            	camera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETAUDIOOUTFORMAT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetAudioOutFormatReq.parseContent());
						camera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ, AVIOCTRLDEFs.SMsgAVIoctrlTimeZone.parseContent());
						
						nCount=0;
						break;
		            }
		        }
		    }            
		}).start();
		
	}

	@Override
	public void receiveFrameDataForMediaCodec(Camera camera, int avChannel,
			byte[] buf, int length) {
		// TODO Auto-generated method stub
		
	}

}
