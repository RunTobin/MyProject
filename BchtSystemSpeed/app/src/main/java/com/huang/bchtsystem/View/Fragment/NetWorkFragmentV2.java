package com.huang.bchtsystem.View.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.huang.bchtsystem.R;
import com.huang.bchtsystem.DataAccess.DvrConfigInstance;
import com.huang.bchtsystem.Util.MyPreference;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.View.Activity.BridgeActivity;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 网络设置界面
 */
public class NetWorkFragmentV2 extends AppCompatActivity {
    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;

    @InjectView(R.id.tv_baseSetting)
    LinearLayout tv_baseSetting;

    @InjectView(R.id.tv_serverSetting)
    LinearLayout tv_serverSetting;

    @InjectView(R.id.tv_ftpSetting)
    LinearLayout tv_ftpSetting;

    @InjectView(R.id.but_restart)
    Button but_restart ;
    @InjectView(R.id.et_ipAddr)
    EditText et_ipAddr;
    @InjectView(R.id.et_subNetMask)
    EditText et_subNetMask;
    @InjectView(R.id.et_gateway)
    EditText et_gateway;
    @InjectView(R.id.et_dns)
    EditText et_dns;
    @InjectView(R.id.et_dns1)
    EditText et_dns1;
    @InjectView(R.id.et_YiTiIP)
    EditText et_YiTiIP;

    @InjectView(R.id.cb_server)
    CheckBox cb_server;
    @InjectView(R.id.et_serverIP)
    EditText et_serverIP;
    @InjectView(R.id.et_serverAccount)
    EditText et_serverAccount;
    @InjectView(R.id.et_serverPwd)
    EditText getEt_serverPwd;
    @InjectView(R.id.et_serverPort)
    EditText et_serverPort;

    @InjectView(R.id.cb_networkFTP)
    CheckBox cb_networkFTP;
    @InjectView(R.id.et_ftpAccount)
    EditText et_ftpAccount;
    @InjectView(R.id.et_ftpIP)
    EditText et_ftpIP;
    @InjectView(R.id.et_ftpPath)
    EditText et_ftpPath;
    @InjectView(R.id.et_ftpPort)
    EditText et_ftpPort;
    @InjectView(R.id.et_ftpPwd)
    EditText et_ftpPwd;

    private int uid,channel;

    private int[] menus = {R.id.tv_baseSetting,R.id.tv_serverSetting,R.id.tv_ftpSetting};
    private int[] layout = {R.id.ll_baseSetting,R.id.ll_serverSetting,R.id.ll_ftpSetting};

    Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_v2);
        ButterKnife.inject(this);

        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("网络设置");
        textview.setVisibility(View.GONE);

        init();
        tv_baseSetting.setBackgroundResource(R.drawable.networkbg);
        uid = getIntent().getExtras().getInt("m_iLogID");
        channel = getIntent().getExtras().getInt("m_iChanNum");
        initBaseSetting();
        baseEditTextClick();
    }

    private void init()
    {
        but_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restarClick("设备重启");
            }
        });
        layout_Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        for(int i = 0; i < menus.length; i ++ ) {
            TextViewOnclickListener clickListener = new TextViewOnclickListener();
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
    }
    private void initBaseSetting()
    {
        DvrConfigInstance.getInstance().getDvrConfig(uid,channel);
        et_ipAddr.setText( DvrConfigInstance.getInstance().getDvr().getIpAddr());
        et_subNetMask.setText( DvrConfigInstance.getInstance().getDvr().getSubNetMask());
        et_gateway.setText( DvrConfigInstance.getInstance().getDvr().getGateWay());
        et_dns.setText( DvrConfigInstance.getInstance().getDvr().getDns());
        et_dns1.setText( DvrConfigInstance.getInstance().getDvr().getDns1());
        et_YiTiIP.setText(Util.getLocalIpAddress());
    }

    private void initServerSetting()
    {
        DvrConfigInstance.getInstance().getServerConfig(uid,channel);
        boolean b = DvrConfigInstance.getInstance().getServerSetting().getHost().isStartServerSetting();
        cb_server.setChecked(b);
        if (cb_server.isChecked()){
            et_serverIP.setText( DvrConfigInstance.getInstance().getServerSetting().getHost().getServerIP() );
            et_serverPort.setText(DvrConfigInstance.getInstance().getServerSetting().getHost().getPortString());
            et_serverAccount.setText(DvrConfigInstance.getInstance().getServerSetting().getUserData().getAccount());
            getEt_serverPwd.setText(DvrConfigInstance.getInstance().getServerSetting().getUserData().getUserPwd());
        }else {
            et_serverIP.setText("");
            et_serverPort.setText("");
            et_serverAccount.setText("");
            getEt_serverPwd.setText("");
        }
        if (cb_server.isChecked()){
            serverEditTextClick();
        }
    }

    private void initFtpSetting()
    {
        DvrConfigInstance.getInstance().getFtpServerConfig(uid,channel);
        boolean a = DvrConfigInstance.getInstance().getFtpServerSetting().getHost().isStartServerSetting() ;
        cb_networkFTP.setChecked(a);
        if (cb_networkFTP.isChecked()){
            et_ftpIP.setText(DvrConfigInstance.getInstance().getFtpServerSetting().getHost().getServerIP());
            et_ftpPort.setText(DvrConfigInstance.getInstance().getFtpServerSetting().getHost().getPortString());
            et_ftpAccount.setText(DvrConfigInstance.getInstance().getFtpServerSetting().getUserData().getAccount());
            et_ftpPwd.setText(DvrConfigInstance.getInstance().getFtpServerSetting().getUserData().getUserPwd());
            et_ftpPath.setText(DvrConfigInstance.getInstance().getFtpServerSetting().getHost().getPath());
        }else {
            et_ftpIP.setText("");
            et_ftpPort.setText("");
            et_ftpAccount.setText("");
            et_ftpPwd.setText("");
            et_ftpPath.setText("");
        }
    }

    private class TextViewOnclickListener implements View.OnClickListener
    {
        public void onClick(View v)
        {
            int id = v.getId();
            switch (id)
            {
                case R.id.tv_baseSetting :
                    tv_baseSetting.setBackgroundResource(R.drawable.networkbg);
                    tv_serverSetting.setBackgroundResource(R.color.grey);
                    tv_ftpSetting.setBackgroundResource(R.color.grey);
                    if (uid > -1){
                        initBaseSetting();
                        baseEditTextClick();
                    }
                    break;
                case R.id.tv_serverSetting :
                    tv_serverSetting.setBackgroundResource(R.drawable.networkbg);
                    tv_baseSetting.setBackgroundResource(R.color.grey);
                    tv_ftpSetting.setBackgroundResource(R.color.grey);
                    if (uid > -1){
                        initServerSetting();
                        serverClick();
                    }
                    break;
                case R.id.tv_ftpSetting :
                    tv_ftpSetting.setBackgroundResource(R.drawable.networkbg);
                    tv_baseSetting.setBackgroundResource(R.color.grey);
                    tv_serverSetting.setBackgroundResource(R.color.grey);
                    if (uid > -1){
                        initFtpSetting();
                        ftpclick();
                    }
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

    //基础设置
    private void baseEditTextClick()
    {
        et_ipAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextClick("IP地址修改",MyPreference.getInstance(NetWorkFragmentV2.this)
                        .getIpAddress(),9);
            }
        });
        et_subNetMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextClick("子网掩码修改",DvrConfigInstance.getInstance().getDvr().getSubNetMask(),10);
            }
        });
        et_gateway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextClick("网关地址修改",DvrConfigInstance.getInstance().getDvr().getGateWay(),11);
            }
        });
        et_dns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextClick("DNS修改",DvrConfigInstance.getInstance().getDvr().getDns(),12);
            }
        });
        et_dns1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextClick("DNS1修改",DvrConfigInstance.getInstance().getDvr().getDns1(),13);
            }
        });
        et_YiTiIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =  new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//                startActivity(intent);
                EditTextClick("一体机IP地址修改",Util.getLocalIpAddress(),14);
            }
        });
    }
    //一体机修改以太网ip地址
    private void shella(String s){
        String com="ifconfig eth0 "+s+" netmask 255.255.255.0";
        try{
            Process suProcess = Runtime.getRuntime().exec("su");//root权限
            DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
            // Execute commands that require root access
            os.writeBytes(com+ "\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("一体机IP地址", "succ" );
    }

    //服务器点击事件
    private void serverClick()
    {
        cb_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_server.isChecked()){
                    DvrConfigInstance.getInstance().setServerConfig(uid,channel ,1);
                    cb_server.setChecked(true);
                }else {
                    DvrConfigInstance.getInstance().setServerConfig(uid,channel ,0);
                    cb_server.setChecked(false);
                }
                initServerSetting();
            }
        });
    }

    private void serverEditTextClick()
    {
        et_serverIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextClick("服务器IP地址修改",DvrConfigInstance.getInstance().getServerSetting().getHost().getServerIP(),5);
            }
        });
        et_serverPort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextClick("端口号修改",DvrConfigInstance.getInstance().getServerSetting().getHost().getPortString(),6);
            }
        });
        et_serverAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextClick("用户名修改",DvrConfigInstance.getInstance().getServerSetting().getUserData().getAccount(),7);
            }
        });
        getEt_serverPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextClick("密码修改",DvrConfigInstance.getInstance().getServerSetting().getUserData().getUserPwd(),8);
            }
        });
    }

    //FTP设置点击事件
    private void ftpclick(){
        cb_networkFTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_networkFTP.isChecked()){
                    DvrConfigInstance.getInstance().setFtpServerConfig(uid,channel ,1);
                    cb_networkFTP.setChecked(true);
                }else {
                    DvrConfigInstance.getInstance().setFtpServerConfig(uid,channel ,0);
                    cb_networkFTP.setChecked(false);
                }
                initFtpSetting();
            }
        });
        et_ftpIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextClick("服务器IP地址修改",DvrConfigInstance.getInstance().getFtpServerSetting().getHost().getServerIP(),1);
            }
        });
        et_ftpPort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextClick("端口号修改",DvrConfigInstance.getInstance().getFtpServerSetting().getHost().getPortString(),2);
            }
        });
        et_ftpAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextClick("登录名修改",DvrConfigInstance.getInstance().getFtpServerSetting().getUserData().getAccount(),3);
            }
        });
        et_ftpPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextClick("密码修改",DvrConfigInstance.getInstance().getFtpServerSetting().getUserData().getUserPwd(),4);
            }
        });
    }

    private void EditTextClick(String str ,String str1 ,int j)
    {
        // 加载输入框的布局文件
        LayoutInflater inflater = (LayoutInflater) NetWorkFragmentV2.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.storage_file, null);
        EditText inputStringr = (EditText) layout
                .findViewById(R.id.input_add_string);
        inputStringr.setText(str1);
        // 弹出的对话框
        new AlertDialog.Builder(NetWorkFragmentV2.this)
					/* 弹出窗口的最上头文字 */
                .setTitle(str)
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setMessage("网络内容设置，确认内容修改，取消不进行操作！")
                .setView(layout)
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                String s = inputStringr.getText().toString().trim();
                               if ( j < 5){
                                    DvrConfigInstance.getInstance().setFtpServerConfig(uid,channel ,s ,j);
                                    initFtpSetting();
                                }else if (j < 9){
                                    DvrConfigInstance.getInstance().setServerConfig(uid,channel ,s ,j);
                                    initServerSetting();
                                }else if (i< 14){
                                  if (j == 9){
                                      et_ipAddr.setText(s);
                                  }else if (j == 10){
                                      et_subNetMask.setText(s);
                                  }else if (j == 11){
                                      et_gateway.setText(s);
                                  }else if (j == 12){
                                      et_dns.setText(s);
                                  }
                                  else if (j == 13){
                                      et_dns1.setText(s);
                                  }else if (j == 14){
                                      et_YiTiIP.setText(s);
                                  }
                               }
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
    public void onResume()
    {
        super.onResume();
        et_ipAddr.setText(MyPreference.getInstance(this)
                .getIpAddress());
    }

    private void restar(int iuseid)
    {
        boolean  ret = HCNetSDKJNAInstance.getInstance().NET_DVR_RebootDVR(iuseid);
        if (!ret){
            Log.e("网络设置", "NET_DVR_RebootDVR, error = " + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }else {
            Log.e("网络设置", "NET_DVR_RebootDVR, succ " );
        }

    }
    Dialog progressDialog ;
    Handler mhandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                {
                    BridgeActivity.startActivity(NetWorkFragmentV2.this,"LoginFragment","登录页面");
                    finish();
                }
                break;
                default:
                    break;
            }
        };
    };

    private void progressUi() {
        progressDialog = Util.createLoadingDialog(NetWorkFragmentV2.this, "重启中......");
        progressDialog.show();
        //新建线程   
        new Thread() {
            @Override
            public void run() {
        //需要花时间计算的方法   
            try{
                Thread.sleep(90000);
            }catch ( Exception e){
                e.fillInStackTrace();
            }
        //向handler发消息   
                mhandler.sendEmptyMessage(0);
            }
        }.start();
    }


    private void restarClick(String str)
    {
        new android.support.v7.app.AlertDialog.Builder(NetWorkFragmentV2.this)
					/* 弹出窗口的最上头文字 */
                .setTitle(str)
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setMessage("是否重启设备！")
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                MyPreference.getInstance(NetWorkFragmentV2.this)
                                        .SetIpAddress(et_ipAddr.getText().toString());
                                DvrConfigInstance.getInstance().setDvrConfig(uid,et_ipAddr.getText().toString(),et_subNetMask.getText().toString(),
                                        et_gateway.getText().toString(),et_dns.getText().toString(),et_dns1.getText().toString(),channel);
                                restar(uid);
                                shella(et_YiTiIP.getText().toString().trim());
                                progressUi();
                                dialoginterface.dismiss();
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() { /* 设置跳出窗口的返回事件 */
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                dialoginterface.dismiss();
                            }
                        })
                .show();
    }
}