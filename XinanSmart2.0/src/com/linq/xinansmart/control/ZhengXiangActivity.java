package com.linq.xinansmart.control;

import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location_equipment;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ZhengXiangActivity extends Activity {

	private CheckBox check_on, check_off, check_pause, check_kao, check_zheng;
	private Button btn_ok, btn_cancel;
	private TextView edit_time;
	private TextView txt_time, txt_tem;
	private LinearLayout detail;
	private int id;
	private String user, password;
	private Equipment equipment;
	private int remainTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		setContentView(R.layout.activity_zhengxiang);
		id = intent.getIntExtra("equipmentId", 0);
		user = intent.getStringExtra("user");
		password = intent.getStringExtra("password");
		Location_equipment location_equipment = (Location_equipment) intent
				.getExtras().getSerializable("location_equiment");
		if (id != 0) {
			equipment = new Equipment();
			equipment = EquipmentManager.getInstance().getEqById(id);
		} 
		else {
			equipment = new Equipment();
			equipment.setNcode(location_equipment.getNcode());
			equipment.setSvalue(location_equipment.getSvalue());
			equipment.setType(location_equipment.getType());
			equipment.setMachinID(location_equipment.getMachinID());
			equipment.setNindex(location_equipment.getNindex());
		}
		findView();
		GetDisplayValue(equipment.getSvalue());
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String value = getValue();
				Log.e("value", value);
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
				}

			}
		});
	}

	private void findView() {

		check_on = (CheckBox) this.findViewById(R.id.zheng_on);
		check_off = (CheckBox) this.findViewById(R.id.zheng_off);
		check_pause = (CheckBox) this.findViewById(R.id.zheng_pause);
		btn_ok = (Button) this.findViewById(R.id.zheng_ok);
		btn_cancel = (Button) this.findViewById(R.id.zheng_cancel);
		edit_time = (TextView) this.findViewById(R.id.zheng_tim);

	}

	private void GetDisplayValue(String value) {

		String[] arrValues = value.split(",");
		if (arrValues.length > 0) {
			int nStats = Integer.parseInt(arrValues[0]);
			if (nStats == 1) {
				check_on.setChecked(true);
			} else if (nStats == 0) {
				check_off.setChecked(true);
			} else if (nStats == 2) {
				check_pause.setChecked(true);
			}
			remainTime = Integer.parseInt(arrValues[1]);
			edit_time.setText(remainTime+"∑÷÷”");
		}
	}

	private String getValue() {
		String value = "";
		int nState = 0;
		if (check_on.isChecked()) {
			nState = 1;
		} else if (check_off.isChecked()) {
			nState = 0;
		} else if (check_pause.isChecked()) {
			nState = 2;
		}

//		if (!"".equals(edit_time.getText().toString())) {
//
//			setTime = Integer.parseInt(edit_time.getText().toString());
//
//		}
		value = nState + "," + "75";

		return value;
	}

	
}
