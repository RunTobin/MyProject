package com.linq.xinansmart.control;

import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location_equipment;
import com.linq.xinansmart.web.CommManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ProgressActivity extends Activity {

	private SeekBar sb_progress;
	private int id, contype = 0;
	private Equipment equipment;
	private LinearLayout ln_progress;
	private String user, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress);
		sb_progress = (SeekBar) findViewById(R.id.sb_progress);
		ln_progress = (LinearLayout) findViewById(R.id.ln_progress);

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
		sb_progress.setProgress(Integer.parseInt(equipment.getSvalue()));
		sb_progress.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

				String value = "" + seekBar.getProgress();
				EquipmentManager.getInstance().SetEquipmentValue(
						equipment.getNcode(), equipment.getType(),
						equipment.getMachinID(), value, equipment.getNindex(),
						user, password);

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});

		// 点击背景不退出窗体
		ln_progress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

	}

	// 点击其他地方 让其退出
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		finish();
		return true;
	}
}
