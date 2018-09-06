package com.report;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.color.Fonts;
import com.constants.Constants;
import com.enums.GapsDescription;
import com.enums.Languages;
import com.exceptions.PathNotSelected;
import com.file.handler.FileChooser;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.utilities.Utilities;

public class ReportAdapter {

	private Document document;
	private Paragraph paragrafo;
	private FileOutputStream file;
	private List<File> listaArquivos;
	private Map<String, String> map;
	private String path = "";
	private String arquivo;


	public ReportAdapter() {
		listaArquivos = new ArrayList<>();
		map = new HashMap<>();
	}

	private String makeNewLine(String e) {
		return e.trim() + Constants.COMMA_SYMBOL + " \n";
	}

	public void recebeErrosLinha(ArrayList<String> erros, GapsDescription desc) {
		String linhaErros = "";
		String key = "";
		for (String e : erros) {
			if (e.contains(Constants.ARROW_SYMBOL)) {
				key = e.substring(0, e.indexOf(Constants.ARROW_SYMBOL)) + desc.toString().trim();
				map.put(key, verificaChave(map.get(key)) + makeNewLine(e.replace(e.substring(0, e.indexOf(Constants.ARROW_SYMBOL) + Constants.ARROW_SYMBOL.length()), "")));
				continue;
			}

			linhaErros += makeNewLine(e);

		}

		map.put(getNomenclatura(desc), map.get(getNomenclatura(desc)) == null ? "" + linhaErros : map.get(getNomenclatura(desc)) + linhaErros);

	}
	
	private String verificaChave(String chave) {
		return chave == null ? "" : chave;
	}
	
	private String getNomenclatura(GapsDescription desc) {
		return arquivo + desc.toString();
	}

	public void nenhumaFalha() {
		paragrafo.setFont(Fonts.getFontGreen());
		paragrafo.add("0 falhas encontradas\n\n");
	}

	public void generate() {
		geraRelatorio();
		fechandoDocumento();
	}

	/*                          */
	/* Abrir e fechar documento */
	/*                          */

	public void openDocument() throws PathNotSelected {
		document = new Document();
		try {
			path = FileChooser.choosePath();
			if (path.equals("")) {
				throw new PathNotSelected("Nenhum diretório selecionado");
			}
			
			file = new FileOutputStream(path + Constants.PDF);
			PdfWriter.getInstance(document, file);
			document.open();
			paragrafo = new Paragraph("Relatorio de falhas\n\n\n", Fonts.getFontBlack());
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
				extensao = Utilities.getFileExtension(nome);
				setClasse(nome);

				if (Languages.JAVA.toString().equals(extensao)) {
					adicionaConteudoJava(nome);
					continue;
				}

				if (Languages.PHP.toString().equals(extensao)) {
					adicionaConteudoPHP(nome);
					continue;
				}
				
				if (Languages.CPP.toString().equals(extensao) || Languages.H.toString().equals(extensao)) {
					adicionaConteudoCPP(nome);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setFile(String arquivo) {
		this.arquivo = arquivo;
	}

	public void setClasse(String classeAnalisada) {
		paragrafo.setFont(Fonts.getFontBlue());
		paragrafo.add(classeAnalisada + "\n");
	}

	private void adicionaConteudoRelatorio(String titulo, String erro, String rodape) {
		paragrafo.setFont(Fonts.getFontBlack());
		paragrafo.add(titulo);
		if (!erro.equals(Constants.VOID_SYMBOL)) {
			paragrafo.setFont(Fonts.getFontRed());
			paragrafo.add(Constants.VOID_SYMBOL + erro.substring(0, erro.length()) + "\n");
			paragrafo.setFont(Fonts.getFontOrange());
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
			if (m.getKey().equals(nome + GapsDescription.JAVA_PASSWORDFIELD.toString())) {
				adicionaConteudoRelatorio("Falhas de segurança de senha: ", m.getValue(),
						"PasswordField utilizando metodo .getText(), recomendado utilizar .getPassword()");
				continue;
			}
			
			if (m.getKey().equals(nome + GapsDescription.JAVA_SQLINJECTION.toString())) {
				adicionaConteudoRelatorio("Falhas de SQL: ", m.getValue(),
						"Evite concatenar valores diretamente na instrução SQL");
				continue;
			}
			
			if (m.getKey().equals(nome + GapsDescription.JAVA_VARIAVEISFINAL.toString())) {
				adicionaConteudoRelatorio("Falhas variaveis final: ", m.getValue(),
						"Declare esta variavel como final");
				continue;
			}
			
			if (m.getKey().equals(nome + GapsDescription.JAVA_SOCKETSSL.toString())) {
				adicionaConteudoRelatorio("Socket não possue criptografia: ", m.getValue(),
						"Utilize criptografia SSL");
				continue;
			}
			
			if(m.getKey().equals(nome + GapsDescription.JAVA_UNBOXING.toString())) {
				adicionaConteudoRelatorio("Unboxing: ", m.getValue(),
						"O retorno das seguintes variáveis pode ser nulo, adicione clausulas throws ou bloco try catch para tratar exceções");
				continue;
			}
		}
	
	}

	private void adicionaConteudoPHP(String nome) throws Exception {
		
		for (Map.Entry<String, String> m : map.entrySet()) {
			
			if (m.getKey().equals(nome + GapsDescription.PHP_SQLINJECTION.toString())) {
				adicionaConteudoRelatorio("Falhas de SQL: ", m.getValue(),
						"Evite concatenar valores diretamente na instrução SQL");
				continue;
			}
			
			if (m.getKey().equals(nome + GapsDescription.PHP_URL.toString())) {
				adicionaConteudoRelatorio("Possível falha de Manipulação de URL: ", m.getValue(),
						"Verifique se há validação para estas variáveis");
				continue;
			}
		}
	}
	
	private void adicionaConteudoCPP(String nome) throws Exception {
		for (Map.Entry<String, String> m : map.entrySet()) {
			if (m.getKey().equals(nome + GapsDescription.CPP_GETSVSPSP.toString())) {
				adicionaConteudoRelatorio("Uso de método indevido: ", m.getValue(),
						"Evite utilizar estes métodos para não ocorrer vazamentos de memória");
				continue;
			}
			
			if (m.getKey().equals(nome + GapsDescription.CPP_UNSIGNED.toString())) {
				adicionaConteudoRelatorio("Unsigned não tratado: ", m.getValue().replaceAll("(\\+*)(\\-*)", ""), 
						"Verifique se o valor recebido é positivo");
				continue;
			}
		}
	}

	public void setFileList(List<File> listaArquivos) {
		this.listaArquivos = listaArquivos;
	}
	
	public String getPath() {
		return path;
	}

	/*                                    */
	/* Atribuindo valor as variaveis Java */
	/*                                    */

	public void setPasswordFieldGaps(ArrayList<String> falhasDeSegurancaPasswordField) {
		recebeErrosLinha(falhasDeSegurancaPasswordField, GapsDescription.JAVA_PASSWORDFIELD);
	}

	public void setSqlGaps(ArrayList<String> falhasDeSegurancaSQLInjection) {
		recebeErrosLinha(falhasDeSegurancaSQLInjection, GapsDescription.JAVA_SQLINJECTION);
	}

	public void setFinalVariablesGaps(ArrayList<String> falhasDeSegurancaVariaveisFinal) {
		recebeErrosLinha(falhasDeSegurancaVariaveisFinal, GapsDescription.JAVA_VARIAVEISFINAL);
	}

	public void setSocketGaps(ArrayList<String> falhasDeSegurancaSockets) {
		recebeErrosLinha(falhasDeSegurancaSockets, GapsDescription.JAVA_SOCKETSSL);
	}

	public void setUnboxingGaps(ArrayList<String> falhasDeSegurancaUnboxing) {
		recebeErrosLinha(falhasDeSegurancaUnboxing, GapsDescription.JAVA_UNBOXING);
	}

	/*                                    */
	/* Atribuindo valor as variaveis PHP  */
	/*                                    */

	public void setSqlPHPGaps(ArrayList<String> falhasDeSegurancaSQLPHP) {
		recebeErrosLinha(falhasDeSegurancaSQLPHP, GapsDescription.PHP_SQLINJECTION);
	}

	public void setUrlGaps(ArrayList<String> falhasSegurancaUrlPHP) {
		recebeErrosLinha(falhasSegurancaUrlPHP, GapsDescription.PHP_URL);
	}
	
	/*                                    */
	/* Atribuindo valor as variaveis CPP  */
	/*                                    */
	
	public void setGetsGaps(ArrayList<String> falhasDeSegurancaGets) {
		recebeErrosLinha(falhasDeSegurancaGets, GapsDescription.CPP_GETSVSPSP);
	}
	
	public void setUnsignedGaps(ArrayList<String> falhasDeSegurancaUnsigned) {
		recebeErrosLinha(falhasDeSegurancaUnsigned, GapsDescription.CPP_UNSIGNED);
	}

}
