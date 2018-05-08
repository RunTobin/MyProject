package com.linq.xinansmart.ui_fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.j256.ormlite.stmt.query.In;
import com.linq.xinansmart.R;

import com.linq.xinansmart.guide.GuideActivity;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Mode;
import com.linq.xinansmart.ui_activity.EditResourceActivity;
import com.linq.xinansmart.ui_activity.ModifyDeviceNameActivity;
import com.linq.xinansmart.ui_activity.VideoPlayActivity;
import com.linq.xinansmart.ui_fragment.ScenceFragment.GetModListThread;
import com.tutk.P2PCam264.MainActivity;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZCameraInfo;

public class VideoFragment extends SherlockFragment implements OnItemClickListener ,OnItemLongClickListener{
	private Concenter concenter = null;
	public VideoFragment(Concenter concenter) {
		super();
		this.concenter = concenter;

	}
	
	private ImageButton btn_online = null;
	private ListView cameralist ;
	public static List<EZCameraInfo> mCameraInfoList = new ArrayList<EZCameraInfo>();
	private EZCameraListAdapter mAdapter = null;
	EZOpenSDK mEzOpenSDK;
	int mErrorCode;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.cameralist_page, null);
		
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		cameralist = (ListView) view.findViewById(R.id.camera_listview);
	    mEzOpenSDK = EZOpenSDK.getInstance();
	    btn_online = (ImageButton) getActivity().findViewById(R.id.btn_online);
		btn_online.setVisibility(View.GONE);
		
		mAdapter=new EZCameraListAdapter();
		cameralist.setAdapter(mAdapter);
 		new GetModListThread().start();	 
 		
        cameralist.setOnItemClickListener(this);
        cameralist.setOnItemLongClickListener(this);
	}
	
	public final int UPDATE = 0;
	public Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE:
				mAdapter.notifyDataSetChanged();
				break;
			}
		};
	};
	public class GetModListThread extends Thread {

		@Override
		public void run() {
			super.run();
			getCameraList();
		}
	}
	
	// 获取摄像头列表
	public  void getCameraList() {
		try {
			mCameraInfoList = mEzOpenSDK.getCameraList(0, 20);
			Message msg = mhandler.obtainMessage();
			mhandler.sendEmptyMessage(UPDATE);
		} catch (BaseException e) {
			mErrorCode = e.getErrorCode();
			Log.e("size", "121412341234");
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == -1 && requestCode == 0) {
			// Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();
			// modeList = modeManager.getInstance(concenter).getModeList();
			// upData();
			new GetModListThread().start();
		}

	}
	private Handler handler;
	private Runnable runnable;
	

	/**
	  * 摄像头设配器
	  * @author Administrator
	  *
	  */
	 EZCameraInfo cameraInfo =null;
    class EZCameraListAdapter extends BaseAdapter implements View.OnClickListener,OnLongClickListener{
		
		@Override
		public int getCount() {
			if (mCameraInfoList != null && mCameraInfoList.size() != 0) {
				return mCameraInfoList.size();
			}
			return 0;
		}

		@Override
		public EZCameraInfo getItem(int position) {
			EZCameraInfo item = null;
	        if (position >= 0 && getCount() > position) {
	            item = mCameraInfoList.get(position);
	        }
	        return item;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			 // 自定义视图
	        ViewHolder viewHolder = null;
	        if (convertView == null) {
	            viewHolder = new ViewHolder();

	         // 获取list_item布局文件的视图
	            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.cameralist_small_item, null);

	         // 获取控件对象
	            viewHolder.playBtn = (ImageView) convertView.findViewById(R.id.item_play_btn);
	            viewHolder.reLayout = (RelativeLayout) convertView.findViewById(R.id.camera_item_rl);
	            viewHolder.cameraNameTv = (TextView) convertView.findViewById(R.id.camera_name_tv);
	            viewHolder.cameraStaTeView =(TextView) convertView.findViewById(R.id.camera_state);
	            // 设置点击图标的监听响应函数 
	            convertView.setTag(viewHolder);
	        } else {
	            viewHolder = (ViewHolder) convertView.getTag();
	        }
	        
	        // 设置position
	        viewHolder.reLayout.setTag(position);

	        cameraInfo = (EZCameraInfo) getItem(position);
	        if(cameraInfo != null) {
	            if (cameraInfo.getOnlineStatus() == 0) {
	                viewHolder.cameraStaTeView.setText("不在线");
	            } else {
	                viewHolder.playBtn.setVisibility(View.VISIBLE);
//	                viewHolder.playBtn.setOnClickListener(this);
//	                viewHolder.reLayout.setOnClickListener(this);
//	                viewHolder.reLayout.setOnLongClickListener(this);
	                viewHolder.cameraStaTeView.setText("在线");
	            }
	          
	            viewHolder.cameraNameTv.setText(cameraInfo.getCameraName());
	           
	        }
	         
	        return convertView;
		}
		   /**
	     * 自定义控件集合
	     * @author Administrator
	     *
	     */
	   public class ViewHolder {
	        public ImageView playBtn;
	        public RelativeLayout  reLayout;
	        public TextView cameraNameTv;
	        public TextView  cameraStaTeView;
	    }
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.camera_item_rl:
				Toast.makeText(getActivity(), "this is play page", Toast.LENGTH_SHORT).show();				
				Bundle bundle = new Bundle();
				bundle.putString("camera", cameraInfo.getCameraId());
				Intent intent = new Intent();
				intent.setClass(getActivity(), VideoPlayActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
				    break;
				default:
					break;
			}
		}
		/**
		 * 弹出编辑对话框，进行摄像头名称编辑
		 */
		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.camera_item_rl) {
				  AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
					View view = LayoutInflater.from(getActivity()).inflate(
							R.layout.editdialog, null);
					builder1.setView(view);
					builder1.setTitle("设备名称编辑");
					final AlertDialog dialog = builder1.create();
					dialog.show();
				    
					Button edit = (Button) view.findViewById(R.id.edit);
					edit.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							Toast.makeText(getActivity(), "this is edit reqiment name", Toast.LENGTH_SHORT).show();
							Intent  intent = new Intent(getActivity(), ModifyDeviceNameActivity.class);
		                    intent.putExtra("DEVICE_SERIAL", cameraInfo.getDeviceSerial());
		                    intent.putExtra("EZCAMERA_INFO", cameraInfo);
		                    startActivityForResult(intent, 0);
						}
					});
			}
			return true;
		}
	}	
	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(cameralist !=null){
			cameraInfo = mAdapter.getItem(position);
			Bundle bundle = new Bundle();
			bundle.putString("camera", cameraInfo.getCameraId());
			Intent intent = new Intent();
			intent.setClass(getActivity(), VideoPlayActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		    cameraInfo = mAdapter.getItem(position);
		    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
			view = LayoutInflater.from(getActivity()).inflate(
					R.layout.editdialog, null);
			builder1.setView(view);
			builder1.setTitle("设备名称编辑");
			final AlertDialog dialog = builder1.create();
			dialog.show();
		    
			Button edit = (Button) view.findViewById(R.id.edit);
			edit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					Intent  intent = new Intent(getActivity(), ModifyDeviceNameActivity.class);
                    intent.putExtra("DEVICE_SERIAL", cameraInfo.getDeviceSerial());
                    intent.putExtra("EZCAMERA_INFO", cameraInfo);
                    startActivityForResult(intent, 0);
				}
			});
		
		return true;
	}
	
}
