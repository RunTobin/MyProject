package com.huang.bchtsystem.Model;

import java.io.Serializable;

/**
 * Created by admin on 2017/3/27.
 *   用户管理
 */

public class UserData implements Serializable {
    public String Account; //用户名
    public String userType ;//用户类型
    public  String userPower ;//用户权限
    public String userPwd; //用户密码

    public UserData(String Account,String userPwd,String userType,String userPower){
        this.Account = Account;
        this.userPwd = userPwd;
        this.userType = userType;
        this.userPower = userPower;
    }
    public UserData()
        {

    }
    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }



    public String getUserPower() {
        return userPower;
    }

    public void setUserPower(String userPower) {
        this.userPower = userPower;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


}
