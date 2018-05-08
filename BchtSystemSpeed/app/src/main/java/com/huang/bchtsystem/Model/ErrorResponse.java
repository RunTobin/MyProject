package com.huang.bchtsystem.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2017/3/13.
 */

public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = -3818150138355280561L;

    /**
     * + 401001  用户名或者密码错误
     * + 401002  accesstoken 过期
     * --------------------------
     */
    @SerializedName("errid")
    public int errid;

    @SerializedName("errmsg")
    public String errMsg;
}