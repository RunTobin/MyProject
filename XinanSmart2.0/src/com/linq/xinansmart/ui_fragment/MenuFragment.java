package com.linq.xinansmart.ui_fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.linq.xinansmart.R;
import com.linq.xinansmart.control.OnOffActivity;
import com.linq.xinansmart.control.XiWanJiActivity;
import com.linq.xinansmart.control.ZhengXiangActivity;
import com.linq.xinansmart.manager.ConcenterManager;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.manager.GateWayManager;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.GateWay;
import com.linq.xinansmart.ui_activity.AboutActivity;
import com.linq.xinansmart.ui_activity.Activity_concenterEdit;
import com.linq.xinansmart.ui_activity.Activity_Login;
import com.linq.xinansmart.ui_activity.HelpWordActivity;

public class MenuFragment extends SherlockFragment implements
		OnItemClickListener, OnItemLongClickListener,
		android.view.View.OnClickListener {

	private ListView menu_list = null;
	private MenuAdapter menuAdapter = null;
	private Handler mhandler;
	private List<GateWay> netList = new ArrayList<GateWay>();
	private List<Equipment> eqList = new ArrayList<Equipment>();
	private boolean update = false;
	private List<Concenter> conList = new ArrayList<Concenter>();
	private GateWayManager gateWayManager = null;
	private EquipmentManager equipmentManager = null;
	private AlertDialog dialog = null;
	private ConcenterManager concenterManager = null;
	private Context context;
	private boolean add = false;

	public List<Concenter> getConList() {
		return conList;
	}

	public void setConList(List<Concenter> conList) {
		this.conList = conList;
	}

	public MenuFragment(Handler mhandler, Context context) {
		super();
		this.context = context;
		this.mhandler = mhandler;
		this.gateWayManager = GateWayManager.getInstance();
		this.equipmentManager = EquipmentManager.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_menu, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		RelativeLayout addRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.relative_add_web);
		RelativeLayout guideRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.relative_guide);
		RelativeLayout aboutRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.relative_about);
		menuAdapter = new MenuAdapter();
		menu_list = (ListView) view.findViewById(R.id.menu_list);
		queryConcenter();
		menu_list.setAdapter(menuAdapter);
		menu_list.setOnItemClickListener(this);
		menu_list.setOnItemLongClickListener(this);
		addRelativeLayout.setOnClickListener(this);
		guideRelativeLayout.setOnClickListener(this);
		aboutRelativeLayout.setOnClickListener(this);
	}

	private void queryConcenter() {
		concenterManager = ConcenterManager.getInstance(context);
		List<Concenter> reusltlist = concenterManager.getconList(); // 查询数据库的控制中心列表
		if (reusltlist != null) {
			conList = reusltlist; // 当数据库中列表不为空的 时候复制给menuList中的conList
			// 更新menuList列表
			Message msg = mhandler.obtainMessage();
			mhandler.sendEmptyMessage(20);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_add_web:
			Intent intent = new Intent(getActivity(), Activity_Login.class); // 登录账号
			startActivityForResult(intent, 1);
			break;

		case R.id.relative_guide:
			//使用指南
			Intent i = new Intent();
			i.setClass(getActivity(), HelpWordActivity.class);
			startActivity(i);
			break;
		case R.id.relative_about:
			//跳转关于页面
			Intent inte = new Intent();
			inte.putExtra("111", "1111");
			inte.setClass(getActivity(), AboutActivity.class);
			startActivity(inte);
			
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == 2) {
			String name = data.getStringExtra("name");
			String user = data.getStringExtra("user");
			String password = data.getStringExtra("password");
			if (user != null && password != null) {
				Concenter concenter = new Concenter();
				concenter.setName(name);
				concenter.setUser(user);
				concenter.setPassword(password); // 建立新的concenter对象
				add = true;
				if (conList.size() != 0) {
					for (int i = 0; i < conList.size(); i++) {// 遍历已有的conList列表
																// 如果有用户名或账号重复
																// 就提示 不在添加
						if (conList.get(i).getName().equalsIgnoreCase(name)) {
							Message msg = mhandler.obtainMessage();
							mhandler.sendEmptyMessage(70);
							break;
						}
						if (conList.get(i).getUser().equalsIgnoreCase(user)) {
							Message msg = mhandler.obtainMessage();
							mhandler.sendEmptyMessage(80);
							break;

						} else
							new getNetGateThread(concenter).start();
					}
				} else
					new getNetGateThread(concenter).start(); // 如果menuconList列表为空
			}
		}

		if (requestCode == 3 && resultCode == 4) {
			String name = data.getStringExtra("name");
			String user = data.getStringExtra("user");
			String password = data.getStringExtra("password");
			int pos = data.getIntExtra("pos", -1);
			Log.e("pos", pos + "");
			if (user != null && password != null) {
				if (conList.size() != 0) {
					Concenter concenter = conList.get(pos);// 0之前的concenter对象
					for (int i = 0; i < conList.size(); i++) {
						if (conList.get(i).getUser().equalsIgnoreCase(user) && conList.get(i).getName().equals(name)) {
//							Message msg = mhandler.obtainMessage();
//							mhandler.sendEmptyMessage(80);
							break;
						}

						else {
							concenterManager.updateConcenter(concenter, name,
									user, password);// 更新数据库中的concenter对象
							queryConcenter(); // 更新menu 中的conlist列表
							concenter = conList.get(pos);
							Log.e("gengxinname", concenter.getName()
									+ concenter.getUser());
							new getNetGateThread(concenter);
						}
					}

				}
			}
		}

	}

	public void upData() {
		menuAdapter.notifyDataSetChanged();
		Log.e("updata", "updata");
	}

	class getNetGateThread extends Thread {

		private Concenter concenter = null;

		public getNetGateThread(Concenter concenter) {
			this.concenter = concenter;
		}

		@Override
		public void run() {
			super.run();
			try {
				netList = gateWayManager.getAllNetgate(concenter.getUser(),
						concenter.getPassword());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (netList == null) {
				Message msg = mhandler.obtainMessage();
				mhandler.sendEmptyMessage(40);
			} else {
				if (add) {
					concenterManager.addConcenter(concenter.getName(),
							concenter.getUser(), concenter.getPassword()); // 把控制中心添加到数据库中
					queryConcenter(); // Id为1
					add = false;

				}
				eqList = equipmentManager.getAllEquipment(concenter.getUser(),
						concenter.getPassword());
				// if (eqList != null) {
				// // Log.e("updata", "updatass");
				// // Message msg = mhandler.obtainMessage();
				// // mhandler.sendEmptyMessage(20);
				//
				// }

			}
		}
	}

	class MenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (conList != null && conList.size() != 0) {
				return conList.size();
			}
			return 0;
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
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.fragment_menu_item, null);
				viewHolder.imageView = (ImageView) convertView
						.findViewById(R.id.add_net_image);
				viewHolder.textView = (TextView) convertView
						.findViewById(R.id.text_net);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.imageView.setImageResource(R.drawable.img_web);
			viewHolder.textView.setText(conList.get(position).getName());
			convertView.setTag(R.id.tag_first, conList.get(position));
			return convertView;
		}

		class ViewHolder {
			ImageView imageView;
			TextView textView;

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {

		Concenter concenter = (Concenter) view.getTag(R.id.tag_first);
//		Toast.makeText(getActivity(),
//				position + concenter.toString() + concenter.getUser() + "被点击了",
//				Toast.LENGTH_SHORT).show();

		String currentName = concenter.getName();
		Message msg = mhandler.obtainMessage();
		msg.what = 30;
		msg.obj = concenter;
		msg.arg1 = position;
		mhandler.sendMessage(msg);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view,
			int position, long arg3) {
		Log.e("long", "能进去？");
		Concenter concenter = (Concenter) view.getTag(R.id.tag_first);
		int pos = position;
		editDialog(concenter, pos);
		String currentUser = concenter.getUser();
		Log.e("currentUser", currentUser);
		dialog.show();
		return true;
	}

	public void editDialog(final Concenter concenter, final int position) {
		dialog = new Builder(getActivity())
				.setTitle("用户编辑")
				.setItems(new String[] { "编辑", "删除", "修改密码" },
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								switch (which) {
								case 0: {
									Intent intent = new Intent(getActivity(),
											Activity_concenterEdit.class);
									String name = concenter.getName();
									String user = concenter.getUser();
									String password = concenter.getPassword();
									intent.putExtra("pos", position);
									intent.putExtra("name", name);
									intent.putExtra("user", user);
									intent.putExtra("password", password);
									startActivityForResult(intent, 3);
								}
									break;
								case 1: {

									Message msg = mhandler.obtainMessage();
									msg.what = 60;
									// msg.arg1 = position;
									msg.obj = concenter;
									mhandler.sendMessage(msg);
									conList.remove(concenter);
									concenterManager.deleteConcenter(concenter);
									Log.e("colist", conList.size() + " ");
								}
									break;
								case 2: {

								}
									break;
								}
							}
						}).create();

	}

}
