package com.frame.example.concurrent.parallel;

public class PStreamMain {
	
	public static void test(){
		long startTime = System.currentTimeMillis() ;
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				System.out.println("(("+i+"+"+j+")*"+i+")/2="+(((i+j)*i)/2));
			}
		}
		long endTime = System.currentTimeMillis() ;
		System.out.println("end time="+(endTime-startTime));
	}
	
	public static void test2(){
		new Thread(new Plus()).start();
		new Thread(new Multipl()).start();
		new Thread(new Div()).start();
		long startTime = System.currentTimeMillis() ;
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				Msg msg = new Msg();
				msg.i = i ;
				msg.j = j ;
				msg.orgStr = "time="+startTime+"(("+i+"+"+j+")*"+i+")/2" ;
				Plus.bq.add(msg) ;
			}
		}
	}
	
	public static void main(String[] args) {
//		test2();
		test();
	}
}
