package com.tutk.P2PCam264;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.linq.xinansmart.R;
import com.tutk.IOTC.AVIOCTRLDEFs;
import com.tutk.IOTC.AVIOCTRLDEFs.STimeDay;
import com.tutk.IOTC.Camera;
import com.tutk.IOTC.IMonitor;
import com.tutk.IOTC.IRegisterIOTCListener;
import com.tutk.IOTC.Packet;

public class PlaybackActivity extends SherlockActivity implements IRegisterIOTCListener {

	private static final int Build_VERSION_CODES_ICE_CREAM_SANDWICH = 14;
	private static final int STS_CHANGE_CHANNEL_STREAMINFO = 99;

	private final int OPT_MENU_ITEM_PLAY = 0;

	//private TouchedMonitor monitor = null;
	private IMonitor monitor = null;
	private MyCamera mCamera = null;

	private TextView txtEventType;
	private TextView txtEventTime;

	private TextView txtResolution;
	private TextView txtFrameRate;
	private TextView txtBitRate;
	private TextView txtFrameCount;
	private TextView txtIncompleteFrameCount;

	private String mDevUUID;
	private String mDevNickname;
	private String mViewAcc;
	private String mViewPwd;
	private String mEvtUUID;

	private int mCameraChannel;
	private int mEvtType;
	// private long mEvtTime;
	private AVIOCTRLDEFs.STimeDay mEvtTime2;

	private int mVideoWidth;
	private int mVideoHeight;
	
	private final int MEDIA_STATE_STOPPED = 0;
	private final int MEDIA_STATE_PLAYING = 1;
	private final int MEDIA_STATE_PAUSED = 2;
	private final int MEDIA_STATE_OPENING = 3;

	private int mPlaybackChannel = -1;
	private int mMediaState = MEDIA_STATE_STOPPED;

	private BitmapDrawable bg;
	private BitmapDrawable bgSplit;
	
	private boolean FLAG_FINISH = false;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playback_portrait);

		bg = (BitmapDrawable) getResources().getDrawable(R.drawable.bg_striped);
		bgSplit = (BitmapDrawable) getResources().getDrawable(R.drawable.bg_striped_split_img);

		Bundle bundle = this.getIntent().getExtras();
		mDevUUID = bundle != null ? bundle.getString("dev_uuid") : "";
		mDevNickname = bundle != null ? bundle.getString("dev_nickname") : "";
		mCameraChannel = bundle != null ? bundle.getInt("camera_channel") : -1;
		mViewAcc = bundle != null ? bundle.getString("view_acc") : "";
		mViewPwd = bundle != null ? bundle.getString("view_pwd") : "";
		mEvtType = bundle != null ? bundle.getInt("event_type") : -1;
		// mEvtTime = bundle != null ? bundle.getLong("event_time") : -1;
		mEvtUUID = bundle != null ? bundle.getString("event_uuid") : null;
		mEvtTime2 = bundle != null ? new STimeDay(bundle.getByteArray("event_time2")) : null;

		for (MyCamera camera : MainActivity.CameraList) {

			if (mDevUUID.equalsIgnoreCase(camera.getUUID())) {
				mCamera = camera;
				mCamera.registerIOTCListener(this);
				mCamera.resetEventCount();
				break;
			}
		}

		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int rotation = display.getOrientation();

		if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
			setupViewInPortraitLayout();
		} else {
			setupViewInLandscapeLayout();
		}

		if (mCamera != null) {
			mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(mCameraChannel, AVIOCTRLDEFs.AVIOCTRL_RECORD_PLAY_START, 0, mEvtTime2.toByteArray()));
			mMediaState = MEDIA_STATE_OPENING;

			/* if server no response, close playback function */
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (mPlaybackChannel < 0 && mMediaState == MEDIA_STATE_OPENING) {
						mMediaState = MEDIA_STATE_STOPPED;
						Toast.makeText(PlaybackActivity.this, getText(R.string.tips_play_record_timeout), Toast.LENGTH_SHORT).show();
					}
					PlaybackActivity.this.invalidateOptionsMenu();
				}
			}, 5000);
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
	protected void onPause() {
		super.onPause();

		if (mCamera != null) {
			mCamera.stopListening(mPlaybackChannel);
			mCamera.stopShow(mPlaybackChannel);
			mCamera.stop(mPlaybackChannel);
			mCamera.unregisterIOTCListener(this);
			monitor.deattachCamera();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (monitor != null)
			monitor.deattachCamera();

		Configuration cfg = getResources().getConfiguration();

		if (cfg.orientation == Configuration.ORIENTATION_LANDSCAPE) {

			setupViewInLandscapeLayout();

		} else if (cfg.orientation == Configuration.ORIENTATION_PORTRAIT) {

			setupViewInPortraitLayout();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {

//		case KeyEvent.KEYCODE_BACK:
//
//			quit();
//
//			break;
		
		case KeyEvent.KEYCODE_BACK:
			if(FLAG_FINISH)
			{
				return super.onKeyDown(keyCode, event);
			}
			else
			{
				this.stop_exit(true);
				return true ;
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	private synchronized boolean  stop_exit(final boolean exit)
	{ 
		
		final Handler handler=new Handler();
		
	    handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				 if(exit) quit() ;
			}
		}, 600);
	    
		return true ;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		int icon = R.drawable.ic_menu_pause_inverse;
		if (mMediaState == MEDIA_STATE_STOPPED || mMediaState == MEDIA_STATE_PAUSED)
			icon = R.drawable.ic_menu_play;
		else if (mMediaState == MEDIA_STATE_PLAYING)
			icon = R.drawable.ic_menu_pause;

		menu.add(Menu.NONE, OPT_MENU_ITEM_PLAY, 0, "Play").setIcon(icon).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {

		int id = item.getItemId();

		if (id == OPT_MENU_ITEM_PLAY) {

			if (mPlaybackChannel < 0) {

				if (mCamera != null) {
					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(mCameraChannel, AVIOCTRLDEFs.AVIOCTRL_RECORD_PLAY_START, 0, mEvtTime2.toByteArray()));
					mMediaState = MEDIA_STATE_OPENING;

					/* if server no response, close playback function */
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							if (mPlaybackChannel < 0 && mMediaState == MEDIA_STATE_OPENING) {
								mMediaState = MEDIA_STATE_STOPPED;
								Toast.makeText(PlaybackActivity.this, getText(R.string.tips_play_record_timeout), Toast.LENGTH_SHORT).show();
							}
							PlaybackActivity.this.invalidateOptionsMenu();
						}
					}, 5000);
				}
			} else {

				if (mCamera != null) {
					mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(mCameraChannel, AVIOCTRLDEFs.AVIOCTRL_RECORD_PLAY_PAUSE, 0, mEvtTime2.toByteArray()));
				}
			}

			PlaybackActivity.this.invalidateOptionsMenu();
		}

		return super.onOptionsItemSelected(item);
	}

	private void setupViewInLandscapeLayout() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		setContentView(R.layout.playback_landscape);

		if (Build.VERSION.SDK_INT < Build_VERSION_CODES_ICE_CREAM_SANDWICH) {
			bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setBackgroundDrawable(bg);

			bgSplit.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setSplitBackgroundDrawable(bgSplit);
		}

		getSupportActionBar().setSubtitle(null);

		txtEventType = null;
		txtEventTime = null;
		txtResolution = null;
		txtFrameRate = null;
		txtBitRate = null;
		txtFrameCount = null;
		txtIncompleteFrameCount = null;

		// register camera
		if (monitor != null)
			monitor.deattachCamera();
		
		monitor = null;
		monitor = (IMonitor) findViewById(R.id.monitor);
		monitor.setMaxZoom(3.0f);
		monitor.enableDither(mCamera.mEnableDither);
		monitor.attachCamera(mCamera, mPlaybackChannel);

//		if (mPlaybackChannel >= 0) {
//			monitor.enableDither(mCamera.mEnableDither);
//			monitor.attachCamera(mCamera, mPlaybackChannel);
//		}
	}

	private void setupViewInPortraitLayout() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		setContentView(R.layout.playback_portrait);

		if (Build.VERSION.SDK_INT < Build_VERSION_CODES_ICE_CREAM_SANDWICH) {
			bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setBackgroundDrawable(bg);

			bgSplit.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
			getSupportActionBar().setSplitBackgroundDrawable(bgSplit);
		}

		getSupportActionBar().setSubtitle(getText(R.string.dialog_Playback).toString() + " : " + mDevNickname);

		txtEventType = (TextView) findViewById(R.id.txtEventType);
		txtEventTime = (TextView) findViewById(R.id.txtEventTime);
		txtResolution = (TextView) findViewById(R.id.txtResolution);
		txtFrameRate = (TextView) findViewById(R.id.txtFrameRate);
		txtBitRate = (TextView) findViewById(R.id.txtBitRate);
		txtFrameCount = (TextView) findViewById(R.id.txtFrameCount);
		txtIncompleteFrameCount = (TextView) findViewById(R.id.txtIncompleteFrameCount);

		txtEventType.setText(MainActivity.getEventType(PlaybackActivity.this, mEvtType, false));
		txtEventTime.setText(mEvtTime2.getLocalTime());

		if (monitor != null)
			monitor.deattachCamera();
		
		monitor = null;
		monitor = (IMonitor) findViewById(R.id.monitor);
		monitor.setMaxZoom(3.0f);
		monitor.enableDither(mCamera.mEnableDither);
		monitor.attachCamera(mCamera, mPlaybackChannel);

//		if (mPlaybackChannel >= 0) {
//			monitor.enableDither(mCamera.mEnableDither);
//			monitor.attachCamera(mCamera, mPlaybackChannel);
//		}
	}

	private void quit() {

		if (monitor != null)
			monitor.deattachCamera();

		if (mCamera != null) {

			if (mPlaybackChannel >= 0) {

				mCamera.stopListening(mPlaybackChannel);
				mCamera.stopShow(mPlaybackChannel);
				mCamera.stop(mPlaybackChannel);
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(mCameraChannel, AVIOCTRLDEFs.AVIOCTRL_RECORD_PLAY_STOP, 0, mEvtTime2.toByteArray()));
				mPlaybackChannel=-1;
			}
		}

		Bundle extras = new Bundle();
		extras.putInt("event_type", mEvtType);
		// extras.putLong("event_time", mEvtTime);
		extras.putByteArray("event_time2", mEvtTime2.toByteArray());
		extras.putString("event_uuid", mEvtUUID);

		Intent intent = new Intent();
		intent.putExtras(extras);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void receiveFrameData(final Camera camera, int sessionChannel, Bitmap bmp) {

		if (mCamera == camera && sessionChannel == mPlaybackChannel && bmp != null) {
			mVideoWidth = bmp.getWidth();
			mVideoHeight = bmp.getHeight();
		}
	}

	@Override
	public void receiveSessionInfo(final Camera camera, int resultCode) {
	}

	@Override
	public void receiveChannelInfo(final Camera camera, int sessionChannel, int resultCode) {
	}

	@Override
	public void receiveFrameInfo(final Camera camera, int sessionChannel, long bitRate, int frameRate, int onlineNm, int frameCount, int incompleteFrameCount) {

		if (mCamera == camera && sessionChannel == mPlaybackChannel) {
			Bundle bundle = new Bundle();
			bundle.putInt("sessionChannel", sessionChannel);
			bundle.putInt("videoFPS", frameRate);
			bundle.putLong("videoBPS", bitRate);
			bundle.putInt("frameCount", frameCount);
			bundle.putInt("inCompleteFrameCount", incompleteFrameCount);

			Message msg = handler.obtainMessage();
			msg.what = STS_CHANGE_CHANNEL_STREAMINFO;
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}

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

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			Bundle bundle = msg.getData();
			byte[] data = bundle.getByteArray("data");

			if (msg.what == STS_CHANGE_CHANNEL_STREAMINFO) {

				int videoFPS = bundle.getInt("videoFPS");
				long videoBPS = bundle.getLong("videoBPS");
				int frameCount = bundle.getInt("frameCount");
				int inCompleteFrameCount = bundle.getInt("inCompleteFrameCount");

				if (txtResolution != null)
					txtResolution.setText(String.valueOf(mVideoWidth) + "x" + String.valueOf(mVideoHeight));
				if (txtFrameRate != null)
					txtFrameRate.setText(String.valueOf(videoFPS));
				if (txtBitRate != null)
					txtBitRate.setText(String.valueOf(videoBPS) + "Kb");
				if (txtFrameCount != null)
					txtFrameCount.setText(String.valueOf(frameCount));
				if (txtIncompleteFrameCount != null)
					txtIncompleteFrameCount.setText(String.valueOf(inCompleteFrameCount));

			} else if (msg.what == AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL_RESP) {

				int command = Packet.byteArrayToInt_Little(data, 0);
				int result = Packet.byteArrayToInt_Little(data, 4);

				switch (command) {

				case AVIOCTRLDEFs.AVIOCTRL_RECORD_PLAY_START:

					System.out.println("AVIOCTRL_RECORD_PLAY_START");

					if (mMediaState == MEDIA_STATE_OPENING) {
						if (0 <= result && result <= 31) {

							mPlaybackChannel = result;
							mMediaState = MEDIA_STATE_PLAYING;

							if (mCamera != null) {
								mCamera.start(mPlaybackChannel, mViewAcc, mViewPwd);
								mCamera.startShow(mPlaybackChannel,false);
								mCamera.startListening(mPlaybackChannel);
								monitor.enableDither(mCamera.mEnableDither);
								monitor.attachCamera(mCamera, mPlaybackChannel);
							}

							PlaybackActivity.this.invalidateOptionsMenu();

						} else {
							Toast.makeText(PlaybackActivity.this, PlaybackActivity.this.getText(R.string.tips_play_record_failed), Toast.LENGTH_SHORT).show();
						}
					}

					break;

				case AVIOCTRLDEFs.AVIOCTRL_RECORD_PLAY_PAUSE:

					System.out.println("AVIOCTRL_RECORD_PLAY_PAUSE");

					if (mPlaybackChannel >= 0 && mCamera != null) {

						if (mMediaState == MEDIA_STATE_PAUSED)
							mMediaState = MEDIA_STATE_PLAYING;
						else if (mMediaState == MEDIA_STATE_PLAYING)
							mMediaState = MEDIA_STATE_PAUSED;

						if (mMediaState == MEDIA_STATE_PAUSED) {
							monitor.deattachCamera();
						} else {
							monitor.enableDither(mCamera.mEnableDither);
							monitor.attachCamera(mCamera, mPlaybackChannel);
						}

						PlaybackActivity.this.invalidateOptionsMenu();
					}

					break;

				case AVIOCTRLDEFs.AVIOCTRL_RECORD_PLAY_STOP:

					System.out.println("AVIOCTRL_RECORD_PLAY_STOP");

					if (mPlaybackChannel >= 0 && mCamera != null) {
						mCamera.stopListening(mPlaybackChannel);
						mCamera.stopShow(mPlaybackChannel);
						mCamera.stop(mPlaybackChannel);
						monitor.deattachCamera();
					}

					mPlaybackChannel = -1;
					mMediaState = MEDIA_STATE_STOPPED;

					PlaybackActivity.this.invalidateOptionsMenu();

					break;

				case AVIOCTRLDEFs.AVIOCTRL_RECORD_PLAY_END:

					System.out.println("AVIOCTRL_RECORD_PLAY_END");

					if (mPlaybackChannel >= 0 && mCamera != null) {
						mCamera.stopListening(mPlaybackChannel);
						mCamera.stopShow(mPlaybackChannel);
						mCamera.stop(mPlaybackChannel);
						monitor.deattachCamera();

						mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL, AVIOCTRLDEFs.SMsgAVIoctrlPlayRecord.parseContent(mCameraChannel, AVIOCTRLDEFs.AVIOCTRL_RECORD_PLAY_STOP, 0, mEvtTime2.toByteArray()));
					}

					Toast.makeText(PlaybackActivity.this, getText(R.string.tips_play_record_end), Toast.LENGTH_LONG).show();
					FLAG_FINISH = true;

					if (txtFrameRate != null)
						txtFrameRate.setText("0");
					if (txtBitRate != null)
						txtBitRate.setText("0kb");

					mPlaybackChannel = -1;
					mMediaState = MEDIA_STATE_STOPPED;

					PlaybackActivity.this.invalidateOptionsMenu();

					break;

				case AVIOCTRLDEFs.AVIOCTRL_RECORD_PLAY_BACKWARD:

					break;

				case AVIOCTRLDEFs.AVIOCTRL_RECORD_PLAY_FORWARD:

					break;
				}
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