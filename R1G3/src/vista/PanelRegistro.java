package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import principal.Principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;

public class PanelRegistro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelRegistro;
	private JTextField txtFNombre;
	private JTextField txtFApellidos;
	private JTextField txtFEmail;
	private JTextField txtFContrasena;
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JLabel lblEmail;
	private JLabel lblFecha;
	private JLabel lblContrasena;
	private JButton btnRegistrarse;
	private JDateChooser dateChooser;

	public PanelRegistro() {

		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				Principal.iniciarAplicacion();
				dispose();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 677, 510);
		panelRegistro = new JPanel();
		panelRegistro.setBackground(new Color(255, 255, 255));
		panelRegistro.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panelRegistro);
		panelRegistro.setLayout(null);

		JLabel lblInicioSesion = new JLabel("Registro");
		lblInicioSesion.setFont(new Font("Rockwell Condensed", Font.BOLD, 28));
		lblInicioSesion.setHorizontalAlignment(SwingConstants.CENTER);
		lblInicioSesion.setBounds(202, 56, 235, 40);
		panelRegistro.add(lblInicioSesion);

		txtFNombre = new JTextField();
		txtFNombre.setFont(new Font("Nirmala UI", Font.PLAIN, 24));
		txtFNombre.setBounds(304, 114, 207, 27);
		panelRegistro.add(txtFNombre);
		txtFNombre.setColumns(10);

		txtFApellidos = new JTextField();
		txtFApellidos.setFont(new Font("Nirmala UI", Font.PLAIN, 24));
		txtFApellidos.setColumns(10);
		txtFApellidos.setBounds(304, 164, 207, 27);
		panelRegistro.add(txtFApellidos);

		txtFEmail = new JTextField();
		txtFEmail.setFont(new Font("Nirmala UI", Font.PLAIN, 24));
		txtFEmail.setColumns(10);
		txtFEmail.setBounds(304, 216, 207, 27);
		panelRegistro.add(txtFEmail);

	
		txtFContrasena = new JTextField();
		txtFContrasena.setFont(new Font("Nirmala UI", Font.PLAIN, 24));
		txtFContrasena.setColumns(10);
		txtFContrasena.setBounds(304, 320, 207, 27);
		panelRegistro.add(txtFContrasena);

		lblNombre = new JLabel("Nombre:");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		lblNombre.setBounds(69, 116, 225, 27);
		panelRegistro.add(lblNombre);

		lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblApellidos.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		lblApellidos.setBounds(69, 166, 225, 27);
		panelRegistro.add(lblApellidos);

		lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		lblEmail.setBounds(69, 218, 225, 27);
		panelRegistro.add(lblEmail);

		lblFecha = new JLabel("Fecha de nacimiento:");
		lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFecha.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		lblFecha.setBounds(69, 272, 225, 27);
		panelRegistro.add(lblFecha);

		lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrasena.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		lblContrasena.setBounds(87, 322, 207, 27);
		panelRegistro.add(lblContrasena);

		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		btnRegistrarse.setBounds(165, 399, 291, 29);
		panelRegistro.add(btnRegistrarse);

		dateChooser = new JDateChooser();
		dateChooser.setMaxSelectableDate(new Date());
		dateChooser.setBounds(304, 270, 207, 27);
		panelRegistro.add(dateChooser);

	}

	public JPanel getPanelRegistro() {
		return panelRegistro;
	}

	public void setPanelRegistro(JPanel panelRegistro) {
		this.panelRegistro = panelRegistro;
	}

	public JTextField getTxtFNombre() {
		return txtFNombre;
	}

	public void setTxtFNombre(JTextField txtFNombre) {
		this.txtFNombre = txtFNombre;
	}

	public JTextField getTxtFApellidos() {
		return txtFApellidos;
	}

	public void setTxtFApellidos(JTextField txtFApellidos) {
		this.txtFApellidos = txtFApellidos;
	}

	public JTextField getTxtFEmail() {
		return txtFEmail;
	}

	public void setTxtFEmail(JTextField txtFEmail) {
		this.txtFEmail = txtFEmail;
	}

	public JTextField getTxtFContrasena() {
		return txtFContrasena;
	}

	public void setTxtFContrasena(JTextField txtFContrasena) {
		this.txtFContrasena = txtFContrasena;
	}

	public JButton getBtnRegistrarse() {
		return btnRegistrarse;
	}

	public void setBtnRegistrarse(JButton btnRegistrarse) {
		this.btnRegistrarse = btnRegistrarse;
	}

	public JDateChooser getDateChooser() {
		return dateChooser;
	}

	public void setDateChooser(JDateChooser dateChooser) {
		this.dateChooser = dateChooser;
	}
	
}
