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

public class OnOffActivity extends Activity {

	private String user, password;
	private Button btn;
	private Equipment equipment;
	private int id = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_onoff);
		Intent intent = getIntent();
		Location_equipment location_equipment=(Location_equipment) intent.getExtras().getSerializable("location_equiment");
		id = intent.getIntExtra("equipmentId", 0);
		user = intent.getStringExtra("user");
		password = intent.getStringExtra("password");
		equipment = new Equipment();
		btn = (Button) findViewById(R.id.bt_onoff);

		if (id != 0) {
			equipment = EquipmentManager.getInstance().getEqById(id);
			if (equipment.getSvalue().equals("TRUE")) {
				btn.setBackgroundResource(R.drawable.guan);
			} else {

				btn.setBackgroundResource(R.drawable.kai);
			}
		} else {
			if (location_equipment.getSvalue().equals("TRUE")) {
				btn.setBackgroundResource(R.drawable.guan);
			} else {

				btn.setBackgroundResource(R.drawable.kai);
			}
			equipment = new Equipment();
			equipment.setNcode(location_equipment.getNcode());
			equipment.setSvalue(location_equipment.getSvalue());
			equipment.setType(location_equipment.getType());
			equipment.setMachinID(location_equipment.getMachinID());
			equipment.setNindex(location_equipment.getNindex());
		}

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (equipment.getSvalue().equals("TRUE")) {

					String value = "FALSE";
					EquipmentManager.getInstance().SetEquipmentValue(
							equipment.getNcode(), equipment.getType(),
							equipment.getMachinID(), value,
							equipment.getNindex(), user, password);
				} else if (equipment.getSvalue().equals("FALSE")) {
					String value = "TRUE";
					EquipmentManager.getInstance().SetEquipmentValue(
							equipment.getNcode(), equipment.getType(),
							equipment.getMachinID(), value,
							equipment.getNindex(), user, password);
				}
				finish();
			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		finish();
		return true;
	}
}
