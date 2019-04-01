package com.frame.example.concurrent;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class LockFreeVector<E> {
	private final int N_BUCKET = 30 ;
	private final int FIRST_BUCKET_SIZE = 8 ;
	private AtomicReferenceArray<AtomicReferenceArray<E>> buckets  ;
	private AtomicReferenceArray<Descriptor<E>> descriptor ;
	public LockFreeVector(){
		buckets = new AtomicReferenceArray<AtomicReferenceArray<E>>(N_BUCKET) ;
		buckets.set(0, new AtomicReferenceArray<E>(FIRST_BUCKET_SIZE));
//		descriptor = new AtomicReferenceArray<Descriptor<E>>(new Descriptor<E>(0, null)) ;
	}
	
	static class Descriptor<E>{
		public int size ;
		volatile WriteDescriptor<E> writeop ;
		public Descriptor(int size , WriteDescriptor<E> writeop){
			this.size = size ;
			this.writeop = writeop ;
		}
		public void completeWrite(){
			WriteDescriptor<E> tmpop = writeop ;
			if(tmpop != null){
				tmpop.doIt();
				writeop = null ; //this is safe since all write to writeop use 
			}
		}
	}
	
	static class WriteDescriptor<E> {
		public E oldV ;
		public E newV ;
		public AtomicReferenceArray<E> addr ;
		public int addr_ind ;
		
		public WriteDescriptor(AtomicReferenceArray<E> addr, int addr_ind,E oldV, E newV){
			this.addr= addr ;
			this.addr_ind= addr_ind ;
			this.oldV = oldV ;
			this.newV = newV ;
		}
		
		public void doIt(){
			addr.compareAndSet(addr_ind, oldV, newV);
		}
		
	}
	
}
