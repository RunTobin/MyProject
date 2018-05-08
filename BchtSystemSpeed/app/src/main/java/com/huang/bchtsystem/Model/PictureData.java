package com.huang.bchtsystem.Model;

/**
 * Created by admin on 2017/3/27.
 */

public class PictureData {

    public String PictureNum;  //序列号
    public String FileName; //文件名
    public String PictureTime; //时间
    public String PictureSpeed; //速度
    public String PictureType;  //违章类型
    public String PicturePlateNum; //车牌号
    public String PictureBM;   //大小车
    public String PictureUpload;  //是否上传

    public String getPicturePlateNum() {
        return PicturePlateNum;
    }

    public void setPicturePlateNum(String picturePlateNum) {
        PicturePlateNum = picturePlateNum;
    }



    public String getPictureTime() {
        return PictureTime;
    }

    public void setPictureTime(String pictureTime) {
        PictureTime = pictureTime;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getPictureSpeed() {
        return PictureSpeed;
    }

    public void setPictureSpeed(String pictureSpeed) {
        PictureSpeed = pictureSpeed;
    }

    public String getPictureType() {
        return PictureType;
    }

    public void setPictureType(String pictureType) {
        PictureType = pictureType;
    }

    public String getPictureNum() {
        return PictureNum;
    }

    public void setPictureNum(String pictureNum) {
        PictureNum = pictureNum;
    }

    public String getPictureBM() {
        return PictureBM;
    }

    public void setPictureBM(String pictureBM) {
        PictureBM = pictureBM;
    }

    public String getPictureUpload() {
        return PictureUpload;
    }

    public void setPictureUpload(String pictureUpload) {
        PictureUpload = pictureUpload;
    }


}
