package com.frame.example.headfirst.decorator;
/**
 * 装饰者角色 
 * @author wangzhilong
 * 2016年4月12日下午2:00:39
 */
public class Decorator implements TheGreatestSage { //实现接口
	
	private TheGreatestSage sage ; //组合接口
	
	public Decorator(TheGreatestSage sage){  //构造装饰对象
		this.sage = sage ;
	}
	
	public void move() {
		sage.move();
	}
	
}
