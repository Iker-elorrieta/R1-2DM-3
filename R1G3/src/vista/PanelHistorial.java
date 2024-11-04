package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;

public class PanelHistorial extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaHistorico;


	public PanelHistorial() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 677, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 42, 603, 384);
		contentPane.add(scrollPane);
		
		tablaHistorico = new JTable();
		tablaHistorico.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre Workout", "Nivel Workout", "% Ejecicios Completados", "Fecha", "Tiempo Estimado", "Tiempo Total"
			}
		));
		tablaHistorico.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
		scrollPane.setViewportView(tablaHistorico);
	}
}
