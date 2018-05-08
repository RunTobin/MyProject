package com.linq.xinansmart.ui_activity;

import java.util.ArrayList;
import java.util.List;

import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.model.Equipment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ChioceEquActivity extends Activity {

	ListView lv_euipment;
	boolean check = false, checkAll = false;
	List<Integer> lstEquId;
	List<Equipment> lstEquipment;
	MyAdapter adapter;
	private static final int UPDATE = 0;
	Handler handler;
	Button bt_return;
	String et_modeCode, et_modeName, et_startDate, et_endDate, et_startTime,
			et_endTime;
	CheckBox selectAll, clearAll;
	int contype = 0, cid = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chioceequ);
		lv_euipment = (ListView) findViewById(R.id.lv_chioceequ);
		bt_return = (Button) findViewById(R.id.bt_chioceequ);
		selectAll = (CheckBox) findViewById(R.id.cb_selectAll);
		clearAll = (CheckBox) findViewById(R.id.cb_clearAll);
		init();
		adapter = new MyAdapter();
		lv_euipment.setAdapter(adapter);
		lv_euipment.setSelection(0);
		// 全部选中
		selectAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == true) {
					clearAll.setChecked(false);
					clearAll.setEnabled(true);
					checkAll = true;
					lstEquipment = EquipmentManager.getInstance().getEqList();
					update();

				} else {
					clearAll.setChecked(true);
					clearAll.setEnabled(false);
					checkAll = false;

					lstEquipment = EquipmentManager.getInstance().getEqList();
					update();

				}

			}
		});
		// 取消全选
		clearAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == true) {
					selectAll.setChecked(false);
					selectAll.setEnabled(true);
					check = true;
					
						lstEquipment = EquipmentManager.getInstance().getEqList();
						update();
					
						
				} else {
					selectAll.setChecked(true);
					selectAll.setEnabled(false);
					check = false;
					
						lstEquipment = EquipmentManager.getInstance().getEqList();
						update();
					

				}

			}
		});
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == UPDATE) {
					adapter.notifyDataSetChanged();
					lv_euipment.setSelection(0);
				} else
					super.handleMessage(msg);
			}

		};
		// 将选中的设备id传回选择页面
		bt_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putIntegerArrayListExtra("lstEquId",
						(ArrayList<Integer>) lstEquId);
				intent.putExtra("et_modeCode", et_modeCode);
				intent.putExtra("et_modeName", et_modeName);
				intent.putExtra("et_startDate", et_startDate);
				intent.putExtra("et_endDate", et_endDate);
				intent.putExtra("et_startTime", et_startTime);
				intent.putExtra("et_endTime", et_endTime);
				intent.putExtra("type", contype);
				intent.putExtra("cId", cid);
				setResult(11112222, intent);
				finish();
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			setResult(1111111);
			finish();
		

			break;
		}

		return super.onKeyDown(keyCode, event);
	}
	private void update() {
		Message msg = new Message();
		msg.what = UPDATE;
		handler.sendMessage(msg);
	}

	private void init() {

		lstEquId = new ArrayList<Integer>();
		Intent in = getIntent();
		contype = in.getIntExtra("type", -1);
		cid = in.getIntExtra("cId", -1);
		
			lstEquipment = EquipmentManager.getInstance().getEqList();
		et_modeCode = in.getStringExtra("et_modeCode");
		et_modeName = in.getStringExtra("et_modeName");
		et_startDate = in.getStringExtra("et_startDate");
		et_endDate = in.getStringExtra("et_endDate");
		et_startTime = in.getStringExtra("et_startTime");
		et_endTime = in.getStringExtra("et_endTime");
	}

	public class MyAdapter extends BaseAdapter {

		private boolean[] checks; // 用于保存checkBox的选择状态

		public MyAdapter() {
			checks = new boolean[lstEquipment.size()];
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lstEquipment.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			convertView = View.inflate(ChioceEquActivity.this,
					R.layout.item_chioceequ, null);
			CheckBox cbox = (CheckBox) convertView
					.findViewById(R.id.cb_equChoice);
			ImageView img = (ImageView) convertView
					.findViewById(R.id.img_equipChoice);
			TextView tv_equ = (TextView) convertView
					.findViewById(R.id.tv_equipName);
			int type = lstEquipment.get(position).getType();
			int index = lstEquipment.get(position).getNindex();
			int imageId = getImageResource(type, index);
			// 温度和湿度不给选中
			if (type == 10 && (index == 1 || index == 2)) {
				cbox.setEnabled(false);
			}
			img.setImageResource(imageId);
			tv_equ.setText(lstEquipment.get(position).getName());
			cbox.setTag(position);
			// final int pos = position; //pos必须声明为final
			cbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					int curPosition = (Integer) buttonView.getTag();
					checks[curPosition] = isChecked;
					if (isChecked == true) {
						// lstEquId.add(lstEquipment.get(curPosition).getId());

						if (lstEquId.size() > 0) {
							if (lstEquId.contains(lstEquipment.get(curPosition)
									.getId()) == false) {
								if (contype == 0)
									lstEquId.add(lstEquipment.get(curPosition)
											.getId());
								else
									lstEquId.add(lstEquipment.get(curPosition)
											.getId());
							}

						} else {
							if (contype == 0)
								lstEquId.add(lstEquipment.get(curPosition)
										.getId());
							else
								lstEquId.add(lstEquipment.get(curPosition)
										.getId());
						}

					} else {
						if (lstEquId.size() > 0) {
							for (int i = 0; i < lstEquId.size(); i++) {
								if (contype == 0) {
									if (lstEquId.get(i) == lstEquipment.get(
											curPosition).getId()) {
										lstEquId.remove(i);
									}
								} else {
									if (lstEquId.get(i) == lstEquipment.get(
											curPosition).getId()) {
										lstEquId.remove(i);
									}
								}
							}
						} else {
							return;
						}
					}

				}

			});

			if (checkAll == true && check == false) {
				if (type == 10 && (index == 1 || index == 2)) {
					cbox.setChecked(false);
				} else {
					cbox.setChecked(true);
				}
				lstEquId.clear();
				for (int j = 0; j < lstEquipment.size(); j++) {
					if (!(lstEquipment.get(j).getType() == 10 && (lstEquipment
							.get(j).getNindex() == 1 || lstEquipment.get(j)
							.getNindex() == 2))) {
						lstEquId.add(lstEquipment.get(j).getId());
					}
				}

			} else if (checkAll == false && check == true) {
				for (int i = 0; i < checks.length; i++) {
					checks[i] = false;
				}
				cbox.setChecked(checks[position]);
				lstEquId.clear();
			} else {
				cbox.setChecked(checks[position]);
			}

			return convertView;
		}

	}

	private int getImageResource(int type, int index) {
		int result = 0;
		switch (type) {
		case 1:
			result = R.drawable.kaiguan1;

			break;
		case 2:
			result = R.drawable.kaiguan2;
			break;
		case 3:
			result = R.drawable.kaiguan3;

			break;
		case 4:
			result = R.drawable.chazuo;

			break;
		case 5:
			result = R.drawable.chazuo;

			break;
		case 6:
			result = R.drawable.chuanglian;

			break;
		case 7:
			result = R.drawable.dengpao;

			break;
		case 8:
			result = R.drawable.dengpaoh;

			break;
		case 9:
			result = R.drawable.kaiguan1;
			break;
		case 10: {
			if (index == 1) {
				result = R.drawable.wendu;
			} else if (index == 2) {
				result = R.drawable.shidu;
			} else if (index == 3) {
				result = R.drawable.huojin;
			} else if (index == 4) {
				result = R.drawable.ruqin;
			}
		}
			break;
		case 11:
			result = R.drawable.danhuo;

			break;
		case 12:
			result = R.drawable.shext_on;
			break;
		case 13:
			result = R.drawable.danhuo2_on;
			break;
		case 14:
			result = R.drawable.danhuo1_on;
			break;
		case 15:
			result = R.drawable.biansedeng_on;
			break;
		// case 16:result=R.drawable.huanjinjcy_on;
		// break;
		case 16: {
			if (index == 1) {
				result = R.drawable.wendu;
			} else if (index == 2) {
				result = R.drawable.shidu;
			} else if (index == 3) {

				result = R.drawable.pm;
			}
		}
			break;
		case 17:
			result = R.drawable.ruqinjc_on;
			break;
		case 18:
			result = R.drawable.huanjinjcy_on;
			break;
		case 19:
			result = R.drawable.keranjc_on;
			break;
		case 20:
			result = R.drawable.baiyechuang;
			break;
		case 21:
			// result=R.drawable.shouhuanon;
			break;
		case 22:
			result = R.drawable.yangqion;
			break;
		case 23:
			switch (index) {
			case 1:
				result = R.drawable.xiwanji;
				break;
			case 2:
				result = R.drawable.zhengkaoji;
				break;
			case 3:
				result = R.drawable.youyanji;
				break;
			case 4:
				result = R.drawable.recycle;
				break;
			}
			break;
		case 156:
			result = R.drawable.eryanghuatanon;
			break;
		}
		return result;
	}
}
