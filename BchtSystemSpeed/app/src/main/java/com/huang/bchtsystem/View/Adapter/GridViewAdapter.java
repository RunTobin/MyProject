package com.huang.bchtsystem.View.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huang.bchtsystem.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/8/2.
 */

public class GridViewAdapter extends BaseAdapter {
    private ArrayList<String> mNameList = new ArrayList<String>();
    private LayoutInflater mInflater;
    private Context mContext;
    LinearLayout.LayoutParams params;

    public GridViewAdapter(Context context, ArrayList<String> nameList) {
        mNameList = nameList;
        mContext = context;
        mInflater = LayoutInflater.from(context);

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
    }

    public int getCount() {
        return mNameList.size();
    }

    public Object getItem(int position) {
        return mNameList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewTag viewTag;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.ab_record_item, null);

            // construct an item tag
            viewTag = new ItemViewTag((ImageView) convertView.findViewById(R.id.grid_icon), (TextView) convertView.findViewById(R.id.grid_name));
            convertView.setTag(viewTag);
        } else
        {
            viewTag = (ItemViewTag) convertView.getTag();
        }


        // set name
        String name = mNameList.get(position);
        viewTag.mName.setText(name.substring(name.lastIndexOf("/")).split("/")[1]);
        // set icon
        viewTag.mIcon.setBackgroundResource(R.drawable.stop_record);
        return convertView;
    }

    class ItemViewTag
    {
        protected ImageView mIcon;
        protected TextView mName;

        /**
         * The constructor to construct a navigation view tag
         *
         * @param name
         *            the name view of the item
         * @param size
         *            the size view of the item
         * @param icon
         *            the icon view of the item
         */
        public ItemViewTag(ImageView icon, TextView name)
        {
            this.mName = name;
            this.mIcon = icon;
        }
    }

}
