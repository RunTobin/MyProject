package com.huang.bchtsystem.DataAccess;

import android.util.Log;

import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_NETCFG_V30;
import com.huang.bchtsystem.Model.ServerSetting;
import com.huang.bchtsystem.Model.Dvr;

import com.huang.bchtsystem.jna.HCNetSDKByJNA;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;

import com.huang.bchtsystem.jna.video.CommonMethod;
import com.huang.bchtsystem.Util.Util;

/**
 * Created by admin on 2017/5/11.
 */

public class DvrConfig {
    private static final String TAG = "DvrConfig";

    private Dvr dvr = new Dvr();
    private ServerSetting serverSetting;
    private ServerSetting ftpServerSetting;
    private String str = null;

    public Dvr getDvr() {
        return dvr;
    }

    public void setDvr(Dvr dvr) {
        this.dvr = dvr;
    }

    public ServerSetting getServerSetting() {
        return serverSetting;
    }

    public void setServerSetting(ServerSetting serverSetting) {
        this.serverSetting = serverSetting;
    }

    public ServerSetting getFtpServerSetting() {
        return ftpServerSetting;
    }

    public void setFtpServerSetting(ServerSetting ftpServerSetting) {
        this.ftpServerSetting = ftpServerSetting;
    }

    void create()
    {
        if( dvr == null)
            dvr = new Dvr();

        if(serverSetting == null)
        {
            serverSetting = new ServerSetting();
            serverSetting.create();
        }

        if(ftpServerSetting == null)
        {
            ftpServerSetting = new ServerSetting();
            ftpServerSetting.create();
        }
    }
    //基础设置
    NET_DVR_NETCFG_V30 dvrConfig = null;
    public void  baseConfig(int uid,int channel){
        dvrConfig = new NET_DVR_NETCFG_V30();
        boolean ret = HCNetSDK.getInstance().NET_DVR_GetDVRConfig(uid,HCNetSDK.NET_DVR_GET_NETCFG_V30,channel,dvrConfig);
        if (!ret){
            Log.e("NET_DVR_GET_NETCFG_V30",""+ HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }else {
            Log.e("NET_DVR_GET_NETCFG_V30","SuccessFul!");
        }
    }
    public void getDvrConfig(int uid,int channel) {
        baseConfig(uid,channel);
        create();
        dvr.setIpAddr( CommonMethod.bytes2HexString(dvrConfig.struEtherNet[0].struDVRIP.sIpV4));
        dvr.setGateWay( CommonMethod.bytes2HexString(dvrConfig.struGatewayIpAddr.sIpV4));
        dvr.setSubNetMask( CommonMethod.bytes2HexString(dvrConfig.struEtherNet[0].struDVRIPMask.sIpV4));
        dvr.setDns( CommonMethod.bytes2HexString(dvrConfig.struDnsServer1IpAddr.sIpV4));
        dvr.setDns1( CommonMethod.bytes2HexString(dvrConfig.struDnsServer2IpAddr.sIpV4));
    }

    public void setDvrConfig(int uid  ,String IpAddress ,String NetMask,String GateWay,String Dns,String Dns1,int channel){
        baseConfig(uid,channel);
        dvrConfig.struEtherNet[0].struDVRIP.sIpV4 = CommonMethod.string2ASCII(IpAddress,16) ;
        dvrConfig.struEtherNet[0].struDVRIPMask.sIpV4 = CommonMethod.string2ASCII(NetMask,16) ;
        dvrConfig.struGatewayIpAddr.sIpV4 = CommonMethod.string2ASCII(GateWay,16) ;
        dvrConfig.struDnsServer1IpAddr.sIpV4 =  CommonMethod.string2ASCII(Dns,16) ;
        dvrConfig.struDnsServer2IpAddr.sIpV4 =  CommonMethod.string2ASCII(Dns1,16) ;

        boolean ret = HCNetSDK.getInstance().NET_DVR_SetDVRConfig(uid,HCNetSDK.NET_DVR_SET_NETCFG_V30,channel,dvrConfig);
        if (!ret){
            Log.e("NET_DVR_SET_NETCFG_V30",""+ HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }else {
            Log.e("NET_DVR_SET_NETCFG_V30","SuccessFul!");
        }
    }

    //服务器设置
    HCNetSDKByJNA.NET_DVR_CLOUDSTORAGE_COND  cond = null;
    HCNetSDKByJNA.NET_DVR_CLOUDSTORAGE_CFG  cloundCfg = null;
    public void getServer(int uid, int channel){
        cond = new HCNetSDKByJNA.NET_DVR_CLOUDSTORAGE_COND();
        cloundCfg = new HCNetSDKByJNA.NET_DVR_CLOUDSTORAGE_CFG();
        pInt = new HCNetSDKByJNA.INT_ARRAY(1);
        pInt.iValue[0] = -1;
        cond.dwSize = cond.size();
        cond.dwChannel = channel ;
        cond.write();
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDeviceConfig(uid, HCNetSDKByJNA.NET_DVR_GET_CLOUDSTORAGE_CFG, 1,
                         cond.getPointer(), cond.size(),pInt.getPointer(), cloundCfg.getPointer(), cloundCfg.size());
        cloundCfg.read();
        pInt.read();
        if(!ret)
        {
            Log.e(TAG, "NET_DVR_GET_CLOUDSTORAGE_CFG, error = " + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }
    }
    public void getServerConfig(int uid, int channel){
        getServer(uid , channel);
        create();
        serverSetting.getHost().setStartServerSetting( 1 ==cloundCfg.byEnable);
        serverSetting.getHost().setPort(cloundCfg.wPort);
        serverSetting.getHost().setServerIP(  Util.toValidString(new String(cloundCfg.struIP.sIpV4)) );
        serverSetting.getUserData().setAccount( Util.toValidString(new String(cloundCfg.szUser)) );
        serverSetting.getUserData().setUserPwd( Util.toValidString(new String(cloundCfg.szPassword)) );
    }
    public void setServerConfig(int uid ,int channel ,int i){
        getServer(uid , channel);
        cloundCfg.byEnable = (byte)i;
        cloundCfg.write();
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDeviceConfig(uid, HCNetSDKByJNA.NET_DVR_SET_CLOUDSTORAGE_CFG, 1,
                cond.getPointer(), cond.size(),pInt.getPointer(), cloundCfg.getPointer(), cloundCfg.size());
        if (!ret){
            Log.e(TAG, "NET_DVR_SET_CLOUDSTORAGE_CFG, error = " + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }else {
            System.out.println("NET_DVR_SET_CLOUDSTORAGE_CFG,succ");
        }
    }
    public void setServerConfig(int uid ,int channel ,String str ,int j){
        getServer(uid , channel);
        if (j == 5){
            cloundCfg.struIP.sIpV4 = CommonMethod.Stringtobyte2(str) ;
        }else if ( j == 6){
            cloundCfg.wPort = Short.parseShort(str);
        }else if (j == 7){
            if (!cloundCfg.szUser.equals("") || cloundCfg.szUser !=null){
                cloundCfg.szUser = new byte[48];
                SetServerNull(uid);
            }
            cloundCfg.szUser = CommonMethod.Stringtobyte2(str) ;
        }else if (j == 8){
            if (!cloundCfg.szPassword.equals("") || cloundCfg.szPassword !=null){
                cloundCfg.szPassword = new byte[48];
                SetServerNull(uid);
            }
            cloundCfg.szPassword = CommonMethod.Stringtobyte2(str) ;
        }
        cloundCfg.write();
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDeviceConfig(uid, HCNetSDKByJNA.NET_DVR_SET_CLOUDSTORAGE_CFG, 1,
                cond.getPointer(), cond.size(),pInt.getPointer(), cloundCfg.getPointer(), cloundCfg.size());
        if (!ret){
            Log.e(TAG, "NET_DVR_SET_CLOUDSTORAGE_CFG, error = " + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }else {
            System.out.println("NET_DVR_SET_CLOUDSTORAGE_CFG,succ");
        }
    }
    public void SetServerNull(int uid){
        cloundCfg.write();
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDeviceConfig(uid, HCNetSDKByJNA.NET_DVR_SET_CLOUDSTORAGE_CFG, 1,
                cond.getPointer(), cond.size(),pInt.getPointer(), cloundCfg.getPointer(), cloundCfg.size());
        if (!ret){
            Log.e(TAG, "NET_DVR_SET_CLOUDSTORAGE_CFG, error = " + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }else {
            System.out.println("NET_DVR_SET_CLOUDSTORAGE_CFG,succ");
        }
    }
    //FTP设置
    HCNetSDKByJNA.NET_ITC_FTP_TYPE_COND ftpcond = null;
    HCNetSDKByJNA.NET_ITC_FTP_CFG  ftpCfg = null ;
    HCNetSDKByJNA.INT_ARRAY pInt = null ;
    public void ServerConfig(int uid ,int channel) {
        ftpcond = new HCNetSDKByJNA.NET_ITC_FTP_TYPE_COND();
        ftpCfg = new HCNetSDKByJNA.NET_ITC_FTP_CFG();
        pInt = new HCNetSDKByJNA.INT_ARRAY(1);
        pInt.iValue[0] = -1;
        ftpCfg.unionServer.setType("struAddrIP");
        ftpcond .dwChannel = channel ;
        ftpcond.write();
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_GetDeviceConfig(uid, HCNetSDKByJNA.NET_ITC_GET_FTPCFG, 1,
                ftpcond.getPointer(), ftpcond.size(),pInt.getPointer(), ftpCfg.getPointer(), ftpCfg.size());
        ftpCfg.read();
        pInt.read();
        if (!ret){
            Log.e(TAG, "NET_ITC_GET_FTPCFG, error = " + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }
    }
    public void getFtpServerConfig(int uid ,int channel){
        ServerConfig(uid,channel);
        create();
        if (ftpCfg.byDirLevel == 0){
            str = "保存在根目录";
        }else {
            str = "";
        }
        ftpServerSetting.getHost().setStartServerSetting( 1 == ftpCfg.byEnable);
        ftpServerSetting.getHost().setServerIP( Util.toValidString(new String(ftpCfg.unionServer.struAddrIP.struIp.sIpV4)));
        ftpServerSetting.getHost().setPort( ftpCfg.wFTPPort );
        ftpServerSetting.getUserData().setAccount( Util.toValidString(new String(ftpCfg.szUserName)) );
        ftpServerSetting.getUserData().setUserPwd( Util.toValidString(new String(ftpCfg.szPassWORD)) );
        ftpServerSetting.getHost().setPath(str);
    }
    public void setFtpServerConfig(int uid ,int channel ,int i){
        ServerConfig(uid,channel);
        ftpCfg.byEnable = (byte)i ;
        ftpCfg.write();
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDeviceConfig(uid, HCNetSDKByJNA.NET_ITC_SET_FTPCFG, 1,
                ftpcond.getPointer(), ftpcond.size(),pInt.getPointer(), ftpCfg.getPointer(), ftpCfg.size());
        if (!ret){
            Log.e(TAG, "NET_ITC_SET_FTPCFG, error = " + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }else {
            System.out.println("NET_ITC_SET_FTPCFG,succ");
        }
    }
    public void setFtpServerConfig(int uid ,int channel ,String str ,int j){
        ServerConfig(uid,channel);
        if ( j == 1){
            ftpCfg.unionServer.struAddrIP.struIp.sIpV4 = CommonMethod.Stringtobyte2(str) ;
        }else if (j == 2){
            ftpCfg.wFTPPort = Short.parseShort(str) ;
        }else if ( j == 3){
          if (!ftpCfg.szUserName.equals("") || ftpCfg.szUserName !=null){
              ftpCfg.szUserName = new byte[32];
              setFtpNull(uid);
          }
            ftpCfg.szUserName = CommonMethod.Stringtobyte2(str) ;
        }else if ( j == 4){
            if (!ftpCfg.szPassWORD.equals("") || ftpCfg.szPassWORD !=null){
                ftpCfg.szPassWORD = new byte[16];
                setFtpNull(uid);
            }
            ftpCfg.szPassWORD = CommonMethod.Stringtobyte2(str) ;
        }
        ftpCfg.write();
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDeviceConfig(uid, HCNetSDKByJNA.NET_ITC_SET_FTPCFG, 1,
                ftpcond.getPointer(), ftpcond.size(),pInt.getPointer(), ftpCfg.getPointer(), ftpCfg.size());
        if (!ret){
            Log.e(TAG, "NET_ITC_SET_FTPCFG, error = " + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }else {
            System.out.println("NET_ITC_SET_FTPCFG,succ");
        }
    }
    public void setFtpNull(int uid){
        ftpCfg.write();
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_SetDeviceConfig(uid, HCNetSDKByJNA.NET_ITC_SET_FTPCFG, 1,
                ftpcond.getPointer(), ftpcond.size(),pInt.getPointer(), ftpCfg.getPointer(), ftpCfg.size());
        if (!ret){
            Log.e(TAG, "NET_ITC_SET_FTPCFG, error = " + HCNetSDKJNAInstance.getInstance().NET_DVR_GetLastError());
        }else {
            System.out.println("NET_ITC_SET_FTPCFG,succ");
        }
    }

    public void getAllConfig(int uid, int channel)
    {
        getDvrConfig(uid,channel);
        getFtpServerConfig(uid,channel);
        getServerConfig(uid, channel);
    }


}


