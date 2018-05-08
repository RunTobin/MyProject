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
		String result = "δ֪����";
		switch (type) {
		case 1:
			result = "��·���ܿ���";
			break;
		case 2:
			result = "˫·���ܿ���";
			break;
		case 3:
			result = "��·���ܿ���";
			break;
		case 4:
			result = "���ƶ������ܲ���";
			break;
		case 5:
			result = "86�������ܲ���";
			break;
		case 6:
			result = "���ܴ���";
			break;
		case 7:
			result = "С���ʵ��ƿ�����";
			break;
		case 8:
			result = "���ʵ��ƿ�����";
			break;
		case 9:
			result = "����ת����";
			break;
		case 10: {
			switch (nIndex) {
			case 1:
				result = "�¶ȴ�����";
				break;
			case 2:
				result = "ʪ�ȴ�����";
				break;
			case 3:
				result = "�𾯴�����";
				break;
			case 4:
				result = "���ִ�����";
				break;
			}
		}
			break;
		case 11:
			result = "��·���𿪹�";
			break;
		case 12:
			result = "����ͷ";
			break;
		case 13:
			result = "˫·���𿪹�";
			break;
		case 14:
			result = "��·���𿪹�";
			break;
		case 15:
			result = "���ܱ�ɫ��";
			break;
		// case 16:
		// result = "���ܻ��������";
		// break;

		case 16: {
			switch (nIndex) {
			case 1:
				result = "�¶ȴ�����";
				break;
			case 2:
				result = "ʪ�ȴ�����";
				break;
			case 3:
				result = "PM2.5";
				break;

			}
		}
			break;

		case 17:
			result = "���ּ����";
			break;
		case 18:
			result = "���ּ����";
			break;
		case 19:
			result = "��ȼ��������";
			break;
		case 21:
			// result = "�����ֻ�";
			break;
		case 22:
			result = "����Ũ�ȼ����";
			break;
		case 23:
			switch (nIndex) {
			case 1:
				result = "ϴ���";
				break;
			case 2:
				result = "������";
				break;
			case 3:
				result = "���̻�";
				break;
			case 4:
				result = "����������";
				break;
			}
			// case 156:
			// result="������̼�����";
			// break;

		}
		return result;
	}

}
