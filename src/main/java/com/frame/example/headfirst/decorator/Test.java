package com.frame.example.headfirst.decorator;

public class Test {

	public static void main(String[] args) {
		TheGreatestSage sage = new MoneyKey() ;
		TheGreatestSage bird = new Bird(sage) ;
		TheGreatestSage fish = new Flsh(bird);
		fish.move();
		TheGreatestSage sage2 = new Bird(new Flsh(new MoneyKey()));
		sage2.move();
	}
	
}
