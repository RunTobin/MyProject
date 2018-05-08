package com.huang.bchtsystem.Model;

import java.io.Serializable;

/**
 * Created by admin on 2017/5/11.
 */

public class Dvr  implements Serializable {
    private String ipAddr;
    private String subNetMask;
    private String gateWay;
    private String dns;
    private String dns1;

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getSubNetMask() {
        return subNetMask;
    }

    public void setSubNetMask(String subNetMask) {
        this.subNetMask = subNetMask;
    }

    public String getGateWay() {
        return gateWay;
    }

    public void setGateWay(String gateWay) {
        this.gateWay = gateWay;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getDns1() {
        return dns1;
    }

    public void setDns1(String dns1) {
        this.dns1 = dns1;
    }



}
