package com.relatorio;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.color.Fontes;
import com.constants.Constants;
import com.enums.DescFalhas;
import com.enums.Linguagens;
import com.exceptions.NenhumDiretorioSelecionado;
import com.file.handler.FileChooser;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.utilidades.Utilidades;

public class RelatorioAdapter {

	private Document document;
	private Paragraph paragrafo;
	private FileOutputStream file;
	private List<File> listaArquivos;
	private Map<String, String> map;
	private String path = "";
	private String arquivo;


	public RelatorioAdapter() {
		listaArquivos = new ArrayList<>();
		map = new HashMap<>();
	}

	private String makeNewLine(String e) {
		return e.trim() + Constants.CARACTER_VIRGULA + " \n";
	}

	public void recebeErrosLinha(ArrayList<String> erros, DescFalhas desc) {
		String linhaErros = "";
		String key = "";
		for (String e : erros) {
			if (e.contains(Constants.CARACTER_FLECHA)) {
				key = e.substring(0, e.indexOf(Constants.CARACTER_FLECHA)) + desc.toString().trim();
				map.put(key, verificaChave(map.get(key)) + makeNewLine(e.replace(e.substring(0, e.indexOf(Constants.CARACTER_FLECHA) + Constants.CARACTER_FLECHA.length()), "")));
				continue;
			}

			linhaErros += makeNewLine(e);

		}

		map.put(getNomenclatura(desc), map.get(getNomenclatura(desc)) == null ? "" + linhaErros : map.get(getNomenclatura(desc)) + linhaErros);

	}
	
	private String verificaChave(String chave) {
		return chave == null ? "" : chave;
	}
	
	private String getNomenclatura(DescFalhas desc) {
		return arquivo + desc.toString();
	}

	public void nenhumaFalha() {
		paragrafo.setFont(Fontes.getFontGreen());
		paragrafo.add("0 falhas encontradas\n\n");
	}

	public void gerar() {
		geraRelatorio();
		fechandoDocumento();
	}

	/*                          */
	/* Abrir e fechar documento */
	/*                          */

	public void abrindoDocumento() throws NenhumDiretorioSelecionado {
		document = new Document();
		try {
			path = FileChooser.choosePath();
			if (path.equals("")) {
				throw new NenhumDiretorioSelecionado("Nenhum diretório selecionado");
			}
			
			file = new FileOutputStream(path + Constants.PDF);
			PdfWriter.getInstance(document, file);
			document.open();
			paragrafo = new Paragraph("Relatorio de falhas\n\n\n", Fontes.getFontBlack());
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}

	private void fechandoDocumento() {
		try {
			document.add(paragrafo);
			paragrafo.clear();
			document.close();
		} catch (DocumentException ex) {
			ex.printStackTrace();
		}

		try {
			System.gc();
			Desktop.getDesktop().open(new File(path + Constants.PDF));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*						*/
	/* Gerador de ralatorio */
	/*                      */

	private void geraRelatorio() {
		try {
			String extensao = "";
			String nome = "";
			for (File arquivo : listaArquivos) {
				nome = arquivo.getName();
				extensao = Utilidades.getExtensaoArquivo(nome);
				setClasse(nome);

				if (Linguagens.JAVA.toString().equals(extensao)) {
					adicionaConteudoJava(nome);
					continue;
				}

				if (Linguagens.PHP.toString().equals(extensao)) {
					adicionaConteudoPHP(nome);
					continue;
				}
				
				if (Linguagens.CPP.toString().equals(extensao) || Linguagens.H.toString().equals(extensao)) {
					adicionaConteudoCPP(nome);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public void setClasse(String classeAnalisada) {
		paragrafo.setFont(Fontes.getFontBlue());
		paragrafo.add(classeAnalisada + "\n");
	}

	private void adicionaConteudoRelatorio(String titulo, String erro, String rodape) {
		paragrafo.setFont(Fontes.getFontBlack());
		paragrafo.add(titulo);
		if (!erro.equals(Constants.CARACTER_VAZIO)) {
			paragrafo.setFont(Fontes.getFontRed());
			paragrafo.add(Constants.CARACTER_VAZIO + erro.substring(0, erro.length()) + "\n");
			paragrafo.setFont(Fontes.getFontOrange());
			paragrafo.add(rodape + "\n\n");
		} else {
			nenhumaFalha();
		}
	}


	/*                               */
	/* Adicionando conteudos */
	/*                               */

	private void adicionaConteudoJava(String nome) throws Exception {
		for (Map.Entry<String, String> m : map.entrySet()) {
			if (m.getKey().equals(nome + DescFalhas.JAVA_PASSWORDFIELD.toString())) {
				adicionaConteudoRelatorio("Falhas de segurança de senha: ", m.getValue(),
						"PasswordField utilizando metodo .getText(), recomendado utilizar .getPassword()");
				continue;
			}
			
			if (m.getKey().equals(nome + DescFalhas.JAVA_SQLINJECTION.toString())) {
				adicionaConteudoRelatorio("Falhas de SQL: ", m.getValue(),
						"Evite concatenar valores diretamente na instrução SQL");
				continue;
			}
			
			if (m.getKey().equals(nome + DescFalhas.JAVA_VARIAVEISFINAL.toString())) {
				adicionaConteudoRelatorio("Falhas variaveis final: ", m.getValue(),
						"Declare esta variavel como final");
				continue;
			}
			
			if (m.getKey().equals(nome + DescFalhas.JAVA_SOCKETSSL.toString())) {
				adicionaConteudoRelatorio("Socket não possue criptografia: ", m.getValue(),
						"Utilize criptografia SSL");
				continue;
			}
			
			if(m.getKey().equals(nome + DescFalhas.JAVA_UNBOXING.toString())) {
				adicionaConteudoRelatorio("Unboxing: ", m.getValue(),
						"O retorno das seguintes variáveis pode ser nulo, adicione clausulas throws ou bloco try catch para tratar exceções");
				continue;
			}
		}
	
	}

	private void adicionaConteudoPHP(String nome) throws Exception {
		
		for (Map.Entry<String, String> m : map.entrySet()) {
			
			if (m.getKey().equals(nome + DescFalhas.PHP_SQLINJECTION.toString())) {
				adicionaConteudoRelatorio("Falhas de SQL: ", m.getValue(),
						"Evite concatenar valores diretamente na instrução SQL");
				continue;
			}
			
			if (m.getKey().equals(nome + DescFalhas.PHP_URL.toString())) {
				adicionaConteudoRelatorio("Possível falha de Manipulação de URL: ", m.getValue(),
						"Verifique se há validação para estas variáveis");
				continue;
			}
		}
	}
	
	private void adicionaConteudoCPP(String nome) throws Exception {
		for (Map.Entry<String, String> m : map.entrySet()) {
			if (m.getKey().equals(nome + DescFalhas.CPP_GETSVSPSP.toString())) {
				adicionaConteudoRelatorio("Uso de método indevido: ", m.getValue(),
						"Evite utilizar estes métodos para não ocorrer vazamentos de memória");
				continue;
			}
			
			if (m.getKey().equals(nome + DescFalhas.CPP_UNSIGNED.toString())) {
				adicionaConteudoRelatorio("Unsigned não tratado: ", m.getValue().replaceAll("(\\+*)(\\-*)", ""), 
						"Verifique se o valor recebido é positivo");
				continue;
			}
		}
	}

	public void setListaArquivos(List<File> listaArquivos) {
		this.listaArquivos = listaArquivos;
	}
	
	public String getPàth() {
		return path;
	}

	/*                                    */
	/* Atribuindo valor as variaveis Java */
	/*                                    */

	public void setFalhasDeSegurancaPasswordField(ArrayList<String> falhasDeSegurancaPasswordField) {
		recebeErrosLinha(falhasDeSegurancaPasswordField, DescFalhas.JAVA_PASSWORDFIELD);
	}

	public void setFalhasDeSegurancaSql(ArrayList<String> falhasDeSegurancaSQLInjection) {
		recebeErrosLinha(falhasDeSegurancaSQLInjection, DescFalhas.JAVA_SQLINJECTION);
	}

	public void setFalhasDeSegurancaVariaveisFinal(ArrayList<String> falhasDeSegurancaVariaveisFinal) {
		recebeErrosLinha(falhasDeSegurancaVariaveisFinal, DescFalhas.JAVA_VARIAVEISFINAL);
	}

	public void setFalhasDeSegurancaSockets(ArrayList<String> falhasDeSegurancaSockets) {
		recebeErrosLinha(falhasDeSegurancaSockets, DescFalhas.JAVA_SOCKETSSL);
	}

	public void setFalhasDeSegurancaUnboxing(ArrayList<String> falhasDeSegurancaUnboxing) {
		recebeErrosLinha(falhasDeSegurancaUnboxing, DescFalhas.JAVA_UNBOXING);
	}

	/*                                    */
	/* Atribuindo valor as variaveis PHP  */
	/*                                    */

	public void setFalhasDeSegurancaSqlPHP(ArrayList<String> falhasDeSegurancaSQLPHP) {
		recebeErrosLinha(falhasDeSegurancaSQLPHP, DescFalhas.PHP_SQLINJECTION);
	}

	public void setFalhasDeSegurancaUrl(ArrayList<String> falhasSegurancaUrlPHP) {
		recebeErrosLinha(falhasSegurancaUrlPHP, DescFalhas.PHP_URL);
	}
	
	/*                                    */
	/* Atribuindo valor as variaveis CPP  */
	/*                                    */
	
	public void setFalhasDeSegurancaGets(ArrayList<String> falhasDeSegurancaGets) {
		recebeErrosLinha(falhasDeSegurancaGets, DescFalhas.CPP_GETSVSPSP);
	}
	
	public void setFalhasDeSegurancaUnsigned(ArrayList<String> falhasDeSegurancaUnsigned) {
		recebeErrosLinha(falhasDeSegurancaUnsigned, DescFalhas.CPP_UNSIGNED);
	}

}
