package com.exceptions;

import com.mensagens.Mensagem;

public class NenhumDiretorioSelecionado extends Exception {

	private static final long serialVersionUID = 1L;

	public NenhumDiretorioSelecionado(String msg) {
		Mensagem.alertar(msg);
	}

}
