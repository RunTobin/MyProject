package com.huang.bchtsystem.View.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.TabActivity;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_NETCFG_V30;
import com.hikvision.netsdk.NET_DVR_NTPPARA;
import com.hikvision.netsdk.NET_ITC_TRIGGERCFG;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.MyPreference;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.View.Activity.MainActivity;
import com.huang.bchtsystem.jna.HCNetSDKByJNA;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;
import com.huang.bchtsystem.jna.video.CommonMethod;
import com.huang.bchtsystem.jna.video.JNATest;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import java.io.File;
import java.sql.Timestamp;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.huang.bchtsystem.Web.ApiServiceSupport.TAG;

/**
 * Created by admin on 2017/6/20.
 * 相机设置
 */

public class CameraActivity extends TabActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;

    @InjectView(R.id.layout_cameraSetting)
    LinearLayout layout_cameraSetting;
    @InjectView(R.id.previewCamera_RG)
    RadioGroup previewCamera_RG;
    @InjectView(R.id.previewCamera_Flash_N)
    RadioButton previewCamera_Flash_N;
    @InjectView(R.id.previewCamera_Flash_Y)
    RadioButton previewCamera_Flash_Y;
    @InjectView(R.id.previewCamera_Flash_Auto)
    RadioButton previewCamera_Flash_Auto;

    @InjectView(R.id.tv_BarFlash)
    EditText tv_BarFlash;
    @InjectView(R.id.SeekBarFlash)
    SeekBar SeekBarFlash;
    @InjectView(R.id.FlashValue)
    EditText FlashValue;
    @InjectView(R.id.SeekBarBrand)
    SeekBar SeekBarBrand;
    @InjectView(R.id.FlashCheck)
    CheckBox FlashCheck;
    @InjectView(R.id.camera_start_ImageButton)
    ImageButton camera_start_ImageButton;
    @InjectView(R.id.camera_start_time)
    EditText camera_start_time;
    @InjectView(R.id.camera_end_time)
    EditText camera_end_time;
    @InjectView(R.id.camera_end_ImageButton)
    ImageButton camera_end_ImageButton;

    @InjectView(R.id.checkbox_value)
    CheckBox checkbox_value;
    @InjectView(R.id.layout_value)
    LinearLayout layout_value;
    @InjectView(R.id.checkbox_Time)
    CheckBox checkbox_Time;
    @InjectView(R.id.layout_Time)
    LinearLayout layout_Time;

    @InjectView(R.id.previewCamera_BaoCun)
    Button previewCamera_BaoCun;
    @InjectView(R.id.previewCamera_Setting)
    Button previewCamera_Setting;
    @InjectView(R.id.SeekLight)
    SeekBar SeekLight;

    @InjectView(R.id.LPR_Forward)
    RadioButton LPR_Forward;
    @InjectView(R.id.LPR_Back)
    RadioButton LPR_Back;
    @InjectView(R.id.LPR_Big)
    RadioButton LPR_Big;
    @InjectView(R.id.LPR_Small)
    RadioButton LPR_Small;
    @InjectView(R.id.camera_but)
    Button but;

    private static int m_iLogID = -1; // return by NET_DVR_Login_v30
    private byte checknum = 0; //闪光灯关联口号
    private TabHost tabHost ;
    private TabWidget tabWidget ;
    private static int myMenuSettingTag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previewcamera);
        ButterKnife.inject(this);
        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("相机设置");
        textview.setVisibility(View.GONE);
        m_iLogID = getIntent().getExtras().getInt("m_iLogID");
        init();
    }

    private void init()
    {
        tabHost = getTabHost();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1").setIndicator("闪光灯设置").setContent(R.id.camera_tab1);
        tabHost.addTab(tab1);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2").setIndicator("参数设置").setContent(R.id.camera_tab2);
        tabHost.addTab(tab2);
        tabWidget = tabHost.getTabWidget();
        for (int i = 0 ; i<tabWidget.getChildCount();i++){
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(20);
            tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
            tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tabwidget_selector);
        }
        initView();
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tagString) {

                if (tagString.equals("tab1")) {
                    myMenuSettingTag = 1;
                }
                if (tagString.equals("tab2")) {
                    myMenuSettingTag = 2;
                }
            }
        });
    }

    private void initView()
    {
        getFlashData(m_iLogID);
        getBrandFirst(m_iLogID);
        GetLPR(m_iLogID);
        checkbox_Time.setOnCheckedChangeListener(this);
        checkbox_value.setOnCheckedChangeListener(this);
        FlashCheck.setOnCheckedChangeListener(this);
        layout_cameraSetting.setOnClickListener(this);
        camera_start_ImageButton.setOnClickListener(this);
        camera_end_ImageButton.setOnClickListener(this);
        layout_Title.setOnClickListener(this);
        previewCamera_BaoCun.setOnClickListener(this);
        previewCamera_Setting.setOnClickListener(this);
        but.setOnClickListener(this);
        SeekLight.setProgress(Util.getSystemBrightness(CameraActivity.this));
        SeekBarFlash.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_BarFlash.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        SeekBarBrand.setOnSeekBarChangeListener(seekBarChangeListener);
        SeekLight.setOnSeekBarChangeListener(seekBarLightChangeListener);
        previewCamera_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                MyPreference.getInstance(CameraActivity.this).SetRADIOFLASHMODE(checkedId);
                if (previewCamera_Flash_N.getId() == checkedId){
                    layout_value.setVisibility(View.GONE);
                    layout_Time.setVisibility(View.GONE);
                }else if (previewCamera_Flash_Y.getId() == checkedId){
                    layout_value.setVisibility(View.GONE);
                    layout_Time.setVisibility(View.VISIBLE);
                }else if (previewCamera_Flash_Auto.getId() == checkedId){
                    layout_value.setVisibility(View.VISIBLE);
                    layout_Time.setVisibility(View.GONE);
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.layout_cameraSetting:
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
            case R.id.layout_Title:
                finish();
                break;
            case R.id.previewCamera_BaoCun:
                if (m_iLogID > -1)
                {
                    SetFlash(m_iLogID);
                }
                break;
            case R.id.previewCamera_Setting:
                if (m_iLogID > -1)
                {
                    SetBrand(m_iLogID);
                    saveScreenBrightness(light);
                    SeekLight.setProgress(Util.getSystemBrightness(CameraActivity.this));
                }
                break;
            case R.id.camera_start_ImageButton:
                SetTime(camera_start_time);
                break;
            case R.id.camera_end_ImageButton:
                SetTime(camera_end_time);
                break;
            case R.id.camera_but:
                progressUi();
//                if ( JNATest.struTriggerCfg !=null && JNATest.struHvt != null)
//                {
//                    Util.show_Toast(CameraActivity.this,"参数已配置",1000);
//                }else {
//                    progressUi();
//                }
                break;
            default:
                break;
        }
    }
    String sHour,sMinute = null;
    private void SetTime(EditText editText)
    {
        new TimePickerDialog(CameraActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                sHour = hourOfDay<10 ? "0"+hourOfDay:""+hourOfDay;
                sMinute = minute<10 ? "0"+minute : ""+minute;
                editText.setText(sHour+":"+sMinute);
            }
        },0,0,true).show();
    }

    /**
     * 车牌亮度SeekBar调节
     */
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.i(TAG,"onProgressChanged");
            FlashValue.setText(""+(progress+1));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            Log.i(TAG,"onStartTrackingTouch");
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.i(TAG,"onStopTrackingTouch");
        }
    };
    /**
     * 系统屏幕亮度调节
     */
    private int light = 0;
    private SeekBar.OnSeekBarChangeListener seekBarLightChangeListener = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.i(TAG,"onProgressChanged");
            light =progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            Log.i(TAG,"onStartTrackingTouch");
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.i(TAG,"onStopTrackingTouch");
        }
    };
    private void saveScreenBrightness(int value)
    {
        Util.setScrennManualMode(CameraActivity.this);
        ContentResolver contentResolver = CameraActivity.this.getContentResolver();
//        int value = 100; // 设置亮度值为255
        Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, value);
    }
    /**
     * 车牌亮度补偿
     */
    HCNetSDKByJNA.NET_DVR_PLCCFG plccfg = null;
    private void getBrand(int uid)
    {
        plccfg = new HCNetSDKByJNA.NET_DVR_PLCCFG();
        IntByReference pInt = new IntByReference(0);
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(uid, HCNetSDKByJNA.NET_DVR_GET_PLCCFG, 0xFFFFFFFF,
                plccfg.getPointer(), plccfg.size(), pInt);
        plccfg.read();
        if (!ret)
        {
            Log.e(TAG, "NET_DVR_GET_FLASHCFG failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
        }else{
            Log.e(TAG, "NET_DVR_GET_FLASHCFG SuccessFul !" );
        }
    }
    private void getBrandFirst(int uid)
    {
        getBrand(uid);
        if (plccfg.byPlcEnable ==1){
            FlashCheck.setChecked(true);
            SeekBarBrand.setProgress((int)plccfg.wPlcBrightOffSet);
            FlashValue.setText((int)plccfg.wPlcBrightOffSet+"");
        }else {
            FlashCheck.setChecked(false);
            SeekBarBrand.setProgress(1);
            FlashValue.setText(1+"");
        }
    }
    private void SetBrand(int uid)
    {
        getBrand(uid);
        if (FlashCheck .isChecked())
        {
            plccfg.byPlcEnable = 1;
            if(Integer.parseInt(FlashValue.getText().toString()) <= 100)
            {
                plccfg.wPlcBrightOffSet = (byte)Integer.parseInt(FlashValue.getText().toString());
                SeekBarBrand.setProgress(Integer.parseInt(FlashValue.getText().toString())-1);
            }else {
                plccfg.wPlcBrightOffSet = (byte)100;
                SeekBarBrand.setProgress(100);
            }
        }else {
            plccfg.byPlcEnable = 0;
        }
        plccfg.write();
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(uid, HCNetSDKByJNA.NET_DVR_SET_PLCCFG, 0xFFFFFFFF,
                plccfg.getPointer(), plccfg.size());
        if (!ret)
        {
            Log.e(TAG, "NET_DVR_SET_PLCCFG failed, error + 车牌亮度设置失败:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            Toast.makeText(CameraActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
        }else{
            Log.e(TAG, "NET_DVR_SET_PLCCFG SuccessFul ! + 车牌亮度设置成功 " );
            Toast.makeText(CameraActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置闪光灯参数
     */
    HCNetSDKByJNA.NET_DVR_FLASH_OUTCFG flash_outcfg=null;
    private void getFlash(int uid)
    {
        flash_outcfg = new HCNetSDKByJNA.NET_DVR_FLASH_OUTCFG();
        IntByReference pInt = new IntByReference(0);
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(uid, HCNetSDKByJNA.NET_DVR_GET_FLASHCFG, 1,
                flash_outcfg.getPointer(), flash_outcfg.size(), pInt);
        flash_outcfg.read();
        if (!ret)
        {
            Log.e(TAG, "NET_DVR_GET_FLASHCFG failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
        }else{
            Log.e(TAG, "NET_DVR_GET_FLASHCFG SuccessFul !" );
        }
    }

    private void SetFlash(int uid)
    {
        getFlash(uid);
        setFlashData();
        flash_outcfg.write();
        boolean ret  = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(uid, HCNetSDKByJNA.NET_DVR_SET_FLASHCFG, 1,
                flash_outcfg.getPointer(), flash_outcfg.size());
        if (!ret)
        {
            Log.e(TAG, "NET_DVR_SET_FLASHCFG failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
        }else{
            Log.e(TAG, "NET_DVR_SET_FLASHCFG SuccessFul !" );
        }
    }

    private void getFlashData(int uid)
    {
        getFlash(uid);
        if (flash_outcfg.byDetectBrightness == 0){
            checkbox_value.setChecked(false);
        }else {
            checkbox_value.setChecked(true);
            tv_BarFlash.setText(String.valueOf(flash_outcfg.byBrightnessThreld));
            SeekBarFlash.setProgress((int)flash_outcfg.byBrightnessThreld);
        }
        if (flash_outcfg.byFlashLightEnable == 0){
            checkbox_Time.setChecked(false);
        }else {
            checkbox_Time.setChecked(true);
            camera_start_time.setText(String .valueOf((int)flash_outcfg.byStartHour<10?"0"+flash_outcfg.byStartHour:flash_outcfg.byStartHour
            )+":"+String .valueOf((int)flash_outcfg.byStartMinute<10?"0"+flash_outcfg.byStartMinute:flash_outcfg.byStartMinute));
            camera_end_time.setText(String .valueOf((int)flash_outcfg.byEndHour<10?"0"+flash_outcfg.byEndHour:flash_outcfg.byEndHour
            )+":"+String .valueOf((int)flash_outcfg.byEndMinute<10?"0"+flash_outcfg.byEndMinute:flash_outcfg.byEndMinute));
        }
    }

    private String[] start,end = null;
    private Dialog progressDialog;
    private void setFlashData()
    {
        if (previewCamera_Flash_N.isChecked()){
            checkbox_Time.setChecked(false);
            checkbox_value.setChecked(false);
            checknum = 0;
            SetFlashIO();
        }
        if (checkbox_value.isChecked())
        {
            checkbox_Time.setChecked(false);
            camera_start_time.setText("");
            camera_end_time.setText("");
            flash_outcfg.byDetectBrightness = 1;
            if (Integer.parseInt(tv_BarFlash.getText().toString()) <= 100)
            {
                flash_outcfg.byBrightnessThreld = Byte.parseByte(tv_BarFlash.getText().toString());
                SeekBarFlash.setProgress(Integer.parseInt(tv_BarFlash.getText().toString()));
            }else {
                flash_outcfg.byBrightnessThreld = (byte)100;
                SeekBarFlash.setProgress(100);
            }
            checknum = 1;
            SetFlashIO();
        }else {
            flash_outcfg.byDetectBrightness = 0;
            SeekBarFlash.setProgress(0);
        }
        if (checkbox_Time.isChecked()){
            checkbox_value.setChecked(false);
            tv_BarFlash.setText("");
            flash_outcfg.byFlashLightEnable = 1;
            start = camera_start_time.getText().toString().split(":");
            end = camera_end_time.getText().toString().split(":");
            flash_outcfg.byStartHour = (byte) Integer.parseInt(start[0]);
            flash_outcfg.byStartMinute = (byte) Integer.parseInt(start[1]);
            flash_outcfg.byEndHour =(byte) Integer.parseInt(end[0]);
            flash_outcfg.byEndMinute = (byte) Integer.parseInt(end[1]);
            checknum = 1;
            SetFlashIO();
        }else {
            flash_outcfg.byFlashLightEnable = 0;
        }
    }

    private void SetFlashIO()
    {
        progressDialog = Util.createLoadingDialog(this,"正在设置中...");
        progressDialog.show();
        //新建线程   
        new Thread() {
            @Override
            public void run() {
                //需要花时间计算的方法  
                try{
                    if (mode == 0){
                        Test_SetCanshu(m_iLogID);
                    }else {
                        SetPRS(m_iLogID);
                    }
                }catch ( Exception e){
                    e.fillInStackTrace();
                }finally{
                    progressDialog.dismiss();
                }
            }
        }.start();

    }

    HCNetSDKByJNA.NET_ITC_RSTRIGGERCFG struTriggerCfg =null;
    private void Test_SetCanshu(int iUserID)
    {
        struTriggerCfg = JNATest.struTriggerCfg;
        if (struTriggerCfg == null)
        {
            Util.show_Toast(CameraActivity.this,"请进行参数配置",1000);
        }
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].byRelatedIOOutEx = checknum;

        if (LPR_Forward.isChecked()){
            if (LPR_Small.isChecked()){
                struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struPlateRecog.dwRecogMode=0;
            }else if (LPR_Big.isChecked()){
                struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struPlateRecog.dwRecogMode=2;
            }
        }else if (LPR_Back.isChecked()){
            if (LPR_Small.isChecked()){
                struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struPlateRecog.dwRecogMode=1;
            }else if (LPR_Big.isChecked()){
                struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struPlateRecog.dwRecogMode=3;
            }
        }

        struTriggerCfg.write();
        boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG, 0x8, struTriggerCfg.getPointer(), struTriggerCfg.size());
        if(!bRet)
        {
            System.out.println("HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
            Util.show_Toast(CameraActivity.this,"参数设置错误",1000);
        }
        else {
            System.out.println("HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG succ!");
            Util.show_Toast(CameraActivity.this,"参数设置成功",1000);

        }

    }

    HCNetSDKByJNA.NET_ITC_TRIGGERCFG triggerCfg = null;
    private void SetPRS(int uid)
    {

        triggerCfg = JNATest.struHvt;
        if (triggerCfg == null)
        {
            Util.show_Toast(CameraActivity.this,"请进行参数配置",1000);
        }
        triggerCfg.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].dwRelatedIOOut = checknum;
        if (LPR_Forward.isChecked()){
            if (LPR_Small.isChecked()){
                triggerCfg.struTriggerParam.uTriggerParam.struHvtV50.struPlateRecog.dwRecogMode=0;
            }else if (LPR_Big.isChecked()){
                triggerCfg.struTriggerParam.uTriggerParam.struHvtV50.struPlateRecog.dwRecogMode=2;
            }
        }else if (LPR_Back.isChecked()){
            if (LPR_Small.isChecked()){
                triggerCfg.struTriggerParam.uTriggerParam.struHvtV50.struPlateRecog.dwRecogMode=1;
            }else if (LPR_Big.isChecked()){
                triggerCfg.struTriggerParam.uTriggerParam.struHvtV50.struPlateRecog.dwRecogMode=3;
            }
        }

        triggerCfg.write();
        boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(uid, HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG, 0x20, triggerCfg.getPointer(), triggerCfg.size());
        if(!bRet)
        {
            System.out.println("NET_ITC_SET_TRIGGERCFG failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
            Util.show_Toast(CameraActivity.this,"参数设置错误",1000);
        }
        else {
            System.out.println("NET_ITC_SET_TRIGGERCFG succ!");
            Util.show_Toast(CameraActivity.this,"参数设置成功",1000);
        }
    }

    private int mode;
    @Override
    protected void onResume()
    {
        super.onResume();
        mode = MyPreference.getInstance(CameraActivity.this).getPAISHIJC();
        int flash_mode = MyPreference.getInstance(CameraActivity.this).getRADIOFLASHMODE();
        //   previewCamera_RG.check(flash_mode);
        if (flash_mode == 0) {
            previewCamera_Flash_N.setChecked(true);
        }else if (flash_mode == 1){
            previewCamera_Flash_Y.setChecked(true);
        }else if (flash_mode == 2) {
            previewCamera_Flash_Auto.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        switch (buttonView.getId()){
            case R.id.checkbox_Time:
                checkbox_value.setChecked(false);
                SeekBarFlash.setProgress(0);
                tv_BarFlash.setText("");
                break;
            case R.id.checkbox_value:
                checkbox_Time.setChecked(false);
                camera_start_time.setText("");
                camera_end_time.setText("");
                break;
            case R.id.FlashCheck:
                if (!isChecked){
                    SeekBarBrand.setProgress(1);
                    FlashValue.setText(1+"");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 车牌识别设置
     */
    HCNetSDKByJNA.NET_DVR_SPRCFG Sprcfg = null;
    private void GetLPR(int uid)
    {
        Sprcfg = new HCNetSDKByJNA.NET_DVR_SPRCFG();
        IntByReference pInt = new IntByReference(0);
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(uid, HCNetSDKByJNA.NET_DVR_GET_SPRCFG, 0xFFFFFFFF,
                Sprcfg.getPointer(), Sprcfg.size(), pInt);
        Sprcfg.read();
        if (!ret)
        {
            Log.e(TAG, "NET_DVR_GET_SPRCFG failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
        }else{
            Log.e(TAG, "NET_DVR_GET_SPRCFG SuccessFul !" );
        }
        if (Sprcfg.dwRecogMode == 0)
        {
            LPR_Forward.setChecked(true);
            LPR_Small.setChecked(true);
        }else if (Sprcfg.dwRecogMode == 1){
            LPR_Back.setChecked(true);
            LPR_Small.setChecked(true);
        }else if (Sprcfg.dwRecogMode == 2){
            LPR_Forward.setChecked(true);
            LPR_Big.setChecked(true);
        }else if (Sprcfg.dwRecogMode == 3){
            LPR_Back.setChecked(true);
            LPR_Big.setChecked(true);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        finish();
    }


    Dialog proDialog ;
    private void progressUi() {
        proDialog = Util.createLoadingDialog(CameraActivity.this, "参数配置中......");
        proDialog.show();
        //新建线程   
        new Thread() {
            @Override
            public void run() {
                //需要花时间计算的方法   
                try{
                    JNATest.TEST_Config(m_iLogID);
                }catch ( Exception e){
                    e.fillInStackTrace();
                }finally{
                    proDialog.dismiss();
                }
            }
        }.start();
    }

}