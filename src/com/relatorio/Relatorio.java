package com.relatorio;

import java.io.File;
import java.util.List;
import java.util.Vector;

import com.exceptions.NenhumDiretorioSelecionado;
import com.metodos.padrao.MetodosPadrao;

public class Relatorio {

	private List<Object> listaDeDados;
	private Vector<File> pathList;
	private RelatorioAdapter ra;

	public Relatorio(List<Object> listaDeDados, Vector<File> pathList) {
		this.listaDeDados = listaDeDados;
		this.pathList = pathList;
		ra = new RelatorioAdapter();
	}

	public void gerar() {
		try {
			ra.abrindoDocumento();
			enviandoDados();
			ra.gerar();
		} catch (NenhumDiretorioSelecionado e) {
		}
	}
	
	private void enviandoDados() {
		int size = pathList.size();
		EnviaDadosRelatorio enviaDadosRelatorio = new EnviaDadosRelatorio(ra);
		ra.setListaArquivos(pathList);

		for (int i = 0; i < size; i++) {
			ra.setArquivo(pathList.get(i).getName());
			List<MetodosPadrao> lista = (List<MetodosPadrao>) listaDeDados.get(i);
			for (MetodosPadrao objLista : lista) {
				try {
					enviaDadosRelatorio.verificandoDados(objLista);
				} catch (IllegalArgumentException | SecurityException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
