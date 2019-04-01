package com.frame.example.concurrent;

public class DaemonDemo extends Thread{
	
	public void run(){
		while(true){
			System.out.println("I am alive");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t = new DaemonDemo();
		t.setDaemon(true);  //设置该线程为守护线程，当系统只有守护线程时，java虚拟机就会自然退出
		t.start();
		
		Thread.sleep(2000);
	}
	
}
