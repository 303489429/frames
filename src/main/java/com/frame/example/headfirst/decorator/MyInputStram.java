package com.frame.example.headfirst.decorator;

import java.io.FilterInputStream;
import java.io.InputStream;

public class MyInputStram extends FilterInputStream{

	protected MyInputStram(InputStream in) {
		super(in);
	}

	
}
