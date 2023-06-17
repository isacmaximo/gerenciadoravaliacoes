package siapro.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import siapro.controller.AreaController;
import siapro.controller.EventoController;
import siapro.model.Evento;
import siapro.model.EventoData;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;

public class Informacoes extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNome;
	private JTextField textFieldLocal;
	private JTextField textFieldEstado;
	private JTextArea textAreaInformacoes;
	private Evento evento;
	private Path caminho;
	
	public Informacoes(Evento evento) {
		this.evento = evento;
		setBounds(100, 100, 745, 493);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInformaes = new JLabel("Informações");
		lblInformaes.setBackground(Color.MAGENTA);
		lblInformaes.setForeground(Color.RED);
		lblInformaes.setBounds(22, 0, 131, 15);
		contentPane.add(lblInformaes);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(22, 30, 66, 15);
		contentPane.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(78, 27, 334, 19);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);
		textFieldNome.setText(evento.getNome());
		
		JLabel lblLocal = new JLabel("Local:");
		lblLocal.setBounds(22, 57, 73, 15);
		contentPane.add(lblLocal);
		
		textFieldLocal = new JTextField();
		textFieldLocal.setEditable(false);
		textFieldLocal.setBounds(78, 55, 334, 19);
		contentPane.add(textFieldLocal);
		textFieldLocal.setColumns(10);
		textFieldLocal.setText("IF Goiano - Campus Ceres");

		
		JButton btnLiberar = new JButton("Liberar");
		btnLiberar.setBounds(78, 136, 89, 23);
		contentPane.add(btnLiberar);
		
		JButton btnBloquear = new JButton("Bloquear");
		btnBloquear.setBounds(201, 136, 89, 23);
		contentPane.add(btnBloquear);
		
		btnLiberar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evento.setLiberado(true);
				textFieldEstado.setText("Liberado");
				btnLiberar.setEnabled(false);
				btnBloquear.setEnabled(true);
			}
		});
		
		btnBloquear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evento.setLiberado(false);
				textFieldEstado.setText("Bloqueado");
				btnLiberar.setEnabled(true);
				btnBloquear.setEnabled(false);
			}
		});
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(22, 103, 66, 15);
		contentPane.add(lblEstado);
		
		textFieldEstado = new JTextField();
		textFieldEstado.setBounds(78, 100, 207, 20);
		contentPane.add(textFieldEstado);
		textFieldEstado.setColumns(10);		
		if(evento.getLiberado() == true) {
			textFieldEstado.setText("Liberado");
			btnLiberar.setEnabled(false);
		}else {
			textFieldEstado.setText("Bloqueado");
			btnBloquear.setEnabled(false);
		}

		JLabel lblLogotipo = new JLabel("Logotipo do Evento:");
		lblLogotipo.setBounds(22, 229, 131, 14);
		contentPane.add(lblLogotipo);
		
		JLabel lblLogo = new JLabel();
		lblLogo.setBounds(300, 88, 401, 217);
		contentPane.add(lblLogo);

		JButton btnEscolherArquivo = new JButton("Enviar Arquivo");
		btnEscolherArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(btnEscolherArquivo);
				File file = fc.getSelectedFile();
				try {
					caminho = Paths.get(file.getAbsolutePath());
					lblLogo.setIcon(new ImageIcon(caminho.toString()));
					getContentPane().add(lblLogo);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(btnEscolherArquivo, "Arquivo não carregado");
				}
			}
		});
		
				
		btnEscolherArquivo.setBounds(145, 225, 133, 23);
		contentPane.add(btnEscolherArquivo);
		JLabel lblInformaesSobreO = new JLabel("Informações sobre o Evento");
		lblInformaesSobreO.setForeground(Color.BLUE);
		lblInformaesSobreO.setBounds(22, 297, 224, 15);
		contentPane.add(lblInformaesSobreO);
	
		JTextArea textAreaInformacoes = new JTextArea();
		textAreaInformacoes.setBounds(22, 324, 390, 69);
		contentPane.add(textAreaInformacoes);
		textAreaInformacoes.setText(evento.getInformacoes());
		
		JButton btnSalvar = new JButton("Salvar Alterações");
		btnSalvar.addActionListener(new ActionListener() {
			private long idEvento;
			public void actionPerformed(ActionEvent e) {
				this.idEvento = evento.getId();
				if(textFieldNome.getText().isEmpty() || textAreaInformacoes.getText().isEmpty()){
					JOptionPane.showMessageDialog(null,"Não é possivel salvar com campos vazios");		
				}
				else {
					EventoData eventoData = new EventoData();
					eventoData.setNome(textFieldNome.getText());
					eventoData.setInformacoes(textAreaInformacoes.getText());
					eventoData.setSituacao(evento.getLiberado());
					eventoData.setLogotipo(caminho.toString());
					eventoData.setIdEvento(idEvento);
					new EventoController().editarEvento(eventoData);

					JOptionPane.showMessageDialog(null,"Alteração salva com sucesso");
				}					
			}
		});
					
		btnSalvar.setForeground(Color.BLUE);
		btnSalvar.setBounds(313, 404, 176, 25);
		contentPane.add(btnSalvar);
		
		setVisible(true);
		
	}
}
