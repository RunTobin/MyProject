package com.tutk.P2PCam264;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.linq.xinansmart.R;
import com.tutk.IOTC.AVIOCTRLDEFs;
import com.tutk.IOTC.Camera;
import com.tutk.IOTC.IRegisterIOTCListener;

public class EditDeviceActivity extends Activity implements IRegisterIOTCListener {

	private static final int REQUEST_CODE_ADVANCED = 0;

	private Button btnOK;
	private Button btnCancel;
	private Button btnAdvanced;

	private EditText edtUID;
	private EditText edtSecurityCode;
	private EditText edtNickName;

	private MyCamera mCamera = null;
	private DeviceInfo mDevice = null;

	private boolean mIsModifyAdvancedSettingAndNeedReconnect = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getText(R.string.dialog_EditCamera));
		setContentView(R.layout.edit_device);

		Bundle bundle = this.getIntent().getExtras();
		String devUUID = bundle.getString("dev_uuid");
		String devUID = bundle.getString("dev_uid");

		for (DeviceInfo deviceInfo : MainActivity.DeviceList) {

			if (devUUID.equalsIgnoreCase(deviceInfo.UUID) && devUID.equalsIgnoreCase(deviceInfo.UID)) {

				mDevice = deviceInfo;
				break;
			}
		}

		for (MyCamera camera : MainActivity.CameraList) {

			if (devUUID.equalsIgnoreCase(camera.getUUID()) && devUID.equalsIgnoreCase(camera.getUID())) {

				mCamera = camera;
				mCamera.registerIOTCListener(this);
				break;
			}
			
		}

		/* find view */
		edtUID = (EditText) findViewById(R.id.edtUID);
		edtSecurityCode = (EditText) findViewById(R.id.edtSecurityCode);
		edtNickName = (EditText) findViewById(R.id.edtNickName);
		btnOK = (Button) findViewById(R.id.btnOK);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnAdvanced = (Button) findViewById(R.id.btnAdvanced);

		/* set valude */
		btnAdvanced.setEnabled(mCamera != null && mCamera.isChannelConnected(0));
		edtUID.setText(devUID);
		edtUID.setEnabled(false);
		edtSecurityCode.setText(mDevice.View_Password);
		edtNickName.setText(mDevice.NickName);
		
		/* set listener */
		btnOK.setOnClickListener(btnOKOnClickListener);
		btnCancel.setOnClickListener(btnCancelOnClickListener);
		btnAdvanced.setOnClickListener(btnAdvancedOnClickListener);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// quit(false);
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			quit(false);
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_ADVANCED) {

			Bundle extras = data.getExtras();

			switch (resultCode) {
			case RESULT_OK:

				mIsModifyAdvancedSettingAndNeedReconnect = extras.getBoolean("need_reconnect");
				boolean isChangePassword = extras.getBoolean("change_password");
				String newPassword = extras.getString("new_password");

				if (isChangePassword)
					edtSecurityCode.setText(newPassword);

				break;

			case RESULT_CANCELED:
				break;
			}

			if (mCamera != null)
				btnAdvanced.setEnabled(mCamera.isChannelConnected(0));
		}
	}

	private View.OnClickListener btnAdvancedOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			Bundle extras = new Bundle();
			extras.putString("dev_uuid", mDevice.UUID);
			extras.putString("dev_uid", mDevice.UID);

			Intent intent = new Intent();
			intent.setClass(EditDeviceActivity.this, AdvancedSettingActivity.class);
			intent.putExtras(extras);
			startActivityForResult(intent, REQUEST_CODE_ADVANCED);
		}
	};

	private View.OnClickListener btnOKOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			quit(true);
		}
	};

	private View.OnClickListener btnCancelOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			quit(false);
		}
	};

	private void quit(boolean isPressOK) {

		if (!isPressOK && !mIsModifyAdvancedSettingAndNeedReconnect) {
		
			if (mCamera != null) {
				mCamera.unregisterIOTCListener(this);
			}

			Intent intent = new Intent();
			setResult(RESULT_CANCELED, intent);
			finish();
		} else if (!isPressOK && mIsModifyAdvancedSettingAndNeedReconnect) {

			if (mCamera != null) {
				mCamera.setPassword(mDevice.View_Password);
				mCamera.stop(Camera.DEFAULT_AV_CHANNEL);
				mCamera.disconnect();
				mCamera.connect(mDevice.UID);
				mCamera.start(Camera.DEFAULT_AV_CHANNEL, mDevice.View_Account, mDevice.View_Password);
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVINFO_REQ, AVIOCTRLDEFs.SMsgAVIoctrlDeviceInfoReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetSupportStreamReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETAUDIOOUTFORMAT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetAudioOutFormatReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ, AVIOCTRLDEFs.SMsgAVIoctrlTimeZone.parseContent());
				
			}

			Intent intent = new Intent();
			setResult(RESULT_OK, intent);
			finish();
		} else if (isPressOK && mIsModifyAdvancedSettingAndNeedReconnect) {

			String nickname = edtNickName.getText().toString();
			String uid = edtUID.getText().toString();
			String view_acc = mDevice.View_Account;
			String view_pwd = edtSecurityCode.getText().toString();

			if (nickname.length() == 0) {
				MainActivity.showAlert(EditDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_camera_name), getText(R.string.ok));
				return;
			}

			if (uid.length() == 0) {
				MainActivity.showAlert(EditDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_dev_uid), getText(R.string.ok));
				return;
			}

			if (uid.length() != 20) {
				MainActivity.showAlert(EditDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_dev_uid_character), getText(R.string.ok));
				return;
			}

			if (view_pwd.length() <= 0) {
				MainActivity.showAlert(EditDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_dev_security_code), getText(R.string.ok));
				return;
			}

			// To verify whether is something been modified.
			if (!nickname.equalsIgnoreCase(mDevice.NickName) || !uid.equalsIgnoreCase(mDevice.UID) || !view_acc.equalsIgnoreCase(mDevice.View_Account) || !view_pwd.equalsIgnoreCase(mDevice.View_Password)) {

				mDevice.NickName = nickname;
				mDevice.UID = uid;
				mDevice.View_Account = view_acc;
				mDevice.View_Password = view_pwd;

				/* update value to data base */
				DatabaseManager manager = new DatabaseManager(EditDeviceActivity.this);
				manager.updateDeviceInfoByDBID(mDevice.DBID, mDevice.UID, nickname, "", "", view_acc, view_pwd, mDevice.EventNotification, mDevice.ChannelIndex);
			}

			/* reconnect camera */
			if (mCamera != null) {
				mCamera.setPassword(view_pwd);
				mCamera.unregisterIOTCListener(this);
				mCamera.stop(Camera.DEFAULT_AV_CHANNEL);
				mCamera.disconnect();
				mCamera.connect(uid);
				mCamera.start(Camera.DEFAULT_AV_CHANNEL, view_acc, view_pwd);
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVINFO_REQ, AVIOCTRLDEFs.SMsgAVIoctrlDeviceInfoReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetSupportStreamReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETAUDIOOUTFORMAT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetAudioOutFormatReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ, AVIOCTRLDEFs.SMsgAVIoctrlTimeZone.parseContent());
			}

			Toast.makeText(EditDeviceActivity.this, getText(R.string.tips_edit_camera_ok).toString(), Toast.LENGTH_SHORT).show();

			Intent intent = new Intent();
			setResult(RESULT_OK, intent);
			finish();

		} else if (isPressOK && !mIsModifyAdvancedSettingAndNeedReconnect) {

			String nickname = edtNickName.getText().toString();
			String uid = edtUID.getText().toString();
			String view_acc = mDevice.View_Account;
			String view_pwd = edtSecurityCode.getText().toString();

			if (nickname.length() == 0) {
				MainActivity.showAlert(EditDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_camera_name), getText(R.string.ok));
				return;
			}

			if (uid.length() == 0) {
				MainActivity.showAlert(EditDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_dev_uid), getText(R.string.ok));
				return;
			}

			if (uid.length() != 20) {
				MainActivity.showAlert(EditDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_dev_uid_character), getText(R.string.ok));
				return;
			}

			if (view_pwd.length() <= 0) {
				MainActivity.showAlert(EditDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_dev_security_code), getText(R.string.ok));
				return;
			}

			/* reconnect camera */
			if (mCamera != null && (!uid.equalsIgnoreCase(mDevice.UID) || !view_acc.equalsIgnoreCase(mDevice.View_Account) || !view_pwd.equalsIgnoreCase(mDevice.View_Password))) {
				mCamera.setPassword(view_pwd);
				mCamera.unregisterIOTCListener(this);
				mCamera.stop(Camera.DEFAULT_AV_CHANNEL);
				mCamera.disconnect();
				mCamera.connect(uid);
				mCamera.start(Camera.DEFAULT_AV_CHANNEL, view_acc, view_pwd);
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVINFO_REQ, AVIOCTRLDEFs.SMsgAVIoctrlDeviceInfoReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetSupportStreamReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETAUDIOOUTFORMAT_REQ, AVIOCTRLDEFs.SMsgAVIoctrlGetAudioOutFormatReq.parseContent());
				mCamera.sendIOCtrl(Camera.DEFAULT_AV_CHANNEL, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ, AVIOCTRLDEFs.SMsgAVIoctrlTimeZone.parseContent());
				
			}

			/* Save to database */
			if (!nickname.equalsIgnoreCase(mDevice.NickName) || !uid.equalsIgnoreCase(mDevice.UID) || !view_acc.equalsIgnoreCase(mDevice.View_Account) || !view_pwd.equalsIgnoreCase(mDevice.View_Password)) {

				if(!view_pwd.equalsIgnoreCase(mDevice.View_Password))
				{
					mDevice.ChangePassword=true;
				}
				mDevice.NickName = nickname;
				mDevice.UID = uid;
				mDevice.View_Account = view_acc;
				mDevice.View_Password = view_pwd;

				/* update value to data base */
				DatabaseManager manager = new DatabaseManager(EditDeviceActivity.this);
				manager.updateDeviceInfoByDBID(mDevice.DBID, mDevice.UID, nickname, "", "", view_acc, view_pwd, mDevice.EventNotification, mDevice.ChannelIndex);
			}

			Intent intent = new Intent();
			setResult(RESULT_OK, intent);
			finish();
		}
	}

	@Override
	public void receiveFrameData(final Camera camera, int sessionChannel, Bitmap bmp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveFrameInfo(final Camera camera, int sessionChannel, long bitRate, int frameRate, int onlineNm, int frameCount, int incompleteFrameCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveSessionInfo(final Camera camera, int resultCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveChannelInfo(final Camera camera, int sessionChannel, int resultCode) {

		if (mCamera == camera) {

			if (resultCode == Camera.CONNECTION_STATE_CONNECTED) {

//				btnAdvanced.setEnabled(true);
				runOnUiThread(new Runnable(){
					public void run() {
						btnAdvanced.setEnabled(true);
					}});

			}
		}
	}

	@Override
	public void receiveIOCtrlData(final Camera camera, int sessionChannel, int avIOCtrlMsgType, byte[] data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveFrameDataForMediaCodec(Camera camera, int avChannel,
			byte[] buf, int length) {
		// TODO Auto-generated method stub
		
	}
}
