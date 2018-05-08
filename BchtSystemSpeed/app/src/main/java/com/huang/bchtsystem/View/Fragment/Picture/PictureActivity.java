package com.huang.bchtsystem.View.Fragment.Picture;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huang.bchtsystem.R;
import com.huang.bchtsystem.View.MyView.StrericWheelAdapter;
import com.huang.bchtsystem.View.MyView.WheelView;

import java.sql.Timestamp;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2017/3/22.
 * 图片查询界面
 */

public class PictureActivity  extends FragmentActivity implements View.OnClickListener{
    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;
    @InjectView(R.id.textview)
    protected TextView textview;
    @InjectView(R.id.tv_Title)
    TextView tv_Title;
    @InjectView(R.id.layout_Title)
    LinearLayout layout_Title;
    @InjectView(R.id.layout_picture)
    LinearLayout layout_picture;

    /**
     * 按照时间查询
     */
    @InjectView(R.id.layout_PictureTime)
    protected LinearLayout layout_PictureTime;
    @InjectView(R.id.picture_TimeSure)
    protected Button picture_TimeSure;
    @InjectView(R.id.picture_TimeCancel)
    protected Button picture_TimeCancel;
    //快捷查询
    @InjectView(R.id.picture_OneDay)
    protected Button picture_OneDay;
    @InjectView(R.id.picture_TwoDay)
    protected Button picture_TwoDay;
    @InjectView(R.id.picture_ThreeDay)
    protected Button picture_ThreeDay;
    @InjectView(R.id.picture_OneWeek)
    protected Button picture_OneWeek;
    @InjectView(R.id.picture_Define)
    protected Button picture_Define;
    //自定义查询
    @InjectView(R.id.EditText_Start)
    protected EditText EditText_Start;
    @InjectView(R.id.startTimeCheck)
    protected ImageButton startTimeCheck;
    @InjectView(R.id.EditText_End)
    protected EditText EditText_End;
    @InjectView(R.id.endTimeCheck)
    protected ImageButton endTimeCheck;

    private int minYear = 1970;  //最小年份
    private int fontSize = 13; 	 //字体大小
    private WheelView yearWheel,monthWheel,dayWheel,hourWheel,minuteWheel,secondWheel;
    public static String[] yearContent=null;
    public static String[] monthContent=null;
    public static String[] dayContent=null;
    public static String[] hourContent = null;
    public static String[] minuteContent=null;
    public static String[] secondContent=null;
    private InputMethodManager mInputMethodManager; //输入法管理器
    private int userId;
    private int channel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_activity);
        ButterKnife.inject(this);

        toolbar.setTitle("");
        tv_Title.setVisibility(View.VISIBLE);
        tv_Title.setText("图片查询");
        textview.setVisibility(View.GONE);

        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//初始化输入法
        initContent();
        init();

    }

    public int getUserId( )
    {
        return userId = getIntent().getExtras().getInt("m_iLogID");
    }

    public int getChannel( )
    {
        return channel = getIntent().getExtras().getInt("m_iChanNum");
    }
    private void init()
    {
        getChannel();
        getUserId();
        layout_Title.setOnClickListener(this);
        layout_picture.setOnClickListener(this);
        //时间查询
        picture_OneDay.setOnClickListener(this);
        picture_TwoDay.setOnClickListener(this);
        picture_ThreeDay.setOnClickListener(this);
        picture_OneWeek.setOnClickListener(this);
        picture_Define.setOnClickListener(this);
        picture_TimeCancel.setOnClickListener(this);
        picture_TimeSure.setOnClickListener(this);
        picture_OneDay.performClick();

    }
    //年月日、时分秒数据初始化
    public void initContent()
    {
        yearContent = new String[94];
        for(int i=0;i<94;i++)
            yearContent[i] = String.valueOf(i+1970);

        monthContent = new String[12];
        for(int i=0;i<12;i++)
        {
            monthContent[i]= String.valueOf(i+1);
            if(monthContent[i].length()<2)
            {
                monthContent[i] = "0"+monthContent[i];
            }
        }

        dayContent = new String[31];
        for(int i=0;i<31;i++)
        {
            dayContent[i]=String.valueOf(i+1);
            if(dayContent[i].length()<2)
            {
                dayContent[i] = "0"+dayContent[i];
            }
        }
        hourContent = new String[24];
        for(int i=0;i<24;i++)
        {
            hourContent[i]= String.valueOf(i);
            if(hourContent[i].length()<2)
            {
                hourContent[i] = "0"+hourContent[i];
            }
        }

        minuteContent = new String[60];
        for(int i=0;i<60;i++)
        {
            minuteContent[i]=String.valueOf(i);
            if(minuteContent[i].length()<2)
            {
                minuteContent[i] = "0"+minuteContent[i];
            }
        }
        secondContent = new String[60];
        for(int i=0;i<60;i++)
        {
            secondContent[i]=String.valueOf(i);
            if(secondContent[i].length()<2)
            {
                secondContent[i] = "0"+secondContent[i];
            }
        }
    }
    //设置时间
    Calendar calendar = Calendar.getInstance();
    int curYear = calendar.get(Calendar.YEAR);
    int curMonth= calendar.get(Calendar.MONTH)+1;
    int curDay = calendar.get(Calendar.DAY_OF_MONTH);
    int curHour = calendar.get(Calendar.HOUR_OF_DAY);
    int curMinute = calendar.get(Calendar.MINUTE);
    int curSecond = calendar.get(Calendar.SECOND);
    public void EditTextClick(EditText editText)
    {
        View view = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.time_picker, null);
        yearWheel = (WheelView)view.findViewById(R.id.yearwheel);
        monthWheel = (WheelView)view.findViewById(R.id.monthwheel);
        dayWheel = (WheelView)view.findViewById(R.id.daywheel);
        hourWheel = (WheelView)view.findViewById(R.id.hourwheel);
        minuteWheel = (WheelView)view.findViewById(R.id.minutewheel);
        secondWheel = (WheelView)view.findViewById(R.id.secondwheel);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        yearWheel.setAdapter(new StrericWheelAdapter(yearContent));
        yearWheel.setCurrentItem(curYear-1970);
        yearWheel.setCyclic(true);
        yearWheel.setInterpolator(new AnticipateOvershootInterpolator());

        monthWheel.setAdapter(new StrericWheelAdapter(monthContent));
        monthWheel.setCurrentItem(curMonth-1);
        monthWheel.setCyclic(true);
        monthWheel.setInterpolator(new AnticipateOvershootInterpolator());

        dayWheel.setAdapter(new StrericWheelAdapter(dayContent));
        dayWheel.setCurrentItem(curDay-1);
        dayWheel.setCyclic(true);
        dayWheel.setInterpolator(new AnticipateOvershootInterpolator());

        hourWheel.setAdapter(new StrericWheelAdapter(hourContent));
        hourWheel.setCurrentItem(curHour);
        hourWheel.setCyclic(true);
        hourWheel.setInterpolator(new AnticipateOvershootInterpolator());

        minuteWheel.setAdapter(new StrericWheelAdapter(minuteContent));
        minuteWheel.setCurrentItem(curMinute);
        minuteWheel.setCyclic(true);
        minuteWheel.setInterpolator(new AnticipateOvershootInterpolator());

        secondWheel.setAdapter(new StrericWheelAdapter(secondContent));
        secondWheel.setCurrentItem(curSecond);
        secondWheel.setCyclic(true);
        secondWheel.setInterpolator(new AnticipateOvershootInterpolator());

        builder.setTitle("选取时间");
        builder.setCancelable(false);
        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuffer sb = new StringBuffer();
                sb.append(yearWheel.getCurrentItemValue()).append("-")
                        .append(monthWheel.getCurrentItemValue()).append("-")
                        .append(dayWheel.getCurrentItemValue());
                sb.append(" ");
                sb.append(hourWheel.getCurrentItemValue())
                        .append(":").append(minuteWheel.getCurrentItemValue())
                        .append(":").append(secondWheel.getCurrentItemValue());
                editText.setText(sb);
                editText.setTextSize(20);
                dialog.cancel();
            }
        });
        builder.show();
    }

    String start, end,cMonth,cDay,cHour,cMinute,cSecond,value,valueMonth;
    private void timecheck()
    {
        cMonth = String.valueOf(curMonth);
        cDay = String.valueOf(curDay);
        cHour = String.valueOf(curHour);
        cMinute = String.valueOf(curMinute);
        cSecond = String.valueOf(curSecond);
        if (curMonth < 10)
        {
            cMonth = "0" +curMonth;
        }
        if (curDay < 10)
        {
            cDay = "0" +curDay;
        }
        if (curHour < 10)
        {
            cHour = "0" +curHour;
        }
        if (curMinute < 10)
        {
            cMinute = "0" +curMinute;
        }
        if (curSecond < 10)
        {
            cSecond = "0" +curSecond;
        }
    }
    private void DateTime()
    {
        start = curYear+"-"+valueMonth+"-"+value+" "+cHour+":"+cMinute+":"+cSecond;
        EditText_Start.setTextSize(20);
        EditText_Start.setText(start);
        end = curYear+"-"+cMonth+"-"+cDay+" "+cHour+":"+cMinute+":"+cSecond;
        EditText_End.setTextSize(20);
        EditText_End.setText(end);
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.layout_Title:
                finish();
                break;
            case R.id.layout_picture:
                InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
            case R.id.picture_OneDay:
                startTimeCheck.setEnabled(false);
                endTimeCheck.setEnabled(false);
                timecheck();
                value =  String.valueOf((curDay-1));
                if (curDay-1 < 10  && curDay-1>0)
                {
                    value = "0" + (curDay-1) ;
                    valueMonth = cMonth;
                }else if (curDay-1 <= 0){
                    value = String.valueOf(Integer.parseInt(value)+31) ;
                    if (curMonth<10){
                        valueMonth = "0" +(curMonth-1);
                    }else {
                        valueMonth = String.valueOf(curMonth-1);
                }
                }else if (curDay-1 >= 10){
                    value = String.valueOf(Integer.parseInt(value)) ;
                    if (curMonth<10){
                        valueMonth = "0" +(curMonth);
                    }else {
                        valueMonth = String.valueOf(curMonth);
                    }
                }
                DateTime();
                break;
            case R.id.picture_TwoDay:
                startTimeCheck.setEnabled(false);
                endTimeCheck.setEnabled(false);
                timecheck();
                value =  String.valueOf((curDay-2));
                if (curDay-2 < 10 && curDay-2 > 0)
                {
                    value = "0" + (curDay-2) ;
                    valueMonth = cMonth;
                }else if (curDay-2 <= 0){
                    value = String.valueOf(Integer.parseInt(value)+31) ;
                    if (curMonth<10){
                        valueMonth = "0" +(curMonth-1);
                    }else {
                        valueMonth = String.valueOf(curMonth-1);
                    }
                }else if (curDay-2 >= 10){
                    value = String.valueOf(Integer.parseInt(value)) ;
                    if (curMonth<10){
                        valueMonth = "0" +(curMonth);
                    }else {
                        valueMonth = String.valueOf(curMonth);
                    }
                }
                DateTime();
                break;
            case R.id.picture_ThreeDay:
                startTimeCheck.setEnabled(false);
                endTimeCheck.setEnabled(false);
                timecheck();
                value =  String.valueOf((curDay-3));
                if (curDay-3 < 10 && curDay-3 > 0)
                {
                    value = "0" + (curDay-3) ;
                    valueMonth = cMonth;
                }else if (curDay-3 <= 0){
                    value = String.valueOf(Integer.parseInt(value)+31) ;
                    if (curMonth<10){
                        valueMonth = "0" +(curMonth-1);
                    }else {
                        valueMonth = String.valueOf(curMonth-1);
                    }
                }else if (curDay-3 >= 10){
                value = String.valueOf(Integer.parseInt(value)) ;
                if (curMonth<10){
                    valueMonth = "0" +(curMonth);
                }else {
                    valueMonth = String.valueOf(curMonth);
                }
            }
                DateTime();
                break;
            case R.id.picture_OneWeek:
                startTimeCheck.setEnabled(false);
                endTimeCheck.setEnabled(false);
                timecheck();
                value =  String.valueOf((curDay-7));
                if (curDay-7 < 10 && curDay-7> 0)
                {
                    value = "0" + (curDay-7) ;
                    valueMonth = cMonth;
                }else if (curDay-7 <= 0){
                    value = String.valueOf(Integer.parseInt(value)+31) ;
                    if (curMonth<10){
                        valueMonth = "0" +(curMonth-1);
                    }else {
                        valueMonth = String.valueOf(curMonth-1);
                    }
                }else if (curDay-7 >= 10){
                    value = String.valueOf(Integer.parseInt(value)) ;
                    if (curMonth<10){
                        valueMonth = "0" +(curMonth);
                    }else {
                        valueMonth = String.valueOf(curMonth);
                    }
                }
                DateTime();
                break;
            case R.id.picture_Define:
                startTimeCheck.setEnabled(true);
                endTimeCheck.setEnabled(true);
                startTimeCheck.setFocusable(true); //  是设置能否获得焦点而已
                startTimeCheck.requestFocus();//是让控件得到焦点
                startTimeCheck.performClick();
                EditText_Start.setText("");
                EditText_End.setText("");
                timecheck();
                startTimeCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditTextClick(EditText_Start);
                    }
                });
                endTimeCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditTextClick(EditText_End);
                    }
                });
                break;
            case R.id.picture_TimeSure:
               if (!TextUtils.isEmpty(EditText_Start.getText().toString()) && !TextUtils.isEmpty(EditText_End.getText().toString()))
               {
                   Timestamp startTime = Timestamp.valueOf(EditText_Start.getText().toString());
                   Timestamp endTime = Timestamp.valueOf(EditText_End.getText().toString());
                   int result = endTime.compareTo(startTime) ;
                   if (result >=0)
                   {
                       Bundle bundle = new Bundle();
                       bundle.putInt("m_iLogID",userId );
                       bundle.putInt("m_iChanNum", channel);
                       bundle.putString("start",EditText_Start.getText().toString().trim());
                       bundle.putString("end",EditText_End.getText().toString().trim());
                       Intent intent = new Intent(PictureActivity.this,PictureResultActivity.class);
                       intent.putExtras(bundle);
                       startActivity(intent);
                   }else {
                       Toast.makeText(PictureActivity.this,"开始时间不能小于结束时间",Toast.LENGTH_SHORT).show();
                   }
               }
                break;
            case R.id.picture_TimeCancel:
                EditText_Start.setText("");
                EditText_End.setText("");
                break;
            default:
                break;
        }
    }
}

