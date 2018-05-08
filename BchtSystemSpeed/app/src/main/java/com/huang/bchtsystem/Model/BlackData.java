package com.huang.bchtsystem.Model;

import java.io.Serializable;

/**
 * Created by admin on 2017/3/24.
 */

public class BlackData implements Serializable {
    public int num; //序号
    public String brand; //车牌
    public String color; //颜色
    public String JC;  //简称
    public String type; //违法类型

    public String getJC() {
        return JC;
    }

    public void setJC(String JC) {
        this.JC = JC;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
