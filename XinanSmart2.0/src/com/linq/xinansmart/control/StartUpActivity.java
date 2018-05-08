package com.linq.xinansmart.control;

import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location_equipment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartUpActivity extends Activity {

	private Button bt_startup;
	private int id, contype = 0;
	private Equipment equipment;
	private String user, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
		bt_startup = (Button) findViewById(R.id.bt_startup);
		Intent intent = getIntent();
		Location_equipment location_equipment = (Location_equipment) intent
				.getExtras().getSerializable("location_equiment");
		id = intent.getIntExtra("equipmentId", 0);
		contype = intent.getIntExtra("cenType", -1);
		user = intent.getStringExtra("user");
		password = intent.getStringExtra("password");

		if (id != 0) {
			// equipment = EquipmentManager.getInstance().getEqById(id);
			equipment = EquipmentManager.getInstance().getEqById(id);
		} else {
			equipment = new Equipment();
			equipment.setNcode(location_equipment.getNcode());
			equipment.setSvalue(location_equipment.getSvalue());
			equipment.setType(location_equipment.getType());
			equipment.setMachinID(location_equipment.getMachinID());
			equipment.setNindex(location_equipment.getNindex());
		}
		if (equipment.getSvalue().equals("STOP")) {
			// bt_startup.setText("开启");
			bt_startup.setBackgroundResource(R.drawable.kaiqi);
		} else {
			// bt_startup.setText("禁用");
			bt_startup.setBackgroundResource(R.drawable.jinyong);
		}
		bt_startup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (equipment.getSvalue().equals("STOP")) {
					String value = "FALSE";
					EquipmentManager.getInstance().SetEquipmentValue(
							equipment.getNcode(), equipment.getType(),
							equipment.getMachinID(), value,
							equipment.getNindex(), user, password);

				} else {

					String value = "STOP";
					EquipmentManager.getInstance().SetEquipmentValue(
							equipment.getNcode(), equipment.getType(),
							equipment.getMachinID(), value,
							equipment.getNindex(), user, password);
				}

				finish();
			}
		});
	}

	// 点击其他地方 让其退出
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
}
