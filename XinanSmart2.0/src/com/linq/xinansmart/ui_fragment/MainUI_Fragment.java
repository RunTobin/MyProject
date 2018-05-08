package com.linq.xinansmart.ui_fragment;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.linq.xinansmart.R;
import com.linq.xinansmart.adapter.Grid_Location_Adapter;
import com.linq.xinansmart.adapter.Grid_Scence_Adapter;
import com.linq.xinansmart.manager.LocationManager;
import com.linq.xinansmart.manager.Location_EquipmentManager;
import com.linq.xinansmart.manager.ModeManager;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Location;
import com.linq.xinansmart.model.Location_equipment;
import com.linq.xinansmart.model.Mode;
import com.linq.xinansmart.ui_activity.Activity_Edit_Location_Detail;
import com.linq.xinansmart.ui_activity.Activity_Location_Detail;
import com.linq.xinansmart.ui_activity.Activity_addLocation_background;
import com.linq.xinansmart.ui_activity.Activity_addMode;
import com.linq.xinansmart.ui_activity.EditResourceActivity;
import com.linq.xinansmart.ui_fragment.ScenceFragment.GetModListThread;

public class MainUI_Fragment extends SherlockFragment implements
		OnClickListener {

	private GridView gridView_location = null;
	private GridView gridView_scence = null;
	private Grid_Location_Adapter grid_Location_Adapter = null;
	private Grid_Scence_Adapter grid_scence_Adapter = null;
	private Button btn_main_add;
	private Concenter concenter;
	private ModeManager modeManager;
	private List<Mode> modeList = new ArrayList<Mode>();
	private AlertDialog editDialog = null;
	private LocationManager locationManager;
	private List<Location> locationlistList;
	private List<Location> locationlist = new ArrayList<Location>();
	private Location_EquipmentManager location_EquipmentManager;
	private List<Location_equipment> locationequimentList;
	private Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				grid_Location_Adapter.notifyDataSetChanged();
				grid_scence_Adapter.notifyDataSetChanged();
				break;

			}

		};
	};

	class GetModListThread extends Thread {

		@Override
		public void run() {
			super.run();
			queryMode();

		}

	}

	private void queryMode() {
		// TODO Auto-generated method stub
		modeManager = ModeManager.getInstance(concenter);
		modeList = modeManager.getAllProfile(concenter.getUser(),
				concenter.getPassword()); // 获取所有场景
		grid_scence_Adapter.addModeList(modeList);
		Message msg = mhandler.obtainMessage();
		mhandler.sendEmptyMessage(0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == 2001) {
			new GetModListThread().start();
		}
		if (resultCode == 2016) {
			// Toast.makeText(getActivity(), "哈哈哈", Toast.LENGTH_SHORT).show();
			if(locationlist!=null){
				locationlist.clear();
			}
			queryLocation();
		}

	}

	public MainUI_Fragment(Concenter concenter) {
		// TODO Auto-generated constructor stub
		this.concenter = concenter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main_uin, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		btn_main_add = (Button) view.findViewById(R.id.btn_main_add);
		gridView_location = (GridView) view
				.findViewById(R.id.gridview_location);
		gridView_scence = (GridView) view.findViewById(R.id.gridview_scence);
		ImageButton btn_online=(ImageButton) getActivity().findViewById(R.id.btn_online);
		btn_online.setVisibility(View.GONE);
		grid_Location_Adapter = new Grid_Location_Adapter(getActivity());
		gridView_location.setAdapter(grid_Location_Adapter);
		grid_scence_Adapter = new Grid_Scence_Adapter(getActivity());
		gridView_scence.setAdapter(grid_scence_Adapter);
		gridView_location.setOnItemClickListener(new location_item_listener());
		gridView_scence.setOnItemClickListener(new scence_item_listener());
		gridView_location
				.setOnItemLongClickListener(new location_item_listener2());
		gridView_scence.setOnItemLongClickListener(new scence_item_listener2());
		btn_main_add.setOnClickListener(this);
		this.registerForContextMenu(btn_main_add);
		queryLocation();
		new GetModListThread().start();

	}

	private void queryLocation() {
		// TODO Auto-generated method stub
		locationManager = LocationManager.getInstance(getActivity());
		locationlistList = locationManager.getLocationList();
		if (locationlistList!= null) {
			locationlist.clear();
			Log.e("location", String.valueOf(locationlistList.size()));
			for (Location location : locationlistList) {
				if (location.getConcenter().equals(concenter.getUser())) {
					locationlist.add(location);
				}
			}
		}
		grid_Location_Adapter.addLocationList(locationlist);
		grid_Location_Adapter.notifyDataSetChanged();
		location_EquipmentManager = Location_EquipmentManager
				.getInstance(getActivity());
		locationequimentList = location_EquipmentManager
				.getLocation_equipList();
//		if (locationlistList.size() > 0) {
//			Log.e("location", String.valueOf(locationlistList.size()));
//		}
//		if (locationequimentList.size() > 0) {
//			Log.e("location_equiment",
//					String.valueOf(locationequimentList.size()));
//		}

	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		queryLocation();
	}

	class location_item_listener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Location location = locationlist.get(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable("location", location);
			Intent intent = new Intent();
			intent.setClass(getActivity(), Activity_Location_Detail.class);
			intent.putExtras(bundle);
			bundle.putSerializable("con", concenter);
			intent.putExtras(bundle);
			startActivity(intent);

		}

	}

	private int modeId;

	class scence_item_listener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Mode mode = modeList.get(position);
			modeId = mode.getCenterCode();
			Runnable sendOrder = new Runnable() {
				@Override
				public void run() {

					ModeManager.getInstance(concenter).UsingAppProfile(modeId,
							concenter.getUser(), concenter.getPassword());
				}
			};

			new Thread(sendOrder).start();
			Toast.makeText(getActivity(), "场景" + mode.getModeName() + "已启动", 0)
					.show();
		}

	}

	private ProgressDialog progressDialog;

	class location_item_listener2 implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				final int position, long id) {
			// TODO Auto-generated method stub

			// final Location location = locationlistList.get(position);
			editDialog = new Builder(getActivity())
					.setTitle("用户编辑")
					.setItems(new String[] { "编辑", "删除" },
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									final Location location = locationlist
											.get(position);
									switch (which) {
									case 0:
										// Location location =
										// locationlistList.get(position);
										Bundle bundle = new Bundle();
										bundle.putSerializable("location",
												location);
										Intent intent = new Intent();
										intent.setClass(
												getActivity(),
												Activity_Edit_Location_Detail.class);
										intent.putExtras(bundle);
										bundle.putSerializable("con", concenter);
										intent.putExtras(bundle);
										startActivity(intent);

										break;
									case 1:
										locationManager
												.deleteLocation(location);
										location_EquipmentManager
												.deletLocationEquiment_By_Location(location);
										queryLocation();
										break;

									default:
										break;
									}
								}

							}).create();
			editDialog.show();
			return true;
		}

	}

	class scence_item_listener2 implements OnItemLongClickListener {
		private void update(Mode mode) {
			modeList.remove(mode);
			Message m = new Message();
			m.what = 0;
			mhandler.sendEmptyMessage(0);
		}

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			final Mode mode = modeList.get(position);
			editDialog = new Builder(getActivity())
					.setTitle("用户编辑")
					.setItems(new String[] { "编辑", "删除" },
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									switch (which) {
									case 0: {
										Intent intent = new Intent(
												getActivity(),
												EditResourceActivity.class);
										intent.putExtra("modeId", mode.getId());
										intent.putExtra("user",
												concenter.getUser());
										intent.putExtra("password",
												concenter.getPassword());
										Bundle bundle = new Bundle();
										bundle.putSerializable("con", concenter);
										intent.putExtras(bundle);
										startActivityForResult(intent, 5);
										break;
									}
									case 1:
										// 删除场景
										// Toast.makeText(getActivity(),
										// "123",
										// Toast.LENGTH_SHORT).show();
										editDialog.dismiss();
										progressDialog = new ProgressDialog(
												getActivity());
										progressDialog.show();
										progressDialog.setMessage("删除中。。");
										DeleteMode();

										break;
									}
								}

								private void DeleteMode() {

									Runnable run = new Runnable() {

										@Override
										public void run() {
											modeManager.DeleteAppProfile(
													mode.getId(),
													concenter.getUser(),
													concenter.getPassword());
											update(mode);
											getActivity().runOnUiThread(
													new Runnable() {

														@Override
														public void run() {
															// TODO
															// Auto-generated
															// method stub

															progressDialog
																	.dismiss();
														}
													});
										}
									};
									new Thread(run).start();

								}

							}).create();
			editDialog.show();
			return true;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		btn_main_add.showContextMenu();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {
		menu.add("新增区域");
		menu.add("新增场景");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if ("新增区域".equals(item.getTitle())) {
			Intent intent = new Intent(getActivity(),
					Activity_addLocation_background.class);
			// intent.putExtra("type", concenter.getType());
			Bundle bundle = new Bundle();
			bundle.putSerializable("con", concenter);
			intent.putExtras(bundle);
			// intent.putExtra("cId", concenter.getId());
			startActivityForResult(intent, 5);

		} else if ("新增场景".equals(item.getTitle())) {
			Intent intent = new Intent(getActivity(), Activity_addMode.class);
			// intent.putExtra("type", concenter.getType());
			Bundle bundle = new Bundle();
			bundle.putSerializable("con", concenter);
			intent.putExtras(bundle);
			// intent.putExtra("cId", concenter.getId());
			startActivityForResult(intent, 5);
		}
		return super.onContextItemSelected(item);
	}

}
