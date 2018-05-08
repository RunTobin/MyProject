package com.linq.xinansmart.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.content.Context;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Location;

public class LocationManager {

	private DataHelper helper = null;
	private Dao<Location, Integer> locationDao;
	private Context context;
	private static LocationManager locationManager = null;
	private List<Location> locationlist = null;

	private LocationManager(Context context) {
		this.context = context;
	};
	
	public static LocationManager getInstance(Context context) {
		if (locationManager == null) {
			return new LocationManager(context);
		}
		return locationManager;

	}

	/**
	 * @return ��ѯ���ݿ�õ��Ŀ��������б�
	 */
	public List<Location> getLocationList() {
		if (locationlist == null) {
			locationlist = new ArrayList<Location>();
			}
			try {
				if (helper == null) {
					helper = new DataHelper(context);
				}
				locationDao = helper.getLocationDao();
				locationlist = locationDao.queryForAll();
			} catch (Exception e) {
				locationlist = null;
			}
		
		return locationlist;
	}

	//
	// /**
	// * @param name
	// * �û���
	// * @param user
	// * �˺�
	// * @param password
	// * ����
	// * @return ���ؿ������Ķ��� ��ӵ��б����ݿ���
	// */
	public Location addLocation(String name, String background,String concenter,String View) {

		if (locationlist == null) {
			locationlist = new ArrayList<Location>();
		}

		Location location = new Location();
		location.setName(name);
		location.setBackground(background);
		location.setConcenter(concenter);
		location.setView(View);
		location.setUID("123");
		try {
			if (helper == null) {
				helper = new DataHelper(context);
			}
			locationDao = helper.getLocationDao();
			locationDao.create(location);// �ӵ����ݿ���
			locationlist.add(location);

		} catch (SQLException e) {
		}
		return location;

	}

	//
	// /**
	// * ���ݿ����������޸ĵ�ǰ�û�
	// *
	// * @param concenter
	// * ��������
	// * @param name
	// * �û���
	// * @param user
	// * �˺�
	// * @param Password
	// * ����
	// */
	// public void updateConcenter(Concenter concenter, String name, String
	// user,
	// String password) {
	// try {
	// concenter.setName(name);
	// concenter.setUser(user);
	// concenter.setPassword(password);
	// conDao.update(concenter);
	// for (int i = 0; i < conList.size(); i++) {
	// if (conList.get(i).getId() == concenter.getId()) {
	// conList.remove(i);
	// conList.add(i, concenter);
	// }
	// }
	// } catch (Exception e) {
	// }
	// }
	//
	/**
	 * ��ѯ���ݿ⼰conList ɾ���������Ŀ�������
	 * 
	 * @param concenter
	 */
	public void deleteLocation(Location location) {

		try {
			locationDao.delete(location);
			locationlist.remove(location);
			// DeleteNetGateFromCenter(concenter.getId());
			// DeleteEquipFromCenter(concenter.getId());
			// DeleteModeFromCenter(concenter.getId());
		} catch (Exception e) {
			Toast.makeText(context, "ɾ��ʧ�ܣ�", Toast.LENGTH_SHORT).show();

		}

	}
	
	public void updataPath(Location location,String path){
		if (helper == null) {
			helper = new DataHelper(context);
		}
		try {
			locationDao = helper.getLocationDao();
			location.setBackground(path);
			locationDao.update(location);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updataUID(Location location,String UID){
		if (helper == null) {
			helper = new DataHelper(context);
		}
		try {
			locationDao = helper.getLocationDao();
			location.setUID(UID);
			locationDao.update(location);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updataView(Location location,String view){
		if (helper == null) {
			helper = new DataHelper(context);
		}
		try {
			locationDao = helper.getLocationDao();
			location.setView(view);
			locationDao.update(location);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//
}
