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

public class OnOffStopActivity extends Activity {
	// 窗帘控制界面
	private Button bt_on, bt_off, bt_stop;
	private int id, contype = 0;
	private Equipment equipment;
	private String user, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_onoffstop);
		bt_on = (Button) findViewById(R.id.bt_ooson);
		bt_off = (Button) findViewById(R.id.bt_oosoff);
		bt_stop = (Button) findViewById(R.id.bt_oosstop);
		Intent intent = getIntent();
		Location_equipment location_equipment=(Location_equipment) intent.getExtras().getSerializable("location_equiment");
		id = intent.getIntExtra("equipmentId", 0);
		contype = intent.getIntExtra("cenType", -1);
		user = intent.getStringExtra("user");
		password = intent.getStringExtra("password");

		if (id != 0) {
			//equipment = EquipmentManager.getInstance().getEqById(id);
			equipment = EquipmentManager.getInstance().getEqById(id);
		} else {
			equipment = new Equipment();
			equipment.setNcode(location_equipment.getNcode());
			equipment.setSvalue(location_equipment.getSvalue());
			equipment.setType(location_equipment.getType());
			equipment.setMachinID(location_equipment.getMachinID());
			equipment.setNindex(location_equipment.getNindex());
		}
		bt_on.setOnClickListener(new OnClicked());
		bt_off.setOnClickListener(new OffClicked());
		bt_stop.setOnClickListener(new StopClicked());
	}

	class OnClicked implements OnClickListener {
		@Override
		public void onClick(View v) // 打开
		{
			String value = "TRUE";
			EquipmentManager.getInstance().SetEquipmentValue(
					equipment.getNcode(), equipment.getType(),
					equipment.getMachinID(), value, equipment.getNindex(),
					user, password);

		}
	}

	class OffClicked implements OnClickListener {
		@Override
		public void onClick(View v) // 关闭
		{
			String value = "FALSE";
			EquipmentManager.getInstance().SetEquipmentValue(
					equipment.getNcode(), equipment.getType(),
					equipment.getMachinID(), value, equipment.getNindex(),
					user, password);
		}

	}

	class StopClicked implements OnClickListener {
		@Override
		public void onClick(View v) // 停止
		{

			String value = "STOP";
			EquipmentManager.getInstance().SetEquipmentValue(
					equipment.getNcode(), equipment.getType(),
					equipment.getMachinID(), value, equipment.getNindex(),
					user, password);

		}
	}

	// 点击其他地方 让其退出
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		finish();
		return true;
	}
}
