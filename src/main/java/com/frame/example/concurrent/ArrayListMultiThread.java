package com.frame.example.concurrent;

import java.util.Vector;

/**
 * ArrayList多线程下不安全的实现，可通过使用Vector实现线程安全
 * @author wangzhilong
 * 2016年5月11日上午7:33:09
 */
public class ArrayListMultiThread {
	
	static Vector<Integer> list = new Vector<Integer>(10);
	//static ArrayList<Integer> list = new ArrayList<Integer>(10);
	
	public static class AddThread implements Runnable{

		public void run() {
			for (int i = 0; i < 1000000; i++) {
				list.add(i);
			}
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new AddThread());
		Thread t2 = new Thread(new AddThread());
		t1.start();
		t2.start();
		t1.join();t2.join();
		System.out.println("list size:"+list.size());
	}
	
}
