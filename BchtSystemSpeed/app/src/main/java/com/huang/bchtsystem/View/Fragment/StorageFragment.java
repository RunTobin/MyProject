package com.huang.bchtsystem.View.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StatFs;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.huang.bchtsystem.Model.picturepreviewData;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.View.Adapter.MyAdapter;
import com.huang.bchtsystem.jna.HCNetSDKByJNA;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;
import com.sun.jna.ptr.IntByReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017/3/13.
 * 储存设置界面
 */

public class StorageFragment extends ListActivity implements View.OnClickListener{

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;
    @InjectView(R.id.layout_storage)
    LinearLayout layout_storage;


    @InjectView(R.id.storage_Eq)
    protected Spinner storage_Eq;  //当前设备
    @InjectView(R.id.storage_Eq_text)
    TextView storage_Eq_text;
    @InjectView(R.id.storage_Space)
    protected TextView storage_Space; //剩余空间

    @InjectView(R.id.layout_usb)
    LinearLayout layout_usb;

    @InjectView(R.id.storage_open)
    Button storage_open;
    @InjectView(R.id.storage_delete)
    protected Button storage_delete;  //删除
    @InjectView(R.id.storage_XinJ)
    protected Button storage_XinJ;  //新建
    @InjectView(R.id.storage_GeShiH)
    protected Button storage_GeShiH;  //格式化


    @InjectView(R.id.storage_FanH)
    protected Button storage_FanH; //返回

    private List<String> pathList = new ArrayList<>();
    private  ListView lv;
    private int m_iLogID = -1; // return by NET_DVR_Login_v30
    private int m_iChanNum = 0; // channel number
    private Bitmap[] bitmap;
    private List<String> items = null;
    private List<String> paths = null;
    private String rootPath = null;
    private MyAdapter adapter;
    private  Handler handler = null;
    private  Runnable runnable = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storagefragment);
        ButterKnife.inject(this);
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("存储设置");
        textview.setVisibility(View.GONE);
        m_iLogID = getIntent().getExtras().getInt("m_iLogID");
        m_iChanNum = getIntent().getExtras().getInt("m_iChanNum");
        rootPath = Util.rootPath;
        initView();
        lv = getListView();
    }

    private void initView()
    {
        layout_Title.setOnClickListener(this);
        layout_storage.setOnClickListener(this);
        storage_open.setOnClickListener(this);
        storage_delete.setOnClickListener(this);
        storage_XinJ.setOnClickListener(this);
        storage_GeShiH.setOnClickListener(this);
        storage_FanH.setOnClickListener(this);
        bitmap = new Bitmap[]{BitmapFactory.decodeResource(getResources(), R.mipmap.folder),
                BitmapFactory.decodeResource(getResources(), R.mipmap.file)};
        adapter = new MyAdapter(filePath,paths, bitmap,pathList, StorageFragment.this);
        adapter.setPathList(pathList);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.eq,android.R.layout.simple_spinner_dropdown_item);
        storage_Eq.setAdapter(adapter);
        storage_Eq.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               TextView tv = (TextView)view;
               tv.setTextColor(getResources().getColor(R.color.font));
               tv.setGravity(Gravity.CENTER);
               if (position == 0){
                   layout_usb.setVisibility(View.VISIBLE);
                   try {
                       filePath = rootPath;
                       getFileDir(filePath);
                       storage_Space.setText(Formatter.formatFileSize(StorageFragment.this, Util.initUsb(paths)));
                       itemClick();
                   }catch (NullPointerException e){
                       e.fillInStackTrace();
                   }
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
    }
    public void getFileDir(String filePath)
    {
        try{
            items = new ArrayList<>();
            paths = new ArrayList<>();
            File file = new File(filePath);
            File[] files = file.listFiles();
            if (!filePath.equals(rootPath)) {
                items.add("...");
                paths.add(rootPath);
            }
            if (files != null) {
                int count = files.length ;
                for (int i = 0; i < count; i++) {
                    File file2 =files[i];
                    paths.add(file2.getPath());
                    items.add(file2.getName());
                }
            }
            adapter = new MyAdapter(filePath,paths, bitmap,pathList, StorageFragment.this);
            adapter.setMyAdapter(paths);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Drawable drawable=getResources().getDrawable(R.color.blue);
            lv.setSelector(drawable);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void itemClick()
    {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
                filePath = paths.get(position);
            }
        });
    }
    private String filePath = null;

    //检查扩展名 
    @SuppressLint("DefaultLocale")
    private boolean checkIsImageFile(String fName)
    {
        boolean isImageFile  = false ;
        //获取扩展名
        String FileEnd  = fName.substring(fName.lastIndexOf(".")+1,fName.length()).toLowerCase();
        if (FileEnd .equals("apk")){
            isImageFile = true ;
        }else {
            isImageFile = false ;
        }
        return  isImageFile ;
    }
    //安装apk
    private void openFile(File file)
    {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.layout_Title:
                finish();
                break;
            case R.id.layout_storage:
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
            case R.id.storage_FanH:
                finish();
                break;
            case R.id.storage_open:
                pathList = adapter.getPathList();
                if (filePath!=null){
                 if (pathList.size()>0){
                     if ( pathList.size()==1){
                         filePath = pathList.get(0);
                     }else {
                         Toast.makeText(this,
                                 "请选择根目录", Toast.LENGTH_SHORT)
                                 .show();
                     }
                 }
                  File file = new File(filePath);
                  //如果是文件夹就继续分解
                  if (file.isDirectory()) {
                      getFileDir(filePath);
                  }else {
                      if (checkIsImageFile(file.getPath())){
                          openFile(file);
                      }
                  }
              }
                break;
            case R.id.storage_delete:  //删除ListView的单个item
                pathList = adapter.getPathList();
                Log.e("123","==="+pathList.size());
                showStorage_dialog("删除文件","删除当前文件！",1);
                break;
            case R.id.storage_XinJ:  //新建文件，添加ListView的item
                addFile();
                break;
            case R.id.storage_GeShiH:
                showStorage_dialog("格式化","格式化当前设备",2);
                break;
            default:
                break;
        }
    }




    //新建文件对话框
    private void addFile()
    {
        // 加载输入框的布局文件
        LayoutInflater inflater = (LayoutInflater) StorageFragment.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.storage_file, null);
        // 弹出的对话框
        new AlertDialog.Builder(StorageFragment.this)
					/* 弹出窗口的最上头文字 */
                .setTitle("新建一个文件")
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setMessage("请输入新建文件名")
                .setView(layout)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {

                                EditText inputStringr = (EditText) layout
                                        .findViewById(R.id.input_add_string);

                                String str = inputStringr.getText()
                                        .toString();

                                if (str == null || str.equals("")) {
                                    Toast.makeText(getApplicationContext(),
                                            "添加的内容不能为空", Toast.LENGTH_SHORT)
                                            .show();
                                } else {
                                    String path= filePath + str + "/";
                                    File file=new File(path);
                                    if(!file.exists()){
                                        file.mkdir();
                                    }
                                }
                                handler = new Handler();
                                runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        getFileDir(filePath);
                                        itemClick();
                                    }
                                };
                                handler.postDelayed(runnable,1000);
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() { /* 设置跳出窗口的返回事件 */
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                Toast.makeText(StorageFragment.this,
                                        "取消了添加文件", Toast.LENGTH_SHORT)
                                        .show();

                            }
                        }).show();
    }

    //TODO==格式化对话框
    private void showStorage_dialog(String title,String message,int j)
    {
        // 加载输入框的布局文件
        LayoutInflater inflater = (LayoutInflater) StorageFragment.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 弹出的对话框
        new AlertDialog.Builder(StorageFragment.this)
					/* 弹出窗口的最上头文字 */
                .setTitle(title)
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setMessage(message)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                initDialog(j);
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() { /* 设置跳出窗口的返回事件 */
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                Toast.makeText(StorageFragment.this,
                                        "取消了"+title, Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }).show();
    }
    private ProgressDialog dialog;
    private void initDialog(int j)
    {
        dialog = new ProgressDialog(StorageFragment.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("提示:");
        dialog.setMessage("文件正在删除");
        dialog.setCancelable(false);
        dialog.show();
        new Thread(){
            @Override
            public void run() {
                try{
                    if (j == 1){
                        if (filePath !=null){
                            if (pathList.size()==0){
                                Util.deleteDir(filePath);
                                filePath = rootPath;
                            }else {
                                for (int k =0;k<pathList.size();k++){
                                    if (pathList.get(k)!=rootPath){
                                        Util.deleteDir(pathList.get(k));
                                    }
                                }
                            }
                            Util.show_Toast(StorageFragment.this,"删除成功",1000);
                            pathList = new ArrayList<String>();
                        }
                    }else if (j == 2){
                        Util.deleteDir(rootPath);
                        filePath = rootPath;
                        Util.show_Toast(StorageFragment.this,"格式化成功",1000);
                    }
                    dialog.cancel();
                }catch (Exception e){
                    dialog.cancel();
                }
            }
        }.start();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (j==1 && pathList.size()==0){
                    getFileDir(filePath);
                }else {
                    getFileDir(rootPath);
                }
                itemClick();
            }
        };
        handler.postDelayed(runnable,1000);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        finish();
    }
}
