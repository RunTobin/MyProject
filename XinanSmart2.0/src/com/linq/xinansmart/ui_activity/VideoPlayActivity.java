package com.linq.xinansmart.ui_activity;

import java.util.List;

import com.linq.xinansmart.R;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZConstants.EZPTZAction;
import com.videogo.openapi.EZConstants.EZPTZCommand;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.realplay.RealPlayStatus;
import com.videogo.widget.CustomRect;
import com.videogo.widget.CustomTouchListener;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class VideoPlayActivity extends Activity implements SurfaceHolder.Callback
   					,OnTouchListener{
	EZOpenSDK mEzOpenSDK;
	int mErrorCode;
	
	String camera ,ID;
	EZCameraInfo eZCamerainfo;
	SurfaceView mRealPlaySv;
	EZPlayer mEzPlayer;
	SurfaceHolder mRealPlaySh;
	private LinearLayout  mPtzControlLy;
	private LinearLayout  mRealPlayPageLy;
	private EZDeviceInfo mDeviceInfo = null;
	ImageButton ptzTopBtn = null;
	ImageButton ptzBottomBtn = null;
	ImageButton ptzLeftBtn = null;
	ImageButton ptzRightBtn = null;
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				mEzPlayer.startRealPlay();
				break;
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		camera = getIntent().getExtras().getString("camera");
		setContentView(R.layout.camera_play);
		mRealPlaySv = (SurfaceView) findViewById(R.id.realplay_sv);
		mPtzControlLy = (LinearLayout) findViewById(R.id.ptz_control_ly);
		mRealPlayPageLy = (LinearLayout) findViewById(R.id.layout_realplay_page_ly);
		mRealPlayPtzDirectionIv = (ImageView) findViewById(R.id.realplay_ptz_direction_iv);
		
		ptzTopBtn = (ImageButton) findViewById(R.id.ptz_top_btn);
		ptzBottomBtn = (ImageButton) findViewById(R.id.ptz_bottom_btn);
		ptzLeftBtn = (ImageButton) findViewById(R.id.ptz_left_btn);
		ptzRightBtn = (ImageButton) findViewById(R.id.ptz_right_btn);
		
		mEzOpenSDK=EZOpenSDK.getInstance();
		getlist();
		mRealPlaySv.getHolder().addCallback(this);
//		initview();
				
		ptzTopBtn.setOnTouchListener(this);
		ptzLeftBtn.setOnTouchListener(this);
		ptzRightBtn.setOnTouchListener(this);
		ptzBottomBtn.setOnTouchListener(this);
	}
	
	private int mStatus = RealPlayStatus.STATUS_INIT;
	private void initview(){
		mRealPlayTouchListener = new CustomTouchListener() {
			@Override
			public boolean canZoom(float scale) {
				// TODO Auto-generated method stub
				 if (mStatus == RealPlayStatus.STATUS_PLAY) {
	                    return true;
	                } else {
	                    return false;
	                }
			}		
			@Override
			public boolean canDrag(int direction) {
				// TODO Auto-generated method stub
				 if (mStatus != RealPlayStatus.STATUS_PLAY) {
	                    return false;
	                }
	                if (mEzPlayer != null) {
	                    // 出界判断
	                    if (DRAG_LEFT == direction || DRAG_RIGHT == direction) {
	                        // 左移/右移出界判断
	                        if (mDeviceInfo.isSupportPTZ()) {
	                            return true;
	                        }
	                    } else if (DRAG_UP == direction || DRAG_DOWN == direction) {
	                        // 上移/下移出界判断
	                        if (mDeviceInfo.isSupportPTZ()) {
	                            return true;
	                        }
	                    }
	                }
	                return false;
			}
			
			@Override
			public void onDoubleClick(MotionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onDrag(int direction, float distance, float rate) {
				// TODO Auto-generated method stub
				 if (mEzPlayer != null) {
	                    //Utils.showLog(RealPlayActivity.this, "onDrag rate:" + rate);
	                    startDrag(direction, distance, rate);
	                }
			}

			@Override
			public void onEnd(int arg0) {
				// TODO Auto-generated method stub
				 if (mEzPlayer != null) {
	                    stopDrag(false);
	                }
	                if (mEzPlayer != null && mDeviceInfo.isSupportZoom()) {
	                    stopZoom();
	                }
			}

			@Override
			public void onSingleClick() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onZoom(float scale) {
				// TODO Auto-generated method stub
				if (mEzPlayer != null && mDeviceInfo.isSupportZoom()) {
                    startZoom(scale);
                }
			}

			@Override
			public void onZoomChange(float scale, CustomRect oRect, 
					CustomRect curRect) {
				// TODO Auto-generated method stub
				if (mEzPlayer != null && mDeviceInfo.isSupportZoom()) {
                    //采用云台调焦
                    return;
                }
                if (mStatus == RealPlayStatus.STATUS_PLAY) {
                    if (scale > 1.0f && scale < 1.1f) {
                        scale = 1.1f;
                    }
                }
            }
			 
		 };	 
	}
	
	//获取摄像头列表
	private void getlist() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
					mEzPlayer = mEzOpenSDK.createPlayer(VideoPlayActivity.this, camera);
					if(mEzPlayer == null)
					return;
					mEzPlayer.setHandler(handler);
					mEzPlayer.setSurfaceHold(mRealPlaySh);
					Message msg=new Message();
					msg.what=0;
					handler.sendMessage(msg);
					}
				//Log.e("size", result.size()+"");
		}).start();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (mEzPlayer != null) {
			mEzPlayer.setSurfaceHold(holder);
			    }
			mRealPlaySh = holder;
			Log.e("1111111", "11111111111");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mEzPlayer.stopRealPlay();
		finish();
	}
	
	private ImageView mRealPlayPtzDirectionIv = null;
	private CustomTouchListener mRealPlayTouchListener = null;
    private float mZoomScale = 0;
    
     
	private void startZoom(float scale) {
	        if (mEzPlayer == null) {
	            return;
	        }
	        boolean preZoomIn = mZoomScale > 1.01 ? true : false;
	        boolean zoomIn = scale > 1.01 ? true : false;
	        if (mZoomScale != 0 && preZoomIn != zoomIn) {
	            mZoomScale = 0;
	        }
	        if (scale != 0 && (mZoomScale == 0 || preZoomIn != zoomIn)) {
	            mZoomScale = scale;
	        }
    }
	private void stopZoom() {
	        if (mEzPlayer == null) {
	            return;
	        }
	        if (mZoomScale != 0) {
	            mZoomScale = 0;
	        }
	    }
	public void startDrag(int direction, float distance, float rate) {
	    }

	public void stopDrag(boolean control) {
	    }



	@Override
	public boolean onTouch(View view, MotionEvent motionevent) {
		// TODO Auto-generated method stub
		int ptz_result = -1;
		int action = motionevent.getAction();
		final int speed = EZConstants.PTZ_SPEED_DEFAULT;
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			switch (view.getId()) {
			case R.id.ptz_top_btn:
				mPtzControlLy.setBackgroundResource
				(R.drawable.ptz_up_sel);
				ptz_result = mEzOpenSDK.controlPTZ
				(camera, EZPTZCommand.EZPTZCommandUp,
						EZPTZAction.EZPTZActionSTART, speed);
				break;
			case R.id.ptz_bottom_btn:
				mPtzControlLy.setBackgroundResource
				(R.drawable.ptz_bottom_sel);
				ptz_result = mEzOpenSDK.controlPTZ
				(camera, EZPTZCommand.EZPTZCommandDown,
						EZPTZAction.EZPTZActionSTART, speed);
				break;
			case R.id.ptz_left_btn:	
				mPtzControlLy.setBackgroundResource
				(R.drawable.ptz_left_sel);
				ptz_result = mEzOpenSDK.controlPTZ
				(camera, EZPTZCommand.EZPTZCommandLeft,
						EZPTZAction.EZPTZActionSTART, speed);
				break;
			case R.id.ptz_right_btn:
				mPtzControlLy.setBackgroundResource
				(R.drawable.ptz_right_sel);
				ptz_result = mEzOpenSDK.controlPTZ
				(camera,EZPTZCommand.EZPTZCommandRight,
						EZPTZAction.EZPTZActionSTART, speed);
				break;
			default:
				break;
			}
			break;
		case MotionEvent.ACTION_UP:
			switch (view.getId()) {
			case R.id.ptz_top_btn:
				mPtzControlLy.setBackgroundResource
				(R.drawable.ptz_bg);
				ptz_result = mEzOpenSDK.controlPTZ
				(camera, EZPTZCommand.EZPTZCommandUp,
						EZPTZAction.EZPTZActionSTOP, speed);
				break;
			case R.id.ptz_bottom_btn:
				mPtzControlLy.setBackgroundResource
				(R.drawable.ptz_bg);
				ptz_result = mEzOpenSDK.controlPTZ
				(camera, EZPTZCommand.EZPTZCommandDown,
						EZPTZAction.EZPTZActionSTOP, speed);
				break;
			case R.id.ptz_left_btn:
				mPtzControlLy.setBackgroundResource
				(R.drawable.ptz_bg);
				ptz_result = mEzOpenSDK.controlPTZ
				(camera, EZPTZCommand.EZPTZCommandLeft,
						EZPTZAction.EZPTZActionSTOP, speed);
				break;
			case R.id.ptz_right_btn:
				mPtzControlLy.setBackgroundResource
				(R.drawable.ptz_bg);
				ptz_result = mEzOpenSDK.controlPTZ
				(camera,EZPTZCommand.EZPTZCommandRight,
						EZPTZAction.EZPTZActionSTOP, speed);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return false;
	}				
   					
}
