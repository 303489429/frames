package com.frame.example.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleExecutorServiceDemo {
	
	public static void main(String[] args) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
		//如果前面的任务没有完成，则调度也不会启用
		/*ses.scheduleAtFixedRate(new Runnable(){
			public void run(){
				try {
					Thread.sleep(8000);
					System.out.println(System.currentTimeMillis()/1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 2, TimeUnit.SECONDS) ;
	*/
		//任务执行完成后间隔周期时间2秒继续执行
		ses.scheduleWithFixedDelay(new Runnable(){
			public void run(){
				try {
					Thread.sleep(8000);
					System.out.println(System.currentTimeMillis()/1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 2, TimeUnit.SECONDS) ;
	}
	
	
}
