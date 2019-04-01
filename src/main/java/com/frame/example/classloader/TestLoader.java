package com.frame.example.classloader;

public class TestLoader {
	
	public static void main(String[] args) {
		
		ClassLoader classLoader = TestLoader.class.getClassLoader() ; 
		System.out.println("调用加载器1"+classLoader);
		
		ClassLoader clExt = classLoader.getParent() ; 
		System.out.println("调用加载器2"+clExt);
		ClassLoader root = clExt.getParent();  //系统类
		System.out.println("调用加载器3"+root);
	}
	
	
	
}
