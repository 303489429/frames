package com.frame.example.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalGc {
	static volatile ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>(){
		 protected void finalize() throws Throwable {
			System.out.println(this.toString()+" is gc");
		}
	};
	static volatile CountDownLatch cd = new CountDownLatch(10000) ; 
	public static class ParseDate implements Runnable{
		int i = 0 ;
		public ParseDate(int i){
			this.i=i;
		}
		public void run() {
			try {
				if(tl.get() == null){ //使用本地线程变量
					tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"){
						 protected void finalize() throws Throwable {
							 System.out.println(this.toString()+" is gc");
						 }
					});
					System.out.println(Thread.currentThread().getId()+":create SimpleDateFormat");
				}
				Date t = tl.get().parse("2016-05-18 19:29:" + i % 60);
//				System.out.println(i+":"+t);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				cd.countDown(); //计数
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10000; i++) {
			es.execute(new ParseDate(i));
		}
		cd.await();
		System.out.println("mission complete！");  //任务完成，使命完成
		tl = null ;
		System.gc();
		System.out.println("first GC complete");
		//在设置ThreadLocal的时候，会清除ThreadLocalMap中无效的对象
		tl = new ThreadLocal<SimpleDateFormat>() ;
		cd = new CountDownLatch(10000) ;
		for (int i = 0; i < 10000; i++) {
			es.execute(new ParseDate(i));
		}
		cd.await();
		Thread.sleep(1000);
		System.gc();
		System.out.println("second GC complete!");
	}
}
