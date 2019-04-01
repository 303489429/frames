package com.frame.example.headfirst.proxy;
/**
 * 静态代理类
 * 每一个代理类只能为一个接口服务，这样一来程序开发中必然会产生过多的代理，而且，所有的代理操作除了调用的方法不一样之外，其他的操作都一样，则此时肯定是重复代码。
 * 解决这一问题最好的做法是可以通过一个代理类完成全部的代理功能，那么此时就必须使用动态代理完成。 再来看一下动态代理： JDK动态代理中包含一个类和一个接口： 
 * @author wangzhilong
 * 2016年4月15日下午1:28:34
 */
public class ProxyCount implements Count {

	private CountImpl impl ; 
	
	public ProxyCount(CountImpl impl){
		this.impl = impl ;
	}
	
	public void count() {
		 System.out.println("事务处理之前");  
        // 调用委托类的方法;  
		 impl.count();  
        System.out.println("事务处理之后");  
	}

}
