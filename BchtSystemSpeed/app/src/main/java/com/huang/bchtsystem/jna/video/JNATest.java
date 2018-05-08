package com.huang.bchtsystem.jna.video;

import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import com.hikvision.netsdk.HCNetSDK;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.jna.HCNetSDKByJNA;
import com.huang.bchtsystem.jna.HCNetSDKByJNA.NET_DVR_ALARMER;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;
import com.hikvision.netsdk.INT_PTR;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class JNATest {

	public static JNATest jnaTest = new JNATest();
	public int m_lAlarmID = -1;
	public int m_lAlarmIDV41 = -1;
	public int m_lLongConfig = -1;
	public int m_lDisplay = -1;

	public void Test_GetSDKVersion()
	{
		System.out.println("get sdk version by jna:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetSDKVersion());
	}

	public void Test_TimeCfg(int iUserID)
	{
		HCNetSDKByJNA.NET_DVR_TIME time = new HCNetSDKByJNA.NET_DVR_TIME();
		Pointer lpPicConfig = time.getPointer();
		IntByReference pInt = new IntByReference(0);
		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDKByJNA.NET_DVR_GET_TIMECFG, 0, lpPicConfig, time.size(), pInt);
		time.read();
		if(bRet)
		{
			System.out.println("dvr time:" + time.toString());
		}

		time.dwHour = time.dwHour + 1;
		time.write();
		bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDKByJNA.NET_DVR_SET_TIMECFG, 0, lpPicConfig, time.size());
		if(!bRet)
		{
			System.out.println("HCNetSDKByJNA.NET_DVR_SET_TIMECFG succ");
		}
	}

	public void Test_MultiStreamCompression(int iUserID)
	{
		HCNetSDKByJNA.NET_DVR_MULTI_STREAM_COMPRESSIONCFG_COND[] compressCond = (HCNetSDKByJNA.NET_DVR_MULTI_STREAM_COMPRESSIONCFG_COND[])new HCNetSDKByJNA.NET_DVR_MULTI_STREAM_COMPRESSIONCFG_COND().toArray(2);
		HCNetSDKByJNA.NET_DVR_MULTI_STREAM_COMPRESSIONCFG[]	compress = (HCNetSDKByJNA.NET_DVR_MULTI_STREAM_COMPRESSIONCFG[])new HCNetSDKByJNA.NET_DVR_MULTI_STREAM_COMPRESSIONCFG().toArray(2);
		Pointer lpCond = compressCond[0].getPointer();
		Pointer lpParam = compress[0].getPointer();

		HCNetSDKByJNA.INT_ARRAY pInt = new HCNetSDKByJNA.INT_ARRAY(2);

		pInt.iValue[0] = -1;
		pInt.iValue[1] = -1;
		System.out.println("NET_DVR_GetDeviceConfig status:" + pInt.iValue[0] + pInt.iValue[1]);

		compressCond[0].dwSize = compressCond[0].size();
		compressCond[0].dwStreamType = 0;
		compressCond[0].struStreamInfo.dwSize = compressCond[0].struStreamInfo.size();
		compressCond[0].struStreamInfo.dwChannel = 1;
		compressCond[0].write();

		compressCond[1].dwSize = compressCond[1].size();
		compressCond[1].dwStreamType = 1;
		compressCond[1].struStreamInfo.dwSize = compressCond[1].struStreamInfo.size();
		compressCond[1].struStreamInfo.dwChannel = 1;
		compressCond[1].write();
		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDeviceConfig(iUserID, HCNetSDKByJNA.NET_DVR_GET_MULTI_STREAM_COMPRESSIONCFG, 2,
				lpCond, compressCond[0].size() * 2, pInt.getPointer(), lpParam, compress[0].size() * 2);
		compress[0].read();
		compress[1].read();
		pInt.read();
		if(!bRet)
		{
			System.out.println("NET_DVR_GET_MULTI_STREAM_COMPRESSIONCFG failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GetDeviceConfig status:" + pInt.iValue[0] + pInt.iValue[1]);
		}
	}

	public static class FMSGCallBack implements HCNetSDKByJNA.FMSGCallBack
	{

		@Override
		public void invoke(int lCommand, NET_DVR_ALARMER pAlarmer,
						   Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {

			// TODO Auto-generated method stub
			System.out.println("alarm type:" + lCommand);
			if(lCommand == HCNetSDKByJNA.COMM_ALARM_V30)
			{
				HCNetSDKByJNA.NET_DVR_ALARMINFO_V30	struAlarmInfo = new HCNetSDKByJNA.NET_DVR_ALARMINFO_V30(pAlarmInfo);
				struAlarmInfo.read();
				System.out.println("COMM_ALARM_V30 alarm type:" + struAlarmInfo.dwAlarmType);
			}
			else if(lCommand == HCNetSDKByJNA.COMM_ALARM_V40)
			{
				HCNetSDKByJNA.NET_DVR_ALARMINFO_V40	struAlarmInfo = new HCNetSDKByJNA.NET_DVR_ALARMINFO_V40(pAlarmInfo);
				struAlarmInfo.read();
				System.out.println("COMM_ALARM_V40 alarm type:" + struAlarmInfo.struAlarmFixedHeader.dwAlarmType);
			}
			else if(lCommand == HCNetSDKByJNA.COMM_UPLOAD_PLATE_RESULT)
			{
				HCNetSDKByJNA.NET_DVR_PLATE_RESULT	struAlarmInfo = new HCNetSDKByJNA.NET_DVR_PLATE_RESULT(pAlarmInfo);
				struAlarmInfo.read();

				try {
					SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
					String   date   =   sDateFormat.format(new   java.util.Date());
					FileOutputStream file = new FileOutputStream("/mnt/sdcard/" + date + ".bmp");
					file.write(struAlarmInfo.pBuffer1.getPointer().getByteArray(0, struAlarmInfo.dwPicLen), 0, struAlarmInfo.dwPicLen);
					file.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("COMM_UPLOAD_PLATE_RESULT license:" + CommonMethod.toValidString(new String(struAlarmInfo.struPlateInfo.sLicense)));
			}
			else if(lCommand == HCNetSDKByJNA.COMM_ITS_PLATE_RESULT)
			{
				HCNetSDKByJNA.NET_ITS_PLATE_RESULT	struAlarmInfo = new HCNetSDKByJNA.NET_ITS_PLATE_RESULT(pAlarmInfo);
				struAlarmInfo.read();
				System.out.println("COMM_ITS_PLATE_RESULT license:" + CommonMethod.toValidString(new String(struAlarmInfo.struPlateInfo.sLicense)));
			}
			else if(lCommand == HCNetSDKByJNA.COMM_ALARM_RULE)
			{
				HCNetSDKByJNA.NET_VCA_RULE_ALARM	struAlarmInfo = new HCNetSDKByJNA.NET_VCA_RULE_ALARM(pAlarmInfo);
				struAlarmInfo.read();
				if(struAlarmInfo.struRuleInfo.wEventTypeEx == HCNetSDKByJNA.ENUM_VCA_EVENT_EXIT_AREA)
				{
					HCNetSDKByJNA.NET_VCA_AREA	struExit = new HCNetSDKByJNA.NET_VCA_AREA(struAlarmInfo.struRuleInfo.uEventParam.getPointer());
					struExit.read();
				}

				System.out.println("COMM_ALARM_RULE rule name:" + CommonMethod.toValidString(new String(struAlarmInfo.struRuleInfo.byRuleName)));
			}

			else if(lCommand == HCNetSDKByJNA.COMM_VEHICLE_CONTROL_ALARM)
			{
				HCNetSDKByJNA.NET_DVR_VEHICLE_CONTROL_ALARM	struAlarmInfo = new HCNetSDKByJNA.NET_DVR_VEHICLE_CONTROL_ALARM(pAlarmInfo);
				struAlarmInfo.read();
				System.out.println("NET_DVR_VEHICLE_CONTROL_ALARM license:" + struAlarmInfo.byListType + "byPlateType:" + struAlarmInfo.byPlateType +
						"sLicense:" +  CommonMethod.toValidString(new String(struAlarmInfo.sLicense)));
			}

		}
	}
	private static final HCNetSDKByJNA.FMSGCallBack fMSFCallBack = new FMSGCallBack();

	public void Test_Alarm(int iUserID)
	{
		if(m_lAlarmID < 0)
		{
			Pointer pUser = null;
			if(!HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRMessageCallBack_V30(fMSFCallBack, pUser))
			{
				System.out.println("NET_DVR_SetDVRMessageCallBack_V30 failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			}
			m_lAlarmID = HCNetSDKJNAInstance.getInstance().NET_DVR_SetupAlarmChan_V30(iUserID);
		}
		else
		{
			HCNetSDKJNAInstance.getInstance().NET_DVR_CloseAlarmChan_V30(m_lAlarmID);
			m_lAlarmID = -1;
		}
	}

	public void Test_Alarm_V41(int iUserID)
	{
		if(m_lAlarmIDV41 < 0)
		{
			Pointer pUser = null;
			boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRMessageCallBack_V30(fMSFCallBack, pUser);

			if(!ret)
			{
				System.out.println("NET_DVR_SetDVRMessageCallBack_V30 failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			}

			HCNetSDKByJNA.NET_DVR_SETUPALARM_PARAM 	arlarmParam = new HCNetSDKByJNA.NET_DVR_SETUPALARM_PARAM();
			arlarmParam.dwSize = arlarmParam.size();
			arlarmParam.byRetDevInfoVersion = 1;
			arlarmParam.write();
			m_lAlarmIDV41 = HCNetSDKJNAInstance.getInstance().NET_DVR_SetupAlarmChan_V41(iUserID, arlarmParam.getPointer());
		}
		else
		{
			HCNetSDKJNAInstance.getInstance().NET_DVR_CloseAlarmChan_V30(m_lAlarmIDV41);
			m_lAlarmIDV41 = -1;
		}
	}

	public void Test_LoiteringDetection(int iUserID)
	{
//		System.out.println("Test_LoiteringDetection channel:" + iChan);

		HCNetSDKByJNA.INT_ARRAY ptrInt = new HCNetSDKByJNA.INT_ARRAY(1);
		ptrInt.iValue[0] = 1;
		ptrInt.write();

		HCNetSDKByJNA.NET_DVR_LOITERING_DETECTION struParam = new HCNetSDKByJNA.NET_DVR_LOITERING_DETECTION();
		HCNetSDKByJNA.BYTE_ARRAY ptrByte = new HCNetSDKByJNA.BYTE_ARRAY(1024);


		HCNetSDKByJNA.NET_DVR_STD_CONFIG stdCfg = new HCNetSDKByJNA.NET_DVR_STD_CONFIG();
		stdCfg.lpCondBuffer = ptrInt.getPointer();
		stdCfg.dwCondSize = ptrInt.size();
		stdCfg.lpOutBuffer = struParam.getPointer();
		stdCfg.dwOutSize = struParam.size();
		stdCfg.lpStatusBuffer = ptrByte.getPointer();
		stdCfg.dwStatusSize = ptrByte.size();
		stdCfg.write();
		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetSTDConfig(iUserID, HCNetSDKByJNA.NET_DVR_GET_LOITERING_DETECTION, stdCfg.getPointer());
		stdCfg.read();
		struParam.read();
		ptrByte.read();
		if(!bRet)
		{
			System.out.println("NET_DVR_GET_LOITERING_DETECTION failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError() + CommonMethod.toValidString(new String(ptrByte.byValue)));
		}
		else
		{
			System.out.println("NET_DVR_GET_LOITERING_DETECTION succ");
		}
	}

	public static class FRemoteConfigCallBack implements HCNetSDKByJNA.fRemoteConfigCallback
	{

		@Override
		public void invoke(int dwType, Pointer lpBuffer, int dwBufLen,
						   Pointer pUserData) {
			// TODO Auto-generated method stub
			int dwVolume = lpBuffer.getInt(0);

			System.out.println("NET_DVR_START_GET_INPUTVOLUME volume:" + dwVolume);
		}

	}

	private static final HCNetSDKByJNA.fRemoteConfigCallback fCallback = new	FRemoteConfigCallBack();
	public void Test_GetInputVolume(int iUserID)
	{
		if(m_lLongConfig == -1)
		{
			HCNetSDKByJNA.NET_DVR_INPUTVOLUME struInputVolume = new HCNetSDKByJNA.NET_DVR_INPUTVOLUME();
			struInputVolume.dwSize = struInputVolume.size();
			struInputVolume.byAudioInputChan = 1;
			struInputVolume.write();

			m_lLongConfig = HCNetSDKJNAInstance.getInstance().NET_DVR_StartRemoteConfig(iUserID, HCNetSDKByJNA.NET_DVR_START_GET_INPUTVOLUME, struInputVolume.getPointer(), struInputVolume.size(), fCallback, null);
		}
		else
		{
			HCNetSDKJNAInstance.getInstance().NET_DVR_StopRemoteConfig(m_lLongConfig);
			m_lLongConfig = -1;
		}
	}

	private int m_lCardCfg = -1;
	public static class fCardCfgCallBack implements HCNetSDKByJNA.fRemoteConfigCallback
	{

		@Override
		public void invoke(int dwType, Pointer lpBuffer, int dwBufLen,
						   Pointer pUserData) {
			// TODO Auto-generated method stub
			if(dwType == HCNetSDKByJNA.NET_SDK_CALLBACK_TYPE_DATA)
			{
				HCNetSDKByJNA.NET_DVR_CARD_CFG struCardCfg = new HCNetSDKByJNA.NET_DVR_CARD_CFG(lpBuffer);
				struCardCfg.read();
				System.out.println("card no:" + CommonMethod.toValidString(new String(struCardCfg.byCardNo)));
			}
		}
	}

	private static final HCNetSDKByJNA.fRemoteConfigCallback fCardCallback = new	fCardCfgCallBack();
	public void Test_GetCardCfg(int iUserID)
	{
		if(m_lCardCfg == -1)
		{
			HCNetSDKByJNA.NET_DVR_CARD_CFG_COND struCardCond = new HCNetSDKByJNA.NET_DVR_CARD_CFG_COND();
			struCardCond.dwSize = struCardCond.size();
			struCardCond.dwCardNum = 0xffffffff;
			struCardCond.write();

			m_lCardCfg = HCNetSDKJNAInstance.getInstance().NET_DVR_StartRemoteConfig(iUserID, HCNetSDKByJNA.NET_DVR_GET_CARD_CFG, struCardCond.getPointer(), struCardCond.size(), fCardCallback, null);
			if(m_lCardCfg >= 0)
			{
				System.out.println("NET_DVR_StartRemoteConfig NET_DVR_GET_CARD_CFG succ");
			}
			else
			{
				System.out.println("NET_DVR_StartRemoteConfig NET_DVR_GET_CARD_CFG failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			}
		}
		else
		{
			HCNetSDKJNAInstance.getInstance().NET_DVR_StopRemoteConfig(m_lCardCfg);
			m_lCardCfg = -1;
		}
	}

	public void Test_LED_Area(int iUserID)
	{
		HCNetSDKByJNA.NET_DVR_STD_CONFIG stdCfg = new HCNetSDKByJNA.NET_DVR_STD_CONFIG();

		HCNetSDKByJNA.NET_DVR_LED_AREA_COND struCond = new HCNetSDKByJNA.NET_DVR_LED_AREA_COND();
		struCond.dwLEDAreaNo = 1;
		struCond.dwSize = struCond.size();
		struCond.dwVideoWallNo = 1;
		HCNetSDKByJNA.BYTE_ARRAY ptrByte = new HCNetSDKByJNA.BYTE_ARRAY(1024);
		struCond.write();

		HCNetSDKByJNA.NET_DVR_LED_AREA_INFO_LIST struInfoList = new HCNetSDKByJNA.NET_DVR_LED_AREA_INFO_LIST();
		struInfoList.dwSize = struInfoList.size();
		struInfoList.dwLEDAreaNum =1;
		HCNetSDKByJNA.NET_DVR_LED_AREA_INFO [] astruInfo = (HCNetSDKByJNA.NET_DVR_LED_AREA_INFO[])new HCNetSDKByJNA.NET_DVR_LED_AREA_INFO().toArray(128);
		astruInfo[0].dwSize = astruInfo[0].size();
		struInfoList.lpstruBuffer = null;//astruInfo[0].getPointer();
		struInfoList.dwBufferSize = 0;//astruInfo[0].size()*128;

		stdCfg.lpCondBuffer = struCond.getPointer();
		stdCfg.dwCondSize = struCond.size();
		stdCfg.lpInBuffer = null;
		stdCfg.dwInSize = 0;
		stdCfg.lpStatusBuffer = ptrByte.getPointer();
		stdCfg.dwStatusSize = ptrByte.size();

		stdCfg.lpOutBuffer = struInfoList.getPointer();
		stdCfg.dwOutSize = struInfoList.size();
		struInfoList.write();
		stdCfg.write();
		//astruInfo[0].write();
		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetSTDConfig(iUserID, HCNetSDKByJNA.NET_DVR_GET_LED_AREA_INFO_LIST, stdCfg.getPointer());
		stdCfg.read();
		struInfoList.read();
		ptrByte.read();
		if(!bRet)
		{
			System.out.println("NET_DVR_GET_LED_AREA_INFO_LIST failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError() + CommonMethod.toValidString(new String(ptrByte.byValue)));
			return;
		}
		else
		{
			System.out.println("NET_DVR_GET_LED_AREA_INFO_LIST succ,num="+struInfoList.dwLEDAreaNum);
		}


	}

	public void TEST_Passive_Decode(int iUserID)
	{
		int iChan = 0x01010001;
		HCNetSDKByJNA.NET_DVR_MATRIX_PASSIVEMODE struPasMode = new HCNetSDKByJNA.NET_DVR_MATRIX_PASSIVEMODE();
		struPasMode.byStreamType=1;
		struPasMode.wPassivePort = 9000;
		struPasMode.wTransProtol=0;
		struPasMode.write();
		int iHandle = HCNetSDKJNAInstance.getInstance().NET_DVR_MatrixStartPassiveDecode(iUserID,iChan,struPasMode.getPointer());
		if(iHandle < 0)
		{
			System.out.println("NET_DVR_MATRIX_PASSIVEMODE failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			return;
		}
		else
		{
			System.out.println("NET_DVR_MATRIX_PASSIVEMODE succ");
		}

		HCNetSDKByJNA.BYTE_ARRAY ptrByte = new HCNetSDKByJNA.BYTE_ARRAY(30*1024);
		ptrByte.write();
		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_MatrixSendData(iHandle, ptrByte.getPointer(), ptrByte.size());
		if(!bRet)
		{
			System.out.println("NET_DVR_MatrixSendData failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError() + CommonMethod.toValidString(new String(ptrByte.byValue)));
			return;
		}
		else
		{
			System.out.println("NET_DVR_MatrixSendData succ");
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int iRet = HCNetSDKJNAInstance.getInstance().NET_DVR_MatrixGetPassiveDecodeStatus(iHandle);
		if(iRet < 0)
		{
			System.out.println("NET_DVR_MatrixGetPassiveDecodeStatus fail="+HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_MatrixGetPassiveDecodeStatus succ,iRet = "+iRet);
		}


		bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_MatrixStopPassiveDecode(iHandle);
		if(!bRet)
		{
			System.out.println("NET_DVR_MatrixStopPassiveDecode failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			return;
		}
		else
		{
			System.out.println("NET_DVR_MatrixStopPassiveDecode succ");
		}
	}

	public void Test_Login_V40()
	{
		HCNetSDKByJNA.NET_DVR_USER_LOGIN_INFO loginInfo = new HCNetSDKByJNA.NET_DVR_USER_LOGIN_INFO();
		System.arraycopy("10.17.132.118".getBytes(), 0, loginInfo.sDeviceAddress, 0, "10.17.132.118".length());
		System.arraycopy("admin".getBytes(), 0, loginInfo.sUserName, 0, "admin".length());
		System.arraycopy("hik12345".getBytes(), 0, loginInfo.sPassword, 0, "hik12345".length());
		loginInfo.wPort = (short)65534;
		HCNetSDKByJNA.NET_DVR_DEVICEINFO_V40	deviceInfo = new HCNetSDKByJNA.NET_DVR_DEVICEINFO_V40();
		loginInfo.write();
		int lUserID = HCNetSDKJNAInstance.getInstance().NET_DVR_Login_V40(loginInfo.getPointer(), deviceInfo.getPointer());
		if(lUserID < 0)
		{
			System.out.println("NET_DVR_Login_V40 failed with:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			deviceInfo.read();
			System.out.println("NET_DVR_Login_V40 succ with:" + lUserID);
		}

		if(!HCNetSDKJNAInstance.getInstance().NET_DVR_Logout(lUserID))
		{
			System.out.println("NET_DVR_Logout failed with:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_Logout succ");
		}
	}

	public void Test_Other(int iUserID)
	{
		HCNetSDKByJNA.NET_DVR_ALARMHOST_OTHER_STATUS_V50 struCfg = new HCNetSDKByJNA.NET_DVR_ALARMHOST_OTHER_STATUS_V50();
		Pointer lpPicConfig = struCfg.getPointer();
		IntByReference pInt = new IntByReference(0);
		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDKByJNA.NET_DVR_GET_ALARMHOST_OTHER_STATUS_V50, 0, lpPicConfig, struCfg.size(), pInt);
		struCfg.read();
		if(!bRet)
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_GET_ALARMHOST_OTHER_STATUS_V50) failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			return;
		}
		else
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_GET_ALARMHOST_OTHER_STATUS_V50) succ");
		}
	}

	public void Test_AlarmHostSubsystemCfg(int iUserID)
	{
		HCNetSDKByJNA.NET_DVR_ALARMSUBSYSTEMPARAM struCfg = new HCNetSDKByJNA.NET_DVR_ALARMSUBSYSTEMPARAM();
		Pointer lpPicConfig = struCfg.getPointer();
		IntByReference pInt = new IntByReference(0);
		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDKByJNA.NET_DVR_GET_ALARMHOSTSUBSYSTEM_CFG, 0, lpPicConfig, struCfg.size(), pInt);
		struCfg.read();
		if(!bRet)
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_GET_ALARMHOSTSUBSYSTEM_CFG) failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			return;
		}
		else
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_GET_ALARMHOSTSUBSYSTEM_CFG) succ");
		}

		struCfg.wEnterDelay += 1;
		struCfg.write();
		bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDKByJNA.NET_DVR_SET_ALARMHOSTSUBSYSTEM_CFG, 1, lpPicConfig, struCfg.size());
		if(!bRet)
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_SET_ALARMHOSTSUBSYSTEM_CFG) failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			return;
		}
		else
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_SET_ALARMHOSTSUBSYSTEM_CFG) succ");
		}
	}

	public void Test_STDXMLConfig(int iUserID)
	{
		HCNetSDKByJNA.NET_DVR_XML_CONFIG_INPUT	inputCfg = new HCNetSDKByJNA.NET_DVR_XML_CONFIG_INPUT();
		inputCfg.dwSize = inputCfg.size();
		String str = "GET /ISAPI/VCS/terminals\r\n";
		HCNetSDKByJNA.BYTE_ARRAY byteArr = new  HCNetSDKByJNA.BYTE_ARRAY(str.length());
		System.arraycopy(str.getBytes(), 0, byteArr.byValue, 0, str.length());
		byteArr.write();
		inputCfg.lpRequestUrl = byteArr.getPointer();
		inputCfg.dwRequestUrlLen = str.length();
		inputCfg.write();

		HCNetSDKByJNA.NET_DVR_XML_CONFIG_OUTPUT outputCfg = new HCNetSDKByJNA.NET_DVR_XML_CONFIG_OUTPUT();
		HCNetSDKByJNA.BYTE_ARRAY outBuf = new HCNetSDKByJNA.BYTE_ARRAY(100*1024);
		outBuf.write();
		outputCfg.dwSize = outputCfg.size();
		outputCfg.lpOutBuffer = outBuf.getPointer();
		outputCfg.dwOutBufferSize = 100*1024;
		HCNetSDKByJNA.BYTE_ARRAY statusBuf = new HCNetSDKByJNA.BYTE_ARRAY(1024);
		outputCfg.lpStatusBuffer = statusBuf.getPointer();
		outputCfg.dwStatusSize = 1024;
		statusBuf.write();
		outputCfg.write();

		if(!HCNetSDKJNAInstance.getInstance().NET_DVR_STDXMLConfig(iUserID, inputCfg, outputCfg))
		{
			System.out.println("NET_DVR_STDXMLConfig failed with:" + iUserID + " "+ HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_STDXMLConfig succ");
			outputCfg.read();
			System.out.println(outputCfg.lpOutBuffer.getString(0));
		}
	}

	public int Test_CreateOpenEzvizUser()
	{
		HCNetSDKByJNA.NET_DVR_OPEN_EZVIZ_USER_LOGIN_INFO loginInfo = new HCNetSDKByJNA.NET_DVR_OPEN_EZVIZ_USER_LOGIN_INFO();

		System.arraycopy("pbdev.ys7.com".getBytes(), 0, loginInfo.sEzvizServerAddress, 0, "pbdev.ys7.com".length());
		System.arraycopy("at.7z3qhjhi1k0kpk88am6wm2p00x1xc397-7p0c49v5ms-1sn768m-gocfbh7v1".getBytes(), 0, loginInfo.sAccessToken, 0, "at.7z3qhjhi1k0kpk88am6wm2p00x1xc397-7p0c49v5ms-1sn768m-gocfbh7v1".length());
		System.arraycopy("626969511".getBytes(), 0, loginInfo.sDeviceID, 0, "626969511".length());

		System.arraycopy("/api/device/transmission".getBytes(), 0, loginInfo.sUrl, 0, "/api/device/transmission".length());
		System.arraycopy("2".getBytes(), 0, loginInfo.sClientType, 0, "2".length());
		System.arraycopy("226f102a99ad0e078504d380b9ddf760".getBytes(), 0, loginInfo.sFeatureCode, 0, "226f102a99ad0e078504d380b9ddf760".length());
		System.arraycopy("5.0.1".getBytes(), 0, loginInfo.sOsVersion, 0, "5.0.1".length());
		System.arraycopy("UNKNOWN".getBytes(), 0, loginInfo.sNetType, 0, "UNKNOWN".length());
		System.arraycopy("v.5.1.5.30".getBytes(), 0, loginInfo.sSdkVersion, 0, "v.5.1.5.30".length());
		System.arraycopy("com.hik.visualintercom".getBytes(), 0, loginInfo.sAppID, 0, "com.hik.visualintercom".length());
		loginInfo.wPort = (short)443;

		HCNetSDKByJNA.NET_DVR_DEVICEINFO_V40	deviceInfo = new HCNetSDKByJNA.NET_DVR_DEVICEINFO_V40();
		loginInfo.write();

		int lUserID = HCNetSDKJNAInstance.getInstance().NET_DVR_CreateOpenEzvizUser(loginInfo.getPointer(), deviceInfo.getPointer());
		if(lUserID < 0)
		{
			System.out.println("NET_DVR_CreateOpenEzvizUser failed with:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			deviceInfo.read();
			System.out.println("NET_DVR_CreateOpenEzvizUser succ with:" + lUserID);
		}

		return lUserID;

		/*
		if(!HCNetSDKJNAInstance.getInstance().NET_DVR_DeleteOpenEzvizUser(lUserID))
		{
			System.out.println("NET_DVR_DeleteOpenEzvizUser failed with:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_DeleteOpenEzvizUser succ");
		}
		*/
	}

	public void Test_EzvizAccessCfg(int iUserID)
	{
		HCNetSDKByJNA.NET_DVR_EZVIZ_ACCESS_CFG struCfg = new HCNetSDKByJNA.NET_DVR_EZVIZ_ACCESS_CFG();
		Pointer lpPicConfig = struCfg.getPointer();
		IntByReference pInt = new IntByReference(0);
		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDKByJNA.NET_DVR_GET_EZVIZ_ACCESS_CFG , 0, lpPicConfig, struCfg.size(), pInt);
		struCfg.read();
		if(!bRet)
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_GET_ALARMHOSTSUBSYSTEM_CFG) failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			return;
		}
		else
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_GET_ALARMHOSTSUBSYSTEM_CFG) succ");
		}

//       struCfg.write();
		bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDKByJNA.NET_DVR_SET_EZVIZ_ACCESS_CFG, 1, lpPicConfig, struCfg.size());
		if(!bRet)
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_SET_ALARMHOSTSUBSYSTEM_CFG) failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			return;
		}
		else
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_SET_ALARMHOSTSUBSYSTEM_CFG) succ");
		}
	}


	public void TestDisplayNo(int iLoginId)
	{
		HCNetSDKByJNA.NET_DVR_DISPLAYCFG struCfg = new HCNetSDKByJNA.NET_DVR_DISPLAYCFG();
		struCfg.dwSize = struCfg.size();
		IntByReference pInt = new IntByReference(0);
		if(HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(iLoginId, HCNetSDKByJNA.NET_DVR_GET_VIDEOWALLDISPLAYNO,0, struCfg.getPointer(), struCfg.size(), pInt))
		{
			System.out.println("NET_DVR_GET_VIDEOWALLDISPLAYNO success");
		}
		else {
			System.out.println("NET_DVR_GET_VIDEOWALLDISPLAYNO fail,error="+HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}

	}


	public static class FDisplayNoCallBack implements HCNetSDKByJNA.fRemoteConfigCallback
	{

		@Override
		public void invoke(int dwType, Pointer lpBuffer, int dwBufLen,
						   Pointer pUserData) {
			// TODO Auto-generated method stub
			if(dwType == HCNetSDKByJNA.NET_SDK_CALLBACK_TYPE_DATA)
			{
				HCNetSDKByJNA.NET_DVR_DISPLAYPARAM struDisplayNo = new HCNetSDKByJNA.NET_DVR_DISPLAYPARAM(lpBuffer);
				struDisplayNo.read();
				System.out.println("DisplayNo=" + struDisplayNo.dwDisplayNo+",Type="+struDisplayNo.byDispChanType);
			}
		}

	}

	private static final HCNetSDKByJNA.fRemoteConfigCallback fDisplayCB = new	FDisplayNoCallBack();
	public void TestDisplayNoLongCfg(int iLogId)
	{
		if(m_lDisplay == -1)
		{
			HCNetSDKByJNA.INT_ARRAY pInt = new HCNetSDKByJNA.INT_ARRAY(1);
			pInt.iValue[0]= 0xffffffff;
			pInt.write();
			INT_PTR poiInt_PTR = new INT_PTR();
			m_lDisplay = HCNetSDKJNAInstance.getInstance().NET_DVR_StartRemoteConfig(iLogId, HCNetSDKByJNA.NET_SDK_GET_VIDEOWALLDISPLAYNO, pInt.getPointer(), 4, fDisplayCB, null);
			if(m_lDisplay ==-1)
			{
				System.out.println("NET_DVR_StartRemoteConfig failed,error code= "+HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			}
		}
		else
		{
			HCNetSDKJNAInstance.getInstance().NET_DVR_StopRemoteConfig(m_lDisplay);
			m_lDisplay = -1;
		}
	}

	public static void TEST_Config( int iUserID)
	{
//		jnaTest.Test_Login_V40();
//		jnaTest.Test_Other(iUserID);
//		jnaTest.Test_AlarmHostSubsystemCfg(iUserID);
//		jnaTest.Test_GetSDKVersion();
//		jnaTest.Test_TimeCfg(iUserID);
//		jnaTest.Test_LoiteringDetection(iUserID, iChan);
//		jnaTest.Test_MultiStreamCompression(iUserID);
//		jnaTest.Test_Alarm(iUserID);
//  	jnaTest.Test_Alarm_V41(iUserID);
//		jnaTest.Test_GetInputVolume(iUserID);
//		jnaTest.Test_GetCardCfg(iUserID);
//		jnaTest.Test_LED_Area(iUserID);
//		jnaTest.TEST_Passive_Decode(iUserID);
//		jnaTest.Test_LED_Area(iUserID);
//		jnaTest.TEST_Passive_Decode(iUserID);
//		jnaTest.Test_STDXMLConfig(iUserID);
		//jnaTest.Test_EzvizAccessCfg(iUserID);
		//jnaTest.TestDisplayNo(iUserID);
//		jnaTest.TestDisplayNoLongCfg(iUserID);
		//	jnaTest.TEST_GetCurTriggerMode(iUserID);
		//jnaTest.TEST_RS485AccessInfo(iUserID, iChan);
		jnaTest.GetCanshu(iUserID);
		jnaTest.getHvt(iUserID);
//		jnaTest.Test_ShowString(iUserID, iChan);
		//	jnaTest.Test_saveLog();
	}

	public static int TEST_EzvizLogin()
	{
		int iloginID = -1;
		iloginID = jnaTest.Test_CreateOpenEzvizUser();
		return iloginID;
	}

	public void TEST_GetCurTriggerMode(int iUserID)
	{
		HCNetSDKByJNA.NET_DVR_CURTRIGGERMODE struCurTriggerMode = new HCNetSDKByJNA.NET_DVR_CURTRIGGERMODE();
		Pointer lpCurTriggerMode = struCurTriggerMode.getPointer();
		IntByReference pInt = new IntByReference(0);
		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDKByJNA.NET_DVR_GET_CURTRIGGERMODE, 0, lpCurTriggerMode, struCurTriggerMode.size(), pInt);
		struCurTriggerMode.read();
		if(!bRet)
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_GET_CURTRIGGERMODE) failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			return;
		}
		else
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_GET_CURTRIGGERMODE) succ! Current mode is " + struCurTriggerMode.dwTriggerType);
		}
	}

	public void TEST_RS485AccessInfo(int iUserID, int iStartChan)
	{

		HCNetSDKByJNA.NET_ITC_RS485_ACCESS_INFO_COND[] accessInfoCond = (HCNetSDKByJNA.NET_ITC_RS485_ACCESS_INFO_COND[])new HCNetSDKByJNA.NET_ITC_RS485_ACCESS_INFO_COND().toArray(2);
		HCNetSDKByJNA.NET_ITC_RS485_ACCESS_CFG[] accessInfoCfg = (HCNetSDKByJNA.NET_ITC_RS485_ACCESS_CFG[])new HCNetSDKByJNA.NET_ITC_RS485_ACCESS_CFG().toArray(2);
		Pointer lpCond = accessInfoCond[0].getPointer();
		Pointer lpParam = accessInfoCfg[0].getPointer();
		HCNetSDKByJNA.INT_ARRAY pInt = new HCNetSDKByJNA.INT_ARRAY(2);

		pInt.iValue[0] = -1;
		pInt.iValue[1] = -1;
		System.out.println("NET_DVR_GetDeviceConfig status:" + pInt.iValue[0] + pInt.iValue[1]);

		accessInfoCond[0].dwSize = accessInfoCond[0].size();
		accessInfoCond[0].dwTriggerModeType = 8;
		accessInfoCond[0].dwChannel = iStartChan;
		accessInfoCond[0].byAssociateRS485No = 1;
		accessInfoCond[0].write();

		accessInfoCond[1].dwSize = accessInfoCond[1].size();
		accessInfoCond[1].dwTriggerModeType = 8;
		accessInfoCond[1].dwChannel = iStartChan;
		accessInfoCond[1].byAssociateRS485No = 2;
		accessInfoCond[1].write();

		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDeviceConfig(iUserID, HCNetSDKByJNA.NET_ITC_GET_RS485_ACCESSINFO, 2,
				lpCond, accessInfoCond[0].size() * 2, pInt.getPointer(), lpParam, accessInfoCfg[0].size() * 2);
		accessInfoCond[0].read();
		accessInfoCond[1].read();
		pInt.read();
		if(!bRet)
		{
			System.out.println("NET_ITC_GET_RS485_ACCESSINFO failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_GetDeviceConfig NET_ITC_GET_RS485_ACCESSINFO status:" + accessInfoCfg.toString());
		}

		pInt.iValue[0] = -1;
		pInt.iValue[1] = -1;
		System.out.println("NET_DVR_SetDeviceConfig status:" + pInt.iValue[0] + pInt.iValue[1]);
		bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDeviceConfig(iUserID, HCNetSDKByJNA.NET_ITC_SET_RS485_ACCESSINFO, 2,
				lpCond, accessInfoCond[0].size() * 2, pInt.getPointer(), lpParam, accessInfoCfg[0].size() * 2);
		accessInfoCond[0].read();
		accessInfoCond[1].read();
		pInt.read();
		if(!bRet)
		{
			System.out.println("NET_ITC_SET_RS485_ACCESSINFO failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_SetDeviceConfig NET_ITC_SET_RS485_ACCESSINFO status:" + pInt.iValue[0] + pInt.iValue[1]);
		}
	}
	public static HCNetSDKByJNA.NET_ITC_RSTRIGGERCFG struTriggerCfg ;
	private void GetCanshu(int iUserID)
	{
		struTriggerCfg = new HCNetSDKByJNA.NET_ITC_RSTRIGGERCFG();
		Pointer lpTriggerConfig = struTriggerCfg.getPointer();
		struTriggerCfg.struTriggerParam.uTriggerParam.setType("struPostRadar");
		struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struLane[0].struPlateRecog[0].uRegion.setType("struPolygon");
		IntByReference pInt = new IntByReference(0);
		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDKByJNA.NET_ITC_GET_TRIGGERCFG, 0x8, lpTriggerConfig, struTriggerCfg.size(), pInt);
		struTriggerCfg.read();
		if(!bRet)
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_ITC_GET_TRIGGERCFG) failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			return;
		}
		else
		{
			struTriggerCfg.struTriggerParam.uTriggerParam.read();
			System.out.println("NET_DVR_GetDVRConfig(NET_ITC_GET_TRIGGERCFG) succ! 识别类型");
		}
	}
	public static HCNetSDKByJNA.NET_ITC_TRIGGERCFG struHvt;
	private void getHvt(int uid)
	{
		struHvt = new HCNetSDKByJNA.NET_ITC_TRIGGERCFG();
		Pointer lpTriggerConfig = struHvt.getPointer();
		struHvt.struTriggerParam.uTriggerParam.setType("struHvtV50");
		IntByReference pInt = new IntByReference(0);
		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(uid, HCNetSDKByJNA.NET_ITC_GET_TRIGGERCFG, 0x20, lpTriggerConfig, struHvt.size(), pInt);
		struHvt.read();
		if(!bRet)
		{
			System.out.println("NET_ITC_GET_TRIGGERCFG failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			struHvt.struTriggerParam.uTriggerParam.struHvtV50.read();
			System.out.println("NET_ITC_GET_TRIGGERCFG succ! 车道数==="+struHvt.struTriggerParam.uTriggerParam.struHvtV50.byLaneNum);
		}
	}
	public void Test_TriggerCfg(int iUserID)
	{
		HCNetSDKByJNA.NET_ITC_RSTRIGGERCFG struTriggerCfg = new HCNetSDKByJNA.NET_ITC_RSTRIGGERCFG();
		Pointer lpTriggerConfig = struTriggerCfg.getPointer();
		struTriggerCfg.struTriggerParam.uTriggerParam.setType("struPostRadar");
		IntByReference pInt = new IntByReference(0);
		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDKByJNA.NET_ITC_GET_TRIGGERCFG, 0, lpTriggerConfig, struTriggerCfg.size(), pInt);
		struTriggerCfg.read();
		if(!bRet)
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_ITC_GET_TRIGGERCFG) failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			return;
		}
		else
		{
			struTriggerCfg.struTriggerParam.uTriggerParam.read();
			System.out.println("NET_DVR_GetDVRConfig(NET_ITC_GET_TRIGGERCFG) succ! Enable: " + struTriggerCfg.struTriggerParam.byEnable + " ;dwTriggerType is " + struTriggerCfg.struTriggerParam.dwTriggerType + "; byRadarType is" + struTriggerCfg.struTriggerParam.uTriggerParam.struPostRadar.struRadar.byRadarType);
		}

		struTriggerCfg.write();
		bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG, 0x8, lpTriggerConfig, struTriggerCfg.size());
		if(!bRet)
		{
			System.out.println("HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("HCNetSDKByJNA.NET_ITC_SET_TRIGGERCFG succ!");
		}
	}
	public void Test_ShowString(int iUserID, int iStartChan)
	{
		HCNetSDKByJNA.NET_DVR_SHOWSTRING_V30 struShowString = new HCNetSDKByJNA.NET_DVR_SHOWSTRING_V30();
		Pointer lpShowStringConfig = struShowString.getPointer();
		IntByReference pInt = new IntByReference(0);
		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDKByJNA.NET_DVR_GET_SHOWSTRING_V30, iStartChan, lpShowStringConfig, struShowString.size(), pInt);
		struShowString.read();
		if(!bRet)
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_GET_SHOWSTRING_V30) failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			return;
		}
		else
		{
			try {
				String name = new String(struShowString.struStringInfo[0].sString,"GBK");

				System.out.println("NET_DVR_GetDVRConfig(NET_DVR_GET_SHOWSTRING_V30) succ! wShowString : " + struShowString.struStringInfo[0].wShowString
						+";name:" + CommonMethod.bytes2HexString(struShowString.struStringInfo[0].sString));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}


		}

		struShowString.write();
		bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDVRConfig(iUserID, HCNetSDKByJNA.NET_DVR_SET_SHOWSTRING_V30, iStartChan, lpShowStringConfig, struShowString.size());
		if(!bRet)
		{
			System.out.println("NET_DVR_GetDVRConfig(NET_DVR_SET_SHOWSTRING_V30) failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("HCNetSDKByJNA.NET_DVR_SET_SHOWSTRING_V30 succ");
		}
	}

	public void Test_FindPicture(int iUserID, int iStartChan)
	{
		int size = 0;
		HCNetSDKByJNA.NET_DVR_FIND_PICTURE_PARAM struPicParam = new HCNetSDKByJNA.NET_DVR_FIND_PICTURE_PARAM();
		struPicParam.dwSize = struPicParam.size();
		struPicParam.lChannel = iStartChan;
		struPicParam.dwIllegalType = 0x2000;
		struPicParam.struStartTime.dwDay = 26;
		struPicParam.struStartTime.dwHour = 0;
		struPicParam.struStartTime.dwMinute = 0;
		struPicParam.struStartTime.dwSecond = 0;
		struPicParam.struStartTime.dwYear = 2017;
		struPicParam.struStartTime.dwMonth = 10;

		struPicParam.struStopTime.dwDay = 16;
		struPicParam.struStopTime.dwHour = 0;
		struPicParam.struStopTime.dwMinute = 0;
		struPicParam.struStopTime.dwSecond = 0;
		struPicParam.struStopTime.dwYear = 2018;
		struPicParam.struStopTime.dwMonth = 1;
		struPicParam.write();
		Pointer lpPicParam = struPicParam.getPointer();


		int handle = HCNetSDKJNAInstance.getInstance().NET_DVR_FindPicture(iUserID, lpPicParam);

		if(handle < 0)
		{
			System.out.println("NET_DVR_FindPicture failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
			return;
		}
		else
		{
			System.out.println("NET_DVR_FindPicturesucc! return handle: " + handle);
		}

		HCNetSDKByJNA.NET_DVR_FIND_PICTURE_V40 struPicV40 = new HCNetSDKByJNA.NET_DVR_FIND_PICTURE_V40();
		Pointer lpstruPicV40 = struPicV40.getPointer();

		int iFindPicRet = 0;

		while(iFindPicRet != -1)
		{
			iFindPicRet = HCNetSDKJNAInstance.getInstance().NET_DVR_FindNextPicture(handle, lpstruPicV40);
			if(iFindPicRet < 0)
			{
				System.out.println("NET_DVR_FindNextPicture_V40 failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
				break;
			}
			struPicV40.read();
			if (iFindPicRet == HCNetSDKByJNA.NET_DVR_FILE_SUCCESS)
			{
				System.out.println("~~~~~Find Pic" + CommonMethod.toValidString(new String(struPicV40.sFileName)));
				System.out.println("~~~~~File Size" + struPicV40.dwFileSize);

//				IntByReference pInt = new IntByReference(0);
//				HCNetSDKByJNA.BYTE_ARRAY ptrFileName = new HCNetSDKByJNA.BYTE_ARRAY(64);
//				HCNetSDKByJNA.BYTE_ARRAY ptrByte = new HCNetSDKByJNA.BYTE_ARRAY(1048576);
//				System.arraycopy(struPicV40.sFileName, 0, ptrByte.byValue, 0, 64);
//				ptrFileName.write();
//				if(!HCNetSDKJNAInstance.getInstance().NET_DVR_GetPicture_V30(iUserID, ptrFileName.getPointer(), ptrByte.getPointer(), 1048576, pInt))
//				{
//					System.out.println("NET_DVR_GetPicture_V30 faile:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
//				}
//				else
//				{
//					ptrByte.read();
//					System.out.println("NET_DVR_GetPicture_V30 success");
//				}
				size ++;
				Log.i("size",""+size);
			}
			else if (HCNetSDKByJNA.NET_DVR_FILE_NOFIND == iFindPicRet)
			{
				System.out.println("No file found");
				break;
			}
			else if (HCNetSDKByJNA.NET_DVR_NOMOREFILE == iFindPicRet)
			{
				System.out.println("All files are listed");
				break;
			}
			else if (HCNetSDKByJNA.NET_DVR_FILE_EXCEPTION == iFindPicRet)
			{
				System.out.println("Exception in searching");
				break;
			}
			else if (HCNetSDKByJNA.NET_DVR_ISFINDING == iFindPicRet)
			{
				System.out.println("NET_DVR_ISFINDING");
			}
		}

		if(!HCNetSDKJNAInstance.getInstance().NET_DVR_CloseFindPicture(handle))
		{
			System.out.println("NET_DVR_CloseFindPicture failed:" + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
		}
		else
		{
			System.out.println("NET_DVR_CloseFindPicture succ!");
		}

	}
	public  void Test_saveLog()
	{
		int bLogEnable = 1;
		String strLogDir = "C:\\SdkLog\\";

		boolean bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_SetLogToFile(bLogEnable, strLogDir,true);
		if(! bRet )
		{
			Log.e("JNATEST", "NET_DVR_SetLogToFile failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
		}
	}
}



