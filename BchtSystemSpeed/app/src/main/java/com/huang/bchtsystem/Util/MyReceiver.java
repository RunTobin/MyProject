package com.huang.bchtsystem.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by admin on 2017/3/16.
 */

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取当前电量
        int level = intent.getIntExtra("level", 0);
        //电量的总刻度
        myListener.onListener(level+"");
        System.out.println(level + "%");
    }
    //设置监听回调，用于把电量的信息传递给activity
    public MyListener myListener;
    public interface MyListener {
        public void onListener(String level);
    }
    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }
}
