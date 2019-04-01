package com.frame.example.concurrent;

import java.util.concurrent.*;

public class RejectThreadPoolDemo {
	
	public static class MyTask implements Runnable{
		public String name ;
		
		public MyTask(String name){
			this.name= name ;
		}
		public void run() {
			System.err.println("正在执行: Thread Id:"+Thread.currentThread().getId());
			try {
//				Thread.sleep(100); //此处睡眠后无法完成线程执行
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		//测试固定大小线程池
		/*ExecutorService exec = Executors.newFixedThreadPool(10) ;  //使用的是无界队列实现
		Thread.sleep(5000);
		for (;;) {
			exec.submit(new MyTask());
		}*/
		//自定义线程池
		ExecutorService exec = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingDeque<Runnable>(10),
//			Executors.defaultThreadFactory(), //默认线程工厂
			new ThreadFactory() { //匿名内部类方式实现：自定义线程工厂
				public Thread newThread(Runnable r) {
					Thread thread = new Thread(r);
					thread.setDaemon(true); //设置线程为守护线程,主线程退出，将会强制销毁线程池
					System.out.println("create thread:"+thread);
					return thread;
				}
			},
			new RejectedExecutionHandler() {  //拒绝策略
				public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
					System.out.println(r.toString()+" is discard");
			}
		}){
			protected void beforeExecute(Thread t, Runnable r) {
				System.out.println("准备执行："+((MyTask)r).name); //submit提交时，此处无法转换
//				System.out.println("准备执行："+t.getName());
			}
			
			protected void afterExecute(Runnable r, Throwable t) { 
				System.out.println("执行完成："+((MyTask)r).name); //submit提交时，此处无法转换
//				System.out.println("执行完成："+r);
			}
			 protected void terminated() { 
				 System.out.println("线程退出");
			 }
		};
		
		for(int i=0;i<5;i++){
//			exec.submit(new MyTask("my task "+i)); //subimt 和 execute 二则存在区别
			exec.execute(new MyTask("my task "+i));
			Thread.sleep(10);
		}
		exec.shutdown();
	}
	
}
