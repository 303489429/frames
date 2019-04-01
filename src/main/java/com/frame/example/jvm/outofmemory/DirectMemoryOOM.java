package com.frame.example.jvm.outofmemory;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 本机直接内存溢出 
 * VM Args: -Xmx20M -XX:MaxDirectMemorySize=10M
 * @author wangzhilong
 * 2016年4月18日上午9:02:23
 */
public class DirectMemoryOOM {
	
	private static final int _1MB = 1024 * 1024 ;
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Field unsafeField = Unsafe.class.getDeclaredFields()[0];
		unsafeField.setAccessible(true);
		Unsafe safe = (Unsafe)unsafeField.get(null);
		while(true){
			safe.allocateMemory(_1MB);
		}
	}
	
}
