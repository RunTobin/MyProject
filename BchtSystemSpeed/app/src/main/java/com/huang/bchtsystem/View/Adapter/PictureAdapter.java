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
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;
import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.List;

import static com.huang.bchtsystem.Web.ApiServiceSupport.TAG;

/**
 * Created by admin on 2017/4/17.
 */

public class PictureAdapter extends BaseAdapter {

    public static final int SIZE = 1024*1024*4;
    private Context context;
    private List<PictureResultData> pictureDataList;
    private OnClickFileNameListener onClickFileNameListener;

    private int uid;
    private ImageView imageView;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private TextView textView;

    private Memory pFileName = new Memory(128);
    private Memory pBuffer = new Memory(SIZE);

    Worker target;

    public PictureAdapter(Context context, List<PictureResultData> pictureDataList) {
        this.context = context;
        this.pictureDataList = pictureDataList;
    }
    public PictureAdapter(Context context) {
        this.context = context;
    }

    public void setPictureDataList( List<PictureResultData> pictureDataList )
    {
        this.pictureDataList = pictureDataList;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public void setImageView(ImageView imageView)
    {
        this.imageView = imageView;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
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

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public TextView getTextView() {
        return textView;
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
        return pictureDataList == null ? 0:pictureDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return pictureDataList == null ? null : pictureDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView ==null){
            viewHolder = new ViewHolder();
            // 获取组件布局
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pictureresult, null);
            viewHolder.item_PictureNum = (TextView) convertView.findViewById(R.id.item_picNum);
            viewHolder.item_PictureFileName = (TextView) convertView.findViewById(R.id.item_picFile);
            viewHolder.item_PictureTime = (TextView) convertView.findViewById(R.id.item_pic_Time);
            viewHolder.item_byFileType = (TextView)convertView.findViewById(R.id.item_byFileType);
            // 这里要注意，是使用的tag来存储数据的。
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.item_PictureFileName.setClickable(true);
        String fileName = pictureDataList.get(position).filename;
        viewHolder.item_PictureFileName.setOnClickListener(new OnClickFileNameListener(fileName,this));
        viewHolder.item_PictureNum.setText(pictureDataList.get(position).num);
        viewHolder.item_PictureFileName.setText(pictureDataList.get(position).filename);
        viewHolder.item_PictureTime.setText(pictureDataList.get(position).filetime);
        viewHolder.item_byFileType.setText(pictureDataList.get(position).sLicense);
        return convertView;
    }

    public class ViewHolder{
        TextView item_PictureNum = null;
        TextView item_PictureFileName = null;
        TextView item_PictureTime = null;
        TextView item_byFileType = null;
    }

    private class OnClickFileNameListener implements View.OnClickListener,Task, Handler.Callback
    {

        private byte[] fileName;
        private int uid;
        private ImageView imageView;
        private LinearLayout linearLayout;
        private ProgressBar progressBar;
        private TextView textView;
        private Memory pFileName;
        private Memory pBuffer;
        IntByReference retLen = new IntByReference();
        boolean  ret = false;

        PictureAdapter adapter;

        public OnClickFileNameListener(String fileName, PictureAdapter adapter)
        {
            this.linearLayout = adapter.getLinearLayout();
            this.imageView = adapter.getImageView();
            this.fileName = fileName.getBytes();
            this.textView = adapter.getTextView();
            this.progressBar = adapter.getProgressBar();
            this.uid = adapter.getUid();
            this.pFileName = adapter.getPFileName();
            this.pBuffer = adapter.getPBuffer();
            pFileName.setString(0,fileName);
        }

        public OnClickFileNameListener()
        {

        }

        public void setFileName(byte[] fileName) {
            this.fileName = fileName;
            pFileName.write(0, fileName, 0, fileName.length);
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public void setLinearLayout(LinearLayout linearLayout) {
            this.linearLayout = linearLayout;
        }

        public void setProgressBar(ProgressBar progressBar) {
            this.progressBar = progressBar;
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
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
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
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
        }
        @Override
        public void onTask()
        {
            ret =  HCNetSDKJNAInstance.getInstance().NET_DVR_GetPicture_V30(uid, pFileName,pBuffer, PictureAdapter.SIZE, retLen);
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
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);

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
}
