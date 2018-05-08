package com.huang.bchtsystem.View.Fragment.PreView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.MyPreference;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017/7/10.
 * 预览抓拍图片查看
 */

public class PictureFromSDActivity extends Activity implements View.OnClickListener{

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;

    @InjectView(R.id.image)
    ImageView imageView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previewpicturefragment);
        ButterKnife.inject(this);
        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("图片查看");
        textview.setVisibility(View.GONE);
        layout_Title.setOnClickListener(this);
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyPreference.isBack){
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i,0);
            MyPreference.isBack = false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!= null){
            if (requestCode == 0 && resultCode == Activity.RESULT_OK){
                Uri selectedImage = data.getData();
                String [] filePathCoulum = {MediaStore.Audio.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,filePathCoulum,null,null,null);
                cursor.moveToFirst();
                int coulumIndex = cursor.getColumnIndex(filePathCoulum[0]);
                String picPath = cursor.getString(coulumIndex);
                cursor.close();
                if (picPath.equals("")) return;
                Intent intent = new Intent(this,PicturePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("imagePath",picPath);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }else {
            finish();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_Title:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
