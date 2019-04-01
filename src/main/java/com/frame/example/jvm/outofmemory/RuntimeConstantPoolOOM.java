package com.frame.example.jvm.outofmemory;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args : -XX:PermSize=10M -XX:MaxPermSize=10M
 * JDK 1.6 和 JDK 1.7中区别很大，JDK1.7中已经已经将常量池从永久代中移除
 * JDK 1.7开始逐步“去永久代” 的事情
 * @author wangzhilong
 * 2016年4月15日上午8:37:08
 */
public class RuntimeConstantPoolOOM {
	
	public static void main(String[] args) throws InterruptedException {
		//使用 list 保持着常量池引用，避免FULL GC 回收常量池行为
		List<String> list = new ArrayList<String>();
		//10MB 的PermSize在integer 范围内足够产生OOM了
		int i = 0 ;
		while(true){
			list.add(String.valueOf(i++).intern()) ;
		}
	}
	
}
