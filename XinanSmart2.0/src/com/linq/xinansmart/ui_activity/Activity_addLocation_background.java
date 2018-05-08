package com.linq.xinansmart.ui_activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.actionbarsherlock.app.SherlockActivity;
import com.linq.xinansmart.R;
import com.linq.xinansmart.R.id;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.ViewEquipment;
import com.squareup.picasso.Picasso;

import de.greenrobot.event.EventBus;

public class Activity_addLocation_background extends SherlockActivity implements
		OnClickListener {

	ImageView keting;

	ImageView woshi;

	ImageView chufang;

	ImageView cesuo;

	ImageView shufang;

	ImageView bangongshi;

	EditText editlocation;
	TextView locationname;
	
	View background1;
	View background2;
	View background3;
	View background4;
	View background5;
	View background6;
	
	

	Button saveButton;
	Button cancleButton;;
	private Picasso picasso;
	private Concenter concenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// setContentView(R.layout.activity_add_location_1);
		View view = View.inflate(this, R.layout.activity_add_location_1, null);
		setContentView(view);
		keting = (ImageView) view.findViewById(R.id.myActivity);
		woshi = (ImageView) view.findViewById(R.id.myFollow);
		chufang = (ImageView) view.findViewById(R.id.myWallet);
		cesuo = (ImageView) view.findViewById(R.id.myScroll);
		shufang = (ImageView) view.findViewById(R.id.myEnroll);
		bangongshi = (ImageView) view.findViewById(R.id.myShare);
		editlocation = (EditText) view.findViewById(R.id.editlocation);
		locationname = (TextView) view.findViewById(R.id.text_get_location);
		saveButton = (Button) view.findViewById(R.id.save);
		cancleButton = (Button) view.findViewById(R.id.cancel);
		background1=view.findViewById(R.id.background1);
		background2=view.findViewById(R.id.background2);
		background3=view.findViewById(R.id.background3);
		background4=view.findViewById(R.id.background4);
		background5=view.findViewById(R.id.background5);
		background6=view.findViewById(R.id.background6);
		Intent intent=getIntent();
		concenter=(Concenter) intent.getExtras().getSerializable("con");
		EventBus.getDefault().register(this);
		displayImage(keting, R.drawable.keting);
		displayImage(woshi, R.drawable.woshi);
		displayImage(chufang, R.drawable.chufang);
		displayImage(cesuo, R.drawable.cesuo);
		displayImage(shufang, R.drawable.shufang);
		displayImage(bangongshi, R.drawable.bangongshi);
		keting.setOnClickListener(this);
		woshi.setOnClickListener(this);
		chufang.setOnClickListener(this);
		cesuo.setOnClickListener(this);
		shufang.setOnClickListener(this);
		bangongshi.setOnClickListener(this);
		saveButton.setOnClickListener(this);
		cancleButton.setOnClickListener(this);
		super.onCreate(savedInstanceState);
	}

	private void displayImage(ImageView imageView, int drawable) {

		Picasso.with(Activity_addLocation_background.this).load(drawable)
				.resize(100, 100)
				.into(imageView);
	}

	public void onEventMainThread(FinishActivityEvent event) {

		if (event.getEventType() == FinishActivityEvent.Type.FINISH) {
			setResult(2016);
			finish();
		}
	}

	public static abstract class FinishActivityEvent {

		public static enum Type {
			FINISH
		}

		public abstract Type getEventType();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.myActivity:
			locationname.setText("客厅");
			background1.setVisibility(View.VISIBLE);
			background2.setVisibility(View.GONE);
			background3.setVisibility(View.GONE);
			background4.setVisibility(View.GONE);
			background5.setVisibility(View.GONE);
			background6.setVisibility(View.GONE);
			break;

		case R.id.myFollow:
			locationname.setText("卧室");
			background1.setVisibility(View.GONE);
			background2.setVisibility(View.VISIBLE);
			background3.setVisibility(View.GONE);
			background4.setVisibility(View.GONE);
			background5.setVisibility(View.GONE);
			background6.setVisibility(View.GONE);
			
			break;

		case R.id.myWallet:
			locationname.setText("厨房");
			background1.setVisibility(View.GONE);
			background2.setVisibility(View.GONE);
			background3.setVisibility(View.VISIBLE);
			background4.setVisibility(View.GONE);
			background5.setVisibility(View.GONE);
			background6.setVisibility(View.GONE);
			break;

		case R.id.myScroll:
			locationname.setText("厕所");
			background1.setVisibility(View.GONE);
			background2.setVisibility(View.GONE);
			background3.setVisibility(View.GONE);
			background4.setVisibility(View.VISIBLE);
			background5.setVisibility(View.GONE);
			background6.setVisibility(View.GONE);
			break;

		case R.id.myEnroll:
			locationname.setText("书房");
			background1.setVisibility(View.GONE);
			background2.setVisibility(View.GONE);
			background3.setVisibility(View.GONE);
			background4.setVisibility(View.GONE);
			background5.setVisibility(View.VISIBLE);
			background6.setVisibility(View.GONE);
			break;

		case R.id.myShare:
			locationname.setText("办公室");
			background1.setVisibility(View.GONE);
			background2.setVisibility(View.GONE);
			background3.setVisibility(View.GONE);
			background4.setVisibility(View.GONE);
			background5.setVisibility(View.GONE);
			background6.setVisibility(View.VISIBLE);
			break;

		case R.id.cancel:
			finish();
			break;

		case R.id.save:
			if (TextUtils.isEmpty(editlocation.getText().toString())) {
				Toast.makeText(Activity_addLocation_background.this, "请填写区域名", Toast.LENGTH_SHORT).show();
				break;
			}
			Intent intent = new Intent();
			Bundle bundle=new Bundle();
			bundle.putString("LocationName", editlocation.getText().toString());
			bundle.putString("image", locationname.getText().toString());
			bundle.putSerializable("con", concenter);
			intent.putExtras(bundle);
			intent.setClass(Activity_addLocation_background.this,
					CopyOfActivity_addLocation_Detail_test.class);
			startActivity(intent);
			break;
		}
	}
}
