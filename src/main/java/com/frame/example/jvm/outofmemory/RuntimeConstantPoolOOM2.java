package com.frame.example.jvm.outofmemory;

public class RuntimeConstantPoolOOM2 {

	public static void main(String[] args) {
		String str1 = new StringBuilder("计算机").append("科学").toString() ;
		//JDK1.6中，intern()方法会把首次遇到的字符串实例复制到永久代（常量池）中，返回也是永久代中这个字符串实例的引用
		//JDK1.7开始逐步“去永久代”特指HotSpot虚拟机，intern()方法实现不会再复制实例
		System.out.println(str1.intern() == str1); //JDK1.6返回false JDK1.7返回true
		String str2 = new StringBuilder("ja").append("va").toString();
		System.out.println(str2.intern() == str2);
	}
	
}
