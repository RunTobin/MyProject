package com.linq.xinansmart.ui_activity;

import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.model.Equipment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EquipUpdateActivity extends Activity {
	private TextView tv_equipmentType;
	private EditText et_equipmentName, et_equipmentAddress;
	private Button bt_equipmentUpdate;
	private Equipment equipment;
	private int eqId;
	private String user, password;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipupdate);
		tv_equipmentType = (TextView) findViewById(R.id.tv_equipType);
		et_equipmentName = (EditText) findViewById(R.id.et_equipName);
		et_equipmentAddress = (EditText) findViewById(R.id.equipAddress);
		bt_equipmentUpdate = (Button) findViewById(R.id.bt_equipExit);

		Intent in = getIntent();
		eqId = in.getIntExtra("eqId", -1);
		user = in.getStringExtra("user");
		password = in.getStringExtra("password");
		equipment = EquipmentManager.getInstance().getEqById(eqId);
		init();

		bt_equipmentUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String name = et_equipmentName.getText().toString();
				final String address = et_equipmentAddress.getText().toString();

				Runnable updateRunnable = new Runnable() {

					@Override
					public void run() {
						EquipmentManager.getInstance().UpdateEquipmentInfo(
								equipment.getNcode(), equipment.getType(),
								equipment.getMachinID(),
								equipment.getEquCode(), name, address,
								equipment.getScan(), equipment.getNindex(),
								user, password);
					}
				};
				new Thread(updateRunnable).start();
				Intent intent=new Intent();
				intent.putExtra("name", name);
				setResult(20000, intent);
				finish();
			}

		});
	}

	private void init() {
		String typeName = GetTypeName(equipment.getType(),
				equipment.getNindex());
		String name = equipment.getName();
		String equAddress = equipment.getAddress();
		// Toast.makeText(this,typeName, Toast.LENGTH_SHORT).show();
		tv_equipmentType.setText(typeName);
		et_equipmentName.setText(name);
		et_equipmentAddress.setText(equAddress);
	}

	public static String GetTypeName(int type, int nIndex) {
		String result = "未知类型";
		switch (type) {
		case 1:
			result = "单路智能开关";
			break;
		case 2:
			result = "双路智能开关";
			break;
		case 3:
			result = "三路智能开关";
			break;
		case 4:
			result = "可移动型智能插座";
			break;
		case 5:
			result = "86盒型智能插座";
			break;
		case 6:
			result = "智能窗帘";
			break;
		case 7:
			result = "小功率单灯控制器";
			break;
		case 8:
			result = "大功率单灯控制器";
			break;
		case 9:
			result = "红外转发器";
			break;
		case 10: {
			switch (nIndex) {
			case 1:
				result = "温度传感器";
				break;
			case 2:
				result = "湿度传感器";
				break;
			case 3:
				result = "火警传感器";
				break;
			case 4:
				result = "入侵传感器";
				break;
			}
		}
			break;
		case 11:
			result = "三路单火开关";
			break;
		case 12:
			result = "摄像头";
			break;
		case 13:
			result = "双路单火开关";
			break;
		case 14:
			result = "单路单火开关";
			break;
		case 15:
			result = "智能变色灯";
			break;
		// case 16:
		// result = "智能环境监测仪";
		// break;

		case 16: {
			switch (nIndex) {
			case 1:
				result = "温度传感器";
				break;
			case 2:
				result = "湿度传感器";
				break;
			case 3:
				result = "PM2.5";
				break;

			}
		}
			break;

		case 17:
			result = "入侵监测仪";
			break;
		case 18:
			result = "火灾监测仪";
			break;
		case 19:
			result = "可燃气体监测仪";
			break;
		case 21:
			// result = "智能手环";
			break;
		case 22:
			result = "氧气浓度检测仪";
			break;
		case 23:
			switch (nIndex) {
			case 1:
				result = "洗碗机";
				break;
			case 2:
				result = "蒸烤机";
				break;
			case 3:
				result = "油烟机";
				break;
			case 4:
				result = "垃圾处理器";
				break;
			}
			// case 156:
			// result="二氧化碳检测仪";
			// break;

		}
		return result;
	}

}
