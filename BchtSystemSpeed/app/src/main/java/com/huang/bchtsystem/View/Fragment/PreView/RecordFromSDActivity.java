package com.huang.bchtsystem.View.Fragment.PreView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huang.bchtsystem.R;
import com.huang.bchtsystem.View.Adapter.GridViewAdapter;

import java.io.File;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017/8/2.
 */

public class RecordFromSDActivity extends Activity implements View.OnClickListener{
    @InjectView(R.id.gridView)
    GridView gridView;
    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;

    private int itemPosition = 0;
    private GridViewAdapter gridViewAdapter ;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ab_record);
        ButterKnife.inject(this);
        gridView = (GridView) findViewById(R.id.gridView);
        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("录像");
        textview.setVisibility(View.GONE);
        layout_Title.setOnClickListener(this);
        init();
        gridViewAdapter = new GridViewAdapter(this,imagePathList);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
                for (int i = 0; i<parent.getCount();i++){
                    View v = parent.getChildAt(i);
                    if (position == i){
                        arg1.setBackgroundResource(R.color.blue);
                    }else {
                        v.setBackgroundResource(R.color.grey);
                    }
                }
                new AlertDialog.Builder(RecordFromSDActivity.this).setTitle("选择").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"播放", "删除"}, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                itemPosition = which;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (itemPosition == 0){
                                    Intent intent = new Intent(RecordFromSDActivity.this,RecordVideoActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("videoPath",imagePathList.get(position));
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }else {
                                    deletefile(imagePathList.get(position));
                                    handler = new Handler();
                                    runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            gridViewAdapter =null;
                                            init();
                                            gridViewAdapter = new GridViewAdapter(RecordFromSDActivity.this,imagePathList);
                                            gridView.setAdapter(gridViewAdapter);
                                            gridViewAdapter.notifyDataSetChanged();
                                        }
                                    };
                                    handler.postDelayed(runnable,1000);
                                itemPosition =0;
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

    }


    private  void deletefile(String path)
    {
        if (!TextUtils.isEmpty(path)){
//                    Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                    ContentResolver resolver = this.getContentResolver();
//                    String where = MediaStore.Images.Media.DATA + "=" + imagePath ;
//                    //删除图片
//                    resolver.delete(uri,where,null);
            //发送广播
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File file = new File(path);
            Uri uri1 = Uri.fromFile(file);
            intent.setData(uri1);
            this.sendBroadcast(intent);
            file.delete();
//            init();
//
//            gridView.setAdapter(gridViewAdapter);
        }
    }
    private ArrayList<String> imagePathList = null;
    private void init(){
        imagePathList = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory() + "/Pictures/";
        File file = new File(path);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (checkIsImageFile(f.getPath())){
                imagePathList.add(f.getPath());
            }
        }
        Log.i("index","========"+imagePathList.size());
    }

    //检查扩展名 
    @SuppressLint("DefaultLocale")
    private boolean checkIsImageFile(String fName){
        boolean isImageFile  = false ;
        //获取扩展名
        String FileEnd  = fName.substring(fName.lastIndexOf(".")+1,fName.length()).toLowerCase();
        if (FileEnd .equals("mp4")){
            isImageFile = true ;
        }else {
            isImageFile = false ;
        }
        return  isImageFile ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_Title:
                finish();
                break;
        }
    }

    //    Handler mhandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                {
////                    Toast.makeText(IntelligentFragment.this,"牌识区设置成功",Toast.LENGTH_SHORT).show();
//                }
//                break;
//                default:
//                    break;
//            }
//        };
//    };
    class TaskThread extends Thread {
        public void run() {
            System.out.println("-->做一些耗时的任务");

        };
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
