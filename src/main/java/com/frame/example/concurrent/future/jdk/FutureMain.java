package com.frame.example.concurrent.future.jdk;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureMain {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//构造futureTask
		FutureTask<String> task = new FutureTask<String>(new RealData("a")) ;
		ExecutorService ex = Executors.newFixedThreadPool(1) ;
		//执行futureTask，相当于上例中的client.request('a') 发送请求
		//在这里开启线程进行RealData的call()执行
		ex.submit(task) ;
		System.out.println("请求完毕");
//		Thread.sleep(1000); //模拟额外任务开销
		//相当于data.getResult()方法，取得call()方法的返回值 如果此时call()方法没有执行完成，则依然会等待
		System.out.println("数据="+task.get());
	}
}
