package com.huang.bchtsystem.View.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_PICCFG_V30;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.jna.HCNetSDKByJNA;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;
import com.huang.bchtsystem.jna.video.CommonMethod;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import java.util.Calendar;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017/3/13.
 * OSD叠加界面->图像OSD叠加、视频OSD叠加
 */

public class OSDFragment extends TabActivity implements View.OnClickListener{
    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;

    @InjectView(R.id.layout_osd)
    LinearLayout layout_osd;

    /**
     * OSD设置项
     */
    @InjectView(R.id.osd_CB_Location)
    CheckBox osd_CB_Location;
    @InjectView(R.id.osd_CB_Load)
    CheckBox osd_CB_Load;
    @InjectView(R.id.osd_CB_Equipment)
    CheckBox osd_CB_Equipment;
    @InjectView(R.id.osd_CB_Direction)
    CheckBox osd_CB_Direction;
    @InjectView(R.id.osd_CB_DirectionDescription)
    CheckBox osd_CB_DirectionDescription;
    @InjectView(R.id.osd_CB_LaneDescription)
    CheckBox osd_CB_LaneDescription;

    @InjectView(R.id.osd_ET_Location)
    EditText osd_ET_Location;  //地点描述
    @InjectView(R.id.osd_ET_Load)
    EditText osd_ET_Load;  //路口编号
    @InjectView(R.id.osd_ET_Equipment)
    EditText osd_ET_Equipment;  //设备编号
    @InjectView(R.id.osd_ET_Direction)
    EditText osd_ET_Direction;  //方向编号
    @InjectView(R.id.osd_ET_DirectionDescription)
    EditText osd_ET_DirectionDescription;  //方向描述
    @InjectView(R.id.osd_ET_LaneDescription)
    EditText osd_ET_LaneDescription;  //车道描述
    /**
     * 图片叠加项
     */
    //车辆信息
    @InjectView(R.id.osd_CB_VehicleLicence)
    CheckBox osd_CB_VehicleLicence;  //车牌号码
    @InjectView(R.id.osd_CB_VehicleColor)
    CheckBox osd_CB_VehicleColor;  //车身颜色
    @InjectView(R.id.osd_CB_VehicleStyle)
    CheckBox osd_CB_VehicleStyle;  //车型
    @InjectView(R.id.osd_CB_PlateLength)
    CheckBox osd_CB_PlateLength;  //车牌颜色  30

    //2.违法信息
    @InjectView(R.id.osd_CB_IllegalCode)
    CheckBox osd_CB_IllegalCode;  //违法代码
    @InjectView(R.id.osd_CB_IllegalBehavior)
    CheckBox osd_CB_IllegalBehavior;  //违法行为
    @InjectView(R.id.osd_CB_SpeedLimit)
    CheckBox osd_CB_SpeedLimit;  //超速
    @InjectView(R.id.osd_CB_Speed)
    CheckBox osd_CB_Speed;  //限速
    @InjectView(R.id.osd_CB_SpeedCar)
    CheckBox osd_CB_SpeedCar;  //车速
    @InjectView(R.id.osd_CB_Lane)
    CheckBox osd_CB_Lane;  //车道行驶方向
    @InjectView(R.id.osd_CB_STime)
    CheckBox osd_CB_STime;  //抓拍时间

    //3.其他信息
    @InjectView(R.id.osd_CB_TrackNum)
    CheckBox osd_CB_TrackNum;  //车道号
    @InjectView(R.id.osd_CB_SecurityCode)
    CheckBox osd_CB_SecurityCode;  //防伪码
    @InjectView(R.id.osd_CB_SnapshotNum)
    CheckBox osd_CB_SnapshotNum;  //抓拍编号

    /**
     * 视频叠加文字
     */
    @InjectView(R.id.osd_CB_VideoName)
    CheckBox osd_CB_VideoName;
    @InjectView(R.id.osd_Edit_VideoName)
    EditText osd_Edit_VideoName;
    @InjectView(R.id.osd_CB_VideoDate)
    CheckBox osd_CB_VideoDate;
    @InjectView(R.id.layout_VideoDate)
    LinearLayout layout_VideoDate;
    @InjectView(R.id.osd_CB_VideoWeek)
    CheckBox osd_CB_VideoWeek;

    @InjectView(R.id.osd_ed_videoTime)
    Spinner osd_ed_videoTime;
    @InjectView(R.id.osd_ed_videoDate)
    Spinner osd_ed_videoDate;

    @InjectView(R.id.osd_show1)
    CheckBox osd_show1;
    @InjectView(R.id.osd_show1EditText)
    EditText osd_show1EditText;
    @InjectView(R.id.osd_show2)
    CheckBox osd_show2;
    @InjectView(R.id.osd_show2EditText)
    EditText osd_show2EditText;
    @InjectView(R.id.osd_show3)
    CheckBox osd_show3;
    @InjectView(R.id.osd_show3EditText)
    EditText osd_show3EditText;
    @InjectView(R.id.osd_show4)
    CheckBox osd_show4;
    @InjectView(R.id.osd_show4EditText)
    EditText osd_show4EditText;

    @InjectView(R.id.osd_Capture_Save)
    Button osd_Capture_Save;
    @InjectView(R.id.osd_Video_Save)
    Button osd_Video_Save;

    private static int m_iLogID = -1; // return by NET_DVR_Login_v30
    private int m_iChanNum ; // channel number
    private TabHost tabHost ;
    private TabWidget tabWidget ;
    private static int myMenuSettingTag = 0;
    HCNetSDKByJNA.NET_ITS_OVERLAP_CFG_V50 InfoCfg = null ;
    HCNetSDKByJNA.NET_ITS_OVERLAPCFG_COND InfoCond = null ;
    HCNetSDKByJNA.INT_ARRAY pInt = null ;

    Handler handler = null;
    Runnable runnable = null;
    private static int existnum ;
    private static int SelectedTimeType =0 ;
    private static int SelectedDateType =0  ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osd);
        ButterKnife.inject(this);

        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("OSD设置");
        textview.setVisibility(View.GONE);

        Test_osd(m_iLogID);
        m_iLogID = getIntent().getExtras().getInt("m_iLogID");
        m_iChanNum = getIntent().getExtras().getInt("m_iChanNum");
        initView();

    }
    private void initView()
    {
        tabHost = getTabHost();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1").setIndicator("抓图OSD设置项").setContent(R.id.osd_tab1);
        tabHost.addTab(tab1);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2").setIndicator("抓图OSD叠加项").setContent(R.id.osd_tab2);
        tabHost.addTab(tab2);
        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3").setIndicator("视频叠加文字").setContent(R.id.osd_tab3);
        tabHost.addTab(tab3);
        tabWidget = tabHost.getTabWidget();
        for (int i = 0 ; i<tabWidget.getChildCount();i++){
//            tabWidget.getChildAt(i).getLayoutParams().height = 60;
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(20);
            tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
            tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tabwidget_selector);
        }
        init();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (m_iLogID>-1){
                    tab_Get();
                }
            }
        };
        handler.postDelayed(runnable,100);


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
    private void init()
    {
        layout_Title.setOnClickListener(this);
        layout_osd.setOnClickListener(this);
        osd_Video_Save.setOnClickListener(this);
        osd_ET_Location.setOnClickListener(new EditTextClick());
        osd_ET_Load.setOnClickListener(new EditTextClick());
        osd_ET_Equipment.setOnClickListener(new EditTextClick());
        osd_ET_DirectionDescription.setOnClickListener(new EditTextClick());
        osd_ET_LaneDescription.setOnClickListener(new EditTextClick());
        osd_ET_Direction.setOnClickListener(new EditTextClick());

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.osdTime,android.R.layout.simple_spinner_dropdown_item);
        osd_ed_videoTime.setAdapter(adapter1);
        osd_ed_videoTime.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                MyPreference.getInstance(RadarFragment.this).SetDIRECTIONTYPE(position);
                SelectedTimeType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.osdDate,android.R.layout.simple_spinner_dropdown_item);
        osd_ed_videoDate.setAdapter(adapter);
        osd_ed_videoDate.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                MyPreference.getInstance(RadarFragment.this).SetDIRECTIONTYPE(position);
                SelectedDateType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (m_iLogID > -1) {
            CheckBoxClick();
        }
    }

    NET_DVR_PICCFG_V30 cfg_v30 = null;
    private void Get_VideoOsd(int uid,int chanNum)
    {
        cfg_v30 = new NET_DVR_PICCFG_V30();
        boolean bRet = HCNetSDK.getInstance().NET_DVR_GetDVRConfig(uid, HCNetSDK.NET_DVR_GET_PICCFG_V30, chanNum, cfg_v30);
        if (!bRet){
            System.out.println("NET_DVR_GET_PICCFG_V30 failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }


    }
    private void Set_VideoOsd(int uid,int chanNum)
    {
        Get_VideoOsd(m_iLogID,m_iChanNum);
        setOsd();
        boolean bRet = HCNetSDK.getInstance().NET_DVR_SetDVRConfig(uid, HCNetSDK.NET_DVR_SET_PICCFG_V30, chanNum, cfg_v30);
        if (!bRet){
            System.out.println("NET_DVR_GET_PICCFG_V30 failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }else {
            Log.e("视频通道参数","SuccessFul!........");
        }
    }


    //视频OSD内容
    HCNetSDKByJNA.NET_DVR_SHOWSTRING_V30 showstring_v30 = null;
    private void Get_videoContextOsd(int uid,int chanNum)
    {
        showstring_v30 = new HCNetSDKByJNA.NET_DVR_SHOWSTRING_V30();
        IntByReference pInt1 = new IntByReference(0);
        boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(uid, HCNetSDKByJNA.NET_DVR_GET_SHOWSTRING_V30, chanNum,
                showstring_v30.getPointer(), showstring_v30.size(), pInt1);
        showstring_v30.read();
        if (!bRet){
            System.out.println("NET_DVR_GET_SHOWSTRING_V30 failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }
    }
    private  void Set_videoContext(int uid,int chanNum)
    {
        showstring_v30.write();
        boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(uid, HCNetSDKByJNA.NET_DVR_SET_SHOWSTRING_V30, chanNum,
                showstring_v30.getPointer(), showstring_v30.size());
        if (!bRet){
            System.out.println("NET_DVR_SET_SHOWSTRING_V30 failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }else {
            Log.e("视频OSD内容","SuccessFul!........");
        }
    }


    private void Set_videoContextOsd(int uid,int chanNum)
    {
        Get_videoContextOsd(uid,chanNum);
        if (osd_show1.isChecked()){
            showstring_v30.struStringInfo[0].sString=new byte[44];
            Set_videoContext(uid,chanNum);
            showstring_v30.struStringInfo[0].wShowString = 1;
            showstring_v30.struStringInfo[0].sString=CommonMethod.Stringtobyte2(osd_show1EditText.getText().toString().trim());
            showstring_v30.struStringInfo[0].wShowStringTopLeftX = 0;
            showstring_v30.struStringInfo[0].wShowStringTopLeftY = 0;
        }else {
            osd_show1EditText.setText("");
            showstring_v30.struStringInfo[0].wShowString = 0;
            showstring_v30.struStringInfo[0].sString=new byte[44];
        }
        if (osd_show2.isChecked()){
            showstring_v30.struStringInfo[1].sString=new byte[44];
            Set_videoContext(uid,chanNum);
            showstring_v30.struStringInfo[1].wShowString = 1;
            showstring_v30.struStringInfo[1].sString=CommonMethod.Stringtobyte2(osd_show2EditText.getText().toString());
            showstring_v30.struStringInfo[1].wShowStringTopLeftX = 280;
            showstring_v30.struStringInfo[1].wShowStringTopLeftY = 0;
        }else {
            osd_show2EditText.setText("");
            showstring_v30.struStringInfo[1].wShowString = 0;
            showstring_v30.struStringInfo[1].sString=new byte[44];
        }
        if (osd_show3.isChecked()){
            showstring_v30.struStringInfo[2].sString=new byte[44];
            Set_videoContext(uid,chanNum);
            showstring_v30.struStringInfo[2].wShowString = 1;
            showstring_v30.struStringInfo[2].sString=CommonMethod.Stringtobyte2(osd_show3EditText.getText().toString());
            showstring_v30.struStringInfo[2].wShowStringTopLeftX = 0;
            showstring_v30.struStringInfo[2].wShowStringTopLeftY = 100;
        }else {
            osd_show3EditText.setText("");
            showstring_v30.struStringInfo[2].wShowString = 0;
            showstring_v30.struStringInfo[2].sString=new byte[44];
        }
        if (osd_show4.isChecked()){
            showstring_v30.struStringInfo[3].sString=new byte[44];
            Set_videoContext(uid,chanNum);
            showstring_v30.struStringInfo[3].wShowString = 1;
            showstring_v30.struStringInfo[3].sString=CommonMethod.Stringtobyte2(osd_show4EditText.getText().toString());
            showstring_v30.struStringInfo[3].wShowStringTopLeftX = 280;
            showstring_v30.struStringInfo[3].wShowStringTopLeftY = 100;
        }else {
            osd_show4EditText.setText("");
            showstring_v30.struStringInfo[3].wShowString = 0;
            showstring_v30.struStringInfo[3].sString=new byte[44];
        }
        Set_videoContext(uid,chanNum);
    }

    private void getVideoOSd()
    {
        Get_VideoOsd(m_iLogID,m_iChanNum);
        if (cfg_v30.dwShowChanName == 1){
            osd_CB_VideoName.setChecked(true);
            osd_Edit_VideoName.setEnabled(true);
            osd_Edit_VideoName.setText(CommonMethod.bytes2HexString(cfg_v30.sChanName));
            osd_Edit_VideoName.setSelection(CommonMethod.bytes2HexString(cfg_v30.sChanName).length());
        }else {
            osd_CB_VideoName.setChecked(false);
            osd_Edit_VideoName.setEnabled(false);
            osd_Edit_VideoName.setText(CommonMethod.bytes2HexString(cfg_v30.sChanName));
        }
        if (cfg_v30.dwShowOsd == 1){
            osd_CB_VideoDate.setChecked(true);
            layout_VideoDate.setVisibility(View.VISIBLE);
            if (cfg_v30.byDispWeek == 1){
                osd_CB_VideoWeek.setChecked(true);
            }else {
                osd_CB_VideoWeek.setChecked(false);
            }
        }else {
            osd_CB_VideoDate.setChecked(false);
            layout_VideoDate.setVisibility(View.GONE);
        }
        if (cfg_v30.byOSDType == 0){
            osd_ed_videoDate.setSelection(0);
        }else if (cfg_v30.byOSDType == 1){
            osd_ed_videoDate.setSelection(1);
        }else if (cfg_v30.byOSDType == 2){
            osd_ed_videoDate.setSelection(2);
        }else if (cfg_v30.byOSDType == 3){
            osd_ed_videoDate.setSelection(3);
        }else if (cfg_v30.byOSDType == 4){
            osd_ed_videoDate.setSelection(4);
        }else if (cfg_v30.byOSDType == 5){
            osd_ed_videoDate.setSelection(5);
        }

        if (cfg_v30.byHourOsdType == 0){
            osd_ed_videoTime.setSelection(0);
        }else {
            osd_ed_videoTime.setSelection(1);
        }

    }
    private  void getVideoContextOsd()
    {
        Get_videoContextOsd(m_iLogID,m_iChanNum);
        if (showstring_v30.struStringInfo[0].wShowString == 1){
            osd_show1.setChecked(true);
        }else {
            osd_show1.setChecked(false);
        }
        osd_show1EditText.setText(CommonMethod.bytes2HexString(showstring_v30.struStringInfo[0].sString));
        if (showstring_v30.struStringInfo[1].wShowString == 1){
            osd_show2.setChecked(true);
        }else {
            osd_show2.setChecked(false);
        }
        osd_show2EditText.setText(CommonMethod.bytes2HexString(showstring_v30.struStringInfo[1].sString));
        if (showstring_v30.struStringInfo[2].wShowString == 1){
            osd_show3.setChecked(true);
        }else {
            osd_show3.setChecked(false);
        }
        osd_show3EditText.setText(CommonMethod.bytes2HexString(showstring_v30.struStringInfo[2].sString));
        if (showstring_v30.struStringInfo[3].wShowString == 1){
            osd_show4.setChecked(true);
        }else {
            osd_show4.setChecked(false);
        }
        osd_show4EditText.setText(CommonMethod.bytes2HexString(showstring_v30.struStringInfo[3].sString));
    }
    private void setOsd()
    {
        if (osd_CB_VideoName.isChecked()){
            cfg_v30.dwShowChanName = 1;
            cfg_v30.sChanName = CommonMethod.string2ASCII(osd_Edit_VideoName.getText().toString().trim(),32);
        }else {
            cfg_v30.dwShowChanName = 0;
        }
        if (osd_CB_VideoDate.isChecked()){
            cfg_v30.dwShowOsd = 1;
            cfg_v30.byHourOsdType = (byte)SelectedTimeType;
            cfg_v30.byOSDType = (byte)SelectedDateType;
            if (osd_CB_VideoWeek.isChecked()){
                cfg_v30.byDispWeek = 1;
            }else {
                cfg_v30.byDispWeek = 0;
            }
        }else {
            cfg_v30.dwShowOsd = 0;
        }
    }


    /**
     * 抓图OSD
     */
    private void Test_osd(int iUserID)
    {
        InfoCond = new HCNetSDKByJNA.NET_ITS_OVERLAPCFG_COND();
        InfoCfg = new HCNetSDKByJNA.NET_ITS_OVERLAP_CFG_V50();
        Pointer lpCond = InfoCond.getPointer();
        Pointer lpParam = InfoCfg.getPointer();
        pInt = new HCNetSDKByJNA.INT_ARRAY(1);

        pInt.iValue[0] = -1;
        System.out.println("NET_DVR_GetDeviceConfig status:" + pInt.iValue[0]);
        InfoCond.dwSize = InfoCond.size();
        InfoCond.dwChannel = m_iChanNum;
        InfoCond.dwConfigMode = 1;
        InfoCond.byPicModeType = 0;
        InfoCond.write();

        boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDeviceConfig(iUserID, HCNetSDKByJNA.NET_ITS_GET_OVERLAP_CFG_V50, 1,
                lpCond, InfoCond.size(), pInt.getPointer(), lpParam, InfoCfg.size());
        InfoCfg.read();
        pInt.read();
        if(!bRet)
        {
            System.out.println("NET_ITS_GET_OVERLAP_CFG_V50 failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }
        else
        {
            System.out.println("NET_DVR_GetDeviceConfig NET_ITS_GET_OVERLAP_CFG_V50 status:" );
        }
    }
    private void Set_osd(int iUserID)
    {
        pInt.iValue[0] = -1;
        System.out.println("NET_DVR_GetDeviceConfig status:" + pInt.iValue[0]);
        InfoCfg.byEnable = 1;
        InfoCfg.write();
        boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDeviceConfig(iUserID, HCNetSDKByJNA.NET_ITS_SET_OVERLAP_CFG_V50, 1,
                InfoCond.getPointer(), InfoCond.size(), pInt.getPointer(), InfoCfg.getPointer(), InfoCfg.size());
        if(!bRet)
        {
            System.out.println("NET_ITS_SET_OVERLAP_CFG_V50 failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }
        else
        {
            System.out.println("NET_DVR_SetDeviceConfig NET_ITS_SET_OVERLAP_CFG_V50  succ");
        }
    }


    private void tab_Get()
    {
        Test_osd(m_iLogID);
        for (int i = 0; i< InfoCfg.struOverLapItem.struSingleItem.length ; i++)
        {
            if (1 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType) { //地点
                osd_CB_Location.setChecked(true);
                osd_ET_Location.setText(CommonMethod.bytes2HexString(InfoCfg.struOverLapInfo.bySite));
                osd_ET_Location.setSelection(CommonMethod.bytes2HexString(InfoCfg.struOverLapInfo.bySite).length());
            }else if (2 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){ //路口
                osd_CB_Load.setChecked(true);
                osd_ET_Load.setText(CommonMethod.bytes2HexString(InfoCfg.struOverLapInfo.byRoadNum));
            }else if (3 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){ //设备编号
                osd_CB_Equipment.setChecked(true);
                osd_ET_Equipment.setText(CommonMethod.bytes2HexString(InfoCfg.struOverLapInfo.byInstrumentNum));
            }else if (4 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){ //方向编号
                osd_CB_Direction.setChecked(true);
                osd_ET_Direction.setText(CommonMethod.bytes2HexString(InfoCfg.struOverLapInfo.byDirection));
            }else if (5 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){ //方向描述
                osd_CB_DirectionDescription.setChecked(true);
                osd_ET_DirectionDescription.setText(CommonMethod.bytes2HexString(InfoCfg.struOverLapInfo.byDirectionDesc));
            }else if (7 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){ //车道描述
                osd_CB_LaneDescription.setChecked(true);
                osd_ET_LaneDescription.setText(CommonMethod.bytes2HexString(InfoCfg.struOverLapInfo.byLaneDes));
            }else if (6 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){
                osd_CB_TrackNum.setChecked(true);
            }else if (10 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){
                osd_CB_VehicleLicence.setChecked(true);
            }else if (11 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){
                osd_CB_VehicleColor.setChecked(true);
            }else if (12 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){
                osd_CB_VehicleStyle.setChecked(true);
            }else if (14 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){
                osd_CB_SpeedCar.setChecked(true);
            }else if (15 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){
                osd_CB_Speed.setChecked(true);
            }else if (17 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){
                osd_CB_IllegalCode.setChecked(true);
            }else if (19 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){
                osd_CB_IllegalBehavior.setChecked(true);
            }else if (20 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){ //超速比
                osd_CB_SpeedLimit.setChecked(true);
            }else if (24 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){
                osd_CB_SecurityCode.setChecked(true);
            }else if (25 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){
                osd_CB_SnapshotNum.setChecked(true);
            }else if (9 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){
                osd_CB_STime.setChecked(true);
            }else if (29 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){
                osd_CB_Lane.setChecked(true);
            }else if(16 == InfoCfg.struOverLapItem.struSingleItem[i].byItemType){
                osd_CB_PlateLength.setChecked(true);
            }
        }
        getVideoOSd();
        getVideoContextOsd();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.layout_osd:
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
            case R.id.layout_Title:
                this.finish();
                break;
            case R.id.osd_Video_Save:
                if (m_iLogID > -1)
                {
                    Set_VideoOsd(m_iLogID,m_iChanNum);
                    Set_videoContextOsd(m_iLogID,m_iChanNum);
                    Util.show_Toast(OSDFragment.this,"保存成功",500);
                }
                break;
            case R.id.osd_Capture_Save:

                break;
            default:
                break;
        }
    }

    private class EditTextClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.osd_ET_Location:
                    EditTextClick("请输入地点描述",1,osd_CB_Location);
                    break;
                case R.id.osd_ET_Load:
                    EditTextClick("请输入道路编号",2,osd_CB_Load);
                    break;
                case R.id.osd_ET_Equipment:
                    EditTextClick("请输入标定证书编号",3,osd_CB_Equipment);
                    break;
                case R.id.osd_ET_Direction:
//                    EditTextClick("请输入方向编号",4,osd_CB_Direction);
                    break;
                case R.id.osd_ET_DirectionDescription:
                    EditTextClick("请输入方向描述",5,osd_CB_DirectionDescription);
                    break;
                case R.id.osd_ET_LaneDescription:
//                    EditTextClick("请输入车道描述",6,osd_CB_LaneDescription);
                    break;
            }
        }
    }

    private void getNum()
    {
        for (int i = 0; i < InfoCfg.struOverLapItem.struSingleItem.length; i++) {
            if (InfoCfg.struOverLapItem.struSingleItem[i].byItemType == 0) {
                existnum = i;
                break;
            }
        }
    }
    private static boolean  isclick = false ;
    private void getMethod( int i,CheckBox cb ,String str)
    {
        for (int j = 0; j < InfoCfg.struOverLapItem.struSingleItem.length; j++) {
            if ((byte) i == InfoCfg.struOverLapItem.struSingleItem[j].byItemType) {
                cb.setChecked(true);
                isclick = true ;
                break;
            }else {
                isclick = false ;
            }
        }
        if (!isclick){
            getNum();
            InfoCfg.struOverLapItem.struSingleItem[existnum].byItemType = (byte) i;
            InfoCfg.struOverLapItem.struSingleItem[existnum].byItemTypeCustom =  CommonMethod.Stringtobyte2(str);
            Set_osd(m_iLogID);
            cb.setChecked(true);
        }
    }

    private void setMethod( int i,CheckBox cb)
    {
        for (int j = 0; j < InfoCfg.struOverLapItem.struSingleItem.length; j++) {
            if ((byte) i == InfoCfg.struOverLapItem.struSingleItem[j].byItemType) {
                InfoCfg.struOverLapItem.struSingleItem[j].byItemType = 0;
                InfoCfg.struOverLapItem.struSingleItem[j].byItemTypeCustom = new byte[32] ;
                Set_osd(m_iLogID);
                isclick = false ;
                cb.setChecked(false);
                break;
            }
        }
    }

    private static boolean isdefind = false ;
    private void getDefind( int i ,int k,CheckBox cb ,String str)
    {
        for (int j = 0; j < InfoCfg.struOverLapItem.struSingleItem.length; j++) {
            if ((byte) i == InfoCfg.struOverLapItem.struSingleItem[j].byItemType) {
                cb.setChecked(true);
                isdefind = true ;
                break;
            }else {
                isdefind = false ;
            }
        }
        if (!isdefind){
            if ( k == 1){
                InfoCfg.struOverLapInfo.bySite = new byte[128];
            }else if( k == 2){
                InfoCfg.struOverLapInfo.byRoadNum = new byte[32];
            }else if (k == 3){
                InfoCfg.struOverLapInfo.byInstrumentNum = new byte[32];
            }else if (k == 4){
                InfoCfg.struOverLapInfo.byDirection = new byte[32];
            }else if (k == 5){
                InfoCfg.struOverLapInfo.byDirectionDesc = new byte[32];
            }else if (k == 6){
                InfoCfg.struOverLapInfo.byLaneDes = new byte[32];
            }
            Set_osd(m_iLogID);
            cb.setChecked(true);
            EditTextClick(str,k,cb);
        }
    }

    private void setDefind(int i ,CheckBox cb , EditText et)
    {
        for (int j = 0; j < InfoCfg.struOverLapItem.struSingleItem.length; j++) {
            if ((byte) i == InfoCfg.struOverLapItem.struSingleItem[j].byItemType) {
                InfoCfg.struOverLapItem.struSingleItem[j].byItemType = 0;
                InfoCfg.struOverLapItem.struSingleItem[j].byItemTypeCustom = new byte[32] ;
                Set_osd(m_iLogID);
                cb.setChecked(false);
                isdefind = false ;
                isexist = false ;
                et.setText("");
                break;
            }
        }
    }
    private void CheckBoxClick()
    {
        //地点
        osd_CB_Location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    getDefind(1,1,osd_CB_Location ,"请输入地点描述" );
                }else {
                    setDefind(1 , osd_CB_Location , osd_ET_Location);
                }
            }
        });
        //路口编号
        osd_CB_Load.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    getDefind(2,2,osd_CB_Load ,"请输入道路编号" );
                }else {
                    setDefind(2 , osd_CB_Load , osd_ET_Load);
                }
            }
        });
        //设备编号
        osd_CB_Equipment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    getDefind(3,3,osd_CB_Equipment ,"请输入标定证书编号" );
                }else {
                    setDefind(3 , osd_CB_Equipment , osd_ET_Equipment);
                }
            }
        });
        //方向编号
        osd_CB_Direction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    getDefind(4,4,osd_CB_Direction ,"请输入方向编号" );
                }else {
                    setDefind(4 , osd_CB_Direction , osd_ET_Direction);
                }
            }
        });
        //方向描述
        osd_CB_DirectionDescription.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    getDefind(5,5,osd_CB_DirectionDescription ,"请输入方向描述");
                }else {
                    setDefind(5 , osd_CB_DirectionDescription , osd_ET_DirectionDescription);
                }
            }
        });
        //车道描述
        osd_CB_LaneDescription.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            getDefind(7,6,osd_CB_LaneDescription ,"请输入车道描述" );
                        }else {
                            setDefind(7 , osd_CB_LaneDescription , osd_ET_LaneDescription);
                        }
                    }
                });


        //车牌号码
        osd_CB_VehicleLicence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(10,osd_CB_VehicleLicence,"车牌号:");
                }else {
                   setMethod( 10 , osd_CB_VehicleLicence);
                }
            }
        });
        //车身颜色
        osd_CB_VehicleColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(11,osd_CB_VehicleColor,"车身颜色:");
                }else {
                    setMethod(11,osd_CB_VehicleColor);
                }
            }
        });
        //车辆类型
        osd_CB_VehicleStyle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(12,osd_CB_VehicleStyle,"车型:");
                }else {
                    setMethod(12,osd_CB_VehicleStyle);
                }
            }
        });
        //车辆长度
        osd_CB_PlateLength.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(16,osd_CB_PlateLength,"车长:");
                }else {
                    setMethod(16,osd_CB_PlateLength);
                }
            }
        });

        //车道号
        osd_CB_TrackNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(6,osd_CB_TrackNum,"车道号:");
                }else {
                    setMethod(6,osd_CB_TrackNum);
                }
            }
        });
        //防伪码
        osd_CB_SecurityCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(24,osd_CB_SecurityCode,"防伪码:");
                }else {
                    setMethod(24,osd_CB_SecurityCode);
                }
            }
        });
        //抓拍编号
        osd_CB_SnapshotNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(25,osd_CB_SnapshotNum,"抓拍编号:");
                }else {
                    setMethod(25,osd_CB_SnapshotNum);
                }
            }
        });

        //违法代码
        osd_CB_IllegalCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(17,osd_CB_IllegalCode,"违法代码:");
                }else {
                    setMethod(17,osd_CB_IllegalCode);
                }
            }
        });
        //违法行为
        osd_CB_IllegalBehavior.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(19,osd_CB_IllegalBehavior,"违法行为:");
                }else {
                    setMethod(19,osd_CB_IllegalBehavior);
                }
            }
        });
        //超速比
        osd_CB_SpeedLimit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(20,osd_CB_SpeedLimit,"超速比:");
                }else {
                    setMethod(20,osd_CB_SpeedLimit);
                }
            }
        });
        //限速
        osd_CB_Speed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(15,osd_CB_Speed,"限速:");
                }else {
                    setMethod(15,osd_CB_Speed);
                }
            }
        });
        //车速
        osd_CB_SpeedCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(14,osd_CB_SpeedCar,"车速");
                }else {
                    setMethod(14,osd_CB_SpeedCar);
                }
            }
        });
        //车道行驶方向
        osd_CB_Lane.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(29,osd_CB_Lane,"车道行驶方向:");
                }else {
                    setMethod(29,osd_CB_Lane);
                }
            }
        });
        //抓拍时间
        osd_CB_STime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked){
                    getMethod(9,osd_CB_STime,"抓拍时间:");
                }else {
                    setMethod(9,osd_CB_STime);
                }
            }
        });
        VideoOsd();

    }
    private static boolean isexist = false ;
    private void EditTextClick(String str , int j , CheckBox cb)
    {
        // 加载输入框的布局文件
        LayoutInflater inflater = (LayoutInflater) OSDFragment.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.storage_file, null);
        EditText inputStringr = (EditText) layout
                .findViewById(R.id.input_add_string);
        if (j == 1){
            inputStringr.setText(osd_ET_Location.getText().toString());
            InfoCfg.struOverLapInfo.bySite = new byte[128];
            Set_osd(m_iLogID);
        }else if (j == 2){
            inputStringr.setText(osd_ET_Load.getText().toString());
            InfoCfg.struOverLapInfo.byRoadNum = new byte[32];
            Set_osd(m_iLogID);
        }else if (j == 3){
            inputStringr.setText(osd_ET_Equipment.getText().toString());
            InfoCfg.struOverLapInfo.byInstrumentNum = new byte[32];
            Set_osd(m_iLogID);
        }else if (j == 5){
            inputStringr.setText(osd_ET_DirectionDescription.getText().toString());
            InfoCfg.struOverLapInfo.byDirectionDesc = new byte[32];
            Set_osd(m_iLogID);
        }

        // 弹出的对话框
        new AlertDialog.Builder(OSDFragment.this)
					/* 弹出窗口的最上头文字 */
                .setTitle(str)
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setMessage("内容设置，确认内容修改，取消不进行操作！")
                .setView(layout)
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                String s = inputStringr.getText().toString().trim();
                                if ( j == 1 ){
                                    getNum();
                                    for (int k = 0; k < InfoCfg.struOverLapItem.struSingleItem.length; k++) {
                                        if (1 == InfoCfg.struOverLapItem.struSingleItem[k].byItemType) {
                                            InfoCfg.struOverLapInfo.bySite = CommonMethod.Stringtobyte2(s);
                                            isexist = true ;
                                        }
                                    }
                                    if ( !isexist){
                                        InfoCfg.struOverLapItem.struSingleItem[existnum].byItemType = 1;
                                        InfoCfg.struOverLapItem.struSingleItem[existnum].byItemTypeCustom =  CommonMethod.Stringtobyte2("地点描述:");
                                        InfoCfg.struOverLapInfo.bySite = CommonMethod.Stringtobyte2(s);
                                    }
                                }else if (j == 2){
                                    getNum();
                                    for (int k = 0; k < InfoCfg.struOverLapItem.struSingleItem.length; k++) {
                                        if (2 == InfoCfg.struOverLapItem.struSingleItem[k].byItemType) {
                                            InfoCfg.struOverLapInfo.byRoadNum = CommonMethod.Stringtobyte2(s);
                                            isexist = true ;
                                        }
                                    }
                                    if (!isexist){
                                        InfoCfg.struOverLapItem.struSingleItem[existnum].byItemType = 2;
                                        InfoCfg.struOverLapItem.struSingleItem[existnum].byItemTypeCustom =  CommonMethod.Stringtobyte2("道路编号:");
                                        InfoCfg.struOverLapInfo.byRoadNum = CommonMethod.Stringtobyte2(s);
                                    }
                                }else if (j == 3){
                                    getNum();
                                    for (int k = 0; k < InfoCfg.struOverLapItem.struSingleItem.length; k++) {
                                        if (3 == InfoCfg.struOverLapItem.struSingleItem[k].byItemType) {
                                            InfoCfg.struOverLapInfo.byInstrumentNum = CommonMethod.Stringtobyte2(s);
                                            isexist = true ;
                                        }
                                    }
                                    if (!isexist){
                                        InfoCfg.struOverLapItem.struSingleItem[existnum].byItemType = 3;
                                        InfoCfg.struOverLapItem.struSingleItem[existnum].byItemTypeCustom =  CommonMethod.Stringtobyte2("标定证书编号:");
                                        InfoCfg.struOverLapInfo.byInstrumentNum = CommonMethod.Stringtobyte2(s);
                                    }
                                }else if (j == 4){
                                    getNum();
                                    for (int k = 0; k < InfoCfg.struOverLapItem.struSingleItem.length; k++) {
                                        if (4 == InfoCfg.struOverLapItem.struSingleItem[k].byItemType) {
                                            InfoCfg.struOverLapInfo.byDirection = CommonMethod.Stringtobyte2(s);
                                            isexist = true ;
                                        }
                                    }
                                    if (!isexist){
                                        InfoCfg.struOverLapItem.struSingleItem[existnum].byItemType = 4;
                                        InfoCfg.struOverLapItem.struSingleItem[existnum].byItemTypeCustom =  CommonMethod.Stringtobyte2("方向编号:");
                                        InfoCfg.struOverLapInfo.byDirection = CommonMethod.Stringtobyte2(s);
                                    }

                                }else if (j == 5){
                                    getNum();
                                    for (int k = 0; k < InfoCfg.struOverLapItem.struSingleItem.length; k++) {
                                        if (5 == InfoCfg.struOverLapItem.struSingleItem[k].byItemType) {
                                            InfoCfg.struOverLapInfo.byDirectionDesc = CommonMethod.Stringtobyte2(s);
                                            isexist = true ;
                                        }
                                    }
                                    if (!isexist){
                                        InfoCfg.struOverLapItem.struSingleItem[existnum].byItemType = 5;
                                        InfoCfg.struOverLapItem.struSingleItem[existnum].byItemTypeCustom =  CommonMethod.Stringtobyte2("方向描述:");
                                        InfoCfg.struOverLapInfo.byDirectionDesc = CommonMethod.Stringtobyte2(s);
                                    }
                                }else if (j == 6){
                                    getNum();
                                    for (int k = 0; k < InfoCfg.struOverLapItem.struSingleItem.length; k++) {
                                        if (7 == InfoCfg.struOverLapItem.struSingleItem[k].byItemType) {
                                            InfoCfg.struOverLapInfo.byLaneDes =  CommonMethod.Stringtobyte2(s);
                                            isexist = true ;
                                        }
                                    }
                                    if (!isexist){
                                        InfoCfg.struOverLapItem.struSingleItem[existnum].byItemType = 7;
                                        InfoCfg.struOverLapItem.struSingleItem[existnum].byItemTypeCustom =  CommonMethod.Stringtobyte2("车道描述:");
                                        InfoCfg.struOverLapInfo.byLaneDes =  CommonMethod.Stringtobyte2(s);
                                    }
                                }
                                Set_osd(m_iLogID);
                                handler = new Handler();
                                runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        isexist =false;
                                        tab_Get();
                                    }
                                };
                                handler.postDelayed(runnable,1000);
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() { /* 设置跳出窗口的返回事件 */
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                if (j == 1){
                                    if (TextUtils.isEmpty(osd_ET_Location.getText().toString())){
                                        cb.setChecked(false);
                                    }
                                }else if (j == 2){
                                    if (TextUtils.isEmpty(osd_ET_Load.getText().toString())){
                                        cb.setChecked(false);
                                    }
                                }else if (j == 3){
                                    if (TextUtils.isEmpty(osd_ET_Equipment.getText().toString())){
                                        cb.setChecked(false);
                                    }
                                }else if (j == 5){
                                    if (TextUtils.isEmpty(osd_ET_DirectionDescription.getText().toString())){
                                        cb.setChecked(false);
                                    }
                                }
                                dialoginterface.dismiss();

                            }
                        }).show();
    }

    /**
     * 视频叠加
     */
    private void VideoOsd()
    {
        osd_CB_VideoName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    osd_Edit_VideoName.setEnabled(true);
                }else {
                    osd_Edit_VideoName.setEnabled(false);
                }
            }
        });
        osd_CB_VideoDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout_VideoDate.setVisibility(View.VISIBLE);
                }else {
                    layout_VideoDate.setVisibility(View.GONE);
                }
            }
        });
    }
}