package com.frame.example.concurrent;
/**
 * volatile 
 * -server切换服务器模式
 * @author wangzhilong
 * 2016年5月10日上午7:30:22
 */
public class NoVisibility {
	private static volatile boolean read ;
	private static int num ; 
	
	private static class ReaderThread extends Thread{
		
		public void run(){
			while(!read) ;
			System.out.println("num="+num);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new ReaderThread().start();
		Thread.sleep(1000);
		num = 42 ;
		read = true ;
//		Thread.sleep(10000);
	}
	
}
