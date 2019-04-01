package com.frame.example.concurrent.ext;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TraceThreadPoolExecutor extends ThreadPoolExecutor {

	public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	//包装异常堆栈信息
	private Runnable wrap(final Runnable task, final Exception clientStack,String clientThreadName){ 
		return new Runnable() {
			public void run() {
				try {
					task.run();  //将传入的Runnable进行包装，使之能处理异常，当异常发生时能这个异常会被打印
				} catch (Exception e) {
					clientStack.printStackTrace();
					e.printStackTrace();
				}
			}
		};
	}
	
	private Exception clientTrace(){
		return new Exception("client stack trace:");
	}
	
	public void execute(Runnable command) {
		super.execute(wrap(command, clientTrace(), Thread.currentThread().getName()));
	}
 
	public Future<?> submit(Runnable task) {
		return super.submit(wrap(task, clientTrace(), Thread.currentThread().getName()));
	}
	
	 
	
}
