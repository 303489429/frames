package com.frame.example.concurrent.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并行搜索(数据量越大优势越大) 将一个原数组 拆分成N断进行分别搜索
 * @author wangzhilong
 * 2016年6月1日上午8:02:09
 */
public class SearchDemo {
	
	static int[] arr ;
	static ExecutorService pool = Executors.newCachedThreadPool() ;
	static final int Thread_Num = 2 ;
	static AtomicInteger result = new AtomicInteger(-1) ;
	
	public static int search(int searchValue , int beginPos,int endPos){
		for (int i = beginPos; i < endPos; i++) {
			if(result.get() >=0){
				return result.get() ;
			}
			if(arr[i] == searchValue){
				//如果设置失败，表示其他线程已经先找到了
				if(!result.compareAndSet(-1, i)){  //CAS 比较交换算法 实现无锁控制
					return result.get() ;
				}
				return i ;
			}
		}
		return -1 ;
	}
	
	public static class SearchTask implements Callable<Integer>{
		int begin,end,searchValue ;
		public SearchTask(int begin,int end,int searchValue){
			this.begin= begin;
			this.end = end ;
			this.searchValue = searchValue ;
		}
		public Integer call() throws Exception {
			int re = search(searchValue, begin, end) ;
			return re;
		}
	}
	
	public static int pSearch(int searchValue) throws InterruptedException, ExecutionException{
		int subArrSize = arr.length / Thread_Num + 1;
		List<Future<Integer>> re = new ArrayList<Future<Integer>>();
		for (int i = 0; i < arr.length; i+=subArrSize) {
			int end = i + subArrSize ;
			if(end >= arr.length)
				end = arr.length ;
			re.add(pool.submit(new SearchTask(i, end, searchValue))) ;
		}
		for (Future<Integer> fu : re) {
			if(fu.get() >= 0)
				return fu.get() ;
		}
		return -1 ;
	}
	
	public static void setArrValue(){
		if(arr == null)
			arr = new int[100000000] ;
		Random random = new Random() ;
		for (int i = 0; i < 100000000; i++) {
			arr[i] = random.nextInt(1000) ;
		}
		System.out.println("arr lenght = "+arr.length);
	}
	
	public static void commonSearch(){
		long startTime = System.currentTimeMillis() ;
		int index = search(10001, 0, arr.length) ;
		long endTime = System.currentTimeMillis() ;
		if(index == -1)
			index = 0 ;
		System.out.println("commonSearch arr["+index+"] value="+arr[index]+",time="+(endTime-startTime));
	}
	
	public static void parallelSearch() throws InterruptedException, ExecutionException{
		long startTime = System.currentTimeMillis() ;
		int index = pSearch(10001) ;
		long endTime = System.currentTimeMillis() ;
		if(index == -1)
			index = 0 ;
		System.out.println("parallelSearch arr["+index+"] value="+arr[index]+",time="+(endTime-startTime));
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		setArrValue();
		commonSearch();
		parallelSearch();
		
		System.out.println("29/2="+(29/2));
	}
}
