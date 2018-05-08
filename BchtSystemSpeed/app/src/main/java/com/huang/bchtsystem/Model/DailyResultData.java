package com.huang.bchtsystem.Model;

/**
 * Created by admin on 2017/8/14.
 */

public class DailyResultData {


    public String getStrLogTime() {
        return strLogTime;
    }

    public void setStrLogTime(String strLogTime) {
        this.strLogTime = strLogTime;
    }

    public String getDwMajorType() {
        return dwMajorType;
    }

    public void setDwMajorType(String dwMajorType) {
        this.dwMajorType = dwMajorType;
    }

    public String getDwMinorType() {
        return dwMinorType;
    }

    public void setDwMinorType(String dwMinorType) {
        this.dwMinorType = dwMinorType;
    }

    public String getStruRemoteHostAddr() {
        return struRemoteHostAddr;
    }

    public void setStruRemoteHostAddr(String struRemoteHostAddr) {
        this.struRemoteHostAddr = struRemoteHostAddr;
    }
    public String getsNetUser() {
        return sNetUser;
    }
    public void setsNetUser(String sNetUser) {
        this.sNetUser = sNetUser;
    }

    public String strLogTime;//日志时间
    public String dwMajorType;//报警主类型
    public String dwMinorType;//报警次类型
    public String struRemoteHostAddr;//远程主机地址
    public String sNetUser;//网络操作用户名
    public String num; //日志序号


}
