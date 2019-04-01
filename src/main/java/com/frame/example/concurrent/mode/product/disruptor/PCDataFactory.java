package com.frame.example.concurrent.mode.product.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * PCData工厂类
 * 2016年5月29日上午8:30:11
 */
public class PCDataFactory implements EventFactory<ExPCData> {

	public ExPCData newInstance() {
		return new ExPCData();
	}

}
