package principal;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		iniciarAplicacion();

	}

	public static void iniciarAplicacion() {
		// TODO Auto-generated method stub
		try {

			// Creamos el objeto vista.
			vista.PanelLogin ventanaPrincipal = new vista.PanelLogin();
			ventanaPrincipal.setVisible(true);

			// Creamos en controlador con acceso al modelo y la vista
			new controlador.controlador(ventanaPrincipal);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
