package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;


import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PanelLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelLogin;
	private JTextField txtFNombre;
	private JTextField txtFContrasena;
	private JButton btnIniciar;
	private JButton btnRegistrarse;



	public PanelLogin() {

		ImageIcon logo = new ImageIcon("img/EloFiteness.png");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 677, 510);
		panelLogin = new JPanel();
		panelLogin.setBackground(new Color(255, 255, 255));
		panelLogin.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panelLogin);
		panelLogin.setLayout(null);

		JLabel lblInicioSesion = new JLabel("Inicio de Sesion");
		lblInicioSesion.setFont(new Font("Rockwell Condensed", Font.BOLD, 28));
		lblInicioSesion.setHorizontalAlignment(SwingConstants.CENTER);
		lblInicioSesion.setBounds(202, 65, 235, 40);
		panelLogin.add(lblInicioSesion);

		txtFNombre = new JTextField();
		txtFNombre.setFont(new Font("Nirmala UI", Font.PLAIN, 18));
		txtFNombre.setBounds(232, 167, 235, 29);
		panelLogin.add(txtFNombre);
		txtFNombre.setColumns(10);

		txtFContrasena = new JTextField();
		txtFContrasena.setFont(new Font("Nirmala UI", Font.PLAIN, 18));
		txtFContrasena.setColumns(10);
		txtFContrasena.setBounds(232, 244, 235, 29);
		panelLogin.add(txtFContrasena);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setFont(new Font("Rockwell Condensed", Font.PLAIN, 26));
		lblNombre.setBounds(110, 170, 112, 22);
		panelLogin.add(lblNombre);

		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrasena.setFont(new Font("Rockwell Condensed", Font.PLAIN, 26));
		lblContrasena.setBounds(110, 247, 112, 22);
		panelLogin.add(lblContrasena);

		btnIniciar = new JButton("Iniciar Sesion");
		btnIniciar.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		btnIniciar.setBounds(97, 356, 186, 29);
		panelLogin.add(btnIniciar);

		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		btnRegistrarse.setBounds(348, 356, 186, 29);
		panelLogin.add(btnRegistrarse);

		JLabel lblFoto = new JLabel("");
		lblFoto.setBounds(20, 21, 127, 86);
		panelLogin.add(lblFoto);
		lblFoto.setIcon(logo);

	}

	public JPanel getPanelLogin() {
		return panelLogin;
	}

	public void setPanelLogin(JPanel panelLogin) {
		this.panelLogin = panelLogin;
	}

	public JTextField getTxtFNombre() {
		return txtFNombre;
	}

	public void setTxtFNombre(JTextField txtFNombre) {
		this.txtFNombre = txtFNombre;
	}

	public JTextField getTxtFContrasena() {
		return txtFContrasena;
	}

	public void setTxtFContrasena(JTextField txtFContrasena) {
		this.txtFContrasena = txtFContrasena;
	}

	public JButton getBtnIniciar() {
		return btnIniciar;
	}

	public void setBtnIniciar(JButton btnIniciar) {
		this.btnIniciar = btnIniciar;
	}

	public JButton getBtnRegistrarse() {
		return btnRegistrarse;
	}

	public void setBtnRegistrarse(JButton btnRegistrarse) {
		this.btnRegistrarse = btnRegistrarse;
	}

}
