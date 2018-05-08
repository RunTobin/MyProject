package com.linq.xinansmart.ui_activity;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.linq.xinansmart.R;
import com.linq.xinansmart.control.BarbecueActivity;
import com.linq.xinansmart.control.ColorControlActivity;
import com.linq.xinansmart.control.Cook;
import com.linq.xinansmart.control.Garbage;
import com.linq.xinansmart.control.OnOffActivity;
import com.linq.xinansmart.control.OnOffStopActivity;
import com.linq.xinansmart.control.ProgressActivity;
import com.linq.xinansmart.control.StartUpActivity;
import com.linq.xinansmart.control.WashActivity;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.manager.Location_EquipmentManager;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location_equipment;
import com.linq.xinansmart.ui_fragment.LiveView_Fragment;
import com.tutk.IOTC.AVFrame;
import com.tutk.IOTC.AVIOCTRLDEFs;
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
import com.tutk.IOTC.AVIOCTRLDEFs.SMsgAVIoctrlGetSupportStreamReq;
import com.tutk.Logger.Glog;
import com.tutk.P2PCam264.DeviceInfo;
import com.tutk.P2PCam264.LiveViewActivity;
import com.tutk.P2PCam264.MainActivity;
import com.tutk.P2PCam264.MyCamera;
import com.tutk.P2PCam264.New_AddDeviceActivity;

///72KPN7FL1S6WXSPE111A
public class Activity_LiveView extends SherlockActivity implements
		ViewSwitcher.ViewFactory, IRegisterIOTCListener, CameraListener,
		IReceiveSnapshotListener, OnItemClickListener {
	private IMonitor monitor = null;
	private Context mContext;
	private static boolean wait_receive = true;
	private BitmapDrawable bg;
	private BitmapDrawable bgSplit;
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
	private MyCamera mCamera = null;
	private DeviceInfo mDevice = null;

	private TextView textView;
	private boolean mIsListening = false;
	private boolean mIsSpeaking = false;
	private int location_id;
	public List<MyCamera> CameraList = null;
	public List<DeviceInfo> DeviceList = null;
	private static final int BUILD_VERSION_CODES_ICE_CREAM_SANDWICH = 14;
	private static final int STS_CHANGE_CHANNEL_STREAMINFO = 99;
	private static final int STS_SNAPSHOT_SCANED = 98;
	private static final int REQUEST_CODE_ALBUM = 99;
	// private static final String FILE_TYPE = "image/*";
	private List<Location_equipment> location_equipmentlist1 = new ArrayList<Location_equipment>();
	private List<Location_equipment> location_equipmentlist;
	private Location_EquipmentManager location_EquipmentManager;
	private ListView listView;
	private EquipmentManager equipmentManager = EquipmentManager.getInstance();
	private Concenter concenter;
	private Boolean RefreshThreadRun = false;
	private EquipmentAdapter adapter;
	@Override
	protected void onStop() {
		super.onStop();
		RefreshThreadRun=false;
		if (mCamera != null) {
			mCamera.stopSpeaking(mSelectedChannel);
			mCamera.stopListening(mSelectedChannel);
			mCamera.stopShow(mSelectedChannel);
			Glog.E("onPause", "onPause()_stopShow");
		}

		if (monitor != null)
			monitor.deattachCamera();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this;
		CameraList = MainActivity.CameraList;
		DeviceList = MainActivity.DeviceList;
		Bundle bundle = getIntent().getExtras();
		adapter=new EquipmentAdapter();
		concenter = (Concenter) bundle.getSerializable("con");
		location_id = bundle.getInt("location_id");
		mDevUID = bundle.getString("dev_uid");
		mDevUUID = bundle.getString("dev_uuid");
		mConnStatus = bundle.getString("conn_status");
		mSelectedChannel = bundle.getInt("camera_channel");
		
		Glog.E("DEBUG", "START :" + mDevUID);
		initData();
		init();
		new Thread(refrashTask).start();
	}

	Runnable refrashTask = new Runnable() {
		@Override
		public void run() {
			RefreshThreadRun = true;
			while (RefreshThreadRun) {
				try {
					int tick = 0;
					updataData();
					Thread.sleep(1500);

				} catch (Exception e) {
					continue;
				}

			}
			RefreshThreadRun = false;

		}

	};

	private void updataData() {
		List<Equipment> equipmentlist = equipmentManager.getChangeAllEquipment(
				concenter.getUser(), concenter.getPassword());
		location_equipmentlist = location_EquipmentManager.getInstance(
				mContext).getLocation_equipList();
		for (Equipment equipment : equipmentlist) {
			for (Location_equipment location_equipment : location_equipmentlist1) {
				if (equipment.getEquCode().equals(
						location_equipment.getEquCode())) {
					// location_equipment.setSvalue(equipment.getSvalue());
					Location_EquipmentManager location_EquipmentManager = Location_EquipmentManager
							.getInstance(mContext);
					Log.e("id_name", location_equipment.getId()
							+ location_equipment.getName());
					location_EquipmentManager.updataOnline(
							equipment.getEquCode(), equipment.getbOnline(),
							equipment.getSvalue(), equipment.getName(),
							location_equipmentlist);
				}
			}
		}
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				initData();
				adapter.notifyDataSetChanged();
			}
		});
	}

	private void initData() {
		if (location_equipmentlist1 != null) {
			location_equipmentlist1.clear();
		}
		location_equipmentlist = location_EquipmentManager.getInstance(this)
				.getLocation_equipList();
		for (Location_equipment location_equipment : location_equipmentlist) {
			if (location_equipment.getLocation().getId() == location_id) {
				location_equipmentlist1.add(location_equipment);
			}
		}
	}

	private void init() {
		for (MyCamera camera : MainActivity.CameraList) {
			if (mDevUID.equalsIgnoreCase(camera.getUID())
					&& mDevUUID.equalsIgnoreCase(camera.getUUID())) {
				mCamera = camera;
				break;
			}
		}

		for (DeviceInfo dev : MainActivity.DeviceList) {

			if (mDevUID.equalsIgnoreCase(dev.UID)
					&& mDevUUID.equalsIgnoreCase(dev.UUID)) {
				mDevice = dev;
				break;
			}
		}

		if (mCamera != null) {

			mCamera.registerIOTCListener(this);
			mCamera.SetCameraListener(this);

			if (!mCamera.isSessionConnected()) {
				Log.e("______", "_________");
				mCamera.connect(mDevUID);
				mCamera.start(Camera.DEFAULT_AV_CHANNEL, mDevice.View_Account,
						mDevice.View_Password);
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL,
						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_REQ,
						SMsgAVIoctrlGetSupportStreamReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL,
						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVINFO_REQ,
						AVIOCTRLDEFs.SMsgAVIoctrlDeviceInfoReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL,
						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETAUDIOOUTFORMAT_REQ,
						AVIOCTRLDEFs.SMsgAVIoctrlGetAudioOutFormatReq
								.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL,
						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ,
						AVIOCTRLDEFs.SMsgAVIoctrlTimeZone.parseContent());

			}
			initstartview();
		}
	}

	private void initstartview() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_liveview);
		textView = (TextView) findViewById(R.id.status);
		textView.setText("连接状态： " + mConnStatus);
		listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		wait_receive = true;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (monitor != null) {
			monitor.enableDither(mCamera.mEnableDither);
			monitor.attachCamera(mCamera, mSelectedChannel);
		}

		if (mCamera != null) {

			mCamera.startShow(mSelectedChannel, true);
			Glog.D("www", "onResume_startShow");

			if (mIsListening)
				mCamera.startListening(mSelectedChannel);
			if (mIsSpeaking)
				mCamera.startSpeaking(mSelectedChannel);
		}
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
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.bar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.menu_action_icon1) {
			showDialog();
		}
		return super.onOptionsItemSelected(item);
	}

	private View addDeviceView = null;

	private void showDialog() {
		addDeviceView = getLayoutInflater().inflate(R.layout.add_device_row,
				null);
		final AlertDialog alertDialog = new AlertDialog.Builder(
				Activity_LiveView.this).create();
		alertDialog.show();
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.dialog_listview);
		ListView listView = (ListView) window
				.findViewById(R.id.dialog_listview);
		listView.addFooterView(addDeviceView);
		listView.setAdapter(new BaseAdapter() {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				convertView = View.inflate(Activity_LiveView.this,
						R.layout.itme_dialog_listview2, null);
				ImageView imageView = (ImageView) convertView
						.findViewById(R.id.item_image);
				TextView title = (TextView) convertView
						.findViewById(R.id.item_title);
				TextView UID = (TextView) convertView
						.findViewById(R.id.item_UID);
				imageView.setImageBitmap(DeviceList.get(position).Snapshot);
				title.setText(DeviceList.get(position).NickName);
				UID.setText(DeviceList.get(position).UID);
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return DeviceList.size();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position < DeviceList.size()) {
					if (mCamera != null) {

						if (mIsListening)
							mCamera.LastAudioMode = 1;
						else if (mIsSpeaking)
							mCamera.LastAudioMode = 2;
						else
							mCamera.LastAudioMode = 0;

						mCamera.unregisterIOTCListener((IRegisterIOTCListener) mContext);
					}

					mDevUID = DeviceList.get(position).UID;
					mDevUUID = DeviceList.get(position).UUID;
					mConnStatus = DeviceList.get(position).Status;
					mSelectedChannel = DeviceList.get(position).ChannelIndex;
					init();
					// Bundle extras = new Bundle();
					// extras.putString("dev_uid",
					// DeviceList.get(position).UID);
					// extras.putString("dev_uuid",
					// DeviceList.get(position).UUID);
					// extras.putString("dev_nickname",
					// DeviceList.get(position).NickName);
					// extras.putString("conn_status",
					// DeviceList.get(position).Status);
					// extras.putString("view_acc",
					// DeviceList.get(position).View_Account);
					// extras.putString("view_pwd",
					// DeviceList.get(position).View_Password);
					// extras.putInt("camera_channel",
					// DeviceList.get(position).ChannelIndex);

				} else {
					Intent intent = new Intent();
					intent.setClass(Activity_LiveView.this,
							New_AddDeviceActivity.class);
					startActivityForResult(intent, 333333);
				}
				alertDialog.dismiss();
			}
		});
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

				break;

			case STS_SNAPSHOT_SCANED:

				Toast.makeText(Activity_LiveView.this,
						getText(R.string.tips_snapshot_ok), Toast.LENGTH_SHORT)
						.show();

				break;

			case Camera.CONNECTION_STATE_CONNECTING:

				if (!mCamera.isSessionConnected()
						|| !mCamera.isChannelConnected(mSelectedChannel)) {

					mConnStatus = getText(R.string.connstus_connecting)
							.toString();

					if (textView != null)
						textView.setText(mConnStatus);
				}

				break;

			case Camera.CONNECTION_STATE_CONNECTED:

				if (mCamera.isSessionConnected()
						&& avChannel == mSelectedChannel
						&& mCamera.isChannelConnected(mSelectedChannel)) {

					mConnStatus = getText(R.string.connstus_connected)
							.toString();

					if (textView != null)
						textView.setText(mConnStatus);

					Activity_LiveView.this.invalidateOptionsMenu();
				}

				break;

			case Camera.CONNECTION_STATE_DISCONNECTED:

				mConnStatus = getText(R.string.connstus_disconnect).toString();

				if (textView != null)
					textView.setText(mConnStatus);

				Activity_LiveView.this.invalidateOptionsMenu();

				break;

			case Camera.CONNECTION_STATE_UNKNOWN_DEVICE:

				mConnStatus = getText(R.string.connstus_unknown_device)
						.toString();

				if (textView != null)
					textView.setText(mConnStatus);

				Activity_LiveView.this.invalidateOptionsMenu();

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
					mCamera.start(Camera.DEFAULT_AV_CHANNEL,
							mDevice.View_Account, mDevice.View_Password);
					mCamera.startShow(mSelectedChannel, true);
					Glog.D("www", "CONNECTION_STATE_TIMEOUT_startShow");

					mCamera.sendIOCtrl(
							Camera.DEFAULT_AV_CHANNEL,
							AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_REQ,
							AVIOCTRLDEFs.SMsgAVIoctrlGetSupportStreamReq
									.parseContent());
					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL,
							AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVINFO_REQ,
							AVIOCTRLDEFs.SMsgAVIoctrlDeviceInfoReq
									.parseContent());
					mCamera.sendIOCtrl(
							Camera.DEFAULT_AV_CHANNEL,
							AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETAUDIOOUTFORMAT_REQ,
							AVIOCTRLDEFs.SMsgAVIoctrlGetAudioOutFormatReq
									.parseContent());
					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL,
							AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ,
							AVIOCTRLDEFs.SMsgAVIoctrlTimeZone.parseContent());

					if (mIsListening)
						mCamera.startListening(mSelectedChannel);

				}

				break;

			case Camera.CONNECTION_STATE_CONNECT_FAILED:

				mConnStatus = getText(R.string.connstus_connection_failed)
						.toString();

				if (textView != null)
					textView.setText(mConnStatus);

				Activity_LiveView.this.invalidateOptionsMenu();

				break;

			case Camera.CONNECTION_STATE_WRONG_PASSWORD:

				mConnStatus = getText(R.string.connstus_wrong_password)
						.toString();

				if (textView != null)
					textView.setText(mConnStatus);

				break;

			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_RESP:

				Activity_LiveView.this.invalidateOptionsMenu();

				break;
			}

			super.handleMessage(msg);
		}
	};

	private Handler handler_wait = new Handler() {

		public void handleMessage(Message message) {
			switch (message.what) {
			case 1:

				DisplayMetrics metrics = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(metrics);
				Log.i("IOTCamViewer", "" + metrics.widthPixels + " X "
						+ metrics.heightPixels);
				setupViewInPortraitLayout_wait();
				break;
			}
		}

	};

	private void setupViewInPortraitLayout_wait() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		Log.e("Camera_TYPE", Camera.nCodecId_temp + "");
		switch (Camera.nCodecId_temp) {

		case AVFrame.MEDIA_CODEC_VIDEO_MJPEG:
			Glog.E("转换布局", "new_monitor_live_view_landscape");
			setContentView(R.layout.new_monitor_live_view_landscape);
			break;

		case AVFrame.MEDIA_CODEC_VIDEO_H264:
			Glog.E("转换布局", "new_live_view_landscape");
			setContentView(R.layout.new_live_view_landscape);
			break;

		case AVFrame.MEDIA_CODEC_VIDEO_MPEG4:
			Glog.E("转换布局", "new_mjpg4_view_landscape");
			setContentView(R.layout.new_mjpg4_view_landscape);
			break;

		default:
			Glog.E("转换布局", "new_mjpg4_view_landscape");
			setContentView(R.layout.new_mjpg4_view_landscape);
			break;
		}

		if (monitor != null)
			monitor.deattachCamera();

		monitor = null;
		monitor = (IMonitor) findViewById(R.id.monitor);
		monitor.cleanFrameQueue();
		monitor.setMaxZoom(3.0f);
		monitor.enableDither(mCamera.mEnableDither);
		try {
			monitor.attachCamera(mCamera, mSelectedChannel);
		} catch (Exception e) {
			finish();
		}
		monitor.setReceiveotListener(Activity_LiveView.this);
		textView = (TextView) findViewById(R.id.status);
		textView.setText("连接状态： " + mConnStatus);
		listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void receiveFrameData(final Camera camera, int avChannel, Bitmap bmp) {
		// TODO Auto-generated method stub
		// Log.e("FrameData", avChannel + "");
		if (mCamera == camera && avChannel == mSelectedChannel) {
			if (bmp != null
					&& (bmp.getWidth() != mVideoWidth || bmp.getHeight() != mVideoHeight)) {
				mVideoWidth = bmp.getWidth();
				mVideoHeight = bmp.getHeight();
			}
		}

		if (wait_receive) {

			try {
				Glog.D("test_a", "2");
				Message msg = Message.obtain(handler_wait);
				msg.what = 1;
				handler_wait.sendMessage(msg);
				wait_receive = false;

			} catch (Exception e) {
				Glog.D("test_a", "error");
			}
		}
	}

	@Override
	public void receiveFrameDataForMediaCodec(Camera arg0, int arg1,
			byte[] arg2, int arg3) {
		// TODO Auto-generated method stub
		// Log.e("aForMediaCodec", arg1 + "");
		if (monitor != null
				&& monitor.getClass().equals(MediaCodecMonitor.class)) {
			mVideoWidth = ((MediaCodecMonitor) monitor).getVideoWidth();
			mVideoHeight = ((MediaCodecMonitor) monitor).getVideoHeight();
		}

		if (wait_receive) {

			try {
				Glog.E("11111111111111", "1111111111");
				Message msg = Message.obtain(handler_wait);
				msg.what = 1;
				handler_wait.sendMessage(msg);

				wait_receive = false;

			} catch (Exception e) {
				Glog.D("test_a", "error");
			}
		}

	}

	@Override
	public void receiveIOCtrlData(final Camera camera, int avChannel,
			int avIOCtrlMsgType, byte[] data) {
		// TODO Auto-generated method stub
		Log.e("IOCtrlData", avChannel + "");
		if (mCamera == camera) {
			Bundle bundle = new Bundle();
			bundle.putInt("avChannel", avChannel);
			bundle.putByteArray("data", data);
			Message msg = handler.obtainMessage();
			msg.what = avIOCtrlMsgType;
			handler.sendMessage(msg);
		}
	}

	@Override
	public void receiveFrameInfo(final Camera camera, int avChannel,
			long bitRate, int frameRate, int onlineNm, int frameCount,
			int incompleteFrameCount) {
		// TODO Auto-generated method stub
		// Log.e("FrameInfo", avChannel + "");
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
	public void receiveSessionInfo(final Camera camera, int resultCode) {
		// TODO Auto-generated method stub
		Log.e("哇哈哈哈哈", resultCode + "");
		if (mCamera == camera) {
			Bundle bundle = new Bundle();
			Message msg = handler.obtainMessage();
			msg.what = resultCode;
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}

	@Override
	public void receiveChannelInfo(final Camera camera, int avChannel,
			int resultCode) {
		// TODO Auto-generated method stub
		Log.e("ChannelInfo", avChannel + "");
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
	public View makeView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void receiveSnapshot(Bitmap arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnSnapshotComplete() {
		// TODO Auto-generated method stub

	}

	ImageView imageView;
	TextView text_location;
	TextView text_type;
	TextView text_value;
	TextView text_netgate;
	ImageView img_netgate;

	class EquipmentAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return location_equipmentlist1.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Location_equipment crEquipment = location_equipmentlist1
					.get(position);
			convertView = View.inflate(mContext, R.layout.fragment_allequ_item,
					null);
			imageView = (ImageView) convertView.findViewById(R.id.item_img);
			text_location = (TextView) convertView
					.findViewById(R.id.item_text_location);
			text_type = (TextView) convertView
					.findViewById(R.id.item_text_type);
			text_value = (TextView) convertView
					.findViewById(R.id.item_text_value);
			text_netgate = (TextView) convertView
					.findViewById(R.id.item_text_netgate);
			img_netgate = (ImageView) convertView
					.findViewById(R.id.item_img_netaget);
			if (crEquipment.getbOnline()) {
				text_location.setText("在线状态： 在线");
			} else {
				text_location.setText("在线状态： 不在线");
			}
			text_value.setText(equipmentManager.GetDisplayValue(
					crEquipment.getType(), crEquipment.getNindex(),
					crEquipment.getSvalue()));
			imageView.setImageResource(equipmentManager.getImage2(crEquipment));
			text_type.setText(crEquipment.getName());
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Location_equipment equipment = location_equipmentlist1.get(position);
		if (equipment != null) {
			if (equipment.getbOnline() == true) {
				int equipmentId = equipment.getId();
				int cenType = 1;

				switch (equipment.getType()) {

				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 11:
				case 13:
				case 14:
					Intent intent = new Intent(mContext, OnOffActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("location_equiment", equipment);
					intent.putExtras(bundle);
//					Toast.makeText(mContext, equipment.getSvalue(),
//							Toast.LENGTH_SHORT).show();
					// intent.putExtra("equipmentId", equipmentId);
					intent.putExtra("cenType", cenType);
					intent.putExtra("user", concenter.getUser());
					intent.putExtra("password", concenter.getPassword());
					startActivity(intent);
					break;
				case 6:
					Intent intent2 = new Intent(mContext,
							OnOffStopActivity.class);
					// intent2.putExtra("equipmentId", equipmentId);
					Bundle bundle2 = new Bundle();
					bundle2.putSerializable("location_equiment", equipment);
					intent2.putExtras(bundle2);
					intent2.putExtra("cenType", cenType);
					intent2.putExtra("user", concenter.getUser());
					intent2.putExtra("password", concenter.getPassword());
					startActivity(intent2);
					break;
				case 7:
				case 8:
				case 20:
					Intent intent3 = new Intent(mContext,
							ProgressActivity.class);
					// intent3.putExtra("equipmentId", equipmentId);
					Bundle bundle3 = new Bundle();
					bundle3.putSerializable("location_equiment", equipment);
					intent3.putExtras(bundle3);
					intent3.putExtra("cenType", cenType);
					intent3.putExtra("user", concenter.getUser());
					intent3.putExtra("password", concenter.getPassword());
					startActivity(intent3);
					break;
				case 9:
					break;
				case 10: {
					switch (equipment.getNindex()) {
					case 1:
						break;
					case 2:
						break;
					case 3:
						Intent intent4 = new Intent(mContext,
								StartUpActivity.class);
						// intent4.putExtra("equipmentId", equipmentId);
						Bundle bundle4 = new Bundle();
						bundle4.putSerializable("location_equiment", equipment);
						intent4.putExtras(bundle4);
						intent4.putExtra("cenType", cenType);
						intent4.putExtra("user", concenter.getUser());
						intent4.putExtra("password", concenter.getPassword());
						startActivity(intent4);
						break;
					case 4:
						Intent intent5 = new Intent(mContext,
								StartUpActivity.class);
						// intent5.putExtra("equipmentId", equipmentId);
						Bundle bundle5 = new Bundle();
						bundle5.putSerializable("location_equiment", equipment);
						intent5.putExtras(bundle5);
						intent5.putExtra("cenType", cenType);
						intent5.putExtra("user", concenter.getUser());
						intent5.putExtra("password", concenter.getPassword());
						startActivity(intent5);
						break;
					}
				}
					break;
				case 15:
					Intent intent6 = new Intent(mContext,
							ColorControlActivity.class);
					// intent6.putExtra("equipmentId", equipmentId);
					Bundle bundle6 = new Bundle();
					bundle6.putSerializable("location_equiment", equipment);
					intent6.putExtras(bundle6);
					intent6.putExtra("cenType", cenType);
					intent6.putExtra("user", concenter.getUser());
					intent6.putExtra("password", concenter.getPassword());
					startActivity(intent6);
					break;
				case 23: {

					switch (equipment.getNindex()) {
					case 1:
						Intent wash = new Intent(mContext, WashActivity.class);
						Bundle bundle7 = new Bundle();
						bundle7.putSerializable("location_equiment", equipment);
						wash.putExtras(bundle7);
						wash.putExtra("user", concenter.getUser());
						wash.putExtra("password", concenter.getPassword());
						startActivity(wash);
						break;
					case 2:
						Intent barbecue = new Intent(mContext,
								BarbecueActivity.class);
						Bundle bundle8 = new Bundle();
						bundle8.putSerializable("location_equiment", equipment);
						barbecue.putExtras(bundle8);
						barbecue.putExtra("user", concenter.getUser());
						barbecue.putExtra("password", concenter.getPassword());
						startActivity(barbecue);
						break;
					case 3:
						Intent cook = new Intent(mContext, Cook.class);
						Bundle bundle9 = new Bundle();
						bundle9.putSerializable("location_equiment", equipment);
						cook.putExtras(bundle9);
						cook.putExtra("user", concenter.getUser());
						cook.putExtra("password", concenter.getPassword());
						startActivity(cook);
						break;
					case 4:
						Intent garbage = new Intent(mContext, Garbage.class);
						Bundle bundle10 = new Bundle();
						bundle10.putSerializable("location_equiment", equipment);
						garbage.putExtras(bundle10);
						garbage.putExtra("user", concenter.getUser());
						garbage.putExtra("password", concenter.getPassword());
						startActivity(garbage);
						break;

					}

				}
				default:
					break;
				}
			} else {
				Toast.makeText(mContext, "该设备不在线！", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
