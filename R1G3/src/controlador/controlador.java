package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;
import modelo.Cliente;
import modelo.Ejercicio;
import modelo.Serie;
import modelo.Workout;

public class controlador {
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	private vista.PanelLogin panelLogin;
	private vista.PanelRegistro panelRegistro;
	private Cliente usuarioIniciado = null;
	private vista.PanelPerfil panelPerfil;
	private vista.PanelWorkouts panelWorkouts;
	private ArrayList<Workout> workouts;
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
						if (usuario.getString(campoNombre).equals(panelLogin.getTxtFNombre().getText()) && usuario
								.getString(campoContrasena).equals(panelLogin.getTxtFContrasena().getText())) {
							double nivel = usuario.getDouble(campoNivel);
							usuarioIniciado = new Cliente(usuario.getString(campoNombre),
									usuario.getString(campoApellido), usuario.getString(campoEmail),
									usuario.getString(campoContrasena), usuario.getDate(campoFecha),
									usuario.getBoolean(campoEntrenador), (int) nivel, usuario.getId());
						}
					}
					if (usuarioIniciado == null) {
						JOptionPane.showMessageDialog(null, "El usuario o contraseña no coinciden");
					} else {
						panelWorkouts = new vista.PanelWorkouts();

						panelWorkouts.getLblNivel().setText("Nivel: " + usuarioIniciado.getNivel());

						for (int i = -1; i != usuarioIniciado.getNivel(); i++) {
							panelWorkouts.getCmbxFiltrarNivel().addItem(String.valueOf(i + 1));
							panelWorkouts.revalidate();
							panelWorkouts.repaint();
						}
						workouts = cargarWorkouts();
						panelWorkouts.setVisible(true);
						panelLogin.setVisible(false);
						inicializarWorkouts();
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

	private ArrayList<Workout> cargarWorkouts() {
		Firestore co = null;
		ArrayList<Workout> workouts = new ArrayList<Workout>();

		try {
			co = Conexion.conectar();
			ApiFuture<QuerySnapshot> query = co.collection("Workouts").get();
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> workoutsLista = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot workout : workoutsLista) {
				ArrayList<Ejercicio> ejerciciosGuardados = new ArrayList<Ejercicio>();
				DocumentReference workoutRef = workout.getReference();
				CollectionReference ejercicios = workoutRef.collection("Ejercicios");
				List<QueryDocumentSnapshot> ejerciciosLista = ejercicios.get().get().getDocuments();
				for (QueryDocumentSnapshot ejercicio : ejerciciosLista) {
					ArrayList<Serie> seriesGuardadas = new ArrayList<Serie>();
					DocumentReference ejercicioRef = ejercicio.getReference();
					CollectionReference series = ejercicioRef.collection("Series");
					List<QueryDocumentSnapshot> seriesLista = series.get().get().getDocuments();
					for (QueryDocumentSnapshot serie : seriesLista) {
						double cuenta = serie.getDouble("cuenta_regresiva");
						double repeticiones = serie.getDouble("num_repeticiones");
						Serie nuevaSerie = new Serie((int) cuenta, serie.getString("foto_series"),
								serie.getString("nom_series"), (int) repeticiones, serie.getId());
						seriesGuardadas.add(nuevaSerie);
					}
					double cronometro = ejercicio.getDouble("cronometro");
					double descanso = ejercicio.getDouble("descanso");
					Ejercicio nuevoEjercicio = new Ejercicio((int) cronometro, ejercicio.getString("desc_ejer"),
							(int) descanso, ejercicio.getString("nom_ejer"), ejercicio.getId(), seriesGuardadas);
					ejerciciosGuardados.add(nuevoEjercicio);

				}
				double nivel = workout.getDouble("nivel_workout");
				double numeroEjericios = workout.getDouble("numEjer_workout");
				Workout nuevoWorkout = new Workout((int) nivel, workout.getString("nom_workout"), (int) numeroEjericios,
						workout.getString("video_workout"), ejerciciosGuardados, workout.getId());
				workouts.add(nuevoWorkout);
			}
		} catch (IOException | InterruptedException | ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	private void inicalizarRegistro() {
		this.panelRegistro.getBtnRegistrarse().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JTextField[] componentes = new JTextField[] { panelRegistro.getTxtFNombre(),
						panelRegistro.getTxtFApellidos(), panelRegistro.getTxtFEmail(), panelRegistro.getTxtFFecha(),
						panelRegistro.getTxtFContrasena() };
				String mensaje = comprobarError(componentes);
				if (mensaje.isEmpty()) {

					Date fecha = null;
					try {
						fecha = formato.parse(componentes[3].getText());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Cliente nuevoCliente = new Cliente(componentes[0].getText(), componentes[1].getText(),
							componentes[2].getText(), componentes[4].getText(), fecha, false, 0);
					nuevoCliente.anadirContacto();

					panelRegistro.dispose();
					panelLogin.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, mensaje);
				}
			}

		});
	}

	private void inicializarPerfil() {
		panelPerfil.getBtnGuardarDatos().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField[] componentes = new JTextField[] { panelPerfil.getTxtFNombre(),
						panelPerfil.getTxtFApellidos(), panelPerfil.getTxtFEmail(), panelPerfil.getTxtFFecha(),
						panelPerfil.getTxtFContrasena() };

				String mensaje = comprobarError(componentes);
				if (mensaje.isEmpty()) {

					Date fecha = null;
					try {
						fecha = formato.parse(componentes[3].getText());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					usuarioIniciado.setNombre(componentes[0].getText());
					usuarioIniciado.setApellidos(componentes[1].getText());
					usuarioIniciado.setEmail(componentes[2].getText());
					usuarioIniciado.setFechaNacimiento(fecha);
					usuarioIniciado.setContrasena(componentes[4].getText());
					usuarioIniciado.actualizarContacto();

					panelPerfil.dispose();

				} else {
					JOptionPane.showMessageDialog(null, mensaje);
				}
			}
		});
	}

	private void inicializarWorkouts() {
		// TODO Auto-generated method stub
		panelWorkouts.getLblPerfil().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				panelPerfil = new vista.PanelPerfil();
				panelPerfil.setVisible(true);
				panelPerfil.getTxtFNombre().setText(usuarioIniciado.getNombre());
				panelPerfil.getTxtFApellidos().setText(usuarioIniciado.getApellidos());
				panelPerfil.getTxtFContrasena().setText(usuarioIniciado.getContrasena());
				panelPerfil.getTxtFEmail().setText(usuarioIniciado.getEmail());
				panelPerfil.getTxtFFecha().setText(formato.format(usuarioIniciado.getFechaNacimiento()));
				inicializarPerfil();
			}
		});
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
}
