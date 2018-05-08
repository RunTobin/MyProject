package com.linq.xinansmart.guide;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.UpGradeVersionManager;
import com.linq.xinansmart.model.Version;
import com.tutk.P2PCam264.MainActivity;

public class Splash extends Activity {
	boolean isNotFirst = false;
	UpGradeVersionManager manager = null;
	ProgressBar updat_ProgressBar = null;
	int progress = 0;
	AlertDialog alertDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 强制竖屏
		setContentView(R.layout.activity_splash);
		SharedPreferences sp = getSharedPreferences("guide", MODE_PRIVATE);
		isNotFirst = sp.getBoolean("isExit", false);
		
		mHandler.sendEmptyMessageDelayed(1, 1000);
		creatLoadingDialog();
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				if (isNotFirst) {
					if (isNetworkAvailable()) {
						try {
							manager = new UpGradeVersionManager(Splash.this,
									this);
							Version newVersion = manager.getNewVersion();// 返回服务器版本号
							manager.Update();
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(), "网络超时或异常，请检查网络", Toast.LENGTH_LONG).show();
							finish();
						}
					} else {
						Toast.makeText(Splash.this, "网络异常,请检查网络！",
								Toast.LENGTH_SHORT).show();
						finish();
					}
				} else {
					Intent i = new Intent(Splash.this, GuideActivity.class);
					startActivity(i);
					finish();
				}
				break;
			case 2:
				Intent i = new Intent(Splash.this, MainActivity.class);
				startActivity(i);
				finish();
				break;
			case 3:
				alertDialog.show();
				updat_ProgressBar.setProgress(msg.arg1);
				break;
			case 4:
				alertDialog.dismiss();
				manager.installApk();
			case 5:
				Toast.makeText(getApplicationContext(), "网络超时或异常，请检查网络", Toast.LENGTH_LONG).show();
				finish();
			}
		}
	};

	private void creatLoadingDialog() {
		LayoutInflater inflater = getLayoutInflater();
		View v = inflater.from(this).inflate(R.layout.soft_update_progress,
				null);
		updat_ProgressBar = (ProgressBar) v.findViewById(R.id.update_progress);
		alertDialog = new Builder(this).setTitle("正在更新").setView(v).create();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo != null) {
			return true;
		}
		// return activeNetworkInfo != null;
		return false;
	}
}
