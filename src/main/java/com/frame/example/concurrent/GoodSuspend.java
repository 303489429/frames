package com.frame.example.concurrent;

public class GoodSuspend {
	
	public static Object object = new Object() ;
	static Thread t1 = new ChangeObjectThread("t1") ;
	static Thread t2 = new ChangeObjectThread("t2") ;

	public static class ChangeObjectThread extends Thread{
		volatile boolean suspendme = false ;
		
		public ChangeObjectThread(String name){
			super.setName(name);
		}
		
		public void suspendMe(){
			
		}
		public void run() {
			synchronized (object) {
				System.out.println("in "+ getName());
				Thread.currentThread().suspend(); //挂起
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		t1.start();
		Thread.sleep(100);
		t2.start();
		t1.resume();
		t2.resume();
		t1.join();
		t2.join();
	}
}
