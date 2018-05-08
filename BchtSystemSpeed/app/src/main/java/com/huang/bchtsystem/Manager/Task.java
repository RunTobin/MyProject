package com.huang.bchtsystem.Manager;

/**
 * Created by admin on 2017/5/17.
 */

public interface Task {
    public void onTaskBegin();
    public void onTask();
    public void onTaskFinished();
    public void onError(String error);
}
