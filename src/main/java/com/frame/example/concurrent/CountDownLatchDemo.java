package com.frame.example.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒计时器
 * @author wangzhilong
 * 2016年5月12日上午8:38:29
 */
public class CountDownLatchDemo implements Runnable {
	static final CountDownLatch end = new CountDownLatch(10); //初始化倒计时器个数为10个
	static final CountDownLatchDemo demo = new CountDownLatchDemo() ;
	
	public void run() {
		try {
			Thread.sleep(new Random().nextInt(1000));
			System.out.println(Thread.currentThread().getId()+"-check complete");
			end.countDown(); //通知CountDownLatch一个线程完成任务
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newFixedThreadPool(10) ;
		for (int i = 0; i < 10; i++) {
			exec.submit(demo);
		}
		end.await(); //等待检查（所有线程完毕）
		System.out.println("fire!");
		exec.shutdown();
	}
}
