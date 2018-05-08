package com.huang.bchtsystem.View.Fragment.Picture;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hikvision.netsdk.HCNetSDK;
import com.huang.bchtsystem.DataAccess.PictureFinder;
import com.huang.bchtsystem.Manager.Task;
import com.huang.bchtsystem.Manager.Worker;
import com.huang.bchtsystem.Model.ImageInfoBean;
import com.huang.bchtsystem.Model.PictureResultData;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.Util;

import com.huang.bchtsystem.View.Adapter.MyAdapter;
import com.huang.bchtsystem.View.Adapter.PictureResultAdapter;

import com.huang.bchtsystem.jna.HCNetSDKByJNA;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;
import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.huang.bchtsystem.Web.ApiServiceSupport.TAG;

/**
 * Created by admin on 2017/6/12.
 */

public class PictureResultActivity extends Activity implements View.OnClickListener, Task, Handler.Callback {
    private class MSG {
        public final static int MSG_EMPTY_FIELD = 1;
        public final static int MSG_TASK_FINISHED = 2;
        public final static int MSG_FIELD = 3;

    }

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;

    @InjectView(R.id.pictureResult_Image1)
    ImageView pictureResult_Image1;
    @InjectView(R.id.pictureResult_Image2)
    ImageView pictureResult_Image2;

    @InjectView(R.id.check_all)
    CheckBox check_all;

    @InjectView(R.id.PResult_first)
    Button PResult_first;
    @InjectView(R.id.PResult_last)
    Button PResult_last;
    @InjectView(R.id.PResult_PageUP)
    Button PResult_PageUP;
    @InjectView(R.id.PResult_Page_Down)
    Button PResult_Page_Down;
    @InjectView(R.id.PResult_Explore)
    Button PResult_Explore;
    @InjectView(R.id.PResult_Delete)
    Button PResult_Delete;
    @InjectView(R.id.PResult_FanH)
    Button PResult_FanH;
    @InjectView(R.id.PResult_ListView)
    ListView PResult_ListView;
    @InjectView(R.id.pic_waiting)
    LinearLayout pic_waiting;

    @InjectView(R.id.progress)
    ProgressBar progress;
    @InjectView(R.id.PResult_tv)
    TextView PResult_tv;

    private PictureResultAdapter pictureAdapter;
    private PictureFinder pictureFinder;
    private int userId;
    private int channel;
    private String strStartTime;
    private String strEndTime;


    private List<String> items = null;
    private List<String> paths = null;
    private String rootPath = null;
    private MyAdapter adapter;
    private Bitmap[] bitmap;

    private List<PictureResultData> listPictureData;
    private List<PictureResultData> pictureExploreList = new ArrayList<>();
    Thread worker;
    Worker target;


    public int getUserId() {
        return userId = getIntent().getExtras().getInt("m_iLogID");
    }

    public int getChannel() {
        return channel = getIntent().getExtras().getInt("m_iChanNum");
    }

    public String getStrartTime() {
        return strStartTime = getIntent().getExtras().getString("start");
    }

    public String getEndTime() {
        return strEndTime = getIntent().getExtras().getString("end");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        target = new Worker();
        worker = new Thread(target);
        worker.start();

        setContentView(R.layout.picture_resultfragment);
        ButterKnife.inject(this);
        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("图片结果");
        textview.setVisibility(View.GONE);

        progress.setVisibility(View.GONE);
        pic_waiting.setVisibility(View.GONE);
        rootPath = Util.rootPath;
        init();
        if (userId > -1) {
            SearchPictureList();
        }
    }

    private void init()
    {
        PResult_first.setOnClickListener(this);
        PResult_last.setOnClickListener(this);
        PResult_PageUP.setOnClickListener(this);
        PResult_Page_Down.setOnClickListener(this);
        PResult_Explore.setOnClickListener(this);
        PResult_Delete.setOnClickListener(this);
        PResult_FanH.setOnClickListener(this);
        layout_Title.setOnClickListener(this);
        pictureResult_Image1.setOnClickListener(this);
        pictureResult_Image2.setOnClickListener(this);
        getUserId();
        getChannel();
        getStrartTime();
        getEndTime();
        pictureFinder = new PictureFinder();
        pictureFinder.setlUserId(userId);
        pictureAdapter = new PictureResultAdapter(this, listPictureData, pictureExploreList);
        pictureAdapter.setUid(userId);
        pictureAdapter.setTarget(target);
        if (userId > -1) {
            check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        pictureAdapter.setIscheckall(true);
                        pictureAdapter.notifyDataSetChanged();
                    } else {
                        pictureAdapter.setIscheckall(false);
                        pictureAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.PResult_PageUP:
                leftView();
                break;
            case R.id.PResult_Page_Down:
                rightView();
                break;
            case R.id.PResult_Delete:
                break;
            case R.id.PResult_Explore:
                Log.e("123",pictureExploreList.size()+"");
                getFileDir(rootPath);
                if (paths.size() <= 0) {
                    mhandler.sendEmptyMessage(7);
                } else if (pictureExploreList.size() <= 0){
                    mhandler.sendEmptyMessage(2);
                }else if(Util.initUsb(paths)<pictureExploreList.size()*350*1024){
                    mhandler.sendEmptyMessage(3);
                }else {
                    addexplore();
                }
                break;
            case R.id.PResult_FanH:
                finish();
                break;
            case R.id.layout_Title:
                finish();
                break;
            case R.id.PResult_first:
                fitstView();
                break;
            case R.id.PResult_last:
                lastView();
                break;
            case R.id.pictureResult_Image1:
                if (map!= null){
                    Intent intent = new Intent(PictureResultActivity.this,PictureResultFragment.class);
                    ImageInfoBean dto = new ImageInfoBean();
                    String uri = createImageFromBitmap(map);
                    dto.setUri(uri);
                    intent.putExtra("bitmap",dto);
                    startActivity(intent);
                }
                break;
            case R.id.pictureResult_Image2:
                if (map1!= null){
                    Intent intent = new Intent(PictureResultActivity.this,PictureResultFragment.class);
                    ImageInfoBean dto = new ImageInfoBean();
                    String uri = createImageFromBitmap(map1);
                    dto.setUri(uri);
                    intent.putExtra("bitmap",dto);
                    startActivity(intent);
                }
                break;
        }
    }

    public String createImageFromBitmap(Bitmap bitmap)
    {
        String fileName = "myImage";
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }


    HCNetSDKByJNA.NET_DVR_FIND_PICTURE netDvrFindPicture;

    private List<PictureResultData> pictureSearch() {
        HCNetSDKByJNA.NET_DVR_FIND_PICTURE_PARAM netDvrFindPictureParam = new HCNetSDKByJNA.NET_DVR_FIND_PICTURE_PARAM();
        netDvrFindPicture = new HCNetSDKByJNA.NET_DVR_FIND_PICTURE();
        pictureFinder.setLpNetDvrFindPictureParam(netDvrFindPictureParam, netDvrFindPicture);

        netDvrFindPictureParam.dwSize = netDvrFindPictureParam.size();
        netDvrFindPictureParam.lChannel = getChannel();
        String msg = "";
        if (strStartTime.isEmpty()) {
            msg += "开始时间, ";
        }
        if (strEndTime.isEmpty()) {
            msg += "结束时间 ";
        }
        Timestamp startTime = Timestamp.valueOf(strStartTime);
        Timestamp endTime = Timestamp.valueOf(strEndTime);
        Util.initDVRTime(netDvrFindPictureParam.struStartTime, startTime);
        Util.initDVRTime(netDvrFindPictureParam.struStopTime, endTime);
        netDvrFindPictureParam.dwIllegalType = 0x2000;
        netDvrFindPictureParam.dwVehicleType = 0xFFFFFFFF;
       // netDvrFindPictureParam.dwTrafficType = 0x80;


        netDvrFindPictureParam.write();

        pictureFinder.getAllPicture(netDvrFindPicture);
        Test_saveLog();
        return pictureFinder.getListPictureData();
    }

    public  void Test_saveLog()
    {
        int bLogEnable = 1;
        String strLogDir = "C:\\SdkLog\\";

        boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetLogToFile(bLogEnable, strLogDir,true);
        if(! bRet )
        {
            Log.e("JNATEST", "NET_DVR_SetLogToFile failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
        }
    }

    //图片查找结果
    private void SearchPictureList() {
        onTaskBegin();
        target.push(this);
    }

    private void sendMessage(int message, Object obj) {
        Looper looper = Looper.getMainLooper();
        Handler h = new Handler(looper, this);
        Message msg = new Message();
        msg.what = message;
        msg.obj = obj;
        msg.setTarget(h);
        h.sendMessage(msg);
    }

    private void sendMessage(int message) {
        Looper looper = Looper.getMainLooper();
        Handler h = new Handler(looper, this);
        Message msg = new Message();
        msg.what = message;
        msg.setTarget(h);
        h.sendMessage(msg);
    }

    @Override
    public void onTaskBegin() {
        PResult_ListView.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        pic_waiting.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTask() {
        listPictureData = pictureSearch();
    }

    @Override
    public void onTaskFinished() {
        sendMessage(PictureResultActivity.MSG.MSG_TASK_FINISHED);
    }

    @Override
    public void onError(String error) {

    }

    private boolean handleTaskFinished() {
        pic_waiting.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        PResult_ListView.setVisibility(View.VISIBLE);
        if (listPictureData == null) {
            return true;
        }
        if (listPictureData.size() == 0) {
            if (null == pictureAdapter.getOnClickFileNameListener())
                Toast.makeText(this, "没有找到图片", Toast.LENGTH_SHORT).show();
            else
                pictureAdapter.PictureNotFound();
            return true;
        }
        getpic(userId,0,pictureResult_Image1,0);
        if (listPictureData.get(1).filetime.equals(listPictureData.get(0).filetime)){
            getpic(userId,1,pictureResult_Image2,1);
        }else {
            getpic(userId,0,pictureResult_Image2,1);
        }
        page();
        pictureAdapter.setPictureDataList(listPictureData, index, Yecount);
        pictureAdapter.setIscheckall(false);
        PResult_ListView.setAdapter(pictureAdapter);
        pictureAdapter.notifyDataSetChanged();
        PResult_tv.setText("共 " + listPictureData.size() + " 条 " + Yecount + " 页");
        checkButton();
        PResult_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
                for (int i = 0; i<parent.getCount();i++){
                    View v = parent.getChildAt(i);
                    if (position == i){
                        arg1.setBackgroundResource(R.color.blue);
                    }
                    else {
                        try{
                            v.setBackgroundResource(R.color.grey);
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                }

                ListViewPosition = position + (index - 1) * VIEW_COUNT;
                if (ListViewPosition == 0){
                    getpic(userId,ListViewPosition,pictureResult_Image1,0);
                    if (listPictureData.get(ListViewPosition+1).filetime.equals(listPictureData.get(ListViewPosition).filetime)){
                        getpic(userId,ListViewPosition+1,pictureResult_Image2,1);
                    }else {
                        getpic(userId,ListViewPosition,pictureResult_Image2,1);
                    }
                }else if (ListViewPosition == listPictureData.size()-1){
                    if (listPictureData.get(ListViewPosition).filetime.equals(listPictureData.get(ListViewPosition-1).filetime)){
                        getpic(userId, ListViewPosition-1,pictureResult_Image1,0);
                        getpic(userId,ListViewPosition,pictureResult_Image2,1);
                    }else {
                        getpic(userId, ListViewPosition,pictureResult_Image1,0);
                        getpic(userId,ListViewPosition,pictureResult_Image2,1);
                    }
                } else{
                    if (listPictureData.get(ListViewPosition).filetime.equals(listPictureData.get(ListViewPosition-1).filetime)){
                        getpic(userId, ListViewPosition-1,pictureResult_Image1,0);
                        getpic(userId,ListViewPosition,pictureResult_Image2,1);
                    }else if(listPictureData.get(ListViewPosition).filetime.equals(listPictureData.get(ListViewPosition+1).filetime)){
                        getpic(userId, ListViewPosition,pictureResult_Image1,0);
                        getpic(userId,ListViewPosition+1,pictureResult_Image2,1);
                    }else {
                        getpic(userId, ListViewPosition,pictureResult_Image1,0);
                        getpic(userId,ListViewPosition,pictureResult_Image2,1);
                    }
                }
            }
        });
        return true;
    }

    private static int ListViewPosition = 0;

    private boolean handleEmptyField(String msg) {
        Toast.makeText(PictureResultActivity.this, msg, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean handleMessage(Message msg) {
        boolean retVal = false;
        switch (msg.what) {
            case PictureResultActivity.MSG.MSG_EMPTY_FIELD:
                retVal = handleEmptyField((String) msg.obj);
                break;
            case PictureResultActivity.MSG.MSG_TASK_FINISHED:
                retVal = handleTaskFinished();
                break;
            case PictureResultActivity.MSG.MSG_FIELD:
                retVal = handleEmptyField((String) msg.obj);
                break;
            default:
                break;
        }
        return retVal;
    }

    /**
     * 图片下载导出
     */
    private ListView listView = null;
    private String pathFile = null;
    int position = 0;
    private void addexplore()
    {
        // 加载输入框的布局文件
        LayoutInflater inflater = (LayoutInflater) PictureResultActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.usblistview_file, null);
        listView = (ListView) layout.findViewById(R.id.listView);
        bitmap = new Bitmap[]{BitmapFactory.decodeResource(getResources(), R.mipmap.folder),
                BitmapFactory.decodeResource(getResources(), R.mipmap.file)};
        getFileDir(rootPath);
        itemClick();
        // 弹出的对话框
        new AlertDialog.Builder(PictureResultActivity.this)
                    /* 弹出窗口的最上头文字 */
                .setTitle("选择一个文件夹")
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setView(layout)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                dialoginterface.dismiss();
                                initDialog();
//                                new TaskThread().start();
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() { /* 设置跳出窗口的返回事件 */
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                mhandler.sendEmptyMessage(6);
                            }
                        }).show();
    }
    private void itemClick()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pathFile = paths.get(position);
            }
        });
    }
    public void getFileDir(String filePath)
    {
        try {
            items = new ArrayList<>();
            paths = new ArrayList<>();
            File file = new File(filePath);
            File[] files = file.listFiles();
            if (!filePath.equals(rootPath)) {
                items.add("返回根目录");
                paths.add(rootPath);
                items.add("返回上一层目录");
                paths.add(file.getParent());
            }
            if (files != null) {
                int count = files.length;
                for (int i = 0; i < count; i++) {
                    File file2 = files[i];
                    if (file2.isDirectory()) {
                        items.add(file2.getName());
                        paths.add(file2.getPath());
                    }
                }
            }
            adapter = new MyAdapter(filePath,paths, bitmap, PictureResultActivity.this);
            listView.setAdapter(adapter);
            Drawable drawable = getResources().getDrawable(R.drawable.checkbox_bg);
            listView.setSelector(drawable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 分页显示
     */
    // 用于显示页号的索引，第一次进入时为第一页
    int index = 1;
    // 用于显示每列5个Item项。
    int VIEW_COUNT = 4;
    //用于显示多少页
    int Yecount = 0;
    //获取页数
    private void page()
    {
        if (listPictureData != null) {
            int count = listPictureData.size();
            int Z = count % VIEW_COUNT;
            if (Z == 0) {
                Yecount = count / VIEW_COUNT;
            } else {
                Yecount = (count - Z) / VIEW_COUNT + 1;
            }
        }
    }

    /**
     * 查看按钮是不是可用
     */
    public void checkButton()
    {
        // 索引值小于等于1，表示不能向前翻页了，以经到了第一页了，将向前翻页的按钮设为不可用。
        if (index <=1 &&Yecount > 1) {
            PResult_PageUP.setEnabled(false);
            PResult_Page_Down.setEnabled(true);
            index = 1;
        }else if (index <= 1 && Yecount<=1){
            PResult_PageUP.setEnabled(false);
            PResult_Page_Down.setEnabled(false);
        }
        // 值的长度减去前几页的长度，剩下的就是这一页的长度，如果这一页的长度比View_Count小，表示这是最后的一页了，后面在没有了。
        // 将向后翻页的按钮设为不可用。
        else if (listPictureData.size() - (index - 1) * VIEW_COUNT <= VIEW_COUNT) {
            PResult_PageUP.setEnabled(true);
            PResult_Page_Down.setEnabled(false);
        }

        // 否则将2个按钮都设为可用的。
        else {
            PResult_PageUP.setEnabled(true);
            PResult_Page_Down.setEnabled(true);
        }
    }

    /**
     * 点击首页
     */
    private void fitstView()
    {
        index = 1;
        pictureAdapter.setPictureDataList(listPictureData, index, Yecount);
        pictureAdapter.notifyDataSetChanged();
        checkButton();

    }

    /**
     * 末页
     */
    private void lastView()
    {
        index = Yecount;
        pictureAdapter.setPictureDataList(listPictureData, index, Yecount);
        pictureAdapter.notifyDataSetChanged();
        checkButton();
    }

    /**
     * 点击左边的Button，表示向前翻页，索引值要减1.
     */
    private void leftView()
    {
        index--;
        String tag = String.valueOf(index);
        Log.d(tag, tag);
        if (index < 1) {
            Toast.makeText(PictureResultActivity.this, "这是首页", Toast.LENGTH_LONG).show();
        } else {
            pictureAdapter.setPictureDataList(listPictureData, index, Yecount);
            pictureAdapter.notifyDataSetChanged();
        }
        checkButton();
    }

    /**
     * 点击右边的Button，表示向后翻页，索引值要加1.
     */
    public void rightView()
    {
        index++;
        String tag = String.valueOf(index);
        Log.d(tag, tag);
        if (index > Yecount) {
            Toast.makeText(PictureResultActivity.this, "这是末页", Toast.LENGTH_LONG).show();
        } else {
            pictureAdapter.setPictureDataList(listPictureData, index, Yecount);
            pictureAdapter.notifyDataSetChanged();
        }
        checkButton();
    }

    /**
     * 下载图片显示
     */
    public static final int SIZE = 2048 * 2048 * 8;
    private Memory pFileName = new Memory(128);
    private Memory pBuffer = new Memory(SIZE);
    IntByReference retLen = new IntByReference();

    private Bitmap map =null,map1 = null;

    /**
     *两张图片 显示一张图片，这边还需要添加一个getpic()来保存bitmap显示
     */
    private void getpic(int uid, int p,ImageView image,int a)
    {
        if (a == 0){
            if (map != null)
            {
                map.recycle();
            }
        }else if(a == 1){
            if (map1 != null)
            {
                map1.recycle();
            }
        }
        pFileName.setString(0, listPictureData.get(p).getFilename());
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_GetPicture_V30(uid, pFileName, pBuffer, PictureResultAdapter.SIZE, retLen);
        if (false == ret) {
            Log.e(TAG, "NET_DVR_GetPicture_V30 failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            image.setImageResource(R.drawable.not_found);
        } else {
            int length = retLen.getValue();
            if (a == 0){
                map = byteToBitmap(pBuffer.getByteArray(0, length));
                image.setImageBitmap(Util.scaleBitmap(map, 0.4f));
            }else {
                map1 = byteToBitmap(pBuffer.getByteArray(0, length));
                image.setImageBitmap(Util.scaleBitmap(map1, 0.4f));
            }
        }
    }


    public static Bitmap byteToBitmap(byte[] imgByte)
    {
        InputStream input = null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        input = new ByteArrayInputStream(imgByte);
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                input, null, options));
        bitmap = (Bitmap) softRef.get();
        if (imgByte != null) {
            imgByte = null;
        }
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 图片多线程导出
     */
    Handler mhandler = new Handler()
    {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    Util.show_Toast(PictureResultActivity.this, "第" + position + "张图片导入成功",200);
                }
                break;
                case 1:
                    Util.show_Toast(PictureResultActivity.this, "图片全部导入完成",1000);
                    break;
                case 2:
                    Util.show_Toast(PictureResultActivity.this, "请选择导入图片",1000);
                    break;
                case 3:
                    Util.show_Toast(PictureResultActivity.this, "U盘存储空间不足",1000);
                    break;
                case 4:
                    Util.show_Toast(PictureResultActivity.this, "U盘已断开连接",1000);
                    break;
                case 5:
                    getFileDir(rootPath);
                    break;
                case 6:
                    Util.show_Toast(PictureResultActivity.this, "取消了添加文件",1000);
                    break;
                case 7:
                    Util.show_Toast(PictureResultActivity.this, "请插入U盘",1000);
                    break;
                default:
                    break;
            }
        }
    };
    private ProgressDialog dialog;
    int xh_count =0;
    private void initDialog()
    {
        xh_count = 0;
        dialog = new ProgressDialog(PictureResultActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle("提示:");
        dialog.setMessage("文件正在导出");
        dialog.setMax(pictureExploreList.size());
        dialog.setCancelable(false);
        dialog.show();
        new Thread(){
            @Override
            public void run() {
                try{
                    while(xh_count <= pictureExploreList.size()){
                        showexplore(xh_count);
                        dialog.setProgress(xh_count++);
                        mhandler.sendEmptyMessage(5);
                        if (paths.size() <= 0){
                            getFileDir(pathFile);
                            if(paths.size() == 0){
                                mhandler.sendEmptyMessage(4);
                                break;
                            }
                        }
                    }
                    dialog.cancel();
                }catch (Exception e){
                    dialog.cancel();
                }
            }
        }.start();
    }

    private  void showexplore(int a)
    {
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_GetPicture(userId, pictureExploreList.get(a).filename, pathFile + "/" + pictureExploreList.get(a).filename+".jpg");
        if (!ret) {
            Log.e(TAG, "NET_DVR_GetPicture failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError());
        } else {
            Log.e(TAG, "NET_DVR_GetPicture , SuccessFul!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}