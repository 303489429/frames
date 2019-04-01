package com.frame.example.concurrent;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicReferenceDemo {
//	static AtomicReference<Integer> money = new AtomicReference<Integer>(19) ;
	
	static AtomicStampedReference<Integer> money2 = new AtomicStampedReference<Integer>(19, 0); //带时间戳的原子对象引用

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			final int timestamp = money2.getStamp();
			new Thread(){
				public void run(){
					while(true){
						Integer m2 = money2.getReference();
//						Integer m = money.get() ;
						if(m2 < 20){
							if(money2.compareAndSet(m2, m2+20,timestamp,timestamp+1)){
//								System.out.println("余额小于20元，充值成功，余额："+money.get()+"元");
								System.out.println("余额小于20元，充值成功，余额："+money2.getReference()+"元");
								break;
							}
						}else{
//							System.out.println("余额大于20元,无须充值");
//							break ;
						}
					}
				}
			}.start() ;
		}
		
		
		new Thread(){
			public void run(){
				for (int i = 0; i < 10; i++) {
					while(true){
						int timestamp = money2.getStamp();
						Integer m = money2.getReference();
//						Integer m =money.get() ;
						if(m > 10){
							System.out.println("大于10元");
							if(money2.compareAndSet(m, m-10,timestamp,timestamp+1)){
//								System.out.println("成功消费10元，余额："+money.get()+"元");
								System.out.println("成功消费10元，余额："+money2.getReference()+"元");
								break;
							}
						}else{
							System.out.println("没有足够的金额");
							break ;
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();;
	}
	
}
