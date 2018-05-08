package com.linq.xinansmart.ui_activity;

import java.util.ArrayList;
import java.util.List;

import mvp.AddLocation_Presenter;
import mvp.I_AddLocation;
import mvp.I_Image;
import mvp.ImagePresenter;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification.Action;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.manager.LocationManager;
import com.linq.xinansmart.manager.Location_EquipmentManager;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location;
import com.linq.xinansmart.model.Location_equipment;
import com.squareup.picasso.Picasso;

import de.greenrobot.event.EventBus;

public class Activity_addLocation_Detail extends SherlockActivity implements
		I_Image,I_AddLocation {

	private List<Equipment> eqList = new ArrayList<Equipment>();
	private RelativeLayout frameLayout;
	private Concenter concenter;
	private LocationManager locationManager = null;
	private Location_EquipmentManager location_EquipmentManager = null;
	private List<Location_equipment> equipments = new ArrayList<Location_equipment>();
	private int X;
	private int Y;
	private boolean choice = false;
	private String name;
	private String image;
	private ImagePresenter imagePresenter;
	private ImageView backgound;
	private AddLocation_Presenter addLocation_Presenter;
	private ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		concenter = (Concenter) intent.getExtras().getSerializable("con");
		image = intent.getExtras().getString("image");
		Log.e("123", concenter.getName());
		name = intent.getExtras().getString("LocationName");
		setContentView(R.layout.activity_add_location_detail);
		backgound = (ImageView) findViewById(R.id.imageBackgoound);
		imagePresenter = new ImagePresenter(this);
		imagePresenter.getDrawble(image);
		addLocation_Presenter=new AddLocation_Presenter(this);
		frameLayout = (RelativeLayout) findViewById(R.id.frame);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setTitle("区域名： " + name);
		//locationnameTextView.setText("区域名： " + name);

		super.onCreate(savedInstanceState);
	}

	@Override
	public void checkImage(int drawble) {
		// TODO Auto-generated method stub
		displayImage(backgound, drawble);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		SubMenu subMenu = menu.addSubMenu("");
		// menu.add(0,0,0,"保存").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		subMenu.add(Menu.NONE, 1, 1, "保存");
		subMenu.add(Menu.NONE, 2, 2, "添加设备");
		MenuItem subMenu1Item = subMenu.getItem();
		subMenu1Item.setIcon(R.drawable.ic_menu_overflow);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getOrder() == 1) {
			// Toast.makeText(this, "保存", Toast.LENGTH_SHORT).show();
			locationManager = LocationManager
					.getInstance(Activity_addLocation_Detail.this);
			location_EquipmentManager = Location_EquipmentManager
					.getInstance(Activity_addLocation_Detail.this);
			Location location=addLocation_Presenter.addLocation(locationManager, name, image,concenter.getUser());
			addLocation_Presenter.addLocation_equiment(equipments, location_EquipmentManager, location);	
		} else if (item.getOrder() == 2) {
			progressDialog = new ProgressDialog(
					Activity_addLocation_Detail.this);
			progressDialog.setTitle("正在加载设备列表");
			progressDialog.show();
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					addLocation_Presenter.getEqlist(concenter);
				}
			}).start();
		}
		return super.onOptionsItemSelected(item);
	}

	private void displayImage(ImageView imageView, int drawable) {

		Picasso.with(Activity_addLocation_Detail.this).load(drawable)
				.resize(300, 300)
				.into(imageView);
	}

	private Equipment equipment;

	private void showDialog(final CharSequence[] contents) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Activity_addLocation_Detail.this);
		builder.setItems(contents, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				equipment = eqList.get(which);
				Toast.makeText(Activity_addLocation_Detail.this,
						"请点击背景中任意位置来放置设备", Toast.LENGTH_LONG).show();
				choice = true;
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (choice == true) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				X = (int) event.getX();
				Y = (int) event.getY();
				Log.e("X-------", String.valueOf(event.getX()));
				Log.e("Y-------", String.valueOf(event.getY()));
				TextView textView = new TextView(
						Activity_addLocation_Detail.this);
				textView.setText(equipment.getName());
				textView.setGravity(Gravity.CENTER);
				//textView.layout(l, t, r, b);
				if (equipment.getType() != 0) {
					setDrawble(textView, equipment);
				}
				Log.e("type", String.valueOf(equipment.getType()));
				textView.setTextColor(getResources().getColor(
						R.color.colorPrimary));
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(X, Y - 160, 0, 0);
				textView.setLayoutParams(params);
				frameLayout.addView(textView);
				Location_equipment location_equipment = new Location_equipment();
				location_equipment.setX(X);
				location_equipment.setY(Y - 160);
				// location_equipment.setLocation(location);
				location_equipment.setNcode(equipment.getNcode());
				location_equipment.setSvalue(equipment.getSvalue());
				location_equipment.setType(equipment.getType());
				location_equipment.setMachinID(equipment.getMachinID());
				location_equipment.setNindex(equipment.getNindex());
				location_equipment.setName(equipment.getName());
				location_equipment.setbOnline(equipment.getbOnline());
				// location_equipment.setNcode(equipment.getNcode());
				equipments.add(location_equipment);
				choice = false;
			}
		}
		return super.onTouchEvent(event);
	}


	private void setDrawble(TextView textView, Equipment equipment) {
		// TODO Auto-generated method stub
		Drawable drawable = getResources().getDrawable(
				EquipmentManager.getInstance().getImage(equipment));
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, 100, 100);
		textView.setCompoundDrawables(null, drawable, null, null);
	}

	@Override
	public void addLocation() {
		// 不回调OK
	}

	@Override
	public void addLocation_equiment() {
		// TODO Auto-generated method stub
		Toast.makeText(Activity_addLocation_Detail.this, "保存成功",
				Toast.LENGTH_SHORT).show();
		finish();
		EventBus.getDefault().post(
				new Activity_addLocation_background.FinishActivityEvent() {
					@Override
					public Type getEventType() {
						return Type.FINISH;
					}
				});
	}


	@Override
	public void getEqlist_toArray(final CharSequence[] charSequences,
			List<Equipment> eqList) {
		this.eqList=eqList;
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				progressDialog.dismiss();
				showDialog(charSequences);
			}
		});
	}

	@Override
	public void getimage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		
	}

}
