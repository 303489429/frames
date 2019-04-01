package com.frame.example.headfirst.decorator;

public class Flsh extends Decorator{

	public Flsh(TheGreatestSage sage) {
		super(sage);
	}
	
	public void move(){
		super.move();
		System.out.println("flsh move");
	}
	
}
