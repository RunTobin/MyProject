package mvp;

import java.io.IOException;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.view.Display;

import com.linq.xinansmart.R;

public class ImagePresenter {

	private I_Image i_Image;
	private Display currentDisplay;
	public ImagePresenter(I_Image i_Image,Display currentDisplay) {
		this.currentDisplay=currentDisplay;
		this.i_Image = i_Image;
	}
	
	public ImagePresenter(I_Image i_Image) {
		
		this.i_Image = i_Image;
	}

	public void getDrawble(String image) {
		int drawble = 0;
		if (image.equals("����")) {
			drawble = R.drawable.keting;
			i_Image.checkImage(drawble);
		} else if (image.equals("����")) {
			drawble = R.drawable.woshi;
			i_Image.checkImage(drawble);
		} else if (image.equals("����")) {
			drawble = R.drawable.chufang;
			i_Image.checkImage(drawble);
		} else if (image.equals("����")) {
			drawble = R.drawable.cesuo;
			i_Image.checkImage(drawble);
		} else if (image.equals("�鷿")) {
			drawble = R.drawable.shufang;
			i_Image.checkImage(drawble);
		} else if (image.equals("�칫��")) {
			drawble = R.drawable.bangongshi;
			i_Image.checkImage(drawble);
		}else {
			Bitmap bitmap=getBitMapFromPath(image);
			int degree =getBitmapDegree(image);
		    Bitmap bitmaps=rotateBitmapByDegree(bitmap,degree);
		    i_Image.getimage(bitmaps);
		}

	}
	
	private Bitmap getBitMapFromPath(String imageFilePath) {

		//Display currentDisplay =context.getWindowManager().getDefaultDisplay();
		int dw = currentDisplay.getWidth();
		int dh = currentDisplay.getHeight();
		// Load up the image's dimensions not the image itself
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
				/ (float) dh);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
				/ (float) dw);

		// If both of the ratios are greater than 1,
		// one of the sides of the image is greater than the screen
		if (heightRatio > 1 && widthRatio > 1) {
			if (heightRatio > widthRatio) {
				// Height ratio is larger, scale according to it
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				// Width ratio is larger, scale according to it
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}
		// Decode it for real
		bmpFactoryOptions.inJustDecodeBounds = false;
		bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
		return bmp;
	}
	
	 private int getBitmapDegree(String path) {
	        int degree = 0;
	        try {
	            // ��ָ��·���¶�ȡͼƬ������ȡ��EXIF��Ϣ
	            ExifInterface exifInterface = new ExifInterface(path);
	            // ��ȡͼƬ����ת��Ϣ
	            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
	                    ExifInterface.ORIENTATION_NORMAL);
	            switch (orientation) {
	                case ExifInterface.ORIENTATION_ROTATE_90:
	                    degree = 90;
	                    break;
	                case ExifInterface.ORIENTATION_ROTATE_180:
	                    degree = 180;
	                    break;
	                case ExifInterface.ORIENTATION_ROTATE_270:
	                    degree = 270;
	                    break;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return degree;
	    }

	    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
	        Bitmap returnBm = null;

	        // ������ת�Ƕȣ�������ת����
	        Matrix matrix = new Matrix();
	        matrix.postRotate(degree);
	        try {
	            // ��ԭʼͼƬ������ת���������ת�����õ��µ�ͼƬ
	            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
	        } catch (OutOfMemoryError e) {
	        }
	        if (returnBm == null) {
	            returnBm = bm;
	        }
	        if (bm != returnBm) {
	            bm.recycle();
	        }
	        return returnBm;
	    }
}
