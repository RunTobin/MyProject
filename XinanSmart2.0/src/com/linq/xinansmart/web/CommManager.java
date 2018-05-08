package com.linq.xinansmart.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import android.util.Log;
import android.widget.Toast;

import com.linq.xinansmart.model.Equipment;

public class CommManager {
	//服务器的IP地址URL
	private static CommManager mCommManager;
	private static final int TIMEOUT = 120 * 1000;
	public static final String Service_URL = "http://111.38.122.83:2003/XRFAppPlatID.aspx";
//	public static final String Service_URL = "http://192.168.1.59//XRFAppPlatID.aspx";
	private List<Equipment> eqList = null;

	private CommManager() {

	}

	public static CommManager getInstance() {
		if (mCommManager == null) {
			mCommManager = new CommManager();
		}
		return mCommManager;
	}

	/**
	 * @param json 串  请求数据命令 
	 * @param Service_URL 服务地址
	 * @return 返回json串
	 * @throws IOException 
	 */
	public String sendDataPost(String json, String Service_URL) throws IOException {
		String result = null;
		StringEntity entity;
		Log.e("json",json);
		try {
			entity = new StringEntity(json, HTTP.UTF_8);
			//设置请求体类型
			entity.setContentType("application/json");
			DefaultHttpClient client = new DefaultHttpClient();
			client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,
					TIMEOUT);
			client.getParams().setIntParameter(
					HttpConnectionParams.CONNECTION_TIMEOUT, TIMEOUT);
			HttpPost httpPost = new HttpPost(Service_URL);
			//设置请求实体
			httpPost.setEntity(entity);
			//执行post请求
			HttpResponse response = client.execute(httpPost);
			InputStream inputStream = response.getEntity().getContent();
			StringBuffer buffer = new StringBuffer();
			InputStreamReader inputReader = new InputStreamReader(inputStream);
			BufferedReader bufferReader = new BufferedReader(inputReader);
			String str = new String("");
			while ((str = bufferReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferReader.close();
			result = buffer.toString();
		} catch (UnsupportedEncodingException e) {
			Log.e("111111", "11111111");
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			Log.e("2222222", "222222");
			e.printStackTrace();
		}

		return result;
	}





//	@SuppressWarnings("unchecked")
//	public List<Equipment> GetLastEquipmentStatus(int tick, String userId,
//			String Password) {
//		String data = "{\"Tick\":" + tick + "}";
//		String order = orderControlJson(data,
//				OrderFlag.EquipmentRefreshAllInfo, userId, Password);
//		String str = sendDataPost(order, Service_URL);
//		if (str.trim().length() > 0) {
//			List<Equipment> result = new ArrayList<Equipment>();
//			List<LinkedHashMap<String, String>> lstnew = new ArrayList<LinkedHashMap<String, String>>();
//			lstnew = (List<LinkedHashMap<String, String>>) JsonUtil
//					.JsonToObject(str, lstnew.getClass());
//			for (int i = 0; i < lstnew.size(); i++) {
//				Equipment equipment = new Equipment();
//				equipment = ToEquipment(lstnew.get(i));
//				RefreshEquipmentById(equipment.getId(), equipment.getbOnline(),
//						equipment.getTimeTick(), equipment.getSvalue());
//				result.add(equipment);
//			}
//
//			return result;
//		} else {
//			return null;
//		}
//	}
//
//	private void RefreshEquipmentById(int equipmentId, boolean online,
//			int tick, String value) {
//		for (int i = 0; i < eqList.size(); i++) {
//			Equipment eq = eqList.get(i);
//			if (eq.getId() == equipmentId) {
//				eq.setbOnline(online);
//				eq.setSvalue(value);
//				eq.setTimeTick(tick);
//				break;
//			}
//		}
//	}

//	//合成控制命令
//	
	public static String orderControlJson(String data, String orderFlag,
			String UserId, String Password) {
		String result = null;
		data = JsonUtil.ObjectToJson(data);
		result = "{\"CheckCode\":\"\",\"Data\":" + data + ",\"Order\":\""
				+ orderFlag + "\",\"userID\":\"" + UserId + "\",\"userPwd\":\""
				+ Password + "\"}";
		return result;
	}


	// //根据控制中心ID获取设备列表
	// public List<Equipment> getEqListByCid(int cId)
	// {
	// List<Equipment> lsteq = new ArrayList<Equipment>();
	// for(Equipment eq:eqList)
	// {
	// if(eq.getCid() == cId){
	// lsteq.add(eq);
	// }
	// }
	// return lsteq;
	// }
	//
	// public List<NetGate> getNetList()
	// {
	// return netList;
	// }
	// public List<Equipment> getEqList()
	// {
	// return eqList;
	// }
	// //根据设备ID获取设备
	// public Equipment getEqById(int eqId)
	// {
	// Equipment result = new Equipment();
	// for(Equipment eq:eqList)
	// {
	// if(eq.getId() == eqId){
	// result = eq;
	// break;
	// }
	// }
	//
	// return result;
	// }
	// //给设备赋值
	// public boolean SetEquipmentValue(final int NetGateCode, final int type,
	// final int code, final String value,final int eqIndex,final String
	// user,final String password)
	// {
	//
	//
	// Runnable run = new Runnable() {
	//
	// @Override
	// public void run() {
	// String data =
	// "{\"NetGateCode\":"+NetGateCode+",\"Type\":"+type+",\"Code\":"+code+",\"Value\":\""+value+"\",\"EqIndex\":"+eqIndex+"}";
	// String order =
	// orderControlJson(data,OrderFlag.EquipmentValueChange,user,password);
	// String str = sendDataPost(order, Service_URL);
	// if (str.equals("TRUE") )
	// {
	// for(int i=0;i<eqList.size();i++)
	// {
	// Equipment equip = eqList.get(i);
	// if(equip.getNcode()==NetGateCode&&equip.getMachinID()==code&&equip.getNindex()==eqIndex)
	// {
	// equip.setSvalue(value);
	// break;
	// }
	// }
	// }
	//
	// }
	// };
	// Thread thread = new Thread(run);
	// thread.start();
	// return true;
	//
	// }
	// 更新设备信息
	// public boolean UpdateEquipmentInfo(int NetGateCode, int type, int code,
	// String number, String name, String position, String description, int
	// eqIndex,String UserId,String Password)
	// {
	//
	// String data =
	// "{\"NetGateCode\":"+NetGateCode+",\"Type\":"+type+",\"Code\":"+code+",\"Number\":\""+number+"\",\"Name\":\""+name+"\",\"Position\":\""+position+"\",\"Description\":\""+description+"\",\"EqIndex\":"+eqIndex+"}";
	// String order =
	// orderControlJson(data,OrderFlag.EquipmentInfoUpdate,UserId,Password);
	// String str = sendDataPost(order, Service_URL).toUpperCase();
	// if (str.equals("TRUE") )
	// {
	// for(int i=0;i<eqList.size();i++)
	// {
	// Equipment equip = eqList.get(i);
	// if(equip.getNcode() ==
	// NetGateCode&&equip.getMachinID()==code&&equip.getNindex()==eqIndex)
	// {
	// equip.setAddress(position);
	// equip.setName(name);
	// }
	// }
	// return true;
	// }
	// else
	// {
	// try
	// {
	// throw new Exception(str);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return false;
	// }
	// }
	// }
	// //获取所有场景
	// @SuppressWarnings("unchecked")
	// public List<ModeInfo> getAllProfile(String UserId,String Password)
	// {
	// if(modeList !=null)
	// {
	// modeList.clear();
	// }
	// modeList = new ArrayList<ModeInfo>();
	// String order =
	// JsonUtil.getJsonRequestParams(OrderFlag.AppProfileGetAllInfo, UserId,
	// Password, null);
	// String response = sendDataPost(order,Service_URL);
	// List<LinkedHashMap<String, String>> lstnew = new
	// ArrayList<LinkedHashMap<String,String>>();
	// lstnew = (List<LinkedHashMap<String,
	// String>>)JsonUtil.JsonToObject(response,lstnew.getClass());
	// if(lstnew!=null)
	// {
	// for(int i=0;i<lstnew.size();i++)
	// {
	// ModeInfo meinfo = new ModeInfo();
	// meinfo = ToModeInfo(lstnew.get(i));
	// modeList.add(meinfo);
	// }
	// }
	//
	// return modeList;
	// }
	// public List<ModeInfo> getModeList()
	// {
	// if(modeList ==null)
	// {
	// modeList = new ArrayList<ModeInfo>();
	// }
	//
	// return modeList;
	// }
	// //添加场景
	// public ModeInfo AddAppProfile(String number, String name, boolean enable,
	// String startDate, String endDate, String startTime, String endTime,
	// String description)
	// {
	// if(modeList ==null)
	// {
	// modeList = new ArrayList<ModeInfo>();
	// }
	// String data =
	// "{\"Number\":\""+number+"\",\"Name\":\""+name+"\",\"Enable\":"+enable+",\"StartDate\":\""+startDate+"\",\"EndDate\":\""+endDate+"\",\"StartTime\":\""+startTime+"\",\"EndTime\":\""+endTime+"\",\"Description\":\""+description+"\"}";
	// String order =
	// orderControlJson(data,OrderFlag.AppProfileNew,Global.USERNAME,Global.PASSWORD);
	// String str = sendDataPost(order, Service_URL);
	// if (str.trim().length() > 0)
	// {
	// LinkedHashMap<String, String> profile = new LinkedHashMap<String,
	// String>();
	// profile = (LinkedHashMap<String, String>)
	// JsonUtil.JsonToObject(str,profile.getClass());
	// ModeInfo result = ToModeInfo(profile) ;
	// modeList.add(result);
	// return result;
	// }
	// else
	// {
	// return null;
	// }
	// }
	// //添加场景明细
	// public ModeEquipment AddAppProfileDetail(final int profileID, final int
	// NetGateCode, final int Type, final int EquimentCode, final String
	// EquimentValue,final int EqIndex)
	// {
	// ModeEquipment mresult1 = new ModeEquipment() ;
	// String data =
	// "{\"ProfileID\":"+profileID+",\"NetGateCode\":"+NetGateCode+",\"Type\":"+Type+",\"EquimentCode\":"+EquimentCode+",\"EquimentValue\":\""+EquimentValue+"\",\"EqIndex\":"+EqIndex+"}";
	// String order =
	// orderControlJson(data,OrderFlag.AppProfileAddDetail,Global.USERNAME,Global.PASSWORD);
	// String str = sendDataPost(order, Service_URL);
	// if (str.trim().length() > 0)
	// {
	//
	// LinkedHashMap<String, String> prodetail = new LinkedHashMap<String,
	// String>();
	// prodetail = (LinkedHashMap<String, String>)
	// JsonUtil.JsonToObject(str,prodetail.getClass());
	// mresult1 = ToModeEquipment(prodetail);
	// meqList.add(mresult1);
	//
	// }
	// else
	// {
	// mresult1 = null;
	// }
	//
	// return mresult1;
	//
	// }
	// //使用场景
	// public boolean UsingAppProfile(int profileID,String UserId,String
	// Password)
	// {
	// String data = "{\"ID\":"+profileID+"}";
	// String order = orderControlJson(data,OrderFlag.AppProfileUsing, UserId,
	// Password);
	// String str = sendDataPost(order, Service_URL).toUpperCase();
	// if (str == "TRUE")
	// return true;
	// else
	// {
	// try {
	// throw new Exception(str);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return false;
	// }
	// }
	// }
	// //根据场景id获取场景对应设备
	// public List<ModeEquipment> getEquipmentByModeId(int modeId)
	// {
	// List<ModeEquipment> result = new ArrayList<ModeEquipment>();
	// for(int i=0;i<meqList.size();i++)
	// {
	// if(meqList.get(i).getModeId()==modeId)
	// {
	// result.add(meqList.get(i));
	// }
	// }
	// return result;
	// }
	// //根据NetGateCode、EqIndex、EquimentCode获取设备
	// public Equipment getEquipmentByNee(int NetGateCode,int EqIndex,int
	// EquipmentCode)
	// {
	// Equipment equip = null;
	// for(int i=0;i<eqList.size();i++)
	// {
	// Equipment equipm = eqList.get(i);
	// if(equipm.getNcode()==NetGateCode&&equipm.getNindex()==EqIndex&&equipm.getMachinID()==EquipmentCode)
	// {
	// equip=equipm;
	// break;
	// }
	// }
	// return equip;
	// }
	// //场景信息修改
	// public boolean UpdateAppProfile(int profileID, String number, String
	// name, boolean enable, String startDate, String endDate,
	// String startTime, String endTime, String description,String user,String
	// pwd)
	// {
	//
	// String data =
	// "{\"ID\":"+profileID+",\"Number\":\""+number+"\",\"Name\":\""+name+"\",\"Enable\":"+enable+",\"StartDate\":\""+startDate+"\",\"EndDate\":\""+endDate+"\",\"StartTime\":\""+startTime+"\",\"EndTime\":\""+endTime+"\",\"Description\":\""+description+"\"}";
	// String order =
	// orderControlJson(data,OrderFlag.AppProfileUpdate,user,pwd);
	// String str = sendDataPost(order, Service_URL).toUpperCase();
	// if (str.equals("TRUE"))
	// return true;
	// else
	// {
	// try
	// {
	// throw new Exception(str);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return false;
	// }
	// }
	// }
	// //根据modeID获取ModeEquipment
	// public List<ModeEquipment> getMeqByModeId(int modeId)
	// {
	// List<ModeEquipment> result = new ArrayList<ModeEquipment>();
	// for(int i=0;i<meqList.size();i++)
	// {
	// ModeEquipment meq = meqList.get(i);
	// if(meq.getModeId()==modeId)
	// {
	// result.add(meq);
	// }
	// }
	// return result;
	// }
	//
	// private boolean deleteresult = false;
	// //删除场景明细
	// public boolean DeleteAppProfileDetail(final int profileID, final int
	// detailID,String user,String pwd)
	// {
	//
	// String data = "{\"ProfileID\":"+profileID+",\"ID\":"+detailID+"}";
	// String order =
	// orderControlJson(data,OrderFlag.AppProfileDeleteDetail,user,pwd);
	// String str = sendDataPost(order, Service_URL).toUpperCase();
	// if (str.equals("TRUE"))
	// {
	// deleteresult = true;
	// for(int i=0;i<meqList.size();i++)
	// {
	// ModeEquipment meq = meqList.get(i);
	// if(meq.getModeId()==profileID)
	// {
	// meqList.remove(i);
	// }
	// }
	// }
	// if(deleteresult==true)
	// return true;
	// else
	// {
	// try {
	// throw new Exception();
	// } catch (Exception e) {
	// e.printStackTrace();
	// return false;
	// }
	// }
	// }
	// //根据ID获取场景
	// public ModeInfo getModeById(int modeId)
	// {
	// ModeInfo result = new ModeInfo();
	// for(int i=0;i<modeList.size();i++)
	// {
	// if(modeList.get(i).getId()==modeId)
	// {
	// result = modeList.get(i);
	// }
	// }
	// return result;
	// }
	// //删除场景信息
	// public boolean DeleteAppProfile(int profileID,String UserId,String
	// Password)
	// {
	// for(int i=0;i<meqList.size();i++)
	// {
	// ModeEquipment meq = meqList.get(i);
	// if(meq.getModeId()==profileID)
	// {
	// boolean ok = DeleteAppProfileDetailA(profileID, meq.getId(), UserId,
	// Password);
	// if(ok==true)
	// {
	// meqList.remove(i);
	// }
	// }
	// }
	// String data = "{\"ID\":"+profileID+"}";
	// String order = orderControlJson(data,OrderFlag.AppProfileDelete, UserId,
	// Password);
	// String str = sendDataPost(order, Service_URL).toUpperCase();
	// if (str.equals("TRUE") )
	// {
	// for(int i=0;i<modeList.size();i++)
	// {
	// ModeInfo mode = modeList.get(i);
	// if(mode.getId()==profileID)
	// {
	// modeList.remove(i);
	// }
	// }
	// return true;
	// }
	// else
	// {
	// try {
	// throw new Exception(str);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return false;
	// }
	// }
	// }
	//
	// public boolean DeleteAppProfileDetailA(final int profileID, final int
	// detailID,String UserId,String Password)
	// {
	// String data = "{\"ProfileID\":"+profileID+",\"ID\":"+detailID+"}";
	// String order = orderControlJson(data,OrderFlag.AppProfileDeleteDetail,
	// UserId, Password);
	// String str = sendDataPost(order, Service_URL).toUpperCase();
	// if (str.equals("TRUE"))
	// return true;
	// else
	// {
	// try {
	// throw new Exception(str);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return false;
	// }
	// }
	// }
	// private List<LinkedHashMap<String, String>> linkmaps;
	// //ModeInfo对象转换
	// public ModeInfo ToModeInfo(LinkedHashMap<String, String> lmap)
	// {
	// ModeInfo minfo = new ModeInfo();
	// Iterator it = lmap.entrySet().iterator();
	// while(it.hasNext())
	// {
	// Map.Entry entity = (Entry) it.next();
	// if(entity.getKey().equals("EndDate"))
	// {
	// minfo.setEndDate(entity.getValue().toString());
	// }else if(entity.getKey().equals("EndTime"))
	// {
	// minfo.setEndTime(entity.getValue().toString());
	// }else if(entity.getKey().equals("ID"))
	// {
	// minfo.setCenterCode((int)(Float.parseFloat(entity.getValue().toString())));
	// minfo.setId((int)(Float.parseFloat(entity.getValue().toString())));
	// }else if(entity.getKey().equals("Name"))
	// {
	// minfo.setModeName(entity.getValue().toString());
	// }else if(entity.getKey().equals("Number"))
	// {
	// minfo.setModeCode(entity.getValue().toString());
	// }else if(entity.getKey().equals("StartDate"))
	// {
	// //minfo.setStratDate(entity.getValue().toString());
	// }else if(entity.getKey().equals("StartTime"))
	// {
	// minfo.setStartTime(entity.getValue().toString());
	// }else if(entity.getKey().equals("Detail"))
	// {
	// linkmaps = new ArrayList<LinkedHashMap<String,String>>();
	// linkmaps = (List<LinkedHashMap<String,
	// String>>)JsonUtil.JsonToObject(entity.getValue().toString(),linkmaps.getClass());
	// getAllModeEquipment();
	// }else if(entity.getKey().equals("Enable"))
	// {
	// if(Boolean.parseBoolean(entity.getValue().toString())==true)
	// {
	// minfo.setStratDate("TRUE");
	// }else
	// {
	// minfo.setStratDate("FALSE");
	// }
	// }
	// else
	// {
	// continue;
	// }
	//
	// }
	// return minfo;
	// }
	// //获取单个场景设备
	// public List<ModeEquipment> getAllModeEquipment()
	// {
	// if(meqList==null)
	// {
	// meqList = new ArrayList<ModeEquipment>();
	// }
	// for(int i=0;i<linkmaps.size();i++)
	// {
	// ModeEquipment mequ = new ModeEquipment();
	// mequ = ToModeEquipment(linkmaps.get(i));
	// meqList.add(mequ);
	// }
	//
	// return meqList;
	// }
	//
	// //ModeEquipment对象转换
	// public static ModeEquipment ToModeEquipment(LinkedHashMap<String, String>
	// lmap)
	// {
	// ModeEquipment mequip = new ModeEquipment();
	// Iterator it = lmap.entrySet().iterator();
	// while(it.hasNext())
	// {
	// Map.Entry entity = (Entry) it.next();
	// if(entity.getKey().equals("ID"))
	// {
	// mequip.setId((int)(Float.parseFloat(entity.getValue().toString())));
	// }else if(entity.getKey().equals("EquimentCode"))
	// {
	// mequip.setEquipmentId((int)(Float.parseFloat(entity.getValue().toString())));
	// }else if(entity.getKey().equals("EquimentValue"))
	// {
	// mequip.setValue(entity.getValue().toString());
	// }else if(entity.getKey().equals("ProfileID"))
	// {
	// mequip.setModeId((int)(Float.parseFloat(entity.getValue().toString())));
	// }else if(entity.getKey().equals("NetGateCode"))
	// {
	// mequip.setNetGateCode((int)(Float.parseFloat(entity.getValue().toString())));
	// }else if(entity.getKey().equals("Type"))
	// {
	// mequip.setType((int)(Float.parseFloat(entity.getValue().toString())));
	// }else if(entity.getKey().equals("EqIndex"))
	// {
	// mequip.setEqIndex((int)(Float.parseFloat(entity.getValue().toString())));
	// }
	// }
	// return mequip;
	// }
	// //合成控制命令

	// //netgate对象转换

	//
	// //equipment对象转换

	// 互联网发送函数

	// //获取设备的最新状态
	// @SuppressWarnings("unchecked")
	// public List<Equipment> GetLastEquipmentStatus(int tick,String
	// userId,String Password)
	// {
	// String data = "{\"Tick\":"+tick+"}";
	// String order =
	// orderControlJson(data,OrderFlag.EquipmentRefreshAllInfo,userId,Password);
	// String str = sendDataPost(order, Service_URL);
	// if (str.trim().length() > 0)
	// {
	// List<Equipment> result = new ArrayList<Equipment>();
	// List<LinkedHashMap<String, String>> lstnew = new
	// ArrayList<LinkedHashMap<String,String>>();
	// lstnew = (List<LinkedHashMap<String,
	// String>>)JsonUtil.JsonToObject(str,lstnew.getClass());
	// for(int i=0;i<lstnew.size();i++)
	// {
	// Equipment equipment = new Equipment();
	// equipment = ToEquipment(lstnew.get(i));
	// RefreshEquipmentById(equipment.getId(),equipment.getbOnline(),equipment.getTimeTick(),equipment.getSvalue());
	// result.add(equipment);
	// }
	//
	// return result;
	// }
	// else
	// {
	// return null;
	// }
	// }
	//
	// //根据ID刷新

	// private boolean bReturn = false;
	// private String pwdEditstr ="";
	// //修改密碼
	// public String PasswordEdit( final String value,final String user,final
	// String pwd)
	// {
	// bReturn = false;
	// Runnable run = new Runnable() {
	//
	// @Override
	// public void run()
	// {
	// String data ="{\"Value\":\""+value+"\"}";
	// String order = orderControlJson(data,
	// OrderFlag.UserPasswordEdit,user,pwd);
	// pwdEditstr = sendDataPost(order, Service_URL).toUpperCase();
	// /*if (pwdEditstr.equals("TRUE"))
	// {
	// Password = value;
	// }*/
	// bReturn = true;
	// }
	// };
	// Thread thread = new Thread(run);
	// thread.start();
	//
	// while( !bReturn)
	// {
	// try {
	// Thread.sleep(100);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// //e.printStackTrace();
	// }
	// }
	// return pwdEditstr;
	//
	// }

}
