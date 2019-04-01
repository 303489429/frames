package com.frame.example.concurrent;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实测消息 使用synchronize效果好于atomicInteger CAS无锁 效果好的多
 */
public class AutomicIntegerDemo {
	static AtomicInteger id = new AtomicInteger() ;
	static int count = 0 ;
	public static class AddThread implements Runnable{
		public void run() {
//			for (int i = 0; i < 1000; i++) {
//				id.incrementAndGet() ;
//			}
			for (int i = 0; i < 1000; i++) {
				synchronized (AutomicIntegerDemo.class) {
					count++;
				}
			}	
		}
	}
	public static void main(String[] args) throws InterruptedException, IOException {
		long startTime = System.currentTimeMillis() ;
		Thread[] t = new Thread[10000] ;
		for (int i = 0; i < 10000; i++) 
			t[i] = new Thread(new AddThread()) ;
		for (int i = 0; i < 10000; i++) 
			t[i].start();
		for (int i = 0; i < 10000; i++) 
			t[i].join();
		long endTime = System.currentTimeMillis() ;
		System.out.println(count+",time="+(endTime - startTime)+"(ms)");
		System.out.println(id+",time="+(endTime - startTime)+"(ms)");
		
		Runtime.getRuntime().exec("Ex");
		
	}
}
