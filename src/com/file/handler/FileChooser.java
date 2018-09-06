package com.file.handler;

import java.io.File;

import javax.swing.JFileChooser;

public class FileChooser {
	
	public static String choosePath() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Salvar relatório");
		fc.setApproveButtonText("Salvar");
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		
		if (file == null) {
			return "";
		}
		
		return file.getPath();
	}

}
