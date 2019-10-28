package com.clinic.challenges.peripherals;

import static java.awt.Toolkit.getDefaultToolkit;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;

import org.jfugue.Player;


public class MouseMusic implements Runnable {
	
	public Object lock = new Object();
	
	public MouseMusic() {
		
	}

	@Override
	public void run() {
		while(true) {
			synchronized (lock) {
				while(MouseChecker.m) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			Player player = new Player();
			//Read Screen Dimensions and Mouse Position
			
			Point location = MouseInfo.getPointerInfo().getLocation();
			Dimension screenSize = getDefaultToolkit().getScreenSize();
			
			double width = screenSize.getWidth();
			double height = screenSize.getHeight();
			
			int volume = (int)(location.x * 16383 / width);
			int pitch = 127 - (int)(location.y * 127 / height);
			
			player.play("X[Volume]=" + volume + " [" + pitch + "]");
		}
	}
}
