package com.file.handler;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;

import com.enums.Languages;
import com.view.MainView;

public class FileHandler {

	private MainView view;
	private String path = "";
	private static Vector<File> pathList;

	public FileHandler(MainView view) {
		this.view = view;
		pathList = new Vector<File>();
	}

	public void selecProject() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int option = fc.showOpenDialog(view);
		if (option == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			path = file.getPath();
			return;
		}
		path = "";
	}

	public boolean openProject() {
		if (!verifyPath()) {
			selectFiles();
			return true;
		}
		return false;
	}

	private boolean verifyPath() {
		return path.equals("");
	}

	private void selectFiles() {
		fillFileList(new File(path));
	}
	
	private void fillFileList(File dir){
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				fillFileList(files[i]);
			} else if (verifyExtensions(files[i].getPath())) {
				pathList.addElement(files[i]);
			}
		}
	}
	
	private boolean verifyExtensions(String path) {
		for (String ext : extentions()) {
			if (path.endsWith(ext)) {
				return true;
			}
		}
		
		return false;
	}

	private List<String> extentions() {
		return Arrays.asList(Languages.JAVA.toString(), Languages.PHP.toString(), Languages.C_SHARP.toString(), 
				Languages.CPP.toString(), Languages.H.toString());
	}
	
	public Vector<File> getPathList() {
		return pathList;
	}
	
}
