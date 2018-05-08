package com.linq.xinansmart.guide;

import java.util.List;

import com.videogo.exception.BaseException;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZAccessToken;
import com.videogo.openapi.bean.EZCameraInfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

public class EzvizBroadcastReceiver extends BroadcastReceiver {

	EZOpenSDK mEzOpenSDK=EZOpenSDK.getInstance();
	@Override
	public void onReceive(Context context, Intent intent) {
    //ÅÐ¶ÏµÇÂ¼¹ã²¥³É¹¦
		if(TextUtils.equals(intent.getAction(), "com.videogo.action.OAUTH_SUCCESS_ACTION")){
			EZAccessToken accessToken=EZOpenSDK.getInstance().getEZAccessToken();
			Log.e("accessToken", accessToken+"");
		//	getlist();
			
		}
	}
	
	private void getlist() {
		// TODO Auto-generated method stub
	
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
				    List<EZCameraInfo>result = null;

				result = mEzOpenSDK.getCameraList(0, 20);
				Log.e("size", result.size()+"");
				} catch (BaseException e) {
				//mErrorCode = e.getErrorCode();
				Log.e("size", "121412341234");
				}
			}
		}).start();
	
	
	}

}
