package com.frame.example.concurrent;
/**
 * 线程中断
 * @author wangzhilong
 * 2016年5月9日上午8:44:54
 */
public class InterruptedTest {
	
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(){
			public void run(){
				while(true){
					if(Thread.currentThread().isInterrupted()){
						System.out.println("Thread is Interrupted!");
						break ;
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						System.out.println("interrupted when sleep");
						Thread.interrupted() ; 
					}
					Thread.yield();
				}
			}
		};
		
		t.start();
		Thread.sleep(200);
		t.interrupt();
	}
	
}
