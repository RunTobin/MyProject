package com.linq.xinansmart.ui_fragment;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;
import com.linq.xinansmart.R;
import com.tutk.IOTC.AVIOCTRLDEFs;
import com.tutk.IOTC.AVIOCTRLDEFs.SMsgAVIoctrlGetSupportStreamReq;
import com.tutk.IOTC.Camera;
import com.tutk.IOTC.CameraListener;
import com.tutk.IOTC.IReceiveSnapshotListener;
import com.tutk.IOTC.IRegisterIOTCListener;
import com.tutk.Logger.Glog;
import com.tutk.P2PCam264.DeviceInfo;
import com.tutk.P2PCam264.MainActivity;
import com.tutk.P2PCam264.MyCamera;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

public class LiveView_Fragment extends Fragment implements
		ViewSwitcher.ViewFactory, IRegisterIOTCListener, CameraListener,
		IReceiveSnapshotListener {
	public List<MyCamera> CameraList = null;
	public List<DeviceInfo> DeviceList = null;
	private Context mContext;
	private static boolean wait_receive = true;
	private BitmapDrawable bg;
	private BitmapDrawable bgSplit;
	private String mDevUID;
	private String mDevUUID;
	private String mConnStatus = "";
	private String mFilePath = "";
	private int mSelectedChannel;
	private MyCamera mCamera = null;
	private DeviceInfo mDevice = null;
	private View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContext = getActivity();
		bg = (BitmapDrawable) getResources().getDrawable(R.drawable.bg_striped);
		bgSplit = (BitmapDrawable) getResources().getDrawable(
				R.drawable.bg_striped_split_img);

		Bundle bundle = getArguments();
		mDevUID = bundle.getString("dev_uid");
		mDevUUID = bundle.getString("dev_uuid");
		mConnStatus = bundle.getString("conn_status");
		mSelectedChannel = bundle.getInt("camera_channel");

		Glog.E("DEBUG", "START :" + mDevUID);

		for (MyCamera camera : MainActivity.CameraList) {
			if (mDevUID.equalsIgnoreCase(camera.getUID())
					&& mDevUUID.equalsIgnoreCase(camera.getUUID())) {
				mCamera = camera;
				break;
			}
		}

		for (DeviceInfo dev : MainActivity.DeviceList) {

			if (mDevUID.equalsIgnoreCase(dev.UID)
					&& mDevUUID.equalsIgnoreCase(dev.UUID)) {
				mDevice = dev;
				break;
			}
		}

		if (mCamera != null) {

			mCamera.registerIOTCListener(this);
			mCamera.SetCameraListener(this);

			if (!mCamera.isSessionConnected()) {
				Log.e("______", "_________");
				mCamera.connect(mDevUID);
				mCamera.start(Camera.DEFAULT_AV_CHANNEL, mDevice.View_Account,
						mDevice.View_Password);
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL,
						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_REQ,
						SMsgAVIoctrlGetSupportStreamReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL,
						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVINFO_REQ,
						AVIOCTRLDEFs.SMsgAVIoctrlDeviceInfoReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL,
						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETAUDIOOUTFORMAT_REQ,
						AVIOCTRLDEFs.SMsgAVIoctrlGetAudioOutFormatReq
								.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL,
						AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ,
						AVIOCTRLDEFs.SMsgAVIoctrlTimeZone.parseContent());

			}
			if (null != rootView) {
	            ViewGroup parent = (ViewGroup) rootView.getParent();
	            if (null != parent) {
	                parent.removeView(rootView);
	            }
	        } else {
	            rootView = inflater.inflate(R.layout.fragment_liveview, null);
	          
	        }
		}
		return rootView;
	}

	
	
	

	@Override
	public void receiveSnapshot(Bitmap arg0) {
		// TODO Auto-generated method stub
		//onCreateView(inflater, container, savedInstanceState)
		//getActivity().setc(layoutResID)
		
	}

	@Override
	public void OnSnapshotComplete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveChannelInfo(Camera arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		Log.e("ChannelInfo", arg1+"");
	}

	@Override
	public void receiveFrameData(Camera arg0, int arg1, Bitmap arg2) {
		// TODO Auto-generated method stub
		Log.e("FrameData", arg1+"");

	}

	@Override
	public void receiveFrameDataForMediaCodec(Camera arg0, int arg1,
			byte[] arg2, int arg3) {
		// TODO Auto-generated method stub
		Log.e("aForMediaCodec", arg1+"");

	}

	@Override
	public void receiveFrameInfo(Camera arg0, int arg1, long arg2, int arg3,
			int arg4, int arg5, int arg6) {
		// TODO Auto-generated method stub
		Log.e("FrameInfo", arg1+"");
	}

	@Override
	public void receiveIOCtrlData(Camera arg0, int arg1, int arg2, byte[] arg3) {
		// TODO Auto-generated method stub
		Log.e("IOCtrlData", arg1+"");
	}

	@Override
	public void receiveSessionInfo(Camera arg0, int arg1) {
		// TODO Auto-generated method stub
		Log.e("SessionInfo", arg1+"");
	}

	@Override
	public View makeView() {
		// TODO Auto-generated method stub
		return null;
	}
}