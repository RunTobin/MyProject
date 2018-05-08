package com.linq.xinansmart.ui_activity;

import com.linq.xinansmart.R;
import com.videogo.camera.CameraInfoEx;
import com.videogo.camera.CameraManager;
import com.videogo.constant.IntentConsts;
import com.videogo.device.DeviceInfoEx;
import com.videogo.devicemgt.DeviceInfoCtrl;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.util.ConnectionDetector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class ModifyDeviceNameActivity extends Activity implements OnClickListener{
	private final static int TYPE_DEVICE = 0x01;
	private final static int TYPE_CAMERA = 0x02;
	private final static int TYPE_DETECTOR = 0x04;
    protected static final int MSG_UPDATA_DEVICE_NAME_FAIL = 1001;

    protected static final int MSG_UPDATA_DEVICE_NAME_SUCCESS = 1002;
	
	/** 输入框 */
    private EditText mNameText;
    /** 类型 */
    private TextView mDetectorTypeView;
    /** 清除按钮 */
    private ImageButton mNameDelButton;
    /** 输入提示 */
    private TextView mInputHintView;
    /** 消息对象 */
    private Handler mHandler;
    private int mType;
    private DeviceInfoCtrl mDeviceInfoCtrl;
	private Button mSaveButton = null;
    private EZOpenSDK mEZOpenSDK = null;
    private String mDeviceSerial = null;
    private EZCameraInfo mCameraInfo = null;
    private CameraInfoEx mCamera;
    private DeviceInfoEx mDevice;
   
    private String mDeviceNameString;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_device_name_page);
		findview();
		initData();
		initViews();
	}
	
	
	/**
	 * 控件关联
	 */
	private void findview(){
		mNameText = (EditText)findViewById(R.id.name_text);
		mNameDelButton = (ImageButton)findViewById(R.id.name_del);
		mDetectorTypeView = (TextView)findViewById(R.id.detector_type);
		mInputHintView = (TextView)findViewById(R.id.input_hint);
		mSaveButton = (Button)findViewById(R.id.btn_id_save_name);
	}
	
	/**
	 * 数据初始化
	 */
	private void initData(){
		 mHandler = new MyHandler();
		mDeviceInfoCtrl = DeviceInfoCtrl.getInstance();
		mEZOpenSDK = EZOpenSDK.getInstance();
		   if (getIntent().hasExtra(IntentConsts.EXTRA_CAMERA_ID)) {
	            String cameraId = getIntent().getStringExtra(IntentConsts.EXTRA_CAMERA_ID);
	            mCamera = CameraManager.getInstance().getAddedCameraById(cameraId);

	            if (mCamera == null) {
	                finish();
	                return;
	            }
	            mType = TYPE_CAMERA;

	            mInputHintView.setText("不超过50个字符");
	            mNameText.setFilters(new InputFilter[] {
	                new LengthFilter(50)/*,
	                new IllegalWordFilter(this, "[\\\\/:\\*\\?\"<>\\|'% ]",
	                        getText(R.string.camera_name_contain_illegel_word))*/});

	        } else {
	        	mDeviceSerial = getIntent().getStringExtra("DEVICE_SERIAL");
	        	mCameraInfo = (EZCameraInfo) getIntent().getParcelableExtra("EZCAMERA_INFO");
	            mDevice = null;// DeviceManager.getInstance().getDeviceInfoExById(deviceId);
//	            if (mDevice == null) {
//	                finish();
//	                return;
//	            }

	            mType = TYPE_DEVICE;

	            mInputHintView.setText("不超过50个字符");
	            mNameText.setFilters(new InputFilter[] {
	                new LengthFilter(50)/*,
	                new IllegalWordFilter(this, "[\\\\/:\\*\\?\"<>\\|'% ]",
	                        getText(R.string.camera_name_contain_illegel_word))*/});
	        }
	}

	/**
	 * 設備信息更新顯示
	 */
	private void initViews(){
		String deviceName = getOriginalName(); //mj, get device name, not get camera name

        mNameText.setText(deviceName);
        mNameText.setSelection(mNameText.getText().length());
        mNameText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				 String str = s.toString();
	                setSelectLabel(str);
			}
		});
   
        mNameText.setOnEditorActionListener(new OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_DONE) {
                    modifyDeviceName();
                    return true;
                }
                return false;
			}
        	
        });

        mNameDelButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mNameText.setText(null);
            }
        });

        if (mType == TYPE_DETECTOR) {} else {
//            mCommonNameLayout.setVisibility(View.GONE);
        }
        mSaveButton.setOnClickListener(this);
	}
	
	 private String getOriginalName() {
	    	if(mCameraInfo != null) {
	    		return mCameraInfo.getCameraName();
	    	}
	    	return null;
	    }
	 
	  private void setSelectLabel(String input) {}

	
   /**
    * 保存按钮点击事件
    */
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()) {
		case R.id.btn_id_save_name:
			modifyDeviceName();
			break;
	    }
	}
	/**
	 * 修改设备名
	 */
	 private void modifyDeviceName(){
		 if(TextUtils.isEmpty(mDeviceSerial)) {
	    		return;
	    	}
	        mDeviceNameString = mNameText.getText().toString().trim();

	        if (TextUtils.isEmpty(mDeviceNameString)) {
	           Toast.makeText(this, "不能為空", Toast.LENGTH_LONG).show();
	            return;
	        }

	        // 本地网络检测
	        if (!ConnectionDetector.isNetworkAvailable(ModifyDeviceNameActivity.this)) {
	        	  Toast.makeText(this, "當前網絡不可用，請檢查網絡", Toast.LENGTH_LONG).show();
	            return;
	        }

//	        mWaitDialog.show();

	        new Thread() {
	            @Override
	            public void run() {
	                int errorCode = 0;

	                try {
	                	mEZOpenSDK.setDeviceName(mDeviceSerial, mDeviceNameString);
	                } catch (BaseException e) {
	                	e.printStackTrace();
	                    errorCode = e.getErrorCode();
	                }

	                if (errorCode != 0) {
	                    mHandler.obtainMessage(MSG_UPDATA_DEVICE_NAME_FAIL, errorCode, 0).sendToTarget();
	                } else {
	                    mHandler.obtainMessage(MSG_UPDATA_DEVICE_NAME_SUCCESS).sendToTarget();
	                }
	            }
	        }.start();
	 }
	 
	 class MyHandler extends Handler {
	        @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	                case MSG_UPDATA_DEVICE_NAME_FAIL:
	                    handleUpdateFail(msg.arg1);
	                    break;
	                case MSG_UPDATA_DEVICE_NAME_SUCCESS:
	                    handleUpdateSuccess();
	                    break;
	                default:
	                    break;
	            }
	        }
	    }
	 
	 private void handleUpdateFail(int errorCode) {
//	        mWaitDialog.dismiss();
	        switch (errorCode) {
	            case ErrorCode.ERROR_WEB_DIVICE_NOT_ONLINE:
	               
	                break;
	            case ErrorCode.ERROR_WEB_SESSION_ERROR:
//	                ActivityUtils.handleSessionException(ModifyDeviceNameActivity.this);
	                break;
	            case ErrorCode.ERROR_WEB_HARDWARE_SIGNATURE_ERROR:

	            	break;
	            default:
	                // 修改失败，提示失败的消息
	            	
	                break;
	        }
	    }

	 private void handleUpdateSuccess() {
//	        mWaitDialog.dismiss();
	    	Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
	        Intent intent = new Intent();
	        intent.putExtra(IntentConsts.EXTRA_NAME, mDeviceNameString);
	        setResult(RESULT_OK, intent);
	        finish();
	    }
	    @Override
	 public void finish() {
	    	// TODO Auto-generated method stub
	    	super.finish();
	    }

}
