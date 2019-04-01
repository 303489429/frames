package com.frame.example.headfirst.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Java 动态代理
 * @author wangzhilong
 * 2016年4月15日下午4:58:32
 */
public class DynameicProxy implements InvocationHandler {

	private Object target;  
	
	/** 
     * 绑定委托对象并返回一个代理类 
     * @param target 
     * @return 
     */  
	public Object bind(Object target){
		this.target = target ;
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this) ;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result=null;  
        System.out.println("事物开始");  
        //执行方法  
        result=method.invoke(target, args);  
        System.out.println("事物结束");  
        return result;  
	}
	
	public static void main(String[] args) {
		DynameicProxy dynameicProxy = new DynameicProxy();
		Object obj = dynameicProxy.bind(new BookImpl());
		if(obj instanceof Book){
			Book book = (Book)obj ;
			book.addBook();
		}else{
			System.out.println("err obj="+obj.getClass().getName());
			System.out.println("obj="+obj.getClass().getMethods());
		}
	}
	
}

//Book接口
interface Book{
	void addBook() ;
}

class BookImpl implements Book{

	public void addBook() {
		System.out.println("添加图书……");
	}
	
}



