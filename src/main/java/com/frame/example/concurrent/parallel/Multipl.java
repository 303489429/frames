package com.frame.example.concurrent.parallel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Multipl implements Runnable {
	public static BlockingQueue<Msg> bq = new LinkedBlockingDeque<Msg>();
	public void run() {
		while(true){
			try {
				Msg msg = bq.take() ;
				msg.i = msg.i * msg.j ;
				Div.bq.add(msg) ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
