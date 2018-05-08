package com.tutk.P2PCam264;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.nineoldandroids.animation.ObjectAnimator;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.ConcenterManager;
import com.linq.xinansmart.model.Add_Device_Event;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.GateWay;
import com.linq.xinansmart.ui_fragment.EquipMentFragment;
import com.linq.xinansmart.ui_fragment.MainUI_Fragment;
import com.linq.xinansmart.ui_fragment.MenuFragment;
import com.linq.xinansmart.ui_fragment.No_Concenter_Fragment;
import com.linq.xinansmart.ui_fragment.ScenceFragment;
import com.linq.xinansmart.ui_fragment.VideoFragment;
import com.linq.xinansmart.ui_slidingmenu.SlidingMenu;
import com.tutk.IOTC.AVIOCTRLDEFs;
import com.tutk.IOTC.AVIOCTRLDEFs.SMsgAVIoctrlGetSupportStreamReq;
import com.tutk.IOTC.Camera;
import com.tutk.IOTC.IRegisterIOTCListener;
import com.tutk.IOTC.Packet;
import com.tutk.Logger.Glog;
import com.videogo.openapi.EZOpenSDK;

import de.greenrobot.event.EventBus;

public class MainActivity extends SherlockFragmentActivity implements
		android.view.View.OnClickListener,
		OnCheckedChangeListener {

	private static final int Build_VERSION_CODES_ICE_CREAM_SANDWICH = 14;
	
	public static int nShowMessageCount = 0;
	private static final int REQUEST_CODE_CAMERA_ADD = 0;
	private static final int REQUEST_CODE_CAMERA_VIEW = 1;
	private static final int REQUEST_CODE_CAMERA_EDIT = 2;
	private static final int REQUEST_CODE_CAMERA_HISTORY = 3;

	private final int CONTEXT_MENU_ID = 1;
	private IconContextMenu iconContextMenu = null;
	private static final int CTX_MENU_ITEM_RECONNECT = Menu.FIRST;
	private static final int CTX_MENU_ITEM_EDIT_CAM = Menu.FIRST + 1;
	private static final int CTX_MENU_ITEM_VIEW_EVENT = Menu.FIRST + 2;
	private static final int CTX_MENU_ITEM_VIEW_SNAPSHOT = Menu.FIRST + 3;
	private static final int CTX_MENU_ITEM_REMOVE_CAM = Menu.FIRST + 4;

	private static final int OPT_MENU_ITEM_ADD_CAM = Menu.FIRST;
	private static final int OPT_MENU_ITEM_ABOUT = Menu.FIRST + 1;
	private static final int OPT_MENU_ITEM_EXIT = Menu.FIRST + 2;
	private SlidingMenu slidingMenu = null;

	private DeviceInfo selectedDevice = null;
	private MyCamera selectedCamera = null;
	public static String token = null;
	public static boolean noResetWiFi = true;
	// private Timer timer = new Timer(true);
	// public String[] allFiles;
	// private String SCAN_PATH;
	// private static final String FILE_TYPE = "image/*";
	// private MediaScannerConnection conn;

	private static final int idCmd_AddCamera = 7000;
	private static final int idCmd_LiveView = 7001;
	private int mCustomURL_CmdID;
	private String mstrUid_FromCustomURL;

	public static final int CAMERA_MAX_LIMITS = 20;// 设置摄像头最大添加数量
	public static List<MyCamera> CameraList = new ArrayList<MyCamera>();
	public static List<DeviceInfo> DeviceList = Collections
			.synchronizedList(new ArrayList<DeviceInfo>());
	
	private IntentFilter filter;
	private ThreadTPNS thread;
	
	public static long startTime = 0;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();	
		if (Build.VERSION.SDK_INT < Build_VERSION_CODES_ICE_CREAM_SANDWICH) {
			BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(
					R.drawable.bg_striped);
			bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setBackgroundDrawable(bg);
		}

		if (isNetworkAvailable()) {
			setupView();
		} else {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			setContentView(R.layout.no_network_connection);

			Button btnRetry = (Button) findViewById(R.id.btnRetry);
			btnRetry.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					if (isNetworkAvailable())
						setupView();
				}
			});
		}

	}
	/**
	 * 按键退出对话框
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setMessage(MainActivity.this.getText(R.string.dialog_Exit));

			builder.setPositiveButton(
					MainActivity.this.getText(R.string.btnExit),
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							quit();
						}
					});
			builder.setNeutralButton(
					MainActivity.this.getText(R.string.btnRunInBackground),
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							MainActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									moveTaskToBack(true);
								}
							});
							if (CameraList.size() > 0) {
								MainActivity.this.moveTaskToBack(true);
							}
						}
					});
			builder.setNegativeButton(
					MainActivity.this.getText(R.string.btnCancel),
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create().show();
			return false;

		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		Configuration cfg = getResources().getConfiguration();

		if (cfg.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			System.out.println("ORIENTATION_LANDSCAPE");

		} else if (cfg.orientation == Configuration.ORIENTATION_PORTRAIT) {
			System.out.println("ORIENTATION_PORTRAIT");
		}
	}
	

	
	public void quit() {

		stopOnGoingNotification();

		finish();// IOTC

	}

	private void queryConcenter() {
		concenterManager = ConcenterManager.getInstance(MainActivity.this);
		List<Concenter> reusltlist = concenterManager.getconList(); // 查询数据库的控制中心列表
		if (reusltlist != null) {
			conList = reusltlist; // 当数据库中列表不为空的 时候复制给menuList中的conList
			// 更新menuList列表
		}
	}

	//记录上次的用户
	private void saveUserData(Context context, String string) {
		SharedPreferences sp = context.getSharedPreferences("Config",
				MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("LastUser", string);
		editor.commit();
	}
	
	//记录上次的视图
	private void saveViewData(Context context, String string) {
		SharedPreferences sp = context.getSharedPreferences("Config",
				MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("LastView", string);
		editor.commit();
	}

	//得到上次的用户
	private String loadUser(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Config",
				MODE_PRIVATE);
		return sp.getString("LastUser", "").toString();
	}
	
	//得到上次的视图
	private String loadView(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Config",
				MODE_PRIVATE);
		return sp.getString("LastView", "").toString();
	}

	private ImageButton btn_tomenu = null;

	private TextView main_text = null;
	private RadioGroup radioGroup = null;
	private EquipMentFragment equipMentFragment = null;
	private ScenceFragment ScenceFragment = null;
	private VideoFragment VideoFragment = null;
	private ImageButton btn_reverse = null;
	private ImageButton btn_online = null;
	private ConcenterManager concenterManager = null;
	boolean isReverse = false;
	private RelativeLayout relativeLayout_radiogroup = null;
	private RadioButton btn_alleqRadioButton = null;
	private RadioButton btn_scenceRadioButton = null;
	private RadioButton btn_videoradioButton = null;
	private String name = null;
	private String user = null;
	private String password = null;
	private List<GateWay> netList = new ArrayList<GateWay>();
	private List<Equipment> eqList = new ArrayList<Equipment>();
	private MenuFragment menuFragment = null;
	private List<Concenter> conList = new ArrayList<Concenter>();
	private ListView listView = null;
	private View addDeviceView = null;
	private FrameLayout frameLayout = null;
	private FrameLayout mainFramenLayout = null;
	private int conPosition = 0;

	
//	@Override  
//    protected void onUserLeaveHint() {  
//        Log.d("aeon","onUserLeaveHint");  
//        if(equipMentFragment!=null){
//        	equipMentFragment.RefreshThreadRun=false;
//        }
//        super.onUserLeaveHint();  
//    }  
	private void setupView() {
		// TODO Auto-generated method stub
		queryConcenter();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);
		inSlidingMenu();
		findView();
		listView = (ListView) findViewById(R.id.lstCameraList);
		addDeviceView = getLayoutInflater().inflate(R.layout.add_device_row,
				null);
		
		
		if (conList.size() == 0) {
			// Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			No_Concenter_Fragment no_Concenter_Fragment = new No_Concenter_Fragment();
			ft.replace(R.id.frame_main, no_Concenter_Fragment);
			ft.commit();
			// radioGroup.setVisibility(View.GONE);
		} else {
			String userString = loadUser(MainActivity.this);
			for (Concenter concenter : conList) {
				if (concenter.getUser().equals(userString)) {
					FragmentManager fm = getSupportFragmentManager();
					FragmentTransaction ft = fm.beginTransaction();
					String name = concenter.getName();
					main_text.setText("当前用户:" + name);
					if(loadView(MainActivity.this)!=null){
						if(loadView(MainActivity.this).equals("old")){
							btn_alleqRadioButton.setChecked(true);
							equipMentFragment = new EquipMentFragment(concenter);
							ft.replace(R.id.frame_main, equipMentFragment);
							ft.commit();
							break;
						}else {
							btn_alleqRadioButton.setChecked(true);
							MainUI_Fragment frMainUI_Fragment = new MainUI_Fragment(
									concenter);
							relativeLayout_radiogroup.setVisibility(View.GONE);
							frameLayout.setVisibility(View.GONE);
							mainFramenLayout.removeAllViews();
							ft.replace(R.id.frame_main, frMainUI_Fragment);
							ft.commit();
							isReverse = false;
							break;
						}
					}else {
						btn_alleqRadioButton.setChecked(true);
						equipMentFragment = new EquipMentFragment(concenter);
						ft.replace(R.id.frame_main, equipMentFragment);
						ft.commit();
						break;
					}
				}
			}
		}
		
	}

   //侧滑菜单
	private void inSlidingMenu() {
		// TODO Auto-generated method stub
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.activity_menu_framelayout);
		menuFragment = new MenuFragment(mHandler, this);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_menu, menuFragment).commit();

	}

	private Handler mHandler = new Handler() {
		// TODO Auto-generated method stub
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 20:
				menuFragment.upData();
				break;
			case 30: {
				Concenter concenter = (Concenter) msg.obj;
				String name = concenter.getName();
				Log.e("更改后的user", concenter.getUser());
				main_text.setText("当前用户:" + name);
				Log.e("conPostion", conPosition + "");
				conPosition = msg.arg1;
				saveUserData(MainActivity.this, concenter.getUser());
				setDefaultFragment(concenter);
				slidingMenu.toggle();
				relativeLayout_radiogroup.setVisibility(View.VISIBLE);
				btn_alleqRadioButton.setChecked(true);
			}
				break;
			case 40:
				Toast.makeText(MainActivity.this, "用户名或密码错误,请重新登陆！",
						Toast.LENGTH_SHORT).show();
				break;
			case 60: {
				Concenter concenter = (Concenter) msg.obj;
				delete = true;
				setDefaultFragment(concenter);
				menuFragment.upData();
				if (menuFragment.getConList().size() != 0)
					main_text.setText("请选择用户");
				else
					main_text.setText("请添加用户");
			}
				break;
			case 70:
				Toast.makeText(MainActivity.this, "此用户名已存在！",
						Toast.LENGTH_SHORT).show();
				break;
			case 80:
				Toast.makeText(MainActivity.this, "此账号已存在！", Toast.LENGTH_SHORT)
						.show();
				break;

			}

		}
	};
	boolean delete = false;

	private void setDefaultFragment(Concenter concenter) {
		// TODO Auto-generated method stub

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if (delete==true) {
			mainFramenLayout.removeAllViews();
			delete = false;
		} else {
			equipMentFragment = new EquipMentFragment(concenter, true);
			ft.replace(R.id.frame_main, equipMentFragment);
			saveViewData(MainActivity.this,"old");
			// btn_alleqRadioButton.setChecked(true);
			//Log.e("DBYUDGBASYUHDGUAISHD", concenter.getUser());
			ft.commit();
			isReverse = true;
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int position) {
		// TODO Auto-generated method stub

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		conList = menuFragment.getConList();
		isReverse = true;
		switch (position) {
		case R.id.radio_allequipment: {
			Log.e("eqlist+netList", eqList.size() + "" + netList);
			frameLayout.setVisibility(View.GONE);
//			conList = menuFragment.getConList();
			if (conList.size() == 0) {
				Toast.makeText(MainActivity.this, "请添加网关", Toast.LENGTH_SHORT)
						.show();
			} else {
				String userString = loadUser(MainActivity.this);
				for (Concenter concenter : conList) {
					if (concenter.getUser().equals(userString)) {
						String name = concenter.getName();
						main_text.setText("当前用户:" + name);
						equipMentFragment = new EquipMentFragment(concenter);
						ft.replace(R.id.frame_main, equipMentFragment);
						ft.commit();
						break;
					}
				}
			}
		}
			break;
		case R.id.radio_scence: {
			frameLayout.setVisibility(View.GONE);
			//conList = menuFragment.getConList();
			if (conList.size() == 0) {
				Toast.makeText(MainActivity.this, "请添加网关", Toast.LENGTH_SHORT)
						.show();
			} else {
				String userString = loadUser(MainActivity.this);
				for (Concenter concenter : conList) {
					if (concenter.getUser().equals(userString)) {
						String name = concenter.getName();
						main_text.setText("当前用户:" + name);
						ScenceFragment = new ScenceFragment(concenter);
						ft.replace(R.id.frame_main, ScenceFragment);
						ft.commit();
						break;
					}
				}
			}
		}
			break;
		case R.id.radio_video: {
			//conList = menuFragment.getConList();
			if (conList.size() == 0) {
				Toast.makeText(MainActivity.this, "请添加网关", Toast.LENGTH_SHORT)
						.show();
			} else {
				String userString = loadUser(MainActivity.this);
				for (Concenter concenter : conList) {
					if (concenter.getUser().equals(userString)) {
						String name = concenter.getName();
						main_text.setText("当前用户:" + name);
						VideoFragment = new VideoFragment(concenter);
						ft.replace(R.id.frame_main, VideoFragment);
						ft.commit();
						break;
					}
				}
			}
		}
			break;
		}

	}

	private void findView() {
		btn_tomenu = (ImageButton) findViewById(R.id.img_btn_tomenu);
		main_text = (TextView) findViewById(R.id.text_main);
		relativeLayout_radiogroup = (RelativeLayout) findViewById(R.id.relativelayout_radiogroup);
		radioGroup = (RadioGroup) findViewById(R.id.radio_group);
		btn_alleqRadioButton = (RadioButton) findViewById(R.id.radio_allequipment);
		btn_scenceRadioButton = (RadioButton) findViewById(R.id.radio_scence);
		btn_videoradioButton = (RadioButton) findViewById(R.id.radio_video);
		btn_reverse = (ImageButton) findViewById(R.id.btn_reverse);
		btn_online = (ImageButton) findViewById(R.id.btn_online);
		btn_tomenu.setOnClickListener(this);
		btn_reverse.setOnClickListener(this);
		// btn_online.setOnClickListener(this);
		radioGroup.setOnCheckedChangeListener(this);
		frameLayout = (FrameLayout) findViewById(R.id.layout_vedio);
		mainFramenLayout = (FrameLayout) this.findViewById(R.id.frame_main);
	}

		
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.img_btn_tomenu:
			slidingMenu.toggle();
			break;

		case R.id.btn_reverse: {
			boolean ischecked = btn_alleqRadioButton.isChecked();
			boolean btn_scence_checked = btn_scenceRadioButton.isChecked();
			boolean btn_video_checked = btn_videoradioButton.isChecked();
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			if (btn_alleqRadioButton.isChecked() == true
					|| btn_scenceRadioButton.isChecked() == true
					|| btn_videoradioButton.isChecked() == true) {
				if (isReverse) {
					saveViewData(MainActivity.this,"new");
					conList = menuFragment.getConList();
					Concenter concenter = conList.get(conPosition);
					MainUI_Fragment frMainUI_Fragment = new MainUI_Fragment(
							concenter);
					relativeLayout_radiogroup.setVisibility(View.GONE);
					frameLayout.setVisibility(View.GONE);
					mainFramenLayout.removeAllViews();
					ft.replace(R.id.frame_main, frMainUI_Fragment);
					ft.commit();
					isReverse = false;
				} else {
					saveViewData(MainActivity.this,"old");
					mainFramenLayout.removeAllViews();
					if (ischecked) {
						conList = menuFragment.getConList();
						Concenter concenter = conList.get(conPosition);
						equipMentFragment = new EquipMentFragment(concenter);
						ft.replace(R.id.frame_main, equipMentFragment);
						ft.commit();
					}
					if (btn_scence_checked) {
						conList = menuFragment.getConList();
						Concenter concenter = conList.get(conPosition);
						ScenceFragment = new ScenceFragment(concenter);
						ft.replace(R.id.frame_main, ScenceFragment);
						ft.commit();
					}
					if (btn_video_checked) {
						frameLayout.setVisibility(View.VISIBLE);
					}
					isReverse = true;
					relativeLayout_radiogroup.setVisibility(View.VISIBLE);
				}

			} else {
				Toast.makeText(MainActivity.this, "请先选择用户", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		}
		}
		
		

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();

		// return activeNetworkInfo != null;
		return true;
	}

	public static void showAlert(Context context, CharSequence title,
			CharSequence message, CharSequence btnTitle) {

		AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(context);
		dlgBuilder.setIcon(android.R.drawable.ic_dialog_alert);
		dlgBuilder.setTitle(title);
		dlgBuilder.setMessage(message);
		dlgBuilder.setPositiveButton(btnTitle,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}
	

	public final static String getEventType(Context context, int eventType,
			boolean isSearch) {

		String result = "";

		switch (eventType) {
		case AVIOCTRLDEFs.AVIOCTRL_EVENT_ALL:
			result = isSearch ? context.getText(R.string.evttype_all)
					.toString() : context.getText(
					R.string.evttype_fulltime_recording).toString();
			break;

		case AVIOCTRLDEFs.AVIOCTRL_EVENT_MOTIONDECT:
			result = context.getText(R.string.evttype_motion_detection)
					.toString();
			break;

		case AVIOCTRLDEFs.AVIOCTRL_EVENT_VIDEOLOST:
			result = context.getText(R.string.evttype_video_lost).toString();
			break;

		case AVIOCTRLDEFs.AVIOCTRL_EVENT_IOALARM:
			result = context.getText(R.string.evttype_io_alarm).toString();
			break;

		case AVIOCTRLDEFs.AVIOCTRL_EVENT_MOTIONPASS:
			result = context.getText(R.string.evttype_motion_pass).toString();
			break;

		case AVIOCTRLDEFs.AVIOCTRL_EVENT_VIDEORESUME:
			result = context.getText(R.string.evttype_video_resume).toString();
			break;

		case AVIOCTRLDEFs.AVIOCTRL_EVENT_IOALARMPASS:
			result = context.getText(R.string.evttype_io_alarm_pass).toString();
			break;

		case AVIOCTRLDEFs.AVIOCTRL_EVENT_EXPT_REBOOT:
			result = context.getText(R.string.evttype_expt_reboot).toString();
			break;

		case AVIOCTRLDEFs.AVIOCTRL_EVENT_SDFAULT:
			result = context.getText(R.string.evttype_sd_fault).toString();
			break;
		}

		return result;
	}

	@SuppressWarnings("deprecation")
	private void startOnGoingNotification(String Text) {

		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		try {

			Intent intent = new Intent(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);

			Notification notification = new Notification(R.drawable.nty_app,
					String.format(getText(R.string.ntfAppRunning).toString(),
							getText(R.string.app_name).toString()), 0);
			notification.setLatestEventInfo(this, getText(R.string.app_name),
					Text, pendingIntent);
			notification.flags |= Notification.FLAG_ONGOING_EVENT;

			manager.notify(0, notification);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stopOnGoingNotification() {

		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.cancel(0);
		manager.cancel(1);
	}

	@SuppressWarnings("deprecation")
	private void showNotification(DeviceInfo dev, int camChannel, int evtType,
			long evtTime) {

		try {

			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			Bundle extras = new Bundle();
			extras.putString("dev_uuid", dev.UUID);
			extras.putString("dev_uid", dev.UID);
			extras.putString("dev_nickname", dev.NickName);
			extras.putInt("camera_channel", camChannel);
			extras.putString("view_acc", dev.View_Account);
			extras.putString("view_pwd", dev.View_Password);

			Intent intent = new Intent(this, EventListActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtras(extras);

			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);

			Calendar cal = Calendar.getInstance();
			cal.setTimeZone(TimeZone.getDefault());
			cal.setTimeInMillis(evtTime);
			cal.add(Calendar.MONTH, 0);

			Notification notification = new Notification(R.drawable.nty_alert,
					String.format(
							getText(R.string.ntfIncomingEvent).toString(),
							dev.NickName), cal.getTimeInMillis());
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.flags |= Notification.FLAG_NO_CLEAR;

			if (dev.EventNotification == 0)
				notification.defaults = Notification.DEFAULT_LIGHTS;
			else if (dev.EventNotification == 1)
				notification.defaults = Notification.DEFAULT_SOUND;
			else if (dev.EventNotification == 2)
				notification.defaults = Notification.DEFAULT_VIBRATE;
			else
				notification.defaults = Notification.DEFAULT_ALL;

			notification.setLatestEventInfo(this, String
					.format(getText(R.string.ntfIncomingEvent).toString(),
							dev.NickName), String.format(
					getText(R.string.ntfLastEventIs).toString(),
					getEventType(this, evtType, false)), pendingIntent);

			manager.notify(1, notification);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showSDCardFormatDialog(final Camera camera,
			final DeviceInfo device) {

		final AlertDialog dlg = new AlertDialog.Builder(MainActivity.this)
				.create();
		dlg.setTitle(R.string.dialog_FormatSDCard);
		dlg.setIcon(android.R.drawable.ic_menu_more);

		LayoutInflater inflater = dlg.getLayoutInflater();
		View view = inflater.inflate(R.layout.format_sdcard, null);
		dlg.setView(view);

		final CheckBox chbShowTipsFormatSDCard = (CheckBox) view
				.findViewById(R.id.chbShowTipsFormatSDCard);
		final Button btnFormat = (Button) view
				.findViewById(R.id.btnFormatSDCard);
		final Button btnClose = (Button) view.findViewById(R.id.btnClose);

		btnFormat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				camera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL,
						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_FORMATEXTSTORAGE_REQ,
						AVIOCTRLDEFs.SMsgAVIoctrlFormatExtStorageReq
								.parseContent(0));
				device.ShowTipsForFormatSDCard = chbShowTipsFormatSDCard
						.isChecked();

				DatabaseManager db = new DatabaseManager(MainActivity.this);
				db.updateDeviceAskFormatSDCardByUID(device.UID,
						device.ShowTipsForFormatSDCard);

				dlg.dismiss();
			}
		});

		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				device.ShowTipsForFormatSDCard = chbShowTipsFormatSDCard
						.isChecked();

				DatabaseManager db = new DatabaseManager(MainActivity.this);
				db.updateDeviceAskFormatSDCardByUID(device.UID,
						device.ShowTipsForFormatSDCard);
				dlg.dismiss();
			}
		});
	}


	public static void check_mapping_list(Context context) {
		DatabaseManager manager = new DatabaseManager(context);
		SQLiteDatabase db = manager.getReadableDatabase();
		Cursor cursor = db.query(DatabaseManager.TABLE_DEVICE,
				new String[] { "dev_uid" }, null, null, null, null, null);
		if (cursor != null) {
			SharedPreferences settings = context.getSharedPreferences(
					"Preference", 0);
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String uid = cursor.getString(0);
				settings.edit().putString(uid, uid).commit();
				ThreadTPNS ThreadTPNS = new ThreadTPNS(context, uid, 0);
				ThreadTPNS.start();
				cursor.moveToNext();

			}
			cursor.close();
		}
		db.close();
		check_remove_list(context);
	}

	public static void check_remove_list(Context context) {
		DatabaseManager manager = new DatabaseManager(context);
		SQLiteDatabase db = manager.getReadableDatabase();
		Cursor cursor = db.query(DatabaseManager.TABLE_REMOVE_LIST,
				new String[] { "uid" }, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String uid = cursor.getString(0);
				ThreadTPNS thread = new ThreadTPNS(context, uid);
				thread.start();
				cursor.moveToNext();

			}
			cursor.close();
		}
		db.close();
	}
	
}