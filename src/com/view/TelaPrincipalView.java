package com.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.color.ColorPane;

public class TelaPrincipalView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextPane txtPaneNumerosLinha;
	private JPanel pnSul;
	private JPanel pnTexto;
	private JButton btSair;
	private JButton btAbrir;
	private JButton btLimpar;
	private JButton btRelatorio;
	private ImageIcon imgTituloJanela;
	private JLabel jlbAnalisando;
	private ColorPane txtPaneCodigo;

	public TelaPrincipalView() {
		inicializaComponentes();
		renderizaFrame();
		adicionaComponentes();
	}

	private void inicializaComponentes() {
		txtPaneNumerosLinha = new JTextPane();
		pnSul = new JPanel();
		pnTexto = new JPanel();
		btSair = new JButton("Sair");
		btAbrir = new JButton("Abrir e análisar projeto");
		btLimpar = new JButton("Limpar");
		btRelatorio = new JButton("Gerar Relátorio");
		imgTituloJanela = new ImageIcon("imagens/lupa.png");
		jlbAnalisando = new JLabel("Esperando");
		txtPaneCodigo = new ColorPane();
	}

	private void renderizaFrame() {
		setTitle("SCA");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(imgTituloJanela.getImage());
		setLocationRelativeTo(null);
		
		btAbrir.setBorderPainted(false);
		btLimpar.setBorderPainted(false);
		btSair.setBackground(Color.RED);
		btSair.setBorderPainted(false);
		btRelatorio.setBackground(Color.GREEN);
		btRelatorio.setEnabled(false);
		btRelatorio.setBorderPainted(false);
		btLimpar.setEnabled(false);
		txtPaneNumerosLinha.setForeground(Color.ORANGE);
		txtPaneNumerosLinha.setEditable(false);
		
		pnTexto.setLayout(new BorderLayout());
		setLayout(new BorderLayout());

		getContentPane().add(new JScrollPane(pnTexto, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER);

		getContentPane().add(pnSul, BorderLayout.SOUTH);

	}

	private void adicionaComponentes() {
		pnSul.add(jlbAnalisando);
		pnSul.add(btSair);
		pnSul.add(btAbrir);
		pnSul.add(btLimpar);
		pnSul.add(btRelatorio);
		pnTexto.add(txtPaneCodigo, BorderLayout.CENTER);
		pnTexto.add(txtPaneNumerosLinha, BorderLayout.WEST);
	}

	public JTextPane getTxtPaneNumerosLinha() {
		return txtPaneNumerosLinha;
	}

	public JPanel getPnSul() {
		return pnSul;
	}

	public JPanel getPnTexto() {
		return pnTexto;
	}

	public JButton getBtSair() {
		return btSair;
	}

	public JButton getBtAbrir() {
		return btAbrir;
	}

	public JButton getBtLimpar() {
		return btLimpar;
	}

	public JButton getBtRelatorio() {
		return btRelatorio;
	}

	public ImageIcon getImgTituloJanela() {
		return imgTituloJanela;
	}

	public JLabel getJlbAnalisando() {
		return jlbAnalisando;
	}

	public ColorPane getTxtPaneCodigo() {
		return txtPaneCodigo;
	}

}
