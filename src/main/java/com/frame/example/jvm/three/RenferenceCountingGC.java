package com.frame.example.jvm.three;
/**
 * GC判断对象是否存活的算法：引用计数算法（无法对象解决循环引用的问题） 
 * @author wangzhilong
 * 2016年4月20日上午8:45:11
 */
public class RenferenceCountingGC {
	
	public Object obj = null ;
	private static final int _1MB = 1024 * 1024 ;
	/**
	 * 这个成员变量的唯一意义就是占点内存，以便能在GC日志中看清楚是否被回收过。
	 */
	private byte[] bigSize = new byte[2 * _1MB] ;
	
	public static void main(String[] args) {
		RenferenceCountingGC objA = new RenferenceCountingGC() ;
		RenferenceCountingGC objB = new RenferenceCountingGC() ;
		objA.obj = objB ;
		objB.obj = objA ;
		
		objA = null;
		objB = null ;
		//假设GC发生于此
		System.gc();
	}
	
}
