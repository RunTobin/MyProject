package com.linq.xinansmart.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Version;
import com.linq.xinansmart.web.CommManager;
import com.linq.xinansmart.web.JsonTool;
import com.linq.xinansmart.web.JsonUtil.OrderFlag;
import com.tutk.P2PCam264.MainActivity;

/**
 * @author Administrator
 * 
 */
public class UpGradeVersionManager {

	private Context mContext = null;
	private Version version = null;
	private String serviceCode = "0.0";
	private String currentVersionCode = "0";
	private Handler mHandler = null;
	private String mSavePath = null;
	private downLoadApkThread loadApkThread = null;
	private EquipmentManager equipmentManager = EquipmentManager.getInstance();
	private GateWayManager gateWayManager = GateWayManager.getInstance();
	private HttpURLConnection conn = null;
	private InputStream is = null;
	private FileOutputStream fos = null;
	private CommManager commManager = null;
	private boolean runStatus = true;
	private List<Concenter> conList = new ArrayList<Concenter>();

	public UpGradeVersionManager(Context context, Handler mHandler) {
		this.mContext = context;
		this.mHandler = mHandler;
		this.commManager = CommManager.getInstance();
	}

	/**
	 * 判断是否需要更新
	 * 
	 * @return true 表示需要更新
	 */
	public boolean needUpdate() {
		serviceCode = version.getVersionCode();// 获得服务版本号
		currentVersionCode = getCurrentVersionCode(mContext);
		Log.e("nowversion", String.valueOf(currentVersionCode));
		Log.e("serviceversion", String.valueOf(serviceCode));
		if (serviceCode.equals(currentVersionCode)) {
			return false;
		}
		return true;
	}

	/**
	 * @param context
	 *            上下文对象
	 * @return 获得当前版本号
	 */
	private String getCurrentVersionCode(Context context) {
		int versionCode = 0;
		String versionName = "0.0";
		try {
			versionCode = context.getPackageManager().getPackageInfo(
					"com.linq.xinansmart", 0).versionCode;
			versionName = context.getPackageManager().getPackageInfo(
					"com.linq.xinansmart", 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	private Concenter concenter;
	private Boolean isBoolean = false;

	public void Update() {
		if (needUpdate()) {
			showDownLoadDialog();
		} else {
			//Toast.makeText(mContext, "已是最新版本！", Toast.LENGTH_SHORT).show();
			String user = loadUser(mContext);
			queryConcenter();
			if (conList.size() > 0 && user != null) {
				for (Concenter concenter : conList) {
					if (concenter.getUser().equals(user)) {
						this.concenter = concenter;
						isBoolean = true;
						break;
					}
				}
				if (isBoolean == true) {
					new getThread().start();
				} else {
					mHandler.sendEmptyMessage(2);
				}
			} else {
				mHandler.sendEmptyMessage(2);
			}
			
			
			

		}

	}

	private void queryConcenter() {
		ConcenterManager concenterManager = ConcenterManager
				.getInstance(mContext);
		List<Concenter> reusltlist = concenterManager.getconList(); // 查询数据库的控制中心列表
		if (reusltlist != null) {
			conList = reusltlist; // 当数据库中列表不为空的 时候复制给menuList中的conList
			// 更新menuList列表
		}
	}

	private String loadUser(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Config",
				Context.MODE_PRIVATE);
		return sp.getString("LastUser", "").toString();
	}

	class getThread extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				try {
					gateWayManager.getAllNetgate(concenter.getUser(),
							concenter.getPassword());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				mHandler.sendEmptyMessage(5);
				e.printStackTrace();
			}
			equipmentManager.getAllEquipment(concenter.getUser(),
					concenter.getPassword());
			mHandler.sendEmptyMessage(2);

		}
	}

	// 获得最新版本
	public Version getNewVersion() {

		final String order = JsonTool
				.getJsonRequestParams(OrderFlag.versionUpdate);

		// 把字符串转换成对对象 在通过 gson.toJson(object) 获得json串
		Runnable runGetVersion = null;
		try {
			runGetVersion = new Runnable() {

				@Override
				public void run() {
					runStatus = true;
					String response = null;
					try {
						response = commManager.sendDataPost(order,
								"http://www.xinanrf.com/appmainpage/GetAPPFiles.aspx");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}// 返回值是一组json串
					if (response != null) {
						LinkedHashMap<String, String> linknew = new LinkedHashMap<String, String>();
						String[] strs = JsonTool.JsonToObject(response,
								linknew.getClass());
						version = ToVersion(strs);// 将返回值变成Version对象
						runStatus = false;
					} else {
						runStatus = false;
					}
				}
			};
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new Thread(runGetVersion).start();
		while (runStatus) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return version;
	}

	private Version ToVersion(String[] lmap) {
		Version result = new Version();
		for (int i = 0; i < lmap.length; i++) {
			String[] strs = new String[2];
			strs = lmap[i].split(":");
			if (strs[0].equals("VersionCode")) {
				result.setVersionCode(strs[1]);
			} else if (strs[0].equals("UploadDate")) {
				result.setTime(strs[1]);
			} else if (strs[0].equals("FileName")) {
				result.setFileName(strs[1]);
			} else if (strs[0].equals("IsMust")) {
				result.setIsMust(strs[1]);
			} else if (strs[0].equals("SaveURL")) {
				result.setAppUrl(strs[1]);
			} else {
				continue;
			}
		}
		return result;
	}

	/**
	 * 提示对话框 有新版时是否更新
	 */
	private void showDownLoadDialog() {
		AlertDialog alertDialog = new AlertDialog.Builder(mContext)
				.setTitle("是否更新")
				.setPositiveButton("更新", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						loadApkThread = new downLoadApkThread();
						loadApkThread.start();
					}
				}).setNegativeButton("稍后更新", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						mHandler.sendEmptyMessage(2);
					}
				}).create();
		alertDialog.show();
	}

	/**
	 * @author Administrator
	 * 
	 *         下载新版APK线程
	 * 
	 */
	//http://106.122.250.122/ws.cdn.baidupcs.com/file/15dc302d014caceb8a8b09301e48322f?bkt=p3-140015dc302d014caceb8a8b09301e48322fbde4a7d4000000c70600&xcode=17c1f5e33a5b080d1f7bff7ca167f3d92106549be57c4d4b7f42c5f191c19e11&fid=1090742639-250528-770442758337590&time=1464593563&sign=FDTAXGERLBH-DCb740ccc5511e5e8fedcff06b081203-JkofOtEn%2Ff09QCTc62kAS%2Bxi9iI%3D&to=lc&fm=Qin,B,T,t&sta_dx=12&sta_cs=3&sta_ft=avi&sta_ct=5&fm2=Qingdao,B,T,t&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=000034eecd6c0a32f9c77ca4e0b6afd36e7a&sl=72482894&expires=8h&rt=pr&r=542774364&mlogid=3493946037102930355&vuk=1090742639&vbdid=1205335980&fin=21-%E4%BD%BF%E7%94%A8%E5%8F%8D%E5%B0%84%E6%93%8D%E4%BD%9C%E6%99%AE%E9%80%9A%E6%96%B9%E6%B3%95.avi&fn=21-%E4%BD%BF%E7%94%A8%E5%8F%8D%E5%B0%84%E6%93%8D%E4%BD%9C%E6%99%AE%E9%80%9A%E6%96%B9%E6%B3%95.avi&slt=pm&uta=0&rtype=1&iv=0&isw=0&dp-logid=3493946037102930355&dp-callid=0.1.1&wshc_tag=0&wsts_tag=574bec9c&wsid_tag=b7a18c72&wsiphost=ipdbm
	class downLoadApkThread extends Thread {
		@Override
		public void run() {
			super.run();
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {// 判断SD卡是否正常挂载

				String sdpath = Environment.getExternalStorageDirectory() + "/";
				mSavePath = sdpath + "download";
				try {
					URL url = new URL("http://www.xinanrf.com/appmainpage"
							+ version.getAppUrl().replace("\\", "/"));// 新版本下载路径
					// http://www.xinanrf.com/appmainpage/GetAPPFiles.aspx 版本内容
					// version.getAppUrl()
					// http://www.xinanrf.com/upload/201506/20150626082613474.apk
					conn = (HttpURLConnection) url.openConnection();// 建立连接
					conn.connect();
					int length = conn.getContentLength();
					is = conn.getInputStream();
					File file = new File(mSavePath);
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, version.getFileName());
					if (!apkFile.exists()) {
						apkFile.createNewFile();
					}
					fos = new FileOutputStream(apkFile);
					int count = 0;
					byte buf[] = new byte[1024];
					do {
						int numread = is.read(buf);
						Log.e("byte", String.valueOf(numread));
						count += numread;
						int progress = (int) (((float) count / length) * 100);
						Message msg = mHandler.obtainMessage();
						msg.what = 3;
						msg.arg1 = progress;
						mHandler.sendMessage(msg);
						if (numread <= 0) {
							mHandler.sendEmptyMessage(4);
							break;
						}
						fos.write(buf, 0, numread);
					} while (needUpdate());
					fos.close();
					is.close();

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {

				Toast.makeText(mContext, "SD卡异常", Toast.LENGTH_SHORT).show();

			}

		}

	}

	/**
	 * 安装apk
	 * 
	 */
	public void installApk() {
		File apkfile = new File(mSavePath, version.getFileName());
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}

}
