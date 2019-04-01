package com.frame.example.headfirst.proxy;
/**
 * 静态代理测试方法
 *  
 * @author wangzhilong
 * 2016年4月15日下午1:30:50
 */
public class TestCount {
	
	public static void main(String[] args) {
		new ProxyCount(new CountImpl()).count();
	}
}
