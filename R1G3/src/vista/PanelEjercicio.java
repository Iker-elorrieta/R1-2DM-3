package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class PanelEjercicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, panelEjercicios;
	private JLabel lblCronometroWorkout, lblNomEjer, lblNomWorkout, lblCronometroEjercicio, lblDescanso;
	private JButton btnEmpezar, btnSalir;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelEjercicio frame = new PanelEjercicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PanelEjercicio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 677, 510);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 661, 471);
		contentPane.add(scrollPane);

		panelEjercicios = new JPanel();
		panelEjercicios.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(panelEjercicios);
		panelEjercicios.setLayout(null);

		lblCronometroWorkout = new JLabel("Cronometro Workout: ");
		lblCronometroWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		lblCronometroWorkout.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblCronometroWorkout.setBounds(10, 11, 198, 84);
		panelEjercicios.add(lblCronometroWorkout);

		lblNomEjer = new JLabel("Ejercicio :");
		lblNomEjer.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomEjer.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblNomEjer.setBounds(237, 11, 198, 84);
		panelEjercicios.add(lblNomEjer);

		lblNomWorkout = new JLabel("");
		lblNomWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomWorkout.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblNomWorkout.setBounds(449, 11, 198, 84);
		panelEjercicios.add(lblNomWorkout);

		lblCronometroEjercicio = new JLabel("Cronometro Ejercicio:");
		lblCronometroEjercicio.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblCronometroEjercicio.setHorizontalAlignment(SwingConstants.LEFT);
		lblCronometroEjercicio.setBounds(10, 126, 366, 26);
		panelEjercicios.add(lblCronometroEjercicio);

		lblDescanso = new JLabel("Descanso:");
		lblDescanso.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblDescanso.setBounds(10, 163, 178, 53);
		panelEjercicios.add(lblDescanso);

		btnEmpezar = new JButton("Empezar");
		btnEmpezar.setBackground(new Color(0, 128, 0));
		btnEmpezar.setForeground(Color.WHITE);
		btnEmpezar.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		btnEmpezar.setBounds(107, 395, 151, 49);
		panelEjercicios.add(btnEmpezar);

		btnSalir = new JButton("Salir");
		btnSalir.setBackground(Color.RED);
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		btnSalir.setBounds(422, 395, 151, 49);
		panelEjercicios.add(btnSalir);

	}



	

	public JPanel getPanelEjercicios() {
		return panelEjercicios;
	}

	public void setPanelEjercicios(JPanel panelEjercicios) {
		this.panelEjercicios = panelEjercicios;
	}

	public JButton getBtnEmpezar() {
		return btnEmpezar;
	}

	public void setBtnEmpezar(JButton btnEmpezar) {
		this.btnEmpezar = btnEmpezar;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public void setBtnSalir(JButton btnSalir) {
		this.btnSalir = btnSalir;
	}

	public JLabel getLblNomEjer() {
		return lblNomEjer;
	}

	public void setLblNomEjer(JLabel lblNomEjer) {
		this.lblNomEjer = lblNomEjer;
	}

	public JLabel getLblNomWorkout() {
		return lblNomWorkout;
	}

	public void setLblNomWorkout(JLabel lblNomWorkout) {
		this.lblNomWorkout = lblNomWorkout;
	}

	public JLabel getLblCronometroEjercicio() {
		return lblCronometroEjercicio;
	}

	public void setLblCronometroEjercicio(JLabel lblCronometroEjercicio) {
		this.lblCronometroEjercicio = lblCronometroEjercicio;
	}

	public JLabel getLblDescanso() {
		return lblDescanso;
	}

	public void setLblDescanso(JLabel lblDescanso) {
		this.lblDescanso = lblDescanso;
	}

	public JLabel getLblCronometroWorkout() {
		return lblCronometroWorkout;
	}

	public void setLblCronometroWorkout(JLabel lblCronometroWorkout) {
		this.lblCronometroWorkout = lblCronometroWorkout;
	}
}
