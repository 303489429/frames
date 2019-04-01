package com.frame.example.concurrent.mode.product;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
	private BlockingQueue<PCData> queue ; //缓冲区
	private final static int SLEEPTIME = 1000 ;
	
	public Consumer(BlockingQueue<PCData> queue){
		this.queue = queue ;
	}
	public void run() {
		System.out.println("start consumer id="+Thread.currentThread().getId());
		Random r = new Random() ;
		try {
			while(true){
				PCData data = queue.take() ; //提取任务如果没有任务则等待，知道有任务
				if(data != null){
					int re = data.getData() * data.getData() ; //计算平方
					System.out.println(MessageFormat.format("{0}*{1}={2}", data.getData(),data.getData(),re));
					Thread.sleep(r.nextInt(SLEEPTIME));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

}
