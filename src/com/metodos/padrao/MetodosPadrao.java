package com.metodos.padrao;

import java.util.ArrayList;

public interface MetodosPadrao {

	public void analisando(String linha, int num);
	
	public ArrayList<String> getResultadoAnalise();
	
	public void clearSession();
	
	public Object clonar();
}
