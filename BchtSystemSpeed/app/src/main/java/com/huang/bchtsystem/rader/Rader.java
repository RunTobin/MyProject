package com.huang.bchtsystem.rader;

import java.io.FileDescriptor;

/**
 * final boolean succ = Rader.getInstance().open(Devices.getAbsolutePath(port),Integer.decode(baudrate).intValue());
 * if( succ )
 * {
 *  Rader.getInstance().getSpeedResponse();
 *  int err = Rader.getInstance().setParameter((short) 8,(byte)45,(byte) 01);

 *  Rader.getInstance().close();
 * }
 */

public class Rader {
    private native int raderOpen(String path, int baudrate);
    private native boolean raderClose();

    private native int raderSetParameter( short maxSpeed, byte angle, byte workingMode);
    private native int raderSetParameter2(  RaderCmd.ParameterSetCmd parameterSetCmd );
    private native int raderGetParameter( );
    private native int raderGetSpeedResponse();
    private native int raderEnterMeasure();
    private native int raderExitMeasure();

    private RaderCmd.SpeedResponse speedResponse;
    private RaderCmd.ParameterGetResponse parameterGetResponse;


    private FileDescriptor fd;
    private String curPath;
    private SpeedCallbackHandler speedCallbackHandler;
    private static Rader rader;

    public interface SpeedCallbackHandler
    {
        public void onSpeedResponse(short speed, char lane, char direction);
    }

    private void speedResponseCallback(short speed, char lane, char direction)
    {
        if( null != speedCallbackHandler && null != speedResponse )
        {
            speedCallbackHandler.onSpeedResponse(speedResponse.getSpeed(), speedResponse.getLane(), speedResponse.getDirection());
        }
    }

    public boolean open(String path, int baudrate)
    {
        if( !path.equals(curPath) && isOpened())
        {
            close();
        }
        boolean succ = false;
        if( null == fd )
        {
            int err = raderOpen(path,baudrate);
            succ = 0 == err && fd != null && fd.valid();
        }
        if( succ )
        {
            curPath = path;
        }
        return succ;
    }

    public void close()
    {
        if( null != fd )
        {
            raderClose();
            fd = null;
        }
        curPath = "";
    }

    public void enterMeasure()
    {
        if( isOpened() )
            raderEnterMeasure();
    }
    public void exitMeasure()
    {
        if( isOpened() )
            raderExitMeasure();
    }

    public RaderCmd.SpeedResponse getSpeedResponse()
    {
        if( isOpened() )
            raderGetSpeedResponse();
        return speedResponse;
    }

    public int setParameter( short maxSpeed, byte angle, byte workingMode)
    {
        int err = 0;
        if( isOpened() )
            err = raderSetParameter( maxSpeed,angle,workingMode );
        return err;
    }

    public int setParameter( RaderCmd.ParameterSetCmd parameterSetCmd )
    {
        int err = 0;
        if( isOpened() )
            err = raderSetParameter2( parameterSetCmd );
        return err;
    }
    public RaderCmd.ParameterGetResponse getParameter( )
    {
        int err = 0;
        if( isOpened() )
            err = raderGetParameter();
        return parameterGetResponse;
    }

    public void setSpeedCallbackHandler(SpeedCallbackHandler speedCallbackHandler)
    {
        this.speedCallbackHandler = speedCallbackHandler;
    }

    public boolean isOpened()
    {
        return fd != null && fd.valid();
    }

    public FileDescriptor getFd()
    {
        return fd;
    }

    public static Rader getInstance()
    {
        synchronized(Rader.class)
        {
            if(rader == null)
            {
                rader = new Rader();
            }
        }
        return rader;
    }

    static {
        System.loadLibrary("Camera");
    }
}
