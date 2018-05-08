package com.huang.bchtsystem.DataAccess;

/**
 * Created by admin on 2017/5/12.
 */

public enum DvrConfigInstance
{
    CLASS;
    private static DvrConfig instance = null;
    /**
     * get the instance of HCNetSDK
     * @return the instance of HCNetSDK
     */
    public static DvrConfig getInstance()
    {
        if (null == instance)
        {
            synchronized (DvrConfig.class)
            {
                instance = new DvrConfig();
            }
        }
        return instance;
    }
}