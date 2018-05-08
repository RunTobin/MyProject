package com.tutk.P2PCam264;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.linq.xinansmart.R;
import com.tutk.IOTC.AVIOCTRLDEFs;
import com.tutk.IOTC.AVIOCTRLDEFs.SMsgAVIoctrlListEventReq;
import com.tutk.IOTC.AVIOCTRLDEFs.STimeDay;
import com.tutk.IOTC.Camera;
import com.tutk.IOTC.IRegisterIOTCListener;
import com.tutk.P2PCam264.RefreshableView.PullToRefreshListener;

public class EventListActivity extends SherlockActivity implements IRegisterIOTCListener {

	private static final int Build_VERSION_CODES_ICE_CREAM_SANDWICH = 14;

	private static final int OPT_MENU_ITEM_SEARCH = 0;
	private static final int REQUEST_CODE_EVENT_DETAIL = 0;
	private static final int REQUEST_CODE_EVENT_SEARCH = 1;

	private List<EventInfo> list = Collections.synchronizedList(new ArrayList<EventInfo>());
	private EventListAdapter adapter;

	private MyCamera mCamera;

	private View searchTimeView = null;
	private View loadingView = null;
	private View offlineView = null;
	private View noResultView = null;

	private ListView eventListView = null;

	private String mDevUUID;
	private String mDevUID;
	private String mDevNickName;
	private String mViewAcc;
	private String mViewPwd;
	private int mCameraChannel;

	private Boolean mIsSearchingEvent = false;
	
	RefreshableView refreshableView;

	private long mStartTime = -1;
	private long mStopTime = -1;
	private int mEventType = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.event_view);

		if (Build.VERSION.SDK_INT < Build_VERSION_CODES_ICE_CREAM_SANDWICH) {
			BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(R.drawable.bg_striped);
			bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setBackgroundDrawable(bg);
		}

		Bundle bundle = this.getIntent().getExtras();
		mDevUUID = bundle.getString("dev_uuid");
		mDevUID = bundle.getString("dev_uid");
		mDevNickName = bundle.getString("dev_nickname");
		mCameraChannel = bundle.getInt("camera_channel");
		mViewAcc = bundle.getString("view_acc");
		mViewPwd = bundle.getString("view_pwd");

		/* register recvIOCtrl listener */
		for (MyCamera camera : MainActivity.CameraList) {

			if (mDevUUID.equalsIgnoreCase(camera.getUUID())) {
				mCamera = camera;
				mCamera.registerIOTCListener(this);
				mCamera.resetEventCount();

				break;
			}
		}

		adapter = new EventListAdapter(this);
		refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				//try {
				//	Thread.sleep(3000);
				//} catch (InterruptedException e) {
				//	e.printStackTrace();
				//}
				refreshableView.finishRefreshing();
				if (!mIsSearchingEvent) 
				{
					eventListView.removeFooterView(loadingView);
					eventListView.removeFooterView(noResultView);
					if (mCamera == null || !mCamera.isChannelConnected(0)) {
						eventListView.addFooterView(offlineView);
						eventListView.setAdapter(adapter);
					} else 
					{
						if(mStartTime!=-1&&mStopTime!=-1)
						{
							searchEventList(mStartTime, mStopTime, mEventType);
						}
						else
						{
							initEventList();
						}
					}
				}
				
			}
		},1);
		eventListView = (ListView) findViewById(R.id.lstEventList);
		eventListView.setOnItemClickListener(listViewOnItemClickListener);

		searchTimeView = getLayoutInflater().inflate(R.layout.search_event_result, null);
		loadingView = getLayoutInflater().inflate(R.layout.loading_events, null);
		offlineView = getLayoutInflater().inflate(R.layout.camera_is_offline, null);
		noResultView = getLayoutInflater().inflate(R.layout.no_result, null);

		if (mCamera == null || !mCamera.isChannelConnected(0)) {
			eventListView.addFooterView(offlineView);
			eventListView.setAdapter(adapter);
		} else {
			initEventList();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		quit();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// FlurryAgent.onStartSession(this, "Q1SDXDZQ21BQMVUVJ16W");
	}

	@Override
	protected void onStop() {
		super.onStop();
		// FlurryAgent.onEndSession(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_EVENT_DETAIL) {

			if(data!=null){
				Bundle extras = data.getExtras();
				String evtUUID = extras.getString("event_uuid");
	
				for (EventInfo evt : list) {
	
					if (evt.getUUID().equalsIgnoreCase(evtUUID)) {
						evt.EventStatus = EventInfo.EVENT_READED;
						adapter.notifyDataSetChanged();
						break;
					}
				}
			}	
		}

		else if (requestCode == REQUEST_CODE_EVENT_SEARCH && resultCode == RESULT_OK) {

			if(data != null){
				Bundle extras = data.getExtras();
				long startTime = extras.getLong("start_time");
				long stopTime = extras.getLong("stop_time");
				int eventType = extras.getInt("event_type");
				
				mStartTime = startTime;
				mStopTime = stopTime;
				mEventType = eventType;
	
				searchEventList(startTime, stopTime, eventType);
			}	
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:

			quit();

			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		Configuration cfg = getResources().getConfiguration();

		if (cfg.orientation == Configuration.ORIENTATION_LANDSCAPE) {

		} else if (cfg.orientation == Configuration.ORIENTATION_PORTRAIT) {

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(Menu.NONE, OPT_MENU_ITEM_SEARCH, 1, "Search").setIcon(R.drawable.ic_menu_search).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == OPT_MENU_ITEM_SEARCH) {

			if (!mIsSearchingEvent) {

				Bundle extras = new Bundle();
				extras.putString("dev_uid", mDevUID);

				Intent intent = new Intent();
				intent.putExtras(extras);
				intent.setClass(EventListActivity.this, SearchEventActivity.class);

				startActivityForResult(intent, REQUEST_CODE_EVENT_SEARCH);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	private AdapterView.OnItemClickListener listViewOnItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

			if (list.size() == 0 || list.size() < position || !mCamera.getPlaybackSupported(0))
				return;

			int pos = position - eventListView.getHeaderViewsCount();

			if (pos < 0)
				return;

			EventInfo evt = list.get(pos);

			if (evt.EventStatus == EventInfo.EVENT_NORECORD)
				return;

			Bundle extras = new Bundle();
			extras.putString("dev_uuid", mDevUUID);
			extras.putString("dev_nickname", mDevNickName);
			extras.putInt("camera_channel", mCameraChannel);
			extras.putInt("event_type", evt.EventType);
			extras.putLong("event_time", evt.Time);
			extras.putString("event_uuid", evt.getUUID());
			extras.putString("view_acc", mViewAcc);
			extras.putString("view_pwd", mViewPwd);
			extras.putByteArray("event_time2", evt.EventTime.toByteArray());

			Intent intent = new Intent();
			intent.putExtras(extras);
			intent.setClass(EventListActivity.this, PlaybackActivity.class);
			startActivityForResult(intent, REQUEST_CODE_EVENT_DETAIL);
		}
	};

	private void quit() {

		if (mCamera != null) {

			mCamera.unregisterIOTCListener(this);
			mCamera = null;
		}

		// update history view info back to database
		// DatabaseManager dm = new DatabaseManager(this);
		// dm.updateDeviceHistoryViewByUID(mDevUID, System.currentTimeMillis());
		// dm = null;
	}

	private void initEventList() {

		// m_newHistoryView = System.currentTimeMillis();
		// searchEventList(m_origHistoryView, m_newHistoryView,
		// AVAPIs.AVIOCTRL_EVENT_ALL);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, -1 * 60 * 12);
		searchEventList(calendar.getTimeInMillis(), System.currentTimeMillis(), AVIOCTRLDEFs.AVIOCTRL_EVENT_ALL);
	}

	private static String getLocalTime(long utcTime, boolean subMonth) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
		calendar.setTimeInMillis(utcTime);

		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.setTimeZone(TimeZone.getDefault());

		if (subMonth)
			calendar.add(Calendar.MONTH, -1);

		return dateFormat.format(calendar.getTime());
	}

	private void searchEventList(long startTime, long stopTime, int eventType) {

		if (mCamera != null) {

			list.clear();
			adapter.notifyDataSetChanged();

			mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTEVENT_REQ, SMsgAVIoctrlListEventReq.parseConent(mCameraChannel, startTime, stopTime, (byte) eventType, (byte) 0));

			eventListView.removeFooterView(noResultView);
			eventListView.addFooterView(loadingView);

			// set search time to actionbar title
			String startTimeSring = getLocalTime(startTime, false);
			String stopTimeString = getLocalTime(stopTime, false);
			// getSupportActionBar().setSubtitle(startTimeSring + " - " +
			// stopTimeString);
			TextView txtSearchTime = (TextView) searchTimeView.findViewById(R.id.txtSearchTimeDuration);
			txtSearchTime.setText(startTimeSring + " - " + stopTimeString);

			if (eventListView.getHeaderViewsCount() == 0)
				eventListView.addHeaderView(searchTimeView);

			eventListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();

			mIsSearchingEvent = true;

			/* timeout for no search result been found */
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {

					if (mIsSearchingEvent) {

						mIsSearchingEvent = false;
						eventListView.removeHeaderView(searchTimeView);
						eventListView.removeFooterView(loadingView);
						eventListView.addFooterView(noResultView);
						adapter.notifyDataSetChanged();
					}
				}

			}, 180000);

		}

		/*
		 * - test start - *
		 * 
		 * handler.postDelayed(new Runnable() {
		 * 
		 * @Override public void run() {
		 * 
		 * list.add(new EventInfo(AVAPIs.AVIOCTRL_EVENT_MOTIONDECT,
		 * System.currentTimeMillis())); list.add(new
		 * EventInfo(AVAPIs.AVIOCTRL_EVENT_MOTIONDECT,
		 * System.currentTimeMillis()));
		 * 
		 * adapter.notifyDataSetChanged(); }
		 * 
		 * }, 2000);
		 * 
		 * handler.postDelayed(new Runnable() {
		 * 
		 * @Override public void run() {
		 * 
		 * list.add(new EventInfo(AVAPIs.AVIOCTRL_EVENT_MOTIONDECT,
		 * System.currentTimeMillis())); list.add(new
		 * EventInfo(AVAPIs.AVIOCTRL_EVENT_MOTIONDECT,
		 * System.currentTimeMillis()));
		 * 
		 * adapter.notifyDataSetChanged();
		 * 
		 * }
		 * 
		 * }, 4000);
		 * 
		 * handler.postDelayed(new Runnable() {
		 * 
		 * @Override public void run() {
		 * 
		 * list.add(new EventInfo(AVAPIs.AVIOCTRL_EVENT_MOTIONDECT,
		 * System.currentTimeMillis())); list.add(new
		 * EventInfo(AVAPIs.AVIOCTRL_EVENT_MOTIONDECT,
		 * System.currentTimeMillis()));
		 * 
		 * adapter.notifyDataSetChanged();
		 * 
		 * eventListView.removeFooterView(loadingView);
		 * imgSearch.setEnabled(true);
		 * 
		 * }
		 * 
		 * }, 8000); - test end -
		 */
	}

	public class EventInfo {

		public static final int EVENT_UNREADED = 0;
		public static final int EVENT_READED = 1;
		public static final int EVENT_NORECORD = 2;

		public int EventType;
		public long Time;
		public STimeDay EventTime;
		public int EventStatus;

		private UUID m_uuid = UUID.randomUUID();

		public String getUUID() {
			return m_uuid.toString();
		}

		public EventInfo(int eventType, long time, int eventStatus) {

			EventType = eventType;
			Time = time;
			EventStatus = eventStatus;
		}

		public EventInfo(int eventType, STimeDay eventTime, int eventStatus) {

			EventType = eventType;
			EventTime = eventTime;
			EventStatus = eventStatus;
		}
	}

	public class EventListAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public EventListAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return list.size();
		}

		public Object getItem(int position) {
			return list.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@Override
		public boolean isEnabled(int position) {

			if (list.size() == 0)
				return false;

			return super.isEnabled(position);
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			final EventInfo evt = (EventInfo) getItem(position);

			ViewHolder holder = null;

			if (convertView == null) {

				convertView = mInflater.inflate(R.layout.event_list, null);

				holder = new ViewHolder();
				holder.event = (TextView) convertView.findViewById(R.id.event);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.indicator = (FrameLayout) convertView.findViewById(R.id.eventLayout);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.event.setText(MainActivity.getEventType(EventListActivity.this, evt.EventType, false));
			// holder.time.setText(EventListActivity.getLocalTime(evt.Time,
			// true));
			holder.time.setText(evt.EventTime.getLocalTime());
			if(holder.indicator!=null && mCamera!=null)
			{
				holder.indicator.setVisibility(mCamera.getPlaybackSupported(0) & evt.EventStatus != EventInfo.EVENT_NORECORD ? View.VISIBLE : View.GONE);
			}

			if (evt.EventStatus == EventInfo.EVENT_UNREADED) {
				holder.event.setTypeface(null, Typeface.BOLD);
				holder.event.setTextColor(0xFF000000);
			} else {
				holder.event.setTypeface(null, Typeface.NORMAL);
				holder.event.setTextColor(0xFF999999);
			}

			return convertView;

		}// getView()

		private final class ViewHolder {
			public TextView event;
			public TextView time;
			public FrameLayout indicator;
		}
	}// EventListAdapter

	@Override
	public void receiveIOCtrlData(final Camera camera, int sessionChannel, int avIOCtrlMsgType, byte[] data) {

		if (mCamera == camera) {
			Bundle bundle = new Bundle();
			bundle.putInt("sessionChannel", sessionChannel);
			bundle.putByteArray("data", data);

			Message msg = new Message();
			msg.what = avIOCtrlMsgType;
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}

	@Override
	public void receiveFrameData(final Camera camera, int sessionChannel, Bitmap bmp) {

	}

	@Override
	public void receiveFrameInfo(final Camera camera, int sessionChannel, long bitRate, int frameRate, int onlineNm, int frameCount, int incompleteFrameCount) {

	}

	@Override
	public void receiveSessionInfo(final Camera camera, int resultCode) {

		if (mCamera == camera) {
			Bundle bundle = new Bundle();
			Message msg = handler.obtainMessage();
			msg.what = resultCode;
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}

	@Override
	public void receiveChannelInfo(final Camera camera, int sessionChannel, int resultCode) {

		if (mCamera == camera) {
			Bundle bundle = new Bundle();
			bundle.putInt("sessionChannel", sessionChannel);

			Message msg = handler.obtainMessage();
			msg.what = resultCode;
			msg.setData(bundle);
			handler.sendMessage(msg);
		}

	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			Bundle bundle = msg.getData();
			byte[] data = bundle.getByteArray("data");
			int sessionChannel = bundle.getInt("sessionChannel");

			switch (msg.what) {

			case Camera.CONNECTION_STATE_CONNECTING:
				break;
			case Camera.CONNECTION_STATE_WRONG_PASSWORD:
			case Camera.CONNECTION_STATE_CONNECT_FAILED:
			case Camera.CONNECTION_STATE_DISCONNECTED:
			case Camera.CONNECTION_STATE_UNKNOWN_DEVICE:
			case Camera.CONNECTION_STATE_TIMEOUT:

				if (eventListView.getFooterViewsCount() == 0) {
					list.clear();
					eventListView.addFooterView(offlineView);
					eventListView.setAdapter(adapter);
				}

				break;

			case Camera.CONNECTION_STATE_CONNECTED:

				if (sessionChannel == 0) {
					eventListView.removeFooterView(offlineView);
					adapter.notifyDataSetChanged();
				}

				break;

			case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTEVENT_RESP:

				if (data.length >= 12 && mIsSearchingEvent) {

					// int idx = data[8];
					int end = data[9];
					int cnt = data[10];

					if (cnt > 0) {

						int pos = 12;
						int size = AVIOCTRLDEFs.SAvEvent.getTotalSize();

						for (int i = 0; i < cnt; i++) {

							byte[] t = new byte[8];
							System.arraycopy(data, i * size + pos, t, 0, 8);
							AVIOCTRLDEFs.STimeDay time = new AVIOCTRLDEFs.STimeDay(t);

							byte event = data[i * size + pos + 8];
							byte status = data[i * size + pos + 9];

							//System.out.println(idx + ". Event:" + event + ", Status: " + status + " -> " + time.year + "/" + time.month + "/" + time.day + " "
							//		+ time.hour + ":" + time.minute + ":" + time.second + ") " + Camera.getHex(t, 8));

							// EventInfo evt = new EventInfo((int) event,
							// time.getTimeInMillis(), (int) status);
							// list.add(evt);
							list.add(new EventInfo((int) event, time, (int) status));
						}

						adapter.notifyDataSetChanged();
					}

					if (end == 1) {

						mIsSearchingEvent = false;

						eventListView.removeFooterView(loadingView);
						eventListView.removeFooterView(noResultView);

						if (list.size() == 0)
							Toast.makeText(EventListActivity.this, EventListActivity.this.getText(R.string.tips_search_event_no_result), Toast.LENGTH_SHORT).show();
					}
				}

				break;
			}

			super.handleMessage(msg);
		}
	};

	@Override
	public void receiveFrameDataForMediaCodec(Camera camera, int avChannel,
			byte[] buf, int length) {
		// TODO Auto-generated method stub
		
	}
}
