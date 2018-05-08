package com.huang.bchtsystem.Model;

import java.io.Serializable;


/**
 * Created by admin on 2017/5/11.
 */

public class ServerSetting  implements Serializable {
    private  UserData    userData;
    private  Host        host;

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public void create()
    {
        if( null == userData )
            userData = new UserData();
        if( null == host )
            host = new Host();

    }
}
