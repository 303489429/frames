package com.frame.example.concurrent;

import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
	private static Lock fairLock = new ReentrantLockExt(true);
	private static Lock unfairLock = new ReentrantLockExt();
	
	private static class Job implements Runnable{

		private Lock lock ;
		public Job(Lock lock){
			this.lock=lock ;
		}
		public void run() {
//			for (int i = 0; i < 5; i++) {
				lock.lock();
				System.out.println("lock by:"+Thread.currentThread().getName()+","+((ReentrantLockExt) lock).getQueuedThreads());
				lock.unlock();
//			}
		}
		
	}
	
	public void fail(){
		System.out.println("fair version");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Job(fairLock),""+i);
			thread.start();
		}
	}
	
	public void unfail(){
		System.out.println("unfair version");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Job(unfairLock),""+i);
			thread.start();
		}
	}

	 private static class ReentrantLockExt extends ReentrantLock{
		 
		 public ReentrantLockExt(){
			 super();
		 }
		 public ReentrantLockExt(boolean isfair){
			 super(isfair) ;
		 }
		 public Collection<Thread> getQueuedThreads() {
			 return super.getQueuedThreads();
		 }
	 }
	
	public static void main(String[] args) {
//		new ReentrantLockTest().unfail();
		new ReentrantLockTest().fail();
	}
}
