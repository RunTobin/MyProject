package com.tutk.P2PCam264;

import com.videogo.openapi.EZOpenSDK;

import android.app.Application;

public class Myapplication extends Application{
	
	private static String APP_KEY="90b7169a5c3044a385655a71b4eac598";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//≥ı ºªØSDK
		EZOpenSDK.initLib(this, APP_KEY, "");
	}
}
