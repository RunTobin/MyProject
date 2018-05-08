package com.linq.xinansmart.control;



import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location_equipment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ColorControlActivity extends Activity{

	private SeekBar colorSet, lightSet;
	private CheckBox changeAlong;
	private int id=-1,contype = -1,Red=0,Green=0,Blue=0,code =0,colorValue=0,lightValue =0;
	private Equipment equipment;
	private LinearLayout ly_color;
	private String  user,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_colorcontrol);
		colorSet = (SeekBar) findViewById(R.id.sb_colorSet);
		lightSet  = (SeekBar) findViewById(R.id.sb_lightSet);//亮度调节
		changeAlong = (CheckBox) findViewById(R.id.cb_changeAlong);
		colorSet.setMax(240);//色相调节
		ly_color = (LinearLayout) findViewById(R.id.lny_color);
		Intent intent = getIntent();
		Location_equipment location_equipment=(Location_equipment) intent.getExtras().getSerializable("location_equiment");
		id = intent.getIntExtra("equipmentId", 0);
		contype = intent.getIntExtra("cenType", -1);
		user = intent.getStringExtra("user");
		password = intent.getStringExtra("password");
		
		equipment = new Equipment();
		if (id != 0) {
			//equipment = EquipmentManager.getInstance().getEqById(id);
			equipment = EquipmentManager.getInstance().getEqById(id);
		} else {
			equipment = new Equipment();
			equipment.setNcode(location_equipment.getNcode());
			equipment.setSvalue(location_equipment.getSvalue());
			equipment.setType(location_equipment.getType());
			equipment.setMachinID(location_equipment.getMachinID());
			equipment.setNindex(location_equipment.getNindex());
		}
			
		if(equipment!=null)
		{
			String m_value ="";
			m_value = equipment.getSvalue();
			code = equipment.getMachinID();			
			int ncolor = Integer.parseInt(m_value);//2301725
			Red  = (ncolor>>16) &0xFF;
			Green  = (ncolor>>8) &0xFF;
			Blue  = ncolor &0xFF;
		
			if(Red==Blue && Blue==Green)
			{
				colorSet.setProgress(0);
				lightSet.setProgress(Red*100/255);
				colorValue=0;
				lightValue=Red*100/255;
				
			}else if(Red == 35 && Green == 31 && Blue== 29)//此种情况为特殊情况，用于处理进行变换灯光
			{
				colorSet.setProgress(60);
				lightSet.setProgress(100);
				colorValue=60;
				lightValue=100;
				changeAlong.setChecked(true);
				
			}else
			{
				int maxValue = Red;
                if (Green > maxValue) maxValue = Green;
                if (Blue > maxValue) maxValue = Blue;
                lightSet.setProgress(maxValue * 100 / 255);
                lightValue = maxValue * 100 / 255 ;
                int R = Red * 255 / maxValue;
                int G = Green * 255 / maxValue;
                int B = Blue * 255 / maxValue;
                int nValue = RGBToValue(R, G, B);
                colorSet.setProgress(nValue+1);
                colorValue = nValue+1;
			}
			//
			//SetColorLapValue(Red,Green,Blue,code);
			//持续变换灯光
			changeAlong.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if(isChecked==true)
					{
						colorSet.setProgress(60);
						lightSet.setProgress(100);
						colorSet.setEnabled(false);
						lightSet.setEnabled(false);
						SetColorLapValue(35,31,29,code);
					}else
					{
						colorSet.setEnabled(true);
						lightSet.setEnabled(true);
						colorSet.setProgress(0);
						lightSet.setProgress(0);
						SetColorLapValue(0,0,0,code);
					}
				}
			});
			
			//调节颜色
			colorSet.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					colorValue = seekBar.getProgress();
					
					int nValue = seekBar.getProgress()-1;//(int)Math.round((double)(seekBar.getProgress()*240 /100)) - 1;
		            ValueToRGB(nValue);
		            int a= (int)Math.round((double)lightValue * Red / 100);
		            Red = a;
		            int b = (int)Math.round((double)lightValue * Green / 100);
		            Green =b;
		            int c = (int)Math.round((double)lightValue * Blue / 100);
		            Blue = c;
		            SetColorLapValue(Red,Green,Blue,code);
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
					//int R=0, G=0, B=0;
					
				}
			});
			
			//调节亮度
			lightSet.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					lightValue = seekBar.getProgress();
					
					if (colorValue == 0 || colorValue == 255)
			        {
			            Red = seekBar.getProgress() * 255 / 100;
			            Green = seekBar.getProgress() * 255 / 100;
			            Blue = seekBar.getProgress() * 255 / 100;
			        }
			        else
			        {
			            int nValue = colorValue - 1;
			            ValueToRGB(nValue);
			            Red = (int)Math.round((double)seekBar.getProgress() * Red / 100);
			            Green = (int)Math.round((double)seekBar.getProgress() * Green / 100);
			            Blue = (int)Math.round((double)seekBar.getProgress() * Blue / 100);
			        }
					SetColorLapValue(Red,Green,Blue,code);
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
					//int R=0, G=0, B=0;
					
				}
			});
			
		}
		//点击背景不退出
		ly_color.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
	}

	//发送指令
	private void SetColorLapValue(int R,int G,int B,int code)
    {
        //生成RGB
			String value = String.valueOf(R*65536+G*256+B);//String.valueOf(R<<16 + G<<8 + B);
			EquipmentManager.getInstance().SetEquipmentValue(equipment.getNcode(), equipment.getType(), equipment.getMachinID(),value, equipment.getNindex(),user,password);
		
    }
	
	// #region 色带值与RGB转换
     private void ValueToRGB(int nValue)//,  int R,  int G,  int B
     {
         if (nValue <= 40)
         {
             Red = 255;
             Green = (int)Math.round(nValue * 6.4);
             Blue = 0;
             if (Green > 255)
            	 Green = 255;
         }
         else if (nValue <= 80)
         {
             Red = 255 - (int)Math.round((nValue - 40) * 6.4);
             Green = 255;
             Blue = 0;
             if (Red < 0)
            	 Red = 0;
         }
         else if (nValue <= 120)
         {
             Red = 0;
             Green = 255;
             Blue = (int)Math.round((nValue - 80) * 6.4);
             if (Blue > 255)
            	 Blue = 255;
         }
         else if (nValue <= 160)
         {
             Red = 0;
             Green = 255 - (int)Math.round((nValue - 120) * 6.4);
             Blue = 255;
             if (Green < 0)
            	 Green = 0;
         }
         else if (nValue <= 200)
         {
             Red = (int)Math.round((nValue - 160) * 6.4);
             Green = 0;
             Blue = 255;
             if (Red > 255)
            	 Red = 255;
         }
         else if (nValue <= 239)
         {
             Red = 255;
             Green = 0;
             Blue = 255 - (int)Math.round((nValue - 200) * 6.4);
             if (Blue < 0)
            	 Blue = 0;
         }
     }
     
     private int RGBToValue(int R, int G, int B)
     {
         int result = 0;
         if (R == 255)
         {
             if (G == 0 && B == 0)
             {
                 result = 0;
             }
             else if (G > 0)
             {
                 result = (int)Math.round(G / 6.4);
             }
             else
             {
                 result = (int)Math.round((255 - B) / 6.4) + 200;
             }
         }
         else if (G == 255)
         {
             if (R == 0 && B == 0)
             {
                 result = 80;
             }
             else if (R > 0)
             {
                 result = (int)Math.round((255 - R) / 6.4) + 40;
             }
             else if (B > 0)
             {
                 result = (int)Math.round(B / 6.4) + 80;
             }
         }
         else if (B == 255)
         {
             if (R == 0 && G == 0)
             {
                 result = 160;
             }
             else if (R > 0)
             {
                 result = (int)Math.round(R / 6.4) + 160;
             }
             else if (G > 0)
             {
                 result = (int)Math.round((255 - G) / 6.4) + 120;
             }
         }
         else
         {
             result = -1;
         }

         return result;
     }
   //  #endregion //ESL与RGB转换
	//点击其他地方 让其退出
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			finish();
			return true;
		}
}
