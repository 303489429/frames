package com.frame.example.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiRandomTest {
	public static final int GEN_COUNT = 1000*10000 ;
	public static final int THREAD_COUNT =4 ;
	static ExecutorService exe = Executors.newFixedThreadPool(THREAD_COUNT);
	public static Random rnd = new Random(123) ;
	
	public static ThreadLocal<Random> trnd = new ThreadLocal<Random>(){
		protected Random initialValue() {
	        return new Random(123);
	    }
	} ;
	//TODO 待续
	
}
