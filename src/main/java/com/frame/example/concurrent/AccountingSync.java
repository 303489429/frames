package com.frame.example.concurrent;

public class AccountingSync implements Runnable{
	static AccountingSync instance = new AccountingSync();
	static volatile int count = 0 ;
	
	public  void increase(){
		synchronized (AccountingSync.class) {
			count++ ;
		}
	}
	
	public void run() {
		for (int i = 0; i < 10000000; i++) {
				increase();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new AccountingSync()) ;
		Thread t2 = new Thread(new AccountingSync()) ;
		t1.start();t2.start();
		t1.join();t2.join();
		System.out.println(count);
	}
}
