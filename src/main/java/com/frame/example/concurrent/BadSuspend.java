package com.frame.example.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * jdk1.5使用wait(),notify(),notifyAll()实现线程同步
 * @author wangzhilong
 * 2016年5月12日下午7:53:46
 */
public class BadSuspend {
	
	public static Object object = new Object() ;
	static Thread t1 = new ChangeObjectThread("t1") ;
	static Thread t2 = new ChangeObjectThread("t2") ;

	public static class ChangeObjectThread extends Thread{
		
		public ChangeObjectThread(String name){
			super.setName(name);
		}
		
		public void run() {
			synchronized (object) {
				System.out.println("in "+ getName());
//				Thread.currentThread().suspend(); //挂起
				LockSupport.park(this); //线程调用park函数则等待“许可”,如果许可可用park立即返回，并消费掉这个许可
				if(Thread.interrupted()){
					System.out.println(getName()+" 被中断了");
				}else{
					System.out.println(getName()+" 执行结束了");
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		t1.start();
		Thread.sleep(100);
		t2.start();
		t1.interrupt();
//		LockSupport.unpark(t1); //解除挂起 函数为线程提供
		LockSupport.unpark(t2); //unpark函数可以先于park调用。比如线程B调用unpark函数，给线程A发了一个“许可”，那么当线程A调用park时，它发现已经有“许可”了，那么它会马上再继续运行
//		t1.resume();
//		t2.resume();
		t1.join();
		t2.join();
	}
}
