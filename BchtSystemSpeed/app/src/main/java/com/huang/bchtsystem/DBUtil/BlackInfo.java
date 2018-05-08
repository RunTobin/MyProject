package com.huang.bchtsystem.DBUtil;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class BlackInfo {

    public BlackInfo() {
    }

    public BlackInfo(int userId, String carnum, String color, String address, String type) {
        super();
        this.userId = userId;
        this.carnum = carnum;
        this.color = color;
        this.address = address;
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int userId;
    private String carnum;
    private String color;
    private String address;
    private String type;

    @Override
    public String toString() {
        return "UserInfo [userId=" + userId + ", carnum=" + carnum
                + ", color=" + color + ", address=" + address + ", type=" + type + "]";
    }
}
