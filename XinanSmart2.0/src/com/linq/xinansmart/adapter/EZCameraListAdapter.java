//
//package com.linq.xinansmart.adapter;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.linq.xinansmart.R;
//import com.videogo.openapi.bean.EZCameraInfo;
//import com.videogo.universalimageloader.core.DisplayImageOptions;
//import com.videogo.universalimageloader.core.ImageLoader;
//import com.videogo.universalimageloader.core.assist.FailReason;
//import com.videogo.universalimageloader.core.listener.ImageLoadingListener;
//import com.videogo.util.LogUtil;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//
///**
// *  摄像头列表适配器  (海康摄像头)
// * @author Administrator
// *
// */
//public class EZCameraListAdapter extends BaseAdapter {
//   
//    private Context mContext = null;
//    private List<EZCameraInfo> mCameraInfoList = null;
//    
//    public EZCameraListAdapter(Context context,List<EZCameraInfo> cameralist) {
//        this.mContext = context; 
//        this.mCameraInfoList=cameralist;
//    }
//    
//    
//    public static class ViewHolder {
//        public ImageView iconIv;
//
//        public ImageView playBtn;
//
//        public ImageView offlineBtn;
//
//        public TextView cameraNameTv;
//        
//        public ImageButton cameraDelBtn;
//
//        public ImageButton alarmListBtn;
//        
//        public ImageButton remoteplaybackBtn;
//
//        public ImageButton setDeviceBtn;
//
//        public View itemIconArea;
//
//        public ImageView offlineBgBtn;
//        
//        public ImageButton deleteBtn;
//        
//        public ImageButton devicePicBtn;
//        
//        public ImageButton deviceVideoBtn;
//        
//        public View deviceDefenceRl;
//        public ImageButton deviceDefenceBtn;
//    }
//    
//   
//    @Override
//    public int getCount() {
//        return mCameraInfoList.size();
//    }
//
//    
//    @Override
//    public EZCameraInfo getItem(int position) {
//    	EZCameraInfo item = null;
//        if (position >= 0 && getCount() > position) {
//            item = mCameraInfoList.get(position);
//        }
//        return item;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        // TODO Auto-generated method stub
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//    	 // 自定义视图
//        ViewHolder viewHolder = null;
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//
//         // 获取list_item布局文件的视图
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.cameralist_small_item, null);
//
//         // 获取控件对象
//            viewHolder.iconIv = (ImageView) convertView.findViewById(R.id.item_icon);
//            viewHolder.iconIv.setDrawingCacheEnabled(false);
//            viewHolder.iconIv.setWillNotCacheDrawing(true);
//            viewHolder.playBtn = (ImageView) convertView.findViewById(R.id.item_play_btn);
//
//            viewHolder.offlineBtn = (ImageView) convertView.findViewById(R.id.item_offline);
//            viewHolder.cameraNameTv = (TextView) convertView.findViewById(R.id.camera_name_tv);
//            viewHolder.cameraDelBtn = (ImageButton) convertView.findViewById(R.id.camera_del_btn);
//            viewHolder.alarmListBtn = (ImageButton) convertView.findViewById(R.id.tab_alarmlist_btn);            
//            viewHolder.remoteplaybackBtn = (ImageButton) convertView.findViewById(R.id.tab_remoteplayback_btn);
//            viewHolder.setDeviceBtn = (ImageButton) convertView.findViewById(R.id.tab_setdevice_btn);
//            viewHolder.offlineBgBtn = (ImageView) convertView.findViewById(R.id.offline_bg);
//            viewHolder.itemIconArea = convertView.findViewById(R.id.item_icon_area);
//            viewHolder.deleteBtn = (ImageButton) convertView.findViewById(R.id.camera_del_btn);
//            viewHolder.devicePicBtn = (ImageButton) convertView.findViewById(R.id.tab_devicepicture_btn);
//            viewHolder.deviceVideoBtn = (ImageButton) convertView.findViewById(R.id.tab_devicevideo_btn);
//            viewHolder.deviceDefenceRl = convertView.findViewById(R.id.tab_devicedefence_rl);
//            viewHolder.deviceDefenceBtn = (ImageButton) convertView.findViewById(R.id.tab_devicedefence_btn);
//            
//         
//            
//            // 设置控件集到convertView
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        
//        // 设置position
//        viewHolder.playBtn.setTag(position);
//        viewHolder.remoteplaybackBtn.setTag(position);
//        viewHolder.alarmListBtn.setTag(position);
//        viewHolder.setDeviceBtn.setTag(position);
//        viewHolder.deleteBtn.setTag(position);
//        viewHolder.devicePicBtn.setTag(position);
//        viewHolder.deviceVideoBtn.setTag(position);
//        viewHolder.deviceDefenceBtn.setTag(position);
//
//
//        final EZCameraInfo cameraInfo = getItem(position);
//        if(cameraInfo != null) {
//            if (cameraInfo.getOnlineStatus() == 0) {
//                viewHolder.offlineBtn.setVisibility(View.VISIBLE);
//                viewHolder.offlineBgBtn.setVisibility(View.VISIBLE);
//                viewHolder.playBtn.setVisibility(View.GONE);
//                viewHolder.deviceDefenceRl.setVisibility(View.INVISIBLE);
//            } else {
//                viewHolder.offlineBtn.setVisibility(View.GONE);
//                viewHolder.offlineBgBtn.setVisibility(View.GONE);
//                viewHolder.playBtn.setVisibility(View.VISIBLE);
//                viewHolder.deviceDefenceRl.setVisibility(View.VISIBLE);
//            }
//
//         // 如果是分享设备，隐藏消息列表按钮和设置按钮
//            if(cameraInfo.getShareStatus() != 0 && cameraInfo.getShareStatus() != 1) {
//                viewHolder.alarmListBtn.setVisibility(View.GONE);
//                viewHolder.setDeviceBtn.setVisibility(View.GONE);
//            } else {
//                viewHolder.alarmListBtn.setVisibility(View.VISIBLE);
//                viewHolder.setDeviceBtn.setVisibility(View.VISIBLE);
//            }
//            
//            viewHolder.cameraNameTv.setText(cameraInfo.getCameraName());   
//            viewHolder.iconIv.setVisibility(View.INVISIBLE);
//        }
//        
//        return convertView;
//    }
//
//}
