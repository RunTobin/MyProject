package com.linq.xinansmart.ui_fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.internal.nineoldandroids.animation.ObjectAnimator;
import com.actionbarsherlock.internal.nineoldandroids.animation.ValueAnimator;
import com.linq.xinansmart.R;
import com.linq.xinansmart.control.BarbecueActivity;
import com.linq.xinansmart.control.ColorControlActivity;
import com.linq.xinansmart.control.Cook;
import com.linq.xinansmart.control.Garbage;
import com.linq.xinansmart.control.GuFengJiActivity;
import com.linq.xinansmart.control.OnOffActivity;
import com.linq.xinansmart.control.OnOffStopActivity;
import com.linq.xinansmart.control.ProgressActivity;
import com.linq.xinansmart.control.StartUpActivity;
import com.linq.xinansmart.control.WashActivity;
import com.linq.xinansmart.control.XiWanJiActivity;
import com.linq.xinansmart.control.XiYanJiActivity;
import com.linq.xinansmart.control.ZhengXiangActivity;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.manager.GateWayManager;
import com.linq.xinansmart.manager.ModeManager;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.GateWay;
import com.linq.xinansmart.ui_activity.EquipUpdateActivity;
import com.linq.xinansmart.web.CommManager;

public class EquipMentFragment extends SherlockFragment implements
		OnItemClickListener, OnClickListener,OnItemLongClickListener {

	private ListView listView;
	private List<Equipment> eqList = new ArrayList<Equipment>();
	private List<Equipment> OnlineEqList = new ArrayList<Equipment>();
	private List<GateWay> netList = new ArrayList<GateWay>();
	private Concenter concenter = null;
	private ImageButton btn_online = null;
	private EquipmentManager equipmentManager = EquipmentManager.getInstance();
	private GateWayManager gateWayManager = GateWayManager.getInstance();
	private EquipmentAdapter equipmentAdapter = null;
	private final int UPDATE = 0;
	private final int UPDATE1 = 1;
	public Boolean RefreshThreadRun = false;
	private Boolean Status = false;
	private Animation push_left_in,push_right_in;  
	private Boolean is = false;
	private Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE:
				getOnline();
				equipmentAdapter.notifyDataSetChanged();
				break;
			case UPDATE1:
				getOnline();
				equipmentAdapter.notifyDataSetChanged();
				rotateyAnimRun(listView);
				break;
			}
		};
	};
	
//	HomeKeyEventBroadCastReceiver receiver = new HomeKeyEventBroadCastReceiver();  
//    registerReceiver(receiver, new IntentFilter(  
//                  Intent. ACTION_CLOSE_SYSTEM_DIALOGS));  
//    
//    private static class HomeKeyEventBroadCastReceiver extends BroadcastReceiver{
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			// TODO Auto-generated method stub
//			
//		}
//    	
//    }
	
	private static class ViewWrapper {
    private View mTarget;
 
    public ViewWrapper(View target) {
        mTarget = target;
    }
 
    public int getWidth() {
        return mTarget.getLayoutParams().width;
    }
 
    public void setWidth(int width) {
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }
}
	
	public void rotateyAnimRun(View view)  
    {  
		  ViewWrapper wrapper = new ViewWrapper(view);
         ObjectAnimator//  
         .ofFloat(wrapper, "Alpha", 0.0F, 1.0F)//  
         .setDuration(1000)//  
         .start();  
    }  

	public EquipMentFragment(Concenter concenter) {
		this.concenter = concenter;

	}

	public EquipMentFragment(Concenter concenter, Boolean is) {
		this.is = is;
		this.concenter = concenter;

	}

	// @null
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_allequiment, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView = (ListView) view.findViewById(R.id.allequipment_list);
		btn_online = (ImageButton) getActivity().findViewById(R.id.btn_online);
		btn_online.setVisibility(View.VISIBLE);
		btn_online.setOnClickListener(this);
		equipmentAdapter = new EquipmentAdapter();
		push_left_in=AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in);  
        push_right_in=AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in);  
		new getThread().start();
		new Thread(refrashTask).start();

		listView.setAdapter(equipmentAdapter);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_online:
			if (Status == false) {
				Toast.makeText(getActivity(), "已显示全部设备", Toast.LENGTH_SHORT)
						.show();
				Status = true;
			} else {
				Toast.makeText(getActivity(), "已显示在线设备", Toast.LENGTH_SHORT)
						.show();
				Status = false;
			}
			break;

		default:
			break;
		}
	}

	private void getOnline() {
		// TODO Auto-generated method stub
		OnlineEqList.clear();
		for (Equipment equipment : eqList) {
			if (equipment.getbOnline()) {
				OnlineEqList.add(equipment);
			}
		}
	}

	Runnable refrashTask = new Runnable() {
		@Override
		public void run() {
			RefreshThreadRun = true;
			while (RefreshThreadRun) {
				try {
					Thread.sleep(500);
					int tick = 0;
					equipmentManager.GetLastEquipmentStatus(tick,
							concenter.getUser(), concenter.getPassword());
					// Log.e("123","123"
					eqList = equipmentManager.getAllEquipment(
							concenter.getUser(), concenter.getPassword());
					netList = gateWayManager.getAllNetgate(concenter.getUser(),
							concenter.getPassword());
					Message msg = new Message();
					msg.what = UPDATE;
					mhandler.sendMessage(msg);

					// Thread.sleep(100);
				} catch (Exception e) {
					continue;
				}

			}
			RefreshThreadRun = false;

		}

	};

	public void onStop() {
		super.onStop();
		RefreshThreadRun = false;
	};

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		RefreshThreadRun = false;
	}

	class getThread extends Thread {
		@Override
		public void run() {
			super.run();
			if (is == false) {
				try {
					netList = gateWayManager.getAllNetgate(concenter.getUser(),
							concenter.getPassword());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				netList = gateWayManager.getAllChangeNetgate(
						concenter.getUser(), concenter.getPassword());
				
			}

		
			if (is == false) {
				eqList = equipmentManager.getAllEquipment(concenter.getUser(),
						concenter.getPassword());
			} else {
				eqList = equipmentManager.getChangeAllEquipment(
						concenter.getUser(), concenter.getPassword());
				ModeManager.getInstance(concenter).getAllChangeProfile(
						concenter.getUser(), concenter.getPassword());
			}
			//Log.e("netlist+eqlist", netList + "" + eqList);
			Message msg = new Message();
			msg.what = UPDATE;
			mhandler.sendMessage(msg);
		}

		private void reMovecopy(List<Equipment> listEq) {
			for (int i = 0; i < listEq.size() - 1; i++) {
				for (int j = listEq.size() - 1; j > i; j--) {
					if (listEq.get(j).getName().equals(listEq.get(i).getName())) {
						listEq.remove(j);
					}
				}
			}
		}
	}

	class EquipmentAdapter extends BaseAdapter {

		@Override
		public int getCount() {
		//	Log.e("eqList",eqList.size()+"");
		//	Log.e("netList",netList.size()+"");
			//eqList=
			if (eqList.size() != 0 && netList.size() != 0) {
				reMovecopy();
				reMovecopy(eqList);
				reMovecopy(OnlineEqList);
				//Log.e("____________", "" + OnlineEqList.size() + netList.size());
				//Log.e("121212121","success");
				if (Status == false) {
					return OnlineEqList.size() + netList.size();
				} else {
					return eqList.size() + netList.size();
				}
			}
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		List<Equipment> lstEquipment = null;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 0,NetGate;1,Equipment
			GateWay crNetGate = null;
			Equipment crEquipment = null;
			int type = -1;
			int nPos = 0;
			int size = netList.size() + eqList.size();
			// Log.e("789", String.valueOf(size));
			// Log.e("789", String.valueOf(netList.size()));
			// Log.e("789", String.valueOf(eqList.size()));
			for (int i = 0; i < netList.size(); i++) {
				// reMovecopy();
				GateWay ng = netList.get(i);// 获得列表中的网关
				if (position == nPos) {
					type = 0;
					crNetGate = ng;
					break;
				}
				List<Equipment> lstEquipment = null;
				if (Status == false) {
					lstEquipment = equipmentManager.getEquipmentListByNetGate(
							ng, OnlineEqList);
				} else {
					lstEquipment = equipmentManager.getEquipmentListByNetGate(
							ng, eqList);
				}
				// reMovecopy(lstEquipment);
				if (nPos + lstEquipment.size() >= position) {
					type = 1;
					crEquipment = lstEquipment.get(position - nPos - 1);
					break;
				}
				nPos += lstEquipment.size() + 1;
			}
			ViewHolder viewHolder = null;
			if (convertView == null) {
				
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.fragment_allequ_item, null);
				if (crNetGate == null && crEquipment == null)
					return convertView;
				viewHolder.relativeLayout = (RelativeLayout) convertView
						.findViewById(R.id.relative_item);
				viewHolder.imageView = (ImageView) convertView
						.findViewById(R.id.item_img);
				viewHolder.text_location = (TextView) convertView
						.findViewById(R.id.item_text_location);
				viewHolder.text_type = (TextView) convertView
						.findViewById(R.id.item_text_type);
				viewHolder.text_value = (TextView) convertView
						.findViewById(R.id.item_text_value);
				viewHolder.text_netgate = (TextView) convertView
						.findViewById(R.id.item_text_netgate);
				viewHolder.img_netgate = (ImageView) convertView
						.findViewById(R.id.item_img_netaget);

				convertView.setTag(R.id.tag_zero, viewHolder);
//				if (position % 2 == 0) {  
//                    push_left_in.setDuration(1000);  
//                    convertView.setAnimation(push_left_in);  
//                } else {  
//                    push_right_in.setDuration(1000);  
//                    convertView.setAnimation(push_right_in);  
//                }  
				
			} else {
				viewHolder = (ViewHolder) convertView.getTag(R.id.tag_zero);
			}
			if (type == 0) {
				convertView.setTag(R.id.tag_second, null);
				viewHolder.text_netgate.setVisibility(View.VISIBLE);
				viewHolder.img_netgate.setVisibility(View.VISIBLE);
				viewHolder.text_netgate.setText(crNetGate.getName());
				viewHolder.text_location.setVisibility(View.GONE);
				viewHolder.text_type.setVisibility(View.GONE);
				viewHolder.text_value.setVisibility(View.GONE);
			} else {
				convertView.setTag(R.id.tag_second, crEquipment);
				viewHolder.text_netgate.setVisibility(View.GONE);
				viewHolder.img_netgate.setVisibility(View.GONE);
				viewHolder.imageView.setVisibility(View.VISIBLE);
				viewHolder.imageView.setImageResource(equipmentManager
						.getImage(crEquipment));
				viewHolder.text_location.setVisibility(View.VISIBLE);
				viewHolder.text_type.setVisibility(View.VISIBLE);
				viewHolder.text_value.setVisibility(View.VISIBLE);
				viewHolder.text_type.setText(crEquipment.getName());
				String location = "长按编辑位置";
				viewHolder.text_location.setText(location);
				if (!"".equals(crEquipment.getAddress())) {
					location = crEquipment.getAddress();
					viewHolder.text_location.setText(crEquipment.getAddress());
				}
				viewHolder.text_value.setText(equipmentManager.GetDisplayValue(
						crEquipment.getType(), crEquipment.getNindex(),
						crEquipment.getSvalue()));

			}
			return convertView;
		}

		class ViewHolder {
			RelativeLayout relativeLayout;
			ImageView imageView;
			TextView text_location;
			TextView text_type;
			TextView text_value;
			TextView text_netgate;
			ImageView img_netgate;

		}
	}

	private void reMovecopy() {
		if (netList.size() != 0) {
			for (int i = 0; i < netList.size() - 1; i++) {
				for (int j = netList.size() - 1; j > i; j--) {
					if (netList.get(j).equals(netList.get(i))) {
						netList.remove(j);
					}
				}
			}
		}
	}

	private void reMovecopy(List<Equipment> listEq) {
		for (int i = 0; i < listEq.size() - 1; i++) {
			for (int j = listEq.size() - 1; j > i; j--) {
//				if (listEq.get(j).getName().equals(listEq.get(i).getName())) {
					if (listEq.get(j).getId()==listEq.get(i).getId()) {
					listEq.remove(j);
				}
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {

		Equipment equipment = (Equipment) view.getTag(R.id.tag_second);
		if (equipment != null) {
			if (equipment.getbOnline() == true) {
				int equipmentId = equipment.getId();
				int cenType = 1;

				switch (equipment.getType()) {

				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 11:
				case 13:
				case 159:
				case 14:
					Intent intent = new Intent(getActivity(),
							OnOffActivity.class);
					intent.putExtra("equipmentId", equipmentId);
					intent.putExtra("cenType", cenType);
					intent.putExtra("user", concenter.getUser());
					intent.putExtra("password", concenter.getPassword());
					startActivity(intent);
					break;
				case 6:
					Intent intent2 = new Intent(getActivity(),
							OnOffStopActivity.class);
					intent2.putExtra("equipmentId", equipmentId);
					intent2.putExtra("cenType", cenType);
					intent2.putExtra("user", concenter.getUser());
					intent2.putExtra("password", concenter.getPassword());
					startActivity(intent2);
					break;
				case 7:
				case 8:
				case 20:
					Intent intent3 = new Intent(getActivity(),
							ProgressActivity.class);
					intent3.putExtra("equipmentId", equipmentId);
					intent3.putExtra("cenType", cenType);
					intent3.putExtra("user", concenter.getUser());
					intent3.putExtra("password", concenter.getPassword());
					startActivity(intent3);
					break;
				case 9:
					break;
				case 10: {
					switch (equipment.getNindex()) {
					case 1:
						break;
					case 2:
						break;
					case 3:
						Intent intent4 = new Intent(getActivity(),
								StartUpActivity.class);
						intent4.putExtra("equipmentId", equipmentId);
						intent4.putExtra("cenType", cenType);
						intent4.putExtra("user", concenter.getUser());
						intent4.putExtra("password", concenter.getPassword());
						startActivity(intent4);
						break;
					case 4:
						Intent intent5 = new Intent(getActivity(),
								StartUpActivity.class);
						intent5.putExtra("equipmentId", equipmentId);
						intent5.putExtra("cenType", cenType);
						intent5.putExtra("user", concenter.getUser());
						intent5.putExtra("password", concenter.getPassword());
						startActivity(intent5);
						break;
					}
				}
					break;
				case 15:
					Intent intent6 = new Intent(getActivity(),
							ColorControlActivity.class);
					intent6.putExtra("equipmentId", equipmentId);
					intent6.putExtra("cenType", cenType);
					intent6.putExtra("user", concenter.getUser());
					intent6.putExtra("password", concenter.getPassword());
					startActivity(intent6);
					break;
				case 23: {

					switch (equipment.getNindex()) {
					case 1:
						Intent wash = new Intent(getActivity(),
								WashActivity.class);
						wash.putExtra("equipmentId", equipmentId);
						wash.putExtra("user", concenter.getUser());
						wash.putExtra("password", concenter.getPassword());
						startActivity(wash);
						break;
					case 2:
						Intent barbecue = new Intent(getActivity(),
								BarbecueActivity.class);
						barbecue.putExtra("equipmentId", equipmentId);
						barbecue.putExtra("user", concenter.getUser());
						barbecue.putExtra("password", concenter.getPassword());
						startActivity(barbecue);
						break;
					case 3:
						Intent cook = new Intent(getActivity(), Cook.class);
						cook.putExtra("equipmentId", equipmentId);
						cook.putExtra("user", concenter.getUser());
						cook.putExtra("password", concenter.getPassword());
						startActivity(cook);
						break;
					case 4:
						Intent garbage = new Intent(getActivity(),
								Garbage.class);
						garbage.putExtra("equipmentId", equipmentId);
						garbage.putExtra("user", concenter.getUser());
						garbage.putExtra("password", concenter.getPassword());
						startActivity(garbage);
						break;

					}

				}
				case 24: {

					switch (equipment.getNindex()) {
					case 1:
						Intent wash = new Intent(getActivity(),
								ZhengXiangActivity.class);
						wash.putExtra("equipmentId", equipmentId);
						wash.putExtra("user", concenter.getUser());
						wash.putExtra("password", concenter.getPassword());
						startActivity(wash);
						break;
					case 2:
						Intent barbecue = new Intent(getActivity(),
								XiWanJiActivity.class);
						barbecue.putExtra("equipmentId", equipmentId);
						barbecue.putExtra("user", concenter.getUser());
						barbecue.putExtra("password", concenter.getPassword());
						startActivity(barbecue);
						break;
					case 3:
						Intent cook = new Intent(getActivity(), Cook.class);
						cook.putExtra("equipmentId", equipmentId);
						cook.putExtra("user", concenter.getUser());
						cook.putExtra("password", concenter.getPassword());
						startActivity(cook);
						break;
					case 4:
						Intent garbage = new Intent(getActivity(),
								Garbage.class);
						garbage.putExtra("equipmentId", equipmentId);
						garbage.putExtra("user", concenter.getUser());
						garbage.putExtra("password", concenter.getPassword());
						startActivity(garbage);
						break;

					}

				}
					break;
				case 25:
					EquipmentManager.getInstance().SetEquipmentValue(
							equipment.getNcode(), equipment.getType(),
							equipment.getMachinID(), "1,100|2,600|3,200|4,500",
							equipment.getNindex(), concenter.getUser(), concenter.getPassword());
					break;
				default:
					break;
				}
			} else {
				Toast.makeText(getActivity(), "该设备不在线！", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		Equipment equipment = (Equipment) view.getTag(R.id.tag_second);
		if (equipment != null) {
			Intent in = new Intent();
			in.setClass(getActivity(), EquipUpdateActivity.class);
			in.putExtra("eqId", equipment.getId());
			in.putExtra("user", concenter.getUser());
			in.putExtra("password", concenter.getPassword());
			startActivity(in);
		}
		return true;
	}

}
