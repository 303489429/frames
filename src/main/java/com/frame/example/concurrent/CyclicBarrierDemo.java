package com.frame.example.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.LockSupport;

public class CyclicBarrierDemo  {
	
	private static class Soldier implements Runnable{
		private String soldier ; //
		private final CyclicBarrier cyclic ; //定义循环栅栏
		
		public Soldier(String soldierName , CyclicBarrier cyclic){
			this.soldier=soldierName ;
			this.cyclic=cyclic ;
		}
		void doWork(){
			try {
				Thread.sleep(Math.abs(new Random().nextInt() % 10000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(soldier+"：任务完成");
		}
		public void run() {
			try {
				cyclic.await() ;//等待所有士兵到齐
				doWork();
				cyclic.await() ; //等待所有士兵完成任务
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			} 
			
		}
	}
	
	public static class BarrierActionRun implements Runnable{
		boolean flag ;
		int n ; 
		public BarrierActionRun(boolean flag,int n){
			this.flag=flag;
			this.n = n ;
		}
		public void run() {
			if(flag){
				System.out.println("司令：[士兵]"+ n + "个，任务完成！");
			}else{
				System.out.println("司令：[士兵]"+ n + "个，集合完毕！");
				flag=true ;
			}
		}
	}
	
	public static void main(String[] args) {
		final int n= 10;
		boolean flag = false ; //定义士兵是否到齐标识
		Thread[] allSoldier = new Thread[10] ; //定义10个士兵
		CyclicBarrier cb = new CyclicBarrier(n, new BarrierActionRun(flag, n)); //使用barrierAction 初始化栅栏，当计数器一次计数完成后执行barrierAction动作
		System.out.println("准备集合队伍：");
		for (int i = 0; i < n; i++) {
			LockSupport.parkNanos(1000*1000*1000*1); //线程阻塞工具类 ，纳秒级别的阻塞
			System.out.println("士兵"+i+"报道");
			allSoldier[i] = new Thread(new Soldier("士兵"+i, cb));
			allSoldier[i].start();
		}
	}

	
}
