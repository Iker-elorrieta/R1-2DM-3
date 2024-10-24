package principal;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		iniciarAplicacion();

	}

	public static void iniciarAplicacion() {
		// TODO Auto-generated method stub
		try {

			
			vista.PanelLogin ventanaPrincipal = new vista.PanelLogin();
			ventanaPrincipal.setVisible(true);

		
			new controlador.Controlador(ventanaPrincipal);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
