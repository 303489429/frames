package com.frame.example.concurrent.mode;

/**
 * 高效内部内实现一个单利模式
 * @author wangzhilong
 * 2016年5月27日上午7:44:56
 */
public class StaticSingleton {
	private  StaticSingleton(){
		System.out.println("StaticSingleton is create .");
	}
	
	private static class SingletonHolder{
		private SingletonHolder(){
			System.out.println("SingletonHolder is create.");
		}
		private static StaticSingleton instance = new StaticSingleton();
	}
	
	public static StaticSingleton getInstance(){
		return SingletonHolder.instance;
	}
}
