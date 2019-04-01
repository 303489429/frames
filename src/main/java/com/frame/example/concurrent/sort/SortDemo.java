package com.frame.example.concurrent.sort;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SortDemo {
	static int exchangeFlag=1;  
	  static ExecutorService pool= Executors.newCachedThreadPool();  
	  static int[] array={1,4,2,6,35,3};  
	  
	  static synchronized void setExchangeFlag(int v){  
	      exchangeFlag=v;  
	  }  
	  static synchronized int getExchangeFlag(){  
	      return exchangeFlag;  
	  }  
	  public static class OddEventSortTask implements  Runnable{  
	      int i;  
	      CountDownLatch latch;  
	      public OddEventSortTask(int i,CountDownLatch latch){  
	          this.i=i;  
	          this.latch=latch;  
	      }  
	  
	  
	      public void run() {  
	          if (array[i]>array[i+1]){  
	              int temp=array[i];  
	              array[i]=array[i+1];  
	              array[i+1]=temp;  
	              setExchangeFlag(1);  
	          }  
	          latch.countDown();  
	      }  
	  }  
	  public static void pOddEventSort(int[] arr) throws InterruptedException {  
	      int start=0;  
	      while (getExchangeFlag()==1||start==1){  
	          setExchangeFlag(0);  
	          //偶数的数组长度,当start=1时候,只有len/2-1 个线程  
	          CountDownLatch latch=new CountDownLatch(arr.length/2-(arr.length%2==0?start:0));  
	          for (int i = start; i < arr.length; i+=2) {  
	              pool.submit(new OddEventSortTask(i,latch));  
	          }  
	          //等待所有县城结束  
	          latch.await();  
	          if (start==0){  
	              start=1;  
	          }else {  
	              start=0;  
	          }  
	      }  
	  }  
	  
	  public static void main(String[] args) throws InterruptedException {  
	      pOddEventSort(array);  
	      for (int ar:array  
	           ) {  
	          System.out.println(ar);  
	      } 
	      pool.shutdown();
	  } 
}
