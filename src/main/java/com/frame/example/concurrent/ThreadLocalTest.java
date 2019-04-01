package com.frame.example.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadLocalTest {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Lock lock = new ReentrantLock();  //加锁方式解决 非现场安全
	static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>(); //使用线程局部变量
	private static class ParseDate implements Runnable{
		int i =0 ;
		public ParseDate(int i){
			this.i = i ;
		}
		public void run() {
			try {
//				lock.lock();
				if(tl.get() == null){ //使用本地线程变量
					tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
				}
				Date t = tl.get().parse("2016-05-18 19:29:" + i % 60);
//				Date t = sdf.parse("2016-05-18 19:29:" + i % 60); //多线程下，非线程安全
				System.out.println(i+":"+t);
//				lock.unlock();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * 求答案 ？
	 *	一筐鸡蛋：
	 *	1个1个拿，正好拿完。
	 *	2个2个拿，还剩1个。
	 *	3个3个拿，正好拿完。
	 *	4个4个拿，还剩1个。
	 *	5个5个拿，还剩1个
	 *	6个6个拿，还剩3个。
	 *	7个7个拿，正好拿完。
	 *	8个8个拿，还剩1个。
	 *	9个9个拿，正好拿完。
	 *	问筐里有多少鸡蛋？
	 * @param i
	 */
	public static void getNum(int i){
		if(i % 1 == 0 && i % 2 == 1 && i % 3 == 0 && i % 4 == 1 && i%5==1 && i%6==3 && i%7==0 && i%8==1 && i%9==0)
			System.out.println("i="+i);
	}
	public static void main(String[] args) throws InterruptedException {
//		ExecutorService es = Executors.newFixedThreadPool(10);
//		for (int i = 0; i < 1000; i++) {
//			es.execute(new ParseDate(i));
//		}
////		Thread.sleep(2000);
//		es.shutdown();  //关闭线程池，否则线程池中的线程将一直挂起
		for (int i = 0; i < 1000000; i++) {
			getNum(i);
		}
	}
}
