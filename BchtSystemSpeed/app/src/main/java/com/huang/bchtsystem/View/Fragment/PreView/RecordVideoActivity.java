package com.huang.bchtsystem.View.Fragment.PreView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.huang.bchtsystem.Util.MyPreference;

/**
 * Created by admin on 2017/8/2.
 */

public class RecordVideoActivity extends Activity{

    String videoPath = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        videoPath = bundle.getString("videoPath");
//        MyPreference.isRecordBack = true;

        Intent i = new Intent(Intent.ACTION_VIEW);
        String type = "video/mp4";
        Uri  uri =Uri.parse("file://"+videoPath);
        i.setDataAndType(uri,type);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
