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

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextPane txtPaneRowsNumber;
	private JPanel pnSul;
	private JPanel pnText;
	private JButton btClose;
	private JButton btOpen;
	private JButton btClear;
	private JButton btReport;
	private ImageIcon imgWindowTitle;
	private JLabel jlbAnalyse;
	private ColorPane txtPaneCode;

	public MainView() {
		initializeComponents();
		renderFrame();
		addComponents();
	}

	private void initializeComponents() {
		txtPaneRowsNumber = new JTextPane();
		pnSul = new JPanel();
		pnText = new JPanel();
		btClose = new JButton("Sair");
		btOpen = new JButton("Abrir e análisar projeto");
		btClear = new JButton("Limpar");
		btReport = new JButton("Gerar Relátorio");
		imgWindowTitle = new ImageIcon("imagens/lupa.png");
		jlbAnalyse = new JLabel("Esperando");
		txtPaneCode = new ColorPane();
	}

	private void renderFrame() {
		setTitle("SCA");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(imgWindowTitle.getImage());
		setLocationRelativeTo(null);
		
		btOpen.setBorderPainted(false);
		btClear.setBorderPainted(false);
		btClose.setBackground(Color.RED);
		btClose.setBorderPainted(false);
		btReport.setBackground(Color.GREEN);
		btReport.setEnabled(false);
		btReport.setBorderPainted(false);
		btClear.setEnabled(false);
		txtPaneRowsNumber.setForeground(Color.ORANGE);
		txtPaneRowsNumber.setEditable(false);
		
		pnText.setLayout(new BorderLayout());
		setLayout(new BorderLayout());

		getContentPane().add(new JScrollPane(pnText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER);

		getContentPane().add(pnSul, BorderLayout.SOUTH);

	}

	private void addComponents() {
		pnSul.add(jlbAnalyse);
		pnSul.add(btClose);
		pnSul.add(btOpen);
		pnSul.add(btClear);
		pnSul.add(btReport);
		pnText.add(txtPaneCode, BorderLayout.CENTER);
		pnText.add(txtPaneRowsNumber, BorderLayout.WEST);
	}

	public JTextPane getTxtPaneRowNumbers() {
		return txtPaneRowsNumber;
	}

	public JPanel getPnSul() {
		return pnSul;
	}

	public JPanel getPnTexto() {
		return pnText;
	}

	public JButton getBtClose() {
		return btClose;
	}

	public JButton getBtOpen() {
		return btOpen;
	}

	public JButton getBtClear() {
		return btClear;
	}

	public JButton getBtReport() {
		return btReport;
	}

	public ImageIcon getImgWindowTitle() {
		return imgWindowTitle;
	}

	public JLabel getJlbAnalyse() {
		return jlbAnalyse;
	}

	public ColorPane getTxtPaneCode() {
		return txtPaneCode;
	}

}
