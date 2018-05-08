package com.tutk.P2PCam264;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.linq.xinansmart.R;
import com.tutk.IOTC.AVFrame;
import com.tutk.IOTC.AVIOCTRLDEFs;
import com.tutk.IOTC.AVIOCTRLDEFs.SMsgAVIoctrlGetSupportStreamReq;
import com.tutk.IOTC.AVIOCTRLDEFs.SStreamDef;
import com.tutk.IOTC.Camera;
import com.tutk.IOTC.CameraListener;
import com.tutk.IOTC.IMonitor;
import com.tutk.IOTC.IOTCAPIs;
import com.tutk.IOTC.IReceiveSnapshotListener;
import com.tutk.IOTC.IRegisterIOTCListener;
import com.tutk.IOTC.MediaCodecMonitor;
import com.tutk.IOTC.MediaCodecMonitor_MPEG4;
import com.tutk.IOTC.Monitor;
import com.tutk.IOTC.St_SInfo;
import com.tutk.Logger.Glog;

public class LiveViewActivity extends SherlockActivity implements ViewSwitcher.ViewFactory, IRegisterIOTCListener,  CameraListener, IReceiveSnapshotListener{

	private static final int BUILD_VERSION_CODES_ICE_CREAM_SANDWICH = 14;
	private static final int STS_CHANGE_CHANNEL_STREAMINFO = 99;
	private static final int STS_SNAPSHOT_SCANED = 98;
	//private static final String FILE_TYPE = "image/*";

	private static final int REQUEST_CODE_ALBUM = 99;

	private static final int OPT_MENU_ITEM_ALBUM = Menu.FIRST;
	private static final int OPT_MENU_ITEM_SNAPSHOT = Menu.FIRST + 1;
//	private static final int OPT_MENU_ITEM_SUBSTREAM = Menu.FIRST + 2;
	private static final int OPT_MENU_ITEM_AUDIOCTRL = Menu.FIRST + 2;
	private static final int OPT_MENU_ITEM_AUDIO_IN = Menu.FIRST + 3;
	private static final int OPT_MENU_ITEM_AUDIO_OUT = Menu.FIRST + 4;
	
	private final int THUMBNAIL_LIMIT_HEIGHT = 720;
	private final int THUMBNAIL_LIMIT_WIDTH = 1280;
	

	// private TouchedMonitor monitor = null;
	private IMonitor monitor = null;
	private MyCamera mCamera = null;
	private DeviceInfo mDevice = null;

	private String mDevUID;
	private String mDevUUID;
	private String mConnStatus = "";
	private String mFilePath = "";
	private int mVideoFPS;
	private long mVideoBPS;
	private int mOnlineNm;
	private int mFrameCount;
	private int mIncompleteFrameCount;
	private int mVideoWidth;
	private int mVideoHeight;
	private int mSelectedChannel;

	
	private LinearLayout linPnlCameraInfo;
	private LinearLayout mInfoLayout;
	private TextView txtConnectionSlash;
	private TextView txtResolutionSlash;
	private TextView txtShowFPS;
	private TextView txtFPSSlash;
	private TextView txtShowBPS;
	private TextView txtShowOnlineNumber;
	private TextView txtOnlineNumberSlash;
	private TextView txtShowFrameRatio;
	private TextView txtFrameCountSlash;
	private TextView txtQuality;
	private TextView txtRecvFrmPreSec;
	private TextView txtRecvFrmSlash;
	private TextView txtDispFrmPreSeco;
	
	private TextView txtConnectionStatus;
	private TextView txtConnectionMode;
	private TextView txtResolution;
	private TextView txtFrameRate;
	private TextView txtBitRate;
	private TextView txtOnlineNumber;
	private TextView txtFrameCount;
	private TextView txtIncompleteFrameCount;
	private TextView txtPerformance;
	
	private boolean mIsListening = false;
	private boolean mIsSpeaking = false;

	private BitmapDrawable bg;
	private BitmapDrawable bgSplit;
	
	private ImageButton CH_button;
	private Context mContext;
	
	private static boolean wait_receive = true;
	private static boolean Watch_album = false;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
//		setContentView(R.layout.live_view_portrait);

		bg = (BitmapDrawable) getResources().getDrawable(R.drawable.bg_striped);
		bgSplit = (BitmapDrawable) getResources().getDrawable(R.drawable.bg_striped_split_img);

		Bundle bundle = this.getIntent().getExtras();
		mDevUID = bundle.getString("dev_uid");
		mDevUUID = bundle.getString("dev_uuid");
		mConnStatus = bundle.getString("conn_status");
		mSelectedChannel = bundle.getInt("camera_channel");
		
		Glog.I("DEBUG", "START :"+mDevUID);
		
		for (MyCamera camera : MainActivity.CameraList) {
			if (mDevUID.equalsIgnoreCase(camera.getUID()) && mDevUUID.equalsIgnoreCase(camera.getUUID())) {
				mCamera = camera;
				break;
			}
		}

		for (DeviceInfo dev : MainActivity.DeviceList) {

			if (mDevUID.equalsIgnoreCase(dev.UID) && mDevUUID.equalsIgnoreCase(dev.UUID)) {
				mDevice = dev;
				break;
			}
		}

		if (mCamera != null) {

			mCamera.registerIOTCListener(this);
			mCamera.SetCameraListener(this);

			if (!mCamera.isSessionConnected()) {
				Log.e("______","_________");
				mCamera.connect(mDevUID);
				mCamera.start(Camera.DEFAULT_AV_CHANNEL, mDevice.View_Account, mDevice.View_Password);
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_REQ, SMsgAVIoctrlGetSupportStreamReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVINFO_REQ, AVIOCTRLDEFs.SMsgAVIoctrlDeviceInfoReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETAUDIOOUTFORMAT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetAudioOutFormatReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ, AVIOCTRLDEFs.SMsgAVIoctrlTimeZone.parseContent());

			}

//			mCamera.startShow(mSelectedChannel,true);
			
			/*
			if (mCamera.LastAudioMode == 1) {
				mCamera.startListening(mSelectedChannel);
				mIsListening = true;
			}
			if (mCamera.LastAudioMode == 2) {
				mCamera.startSpeaking(mSelectedChannel);
				mIsSpeaking = true;
			}
			*/		
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			Log.e("IOTCamViewer", "" + metrics.widthPixels + " X "
					+ metrics.heightPixels);
			if (metrics.widthPixels < metrics.heightPixels)
				setupViewInPortraitLayout();
			else
				setupViewInLandscapeLayout();
			
		}

		// Inflate the custom view
		final View audioView = LayoutInflater.from(this).inflate(R.layout.two_way_audio, null);

		// Bind to its state change
		((RadioGroup) audioView.findViewById(R.id.radioAudio)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				if (checkedId == audioView.findViewById(R.id.radioSpeaker).getId()) {

					if (mCamera.isChannelConnected(mSelectedChannel)) {
						mCamera.startSpeaking(mSelectedChannel);
						mCamera.startListening(mSelectedChannel);
						mIsListening = true;
						}

				} else if (checkedId == audioView.findViewById(R.id.radioMicrophone).getId()) {

					if (mCamera.isChannelConnected(mSelectedChannel)) {
						mCamera.stopListening(mSelectedChannel);
						mCamera.startSpeaking(mSelectedChannel);
						mIsListening = false;
						mIsSpeaking = true;
					}
				}
			}
		});

		// getSupportActionBar().setCustomView(audioView);
		// getSupportActionBar().setDisplayShowCustomEnabled(true);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// FlurryAgent.onStartSession(this, "Q1SDXDZQ21BQMVUVJ16W");
	}

	@Override
	protected void onStop() {
		super.onStop();
		// FlurryAgent.onEndSession(this);
//		Watch_album = true;
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mCamera != null) {
			mCamera.stopSpeaking(mSelectedChannel);
			mCamera.stopListening(mSelectedChannel);
			mCamera.stopShow(mSelectedChannel);
			Glog.D("www", "onPause()_stopShow");
		}

		if (monitor != null)
			monitor.deattachCamera();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (monitor != null) {
			monitor.enableDither(mCamera.mEnableDither);
			monitor.attachCamera(mCamera, mSelectedChannel);
		}

		if (mCamera != null) {

			mCamera.startShow(mSelectedChannel,true);
			Glog.D("www", "onResume_startShow");

			if (mIsListening)
				mCamera.startListening(mSelectedChannel);
			if (mIsSpeaking)
				mCamera.startSpeaking(mSelectedChannel);
		}
		
		if(Watch_album) {
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			Log.i("IOTCamViewer", ""+metrics.widthPixels+" X "+metrics.heightPixels);
			if (metrics.widthPixels < metrics.heightPixels)
				setupViewInPortraitLayout();
			else
				setupViewInLandscapeLayout();
			Watch_album = false;
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Log.i("IOTCamViewer", ""+metrics.widthPixels+" X "+metrics.heightPixels);
		if (metrics.widthPixels < metrics.heightPixels)
			setupViewInPortraitLayout();
		else
			setupViewInLandscapeLayout();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_ALBUM) {
			monitor = (IMonitor) findViewById(R.id.monitor);
			monitor.setMaxZoom(3.0f);
			monitor.enableDither(mCamera.mEnableDither);
			monitor.attachCamera(mCamera, mSelectedChannel);
		}
	}
	
	private Handler handler_wait = new Handler() {

		public void handleMessage(Message message) {
			switch (message.what) 
			{
				case 1:
				Glog.D("test_a", "3");
				
				DisplayMetrics metrics = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(metrics);
				Log.i("IOTCamViewer", "" + metrics.widthPixels + " X "+ metrics.heightPixels);
				if (metrics.widthPixels < metrics.heightPixels)
					setupViewInPortraitLayout_wait();
				else
					setupViewInLandscapeLayout_wait();
				
				break;
			}
		}
	};
	
	private void setupViewInLandscapeLayout_wait() {

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		
		switch(Camera.nCodecId_temp) {
		
		case AVFrame.MEDIA_CODEC_VIDEO_MJPEG:
			 setContentView(R.layout.monitor_live_view_landscape);
			break;
			
		case AVFrame.MEDIA_CODEC_VIDEO_H264:
			setContentView(R.layout.live_view_landscape);
			break;
			
		case AVFrame.MEDIA_CODEC_VIDEO_MPEG4:
			setContentView(R.layout.mjpg4_view_landscape);
			break;
			
		default:
			setContentView(R.layout.mjpg4_view_landscape);
			break;
		}

		if (Build.VERSION.SDK_INT < BUILD_VERSION_CODES_ICE_CREAM_SANDWICH) {

			bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setBackgroundDrawable(bg);

			bgSplit.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setSplitBackgroundDrawable(bgSplit);
		}

		if (mCamera != null && mCamera.getMultiStreamSupported(0) && mCamera.getSupportedStream().length > 1)
			getSupportActionBar().setSubtitle(getText(R.string.dialog_LiveView).toString() + " : " + mDevice.NickName + " - CH" + (mSelectedChannel + 1));
		else
			getSupportActionBar().setSubtitle(getText(R.string.dialog_LiveView).toString() + " : " + mDevice.NickName);

		txtConnectionStatus = null;
		txtConnectionMode = null;
		txtResolution = null;
		txtFrameRate = null;
		txtBitRate = null;
		txtOnlineNumber = null;
		txtFrameCount = null;
		txtIncompleteFrameCount = null;
		txtRecvFrmPreSec = null;
		txtDispFrmPreSeco= null;
		txtPerformance = null;
		
		if (monitor != null)
			monitor.deattachCamera();

		monitor = null;
		monitor = (IMonitor) findViewById(R.id.monitor);
		monitor.cleanFrameQueue();
		monitor.setMaxZoom(3.0f);
		monitor.enableDither(mCamera.mEnableDither);
		monitor.attachCamera(mCamera, mSelectedChannel);
		
	}

	private void setupViewInLandscapeLayout() {

		Glog.D("test_a", "1");
		setContentView(R.layout.monitor_live_view_landscape);
		wait_receive = true;
		
	}

	private void setupViewInPortraitLayout() {

		Glog.D("test_a", "1");
		
		setContentView(R.layout.monitor_live_view_portrait);
		
		linPnlCameraInfo = (LinearLayout) findViewById(R.id.pnlCameraInfo);
		mInfoLayout = (LinearLayout) findViewById(R.id.infoLayout);
		txtConnectionSlash = (TextView) findViewById(R.id.txtConnectionSlash);
		txtResolutionSlash = (TextView) findViewById(R.id.txtResolutionSlash);
		txtShowFPS = (TextView) findViewById(R.id.txtShowFPS);
		txtFPSSlash = (TextView) findViewById(R.id.txtFPSSlash);
		txtShowBPS = (TextView) findViewById(R.id.txtShowBPS);
		txtShowOnlineNumber = (TextView) findViewById(R.id.txtShowOnlineNumber);
		txtOnlineNumberSlash = (TextView) findViewById(R.id.txtOnlineNumberSlash);
		txtShowFrameRatio = (TextView) findViewById(R.id.txtShowFrameRatio);
		txtFrameCountSlash = (TextView) findViewById(R.id.txtFrameCountSlash);
		txtQuality = (TextView) findViewById(R.id.txtQuality);
		txtDispFrmPreSeco = (TextView) findViewById(R.id.txtDispFrmPreSeco);
		txtRecvFrmSlash = (TextView) findViewById(R.id.txtRecvFrmSlash);
		txtRecvFrmPreSec = (TextView) findViewById(R.id.txtRecvFrmPreSec);
		txtPerformance = (TextView) findViewById(R.id.txtPerformance);
		
		txtConnectionStatus = (TextView) findViewById(R.id.txtConnectionStatus);
		txtConnectionMode = (TextView) findViewById(R.id.txtConnectionMode);
		txtResolution = (TextView) findViewById(R.id.txtResolution);
		txtFrameRate = (TextView) findViewById(R.id.txtFrameRate);
		txtBitRate = (TextView) findViewById(R.id.txtBitRate);
		txtOnlineNumber = (TextView) findViewById(R.id.txtOnlineNumber);
		txtFrameCount = (TextView) findViewById(R.id.txtFrameCount);
		txtIncompleteFrameCount = (TextView) findViewById(R.id.txtIncompleteFrameCount);
		CH_button = (ImageButton) findViewById(R.id.CH_button);

		CH_button.setVisibility(View.GONE);
		
		txtConnectionStatus.setText(mConnStatus);

		txtConnectionSlash.setText("");
		txtResolutionSlash.setText("");
		txtResolution.setText("0" + "x" + "0 ");
		txtShowFPS.setText("");
		txtFPSSlash.setText("");
		txtShowBPS.setText("");
		txtOnlineNumberSlash.setText("");
		txtShowFrameRatio.setText("");
		txtFrameCountSlash.setText("");
		txtRecvFrmSlash.setText("");
		txtPerformance.setText(getPerformance((int)(((float)mCamera.getDispFrmPreSec()/(float)mCamera.getRecvFrmPreSec())*100)));
		
		txtConnectionMode.setVisibility(View.GONE);
		txtFrameRate.setVisibility(View.GONE);
		txtBitRate.setVisibility(View.GONE);
		txtFrameCount.setVisibility(View.GONE);
		txtIncompleteFrameCount.setVisibility(View.GONE);
		txtRecvFrmPreSec.setVisibility(View.GONE);
		txtDispFrmPreSeco.setVisibility(View.GONE);
		
		wait_receive = true;
	}
	
	private void setupViewInPortraitLayout_wait() {

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		
		switch (Camera.nCodecId_temp) {

		case AVFrame.MEDIA_CODEC_VIDEO_MJPEG:
			setContentView(R.layout.monitor_live_view_portrait);
			break;
			
		case AVFrame.MEDIA_CODEC_VIDEO_H264:
			setContentView(R.layout.live_view_portrait);
			break;

		case AVFrame.MEDIA_CODEC_VIDEO_MPEG4:
			setContentView(R.layout.mjpg4_live_view_portrait);
			break;

		default:
			setContentView(R.layout.mjpg4_live_view_portrait);
			break;
		}
		
		
		if (monitor != null)
           monitor.deattachCamera();

       monitor = null;
       monitor = (IMonitor) this.findViewById(R.id.monitor);
       monitor.cleanFrameQueue();
       monitor.setMaxZoom(3.0f);
       monitor.enableDither(mCamera.mEnableDither);
       //monitor = (BetterMonitor) findViewById(R.id.monitor);
       try {
           monitor.attachCamera(mCamera, mSelectedChannel);
       } catch (Exception e) {
           finish();
       }
       monitor.setReceiveotListener(LiveViewActivity.this);

       // calculate surface view size
       int currentapiVersion = android.os.Build.VERSION.SDK_INT;

		if(currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN){

           Display display = getWindowManager().getDefaultDisplay();
           Point size = new Point();
           display.getSize(size);
           int width = size.x;
           int height = size.y;

           SurfaceView surfaceView = (SurfaceView) monitor;
           surfaceView.getLayoutParams().height = width * 3 / 4;

       }
       
		if (Build.VERSION.SDK_INT < BUILD_VERSION_CODES_ICE_CREAM_SANDWICH) {
			bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setBackgroundDrawable(bg);

			bgSplit.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setSplitBackgroundDrawable(bgSplit);
		}

		if (mCamera != null && mCamera.getMultiStreamSupported(0) && mCamera.getSupportedStream().length > 1)
			getSupportActionBar().setSubtitle(getText(R.string.dialog_LiveView).toString() + " : " + mDevice.NickName + " - CH" + (mSelectedChannel + 1));
		else
			getSupportActionBar().setSubtitle(getText(R.string.dialog_LiveView).toString() + " : " + mDevice.NickName);

		linPnlCameraInfo = (LinearLayout) findViewById(R.id.pnlCameraInfo);
		mInfoLayout = (LinearLayout) findViewById(R.id.infoLayout);
		txtConnectionSlash = (TextView) findViewById(R.id.txtConnectionSlash);
		txtResolutionSlash = (TextView) findViewById(R.id.txtResolutionSlash);
		txtShowFPS = (TextView) findViewById(R.id.txtShowFPS);
		txtFPSSlash = (TextView) findViewById(R.id.txtFPSSlash);
		txtShowBPS = (TextView) findViewById(R.id.txtShowBPS);
		txtShowOnlineNumber = (TextView) findViewById(R.id.txtShowOnlineNumber);
		txtOnlineNumberSlash = (TextView) findViewById(R.id.txtOnlineNumberSlash);
		txtShowFrameRatio = (TextView) findViewById(R.id.txtShowFrameRatio);
		txtFrameCountSlash = (TextView) findViewById(R.id.txtFrameCountSlash);
		txtQuality = (TextView) findViewById(R.id.txtQuality);
		txtDispFrmPreSeco = (TextView) findViewById(R.id.txtDispFrmPreSeco);
		txtRecvFrmSlash = (TextView) findViewById(R.id.txtRecvFrmSlash);
		txtRecvFrmPreSec = (TextView) findViewById(R.id.txtRecvFrmPreSec);
		txtPerformance = (TextView) findViewById(R.id.txtPerformance);
		
		
		txtConnectionStatus = (TextView) findViewById(R.id.txtConnectionStatus);
		txtConnectionMode = (TextView) findViewById(R.id.txtConnectionMode);
		txtResolution = (TextView) findViewById(R.id.txtResolution);
		txtFrameRate = (TextView) findViewById(R.id.txtFrameRate);
		txtBitRate = (TextView) findViewById(R.id.txtBitRate);
		txtOnlineNumber = (TextView) findViewById(R.id.txtOnlineNumber);
		txtFrameCount = (TextView) findViewById(R.id.txtFrameCount);
		txtIncompleteFrameCount = (TextView) findViewById(R.id.txtIncompleteFrameCount);
		CH_button = (ImageButton) findViewById(R.id.CH_button);

		if(CH_button != null) {
			if (mCamera != null && mCamera.isSessionConnected() && mCamera.getMultiStreamSupported(0) && mCamera.getSupportedStream().length > 1) {
				CH_button.setVisibility(View.VISIBLE);
			}else{
				CH_button.setVisibility(View.GONE);
			}
		}
		
		txtConnectionStatus.setText(mConnStatus);
		txtConnectionSlash.setText("");
		txtResolutionSlash.setText("");
		
		txtResolution.setText("0" + "x" + "0 ");
		txtShowFPS.setText("");
		txtFPSSlash.setText("");
		txtShowBPS.setText("");
		txtOnlineNumberSlash.setText("");
		txtShowFrameRatio.setText("");
		txtFrameCountSlash.setText("");
		txtRecvFrmSlash.setText("");
		txtPerformance.setText(getPerformance((int)(((float)mCamera.getDispFrmPreSec()/(float)mCamera.getRecvFrmPreSec())*100)));
		
		txtConnectionMode.setVisibility(View.GONE);
		txtFrameRate.setVisibility(View.GONE);
		txtBitRate.setVisibility(View.GONE);
		txtFrameCount.setVisibility(View.GONE);
		txtIncompleteFrameCount.setVisibility(View.GONE);
		txtRecvFrmPreSec.setVisibility(View.GONE);
		txtDispFrmPreSeco.setVisibility(View.GONE);
		
		linPnlCameraInfo.setOnClickListener(new Button.OnClickListener(){
			
		@Override
		public void onClick(View arg0) {

			MainActivity.nShowMessageCount++;
			showMessage();
			
			}
		});
		
		CH_button.setOnClickListener(new Button.OnClickListener(){

		@Override
		public void onClick(View arg0) {

			final AlertDialog dlg = new AlertDialog.Builder(LiveViewActivity.this).create();
			ListView view = new ListView(LiveViewActivity.this);
			dlg.setView(view);
			dlg.setCanceledOnTouchOutside(true);
			Window window = dlg.getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			// lp.y = -64;
			lp.dimAmount = 0f;

			ArrayAdapter<SStreamDef> adapter = new ArrayAdapter<AVIOCTRLDEFs.SStreamDef>(LiveViewActivity.this, android.R.layout.simple_list_item_1);
			for (SStreamDef streamDef : mCamera.getSupportedStream())
				adapter.add(streamDef);

			view.setAdapter(adapter);
			view.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

					monitor.deattachCamera();
					mCamera.stopShow(mSelectedChannel);
					Glog.D("www", "CH_button_stopShow");
					mCamera.stopListening(mSelectedChannel);
					mCamera.stopSpeaking(mSelectedChannel);
					
					monitor.resetCodec();
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("OnSpinStreamItemSelected: " + arg2);
					mSelectedChannel = arg2;

					if (mCamera != null && mCamera.getMultiStreamSupported(0) && mCamera.getSupportedStream().length > 1)
						getSupportActionBar().setSubtitle(getText(R.string.dialog_LiveView).toString() + " : " + mDevice.NickName + " - CH" + (mSelectedChannel + 1));
					else
						getSupportActionBar().setSubtitle(getText(R.string.dialog_LiveView).toString() + " : " + mDevice.NickName);
					
					DisplayMetrics metrics = new DisplayMetrics();
					getWindowManager().getDefaultDisplay().getMetrics(metrics);
					Log.i("IOTCamViewer", ""+metrics.widthPixels+" X "+metrics.heightPixels);
					if (metrics.widthPixels < metrics.heightPixels)
						setupViewInPortraitLayout();
					else
						setupViewInLandscapeLayout();
					
					monitor.enableDither(mCamera.mEnableDither);
					monitor.attachCamera(mCamera, mSelectedChannel);
					mCamera.startShow(mSelectedChannel,true);
					Glog.D("www", "CH_button_startShow");

					if (mIsListening)
						mCamera.startListening(mSelectedChannel);
					if (mIsSpeaking)
						mCamera.startSpeaking(mSelectedChannel);

//					DisplayMetrics metrics = new DisplayMetrics();
//					getWindowManager().getDefaultDisplay().getMetrics(metrics);
//					Log.i("IOTCamViewer", "" + metrics.widthPixels + " X "
//							+ metrics.heightPixels);
//					if (metrics.widthPixels < metrics.heightPixels)
//						setupViewInPortraitLayout();
//					else
//						setupViewInLandscapeLayout();
					
					dlg.dismiss();
				}
			});

			dlg.show();
			
			}
		});
				
		showMessage();
	}
	
	private void showMessage(){
		
		St_SInfo stSInfo = new St_SInfo();
		IOTCAPIs.IOTC_Session_Check(mCamera.getMSID(), stSInfo);
		
		if(MainActivity.nShowMessageCount >= 10)
		{
			
			txtConnectionStatus.setText(mConnStatus );
			txtConnectionMode.setText(getSessionMode(mCamera != null ? mCamera.getSessionMode() : -1) + " C: " + IOTCAPIs.IOTC_Get_Nat_Type() + ", D: " + stSInfo.NatType + ",R" + mCamera.getbResend() );
			
			txtConnectionSlash.setText(" / ");
			txtResolutionSlash.setText(" / ");
			txtShowFPS.setText(getText(R.string.txtFPS));
			txtFPSSlash.setText(" / ");
			txtShowBPS.setText(getText(R.string.txtBPS));
//			txtShowOnlineNumber.setText(getText(R.string.txtOnlineNumber));
			txtOnlineNumberSlash.setText(" / ");
			txtShowFrameRatio.setText(getText(R.string.txtFrameRatio));
			txtFrameCountSlash.setText(" / ");
			txtQuality.setText(getText(R.string.txtQuality));
			txtRecvFrmSlash.setText(" / ");
//			mCamera.getDispFrmPreSec()
			txtConnectionMode.setVisibility(View.VISIBLE);
			txtResolution.setVisibility(View.VISIBLE);
			txtFrameRate.setVisibility(View.VISIBLE);
			txtBitRate.setVisibility(View.VISIBLE);
			txtOnlineNumber.setVisibility(View.VISIBLE);
			txtFrameCount.setVisibility(View.VISIBLE);
			txtIncompleteFrameCount.setVisibility(View.VISIBLE);
			txtRecvFrmPreSec.setVisibility(View.VISIBLE);
			txtDispFrmPreSeco.setVisibility(View.VISIBLE);
		}		
	}
	
	private void addImageGallery(File file) {
	    ContentValues values = new ContentValues();
	    values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
	    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg"); // setar isso
	    getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	}
	
	private boolean saveImage(String fileName, Bitmap frame) {

		if (fileName == null || fileName.length() <= 0)
			return false;

		boolean bErr = false;
		FileOutputStream fos = null;

		try {

			fos = new FileOutputStream(fileName, false);
			frame.compress(Bitmap.CompressFormat.JPEG, 90, fos);
			fos.flush();
			fos.close();

		} catch (Exception e) {
			
			bErr = true;
			System.out.println("saveImage(.): " + e.getMessage());

		} finally {

			if (bErr) {

				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return false;
			}
		}

		addImageGallery(new File(fileName));
		return true;
	}

	// filename: such as,M20101023_181010.jpg
	private static String getFileNameWithTime() {

		Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH) + 1;
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		int mHour = c.get(Calendar.HOUR_OF_DAY);
		int mMinute = c.get(Calendar.MINUTE);
		int mSec = c.get(Calendar.SECOND);
		int mMilliSec = c.get(Calendar.MILLISECOND);

		StringBuffer sb = new StringBuffer();
		sb.append("IMG_");
		sb.append(mYear);
		if (mMonth < 10)
			sb.append('0');
		sb.append(mMonth);
		if (mDay < 10)
			sb.append('0');
		sb.append(mDay);
		sb.append('_');
		if (mHour < 10)
			sb.append('0');
		sb.append(mHour);
		if (mMinute < 10)
			sb.append('0');
		sb.append(mMinute);
		if (mSec < 10)
			sb.append('0');
		sb.append(mSec);
		sb.append(".jpg");

		return sb.toString();
	}

	private String getSessionMode(int mode) {
		
		String result = "";
		if (mode == 0)
			result = getText(R.string.connmode_p2p).toString();
		else if (mode == 1)
			result = getText(R.string.connmode_relay).toString();
		else if (mode == 2)
			result = getText(R.string.connmode_lan).toString();
		else
			result = getText(R.string.connmode_none).toString();
		
		return result;
	}
	
	private String getPerformance(int mode) {
		
		String result = "";
		if (mode < 30)
			result = getText(R.string.txtBad).toString();
		else if (mode < 60)
			result = getText(R.string.txtNormal).toString();
		else
			result = getText(R.string.txtGood).toString();
		
		return result;
	}

	private static boolean isSDCardValid() {

		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	private void quit() {
		
		if (mCamera != null) {

			if (mIsListening)
				mCamera.LastAudioMode = 1;
			else if (mIsSpeaking)
				mCamera.LastAudioMode = 2;
			else
				mCamera.LastAudioMode = 0;

			mCamera.unregisterIOTCListener(this);
		}
	
		byte[] snapshot = null;
		Bitmap bmp = mCamera.Snapshot(mSelectedChannel);
		
		if (bmp != null) {
			bmp = compressImage(mCamera.Snapshot(mSelectedChannel));
			if (bmp.getWidth() * bmp.getHeight() > THUMBNAIL_LIMIT_WIDTH
					* THUMBNAIL_LIMIT_HEIGHT) {
				bmp = Bitmap.createScaledBitmap(bmp, THUMBNAIL_LIMIT_WIDTH,
						THUMBNAIL_LIMIT_HEIGHT, false);
			}
			snapshot = DatabaseManager.getByteArrayFromBitmap(bmp);
		}
		
		DatabaseManager manager = new DatabaseManager(this);
		manager.updateDeviceChannelByUID(mDevUID, mSelectedChannel);
		if (snapshot != null) {
			manager.updateDeviceSnapshotByUID(mDevUID, snapshot);
		}

		/* return values to main page */
		Bundle extras = new Bundle();
		extras.putString("dev_uuid", mDevUUID);
		extras.putString("dev_uid", mDevUID);
		extras.putByteArray("snapshot", snapshot);
		extras.putInt("camera_channel", mSelectedChannel);

		Intent intent = new Intent();
		intent.putExtras(extras);
		setResult(RESULT_OK, intent);
		finish();
	}

	private Bitmap compressImage(Bitmap image) {
		
		Bitmap tempBitmap = image;
		
		try{
			
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        image.compress(Bitmap.CompressFormat.JPEG, 5, baos);// 閸ゆ繆绗ｇ缓鎰闁插繐娈敓浠嬪閸ゆ瑱鎷烽崵婵囶劜缁烘劕娈卞▔鏇炴閿涘苯娈遍柅娆忔閸撲礁娅�100閸ゆ繆绻嬬紙顔兼娴兼劧鎷烽崵婵囶劜妤规儳娈敓浠嬪閸ゆ瑱鎷烽崵婵撶礉閸ゆ繃鏁肩缓鍫濇閿熶粙澹曢崵娆欐嫹閸ゆ繂鎮楅崵婵婄箣閸ｃ儱娈卞姗堟嫹閸ゆ繃顭甸敓钘夋鐎涙ê娈遍幎顒冨暙閸ゆ繂澧庣紙鐪�aos閸ゆ繃顒叉锟�
	        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 閸ゆ繃鏁肩缓鍫濇閿熶粙澹曢崵娆欐嫹閸ゆ繂鎮楅崵婵婄箣閸ｃ儱娈卞姗堟嫹閸ゆ繃顭甸敓绲檃os閸ゆ繂鐡ㄩ崵婵囧М閼躲劌娈遍崜浣哄嫎ByteArrayInputStream閸ゆ繃顒叉锟�
	        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 閸ゆ繃鏁肩缓鍦攜teArrayInputStream閸ゆ繃缍愰敓钘夋濮楁﹫鎷烽崵婵呯矗閿熻棄娈辨担妤�鍓洪崵婵嗙毆閿熻棄娈卞纭呮殧
	        return bitmap;
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return tempBitmap;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Add Album Menu
		menu.add(Menu.NONE, OPT_MENU_ITEM_ALBUM, 1, "Album").setIcon(R.drawable.ic_menu_album).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		// Add Snapshot Menu
		menu.add(Menu.NONE, OPT_MENU_ITEM_SNAPSHOT, 2, "Snapshot").setIcon((mCamera != null && mCamera.isChannelConnected(mSelectedChannel) ? R.drawable.ic_menu_snapshot : R.drawable.ic_menu_snapshot_inverse)).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		// Add Sub-Channel Menu
//		if (mCamera != null && mCamera.isSessionConnected() && mCamera.getMultiStreamSupported(0) && mCamera.getSupportedStream().length > 1) {
//			menu.add(Menu.NONE, OPT_MENU_ITEM_SUBSTREAM, 3, "Ch").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//		}

		// Add Audio Control Menu
		if (!mIsListening && !mIsSpeaking)
			menu.add(Menu.NONE, OPT_MENU_ITEM_AUDIOCTRL, 3, getText(R.string.txtMute)).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		else if (mIsListening && !mIsSpeaking)
			menu.add(Menu.NONE, OPT_MENU_ITEM_AUDIOCTRL, 3, getText(R.string.txtListening)).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		else if (!mIsListening && mIsSpeaking)
			menu.add(Menu.NONE, OPT_MENU_ITEM_AUDIOCTRL, 3, getText(R.string.txtSpeaking)).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == OPT_MENU_ITEM_ALBUM) {

//			if (monitor != null) {
//				monitor.deattachCamera();
//			}
			
			Watch_album  = true;
			
			File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Snapshot/" + mDevUID);
			// File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Snapshot");
			String[] allFiles = folder.list();
			
			if (allFiles != null && allFiles.length > 0 && monitor != null) {				
				
				monitor.deattachCamera();
				
				String file = folder.getAbsolutePath() + "/" + allFiles[allFiles.length - 1];

				/*
				MediaScannerConnection.scanFile(LiveViewActivity.this,
						new String[] { file.toString() }, 
		                new String[] {"image/*"},
		                new MediaScannerConnection.OnScanCompletedListener() {
							public void onScanCompleted(String path, Uri uri) {
				                Glog.I("ExternalStorage", "Scanned " + path + ":");
				                Glog.I("ExternalStorage", "-> uri=" + uri);
				                
				                Intent intent = new Intent(Intent.ACTION_VIEW);
				                // intent.addCategory(Intent.CATEGORY_OPENABLE); 
				                // intent.setType("image/*"); 
				                intent.setDataAndType(uri, "image/*");
				                startActivity(intent);
		            }
		        });
				*/
				
				/*
				Intent intent = new Intent(Intent.ACTION_VIEW);
				Uri hacked_uri = Uri.parse("file://" + Uri.parse(file).getPath());
				intent.setDataAndType(hacked_uri, "image/*");
				startActivity(intent);
				*/
				
				Intent intent = new Intent(LiveViewActivity.this, GridViewGalleryActivity.class);
				intent.putExtra("snap", mDevUID);
				intent.putExtra("images_path", folder.getAbsolutePath());
				startActivity(intent);
				
			} else {
				String msg = LiveViewActivity.this.getText(R.string.tips_no_snapshot_found).toString();
				Toast.makeText(LiveViewActivity.this, msg, Toast.LENGTH_SHORT).show();	
			}
			
		} else if (id == OPT_MENU_ITEM_SNAPSHOT) {

			if (mCamera != null && mCamera.isChannelConnected(mSelectedChannel)) {

				if (isSDCardValid()) {

					File rootFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Snapshot/");
					File targetFolder = new File(rootFolder.getAbsolutePath() + "/" + mDevUID);
					// File rootFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Snapshot");
					// File targetFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Snapshot");

					if (!rootFolder.exists()) {
						try {
							rootFolder.mkdir();
						} catch (SecurityException se) {
							super.onOptionsItemSelected(item);
						}
					}

					if (!targetFolder.exists()) {

						try {
							targetFolder.mkdir();
						} catch (SecurityException se) {
							super.onOptionsItemSelected(item);
						}
					}

					final String file = targetFolder.getAbsoluteFile() + "/" + getFileNameWithTime();
//					Bitmap frame = mCamera != null ? mCamera.Snapshot(mSelectedChannel) : null;
					mFilePath = file;
					if(mCamera != null){
						mCamera.setSnapshot(mContext, file);
					}

//					if (frame != null && saveImage(file, frame) /* && db.addSnapshot(mDevUID, fileName, System.currentTimeMillis()) >= 0 */) {
//
//						//sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
//												
//						MediaScannerConnection.scanFile(this,
//				                new String[] { file.toString() }, 
//				                new String[] {"image/*"},
//				                new MediaScannerConnection.OnScanCompletedListener() {
//				            public void onScanCompleted(String path, Uri uri) {
//				                Glog.I("ExternalStorage", "Scanned " + path + ":");
//				                Glog.I("ExternalStorage", "-> uri=" + uri);
//								Message msg = handler.obtainMessage();
//								msg.what = STS_SNAPSHOT_SCANED;
//								handler.sendMessage(msg);
//				            }
//				        });
//
//					} 
					
					else {

						Toast.makeText(LiveViewActivity.this, getText(R.string.tips_snapshot_failed), Toast.LENGTH_SHORT).show();
					}

				} else {

					Toast.makeText(LiveViewActivity.this, LiveViewActivity.this.getText(R.string.tips_no_sdcard).toString(), Toast.LENGTH_SHORT).show();
				}
			}

		} else if (id == OPT_MENU_ITEM_AUDIOCTRL) {

			ArrayList<String> s = new ArrayList<String>();
			s.add(getText(R.string.txtMute).toString());
			if (mCamera.getAudioInSupported(0))
				s.add(getText(R.string.txtListen).toString());
			if (mCamera.getAudioOutSupported(0))
				s.add(getText(R.string.txtSpeak).toString());

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, s);

			final AlertDialog dlg = new AlertDialog.Builder(this).create();
			dlg.setTitle(null);
			dlg.setIcon(null);

			ListView view = new ListView(this);
			view.setAdapter(adapter);
			view.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

					if (mCamera == null)
						return;

					if (position == 1) { // Listening
						mCamera.stopSpeaking(mSelectedChannel);
						mCamera.startListening(mSelectedChannel);
						mIsListening = true;
						mIsSpeaking = false;
					} else if (position == 2) { // Speaking
						mCamera.stopListening(mSelectedChannel);
						mCamera.startSpeaking(mSelectedChannel);
						mIsListening = false;
						mIsSpeaking = true;
					} else if (position == 0) { // Mute
						mCamera.stopListening(mSelectedChannel);
						mCamera.stopSpeaking(mSelectedChannel);
						mIsListening = mIsSpeaking = false;
					}

					dlg.dismiss();
					LiveViewActivity.this.invalidateOptionsMenu();
				}
			});

			dlg.setView(view);
			dlg.setCanceledOnTouchOutside(true);
			dlg.show();

		} else if (id == OPT_MENU_ITEM_AUDIO_IN) {

			if (!mIsListening) {
				mCamera.startListening(mSelectedChannel);
			} else {
				mCamera.stopListening(mSelectedChannel);
			}

			mIsListening = !mIsListening;

			this.invalidateOptionsMenu();

		} else if (id == OPT_MENU_ITEM_AUDIO_OUT) {

			if (!mIsSpeaking) {
				mCamera.startSpeaking(mSelectedChannel);
			} else {
				mCamera.stopSpeaking(mSelectedChannel);
			}

			mIsSpeaking = !mIsSpeaking;

			this.invalidateOptionsMenu();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:

			quit();

			break;
		}

		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void receiveFrameData(final Camera camera, int avChannel, Bitmap bmp) {
		Log.e("____", "1");
		if (mCamera == camera && avChannel == mSelectedChannel) {
			if (bmp != null && (bmp.getWidth() != mVideoWidth || bmp.getHeight() != mVideoHeight)) {
				mVideoWidth = bmp.getWidth();
				mVideoHeight = bmp.getHeight();
			}
		}
		
		if(wait_receive) {
			
			try {
				Glog.D("test_a", "2");
				Message msg = Message.obtain(handler_wait);
				msg.what = 1;
				handler_wait.sendMessage(msg);
				wait_receive = false;
				
            } 
			catch(Exception e){
            	Glog.D("test_a", "error");
            }
		}
	}

	@Override
	public void receiveFrameInfo(final Camera camera, int avChannel, long bitRate, int frameRate, int onlineNm, int frameCount, int incompleteFrameCount) {
		Log.e("____", "5");
		if (mCamera == camera && avChannel == mSelectedChannel) {

			mVideoFPS = frameRate;
			mVideoBPS = bitRate;
			mOnlineNm = onlineNm;
			mFrameCount = frameCount;
			mIncompleteFrameCount = incompleteFrameCount;

			Bundle bundle = new Bundle();
			bundle.putInt("avChannel", avChannel);
			
			Message msg = handler.obtainMessage();
			msg.what = STS_CHANGE_CHANNEL_STREAMINFO;
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}

	@Override
	public void receiveChannelInfo(final Camera camera, int avChannel, int resultCode) {
		Log.e("____", "4");
		if (mCamera == camera && avChannel == mSelectedChannel) {
			Bundle bundle = new Bundle();
			bundle.putInt("avChannel", avChannel);

			Message msg = handler.obtainMessage();
			msg.what = resultCode;
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}

	@Override
	public void receiveSessionInfo(final Camera camera, int resultCode) {
		Log.e("____", "3");
		if (mCamera == camera) {
			Bundle bundle = new Bundle();
			Message msg = handler.obtainMessage();
			msg.what = resultCode;
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}

	@Override
	public void receiveIOCtrlData(final Camera camera, int avChannel, int avIOCtrlMsgType, byte[] data) {
		Log.e("____", "2");
		if (mCamera == camera) {
			Bundle bundle = new Bundle();
			bundle.putInt("avChannel", avChannel);
			bundle.putByteArray("data", data);
			Message msg = handler.obtainMessage();
			msg.what = avIOCtrlMsgType;
			handler.sendMessage(msg);			
		}
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			
			Bundle bundle = msg.getData();
			int avChannel = bundle.getInt("avChannel");

			
			St_SInfo stSInfo = new St_SInfo();
			IOTCAPIs.IOTC_Session_Check(mCamera.getMSID(), stSInfo);
			
			switch (msg.what) {

			case STS_CHANGE_CHANNEL_STREAMINFO:
				
				if (txtResolution != null){
					txtResolution.setText(String.valueOf(Camera.view_Width) + "x" + String.valueOf(Camera.view_Height));
				}

				if (txtFrameRate != null) {
//					txtFrameRate.setText(String.valueOf(mVideoFPS));

						if(Camera.nCodecId_temp == AVFrame.MEDIA_CODEC_VIDEO_MPEG4) {
							txtFrameRate.setText(String.valueOf(MediaCodecMonitor_MPEG4.keepFPS));
						}
						else if(Camera.nCodecId_temp == AVFrame.MEDIA_CODEC_VIDEO_H264) {
							txtFrameRate.setText(String.valueOf(MediaCodecMonitor.keepFPS));
						}else{
							txtFrameRate.setText(String.valueOf(Monitor.keepFPS));
						}
				}

				if (txtBitRate != null)
					txtBitRate.setText(String.valueOf(mVideoBPS) + "Kbps");

				if (txtOnlineNumber != null)
					txtOnlineNumber.setText(String.valueOf(mOnlineNm));

				if (txtFrameCount != null)
					txtFrameCount.setText(String.valueOf(mFrameCount));

				if (txtIncompleteFrameCount != null)
					txtIncompleteFrameCount.setText(String.valueOf(mIncompleteFrameCount));

				if (txtConnectionMode != null)
					txtConnectionMode.setText(getSessionMode(mCamera != null ? mCamera.getSessionMode() : -1) + " C: " + IOTCAPIs.IOTC_Get_Nat_Type() + ", D: " + stSInfo.NatType + ",R" + mCamera.getbResend());

				if (txtRecvFrmPreSec != null)
					txtRecvFrmPreSec.setText(String.valueOf(mCamera.getRecvFrmPreSec()));

				if (txtDispFrmPreSeco != null) {
//					txtDispFrmPreSeco.setText(String.valueOf(mCamera.getDispFrmPreSec()));
					txtDispFrmPreSeco.setText(String.valueOf(MediaCodecMonitor.mDecodeCount_temp));
				}

				if (txtPerformance != null)
					txtPerformance.setText(getPerformance((int)(((float)MediaCodecMonitor.mDecodeCount_temp/(float)mCamera.getRecvFrmPreSec())*100)));
				
				break;
				
			case STS_SNAPSHOT_SCANED:
				
				Toast.makeText(LiveViewActivity.this, getText(R.string.tips_snapshot_ok), Toast.LENGTH_SHORT).show();

				break;
				
			case Camera.CONNECTION_STATE_CONNECTING:
				
				if (!mCamera.isSessionConnected() || !mCamera.isChannelConnected(mSelectedChannel)) {
					
					mConnStatus = getText(R.string.connstus_connecting).toString();

					if (txtConnectionStatus != null)
						txtConnectionStatus.setText(mConnStatus);
				}
				
				break;

			case Camera.CONNECTION_STATE_CONNECTED:

				if (mCamera.isSessionConnected() && avChannel == mSelectedChannel && mCamera.isChannelConnected(mSelectedChannel)) {

					mConnStatus = getText(R.string.connstus_connected).toString();

					if (txtConnectionStatus != null)
						txtConnectionStatus.setText(mConnStatus);

					LiveViewActivity.this.invalidateOptionsMenu();
				}

				break;

			case Camera.CONNECTION_STATE_DISCONNECTED:

				mConnStatus = getText(R.string.connstus_disconnect).toString();

				if (txtConnectionStatus != null)
					txtConnectionStatus.setText(mConnStatus);

				LiveViewActivity.this.invalidateOptionsMenu();

				break;

			case Camera.CONNECTION_STATE_UNKNOWN_DEVICE:

				mConnStatus = getText(R.string.connstus_unknown_device).toString();

				if (txtConnectionStatus != null)
					txtConnectionStatus.setText(mConnStatus);

				LiveViewActivity.this.invalidateOptionsMenu();

				break;

			case Camera.CONNECTION_STATE_TIMEOUT:

				if (mCamera != null) {

					mCamera.stopSpeaking(mSelectedChannel);
					mCamera.stopListening(mSelectedChannel);
					mCamera.stopShow(mSelectedChannel);
					Glog.D("www", "CONNECTION_STATE_TIMEOUT_stopShow");
					mCamera.stop(mSelectedChannel);
					mCamera.disconnect();
					mCamera.connect(mDevUID);
					mCamera.start(Camera.DEFAULT_AV_CHANNEL, mDevice.View_Account, mDevice.View_Password);
					mCamera.startShow(mSelectedChannel,true);
					Glog.D("www", "CONNECTION_STATE_TIMEOUT_startShow");

					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetSupportStreamReq.parseContent());
					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVINFO_REQ, AVIOCTRLDEFs.SMsgAVIoctrlDeviceInfoReq.parseContent());
					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETAUDIOOUTFORMAT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetAudioOutFormatReq.parseContent());
					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ, AVIOCTRLDEFs.SMsgAVIoctrlTimeZone.parseContent());

					if (mIsListening)
						mCamera.startListening(mSelectedChannel);

				}

				break;

			case Camera.CONNECTION_STATE_CONNECT_FAILED:

				mConnStatus = getText(R.string.connstus_connection_failed).toString();

				if (txtConnectionStatus != null)
					txtConnectionStatus.setText(mConnStatus);

				LiveViewActivity.this.invalidateOptionsMenu();

				break;

			case Camera.CONNECTION_STATE_WRONG_PASSWORD:

				mConnStatus = getText(R.string.connstus_wrong_password).toString();

				if (txtConnectionStatus != null)
					txtConnectionStatus.setText(mConnStatus);

				break;

			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_RESP:
				
				LiveViewActivity.this.invalidateOptionsMenu();
				
				break;
			}

			super.handleMessage(msg);
		}
	};

	@Override
	public View makeView() {
		TextView t = new TextView(this);
		return t;
	}

	@Override
	public void OnSnapshotComplete() {
		// TODO Auto-generated method stub
		MediaScannerConnection.scanFile(LiveViewActivity.this,
                new String[] { mFilePath.toString() }, 
                new String[] {"image/*"},
                new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String path, Uri uri) {
                Log.i("ExternalStorage", "Scanned " + path + ":");
                Log.i("ExternalStorage", "-> uri=" + uri);
				Message msg = handler.obtainMessage();
				msg.what = STS_SNAPSHOT_SCANED;
				handler.sendMessage(msg);
            }
        });
	}

	
	@Override
	public void receiveFrameDataForMediaCodec(Camera camera, int avChannel,byte[] buf, int length) {
		// TODO Auto-generated method stub
		if (monitor != null && monitor.getClass().equals(MediaCodecMonitor.class)) {
            mVideoWidth = ((MediaCodecMonitor)monitor).getVideoWidth();
            mVideoHeight = ((MediaCodecMonitor)monitor).getVideoHeight();
        }
		
		if(wait_receive) {
			
			try {
				Glog.D("test_a", "2");
				Message msg = Message.obtain(handler_wait);
				msg.what = 1;
				handler_wait.sendMessage(msg);
				
				wait_receive = false;
				
            } 
			catch(Exception e){
            	Glog.D("test_a", "error");
            }
		}
	}

	@Override
	public void receiveSnapshot(Bitmap bmp) {
		// TODO Auto-generated method stub
		try {
			FileOutputStream fos = new FileOutputStream(mFilePath);
			bmp.compress(Bitmap.CompressFormat.JPEG, 90, fos);
			fos.flush();
			fos.close();
			addImageGallery(new File(mFilePath));
			
			runOnUiThread(new Runnable() { 
		        public void run() 
		        { 
		        	Toast.makeText(LiveViewActivity.this, getText(R.string.tips_snapshot_ok), Toast.LENGTH_SHORT).show();
		        } 
		    }); 
			
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		runOnUiThread(new Runnable() { 
	        public void run() 
	        { 
	        	Toast.makeText(LiveViewActivity.this, getText(R.string.tips_snapshot_failed), Toast.LENGTH_SHORT).show();
	        } 
	    }); 
		
		}		
	}

	/*
	@Override
	public void onMediaScannerConnected() {
		conn.scanFile(mScanPath, FILE_TYPE);
	}

	@Override
	public void onScanCompleted(String path, Uri uri) {
		try {
			if (mOpenAlbum) {
				if (uri != null) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					//intent.setData(uri);
					Uri hacked_uri = Uri.parse("file://" + uri.getPath());
					intent.setDataAndType(uri, FILE_TYPE);
					startActivity(intent);
				}
			} else {
				System.out.println("Snapshot:" + path + " ok");
				//Toast.makeText(LiveViewActivity.this, getText(R.string.tips_snapshot_ok), Toast.LENGTH_SHORT).show();
				Message msg = handler.obtainMessage();
				msg.what = STS_SNAPSHOT_SCANED;
				handler.sendMessage(msg);
			}
		} finally {
			conn.disconnect();
			conn = null;
		}
	}
	*/
}
