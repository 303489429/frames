package com.frame.example.concurrent.mode.product.disruptor;

import com.lmax.disruptor.WorkHandler;

public class ExConsumer implements WorkHandler<ExPCData> {

	public void onEvent(ExPCData event) throws Exception {
		System.out.println(Thread.currentThread().getId()+":Event:--"+event.getValue()*event.getValue() + "--");
	}
	
}
