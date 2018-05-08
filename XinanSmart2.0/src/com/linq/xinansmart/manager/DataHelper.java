package com.linq.xinansmart.manager;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.linq.xinansmart.model.Concenter;
import com.linq.xinansmart.model.Location;
import com.linq.xinansmart.model.Location_equipment;

public class DataHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "xrfsmartbase.db";
	private static final int DATABASE_VERSION = 3;
	private Dao<Concenter, Integer> concenterDao = null;
	private Dao<Location, Integer> locatonDao=null;
	private Dao<Location_equipment, Integer> location_equipmentDao=null;
	// private Dao<Equipment,Integer> eqDao = null;
	// private Dao<ModeInfo,Integer> modeDao = null;
	// private Dao<ModeEquipment,Integer> meqDao = null;

	public DataHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Concenter.class);
			 TableUtils.createTable(connectionSource, Location.class);
			 TableUtils.createTable(connectionSource, Location_equipment.class);
			// TableUtils.createTable(connectionSource, ModeInfo.class);
			// TableUtils.createTable(connectionSource, ModeEquipment.class);
		} catch (Exception e) {
			Log.e(DataHelper.class.getName(), "不能创建数据库！", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource, int oldVer, int newVer) {
		try {
			getLocationDao();
//			locatonDao.executeRaw("alter table Location add test String DEFAULT ss;");
			TableUtils.dropTable(connectionSource, Concenter.class, true);
			 TableUtils.dropTable(connectionSource, Location.class, true);
			 TableUtils.dropTable(connectionSource, Location_equipment.class, true);
			// TableUtils.dropTable(connectionSource, ModeInfo.class, true);
			// TableUtils.dropTable(connectionSource, ModeEquipment.class,
			// true);

			onCreate(sqliteDatabase, connectionSource);
		} catch (Exception e) {

		}
	}

	@Override
	public void close() {
		super.close();
		concenterDao = null;
		locatonDao = null;
		location_equipmentDao = null;
		// modeDao = null;
		// meqDao = null;
	}

	public Dao<Location, Integer> getLocationDao() throws SQLException {
		if (locatonDao == null) {
			locatonDao = getDao(Location.class);
		}
		return locatonDao;
	}
	
	public Dao<Location_equipment, Integer> getLocationEquipmentDao() throws SQLException {
		if (location_equipmentDao == null) {
			location_equipmentDao = getDao(Location_equipment.class);
		}
		return location_equipmentDao;
	}
	
	public Dao<Concenter, Integer> getConcenterDao() throws SQLException {
		if (concenterDao == null) {
			concenterDao = getDao(Concenter.class);
		}
		return concenterDao;
	}

	// public Dao<NetGate, Integer> getNetDao() throws SQLException {
	// if (netDao == null)
	// {
	// netDao = getDao(NetGate.class);
	// }
	// return netDao;
	// }
	//
	// public Dao<Equipment, Integer> getEqDao() throws SQLException {
	// if (eqDao == null)
	// {
	// eqDao = getDao(Equipment.class);
	// }
	// return eqDao;
	// }
	//
	// public Dao<ModeInfo, Integer> getModeDao() throws SQLException {
	// if (modeDao == null)
	// {
	// modeDao = getDao(ModeInfo.class);
	// }
	// return modeDao;
	// }
	// public Dao<ModeEquipment, Integer> getMeqDao() throws SQLException {
	// if (meqDao == null) {
	// meqDao = getDao(ModeEquipment.class);
	// }
	// return meqDao;
	// }

}
