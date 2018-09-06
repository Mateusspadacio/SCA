package com.main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.controller.MainViewController;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.view.MainView;



public class StartProject {

	public static void main(String[] args) {
		
		renderLookAndFeel();
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainViewController(new MainView());
			}
		});

	}
	
	private static void renderLookAndFeel() {
		try {
			UIManager.setLookAndFeel(new HiFiLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

}
