package com.tutk.P2PCam264;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.linq.xinansmart.R;
import com.tutk.zxing.CameraManager;
import com.tutk.zxing.CaptureActivityHandler;
import com.tutk.zxing.InactivityTimer;
import com.tutk.zxing.ViewfinderView;


@SuppressWarnings("deprecation")
public class qr_codeActivity extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private SurfaceHolder surfaceHolder;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qr_code_action);
		CameraManager.init(getApplication());
		inactivityTimer = new InactivityTimer(this);
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		hasSurface = false;
	}

	
    
    //<----------------------------------------------------------Following are zxing originally source code--------------------------------------------------------->//
	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {//Scan again		
			handler = new CaptureActivityHandler(this, decodeFormats,characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) 
	{
    

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	public void handleDecode(Result obj, Bitmap barcode){ //After scan completed
		inactivityTimer.onActivity();
		viewfinderView.drawResultBitmap(barcode);//When scan complete,show capture picture on scanner
		playBeepSoundAndVibrate();//Play beep and vibrate 
		//if(obj.getText().matches(".*?\\|\\|.*?"))//锟斤拷锟斤拷鍝ㄨ暛锟斤拷钑拷浣楄暛鍤欒笣钑殭韪濊暛鍤欒尝锟藉殭璩★拷鍤欒笣钑璻code 鍤欒笣钑拷璩拷闉堬絿钑拷钀囷拷鍤欒笣钑殭韪濊暛锟斤拷钑拷锟借暛     //鍤欒笣钑拷璩拷闉堬絿钑拷浣楄暛鍤欒笣钑殭韪濊暛鍤欒尝锟藉殭璩★拷鍤欒笣钑殭韪濊暛鍤欒笣钑拷锟借暛锟斤拷閲殭韪濊暛鍤欒笣钑殭璩拷鍤欒场锟藉殭韪濊暛鍤欒笣钑殭绶碽er_id
		//{
			Intent intent = new Intent();  
			intent = intent.setClass(qr_codeActivity.this,AddDeviceActivity.class); 
			Bundle bundle =new Bundle();			
			bundle.putString("SCAN_RESULT", obj.getText());  
			//bundle.putString("tabhost", "scan_qrcode");
			intent.putExtras(bundle); 
			qr_codeActivity.this.setResult(RESULT_OK, intent);   		
			qr_codeActivity.this.finish(); 
		//else
		//{	
		//	black_view.setVisibility(View.VISIBLE);//锟斤拷锟斤拷鍝ㄨ暛锟斤拷钑拷缍借暛ew锟斤拷閲拷鎷欙拷闋︼拷锟斤拷锟介儹鍤欒场锟藉殭韪濊暛鍤欒笣钑殭韪濊暛锟藉埢锟藉殭璩拷鍤欙拷		//	QR_CODE_CustomDialog failDialog = new QR_CODE_CustomDialog(widthPixels,heightPixels,qr_codeActivity.this,qr_codeActivity.this,R.style.MyDialog,decodeFormats,characterSet,black_view,"鍤欒笣钑拷鍓栵拷闉堬絿钑拷浣楄暛鍤欒尝锟介牔锟斤拷鍤欒尝锟斤拷鍝ㄨ暛鍤欒唱锟藉殭韪濊暛鍤欒笣钑拷锟借暛锟戒綏钑殭韪濊暛鍤欒笣钑殭韪濊暛"锟斤拷锟藉殭韪濊暛锟藉埢锟藉殭璩★拷锟藉摠钑殭韪濊暛R Code 锟斤拷锟斤拷鍝ㄨ暛锟斤拷钑拷浣楄暛鍤欒尝锟介瀳锟借暛鍤欒尝锟藉殭璩★拷鍤欒唱锟藉殭韪濊暛鍤欒笣钑拷锟借暛锟斤拷閲殭璩拷闋︼拷锟藉殭璩拷鍤欒场锟藉殭璩拷鍤欒笣钑拷璩拷鍤欙拷"锟斤拷锟斤拷鍓栵拷闉堬絿钑拷锟借暛0);
		//	failDialog.show();					
		//	CameraManager.get().stopPreview();//鍤欒笣钑拷璩拷闉堬絿钑拷浣楄暛鍤欒笣钑殭璩★拷锟芥壒锟藉殭璩★拷鍤欒笣钑拷锟斤拷锟藉墫锟介瀳锝囪暛锟戒綏钑殭韪濊暛鍤欒笣钑殭韪濊暛	//}
		
		
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
	
	  @Override
	  public void onBackPressed() 
	  { 
	        //鍤欒笣钑拷鍓栵拷闋︼拷锟斤拷锟介嚟鍤欒尝锟介瀳鈯匡拷鍤欒尝锟斤拷鍓涳拷闉堣繋钑殭韪濊暛锟斤拷ck鍤欒笣钑殭韪濊暛鍤欙拷		    //app_static_variables.set_member_id("");//锟斤拷锟斤拷鍝ㄨ暛锟斤拷berid锟斤拷锟斤拷鍝ㄨ暛锟斤拷钑拷锟借暛        //super.onBackPressed();//锟斤拷閲拷鎶暛鍤欒笣钑拷锟斤拷锟藉摠钑殭璩拷锟斤拷锟斤拷鍓涳拷闉堣繋钑殭韪濊暛鍤欒笣钑拷锟借暛锟斤拷鎮卥鍤欒笣钑殭韪濊暛锟斤拷钑拷浣楄暛鍤欒笣钑拷杩庤暛鍤欒笣钑�		    //Log.w("c2dm", "锟斤拷閲拷鎶暛鍤欒笣钑拷浣楄暛鍤欒笣钑殭韪濊暛鍤欒尝锟斤拷鍝ㄨ暛锟介炒钑殭韪濊暛鍤欒笣钑拷锟借暛锟斤拷钑殭锟�);
			Intent intent = new Intent();  
			intent = intent.setClass(qr_codeActivity.this,AddDeviceActivity.class); 
			//Bundle bundle =new Bundle();
			//鍤欒笣钑拷鍓栵拷闉堬絿钑拷浣楄暛鍤欒笣钑拷锟借暛鍤欒笣钑�
			//bundle.putString("MEMBER_ID",""); 
			//bundle.putString("tabhost", "qrcode_activity");  
			//intent.putExtras(bundle); 
			qr_codeActivity.this.setResult(RESULT_CANCELED, intent);  		
			qr_codeActivity.this.finish();  
		  
	  }

}