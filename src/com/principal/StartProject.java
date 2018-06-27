package com.principal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.controller.TelaPrincipalController;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.view.TelaPrincipalView;



public class StartProject {

	public static void main(String[] args) {
		
		renderizaLookAndFeel();
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new TelaPrincipalController(new TelaPrincipalView());
			}
		});

	}
	
	private static void renderizaLookAndFeel() {
		try {
			UIManager.setLookAndFeel(new HiFiLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

}
