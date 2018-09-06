package com.securegaps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.constants.Constants;
import com.constants.FileInfos;
import com.def.methods.DefaultMethods;
import com.load.LoadClasses;
import com.utilities.Utilities;
import com.view.MainView;

public class AnnaliseGapsHandler{

	private Vector<File> pathList;
	private MainView view;
	private boolean isComment = false;
	private List<DefaultMethods> classesList;
	private List<Object> dataList;
	private LoadClasses loadingClasses;

	public AnnaliseGapsHandler(MainView view, Vector<File> pathList) {
		this.view = view;
		this.pathList = pathList;
		loadingClasses = new LoadClasses();
		dataList = new ArrayList<Object>();
	}

	public void startAnalyse() {
		start();
	}

	private void verifyLanguage(String path) {
		loadingClasses.loadLanguage(Utilities.getFileExtension(path));
		classesList = loadingClasses.getClassesInitialized();
	}
	
	private void fileName(File file) {
		FileInfos.FILE_NAME = file.getName();
	}

	private void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				setAnalyseMessage();
				Long start = System.currentTimeMillis();
				lendoArquivos();
				Long end = System.currentTimeMillis();
				setTimeSpent(end - start);
			}
		}).start();
	}

	private void lendoArquivos() {
		String code = "";
		int rowNumber = 1;
		int viewRowNumber = 1;
		String row = "";
		BufferedReader br;
		for (File file : pathList) {
			fileName(file);
			verifyLanguage(file.getAbsolutePath());
			try {
				br = new BufferedReader(new FileReader(file));
				while ((row = br.readLine()) != null) {
					rowNumber++;
					viewRowNumber++;
					code += row + "\n";
					if (verifyComment(row)) {
						continue;
					}
					doAnalyse(row, rowNumber);	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			rowNumber = 1;
			storeData();
			clearSession();
		}

		showLineNumber(viewRowNumber);
		showCode(code);
	}

	private void storeData() {
		List<Object> clonedList = new ArrayList<>();
		for(DefaultMethods classe : classesList){
			try {
				Object obj = (DefaultMethods) classe.cloneObject();
				clonedList.add(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		dataList.add(clonedList);
	}

	private void doAnalyse(String row, int num) {
		for (DefaultMethods classe : classesList) {
			try {
				classe.annaliseGap(row, num);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void clearSession() {
		for (DefaultMethods classe : classesList) {
			try {
				classe.clearSession();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Object> getDataList(){
		return dataList;
	}

	private boolean verifyComment(String linha) {
		linha = linha.trim();
		
		if (linha.isEmpty()) {
			return true;
		}
		
		if (linha.startsWith(Constants.LINE_COMMENT)) {
			return true;
		}

		if (linha.startsWith(Constants.START_MULT_LINE_COMMENT)) {
			isComment = true;
		}

		if (linha.endsWith(Constants.END_MULT_LINE_COMMENT)) {
			isComment = false;
			return true;
		}

		return isComment;
	}

	private void showLineNumber(int rowsNumber) {
		String rowNumber = "";
		for (int i = 1; i <= rowsNumber; i++) {
			rowNumber += i + "\n";
		}
		view.getTxtPaneRowNumbers().setText(rowNumber);
	}

	private void showCode(String code) {
		view.getTxtPaneCode().setText(code);
	}

	private void setAnalyseMessage() {
		view.getJlbAnalyse().setText("Analizando...");
		view.getBtOpen().setEnabled(false);
	}

	private void setTimeSpent(Long timeSpent) {
		view.getJlbAnalyse().setText("Terminado ! Tempo gasto: " + timeSpent + " ms");
		view.getBtReport().setEnabled(true);
		view.getBtClear().setEnabled(true);
	}

}
