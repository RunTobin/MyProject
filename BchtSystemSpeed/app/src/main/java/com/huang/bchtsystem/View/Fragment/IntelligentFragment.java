package com.huang.bchtsystem.View.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.huang.bchtsystem.Model.DrawData;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.MyPreference;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.View.MyView.Draw4;
import com.huang.bchtsystem.View.MyView.DrawFirstLoadView;
import com.huang.bchtsystem.View.MyView.DrawLine;
import com.huang.bchtsystem.View.MyView.DrawRightLaneView;
import com.huang.bchtsystem.jna.HCNetSDKByJNA;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;
import com.huang.bchtsystem.jna.video.JNATest;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;


import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.huang.bchtsystem.Web.ApiServiceSupport.TAG;

/**
 * Created by admin on 2017/3/13.
 * 智能设置界面
 */

public class IntelligentFragment extends FragmentActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;


    @InjectView(R.id.layout_PaiS)
    protected LinearLayout layout_PaiS;
    @InjectView(R.id.layout_HChen)
    protected LinearLayout layout_HChen;

    @InjectView(R.id.intelligent_PaiS)
    protected Button intelligent_PaiS;
    @InjectView(R.id.intelligent_HChen)
    protected Button intelligent_HChen;

    /**
     * 牌识区设置
     */
    @InjectView(R.id.intelligent_Rs485)
    SurfaceView intelligent_Rs485;


    @InjectView(R.id.layout_Rs485)
    LinearLayout layout_Rs485;
    @InjectView(R.id.layout_MixLane)
    LinearLayout layout_MixLane;
    @InjectView(R.id.intelligent_Video)
    SurfaceView intelligent_Video;
    @InjectView(R.id.intelligent_frame)
    FrameLayout intelligent_frame;
    @InjectView(R.id.intel_frameLine)
    FrameLayout intel_frameLine;
    @InjectView(R.id.intelligent_Draw)
    Button intelligent_Draw;
    @InjectView(R.id.intelligent_DrawSure)
    Button intelligent_DrawSure;
    @InjectView(R.id.intelligent_DrawLine)
    Button intelligent_DrawLine;
    @InjectView(R.id.intelligent_ClearDraw)
    Button intelligent_ClearDraw;
    @InjectView(R.id.intelligent_DrawFirstLine)
    Button intelligent_DrawFirstLine;
    @InjectView(R.id.intelligent_DrawRightLine)
    Button intelligent_DrawRightLine;


    @InjectView(R.id.intelligent_Setting)
    protected Button intelligent_Setting;  //setting
    @InjectView(R.id.intelligent_Sure)
    protected Button intelligent_Sure;   //确定

    @InjectView(R.id.PaiS_check)
    CheckBox PaiS_check;//牌识
    @InjectView(R.id.PaiS_JC)
    Spinner PaiS_JC;


    @InjectView(R.id.layout1)
    CheckBox layout1 ;
    @InjectView(R.id.layout2)
    CheckBox layout2 ;
    @InjectView(R.id.layout3)
    CheckBox layout3 ;
    @InjectView(R.id.layout4)
    CheckBox layout4 ;
    @InjectView(R.id.layout5)
    CheckBox layout5 ;

    @InjectView(R.id.layout6)
    CheckBox layout6 ;
    @InjectView(R.id.layout7)
    CheckBox layout7 ;
    @InjectView(R.id.layout8)
    CheckBox layout8 ;
    @InjectView(R.id.layout9)
    CheckBox layout9 ;
    @InjectView(R.id.layout10)
    CheckBox layout10 ;

    @InjectView(R.id.layout11)
    CheckBox layout11 ;
    @InjectView(R.id.layout12)
    CheckBox layout12 ;
    @InjectView(R.id.layout13)
    CheckBox layout13 ;
    @InjectView(R.id.layout14)
    CheckBox layout14 ;
    @InjectView(R.id.layout15)
    CheckBox layout15 ;

    @InjectView(R.id.layout16)
    CheckBox layout16 ;
    @InjectView(R.id.layout17)
    CheckBox layout17 ;
    @InjectView(R.id.layout18)
    CheckBox layout18 ;
    @InjectView(R.id.layout19)
    CheckBox layout19 ;
    @InjectView(R.id.layout20)
    CheckBox layout20 ;

    @InjectView(R.id.layout21)
    CheckBox layout21 ;
    @InjectView(R.id.layout22)
    CheckBox layout22 ;
    @InjectView(R.id.layout23)
    CheckBox layout23 ;
    @InjectView(R.id.layout24)
    CheckBox layout24 ;
    @InjectView(R.id.layout25)
    CheckBox layout25 ;
    @InjectView(R.id.layout26)
    CheckBox layout26 ;
    @InjectView(R.id.layout27)
    CheckBox layout27 ;
    @InjectView(R.id.layout28)
    CheckBox layout28 ;
    @InjectView(R.id.layout29)
    CheckBox layout29 ;
    @InjectView(R.id.layout30)
    CheckBox layout30 ;
    @InjectView(R.id.layout31)
    CheckBox layout31 ;
    @InjectView(R.id.layout32)
    CheckBox layout32 ;
    @InjectView(R.id.layout33)
    CheckBox layout33 ;
    @InjectView(R.id.layout34)
    CheckBox layout34 ;
    @InjectView(R.id.layout35)
    CheckBox layout35 ;
    @InjectView(R.id.layout36)
    CheckBox layout36 ;
    @InjectView(R.id.layout37)
    CheckBox layout37 ;
    @InjectView(R.id.layout38)
    CheckBox layout38 ;
    @InjectView(R.id.layout39)
    CheckBox layout39 ;
    @InjectView(R.id.layout40)
    CheckBox layout40 ;

    /**
     *    合成方式
     */
    @InjectView(R.id.intel_byIsMerge)
    CheckBox intel_byIsMerge; //是否启用图片合成
    //1.二张图片
    @InjectView(R.id.intelligent_RadioTwo)
    protected RadioGroup intelligent_RadioTwo;
    @InjectView(R.id.intelligent_Radio1)
    protected RadioButton intelligent_Radio1;
    @InjectView(R.id.intelligent_Radio2)
    protected RadioButton intelligent_Radio2;
    @InjectView(R.id.intelligent_Radio3)
    protected RadioButton intelligent_Radio3;
    @InjectView(R.id.intelligent_Radio4)
    protected RadioButton intelligent_Radio4;
    @InjectView(R.id.intelligent_Radio5)
    protected RadioButton intelligent_Radio5;
    @InjectView(R.id.intelligent_Radio6)
    protected RadioButton intelligent_Radio6;
    @InjectView(R.id.intelligent_Radio7)
    protected RadioButton intelligent_Radio7;

    //2.一张图片
    @InjectView(R.id.intelligent_RadioOne)
    protected RadioGroup intelligent_RadioOne;
    @InjectView(R.id.intelligent_Radio8)
    protected RadioButton intelligent_Radio8;
    @InjectView(R.id.intelligent_Radio9)
    protected RadioButton intelligent_Radio9;
    @InjectView(R.id.intelligent_Radio10)
    protected RadioButton intelligent_Radio10;
    @InjectView(R.id.intelligent_Radio11)
    protected RadioButton intelligent_Radio11;
    @InjectView(R.id.intelligent_Radio12)
    protected RadioButton intelligent_Radio12;


    private static int m_iLogID = -1; // return by NET_DVR_Login_v30
    private int m_iPlayID = -1; // return by NET_DVR_RealPlay_V30
    private int m_iChanNum ; // channel number
    private int m_iStartChan = 0; // start channel no
    private DrawData drawData;
    private DrawLine drawLine = null;
    private DrawFirstLoadView drawFirstLoadView = null;
    private DrawRightLaneView drawRightLaneView = null;
    private Dialog progressDialog;
    private AlertDialog.Builder dialog;

    HCNetSDKByJNA.NET_ITC_RSTRIGGERCFG struTriggerCfg = null;
    HCNetSDKByJNA.NET_ITS_IMGMERGE_CFG img;
    HCNetSDKByJNA.NET_ITC_TRIGGERCFG struHvt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intelligentfragment);
        ButterKnife.inject(this);

        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("智能设置");
        textview.setVisibility(View.GONE);

        m_iLogID = getIntent().getExtras().getInt("m_iLogID");
        m_iStartChan =  getIntent().getExtras().getInt("m_iStartChan");
        m_iChanNum = getIntent().getExtras().getInt("m_iChanNum");

        if (m_iLogID > -1)
        {
            initView();
        }

    }

    private void initView()
    {
        layout_Title.setOnClickListener(this);
        intelligent_PaiS.setOnClickListener(this);
        intelligent_HChen.setOnClickListener(this);

        //牌识区设置
        intelligent_Setting.setOnClickListener(this);
        intelligent_Sure.setOnClickListener(this);
        intelligent_PaiS.setBackgroundResource(R.color.colorPrimaryDark);
        intelligent_Draw.setOnClickListener(this);
        intelligent_DrawSure.setOnClickListener(this);
        intelligent_DrawLine.setOnClickListener(this);
        intelligent_ClearDraw.setOnClickListener(this);
        intelligent_DrawFirstLine.setOnClickListener(this);
        intelligent_DrawRightLine.setOnClickListener(this);
        drawData = new DrawData();
        initSpinner();

        CheckClick();
    }


    private String SelectedNameJC = null;
    private float x ,y =0;
    private void initSpinner()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mode,android.R.layout.simple_spinner_dropdown_item);
        PaiS_JC.setAdapter(adapter);
        PaiS_JC.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyPreference.getInstance(IntelligentFragment.this).SetPAISHIJC(position);
                SelectedNameJC = parent.getItemAtPosition(position).toString();
                if (position ==0){
                    stopSinglePreview();
                    layout_Rs485.setVisibility(View.VISIBLE);
                    layout_MixLane.setVisibility(View.GONE);
                    intelligent_Rs485.getHolder().setKeepScreenOn(true);
                    intelligent_Rs485.getHolder().addCallback(new SurfaceViewLis());
                    handler = new Handler();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            preview(intelligent_Rs485);
                        }
                    };
                    handler.postDelayed(runnable,100);
                    CheckClick();
                }else {
                    stopSinglePreview();
                    layout_MixLane.setVisibility(View.VISIBLE);
                    layout_Rs485.setVisibility(View.GONE);
                    x = intelligent_frame.getWidth();
                    y = intelligent_frame.getHeight();
                    intelligent_Video.getHolder().setKeepScreenOn(true);
                    intelligent_Video.getHolder().addCallback(new SurfaceViewLis());
                    handler = new Handler();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            preview(intelligent_Video);
                        }
                    };
                    handler.postDelayed(runnable,100);
                    drawLine = new DrawLine(IntelligentFragment.this);
                    intelligent_frame.addView(drawLine);
                    drawFirstLoadView = new DrawFirstLoadView(IntelligentFragment.this);
                    intelligent_frame.addView(drawFirstLoadView);
                    drawRightLaneView = new DrawRightLaneView(IntelligentFragment.this);
                    intelligent_frame.addView(drawRightLaneView);
                    initData();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private Handler handler;
    private Runnable runnable;
    private void initData()
    {
        for (int i = 0; i < 4; i++) {
            if (i== 0) {
                intelligent_frame.addView(new Draw4(IntelligentFragment.this,
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_X(),
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_Y(),
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_X1(),
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_Y1(),i));
            }else if (i == 1) {
                intelligent_frame.addView(new Draw4(IntelligentFragment.this,
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_X1(),
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_Y1(),
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_X2(),
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_Y2(),i));
            }else if (i == 2) {
                intelligent_frame.addView(new Draw4(IntelligentFragment.this,
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_X2(),
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_Y2(),
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_X3(),
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_Y3(),i));
            }else {
                intelligent_frame.addView(new Draw4(IntelligentFragment.this,
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_X3(),
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_Y3(),
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_X(),
                        MyPreference.getInstance(IntelligentFragment.this).getDRAW_Y(),i));
            }

        }
    }

    private void  preview(SurfaceView intelligent)
    {
        if (m_iLogID < 0) {
            Log.e(TAG, "please login on device first");
            return;
        }
        startSinglePreview(intelligent);
    }
    private void startSinglePreview(SurfaceView intelligent)
    {
        NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
        previewInfo.lChannel = m_iStartChan;
        previewInfo.dwStreamType = 0; // substream
        previewInfo.bBlocked = 1;
        previewInfo.hHwnd = intelligent.getHolder();
        // HCNetSDK start preview
        m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(m_iLogID,
                previewInfo, null);
        if (m_iPlayID < 0) {
            Log.e(TAG, "NET_DVR_RealPlay_V40 is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return;
        }
//        boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_OpenSound(m_iPlayID);
        Log.i(TAG,
                "NetSdk Play sucess ***********************3***************************");
    }
    private void stopSinglePreview()
    {
        if (m_iPlayID < 0) {
            Log.e(TAG, "m_iPlayID < 0");
            return;
        }
        // net sdk stop preview
        if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID)) {
            Log.e(TAG, "StopRealPlay is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return;
        }
        Log.e(TAG, "stopSinglePreview:"
                + HCNetSDK.getInstance().NET_DVR_GetLastError());
        m_iPlayID = -1 ;
    }
    class SurfaceViewLis implements SurfaceHolder.Callback{

        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    private void Test_PictureHeChen(int iUserID)
    {
        img = new HCNetSDKByJNA.NET_ITS_IMGMERGE_CFG();
        Pointer lpPicConfig = img.getPointer();
        IntByReference pInt = new IntByReference(0);
        boolean bRes = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDKByJNA.NET_ITS_GET_IMGMERGE_CFG,
                m_iChanNum, lpPicConfig, img.size(), pInt);
        img.read();
        if (bRes){
            System.out.println("dvr dev Get SuccessFul !" );
        }
    }
    //获取图片合成
    private void getPictureHeChen(int iUserID)
    {
        Test_PictureHeChen(iUserID);
        if (img.byIsMerge == 1){
            intel_byIsMerge.setChecked(true);
        }else {
            intel_byIsMerge.setChecked(false);
        }
    }

    //设置图片合成方式
    private void setPictureHeChen(int iUserID)
    {
        img.write();
        boolean bRes = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDKByJNA.NET_ITS_SET_IMGMERGE_CFG,
                m_iChanNum, img.getPointer(), img.size());
        if (bRes){
            Log.e(TAG, "NET_ITS_SET_IMGMERGE_CFG: succ");
        }else {
            Log.e(TAG, "NET_ITS_SET_IMGMERGE_CFG failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
        }
    }
    //合成启用按钮
    private void CheckBoxStart(int iUserID)
    {
        intel_byIsMerge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (img.byIsMerge == 1){
                        intel_byIsMerge.setChecked(true);
                    } else{
                        img.byIsMerge = 1;
                        setPictureHeChen(iUserID);
                        intel_byIsMerge.setChecked(true);
                    }
                }else {
                    img.byIsMerge = 0;
                    setPictureHeChen(iUserID);
                    intel_byIsMerge.setChecked(false);
                }
                getPictureHeChen(m_iLogID);
            }
        });
    }
    private void Twoclick(int iUserID)
    {
        intelligent_RadioTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                MyPreference.getInstance(IntelligentFragment.this).SetRADIOTWO(checkedId);
                if (intelligent_Radio1.getId() == checkedId){
                    if (img.dwTwoMergeType == 208){
                        intelligent_Radio1.setChecked(true);
                    } else{
                        img.dwTwoMergeType = 208;
                        setPictureHeChen(iUserID);
                        intelligent_Radio1.setChecked(true);
                    }
                }else if (intelligent_Radio2.getId() == checkedId){
                    if (img.dwTwoMergeType == 207){
                        intelligent_Radio2.setChecked(true);
                    }else{
                        img.dwTwoMergeType = 207;
                        setPictureHeChen(iUserID);
                        intelligent_Radio2.setChecked(true);
                    }
                }else if (intelligent_Radio4.getId() == checkedId){
                    if (img.dwTwoMergeType == 210){
                        intelligent_Radio4.setChecked(true);
                    }else{
                        img.dwTwoMergeType = 210;
                        setPictureHeChen(iUserID);
                        intelligent_Radio4.setChecked(true);
                    }
                } else if (intelligent_Radio4.getId() == checkedId){
                    if (img.dwTwoMergeType == 210){
                        intelligent_Radio4.setChecked(true);
                    }else{
                        img.dwTwoMergeType = 210;
                        setPictureHeChen(iUserID);
                        intelligent_Radio4.setChecked(true);
                    }
                }else if (intelligent_Radio5.getId() == checkedId){
                    if (img.dwTwoMergeType == 206){
                        intelligent_Radio5.setChecked(true);
                    }else{
                        img.dwTwoMergeType = 206;
                        setPictureHeChen(iUserID);
                        intelligent_Radio5.setChecked(true);
                    }
                }else if (intelligent_Radio6.getId() == checkedId){
                    if (img.dwTwoMergeType == 205){
                        intelligent_Radio6.setChecked(true);
                    }else{
                        img.dwTwoMergeType = 205;
                        setPictureHeChen(iUserID);
                        intelligent_Radio6.setChecked(true);
                    }
                }else if (intelligent_Radio7.getId() == checkedId){
                    if (img.dwTwoMergeType == 202){
                        intelligent_Radio7.setChecked(true);
                    }else{
                        img.dwTwoMergeType = 202;
                        setPictureHeChen(iUserID);
                        intelligent_Radio7.setChecked(true);
                    }
                }else if (intelligent_Radio3.getId() == checkedId){
                    if (img.dwTwoMergeType == 255){
                        intelligent_Radio3.setChecked(true);
                    }else{
                        img.dwTwoMergeType = 255;
                        setPictureHeChen(iUserID);
                        intelligent_Radio3.setChecked(true);
                    }
                }
            }
        });
        intelligent_RadioOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                MyPreference.getInstance(IntelligentFragment.this).SetRADIOONE(checkedId);
                if (intelligent_Radio8.getId() == checkedId){
                    if (img.dwOneMergeType == 104){
                        intelligent_Radio8.setChecked(true);
                    }else {
                        img.dwOneMergeType = 104;
                        setPictureHeChen(iUserID);
                        intelligent_Radio8.setChecked(true);
                    }
                }else if (intelligent_Radio9.getId() == checkedId){
                    if (img.dwOneMergeType == 103){
                        intelligent_Radio9.setChecked(true);
                    } else{
                        img.dwOneMergeType = 103;
                        setPictureHeChen(iUserID);
                        intelligent_Radio9.setChecked(true);
                    }
                }else if (intelligent_Radio10.getId() == checkedId){
                    if (img.dwOneMergeType == 101){
                        intelligent_Radio10.setChecked(true);
                    } else{
                        img.dwOneMergeType = 101;
                        setPictureHeChen(iUserID);
                        intelligent_Radio10.setChecked(true);
                    }
                }else if (intelligent_Radio11.getId() == checkedId){
                    if (img.dwOneMergeType == 102){
                        intelligent_Radio11.setChecked(true);
                    } else{
                        img.dwOneMergeType = 102;
                        setPictureHeChen(iUserID);
                        intelligent_Radio11.setChecked(true);
                    }
                }else if (intelligent_Radio12.getId() == checkedId){
                    if (img.dwOneMergeType == 255){
                        intelligent_Radio12.setChecked(true);
                    } else{
                        img.dwOneMergeType = 255;
                        setPictureHeChen(iUserID);
                        intelligent_Radio12.setChecked(true);
                    }
                }
            }
        });
    }

    private static boolean VideoSucc = false;
    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.layout_Title:
                stopSinglePreview();
                finish();
                break;
            case R.id.intelligent_PaiS:
                layout_PaiS.setVisibility(View.VISIBLE);
                layout_HChen.setVisibility(View.GONE);
                intelligent_PaiS.setBackgroundResource(R.color.colorPrimaryDark);
                intelligent_HChen.setBackgroundResource(R.color.grey);
                break;
            case R.id.intelligent_HChen:
                layout_HChen.setVisibility(View.VISIBLE);
                layout_PaiS.setVisibility(View.GONE);
                intelligent_HChen.setBackgroundResource(R.color.colorPrimaryDark);
                intelligent_PaiS.setBackgroundResource(R.color.grey);
                if (m_iLogID>-1){
                    getPictureHeChen(m_iLogID);
                    CheckBoxStart(m_iLogID);
                    Twoclick(m_iLogID);
                }
                break;
            case R.id.intelligent_Setting:  //牌识区设置
                SettingClear();
                break;
            case R.id.intelligent_Sure: //确定
                if (m_iLogID>-1){
                    progressDialog = Util.createLoadingDialog(IntelligentFragment.this,"正在设置中...");
                    progressDialog.show();
                    //新建线程   
                    new Thread() {
                        @Override
                        public void run() {
                            //需要花时间计算的方法   
                            try{
                                SetCanshu(m_iLogID);
                            }catch ( Exception e){
                                e.fillInStackTrace();
                            }
                            //向handler发消息  
                            progressDialog.dismiss();
                        }
                    }.start();

                }else{
                    Toast.makeText(IntelligentFragment.this,"请连接相机",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.intelligent_Draw:
                if (num == 0) {
                    intelligent_frame.removeAllViews();
                    intelligent_frame.addView(drawFirstLoadView);
                    intelligent_frame.addView(drawRightLaneView);
                    addView();
                    num++;
                }else if (num == 1) {
                    addView();
                    num++;
                }else if(num == 2){
                    addView();
                    num++;
                }else if(num == 3){
                    addView();
                    num=0;
                }
                Log.e("123","width:"+intelligent_frame.getWidth()+"==height:"+intelligent_frame.getHeight());
                break;
            case R.id.intelligent_DrawLine:
                if (drawLine != null){
                    intelligent_frame.removeView(drawLine);
                    intelligent_frame.addView(drawLine);
                }else {
                    intelligent_frame.removeView(drawLine);
                }
                break;
            case R.id.intelligent_ClearDraw:
                intelligent_frame.removeAllViews();
                intelligent_frame.addView(drawFirstLoadView);
                intelligent_frame.addView(drawRightLaneView);
                num = 0;
                break;
            case R.id.intelligent_DrawSure:
                x = intelligent_frame.getWidth();
                y = intelligent_frame.getHeight();
                progressDialog = Util.createLoadingDialog(IntelligentFragment.this,"正在设置中...");
                progressDialog.show();
                //新建线程   
                new Thread() {
                    @Override
                    public void run() {
                        //需要花时间计算的方法   
                        try{
                            getPRS(m_iLogID);
                        }catch ( Exception e){
                            e.fillInStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }.start();
                break;
            case R.id.intelligent_DrawFirstLine:
                if (drawLine != null){
                    intelligent_frame.removeView(drawFirstLoadView);
                    intelligent_frame.addView(drawFirstLoadView);
                }else {
                    intelligent_frame.removeView(drawFirstLoadView);
                }
                break;
            case R.id.intelligent_DrawRightLine:
                if (drawLine != null){
                    intelligent_frame.removeView(drawRightLaneView);
                    intelligent_frame.addView(drawRightLaneView);
                }else {
                    intelligent_frame.removeView(drawRightLaneView);
                }
                break;
            default:
                break;
        }
    }
    private void addView()
    {
        intelligent_frame.addView(new Draw4(IntelligentFragment.this,0,0,0,0,num));
    }

    private void getPRS(int uid)
    {
        struHvt = JNATest.struHvt;
        if (struHvt == null)
        {
            Util.show_Toast(IntelligentFragment.this,"请进行参数配置",1000);
        }
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struPlateRecog.struPos[0].fX = (float) MyPreference.getInstance(this).getDRAW_X()/x;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struPlateRecog.struPos[0].fY = (float)MyPreference.getInstance(this).getDRAW_Y()/y;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struPlateRecog.struPos[1].fX = (float)MyPreference.getInstance(this).getDRAW_X1()/x;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struPlateRecog.struPos[1].fY = (float)MyPreference.getInstance(this).getDRAW_Y1()/y;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struPlateRecog.struPos[2].fX = (float)MyPreference.getInstance(this).getDRAW_X2()/x;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struPlateRecog.struPos[2].fY = (float)MyPreference.getInstance(this).getDRAW_Y2()/y;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struPlateRecog.struPos[3].fX = (float)MyPreference.getInstance(this).getDRAW_X3()/x;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struPlateRecog.struPos[3].fY = (float)MyPreference.getInstance(this).getDRAW_Y3()/y;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struTrigLine.struLine.struStart.fX = (float)MyPreference.getInstance(this).getDRAWLINE_X()/x;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struTrigLine.struLine.struStart.fY = (float)MyPreference.getInstance(this).getDRAWLINE_Y()/y;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struTrigLine.struLine.struEnd.fX = (float)MyPreference.getInstance(this).getDRAWLINE_X1()/x;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struTrigLine.struLine.struEnd.fY = (float)MyPreference.getInstance(this).getDRAWLINE_Y1()/y;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneBoundaryLine.struLine.struStart.fX =(float)MyPreference.getInstance(this).getDRAWRIGHTLINE_X()/x;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneBoundaryLine.struLine.struStart.fY = (float)MyPreference.getInstance(this).getDRAWRIGHTLINE_Y()/y;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneBoundaryLine.struLine.struEnd.fX = (float)MyPreference.getInstance(this).getDRAWRIGHTLINE_X1()/x;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneBoundaryLine.struLine.struEnd.fY = (float) MyPreference.getInstance(this).getDRAWRIGHTLINE_Y1()/y;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struLineLeft.struLine.struStart.fX = (float) MyPreference.getInstance(this).getDRAWFRISTLINE_X()/x;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struLineLeft.struLine.struStart.fY = (float) MyPreference.getInstance(this).getDRAWFRISTLINE_Y()/y;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struLineLeft.struLine.struEnd.fX = (float) MyPreference.getInstance(this).getDRAWFRISTLINE_X1()/x;
        struHvt.struTriggerParam.uTriggerParam.struHvtV50.struLaneParam[0].struLineLeft.struLine.struEnd.fY = (float)MyPreference.getInstance(this).getDRAWFRISTLINE_Y1()/y;
        struHvt.write();
        boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(uid, HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG, 0x20, struHvt.getPointer(), struHvt.size());
        if(!bRet)
        {
            progressDialog.dismiss();
            VideoSucc = false;
            System.out.println("NET_ITC_SET_TRIGGERCFG failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
            Util.show_Toast(IntelligentFragment.this,"参数设置错误",1000);
            Util.show_dialog(IntelligentFragment.this,"牌识区设置","牌识区设置错误");
        }
        else
        {
            progressDialog.dismiss();
            VideoSucc = true;
            struHvt.struTriggerParam.uTriggerParam.struHvtV50.read();
            System.out.println("NET_ITC_SET_TRIGGERCFG succ! 车道数==="+struHvt.struTriggerParam.uTriggerParam.struHvtV50.byLaneNum);
            Util.show_Toast(IntelligentFragment.this,"牌识区设置成功",1000);
        }
    }

    byte num = 0;
    private void CheckClick()
    {

        PaiS_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MyPreference.getInstance(IntelligentFragment.this).SetPAISHI(isChecked);
                    num=1;
                }else {
                    MyPreference.getInstance(IntelligentFragment.this).SetPAISHI(isChecked);
                    num=0;
                }
            }
        });
        layout1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout1.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox1(isChecked);
                }else {
                    layout1.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox1(isChecked);
                }
            }
        });
        layout2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout2.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox2(isChecked);
                }else {
                    layout2.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox2(isChecked);
                }
            }
        });
        layout3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout3.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox3(isChecked);
                }else {
                    layout3.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox3(isChecked);
                }
            }
        });
        layout4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout4.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox4(isChecked);
                }else {
                    layout4.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox4(isChecked);
                }
            }
        });
        layout5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout5.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox5(isChecked);
                }else {
                    layout5.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox5(isChecked);
                }
            }
        });

        layout6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout6.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox6(isChecked);
                }else {
                    layout6.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox6(isChecked);
                }
            }
        });
        layout7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout7.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox7(isChecked);
                }else {
                    layout7.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox7(isChecked);
                }
            }
        });
        layout8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout8.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox8(isChecked);
                }else {
                    layout8.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox8(isChecked);
                }
            }
        });
        layout9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout9.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox9(isChecked);
                }else {
                    layout9.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox9(isChecked);
                }
            }
        });
        layout10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout10.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox10(isChecked);
                }else {
                    layout10.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox10(isChecked);
                }
            }
        });

        layout11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout11.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox11(isChecked);
                }else {
                    layout11.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox11(isChecked);
                }
            }
        });
        layout12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout12.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox12(isChecked);
                }else {
                    layout12.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox12(isChecked);
                }
            }
        });
        layout13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout13.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox13(isChecked);
                }else {
                    layout13.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox13(isChecked);
                }
            }
        });
        layout14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout14.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox14(isChecked);
                }else {
                    layout14.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox14(isChecked);
                }
            }
        });
        layout15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout15.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox15(isChecked);
                }else {
                    layout15.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox15(isChecked);
                }
            }
        });

        layout16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout16.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox16(isChecked);
                }else {
                    layout16.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox16(isChecked);
                }
            }
        });
        layout17.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout17.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox17(isChecked);
                }else {
                    layout17.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox17(isChecked);
                }
            }
        });
        layout18.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout18.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox18(isChecked);
                }else {
                    layout18.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox18(isChecked);
                }
            }
        });
        layout19.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout19.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox19(isChecked);
                }else {
                    layout19.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox19(isChecked);
                }
            }
        });
        layout20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout20.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox20(isChecked);
                }else {
                    layout20.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox20(isChecked);
                }
            }
        });
        layout21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout21.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox21(isChecked);
                }else {
                    layout21.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox21(isChecked);
                }
            }
        });
        layout22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout22.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox22(isChecked);
                }else {
                    layout22.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox22(isChecked);
                }
            }
        });
        layout23.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout23.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox23(isChecked);
                }else {
                    layout23.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox23(isChecked);
                }
            }
        });
        layout24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout24.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox24(isChecked);
                }else {
                    layout24.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox24(isChecked);
                }
            }
        });

        layout25.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout25.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox25(isChecked);
                }else {
                    layout25.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox25(isChecked);
                }
            }
        });
        layout26.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout26.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox26(isChecked);
                }else {
                    layout26.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox26(isChecked);
                }
            }
        });
        layout27.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout27.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox27(isChecked);
                }else {
                    layout27.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox27(isChecked);
                }
            }
        });
        layout28.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout28.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox28(isChecked);
                }else {
                    layout28.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox28(isChecked);
                }
            }
        });
        layout29.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout29.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox29(isChecked);
                }else {
                    layout29.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox29(isChecked);
                }
            }
        });
        layout30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout30.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox30(isChecked);
                }else {
                    layout30.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox30(isChecked);
                }
            }
        });

        layout31.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout31.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox31(isChecked);
                }else {
                    layout31.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox31(isChecked);
                }
            }
        });
        layout32.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout32.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox32(isChecked);
                }else {
                    layout32.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox32(isChecked);
                }
            }
        });
        layout33.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout33.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox33(isChecked);
                }else {
                    layout33.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox33(isChecked);
                }
            }
        });
        layout34.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout34.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox34(isChecked);
                }else {
                    layout34.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox34(isChecked);
                }
            }
        });
        layout35.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout35.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox35(isChecked);
                }else {
                    layout35.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox35(isChecked);
                }
            }
        });
        layout36.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout36.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox36(isChecked);
                }else {
                    layout36.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox36(isChecked);
                }
            }
        });
        layout37.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout37.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox37(isChecked);
                }else {
                    layout37.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox37(isChecked);
                }
            }
        });
        layout38.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout38.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox38(isChecked);
                }else {
                    layout38.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox38(isChecked);
                }
            }
        });
        layout39.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout39.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox39(isChecked);
                }else {
                    layout39.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox39(isChecked);
                }
            }
        });
        layout40.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout40.setBackgroundResource(R.drawable.checkbox1);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox40(isChecked);
                }else {
                    layout40.setBackgroundResource(R.color.transparent);
                    MyPreference.getInstance(IntelligentFragment.this).SetCheckBox40(isChecked);
                }
            }
        });
    }

    private static float x1,y1,x2,y2,x3,y3,x0,y0 =0;
    private void SetNum()
    {
        x1=y1=x2=y2=x3=y3=x0=y0= 0;
        if (layout1.isChecked()){
            setLayout1();
        }
        else if (layout2.isChecked()){
            setLayout2();
        }
        else if (layout3.isChecked()){
            setLayout3();
        }
        else if (layout4.isChecked()){
            setLayout4();
        }
        else if (layout5.isChecked()){
            setLayout5();
        }
        else if (layout6.isChecked()){
            setLayout6();
        }
        else if (layout7.isChecked()){
            setLayout7();
        }
        else if (layout8.isChecked()){
            setLayout8();
        }
        else if (layout9.isChecked()){
            setLayout9();
        }
        else if (layout10.isChecked()){
            setLayout10();
        }
        else if (layout11.isChecked()){
            setLayout11();
        }
        else if (layout12.isChecked()){
            setLayout12();
        }
        else if (layout13.isChecked()){
            setLayout13();
        }
        else if (layout14.isChecked()){
            setLayout14();
        }
        else if (layout15.isChecked()){
            setLayout15();
        }
        else if (layout16.isChecked()){
            setLayout16();
        }
        else if (layout17.isChecked()){
            setLayout17();
        }
        else if (layout18.isChecked()){
            setLayout18();
        }
        else if (layout19.isChecked()){
            setLayout19();
        }
        else if (layout20.isChecked()){
            setLayout20();
        }
        else if (layout21.isChecked()){
            setLayout21();
        }
        else if (layout22.isChecked()){
            setLayout22();
        }
        else if (layout23.isChecked()){
            setLayout23();
        }
        else if (layout24.isChecked()){
            setLayout24();
        }
        else if (layout25.isChecked()){
            setLayout25();
        }
        else if (layout26.isChecked()){
            setLayout26();
        }
        else if (layout27.isChecked()){
            setLayout27();
        }
        else if (layout28.isChecked()){
            setLayout28();
        }
        else if (layout29.isChecked()){
            setLayout29();
        }
        else if (layout30.isChecked()){
            setLayout30();
        }
        else if (layout31.isChecked()){
            setLayout31();
        }
        else if (layout32.isChecked()){
            setLayout32();
        }
        else if (layout33.isChecked()){
            setLayout33();
        }
        else if (layout34.isChecked()){
            setLayout34();
        }
        else if (layout35.isChecked()){
            setLayout35();
        }
    }

    private void setLayout1()
    {
        //1-30
        if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked()&& layout4.isChecked() && layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked()
                )
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        //1-20
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked()&& layout4.isChecked() && layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        //1-15
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked()&& layout4.isChecked() && layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        //1-10
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked()&& layout4.isChecked() && layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.6;
            x3 = (float) 0.001;
            y3 = (float) 0.6;
        }
        //1-5
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked()&& layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.45;
            x3 = (float) 0.001;
            y3 = (float) 0.45;
        }
        //1-5 - 21-25
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked()&& layout4.isChecked() && layout6.isChecked() && layout7.isChecked()
                && layout8.isChecked() && layout9.isChecked() && layout11.isChecked() && layout12.isChecked() && layout13.isChecked()
                && layout14.isChecked() && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked()
                && layout5.isChecked() && layout10.isChecked() && layout15.isChecked() && layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3 ;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6 ;
            y2 = (float) 0.999 ;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        //1-5 - 16-20
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked()&& layout4.isChecked() && layout6.isChecked() && layout7.isChecked()
                && layout8.isChecked() && layout9.isChecked() && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked()
                && layout5.isChecked() && layout10.isChecked() && layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        //1-4 -6-9
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked()&& layout4.isChecked() && layout6.isChecked() && layout7.isChecked()
                && layout8.isChecked() && layout9.isChecked() && layout5.isChecked() && layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        //1-4
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked()&& layout4.isChecked() && layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.6;
            x3 = (float) 0.001;
            y3 = (float) 0.6;
        }
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked()&& layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.45;
            x3 = (float) 0.001;
            y3 = (float) 0.45;
        }
        //1-4 - 21-24
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout6.isChecked() && layout7.isChecked() && layout8.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout16.isChecked() && layout17.isChecked()
                && layout18.isChecked() && layout4.isChecked() && !layout5.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && layout14.isChecked() && !layout15.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        //1-4 - 11-14
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked()&& layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        //1-4 - 6-9
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked()&& layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.6;
            x3 = (float) 0.001;
            y3 = (float) 0.6;
        }
        //1-4
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.45;
            x3 = (float) 0.001;
            y3 = (float) 0.45;
        }
        //1-3 - 21-23
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        //1-3 -16-18
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.9;
            x1 = (float) 0.4;
            y1 = (float) 0.9;
            x2 = (float) 0.4;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        //1-3 - 11-13
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        //1-3 - 6-8
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.6;
            x3 = (float) 0.001;
            y3 = (float) 0.6;
        }
        //1-3
        else if(layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.45;
            x3 = (float) 0.001;
            y3 = (float) 0.45;
        }
        //1-2 - 21-22
        else if(layout1.isChecked() && layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.3;
            y1 = (float) 0.3;
            x2 = (float) 0.3;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        //1-2 - 16-17
        else if(layout1.isChecked() && layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.9;
            x1 = (float) 0.3;
            y1 = (float) 0.9;
            x2 = (float) 0.3;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        //1-2 - 11-12
        else if(layout1.isChecked() && layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.3;
            y1 = (float) 0.3;
            x2 = (float) 0.3;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        //1-2 - 6-7
        else if(layout1.isChecked() && layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.3;
            y1 = (float) 0.3;
            x2 = (float) 0.3;
            y2 = (float) 0.6;
            x3 = (float) 0.001;
            y3 = (float) 0.6;
        }
        //1-2
        else if(layout1.isChecked() && layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.3;
            y1 = (float) 0.3;
            x2 = (float) 0.3;
            y2 = (float) 0.45;
            x3 = (float) 0.001;
            y3 = (float) 0.45;
        }
        //1、6、11、16、21
        else if(layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.15;
            y1 = (float) 0.3;
            x2 = (float) 0.15;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        //1、6、11、16
        else if(layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.15;
            y1 = (float) 0.3;
            x2 = (float) 0.15;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        //1、6、11
        else if(layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.15;
            y1 = (float) 0.3;
            x2 = (float) 0.15;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        //1、6
        else if(layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.15;
            y1 = (float) 0.3;
            x2 = (float) 0.15;
            y2 = (float) 0.6;
            x3 = (float) 0.001;
            y3 = (float) 0.6;
        }
        //1
        else if(layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.3;
            x1 = (float) 0.15;
            y1 = (float) 0.3;
            x2 = (float) 0.15;
            y2 = (float) 0.45;
            x3 = (float) 0.001;
            y3 = (float) 0.45;
        }
    }
    private void setLayout2()
    {
        //2-26 -21-30
        if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        //2-26 -16-29
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        //2-26 -12-28
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        //2-26 -7-27
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.6;
            x3 = (float) 0.15;
            y3 = (float) 0.6;
        }
        //2-26
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.45;
            x3 = (float) 0.15;
            y3 = (float) 0.45;
        }
        //2-5 - 22-25
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.6;
            x3 = (float) 0.15;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.45;
            x3 = (float) 0.15;
            y3 = (float) 0.45;
        }
        //2-5 -17-20
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.6;
            x3 = (float) 0.15;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.45;
            x3 = (float) 0.15;
            y3 = (float) 0.45;
        }
        //
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.6;
            x3 = (float) 0.15;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.45;
            x3 = (float) 0.15;
            y3 = (float) 0.45;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.6;
            x3 = (float) 0.15;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.45;
            x3 = (float) 0.15;
            y3 = (float) 0.45;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.3;
            y1 = (float) 0.3;
            x2 = (float) 0.3;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.3;
            y1 = (float) 0.3;
            x2 = (float) 0.3;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.3;
            y1 = (float) 0.3;
            x2 = (float) 0.3;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.3;
            y1 = (float) 0.3;
            x2 = (float) 0.3;
            y2 = (float) 0.6;
            x3 = (float) 0.15;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.3;
            x1 = (float) 0.3;
            y1 = (float) 0.3;
            x2 = (float) 0.3;
            y2 = (float) 0.45;
            x3 = (float) 0.15;
            y3 = (float) 0.45;
        }

    }
    private void setLayout3()
    {
        //3-26 -23-25
        if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.6;
            x3 = (float) 0.3;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.45;
            x3 = (float) 0.3;
            y3 = (float) 0.45;
        }
        //3-31 - 23-35
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.6;
            x3 = (float) 0.3;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.45;
            x3 = (float) 0.3;
            y3 = (float) 0.45;
        }
        //3-26 - 23-30
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.6;
            x3 = (float) 0.3;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.45;
            x3 = (float) 0.3;
            y3 = (float) 0.45;
        }
        //3-5 - 23-25
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.6;
            x3 = (float) 0.3;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.45;
            x3 = (float) 0.3;
            y3 = (float) 0.45;
        }
        //3-4 - 23-24
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        //3、8、13、18、23
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.6;
            x3 = (float) 0.3;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.3;
            x1 = (float) 0.4;
            y1 = (float) 0.3;
            x2 = (float) 0.4;
            y2 = (float) 0.45;
            x3 = (float) 0.3;
            y3 = (float) 0.45;
        }
    }
    private void setLayout4()
    {
        //4-36 - 24-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.4;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.6;
            x3 = (float) 0.4;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.45;
            x3 = (float) 0.4;
            y3 = (float) 0.45;
        }
        //4-31 - 24-35
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.4;
            y3 = (float) 0.75;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.6;
            x3 = (float) 0.4;
            y3 = (float) 0.6;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.45;
            x3 = (float) 0.4;
            y3 = (float) 0.45;
        }
        //4-26 - 24-30
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.4;
            y3 = (float) 0.75;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.6;
            x3 = (float) 0.4;
            y3 = (float) 0.6;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.45;
            x3 = (float) 0.4;
            y3 = (float) 0.45;
        }
        //4-5 - 24-25
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.75;
            x3 = (float) 0.4;
            y3 = (float) 0.75;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.6;
            x3 = (float) 0.4;
            y3 = (float) 0.6;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.45;
            x3 = (float) 0.4;
            y3 = (float) 0.45;
        }
        //4、9、14、19、24
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.75;
            x3 = (float) 0.4;
            y3 = (float) 0.75;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.6;
            x3 = (float) 0.4;
            y3 = (float) 0.6;
        }
        else  if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.3;
            x1 = (float) 0.5;
            y1 = (float) 0.3;
            x2 = (float) 0.5;
            y2 = (float) 0.45;
            x3 = (float) 0.4;
            y3 = (float) 0.45;
        }
    }
    private void setLayout5()
    {
        //5-36 - 25-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.5;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.5;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.6;
            x3 = (float) 0.5;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.45;
            x3 = (float) 0.5;
            y3 = (float) 0.45;
        }
        //5-31 - 25-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.5;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.5;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.6;
            x3 = (float) 0.5;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.45;
            x3 = (float) 0.5;
            y3 = (float) 0.45;
        }
        //5-26 - 25-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.5;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.5;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.6;
            x3 = (float) 0.5;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.45;
            x3 = (float) 0.5;
            y3 = (float) 0.45;
        }
        //5、10、15、20、25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.3;
            x1 = (float) 0.6;
            y1 = (float) 0.3;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19  5、10、15、20、25
    }

    private void setLayout6()
    {
        //6-27 - 21-30
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.6;
            x3 = (float) 0.001;
            y3 = (float) 0.6;
        }
        //6-10 - 21-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.6;
            x3 = (float) 0.001;
            y3 = (float) 0.6;
        }
        //6-9 - 21-24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.5;
            y1 = (float) 0.45;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.5;
            y1 = (float) 0.45;
            x2 = (float) 0.5;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.5;
            y1 = (float) 0.45;
            x2 = (float) 0.5;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.5;
            y1 = (float) 0.45;
            x2 = (float) 0.5;
            y2 = (float) 0.6;
            x3 = (float) 0.001;
            y3 = (float) 0.6;
        }
        //6-8 - 21-23
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.4;
            y1 = (float) 0.45;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.4;
            y1 = (float) 0.45;
            x2 = (float) 0.4;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.4;
            y1 = (float) 0.45;
            x2 = (float) 0.4;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.4;
            y1 = (float) 0.45;
            x2 = (float) 0.4;
            y2 = (float) 0.6;
            x3 = (float) 0.001;
            y3 = (float) 0.6;
        }
        //6-7 - 21-22
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.3;
            y1 = (float) 0.45;
            x2 = (float) 0.3;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.3;
            y1 = (float) 0.45;
            x2 = (float) 0.3;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.3;
            y1 = (float) 0.45;
            x2 = (float) 0.3;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.3;
            y1 = (float) 0.45;
            x2 = (float) 0.3;
            y2 = (float) 0.6;
            x3 = (float) 0.001;
            y3 = (float) 0.6;
        }
        //6、21
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.45;
            x1 = (float) 0.15;
            y1 = (float) 0.45;
            x2 = (float) 0.15;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19  6、21
    }
    private void setLayout7()
    {
        //7-32 - 22-35
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.6;
            x3 = (float) 0.15;
            y3 = (float) 0.6;
        }
        //7-27 - 22-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.6;
            x3 = (float) 0.15;
            y3 = (float) 0.6;
        }
        //7-10 - 22-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.6;
            x3 = (float) 0.15;
            y3 = (float) 0.6;
        }
        //7-9 - 22-24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.5;
            y1 = (float) 0.45;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.5;
            y1 = (float) 0.45;
            x2 = (float) 0.5;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.5;
            y1 = (float) 0.45;
            x2 = (float) 0.5;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.5;
            y1 = (float) 0.45;
            x2 = (float) 0.5;
            y2 = (float) 0.6;
            x3 = (float) 0.15;
            y3 = (float) 0.6;
        }
        //7-8 - 22-23
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.4;
            y1 = (float) 0.45;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.4;
            y1 = (float) 0.45;
            x2 = (float) 0.4;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.4;
            y1 = (float) 0.45;
            x2 = (float) 0.4;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.4;
            y1 = (float) 0.45;
            x2 = (float) 0.4;
            y2 = (float) 0.6;
            x3 = (float) 0.15;
            y3 = (float) 0.6;
        }
        //7、22
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.45;
            x1 = (float) 0.3;
            y1 = (float) 0.45;
            x2 = (float) 0.3;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19  7、22
    }
    private void setLayout8()
    {
        //8-37 - 23-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.6;
            x3 = (float) 0.3;
            y3 = (float) 0.6;
        }
        //8-32 - 23-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.6;
            x3 = (float) 0.3;
            y3 = (float) 0.6;
        }
        //8-27 - 23-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.6;
            x3 = (float) 0.3;
            y3 = (float) 0.6;
        }
        //8-10 - 23-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.6;
            x3 = (float) 0.3;
            y3 = (float) 0.6;
        }
        //8-9 - 23-24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.5;
            y1 = (float) 0.45;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.5;
            y1 = (float) 0.45;
            x2 = (float) 0.5;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.5;
            y1 = (float) 0.45;
            x2 = (float) 0.5;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.5;
            y1 = (float) 0.45;
            x2 = (float) 0.5;
            y2 = (float) 0.6;
            x3 = (float) 0.3;
            y3 = (float) 0.6;
        }
        //8、23
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.45;
            x1 = (float) 0.4;
            y1 = (float) 0.45;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19
    }
    private void setLayout9()
    {
        //9-37 - 24-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.4;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.6;
            x3 = (float) 0.4;
            y3 = (float) 0.6;
        }
        //9-32 - 24-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.4;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.6;
            x3 = (float) 0.4;
            y3 = (float) 0.6;
        }
        //9-27 - 24-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.4;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.6;
            x3 = (float) 0.4;
            y3 = (float) 0.6;
        }
        //9-10 - 24-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.75;
            x3 = (float) 0.4;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.6;
            x3 = (float) 0.4;
            y3 = (float) 0.6;
        }
        //9、24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.45;
            x1 = (float) 0.5;
            y1 = (float) 0.45;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19
    }
    private void setLayout10()
    {
        //10-37 - 25-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.5;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.5;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.6;
            x3 = (float) 0.5;
            y3 = (float) 0.6;
        }
        //10-32 - 25-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.5;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.5;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.6;
            x3 = (float) 0.5;
            y3 = (float) 0.6;
        }
        //10-27 - 25-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.5;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.5;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.6;
            x3 = (float) 0.5;
            y3 = (float) 0.6;
        }
        //10、20
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.45;
            x1 = (float) 0.6;
            y1 = (float) 0.45;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19
    }

    private void setLayout11()
    {
        //11-28 - 21-30
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        //11-15 - 21-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        //11-14 - 21-24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.5;
            y1 = (float) 0.6;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.5;
            y1 = (float) 0.6;
            x2 = (float) 0.5;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.5;
            y1 = (float) 0.6;
            x2 = (float) 0.5;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        //11-13 - 21-23
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.4;
            y1 = (float) 0.6;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.4;
            y1 = (float) 0.6;
            x2 = (float) 0.4;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.4;
            y1 = (float) 0.6;
            x2 = (float) 0.4;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        //11-12 - 21-22
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.3;
            y1 = (float) 0.6;
            x2 = (float) 0.3;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.3;
            y1 = (float) 0.6;
            x2 = (float) 0.3;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.3;
            y1 = (float) 0.6;
            x2 = (float) 0.3;
            y2 = (float) 0.75;
            x3 = (float) 0.001;
            y3 = (float) 0.75;
        }
        //11、21
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.6;
            x1 = (float) 0.15;
            y1 = (float) 0.6;
            x2 = (float) 0.15;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19
    }
    private void setLayout12()
    {
        //12-33 - 21-35
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        //12-28 - 21-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked()  && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        //12-15 - 22-20
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        //12-14 - 22-24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.5;
            y1 = (float) 0.6;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.5;
            y1 = (float) 0.6;
            x2 = (float) 0.5;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.5;
            y1 = (float) 0.6;
            x2 = (float) 0.5;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        //12-13 - 22-23
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.4;
            y1 = (float) 0.6;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.4;
            y1 = (float) 0.6;
            x2 = (float) 0.4;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.4;
            y1 = (float) 0.6;
            x2 = (float) 0.4;
            y2 = (float) 0.75;
            x3 = (float) 0.15;
            y3 = (float) 0.75;
        }
        //12、22
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.6;
            x1 = (float) 0.3;
            y1 = (float) 0.6;
            x2 = (float) 0.3;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19
    }
    private void setLayout13()
    {
        //13-38 - 23-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        //13-33 - 23-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        //13-28 - 23-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        //13-15 - 23-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        //13-14 - 23-24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.5;
            y1 = (float) 0.6;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.5;
            y1 = (float) 0.6;
            x2 = (float) 0.5;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.5;
            y1 = (float) 0.6;
            x2 = (float) 0.5;
            y2 = (float) 0.75;
            x3 = (float) 0.3;
            y3 = (float) 0.75;
        }
        //13、23
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.6;
            x1 = (float) 0.4;
            y1 = (float) 0.6;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19
    }
    private void setLayout14()
    {
        //14-38 - 24-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.4;
            y3 = (float) 0.75;
        }
        //14-33 - 24-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.4;
            y3 = (float) 0.75;
        }
        //14-28 - 24-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.4;
            y3 = (float) 0.75;
        }
        //14-15 - 24-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.75;
            x3 = (float) 0.4;
            y3 = (float) 0.75;
        }
        //14、24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.6;
            x1 = (float) 0.5;
            y1 = (float) 0.6;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19
    }
    private void setLayout15()
    {
        //15-38 - 25-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.5;
            y2 = (float) 0.85;
            x3 = (float) 0.5;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.5;
            y2 = (float) 0.75;
            x3 = (float) 0.5;
            y3 = (float) 0.75;
        }
        //15-33 - 25-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.5;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.5;
            y3 = (float) 0.75;
        }
        //15-28 - 25-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.5;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.5;
            y3 = (float) 0.75;
        }
        //15、25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.6;
            x1 = (float) 0.6;
            y1 = (float) 0.6;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19
    }

    private void setLayout16()
    {
        //16-29 - 21-30
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.75;
            x1 = (float) 0.7;
            y1 = (float) 0.75;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.75;
            x1 = (float) 0.7;
            y1 = (float) 0.75;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        //16-20 - 21-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.75;
            x1 = (float) 0.6;
            y1 = (float) 0.75;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.75;
            x1 = (float) 0.6;
            y1 = (float) 0.75;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        //16-19 - 21-24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.75;
            x1 = (float) 0.5;
            y1 = (float) 0.75;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.75;
            x1 = (float) 0.5;
            y1 = (float) 0.75;
            x2 = (float) 0.5;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        //16-18 - 21-23
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.75;
            x1 = (float) 0.4;
            y1 = (float) 0.75;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.75;
            x1 = (float) 0.4;
            y1 = (float) 0.75;
            x2 = (float) 0.4;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        //16-17 - 21-22
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.75;
            x1 = (float) 0.3;
            y1 = (float) 0.75;
            x2 = (float) 0.3;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.75;
            x1 = (float) 0.3;
            y1 = (float) 0.75;
            x2 = (float) 0.3;
            y2 = (float) 0.85;
            x3 = (float) 0.001;
            y3 = (float) 0.85;
        }
        //16、21
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.75;
            x1 = (float) 0.15;
            y1 = (float) 0.75;
            x2 = (float) 0.15;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19
    }
    private void setLayout17()
    {
        //17-34 - 21-35
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.75;
            x1 = (float) 0.85;
            y1 = (float) 0.75;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.75;
            x1 = (float) 0.85;
            y1 = (float) 0.75;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        //17-29 - 21-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.75;
            x1 = (float) 0.7;
            y1 = (float) 0.75;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.75;
            x1 = (float) 0.7;
            y1 = (float) 0.75;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        //17-20 - 21-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.75;
            x1 = (float) 0.6;
            y1 = (float) 0.75;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.75;
            x1 = (float) 0.6;
            y1 = (float) 0.75;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        //17-19 - 21-24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.75;
            x1 = (float) 0.5;
            y1 = (float) 0.75;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.75;
            x1 = (float) 0.5;
            y1 = (float) 0.75;
            x2 = (float) 0.5;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        //17-18 - 22-23
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.75;
            x1 = (float) 0.4;
            y1 = (float) 0.75;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.75;
            x1 = (float) 0.4;
            y1 = (float) 0.75;
            x2 = (float) 0.4;
            y2 = (float) 0.85;
            x3 = (float) 0.15;
            y3 = (float) 0.85;
        }
        //17、22
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.75;
            x1 = (float) 0.3;
            y1 = (float) 0.75;
            x2 = (float) 0.3;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19
    }
    private void setLayout18()
    {
        //18-39 -23-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.75;
            x1 = (float) 0.999;
            y1 = (float) 0.75;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.75;
            x1 = (float) 0.999;
            y1 = (float) 0.75;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        //18-34 -23-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.75;
            x1 = (float) 0.85;
            y1 = (float) 0.75;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.75;
            x1 = (float) 0.85;
            y1 = (float) 0.75;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        //18-29 -23-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.75;
            x1 = (float) 0.7;
            y1 = (float) 0.75;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.75;
            x1 = (float) 0.7;
            y1 = (float) 0.75;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        //18-20 -23-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.75;
            x1 = (float) 0.6;
            y1 = (float) 0.75;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.75;
            x1 = (float) 0.6;
            y1 = (float) 0.75;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        //18-19 -23-24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.75;
            x1 = (float) 0.5;
            y1 = (float) 0.75;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.75;
            x1 = (float) 0.5;
            y1 = (float) 0.75;
            x2 = (float) 0.5;
            y2 = (float) 0.85;
            x3 = (float) 0.3;
            y3 = (float) 0.85;
        }
        //18、23
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.75;
            x1 = (float) 0.4;
            y1 = (float) 0.75;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19
    }
    private void setLayout19()
    {
        //19-39 - 24-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.75;
            x1 = (float) 0.999;
            y1 = (float) 0.75;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.75;
            x1 = (float) 0.999;
            y1 = (float) 0.75;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        //19-34 - 24-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.75;
            x1 = (float) 0.85;
            y1 = (float) 0.75;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.75;
            x1 = (float) 0.85;
            y1 = (float) 0.75;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        //19-29 - 24-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.75;
            x1 = (float) 0.7;
            y1 = (float) 0.75;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.75;
            x1 = (float) 0.7;
            y1 = (float) 0.75;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        //19-20 - 24-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.75;
            x1 = (float) 0.6;
            y1 = (float) 0.75;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.75;
            x1 = (float) 0.6;
            y1 = (float) 0.75;
            x2 = (float) 0.6;
            y2 = (float) 0.85;
            x3 = (float) 0.4;
            y3 = (float) 0.85;
        }
        //19、24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.75;
            x1 = (float) 0.5;
            y1 = (float) 0.75;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19
    }
    private void setLayout20()
    {
        //20-39 - 25-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.75;
            x1 = (float) 0.999;
            y1 = (float) 0.75;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.75;
            x1 = (float) 0.999;
            y1 = (float) 0.75;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.5;
            y3 = (float) 0.85;
        }
        //20-34 - 25-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.75;
            x1 = (float) 0.85;
            y1 = (float) 0.75;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.75;
            x1 = (float) 0.85;
            y1 = (float) 0.75;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.5;
            y3 = (float) 0.85;
        }
        //20-29 - 25-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.75;
            x1 = (float) 0.7;
            y1 = (float) 0.75;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.75;
            x1 = (float) 0.7;
            y1 = (float) 0.75;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.5;
            y3 = (float) 0.85;
        }
        //20、 25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.75;
            x1 = (float) 0.6;
            y1 = (float) 0.75;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        // TODO: 2017/7/19
    }

    private void setLayout21()
    {
        //21-30
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.85;
            x1 = (float) 0.7;
            y1 = (float) 0.85;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        //21-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.85;
            x1 = (float) 0.6;
            y1 = (float) 0.85;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        //21-24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.85;
            x1 = (float) 0.5;
            y1 = (float) 0.85;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        //21-23
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.85;
            x1 = (float) 0.4;
            y1 = (float) 0.85;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        //21-22
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.85;
            x1 = (float) 0.3;
            y1 = (float) 0.85;
            x2 = (float) 0.3;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
        //21
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.001;
            y0 = (float) 0.85;
            x1 = (float) 0.15;
            y1 = (float) 0.85;
            x2 = (float) 0.15;
            y2 = (float) 0.999;
            x3 = (float) 0.001;
            y3 = (float) 0.999;
        }
    }
    private void setLayout22()
    {
        //22-35
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.85;
            x1 = (float) 0.85;
            y1 = (float) 0.85;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        //22-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.85;
            x1 = (float) 0.7;
            y1 = (float) 0.85;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        //22-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.85;
            x1 = (float) 0.6;
            y1 = (float) 0.85;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        //22-24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.85;
            x1 = (float) 0.5;
            y1 = (float) 0.85;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        //22-23
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.85;
            x1 = (float) 0.4;
            y1 = (float) 0.85;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
        //22
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.15;
            y0 = (float) 0.85;
            x1 = (float) 0.3;
            y1 = (float) 0.85;
            x2 = (float) 0.3;
            y2 = (float) 0.999;
            x3 = (float) 0.15;
            y3 = (float) 0.999;
        }
    }
    private void setLayout23()
    {
        //23-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.85;
            x1 = (float) 0.999;
            y1 = (float) 0.85;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        //23-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.85;
            x1 = (float) 0.85;
            y1 = (float) 0.85;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        //23-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.85;
            x1 = (float) 0.7;
            y1 = (float) 0.85;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        //23-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.85;
            x1 = (float) 0.6;
            y1 = (float) 0.85;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        //23-24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.85;
            x1 = (float) 0.5;
            y1 = (float) 0.85;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
        //23
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.3;
            y0 = (float) 0.85;
            x1 = (float) 0.4;
            y1 = (float) 0.85;
            x2 = (float) 0.4;
            y2 = (float) 0.999;
            x3 = (float) 0.3;
            y3 = (float) 0.999;
        }
    }
    private void setLayout24()
    {
        //24-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.85;
            x1 = (float) 0.999;
            y1 = (float) 0.85;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        //24-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.85;
            x1 = (float) 0.85;
            y1 = (float) 0.85;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        //24-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.85;
            x1 = (float) 0.7;
            y1 = (float) 0.85;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        //24-25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.85;
            x1 = (float) 0.6;
            y1 = (float) 0.85;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
        //24
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.4;
            y0 = (float) 0.85;
            x1 = (float) 0.5;
            y1 = (float) 0.85;
            x2 = (float) 0.5;
            y2 = (float) 0.999;
            x3 = (float) 0.4;
            y3 = (float) 0.999;
        }
    }
    private void setLayout25()
    {
        //25-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.85;
            x1 = (float) 0.999;
            y1 = (float) 0.85;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        //25-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.85;
            x1 = (float) 0.85;
            y1 = (float) 0.85;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        //25-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.85;
            x1 = (float) 0.7;
            y1 = (float) 0.85;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
        //25
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.5;
            y0 = (float) 0.85;
            x1 = (float) 0.6;
            y1 = (float) 0.85;
            x2 = (float) 0.6;
            y2 = (float) 0.999;
            x3 = (float) 0.5;
            y3 = (float) 0.999;
        }
    }

    private void setLayout26()
    {
        //26-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.6;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.6;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.6;
            x3 = (float) 0.6;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.45;
            x3 = (float) 0.6;
            y3 = (float) 0.45;
        }
        //26-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.6;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.6;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.6;
            x3 = (float) 0.6;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.45;
            x3 = (float) 0.6;
            y3 = (float) 0.45;
        }
        //26-30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.6;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.6;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.6;
            x3 = (float) 0.6;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.3;
            x1 = (float) 0.7;
            y1 = (float) 0.3;
            x2 = (float) 0.7;
            y2 = (float) 0.45;
            x3 = (float) 0.6;
            y3 = (float) 0.45;
        }
    }
    private void setLayout27()
    {
        //27-37 - 30-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.6;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.6;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.6;
            x3 = (float) 0.6;
            y3 = (float) 0.6;
        }
        //27-32 - 30-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.6;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.6;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.6;
            x3 = (float) 0.6;
            y3 = (float) 0.6;
        }
        //27- 30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.6;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.6;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.45;
            x1 = (float) 0.7;
            y1 = (float) 0.45;
            x2 = (float) 0.7;
            y2 = (float) 0.6;
            x3 = (float) 0.6;
            y3 = (float) 0.6;
        }
    }
    private void setLayout28()
    {
        //28-38 - 30-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.6;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.6;
            y3 = (float) 0.75;
        }
        //28-33 - 30-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.6;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.75;
            x3 = (float) 0.6;
            y3 = (float) 0.75;
        }
        //28 - 30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.6;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.6;
            x1 = (float) 0.7;
            y1 = (float) 0.6;
            x2 = (float) 0.7;
            y2 = (float) 0.75;
            x3 = (float) 0.6;
            y3 = (float) 0.75;
        }
    }
    private void setLayout29()
    {
        //29-39 - 30-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.75;
            x1 = (float) 0.999;
            y1 = (float) 0.75;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.75;
            x1 = (float) 0.999;
            y1 = (float) 0.75;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.6;
            y3 = (float) 0.85;
        }
        //29-34 - 30-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.75;
            x1 = (float) 0.85;
            y1 = (float) 0.75;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.75;
            x1 = (float) 0.85;
            y1 = (float) 0.75;
            x2 = (float) 0.85;
            y2 = (float) 0.85;
            x3 = (float) 0.6;
            y3 = (float) 0.85;
        }
        //29- 30
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.75;
            x1 = (float) 0.7;
            y1 = (float) 0.75;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.75;
            x1 = (float) 0.7;
            y1 = (float) 0.75;
            x2 = (float) 0.7;
            y2 = (float) 0.85;
            x3 = (float) 0.6;
            y3 = (float) 0.85;
        }
    }
    private void setLayout30()
    {
        //30-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.85;
            x1 = (float) 0.999;
            y1 = (float) 0.85;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.85;
            x1 = (float) 0.85;
            y1 = (float) 0.85;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.6;
            y0 = (float) 0.85;
            x1 = (float) 0.7;
            y1 = (float) 0.85;
            x2 = (float) 0.7;
            y2 = (float) 0.999;
            x3 = (float) 0.6;
            y3 = (float) 0.999;
        }
    }

    private void setLayout31()
    {
        //31-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.7;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.7;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.7;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.6;
            x3 = (float) 0.7;
            y3 = (float) 0.6;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.3;
            x1 = (float) 0.999;
            y1 = (float) 0.3;
            x2 = (float) 0.999;
            y2 = (float) 0.45;
            x3 = (float) 0.7;
            y3 = (float) 0.45;
        }
        //31-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.3;
            x1 = (float) 0.85;
            y1 = (float) 0.3;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.7;
            y3 = (float) 0.999;
        }
    }
    private void setLayout32()
    {
        //32-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.7;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.7;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.7;
            y3 = (float) 0.75;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.45;
            x1 = (float) 0.999;
            y1 = (float) 0.45;
            x2 = (float) 0.999;
            y2 = (float) 0.6;
            x3 = (float) 0.7;
            y3 = (float) 0.6;
        }
        //32-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.45;
            x1 = (float) 0.85;
            y1 = (float) 0.45;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.7;
            y3 = (float) 0.999;
        }
    }
    private void setLayout33()
    {
        //33-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.7;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.7;
            y3 = (float) 0.85;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && !layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.6;
            x1 = (float) 0.999;
            y1 = (float) 0.6;
            x2 = (float) 0.999;
            y2 = (float) 0.75;
            x3 = (float) 0.7;
            y3 = (float) 0.75;
        }
        //33-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.6;
            x1 = (float) 0.85;
            y1 = (float) 0.6;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.7;
            y3 = (float) 0.999;
        }
    }
    private void setLayout34()
    {
        //34-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.75;
            x1 = (float) 0.999;
            y1 = (float) 0.75;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.7;
            y3 = (float) 0.999;
        }
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && !layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.75;
            x1 = (float) 0.999;
            y1 = (float) 0.75;
            x2 = (float) 0.999;
            y2 = (float) 0.85;
            x3 = (float) 0.7;
            y3 = (float) 0.85;
        }
        //34-35
        else if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && !layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.75;
            x1 = (float) 0.85;
            y1 = (float) 0.75;
            x2 = (float) 0.85;
            y2 = (float) 0.999;
            x3 = (float) 0.7;
            y3 = (float) 0.999;
        }
    }
    private void setLayout35()
    {
        //35-40
        if(!layout1.isChecked() && !layout2.isChecked() && !layout3.isChecked() && !layout4.isChecked() && !layout5.isChecked()
                && !layout6.isChecked() && !layout7.isChecked() && !layout8.isChecked() && !layout9.isChecked() && !layout10.isChecked()
                && !layout11.isChecked() && !layout12.isChecked() && !layout13.isChecked() && !layout14.isChecked() && !layout15.isChecked()
                && !layout16.isChecked() && !layout17.isChecked() && !layout18.isChecked() && !layout19.isChecked() && !layout20.isChecked()
                && !layout21.isChecked() && !layout22.isChecked() && !layout23.isChecked() && !layout24.isChecked() && !layout25.isChecked()
                && !layout26.isChecked() && !layout27.isChecked() && !layout28.isChecked() && !layout29.isChecked() && !layout30.isChecked()
                && !layout31.isChecked() && !layout32.isChecked() && !layout33.isChecked() && !layout34.isChecked() && layout35.isChecked()
                && !layout36.isChecked() && !layout37.isChecked() && !layout38.isChecked() && !layout39.isChecked() && layout40.isChecked())
        {
            x0 = (float) 0.7;
            y0 = (float) 0.85;
            x1 = (float) 0.999;
            y1 = (float) 0.85;
            x2 = (float) 0.999;
            y2 = (float) 0.999;
            x3 = (float) 0.7;
            y3 = (float) 0.999;
        }
    }

    private void setBrand(int iUserID)
    {
        struTriggerCfg = JNATest.struTriggerCfg;
        if (struTriggerCfg == null)
        {
            Util.show_Toast(IntelligentFragment.this,"请进行参数配置",1000);
        }
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].struPlateRecog[0].uRegion.struPolygon.struPos[0].fX = x0;
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].struPlateRecog[0].uRegion.struPolygon.struPos[0].fY= y0;
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].struPlateRecog[0].uRegion.struPolygon.struPos[1].fX = x1;
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].struPlateRecog[0].uRegion.struPolygon.struPos[1].fY= y1;
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].struPlateRecog[0].uRegion.struPolygon.struPos[2].fX = x2;
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].struPlateRecog[0].uRegion.struPolygon.struPos[2].fY= y2;
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].struPlateRecog[0].uRegion.struPolygon.struPos[3].fX = x3;
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].struPlateRecog[0].uRegion.struPolygon.struPos[3].fY= y3;
    }


    private void SetCanshu(int iUserID)
    {
        SetNum();
        if (x0!=0 && y0!=0 && x1!=0 && y1!=0 && x2!=0 && y2!=0 && x3!=0 && y3!=0)
        {
            setBrand(iUserID);
            struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struRadar.byRadarType = 3;
            struTriggerCfg.write();
            boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG, 0x8, struTriggerCfg.getPointer(), struTriggerCfg.size());
            if(!bRet)
            {
                progressDialog.dismiss();
                System.out.println("HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
                Util.show_Toast(IntelligentFragment.this,"牌识区区域设置错误",1000);
            }
            else
            {
                progressDialog.dismiss();
                System.out.println("HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG succ!");
                Util.show_Toast(IntelligentFragment.this,"牌识区设置成功",1000);

            }
        }else {
            Util.show_Toast(IntelligentFragment.this,"参数设置错误",1000);
        }
    }

    private void SettingClear()
    {
        layout1.setChecked(false);
        layout1.setChecked(false);
        layout2.setChecked(false);
        layout3.setChecked(false);
        layout4.setChecked(false);
        layout5.setChecked(false);
        layout6.setChecked(false);
        layout7.setChecked(false);
        layout8.setChecked(false);
        layout9.setChecked(false);
        layout10.setChecked(false);
        layout11.setChecked(false);
        layout12.setChecked(false);
        layout13.setChecked(false);
        layout14.setChecked(false);
        layout15.setChecked(false);
        layout16.setChecked(false);
        layout17.setChecked(false);
        layout18.setChecked(false);
        layout19.setChecked(false);
        layout20.setChecked(false);

        layout21.setChecked(false);
        layout21.setChecked(false);
        layout22.setChecked(false);
        layout23.setChecked(false);
        layout24.setChecked(false);
        layout25.setChecked(false);
        layout26.setChecked(false);
        layout27.setChecked(false);
        layout28.setChecked(false);
        layout29.setChecked(false);
        layout30.setChecked(false);
        layout31.setChecked(false);
        layout32.setChecked(false);
        layout33.setChecked(false);
        layout34.setChecked(false);
        layout35.setChecked(false);
        layout36.setChecked(false);
        layout37.setChecked(false);
        layout38.setChecked(false);
        layout39.setChecked(false);
        layout40.setChecked(false);
    }


    Handler mhandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                {
                    Toast.makeText(IntelligentFragment.this,"牌识区设置成功",Toast.LENGTH_LONG).show();
                }
                break;
                case 1:
                {
                    Toast.makeText(IntelligentFragment.this,"参数设置错误",Toast.LENGTH_LONG).show();
                }
                break;
                case 2:
                {
                    Toast.makeText(IntelligentFragment.this,"牌识区区域设置过大",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;
            }
        };
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSinglePreview();
        intelligent_Rs485.getHolder().getSurface().release();
        intelligent_Video.getHolder().getSurface().release();
        finish();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        PaiS_JC.setSelection(MyPreference.getInstance(IntelligentFragment.this).getPAISHIJC());

        int radio_two = MyPreference.getInstance(IntelligentFragment.this).getRADIOTWO();
        int radio_one = MyPreference.getInstance(IntelligentFragment.this).getRADIOONE();
        intelligent_RadioTwo.check(radio_two);
        intelligent_RadioOne.check(radio_one);
        if (radio_two == 6)
        {
            intelligent_Radio3.setChecked(true);
        }
        if (radio_one == 4)
        {
            intelligent_Radio12.setChecked(true);
        }
        PaiS_check.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getPAISHI());
        layout1.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox1());
        layout2.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox2());
        layout3.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox3());
        layout4.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox4());
        layout5.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox5());
        layout6.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox6());
        layout7.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox7());
        layout8.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox8());
        layout9.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox9());
        layout10.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox10());

        layout11.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox11());
        layout12.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox12());
        layout13.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox13());
        layout14.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox14());
        layout15.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox15());
        layout16.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox16());
        layout17.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox17());
        layout18.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox18());
        layout19.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox19());
        layout20.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox20());

        layout21.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox21());
        layout22.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox22());
        layout23.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox23());
        layout24.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox24());
        layout25.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox25());
        layout26.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox26());
        layout27.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox27());
        layout28.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox28());
        layout29.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox29());
        layout30.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox30());

        layout31.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox31());
        layout32.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox32());
        layout33.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox33());
        layout34.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox34());
        layout35.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox35());
        layout36.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox36());
        layout37.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox37());
        layout38.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox38());
        layout39.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox39());
        layout40.setChecked(MyPreference.getInstance(IntelligentFragment.this)
                .getCheckBox40());
    }

}