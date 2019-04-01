package com.frame.example.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁的好搭档 Condition  类似synchronized的搭档Object.wait();Object.notify();
 * @author wangzhilong
 * 2016年5月12日上午7:27:49
 */
public class ReenterLockCondition implements Runnable {
	
	private static ReentrantLock lock = new ReentrantLock() ;
	private static Condition condition = lock.newCondition() ; // 生成一个和当前重入锁绑定的condition实例
	public void run() {
		try {
			lock.lock(); //加锁
			condition.await(); //锁等待
			System.out.println("there is going on");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally  {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new ReenterLockCondition()) ;
		t1.start();
		Thread.sleep(2000);
		//通知线程继续执行
		lock.lock();
		condition.signal();
		lock.unlock();
	}

}
