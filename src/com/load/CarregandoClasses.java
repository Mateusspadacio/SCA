package com.load;

import java.util.ArrayList;
import java.util.List;

import com.c.falhasdeseguranca.getsvspsp.FalhaGetsVSPSPCPP;
import com.c.falhasdeseguranca.unsigned.FalhaUnsignedCPP;
import com.enums.Linguagens;
import com.falhasdeseguranca.passwordfield.FalhaPasswordFieldJava;
import com.falhasdeseguranca.socketssl.FalhaSocketSSLJava;
import com.falhasdeseguranca.sqlinjector.FalhaSQLInjectorJava;
import com.falhasdeseguranca.unboxing.FalhaUnboxingJava;
import com.falhasdeseguranca.variaveisfinal.FalhaVariaveisFinalJava;
import com.metodos.padrao.MetodosPadrao;
import com.php.falhasdeseguranca.sqlinjector.FalhaSQLInjectorPHP;
import com.php.falhasdeseguranca.url.FalhaUrlPHP;

public class CarregandoClasses {

	private List<MetodosPadrao> listaDeTodasAsClassesInstanciadas;
	private List<MetodosPadrao> listaFalhasJava;
	private List<MetodosPadrao> listaFalhasPHP;
	private List<MetodosPadrao> listaFalhasCPP;
	
	public CarregandoClasses(){
	}
	
	public void carregandoLinguagem(String extensao) {
		if(Linguagens.JAVA.toString().equals(extensao)) {
			loadingFalhasJava();
			return;
		}
		
		if (Linguagens.PHP.toString().equals(extensao)) {
			loadingFalhasPHP();
			return;
		}
		
		if (Linguagens.CPP.toString().equals(extensao) || Linguagens.H.toString().equals(extensao)) {
			loadingFalhasCPP();
			return;
		}
		
	}
	
	private void loadingFalhasJava(){
		if (listaFalhasJava == null) {
			listaFalhasJava = new ArrayList<>();
			listaFalhasJava.add(new FalhaPasswordFieldJava());
			listaFalhasJava.add(new FalhaSocketSSLJava());
			listaFalhasJava.add(new FalhaSQLInjectorJava());
			listaFalhasJava.add(new FalhaUnboxingJava());
			listaFalhasJava.add(new FalhaVariaveisFinalJava());
		}
		
		listaDeTodasAsClassesInstanciadas = listaFalhasJava;
	}
	
	private void loadingFalhasPHP() {
		if (listaFalhasPHP == null) {
			listaFalhasPHP = new ArrayList<>();
			listaFalhasPHP.add(new FalhaSQLInjectorPHP());
			listaFalhasPHP.add(new FalhaUrlPHP());
		}
		
		listaDeTodasAsClassesInstanciadas = listaFalhasPHP;
	}
	
	
	private void loadingFalhasCPP() {
		if (listaFalhasCPP == null) {
			listaFalhasCPP = new ArrayList<>();
			listaFalhasCPP.add(new FalhaGetsVSPSPCPP());
			listaFalhasCPP.add(new FalhaUnsignedCPP());
		}
		
		listaDeTodasAsClassesInstanciadas = listaFalhasCPP;
	}
	
	public List<MetodosPadrao> getListaDeTodasAsClassesInstanciadas(){
		return listaDeTodasAsClassesInstanciadas;
	}
	
}
