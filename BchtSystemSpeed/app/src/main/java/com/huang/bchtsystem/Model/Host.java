package com.huang.bchtsystem.Model;

import com.sun.jna.Structure;

import java.io.Serializable;

/**
 * Created by admin on 2017/5/12.
 */

public class  Host   implements Serializable {
    private boolean startServerSetting;
    private String serverIP;
    private int port;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path ;

    public boolean isStartServerSetting() {
        return startServerSetting;
    }

    public void setStartServerSetting(boolean startServerSetting) {
        this.startServerSetting = startServerSetting;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getPort() {
        return port;
    }

    public String getPortString()
    {
        return "" + port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
