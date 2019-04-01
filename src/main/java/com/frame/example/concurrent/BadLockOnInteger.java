package com.frame.example.concurrent;

public class BadLockOnInteger implements Runnable{
	
	public static Integer i = 0 ;
	
	static BadLockOnInteger instance = new BadLockOnInteger() ;
	
	public void run(){
		for (int j = 0; j < 1000000; j++) {
			synchronized (i) { //Integer 是一个不可变对象，每次加锁加到不同对象上了 导致无法实现同步
				i++ ;
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(instance) ;
		Thread t2 = new Thread(instance) ;
		t1.start();t2.start();
		t1.join();t2.join();
		
		System.out.println("i="+i);
	}
}
