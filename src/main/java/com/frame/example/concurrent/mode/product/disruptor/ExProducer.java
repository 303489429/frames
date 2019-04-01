package com.frame.example.concurrent.mode.product.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class ExProducer {
	
	private final RingBuffer<ExPCData> ringBuffer ;
	public ExProducer(RingBuffer<ExPCData> ringBuffer){
		this.ringBuffer = ringBuffer ;
	}
	public void pushData(ByteBuffer bb){
		long sequence = ringBuffer.next() ; //获取下一个序号
		try {
			ExPCData event = ringBuffer.get(sequence);
			event.setValue(bb.getLong(0)); //fill with data
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			ringBuffer.publish(sequence);  //数据发布，只有发布后的数据才会真正被消费者看见
		}
	}
	
	
}
