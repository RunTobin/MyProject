package com.linq.xinansmart.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location_equipment;

public class WashActivity extends Activity {

	private CheckBox check_on, check_off, check_pause, check_fast, check_slow;
	private TextView txt_time;
	private Button btn_ok, btn_cancel;
	private int id;
	private String user, password;
	private Equipment equipment;
	private int remainTime;
	private LinearLayout detail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wash);
		Intent intent = getIntent();
		id = intent.getIntExtra("equipmentId", 0);
		user = intent.getStringExtra("user");
		password = intent.getStringExtra("password");
		Location_equipment location_equipment=(Location_equipment) intent.getExtras().getSerializable("location_equiment");
		if (id != 0) {
			equipment = new Equipment();
			equipment = EquipmentManager.getInstance().getEqById(id);
		}else {
			equipment = new Equipment();
			equipment.setNcode(location_equipment.getNcode());
			equipment.setSvalue(location_equipment.getSvalue());
			equipment.setType(location_equipment.getType());
			equipment.setMachinID(location_equipment.getMachinID());
			equipment.setNindex(location_equipment.getNindex());
		}
		find();
		GetDisplayValue(equipment.getSvalue());

		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String value = getValue();

				EquipmentManager.getInstance().SetEquipmentValue(
						equipment.getNcode(), equipment.getType(),
						equipment.getMachinID(), value, equipment.getNindex(),
						user, password);
				finish();

			}

		});

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		check_on.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					check_pause.setChecked(false);
					Log.d("ss1", "ss1");
					check_off.setChecked(false);

					detail.setVisibility(View.VISIBLE);
					Message msg = mHandler.obtainMessage();
					mHandler.sendEmptyMessageDelayed(remainTime, 60000);
				}
			}
		});
		check_off.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					check_on.setChecked(false);
					Log.d("ss3", "ss3");
					check_pause.setChecked(false);
				}
			}
		});
		check_pause.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					check_on.setChecked(false);
					Log.d("ss3", "ss3");
					check_off.setChecked(false);
					detail.setVisibility(View.VISIBLE);
					Message msg = mHandler.obtainMessage();
					mHandler.sendEmptyMessageDelayed(remainTime, 60000);
				}

			}
		});
		check_fast.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					check_slow.setChecked(false);
					
				}
			}
		});
		check_slow.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					check_fast.setChecked(false);
				}
			}
		});

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			txt_time.setText(msg.what + "∑÷÷”");
		};
	};

	private void GetDisplayValue(String value) {

		String[] arrValues = value.split(",");
		if (arrValues.length == 3) {
			int nStats = Integer.parseInt(arrValues[0]);
			switch (nStats) {
			case 0:{
				check_on.setChecked(true);
				detail.setVisibility(View.VISIBLE);
				Message msg = mHandler.obtainMessage();
				mHandler.sendEmptyMessageDelayed(remainTime, 60000);
			
			}break;
			case 1:
				check_off.setChecked(true);
				break;
			case 2:
				check_pause.setChecked(true);
				detail.setVisibility(View.VISIBLE);
				Message msg = mHandler.obtainMessage();
				mHandler.sendEmptyMessageDelayed(remainTime, 60000);
				break;
			}
			int nMode = Integer.parseInt(arrValues[1]);
			switch (nMode) {
			case 1:
				check_fast.setChecked(true);
				break;

			case 2:
				check_slow.setChecked(true);
				break;
			}
			remainTime = Integer.parseInt(arrValues[2]);

		}
	}

	private String getValue() {
		String value = "";
		int nState = 0;
		if (check_on.isChecked())
			nState = 1;
		else if (check_pause.isChecked())
			nState = 2;

		int nModel = 1;
		if (check_fast.isChecked())
			nModel = 2;

		value = nState + "," + nModel + ",0";
		/*
		 * if (check_fast.isChecked()) { if (check_on.isChecked()) value =
		 * "1,2,0"; if (check_off.isChecked()) value = "0,2,0"; if
		 * (check_pause.isChecked()) value = "2,2,0";
		 * 
		 * } if (check_slow.isChecked()) { if (check_on.isChecked()) value =
		 * "1,1,0"; if (check_off.isChecked()) value = "0,1,0"; if
		 * (check_pause.isChecked()) value = "2,1,0"; }
		 */

		return value;
	}

	private void find() {
		detail = (LinearLayout) findViewById(R.id.detail);
		check_on = (CheckBox) findViewById(R.id.btn_wash_kai);
		check_off = (CheckBox) findViewById(R.id.btn_wash_guan);
		check_pause = (CheckBox) findViewById(R.id.btn_wash_pause);
		check_fast = (CheckBox) findViewById(R.id.btn_wash_fast);
		check_slow = (CheckBox) findViewById(R.id.btn_wash_slow);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		txt_time = (TextView) findViewById(R.id.text_wash_time);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		finish();
		return true;
	}
}
