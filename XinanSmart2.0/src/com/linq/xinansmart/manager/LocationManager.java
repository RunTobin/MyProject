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
	 * @return 查询数据库得到的控制中心列表
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
	// * 用户名
	// * @param user
	// * 账号
	// * @param password
	// * 密码
	// * @return 返回控制中心对象 添加到列表及数据库中
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
			locationDao.create(location);// 加到数据库中
			locationlist.add(location);

		} catch (SQLException e) {
		}
		return location;

	}

	//
	// /**
	// * 根据控制中心来修改当前用户
	// *
	// * @param concenter
	// * 控制中心
	// * @param name
	// * 用户名
	// * @param user
	// * 账号
	// * @param Password
	// * 密码
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
	 * 查询数据库及conList 删除传过来的控制中心
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
			Toast.makeText(context, "删除失败！", Toast.LENGTH_SHORT).show();

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
