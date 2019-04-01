package com.frame.example.concurrent.mode.product.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExMain {
	public static void main(String[] args) throws InterruptedException {
//		Executor executor = Executors.newCachedThreadPool() ;
		ThreadFactory executor = Executors.defaultThreadFactory();
		PCDataFactory factory = new PCDataFactory();
		int bufferSize = 1024 ; //缓冲区大小 disruptor 规定 缓冲区大小必须为2的整数次方
		Disruptor<ExPCData> disruptor = new Disruptor<ExPCData>(
				factory, 
				bufferSize,
				executor, 
				ProducerType.MULTI,
//				new BlockingWaitStrategy() //默认策略，高并发环境下最糟糕的一种等待策略
//				new SleepingWaitStrategy() //CPU使用率非常保守
				new YieldingWaitStrategy() //最好有多余消费者线程数的逻辑CPU数量
//				new BusySpinWaitStrategy() //多余消费者线程数的物理CPU数量（很恐怖）
		);
		
		disruptor.handleEventsWithWorkerPool(
			new ExConsumer(),
			new ExConsumer(),
			new ExConsumer(),
			new ExConsumer(),
			new ExConsumer(),
			new ExConsumer()
		);
		disruptor.start() ;
		
		RingBuffer<ExPCData> ringBuffer = disruptor.getRingBuffer();
		ExProducer producer = new ExProducer(ringBuffer) ;
		ByteBuffer bb = ByteBuffer.allocate(8) ;
		for (long i = 0; true; i++) {
			bb.putLong(0, i);
			producer.pushData(bb);
			System.out.println("add data "+ i);
			Thread.sleep(100);
		}
	}
}
