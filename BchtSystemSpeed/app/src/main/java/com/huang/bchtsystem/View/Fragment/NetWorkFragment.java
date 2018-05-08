package com.huang.bchtsystem.View.Fragment;

import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.huang.bchtsystem.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017/3/13.
 *
 */

public class NetWorkFragment extends TabActivity implements View.OnClickListener{

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;


    @InjectView(R.id.layout_network)
    protected LinearLayout layout_network;
    //基本参数
    @InjectView(R.id.network_IP)
    protected EditText network_IP;
    @InjectView(R.id.network_ZiWang)
    protected EditText network_ZiWang;
    @InjectView(R.id.network_WangG)
    protected EditText network_WangG;

    //服务器设置
    @InjectView(R.id.network_ServerCB)
    protected CheckBox network_ServerCB;
    @InjectView(R.id.network_ServerIP)
    protected EditText network_ServerIP;
    @InjectView(R.id.network_ServerAccount)
    protected EditText network_ServerAccount;
    @InjectView(R.id.network_ServerPwd)
    protected EditText network_ServerPwd;
    @InjectView(R.id.network_ServerPort)
    protected EditText network_ServerPort;
    //FTP设置
    @InjectView(R.id.networkFTP_CB)
    protected CheckBox networkFTP_CB;
    @InjectView(R.id.networkFTP_Account)
    protected EditText networkFTP_Account;
    @InjectView(R.id.networkFTP_Pwd)
    protected EditText networkFTP_Pwd;
    @InjectView(R.id.networkFTP_IP)
    protected EditText networkFTP_IP;
    @InjectView(R.id.networkFTP_Port)
    protected EditText networkFTP_Port;
    @InjectView(R.id.networkFTP_Path)
    protected EditText networkFTP_Path;
    //默认、保存、返回
    @InjectView(R.id.network_MoRen)
    protected Button network_MoRen;
    @InjectView(R.id.network_BaoCun)
    protected Button network_BaoCun;
    @InjectView(R.id.network_FanHui)
    protected Button network_FanHui;

    private TabHost tabHost ;
    private static int myMenuSettingTag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network);
        ButterKnife.inject(this);

        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("网络设置");
        textview.setVisibility(View.GONE);

        initView();
    }

    private void initView(){
        tabHost = getTabHost();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1").setIndicator("网络设置").setContent(R.id.network_tab1);
        tabHost.addTab(tab1);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2").setIndicator("服务器设置").setContent(R.id.network_tab2);
        tabHost.addTab(tab2);
        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3").setIndicator("FTP设置").setContent(R.id.network_tab3);
        tabHost.addTab(tab3);
        init();
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tagString) {

                if (tagString.equals("tab1")) {
                    myMenuSettingTag = 1;
                    NetworkTab1();
                }
                if (tagString.equals("tab2")) {
                    myMenuSettingTag = 2;
                    ServerTab2();
                }
                if (tagString.equals("tab3")) {
                    myMenuSettingTag = 3;
                    FTPTab3();
                }
            }
        });
    }

    //网络设置
    private void NetworkTab1(){
        network_IP.getText().toString();
        network_ZiWang.getText().toString();
        network_WangG.getText().toString();
    }
    //服务器设置
    boolean isChecked =false ;
    private void ServerTab2(){
        network_ServerCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(NetWorkFragment.this,"服务器开启",Toast.LENGTH_LONG).show();
                }else {

                }
            }
        });
        network_ServerCB.setChecked(isChecked);

        network_ServerIP.getText().toString();
        network_ServerAccount.getText().toString();
        network_ServerPwd.getText().toString();
        network_ServerPort.getText().toString();
        networkFTP_Port.getText().toString();
    }
    //FTP设置
    private void FTPTab3(){
        if ( networkFTP_CB.isChecked()){
            Toast.makeText(this,"FTP开启",Toast.LENGTH_LONG).show();
        }
        networkFTP_Account.getText().toString();
        networkFTP_Pwd.getText().toString();
        networkFTP_IP.getText().toString();
        networkFTP_Path.getText().toString();
    }

    private void init()
    {
        layout_network.setOnClickListener(this);
        network_MoRen.setOnClickListener(this);
        network_BaoCun.setOnClickListener(this);
        network_FanHui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.layout_network:
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
            case R.id.network_MoRen:
                break;
            case R.id.network_BaoCun:
                break;
            case R.id.network_FanHui:
                finish();
                break;
            default:
                break;
        }
    }
}
