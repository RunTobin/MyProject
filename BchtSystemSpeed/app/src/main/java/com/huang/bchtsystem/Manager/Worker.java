package com.huang.bchtsystem.Manager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.huang.bchtsystem.Manager.Task;


/**
 * Created by admin on 2017/5/17.
 */

public class Worker implements Runnable {

    private ArrayBlockingQueue<Task> tasks= new ArrayBlockingQueue<Task>(4);
    boolean isStopped = false;

    @Override
    public void run() {
        while(!isStopped)
        {
            Task task = poll();

            if(task != null)
            {
                task.onTask();
                task.onTaskFinished();
            }
        }
    }

    public void push(Task task)
    {
        try
        {
            tasks.put(task);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    public Task poll()
    {
        Task task = null;
        try
        {
            task = tasks.poll(1000, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return task;
    }

    public void start()
    {
        this.isStopped = false;
    }

    public void stop()
    {
        this.isStopped = true;
    }
}
