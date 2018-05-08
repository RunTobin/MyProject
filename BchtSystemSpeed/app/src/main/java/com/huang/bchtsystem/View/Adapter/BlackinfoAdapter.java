package com.huang.bchtsystem.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huang.bchtsystem.DBUtil.BlackInfo;
import com.huang.bchtsystem.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class BlackinfoAdapter extends BaseAdapter {

    private List<BlackInfo> userinfos;
    private Context context;
    private BlackinfoAdapter.ViewHolder viewHolder = null;
    private int selectedPosition = 0; //选中位置

    public BlackinfoAdapter(Context context, List<BlackInfo> _blackinfos){
        this.context = context;
        this.userinfos=_blackinfos;
    }

    public void deletePosition(int position){
       for(int i=0; i<userinfos.size();i++)
       {
           if (position == userinfos.get(i).getUserId()){
               userinfos.remove(i);
           }
       }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return userinfos.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return userinfos.get(position);
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
            viewHolder = new BlackinfoAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_black, null);

            viewHolder.txtNum = (TextView) convertView.findViewById(R.id.item_blackNum);
            viewHolder.txtCarNum = (TextView) convertView.findViewById(R.id.item_blackCarNum);
            viewHolder.txtColor = (TextView) convertView.findViewById(R.id.item_blackColor);
            viewHolder.txtAddress = (TextView) convertView.findViewById(R.id.item_blackAddress);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.item_blackType);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (BlackinfoAdapter.ViewHolder) convertView.getTag();
        }
        if (selectedPosition == position){
            convertView.setBackgroundColor(Color.parseColor("#330066"));
        }else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        viewHolder.txtNum.setText(String.valueOf(position+1));
        viewHolder.txtCarNum.setText(userinfos.get(position).getCarnum());
        viewHolder.txtColor.setText(userinfos.get(position).getColor());
        viewHolder.txtAddress.setText(userinfos.get(position).getAddress());
        viewHolder.txtType.setText(userinfos.get(position).getType());

        return convertView;
    }
    public class ViewHolder{
        TextView txtNum = null;
        TextView txtCarNum = null;
        TextView txtColor = null;
        TextView txtAddress = null;
        TextView txtType = null;
    }

}
