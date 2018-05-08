package com.linq.xinansmart.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.linq.xinansmart.model.Concenter;

public class ConcenterManager {

	private DataHelper helper = null;
	private Dao<Concenter, Integer> conDao;
	private Context context;
	private static ConcenterManager concenterManager = null;
	private List<Concenter> conList = null;

	private ConcenterManager(Context context) {
		this.context = context;
	};

	public static ConcenterManager getInstance(Context context) {
		if (concenterManager == null) {
			return new ConcenterManager(context);
		}
		return concenterManager;

	}

	/**
	 * @return ��ѯ���ݿ�õ��Ŀ��������б�
	 */
	public List<Concenter> getconList() {
		if (conList == null) {
			conList = new ArrayList<Concenter>();
			try {
				if (helper == null) {
					helper = new DataHelper(context);
				}
				conDao = helper.getConcenterDao();
				conList = conDao.queryForAll();
			} catch (Exception e) {
				conList = null;
			}
		}
		return conList;
	}

	/**
	 * @param name
	 *            �û���
	 * @param user
	 *            �˺�
	 * @param password
	 *            ����
	 * @return ���ؿ������Ķ��� ��ӵ��б����ݿ���
	 */
	public Concenter addConcenter(String name, String user, String password) {

		if (conList == null) {
			conList = new ArrayList<Concenter>();
		}

		Concenter centerResult = new Concenter();
		centerResult.setName(name);
		centerResult.setUser(user);
		centerResult.setPassword(password);
		try {
			conDao.create(centerResult);// �ӵ����ݿ���
			conList.add(centerResult);

		} catch (SQLException e) {
		}
		return centerResult;

	}

	/**
	 * ���ݿ����������޸ĵ�ǰ�û�
	 * 
	 * @param concenter
	 *            ��������
	 * @param name
	 *            �û���
	 * @param user
	 *            �˺�
	 * @param Password
	 *            ����
	 */
	public void updateConcenter(Concenter concenter, String name, String user,
			String password) {
		try {
			concenter.setName(name);
			concenter.setUser(user);
			concenter.setPassword(password);
			conDao.update(concenter);
			for (int i = 0; i < conList.size(); i++) {
				if (conList.get(i).getId() == concenter.getId()) {
					conList.remove(i);
					conList.add(i, concenter);
				}
			}
		} catch (Exception e) {
		}
	}

	/** ��ѯ���ݿ⼰conList  ɾ���������Ŀ�������
	 * @param concenter 
	 */
	public void deleteConcenter(Concenter concenter) {

		try {
			conDao.delete(concenter);
			conList.remove(concenter);
			// DeleteNetGateFromCenter(concenter.getId());
			// DeleteEquipFromCenter(concenter.getId());
			// DeleteModeFromCenter(concenter.getId());
		} catch (Exception e) {
			Toast.makeText(context, "ɾ��ʧ�ܣ�", Toast.LENGTH_SHORT).show();

		}

	}

}
