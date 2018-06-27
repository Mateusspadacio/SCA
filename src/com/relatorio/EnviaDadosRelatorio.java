package com.relatorio;

import java.util.ArrayList;

import com.c.falhasdeseguranca.getsvspsp.FalhaGetsVSPSPCPP;
import com.c.falhasdeseguranca.unsigned.FalhaUnsignedCPP;
import com.falhasdeseguranca.passwordfield.FalhaPasswordFieldJava;
import com.falhasdeseguranca.socketssl.FalhaSocketSSLJava;
import com.falhasdeseguranca.sqlinjector.FalhaSQLInjectorJava;
import com.falhasdeseguranca.unboxing.FalhaUnboxingJava;
import com.falhasdeseguranca.variaveisfinal.FalhaVariaveisFinalJava;
import com.metodos.padrao.MetodosPadrao;
import com.php.falhasdeseguranca.sqlinjector.FalhaSQLInjectorPHP;
import com.php.falhasdeseguranca.url.FalhaUrlPHP;

public class EnviaDadosRelatorio {
	
	private RelatorioAdapter ra;
	
	public EnviaDadosRelatorio(RelatorioAdapter ra) {
		this.ra = ra;	
	}
	
	private ArrayList<String> retornaLista(MetodosPadrao obj) {
		ArrayList<String> lista = null;
		try {
			lista = obj.getResultadoAnalise();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (lista == null) {
			return new ArrayList<String>();
		}

		return lista;
	}
	
	/*
	 * ANEXAR MAIS UM IF ELSE QUANDO MAIS ERRORS 
	 * FOREM IMPLEMENTADOS NO SISTEMA
	 * 
	 */

	protected void verificandoDados(MetodosPadrao obj) {
		verificandoJava(obj);
		verificandoPHP(obj);
		verificandoCPP(obj);
	}
	
	private void verificandoJava(MetodosPadrao obj) {
		if (obj instanceof FalhaPasswordFieldJava) {
			ra.setFalhasDeSegurancaPasswordField(retornaLista(obj));
		} else if (obj instanceof FalhaSocketSSLJava) {
			ra.setFalhasDeSegurancaSockets(retornaLista(obj));
		} else if (obj instanceof FalhaSQLInjectorJava) {
			ra.setFalhasDeSegurancaSql(retornaLista(obj));
		} else if (obj instanceof FalhaUnboxingJava) {
			ra.setFalhasDeSegurancaUnboxing(retornaLista(obj));
		}else if (obj instanceof FalhaVariaveisFinalJava) {
			ra.setFalhasDeSegurancaVariaveisFinal(retornaLista(obj));
		}
	}
	
	private void verificandoPHP(MetodosPadrao obj) {
		if (obj instanceof FalhaSQLInjectorPHP) {
			ra.setFalhasDeSegurancaSqlPHP(retornaLista(obj));
		} else if (obj instanceof FalhaUrlPHP) {
			ra.setFalhasDeSegurancaUrl(retornaLista(obj));
		}
	}
	
	private void verificandoCPP(MetodosPadrao obj) {
		if (obj instanceof FalhaGetsVSPSPCPP) {
			ra.setFalhasDeSegurancaGets(retornaLista(obj));
		} else if(obj instanceof FalhaUnsignedCPP) {
			ra.setFalhasDeSegurancaUnsigned(retornaLista(obj));
		}
	}

}
