package com.linq.xinansmart.control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;




import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location_equipment;

public class Cook extends Activity {

	private RadioButton radio_off, radio_low, radio_medium, radio_high;
	private int id;
	private RadioGroup radioGroup;
	private String user, password;
	private Equipment equipment;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cook);
		context=this;
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
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				String value = "";
				switch (checkedId) {
				case R.id.radio_off:
					value = "0";
					
					break;
				case R.id.radio_low:
					value = "1";
					break;
				case R.id.radio_medium:
					value = "2";
					break;
				case R.id.radio_high:
					value = "3";
					
					break;
				}

				EquipmentManager.getInstance().SetEquipmentValue(
						equipment.getNcode(), equipment.getType(),
						equipment.getMachinID(), value, equipment.getNindex(),
						user, password);
				finish();
			}
		});
	}

	private void GetDisplayValue(String value) {

		int nStats = Integer.parseInt(value);
		switch (nStats) {
		case 0:
			radio_off.setChecked(true);
			
			break;
		case 1:
			radio_low.setChecked(true);
			break;
		case 2:
			radio_medium.setChecked(true);
			break;
		case 3:
			radio_high.setChecked(true);
			break;

		}
	}

	private void findView() {
		radioGroup = (RadioGroup) findViewById(R.id.radio_group);
		radio_off = (RadioButton) findViewById(R.id.radio_off);
		radio_low = (RadioButton) findViewById(R.id.radio_off);
		radio_medium = (RadioButton) findViewById(R.id.radio_medium);
		radio_high = (RadioButton) findViewById(R.id.radio_high);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

}
