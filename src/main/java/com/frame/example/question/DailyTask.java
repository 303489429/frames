package com.frame.example.question;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时器：接收一个时间，然后每隔开一秒比对是否是任务执行时间，如果是则执行任务
 * @author bh
 */
class SheduledTask  implements Runnable{
    private Task task;  //需要执行的任务
    private String time;//任务执行的时间
    //定时器每隔一秒执行run方法
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
    
    public SheduledTask (Task task,String time){
        this.task = task;
        this.time = time;
    }

    public void run() {
        //判断当前时间是否为执行时间，如果是则执行任务，结束后关闭定时器
        System.out.println("每秒钟定时任务：比对任务时间，如果匹配则执行任务...");
        String now = nowAsString();
        if(now.equals(time)){
            this.task.run();
            service.shutdown();
        }
    }

    public void execute(){
        service.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
    }

    private  String nowAsString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        return df.format(new Date()).toString();
    }
}

class Task implements Runnable{
    public void run() {
        System.out.println("do task.");
    }
}

public class DailyTask implements Runnable{

    public void run() {
        //定时生成一个任务时间
        System.out.println("每日定时任务...");
        //此次时间从数据库获取
        String time = "2016-04-14 16:49:10";
        Task task = new Task();
        new SheduledTask(task,time).execute();
    }

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        DailyTask dailyTask= new DailyTask();
        //每天凌晨执行当日定时任务
        service.scheduleAtFixedRate(dailyTask, 0, 1, TimeUnit.DAYS);
//        service.shutdown();
    }
}