package com.huang.bchtsystem.View.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hikvision.netsdk.HCNetSDK;
import com.huang.bchtsystem.Manager.Task;
import com.huang.bchtsystem.Manager.Worker;
import com.huang.bchtsystem.Model.PictureResultData;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.jna.HCNetSDKByJNA;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;
import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.huang.bchtsystem.Web.ApiServiceSupport.TAG;

/**
 * Created by admin on 2017/4/17.
 */

public class PictureResultAdapter extends BaseAdapter implements View.OnClickListener{

    public static final int SIZE = 2048 * 2048 * 8;
    private Context context;
    private List<PictureResultData> pictureDataList;
    private List<PictureResultData> pictureExploreList ;
    private OnClickFileNameListener onClickFileNameListener;

    public static HashMap<Integer, Boolean> isSelected ;
    private int uid;
    private ImageView imageView;
    private  int index ;
    int VIEW_COUNT = 4;
    int Yecount = 0 ;

    public boolean ischeckall() {
        return ischeckall;
    }

    public void setIscheckall(boolean ischeckall) {
        this.ischeckall = ischeckall;
        initboolean();
    }

    private boolean ischeckall = false;

    public ImageView getImageView1() {
        return imageView1;
    }

    public void setImageView1(ImageView imageView1) {
        this.imageView1 = imageView1;
    }

    private ImageView imageView1;


    private Memory pFileName = new Memory(128);
    private Memory pBuffer = new Memory(SIZE);
    Worker target;

    public PictureResultAdapter(Context context, List<PictureResultData> pictureDataList ,List<PictureResultData> pictureExploreList) {
        this.context = context;
        this.pictureDataList = pictureDataList;
        this.pictureExploreList = pictureExploreList;
    }
    private void init_1() {
        if(isSelected == null && pictureDataList != null){
            isSelected = new HashMap<>();
            for (int i = 0; i < pictureDataList.size(); i++) {
                isSelected.put(i, false);
            }
        }

    }
    private void initboolean()
    {
        pictureExploreList.clear();
        if (ischeckall)
        {
            for (int i = 0; i < pictureDataList.size(); i++) {
                isSelected.put(i, true);
                addData(i);
            }
        }else {
            for (int i = 0; i < pictureDataList.size(); i++) {
                isSelected.put(i, false);
            }
        }
    }

    public PictureResultAdapter(Context context) {
        this.context = context;
    }

    public void setPictureDataList( List<PictureResultData> pictureDataList ,int index ,int Yecount)
    {
        this.pictureDataList = pictureDataList;
        this.index = index;
        this.Yecount = Yecount;
        init_1();
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public void setImageView(ImageView imageView)
    {
        this.imageView = imageView;
    }

    public void setTarget(Worker target) {
        this.target = target;
    }

    public int getUid() {
        return uid;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Worker getTarget() {
        return target;
    }

    public Memory getPFileName() {
        return pFileName;
    }

    public Memory getPBuffer() {
        return pBuffer;
    }

    public OnClickFileNameListener getOnClickFileNameListener() {
        return onClickFileNameListener;
    }
    public void PictureNotFound()
    {
        onClickFileNameListener.PictureNotFound();
    }

    @Override
    public int getCount() {
        int position = 0;
        if (index < Yecount)
        {
            position= VIEW_COUNT;
        }else if (index == Yecount){
            if (pictureDataList.size()% VIEW_COUNT == 0)
                position= VIEW_COUNT;
            else
                position = pictureDataList.size()% VIEW_COUNT;
        }
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;//pictureDataList == null ? null : pictureDataList.get(position)
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder viewHolder = null;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView ==null){
            viewHolder = new ViewHolder();
            // 获取组件布局
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pictureresult, null);
            viewHolder.item_check = (CheckBox) convertView.findViewById(R.id.item_check);
            viewHolder.item_PictureNum = (TextView) convertView.findViewById(R.id.item_picNum);
            viewHolder.item_PictureFileName = (TextView) convertView.findViewById(R.id.item_picFile);
            viewHolder.item_PictureTime = (TextView) convertView.findViewById(R.id.item_pic_Time);
            viewHolder.item_byFileType = (TextView)convertView.findViewById(R.id.item_byFileType);

            viewHolder.item_sLicense = (TextView)convertView.findViewById(R.id.item_sLicense);
            viewHolder.item_type = (TextView)convertView.findViewById(R.id.item_type);
            viewHolder.item_DaXi = (TextView)convertView.findViewById(R.id.item_DaXi);
            // 这里要注意，是使用的tag来存储数据的。
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position+(index-1)*VIEW_COUNT <= pictureDataList.size())
           if (isSelected != null)
           {
               viewHolder.item_check.setChecked(isSelected.get(position+(index-1)*VIEW_COUNT));
               if (isSelected.get(position+(index-1)*VIEW_COUNT))
               {
                   if (pictureExploreList==null && pictureExploreList.size()==0)
                   {
                       addData(position+(index-1)*VIEW_COUNT);
                   }
               }
           }
           viewHolder.item_PictureNum.setText(pictureDataList.get(position+(index-1)*VIEW_COUNT).num);
           viewHolder.item_PictureFileName.setText(pictureDataList.get(position+(index-1)*VIEW_COUNT).filename);
           viewHolder.item_PictureTime.setText(pictureDataList.get(position+(index-1)*VIEW_COUNT).filetime);
           viewHolder.item_byFileType.setText(pictureDataList.get(position+(index-1)*VIEW_COUNT).byFileType);

           viewHolder.item_sLicense.setText(pictureDataList.get(position+(index-1)*VIEW_COUNT).sLicense);
           viewHolder.item_type.setText(gettype(pictureDataList.get(position+(index-1)*VIEW_COUNT).byRecogResult));
           viewHolder.item_DaXi.setText(pictureDataList.get(position+(index-1)*VIEW_COUNT).dwFileSize);
           viewHolder.item_check.setOnClickListener(new OnClickCheckListener(position+(index-1)*VIEW_COUNT,this));
        return convertView;
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder{
        CheckBox item_check = null;
        TextView item_PictureNum = null;
        TextView item_PictureFileName = null;
        TextView item_PictureTime = null;
        TextView item_byFileType = null;
        TextView item_sLicense = null;
        TextView item_type = null;
        TextView item_DaXi = null;
    }

    private class OnClickCheckListener implements View.OnClickListener
    {
        PictureResultAdapter adapter;
        private int p;

        public OnClickCheckListener(int num, PictureResultAdapter adapter)
        {
          this.p = num;
        }
        @Override
        public void onClick(View v) {
            Integer tag  = (Integer) v.getTag();
            if(isSelected.get(p)){//先判断isSelected中是否已经选中
                //选中就置为false，即不选中
                isSelected.put(p, false);
                delete(p);
            }else{
                //选中
                isSelected.put(p, true);
                addData(p);
            }
//            notifyDataSetChanged();
        }
    }

    private void addData(int p)
    {
        PictureResultData data = new PictureResultData();
        data.num = Integer.toString(p);
        data.filename = pictureDataList.get(p).filename;
        data.filetime = pictureDataList.get(p).filetime ;
        data.byFileType = pictureDataList.get(p).byFileType ;
        data.sLicense = pictureDataList.get(p).sLicense;
        data.byRecogResult = pictureDataList.get(p).byRecogResult;
        data.dwFileSize = pictureDataList.get(p).dwFileSize;
        pictureExploreList.add(data);
    }
    private void delete(int p)
    {
        for (int i =0 ; i<pictureExploreList.size();i++){
            if ( p == Integer.parseInt(pictureExploreList.get(i).num))
            {
                pictureExploreList.remove(i);
            }
        }
    }

    public class OnClickFileNameListener implements View.OnClickListener,Task, Handler.Callback
    {
        private String fileName;
        private int uid;
        private ImageView imageView;
        private Memory pFileName;
        private Memory pBuffer;
        IntByReference retLen = new IntByReference();
        boolean  ret = false;
        int m= 0;
        PictureResultAdapter adapter;

        public OnClickFileNameListener(String fileName,int p, PictureResultAdapter adapter)
        {
            this.imageView = adapter.getImageView();
            this.fileName = fileName;
            this.uid = adapter.getUid();
            this.pFileName = adapter.getPFileName();
            this.pBuffer = adapter.getPBuffer();
            this.m = p ;
            pFileName.setString(0,fileName);
        }

        public OnClickFileNameListener()
        {

        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }


        private Bitmap scaleBitmap(Bitmap origin, float ratio) {
            if (origin == null || ratio == (float) 1.0) {
                return null;
            }
            int width = origin.getWidth();
            int height = origin.getHeight();
            Matrix matrix = new Matrix();
            matrix.preScale(ratio, ratio);
            Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, true);
            origin.recycle();
            return newBM;
        }

        public void PictureNotFound()
        {

            imageView.setVisibility(View.VISIBLE);
            this.imageView.setImageResource(R.drawable.not_found);
        }

        public void onClick(View v)
        {
            //String strSavedFileName = Util.toValidString("/data/data/"+new String(fn));
            //savedFileName.write(0,strSavedFileName.getBytes(),0,strSavedFileName.length());
            onTaskBegin();
            target.push(this);

        }

        @Override
        public void onTaskBegin()
        {
            imageView.setVisibility(View.GONE);
        }
        @Override
        public void onTask()
        {
            ret =  HCNetSDKJNAInstance.getInstance().NET_DVR_GetPicture_V30(uid, pFileName,pBuffer, PictureResultAdapter.SIZE, retLen);
            Log.e(TAG, "m" + m );
            Log.e(TAG, "fileName" + fileName );
        }

        @Override
        public void onTaskFinished()
        {
            Looper looper = Looper.getMainLooper();
            Handler h = new Handler(looper,this);
            Message msg = new Message();
            msg.setTarget(h);
            h.sendMessage(msg);
        }

        @Override
        public void onError(String error)
        {

        }

        @Override
        public boolean handleMessage(Message msg)
        {

            imageView.setVisibility(View.VISIBLE);

            if( false == ret )
            {
                Log.e(TAG, "NET_DVR_GetPicture_V30 failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
                this.imageView.setImageResource(R.drawable.not_found);
            }
            else
            {
                int length = retLen.getValue();
                Bitmap map = BitmapFactory.decodeByteArray(pBuffer.getByteArray(0,length),0,length);
                this.imageView.setImageBitmap(scaleBitmap(map,0.2f));
            }
            return true;
        }
    }
    private  String type = null;
    private String gettype(int num)
    {
        if (num == 0){
            type = "未知";
        }else if (num == 1){
            type = "客车";
        } else if (num == 2){
            type = "货车";
        }else if (num == 3){
            type = "轿车";
        }else if (num == 4){
            type = "面包车";
        }
        return type ;
    }

}
