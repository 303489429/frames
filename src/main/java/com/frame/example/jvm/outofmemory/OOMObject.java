package com.frame.example.jvm.outofmemory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * vm arges : -Xms100m -Xmx100m -XX:UseSerialGC
 * @author wangzhilong
 * 2016年5月16日下午8:08:09
 */
public class OOMObject {
	
	static class TestObject {
		public byte[] placeHolder = new byte[64 * 1024] ;
	}
	
	public static void fillHeap(int num) throws InterruptedException {
		List<TestObject> list = new ArrayList<TestObject>() ;
		for(int i=0; i< num ; i++){
			Thread.sleep(50);
			list.add(new TestObject());
		}
		System.gc();
	}
	
	public static void createBusyThread(){
		Thread thread = new Thread(new Runnable() {
			public void run() {
				while(true)
					; //无线循环
			}
		},"testBusyThread");
		thread.start();
	}
	
	/**
	 * 线程死锁等待演示
	 * @param lock
	 */
	public static void createLockThread(final Object lock){
		Thread thread = new Thread(new Runnable() {
			public void run() {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		},"testLockThread");
		thread.start();
	}
	
	static class SynAddRunnable implements Runnable{
		int a,b ;
		public SynAddRunnable(int a , int b) {
			this.a= a ;
			this.b= b ;
		}
		public void run() {
			synchronized (Integer.valueOf(a)) {
				synchronized (Integer.valueOf(b)) {
					System.out.println(a+b);
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
//		OOMObject.fillHeap(1000);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ;
		br.readLine() ; 
		createBusyThread();
		br.readLine();
		Object obj = new Object();
		createLockThread(obj);
		br.readLine() ;
		for (int i = 0; i < 100; i++) {
			new Thread(new SynAddRunnable(1, 2)).start();
			new Thread(new SynAddRunnable(2, 1)).start();
		}
		
	}
	
}
