package com.linq.xinansmart.guide;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.linq.xinansmart.R;
import com.tutk.P2PCam264.MainActivity;
import com.videogo.openapi.EZOpenSDK;

public class GuideActivity extends Activity implements OnPageChangeListener {
	EZOpenSDK mEzOpenSDK;
	boolean isFirst = false;
	ViewPager viewPager = null;
	View dot1 = null;
	View dot2 = null;
	View dot3 = null;
	int oldPosition = 0;
	List<View> dots = new ArrayList<View>();
	int[] imgs = { R.drawable.guide_image1, R.drawable.guide_image2,
			R.drawable.guide_image3 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		save();
		findView();
		viewPager.setPageTransformer(true, new DepthPageTransformer());
		viewPager.setAdapter(new MyPagerAdapter());
		viewPager.setOnPageChangeListener(this);
//		startActivity(intent);(intents);
		mEzOpenSDK=EZOpenSDK.getInstance();

	}

	private void save() {
		SharedPreferences sp = getSharedPreferences("guide", MODE_PRIVATE);
		sp.edit().putBoolean("isExit", true).commit();

	}

	private void findView() {

		viewPager = (ViewPager) findViewById(R.id.guide_viewpager);
		dot1 = findViewById(R.id.dot1);
		dot2 = findViewById(R.id.dot2);
		dot3 = findViewById(R.id.dot3);
		dots.add(dot1);
		dots.add(dot2);
		dots.add(dot3);

	}

	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imgs.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			ImageView img = new ImageView(GuideActivity.this);
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			img.setLayoutParams(lp);
			img.setScaleType(ScaleType.FIT_XY);// android:scaleType="fitXY"
			img.setImageResource(imgs[position]);
			container.addView(img);
			return img;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView img = (ImageView) object;
			container.removeView(img);

		}
	}

	@Override
	public void onPageScrollStateChanged(int position) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {

		dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
		dots.get(position).setBackgroundResource(R.drawable.dot_focused);
		oldPosition = position;
		if (position == 2) {
			Timer timer = new Timer();
			TimerTask task=new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent i = new Intent(GuideActivity.this, MainActivity.class);
					startActivity(i);
					mEzOpenSDK.openLoginPage();
					finish();
				}
			};
			timer.schedule(task, 1000 * 2);
		}
	}

}
