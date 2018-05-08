package com.huang.bchtsystem.Model;

/**
 * Created by admin on 2017/3/27.
 */

public class DailyData {
    public String User;  //编码用户
    public String Time;  //时间
    public String Content;  //操作内容

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
