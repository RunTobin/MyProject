package com.huang.bchtsystem.View.Adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huang.bchtsystem.Model.picturepreviewData;
import com.huang.bchtsystem.R;

public class HorizontalScrollViewAdapter
{
	private Context mContext;
	private LayoutInflater mInflater;
	private List<picturepreviewData> mDatas;
	Bitmap bm = null;

	public HorizontalScrollViewAdapter(Context context, List<picturepreviewData> mDatas)
	{
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
	}

	public int getCount()
	{
		return mDatas.size();
	}

	public Object getItem(int position)
	{
		return mDatas.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_index_gallery_item, parent, false);
			viewHolder.mImg = (ImageView) convertView
					.findViewById(R.id.id_index_gallery_item_image);
			viewHolder.mText = (TextView) convertView
					.findViewById(R.id.id_index_gallery_item_text);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String name = mDatas.get(position).fileName ;
		bm = BitmapFactory.decodeFile(name);
		viewHolder.mImg.setImageBitmap(scaleBitmap(bm,0.3f));
		viewHolder.mText.setText(name.substring(name.lastIndexOf("/")).split("/")[1]);
		return convertView;
	}

	private class ViewHolder
	{
		ImageView mImg;
		TextView mText;
	}
	private Bitmap scaleBitmap(Bitmap origin, float ratio)
	{
		if (origin == null || ratio == (float) 1.0) {
			return null;
		}
		int width = origin.getWidth();
		int height = origin.getHeight();
		Matrix matrix = new Matrix();
		matrix.preScale(ratio, ratio);
		Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, true);
		origin.recycle();
		return newBM;
	}
}
