package com.frame.example.concurrent;

public class PriorityDemo {
	
	private static class HightPriority extends Thread{
		static int count = 0 ;
		public void run(){
			while(true){
				synchronized (PriorityDemo.class) {
					count++ ;
					if(count > 10000000){
						System.out.println("Hight is Complete");
						break;
					}
				}
			}
		}
	}
	
	private static class LowPriority extends Thread{
		static int count = 0 ;
		public void run(){
			while(true){
				synchronized (PriorityDemo.class) {
					count++ ;
					if(count > 10000000){
						System.out.println("Low is Complete");
						break;
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Thread high = new HightPriority();
		Thread low = new LowPriority();
		high.setPriority(Thread.MAX_PRIORITY);
		low.setPriority(Thread.MIN_PRIORITY);
		low.start();
		high.start();
	}
}
