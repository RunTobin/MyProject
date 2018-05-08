package com.linq.xinansmart.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Equipment;
import com.linq.xinansmart.model.Location;
import com.linq.xinansmart.model.Location_equipment;

public class Location_EquipmentManager {

	private DataHelper helper = null;
	private Dao<Location_equipment, Integer> locationEquipmentDao;
	private Context context;
	private static Location_EquipmentManager location_EquipmentManager = null;
	private List<Location_equipment> location_equipmentList = null;

	private Location_EquipmentManager(Context context) {
		this.context = context;
	};

	public static Location_EquipmentManager getInstance(Context context) {
		if (location_EquipmentManager == null) {
			return new Location_EquipmentManager(context);
		}
		return location_EquipmentManager;

	}

	/**
	 * @return 查询数据库得到的控制中心列表
	 */
	public List<Location_equipment> getLocation_equipList() {
		if (location_equipmentList == null) {
			location_equipmentList = new ArrayList<Location_equipment>();
		}
		try {
			if (helper == null) {
				helper = new DataHelper(context);
			}
			locationEquipmentDao = helper.getLocationEquipmentDao();
			location_equipmentList = locationEquipmentDao.queryForAll();
		} catch (Exception e) {
			location_equipmentList = null;
		}

		return location_equipmentList;
	}

	//更新设备状态
	public void updataOnline(String code, Boolean online,String Svalue,String name, List<Location_equipment> list) {
		for (int i = 0; i < list.size(); i++) {
			Location_equipment equip = list.get(i);
			if (equip.getEquCode().equals(code)) {
				equip.setName(name);
				equip.setbOnline(online);
				equip.setSvalue(Svalue);
				if (helper == null) {
					helper = new DataHelper(context);
				}
				try {
					locationEquipmentDao = helper.getLocationEquipmentDao();
					locationEquipmentDao.update(equip);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}


	 /**
	 * @param name
	 * 用户名
	 * @param user
	 * 账号
	 * @param password
	 * 密码
	 * @return 返回控制中心对象 添加到列表及数据库中
	 */
	public Location_equipment addLocation_equipment(int X, int Y,
			Location location, int Ncode, String Svalue, int Type,
			int MachinID, int Nindex, String Name, boolean online,String eqcode) {

		if (location_equipmentList == null) {
			location_equipmentList = new ArrayList<Location_equipment>();
		}

		Location_equipment location_equipment = new Location_equipment();
		location_equipment.setX(X);
		location_equipment.setY(Y);
		location_equipment.setLocation(location);
		location_equipment.setNcode(Ncode);
		location_equipment.setSvalue(Svalue);
		location_equipment.setType(Type);
		location_equipment.setMachinID(MachinID);
		location_equipment.setNindex(Nindex);
		location_equipment.setName(Name);
		location_equipment.setbOnline(online);
		location_equipment.setEquCode(eqcode);
		try {
			if (helper == null) {
				helper = new DataHelper(context);
			}
			locationEquipmentDao = helper.getLocationEquipmentDao();
			locationEquipmentDao.create(location_equipment);// 加到数据库中
			location_equipmentList.add(location_equipment);

		} catch (SQLException e) {
		}
		return location_equipment;

	}
	
	//更新设备XY值
		public void updataXY(Location_equipment equip) {
			
					if (helper == null) {
						helper = new DataHelper(context);
					}
					try {
						locationEquipmentDao = helper.getLocationEquipmentDao();
						locationEquipmentDao.update(equip);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				
			}
		

	public void deletLocationEquiment_By_Location(Location location) {
		for (Location_equipment location_equipment : location_equipmentList) {
			if (location_equipment.getLocation().getId() == location.getId()) {
				try {
					locationEquipmentDao.delete(location_equipment);
					// location_equipmentList.remove(location_equipment);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Toast.makeText(context, "删除失败！", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		}
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
	public void deleteEquiment(Location_equipment equipment) {

		if (helper == null) {
			helper = new DataHelper(context);
		}
		try {
			locationEquipmentDao = helper.getLocationEquipmentDao();
			locationEquipmentDao.delete(equipment);
			Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// try {
		// conDao.delete(concenter);
		// conList.remove(concenter);
		// // DeleteNetGateFromCenter(concenter.getId());
		// // DeleteEquipFromCenter(concenter.getId());
		// // DeleteModeFromCenter(concenter.getId());
		// } catch (Exception e) {
		// Toast.makeText(context, "删除失败！", Toast.LENGTH_SHORT).show();
		//
		// }

	}
	//
}
