package com.frame.example.concurrent;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayDemo {
	static AtomicIntegerArray arr = new AtomicIntegerArray(10) ; //定义长度为10的原子Integer数组
	static  class AddThread implements Runnable{
		public void run() {
			for (int i = 0; i < 1000; i++) {
				arr.incrementAndGet(i%arr.length()) ; //将第i个下标元素+1
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread[] t = new Thread[10] ;
		for (int i = 0; i < 10; i++) 
			t[i] = new Thread(new AddThread()) ;
		for (int i = 0; i < 10; i++) 
			t[i].start();
		for (int i = 0; i < 10; i++) 
			t[i].join();
		
		System.out.println("arr="+arr);
	}
}
