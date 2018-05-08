package com.linq.xinansmart.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linq.xinansmart.R;
import com.linq.xinansmart.model.Location;
import com.linq.xinansmart.model.Mode;
import com.linq.xinansmart.ui_activity.Activity_addLocation_background;
import com.squareup.picasso.Picasso;

public class Grid_Location_Adapter extends BaseAdapter {

	private Context context;
	private LayoutParams params;
	private List<Location> locationlist = new ArrayList<Location>();
	private TranslateAnimation taLeft, taRight, taTop, taBlow;

	public Grid_Location_Adapter(Context context) {
		super();
		this.context = context;
		taTop = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 1f, Animation.RELATIVE_TO_PARENT,
				0.0f);
		taBlow = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		taTop.setDuration(1000);
		taBlow.setDuration(1000);
	}

	public Grid_Location_Adapter(Context context, LayoutParams params) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.params = params;

	}

	public void addLocationList(List<Location> location) {
		locationlist.clear();
		locationlist.addAll(location);
	}

	@Override
	public int getCount() {
		return locationlist.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.fragment_mainui_item_location, null);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.img_main_item);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.txt_main_item);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// viewHolder.imageView.setImageResource(R.id.img_main_item);
		setImage(position, viewHolder.imageView);
		viewHolder.textView.setText(locationlist.get(position).getName());
		// if (position % 2 == 0) {
		//
		// convertView.setAnimation(taTop);
		// } else {
		//
		// convertView.setAnimation(taBlow);
		// }
		return convertView;
	}

	private void setImage(int position, ImageView backgound) {
		// TODO Auto-generated method stub
		if (locationlist.get(position).getBackground().equals("����")) {
			displayImage(backgound, R.drawable.keting);
		} else if (locationlist.get(position).getBackground().equals("����")) {
			displayImage(backgound, R.drawable.woshi);
		} else if (locationlist.get(position).getBackground().equals("����")) {
			displayImage(backgound, R.drawable.chufang);
		} else if (locationlist.get(position).getBackground().equals("����")) {
			displayImage(backgound, R.drawable.cesuo);
		} else if (locationlist.get(position).getBackground().equals("�鷿")) {
			displayImage(backgound, R.drawable.shufang);
		} else if (locationlist.get(position).getBackground().equals("�칫��")) {
			displayImage(backgound, R.drawable.bangongshi);
		}else {
			BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
			bmpFactoryOptions.inJustDecodeBounds = true;
			Bitmap bmp = BitmapFactory.decodeFile(locationlist.get(position).getBackground(), bmpFactoryOptions);
			bmpFactoryOptions.inSampleSize=16;
			// Decode it for real
			bmpFactoryOptions.inJustDecodeBounds = false;
			bmp = BitmapFactory.decodeFile(locationlist.get(position).getBackground(), bmpFactoryOptions);
			int degree = getBitmapDegree(locationlist.get(position).getBackground());
			Bitmap bitmaps = rotateBitmapByDegree(bmp, degree);
			backgound.setImageBitmap(bitmaps);
		}
		
	}
	private int getBitmapDegree(String path) {
		int degree = 0;
		try {
			// ��ָ��·���¶�ȡͼƬ������ȡ��EXIF��Ϣ
			ExifInterface exifInterface = new ExifInterface(path);
			// ��ȡͼƬ����ת��Ϣ
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
		Bitmap returnBm = null;

		// ������ת�Ƕȣ�������ת����
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		try {
			// ��ԭʼͼƬ������ת���������ת�����õ��µ�ͼƬ
			returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
					bm.getHeight(), matrix, true);
		} catch (OutOfMemoryError e) {
		}
		if (returnBm == null) {
			returnBm = bm;
		}
		if (bm != returnBm) {
			bm.recycle();
		}
		return returnBm;
	}
	private void displayImage(ImageView imageView, int drawable) {

		Picasso.with(context).load(drawable).resize(100, 100).into(imageView);
	}

	class ViewHolder {
		ImageView imageView;
		TextView textView;
	}

}
