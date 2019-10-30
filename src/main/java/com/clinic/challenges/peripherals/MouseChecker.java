package com.clinic.challenges.peripherals;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MouseChecker extends JPanel implements MouseListener, Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static boolean m = true;
	
	public MouseMusic music;
	
	public MouseChecker() {
		// Can be implemented, if needed for some initialization
	}
	
	public void mousePressed(MouseEvent e) {
		m = false;
		synchronized (music.lock) {
			music.lock.notifyAll();
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		m = true;
		synchronized (music.lock) {
			music.lock.notifyAll();
		}
	}

	@Override
	public void mouseClicked(MouseEvent paramMouseEvent) {
		System.out.println("Mouse Click happened at : " + paramMouseEvent.getX() + " : " + paramMouseEvent.getY());
	}

	@Override
	public void mouseEntered(MouseEvent paramMouseEvent) {
		System.out.println("Mouse Entered at : " + paramMouseEvent.getX() + " : " + paramMouseEvent.getY());
	}

	@Override
	public void mouseExited(MouseEvent paramMouseEvent) {
		System.out.println("Mouse Exited at : " + paramMouseEvent.getX() + " : " + paramMouseEvent.getY());
	}

	@Override
	public void run() {
		JFrame frame = new JFrame("MouseMusic");
		
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent newContentPane = this;
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.setAlwaysOnTop(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
		addMouseListener(this);
		music = new MouseMusic();
		new Thread(music).start();
	}
}
