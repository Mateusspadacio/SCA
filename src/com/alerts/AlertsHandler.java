package com.alerts;

import javax.swing.JOptionPane;

public class AlertsHandler {

	public static void alert(String message){
		JOptionPane.showMessageDialog(null, message, "Aviso", JOptionPane.WARNING_MESSAGE);
	}
	
	public static void inform(String message) {
		JOptionPane.showMessageDialog(null, message, "Concluido", JOptionPane.DEFAULT_OPTION);
	}
	
	public static String inputReportName(String message){
		return JOptionPane.showInputDialog(null, message, "Nome do relatório", JOptionPane.OK_CANCEL_OPTION);
	}
	
}
