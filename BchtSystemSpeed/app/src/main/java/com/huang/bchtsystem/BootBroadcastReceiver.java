package com.huang.bchtsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.huang.bchtsystem.View.Activity.SplashActivity;
import com.huang.bchtsystem.View.LoginFragment;

/**
 * Created by admin on 2017/6/23.开机自启动
 */

public class BootBroadcastReceiver extends BroadcastReceiver {

    //	static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction().equals(ACTION)) {
//            Intent sayHelloIntent = new Intent(context,SplashActivity.class);
//            sayHelloIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(sayHelloIntent);
//            Log.d("DEBUG", "开机自动服务自动启动...");
//        }
    }
}
