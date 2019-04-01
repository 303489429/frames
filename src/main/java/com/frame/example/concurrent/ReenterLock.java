package com.frame.example.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class ReenterLock implements Runnable{
	
	public static ReentrantLock lock = new ReentrantLock(); //重入锁
	static ReenterLock instance = new ReenterLock() ;
	public static int num = 0 ;
	
	public void run(){
		for (int i = 0; i < 100000000; i++) {
			lock.lock();
			try {
				num++ ;
			} finally {
				lock.unlock();
			}
		}
	}
	
//	public void run(){
//		for (int i = 0; i < 100000000; i++) {
//			synchronized (instance) {
//				num++ ;
//			}
//		}
//	}
	
	public static void main(String[] args) throws InterruptedException {
		long startTime = System.currentTimeMillis() ;
		Thread t1 = new Thread(instance) ;
		Thread t2 = new Thread(instance) ;
		Thread t3 = new Thread(instance) ;
		Thread t4 = new Thread(instance) ;
		t1.start();t2.start();t3.start();t4.start();
		t1.join();t2.join();t3.join();t4.join();
		System.out.println("time="+(System.currentTimeMillis() - startTime));
		System.out.println("num="+num);
	}
}
