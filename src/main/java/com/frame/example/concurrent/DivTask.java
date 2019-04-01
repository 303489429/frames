package com.frame.example.concurrent;



import com.frame.example.concurrent.ext.TraceThreadPoolExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class DivTask implements Runnable {
	
	int a , b ;
	public DivTask(int a, int b){
		this.a= a ;
		this.b= b;
	}
	public void run() {
		double re = a/b ;
		System.out.println("re="+re);
	}

	public static void main(String[] args) {
		ExecutorService exec = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 0L,
				TimeUnit.SECONDS, 
				new SynchronousQueue<Runnable>()); 
		//SynchronousQueue 队列是一种直接提交的队列，使用这个队列的任务不会被真实保存，而总是将新任务提交给线程执行
		//如果没有空闲的进程则创建新的进程，如果进程数量已经达到最大值，则执行拒绝策略，因此SynchronousQueue队列通常设置很大的线程数（maximumPoolSize）
		//直接提交的队列
		//有界任务队列 ArrayBlockingQueue
		//无界任务队列LinkBlockingQueue
		//优先级任务队列PriorityBlockingQueue
		for (int i = 0; i < 5; i++) {
//			exec.submit(new DivTask(100, i)) ;  //无异常打印
			exec.execute(new DivTask(100,i));  //有堆着异常打印
		}
	}
}
