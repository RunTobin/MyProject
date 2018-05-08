package com.huang.bchtsystem.Model;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.CheckBox;

import com.huang.bchtsystem.View.Fragment.Picture.PictureResultActivity;

/**
 * Created by admin on 2017/4/17.
 */

public class PictureResultData{
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletime() {
        return filetime;
    }

    public void setFiletime(String filetime) {
        this.filetime = filetime;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
    public String num; //图片序号
    public String filename ; //图片文件名称
    public String filetime ; //图片文件日期

    public String getsLicense() {
        return sLicense;
    }

    public void setsLicense(String sLicense) {
        this.sLicense = sLicense;
    }

    public String sLicense; //车牌号
    public CheckBox checkBox;

    public String getDwFileSize() {
        return dwFileSize;
    }

    public void setDwFileSize(String dwFileSize) {
        this.dwFileSize = dwFileSize;
    }

    public int getByRecogResult() {
        return byRecogResult;
    }

    public void setByRecogResult(int byRecogResult) {
        this.byRecogResult = byRecogResult;
    }

    public String getByFileType() {
        return byFileType;
    }

    public void setByFileType(String byFileType) {
        this.byFileType = byFileType;
    }

    public String dwFileSize ; //图片大小
    public int byRecogResult ;//车辆识别结果
    public String  byFileType ; //图片类型


    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
