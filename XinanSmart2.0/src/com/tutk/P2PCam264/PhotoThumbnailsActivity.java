package com.tutk.P2PCam264;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.linq.xinansmart.R;

public class PhotoThumbnailsActivity extends Activity {

	private GridView imageGrid;
	private String mDevUID;
	private List<String> mPhotoSet = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = this.getIntent().getExtras();
		mDevUID = bundle.getString("dev_uid");

		DatabaseManager manager = new DatabaseManager(this);
		SQLiteDatabase db = manager.getReadableDatabase();
		Cursor cursor = db.query(DatabaseManager.TABLE_SNAPSHOT, new String[] { "_id", "dev_uid", "file_path", "time" }, "dev_uid = '" + mDevUID + "'", null,
				null, null, "_id");

		while (cursor.moveToNext()) {

			// String dev_uid = cursor.getString(1);
			String file_path = cursor.getString(2);
			// long time = cursor.getLong(3);

			mPhotoSet.add(file_path);
		}

		cursor.close();
		db.close();

		if (mPhotoSet.size() == 0) {

			setContentView(R.layout.no_photo);
		} else {

			setContentView(R.layout.photo_thumbnails);

			imageGrid = (GridView) findViewById(R.id.PhoneImageGrid);
			imageGrid.setAdapter(new ImageAdapter(getApplicationContext()));
			imageGrid.setOnItemClickListener(gridOnItemClickListener);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:

			quit();

			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	private void quit() {

		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();
	}

	private OnItemClickListener gridOnItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

			String i = mPhotoSet.get(position);
			System.gc();
			Intent intent = new Intent(getApplicationContext(), PhotoViewerActivity.class);
			intent.putExtra("filename", i);
			startActivity(intent);
		}
	};

	private class ImageAdapter extends BaseAdapter {

		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mPhotoSet.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			System.gc();
			ImageView i = new ImageView(mContext.getApplicationContext());
			BitmapFactory.Options bfo = new BitmapFactory.Options();
			bfo.inSampleSize = 2;

			if (convertView == null) {

				try {
					
					Bitmap bm = BitmapFactory.decodeFile(mPhotoSet.get(position), bfo);
					i.setImageBitmap(bm);
					i.setScaleType(ImageView.ScaleType.CENTER_CROP);
					i.setLayoutParams(new GridView.LayoutParams(120, 90));
				} catch (Exception e) {
				}

			} else {
				i = (ImageView) convertView;
			}

			return i;
		}
	}
}
