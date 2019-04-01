package com.frame.example.concurrent.sort;
/**
 * 希尔排序  将整个数组间隔h分隔为若干个子数组
 * @author wangzhilong
 * 2016年6月6日上午8:17:10
 */
public class ShellSort {
	
	public static void shellSord(int[] arr){
		int h = 1 ; //计算出最大的h值
		while(h <= arr.length / 3){
			h = h*3 + 1 ;
		}
		while(h>0){
			for (int i = 0; i < arr.length; i++) {
				if(arr[i] < arr[i-h]){
					int temp = arr[i] ;
					int j=i-h;
					while(j>=0 && arr[j] > temp){
						arr[j+h] = arr[j] ;
						j -= h ;
					}
					arr[j+h] = temp ;
				}
			}
			h = (h-1) / 3 ;
		}
	}
	
	public static void print(int arr[] ,int j,int type){
		for (int i = 0; i < arr.length; i++) {
			if(i == 0)
				System.out.print("i="+j+",type="+type+"arr=[");
			System.out.print(arr[i]);
			System.out.print(" ");
			if(i == arr.length -1)
				System.out.print("]");
		}
		System.out.println(" ");
	}
	
	public static void main(String[] args) {
		int arr[] = {5,52,6,3,4} ;
		shellSord(arr);
		for (int ar:arr  ) {  
	          System.out.println(ar);  
	     } 
	}

}
