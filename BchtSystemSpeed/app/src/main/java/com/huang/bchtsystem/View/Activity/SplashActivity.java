package com.huang.bchtsystem.View.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.huang.bchtsystem.DBUtil.BlackInfo;
import com.huang.bchtsystem.DBUtil.ConstantUtil;
import com.huang.bchtsystem.DBUtil.MySQLiteOpenHelper;
import com.huang.bchtsystem.DBUtil.SQLiteOpenHelper;
import com.huang.bchtsystem.DBUtil.User;
import com.huang.bchtsystem.DBUtil.UserUtil;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.MyPreference;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.jna.video.JNATest;
import com.huang.bchtsystem.rader.ComBean;
import com.huang.bchtsystem.rader.Devices;
import com.huang.bchtsystem.rader.Rader;
import com.huang.bchtsystem.rader.SerialHelper;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017/3/13.
 */

public class SplashActivity extends Activity implements View.OnClickListener{

    @InjectView(R.id.layout_login)
    LinearLayout layout_login;
    @InjectView(R.id.login_account)
    EditText login_account;
    @InjectView(R.id.login_password)
    EditText login_password;
    @InjectView(R.id.login_sure)
    Button login_sure;
    @InjectView(R.id.login_cancel)
    Button login_cancel;

    private Dialog progressDialog;
    private static String msg = null;

    //定义用户名和密码
    private String m_user="";
    private String m_pwd="";
    private Handler handler;
    private Runnable runnable;
    private int currentItem=0;  //当前显示页面索引
    private String url = null;
    private MySQLiteOpenHelper mySQLiteOpenHelper; //黑名单
    private SQLiteOpenHelper sqLiteOpenHelper; //用户管理
    private List<User> userList = null;
    private List<BlackInfo> userblack = null;
    SerialControl ComA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComA = new SerialControl();
        OpenComPort(ComA);
        CloseComPort(ComA);
        final boolean succ = Rader.getInstance().open(Devices.getAbsolutePath("/dev/ttyS2"),9600);
        if (succ)
        {
            Rader.getInstance().close();
        }
        setContentView(R.layout.login_activity);
        ButterKnife.inject(this);
        login_sure.setOnClickListener(this);
        login_cancel.setOnClickListener(this);
        layout_login.setOnClickListener(this);

   /*     Intent intent = new Intent(Intent.ACTION_MAIN);
        ComponentName componentName = new ComponentName("com.bjw.ComAssistant", "com.bjw.ComAssistant.ComAssistantActivity");
        intent.setComponent(componentName);
        startActivity(intent);
*/
        //取用户名和密码
        m_user = MyPreference.getInstance(this).getLoginName();
        m_pwd = MyPreference.getInstance(this).getPassword();
        mySQLiteOpenHelper = new MySQLiteOpenHelper(SplashActivity.this);
        sqLiteOpenHelper = new SQLiteOpenHelper(SplashActivity.this);
        userList = sqLiteOpenHelper.queryData();
        userblack = mySQLiteOpenHelper.queryData();
        if (m_user.isEmpty() && userList.size() == 0 && userblack.size() ==0 ){
            addDBData();
            addUser();
        }
        userList = sqLiteOpenHelper.queryData();
        if (!initSdk()) {
            this.finish();
            return;
        }
        LoadingViewUi();
        //TODO======检查自动登录
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

    private void LoadingViewUi()
    {
        progressDialog = Util.createLoadingDialog(SplashActivity.this, "系统相机开启中......");
        progressDialog.show();
        //新建线程   
        new Thread() {
            @Override
            public void run() {
                //需要花时间计算的方法   
                try{
                    Thread.sleep(1000);
//                    if (MyPreference.getInstance(SplashActivity.this).getIsFirst())
//                    {
//                        MyPreference.getInstance(SplashActivity.this).SetIsFirst(false);
//                        loginCamera();
//                        JNATest.TEST_Config(m_iLogID);
//                    }
                }catch ( Exception e){
                    e.fillInStackTrace();
                }
                //向handler发消息  
                progressDialog.dismiss();
            }
        }.start();
    }

    //添加黑名单数据
    private void addDBData()
    {
        //传入参数
        ContentValues cv=new ContentValues();
        //列名和值
        cv.put(ConstantUtil.USER_CARNUM, "皖AR223C");
        cv.put(ConstantUtil.USER_COLOR, "蓝色");
        cv.put(ConstantUtil.USER_ADDRESS, "皖");
        cv.put(ConstantUtil.USER_TYPE, "占用应急车道");
        mySQLiteOpenHelper.insertData(cv);

        ContentValues cv1=new ContentValues();
        //列名和值
        cv1.put(ConstantUtil.USER_CARNUM, "皖AY1J66");
        cv1.put(ConstantUtil.USER_COLOR, "蓝色");
        cv1.put(ConstantUtil.USER_ADDRESS, "皖");
        cv1.put(ConstantUtil.USER_TYPE, "占用应急车道");
        mySQLiteOpenHelper.insertData(cv1);
    }
    //添加用户数据
    private void addUser()
    {
        //传入参数
        ContentValues cv=new ContentValues();
        //列名和值
        cv.put(UserUtil.USER_NAME, "admin");
        cv.put(UserUtil.USER_PWD, "admin12345");
        cv.put(UserUtil.USER_INFO, "最高权限");
        cv.put(UserUtil.USER_TYPE, "超级管理员");
        sqLiteOpenHelper.insertData(cv);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_login:
                InputMethodManager imm=(InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
            case R.id.login_sure:
                progressDialog = Util.createLoadingDialog(this, "登录中");
                progressDialog.show();
                //新建线程   
                new Thread() {
                    @Override
                    public void run() {
                        //需要花时间计算的方法   
                        try{
                            login();
                        }catch ( Exception e){
                            e.fillInStackTrace();
                        }
                        //向handler发消息  
                        progressDialog.dismiss();
                    }
                }.start();
                break;
            case R.id.login_cancel:
                login_account.setText("");
                login_password.setText("");
                break;
            default:
                break;
        }
    }
    private void login()
    {
        if (TextUtils.isEmpty(login_account.getText().toString()) && TextUtils.isEmpty(login_password.getText().toString()))
        {
            msg = "账号和密码不能为空";
            showStorage_dialog();
            return;
        } else {
            for(int i=0;i<userList.size();i++)
            {
                if (login_account.getText().toString().equals(userList.get(i).getName().trim()) ){
                    if (login_password.getText().toString().isEmpty()){
                        Util.show_Toast(SplashActivity.this,"密码不能为空",1000);
                        return;
                    }else {
                        if (login_password.getText().toString().equals(userList.get(i).getPwd().trim()))
                        {
                            Util.DBPositon = i;
                            MyPreference.getInstance(this)
                                    .SetLoginName(login_account.getText().toString().trim());
//                            MyPreference.getInstance(this)
//                                    .SetPassword(login_password.getText().toString().trim());
                            //TODO====登录到主界面MainActivity
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        }else {
                            Util.show_Toast(SplashActivity.this,"密码错误",1000);
                        }
                    }
                }
            }
        }
    }
    private void showStorage_dialog()
    {
        // 加载输入框的布局文件
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 弹出的对话框
        new AlertDialog.Builder(this)
					/* 弹出窗口的最上头文字 */
                .setTitle("提示")
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setMessage(msg)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                dialoginterface.dismiss();
                            }
                        })
                .show();
    }
    //用户记住账号和密码
    @Override
    public void onResume()
    {
        super.onResume();
//        getContext();
        login_account.setText(MyPreference.getInstance(this)
                .getLoginName());
//        login_password
//                .setText(MyPreference.getInstance(this).getPassword());
    }
    //----------------------------------------------------串口控制类
    private class SerialControl extends SerialHelper {
        public SerialControl(){
        }

        @Override
        protected void onDataReceived(final ComBean ComRecData)
        {
            //数据接收量大或接收时弹出软键盘，界面会卡顿,可能和6410的显示性能有关
            //直接刷新显示，接收数据量大时，卡顿明显，但接收与显示同步。
            //用线程定时刷新显示可以获得较流畅的显示效果，但是接收数据速度快于显示速度时，显示会滞后。
            //最终效果差不多-_-，线程定时刷新稍好一些。
            //	DispQueue.AddQueue(ComRecData);//线程定时刷新显示(推荐)
			/*
			runOnUiThread(new Runnable()//直接刷新显示
			{
				public void run()
				{
					DispRecData(ComRecData);
				}
			});*/
        }
    }
    //----------------------------------------------------关闭串口
    private void CloseComPort(SerialHelper ComPort){
        if (ComPort!=null){
            ComPort.stopSend();
            ComPort.close();
        }
    }
    //----------------------------------------------------打开串口
    private void OpenComPort(SerialHelper ComPort)
    {
        try
        {
            ComPort.open();
        } catch (SecurityException e) {
            ShowMessage("打开串口失败:没有串口读/写权限!");
        } catch (IOException e) {
            ShowMessage("打开串口失败:未知错误!");
        } catch (InvalidParameterException e) {
            ShowMessage("打开串口失败:参数错误!");
        }
    }
    //------------------------------------------显示消息
    private void ShowMessage(String sMsg)
    {
        Toast.makeText(this, sMsg, Toast.LENGTH_SHORT).show();
    }


    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;
    private int m_iLogID = -1; // return by NET_DVR_Login_v30
    private final String TAG = "SplashActivity";
    private int m_iStartChan = 0; // start channel no
    private int m_iChanNum = 0; // channel number
    public static String strIP;
    //登录海康相机
    private void loginCamera()
    {
        if (m_iLogID < 0) {
            m_iLogID = loginDevice();
            if (m_iLogID < 0) {
                Log.e(TAG, "This device logins failed!");
                //   show_dialog();
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
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
        Cleanup();
        MyPreference.getInstance(SplashActivity.this).SetIsFirst(true);
        finish();
    }

}