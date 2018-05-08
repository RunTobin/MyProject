package com.huang.bchtsystem.View.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.huang.bchtsystem.R;
import com.squareup.picasso.Picasso;

/**
 * Created by admin on 2017/3/14.
 */

public class SplashAdapter extends PagerAdapter {
    private Context context;
    private int[] imageViews=new int[]{R.drawable.guide1 ,R.drawable.guide2,R.drawable.guide3};
    public  SplashAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return imageViews.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(lp);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);// android:scaleType="fitXY"
        Picasso.with(context).load(imageViews[position]).resize(400,800).centerCrop().into(imageView);
        container.addView(imageView);
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
