package com.clinic.challenges.peripherals;


public class Main {
	
	public static void main(String[] args) {
		new Thread(new MouseChecker()).start();
	}
}
