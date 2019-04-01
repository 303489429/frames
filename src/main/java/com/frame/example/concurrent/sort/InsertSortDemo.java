package com.frame.example.concurrent.sort;

public class InsertSortDemo {

	public static void insertSort(int[] arr){
		if(arr != null && arr.length > 0){
			int i,j,key ;
			for (i = 1; i < arr.length; i++) {
				//key为要准备插入的元素
				key = arr[i] ; //当前带交换的数据
				j=i-1; //初始化为当前元素的前一个元素
				while(j >= 0 && arr[j] > key ){
					arr[j+1] = arr[j] ; //前一个数复制给后一个数
					j--;
					print(arr,i,1);
				}
				arr[j+1]=key ;
				print(arr,i,2);
			}
			
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
		insertSort(arr);
		
		for (int ar:arr  ) {  
	          System.out.println(ar);  
	     } 
	}

}
