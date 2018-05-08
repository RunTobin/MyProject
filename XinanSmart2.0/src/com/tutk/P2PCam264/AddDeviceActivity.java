package com.tutk.P2PCam264;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.linq.xinansmart.R;
import com.tutk.IOTC.Camera;
import com.tutk.IOTC.st_LanSearchInfo;


public class AddDeviceActivity extends Activity {

	private final int REQUEST_CODE_GETUID_BY_SCAN_BARCODE = 0;

	private EditText edtUID;
	private EditText edtSecurityCode;
	private EditText edtName;
	private Button btnScan;
	private Button btnSearch;
	private Button btnOK;
	private Button btnCancel;
	private ResultStateReceiver resultStateReceiver;
	private IntentFilter filter;
	private ThreadTPNS thread;

	// private final static String ZXING_DOWNLOAD_URL = "http://zxing.googlecode.com/files/BarcodeScanner3.72.apk";
	// private final static String ZXING_DOWNLOAD_URL = "http://www.tutk.com/IOTCApp/Android/BarcodeScanner.apk";
	private final static String ZXING_DOWNLOAD_URL = "http://push.iotcplatform.com/release/BarcodeScanner.apk";

	private List<SearchResult> list = new ArrayList<SearchResult>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getText(R.string.dialog_AddCamera));
		setContentView(R.layout.add_device);

        filter=new IntentFilter();
        filter.addAction(AddDeviceActivity.class.getName());
        resultStateReceiver=new ResultStateReceiver();
        
        registerReceiver(resultStateReceiver, filter);
		
		edtUID = (EditText) findViewById(R.id.edtUID);
		edtSecurityCode = (EditText) findViewById(R.id.edtSecurityCode);
		edtName = (EditText) findViewById(R.id.edtNickName);

		btnScan = (Button) findViewById(R.id.btnScan);
		btnScan.setOnClickListener(btnScanClickListener);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(btnSearchOnClickListener);
		btnOK = (Button) findViewById(R.id.btnOK);
		btnOK.setOnClickListener(btnOKOnClickListener);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(btnCancelOnClickListener);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (requestCode == REQUEST_CODE_GETUID_BY_SCAN_BARCODE) {

			if (resultCode == RESULT_OK) {

				String contents = intent.getStringExtra("SCAN_RESULT");
				// String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				if ( null == contents){
					Bundle bundle = intent.getExtras();
				    if (null != bundle) {
				      /* String */contents = bundle.getString("result");
				    }
				}
				if( null == contents){
					return;
				}
				    
				if(contents.length() > 20){
					String temp = "";
				
					for(int t = 0;t<contents.length();t++){
						if(contents.substring(t, t+1).matches("[A-Z0-9]{1}"))
						temp += contents.substring(t, t+1);
					}
					contents = temp;
				}
	
					edtUID.setText(contents);
	
					edtSecurityCode.requestFocus();
				
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
			}
		}
	}

	/*
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
	*/
	
	private View.OnClickListener btnScanClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent mintent = new Intent();  
    		mintent = mintent.setClass(AddDeviceActivity.this,qr_codeActivity.class); 
    		AddDeviceActivity.this.startActivityForResult(mintent,REQUEST_CODE_GETUID_BY_SCAN_BARCODE );
		}
	};

	private View.OnClickListener btnSearchOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(AddDeviceActivity.this, R.style.HoloAlertDialog));
			final AlertDialog dlg = builder.create();
			dlg.setTitle(getText(R.string.dialog_LanSearch));
			dlg.setIcon(android.R.drawable.ic_menu_more);

			LayoutInflater inflater = dlg.getLayoutInflater();
			View view = inflater.inflate(R.layout.search_device, null);
			dlg.setView(view);

			ListView lstSearchResult = (ListView) view.findViewById(R.id.lstSearchResult);
			Button btnRefresh = (Button) view.findViewById(R.id.btnRefresh);

			list.clear();
			st_LanSearchInfo[] arrResp = Camera.SearchLAN();

			if (arrResp != null && arrResp.length > 0) {
				for (st_LanSearchInfo resp : arrResp) {

					list.add(new SearchResult(new String(resp.UID).trim(), new String(resp.IP).trim(), (int) resp.port));
				}
			}

			final SearchResultListAdapter adapter = new SearchResultListAdapter(dlg.getLayoutInflater());
			lstSearchResult.setAdapter(adapter);

			lstSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					SearchResult r = list.get(position);
					edtUID.setText(r.UID);
					dlg.dismiss();
				}
			});

			btnRefresh.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					list.clear();
					st_LanSearchInfo[] arrResp = Camera.SearchLAN();

					if (arrResp != null && arrResp.length > 0) {
						for (st_LanSearchInfo resp : arrResp) {

							list.add(new SearchResult(new String(resp.UID).trim(), new String(resp.IP).trim(), (int) resp.port));
						}
					}

					adapter.notifyDataSetChanged();
				}
			});

			dlg.show();
		}
	};

	private View.OnClickListener btnOKOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			String dev_nickname = edtName.getText().toString();
			String dev_uid = edtUID.getText().toString().trim();
			String view_pwd = edtSecurityCode.getText().toString().trim();
			if (dev_nickname.length() == 0) {
				MainActivity.showAlert(AddDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_camera_name), getText(R.string.ok));
				return;
			}

			if (dev_uid.length() == 0) {
				MainActivity.showAlert(AddDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_dev_uid), getText(R.string.ok));
				return;
			}

			if (dev_uid.length() != 20) {
				MainActivity.showAlert(AddDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_dev_uid_character), getText(R.string.ok));
				return;
			}
			
			if (!dev_uid.matches("[a-zA-Z0-9]+")) 
			{
				MainActivity.showAlert(AddDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_dev_uid_special_characters), getText(R.string.ok));
				return;
			}
			
			if (view_pwd.length() == 0) {
				MainActivity.showAlert(AddDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_dev_security_code), getText(R.string.ok));
				return;
			}
			
			thread = new ThreadTPNS(AddDeviceActivity.this,dev_uid,0);
			thread.start();
			
//        	TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//			HttpGetTool HttpGetTool=new HttpGetTool(AddDeviceActivity.this,AddDeviceActivity.class.getName());
//		    HttpGetTool.execute(com.tutk.P2PCam264.DatabaseManager.s_GCM_PHP_URL+"?cmd=reg_mapping&token="+com.tutk.P2PCam264.DatabaseManager.s_GCM_token+"&appid="+com.tutk.P2PCam264.DatabaseManager.s_Package_name+"&uid="+dev_uid+"&imei="+tm.getDeviceId());
			int video_quality = 0;
			int channel = 0;

				
			boolean duplicated = false;
			for (MyCamera camera : MainActivity.CameraList) {

				if (dev_uid.equalsIgnoreCase(camera.getUID())) {
					duplicated = true;
					break;
				}
			}

			if (duplicated) {
				MainActivity.showAlert(AddDeviceActivity.this, getText(R.string.tips_warning), getText(R.string.tips_add_camera_duplicated), getText(R.string.ok));
				return;
			}
				
				
			/* add value to data base */
			DatabaseManager manager = new DatabaseManager(AddDeviceActivity.this);
			long db_id = manager.addDevice(dev_nickname, dev_uid, "", "", "admin", view_pwd, 3, channel);

			Toast.makeText(AddDeviceActivity.this, getText(R.string.tips_add_camera_ok).toString(), Toast.LENGTH_SHORT).show();

			/* return value to main activity */
			Bundle extras = new Bundle();
			extras.putLong("db_id", db_id);
			extras.putString("dev_nickname", dev_nickname);
			extras.putString("dev_uid", dev_uid);
			extras.putString("dev_name", "");
			extras.putString("dev_pwd", "");
			extras.putString("view_acc", "admin");
			extras.putString("view_pwd", view_pwd);
			extras.putInt("video_quality", video_quality);
			extras.putInt("camera_channel", 0);

			Intent Intent = new Intent();
			Intent.putExtras(extras);
			AddDeviceActivity.this.setResult(RESULT_OK, Intent);
			AddDeviceActivity.this.finish();
		}
	};

	private View.OnClickListener btnCancelOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			Intent intent = new Intent();
			setResult(RESULT_CANCELED, intent);
			finish();
		}
	};

	private boolean isSDCardValid() {

		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	private void startInstall(File tempFile) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(tempFile), "application/vnd.android.package-archive");

		startActivity(intent);
	}

	private void startDownload(String url) throws Exception {

		if (URLUtil.isNetworkUrl(url)) {

			URL myURL = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
			// conn.connect();

			InputStream is = conn.getInputStream();

			if (is == null) {
				return;
			}

			BufferedInputStream bis = new BufferedInputStream(is);

			File myTempFile = File.createTempFile("BarcodeScanner", ".apk", Environment.getExternalStorageDirectory());
			FileOutputStream fos = new FileOutputStream(myTempFile);

			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = bis.read(buffer)) > 0) {
				fos.write(buffer, 0, read);
			}

			try {
				fos.flush();
				fos.close();
			} catch (Exception ex) {
				System.out.println("error: " + ex.getMessage());
			}

			startInstall(myTempFile);
		}
	}

	private class SearchResult {

		public String UID;
		public String IP;

		// public int Port;

		public SearchResult(String uid, String ip, int port) {

			UID = uid;
			IP = ip;
			// Port = port;
		}
	}

	private class SearchResultListAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public SearchResultListAdapter(LayoutInflater inflater) {

			this.mInflater = inflater;
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

		public View getView(int position, View convertView, ViewGroup parent) {

			final SearchResult result = (SearchResult) getItem(position);
			ViewHolder holder = null;

			if (convertView == null) {

				convertView = mInflater.inflate(R.layout.search_device_result, null);

				holder = new ViewHolder();
				holder.uid = (TextView) convertView.findViewById(R.id.uid);
				holder.ip = (TextView) convertView.findViewById(R.id.ip);

				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			holder.uid.setText(result.UID);
			holder.ip.setText(result.IP);
			// holder.port.setText(result.Port);

			return convertView;
		}// getView()

		public final class ViewHolder {
			public TextView uid;
			public TextView ip;
		}
	}
	protected void onDestroy () 
	{
	    super.onDestroy(); 	
	    unregisterReceiver(resultStateReceiver);
	}
	
	private class ResultStateReceiver extends BroadcastReceiver 
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			// TODO Auto-generated method stub

		}
	}
}
