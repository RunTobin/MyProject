package com.linq.xinansmart.control;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.manager.ModeManager;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Mode;
import com.linq.xinansmart.model.ModeEquipment;
import com.linq.xinansmart.model.ViewEquipment;

public class EditResourceActivity extends Activity {

	private EditText et_modeCode, et_modeName, et_startDate, et_endDate,
			et_startTime, et_endTime;
	private CheckBox cb_statrt, cb_isclock;
	private RelativeLayout ln_startime;
	private ListView lv_modeelecType;
	private Button bt_equipmentChoice, bt_equipmentSave, bt_equipmentDis;
	private LinearLayout ln_mode;
	private List<ViewEquipment> lstViewEquip = new ArrayList<ViewEquipment>();
	private int modeId;
	private String user, password;
	private Mode mode;
	private MyAdapter adapter = null;
	private int CHANGE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addmode);
		find();
		bt_equipmentChoice.setOnClickListener(new ChoiceOnClicked());
		bt_equipmentSave.setOnClickListener(new SaveClicked());
		bt_equipmentDis.setOnClickListener(new DisClicked());

		 init();

		adapter = new MyAdapter();
		lv_modeelecType.setAdapter(adapter);
	}

	private void init() {
		Intent in = getIntent();
		modeId = in.getIntExtra("modeId", -1);
		Log.e("editresourceMODEid", modeId+"");
		user = in.getStringExtra("user");
		password = in.getStringExtra("password");
		Concenter concenter = new Concenter();
		concenter.setUser(user);
		concenter.setPassword(password);
		new getAllEquipmentThread(concenter).start();

	}

	class getAllEquipmentThread extends Thread {

		private Concenter concenter = null;

		public getAllEquipmentThread(Concenter concenter) {
			this.concenter = concenter;
		}

		@Override
		public void run() {
			ModeManager modeManager = ModeManager.getInstance(concenter);//获得场景控制对象
			mode = modeManager.getModeById(modeId);//获取该concenter下对应的mode对象
			List<ModeEquipment> lstmodeEqu = new ArrayList<ModeEquipment>();
			lstmodeEqu = modeManager.getEquipmentByModeId(modeId);//根据场景获取场景设备
			Log.e("listmodeEqu", lstmodeEqu.size()+"sss"+lstmodeEqu.get(1).getEquipmentId());
			if (lstmodeEqu != null) {
				for (int i = 0; i < lstmodeEqu.size(); i++) {
					ViewEquipment vequ = new ViewEquipment();
//					vequ.setViewId(modeManager.getEquipmentByNee(
//							lstmodeEqu.get(i).getNetGateCode(),
//							lstmodeEqu.get(i).getEqIndex(),
//							lstmodeEqu.get(i).getEquipmentId()).getId());
					vequ.setViewId(lstmodeEqu.get(i).getEquipmentId());//
					lstViewEquip.add(vequ);
				}
				Message msg = mHandler.obtainMessage();
				mHandler.sendEmptyMessage(CHANGE);
			}

			super.run();
		}

	}

	private void find() {
		et_modeCode = (EditText) findViewById(R.id.et_modeCode); // 场景编号
		et_modeName = (EditText) findViewById(R.id.et_modeName); // 场景名
		et_startDate = (EditText) findViewById(R.id.et_startDate); // 开始日期
		et_endDate = (EditText) findViewById(R.id.et_endDate); // 结束日期
		et_startTime = (EditText) findViewById(R.id.et_startTime);// 开始时间
		et_endTime = (EditText) findViewById(R.id.et_endTime);// 结束时间
		cb_statrt = (CheckBox) findViewById(R.id.cb_electype);// 是否定时？
		ln_startime = (RelativeLayout) findViewById(R.id.ln_startime);
		lv_modeelecType = (ListView) findViewById(R.id.lv_modeElec);
		bt_equipmentChoice = (Button) findViewById(R.id.bt_equipmentChoice);
		bt_equipmentSave = (Button) findViewById(R.id.bt_equimentSave);
		bt_equipmentDis = (Button) findViewById(R.id.bt_equipmentDis);
		ln_mode = (LinearLayout) findViewById(R.id.ln_electype);
		cb_isclock = (CheckBox) findViewById(R.id.cb_isClock);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.what == CHANGE) {
				adapter.notifyDataSetChanged();
			}

		};

	};

	class ChoiceOnClicked implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}

	class SaveClicked implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}

	class DisClicked implements OnClickListener {

		@Override
		public void onClick(View v) {

			finish();
		}

	}

	class MyAdapter extends BaseAdapter {
		// //TODO Auto-Generated-stub
		@Override
		public int getCount() {
			return lstViewEquip.size();
		}

		@Override
		public Object getItem(int position) {
			return lstViewEquip.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			convertView = View.inflate(EditResourceActivity.this,
					R.layout.item_modeelec, null);
			ImageView img = (ImageView) convertView
					.findViewById(R.id.img_modeelec);
			TextView text = (TextView) convertView
					.findViewById(R.id.tv_modeelec);
			Button button = (Button) convertView.findViewById(R.id.bt_modeelec);
			SeekBar seek = (SeekBar) convertView.findViewById(R.id.sb_modeelec);

			ViewEquipment vequp = lstViewEquip.get(position);
			Equipment equp = EquipmentManager.getInstance().getEqById(
					vequp.getViewId());// 互联网
			button.setTag(vequp);
			seek.setTag(vequp);
			img.setImageResource(EquipmentManager.getInstance().getImage(equp));
			int type = equp.getType();
			int index = equp.getNindex();
			if (type == 1 || type == 2 || type == 3 || type == 4 || type == 5
					|| type == 11 || type == 6) {

				seek.setVisibility(View.GONE);
				button.setVisibility(View.VISIBLE);
				String svalue = "关";
				if (vequp.getViewValue().toUpperCase().equals("TRUE")) {
					svalue = "开";
				}
				button.setText(svalue);
				button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ViewEquipment veq = (ViewEquipment) v.getTag();
						if (veq.getViewValue().toUpperCase().equals("FALSE")) {
							veq.setViewValue("TRUE");
						} else {
							veq.setViewValue("FALSE");
						}
						Message msg = mHandler.obtainMessage();
						msg.what = CHANGE;
						mHandler.sendMessage(msg);
					}
				});
			}
			return convertView;
		}

	}

}