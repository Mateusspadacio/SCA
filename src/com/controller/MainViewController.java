package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alerts.AlertsHandler;
import com.constants.FileInfos;
import com.file.handler.FileHandler;
import com.report.Report;
import com.securegaps.AnnaliseGapsHandler;
import com.view.MainView;

public class MainViewController {

	private MainView view;
	private FileHandler fileHandler;
	private AnnaliseGapsHandler analyseGaps;

	public MainViewController(MainView view) {
		this.view = view;
		fileHandler = new FileHandler(view);
		addEvents();
		this.view.setVisible(true);
	}

	private void addEvents() {
		view.getBtOpen().addActionListener(new EventButton());
		view.getBtReport().addActionListener(new EventButton());
		view.getBtClose().addActionListener(new EventButton());
		view.getBtClear().addActionListener(new EventButton());
	}

	private void openAnalyseProject() {
		if (!openProject()) {
			return;
		}
		analyseProject();
	}

	private boolean openProject() {
		fileHandler.selecProject();
		if (!fileHandler.openProject()) {
			AlertsHandler.alert("Selecione um projeto");
			return false;
		}
		return true;
	}

	private void analyseProject() {
		if (fileHandler.getPathList().size() == 0) {
			AlertsHandler.alert("Não há arquivos para análisar");
			return;
		}
		FileInfos.setCacheFileList(fileHandler.getPathList());
		analyseGaps = new AnnaliseGapsHandler(view, fileHandler.getPathList());
		analyseGaps.startAnalyse();
	}
	
	private void generateReport(){
		if(analyseGaps.getDataList().isEmpty()){
			return;
		}
		
		Report relatorio = new Report(analyseGaps.getDataList(), fileHandler.getPathList());
		relatorio.generate();
		view.getBtReport().setEnabled(false);
	}
	
	private void clear() {
		analyseGaps.getDataList().clear();
		fileHandler.getPathList().clear();
		view.getTxtPaneCode().setText("");
		view.getTxtPaneRowNumbers().setText("");
		view.getBtReport().setEnabled(false);
		view.getBtClear().setEnabled(false);
		view.getBtOpen().setEnabled(true);
		AlertsHandler.inform("Limpo !");
	}

	private class EventButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(view.getBtOpen())) {
				openAnalyseProject();
			} else if (e.getSource().equals(view.getBtReport())){
				generateReport();
			} else if (e.getSource().equals(view.getBtClose())) {
				System.exit(0);
			} else if (e.getSource().equals(view.getBtClear())) {
				clear();
			}

		}

	}

}
