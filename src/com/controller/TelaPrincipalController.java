package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.falhasdeseguranca.AnalisaFalhasHandler;
import com.file.handler.FileHandler;
import com.mensagens.Mensagem;
import com.relatorio.Relatorio;
import com.view.TelaPrincipalView;

public class TelaPrincipalController {

	private TelaPrincipalView view;
	private FileHandler fileHandler;
	private AnalisaFalhasHandler analisaFalhas;

	public TelaPrincipalController(TelaPrincipalView view) {
		this.view = view;
		fileHandler = new FileHandler(view);
		addEvents();
		this.view.setVisible(true);
	}

	private void addEvents() {
		view.getBtAbrir().addActionListener(new EventButton());
		view.getBtRelatorio().addActionListener(new EventButton());
		view.getBtSair().addActionListener(new EventButton());
		view.getBtLimpar().addActionListener(new EventButton());
	}

	private void abrirAnalisarProjeto() {
		if (!abrirProjeto()) {
			return;
		}
		analisarProjeto();
	}

	private boolean abrirProjeto() {
		fileHandler.selecionarProjeto();
		if (!fileHandler.abrirProjeto()) {
			Mensagem.alertar("Selecione um projeto");
			return false;
		}
		return true;
	}

	private void analisarProjeto() {
		if (fileHandler.getPathList().size() == 0) {
			Mensagem.alertar("Não há arquivos para análisar");
			return;
		}
		
		analisaFalhas = new AnalisaFalhasHandler(view, fileHandler.getPathList());
		analisaFalhas.iniciarAnalise();
	}
	
	private void gerarRelatorio(){
		if(analisaFalhas.getListaDeDados().isEmpty()){
			return;
		}
		
		Relatorio relatorio = new Relatorio(analisaFalhas.getListaDeDados(), fileHandler.getPathList());
		relatorio.gerar();
		view.getBtRelatorio().setEnabled(false);
	}
	
	private void clear() {
		analisaFalhas.getListaDeDados().clear();
		fileHandler.getPathList().clear();
		view.getTxtPaneCodigo().setText("");
		view.getTxtPaneNumerosLinha().setText("");
		view.getBtRelatorio().setEnabled(false);
		view.getBtLimpar().setEnabled(false);
		view.getBtAbrir().setEnabled(true);
		Mensagem.informar("Limpo !");
	}

	private class EventButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(view.getBtAbrir())) {
				abrirAnalisarProjeto();
			} else if (e.getSource().equals(view.getBtRelatorio())){
				gerarRelatorio();
			} else if (e.getSource().equals(view.getBtSair())) {
				System.exit(0);
			} else if (e.getSource().equals(view.getBtLimpar())) {
				clear();
			}

		}

	}

}
