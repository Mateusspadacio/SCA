package com.file.handler;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;

import com.enums.Linguagens;
import com.view.TelaPrincipalView;

public class FileHandler {

	private TelaPrincipalView view;
	private String path = "";
	private Vector<File> pathList;

	public FileHandler(TelaPrincipalView view) {
		this.view = view;
		pathList = new Vector<File>();
	}

	public void selecionarProjeto() {
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

	public boolean abrirProjeto() {
		if (!verificarPath()) {
			selecionandoArquivos();
			return true;
		}
		return false;
	}

	private boolean verificarPath() {
		return path.equals("");
	}

	private void selecionandoArquivos() {
		populandoListaDeArquivos(new File(path));
	}
	
	private void populandoListaDeArquivos(File dir){
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				populandoListaDeArquivos(files[i]);
			} else if (verificaExtensoes(files[i].getPath())) {
				pathList.addElement(files[i]);
			}
		}
	}
	
	private boolean verificaExtensoes(String path) {
		for (String ext : extensoes()) {
			if (path.endsWith(ext)) {
				return true;
			}
		}
		
		return false;
	}

	private List<String> extensoes() {
		return Arrays.asList(Linguagens.JAVA.toString(), Linguagens.PHP.toString(), Linguagens.C_SHARP.toString(), Linguagens.CPP.toString()
				, Linguagens.H.toString());
	}
	
	public Vector<File> getPathList() {
		return pathList;
	}
}
