package com.frame.example.concurrent.future;

public class RealData implements Data {
	
	protected final String result ;
	
	public RealData(String para){
		//RealData的构造可能很慢 需要用户等很久 这里使用sleep模拟
		StringBuffer sb = new StringBuffer() ;
		for (int i = 0; i < 10; i++) {
			sb.append(para);
			try {
				Thread.sleep(100);  //这里使用sleep，代替一个很慢的操作过程
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.result = sb.toString() ;
	}
	
	public String getResult() {
		return result;
	}

}
