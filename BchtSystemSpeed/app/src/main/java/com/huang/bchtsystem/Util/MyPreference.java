package com.huang.bchtsystem.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2017/3/14.
 * 数据存储管理管理
 */

public class MyPreference {
    private static MyPreference preference = null;
    private SharedPreferences sharedPreference;
    private String packageName = "";
    public static Boolean isBack =false;
    public static Boolean isLogin =false;
    private static final String IS_FIRST = "First";

    private static final String LOGIN_NAME = "loginName"; //登录名
    private static final String PASSWORD = "password";  //密码
    private static final String IS_SAVE_PWD = "isSavePwd"; //是否保留密码
    private static final  String IPADDRESS = "ipAddress"; //设备IP地址
    private static final  String   LAYOUT1 = "layout1";
    private static final  String   LAYOUT2 = "layout2";
    private static final  String   LAYOUT3 = "layout3";
    private static final  String   LAYOUT4 = "layout4";
    private static final  String   LAYOUT5 = "layout5";

    private static final  String   LAYOUT6 = "layout6";
    private static final  String   LAYOUT7 = "layout7";
    private static final  String   LAYOUT8 = "layout8";
    private static final  String   LAYOUT9 = "layout9";
    private static final  String   LAYOUT10 = "layout10";

    private static final  String   LAYOUT11 = "layout11";
    private static final  String   LAYOUT12 = "layout12";
    private static final  String   LAYOUT13 = "layout13";
    private static final  String   LAYOUT14 = "layout14";
    private static final  String   LAYOUT15 = "layout15";

    private static final  String   LAYOUT16 = "layout16";
    private static final  String   LAYOUT17 = "layout17";
    private static final  String   LAYOUT18 = "layout18";
    private static final  String   LAYOUT19 = "layout19";
    private static final  String   LAYOUT20 = "layout20";

    private static final  String   LAYOUT21 = "layout21";
    private static final  String   LAYOUT22 = "layout22";
    private static final  String   LAYOUT23 = "layout23";
    private static final  String   LAYOUT24 = "layout24";
    private static final  String   LAYOUT25 = "layout25";
    private static final  String   LAYOUT26 = "layout26";
    private static final  String   LAYOUT27 = "layout27";
    private static final  String   LAYOUT28 = "layout28";
    private static final  String   LAYOUT29 = "layout29";
    private static final  String   LAYOUT30 = "layout30";

    private static final  String   LAYOUT31 = "layout31";
    private static final  String   LAYOUT32 = "layout32";
    private static final  String   LAYOUT33 = "layout33";
    private static final  String   LAYOUT34 = "layout34";
    private static final  String   LAYOUT35 = "layout35";
    private static final  String   LAYOUT36 = "layout36";
    private static final  String   LAYOUT37 = "layout37";
    private static final  String   LAYOUT38 = "layout38";
    private static final  String   LAYOUT39 = "layout39";
    private static final  String   LAYOUT40 = "layout40";


    private static final String  BYBIGCARLIMIT="byCartSpeedLimit"; //大车限高速
    private static final String  BYBIGCARLOWLIMIT="byBigCarLowSpeedLimit";//大车限低速
    private static final String  BYSMALLCARLOWLIMIT="byLowSpeedLimit";//小车限低速
    private static final String  BYCARLIMIT="bySpeedLimit";//小车限高速
    private static final String  BYSIGNSPEED="bySignSpeed";//小车标志限速
    private static final String  BYCARSIGNSPEED="byCartSignSpeed";//大车标志限速


    private static final String  BYLIMIT="bySpeed";//限制速度
    private static final String  ANGLENUM="radar_Anglenum";//角度
    private static final String  hardThreshold="radar_hardThreshold";//硬门限
    private static final String  threshold="radar_threshold";//门限系数
    private static final String  raderHeight="radar_raderHeight";//架设高度
    private static final String  leftSpeed="radar_leftSpeed";//余量
    private static final String  adjustedLane="radar_adjustedLane";//车辆校准系数
    private static final String  laneWidth="radar_laneWidth";//车道宽度
    private static final String  Interval="radar_Interval";//两车车距差值
    private static final String  distance="radar_distance";//雷达到车道距离

    private static final String  MODE="radar_mode";//工作模式
    private static final String  RoadMode="radar_roadmode";//路况设置


    private static final String  PAISHI="PaiS_check";//牌识
    private static final String  PAISHIJC="PaiS_JC";//简称
    private static final String  PAIS_CAPTURE="PaiS_capture";//抓拍张数
    private static final String  DIRECTIONTYPE="directionType";//方向类型

    private static final String RADIOONE = "radio_one"; //一张合成
    private static final String RADIOTWO = "radio_two"; //一张合成

    private static final String RADIOFLASHMODE = "flash_Mode"; //闪光模式

    private static final String DRAW_X = "start_X";
    private static final String DRAW_Y = "start_Y";

    private static final String DRAW_X1 = "start_X1";
    private static final String DRAW_Y1 = "start_Y1";

    private static final String DRAW_X2 = "start_X2";
    private static final String DRAW_Y2 = "start_Y2";

    private static final String DRAW_X3 = "start_X3";
    private static final String DRAW_Y3 = "start_Y3";

    private static final String DRAWLINE_X = "lineStartX";
    private static final String DRAWLINE_Y = "lineStartY";
    private static final String DRAWLINE_X1 = "lineEndX1";
    private static final String DRAWLINE_Y1 = "lineEndY1";

    private static final String DRAWFRISTLINE_X = "lineFirstStartX";
    private static final String DRAWFRISTLINE_Y = "lineFirstStartY";
    private static final String DRAWFRISTLINE_X1 = "lineFirstEndX1";
    private static final String DRAWFRISTLINE_Y1 = "lineFirstEndY1";

    private static final String DRAWRIGHTLINE_X = "lineRightStartX";
    private static final String DRAWRIGHTLINE_Y = "lineRightStartY";
    private static final String DRAWRIGHTLINE_X1 = "lineRightEndX1";
    private static final String DRAWRIGHTLINE_Y1 = "lineRightEndY1";

    public static synchronized MyPreference getInstance(Context context){
        if(preference == null)
            preference = new MyPreference(context);
        return preference;
    }

    public MyPreference(Context context){
        packageName = context.getPackageName() + "_preferences";
        sharedPreference = context.getSharedPreferences(
                packageName, context.MODE_PRIVATE);
    }

    public boolean getIsFirst(){
        boolean ischeck = sharedPreference.getBoolean(IS_FIRST, true);
        return ischeck;
    }

    public void SetIsFirst(Boolean first){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(IS_FIRST, first);
        editor.commit();
    }

    public String getLimit(){
        String  bySpeed = sharedPreference.getString(BYLIMIT, "8.0");
        return bySpeed ;
    }

    public void SetLimit(String bySpeed){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(BYLIMIT, bySpeed);
        editor.commit();
    }
    public String getAnglenum(){
        String  radar_Anglenum = sharedPreference.getString(ANGLENUM, "25.0");
        return radar_Anglenum ;
    }

    public void SetAnglenum(String radar_Anglenum){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(ANGLENUM, radar_Anglenum);
        editor.commit();
    }
    public String gethardThreshold(){
        String  radar_hardThreshold = sharedPreference.getString(hardThreshold, "0.9");
        return radar_hardThreshold ;
    }

    public void SethardThreshold(String radar_hardThreshold){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(hardThreshold, radar_hardThreshold);
        editor.commit();
    }
    public String getthreshold(){
        String  radar_threshold = sharedPreference.getString(threshold, "10.0");
        return radar_threshold ;
    }

    public void Setthreshold(String radar_threshold){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(threshold, radar_threshold);
        editor.commit();
    }
    public String getraderHeight(){
        String  radar_raderHeight = sharedPreference.getString(raderHeight, "1.0");
        return radar_raderHeight ;
    }

    public void SetraderHeight(String radar_raderHeight){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(raderHeight, radar_raderHeight);
        editor.commit();
    }
    public String getleftSpeed(){
        String  radar_leftSpeed = sharedPreference.getString(leftSpeed, "0.0");
        return radar_leftSpeed ;
    }

    public void SetleftSpeed(String radar_leftSpeed){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(leftSpeed, radar_leftSpeed);
        editor.commit();
    }
    public String getadjustedLane(){
        String  radar_adjustedLane = sharedPreference.getString(adjustedLane, "1.3");
        return radar_adjustedLane ;
    }

    public void SetadjustedLane(String radar_adjustedLane){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(adjustedLane, radar_adjustedLane);
        editor.commit();
    }

    public String getlaneWidth(){
        String  radar_laneWidth = sharedPreference.getString(laneWidth, "3.8");
        return radar_laneWidth ;
    }

    public void SetlaneWidth(String radar_laneWidth){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(laneWidth, radar_laneWidth);
        editor.commit();
    }
    public String getInterval(){
        String  radar_Interval = sharedPreference.getString(Interval, "5.0");
        return radar_Interval ;
    }

    public void SetInterval(String radar_Interval){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(Interval, radar_Interval);
        editor.commit();
    }
    public String getdistance(){
        String  radar_distance = sharedPreference.getString(distance, "0.4");
        return radar_distance ;
    }

    public void Setdistance(String radar_distance){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(distance, radar_distance);
        editor.commit();
    }

    public String getRoadMode(){
        String  radar_RoadMode = sharedPreference.getString(RoadMode, "0");
        return radar_RoadMode ;
    }

    public void SetRoadMode(String radar_RoadMode){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(RoadMode, radar_RoadMode);
        editor.commit();
    }

    public String getMode(){
        String  radar_mode = sharedPreference.getString(MODE, "1");
        return radar_mode ;
    }

    public void SetMode(String radar_mode){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(MODE, radar_mode);
        editor.commit();
    }




    public float getDRAWRIGHTLINE_X(){
        float  lineStartX = sharedPreference.getFloat(DRAWRIGHTLINE_X, 0);
        return lineStartX ;
    }
    public void SetDRAWRIGHTLINE_X(float lineStartX){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAWRIGHTLINE_X, lineStartX);
        editor.commit();
    }
    public float getDRAWRIGHTLINE_Y(){
        float  lineStartY = sharedPreference.getFloat(DRAWRIGHTLINE_Y, 0);
        return lineStartY ;
    }
    public void SetDRAWRIGHTLINE_Y(float lineStartY){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAWRIGHTLINE_Y, lineStartY);
        editor.commit();
    }
    public float getDRAWRIGHTLINE_X1(){
        float  lineEndX1 = sharedPreference.getFloat(DRAWRIGHTLINE_X1, 0);
        return lineEndX1 ;
    }
    public void SetDRAWRIGHTLINE_X1(float lineEndX1){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAWRIGHTLINE_X1, lineEndX1);
        editor.commit();
    }
    public float getDRAWRIGHTLINE_Y1(){
        float  lineEndY1 = sharedPreference.getFloat(DRAWRIGHTLINE_Y1, 0);
        return lineEndY1 ;
    }
    public void SetDRAWRIGHTLINE_Y1(float lineEndY1){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAWRIGHTLINE_Y1, lineEndY1);
        editor.commit();
    }





    public float getDRAWFRISTLINE_X(){
        float  lineStartX = sharedPreference.getFloat(DRAWFRISTLINE_X, 0);
        return lineStartX ;
    }
    public void SetDRAWFRISTLINE_X(float lineStartX){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAWFRISTLINE_X, lineStartX);
        editor.commit();
    }
    public float getDRAWFRISTLINE_Y(){
        float  lineStartY = sharedPreference.getFloat(DRAWFRISTLINE_Y, 0);
        return lineStartY ;
    }
    public void SetDRAWFRISTLINE_Y(float lineStartY){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAWFRISTLINE_Y, lineStartY);
        editor.commit();
    }
    public float getDRAWFRISTLINE_X1(){
        float  lineEndX1 = sharedPreference.getFloat(DRAWFRISTLINE_X1, 0);
        return lineEndX1 ;
    }
    public void SetDRAWFRISTLINE_X1(float lineEndX1){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAWFRISTLINE_X1, lineEndX1);
        editor.commit();
    }
    public float getDRAWFRISTLINE_Y1(){
        float  lineEndY1 = sharedPreference.getFloat(DRAWFRISTLINE_Y1, 0);
        return lineEndY1 ;
    }
    public void SetDRAWFRISTLINE_Y1(float lineEndY1){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAWFRISTLINE_Y1, lineEndY1);
        editor.commit();
    }

    public float getDRAWLINE_X(){
        float  lineStartX = sharedPreference.getFloat(DRAWLINE_X, 0);
        return lineStartX ;
    }
    public void SetDRAWLINE_X(float lineStartX){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAWLINE_X, lineStartX);
        editor.commit();
    }
    public float getDRAWLINE_Y(){
        float  lineStartY = sharedPreference.getFloat(DRAWLINE_Y, 0);
        return lineStartY ;
    }
    public void SetDRAWLINE_Y(float lineStartY){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAWLINE_Y, lineStartY);
        editor.commit();
    }
    public float getDRAWLINE_X1(){
        float  lineEndX1 = sharedPreference.getFloat(DRAWLINE_X1, 0);
        return lineEndX1 ;
    }
    public void SetDRAWLINE_X1(float lineEndX1){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAWLINE_X1, lineEndX1);
        editor.commit();
    }
    public float getDRAWLINE_Y1(){
        float  lineEndY1 = sharedPreference.getFloat(DRAWLINE_Y1, 0);
        return lineEndY1 ;
    }
    public void SetDRAWLINE_Y1(float lineEndY1){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAWLINE_Y1, lineEndY1);
        editor.commit();
    }

    public float getDRAW_X(){
        float  start_X = sharedPreference.getFloat(DRAW_X, 0);
        return start_X ;
    }
    public void SetDRAW_X(float start_X){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAW_X, start_X);
        editor.commit();
    }
    public float getDRAW_Y(){
        float  start_Y = sharedPreference.getFloat(DRAW_Y, 0);
        return start_Y ;
    }
    public void SetDRAW_Y(float start_Y){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAW_Y, start_Y);
        editor.commit();
    }

    public float getDRAW_X1(){
        float  start_X1 = sharedPreference.getFloat(DRAW_X1, 0);
        return start_X1 ;
    }
    public void SetDRAW_X1(float start_X1){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAW_X1, start_X1);
        editor.commit();
    }
    public float getDRAW_Y1(){
        float  start_Y1 = sharedPreference.getFloat(DRAW_Y1, 0);
        return start_Y1 ;
    }
    public void SetDRAW_Y1(float start_Y1){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAW_Y1, start_Y1);
        editor.commit();
    }

    public float getDRAW_X2(){
        float  start_X2 = sharedPreference.getFloat(DRAW_X2, 0);
        return start_X2 ;
    }
    public void SetDRAW_X2(float start_X2){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAW_X2, start_X2);
        editor.commit();
    }
    public float getDRAW_Y2(){
        float  start_Y2 = sharedPreference.getFloat(DRAW_Y2, 0);
        return start_Y2 ;
    }
    public void SetDRAW_Y2(float start_Y2){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAW_Y2, start_Y2);
        editor.commit();
    }

    public float getDRAW_X3(){
        float  start_X3 = sharedPreference.getFloat(DRAW_X3, 0);
        return start_X3 ;
    }
    public void SetDRAW_X3(float start_X3){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAW_X3, start_X3);
        editor.commit();
    }
    public float getDRAW_Y3(){
        float  start_Y3 = sharedPreference.getFloat(DRAW_Y3, 0);
        return start_Y3 ;
    }
    public void SetDRAW_Y3(float start_Y3){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(DRAW_Y3, start_Y3);
        editor.commit();
    }


    public int getRADIOFLASHMODE(){
        int  flash_mode = sharedPreference.getInt(RADIOFLASHMODE, 0);
        return flash_mode ;
    }
    public void SetRADIOFLASHMODE(int flash_mode){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(RADIOFLASHMODE, flash_mode);
        editor.commit();
    }


    public int getDIRECTIONTYPE(){
        int  directionType = sharedPreference.getInt(DIRECTIONTYPE, 1);
        return directionType ;
    }
    public void SetDIRECTIONTYPE(int directionType){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(DIRECTIONTYPE, directionType);

        editor.commit();
    }
    public int getRADIOTWO(){
        int  radio_two = sharedPreference.getInt(RADIOTWO, 6);
        return radio_two ;
    }
    public void SetRADIOTWO(int radio_two){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(RADIOTWO, radio_two);
        editor.commit();
    }

    public int getRADIOONE(){
        int  radio_one = sharedPreference.getInt(RADIOONE, 4);
        return radio_one ;
    }
    public void SetRADIOONE(int radio_one){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(RADIOONE, radio_one);
        editor.commit();
    }

    public Boolean getPAISHI(){
        boolean ischeck = sharedPreference.getBoolean(PAISHI, false);
        return ischeck;
    }
    public void SetPAISHI(boolean PaiS_check){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(PAISHI, PaiS_check);
        editor.commit();
    }

    public int getPAISHIJC(){
        int  PaiS_JC = sharedPreference.getInt(PAISHIJC, 1);
        return PaiS_JC ;
    }
    public void SetPAISHIJC(int PaiS_JC){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(PAISHIJC, PaiS_JC);
        editor.commit();
    }
    public int getPAIS_CAPTURE(){
        int  PaiS_capture = sharedPreference.getInt(PAIS_CAPTURE, 1);
        return PaiS_capture ;
    }
    public void SetPAIS_CAPTURE(int PaiS_capture){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(PAIS_CAPTURE, PaiS_capture);
        editor.commit();
    }

    public String getbyCartSignSpeed(){
        String  byCartSignSpeed = sharedPreference.getString(BYCARSIGNSPEED, "70");
        return byCartSignSpeed ;
    }
    public void SetbyCartSignSpeed(String byCartSignSpeed){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(BYCARSIGNSPEED, byCartSignSpeed);
        editor.commit();
    }

    public String getbySignSpeed(){
        String  bySignSpeed = sharedPreference.getString(BYSIGNSPEED, "70");
        return bySignSpeed ;
    }
    public void SetbySignSpeed(String bySignSpeed){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(BYSIGNSPEED, bySignSpeed);
        editor.commit();
    }

    public String getBigCarLimit(){
        String  byCartSpeedLimit = sharedPreference.getString(BYBIGCARLIMIT, "80");
        return byCartSpeedLimit ;
    }

    public void SetBigCarLimit(String byCartSpeedLimit){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(BYBIGCARLIMIT, byCartSpeedLimit);
        editor.commit();
    }

    public String getBigCarLowLimit(){
        String  byBigCarLowSpeedLimit = sharedPreference.getString(BYBIGCARLOWLIMIT, "30");
        return byBigCarLowSpeedLimit ;
    }

    public void SetBigCarLowLimit(String byBigCarLowSpeedLimit){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(BYBIGCARLOWLIMIT, byBigCarLowSpeedLimit);
        editor.commit();
    }

    public String getSmallCarLowLimit(){
        String  byLowSpeedLimit = sharedPreference.getString(BYSMALLCARLOWLIMIT, "20");
        return byLowSpeedLimit ;
    }

    public void SetSmallCarLowLimit(String byLowSpeedLimit){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(BYSMALLCARLOWLIMIT, byLowSpeedLimit);
        editor.commit();
    }
    public String getCarLimit(){
        String  bySpeedLimit = sharedPreference.getString(BYCARLIMIT, "80");
        return bySpeedLimit ;
    }

    public void SetCarLimit(String bySpeedLimit){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(BYCARLIMIT, bySpeedLimit);
        editor.commit();
    }

    public String getIpAddress(){
        String  ipAddress = sharedPreference.getString(IPADDRESS, "192.168.1.64");
        return ipAddress ;
    }

    public void SetIpAddress(String ipAddress){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(IPADDRESS, ipAddress);
        editor.commit();
    }

    public String getLoginName(){
        String loginName = sharedPreference.getString(LOGIN_NAME, "");
        return loginName;
    }

    public void SetLoginName(String loginName){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(LOGIN_NAME, loginName);
        editor.commit();
    }


    public String getPassword(){
        String password = sharedPreference.getString(PASSWORD, "");
        return password;
    }


    public void SetPassword(String password){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(PASSWORD, password);
        editor.commit();
    }


    public boolean getCheckBox1(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT1, false);
        return ischeck;
    }

    public void SetCheckBox1(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT1, ischeck);
        editor.commit();
    }

    public boolean getCheckBox2(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT2, false);
        return ischeck;
    }

    public void SetCheckBox2(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT2, ischeck);
        editor.commit();
    }
    public boolean getCheckBox3(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT3, false);
        return ischeck;
    }

    public void SetCheckBox3(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT3, ischeck);
        editor.commit();
    }
    public boolean getCheckBox4(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT4, false);
        return ischeck;
    }

    public void SetCheckBox4(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT4, ischeck);
        editor.commit();
    }
    public boolean getCheckBox5(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT5, false);
        return ischeck;
    }

    public void SetCheckBox5(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT5, ischeck);
        editor.commit();
    }

    public boolean getCheckBox6(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT6, false);
        return ischeck;
    }

    public void SetCheckBox6(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT6, ischeck);
        editor.commit();
    }

    public boolean getCheckBox7(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT7, false);
        return ischeck;
    }

    public void SetCheckBox7(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT7, ischeck);
        editor.commit();
    }
    public boolean getCheckBox8(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT8, false);
        return ischeck;
    }

    public void SetCheckBox8(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT8, ischeck);
        editor.commit();
    }
    public boolean getCheckBox9(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT9, false);
        return ischeck;
    }

    public void SetCheckBox9(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT9, ischeck);
        editor.commit();
    }
    public boolean getCheckBox10(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT10, false);
        return ischeck;
    }

    public void SetCheckBox10(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT10, ischeck);
        editor.commit();
    }
    public boolean getCheckBox11(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT11, false);
        return ischeck;
    }

    public void SetCheckBox11(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT11, ischeck);
        editor.commit();
    }
    public boolean getCheckBox12(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT12, false);
        return ischeck;
    }

    public void SetCheckBox12(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT12, ischeck);
        editor.commit();
    }

    public boolean getCheckBox13(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT13, false);
        return ischeck;
    }

    public void SetCheckBox13(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT13, ischeck);
        editor.commit();
    }
    public boolean getCheckBox14(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT14, false);
        return ischeck;
    }

    public void SetCheckBox14(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT14, ischeck);
        editor.commit();
    }

    public boolean getCheckBox15(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT15, false);
        return ischeck;
    }

    public void SetCheckBox15(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT15, ischeck);
        editor.commit();
    }

    public boolean getCheckBox16(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT16, false);
        return ischeck;
    }

    public void SetCheckBox16(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT16, ischeck);
        editor.commit();
    }

    public boolean getCheckBox17(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT17, false);
        return ischeck;
    }

    public void SetCheckBox17(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT17, ischeck);
        editor.commit();
    }
    public boolean getCheckBox18(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT18, false);
        return ischeck;
    }

    public void SetCheckBox18(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT18, ischeck);
        editor.commit();
    }
    public boolean getCheckBox19(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT19, false);
        return ischeck;
    }

    public void SetCheckBox19(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT19, ischeck);
        editor.commit();
    }
    public boolean getCheckBox20(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT20, false);
        return ischeck;
    }

    public void SetCheckBox20(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT20, ischeck);
        editor.commit();
    }

    public boolean getCheckBox21(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT21, false);
        return ischeck;
    }

    public void SetCheckBox21(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT21, ischeck);
        editor.commit();
    }
    public boolean getCheckBox22(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT22, false);
        return ischeck;
    }

    public void SetCheckBox22(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT22, ischeck);
        editor.commit();
    }
    public boolean getCheckBox23(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT23, false);
        return ischeck;
    }

    public void SetCheckBox23(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT23, ischeck);
        editor.commit();
    }
    public boolean getCheckBox24(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT24, false);
        return ischeck;
    }

    public void SetCheckBox24(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT24, ischeck);
        editor.commit();
    }
    public boolean getCheckBox25(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT25, false);
        return ischeck;
    }

    public void SetCheckBox25(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT25, ischeck);
        editor.commit();
    }
    public boolean getCheckBox26(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT26, false);
        return ischeck;
    }

    public void SetCheckBox26(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT26, ischeck);
        editor.commit();
    }
    public boolean getCheckBox27(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT27, false);
        return ischeck;
    }

    public void SetCheckBox27(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT27, ischeck);
        editor.commit();
    }
    public boolean getCheckBox28(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT28, false);
        return ischeck;
    }

    public void SetCheckBox28(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT28, ischeck);
        editor.commit();
    }
    public boolean getCheckBox29(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT29, false);
        return ischeck;
    }

    public void SetCheckBox29(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT29, ischeck);
        editor.commit();
    }
    public boolean getCheckBox30(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT30, false);
        return ischeck;
    }

    public void SetCheckBox30(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT30, ischeck);
        editor.commit();
    }
    public boolean getCheckBox31(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT31, false);
        return ischeck;
    }

    public void SetCheckBox31(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT31, ischeck);
        editor.commit();
    }
    public boolean getCheckBox32(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT32, false);
        return ischeck;
    }

    public void SetCheckBox32(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT32, ischeck);
        editor.commit();
    }
    public boolean getCheckBox33(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT33, false);
        return ischeck;
    }

    public void SetCheckBox33(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT33, ischeck);
        editor.commit();
    }
    public boolean getCheckBox34(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT34, false);
        return ischeck;
    }

    public void SetCheckBox34(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT34, ischeck);
        editor.commit();
    }
    public boolean getCheckBox35(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT35, false);
        return ischeck;
    }

    public void SetCheckBox35(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT35, ischeck);
        editor.commit();
    }
    public boolean getCheckBox36(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT36, false);
        return ischeck;
    }

    public void SetCheckBox36(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT36, ischeck);
        editor.commit();
    }
    public boolean getCheckBox37(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT37, false);
        return ischeck;
    }
    public void SetCheckBox37(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT37, ischeck);
        editor.commit();
    }
    public boolean getCheckBox38(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT38, false);
        return ischeck;
    }
    public void SetCheckBox38(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT38, ischeck);
        editor.commit();
    }
    public boolean getCheckBox39(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT39, false);
        return ischeck;
    }

    public void SetCheckBox39(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT39, ischeck);
        editor.commit();
    }
    public boolean getCheckBox40(){
        boolean ischeck = sharedPreference.getBoolean(LAYOUT40, false);
        return ischeck;
    }

    public void SetCheckBox40(Boolean ischeck){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LAYOUT40, ischeck);
        editor.commit();
    }
}
