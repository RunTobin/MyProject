package com.linq.xinansmart.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;




import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location_equipment;

public class Garbage extends Activity {

	private Button gar_on;
	private int id;
	private String user, password;
	private Equipment equipment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_garbage);
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

		gar_on.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String value="";
				if(nStats==0){
					value=1+"";
					gar_on.setBackgroundResource(R.drawable.kai);;
				}else{
					value=0+"";
					gar_on.setBackgroundResource(R.drawable.guan);;
				}
				
				EquipmentManager.getInstance().SetEquipmentValue(
						equipment.getNcode(), equipment.getType(),
						equipment.getMachinID(), value, equipment.getNindex(),
						user, password);
				finish();

			}
		});
	

	}
	private int nStats ;
	private void GetDisplayValue(String value) {
		nStats= Integer.parseInt(value);
		switch (nStats) {
		case 0:
			gar_on.setBackgroundResource(R.drawable.guan);
			break;
		case 1:
			gar_on.setBackgroundResource(R.drawable.kai);;
			break;

		}

	}

	private void findView() {
		gar_on = (Button) findViewById(R.id.gar_on);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
}
