package com.linq.xinansmart.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location_equipment;

public class BarbecueActivity extends Activity {

	private CheckBox check_on, check_off, check_pause, check_kao, check_zheng;
	private Button btn_ok, btn_cancel;
	private EditText edit_time, edit_tem;
	private TextView txt_time, txt_tem;
	private LinearLayout detail;
	private int id;
	private String user, password;
	private Equipment equipment;
	private int remainTime, curTem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barbecue);
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
		
		findView();
		GetDisplayValue(equipment.getSvalue());
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String value = getValue();
				EquipmentManager.getInstance().SetEquipmentValue(
						equipment.getNcode(), equipment.getType(),
						equipment.getMachinID(), value, equipment.getNindex(),
						user, password);
				Log.d("settime", setTime + "");

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
					msg.what = 1;
					msg.arg1 = curTem;
					msg.arg2 = remainTime;
					mHandler.sendMessageDelayed(msg, 60000);
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
					msg.what = 1;
					msg.arg1 = curTem;
					msg.arg2 = remainTime;
					mHandler.sendMessageDelayed(msg, 60000);
				}

			}
		});
		check_zheng.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					check_kao.setChecked(false);
				}
			}
		});
		check_kao.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					check_zheng.setChecked(false);
				}
			}
		});

		edit_time.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
	
			

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				
				edit_timeStart = edit_time.getSelectionStart();
				String text = s.toString();
				edit_timeEnd = edit_time.getSelectionEnd();
				Log.d("edit_timeEnd", edit_timeEnd + "");
				if (!"".equals(text)) {
					if (Integer.parseInt(text) > 240) {
						Toast.makeText(getApplicationContext(),
								"最大时间不大于240分钟！", Toast.LENGTH_LONG).show();
						s.delete(edit_timeStart - 1, edit_timeEnd);
						int tempSelection = edit_timeStart;
						edit_time.setText(s);
						edit_time.setSelection(tempSelection);
					}
					if(text.charAt(0)=='0'){
						s.clear();
						Toast.makeText(getApplicationContext(),
								"首位不能为0！", Toast.LENGTH_LONG).show();
					}
				}

			}
		});
		edit_tem.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				Log.d("ss", s.toString());

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				editStart = edit_tem.getSelectionStart();
				String text = s.toString();
				editEnd = edit_tem.getSelectionEnd();
				Log.d("editEnd", editEnd + "");
				if (!"".equals(text)) {
					if (Integer.parseInt(text) > 10000) {
						Toast.makeText(getApplicationContext(),
								"最高温度不大于10000度！", Toast.LENGTH_LONG).show();
						s.delete(editStart - 1, editEnd);
						int tempSelection = editStart;
						edit_tem.setText(s);
						// Log.d("ss1", s.toString());
						edit_tem.setSelection(tempSelection);
					}
					if(text.charAt(0)=='0'){
						s.clear();
						Toast.makeText(getApplicationContext(),
								"首位不能为0！", Toast.LENGTH_LONG).show();
					}
				}
				
			}
		});

	}

	private int editStart;// 光标开始位置
	private int editEnd;// 光标结束位置
	private int edit_timeStart;// 光标开始位置
	private int edit_timeEnd;// 光标结束位置
	// private CharSequence temp;// 监听前的文本
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				txt_time.setText(msg.arg1 + "分钟");
				txt_tem.setText(msg.arg2 + "度");
			}
		}

	};
	private int setTime = 0;
	private int setTem = 0;

	private String getValue() {
		String value = "";
		int nState = 0;
		if (check_on.isChecked())
			nState = 1;
		else if (check_pause.isChecked())
			nState = 2;

		int nModel = 1;
		if (check_kao.isChecked())
			nModel = 2;

		if (!"".equals(edit_time.getText().toString())) {

			setTime = Integer.parseInt(edit_time.getText().toString());

		}
		if (!"".equals(edit_tem.getText().toString())) {

			setTem = Integer.parseInt(edit_tem.getText().toString());
		}

		value = nState + "," + nModel + "," + setTem + "," + setTime + ",0,0";
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

	private void GetDisplayValue(String value) {

		String[] arrValues = value.split(",");
		if (arrValues.length == 6) {
			int nStats = Integer.parseInt(arrValues[0]);
			curTem = Integer.parseInt(arrValues[4]);
			remainTime = Integer.parseInt(arrValues[5]);
			switch (nStats) {
			case 0: {
				check_on.setChecked(true);
				detail.setVisibility(View.VISIBLE);
				Message msg = mHandler.obtainMessage();
				msg.what = 1;
				msg.arg1 = curTem;
				msg.arg2 = remainTime;
				mHandler.sendMessageDelayed(msg, 60000);
			}
				break;
			case 1:
				check_off.setChecked(true);
				break;
			case 2:
				check_pause.setChecked(true);
				detail.setVisibility(View.VISIBLE);
				Message msg = mHandler.obtainMessage();
				msg.what = 1;
				msg.arg1 = curTem;
				msg.arg2 = remainTime;
				mHandler.sendMessageDelayed(msg, 60000);
				break;
			}
			int nMode = Integer.parseInt(arrValues[1]);
			switch (nMode) {
			case 1:
				check_zheng.setChecked(true);
				break;
			case 2:
				check_kao.setChecked(true);
				break;
			}

		}
	}

	private void findView() {

		check_on = (CheckBox) this.findViewById(R.id.barbe_on);
		check_off = (CheckBox) this.findViewById(R.id.barbe_off);
		check_pause = (CheckBox) this.findViewById(R.id.barbe_pause);
		check_zheng = (CheckBox) this.findViewById(R.id.barbe_zheng);
		check_kao = (CheckBox) this.findViewById(R.id.barbe_kao);
		btn_ok = (Button) this.findViewById(R.id.barbe_ok);
		btn_cancel = (Button) this.findViewById(R.id.barbe_cancel);
		edit_time = (EditText) findViewById(R.id.barbe_settim);
		edit_tem = (EditText) findViewById(R.id.barbe_settem);
		txt_time = (TextView) findViewById(R.id.txt_detaliltime);
		txt_tem = (TextView) findViewById(R.id.txt_detaliltem);
		detail = (LinearLayout) findViewById(R.id.barbe_detail);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		finish();
		return true;
	}
}
