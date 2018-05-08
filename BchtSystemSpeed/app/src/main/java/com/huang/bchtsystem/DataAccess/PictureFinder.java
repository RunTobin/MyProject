package com.huang.bchtsystem.DataAccess;
import android.util.Log;

import com.hikvision.netsdk.HCNetSDK;
import com.huang.bchtsystem.Model.PictureResultData;
import com.huang.bchtsystem.jna.HCNetSDKByJNA;
import com.huang.bchtsystem.jna.HCNetSDKJNAInstance;
import com.huang.bchtsystem.Util.Util;
import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;

import java.util.ArrayList;
import java.util.List;
import static com.huang.bchtsystem.Web.ApiServiceSupport.TAG;

/**
 * Created by admin on 2017/4/24.
 */

public class PictureFinder extends Thread{

    private enum PICTURE_CMD
    {
        PICTURE_NULL,
        PICTURE_GET,
        PICTURE_DOWNLOAD,
    }
    public PictureFinder(HCNetSDKByJNA.NET_DVR_FIND_PICTURE_PARAM netDvrFindPictureParam, HCNetSDKByJNA.NET_DVR_FIND_PICTURE netDvrFindPicture)
    {
        lpNetDvrFindPictureParam = netDvrFindPictureParam;
        lpNetDvrFindPicture = netDvrFindPicture;
        isStopped = true;
        cmd = PICTURE_CMD.PICTURE_NULL;
    }

    public PictureFinder()
    {
        isStopped = true;
        cmd = PICTURE_CMD.PICTURE_NULL;
    }

    public void setLpNetDvrFindPictureParam(HCNetSDKByJNA.NET_DVR_FIND_PICTURE_PARAM netDvrFindPictureParam ,HCNetSDKByJNA.NET_DVR_FIND_PICTURE netDvrFindPicture)
    {
        lpNetDvrFindPictureParam = netDvrFindPictureParam;
        lpNetDvrFindPicture = netDvrFindPicture ;
    }
    /*
        public void setLpNetDvrFindPicture(HCNetSDKByJNA.NET_DVR_FIND_PICTURE_v40 netDvrFindPicture)
        {
            lpNetDvrFindPicture = netDvrFindPicture;
        }
    */
    public void setlUserId(int uid)
    {
        lUserId = uid;
    }

    public int findFirstPicture()
    {
        lpNetDvrFindPictureParam.write();
        int ret = HCNetSDKJNAInstance.getInstance().NET_DVR_FindPicture(lUserId, lpNetDvrFindPictureParam.getPointer());
        if( -1 == ret )
        {
            int err = HCNetSDK.getInstance().NET_DVR_GetLastError();
            Log.e(TAG, "NET_DVR_FindPicture failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
        }

        return ret;
    }
    public int findNextPicture(int lFindHandle)
    {
        if( -1 == lUserId )
        {
            Log.e(TAG, "findNextPicture failed, error : UserId == " + lUserId);
            return -1;
        }
        int ret = HCNetSDKJNAInstance.getInstance().NET_DVR_FindNextPicture(lFindHandle,lpNetDvrFindPicture.getPointer());

        if (ret == HCNetSDKByJNA.NET_DVR_FILE_SUCCESS)
        {
            lpNetDvrFindPicture.read();
            //     lpNetDvrFindPicture.uPicExtraInfo.read();
            PictureResultData pictureData = new PictureResultData();
            pictureData.dwFileSize = Integer.toString(lpNetDvrFindPicture.dwFileSize);  //Integer.toString(lpNetDvrFindPicture.byFileType) ;

            if (listPictureData.size() > 0  )
            {
                if (Integer.parseInt(pictureData.dwFileSize)>50){
                    pictureData.filename = Util.toValidString(new String(lpNetDvrFindPicture.sFileName));
                    pictureData.filetime = lpNetDvrFindPicture.struTime.toString();
                    //        pictureData.sLicense = Util.toValidString(new String(lpNetDvrFindPicture.sLicense));
                    //   pictureData.byRecogResult = lpNetDvrFindPicture.byRecogResult;
                    pictureData.dwFileSize = Integer.toString(lpNetDvrFindPicture.dwFileSize);
                    seq ++;
                    pictureData.num = Integer.toString( seq );
                    listPictureData.add(pictureData);
                }
            }else {
                pictureData.filename = Util.toValidString(new String(lpNetDvrFindPicture.sFileName));
                pictureData.filetime = lpNetDvrFindPicture.struTime.toString();
                //   pictureData.sLicense = Util.toValidString(new String(lpNetDvrFindPicture.sLicense));
                //  pictureData.byRecogResult = lpNetDvrFindPicture.byRecogResult;
                pictureData.dwFileSize = Integer.toString(lpNetDvrFindPicture.dwFileSize);
                seq ++;
                pictureData.num = Integer.toString( seq );
                listPictureData.add(pictureData);
            }
        }
        else if (HCNetSDKByJNA.NET_DVR_FILE_NOFIND == ret)
        {
            Log.e(TAG, "findNextPicture: No file found");
            return -1;
        }
        else if (HCNetSDKByJNA.NET_DVR_NOMOREFILE == ret)
        {
            Log.e(TAG, "findNextPicture: All files are listed");
            return -1;
        }
        else if (HCNetSDKByJNA.NET_DVR_FILE_EXCEPTION == ret)
        {
            Log.e(TAG, "findNextPicture: Exception in searching");
            return -1;
        }
        else if (HCNetSDKByJNA.NET_DVR_ISFINDING == ret)
        {
            Log.e(TAG, "findNextPicture: NET_DVR_ISFINDING");
            return 0;
        }
        else
        {
            int error = HCNetSDK.getInstance().NET_DVR_GetLastError();
            Log.e(TAG, "findNextPicture: NET_DVR_FindNextPicture failed, error =" +  error);
            return -1;
        }
        return 0;
    }

    public boolean closeFinderPicture(int lFindHandle)
    {
        if( -1 == lUserId )
        {
            Log.e(TAG, "closeFinderPicture failed, error : UserId == " + lUserId);
            return true;
        }

        boolean ret = HCNetSDKJNAInstance.getInstance().NET_DVR_CloseFindPicture(lFindHandle);
        if( false == ret )
        {
            Log.e(TAG, "NET_DVR_CloseFindPicture failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
        }else {
            Log.e(TAG, "NET_DVR_CloseFindPicture Succ" );
        }

        return ret;
    }

    public void getAllPicture(HCNetSDKByJNA.NET_DVR_FIND_PICTURE lpNetDvrFindPicture )
    {
        listPictureData = new ArrayList<PictureResultData>();
        seq = 0;
        int ret = findFirstPicture();
        if( -1 != ret )
        {
            while ( -1 != findNextPicture(ret));
            closeFinderPicture(ret);
            Log.e(TAG, "listPictureData.Size() == "+ listPictureData.size());
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


        ret =  HCNetSDKJNAInstance.getInstance().NET_DVR_GetPicture_V30(lUserId, fileName,savedFileName, size, returnCode);
        if( false == ret )
        {
            Log.e(TAG, "NET_DVR_GetPicture failed, error :" + HCNetSDK.getInstance().NET_DVR_GetLastError() );
        }
        else
        {
            int length = returnCode.getValue();
            byte[] pic = savedFileName.getByteArray(0,length);
        }
    }

    @Override
    public void run()
    {
        while (!isStopped)
        {
            if(PICTURE_CMD.PICTURE_GET == cmd)
                getAllPicture(lpNetDvrFindPicture);
            else if (PICTURE_CMD.PICTURE_DOWNLOAD == cmd)
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
    public List<PictureResultData> getListPictureData()
    {
        return listPictureData;
    }

    private List<PictureResultData> listPictureData = new ArrayList<PictureResultData>();
    private int lUserId;
    private int seq = 0;
    private HCNetSDKByJNA.NET_DVR_FIND_PICTURE_PARAM lpNetDvrFindPictureParam;
    private HCNetSDKByJNA.NET_DVR_FIND_PICTURE lpNetDvrFindPicture ;

    boolean isStopped;
    PICTURE_CMD cmd;
}
