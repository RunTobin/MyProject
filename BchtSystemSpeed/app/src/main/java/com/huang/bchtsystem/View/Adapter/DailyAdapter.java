package com.huang.bchtsystem.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huang.bchtsystem.Model.DailyResultData;
import com.huang.bchtsystem.R;

import java.util.List;

public class DailyAdapter extends BaseAdapter{

    private Context context;
    private List<DailyResultData> dailyResultDataList;
    private  int index ;
    int VIEW_COUNT = 10;
    int Yecount = 0 ;


    public DailyAdapter(Context context, List<DailyResultData> dailyDataList ) {
        this.context = context;
        this.dailyResultDataList = dailyDataList;
    }
    public DailyAdapter(Context context) {
        this.context = context;
    }

    public void setPictureDataList(List<DailyResultData> dailyDataList , int index , int Yecount)
    {
        this.dailyResultDataList = dailyDataList;
        this.index = index;
        this.Yecount = Yecount;
    }
    @Override
    public int getCount() {
        int position = 0;
        if (index < Yecount)
        {
            position= VIEW_COUNT;
        }else if (index == Yecount){
            if (dailyResultDataList.size()% VIEW_COUNT == 0)
                position= VIEW_COUNT;
            else
                position = dailyResultDataList.size()% VIEW_COUNT;
        }
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;//pictureDataList == null ? null : pictureDataList.get(position)
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    DailyAdapter.ViewHolder viewHolder = null;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView ==null){
            viewHolder = new DailyAdapter.ViewHolder();
            // 获取组件布局
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dailyresult, null);
            viewHolder.item_dailyNum = (TextView) convertView.findViewById(R.id.item_dailyNum);
            viewHolder.item_dailyTime = (TextView) convertView.findViewById(R.id.item_dailyTime);
            viewHolder.item_dailyTypeMain = (TextView) convertView.findViewById(R.id.item_dailyTypeMain);
            viewHolder.item_dailyTypeSecond = (TextView)convertView.findViewById(R.id.item_dailyTypeSecond);
            viewHolder.item_dailyUser = (TextView)convertView.findViewById(R.id.item_dailyUser);
            viewHolder.item_dailyIpAddress = (TextView)convertView.findViewById(R.id.item_dailyIpAddress);
            // 这里要注意，是使用的tag来存储数据的。
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (DailyAdapter.ViewHolder) convertView.getTag();
        }
        if (position+(index-1)*VIEW_COUNT <= dailyResultDataList.size())
        viewHolder.item_dailyNum.setText(dailyResultDataList.get(position+(index-1)*VIEW_COUNT).num);
        viewHolder.item_dailyTime.setText(dailyResultDataList.get(position+(index-1)*VIEW_COUNT).strLogTime);
        viewHolder.item_dailyTypeMain.setText(dailyResultDataList.get(position+(index-1)*VIEW_COUNT).dwMajorType);
        viewHolder.item_dailyTypeSecond.setText(dailyResultDataList.get(position+(index-1)*VIEW_COUNT).dwMinorType);

        viewHolder.item_dailyUser.setText(dailyResultDataList.get(position+(index-1)*VIEW_COUNT).sNetUser);
        viewHolder.item_dailyIpAddress.setText(dailyResultDataList.get(position+(index-1)*VIEW_COUNT).struRemoteHostAddr);
        return convertView;
    }

    public class ViewHolder{
        TextView item_dailyNum = null;
        TextView item_dailyTime = null;
        TextView item_dailyTypeMain = null;
        TextView item_dailyTypeSecond = null;
        TextView item_dailyUser = null;
        TextView item_dailyIpAddress = null;
    }
}
