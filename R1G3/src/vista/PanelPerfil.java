package vista;



import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PanelPerfil extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelPerfil;
	private JTextField txtFNombre;
	private JTextField txtFApellidos;
	private JTextField txtFEmail;
	private JTextField txtFFecha;
	private JTextField txtFContrasena;
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JLabel lblEmail;
	private JLabel lblFecha;
	private JLabel lblContrasena;
	private JButton btnGuardarDatos;

	/**
	 * Create the frame.
	 */
	public PanelPerfil() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 677, 510);
		panelPerfil = new JPanel();
		panelPerfil.setBackground(new Color(255, 255, 255));
		panelPerfil.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panelPerfil);
		panelPerfil.setLayout(null);

		JLabel lblPerfil = new JLabel("Tu Perfil");
		lblPerfil.setFont(new Font("Rockwell Condensed", Font.BOLD, 28));
		lblPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		lblPerfil.setBounds(202, 56, 235, 40);
		panelPerfil.add(lblPerfil);

		txtFNombre = new JTextField();
		txtFNombre.setFont(new Font("Nirmala UI", Font.PLAIN, 24));
		txtFNombre.setBounds(304, 114, 207, 27);
		panelPerfil.add(txtFNombre);
		txtFNombre.setColumns(10);

		txtFApellidos = new JTextField();
		txtFApellidos.setFont(new Font("Nirmala UI", Font.PLAIN, 24));
		txtFApellidos.setColumns(10);
		txtFApellidos.setBounds(304, 164, 207, 27);
		panelPerfil.add(txtFApellidos);

		txtFEmail = new JTextField();
		txtFEmail.setFont(new Font("Nirmala UI", Font.PLAIN, 24));
		txtFEmail.setColumns(10);
		txtFEmail.setBounds(304, 216, 207, 27);
		panelPerfil.add(txtFEmail);

		txtFFecha = new JTextField();
		txtFFecha.setFont(new Font("Nirmala UI", Font.PLAIN, 24));
		txtFFecha.setColumns(10);
		txtFFecha.setBounds(304, 270, 207, 27);
		panelPerfil.add(txtFFecha);

		txtFContrasena = new JTextField();
		txtFContrasena.setFont(new Font("Nirmala UI", Font.PLAIN, 24));
		txtFContrasena.setColumns(10);
		txtFContrasena.setBounds(304, 320, 207, 27);
		panelPerfil.add(txtFContrasena);

		lblNombre = new JLabel("Nombre:");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		lblNombre.setBounds(87, 116, 207, 27);
		panelPerfil.add(lblNombre);

		lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblApellidos.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		lblApellidos.setBounds(87, 164, 207, 27);
		panelPerfil.add(lblApellidos);

		lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		lblEmail.setBounds(87, 216, 207, 27);
		panelPerfil.add(lblEmail);

		lblFecha = new JLabel("Fecha de nacimiento:");
		lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFecha.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		lblFecha.setBounds(87, 270, 207, 27);
		panelPerfil.add(lblFecha);

		lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrasena.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		lblContrasena.setBounds(87, 320, 207, 27);
		panelPerfil.add(lblContrasena);

		btnGuardarDatos = new JButton("Guardar datos");
		btnGuardarDatos.setFont(new Font("Rockwell Condensed", Font.PLAIN, 24));
		btnGuardarDatos.setBounds(202, 400, 235, 29);
		panelPerfil.add(btnGuardarDatos);
	}

}
