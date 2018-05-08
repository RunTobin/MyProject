package com.huang.bchtsystem.View.Fragment.Picture;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huang.bchtsystem.Model.ImageInfoBean;
import com.huang.bchtsystem.Model.picturepreviewData;
import com.huang.bchtsystem.R;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.huang.bchtsystem.R.id.toolbar;

/**
 * Created by admin on 2017/3/20.
 * 图片查看结果单个查看
 */

public class PictureResultFragment extends AppCompatActivity implements View.OnClickListener{
    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;

    @InjectView(R.id.image)
    ImageView imageView ;

    Bitmap bm = null;
    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previewpicturefragment);
        ButterKnife.inject(this);
        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("图片查看");
        textview.setVisibility(View.GONE);
        initData();
        setPhotoViewClickEvent();
        layout_Title.setOnClickListener(this);
    }
    private void initData()
    {
        Object ojc = this.getIntent().getExtras().get("bitmap");
        try {
            if (ojc != null && ojc instanceof ImageInfoBean) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                ImageInfoBean dto = (ImageInfoBean) ojc;
                bm = BitmapFactory.decodeStream(this.openFileInput(dto.getUri()),null,options);
                imageView.setImageBitmap(bm);
                imageView.invalidate();
                mAttacher = new PhotoViewAttacher(imageView);
                mAttacher.update();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    private void setPhotoViewClickEvent() {
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
               // hideOrShowToolBar();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
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
    public void onDestroy() {
        super.onDestroy();
        if (bm != null){
            bm.recycle();
        }
        finish();
    }
}
