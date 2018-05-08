package com.huang.bchtsystem.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huang.bchtsystem.DBUtil.User;
import com.huang.bchtsystem.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class UserAdapter extends BaseAdapter {

    private List<User> useList;
    private Context context;
    private UserAdapter.ViewHolder viewHolder = null;
    private int selectedPosition = 0; //选中位置

    public UserAdapter(Context context, List<User> userlist){
        this.context = context;
        this.useList=userlist;
    }

    public void deletePosition(int position)
    {
        for(int i=0; i<useList.size();i++)
        {
            if (position == useList.get(i).getId()){
                useList.remove(i);
            }
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return useList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return useList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void setSelectedPosition(int position){
        selectedPosition = position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView==null) {
            viewHolder = new UserAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user, null);

            viewHolder.user_num = (TextView) convertView.findViewById(R.id.user_num);
            viewHolder.user_name = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.user_type = (TextView) convertView.findViewById(R.id.user_type);
            viewHolder.user_info = (TextView) convertView.findViewById(R.id.user_info);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (UserAdapter.ViewHolder) convertView.getTag();
        }
        if (selectedPosition == position){
            convertView.setBackgroundColor(Color.parseColor("#330066"));
        }else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        viewHolder.user_num.setText(String.valueOf(position+1));
        viewHolder.user_name.setText(useList.get(position).getName());
        viewHolder.user_type.setText(useList.get(position).getType());
        viewHolder.user_info.setText(useList.get(position).getInfo());


        return convertView;
    }
    public class ViewHolder{
        TextView user_num = null;
        TextView user_name = null;
        TextView user_type = null;
        TextView user_info = null;
    }

}
