package com.huang.bchtsystem.View.Fragment.PreView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huang.bchtsystem.Model.picturepreviewData;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.MyPreference;
import com.huang.bchtsystem.View.Activity.BridgeActivity;
import com.huang.bchtsystem.View.Adapter.PreviewPictureAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017/5/27.
 */

public class PicturePreviewActivity extends AppCompatActivity implements View.OnClickListener{
    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.button)
    Button button;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;


    @InjectView(R.id.imageView)
    ImageView imageView;

    private PreviewPictureAdapter adapter ;

    private Bitmap bm = null;
    String imagePath = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a);
        ButterKnife.inject(this);
        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        tv_Title.setText("图片查看");
        textview.setVisibility(View.GONE);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        imagePath = bundle.getString("imagePath");
        bm = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(bm);
        layout_Title.setOnClickListener(this);
        button.setOnClickListener(this);
        MyPreference.isBack = true;

    }

    private List<picturepreviewData> imagePathList ;
    private picturepreviewData data ;
//    String filePath = "/storage/emulated/0/"; //  /storage/usbhost/
//    String filePath = "/Internal Memory/Pictures/";
    //从SD卡获取图片资源
    private List<picturepreviewData>getImagePathFromSD(){
        imagePathList = new ArrayList<>();
        String filePath = Environment.getExternalStorageDirectory()+"/";
        File fileAll = new File(filePath);
        File[] files = fileAll.listFiles();
        for(int i =0 ;i<files.length ; i++){
            File  file = files[i];
            if (checkIsImageFile(file.getPath())){
                data =new  picturepreviewData();
                data.fileName = file.getPath();
                imagePathList.add(data);
            }
        }
        return imagePathList ;
    }

    //检查扩展名 
     @SuppressLint("DefaultLocale")
    private boolean checkIsImageFile(String fName){
        boolean isImageFile  = false ;
        //获取扩展名
        String FileEnd  = fName.substring(fName.lastIndexOf(".")+1,fName.length()).toLowerCase();
        if (FileEnd .equals("png") || FileEnd.equals("jpg")){
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
            case R.id.button:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("确定删除吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete_Image();
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            default:
                break;
        }
    }
    private void delete_Image()
    {
        if (!TextUtils.isEmpty(imagePath)){
//                    Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                    ContentResolver resolver = this.getContentResolver();
//                    String where = MediaStore.Images.Media.DATA + "=" + imagePath ;
//                    //删除图片
//                    resolver.delete(uri,where,null);
            //发送广播
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File file = new File(imagePath);
            Uri uri1 = Uri.fromFile(file);
            intent.setData(uri1);
            this.sendBroadcast(intent);
            file.delete();
        }
    }

}
