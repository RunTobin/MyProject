package com.huang.bchtsystem.Model;

import java.io.Serializable;

/**
 * Created by admin on 2017/7/7.
 */

public class ImageInfoBean implements Serializable{
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    private String uri;

}
