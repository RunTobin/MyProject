package com.huang.bchtsystem.View.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_NTPPARA;
import com.huang.bchtsystem.DBUtil.BlackInfo;
import com.huang.bchtsystem.DBUtil.ConstantUtil;
import com.huang.bchtsystem.DBUtil.MySQLiteOpenHelper;
import com.huang.bchtsystem.DBUtil.SQLiteOpenHelper;
import com.huang.bchtsystem.DBUtil.User;
import com.huang.bchtsystem.DBUtil.UserUtil;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.MyPreference;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.View.Adapter.BlackinfoAdapter;
import com.huang.bchtsystem.View.Adapter.UserAdapter;
import com.huang.bchtsystem.View.MyView.StrericWheelAdapter;
import com.huang.bchtsystem.View.MyView.WheelView;
import com.huang.bchtsystem.jna.HCNetSDKByJNA;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;
import com.huang.bchtsystem.jna.video.CommonMethod;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017/3/13.
 * 系统设置界面
 */

public class SystemFragment extends FragmentActivity implements View.OnClickListener{
    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;
    @InjectView(R.id.layout_system)
    protected LinearLayout layout_system;

    @InjectView(R.id.system_Equipment)
    LinearLayout system_Equipment;

    @InjectView(R.id.system_Time)
    LinearLayout system_Time;

    @InjectView(R.id.layout_Equipment)
    protected LinearLayout layout_Equipment;

    @InjectView(R.id.layout_Time)
    protected LinearLayout layout_Time;


    /**
     * 设备信息 ===基本信息  设备存储列表
     */
    @InjectView(R.id.system_Equipment_name)
    TextView system_Equipment_name; //名称
    @InjectView(R.id.system_Equipment_type)
    TextView system_Equipment_type; //型号
    @InjectView(R.id.system_Equipment_ZhuVersion)
    TextView system_Equipment_ZhuVersion;//主版本

    @InjectView(R.id.system_Equipment_HardVersion)
    TextView system_Equipment_HardVersion;//硬件版本

    @InjectView(R.id.system_Equipment_Sum)
    TextView system_Equipment_Sum;
    @InjectView(R.id.system_Equipment_VolumeProgress)
    ProgressBar system_Equipment_VolumeProgress;
    @InjectView(R.id.system_Equipment_Volume)
    TextView system_Equipment_Volume;//可用容量
    @InjectView(R.id.system_Equipment_VolumeType)
    TextView system_Equipment_VolumeType;//容量类型
    @InjectView(R.id.system_Equipment_VolumeProperty)
    TextView system_Equipment_VolumeProperty;//容量属性
    @InjectView(R.id.system_Equipment_VolumeState)
    TextView system_Equipment_VolumeState;//容量状态

    @InjectView(R.id.restore)
    Button restore;

    /**时间校准
     *  ==1.自动校时
     *    ==启用、同步周期、校时工具、服务器地址、服务器端口
     *  ==2.手动校时
     *    ==启用 、显示
     *  保存
     */
    @InjectView(R.id.system_Time_AutoCK)
    CheckBox system_Time_AutoCK;
    @InjectView(R.id.system_Time_AutoCycle)
    EditText system_Time_AutoCycle;
    @InjectView(R.id.system_Time_AutoIP)
    EditText system_Time_AutoIP;
    @InjectView(R.id.system_Time_AutoPort)
    EditText system_Time_AutoPort;
    @InjectView(R.id.system_Time_Manual)
    ImageButton system_Time_Manual;
    @InjectView(R.id.system_Time_ManualText)
    EditText system_Time_ManualText;  //设置时间
    @InjectView(R.id.system_Time_ManualTextV)
    EditText system_Time_ManualTextV;  //设备时间
    @InjectView(R.id.system_Time_Synchronization)
    CheckBox system_Time_Synchronization; //与计算机同步
    @InjectView(R.id.system_Time_Save)
    Button system_Time_Save;

    @InjectView(R.id.layout_IPWarning)
    LinearLayout layout_IPWarning;
    @InjectView(R.id.layout_PortWarning)
    LinearLayout layout_PortWarning;
    @InjectView(R.id.layout_CycleWarning)
    LinearLayout layout_CycleWarning;

    //黑名单设置
    @InjectView(R.id.system_Black)
    LinearLayout system_Black;
    @InjectView(R.id.layout_Black)
    LinearLayout layout_Black;
    @InjectView(R.id.sys_editBlackCarNum)
    EditText sys_editBlackCarNum;
    @InjectView(R.id.sys_BlackList)
    ListView sys_BlackList;
    @InjectView(R.id.sys_blackColor)
    Spinner sys_blackColor;
    @InjectView(R.id.sys_blackAddress)
    Spinner sys_blackAddress;
    @InjectView(R.id.sys_BlackSearch)
    Button sys_BlackSearch;
    @InjectView(R.id.sys_BlackAdd)
    Button sys_BlackAdd;
    @InjectView(R.id.sys_blackSearchAll)
    Button sys_blackSearchAll;

    //用户管理
    @InjectView(R.id.system_User)
    LinearLayout system_User;
    @InjectView(R.id.layout_User)
    LinearLayout layout_User;
    @InjectView(R.id.sys_UserList)
    ListView sys_UserList;
    @InjectView(R.id.sys_userAdd)
    Button sys_userAdd;
    @InjectView(R.id.sys_userUpdate)
    Button sys_userUpdate;
    @InjectView(R.id.sys_userDelete)
    Button sys_userDelete;


    private WheelView yearWheel,monthWheel,dayWheel,hourWheel,minuteWheel,secondWheel;
    public static String[] yearContent=null;
    public static String[] monthContent=null;
    public static String[] dayContent=null;
    public static String[] hourContent = null;
    public static String[] minuteContent=null;
    public static String[] secondContent=null;



    private static int m_iLogID = -1; // return by NET_DVR_Login_v30
    private int m_iChanNum ; // channel number
    private  ProgressDialog  progressDialog;
    private int[] menus = {R.id.system_Equipment,R.id.system_Time,R.id.system_Black,R.id.system_User};
    private int[] layout = {R.id.layout_Equipment,R.id.layout_Time,R.id.layout_Black,R.id.layout_User};


    HCNetSDKByJNA.NET_DVR_HDCFG hdCfg = null;//相机硬盘配置
    NET_DVR_NTPPARA ntpTime = null; //ntp设置
    // 数据库对象
    private int selectPosition = 0;//黑名单点击位置
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private BlackinfoAdapter blackAdapter;
    private String SelectedBlackColor = null;
    private String SelectedBlackAddress = null;
    private List<BlackInfo> userinfos = null;
    //用户
    private int selectUserPosition = 0;//用户管理点击位置
    private SQLiteOpenHelper sqLiteOpenHelper;
    private List<User> userList = null;
    private UserAdapter userAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.systemfragment);
        ButterKnife.inject(this);
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("系统设置");
        textview.setVisibility(View.GONE);
        m_iLogID = getIntent().getExtras().getInt("m_iLogID");
        m_iChanNum = getIntent().getExtras().getInt("m_iChanNum");
        Get_HDCFG(m_iLogID);
        new TaskThreadTime().start();
        init();
        initSpinner();
        system_Equipment.setBackgroundResource(R.drawable.networkbg);
    }
    //数据初始化
    private void init()
    {
        layout_Title.setOnClickListener(this);
        layout_system.setOnClickListener(this);
        system_Equipment.setOnClickListener(this);
        system_Black.setOnClickListener(this);
        system_Time.setOnClickListener(this);
        sys_BlackAdd.setOnClickListener(this);
        sys_BlackSearch.setOnClickListener(this);
        sys_blackSearchAll.setOnClickListener(this);
        restore.setOnClickListener(this);
        sys_userAdd.setOnClickListener(this);
        sys_userUpdate.setOnClickListener(this);
        sys_userDelete.setOnClickListener(this);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(SystemFragment.this);
        userinfos=mySQLiteOpenHelper.queryData();
        if (blackAdapter!=null) {
            blackAdapter=null;
        }
        blackAdapter=new BlackinfoAdapter(SystemFragment.this,userinfos);
        sys_BlackList.setAdapter(blackAdapter);

        sqLiteOpenHelper = new SQLiteOpenHelper(SystemFragment.this);
        userList = sqLiteOpenHelper.queryData();
        if (userAdapter !=null)
        {
            userAdapter = null;
        }
        userAdapter = new UserAdapter(SystemFragment.this,userList);
        sys_UserList.setAdapter(userAdapter);

        for(int i = 0; i < menus.length; i ++ ) {
            SystemFragment.TextViewOnclickListener clickListener = new SystemFragment.TextViewOnclickListener();
            LinearLayout tv = (LinearLayout) findViewById(menus[i]);
            if (tv == null)
                throw new NullPointerException("NetWorkFragmentV2.init: Null TextView");
            tv.setClickable(true);
            tv.setOnClickListener(clickListener);

            if (i > 0) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
                params.topMargin = 20;
                tv.setLayoutParams(params);
            }
        }

        //时间校准
        system_Time_Manual.setOnClickListener(new TimeListener());
        system_Time_Save.setOnClickListener(new TimeListener());
        system_Time_Synchronization.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    handler.post(runnable);
                }else {
                    handler.removeCallbacks(runnable);
                    system_Time_ManualText.setText("");
                }
            }
        });
        system_Time_AutoCK.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    system_Time_Synchronization.setEnabled(false);
                }else {
                    system_Time_Synchronization.setEnabled(true);
                    clear();
                }
            }
        });

        sys_BlackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = position;
                blackAdapter.setSelectedPosition(position);
                blackAdapter.notifyDataSetInvalidated();
                show_dialog(SystemFragment.this,"提示");
            }
        });

        sys_UserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectUserPosition = position;
                userAdapter.setSelectedPosition(selectUserPosition);
                userAdapter.notifyDataSetInvalidated();
            }
        });

        Handler handler1 = new Handler();
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                if (m_iLogID>-1 && hdCfg!=null){
                    initData();
                }
            }
        };
        handler1.postDelayed(runnable1,100);
    }
    private void initSpinner()
    {
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sysColor,android.R.layout.simple_spinner_dropdown_item);
        sys_blackColor.setAdapter(adapter1);
        sys_blackColor.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedBlackColor =parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.sysAddress,android.R.layout.simple_spinner_dropdown_item);
        sys_blackAddress.setAdapter(adapter2);
        sys_blackAddress.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedBlackAddress = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 设备信息设置
     */
    private void initData()
    {
        if (hdCfg != null){
            system_Equipment_Sum.setText(FormetFileSize(hdCfg.struHDInfo[0].dwCapacity*1024));
            int a = hdCfg.struHDInfo[0].dwCapacity*1024 - hdCfg.struHDInfo[0].dwFreeSpace*1024 ;
            system_Equipment_VolumeProgress.setProgress(a/1024/1024);
            system_Equipment_Volume.setText(FormetFileSize(hdCfg.struHDInfo[0].dwFreeSpace*1024));
            if (hdCfg.struHDInfo[0].byHDType == 0){
                system_Equipment_VolumeType.setText("本地");
            }
            if (hdCfg.struHDInfo[0].byHDAttr == 2){
                system_Equipment_VolumeProperty.setText("只读");
            }
            if (hdCfg.struHDInfo[0].dwHdStatus == 0){
                system_Equipment_VolumeState.setText("正常");
            }
        }
    }
    public String FormetFileSize(long fileS)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "K";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "M";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "G";
        }
        return fileSizeString;
    }
    private void Get_HDCFG(int iUserID)
    {
        hdCfg = new HCNetSDKByJNA.NET_DVR_HDCFG();
        IntByReference pInt = new IntByReference(0);
        boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDKByJNA.NET_DVR_GET_HDCFG,  0xFFFFFFFF, hdCfg.getPointer(), hdCfg.size(), pInt);
        hdCfg.read();
        if (!bRet){
            System.out.println("NET_DVR_GET_SPRCFG failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
            return;
        }else {
            System.out.println("NET_DVR_GET_SPRCFG Succ!");
        }
    }

    /**
     * 时间校准
     */

    // TODO: 2017/7/21  时间校准
    //手动时间校准
    HCNetSDKByJNA.NET_DVR_CALIBRATE_TIME time = null;
    private void Test_Time()
    {
        if (time != null){
            system_Time_ManualTextV.setText(time.struTime.toString());
        }
        EditTextTime(system_Time_ManualText);
    }
    private void Get_Time(int iUserID)
    {
        time = new HCNetSDKByJNA.NET_DVR_CALIBRATE_TIME();
        Pointer lpPicConfig = time.getPointer();
        IntByReference pInt = new IntByReference(0);
        boolean bRes = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDKByJNA.NET_DVR_GET_CALIBRATE_TIME,
                -1, lpPicConfig, time.size(), pInt);
        time.read();
        if (!bRes){
            System.out.println("NET_DVR_CALIBRATE_TIME failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
            return;
        }
    }
    private void Set_Time(int iUserID)
    {
        Get_Time(iUserID);
        if (TextUtils.isEmpty(system_Time_ManualText.getText().toString().trim())){
            EditTextTime(system_Time_ManualText);
        }
        String timename = system_Time_ManualText.getText().toString().trim();
        Timestamp startTime = Timestamp.valueOf(timename);
        Util.initDVRTime(time.struTime, startTime);
        time.write();
        boolean bRes = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDKByJNA.NET_DVR_SET_CALIBRATE_TIME,
                -1, time.getPointer(), time.size());
        if (!bRes){
            System.out.println("NET_DVR_SET_CALIBRATE_TIME failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
            return;
        }else {
            Log.e("手动","手动校时成功。。。。。。。。。。。" );
        }
    }


    //TODO:  NTP校时

    private  void Test_NtpTime(int uid,int channel)
    {
        ntpTime = new NET_DVR_NTPPARA();
        boolean ret = HCNetSDK.getInstance().NET_DVR_GetDVRConfig(uid,HCNetSDK.NET_DVR_GET_NTPCFG,channel,ntpTime);
        if (!ret){
            Log.e("NET_DVR_GET_NTPCFG",""+ HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }else {
            Log.e("NET_DVR_GET_NTPCFG","SuccessFul!");
        }
    }
    private void initNTP()
    {
        if (ntpTime != null){
            if (ntpTime.byEnableNTP == 1)
            {
                system_Time_AutoCK.setChecked(true);
                system_Time_AutoIP.setText(CommonMethod.bytes2HexString(ntpTime.sNTPServer));
                system_Time_AutoCycle.setText(String.valueOf(ntpTime.wInterval));
                system_Time_AutoPort.setText(String.valueOf(ntpTime.wNtpPort));
            }else {
                system_Time_AutoCK.setChecked(false);
                system_Time_AutoIP.setText("");
                system_Time_AutoCycle.setText("");
                system_Time_AutoPort.setText("");
            }
        }
    }
    private void Set_initNTP(int uid,int channel)
    {
        if (system_Time_AutoCK.isChecked())
        {
            Test_NtpTime(uid,channel);
            ntpTime.byEnableNTP = 1 ;
            NtpJudge(uid,channel);
        }else {
            clear();
            Test_NtpTime(uid,channel);
            ntpTime.byEnableNTP = 0 ;
            ntpTime.sNTPServer = new byte[64];
            NtpSet(uid,channel);
        }
    }
    private void NtpSet(int uid,int channel)
    {
        boolean ret = HCNetSDK.getInstance().NET_DVR_SetDVRConfig(uid,HCNetSDK.NET_DVR_SET_NTPCFG,channel,ntpTime);
        if (!ret){
            Log.e("NET_DVR_SET_NTPCFG",""+ HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }else {
            Log.e("NET_DVR_SET_NTPCFG","SuccessFul!");
            Toast.makeText(SystemFragment.this,"保存成功",Toast.LENGTH_SHORT).show();
        }
    }
    private void NtpJudge(int uid,int channel)
    {
        if (TextUtils.isEmpty(system_Time_AutoIP.getText().toString())){
            layout_IPWarning.setVisibility(View.VISIBLE);
            layout_PortWarning.setVisibility(View.INVISIBLE);
            layout_CycleWarning.setVisibility(View.INVISIBLE);
        }else if (TextUtils.isEmpty(system_Time_AutoPort.getText().toString())){
            layout_IPWarning.setVisibility(View.INVISIBLE);
            layout_PortWarning.setVisibility(View.VISIBLE);
            layout_CycleWarning.setVisibility(View.INVISIBLE);
        }else if (TextUtils.isEmpty(system_Time_AutoCycle.getText().toString())){
            layout_IPWarning.setVisibility(View.INVISIBLE);
            layout_PortWarning.setVisibility(View.INVISIBLE);
            layout_CycleWarning.setVisibility(View.VISIBLE);
        }else {
            if(Integer.parseInt(system_Time_AutoCycle.getText().toString()) <1 || Integer.parseInt(system_Time_AutoCycle.getText().toString())>10080)
            {
                layout_IPWarning.setVisibility(View.INVISIBLE);
                layout_PortWarning.setVisibility(View.INVISIBLE);
                layout_CycleWarning.setVisibility(View.VISIBLE);
            }else{
                layout_IPWarning.setVisibility(View.INVISIBLE);
                layout_PortWarning.setVisibility(View.INVISIBLE);
                layout_CycleWarning.setVisibility(View.INVISIBLE);
                ntpTime.sNTPServer = CommonMethod.string2ASCII(system_Time_AutoIP.getText().toString(),64);
                ntpTime.wInterval =  Integer.parseInt(system_Time_AutoCycle.getText().toString());
                ntpTime.wNtpPort = Integer.parseInt(system_Time_AutoPort.getText().toString());
                NtpSet(uid,channel);
            }
        }
    }
    private void clear()
    {
        system_Time_AutoIP.setText("");
        system_Time_AutoCycle.setText("");
        system_Time_AutoPort.setText("");
        layout_IPWarning.setVisibility(View.INVISIBLE);
        layout_PortWarning.setVisibility(View.INVISIBLE);
        layout_CycleWarning.setVisibility(View.INVISIBLE);
    }







    private class TextViewOnclickListener implements View.OnClickListener
    {
        public void onClick(View v)
        {
            int id = v.getId();
            switch (id)
            {
                case R.id.system_Equipment :
                    system_Equipment.setBackgroundResource(R.drawable.networkbg);
                    system_Time.setBackgroundResource(R.color.grey);
                    system_Black.setBackgroundResource(R.color.grey);
                    if (m_iLogID>-1){
                        new TaskThread().start();
                        initData();
                    }
                    break;

                case R.id.system_Time :
                    system_Time.setBackgroundResource(R.drawable.networkbg);
                    system_Equipment.setBackgroundResource(R.color.grey);
                    system_Black.setBackgroundResource(R.color.grey);
                    system_User.setBackgroundResource(R.color.grey);
                    if (m_iLogID>-1){
                        Test_Time();
                        initNTP();
                    }
                    break;
                case R.id.system_Black:
                    system_Black.setBackgroundResource(R.drawable.networkbg);
                    system_Equipment.setBackgroundResource(R.color.grey);
                    system_Time.setBackgroundResource(R.color.grey);
                    system_User.setBackgroundResource(R.color.grey);
                    break;

                case R.id.system_User:
                    system_User.setBackgroundResource(R.drawable.networkbg);
                    system_Black.setBackgroundResource(R.color.grey);
                    system_Equipment.setBackgroundResource(R.color.grey);
                    system_Time.setBackgroundResource(R.color.grey);

                    break;
            }
            for(int i = 0; i < menus.length; i ++ )
            {
                LinearLayout ll = (LinearLayout) findViewById(layout[i]);
                if( ll == null )
                    throw new NullPointerException("TextViewOnclickListener.onClick: Null Layout");
                if( menus[i] == id )
                {
                    ll.setVisibility(View.VISIBLE);
                }
                else
                {
                    ll.setVisibility(View.GONE);
                }
            }
        }
    }
    class TaskThread extends Thread {
        public void run() {
            Get_HDCFG(m_iLogID);
        };
    };
    class TaskThreadTime extends Thread {
        public void run() {
            Get_Time(m_iLogID);
            Test_NtpTime(m_iLogID,m_iChanNum);
        };
    };
    Handler handler = new Handler();
    Runnable runnable = new Runnable()
    {
        @Override
        public void run() {
            handler.postDelayed(runnable ,1000);
            EditTextTime(system_Time_ManualText);
        }
    };
    private void EditTextTime(EditText editText)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        editText.setTextSize(20);
        editText.setText(df.format(date));
    }

    //TODO==按钮点击事件
    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.layout_Title:
                finish();
                break;
            case R.id.layout_system:
                InputMethodManager imm=(InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
            case R.id.restore:  //恢复默认
                boolean  restore = HCNetSDKJNAInstance.getInstance().NET_DVR_RestoreConfig(m_iLogID);
                if (!restore){
                    Log.e("NET_DVR_GET_NTPCFG",""+ HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
                    Util.show_dialog(SystemFragment.this,"提示","恢复默认设置失败");
                }else {
                    Log.e("NET_DVR_GET_NTPCFG","SuccessFul!");
                    Util.show_dialog(SystemFragment.this,"提示","恢复默认设置成功");
                }
                break;
            case R.id.sys_BlackSearch:
                Serachdialog();
                break;
            case R.id. sys_blackSearchAll:
                updateView();
                break;
            case R.id.sys_BlackAdd:
                if (sys_editBlackCarNum.getText().toString().trim().isEmpty()) {
                    Util.show_Toast(SystemFragment.this, "车牌号不能为空", 1000);
                }else {
                    ContentValues cv = new ContentValues();
                    //列名和值
                    cv.put(ConstantUtil.USER_CARNUM, sys_editBlackCarNum.getText().toString().trim());
                    cv.put(ConstantUtil.USER_COLOR, SelectedBlackColor);
                    cv.put(ConstantUtil.USER_ADDRESS, SelectedBlackAddress);
                    cv.put(ConstantUtil.USER_TYPE, "占用应急车道");
                    boolean flag = mySQLiteOpenHelper.insertData(cv);
                    if (flag) {
                        Util.show_Toast(SystemFragment.this, "添加成功", 1000);
                    } else {
                        Util.show_Toast(SystemFragment.this, "添加失败", 1000);
                    }
                    userinfos = mySQLiteOpenHelper.queryData();
                    if (blackAdapter != null) {
                        blackAdapter = null;
                    }
                    blackAdapter = new BlackinfoAdapter(SystemFragment.this, userinfos);
                    sys_BlackList.setAdapter(blackAdapter);
                }
                break;
            case R.id.sys_userAdd:
                if (!userList.get(Util.DBPositon).getType().equals("普通用户"))
                {
                    show_UserAdd();
                }else {
                    Util.show_Toast(SystemFragment.this,"没有权限",1000);
                }
                break;
            case R.id.sys_userUpdate:
                if (!userList.get(Util.DBPositon).getType().equals("普通用户"))
                {
                    show_UserUpdate();
                }else {
                    Util.show_Toast(SystemFragment.this,"没有权限",1000);
                }
                break;
            case R.id.sys_userDelete:
                if (!userList.get(Util.DBPositon).getType().equals("普通用户"))
                {
                    if (!userList.get(selectUserPosition).getType().equals("超级管理员"))
                    {
                        if (selectUserPosition+1 >userList.size())
                        {
                            Util.show_Toast(SystemFragment.this,"请选择删除的列表",1000);
                        }else {
                            sqLiteOpenHelper.delete(selectUserPosition+1);
                            Util.show_Toast(SystemFragment.this,"删除成功",1000);
                            updateUserView();
                        }
                    }else
                    {
                        Util.show_Toast(SystemFragment.this,"超级管理员不能删除",1000);
                    }
                }else {
                    Util.show_Toast(SystemFragment.this,"没有权限",1000);
                }
                break;
            default:
                break;
        }
    }

    //TODO==时间校准点击事件处理
    class  TimeListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.system_Time_Manual:
                    initContent();
                    EditTextClick(system_Time_ManualText);
                    break;
                case R.id.system_Time_Save:
                    if (m_iLogID>-1){
                        if (system_Time_AutoCK.isChecked()){
                            Set_initNTP(m_iLogID,m_iChanNum);
                        }else {
                            Set_Time(m_iLogID);
                            Set_initNTP(m_iLogID,m_iChanNum);
                            Test_Time();
                        }
                    }
                    break;
            }
        }
    }
    //TODO==年月日、时分秒数据初始化
    public void initContent()
    {
        yearContent = new String[94];
        for(int i=0;i<94;i++)
            yearContent[i] = String.valueOf(i+1970);

        monthContent = new String[12];
        for(int i=0;i<12;i++)
        {
            monthContent[i]= String.valueOf(i+1);
            if(monthContent[i].length()<2)
            {
                monthContent[i] = "0"+monthContent[i];
            }
        }

        dayContent = new String[31];
        for(int i=0;i<31;i++)
        {
            dayContent[i]=String.valueOf(i+1);
            if(dayContent[i].length()<2)
            {
                dayContent[i] = "0"+dayContent[i];
            }
        }
        hourContent = new String[24];
        for(int i=0;i<24;i++)
        {
            hourContent[i]= String.valueOf(i);
            if(hourContent[i].length()<2)
            {
                hourContent[i] = "0"+hourContent[i];
            }
        }

        minuteContent = new String[60];
        for(int i=0;i<60;i++)
        {
            minuteContent[i]=String.valueOf(i);
            if(minuteContent[i].length()<2)
            {
                minuteContent[i] = "0"+minuteContent[i];
            }
        }
        secondContent = new String[60];
        for(int i=0;i<60;i++)
        {
            secondContent[i]=String.valueOf(i);
            if(secondContent[i].length()<2)
            {
                secondContent[i] = "0"+secondContent[i];
            }
        }
    }

    //设置时间
    private void EditTextClick(TextView editText)
    {
        View view = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.time_picker, null);
        Calendar calendar = Calendar.getInstance();
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth= calendar.get(Calendar.MONTH)+1;
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMinute = calendar.get(Calendar.MINUTE);
        int curSecond = calendar.get(Calendar.SECOND);
        yearWheel = (WheelView)view.findViewById(R.id.yearwheel);
        monthWheel = (WheelView)view.findViewById(R.id.monthwheel);
        dayWheel = (WheelView)view.findViewById(R.id.daywheel);
        hourWheel = (WheelView)view.findViewById(R.id.hourwheel);
        minuteWheel = (WheelView)view.findViewById(R.id.minutewheel);
        secondWheel = (WheelView)view.findViewById(R.id.secondwheel);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        yearWheel.setAdapter(new StrericWheelAdapter(yearContent));
        yearWheel.setCurrentItem(curYear-1970);
        yearWheel.setCyclic(true);
        yearWheel.setInterpolator(new AnticipateOvershootInterpolator());

        monthWheel.setAdapter(new StrericWheelAdapter(monthContent));
        monthWheel.setCurrentItem(curMonth-1);
        monthWheel.setCyclic(true);
        monthWheel.setInterpolator(new AnticipateOvershootInterpolator());

        dayWheel.setAdapter(new StrericWheelAdapter(dayContent));
        dayWheel.setCurrentItem(curDay-1);
        dayWheel.setCyclic(true);
        dayWheel.setInterpolator(new AnticipateOvershootInterpolator());

        hourWheel.setAdapter(new StrericWheelAdapter(hourContent));
        hourWheel.setCurrentItem(curHour);
        hourWheel.setCyclic(true);
        hourWheel.setInterpolator(new AnticipateOvershootInterpolator());

        minuteWheel.setAdapter(new StrericWheelAdapter(minuteContent));
        minuteWheel.setCurrentItem(curMinute);
        minuteWheel.setCyclic(true);
        minuteWheel.setInterpolator(new AnticipateOvershootInterpolator());

        secondWheel.setAdapter(new StrericWheelAdapter(secondContent));
        secondWheel.setCurrentItem(curSecond);
        secondWheel.setCyclic(true);
        secondWheel.setInterpolator(new AnticipateOvershootInterpolator());

        builder.setTitle("选取时间");
        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuffer sb = new StringBuffer();
                sb.append(yearWheel.getCurrentItemValue()).append("-")
                        .append(monthWheel.getCurrentItemValue()).append("-")
                        .append(dayWheel.getCurrentItemValue());
                sb.append(" ");
                sb.append(hourWheel.getCurrentItemValue())
                        .append(":").append(minuteWheel.getCurrentItemValue())
                        .append(":").append(secondWheel.getCurrentItemValue());
                editText.setText(sb);
                editText.setTextSize(20);
                dialog.cancel();
            }
        });
        builder.show();
    }

    //TODO==数据保存
    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        finish();
        handler.removeCallbacks(runnable);
    }

    /**
     * 黑名单设置对话框
     */
    private void updateView()
    {
        userinfos=mySQLiteOpenHelper.queryData();
        if (blackAdapter!=null) {
            blackAdapter=null;
        }
        blackAdapter=new BlackinfoAdapter(SystemFragment.this,userinfos);
        sys_BlackList.setAdapter(blackAdapter);
    }
    //删除与修改
    private void show_dialog(Context context,String title)
    {
        // 加载输入框的布局文件
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 弹出的对话框
        new AlertDialog.Builder(context)
					/* 弹出窗口的最上头文字 */
                .setTitle(title)
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setPositiveButton("删除",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int j) {
                                mySQLiteOpenHelper.delete(userinfos.get(selectPosition).getUserId());
                                updateView();
                            }
                        })
                .setNegativeButton("修改",
                        new DialogInterface.OnClickListener() { /* 设置跳出窗口的返回事件 */
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                updateDialog();
                            }
                        }).show();
    }
    String color ,address= null;
    private void updateDialog()
    {
        LayoutInflater inflater = (LayoutInflater) SystemFragment.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.item_blackinfo, null);

        Spinner blackColor = (Spinner) layout.findViewById(R.id.sys_blackColor);
        Spinner blackAddress = (Spinner) layout.findViewById(R.id.sys_blackAddress);
        EditText blackCarNum = (EditText)layout.findViewById(R.id.sys_editBlackCarNum);
        blackCarNum.setText(userinfos.get(selectPosition).getCarnum());
        blackCarNum.setSelection(userinfos.get(selectPosition).getCarnum().length());
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sysColor,android.R.layout.simple_spinner_dropdown_item);
        blackColor.setAdapter(adapter1);
        blackColor.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                color =parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.sysAddress,android.R.layout.simple_spinner_dropdown_item);
        blackAddress.setAdapter(adapter2);
        blackAddress.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                address = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 弹出的对话框
        new AlertDialog.Builder(SystemFragment.this)
					/* 弹出窗口的最上头文字 */
                .setTitle("修改")
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setView(layout)
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                String s  = blackCarNum.getText().toString().trim();
                                ContentValues cv=new ContentValues();
                                //列名和值
                                cv.put(ConstantUtil.USER_CARNUM, s);
                                cv.put(ConstantUtil.USER_COLOR, color);
                                cv.put(ConstantUtil.USER_ADDRESS, address);
                                cv.put(ConstantUtil.USER_TYPE, "占用应急车道");
                                mySQLiteOpenHelper.update(cv,userinfos.get(selectPosition).getUserId());
                                updateView();
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
    private void Serachdialog()
    {
        // 加载输入框的布局文件
        LayoutInflater inflater = (LayoutInflater) SystemFragment.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.storage_file, null);
        EditText inputStringr = (EditText) layout
                .findViewById(R.id.input_add_string);

        // 弹出的对话框
        new AlertDialog.Builder(SystemFragment.this)
					/* 弹出窗口的最上头文字 */
                .setTitle("查询")
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setMessage("输入车牌号码：")
                .setView(layout)
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                String carNum = inputStringr.getText().toString().trim();
                                List<BlackInfo> userinfos=mySQLiteOpenHelper.queryData(carNum);
                                if (blackAdapter!=null) {
                                    blackAdapter=null;
                                }
                                blackAdapter=new BlackinfoAdapter(SystemFragment.this,userinfos);
                                sys_BlackList.setAdapter(blackAdapter);
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

    /**
     * 用户管理
     */
    private String type,info = null;
    private void show_UserAdd()
    {
        // 加载输入框的布局文件
        LayoutInflater inflater = (LayoutInflater) SystemFragment.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.item_user_add, null);
        EditText userName = (EditText) layout.findViewById(R.id.sys_editUserName);
        EditText userPwd = (EditText) layout.findViewById(R.id.sys_editUserPwd);
        EditText userPwdSure = (EditText) layout.findViewById(R.id.sys_editUserPwdSure);
        Spinner userInfo = (Spinner) layout.findViewById(R.id.sys_UserInfo);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sysUserType,android.R.layout.simple_spinner_dropdown_item);
        userInfo.setAdapter(adapter1);
        userInfo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    info = "最高权限";
                }else {
                    info = "使用权限";
                }
                type =parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        new AlertDialog.Builder(SystemFragment.this)
					/* 弹出窗口的最上头文字 */
                .setTitle("添加用户")
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setView(layout)
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                String name = userName.getText().toString().trim();
                                String pwd =  userPwd.getText().toString().trim();
                                String pwdSure = userPwdSure.getText().toString().trim();
                                if (pwdSure.equals(pwd)){
                                    ContentValues cv=new ContentValues();
                                    //列名和值
                                    cv.put(UserUtil.USER_NAME, name);
                                    cv.put(UserUtil.USER_PWD, pwd);
                                    cv.put(UserUtil.USER_TYPE, type);
                                    cv.put(UserUtil.USER_INFO, info);
                                    sqLiteOpenHelper.insertData(cv);
                                }
                                updateUserView();
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
    private void show_UserUpdate()
    {
        // 加载输入框的布局文件
        LayoutInflater inflater = (LayoutInflater) SystemFragment.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.item_user_update, null);
        EditText updateName = (EditText) layout.findViewById(R.id.sys_updateUserName);
        EditText updatePwd = (EditText) layout.findViewById(R.id.sys_updateUserPwd);
        EditText updateNewPwd = (EditText) layout.findViewById(R.id.sys_updateNewUserPwd);
        EditText updatePwdSure = (EditText) layout.findViewById(R.id.sys_updateUserPwdSure);
        Spinner updateInfo = (Spinner) layout.findViewById(R.id.sys_updateUserInfo);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sysUserType,android.R.layout.simple_spinner_dropdown_item);
        updateInfo.setAdapter(adapter1);
        updateInfo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    info = "最高权限";
                }else {
                    info = "使用权限";
                }
                type =parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        updateName.setText(userList.get(selectUserPosition).getName().trim());
        // 弹出的对话框
        new AlertDialog.Builder(SystemFragment.this)
					/* 弹出窗口的最上头文字 */
                .setTitle("修改用户信息")
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setView(layout)
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                String name = updateName.getText().toString().trim();
                                String pwd =  updatePwd.getText().toString().trim();
                                String newPwd =  updateNewPwd.getText().toString().trim();
                                String pwdSure = updatePwdSure.getText().toString().trim();
                                if (!pwd.equals(userList.get(selectUserPosition).getPwd())){
                                    Util.show_Toast(SystemFragment.this,"原密码错误",1000);
                                }else {
                                    if (pwdSure.equals(newPwd)){
                                        ContentValues cv=new ContentValues();
                                        //列名和值
                                        cv.put(UserUtil.USER_NAME, name);
                                        cv.put(UserUtil.USER_PWD, newPwd);
                                        cv.put(UserUtil.USER_TYPE, type);
                                        cv.put(UserUtil.USER_INFO, info);
                                        sqLiteOpenHelper.update(cv,selectUserPosition+1);
                                    }else {
                                        Util.show_Toast(SystemFragment.this,"两次密码不一致",1000);
                                    }
                                }
                                updateUserView();
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
    private void updateUserView()
    {
        userList=sqLiteOpenHelper.queryData();
        if (userAdapter!=null) {
            userAdapter=null;
        }
        userAdapter=new UserAdapter(SystemFragment.this,userList);
        sys_UserList.setAdapter(userAdapter);
        selectUserPosition = 0;
    }

}
