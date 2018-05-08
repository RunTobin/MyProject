package com.huang.bchtsystem.DataAccess;

import android.util.Log;

import com.hikvision.netsdk.HCNetSDK;
import com.huang.bchtsystem.Model.DailyResultData;
import com.huang.bchtsystem.Util.Util;
import com.huang.bchtsystem.jna.HCNetSDKByJNA;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;
import com.huang.bchtsystem.jna.video.CommonMethod;
import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;

import java.util.ArrayList;
import java.util.List;

import static com.huang.bchtsystem.Web.ApiServiceSupport.TAG;

/**
 * Created by admin on 2017/8/11.
 */

public class DailyFinder extends Thread{
    HCNetSDKByJNA.NET_DVR_TIME starttime ;
    HCNetSDKByJNA.NET_DVR_TIME endtime;
    private enum DAILY_CMD
    {
        PICTURE_NULL,
        PICTURE_GET,
        PICTURE_DOWNLOAD,
    }
    public DailyFinder(HCNetSDKByJNA.NET_DVR_TIME starttime, HCNetSDKByJNA.NET_DVR_TIME endtime, HCNetSDKByJNA.NET_DVR_LOG_V30 log_v30)
    {
        this.starttime =starttime;
        this.endtime = endtime;
        this.log_v30 = log_v30;
        isStopped = true;
        cmd = DailyFinder.DAILY_CMD.PICTURE_NULL;
    }

    public DailyFinder()
    {
        isStopped = true;
        cmd = DailyFinder.DAILY_CMD.PICTURE_NULL;
    }

    public void setLpNetDvrFindPictureParam(HCNetSDKByJNA.NET_DVR_TIME start, HCNetSDKByJNA.NET_DVR_TIME end , HCNetSDKByJNA.NET_DVR_LOG_V30 V_30)
    {
        starttime = start;
        endtime =end;
        log_v30 = V_30 ;
    }
    public void setlUserId(int uid)
    {
        this.lUserId = uid;
    }

    public int findFirstLog(int uid, HCNetSDKByJNA.NET_DVR_TIME starttime, HCNetSDKByJNA.NET_DVR_TIME endtime)
    {

        int ret = HCNetSDKJNAInstance.getInstance().NET_DVR_FindDVRLog_V30(uid,1,0,0,starttime.getPointer(),endtime.getPointer(),false);
        if( -1 == ret )
        {
            Log.e(TAG, "NET_DVR_FindFirstLog failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
        }
        return ret;
    }
    public int findNextLog(int lFindHandle,HCNetSDKByJNA.NET_DVR_LOG_V30 log_v30)
    {
        if( -1 == lUserId )
        {
            Log.e(TAG, "findNextLog failed, error : UserId == " + lUserId);
            return -1;
        }
        int ret = HCNetSDKJNAInstance.getInstance().NET_DVR_FindNextLog_V30(lFindHandle,log_v30.getPointer());

        if (ret == HCNetSDKByJNA.NET_DVR_FILE_SUCCESS)
        {
            log_v30.read();
            DailyResultData dailyData = new DailyResultData();
            dailyData.strLogTime = log_v30.strLogTime.toString();
            dailyData.dwMajorType = Integer.toString(log_v30.dwMajorType);
            dailyData.dwMinorType = Integer.toString(log_v30.dwMinorType);
            dailyData.struRemoteHostAddr = CommonMethod.bytes2HexString(log_v30.struRemoteHostAddr.sIpV4);
            dailyData.sNetUser = CommonMethod.bytes2HexString(log_v30.sNetUser);
            seq ++;
            dailyData.num = Integer.toString( seq );
            listDailyData.add(dailyData);
        }
        else if (HCNetSDKByJNA.NET_DVR_FILE_NOFIND == ret)
        {
            Log.e(TAG, "findNextLog: No file found");
            return -1;
        }
        else if (HCNetSDKByJNA.NET_DVR_NOMOREFILE == ret)
        {
            Log.e(TAG, "findNextLog: All files are listed");
            return -1;
        }
        else if (HCNetSDKByJNA.NET_DVR_FILE_EXCEPTION == ret)
        {
            Log.e(TAG, "findNextLog: Exception in searching");
            return -1;
        }
        else if (HCNetSDKByJNA.NET_DVR_ISFINDING == ret)
        {
            Log.e(TAG, "findNextLog: NET_DVR_ISFINDING");
            return 0;
        }
        else
        {
            int error = HCNetSDK.getInstance().NET_DVR_GetLastError();
            Log.e(TAG, "findNextLog: NET_DVR_FindNextLog failed, error =" +  error);
            return -1;
        }
        return 0;
    }

    public boolean closeFinderLog(int lFindHandle)
    {
        if( -1 == lUserId )
        {
            Log.e(TAG, "closeFinderLog failed, error : UserId == " + lUserId);
            return true;
        }
        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_FindLogClose_V30(lFindHandle);
        if( false == ret )
        {
            Log.e(TAG, "NET_DVR_FindLogClose_V30 failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
        }else {
            Log.e(TAG, "NET_DVR_FindLogClose_V30 Succ" );
        }

        return ret;
    }

    public void getAllLog()
    {
        listDailyData = new ArrayList<DailyResultData>();
        seq = 0;
        int ret = findFirstLog(lUserId ,starttime,endtime);
        if( -1 != ret )
        {
            while ( -1 != findNextLog(ret,log_v30));
            closeFinderLog(ret);
            Log.e(TAG, "listPictureData.Size() == "+ listDailyData.size());
        }
    }

    public void downloadPicture(byte[] fn)
    {
        Memory fileName = new Memory(128);
        final int size = 1024*1024*4;
        Memory savedFileName = new Memory(size);
        fileName.write(0,fn,0,fn.length);
        String strSavedFileName = Util.toValidString("/data/data/"+new String(fn));
        savedFileName.write(0,strSavedFileName.getBytes(),0,strSavedFileName.length());
        IntByReference returnCode = new IntByReference();

        boolean  ret =  HCNetSDKJNAInstance.getInstance().NET_DVR_SetRecvTimeOut(10000);
        if( false == ret )
        {
            Log.e(TAG, "NET_DVR_GetPicture failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
        }

        ret = HCNetSDKJNAInstance.getInstance().NET_DVR_GetPicture(lUserId, fileName,savedFileName);

        if( false == ret )
        {
            Log.e(TAG, "NET_DVR_GetPicture failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
        }


//        ret =  HCNetSDKJNAInstance.getInstance().NET_DVR_GetPicture_V30(lUserId, fileName,savedFileName, size, returnCode);
//        if( false == ret )
//        {
//            Log.e(TAG, "NET_DVR_GetPicture failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
//        }
//        else
//        {
//            int length = returnCode.getValue();
//            byte[] pic = savedFileName.getByteArray(0,length);
//        }
    }

    @Override
    public void run()
    {
        while (!isStopped)
        {
            if( DailyFinder.DAILY_CMD.PICTURE_GET == cmd)
                getAllLog();
            else if ( DailyFinder.DAILY_CMD.PICTURE_DOWNLOAD == cmd)
                downloadPicture("".getBytes());
            else
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception e)
                {

                }
            }
        }
    }
    public List<DailyResultData> getListDailyData()
    {
        return listDailyData;
    }

    HCNetSDKByJNA.NET_DVR_LOG_V30 log_v30 = new HCNetSDKByJNA.NET_DVR_LOG_V30();

    private List<DailyResultData> listDailyData = new ArrayList<DailyResultData>();
    private int lUserId;
    private int seq = 0;

    boolean isStopped;
    DailyFinder.DAILY_CMD cmd;
}
