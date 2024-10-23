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

public class PanelEjercicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblCronometroWorkout, lblNomEjer, lblNomWorkout, lblCronometroEjercicio, lblDescanso, lblSerie1,
			lblSerie2, lblCronometroSerie2, lblCronometroSerie1, lblCronometroDescanso;
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

		lblCronometroWorkout = new JLabel("Cronometro Workout: ");
		lblCronometroWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		lblCronometroWorkout.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblCronometroWorkout.setBounds(10, 11, 198, 84);
		contentPane.add(lblCronometroWorkout);

		lblNomEjer = new JLabel("Ejercicio :");
		lblNomEjer.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomEjer.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblNomEjer.setBounds(237, 11, 198, 84);
		contentPane.add(lblNomEjer);

		lblNomWorkout = new JLabel("");
		lblNomWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomWorkout.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblNomWorkout.setBounds(449, 11, 198, 84);
		contentPane.add(lblNomWorkout);

		lblCronometroEjercicio = new JLabel("Cronometro Ejercicio:");
		lblCronometroEjercicio.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblCronometroEjercicio.setHorizontalAlignment(SwingConstants.LEFT);
		lblCronometroEjercicio.setBounds(10, 126, 366, 26);
		contentPane.add(lblCronometroEjercicio);

		lblDescanso = new JLabel("Descanso:");
		lblDescanso.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblDescanso.setBounds(10, 163, 77, 53);
		contentPane.add(lblDescanso);

		lblSerie1 = new JLabel("Serie 1:");
		lblSerie1.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblSerie1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSerie1.setBounds(215, 177, 151, 39);
		contentPane.add(lblSerie1);

		lblSerie2 = new JLabel("Serie 2:");
		lblSerie2.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblSerie2.setHorizontalAlignment(SwingConstants.CENTER);
		lblSerie2.setBounds(215, 251, 151, 39);
		contentPane.add(lblSerie2);

		btnEmpezar = new JButton("Empezar");
		btnEmpezar.setBackground(new Color(0, 128, 0));
		btnEmpezar.setForeground(Color.WHITE);
		btnEmpezar.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		btnEmpezar.setBounds(107, 395, 151, 49);
		contentPane.add(btnEmpezar);

		btnSalir = new JButton("Salir");
		btnSalir.setBackground(Color.RED);
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		btnSalir.setBounds(422, 395, 151, 49);
		contentPane.add(btnSalir);

		lblCronometroSerie2 = new JLabel("");
		lblCronometroSerie2.setHorizontalAlignment(SwingConstants.CENTER);
		lblCronometroSerie2.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblCronometroSerie2.setBounds(265, 251, 151, 39);
		contentPane.add(lblCronometroSerie2);

		lblCronometroSerie1 = new JLabel("");
		lblCronometroSerie1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCronometroSerie1.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblCronometroSerie1.setBounds(265, 177, 151, 39);
		contentPane.add(lblCronometroSerie1);

		lblCronometroDescanso = new JLabel("");
		lblCronometroDescanso.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		lblCronometroDescanso.setBounds(102, 175, 106, 32);
		contentPane.add(lblCronometroDescanso);

	}

	public JLabel getLblCronometroSerie2() {
		return lblCronometroSerie2;
	}

	public void setLblCronometroSerie2(JLabel lblCronometroSerie2) {
		this.lblCronometroSerie2 = lblCronometroSerie2;
	}

	public JLabel getLblCronometroSerie1() {
		return lblCronometroSerie1;
	}

	public void setLblCronometroSerie1(JLabel lblCronometroSerie1) {
		this.lblCronometroSerie1 = lblCronometroSerie1;
	}

	public JLabel getLblCronometroDescanso() {
		return lblCronometroDescanso;
	}

	public void setLblCronometroDescanso(JLabel lblCronometroDescanso) {
		this.lblCronometroDescanso = lblCronometroDescanso;
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

	public JLabel getLblSerie1() {
		return lblSerie1;
	}

	public void setLblSerie1(JLabel lblSerie1) {
		this.lblSerie1 = lblSerie1;
	}

	public JLabel getLblSerie2() {
		return lblSerie2;
	}

	public void setLblSerie2(JLabel lblSerie2) {
		this.lblSerie2 = lblSerie2;
	}

	public JLabel getLblCronometroWorkout() {
		return lblCronometroWorkout;
	}

	public void setLblCronometroWorkout(JLabel lblCronometroWorkout) {
		this.lblCronometroWorkout = lblCronometroWorkout;
	}
}
