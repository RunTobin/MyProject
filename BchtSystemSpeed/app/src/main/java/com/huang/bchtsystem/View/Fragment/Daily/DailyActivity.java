package com.huang.bchtsystem.View.Fragment.Daily;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hikvision.netsdk.NET_DVR_LOG_V30;
import com.hikvision.netsdk.NET_DVR_TIME;
import com.huang.bchtsystem.DataAccess.DailyFinder;
import com.huang.bchtsystem.Manager.Task;
import com.huang.bchtsystem.Manager.Worker;
import com.huang.bchtsystem.Model.DailyResultData;
import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.View.Adapter.DailyAdapter;
import com.huang.bchtsystem.jna.HCNetSDKByJNA;

import java.sql.Timestamp;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017/8/11.
 */

public class DailyActivity extends Activity implements View.OnClickListener, Task, Handler.Callback{
    private class MSG {
        public final static int MSG_EMPTY_FIELD = 1;
        public final static int MSG_TASK_FINISHED = 2;
        public final static int MSG_FIELD = 3;

    }
    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;

    @InjectView(R.id.daily_waiting)
    LinearLayout daily_waiting;
    @InjectView(R.id.progress)
    ProgressBar progress;
    @InjectView(R.id.daily_ListView)
    ListView daily_ListView;

    @InjectView(R.id.daily_tv)
    TextView daily_tv;
    @InjectView(R.id.daily_first)
    Button daily_first;
    @InjectView(R.id.daily_last)
    Button daily_last;
    @InjectView(R.id.daily_PageUP)
    Button daily_PageUP;
    @InjectView(R.id.daily_Page_Down)
    Button daily_Page_Down;
    @InjectView(R.id.daily_FanH)
    Button daily_FanH;

    Thread worker;
    Worker target;
    private List<DailyResultData> dailyResultList;
    private DailyFinder dailyFinder;
    private int userId;
    private String strStartTime;
    private String strEndTime;
    private DailyAdapter dailyAdapter;

    public int getUserId() {
        return userId = getIntent().getExtras().getInt("m_iLogID");
    }

    public String getStrartTime() {
        return strStartTime = getIntent().getExtras().getString("start");
    }

    public String getEndTime() {
        return strEndTime = getIntent().getExtras().getString("end");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        target = new Worker();
        worker = new Thread(target);
        worker.start();

        setContentView(R.layout.daily_resultactivity);
        ButterKnife.inject(this);
        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("日志结果");
        textview.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        daily_waiting.setVisibility(View.GONE);
        init();
        if (userId > -1) {
            SearchDailyList();
        }
    }

    private void init(){
        daily_first.setOnClickListener(this);
        daily_last.setOnClickListener(this);
        daily_PageUP.setOnClickListener(this);
        daily_Page_Down.setOnClickListener(this);
        daily_FanH.setOnClickListener(this);
        layout_Title.setOnClickListener(this);
        getUserId();
        getStrartTime();
        getEndTime();
        dailyFinder = new DailyFinder();
        dailyFinder.setlUserId(userId);
        dailyAdapter = new DailyAdapter(this, dailyResultList);
    }
    HCNetSDKByJNA.NET_DVR_LOG_V30 log_v30;
    HCNetSDKByJNA.NET_DVR_TIME start,end;
    private List<DailyResultData> dailySearch(){
        start = new HCNetSDKByJNA.NET_DVR_TIME();
        end = new HCNetSDKByJNA.NET_DVR_TIME();
        log_v30 = new  HCNetSDKByJNA.NET_DVR_LOG_V30();
        Timestamp startTime = Timestamp.valueOf(strStartTime);
        Timestamp endTime = Timestamp.valueOf(strEndTime);
        Util.initDVRTime(start, startTime);
        Util.initDVRTime(end, endTime);
        dailyFinder.setLpNetDvrFindPictureParam(start, end,log_v30);
        dailyFinder.getAllLog();
        return dailyFinder.getListDailyData();
    }
    //日志查找结果
    private void SearchDailyList() {
        onTaskBegin();
        target.push(this);
    }
    private boolean handleTaskFinished() {
        daily_waiting.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        daily_ListView.setVisibility(View.VISIBLE);
        if (dailyResultList == null) {
            return true;
        }
        if (dailyResultList.size() == 0) {
            Toast.makeText(this, "没有找到日志", Toast.LENGTH_SHORT).show();
            return true;
        }

        page();
        dailyAdapter.setPictureDataList(dailyResultList, index, Yecount);
        daily_ListView.setAdapter(dailyAdapter);
        dailyAdapter.notifyDataSetChanged();
        daily_tv.setText("共 " + dailyResultList.size() + " 条 " + Yecount + " 页");
        checkButton();

        return true;
    }
    private void sendMessage(int message) {
        Looper looper = Looper.getMainLooper();
        Handler h = new Handler(looper, this);
        Message msg = new Message();
        msg.what = message;
        msg.setTarget(h);
        h.sendMessage(msg);
    }
    private boolean handleEmptyField(String msg) {
        Toast.makeText(DailyActivity.this, msg, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean handleMessage(Message msg) {
        boolean retVal = false;
        switch (msg.what) {
            case DailyActivity.MSG.MSG_EMPTY_FIELD:
                retVal = handleEmptyField((String) msg.obj);
                break;
            case DailyActivity.MSG.MSG_TASK_FINISHED:
                retVal = handleTaskFinished();
                break;
            case DailyActivity.MSG.MSG_FIELD:
                retVal = handleEmptyField((String) msg.obj);
                break;
            default:
                break;
        }
        return retVal;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.daily_first:
                fitstView();
                break;
            case R.id.daily_last:
                lastView();
                break;
            case R.id.daily_PageUP:
                leftView();
                break;
            case R.id.daily_Page_Down:
                rightView();
                break;
            case R.id.daily_FanH:
                finish();
                break;
            case R.id.layout_Title:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onTaskBegin() {
        daily_ListView.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        daily_waiting.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTask() {
        dailyResultList = dailySearch();
    }

    @Override
    public void onTaskFinished() {
        sendMessage(DailyActivity.MSG.MSG_TASK_FINISHED);
    }

    @Override
    public void onError(String error) {

    }

    /**
     * 分页显示
     */
    // 用于显示页号的索引，第一次进入时为第一页
    int index = 1;
    // 用于显示每列5个Item项。
    int VIEW_COUNT = 10;
    //用于显示多少页
    int Yecount = 0;
    //获取页数
    private void page()
    {
        if (dailyResultList != null) {
            int count = dailyResultList.size();
            int Z = count % VIEW_COUNT;
            if (Z == 0) {
                Yecount = count / VIEW_COUNT;
            } else {
                Yecount = (count - Z) / VIEW_COUNT + 1;
            }
        }
    }

    /**
     * 查看按钮是不是可用
     */
    public void checkButton()
    {
        // 索引值小于等于1，表示不能向前翻页了，以经到了第一页了，将向前翻页的按钮设为不可用。
        if (index <=1 &&Yecount > 1) {
            daily_PageUP.setEnabled(false);
            daily_Page_Down.setEnabled(true);
            index = 1;
        }else if (index <= 1 && Yecount<=1){
            daily_PageUP.setEnabled(false);
            daily_Page_Down.setEnabled(false);
        }
        // 值的长度减去前几页的长度，剩下的就是这一页的长度，如果这一页的长度比View_Count小，表示这是最后的一页了，后面在没有了。
        // 将向后翻页的按钮设为不可用。
        else if (dailyResultList.size() - (index - 1) * VIEW_COUNT <= VIEW_COUNT) {
            daily_PageUP.setEnabled(true);
            daily_Page_Down.setEnabled(false);
        }

        // 否则将2个按钮都设为可用的。
        else {
            daily_PageUP.setEnabled(true);
            daily_Page_Down.setEnabled(true);
        }
    }

    /**
     * 点击首页
     */
    private void fitstView()
    {
        index = 1;
        dailyAdapter.setPictureDataList(dailyResultList, index, Yecount);
        dailyAdapter.notifyDataSetChanged();
        checkButton();
    }

    /**
     * 末页
     */
    private void lastView()
    {
        index = Yecount;
        dailyAdapter.setPictureDataList(dailyResultList, index, Yecount);
        dailyAdapter.notifyDataSetChanged();
        checkButton();

    }

    /**
     * 点击左边的Button，表示向前翻页，索引值要减1.
     */
    private void leftView()
    {
        index--;
        String tag = String.valueOf(index);
        Log.d(tag, tag);
        if (index < 1) {
            Toast.makeText(DailyActivity.this, "这是首页", Toast.LENGTH_LONG).show();
        } else {
            dailyAdapter.setPictureDataList(dailyResultList, index, Yecount);
            dailyAdapter.notifyDataSetChanged();
        }
        checkButton();
    }

    /**
     * 点击右边的Button，表示向后翻页，索引值要加1.
     */
    public void rightView()
    {

        index++;
        String tag = String.valueOf(index);
        Log.d(tag, tag);
        if (index > Yecount) {
            Toast.makeText(DailyActivity.this, "这是末页", Toast.LENGTH_LONG).show();
        } else {
            dailyAdapter.setPictureDataList(dailyResultList, index, Yecount);
            dailyAdapter.notifyDataSetChanged();
        }
        checkButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
