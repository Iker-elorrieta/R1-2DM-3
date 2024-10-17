package vista;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class PanelWorkouts extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblPerfil, lblNivel;
	private JPanel panelWorkout;
	private JButton btnHistorial;
	private JComboBox<String> cmbxFiltrarNivel;
	private JScrollPane scrollPane;



	public PanelWorkouts() {

		ImageIcon perfil = new ImageIcon("img/perfilUsuario.jpg");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 677, 510);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 661, 471);
		contentPane.add(scrollPane);

		panelWorkout = new JPanel();
		panelWorkout.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(panelWorkout);
		panelWorkout.setLayout(null);

		lblPerfil = new JLabel("");
		lblPerfil.setBounds(540, 21, 98, 87);
		panelWorkout.add(lblPerfil);
		lblPerfil.setIcon(perfil);

		lblNivel = new JLabel("Nivel: ");
		lblNivel.setFont(new Font("Nirmala UI", Font.PLAIN, 17));
		lblNivel.setBounds(10, 57, 83, 24);
		panelWorkout.add(lblNivel);

		JLabel lblWorkouts = new JLabel("Workouts");
		lblWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkouts.setFont(new Font("Rockwell Condensed", Font.BOLD, 24));
		lblWorkouts.setBounds(236, 48, 179, 39);
		panelWorkout.add(lblWorkouts);

		cmbxFiltrarNivel = new JComboBox<String>();
		cmbxFiltrarNivel.setFont(new Font("Nirmala UI", Font.PLAIN, 15));
		cmbxFiltrarNivel.setBounds(540, 121, 98, 22);
		panelWorkout.add(cmbxFiltrarNivel);

		btnHistorial = new JButton("Historial");
		btnHistorial.setFont(new Font("Nirmala UI", Font.PLAIN, 15));
		btnHistorial.setBounds(540, 171, 98, 29);
		panelWorkout.add(btnHistorial);
		
	}





	public JLabel getLblPerfil() {
		return lblPerfil;
	}



	public void setLblPerfil(JLabel lblPerfil) {
		this.lblPerfil = lblPerfil;
	}



	public JLabel getLblNivel() {
		return lblNivel;
	}



	public void setLblNivel(JLabel lblNivel) {
		this.lblNivel = lblNivel;
	}



	public JPanel getPanelWorkout() {
		return panelWorkout;
	}



	public void setPanelWorkout(JPanel panelWorkout) {
		this.panelWorkout = panelWorkout;
	}



	public JButton getBtnHistorial() {
		return btnHistorial;
	}



	public void setBtnHistorial(JButton btnHistorial) {
		this.btnHistorial = btnHistorial;
	}



	public JComboBox<String> getCmbxFiltrarNivel() {
		return cmbxFiltrarNivel;
	}



	public void setCmbxFiltrarNivel(JComboBox<String> cmbxFiltrarNivel) {
		this.cmbxFiltrarNivel = cmbxFiltrarNivel;
	}



	public JScrollPane getScrollPane() {
		return scrollPane;
	}



	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
	
}
