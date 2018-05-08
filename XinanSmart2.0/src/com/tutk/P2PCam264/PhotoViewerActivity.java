package com.tutk.P2PCam264;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import com.tutk.P2PCam264.*;

public class PhotoViewerActivity  extends Activity {
    
	private String mFileName;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
          super.onCreate(savedInstanceState);
          System.gc();
          Intent i = getIntent();
          Bundle extras = i.getExtras();
          //BitmapFactory.Options bfo = new BitmapFactory.Options();
          //bfo.inSampleSize = 2;
          mFileName = extras.getString("filename");
          ImageView iv = new ImageView(getApplicationContext());
          Bitmap bm = BitmapFactory.decodeFile(mFileName);// ,bfo);
          iv.setImageBitmap(bm);
          setContentView(iv);
    } 
}