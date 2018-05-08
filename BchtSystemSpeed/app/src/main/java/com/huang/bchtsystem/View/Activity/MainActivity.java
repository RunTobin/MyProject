package com.huang.bchtsystem.View.Activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.MyPreference;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.View.Fragment.CameraActivity;

import com.huang.bchtsystem.View.Fragment.IntelligentFragment;
import com.huang.bchtsystem.View.Fragment.NetWorkFragmentV2;
import com.huang.bchtsystem.View.Fragment.OSDFragment;


import com.huang.bchtsystem.View.Fragment.Picture.PictureActivity;

import com.huang.bchtsystem.View.Fragment.PreView.PreViewActivity;
import com.huang.bchtsystem.View.Fragment.RadarFragment;
import com.huang.bchtsystem.View.Fragment.StorageFragment;
import com.huang.bchtsystem.View.Fragment.SystemFragment;



import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.huang.bchtsystem.Util.MyPreference.isLogin;

public class  MainActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;
    @InjectView(R.id.textview)
    TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;

    @InjectView(R.id.layout_preview)
    LinearLayout layout_preview;
    @InjectView(R.id.layout_Radar)
    LinearLayout layout_Radar;
    @InjectView(R.id.layout_OSD)
    LinearLayout layout_OSD;
    @InjectView(R.id.image_preview)
    ImageView image_preview;
    @InjectView(R.id.image_radar)
    ImageView image_radar;
    @InjectView(R.id.image_osd)
    ImageView image_osd;

    @InjectView(R.id.layout_NetWork)
    LinearLayout layout_NetWork;
    @InjectView(R.id.layout_Picture)
    LinearLayout layout_Picture;
    @InjectView(R.id.layout_Intelligent)
    LinearLayout layout_Intelligent;
    @InjectView(R.id.image_net)
    ImageView image_net;
    @InjectView(R.id.image_picture)
    ImageView image_picture;
    @InjectView(R.id.image_intel)
    ImageView image_intel;

    @InjectView(R.id.layout_System)
    LinearLayout layout_System;
    @InjectView(R.id.layout_Storage)
    LinearLayout layout_Storage;
    @InjectView(R.id.layout_Daily)
    LinearLayout layout_Daily;
    @InjectView(R.id.image_sys)
    ImageView image_sys;
    @InjectView(R.id.image_sto)
    ImageView image_sto;
    @InjectView(R.id.image_daily)
    ImageView image_daily;


    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;

    private int m_iLogID = -1; // return by NET_DVR_Login_v30
    private final String TAG = "MainActivity";
    private int m_iStartChan = 0; // start channel no
    private int m_iChanNum = 0; // channel number
    public static String strIP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        toolbar.setTitle("");
        layout_Title.setVisibility(View.INVISIBLE);
        textview.setText("视频雷达测速系统");
        setSupportActionBar(toolbar);

        initView();
        if (!initSdk()) {
            this.finish();
            return;
        }
        login();
    }

    private void saveScreenBrightness() {
//        setScrennManualMode();
        ContentResolver contentResolver = this.getContentResolver();
        int value = 255; // 设置亮度值为255
        Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, value);
    }

    private void brightnessMax() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = 1.0f;
        getWindow().setAttributes(lp);
    }

    private void initView(){
        layout_preview.setOnClickListener(this);
        layout_Radar.setOnClickListener(this);
        layout_OSD.setOnClickListener(this);
        layout_NetWork.setOnClickListener(this);
        layout_Picture.setOnClickListener(this);
        layout_Intelligent.setOnClickListener(this);
        layout_System.setOnClickListener(this);
        layout_Storage.setOnClickListener(this);
        layout_Daily.setOnClickListener(this);
    }

    private boolean initSdk() {
        // init net sdk
        if (!HCNetSDK.getInstance().NET_DVR_Init()) {
            Log.e(TAG, "HCNetSDK init is failed!");
            return false;
        }
        HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/",
                true);
        return true;
    }
    //登录海康相机
    private void login()
    {
        if (m_iLogID < 0) {
            m_iLogID = loginDevice();
            if (m_iLogID < 0) {
                Log.e(TAG, "This device logins failed!");
                show_dialog();
                return;
            } else {
                System.out.println("m_iLogID=" + m_iLogID);
            }
            // get instance of exception callback and set
            ExceptionCallBack oexceptionCbf = getExceptiongCbf();
            if (oexceptionCbf == null) {
                Log.e(TAG, "ExceptionCallBack object is failed!");
                return;
            }
            if (!HCNetSDK.getInstance().NET_DVR_SetExceptionCallBack(
                    oexceptionCbf)) {
                Log.e(TAG, "NET_DVR_SetExceptionCallBack is failed!");
                return;
            }
            Log.i(TAG,
                    "Login sucess ****************************1***************************");
        }
    }
    private ExceptionCallBack getExceptiongCbf()
    {
        ExceptionCallBack oExceptionCbf = new ExceptionCallBack() {
            public void fExceptionCallBack(int iType, int iUserID, int iHandle) {
                System.out.println("recv exception, type:" + iType);
            }
        };
        return oExceptionCbf;
    }
    //连接相机
    private int loginDevice()
    {
        int iLogID = -1;
        iLogID = loginNormalDevice();
        return iLogID;
    }
    private int loginNormalDevice()
    {
        // get instance
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30) {
            Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
            return -1;
        }
        strIP =  MyPreference.getInstance(this)
                .getIpAddress();
//        if (MyPreference.getInstance(this)
//                .getIpAddress().equals("") || MyPreference.getInstance(this)
//                .getIpAddress() == null){
//            strIP = "192.168.1.64";//192.168.1.64
//        }else {
//            strIP =  MyPreference.getInstance(this)
//                    .getIpAddress();
//        }
        int nPort = 8000;
        String strUser = "admin";
        String strPsd = "admin12345";
        int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(strIP, nPort,
                strUser, strPsd, m_oNetDvrDeviceInfoV30);

        if (iLogID < 0) {
            Log.e(TAG, "NET_DVR_Login is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return -1;
        }
        if (m_oNetDvrDeviceInfoV30.byChanNum > 0) {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartChan;
            m_iChanNum = m_oNetDvrDeviceInfoV30.byChanNum;
        } else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0) {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartDChan;
            m_iChanNum = 1/*m_oNetDvrDeviceInfoV30.byIPChanNum
                    + m_oNetDvrDeviceInfoV30.byHighDChanNum * 256*/;
        }
        Log.i(TAG, m_iChanNum+"");//1
        System.out.println("相机连接成功");
        return iLogID;
    }

    public boolean Cleanup()
    {
        if (!HCNetSDK.getInstance().NET_DVR_Cleanup()){
            return false;
        }
        return true;
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        isLogin = false;
        if (!Cleanup()){
            System.exit(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_preview:
                initview1();
                initPreViewFragment();
                break;
            case R.id.layout_Radar:
                initview2();
                initRadarFragment();
                break;
            case R.id.layout_OSD:
                initview3();
                initOSDFragment();
                break;
            case R.id.layout_NetWork:
                initview4();
                initNetWorkFragment();
                break;
            case R.id.layout_Picture:
                initview5();
                initPictureActivity();
                break;
            case R.id.layout_Intelligent:
                initview6();
                initIntelligentFragment();
                break;
            case R.id.layout_System:
                initview7();
                initSystemFragment();
                break;
            case R.id.layout_Storage:
                initview8();
                initStorageFragment();
                break;
            case R.id.layout_Daily:
                initview9();
                initDailyFragment();
                break;
            default:
                break;
        }
    }

    //预览
    private void initPreViewFragment()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("m_iLogID", m_iLogID);
        bundle.putInt("m_iChanNum", m_iChanNum);
        bundle.putInt("m_iStartChan", m_iStartChan);
        Intent intent = new Intent(MainActivity.this,PreViewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    //雷达设置
    private void initRadarFragment()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("m_iLogID", m_iLogID);
        bundle.putInt("m_iChanNum", m_iChanNum);
        Intent intent = new Intent(MainActivity.this,RadarFragment.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    //OSD叠加
    private void initOSDFragment()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("m_iLogID", m_iLogID);
        bundle.putInt("m_iChanNum", m_iChanNum);
        Intent intent = new Intent(MainActivity.this,OSDFragment.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    //网络设置
    private void initNetWorkFragment()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("m_iLogID", m_iLogID);
        bundle.putInt("m_iChanNum", m_iChanNum);
        Intent intent = new Intent(MainActivity.this,NetWorkFragmentV2.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    //图片查询
    private void initPictureActivity()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("m_iLogID", m_iLogID);
        bundle.putInt("m_iChanNum", m_iChanNum);
        Intent intent = new Intent(MainActivity.this,PictureActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    //智能设置
    private void initIntelligentFragment()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("m_iLogID", m_iLogID);
        bundle.putInt("m_iChanNum", m_iChanNum);
        bundle.putInt("m_iStartChan", m_iStartChan);
        Intent intent = new Intent(MainActivity.this,IntelligentFragment.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    //系统设置
    private void initSystemFragment()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("m_iLogID", m_iLogID);
        bundle.putInt("m_iChanNum", m_iChanNum);
        Intent intent = new Intent(MainActivity.this,SystemFragment.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    //储存设置
    private void initStorageFragment()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("m_iLogID", m_iLogID);
        bundle.putInt("m_iChanNum", m_iChanNum);
        Intent intent = new Intent(MainActivity.this,StorageFragment.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    //日志管理
    private void initDailyFragment()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("m_iLogID", m_iLogID);
        Intent intent = new Intent(MainActivity.this,CameraActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 界面图标转换
     */
    private void initview1()
    {
        layout_preview.setBackgroundResource(R.drawable.bg);
        image_preview.setImageResource(R.drawable.preview1);
        layout_Radar.setBackgroundResource(R.color.transparent);
        image_radar.setImageResource(R.drawable.radar);
        layout_OSD.setBackgroundResource(R.color.transparent);
        image_osd.setImageResource(R.drawable.osd);
        layout_NetWork.setBackgroundResource(R.color.transparent);
        image_net.setImageResource(R.drawable.network);
        layout_Picture.setBackgroundResource(R.color.transparent);
        image_picture.setImageResource(R.drawable.picture);
        layout_Intelligent.setBackgroundResource(R.color.transparent);
        image_intel.setImageResource(R.drawable.intelligent);
        layout_System.setBackgroundResource(R.color.transparent);
        image_sys.setImageResource(R.drawable.setting);
        layout_Storage.setBackgroundResource(R.color.transparent);
        image_sto.setImageResource(R.drawable.storage);
        layout_Daily.setBackgroundResource(R.color.transparent);
        image_daily.setImageResource(R.drawable.camerasetting);
    }
    private void initview2()
    {
        layout_preview.setBackgroundResource(R.color.transparent);
        image_preview.setImageResource(R.drawable.preview);
        layout_Radar.setBackgroundResource(R.drawable.bg);
        image_radar.setImageResource(R.drawable.radar1);
        layout_OSD.setBackgroundResource(R.color.transparent);
        image_osd.setImageResource(R.drawable.osd);
        layout_NetWork.setBackgroundResource(R.color.transparent);
        image_net.setImageResource(R.drawable.network);
        layout_Picture.setBackgroundResource(R.color.transparent);
        image_picture.setImageResource(R.drawable.picture);
        layout_Intelligent.setBackgroundResource(R.color.transparent);
        image_intel.setImageResource(R.drawable.intelligent);
        layout_System.setBackgroundResource(R.color.transparent);
        image_sys.setImageResource(R.drawable.setting);
        layout_Storage.setBackgroundResource(R.color.transparent);
        image_sto.setImageResource(R.drawable.storage);
        layout_Daily.setBackgroundResource(R.color.transparent);
        image_daily.setImageResource(R.drawable.camerasetting);
    }
    private void initview3()
    {
        layout_preview.setBackgroundResource(R.color.transparent);
        image_preview.setImageResource(R.drawable.preview);
        layout_Radar.setBackgroundResource(R.color.transparent);
        image_radar.setImageResource(R.drawable.radar);
        layout_OSD.setBackgroundResource(R.drawable.bg);
        image_osd.setImageResource(R.drawable.osd1);
        layout_NetWork.setBackgroundResource(R.color.transparent);
        image_net.setImageResource(R.drawable.network);
        layout_Picture.setBackgroundResource(R.color.transparent);
        image_picture.setImageResource(R.drawable.picture);
        layout_Intelligent.setBackgroundResource(R.color.transparent);
        image_intel.setImageResource(R.drawable.intelligent);
        layout_System.setBackgroundResource(R.color.transparent);
        image_sys.setImageResource(R.drawable.setting);
        layout_Storage.setBackgroundResource(R.color.transparent);
        image_sto.setImageResource(R.drawable.storage);
        layout_Daily.setBackgroundResource(R.color.transparent);
        image_daily.setImageResource(R.drawable.camerasetting);
    }
    private void initview4()
    {
        layout_preview.setBackgroundResource(R.color.transparent);
        image_preview.setImageResource(R.drawable.preview);
        layout_Radar.setBackgroundResource(R.color.transparent);
        image_radar.setImageResource(R.drawable.radar);
        layout_OSD.setBackgroundResource(R.color.transparent);
        image_osd.setImageResource(R.drawable.osd);
        layout_NetWork.setBackgroundResource(R.drawable.bg);
        image_net.setImageResource(R.drawable.network1);
        layout_Picture.setBackgroundResource(R.color.transparent);
        image_picture.setImageResource(R.drawable.picture);
        layout_Intelligent.setBackgroundResource(R.color.transparent);
        image_intel.setImageResource(R.drawable.intelligent);
        layout_System.setBackgroundResource(R.color.transparent);
        image_sys.setImageResource(R.drawable.setting);
        layout_Storage.setBackgroundResource(R.color.transparent);
        image_sto.setImageResource(R.drawable.storage);
        layout_Daily.setBackgroundResource(R.color.transparent);
        image_daily.setImageResource(R.drawable.camerasetting);
    }
    private void initview5()
    {
        layout_preview.setBackgroundResource(R.color.transparent);
        image_preview.setImageResource(R.drawable.preview);
        layout_Radar.setBackgroundResource(R.color.transparent);
        image_radar.setImageResource(R.drawable.radar);
        layout_OSD.setBackgroundResource(R.color.transparent);
        image_osd.setImageResource(R.drawable.osd);
        layout_NetWork.setBackgroundResource(R.color.transparent);
        image_net.setImageResource(R.drawable.network);
        layout_Picture.setBackgroundResource(R.drawable.bg);
        image_picture.setImageResource(R.drawable.picture1);
        layout_Intelligent.setBackgroundResource(R.color.transparent);
        image_intel.setImageResource(R.drawable.intelligent);
        layout_System.setBackgroundResource(R.color.transparent);
        image_sys.setImageResource(R.drawable.setting);
        layout_Storage.setBackgroundResource(R.color.transparent);
        image_sto.setImageResource(R.drawable.storage);
        layout_Daily.setBackgroundResource(R.color.transparent);
        image_daily.setImageResource(R.drawable.camerasetting);
    }
    private void initview6()
    {
        layout_preview.setBackgroundResource(R.color.transparent);
        image_preview.setImageResource(R.drawable.preview);
        layout_Radar.setBackgroundResource(R.color.transparent);
        image_radar.setImageResource(R.drawable.radar);
        layout_OSD.setBackgroundResource(R.color.transparent);
        image_osd.setImageResource(R.drawable.osd);
        layout_NetWork.setBackgroundResource(R.color.transparent);
        image_net.setImageResource(R.drawable.network);
        layout_Picture.setBackgroundResource(R.color.transparent);
        image_picture.setImageResource(R.drawable.picture);
        layout_Intelligent.setBackgroundResource(R.drawable.bg);
        image_intel.setImageResource(R.drawable.intel1);
        layout_System.setBackgroundResource(R.color.transparent);
        image_sys.setImageResource(R.drawable.setting);
        layout_Storage.setBackgroundResource(R.color.transparent);
        image_sto.setImageResource(R.drawable.storage);
        layout_Daily.setBackgroundResource(R.color.transparent);
        image_daily.setImageResource(R.drawable.camerasetting);
    }
    private void initview7()
    {
        layout_preview.setBackgroundResource(R.color.transparent);
        image_preview.setImageResource(R.drawable.preview);
        layout_Radar.setBackgroundResource(R.color.transparent);
        image_radar.setImageResource(R.drawable.radar);
        layout_OSD.setBackgroundResource(R.color.transparent);
        image_osd.setImageResource(R.drawable.osd);
        layout_NetWork.setBackgroundResource(R.color.transparent);
        image_net.setImageResource(R.drawable.network);
        layout_Picture.setBackgroundResource(R.color.transparent);
        image_picture.setImageResource(R.drawable.picture);
        layout_Intelligent.setBackgroundResource(R.color.transparent);
        image_intel.setImageResource(R.drawable.intelligent);
        layout_System.setBackgroundResource(R.drawable.bg);
        image_sys.setImageResource(R.drawable.setting1);
        layout_Storage.setBackgroundResource(R.color.transparent);
        image_sto.setImageResource(R.drawable.storage);
        layout_Daily.setBackgroundResource(R.color.transparent);
        image_daily.setImageResource(R.drawable.camerasetting);
    }
    private void initview8()
    {
        layout_preview.setBackgroundResource(R.color.transparent);
        image_preview.setImageResource(R.drawable.preview);
        layout_Radar.setBackgroundResource(R.color.transparent);
        image_radar.setImageResource(R.drawable.radar);
        layout_OSD.setBackgroundResource(R.color.transparent);
        image_osd.setImageResource(R.drawable.osd);
        layout_NetWork.setBackgroundResource(R.color.transparent);
        image_net.setImageResource(R.drawable.network);
        layout_Picture.setBackgroundResource(R.color.transparent);
        image_picture.setImageResource(R.drawable.picture);
        layout_Intelligent.setBackgroundResource(R.color.transparent);
        image_intel.setImageResource(R.drawable.intelligent);
        layout_System.setBackgroundResource(R.color.transparent);
        image_sys.setImageResource(R.drawable.setting);
        layout_Storage.setBackgroundResource(R.drawable.bg);
        image_sto.setImageResource(R.drawable.storage1);
        layout_Daily.setBackgroundResource(R.color.transparent);
        image_daily.setImageResource(R.drawable.camerasetting);
    }
    private void initview9()
    {
        layout_preview.setBackgroundResource(R.color.transparent);
        image_preview.setImageResource(R.drawable.preview);
        layout_Radar.setBackgroundResource(R.color.transparent);
        image_radar.setImageResource(R.drawable.radar);
        layout_OSD.setBackgroundResource(R.color.transparent);
        image_osd.setImageResource(R.drawable.osd);
        layout_NetWork.setBackgroundResource(R.color.transparent);
        image_net.setImageResource(R.drawable.network);
        layout_Picture.setBackgroundResource(R.color.transparent);
        image_picture.setImageResource(R.drawable.picture);
        layout_Intelligent.setBackgroundResource(R.color.transparent);
        image_intel.setImageResource(R.drawable.intelligent);
        layout_System.setBackgroundResource(R.color.transparent);
        image_sys.setImageResource(R.drawable.setting);
        layout_Storage.setBackgroundResource(R.color.transparent);
        image_sto.setImageResource(R.drawable.storage);
        layout_Daily.setBackgroundResource(R.drawable.bg);
        image_daily.setImageResource(R.drawable.camerasetting1);
    }

    Handler mhandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                {
//                    Toast.makeText(MainActivity.this,"相机连接成功",Toast.LENGTH_SHORT).show();
                }
                break;
                case 1:
                {
                    Toast.makeText(MainActivity.this,"相机没有连接",Toast.LENGTH_SHORT).show();
                    show_dialog();
                }
                break;
                default:
                    break;
            }
        };
    };
    class TaskThread extends Thread {
        public void run() {
            System.out.println("-->做一些耗时的任务");
           if (Util.isNetworkConnected(MainActivity.this) && m_iLogID > -1){
               System.out.println("已经连接相机");
               login();
           }else {
               mhandler.sendEmptyMessage(1);
           }
        };
    };
    private void show_dialog()
    {
        // 加载输入框的布局文件
        LayoutInflater inflater = (LayoutInflater) MainActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 弹出的对话框
        new AlertDialog.Builder(MainActivity.this)
					/* 弹出窗口的最上头文字 */
                .setTitle("相机连接")
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setMessage("相机重新连接")
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                login();
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() { /* 设置跳出窗口的返回事件 */
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                dialoginterface.dismiss();
                            }
                        }).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        new TaskThread().start();
    }

}