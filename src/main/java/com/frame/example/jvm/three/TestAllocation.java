package com.frame.example.jvm.three;

public class TestAllocation {
	
	private static final int _1MB = 1024*1024 ;
	
	/**
	 * vm 参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC
	 * 2016年5月3日上午7:21:48
	 */
	public static void testAllocation(){
		byte[] allocation1,allocation2,allocation3,allocation4 ;
		allocation1 = new byte[2 * _1MB] ;
		allocation2 = new byte[2 * _1MB] ;
		allocation3 = new byte[2 * _1MB] ;
		allocation4 = new byte[4 * _1MB] ; //出现一次minor gc (新生代GC)  (Major gc  老年代GC)
	}
	
	/**
	 * vm 参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC
	 * -XX:PretenureSizeThreshold=3145728
	 * 2016年5月3日上午7:21:48
	 */
	public static void testPretenureSizeThreshold(){
		byte [] allocation ; 
		allocation = new  byte[4 * _1MB] ; //直接分配在老年去中
	}
	
	/**
	 * vm 参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+UseSerialGC
	 * 
	 * 2016年5月3日上午7:21:48
	 */
	public static void testTenuringThreshold(){
		byte[] allocation1,allocation2,allocation3 ;
		allocation1 = new byte[_1MB / 4] ;
		//什么时候进入老年代取决于 XX:MaxTenuringThreshold 设置
		allocation2 = new byte[_1MB * 4] ;
		allocation3 = new byte[_1MB * 4] ;
		allocation3 = null ; 
		allocation3 = new byte[_1MB * 4] ;
	}
	
	public static void main(String[] args) {
//		testAllocation();
//		testPretenureSizeThreshold();
		testTenuringThreshold();
	}
	
}
