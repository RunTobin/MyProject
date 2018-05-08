package com.huang.bchtsystem.rader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

/**
 * Created by admin on 2017/6/19.
 */

public class Devices {

    private String[]        devices;
    private String[]        baudrate;
    private String[]        checkBit;
    private String[]        stopBits;
    private String[]        dataBits;

    public  Devices()
    {
        baudrate = new String[]{
                "9600",
                "19200",
                "38400",
                "57600",
                "115200",
                "230400",
                "460800",
                "500000",
                "576000",
                "921600",
                "1000000",
                "1152000",
                "1500000",
                "2000000",
                "2500000",
                "3000000",
                "3500000",
                "4000000",
        };

        checkBit =  new String[]{"None","Even","Odd"};
        stopBits =  new String[]{"1.0", "2.0", "1.5"};
        dataBits =  new String[]{"5","6","7","8"};
    }

    public String[] getDevices()
    {
        if( devices != null )
            return devices;

        final File file = new File("/dev");
        FilenameFilter fileFilter = new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name)
            {
                boolean succ = dir.equals(file) && name.startsWith("ttyS");
                return succ;
            }
        };

        devices = file.list(fileFilter);
        if( null == devices )
            devices = new String[]{"No Device"};
        Arrays.sort(devices);
        return devices;
    }

    public static String getAbsolutePath(String rpath)
    {
        if(rpath.startsWith("/"))
            return rpath;
        else
        {
            rpath = "/dev/" + rpath;
        }
        return rpath;
    }

    public String[] getBaudrate()
    {
        return baudrate;
    }

    public String[] getCheckBit()
    {
        return checkBit;
    }

    public String[] getStopBits()
    {
        return stopBits;
    }

    public String[] getDataBits()
    {
        return dataBits;
    }
}
