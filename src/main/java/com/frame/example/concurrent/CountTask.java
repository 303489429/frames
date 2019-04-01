package com.frame.example.concurrent;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * fork/join框架 分而治之
 * @author wangzhilong
 * 2016年5月13日下午8:25:20
 */
public class CountTask extends RecursiveTask<Long> { //RecursiveTask 可以携带返回值long
	
	private static final int THESHOLD = 10 ; // 入口 
	private long start ;
	private long end ;
	
	public CountTask(long start,long end){
		this.start = start ;
		this.end = end ;
	}
	
	@Override
	protected Long compute() {
		long sum = 0 ;
		boolean canCompute = (end-start) < THESHOLD ;
		if(canCompute){ 
			for(long i=start ; i<end ; i++){
				sum +=i ;
			}
		}else{//当求和的总数大于THESHOLD时就将任务分解
			//分成100个小任务
			long step = (start+end) / 100 ;  //定义100个任务处理的个数
			ArrayList<CountTask> subTasks = new ArrayList<CountTask>() ; //定义一个集合存放子任务
			long pos = start ; //开始值
			for(int i=0;i < 100 ; i++){
				long lastOne = pos+step ;
				if(lastOne > end){
					lastOne = end ;
				}
				CountTask subTask  = new CountTask(pos, lastOne);
				pos +=step +1 ;
				subTasks.add(subTask);
				subTask.fork(); //使用fork提交子任务
			}
			for(CountTask task : subTasks){
				sum+=task.join() ;
			}
		}
		return sum;
	}
	
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(); //建立forkjoinPool线程池
		CountTask task = new CountTask(0, 200000L) ; //构造计算1到200000求和的任务
		ForkJoinTask<Long> result = forkJoinPool.submit(task) ;  //将任务提交给线程池
		try {
			long res = result.get() ; //此处如果获取值时任务没有结束，则等到任务结束
			System.out.println("sum="+res);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}
