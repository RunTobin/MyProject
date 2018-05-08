package com.linq.xinansmart.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linq.xinansmart.R;
import com.linq.xinansmart.model.Mode;

public class Grid_Scence_Adapter extends BaseAdapter {

	private Context context;
	private List<Mode> modeList = new ArrayList<Mode>();
	 private TranslateAnimation taLeft, taRight, taTop, taBlow; 
	 
	public void addModeList(List<Mode> modes) {
		modeList.clear();
		modeList.addAll(modes);
	}

	public Grid_Scence_Adapter(Context context) {
		super();
		 taLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,  
				    Animation.RELATIVE_TO_PARENT, 0.0f,  
				    Animation.RELATIVE_TO_PARENT, 0.0f,  
				    Animation.RELATIVE_TO_PARENT, 0.0f); 
		 taRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,  
				    Animation.RELATIVE_TO_PARENT, 0.0f,  
				    Animation.RELATIVE_TO_PARENT, 0.0f,  
				    Animation.RELATIVE_TO_PARENT, 0.0f);  
		 taLeft.setDuration(1000);  
		 taRight.setDuration(1000);
		this.context = context;
	}

	@Override
	public int getCount() {
		return modeList.size();
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
					R.layout.fragment_mainui_item_sce, null);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.img_main_item);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.txt_main_item);
			convertView.setTag(viewHolder);
//			 if (position % 2 == 0) {  
//              
//                 convertView.setAnimation(taLeft);  
//             } else {  
//                
//                 convertView.setAnimation(taRight);  
//             }  
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.imageView.setImageResource(R.drawable.scence);
		viewHolder.textView.setText(modeList.get(position).getModeName());

		return convertView;
	}

	class ViewHolder {
		ImageView imageView;
		TextView textView;

	}
}
