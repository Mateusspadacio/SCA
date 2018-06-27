package com.falhasdeseguranca;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.constants.Constants;
import com.constants.FileInfos;
import com.load.CarregandoClasses;
import com.metodos.padrao.MetodosPadrao;
import com.utilidades.Utilidades;
import com.view.TelaPrincipalView;

public class AnalisaFalhasHandler{

	private Vector<File> pathList;
	private TelaPrincipalView view;
	private boolean isComentario = false;
	private List<MetodosPadrao> listaDeClasses;
	private List<Object> listaDeDados;
	private CarregandoClasses carregandoClasses;

	public AnalisaFalhasHandler(TelaPrincipalView view, Vector<File> pathList) {
		this.view = view;
		this.pathList = pathList;
		carregandoClasses = new CarregandoClasses();
		listaDeDados = new ArrayList<Object>();
	}

	public void iniciarAnalise() {
		start();
	}

	private void verificaLinguagemAnalisada(String path) {
		carregandoClasses.carregandoLinguagem(Utilidades.getExtensaoArquivo(path));
		listaDeClasses = carregandoClasses.getListaDeTodasAsClassesInstanciadas();
	}
	
	private void nomeArquivo(File arquivo) {
		FileInfos.FILE_NAME = arquivo.getName();
	}

	private void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				setMensagemAnalisando();
				Long start = System.currentTimeMillis();
				lendoArquivos();
				Long end = System.currentTimeMillis();
				setTempoGasto(end - start);
			}
		}).start();
	}

	private void lendoArquivos() {
		String codigo = "";
		int numLinhas = 1;
		int numLinhasTela = 1;
		String linha = "";
		BufferedReader br;
		for (File arquivo : pathList) {
			nomeArquivo(arquivo);
			verificaLinguagemAnalisada(arquivo.getAbsolutePath());
			try {
				br = new BufferedReader(new FileReader(arquivo));
				while ((linha = br.readLine()) != null) {
					numLinhas++;
					numLinhasTela++;
					codigo += linha + "\n";
					if (verificaComentario(linha)) {
						continue;
					}
					efetuandoAnalise(linha, numLinhas);
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			numLinhas = 1;
			armazenandoDados();
			limpandoSessao();
		}

		exibirNumeroLinha(numLinhasTela);
		exibirCodigo(codigo);
	}

	private void armazenandoDados() {
		List<Object> listaClonada = new ArrayList<>();
		for(MetodosPadrao classe : listaDeClasses){
			try {
				Object obj = (MetodosPadrao) classe.clonar();
				listaClonada.add(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		listaDeDados.add(listaClonada);
	}

	private void efetuandoAnalise(String linha, int numeroLinha) {
		for (MetodosPadrao classe : listaDeClasses) {
			try {
				classe.analisando(linha, numeroLinha);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void limpandoSessao() {
		for (MetodosPadrao classe : listaDeClasses) {
			try {
				classe.clearSession();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Object> getListaDeDados(){
		return listaDeDados;
	}

	private boolean verificaComentario(String linha) {
		linha = linha.trim();
		
		if (linha.isEmpty()) {
			return true;
		}
		
		if (linha.startsWith(Constants.CARACTER_ABRE_COMENTARIO_LINHA)) {
			return true;
		}

		if (linha.startsWith(Constants.CARACTER_ABRE_COMENTARIO_MULTI_LINHA)) {
			isComentario = true;
		}

		if (linha.endsWith(Constants.CARACTER_FECHA_COMENTARIO_MULTI_LINHA)) {
			isComentario = false;
			return true;
		}

		return isComentario;
	}

	private void exibirNumeroLinha(int numLinhas) {
		String numLinha = "";
		for (int i = 1; i <= numLinhas; i++) {
			numLinha += i + "\n";
		}
		view.getTxtPaneNumerosLinha().setText(numLinha);
	}

	private void exibirCodigo(String codigo) {
		view.getTxtPaneCodigo().setText(codigo);
	}

	private void setMensagemAnalisando() {
		view.getJlbAnalisando().setText("Analizando...");
		view.getBtAbrir().setEnabled(false);
	}

	private void setTempoGasto(Long tempoGasto) {
		view.getJlbAnalisando().setText("Terminado ! Tempo gasto: " + tempoGasto + " ms");
		view.getBtRelatorio().setEnabled(true);
		view.getBtLimpar().setEnabled(true);
	}

}
