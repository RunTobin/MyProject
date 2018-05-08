package com.tutk.P2PCam264;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;
import com.linq.xinansmart.R;

public class SplashScreenActivity extends SherlockActivity {

	private static final int SPLASH_DISPLAY_TIME = 2000; /* 2 seconds */
	private static boolean isFirstLaunch = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE);

		String versionName = "";
		try {
			versionName = this.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
		}

		Splash splash = (Splash) findViewById(R.id.splash);

		if (splash != null) {
			splash.setVersion(versionName);

			if (!isFirstLaunch) {
				splash.setVisibility(View.INVISIBLE);
			}
		}

		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
				// mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
				// Intent.FLAG_ACTIVITY_CLEAR_TOP);
				SplashScreenActivity.this.startActivity(mainIntent);
				SplashScreenActivity.this.finish();

				overridePendingTransition(R.anim.mainfadein, R.anim.splashfadeout);
			}
		}, isFirstLaunch ? SPLASH_DISPLAY_TIME : 500);

		isFirstLaunch = false;
	}

	@Override
	protected void onStart() {
		super.onStart();
		// FlurryAgent.onStartSession(this, "Q1SDXDZQ21BQMVUVJ16W");
	}

	@Override
	protected void onStop() {
		super.onStop();
		// FlurryAgent.onEndSession(this);
	}
}