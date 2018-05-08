package com.linq.xinansmart.ui_activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mvp.I_Image;
import mvp.ImagePresenter;
import android.R.integer;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
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
import com.linq.xinansmart.manager.LocationManager;
import com.linq.xinansmart.manager.Location_EquipmentManager;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location;
import com.linq.xinansmart.model.Location_equipment;
import com.linq.xinansmart.ui_activity.Activity_LiveView.EquipmentAdapter;
import com.linq.xinansmart.ui_fragment.VideoFragment;
import com.squareup.picasso.Picasso;
import com.tutk.IOTC.AVFrame;
import com.tutk.IOTC.AVIOCTRLDEFs;
import com.tutk.IOTC.Camera;
import com.tutk.IOTC.IMonitor;
import com.tutk.IOTC.IOTCAPIs;
import com.tutk.IOTC.IReceiveSnapshotListener;
import com.tutk.IOTC.MediaCodecMonitor;
import com.tutk.IOTC.St_SInfo;
import com.tutk.IOTC.AVIOCTRLDEFs.SMsgAVIoctrlGetSupportStreamReq;
import com.tutk.IOTC.CameraListener;
import com.tutk.IOTC.IRegisterIOTCListener;
import com.tutk.Logger.Glog;
import com.tutk.P2PCam264.AddDeviceActivity;
import com.tutk.P2PCam264.DatabaseManager;
import com.tutk.P2PCam264.DeviceInfo;
import com.tutk.P2PCam264.LiveViewActivity;
import com.tutk.P2PCam264.MainActivity;
import com.tutk.P2PCam264.MyCamera;
import com.tutk.P2PCam264.New_AddDeviceActivity;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;
import com.videogo.openapi.bean.EZCameraInfo;

public class Activity_Location_Detail extends SherlockActivity implements
		OnClickListener, I_Image,  OnItemClickListener,
		CameraListener, SurfaceHolder.Callback {
	private RelativeLayout frameLayout;
	private Location location;  //区域对象
	private Location_EquipmentManager location_EquipmentManager;
	private List<Location_equipment> location_equipmentlist;
	private List<Location_equipment> location_equipmentlist1 = new ArrayList<Location_equipment>();
	private Concenter concenter;
	private ImagePresenter imagePresenter;
	private ImageView backgound;
	private TextView locationTextView;
	private Boolean RefreshThreadRun = false;
	private EquipmentManager equipmentManager;
	private ProgressDialog progressDialog;
	private String image;
	private List<TextView> viewlist = new ArrayList<TextView>();
	public List<MyCamera> CameraList = null;
	public List<DeviceInfo> DeviceList = null;
	private ImageButton add_camButton;
	private ImageButton switch_button;
	private DeviceInfo deviceInfo;
	private String mDevUID;
	private String mDevUUID;
	private String mConnStatus = "";
	private Context mContext;
	private EquipmentAdapter adapter;
	private int mSelectedChannel;
	private int location_id;
	private MyCamera mCamera = null;
	private DeviceInfo mDevice = null;
	private TextView textView;
	private ListView listView;
	private static boolean wait_receive = true;
	private IMonitor monitor = null;
	private boolean mIsListening = false;
	private boolean mIsSpeaking = false;
	private static final int BUILD_VERSION_CODES_ICE_CREAM_SANDWICH = 14;
	private static final int STS_CHANGE_CHANNEL_STREAMINFO = 99;
	private static final int STS_SNAPSHOT_SCANED = 98;
	private static final int REQUEST_CODE_ALBUM = 99;
	private int mVideoWidth;
	private int mVideoHeight;
	private int mVideoFPS;
	private long mVideoBPS;
	private int mOnlineNm;
	private int mFrameCount;
	private int mIncompleteFrameCount;
	private Boolean isSwitch = false;
	private Boolean none = false;
	private LocationManager locationManager;
	private List<Location> locationlist=new ArrayList<Location>();
	
	
	private Thread reFresh;
	private void setView(String s){
		locationlist=locationManager.getLocationList();
		for(Location locations:locationlist){
			if(location.getId()==locations.getId()){
				locationManager.updataView(locations, s);
			}
		}
		Log.e("写入了VIew", s);
		SharedPreferences sp = this.getSharedPreferences("view",  
                MODE_PRIVATE);  
		sp.edit().putString("view", s).commit();
	}
	
	private String getView(){
		if(location.getView()!=null){
			Log.e("得到的VIew",location.getView());
			return location.getView();
		}
//		return null;
		SharedPreferences sp = this.getSharedPreferences("view",  
                MODE_PRIVATE);  
		return sp.getString("view", "location");
	}
	
	private void setUID(String s){
		locationlist=locationManager.getLocationList();
		for(Location locations:locationlist){
			if(location.getId()==locations.getId()){
				locationManager.updataUID(locations, s);
			}
		}
		Log.e("写入了UID", s);
		SharedPreferences sp = this.getSharedPreferences("UID",  
                MODE_PRIVATE);  
		sp.edit().putString("UID", s).commit();
	}
	
	private String getUID(){
		if(location.getUID()!=null){
			Log.e("得到了UID",location.getUID());
			return location.getUID();
		}
		Log.e("得到了UID","空");
//		return null;
		SharedPreferences sp = this.getSharedPreferences("UID",  
                MODE_PRIVATE);  
		return sp.getString("UID", "0");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
//		CameraList = MainActivity.CameraList;
//		DeviceList = MainActivity.DeviceList;
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		isSwitch = false;
		none = false;
		locationManager=LocationManager.getInstance(this);
		location = (Location) getIntent().getExtras().getSerializable(
				"location");
		concenter = (Concenter) getIntent().getExtras().getSerializable("con");
		equipmentManager = EquipmentManager.getInstance();
		String view=getView();
		Log.e("view", view);
		if(getUID()!=null){
			Log.e("UID", getUID());
		}else {
			view="location";
		}

		if(view.equals("location")){
			progressDialog = new ProgressDialog(Activity_Location_Detail.this);
			progressDialog.setTitle("更新数据中");
			progressDialog.show();
			initlocationView();
		}else {
			String UID=getUID();
			for(EZCameraInfo info:mCameraInfoList){
				if(UID.equals(info.getCameraId())){
					isSwitch = true;
					none=true;
					cameraInfo=info;
					initvideoviewhaikang();
					break;
				}
			}
			if(none==false){
				Log.e("none--","none");
				initlocationView();
			}
		}
		reFresh=new Thread(refrashTask);
		reFresh.start();
		//new Thread(refrashTask).start();

		super.onCreate(savedInstanceState);
	}

	private Boolean change=false;

	private void initlocationView() {
		setContentView(R.layout.activity_location_detail);
		Display currentDisplay =getWindowManager().getDefaultDisplay();
		add_camButton = (ImageButton) findViewById(R.id.add_cam);
		switch_button = (ImageButton) findViewById(R.id.switch_cam);
		locationTextView = (TextView) findViewById(R.id.text_main);
		locationTextView.setText("当前区域名: " + location.getName());
		backgound = (ImageView) findViewById(R.id.imageBackgoound);
		TextView locationnameTextView = (TextView) findViewById(R.id.text_main);
		ActionBar actionBar1 = getSupportActionBar();
		add_camButton.setVisibility(View.GONE);
		actionBar1.setTitle("区域名： " + location.getName());
		locationnameTextView.setText("当前区域名: "+location.getName());
		frameLayout = (RelativeLayout) findViewById(R.id.frame);
		image = location.getBackground();
		Log.e("image", image);
		imagePresenter = new ImagePresenter(this,currentDisplay);
		imagePresenter.getDrawble(image);
		initData();
		showEquiment();
	
		switch_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(getUID()!=null){
					String UID=getUID();
					setView("video");
					for(EZCameraInfo info:mCameraInfoList){
						if(UID.equals(info.getCameraId())){
							isSwitch = true;
							none=true;
							cameraInfo=info;
							change=true;
							setUID(cameraInfo.getCameraId());
							initvideoviewhaikang();
							break;
						}
					}
					if(change==false){
						change=true;
						showCameraDialog();
					}
				
				}else {
					change=true; 
					showCameraDialog();
				}
				
			}
		});
	}



	Runnable refrashTask = new Runnable() {
		@Override
		public void run() {
			RefreshThreadRun = true;
			while (RefreshThreadRun) {
				try {
//					int tick = 0;
					updataData();
					Thread.sleep(500);

				} catch (Exception e) {
					continue;
				}

			}
			RefreshThreadRun = false;

		}

	};


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
		finish();
	}
	// 从数据库获取数据
	private void initData() {
		if (location_equipmentlist1 != null) {
			location_equipmentlist1.clear();
		}
		location_equipmentlist = location_EquipmentManager.getInstance(this)
				.getLocation_equipList();
		for (Location_equipment location_equipment : location_equipmentlist) {
			if (location_equipment.getLocation().getId() == location.getId()) {
				location_equipmentlist1.add(location_equipment);
			}
		}
	}

	// 获取设备的当前状态，并刷新数据库       
	private void updataData() {
		List<Equipment> equipmentlist = equipmentManager.getEquipment(
				concenter.getUser(), concenter.getPassword());
		Log.e("___________", equipmentlist.size() + "");
		Log.e("_______", location_equipmentlist1.size() + "");
		location_equipmentlist = location_EquipmentManager.getInstance(
				Activity_Location_Detail.this).getLocation_equipList();
		// Log.e("id_name", location_equipmentlist1.get(0).getMachinID()+"");
		for (Equipment equipment : equipmentlist) {
			for (Location_equipment location_equipment : location_equipmentlist1) {
				if (equipment.getEquCode().equals(
						location_equipment.getEquCode())) {
					// location_equipment.setSvalue(equipment.getSvalue());
					Location_EquipmentManager location_EquipmentManager = Location_EquipmentManager
							.getInstance(Activity_Location_Detail.this);
					Log.e("id_name", location_equipment.getId()
							+ location_equipment.getName());
					try {
						location_EquipmentManager.updataOnline(
								equipment.getEquCode(), equipment.getbOnline(),
								equipment.getSvalue(), equipment.getName(),
								location_equipmentlist);
					} catch (Exception e) {
						RefreshThreadRun = false;
						reFresh.stop();
						reFresh.start();
					}

				}
			}
		}
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				initData();
				if (isSwitch == false) {
					showEquiment();
					if (progressDialog != null) {
						progressDialog.dismiss();
					}
				} else {
					adapter.notifyDataSetChanged();
				}
			}
		});
	}

	// 在页面中显示数据库中的数据
	private void showEquiment() {
		int i = 0;
		if (viewlist.size() > 0) {
			for (TextView textView : viewlist) {
				frameLayout.removeView(textView);
			}
			viewlist.clear();
		}
		for (Location_equipment location_equipment : location_equipmentlist1) {
			TextView textView = new TextView(Activity_Location_Detail.this);
			textView.setText(location_equipment.getName());
			textView.setGravity(Gravity.CENTER);
			if (location_equipment.getType() != 0) {
				setDrawble(textView, location_equipment);
			}
			textView.setTextColor(getResources().getColor(R.color.colorPrimary));
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(location_equipment.getX(),
					location_equipment.getY()+160, 0, 0);
			textView.setLayoutParams(params);
			textView.setTag(R.id.about_image, location_equipment);
			textView.setOnClickListener(this);
			viewlist.add(textView);
			frameLayout.addView(textView);
		}

	}

	private void displayImage(ImageView imageView, int drawable) {

		Picasso.with(Activity_Location_Detail.this).load(drawable)
				.resize(300, 300).into(imageView);
	}

	private void setDrawble(TextView textView, Location_equipment equipment) {
		// TODO Auto-generated method stub
		Drawable drawable = getResources().getDrawable(
				EquipmentManager.getInstance().getImage2(equipment));
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, 100, 100);
		textView.setCompoundDrawables(null, drawable, null, null);
	}

	private Location_equipment equipment;

	@Override
	public void onClick(View v) {
		equipment = (Location_equipment) v.getTag(R.id.about_image);
		// Toast.makeText(Activity_Location_Detail.this, equipment.getName(),
		// Toast.LENGTH_SHORT).show();
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
					Intent intent = new Intent(Activity_Location_Detail.this,
							OnOffActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("location_equiment", equipment);
					intent.putExtras(bundle);
					// Toast.makeText(Activity_Location_Detail.this,
					// equipment.getSvalue(), Toast.LENGTH_SHORT).show();
					// intent.putExtra("equipmentId", equipmentId);
					intent.putExtra("cenType", cenType);
					intent.putExtra("user", concenter.getUser());
					intent.putExtra("password", concenter.getPassword());
					startActivity(intent);
					break;
				case 6:
					Intent intent2 = new Intent(Activity_Location_Detail.this,
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
					Intent intent3 = new Intent(Activity_Location_Detail.this,
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
						Intent intent4 = new Intent(
								Activity_Location_Detail.this,
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
						Intent intent5 = new Intent(
								Activity_Location_Detail.this,
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
					Intent intent6 = new Intent(Activity_Location_Detail.this,
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
						Intent wash = new Intent(Activity_Location_Detail.this,
								WashActivity.class);
						Bundle bundle7 = new Bundle();
						bundle7.putSerializable("location_equiment", equipment);
						wash.putExtras(bundle7);
						wash.putExtra("user", concenter.getUser());
						wash.putExtra("password", concenter.getPassword());
						startActivity(wash);
						break;
					case 2:
						Intent barbecue = new Intent(
								Activity_Location_Detail.this,
								BarbecueActivity.class);
						Bundle bundle8 = new Bundle();
						bundle8.putSerializable("location_equiment", equipment);
						barbecue.putExtras(bundle8);
						barbecue.putExtra("user", concenter.getUser());
						barbecue.putExtra("password", concenter.getPassword());
						startActivity(barbecue);
						break;
					case 3:
						Intent cook = new Intent(Activity_Location_Detail.this,
								Cook.class);
						Bundle bundle9 = new Bundle();
						bundle9.putSerializable("location_equiment", equipment);
						cook.putExtras(bundle9);
						cook.putExtra("user", concenter.getUser());
						cook.putExtra("password", concenter.getPassword());
						startActivity(cook);
						break;
					case 4:
						Intent garbage = new Intent(
								Activity_Location_Detail.this, Garbage.class);
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
				Toast.makeText(Activity_Location_Detail.this, "改设备不在线！",
						Toast.LENGTH_SHORT).show();
			}
		}
		// Toast.makeText(Activity_Location_Detail.this, equipment.getName(),
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public void checkImage(int drawble) {
		// TODO Auto-generated method stub
		displayImage(backgound, drawble);
	}

	private View addDeviceView = null;
	private EZCameraListAdapter mAdapter = null;
	EZOpenSDK mEzOpenSDK;
	AlertDialog alertDialog = null;
	//海康摄像头列表
	private void showCameraDialog(){
		addDeviceView = getLayoutInflater().inflate(R.layout.cameralist_page,
				null);
	    alertDialog = new AlertDialog.Builder(
				Activity_Location_Detail.this).create();
		alertDialog.show();
		new GetModListThread().start();
		mEzOpenSDK = EZOpenSDK.getInstance(); 
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.dialog_listview);
		ListView listView = (ListView) window
				.findViewById(R.id.dialog_listview);
		listView.addFooterView(addDeviceView);	
		mAdapter=new EZCameraListAdapter();
		listView.setAdapter(mAdapter);
		
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
					// Toast.makeText(mContext, equipment.getSvalue(),
					// Toast.LENGTH_SHORT).show();
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
	public void getimage(Bitmap bitmap) {
		backgound.setImageBitmap(bitmap);
		
	}
	
	//海康摄像头
	/**
	  * 摄像头适配器
	  * @author Administrator
	  *
	  */
	public static List<EZCameraInfo> mCameraInfoList = new ArrayList<EZCameraInfo>();
	EZCameraInfo cameraInfo =null;
   class EZCameraListAdapter extends BaseAdapter implements View.OnClickListener{
		 
		@Override
		public int getCount() {
			if (mCameraInfoList != null && mCameraInfoList.size() != 0) {
				return mCameraInfoList.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			EZCameraInfo item = null;
	        if (position >= 0 && getCount() > position) {
	            item = mCameraInfoList.get(position);
	        }
	        return item;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			 // 自定义视图
	        ViewHolder viewHolder = null;
	        if (convertView == null) {
	            viewHolder = new ViewHolder();

	         // 获取list_item布局文件的视图
	            convertView = LayoutInflater.from(Activity_Location_Detail.this).inflate(R.layout.cameralist_small_item, null);

	         // 获取控件对象
	            viewHolder.playBtn = (ImageView) convertView.findViewById(R.id.item_play_btn);

	            viewHolder.cameraNameTv = (TextView) convertView.findViewById(R.id.camera_name_tv);
	            
	            // 设置点击图标的监听响应函数 
	            convertView.setTag(viewHolder);
	        } else {
	            viewHolder = (ViewHolder) convertView.getTag();
	        }
	        
	        // 设置position
	        viewHolder.playBtn.setTag(position);

	        cameraInfo = (EZCameraInfo) getItem(position);
	        if(cameraInfo != null) {
	            if (cameraInfo.getOnlineStatus() == 0) {
	                viewHolder.playBtn.setVisibility(View.GONE);
	            } else {
	                viewHolder.playBtn.setVisibility(View.VISIBLE);
	                viewHolder.playBtn.setOnClickListener(this);
	            }
	            viewHolder.cameraNameTv.setText(cameraInfo.getCameraName());   
	        }
	        
	        return convertView;
		}
		   /**
	     * 自定义控件集合
	     * @author Administrator
	     *
	     */
	   public class ViewHolder {
	        public ImageView playBtn;
	        public TextView cameraNameTv;
	    }
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			if(v.getId() == R.id.item_play_btn){
		
				
//				Toast.makeText(getActivity(), "视频就是它", Toast.LENGTH_LONG).show();
			
//		}
			switch(v.getId()){
			case R.id.item_play_btn:
				
//				Toast.makeText(getActivity(), " this is a  camera", Toast.LENGTH_LONG).show();
//				RelativeLayout relativeLayout = (RelativeLayout) v;
//				int position = (Integer) relativeLayout.getTag();
//				EZCameraInfo camera = mCameraInfoList.get(position);
//				Toast.makeText(getActivity(), cameraInfo.getCameraName(), Toast.LENGTH_LONG).show();
//				Bundle bundle = new Bundle();
//				bundle.putString("camera", cameraInfo.getCameraId());
//				Intent intent = new Intent();
//				intent.setClass(Activity_Location_Detail.this, VideoPlayActivity.class);
//				intent.putExtras(bundle);
//				startActivity(intent);
				
				setView("video");
				setUID(cameraInfo.getCameraId());
				isSwitch = true;
//				initvideoView();
				initvideoviewhaikang();
				alertDialog.dismiss();
//				finish();
				    break;
				default:
					break;
			}
		}
	}
   
   private void initvideoviewhaikang(){
	    mContext = this;
		adapter = new EquipmentAdapter();
		location_id = location.getId();
		setUID(cameraInfo.getCameraId());
		initData();
		initstartviewhaikang();
   }

	private void initstartviewhaikang() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_liveview1);
		textView = (TextView) findViewById(R.id.status);
//		textView.setText("连接状态： " + mConnStatus);
		listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		wait_receive = true;
		mRealPlaySv = (SurfaceView) findViewById(R.id.realplay_sv);
		mEzOpenSDK=EZOpenSDK.getInstance();
//		Toast.makeText(this, camera, Toast.LENGTH_LONG).show();
		getlist();
		mRealPlaySv.getHolder().addCallback(this);
		add_camButton = (ImageButton) findViewById(R.id.add_cam);
		switch_button = (ImageButton) findViewById(R.id.switch_cam);
		locationTextView = (TextView) findViewById(R.id.text_main);
		locationTextView.setText("当前区域名: " + location.getName());
		add_camButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showCameraDialog();
			}
		});
		switch_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				setView("location");
				isSwitch = false;
				setUID(cameraInfo.getCameraId());
				initlocationView();
			}
		});
	}
   	//获取所有摄像头列表的界面
	public final int UPDATE = 0;
	public final int STOP=1;
	int mErrorCode;
	public Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE:
				mAdapter.notifyDataSetChanged();
				break;
			}
		};
	};
	public class GetModListThread extends Thread {

		@Override
		public void run() {
			super.run();
			getCameraList();
		}
	}
	
	// 获取摄像头所有列表
	public  void getCameraList() {
		try {
			mCameraInfoList = mEzOpenSDK.getCameraList(0, 10);
			Message msg = mhandler.obtainMessage();
			mhandler.sendEmptyMessage(UPDATE);
		} catch (BaseException e) {
			mErrorCode = e.getErrorCode();
			Log.e("size", "121412341234");
		}
	}
  
	
	//海康摄像头播放需要继承的类
	SurfaceView mRealPlaySv;
	EZPlayer mEzPlayer;
	SurfaceHolder mRealPlaySh;
	Handler handler1=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				mEzPlayer.startRealPlay();
				break;
			default:
				break;
			}
		};
	};
	
	//获取该单个摄像头的ＩＤ来进行播放
		private void getlist() {
			// TODO Auto-generated method stub
			new Thread(new Runnable() {
				@Override
				public void run() {
						mEzPlayer = mEzOpenSDK.createPlayer(Activity_Location_Detail.this, cameraInfo.getCameraId());
						if(mEzPlayer == null)
						return;
						mEzPlayer.setHandler(handler1);
						mEzPlayer.setSurfaceHold(mRealPlaySh);
						Message msg=new Message();
						msg.what=0;
						handler1.sendMessage(msg);
						}
					//Log.e("size", result.size()+"");
			}).start();
		}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (mEzPlayer != null) {
			mEzPlayer.setSurfaceHold(holder);
			    }
			mRealPlaySh = holder;
			Log.e("1111111", "11111111111");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		mEzPlayer.stopRealPlay();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();		
		finish();
	}
	
	

}
