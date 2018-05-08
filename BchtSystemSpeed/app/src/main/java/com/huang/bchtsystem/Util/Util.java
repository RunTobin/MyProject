package com.huang.bchtsystem.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.provider.Settings;
import android.text.TextUtils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huang.bchtsystem.MyApplication;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.jna.HCNetSDKByJNA;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017/3/13.
 * 1.判断电话号码正则表达式 2.网络返回显示数据  3.数据加载动画对话框
 */

public class Util {
    //        public static String rootPath = "/storage/udisk/"; //大屏
    public static String rootPath = "/storage/usbhost2/"; //大屏
   // public static String rootPath  = "/storage/external_storage/sda1/"; //  /USB/USB_DISK1/udisk0/ //电阻屏
    //    public static String rootPath = "/mnt/usb_storage/USB_DISK1/udisk0/"; //电容屏新
    private static Handler handler = new Handler(Looper.getMainLooper());
    public static String dev ="/dev/ttyS2";
    public static int DBPositon = 0;
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    public static void startToast(final String words) {
        if (TextUtils.isEmpty(words)) {
            return;
        } else {
            handler.post(() -> Toast.makeText(MyApplication.getAppContext(), words, Toast.LENGTH_SHORT).show());
        }
    }
    /*创建加载对话框*/
    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.progressdialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.load_animation);
        // 使用ImageView显示动画

        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创 建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;
    }

    public static String FormatDouble(int i){
        String s=new DecimalFormat("00").format(i);
        return s;
    }

    public static String FormatFourth(int i){
        String s=new DecimalFormat("0000").format(i);
        return s;
    }
    public static byte[] string2ASCII(String s,int length) {// 字符串转换为ASCII码
        if (s == null || "".equals(s)) {
            return null;
        }
        char[] chars = s.toCharArray();
        byte[] asciiArray = new byte[length];

        for (int i = 0; i < length; i++) {
            if (i < chars.length) {
                asciiArray[i] = char2ASCII(chars[i]);
            } else {
                asciiArray[i] = char2ASCII('\0');
            }
        }
        return asciiArray;
    }

    public static byte char2ASCII(char c){
        return (byte)c;
    }
    public static String toValidString(String s){
        String[] sIP = new String[2];
        sIP = s.split("\0", 2);
        return sIP[0];
    }

    public static  void initDVRTime(HCNetSDKByJNA.NET_DVR_TIME dvrTime, Timestamp time )
    {
        dvrTime.dwYear = time.getYear() + 1900;
        dvrTime.dwMonth = time.getMonth() + 1;
        dvrTime.dwDay = time.getDate();
        dvrTime.dwHour = time.getHours();
        dvrTime.dwMinute = time.getMinutes();
        dvrTime.dwSecond = time.getSeconds();
    }

    public static void initArrayWithString( byte[] b, String s )
    {
        byte[] sob = s.getBytes();
        for(int i = 0; i < sob.length; i ++ )
        {
            b[i] = sob[i];
        }
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    //一体机IP地址获取
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()&&(inetAddress instanceof Inet4Address)) {

                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            // TODO: handle exception
        }
        return null;
    }
    //自定义显示时长
    public static void show_Toast(final Activity activity,final String word,final long time)
    {
        activity.runOnUiThread(new Runnable(){
            public void run(){
                final Toast toast = Toast.makeText(activity,word,Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    public void run(){
                        toast.cancel();
                    }
                },time);
            }
        });
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final  String pPath)
    {
        File dir = new File(pPath);
        if (dir.exists()){
            if (dir.isFile()){
                dir.delete();
            }else {
                deleteDirWihtFile(dir);
            }
        }
    }
    public static void deleteDirWihtFile(File dir)
    {
        if(dir == null || !dir.exists() || !dir.isDirectory()){
            return;
        }
        for (File file : dir.listFiles()){
            if (file.isFile()){
                file.delete();//删除所有文件
            }
            else if (file.isDirectory()){
                deleteDirWihtFile(file);//递归方式删除所有文件
            }
        }
        dir.delete();
    }

    public static boolean getfilePath(String filePath){
        File file = new File(rootPath);
        for (int i =0;i<file.listFiles().length;i++){
            File file2 =file.listFiles()[i];
            if (filePath.equals(file2.getPath())){
                return true;
            }
        }
        return false;
    }

    public static Bitmap scaleBitmap(Bitmap origin, float ratio)
    {
        if (origin == null || ratio == (float) 1.0) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, true);
        return newBM;
    }

    public static long initUsb(List<String> paths)
    {
        long mAvailSize = 0;
        if (paths.size() != 0)
        {
            final StatFs stat = new StatFs(rootPath);
            final long blockSize = stat.getBlockSize();
            final long totalBlocks = stat.getBlockCount();
            final long availableBlocks = stat.getAvailableBlocks();
            long mTotalSize = totalBlocks * blockSize;
            mAvailSize =availableBlocks * blockSize;
        }
        return mAvailSize;
    }

    public static void show_dialog(final Activity activity,String title,String msg)
    {

        // 弹出的对话框
        new AlertDialog.Builder(activity)
					/* 弹出窗口的最上头文字 */
                .setTitle(title)
					/* 设置弹出窗口的图式 */
                .setIcon(android.R.drawable.ic_dialog_info)
					/* 设置弹出窗口的信息 */
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("确定",
                        new DialogInterface.OnClickListener() { /* 设置跳出窗口的返回事件 */
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                                dialoginterface.dismiss();
                            }
                        }).show();
    }

    /**
     *获得系统亮度
     */
    public static int  getSystemBrightness(Context context)
    {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemBrightness;
    }

    public static void setScrennManualMode(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            int mode = Settings.System.getInt(contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
            if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

}