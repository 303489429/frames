package com.frame.example.headfirst.decorator;

public class Bird extends Decorator{

	public Bird(TheGreatestSage sage) {
		super(sage);
	}

	public void move(){
		super.move();
		System.out.println("bird move");
	}
	
}
