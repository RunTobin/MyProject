package com.huang.bchtsystem.View.Fragment;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.MyPreference;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.jna.HCNetSDKByJNA;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;
import com.huang.bchtsystem.jna.video.JNATest;
import com.huang.bchtsystem.rader.ComBean;
import com.huang.bchtsystem.rader.Devices;
import com.huang.bchtsystem.rader.Rader;
import com.huang.bchtsystem.rader.RaderCmd;
import com.huang.bchtsystem.rader.SerialHelper;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017/3/13.
 * 雷达设置界面
 */

public class RadarFragment extends TabActivity implements View.OnClickListener{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.textview)
    TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;

    @InjectView(R.id.layout_radar)
    protected LinearLayout layout_radar;

    /**
     * 抓拍设置
     */
    @InjectView(R.id.radar_LoadDirectionType)
    Spinner radar_LoadDirectionType;  //车道方向类型
    @InjectView(R.id.radar_CaptureTimes)
    Spinner radar_CaptureTimes;  //抓拍次数
    @InjectView(R.id.radar_smallEt1)
    EditText radar_smallEt1;  //小车限高速
    @InjectView(R.id.radar_BigEt1)
    EditText radar_BigEt1;  //大车限高速
    @InjectView(R.id.radar_smallEt2)
    EditText radar_smallEt2;  //小车限低速
    @InjectView(R.id.radar_BigEt2)
    EditText radar_BigEt2;  //大车限低速
    @InjectView(R.id.radar_smallEt3)
    EditText radar_smallEt3;  //小车标志限速
    @InjectView(R.id.radar_BigEt3)
    EditText radar_BigEt3;  //大车标志限速
    @InjectView(R.id.radar_LoadSave)
    Button radar_LoadSave;//抓拍保存

    /**
     * 雷达设置
     */
    @InjectView(R.id.radar_Speed)
    EditText radar_Speed; //道路限速
    @InjectView(R.id.radar_hardThreshold)
    EditText radar_hardThreshold; //硬门限
    @InjectView(R.id.radar_Angle)
    EditText radar_Andle;  //角度
    @InjectView(R.id.radar_threshold)
    EditText radar_threshold; //门限系数
    @InjectView(R.id.radar_raderHeight)
    EditText radar_raderHeight;  //架设高度
    @InjectView(R.id.radar_leftSpeed)
    EditText radar_leftSpeed; //余量
    @InjectView(R.id.radar_adjustedLaneCoefficient)
    EditText radar_adjustedLaneCoefficient; //车辆校准系数
    @InjectView(R.id.radar_laneWidth)
    EditText radar_laneWidth; //车道宽度
    @InjectView(R.id.radar_Interval)
    EditText radar_Interval; // 两车车距差值
    @InjectView(R.id.radar_distance)
    EditText radar_distance; //雷达到车道距离

    //触发模式
    @InjectView(R.id.radar_butModeHead)
    RadioButton radar_butModeHead;
    @InjectView(R.id.radar_butModeFoot)
    RadioButton radar_butModeFoot;

    //路况
    @InjectView(R.id.radar_city)
    RadioButton radar_city;
    @InjectView(R.id.radar_hightway)
    RadioButton radar_hightway;

    @InjectView(R.id.radar_Save)
    Button radar_Save;

    @InjectView(R.id.radar_Meas)
    Button radar_Meas;


    private static int m_iLogID = -1; // return by NET_DVR_Login_v30
    private int m_iChanNum ; // channel number
    HCNetSDKByJNA.NET_ITC_RSTRIGGERCFG struTriggerCfg = null;
    private Dialog progressDialog;
    private TabHost tabHost ;
    private TabWidget tabWidget ;
    private static int myMenuSettingTag = 0;
    private String SelectedNameCap = null;
    private int SelectedDirectionType = 0;
    RaderCmd.TriggerMode triggerMode ;
    RaderCmd.RoadMode roadMode ;
    float hardThreshold,raderHeight,adjustedLaneCoefficient,laneWidth,Interval,distance;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radarfragment);
        ButterKnife.inject(this);
        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("抓拍设置");
        textview.setVisibility(View.GONE);
        initView();
        m_iLogID = getIntent().getExtras().getInt("m_iLogID");
        m_iChanNum = getIntent().getExtras().getInt("m_iChanNum");
    }
    private void initView()
    {
        tabHost = getTabHost();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1").setIndicator("抓拍设置").setContent(R.id.radar_tab1);
        tabHost.addTab(tab1);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2").setIndicator("雷达设置").setContent(R.id.radar_tab2);
        tabHost.addTab(tab2);
        tabWidget = tabHost.getTabWidget();
        for (int i = 0 ; i<tabWidget.getChildCount();i++){
            tabWidget.getChildAt(i).getLayoutParams().height = 60;
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);

            tv.setTextSize(20);
            tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
            tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tabwidget_selector);
        }
        init();
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
        layout_radar.setOnClickListener(this);
        radar_LoadSave.setOnClickListener(this);
        radar_Save.setOnClickListener(this);
        radar_Meas.setOnClickListener(this);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.radarDirectionType,android.R.layout.simple_spinner_dropdown_item);
        radar_LoadDirectionType.setAdapter(adapter1);
        radar_LoadDirectionType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyPreference.getInstance(RadarFragment.this).SetDIRECTIONTYPE(position);
                SelectedDirectionType =position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.capture,android.R.layout.simple_spinner_dropdown_item);
        radar_CaptureTimes.setAdapter(adapter2);
        radar_CaptureTimes.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyPreference.getInstance(RadarFragment.this).SetPAIS_CAPTURE(position);
                SelectedNameCap = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initData()
    {
        //小车限高速
        int bySpeedLimit =  struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].bySpeedLimit;
        //小车标志限速
        int bySignSpeed = struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].bySignSpeed;
        //小车限低速值
        int byLowSpeedLimit = struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].byLowSpeedLimit;
        //大车标志限速
        int byCartSignSpeed = struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].byCartSignSpeed;
        //大车限高速值
        int byCartSpeedLimit = struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].byCartSpeedLimit;
        //大车限低速值
        int byBigCarLowSpeedLimit  = struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].byBigCarLowSpeedLimit;
        //抓拍次数
        int bySnapTimes = struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].bySnapTimes;
        //方向类型
        int byRelaLaneDirectionType = struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].byRelaLaneDirectionType;



        MyPreference.getInstance(RadarFragment.this).SetBigCarLimit(String.valueOf(byCartSpeedLimit));
        MyPreference.getInstance(RadarFragment.this).SetBigCarLowLimit(String.valueOf(byBigCarLowSpeedLimit));
        MyPreference.getInstance(RadarFragment.this).SetSmallCarLowLimit(String.valueOf(byLowSpeedLimit));
        MyPreference.getInstance(RadarFragment.this).SetCarLimit(String.valueOf(bySpeedLimit));
        MyPreference.getInstance(RadarFragment.this).SetbySignSpeed(String.valueOf(bySignSpeed));
        MyPreference.getInstance(RadarFragment.this).SetbyCartSignSpeed(String.valueOf(byCartSignSpeed));
    }

    /**
     * 设置抓拍参数
     */
    private void Test_SetCanshu(int iUserID)
    {
        struTriggerCfg = JNATest.struTriggerCfg;
        System.out.println("SelectedDirectionType==="+SelectedDirectionType+"  ==="+ (byte)SelectedDirectionType);
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].byRelaLaneDirectionType = (byte)SelectedDirectionType;
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].bySnapTimes = (byte)Integer.parseInt(SelectedNameCap);

        //大车限高速值
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].byCartSpeedLimit = (byte) Integer.parseInt(radar_BigEt1.getText().toString());
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].byBigCarLowSpeedLimit = (byte) Integer.parseInt(radar_BigEt2.getText().toString()) ;
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].byCartSignSpeed = (byte) Integer.parseInt(radar_BigEt3.getText().toString()) ;
        //小车限高速
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].bySpeedLimit = (byte) Integer.parseInt(radar_smallEt1.getText().toString()) ;
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].byLowSpeedLimit =(byte) Integer.parseInt(radar_smallEt2.getText().toString()) ;
        struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].bySignSpeed =(byte) Integer.parseInt(radar_smallEt3.getText().toString());

        struTriggerCfg.write();
        boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG, 0x8, struTriggerCfg.getPointer(), struTriggerCfg.size());
        if(!bRet)
        {
            System.out.println("HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
            handler.sendEmptyMessage(3);
        }
        else
        {
            System.out.println("HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG succ!");
            handler.sendEmptyMessage(0);
        }
        initData();
    }

    private void SetRadarData()
    {
        progressDialog = Util.createLoadingDialog(this,"正在设置中...");
        progressDialog.show();
        //新建线程   
        new Thread() {
            @Override
            public void run() {
                try{
                    Test_SetCanshu(m_iLogID);
                }catch ( Exception e){
                    e.fillInStackTrace();
                }finally{
                    progressDialog.dismiss();
                }
            }
        }.start();
    }

    Thread thread;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_radar:
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
            case R.id.layout_Title:
                this.finish();
                break;
            case R.id.radar_LoadSave:
                if (m_iLogID > -1){
                    if (mode == 0)
                    {
                        PanJudge();
                    }else {
                        Util.show_Toast(RadarFragment.this,"请切换雷达模式",1000);
                    }

                }
                break;
            case R.id.radar_Save:
                thread = new TaskThreadGet();
                thread.start();
                break;
            case R.id.radar_Meas:
                showMeas("模拟测试");
                break;
            default:
                break;
        }
    }

    private void PanJudge()
    {
        if (!TextUtils.isEmpty(radar_BigEt3.getText().toString()) && !TextUtils.isEmpty(radar_smallEt3.getText().toString())
                && !TextUtils.isEmpty(radar_BigEt1.getText().toString()) && !TextUtils.isEmpty(radar_smallEt1.getText().toString())
                && !TextUtils.isEmpty(radar_BigEt2.getText().toString()) && !TextUtils.isEmpty(radar_smallEt2.getText().toString())){
            if (Integer.parseInt(radar_smallEt2.getText().toString()) < Integer.parseInt(radar_smallEt3.getText().toString())&&
                    Integer.parseInt(radar_BigEt2.getText().toString()) < Integer.parseInt(radar_smallEt3.getText().toString())&&
                    Integer.parseInt(radar_smallEt2.getText().toString()) < Integer.parseInt(radar_BigEt3.getText().toString()) &&
                    Integer.parseInt(radar_BigEt2.getText().toString()) < Integer.parseInt(radar_BigEt3.getText().toString()) &&
                    Integer.parseInt(radar_BigEt3.getText().toString()) < Integer.parseInt(radar_BigEt1.getText().toString()) &&
                    Integer.parseInt(radar_smallEt3.getText().toString()) < Integer.parseInt(radar_smallEt1.getText().toString())){
                SetRadarData();
            }else {
                handler.sendEmptyMessage(3);
            }
        }
    }

    private int mode ;
    @Override
    protected void onResume() {
        super.onResume();
        mode = MyPreference.getInstance(RadarFragment.this).getPAISHIJC();
        radar_LoadDirectionType.setSelection(MyPreference.getInstance(RadarFragment.this).getDIRECTIONTYPE());
        radar_CaptureTimes.setSelection(MyPreference.getInstance(RadarFragment.this).getPAIS_CAPTURE());
        radar_BigEt1.setText(MyPreference.getInstance(RadarFragment.this).getBigCarLimit());
        radar_BigEt2.setText(MyPreference.getInstance(RadarFragment.this).getBigCarLowLimit());
        radar_smallEt2.setText(MyPreference.getInstance(RadarFragment.this).getSmallCarLowLimit());
        radar_smallEt1.setText(MyPreference.getInstance(RadarFragment.this).getCarLimit());
        radar_smallEt3.setText(MyPreference.getInstance(RadarFragment.this).getbySignSpeed());
        radar_BigEt3.setText(MyPreference.getInstance(RadarFragment.this).getbyCartSignSpeed());


        radar_Andle.setText(MyPreference.getInstance(RadarFragment.this).getAnglenum());
        radar_Speed.setText(MyPreference.getInstance(RadarFragment.this).getLimit());
        radar_hardThreshold.setText(MyPreference.getInstance(RadarFragment.this).gethardThreshold());
        radar_threshold.setText(MyPreference.getInstance(RadarFragment.this).getthreshold());
        radar_raderHeight.setText(MyPreference.getInstance(RadarFragment.this).getraderHeight());
        radar_leftSpeed.setText(MyPreference.getInstance(RadarFragment.this).getleftSpeed());
        radar_adjustedLaneCoefficient.setText(MyPreference.getInstance(RadarFragment.this).getadjustedLane());
        radar_laneWidth.setText(MyPreference.getInstance(RadarFragment.this).getlaneWidth());
        radar_Interval.setText(MyPreference.getInstance(RadarFragment.this).getInterval());
        radar_distance.setText(MyPreference.getInstance(RadarFragment.this).getdistance());

        String TouchMode =  MyPreference.getInstance(RadarFragment.this).getMode();
        if (TouchMode.equals("0")){
            radar_butModeHead.setChecked(true);
        }else {
            radar_butModeFoot.setChecked(true);
        }
        String RoadMode = MyPreference.getInstance(RadarFragment.this).getRoadMode();
        if (RoadMode.equals("0")){
            radar_city.setChecked(true);
        }else {
            radar_hightway.setChecked(true);
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(RadarFragment.this,"抓拍参数设置成功",Toast.LENGTH_SHORT).show();
                   break;
                case 1:
                    Toast.makeText(RadarFragment.this,"雷达参数设置失败",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(RadarFragment.this,"雷达参数设置成功",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(RadarFragment.this,"抓拍参数设置失败",Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(RadarFragment.this,"雷达没有连接成功",Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(RadarFragment.this,"雷达连接成功",Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    showDialog("限速值不能大于255!");
                    break;
                case 7:
                    showDialog("角度值不能大于255!");
                    break;
                default:
                    break;
            }
        };
    };
    class TaskThreadGet extends Thread {
        public void run() {
            System.out.println("-->做一些耗时的任务");
            getRadar();
        };
    };
    /**
     * final boolean succ = Rader.getInstance().open(Devices.getAbsolutePath(port),Integer.decode(baudrate).intValue());
     * if( succ )
     * {
     *  Rader.getInstance().getSpeedResponse();
     *  int err = Rader.getInstance().setParameter((short) 8,(byte)45,(byte) 01);
     *  Rader.getInstance().close();
     * }
     */
    private void getRadar()
    {
      if (!TextUtils.isEmpty(radar_Andle.getText().toString())&& !TextUtils.isEmpty(radar_Speed.getText().toString())){
          float speed = Float.parseFloat(radar_Speed.getText().toString());
          float angle = Float.parseFloat(radar_Andle.getText().toString());
          float threshold = Float.parseFloat(radar_threshold.getText().toString());
          float leftSpeed = Float.parseFloat(radar_leftSpeed.getText().toString());
          try{
               hardThreshold =  Float.parseFloat(radar_hardThreshold.getText().toString());
               raderHeight =  Float.parseFloat(radar_raderHeight.getText().toString());
               adjustedLaneCoefficient =  Float.parseFloat(radar_adjustedLaneCoefficient.getText().toString());
               laneWidth =  Float.parseFloat(radar_laneWidth.getText().toString());
               Interval = Float.parseFloat(radar_Interval.getText().toString());
               distance =  Float.parseFloat(radar_distance.getText().toString());
          }catch (Exception e){
              e.printStackTrace();
          }
          if (radar_butModeHead.isChecked()){
              triggerMode =RaderCmd.TriggerMode.TRIGGER_HEADER;
          }else if (radar_butModeFoot.isChecked()){
              triggerMode=RaderCmd.TriggerMode.TRIGGER_TAIL;
          }
          if (radar_city.isChecked()) {
              roadMode =RaderCmd.RoadMode.ROAD_CITY;
          }else if(radar_hightway.isChecked()){
              roadMode =RaderCmd.RoadMode.ROAD_HIGHWAY;
          }
          if (speed > 255 ){
              handler.sendEmptyMessage(6);
          }else if (Float.parseFloat(radar_Andle.getText().toString()) > 255){
              handler.sendEmptyMessage(7);
          }else {
              final boolean succ = Rader.getInstance().open(Devices.getAbsolutePath("/dev/ttyS2"),9600);
              if( succ )
              {
                  int err = 0;
                  RaderCmd.ParameterGetResponse parameterGetResponse = Rader.getInstance().getParameter();
                  RaderCmd.ParameterSetCmd parameterSetCmd = new RaderCmd().new ParameterSetCmd();
                  RaderCmd.Parameter parameter = parameterGetResponse.getParameter();
                  try
                  {
                      parameter.setMaxSpeed(speed);
                      parameter.setHardThreshold(hardThreshold);
                      parameter.setAngle(angle);
                      parameter.setThreshold(threshold);
                      parameter.setRaderHeight(raderHeight);
                      parameter.setLeftSpeed(leftSpeed);
                      parameter.setAdjustedLaneCoefficient(adjustedLaneCoefficient);
                      parameter.setLaneWidth(laneWidth);
                      parameter.setInterval(Interval);
                      parameter.setDistance(distance);
                      parameter.setRoadMode(roadMode);
                      parameter.setTriggerMode(triggerMode);
                  }
                  catch (Exception e)
                  {
                        e.printStackTrace();
                  }

                  parameterSetCmd.setParameter(parameter);
                  err = Rader.getInstance().setParameter(parameterSetCmd);
                  System.out.println(" succ!"+ err);
                  handler.sendEmptyMessage(2);
                  MyPreference.getInstance(RadarFragment.this).SetLimit(String.valueOf(speed));
                  MyPreference.getInstance(RadarFragment.this).SetAnglenum(String.valueOf(angle));
                  MyPreference.getInstance(RadarFragment.this).SethardThreshold(String.valueOf(hardThreshold));
                  MyPreference.getInstance(RadarFragment.this).Setthreshold(String.valueOf(threshold));
                  MyPreference.getInstance(RadarFragment.this).SetraderHeight(String.valueOf(raderHeight));
                  MyPreference.getInstance(RadarFragment.this).SetleftSpeed(String.valueOf(leftSpeed));
                  MyPreference.getInstance(RadarFragment.this).SetadjustedLane(String.valueOf(adjustedLaneCoefficient));
                  MyPreference.getInstance(RadarFragment.this).SetlaneWidth(String.valueOf(laneWidth));
                  MyPreference.getInstance(RadarFragment.this).SetInterval(String.valueOf(Interval));
                  MyPreference.getInstance(RadarFragment.this).Setdistance(String.valueOf(distance));
                  if (roadMode == RaderCmd.RoadMode.ROAD_CITY){
                      MyPreference.getInstance(RadarFragment.this).SetRoadMode("0");
                  }else {
                      MyPreference.getInstance(RadarFragment.this).SetRoadMode("1");
                  }
                  if (triggerMode == RaderCmd.TriggerMode.TRIGGER_HEADER){
                      MyPreference.getInstance(RadarFragment.this).SetMode("0");
                  }else {
                      MyPreference.getInstance(RadarFragment.this).SetMode("1");
                  }
                  Rader.getInstance().close();
              }else {
                  handler.sendEmptyMessage(4);
              }
          }
      }
    }
    private void showDialog(String str){
        // 弹出的对话框
        new AlertDialog.Builder(RadarFragment.this)
                .setTitle("警告")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setMessage(str)
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                          dialoginterface.dismiss();
                            }
                        })
                .show();
    }
    private void showMeas(String str){
        final boolean succ = Rader.getInstance().open(Devices.getAbsolutePath("/dev/ttyS2"),9600);
        if( succ )
        {
            Rader.getInstance().enterMeasure();
            new AlertDialog.Builder(RadarFragment.this)
                    .setTitle("计量模式")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage(str)
                    .setCancelable(false)
                    .setPositiveButton("退出",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    Rader.getInstance().exitMeasure();
                                    Rader.getInstance().close();
                                    dialoginterface.dismiss();
                                }
                            })
                    .show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        CloseComPort(ComA);
//        finish();
    }
}
