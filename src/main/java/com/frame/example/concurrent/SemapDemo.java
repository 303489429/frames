package com.frame.example.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemapDemo implements Runnable {
	
	final Semaphore semaphore = new Semaphore(5) ; //初始化5个信号量许可
	
	public void run() {
		try {
			semaphore.acquire();//尝试获取一个信号量许可
			Thread.sleep(1000); //模拟耗时
			System.out.println(Thread.currentThread().getId()+":done");
			semaphore.release(); //释放信号量
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}

	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(20) ; 
		final SemapDemo demo = new SemapDemo() ;
		for (int i = 0; i < 20; i++) {
			exec.submit(demo);
		}
	}
	
}
