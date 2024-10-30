package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JButton;

public class PanelVistaEjercicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList<String> listaEjercicios;
	private JButton btnCerrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelVistaEjercicio frame = new PanelVistaEjercicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PanelVistaEjercicio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		listaEjercicios = new JList<String>();
		listaEjercicios.setBounds(46, 11, 346, 180);
		contentPane.add(listaEjercicios);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(169, 227, 89, 23);
		contentPane.add(btnCerrar);
	}

	public JList<String> getListaEjercicios() {
		return listaEjercicios;
	}

	public void setListaEjercicios(JList<String> listaEjercicios) {
		this.listaEjercicios = listaEjercicios;
	}

	public JButton getBtnCerrar() {
		return btnCerrar;
	}

	public void setBtnCerrar(JButton btnCerrar) {
		this.btnCerrar = btnCerrar;
	}
}
