package com.frame.example.jvm.outofmemory;
/**
 * VM arges -Xss128k    //设置线程栈内存容量大小
 * @author wangzhilong
 * 2016年4月15日上午8:02:07
 */
public class JavaVMStackSOF {
	
	private int stackLength = 1 ; 
	
	public void stackLeak(){
		stackLength ++ ;
		stackLeak();
	}
	
	public static void main(String[] args) throws Throwable {
		JavaVMStackSOF oom = new JavaVMStackSOF();
		try {
			oom.stackLeak();
		} catch (Throwable e) {
			System.err.println("stack length:"+oom.stackLength);
			throw e ;
		}
	}
	
}
