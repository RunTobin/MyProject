package com.linq.xinansmart.ui_activity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mvp.I_Image;
import mvp.ImagePresenter;
import android.R.integer;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.linq.xinansmart.R;
import com.linq.xinansmart.control.ColorControlActivity;
import com.linq.xinansmart.control.OnOffActivity;
import com.linq.xinansmart.control.OnOffStopActivity;
import com.linq.xinansmart.control.ProgressActivity;
import com.linq.xinansmart.control.StartUpActivity;
import com.linq.xinansmart.manager.EquipmentManager;
import com.linq.xinansmart.manager.LocationManager;
import com.linq.xinansmart.manager.Location_EquipmentManager;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location;
import com.linq.xinansmart.model.Location_equipment;
import com.squareup.picasso.Picasso;

public class Activity_Edit_Location_Detail extends SherlockActivity implements
		I_Image, OnTouchListener {
	private RelativeLayout frameLayout;
	private List<Equipment> eqList = new ArrayList<Equipment>();
	private Location location;
	private Location_EquipmentManager location_EquipmentManager;
	private CharSequence[] charSequences = new CharSequence[] {};
	private List<Equipment> choiceEqList = new ArrayList<Equipment>();
	private List<Location_equipment> location_equipmentlist;
	private List<Location_equipment> location_equipmentlist1 = new ArrayList<Location_equipment>();
	private List<Location_equipment> oldequipments_delete = new ArrayList<Location_equipment>();
	private List<Location_equipment> oldequipments_old = new ArrayList<Location_equipment>();
	private List<Location_equipment> newequipments = new ArrayList<Location_equipment>();
	private List<TextView> views = new ArrayList<TextView>();
	private Concenter concenter;
	private int X;
	private int Y;
	private ImagePresenter imagePresenter;
	private boolean choice = false;
	private ImageView backgound;
	private Equipment choicEquipment;
	private Location_equipment choicelocationEquipment;
	private static int REQUEST_CODE_camera = 200010;
	private String path;
	private String path2;
	private String image;
	private LocationManager locationManager;
	
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
				image=path;
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
		location = (Location) getIntent().getExtras().getSerializable(
				"location");
		locationManager=LocationManager.getInstance(getApplicationContext());
		concenter = (Concenter) getIntent().getExtras().getSerializable("con");
		setContentView(R.layout.activity_add_location_detail);
		backgound = (ImageView) findViewById(R.id.imageBackgoound);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("�������� " + location.getName());
		frameLayout = (RelativeLayout) findViewById(R.id.frame);
		image = location.getBackground();
		Display currentDisplay = getWindowManager().getDefaultDisplay();
		imagePresenter = new ImagePresenter(this,currentDisplay);
		imagePresenter.getDrawble(image);
		initData();
		showEquiment();
		super.onCreate(savedInstanceState);
	}

	private void initData() {
		location_equipmentlist = location_EquipmentManager.getInstance(this)
				.getLocation_equipList();
		for (Location_equipment location_equipment : location_equipmentlist) {
			if (location_equipment.getLocation().getId() == location.getId()) {
				location_equipmentlist1.add(location_equipment);
			}
		}
	}

	private void showEquiment() {
		for (Location_equipment location_equipment : location_equipmentlist1) {
			TextView textView = new TextView(Activity_Edit_Location_Detail.this);
			textView.setText(location_equipment.getName());
			textView.setTextColor(getResources().getColor(R.color.colorPrimary));
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			textView.setGravity(Gravity.CENTER);
			if (location_equipment.getType() != 0) {
				setDrawble2(textView, location_equipment);
			}
			params.setMargins(location_equipment.getX(),
					location_equipment.getY(), 0, 0);
			textView.setLayoutParams(params);
			textView.setTag(R.id.about_image, location_equipment);

			frameLayout.addView(textView);
			textView.setOnTouchListener(this);

			// ���豸code��Ϊ�����Ψһ��־
			textView.setTag(R.id.abs__action_bar,
					location_equipment.getEquCode());
			textView.setTag(R.id.sb_colorSet, "1");
			// �����豸�����豸���󣬸�����ֵ������ӵ�list��
			oldequipments_old.add(location_equipment);
		}

	}

	private void setDrawble2(TextView textView, Location_equipment equipment) {
		// TODO Auto-generated method stub
		Drawable drawable = getResources().getDrawable(
				EquipmentManager.getInstance().getImage2(equipment));
		// / ��һ������Ҫ��,���򲻻���ʾ.
		drawable.setBounds(0, 0, 100, 100);
		textView.setCompoundDrawables(null, drawable, null, null);
	}

	private void setDrawble2(TextView textView, Equipment equipment) {
		// TODO Auto-generated method stub
		Drawable drawable = getResources().getDrawable(
				EquipmentManager.getInstance().getImage(equipment));
		// / ��һ������Ҫ��,���򲻻���ʾ.
		drawable.setBounds(0, 0, 100, 100);
		textView.setCompoundDrawables(null, drawable, null, null);
	}

	private void displayImage(ImageView imageView, int drawable) {

		Picasso.with(Activity_Edit_Location_Detail.this).load(drawable)
				.resize(300, 300).into(imageView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		SubMenu subMenu = menu.addSubMenu("");
		// menu.add(0,0,0,"����").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		subMenu.add(Menu.NONE, 1, 1, "����豸");
		subMenu.add(Menu.NONE, 2, 2, "����");
		subMenu.add(Menu.NONE, 3, 3, "���ı���");
		MenuItem subMenu1Item = subMenu.getItem();
		subMenu1Item.setIcon(R.drawable.ic_menu_overflow);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getOrder() == 2) {
			location_EquipmentManager = Location_EquipmentManager
					.getInstance(Activity_Edit_Location_Detail.this);
			if (newequipments.size() > 0) {
				for (int i = 0; i < newequipments.size(); i++) {
					location_EquipmentManager.addLocation_equipment(
							newequipments.get(i).getX(), newequipments.get(i)
									.getY(), location, newequipments.get(i)
									.getNcode(), newequipments.get(i)
									.getSvalue(), newequipments.get(i)
									.getType(), newequipments.get(i)
									.getMachinID(), newequipments.get(i)
									.getNindex(), newequipments.get(i)
									.getName(), newequipments.get(i)
									.getbOnline(), newequipments.get(i)
									.getEquCode());
				}
			}
			if(oldequipments_delete.size()>0){
				for(Location_equipment location_equipment:oldequipments_delete){
					location_EquipmentManager.deleteEquiment(location_equipment);
				}
			}
			if(oldequipments_old.size()>0){
				for(Location_equipment location_equipment:oldequipments_old){
					location_EquipmentManager.updataXY(location_equipment);
				}
			}
			locationManager.updataPath(location, image);
			Toast.makeText(Activity_Edit_Location_Detail.this, "����ɹ�",
					Toast.LENGTH_SHORT).show();
			finish();
		} else if (item.getOrder() == 1) {
			final ProgressDialog progressDialog = new ProgressDialog(
					Activity_Edit_Location_Detail.this);
			progressDialog.setTitle("���ڼ����豸�б�");
			progressDialog.show();
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					eqList = EquipmentManager.getInstance().getAllEquipment(
							concenter.getUser(), concenter.getPassword());
					List<String> strings = new ArrayList<String>();
					for (Equipment eq : eqList) {
						strings.add(eq.getName());
					}
					charSequences = strings.toArray(charSequences);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							progressDialog.dismiss();
							showDialog(charSequences);
						}
					});
				}
			}).start();
		} else if (item.getOrder() == 3) {
			final AlertDialog alertDialog = new AlertDialog.Builder(
					Activity_Edit_Location_Detail.this).setItems(
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

	private void showDialog(final CharSequence[] contents) {
		final AlertDialog alertDialog = new AlertDialog.Builder(
				Activity_Edit_Location_Detail.this).create();
		alertDialog.show();
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.dialog_listview);
		ListView listView = (ListView) window
				.findViewById(R.id.dialog_listview);
		listView.setAdapter(new BaseAdapter() {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				convertView = View.inflate(Activity_Edit_Location_Detail.this,
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
						Activity_Edit_Location_Detail.this);
				textView.setText(equipment.getName());
				textView.setGravity(Gravity.CENTER);
				if (equipment.getType() != 0) {
					setDrawble2(textView, equipment);// Ϊtextview���ͼƬ
				}
				textView.setTextColor(getResources().getColor(
						R.color.colorPrimary));
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(300, 400, 0, 0);
				textView.setLayoutParams(params);
				frameLayout.addView(textView);
				views.add(textView);
				textView.setOnTouchListener(Activity_Edit_Location_Detail.this);

				// ���豸code��Ϊ�����Ψһ��־
				textView.setTag(R.id.abs__action_bar, equipment.getEquCode());
				textView.setTag(R.id.sb_colorSet, "2");
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
				newequipments.add(location_equipment);

			}
		});
	}

	private Equipment equipment;

	@Override
	public void checkImage(int drawble) {
		// TODO Auto-generated method stub
		displayImage(backgound, drawble);
	}

	private int startx;
	private int starty;
	private int startx1;
	private int starty1;
	private int s = 0;
	private int b = 0;
	private boolean press;
	String eqcode;
	private String status;

	@Override
	public boolean onTouch(final View v, MotionEvent event) {
		// ��ȡ������豸
		eqcode = (String) v.getTag(R.id.abs__action_bar);
		//��ȡ������豸�����豸��������ӵ��豸
		status = (String) v.getTag(R.id.sb_colorSet);
		// Log.e("---------------------",status+eqcode);
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
				new AlertDialog.Builder(Activity_Edit_Location_Detail.this)
						.setTitle("ϵͳ��ʾ")
						// ���öԻ������
						.setMessage("ȷ��Ҫ�޸��豸��Ϣ��")
						// ������ʾ������

						.setPositiveButton("ɾ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										if (status.equals("1")) {//�ж����µĻ��Ǿɵ��豸
											if (oldequipments_old.size() > 0) {
												// ���������ϵ��豸�����б���ɾ�����豸
												for (Location_equipment location_equipment : oldequipments_old) {
													if (location_equipment
															.getEquCode()
															.equals(eqcode)) {
														oldequipments_old
																.remove(location_equipment);
														//��ȡɾ���˵��豸������ӵ�ɾ��list��
														oldequipments_delete.add(location_equipment);
														break;
													}
												}
											}

										} else {//ɾ������ӵ��豸
											if (newequipments.size() > 0) {
												// ������������ӵ��豸�����б���ɾ�����豸
												for (Location_equipment location_equipment : newequipments) {
													if (location_equipment
															.getEquCode()
															.equals(eqcode)) {
														newequipments
																.remove(location_equipment);
														break;
													}
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

										if(status.equals("2")){
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
														Activity_Edit_Location_Detail.this,
														EquipUpdateActivity.class);
												in.putExtra("eqId",
														choicEquipment.getId());
												in.putExtra("user",
														concenter.getUser());
												in.putExtra("password",
														concenter.getPassword());
												startActivityForResult(in, 2222);
											}	
										}else {
											Equipment noweq = null;
											for (Location_equipment equipment : oldequipments_old) {
												if (equipment.getEquCode().equals(
														eqcode)) {
													choicelocationEquipment = equipment;
													break;
												}
											}
											eqList=EquipmentManager.getInstance().getAllEquipment(concenter.getUser(), concenter.getPassword());
											for(Equipment equipment:eqList){
												if(equipment.getEquCode().equals(choicelocationEquipment.getEquCode())){
													noweq=equipment;
												}
											}
											if (choicelocationEquipment != null) {
												
												Intent in = new Intent();
												in.setClass(
														Activity_Edit_Location_Detail.this,
														EquipUpdateActivity.class);
												in.putExtra("eqId",
														noweq.getId());
												in.putExtra("user",
														concenter.getUser());
												in.putExtra("password",
														concenter.getPassword());
												startActivityForResult(in, 2222);
											}	
										}

									}
								}).show();
			} else {// ����ɾ��
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.setMargins(lastx, lasty, 0, 0);
				v.setLayoutParams(params);
				if (status.equals("1")) {
					for (Location_equipment location_equipment2 : oldequipments_old) {
						// ����list������϶���������ӹ����豸�������list�������豸��Ϣ
						if (location_equipment2.getEquCode().equals(eqcode)) {
							location_equipment2.setX(lastx);
							location_equipment2.setY(lasty);
							break;
						}
					}
				} else {
					for (Location_equipment location_equipment2 : newequipments) {
						// ����list������϶���������ӹ����豸�������list�������豸��Ϣ
						if (location_equipment2.getEquCode().equals(eqcode)) {
							location_equipment2.setX(lastx);
							location_equipment2.setY(lasty);
							break;
						}
					}
				}
			}
			break;
		}

		return true;
	}

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
	public void getimage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		backgound.setImageBitmap(bitmap);
	}

}
