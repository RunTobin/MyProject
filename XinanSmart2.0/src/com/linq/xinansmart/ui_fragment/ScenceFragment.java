package com.linq.xinansmart.ui_fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.ModeManager;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Mode;
import com.linq.xinansmart.ui_activity.Activity_addMode;
import com.linq.xinansmart.ui_activity.EditResourceActivity;

public class ScenceFragment extends SherlockFragment implements
		android.view.View.OnClickListener {
	private ListView scence_list;
	private Concenter concenter = null;
	private ModeManager modeManager = null;
	private List<Mode> modeList = new ArrayList<Mode>();
	private ScenceAdapter scenceAdapter = null;
	private final int UPDATE = 0;
	private AlertDialog editDialog = null;
	private Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE:
				scenceAdapter.notifyDataSetChanged();
				break;

			}

		};
	};

	public ScenceFragment(Concenter concenter) {
		super();
		this.concenter = concenter;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_scence, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Button btn_add = (Button) view
				.findViewById(R.id.btn_add_scence);
		ImageButton btn_online=(ImageButton) getActivity().findViewById(R.id.btn_online);
		btn_online.setVisibility(View.GONE);
		scence_list = (ListView) view.findViewById(R.id.allequipment_list);
		scenceAdapter = new ScenceAdapter();
		scence_list.setAdapter(scenceAdapter);
		new GetModListThread().start();
		btn_add.setOnClickListener(this);
	}

	class GetModListThread extends Thread {

		@Override
		public void run() {
			super.run();
			queryMode();

		}
	}

	private void queryMode() {
		modeManager = ModeManager.getInstance(concenter);
		modeList = modeManager.getAllProfile(concenter.getUser(),
				concenter.getPassword()); // 获取所有场景

		Message msg = mhandler.obtainMessage();
		mhandler.sendEmptyMessage(UPDATE);

	}

	public void upData() {
		scenceAdapter.notifyDataSetChanged();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == 2001) {
			// Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();
			// modeList = modeManager.getInstance(concenter).getModeList();
			// upData();
			new GetModListThread().start();
		}

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), Activity_addMode.class);
		// intent.putExtra("type", concenter.getType());
		Bundle bundle = new Bundle();
		bundle.putSerializable("con", concenter);
		intent.putExtras(bundle);
		// intent.putExtra("cId", concenter.getId());
		startActivityForResult(intent, 5);

	}

	class ScenceAdapter extends BaseAdapter implements OnLongClickListener,
			android.view.View.OnClickListener {

		@Override
		public int getCount() {
			if (modeList != null && modeList.size() != 0) {
				return modeList.size();
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
		public View getView(int position, View convertView, ViewGroup arg2) {
			Holder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.fragment_scence_item, null);
				holder = new Holder();
				holder.relativeLayout = (RelativeLayout) convertView
						.findViewById(R.id.relative_item_scence);
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.item_img_scence);
				holder.textView = (TextView) convertView
						.findViewById(R.id.item_scence_text);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.imageView.setImageResource(R.drawable.scence);
			holder.textView.setText(modeList.get(position).getModeName());
			holder.relativeLayout.setTag(position);
			holder.relativeLayout.setOnClickListener(this);
			holder.relativeLayout.setOnLongClickListener(this);

			return convertView;
		}

		class Holder {
			RelativeLayout relativeLayout;
			ImageView imageView;
			TextView textView;

		}

		private int modeId;

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.relative_item_scence) {
				RelativeLayout relativeLayout = (RelativeLayout) v;
				int position = (Integer) relativeLayout.getTag();
				Mode mode = modeList.get(position);
				modeId = mode.getCenterCode();
				Runnable sendOrder = new Runnable() {
					@Override
					public void run() {

						ModeManager.getInstance(concenter).UsingAppProfile(
								modeId, concenter.getUser(),
								concenter.getPassword());
					}
				};

				new Thread(sendOrder).start();
				Toast.makeText(getActivity(),
						"场景" + mode.getModeName() + "已启动", 0).show();

			}
		}

		private void update(Mode mode) {
			modeList.remove(mode);
			Message m = new Message();
			m.what = UPDATE;
			mhandler.sendEmptyMessage(UPDATE);
		}

		private ProgressDialog progressDialog;

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub

			if (v.getId() == R.id.relative_item_scence) {
				RelativeLayout relativeLayout = (RelativeLayout) v;
				int position = (Integer) relativeLayout.getTag();
				final Mode mode = modeList.get(position);
				editDialog = new Builder(getActivity())
						.setTitle("用户编辑")
						.setItems(new String[] { "编辑", "删除" },
								new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										switch (which) {
										case 0: {
											Intent intent = new Intent(
													getActivity(),
													EditResourceActivity.class);
											intent.putExtra("modeId",
													mode.getId());
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

			}

			return true;
		}
	}

}
