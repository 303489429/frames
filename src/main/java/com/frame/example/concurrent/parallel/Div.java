package com.frame.example.concurrent.parallel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Div implements Runnable {
	public static BlockingQueue<Msg> bq = new LinkedBlockingDeque<Msg>() ;
	public void run() {
		while(true){
			try {
				Msg msg = bq.take() ;
				msg.i = msg.i / 2 ;
				long endTime= System.currentTimeMillis() ;
				System.out.println(msg.orgStr+",endTime="+endTime + ",=" + msg.i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
