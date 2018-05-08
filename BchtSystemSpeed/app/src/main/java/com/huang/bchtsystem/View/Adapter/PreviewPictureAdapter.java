package com.huang.bchtsystem.View.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huang.bchtsystem.Model.picturepreviewData;
import com.huang.bchtsystem.R;

import java.util.List;

/**
 * Created by admin on 2017/5/31.
 */

public class PreviewPictureAdapter extends BaseAdapter {
    private Context context;
    private List<picturepreviewData> pictureList;

    public PreviewPictureAdapter(Context context, List<picturepreviewData> dailyDatasList) {
        this.context = context;
        this.pictureList = dailyDatasList;
    }

    @Override
    public int getCount() {
        return pictureList == null ? 0:pictureList.size();
    }

    @Override
    public picturepreviewData getItem(int position) {
        return pictureList == null ? null : pictureList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PreviewPictureAdapter.ViewHolder viewHolder = null;
        if (convertView ==null){
            viewHolder = new PreviewPictureAdapter.ViewHolder();
            // 获取组件布局
            convertView = LayoutInflater.from(context).inflate(R.layout.item_preview, null);
            viewHolder.textnum = (TextView) convertView
                    .findViewById(R.id.filenum);
            viewHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.imageView);

            viewHolder.textView = (TextView) convertView
                    .findViewById(R.id.tv_scene_name);
            convertView.setTag(viewHolder);
            // 这里要注意，是使用的tag来存储数据的。
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (PreviewPictureAdapter.ViewHolder) convertView.getTag();
        }

//        Bitmap bm = BitmapFactory.decodeFile(pictureList.get(position).fileName);
//        setbitmap(pictureList.get(position).fileName);
//        viewHolder.imageView.setImageBitmap(setbitmap(pictureList.get(position).fileName));
        viewHolder.textnum.setText(position+1+"");
        viewHolder.textView.setText(pictureList.get(position).fileName);
        return convertView;
    }
    private class ViewHolder{
        ImageView imageView = null;
        TextView textView = null;
        TextView textnum = null;
    }
    private Bitmap setbitmap(String imageSdUri){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageSdUri, options);
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inSampleSize = 1;
        int w = 320;
        int h = 480;
        h = w*height/width;//计算出宽高等比率
        int a = options.outWidth/ w;
        int b = options.outHeight / h;
        options.inSampleSize = Math.max(a, b);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(imageSdUri, options);
        return bitmap ;
    }
}
