package com.tutk.P2PCam264;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.linq.xinansmart.R;
import com.tutk.Logger.Glog;
/*
import pl.polidea.coverflow.CoverFlow;

import pl.polidea.coverflow.AbstractCoverFlowImageAdapter;
import pl.polidea.coverflow.ReflectingImageAdapter;
import pl.polidea.coverflow.ResourceImageAdapter;
*/
import com.actionbarsherlock.app.SherlockActivity;

public class GridViewGalleryActivity  extends SherlockActivity {
    
	
	private String mFileName;
	private String imagesPath ;
	private GridView gridview ;
	 /** The Constant DEFAULT_LIST_SIZE. */
    private static final int DEFAULT_LIST_SIZE = 20;
    /** The Constant IMAGE_RESOURCE_IDS. */
    final List<String> IMAGE_FILES = new ArrayList<String>(DEFAULT_LIST_SIZE);
    private ImageAdapter imageAdapter ;
    private static final int All_Delete = 1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          System.gc() ;
          Bundle extras = this.getIntent().getExtras();
          imagesPath = extras.getString("images_path") ; // XXX: extras may be null and data stored in intent directly. WTF
          //imagesPath = this.getIntent().getStringExtra("images_path") ;
          setContentView(R.layout.gridviewgalleryactivity);
          setImagesPath(imagesPath) ;
          removeCorruptImage() ;
          imageAdapter =new ImageAdapter(this) ;
          gridview = (GridView) findViewById(R.id.gridview);
          
          gridview.setAdapter(imageAdapter);

          
          
          gridview.setOnItemClickListener(new OnItemClickListener() {
              public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                  Intent intent = new Intent(GridViewGalleryActivity.this, PhotoViewerActivity.class);
      			  String fileName = IMAGE_FILES.get(position);
      			  intent.putExtra("filename", fileName);
      			  startActivity(intent);
              }
          });
          gridview.setOnItemLongClickListener(new OnItemLongClickListener() {
  			
  			@Override
  			public boolean onItemLongClick(final AdapterView< ? > parent, final View view, final int position, final long id) {
  				// TODO Auto-generated method stub
  				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
  		            @Override
  				    public void onClick(DialogInterface dialog, int which) {
  				        switch (which){
  				        case DialogInterface.BUTTON_POSITIVE:	
  				            //Yes button clicked
  				        	GridViewGalleryActivity.this.runOnUiThread(new Runnable(){
  								@Override
  								public void run() {
  									// TODO Auto-generated method stub
  									boolean deleted = imageAdapter.deleteImageAtPosition(position) ;
  									// XXX: CA's hack, adapter.notifyDataSetChanged not working when delete item
  									// It's strange.
  									//Intent intent = new Intent(CoverFlowGalleryActivity.this, CoverFlowGalleryActivity.class);
  					    			//intent.putExtra("images_path", imagesPath);
  					    			//startActivity(intent);
  					    			//finish() ;
  								}});
  				            break;

  				        case DialogInterface.BUTTON_NEGATIVE:
  				            //No button clicked
  				            break;
  				        }
  		            }
  				} ;
  				AlertDialog.Builder builder = new AlertDialog.Builder(GridViewGalleryActivity.this);
  				
  			    builder.setMessage(getResources().getString(R.string.dlgAreYouSureToDeleteThisSnapshot))
  			        .setPositiveButton(getResources().getString(R.string.dlgDeleteSnapshotYes), dialogClickListener)
  			        .setNegativeButton(getResources().getString(R.string.dlgDeleteSnapshotNo), dialogClickListener).show();
  				return true;
  			}
        });
    } 
    public final void removeCorruptImage() {
    	Iterator<String> it = IMAGE_FILES.iterator() ;
    	while(it.hasNext()){
    		String path = it.next() ;
    		Bitmap bitmap = BitmapFactory.decodeFile(path) ;
	        // XXX: CA's hack, snapshot may fail and create corrupted bitmap
	        if(bitmap == null){
	        	it.remove();
	        }
    	}
    }
    public final synchronized void setImagesPath(String path) {
    	IMAGE_FILES.clear();
    	File folder = new File(path) ;
    	String[] imageFiles = folder.list();
    	
    	if (imageFiles != null && imageFiles.length > 0) {
	    	Arrays.sort(imageFiles) ;
	    	for(String imageFile : imageFiles)
	    		IMAGE_FILES.add(path+"/"+imageFile);
	    	Collections.reverse(IMAGE_FILES);
    	}
    }
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.clear();			
		SubMenu subMenu = menu.addSubMenu("");

		subMenu.add(Menu.NONE, All_Delete, 1, getText(R.string.Delete_All_Photos).toString());
		
		MenuItem subMenu1Item = subMenu.getItem();
		subMenu1Item.setIcon(R.drawable.ic_menu_overflow);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	int id = item.getItemId();
    	
    	switch (id) {
    	case All_Delete:
    		// TODO Auto-generated method stub
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		            @Override
				    public void onClick(DialogInterface dialog, int which) {
				        switch (which){
				        case DialogInterface.BUTTON_POSITIVE:	
				            //Yes button clicked
				        	GridViewGalleryActivity.this.runOnUiThread(new Runnable(){
								@Override
								public void run() {
									// TODO Auto-generated method stub
									int image_Count = imageAdapter.getCount();
									while(imageAdapter.getCount() != 0) {
										Glog.D("test_a", "image_Count:" + image_Count);
										boolean deleted = imageAdapter.deleteImageAtPosition(0);
									}
								}});
				            break;

				        case DialogInterface.BUTTON_NEGATIVE:
				            //No button clicked
				            break;
				        }
		            }
				} ;
				AlertDialog.Builder builder = new AlertDialog.Builder(GridViewGalleryActivity.this);
				
			    builder.setMessage(getResources().getString(R.string.dlgAreYouSureToDeleteAllSnapshot))
			        .setPositiveButton(getResources().getString(R.string.dlgDeleteSnapshotYes), dialogClickListener)
			        .setNegativeButton(getResources().getString(R.string.dlgDeleteSnapshotNo), dialogClickListener).show();
    		
    		break;
    	}
    	return super.onOptionsItemSelected(item);
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
            
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return IMAGE_FILES.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(LayoutParams.FILL_PARENT,160 ));
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }
			BitmapFactory.Options bfo = new BitmapFactory.Options();
			bfo.inSampleSize = 4;
            
            Bitmap bitmap = BitmapFactory.decodeFile(IMAGE_FILES.get(position),bfo) ;
            
            // XXX: CA's hack, snapshot may fail and create corrupted bitmap
            if(bitmap == null){
    	        for(int i = this.getCount() -1 ; i >= 0 ; i--){
    	        	 bitmap = BitmapFactory.decodeFile(IMAGE_FILES.get(i),bfo) ;
    	        	 if(bitmap != null) break ;
    	        }
            }
            imageView.setImageBitmap(bitmap);
            //imageView.setImageURI(Uri.parse(IMAGE_FILES.get(position))) ;
            //imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        public final boolean deleteImageAtPosition(int position){
        	File file = new File(IMAGE_FILES.get(position));
        	boolean deleted = file.delete();
        	IMAGE_FILES.remove(position) ;
        	this.notifyDataSetChanged();
            return deleted ;
        }
    }
    
}
