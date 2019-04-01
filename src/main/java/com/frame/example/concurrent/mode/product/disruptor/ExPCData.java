package com.frame.example.concurrent.mode.product.disruptor;

public class ExPCData {
	private long value ;

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ExPCData [value=" + value + "]";
	}
}
