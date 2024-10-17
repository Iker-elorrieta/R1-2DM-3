package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;
import modelo.Cliente;

public class controlador {

	private vista.PanelLogin panelLogin;
	private vista.PanelRegistro panelRegistro;
	private Cliente usuarioIniciado = null;
	private vista.PanelPerfil panelPerfil;
	private vista.PanelWorkouts panelWorkouts;
	
	private String campoNombre = "nom_usuario", campoApellido = "ap_usuario", campoEmail = "email_usuario",
			campoFecha = "fechaNac_usuario", campoNivel = "nivel_usuario", campoEntrenador = "tipo_entrenador",
			campoContrasena = "cont_usuario";

	public controlador(vista.PanelLogin panelLogin) {
		this.panelLogin = panelLogin;
		this.panelLogin.setVisible(true);
		this.inicializarControlador();
	}

	private void inicializarControlador() {
		// TODO Auto-generated method stub
		this.panelLogin.getBtnIniciar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Firestore co = null;

				try {
					co = Conexion.conectar();
					ApiFuture<QuerySnapshot> query = co.collection("Usuarios").get();
					QuerySnapshot querySnapshot = query.get();
					List<QueryDocumentSnapshot> usuarios = querySnapshot.getDocuments();
					for (QueryDocumentSnapshot usuario : usuarios) {
						if (usuario.getString("nom_usuario").equals(panelLogin.getTxtFNombre().getText())
								&& usuario.getString("cont_usuario").equals(panelLogin.getTxtFContrasena().getText())) {
							usuarioIniciado = new Cliente(usuario.getString("nom_usuario"),
									usuario.getString("ap_usuario"), usuario.getString("email_usuario"),
									usuario.getString("cont_usuario"), usuario.getDate("fechaNac_usuario"),
									usuario.getBoolean("tipo_entrenador"),
									Integer.parseInt(usuario.getString("nivel_usuario")), usuario.getId());
							panelWorkouts = new vista.PanelWorkouts();
							panelWorkouts.setVisible(true);
							panelLogin.setVisible(false);
							inicializarWorkouts();
						}
					}
					if (usuarioIniciado == null) {
						JOptionPane.showMessageDialog(null, "El usuario o contraseña no coinciden");
					}

				} catch (IOException | InterruptedException | ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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

				JTextField[] componentes = new JTextField[] { panelRegistro.getTxtFNombre(),
						panelRegistro.getTxtFApellidos(), panelRegistro.getTxtFEmail(), panelRegistro.getTxtFFecha(),
						panelRegistro.getTxtFContrasena() };
				String mensaje = comprobarError(componentes);
				if (mensaje.isEmpty()) {

					Firestore co = null;

					try {
						co = Conexion.conectar();

						CollectionReference usuarios = co.collection("Usuarios");
						Map<String, Object> usuarioNuevo = new HashMap<>();

						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
						Date fecha = null;
						try {
							fecha = formato.parse(componentes[3].getText());
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						usuarioNuevo.put(campoNombre, componentes[0].getText());
						usuarioNuevo.put(campoApellido, componentes[1].getText());
						usuarioNuevo.put(campoEmail, componentes[2].getText());
						usuarioNuevo.put(campoFecha, fecha);
						usuarioNuevo.put(campoContrasena, componentes[4].getText());
						usuarioNuevo.put(campoNivel, "1");
						usuarioNuevo.put(campoEntrenador, false);
						usuarios.add(usuarioNuevo);

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					panelRegistro.dispose();
					panelLogin.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, mensaje);
				}
			}

			private String comprobarError(JTextField[] componentes) {
				// TODO Auto-generated method stub
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				formato.setLenient(false);
				for (JTextField componente : componentes) {
					if (componente.getText().isEmpty()) {
						return "Todos los campos deben estar rellenados";
					}
				}
				if (!componentes[2].getText().contains("@")) {
					return "El email debe tener un formato correcto";
				}
				try {
					formato.parse(componentes[3].getText());

				} catch (ParseException e) {
					return "La fecha debe estar en formato dd/MM/yyyy";
				}

				return "";
			}
		});
	}

	private void inicializarPerfil() {

	}
	
	private void inicializarWorkouts() {
		// TODO Auto-generated method stub
		
	}
}
