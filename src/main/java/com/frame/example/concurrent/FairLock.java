package com.frame.example.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 此方法不能正确测试公平锁，此处为本书BUG
 * 公平是指的线程初次调用run方法的顺序。
 * cpu记录了多个线程start时访问lock的时间顺序，继而在获取锁的队列里，按照这个顺序往外取，先进先出，先访问先获取。
 * 而你run方法里放的循环就不算了。

 * @author wangzhilong
 * 2016年5月17日下午12:22:46
 */
public class FairLock implements Runnable {
	public static ReentrantLock lock = new ReentrantLock(true) ;
	public void run() {
		while(true){
			lock.lock();
			String threadName = Thread.currentThread().getName() ;
			if(threadName.equals("t1")){
				System.out.println(threadName+"获得锁");
			}else{
				System.err.println(threadName+"获得锁");
			}
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		FairLock lockDemo = new FairLock();
		Thread t1 = new Thread(lockDemo,"t1") ;
		Thread t2= new Thread(lockDemo,"t2") ;
		t1.start();
		t2.start();
		
	}
}
