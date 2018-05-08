package com.huang.bchtsystem.View.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAdapter extends BaseAdapter {
	
	private List<String> list;
	private Context context;
	private Bitmap[] bitmap;
	private static HashMap<Integer, Boolean> isSelected ;


	public List<String> getPathList() {
		return pathList;
	}

	public void setPathList(List<String> pathList) {
		this.pathList = pathList;
	}

	private List<String> pathList = new ArrayList<>();
    String filePath;
	private void init_1() {
		if(list != null){
			isSelected = new HashMap<>();
			for (int i = 0; i < list.size(); i++) {
				isSelected.put(i, false);
			}
		}
	}

	private void delete(String path)
	{
		for (int i =0 ; i<pathList.size();i++){
			if ( path.equals(pathList.get(i)))
			{
				pathList.remove(i);
			}
		}
	}
	private void addData(String  path)
	{
		pathList.add(path);
	}
    public MyAdapter(String filePath,List<String> list, Bitmap[] bitmap , Context context) {
        super();
		this.filePath = filePath;
        this.list = list;
        this.context = context;
        this.bitmap = bitmap;
    }
	public MyAdapter(String filePath,List<String> list, Bitmap[] bitmap,List<String> pathList, Context context) {
		super();
        this.filePath = filePath;
		this.list = list;
		this.context = context;
		this.pathList =pathList;
		this.bitmap = bitmap;
	}

	public void setMyAdapter( List<String> list)
	{
		this.list = list;
		init_1();
		pathList = new ArrayList<>();
	}

	@Override
	public int getCount() {
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_file, null);
			holder = new ViewHolder();
			holder.cb = (CheckBox) convertView.findViewById(R.id.item_CB);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.tv = (TextView) convertView.findViewById(R.id.file_name);
			holder.tv_size = (TextView) convertView.findViewById(R.id.file_size);
			if (Util.getfilePath(filePath)){
				holder.cb.setVisibility(View.VISIBLE);
			}else {
				holder.cb.setVisibility(View.GONE);
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String path = list.get(position);
		if (isSelected != null){
			try{
				holder.cb.setChecked(isSelected.get(position));
				if (isSelected.get(position))
				{
					if ( pathList.size()==0)
					{
						addData(list.get(position));
					}
				}
			}catch (NullPointerException e){
				e.fillInStackTrace();
			}
		}
		File file = new File(path);
		if (file.isDirectory()) {  // 判断其是否为一个文件夹
			holder.image.setImageBitmap(bitmap[0]);
			holder.tv.setText(file.getName());
			holder.tv_size.setText("");
		} else {
			holder.image.setImageBitmap(bitmap[1]);
			holder.tv.setText(file.getName());
			String size = Formatter.formatFileSize(context, file.length());
			holder.tv_size.setText(size);
		}
		holder.cb.setOnClickListener(new OnClickCheckListener(position,this));
		return convertView;
	}

	private class OnClickCheckListener implements View.OnClickListener
	{
		private int p;

		public OnClickCheckListener(int num, MyAdapter adapter)
		{
			this.p = num;
		}
		@Override
		public void onClick(View v) {
			Integer tag  = (Integer) v.getTag();
			if(isSelected.get(p)){//先判断isSelected中是否已经选中
				//选中就置为false，即不选中
				isSelected.put(p, false);
				delete(list.get(p));
			}else{
				//选中
				isSelected.put(p, true);
				addData(list.get(p));
			}
//            notifyDataSetChanged();
		}
	}
	
	class ViewHolder{
		CheckBox cb;
		ImageView image;
		TextView tv,tv_size;
	}
}