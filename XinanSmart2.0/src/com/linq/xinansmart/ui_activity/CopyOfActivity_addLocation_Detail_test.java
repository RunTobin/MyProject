package com.linq.xinansmart.ui_activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import mvp.AddLocation_Presenter;
import mvp.I_AddLocation;
import mvp.I_Image;
import mvp.ImagePresenter;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification.Action;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.linq.xinansmart.R;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.manager.LocationManager;
import com.linq.xinansmart.manager.Location_EquipmentManager;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location;
import com.linq.xinansmart.model.Location_equipment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.greenrobot.event.EventBus;

public class CopyOfActivity_addLocation_Detail_test extends SherlockActivity
		implements I_Image, I_AddLocation, View.OnTouchListener {

	private List<Equipment> eqList = new ArrayList<Equipment>();
	private List<Equipment> choiceEqList = new ArrayList<Equipment>();
	private RelativeLayout frameLayout;
	private Concenter concenter;
	private LocationManager locationManager = null;
	private Location_EquipmentManager location_EquipmentManager = null;
	private List<Location_equipment> equipments = new ArrayList<Location_equipment>();
	private List<TextView> views = new ArrayList<TextView>();
	private int X;
	private int Y;
	private String name;
	private String image;
	private ImagePresenter imagePresenter;
	private ImageView backgound;
	private AddLocation_Presenter addLocation_Presenter;
	private ProgressDialog progressDialog;
	private Equipment equipment;
	private Equipment choicEquipment;
	private static int REQUEST_CODE_camera = 200010;
	private String path;
	private String path2;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 2222:// ���޸ĵ���Ϣ���ص�ҳ����
			String nameString = data.getStringExtra("name");
			if (choicEquipment != null) {
				for (TextView textView : views) {
					if (textView.getText().toString()
							.equals(choicEquipment.getName())) {
						textView.setText(nameString);
						break;
					}
				}
			}
			break;
		case 200010:
			Log.e("resultCode", resultCode + "");
			if (resultCode == -1) {
				String string = "/sdcard" + path2;
				// Log.e("path", string);
				Bitmap bitmap = getBitMapFromPath(path);
				int degree = getBitmapDegree(path);
				Bitmap bitmaps = rotateBitmapByDegree(bitmap, degree);
				backgound.setImageBitmap(bitmaps);
			} else {
				Toast.makeText(this, "ȡ��", Toast.LENGTH_SHORT).show();
			}

			break;

		case 20000000:

			if (resultCode == -1) {
				ContentResolver resolver = getContentResolver();
				Bitmap bm = null;
				try {

					Uri originalUri = data.getData(); // ���ͼƬ��uri

					bm = MediaStore.Images.Media.getBitmap(resolver,
							originalUri); // �Եõ�bitmapͼƬ

					String[] proj = { MediaStore.Images.Media.DATA };

					// ������android��ý�����ݿ�ķ�װ�ӿڣ�����Ŀ�Android�ĵ�

					Cursor cursor = managedQuery(originalUri, proj, null, null,
							null);

					// ���Ҹ������ ����ǻ���û�ѡ���ͼƬ������ֵ

					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

					// �����������ͷ ���������Ҫ����С�ĺ���������Խ��

					cursor.moveToFirst();

					// ����������ֵ��ȡͼƬ·��

					String path2 = cursor.getString(column_index);
					Log.e("path", path2);
					image = path2;
					backgound.setImageBitmap(getBitMapFromPath(path2));
				} catch (IOException e) {

				}
			} else {
				Toast.makeText(this, "ȡ��", Toast.LENGTH_SHORT).show();
			}

			// �˴��������жϽ��յ�Activity�ǲ�������Ҫ���Ǹ�

			break;
		default:
			break;
		}

	}

	private Bitmap getBitMapFromPath(String imageFilePath) {

		Display currentDisplay = getWindowManager().getDefaultDisplay();
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
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
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
			returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
					bm.getHeight(), matrix, true);
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		concenter = (Concenter) intent.getExtras().getSerializable("con");
		image = intent.getExtras().getString("image");
		Log.e("123", concenter.getName());
		name = intent.getExtras().getString("LocationName");
		setContentView(R.layout.activity_add_location_detail);

		backgound = (ImageView) findViewById(R.id.imageBackgoound);
		Display currentDisplay = getWindowManager().getDefaultDisplay();
		imagePresenter = new ImagePresenter(this, currentDisplay);
		imagePresenter.getDrawble(image);
		path = image;

		addLocation_Presenter = new AddLocation_Presenter(this);
		frameLayout = (RelativeLayout) findViewById(R.id.frame);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("�������� " + name);
		// locationnameTextView.setText("�������� " + name);

		super.onCreate(savedInstanceState);
	}

	@Override
	public void checkImage(int drawble) {
		// TODO Auto-generated method stub
		displayImage(backgound, drawble);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		SubMenu subMenu = menu.addSubMenu("");
		// menu.add(0,0,0,"����").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		subMenu.add(Menu.NONE, 1, 1, "����");
		subMenu.add(Menu.NONE, 2, 2, "����豸");
		subMenu.add(Menu.NONE, 3, 3, "���ı���");
		MenuItem subMenu1Item = subMenu.getItem();
		subMenu1Item.setIcon(R.drawable.ic_menu_overflow);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getOrder() == 1) {
			// Toast.makeText(this, "����", Toast.LENGTH_SHORT).show();
			locationManager = LocationManager
					.getInstance(CopyOfActivity_addLocation_Detail_test.this);
			location_EquipmentManager = Location_EquipmentManager
					.getInstance(CopyOfActivity_addLocation_Detail_test.this);
			Location location = addLocation_Presenter.addLocation(
					locationManager, name, path, concenter.getUser());
			addLocation_Presenter.addLocation_equiment(equipments,
					location_EquipmentManager, location);
		} else if (item.getOrder() == 2) {
			progressDialog = new ProgressDialog(
					CopyOfActivity_addLocation_Detail_test.this);
			progressDialog.setTitle("���ڼ����豸�б�");
			progressDialog.show();
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					addLocation_Presenter.getEqlist(concenter);
				}
			}).start();
		} else if (item.getOrder() == 3) {
			final AlertDialog alertDialog = new AlertDialog.Builder(
					CopyOfActivity_addLocation_Detail_test.this).setItems(
					new String[] { "������л�ȡ", "���ջ�ȡ" },
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							case 0:
								getImageFromGal();
								break;
							case 1:
								getImageFromCam();
								break;
							default:
								break;
							}

						}

					}).create();
			alertDialog.show();
		}

		return super.onOptionsItemSelected(item);
	}

	private void getImageFromCam() {
		File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/XianAn/Camera");
		file.mkdirs();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		path2 = "/XianAn/Camera/" + format.format(new Date()) + ".jpg";
		path = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ path2;
		File imageFile = new File(path);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
		startActivityForResult(intent, REQUEST_CODE_camera);
	}

	private void getImageFromGal() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, 20000000);
	}

	private void displayImage(ImageView imageView, int drawable) {

		Picasso.with(CopyOfActivity_addLocation_Detail_test.this)
				.load(drawable).resize(300, 300).into(imageView);
	}

	private void showDialog(final CharSequence[] contents) {
		final AlertDialog alertDialog = new AlertDialog.Builder(
				CopyOfActivity_addLocation_Detail_test.this).create();
		alertDialog.show();
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.dialog_listview);
		ListView listView = (ListView) window
				.findViewById(R.id.dialog_listview);
		listView.setAdapter(new BaseAdapter() {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				convertView = View.inflate(
						CopyOfActivity_addLocation_Detail_test.this,
						R.layout.itme_dialog_listview, null);
				ImageView imageView = (ImageView) convertView
						.findViewById(R.id.item_image);
				TextView textView = (TextView) convertView
						.findViewById(R.id.item_text);
				imageView.setImageResource(EquipmentManager.getInstance()
						.getImage(eqList.get(position)));
				textView.setText(eqList.get(position).getName());
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return eqList.size();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				equipment = eqList.get(position);
				// ��ѡ����豸��ӵ�list��
				choiceEqList.add(equipment);
				alertDialog.dismiss();

				// ��̬����Textview
				TextView textView = new TextView(
						CopyOfActivity_addLocation_Detail_test.this);
				textView.setText(equipment.getName());
				textView.setGravity(Gravity.CENTER);
				if (equipment.getType() != 0) {
					setDrawble(textView, equipment);// Ϊtextview���ͼƬ
				}
				textView.setTextColor(getResources().getColor(
						R.color.colorPrimary));
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(300, 400, 0, 0);
				textView.setLayoutParams(params);
				frameLayout.addView(textView);
				views.add(textView);
				textView.setOnTouchListener(CopyOfActivity_addLocation_Detail_test.this);

				// ���豸code��Ϊ�����Ψһ��־
				textView.setTag(R.id.abs__action_bar, equipment.getEquCode());
				// �����豸�����豸���󣬸�����ֵ������ӵ�list��
				Location_equipment location_equipment = new Location_equipment();
				location_equipment.setX(300);
				location_equipment.setY(400);
				location_equipment.setNcode(equipment.getNcode());
				location_equipment.setSvalue(equipment.getSvalue());
				location_equipment.setType(equipment.getType());
				location_equipment.setMachinID(equipment.getMachinID());
				location_equipment.setNindex(equipment.getNindex());
				location_equipment.setName(equipment.getName());
				location_equipment.setbOnline(equipment.getbOnline());
				location_equipment.setEquCode(equipment.getEquCode());
				Log.e("______________", equipment.getEquCode());
				location_equipment.setScan(equipment.getScan());
				equipments.add(location_equipment);

			}
		});
	}

	private int startx;
	private int starty;
	private int startx1;
	private int starty1;
	private int s = 0;
	private int b = 0;
	private boolean press;
	String eqcode;

	// �ж���ָ�Ƿ��²���
	private boolean isPressed(float lastX, float lastY, float thisX, float thisY) {
		float offsetX = Math.abs(thisX - lastX);
		float offsetY = Math.abs(thisY - lastY);

		if (offsetX <= 10 && offsetY <= 10) {
			return true;
		}
		return false;
	}

	@Override
	public boolean onTouch(final View v, MotionEvent event) {
		// ��ȡ������豸
		eqcode = (String) v.getTag(R.id.abs__action_bar);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startx = (int) event.getRawX();
			starty = (int) event.getRawY();
			startx1 = (int) event.getRawX();
			starty1 = (int) event.getRawY();
		case MotionEvent.ACTION_MOVE:// ��ָ����Ļ���ƶ���Ӧ���¼�
			int x = (int) event.getRawX();
			int y = (int) event.getRawY();

			// ��ȡ��ָ�ƶ��ľ���
			int dx = x - startx;
			int dy = y - starty;
			// �õ�imageView�ʼ�ĸ����������
			int l = v.getLeft();
			int r = v.getRight();
			int t = v.getTop();
			int b = v.getBottom();

			// ����imageView�ڴ����λ��
			v.layout(l + dx, t + dy, r + dx, b + dy);

			// ��ȡ�ƶ����λ��
			startx = (int) event.getRawX();
			starty = (int) event.getRawY();
			break;
		case MotionEvent.ACTION_UP:// ��ָ�뿪��Ļ��Ӧ�¼�
			// ��¼���ͼƬ�ڴ����λ��
			int lasty = v.getTop();
			int lastx = v.getLeft();
			int x1 = (int) event.getRawX();
			int y1 = (int) event.getRawY();
			press = isPressed(startx1, starty1, x1, y1);// �ж��Ƿ�Ϊɾ������
			if (press == true) {
				new AlertDialog.Builder(
						CopyOfActivity_addLocation_Detail_test.this)
						.setTitle("ϵͳ��ʾ")
						// ���öԻ������
						.setMessage("ȷ��Ҫ�޸��豸��Ϣ��")
						// ������ʾ������

						.setPositiveButton("ɾ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										if (equipments.size() > 0) {
											// ������������ӵ��豸�����б���ɾ�����豸
											for (Location_equipment location_equipment : equipments) {
												if (location_equipment
														.getEquCode().equals(
																eqcode)) {
													equipments
															.remove(location_equipment);
													break;
												}
											}
										}
										v.setVisibility(View.GONE);
									}
								})
						.setNegativeButton("����",
								new DialogInterface.OnClickListener() {// ��ӷ��ذ�ť

									@Override
									public void onClick(DialogInterface dialog,
											int which) {// ��Ӧ�¼�
										dialog.dismiss();
									}
								})
						.setNeutralButton("�༭�豸��Ϣ",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										// ������ӵ�list��ͨ���豸code����豸����
										for (Equipment equipment : choiceEqList) {
											if (equipment.getEquCode().equals(
													eqcode)) {
												choicEquipment = equipment;
												break;
											}
										}
										if (choicEquipment != null) {
											Intent in = new Intent();
											in.setClass(
													CopyOfActivity_addLocation_Detail_test.this,
													EquipUpdateActivity.class);
											in.putExtra("eqId",
													choicEquipment.getId());
											in.putExtra("user",
													concenter.getUser());
											in.putExtra("password",
													concenter.getPassword());
											startActivityForResult(in, 2222);
										}

									}
								}).show();
			} else {// ����ɾ��
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(lastx, lasty, 0, 0);
				v.setLayoutParams(params);
				for (Location_equipment location_equipment2 : equipments) {
					// ����list������϶���������ӹ����豸�������list�������豸��Ϣ
					if (location_equipment2.getEquCode().equals(eqcode)) {
						location_equipment2.setX(lastx);
						location_equipment2.setY(lasty);
						break;
					}
				}

			}
			break;
		}

		return true;
	}

	// Ϊtextview���ͼƬ
	private void setDrawble(TextView textView, Equipment equipment) {
		// TODO Auto-generated method stub
		Drawable drawable = getResources().getDrawable(
				EquipmentManager.getInstance().getImage(equipment));
		// / ��һ������Ҫ��,���򲻻���ʾ.
		drawable.setBounds(0, 0, 100, 100);
		textView.setCompoundDrawables(null, drawable, null, null);
	}

	@Override
	public void addLocation() {
		// ���ص�OK
	}

	@Override
	public void addLocation_equiment() {
		// TODO Auto-generated method stub
		Toast.makeText(CopyOfActivity_addLocation_Detail_test.this, "����ɹ�",
				Toast.LENGTH_SHORT).show();
		finish();
		// ���ص�������
		EventBus.getDefault().post(
				new Activity_addLocation_background.FinishActivityEvent() {
					@Override
					public Type getEventType() {
						return Type.FINISH;
					}
				});
	}

	@Override
	public void getEqlist_toArray(final CharSequence[] charSequences,
			List<Equipment> eqList) {
		this.eqList = eqList;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				progressDialog.dismiss();
				showDialog(charSequences);
			}
		});
	}

	@Override
	public void getimage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		backgound.setImageBitmap(bitmap);
	}

}
