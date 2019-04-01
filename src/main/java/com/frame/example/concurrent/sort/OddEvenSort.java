package com.frame.example.concurrent.sort;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OddEvenSort {
	static ExecutorService pool = Executors.newCachedThreadPool();
	static int[] arr = {5,52,6,3,4};
	static int exchFlag = 1 ;
	
	static synchronized void setExchFlag(int v){
		exchFlag = v ;
	}
	static synchronized int getExchFlag(){
		return exchFlag ;
	}
	
	public static class OddEvenSortTask implements Runnable{
		int i;
		CountDownLatch latch ;
		public OddEvenSortTask(int i , CountDownLatch latch){
			this.i = i ;
			this.latch = latch ;
		}
		public void run() {
			if(arr[i] > arr[i+i]){
				int temp = arr[i] ;
				arr[i] = arr[i+1] ;
				arr[i+1] = temp ;
				setExchFlag(1);
			}
			latch.countDown();
		}
	}
//	public static void pOddEvenSort(int[] arry) throws InterruptedException{
//		int start = 0 ;
//		while(getExchFlag() == 1 || start == 1){
//			setExchFlag(0);
//			//偶数的数组长度,当start=1时候,只有len/2-1 个线程          arr.length/2-(arr.length%2==0?start:0)
//			CountDownLatch latch = new CountDownLatch(arry.length/2-(arry.length%2==0?start:0));
//			for (int i = start; i < arry.length; i+=2) {
//				pool.submit(new OddEvenSortTask(i, latch));
//			}
//			//等待所有线程结束
//			latch.await();
//			if (start==0){  
//	            start=1;  
//	        }else {  
//	            start=0;  
//	        }  
//		}
//	}
	
	public static void pOddEvenSort(int[] arry) throws InterruptedException {  
	      int start=0;  
	      while (getExchFlag()==1||start==1){  
	    	  setExchFlag(0);  
	          //偶数的数组长度,当start=1时候,只有len/2-1 个线程  
	          CountDownLatch latch=new CountDownLatch(arry.length/2-(arry.length%2==0?start:0));  
	          for (int i = start; i < arry.length; i+=2) {  
	              pool.submit(new SortDemo.OddEventSortTask(i,latch));
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
	
	//普通奇偶排序
	public static void oddEvenSort(int[] arr){
		int exchFlag = 1 ,start = 0 ;  //是否发送数据交换和排方式：奇数 偶数
		while(exchFlag == 1 || start == 1){
			exchFlag = 0 ;
			for (int i = start; i < arr.length-1; i+=2) {
				if(arr[i] > arr[i+1]){
					int temp = arr[i] ;
					arr[i] = arr[i+1] ;
					arr[i+1] = temp ;
					exchFlag =1 ;
				}
			}
			if(start == 0)
				start =1 ;
			else
				start = 0 ;
		}
		System.out.println(arr);
	}
	
	public static void oddEvenSort2(int[] ary) {  
        //奇偶排序  
        boolean flag = true;  
        while (flag) {  
            boolean odd = false, even = false;  
            for (int i = 0; i < ary.length - 1; i+=2) {  
                if (ary[i] > ary[i + 1]) {  
                    ary[i] = ary[i + 1] + 0 * (ary[i + 1] = ary[i]);    //此种算法高明
                    odd = true;  
                }   
            }  
            for (int i = 1; i < ary.length - 1; i+=2) {  
                if (ary[i] > ary[i + 1]) {  
                    ary[i] = ary[i + 1] + 0 * (ary[i + 1] = ary[i]);  
                    even = true;  
                }   
            }  
            flag = odd || even; //若为false，表示不论奇偶序列，一个符合条件的比较都没有  
        }  
    }  
	
	public static void main(String[] args) throws InterruptedException {
//		oddEvenSort(arr);
//		oddEvenSort2(arr);
		pOddEvenSort(arr);
		
		for (int i = 0; i < arr.length; i++) {
			if(i == 0)
				System.out.print("arr=[");
			System.out.print(arr[i]);
			System.out.print(" ");
			if(i == arr.length -1)
				System.out.print("]");
		}
	}

}
