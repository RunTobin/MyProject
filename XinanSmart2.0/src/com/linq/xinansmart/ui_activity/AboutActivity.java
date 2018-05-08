package com.linq.xinansmart.ui_activity;


import com.linq.xinansmart.R;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends Activity{
	private TextView tv_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);
		tv_name = (TextView) findViewById(R.id.tv_version_name);
		String versionName = null;
		try {
			versionName = AboutActivity.this.getPackageManager().getPackageInfo(
					"com.linq.xinansmart", 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv_name.setText("µ±Ç°°æ±¾£ºV"+versionName);
		//Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show();
	}
}

