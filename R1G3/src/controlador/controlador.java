package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class controlador {

	private vista.PanelLogin panelLogin;
	private vista.PanelRegistro panelRegistro;

	public controlador(vista.PanelLogin panelLogin) {
		this.panelLogin = panelLogin;
		this.panelLogin.setVisible(true);
		this.inicializarControlador();
	}

	private void inicializarControlador() {
		// TODO Auto-generated method stub
		this.panelLogin.getBtnIniciar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		this.panelLogin.getBtnRegistrarse().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelRegistro = new vista.PanelRegistro();
				panelRegistro.setVisible(true);
				panelLogin.setVisible(false);
				inicalizarRegistro();
			}
		});
	}

	private void inicalizarRegistro() {
		this.panelRegistro.getBtnRegistrarse().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				panelRegistro.dispose();
				panelLogin.setVisible(true);
				
			}
		});
	}
}
