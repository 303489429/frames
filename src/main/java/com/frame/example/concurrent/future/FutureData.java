package com.frame.example.concurrent.future;

public class FutureData implements Data {
	protected RealData realData = null ; //FutureData是RealData的包装（包装即用到了组合模式）
	protected boolean isReady = false;  
	public synchronized void setResult(RealData realData){
		if(isReady){
			return ;
		}
		this.realData = realData ;
		isReady = true ;
		notifyAll();
	}
	
	public synchronized String getResult() {
		while(!isReady){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return realData.result;
	}
	
}
