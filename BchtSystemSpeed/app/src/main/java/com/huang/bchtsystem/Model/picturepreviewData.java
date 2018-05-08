package com.huang.bchtsystem.Model;

import java.io.Serializable;

/**
 * Created by admin on 2017/5/31.
 */

public class picturepreviewData implements Serializable {
    public picturepreviewData(){

    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String fileName ; //图片文件名

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int num;
}
