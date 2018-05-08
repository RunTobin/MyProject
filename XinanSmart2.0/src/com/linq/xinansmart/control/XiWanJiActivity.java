package com.linq.xinansmart.control;

import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location_equipment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class XiWanJiActivity extends Activity {
	private CheckBox check_on, check_off, check_pause;
	private Button btn_ok, btn_cancel;
	private EditText edit_time;
	private TextView remain_time;
	private int id;
	private String user, password;
	private Equipment equipment;
	private int remainTime, curTem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiwanji);
		Intent intent = getIntent();
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
		btn_ok = (Button) this.findViewById(R.id.xi_ok1);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("".equals(edit_time.getText().toString())) {
					edit_time.setText("0");
					
				} else if (Integer.parseInt(edit_time.getText().toString())>=240) {
					Toast.makeText(XiWanJiActivity.this, "不能超过240分钟",
							Toast.LENGTH_SHORT).show();
				}else if ("0".equals(edit_time.getText().toString())&&check_on.isChecked()) {
					Toast.makeText(XiWanJiActivity.this, "请输入一个时间",
							Toast.LENGTH_SHORT).show();
				}
				else {
					String value = getValue();
					Log.e("value", value);
					EquipmentManager.getInstance().SetEquipmentValue(
							equipment.getNcode(), equipment.getType(),
							equipment.getMachinID(), value,
							equipment.getNindex(), user, password);

					finish();
				}
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

		if (!"".equals(edit_time.getText().toString())) {

			curTem = Integer.parseInt(edit_time.getText().toString());

		}
		value = nState + "," + curTem + "," + "1111";

		return value;
	}

	private void GetDisplayValue(String svalue) {
		String[] arrValues = svalue.split(",");
		Log.e("11111", svalue);
		if (arrValues.length > 2) {
			int nStats = Integer.parseInt(arrValues[0]);
			if (nStats == 1) {
				check_on.setChecked(true);
			} else if (nStats == 0) {
				check_off.setChecked(true);
			} else if (nStats == 2) {
				check_pause.setChecked(true);
			}
			remainTime = Integer.parseInt(arrValues[2]);
			remain_time.setText(remainTime+"分钟");
			curTem = Integer.parseInt(arrValues[1]);
			
		}
	}

	private void findView() {
		check_on = (CheckBox) this.findViewById(R.id.xi_on);
		check_off = (CheckBox) this.findViewById(R.id.xi_off);
		check_pause = (CheckBox) this.findViewById(R.id.xi_pause);
		
		btn_cancel = (Button) this.findViewById(R.id.xi_cancel);
		edit_time = (EditText) this.findViewById(R.id.xi_settim);
		remain_time = (TextView) this.findViewById(R.id.xi_tim_remain);

	}

//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		finish();
//		return true;
//	}

}
