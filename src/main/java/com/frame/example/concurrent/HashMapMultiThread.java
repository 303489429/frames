package com.frame.example.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用hashMap3种可能：
 * 1结果正常为100000
 * 2结果不正常
 * 3程序无法结束（HashMap内部死循环了，两个key1,key2互为对象next节点）
 * @author wangzhilong
 * 2016年5月11日上午7:49:33
 */
public class HashMapMultiThread {
	
//	static Map<String,String> map = new HashMap<String,String>();
//	static Map<String,String> map = new ConcurrentHashMap<String,String>();
	static Map<String,String> map = Collections.synchronizedMap(new HashMap<String, String>());  // 构造一个线程安全的hashMap
	
	public static class AddThread implements Runnable {
		int start = 0 ;
		public AddThread(int start){
			this.start = start ;
		}
		public void run() {
			for (int i = start; i < 100000; i+=2) {
				map.put(Integer.toString(i), Integer.toBinaryString(i)) ;
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new AddThread(0));
		Thread t2 = new Thread(new AddThread(1));
		t1.start();t2.start();
		t1.join();t2.join();
		System.out.println("map size:"+map.size());
		
	}
	
}
