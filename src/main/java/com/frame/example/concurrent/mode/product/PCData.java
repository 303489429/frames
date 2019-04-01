package com.frame.example.concurrent.mode.product;

public final class PCData {
	private final int initData ; //数据
	public PCData(int d){
		this.initData = d ;
	}
	public PCData(String d){
		this.initData = Integer.valueOf(d);
	}
	public int getData(){
		return this.initData ;
	}
	@Override
	public String toString() {
		return "PCData [initData=" + initData + "]";
	}
}
