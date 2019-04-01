package com.frame.example.concurrent.mode.product;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
	
	private volatile boolean isRunning = true; 
	private BlockingQueue<PCData> queue ;  //内存缓冲区
	private static AtomicInteger count = new AtomicInteger() ;  //总数，原子操作
	private static final int SLEEPTIME = 1000 ;  //随眠时间
	
	public Producer(BlockingQueue<PCData> queue){
		this.queue = queue ;
	}
	public void run() {
		PCData data = null ;
		Random r = new Random();
		System.out.println("Start producer id="+Thread.currentThread().getId());
		try {
			while(isRunning){
				Thread.sleep(r.nextInt(SLEEPTIME));
				data = new PCData(count.incrementAndGet()) ;
				System.out.println(data + "is put into queue");
				if(queue.offer(data, 2, TimeUnit.SECONDS)){  //提交到数据缓存区
					System.err.println("failed to put data:"+data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	public void stop(){
		isRunning = false ;
	}
}
