package com.huang.bchtsystem.View.Fragment.PreView;



import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;
import com.huang.bchtsystem.jna.video.JNATest;
import com.huang.bchtsystem.jna.video.PlaySurfaceView;

import org.MediaPlayer.PlayM4.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.R.attr.delay;


/**
 * Created by admin on 2017/3/13.
 * 预览界面
 */

public class PreViewActivity extends FragmentActivity implements View.OnClickListener{

    @InjectView(R.id.preview)
    Button preview;

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;

    @InjectView(R.id.preview_CB_Record)
    ImageButton preview_CB_Record;  //录像选择
    @InjectView(R.id.preview_SV_Video)
    SurfaceView preview_SV_Video;  //视频预览
    @InjectView(R.id.image)
    ImageView image;

    @InjectView(R.id.preview_Bt_ManualCapture)
    ImageButton preview_Bt_ManualCapture;  //手动抓拍

    @InjectView(R.id.preview_Record_Time)
    Chronometer preview_Record_Time;


    @InjectView(R.id.layout_Frame)
    FrameLayout layout_Frame;

    private static PlaySurfaceView playView;
    private int m_iPlayID = -1; // return by NET_DVR_RealPlay_V30
    private Handler handler;
    private Runnable runnable;
    private final String TAG = "MainActivity";
    private int m_iPort = 0; // play port

    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;

    private int m_iLogID = -1; // return by NET_DVR_Login_v30
    private int m_iStartChan = 0; // start channel no
    private int m_iChanNum = 0; // channel number
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);
        ButterKnife.inject(this);
        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("预览");
        textview.setVisibility(View.GONE);
        m_iLogID = getIntent().getExtras().getInt("m_iLogID");
        m_iStartChan =  getIntent().getExtras().getInt("m_iStartChan");
        m_iChanNum = getIntent().getExtras().getInt("m_iChanNum");
        if (m_iChanNum > 1) {
            ChangeSingleSurFace(false);
        } else {
            ChangeSingleSurFace(true);
        }
        initView();
        if (m_iLogID>-1)
        {
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    preview.performClick();
//                    preview();
                }
            };
            handler.postDelayed(runnable,100);
        }

    }

    boolean isChecked = false;
    private void initView(){
        preview_Bt_ManualCapture.setOnClickListener(this);
        preview_SV_Video.setOnClickListener(this);
        preview_CB_Record.setOnClickListener(this);
        preview.setOnClickListener(this);
        layout_Title.setOnClickListener(this);
        image.setOnClickListener(this);
    }
    //开启录像
    private boolean m_bSaveRealData = false;
    File appDir = null;
    String recordFilePath = null;
    String date = null;
    private void record()
    {
        if (!m_bSaveRealData) {
            appDir = new File(Environment.getExternalStorageDirectory(),"Pictures");
            if (!appDir.exists()){
                appDir.mkdir();
            }
            SimpleDateFormat sDateFormat = new SimpleDateFormat(
                    "MM-dd-hh-mm-ss");
            date = sDateFormat.format(new java.util.Date());
            recordFilePath = Environment.getExternalStorageDirectory()+"/Pictures/"+date+".mp4";
            if (!HCNetSDK.getInstance().NET_DVR_SaveRealData(m_iPlayID,
                    recordFilePath)) {
                System.out.println("NET_DVR_SaveRealData failed! error: "
                        + HCNetSDK.getInstance().NET_DVR_GetLastError());
                return;
            } else {
                System.out.println("NET_DVR_SaveRealData succ!");
            }
            m_bSaveRealData = true;
        } else {
            if (!HCNetSDK.getInstance().NET_DVR_StopSaveRealData(m_iPlayID)) {
                System.out
                        .println("NET_DVR_StopSaveRealData failed! error: "
                                + HCNetSDK.getInstance()
                                .NET_DVR_GetLastError());
            } else {
                System.out.println("NET_DVR_StopSaveRealData succ!");
            }

            File file = new File(appDir,recordFilePath);
            //把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(this.getContentResolver(),file.getAbsolutePath(),recordFilePath,null);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            //最后通知图库
            this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.parse("file://"+Environment.getExternalStorageDirectory())));
            m_bSaveRealData = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_Title:
                stopSinglePreview();
                finish();
                break;
            case R.id.preview:
                preview();
                break;
            case R.id.preview_CB_Record: //录像
                if(!isChecked){
                    preview_Record_Time.setVisibility(View.VISIBLE);
                    preview_Record_Time.setBase(SystemClock.elapsedRealtime());
                    int hour = (int) ((SystemClock.elapsedRealtime() - preview_Record_Time.getBase())/1000/60);
                    preview_Record_Time.setFormat("0"+String.valueOf(hour)+":%s");
                    preview_Record_Time.start();
                    Toast.makeText(PreViewActivity.this,"开启录像",Toast.LENGTH_SHORT).show();
                    record();
                    isChecked = true;
                }else {
                    preview_Record_Time.setVisibility(View.INVISIBLE);
                    preview_Record_Time.stop();
                    Toast.makeText(PreViewActivity.this,"关闭录像",Toast.LENGTH_SHORT).show();
                    record();
                    isChecked = false;
                    this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.parse("file://"+Environment.getExternalStorageDirectory())));
                    dialog();
                }
                break;
            case R.id.preview_Bt_ManualCapture:  //手动抓拍
                Log.e(TAG, "preview_Bt_ManualCapture");
                if (m_iPort < 0) {
                    Log.e(TAG, "please start preview first");
                    return;
                }
                Player.MPInteger stWidth = new Player.MPInteger();
                Player.MPInteger stHeight = new Player.MPInteger();
                if (!Player.getInstance().getPictureSize(m_iPort, stWidth,
                        stHeight)) {
                    Log.e(TAG, "getPictureSize failed with error code:"
                            + Player.getInstance().getLastError(m_iPort));
                    return;
                }
                int nSize = 5 * stWidth.value * stHeight.value;
                byte[] picBuf = new byte[nSize];
                Player.MPInteger stSize = new Player.MPInteger();
                if (!Player.getInstance()
                        .getBMP(m_iPort, picBuf, nSize, stSize)) {
                    Log.e(TAG, "getBMP failed with error code:"
                            + Player.getInstance().getLastError(m_iPort));
                    return;
                }
                appDir = new File(Environment.getExternalStorageDirectory(),"Pictures");
                if (!appDir.exists()){
                    appDir.mkdir();
                }
                SimpleDateFormat sDateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd-hh:mm:ss");
                date = sDateFormat.format(new java.util.Date());
                String fileName = date+".jpg";
                File file = new File(Environment.getExternalStorageDirectory()+"/Pictures/"+date+".jpg");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(picBuf, 0, stSize.value);
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(Environment.getExternalStorageDirectory()+"/Pictures/"+date+".jpg");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap= BitmapFactory.decodeStream(fis);
                image.setVisibility(View.VISIBLE);
                image.setImageBitmap(Util.scaleBitmap(bitmap, 0.2f));
                //把文件插入到系统图库
                try {
                    MediaStore.Images.Media.insertImage(this.getContentResolver(),Environment.getExternalStorageDirectory()+"/Pictures/"+date+".jpg",
                            date+".jpg",null);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }catch (NullPointerException e){
                    e.fillInStackTrace();
                }
                //最后通知图库
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/Pictures/"+date+".jpg"))));
                break;
            case R.id.preview_SV_Video:
                if (!bChoose)
                {
                    layout_Frame.setVisibility(View.VISIBLE);
                    bChoose  = true ;
                }else {
                    layout_Frame.setVisibility(View.GONE);
                    bChoose  = false ;
                }
                break;
            case R.id.image:
                Intent i = new Intent();
                i.setClass(this,PictureFromSDActivity.class);
                startActivity(i);
                image.setVisibility(View.GONE);
                finish();
                break;
            default:
                break;
        }
    }
    private void dialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("视频库通道");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent();
                i.setClass(PreViewActivity.this,RecordFromSDActivity.class);
                startActivity(i);
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private boolean bChoose = false;
    DisplayMetrics metric ;
    private void ChangeSingleSurFace(boolean bSingle)
    {
        metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        if (playView == null) {
            playView = new PlaySurfaceView(PreViewActivity.this);    //*****************
            playView.setParam(metric.widthPixels);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            params.topMargin=5;
            params.leftMargin = 0;
            params.gravity = Gravity.CENTER;
            addContentView(playView, params);
            playView.setVisibility(View.VISIBLE);
        }
    }
    private void  preview()
    {

        if (m_iLogID < 0) {
            Log.e(TAG, "please login on device first");
            return;
        }
        startSinglePreview();
    }

    private void startSinglePreview()
    {
        NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
        previewInfo.lChannel = m_iStartChan;
        previewInfo.dwStreamType = 0; // substream
        previewInfo.bBlocked = 1;
        previewInfo.hHwnd = playView.getHolder();
        // HCNetSDK start preview
        m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(m_iLogID,
                previewInfo, null);
        if (m_iPlayID < 0) {
            Log.e(TAG, "NET_DVR_RealPlay_V40 is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return;
        }
        Log.i(TAG,
                "NetSdk Play sucess ***********************3***************************");
    }
    private void stopSinglePreview()
    {
        if (m_iPlayID < 0) {
            Log.e(TAG, "m_iPlayID < 0");
            return;
        }
        Log.e("111","111");
        if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID)) {
            Log.e(TAG, "StopRealPlay is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return;
        }
        Log.e("222","222");
        m_iPlayID = -1 ;
    }

    @Override
    protected void onDestroy()
    {
        playView = null;
        super.onDestroy();
        stopSinglePreview();
        if (m_iLogID>-1){
            handler.removeCallbacks(runnable);
        }
        preview_SV_Video.getHolder().getSurface().release();
        Log.i(TAG,
                "NetSdk Logout sucess ***********************3***************************");
        finish();
    }
}