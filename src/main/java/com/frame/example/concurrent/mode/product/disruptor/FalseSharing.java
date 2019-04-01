package com.frame.example.concurrent.mode.product.disruptor;

public class FalseSharing implements Runnable {
	public final static int NUM_THREADS = 2 ; //changge
	public final static long ITERATIONS = 500L * 1000L*1000L ;
	private final int arrayIndex ; 
	
	private static VolatileLong[] longs = new VolatileLong[NUM_THREADS] ;
	static{
		for (int i = 0; i < longs.length; i++) {
			longs[i] = new VolatileLong();
		}
	}
	public FalseSharing(final int arrayIndex){
		this.arrayIndex = arrayIndex ;
	}
	
	private static void runTest() throws Exception{
		Thread[] threads = new Thread[NUM_THREADS] ;
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new FalseSharing(i)) ;
		}
		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			thread.join();
		}
	}
	public void run() {
		long i = ITERATIONS + 1 ;
		while(0 != --i){
			longs[arrayIndex].value = i ;
		}
	}
	
	public static void main(String[] args) throws Exception {
		final long start = System.currentTimeMillis() ;
		runTest();
		System.out.println("duration = "+(System.currentTimeMillis() - start));
	}
	
	
	public final static class VolatileLong{
		public volatile long value = 0L ;
		public long p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15 ; //伪共享方案 经测算，本机当前cpu cache为128字节 
	}
}
