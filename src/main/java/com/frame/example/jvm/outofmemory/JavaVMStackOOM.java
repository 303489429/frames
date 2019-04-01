package com.frame.example.jvm.outofmemory;
/**
 * VM　Args : -Xss2m
 * @author wangzhilong
 * 2016年4月15日上午8:03:43
 */
public class JavaVMStackOOM {
	
	private void dontStop(){
		while(true){
			
		}
	}
	
	public void stackLeakByThread(){
		while(true){
			Thread thread = new Thread(new Runnable() {
				public void run() {
					dontStop();
				}
			});
			thread.start();
		}
	}
	
	public static void main(String[] args) {
		JavaVMStackOOM oom = new JavaVMStackOOM() ;
		oom.stackLeakByThread();
	}
	
}
