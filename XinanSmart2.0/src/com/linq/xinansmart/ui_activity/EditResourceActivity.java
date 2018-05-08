package com.linq.xinansmart.ui_activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TimePicker.OnTimeChangedListener;

import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.manager.ModeManager;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Mode;
import com.linq.xinansmart.model.ModeEquipment;
import com.linq.xinansmart.model.ViewEquipment;
import com.linq.xinansmart.ui_activity.Activity_addMode.ChioceOnclicked;
import com.linq.xinansmart.ui_activity.Activity_addMode.DisClicked;
import com.linq.xinansmart.ui_activity.Activity_addMode.MyAdapter;
import com.linq.xinansmart.ui_activity.Activity_addMode.SaveClicked;

public class EditResourceActivity extends Activity {

	private EditText et_modeCode, et_modeName, et_startDate, et_endDate,
			et_startTime, et_endTime;
	private TimePicker tp_startTime;
	private CheckBox cb_statrt, cb_isclock;
	private ListView lv_modeelecType;
	private List<Integer> lstEqu;
	private Concenter concenter;
	private List<ViewEquipment> lstViewEquip = new ArrayList<ViewEquipment>();
	private Button bt_equipmentChoice, bt_equipmentSave, bt_equipmentDis;
	private RelativeLayout ln_startdate, ln_enddate, ln_startime, ln_endtime;
	private LinearLayout ln_mode;
	private static int CHANGE = 0;
	private MyAdapter adapter;
	private Handler handler;
	String str_modeCode = "", str_modeName = "", str_startDate = "",
			str_endDate = "", str_startTime = "", str_endTime = "";
	private int contype = 0, Cid = 0;
	private int modeId = 0, m = 0;
	Mode mode = null;
	Equipment equi = null;
	private String initStartTime = "00:00";
	private AlertDialog ad;
	String title = "", timingStr = "FALSE";
	private List<Integer> idlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addmode);
		init();
		init2();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			final Intent data) {
		if (resultCode == 11112222 && requestCode == 111222) {
			lstViewEquip.clear();
			str_modeCode = data.getStringExtra("et_modeCode");// ?
			et_modeCode.setText(str_modeCode);
			str_modeName = data.getStringExtra("et_modeName");// ?
			et_modeName.setText(str_modeName);
			str_startDate = data.getStringExtra("et_startDate");// ?
			if (str_startDate != null && str_startDate.equals("TRUE")) {
				cb_isclock.setChecked(true);
				ln_startime.setVisibility(View.VISIBLE);
			}
			et_startDate.setText(str_startDate);
			str_endDate = data.getStringExtra("et_endDate");
			et_endDate.setText(str_endDate);
			str_startTime = data.getStringExtra("et_startTime");
			str_endTime = data.getStringExtra("et_endTime");
			et_endTime.setText(str_endTime);
			contype = data.getIntExtra("type", 2);
			Cid = data.getIntExtra("cId", -1);
			idlist = data.getIntegerArrayListExtra("lstEquId");// ?
			if (idlist != null) {
				lstEqu = idlist;
			}
			if (lstEqu.size() > 0) {
				new Thread(new Runnable() {

					@Override
					public void run() {

						// TODO Auto-generated method stub
						for (int i = 0; i < lstEqu.size(); i++) {
							int type = 0;
							type = EquipmentManager.getInstance()
									.getEqById(lstEqu.get(i)).getType();
							ViewEquipment vequ = new ViewEquipment();
							vequ.setViewId(lstEqu.get(i));

							switch (type) {
							case 1:
							case 2:
							case 3:
							case 4:
							case 5:
							case 6:
							case 11:
								vequ.setViewValue("FALSE");
								break;
							case 10:
								vequ.setViewValue("STOP");
								break;
							case 7:
							case 8:
							case 20:
							case 15:
								vequ.setViewValue("0");
								break;
							case 17:
								vequ.setViewValue("FALSE,STOP");
								break;
							case 16:
								vequ.setViewValue("STOP");
								break;
							case 18:
							case 19:
								vequ.setViewValue("FALSE,FALSE");
								break;
							case 21:
								break;
							case 22:
								break;
							case 23:
								break;
							case 156:
								break;
							}

							lstViewEquip.add(vequ);
						}
						Message msg = new Message();
						msg.what = CHANGE;
						handler.sendMessage(msg);
					}
				}).start();
			}

			// TODO Auto-generated method stub

		}

	}

	private void init() {
		et_modeCode = (EditText) findViewById(R.id.et_modeCode);
		et_modeName = (EditText) findViewById(R.id.et_modeName);
		et_startDate = (EditText) findViewById(R.id.et_startDate);
		et_endDate = (EditText) findViewById(R.id.et_endDate);
		et_startTime = (EditText) findViewById(R.id.et_startTime);
		et_startTime.setFocusable(false);
		et_endTime = (EditText) findViewById(R.id.et_endTime);
		cb_statrt = (CheckBox) findViewById(R.id.cb_electype);
		cb_isclock = (CheckBox) findViewById(R.id.cb_isClock);
		lv_modeelecType = (ListView) findViewById(R.id.lv_modeElec);
		bt_equipmentChoice = (Button) findViewById(R.id.bt_equipmentChoice);
		bt_equipmentSave = (Button) findViewById(R.id.bt_equimentSave);
		bt_equipmentDis = (Button) findViewById(R.id.bt_equipmentDis);
		ln_mode = (LinearLayout) findViewById(R.id.ln_electype);
		ln_startdate = (RelativeLayout) findViewById(R.id.ln_startdate);
		ln_enddate = (RelativeLayout) findViewById(R.id.ln_enddate);
		ln_startime = (RelativeLayout) findViewById(R.id.ln_startime);
		ln_endtime = (RelativeLayout) findViewById(R.id.ln_endtime);
		lstEqu = new ArrayList<Integer>(); // ?
		bt_equipmentChoice.setOnClickListener(new ChioceOnclicked());
		bt_equipmentSave.setOnClickListener(new SaveClicked());
		bt_equipmentDis.setOnClickListener(new DisClicked());
		Intent in = getIntent();
		concenter = (Concenter) in.getExtras().getSerializable("con");
		// Toast.makeText(this, concenter.getId()+concenter.getName(),
		// Toast.LENGTH_SHORT).show();
		idlist = new ArrayList<Integer>();
		str_modeCode = in.getStringExtra("et_modeCode");// ?
		et_modeCode.setText(str_modeCode);
		str_modeName = in.getStringExtra("et_modeName");// ?
		et_modeName.setText(str_modeName);
		str_startDate = in.getStringExtra("et_startDate");// ?
		if (str_startDate != null && str_startDate.equals("TRUE")) {
			cb_isclock.setChecked(true);
			ln_startime.setVisibility(View.VISIBLE);
		}
		et_startDate.setText(str_startDate);
		str_endDate = in.getStringExtra("et_endDate");
		et_endDate.setText(str_endDate);
		str_startTime = in.getStringExtra("et_startTime");

		str_endTime = in.getStringExtra("et_endTime");
		et_endTime.setText(str_endTime);
		contype = in.getIntExtra("type", 2);
		Cid = in.getIntExtra("cId", -1);

		cb_isclock.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked == true)// 互联网模式才有定时开启功能
				{
					timingStr = "TRUE";
					ln_startime.setVisibility(View.VISIBLE);
					final String initDateTime;
					if (!"".equals(str_startTime) && str_startTime != null) {
						et_startTime.setText(str_startTime);
						initDateTime = str_startTime;
					} else {
						et_startTime.setText(initStartTime);
						initDateTime = initStartTime;
					}
					et_startTime.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							/*
							 * DateTimeDialogUtil dt = new
							 * DateTimeDialogUtil(AddModeActivity.this,
							 * initDateTime);
							 * dt.dateTimePicKDialog(et_startTime);
							 */
							LinearLayout layout = (LinearLayout) EditResourceActivity.this
									.getLayoutInflater().inflate(
											R.layout.common_datetime, null);
							tp_startTime = (TimePicker) layout
									.findViewById(R.id.timepicker);
							tp_startTime.setIs24HourView(true);
							Calendar c = Calendar.getInstance();
							int hour = c.get(Calendar.HOUR_OF_DAY);
							if (hour > 12)
								hour = hour - 12;
							int minute = c.get(Calendar.MINUTE);
							if (hour < 10)
								title = "0" + hour + ":" + minute;
							else
								title = hour + ":" + minute;
							tp_startTime
									.setOnTimeChangedListener(new OnTimeChangedListener() {

										@Override
										public void onTimeChanged(
												TimePicker view, int hourOfDay,
												int minute) {
											String minuteStr = "";
											String hourStr = "";
											if (minute < 10) {
												minuteStr = "0" + minute;
											} else {
												minuteStr = "" + minute;
											}
											if (hourOfDay < 10) {
												hourStr = "0" + hourOfDay;
											} else {
												hourStr = "" + hourOfDay;
											}
											title = hourStr + ":" + minuteStr;
											ad.setTitle(title);
										}
									});
							ad = new AlertDialog.Builder(
									EditResourceActivity.this)
									.setTitle(initDateTime)
									.setView(layout)
									.setPositiveButton(
											"设置",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													et_startTime.setText(title);
												}
											})
									.setNegativeButton(
											"取消",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													et_startTime
															.setText(et_startTime
																	.getText());
												}
											}).show();
						}
					});
				} else {
					timingStr = "FALSE";
					ln_startime.setVisibility(View.INVISIBLE);
				}
			}
		});

		idlist = in.getIntegerArrayListExtra("lstEquId");// ?
		if (idlist != null) {
			lstEqu = idlist;
		}

		if (lstEqu.size() > 0) {
			for (int i = 0; i < lstEqu.size(); i++) {
				// int type =
				// ConnectUtil.getEquipmentById(lstEqu.get(i)).getType();
				int type = 0;
				type = EquipmentManager.getInstance().getEqById(lstEqu.get(i))
						.getType();
				ViewEquipment vequ = new ViewEquipment();
				vequ.setViewId(lstEqu.get(i));
				// String value=vequ.getViewValue();
				switch (type) {
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 11:
					vequ.setViewValue("FALSE");
					break;
				case 10:
					vequ.setViewValue("STOP");
					break;
				case 7:
				case 8:
				case 20:
				case 15:
					vequ.setViewValue("0");
					break;
				case 17:
					vequ.setViewValue("FALSE,STOP");
					break;
				case 16:
					vequ.setViewValue("STOP");
					break;
				case 18:
				case 19:
					vequ.setViewValue("FALSE,FALSE");
					break;
				case 21:
					break;
				case 22:
					break;
				case 23:
					break;
				case 156:
					break;
				}

				lstViewEquip.add(vequ);
			}
		}

		adapter = new MyAdapter();
		lv_modeelecType.setAdapter(adapter);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == CHANGE) {
					adapter.notifyDataSetChanged();
				} else
					super.handleMessage(msg);
			}
		};
	}

	// 时间格式转换
	public String ToStartime(String timeStr) {
		String result = "";
		String[] strs1 = timeStr.split("\\(");
		if (strs1.length > 1) {
			String[] strs2 = strs1[1].split("\\+");
			long date = Long.parseLong(strs2[0]);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			result = sdf.format(date);
		} else {
			result = timeStr;
		}
		return result;
	}

	private String user, password;
	private List<Equipment> eqList = new ArrayList<Equipment>();

	private void init2() {
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("加载中");
		progressDialog.show();
		Intent in = getIntent();
		modeId = in.getIntExtra("modeId", -1);
		user = in.getStringExtra("user");
		password = in.getStringExtra("password");
		concenter = (Concenter) in.getExtras().getSerializable("con");

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mode = ModeManager.getInstance(concenter).getModeById(modeId);
				eqList = EquipmentManager.getInstance().getAllEquipment(
						concenter.getUser(), concenter.getPassword());
				List<ModeEquipment> lstmodeEqu = new ArrayList<ModeEquipment>();
				lstmodeEqu = ModeManager.getInstance(concenter)
						.getEquipmentByModeId(modeId);
				//Log.e("mode里的设备数", lstmodeEqu.size()+"");
				ModeManager modeManager = ModeManager.getInstance(concenter);
				if (lstmodeEqu != null) {
					for (int i = 0; i < lstmodeEqu.size(); i++) {
						ViewEquipment vequ = new ViewEquipment();
						if(modeManager.getEquipmentByNee(
								lstmodeEqu.get(i).getNetGateCode(),
								lstmodeEqu.get(i).getEqIndex(),
								lstmodeEqu.get(i).getEquipmentId())!=null){
							vequ.setViewId(modeManager.getEquipmentByNee(
									lstmodeEqu.get(i).getNetGateCode(),
									lstmodeEqu.get(i).getEqIndex(),
									lstmodeEqu.get(i).getEquipmentId())
									.getId());
							// lstmodeEqu.get(i).getId()
							vequ.setViewValue(lstmodeEqu.get(i).getValue());
							lstViewEquip.add(vequ);
						}
					}
					Log.e("mode里的设备数", lstViewEquip.size()+"");
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (mode.getStratDate().equals("TRUE")) {
							cb_isclock.setChecked(true);
							ln_startime.setVisibility(View.VISIBLE);
							String str = ToStartime(mode.getStartTime());
							et_startTime.setText(str);
							timingStr = "TRUE";

						} else {
							cb_isclock.setChecked(false);
						}

						et_modeCode.setText(mode.getModeCode());
						et_modeName.setText(mode.getModeName());
						et_startDate.setText(mode.getStratDate());
						et_endDate.setText(mode.getEndDate());
						et_endTime.setText(mode.getEndTime());
						adapter.notifyDataSetChanged();
						progressDialog.dismiss();
					}
				});
			}
		}).start();
	}

	public Equipment getEquipmentByNee(int NetGateCode, int EqIndex,
			int EquipmentCode) {
		Equipment equip = null;
		for (int i = 0; i < eqList.size(); i++) {
			Log.e("lstmodeEqu", eqList.size() + "mmmmmmm");
			Equipment equipm = eqList.get(i);
			if (equipm.getNcode() == NetGateCode
					&& equipm.getNindex() == EqIndex
					&& equipm.getMachinID() == EquipmentCode) {
				equip = equipm;
				break;
			}
		}
		return equip;
	}

	// 选择设备
	class ChioceOnclicked implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent(EditResourceActivity.this,
					ChioceEquActivity.class);
			intent.putExtra("type", contype);
			intent.putExtra("cId", Cid);
			intent.putExtra("et_modeCode", et_modeCode.getText().toString());
			intent.putExtra("et_modeName", et_modeName.getText().toString());
			intent.putExtra("et_startDate", timingStr);
			intent.putExtra("et_endDate", et_endDate.getText().toString());
			intent.putExtra("et_startTime", et_startTime.getText().toString());
			intent.putExtra("et_endTime", et_endTime.getText().toString());
			startActivityForResult(intent, 111222);
		}

	}

	// 保存场景
	class SaveClicked implements OnClickListener {

		@Override
		public void onClick(View v) {

			if (TextUtils.isEmpty(et_modeName.getText())) {
				Toast.makeText(EditResourceActivity.this, "场景名称不能为空",
						Toast.LENGTH_SHORT).show();

			}
			if (et_modeName.getText().length() > 20
					|| et_modeCode.getText().length() > 20) {
				Toast.makeText(EditResourceActivity.this, "请输入20字以内内容",
						Toast.LENGTH_SHORT).show();

			}
			if (!TextUtils.isEmpty(et_modeName.getText())
					&& (et_modeName.getText().length() <= 20 && et_modeCode
							.getText().length() <= 20)) {
				v.setEnabled(false);
				ProgressDialog progressDialog = new ProgressDialog(
						EditResourceActivity.this);
				progressDialog.setMessage("正在保存");
				progressDialog.show();
				
				Runnable run = new Runnable() {

					@Override
					public void run() {
						if (timingStr.equals("TRUE")) {
							try {
								Date today = new Date();
								String timeStr = et_startTime.getText()
										.toString();
								String[] timeStrs = timeStr.split(":");
								Date ss = new Date(today.getYear(),
										today.getMonth(), today.getDay(),
										Integer.parseInt(timeStrs[0]),
										Integer.parseInt(timeStrs[1]), 0);
								String statrTime = String.format("%s",
										ss.getTime());
								ModeManager
										.getInstance(concenter)
										.UpdateAppProfile(
												mode.getId(),
												et_modeCode.getText()
														.toString(),
												et_modeName.getText()
														.toString(),
												true,
												"\\/Date(1422158721471+0800)\\/",
												"\\/Date(1422158721471+0800)\\/",
												"\\/Date(" + statrTime
														+ "+0800)\\/",
												"\\/Date(1422158721471+0800)\\/",
												"", user, password);
								mode.setStratDate("TRUE");
								mode.setStartTime(timeStr);
								modeId = mode.getId();
								List<ModeEquipment> listeq = ModeManager
										.getInstance(concenter).getMeqByModeId(
												modeId);
								ModeManager manager = ModeManager
										.getInstance(concenter);
								for (int k = 0; k < listeq.size(); k++) {
									manager.DeleteAppProfileDetailA(modeId,
											listeq.get(k).getId(), user,
											password);
								}
								//manager.ClearmeqList();
								for (int i = 0; i < lstViewEquip.size(); i++) {

									Equipment equi = manager
											.getEqById(lstViewEquip.get(i)
													.getViewId());
									String curvalue = lstViewEquip.get(i)
											.getViewValue();
									manager.AddAppProfileDetail(modeId,
											equi.getNcode(), equi.getType(),
											equi.getMachinID(), curvalue,
											equi.getNindex());
								}
								Intent intent = new Intent();
								setResult(2001, intent);
								finish();
								// ss.setTime(milliseconds);
							} catch (Exception e) {
								e.printStackTrace();
								Toast.makeText(EditResourceActivity.this,
										"编辑失败！", 0).show();
								Intent intent = new Intent();
								setResult(2001, intent);
								finish();
							}
						} else {
							try {
								ModeManager
										.getInstance(concenter)
										.UpdateAppProfile(
												mode.getId(),
												et_modeCode.getText()
														.toString(),
												et_modeName.getText()
														.toString(),
												false,
												"\\/Date(1422158721471+0800)\\/",
												"\\/Date(1422158721471+0800)\\/",
												"\\/Date(1422158721471+0800)\\/",
												"\\/Date(1422158721471+0800)\\/",
												"", user, password);
								mode.setStratDate("FALSE");
								modeId = mode.getId();
								List<ModeEquipment> listeq = ModeManager
										.getInstance(concenter).getMeqByModeId(
												modeId);
								ModeManager manager = ModeManager
										.getInstance(concenter);
								for (int k = 0; k < listeq.size(); k++) {

									manager.DeleteAppProfileDetailA(modeId,
											listeq.get(k).getId(), user,
											password);
								}
								//manager.ClearmeqList();
								for (int i = 0; i < lstViewEquip.size(); i++) {

									Equipment equi = EquipmentManager.getInstance()
											.getEqById(lstViewEquip.get(i)
													.getViewId());
									String curvalue = lstViewEquip.get(i)
											.getViewValue();
									manager.AddAppProfileDetail(modeId,
											equi.getNcode(), equi.getType(),
											equi.getMachinID(), curvalue,
											equi.getNindex());
								}
								Intent intent = new Intent();
								setResult(2001, intent);
								finish();
							} catch (Exception e) {
								e.printStackTrace();
								Toast.makeText(EditResourceActivity.this,
										"编辑失败！", 0).show();
								Intent intent = new Intent();
								setResult(2001, intent);
								finish();
							}
						}

					}
				};
				new Thread(run).start();

			}
		}

	}

	// 取消保存
	class DisClicked implements OnClickListener {

		@Override
		public void onClick(View v) {
			finish();
		}

	}

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return lstViewEquip.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
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
			Equipment equp = null;

			equp = EquipmentManager.getInstance().getEqById(vequp.getViewId());// 互联网

			int type = equp.getType();
			int index = equp.getNindex();
			int imgid = getImageResource(type, index);
			button.setTag(vequp);
			seek.setTag(vequp);
			img.setImageResource(imgid);
			text.setText(equp.getName());
			if (type == 1 || type == 2 || type == 3 || type == 4 || type == 5
					|| type == 11 || type == 6 || type == 21) {
				button.setVisibility(View.VISIBLE);
				seek.setVisibility(View.GONE);
				String svalue = "关";
				if(vequp.getViewValue()!=null){
					if (vequp.getViewValue().toUpperCase().equals("TRUE")) {
						svalue = "开";
					}
				}
				button.setText(svalue);
				button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ViewEquipment veq = (ViewEquipment) v.getTag();
						if (veq.getViewValue().equals("FALSE")) {
							veq.setViewValue("TRUE");
						} else {
							veq.setViewValue("FALSE");
						}
						Message msg = new Message();
						msg.what = CHANGE;
						handler.sendMessage(msg);
					}
				});
			} else if (type == 17)// 入侵监测
			{
				button.setVisibility(View.VISIBLE);
				seek.setVisibility(View.GONE);
				String svalue = "开启";
				if (vequp.getViewValue().equals("FALSE,STOP")) {
					svalue = "全部禁用";
				} else if (vequp.getViewValue().equals("FALSE,FORBID")) {
					svalue = "声音禁用";
				} else {
					svalue = "开启";
				}
				button.setText(svalue);
				button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ViewEquipment veq = (ViewEquipment) v.getTag();
						if (veq.getViewValue().equals("FALSE,STOP")) {
							veq.setViewValue("FALSE,FALSE");
						} else if (veq.getViewValue().equals("FALSE,FALSE")) {
							veq.setViewValue("FALSE,FORBID");
						} else {
							veq.setViewValue("FALSE,STOP");
						}
						Message msg = new Message();
						msg.what = CHANGE;
						handler.sendMessage(msg);
					}
				});
			} else if ((type == 18 || type == 19))

			{
				button.setVisibility(View.VISIBLE);
				seek.setVisibility(View.GONE);
				String svalue = "开启";
				if (vequp.getViewValue().equals("FALSE,FALSE")) {
					svalue = "禁用";
				} else if (vequp.getViewValue().equals("TRUE,FALSE")) {
					svalue = "声光禁用";
				} else {
					svalue = "开启";
				}
				button.setText(svalue);
				button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ViewEquipment veq = (ViewEquipment) v.getTag();
						if (veq.getViewValue().equals("FALSE,FALSE")) {
							veq.setViewValue("TRUE,TURE");
						} else {
							veq.setViewValue("FALSE,FALSE");
						}
						Message msg = new Message();
						msg.what = CHANGE;
						handler.sendMessage(msg);
					}
				});
			} else if ((type == 10 && index == 3) || (type == 10 && index == 4)) {
				button.setVisibility(View.VISIBLE);
				seek.setVisibility(View.GONE);
				String svalue = "禁用";
				if (vequp.getViewValue().equals("FALSE")) {
					svalue = "开启";
				}
				button.setText(svalue);
				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ViewEquipment veq = (ViewEquipment) v.getTag();
						if (veq.getViewValue().equals("FALSE")) {
							veq.setViewValue("STOP");
						} else {
							veq.setViewValue("FALSE");
						}
						Message msg = new Message();
						msg.what = CHANGE;
						handler.sendMessage(msg);
					}
				});
			} else if ((type == 16 && index == 1) || (type == 16 && index == 2)
					|| (type == 16 && index == 3)) {
				button.setVisibility(View.VISIBLE);
				seek.setVisibility(View.GONE);
				String svalue = "禁用";
				if (vequp.getViewValue().equals("FALSE")) {
					svalue = "启用";
				}
				button.setText(svalue);
				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ViewEquipment veq = (ViewEquipment) v.getTag();
						if (veq.getViewValue().equals("FALSE")) {
							veq.setViewValue("STOP");
						} else {
							veq.setViewValue("FALSE");
						}
						Message msg = new Message();
						msg.what = CHANGE;
						handler.sendMessage(msg);
					}
				});
			} else if (type == 15) {
				button.setVisibility(View.GONE);
				seek.setVisibility(View.VISIBLE);
				seek.setBackgroundResource(R.drawable.pic_color);
				int a;
				if(vequp.getViewValue().equals("0.0")){
					 a=0;
				}else {
					a = (int) Float.parseFloat(vequp.getViewValue());
				}
				if (a > 0) {
					seek.setProgress(60);
				} else {
					seek.setProgress(a);
				}
				seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						ViewEquipment veq = (ViewEquipment) seekBar.getTag();
						veq.setViewValue(String.valueOf(seekBar.getProgress()));
//						Message msg = new Message();
//						msg.what = CHANGE;
//						handler.sendMessage(msg);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
					}
				});
			} else if (type == 7 || type == 8 || type == 20) {
				button.setVisibility(View.GONE);
				seek.setVisibility(View.VISIBLE);
				 seek.setProgress((int) Float.parseFloat(vequp.getViewValue()));
				int a;
				if(vequp.getViewValue().equals("0.0")){
					 a=0;
				}else {
					a = (int) Float.parseFloat(vequp.getViewValue());
					//a=60;
				}
				if (a > 0) {
					seek.setProgress(60);
				} else {
					seek.setProgress(a);
				}
				seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						ViewEquipment veq = (ViewEquipment) seekBar.getTag();
						veq.setViewValue(String.valueOf(seekBar.getProgress()));
//						Message msg = new Message();
//						msg.what = CHANGE;
//						handler.sendMessage(msg);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
					}
				});
			} else {
				seek.setVisibility(View.GONE);
				button.setVisibility(View.GONE);
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
			result = R.drawable.shouhuanon;
			break;
		case 22:
			result = R.drawable.yangqion;
			break;

		case 156:
			result = R.drawable.eryanghuatanon;
			break;
		case 23: {
			if (index == 1) {
				result = R.drawable.xiwanji;
			} else if (index == 2) {
				result = R.drawable.zhengkaoji;
			} else if (index == 3) {
				result = R.drawable.youyanji;
			} else if (index == 4) {
				result = R.drawable.recycle;
			}
		}
			break;
		}
		return result;
	}
}