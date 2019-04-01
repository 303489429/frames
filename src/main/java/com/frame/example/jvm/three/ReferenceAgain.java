package com.frame.example.jvm.three;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class ReferenceAgain extends PhantomReference<Object>{

	public ReferenceAgain(Object referent, ReferenceQueue<? super Object> q) {
		super(referent, q);
		System.out.println("系统通知");
	}

}
