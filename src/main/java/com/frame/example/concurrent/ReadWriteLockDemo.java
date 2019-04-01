package com.frame.example.concurrent;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
	private static Lock lock = new ReentrantLock();
	private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock() ; //读写锁
	private static Lock readLock = readWriteLock.readLock(); //读锁
	private static Lock writeLock = readWriteLock.writeLock() ; //写锁
	private int value ; 
	
	
	public Object handleRead(Lock lock) throws InterruptedException {
		try {
			lock.lock();  //模拟读写操作
			Thread.sleep(1000); //读操作的耗时越多，读写锁的优势越明显
			return value ;
		} finally {
			lock.unlock();
		}
	}
	
	public void handleWrite(Lock lock,int index) throws InterruptedException{
		try {
			lock.lock(); //模拟写锁
			Thread.sleep(1000);
			value = index ;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final ReadWriteLockDemo demo = new ReadWriteLockDemo();
		Runnable readRunnable = new Runnable() {
			public void run() {
				try {
					demo.handleRead(readLock);  //使用读写锁的读锁，并行（非阻塞）
//					demo.handleRead(lock) ; //使用普通重入锁
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
				
			}
		};
		
		Runnable writeRunnable = new Runnable() {
			public void run() {
				try {
					demo.handleWrite(writeLock, new Random().nextInt()); //使用读写锁的写锁，阻塞
//					demo.handleWrite(lock, new Random().nextInt()); //使用重入锁
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		for (int i = 0; i < 18; i++) {
			new Thread(readRunnable).start();
		}
		
		for (int i = 18; i < 20; i++) {
			new Thread(writeRunnable).start();
		}
	}
	
	
	
}	
