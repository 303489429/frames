package com.frame.example.concurrent.parallel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Plus implements Runnable {
	public static BlockingQueue<Msg> bq = new LinkedBlockingDeque<Msg>() ;
	public void run() {
		while(true){
			try {
				Msg msg = bq.take() ;
				msg.j = msg.i + msg.j ;//相加
				Multipl.bq.add(msg) ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
