package com.mensagens;

import javax.swing.JOptionPane;

public class Mensagem {

	public static void alertar(String message){
		JOptionPane.showMessageDialog(null, message, "Aviso", JOptionPane.WARNING_MESSAGE);
	}
	
	public static void informar(String message) {
		JOptionPane.showMessageDialog(null, message, "Concluido", JOptionPane.DEFAULT_OPTION);
	}
	
	public static String inputNomeRelatorio(String message){
		return JOptionPane.showInputDialog(null, message, "Nome do relatório", JOptionPane.OK_CANCEL_OPTION);
	}
	
}
