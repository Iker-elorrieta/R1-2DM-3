package controlador;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;
import modelo.Cliente;
import modelo.Ejercicio;
import modelo.HiloCronometro;
import modelo.HiloEsperar;
import modelo.HiloRegresivo;
import modelo.Historico;
import modelo.Serie;
import modelo.Workout;
import vista.PanelEjercicio;

public class Controlador {
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	private vista.PanelLogin panelLogin;
	private vista.PanelRegistro panelRegistro;
	private Cliente usuarioIniciado = new Cliente();
	private vista.PanelPerfil panelPerfil;
	private vista.PanelWorkouts panelWorkouts;
	private vista.PanelEjercicio panelEjercicio;
	private ArrayList<Workout> workouts;
	private Workout workoutElegido;
	private HiloCronometro hiloWorkout;
	private HiloCronometro hiloEjercicio;
	private HiloEsperar hiloEsperar;
	private HiloRegresivo hiloSerie, hiloDescanso;
	private int contEjercicios, cronometroParado, contSeries = 0;
	private ArrayList<JLabel> labelsWorkout = new ArrayList<JLabel>(), labelsSeries = new ArrayList<JLabel>(),
			labelsFotos = new ArrayList<JLabel>();
	private ArrayList<Integer> tiemposEjercicio = new ArrayList<Integer>();

	public Controlador(vista.PanelLogin panelLogin) {
		this.panelLogin = panelLogin;
		this.panelLogin.setVisible(true);
		this.inicializarControlador();
	}

	public Controlador() {
		// TODO Auto-generated constructor stub
	}

	private void inicializarControlador() {
		// TODO Auto-generated method stub
		this.panelLogin.getBtnIniciar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usuarioIniciado = usuarioIniciado.cargarCliente(panelLogin.getTxtFNombre().getText(),
						panelLogin.getTxtFContrasena().getText());
				if (usuarioIniciado == null) {
					JOptionPane.showMessageDialog(null, "El usuario o contraseña no coinciden");
				} else {
					GenerarBackups.main(null);
					panelWorkouts = new vista.PanelWorkouts();

					panelWorkouts.getLblNivel().setText("Nivel: " + usuarioIniciado.getNivel());
					workouts = cargarWorkouts();
					for (int i = -1; i != usuarioIniciado.getNivel(); i++) {
						panelWorkouts.getCmbxFiltrarNivel().addItem(String.valueOf(i + 1));
						panelWorkouts.revalidate();
						panelWorkouts.repaint();
					}
					rellenarWorkouts();

					panelWorkouts.setVisible(true);
					panelLogin.setVisible(false);
					inicializarWorkouts();
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

	private void rellenarWorkouts() {
		// TODO Auto-generated method stub
		for (int i = 0; i <= workouts.size() - 1; i++) {

			if (usuarioIniciado.getNivel() >= workouts.get(i).getNivel()) {
				JLabel lblWorkout = new JLabel(i + " " + workouts.get(i).getNombre() + " Ejercicios: "
						+ workouts.get(i).getNumEjer() + " Nivel: " + workouts.get(i).getNivel());
				lblWorkout.setToolTipText(String.valueOf(i));
				lblWorkout.setFont(new Font("Nirmala UI", Font.PLAIN, 17));
				lblWorkout.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						panelEjercicio = new PanelEjercicio();
						panelEjercicio.setVisible(true);
						panelWorkouts.setVisible(false);

						workoutElegido = workouts.get(Integer.parseInt(lblWorkout.getToolTipText()));
						panelEjercicio.getLblNomWorkout().setText(workoutElegido.getNombre());
						hiloWorkout = new HiloCronometro(panelEjercicio.getLblCronometroWorkout());
						hiloWorkout.start();
						inicializarEjercicios();
					}

				});
				lblWorkout.setBounds(42, 141 + (40 * i), 349, 22);
				panelWorkouts.getPanelWorkout().add(lblWorkout);

				JLabel lblYoutube = new JLabel("");
				lblYoutube.setToolTipText(String.valueOf(i));
				lblYoutube.setBounds(423, 133 + (40 * i), 46, 39);
				lblYoutube.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {

						if (Desktop.isDesktopSupported()) {
							Desktop desktop = Desktop.getDesktop();
							try {
								URI url = new URI(
										workouts.get(Integer.parseInt(lblYoutube.getToolTipText())).getVideo());
								desktop.browse(url);
							} catch (IOException | URISyntaxException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							System.out.println("La funcionalidad de Desktop no está soportada en este sistema.");

						}
					}
				});
				lblYoutube.setIcon(new ImageIcon("img/logoYT.jpg"));
				panelWorkouts.getPanelWorkout().add(lblYoutube);
				labelsWorkout.add(lblYoutube);
				labelsWorkout.add(lblWorkout);
			}
		}
	}

	private void rellenarWorkoutsFiltrado(int nivel) {
		int o = 0;
		for (int i = 0; i <= workouts.size() - 1; i++) {

			if (nivel == workouts.get(i).getNivel()) {
				JLabel lblWorkout = new JLabel(o + 1 + " " + workouts.get(i).getNombre() + " Ejercicios: "
						+ workouts.get(i).getNumEjer() + " Nivel: " + workouts.get(i).getNivel());
				lblWorkout.setToolTipText(String.valueOf(i));
				lblWorkout.setFont(new Font("Nirmala UI", Font.PLAIN, 17));
				lblWorkout.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						panelEjercicio = new PanelEjercicio();
						panelEjercicio.setVisible(true);
						panelWorkouts.setVisible(false);

						workoutElegido = workouts.get(Integer.parseInt(lblWorkout.getToolTipText()));
						panelEjercicio.getLblNomWorkout().setText(workoutElegido.getNombre());
						hiloWorkout = new HiloCronometro(panelEjercicio.getLblCronometroWorkout());
						hiloWorkout.start();
						inicializarEjercicios();
					}

				});
				lblWorkout.setBounds(42, 141 + (40 * o), 349, 22);
				panelWorkouts.getPanelWorkout().add(lblWorkout);

				JLabel lblYoutube = new JLabel("");
				lblYoutube.setToolTipText(String.valueOf(i));
				lblYoutube.setBounds(423, 133 + (40 * o), 46, 39);
				lblYoutube.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {

						if (Desktop.isDesktopSupported()) {
							Desktop desktop = Desktop.getDesktop();
							try {
								URI url = new URI(
										workouts.get(Integer.parseInt(lblYoutube.getToolTipText())).getVideo());
								desktop.browse(url);
							} catch (IOException | URISyntaxException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							System.out.println("La funcionalidad de Desktop no está soportada en este sistema.");

						}
					}
				});
				lblYoutube.setIcon(new ImageIcon("img/logoYT.jpg"));
				panelWorkouts.getPanelWorkout().add(lblYoutube);
				labelsWorkout.add(lblYoutube);
				labelsWorkout.add(lblWorkout);
				o++;
			}
		}
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
		return workouts;
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
					nuevoCliente.anadirCliente();

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
					usuarioIniciado.actualizarCliente();

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

		panelWorkouts.getCmbxFiltrarNivel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarLabels(labelsWorkout, panelWorkouts.getPanelWorkout());
				rellenarWorkoutsFiltrado(
						Integer.parseInt((String) panelWorkouts.getCmbxFiltrarNivel().getSelectedItem()));
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

	private void inicializarEjercicios() {
		// TODO Auto-generated method stub
		panelEjercicio.getBtnEmpezar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (panelEjercicio.getBtnEmpezar().getText().equals("Empezar")
						|| panelEjercicio.getBtnEmpezar().getText().equals("Siguiente Ejercicio")
						|| panelEjercicio.getBtnEmpezar().getText().equals("Siguiente Serie")) {
					Ejercicio ejercicioActivo = workoutElegido.getEjercicios().get(contEjercicios);
					if (labelsSeries.isEmpty()) {
						for (int i = 0; i < ejercicioActivo.getSeries().size(); i++) {

							JLabel lblSerie = new JLabel("Serie " + (i + 1) + ":");
							lblSerie.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
							lblSerie.setHorizontalAlignment(SwingConstants.CENTER);
							lblSerie.setBounds(257, 125 + (74 * i), 151, 39);

							panelEjercicio.getPanelEjercicios().add(lblSerie);
							labelsSeries.add(lblSerie);

							JLabel lblFoto = new JLabel("");
							lblFoto.setBounds(213, 125 + (74 * i), 46, 39);
							lblFoto.setIcon(new ImageIcon(ejercicioActivo.getSeries().get(i).getFotoSeries()));
							panelEjercicio.getPanelEjercicios().add(lblFoto);
							labelsFotos.add(lblFoto);
							panelEjercicio.getPanelEjercicios().repaint();
							panelEjercicio.getPanelEjercicios().revalidate();
						}
					}
					if (contSeries == 0) {
						panelEjercicio.getLblNomEjer().setText("Ejercicio: " + ejercicioActivo.getNombre());

						hiloEjercicio = new HiloCronometro(panelEjercicio.getLblCronometroEjercicio());
						hiloEjercicio.start();
					}
					hiloSerie = new HiloRegresivo(labelsSeries.get(contSeries),
							ejercicioActivo.getSeries().get(contSeries).getCuentaatras());
					hiloDescanso = new HiloRegresivo(panelEjercicio.getLblDescanso(), ejercicioActivo.getDescanso());
					hiloEsperar = new HiloEsperar(hiloSerie, hiloDescanso, hiloEjercicio,
							panelEjercicio.getBtnEmpezar(), contEjercicios, workoutElegido, ejercicioActivo, contSeries,
							labelsSeries, panelEjercicio.getPanelEjercicios(), labelsFotos, tiemposEjercicio);
					contSeries++;
					if (contSeries == ejercicioActivo.getSeries().size()) {
						contSeries = 0;
						contEjercicios++;

					}

					hiloEsperar.start();
					panelEjercicio.getBtnEmpezar().setBackground(Color.YELLOW);
					panelEjercicio.getBtnEmpezar().setForeground(Color.BLACK);
					panelEjercicio.getBtnEmpezar().setText("Parar");

				} else if (panelEjercicio.getBtnEmpezar().getText().equals("Parar")) {
					if (hiloSerie.isAlive()) {
						hiloSerie.cambiarEstado();
						cronometroParado = 1;
					} else if (hiloDescanso.isAlive()) {
						hiloDescanso.cambiarEstado();
						cronometroParado = 2;

					}
					hiloEjercicio.cambiarEstado();
					panelEjercicio.getBtnEmpezar().setText("Reanudar");
				} else if (panelEjercicio.getBtnEmpezar().getText().equals("Reanudar")) {
					if (cronometroParado == 1) {
						hiloSerie.cambiarEstado();
					} else if (cronometroParado == 2) {
						hiloDescanso.cambiarEstado();
					}
					cronometroParado = 0;
					panelEjercicio.getBtnEmpezar().setText("Parar");
					hiloEjercicio.cambiarEstado();
				}
			}
		});
		panelEjercicio.getBtnSalir().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelEjercicio.dispose();
				panelWorkouts.setVisible(true);
				inicializarWorkouts();
				if (hiloSerie.isAlive()) {
					hiloSerie.terminar();
				}
				if (hiloDescanso.isAlive()) {
					hiloDescanso.terminar();
				}
				if (hiloEjercicio.isAlive()) {
					hiloEjercicio.terminar();
				}
				if (hiloWorkout.isAlive()) {
					hiloWorkout.terminar();
				}
				if (contEjercicios > 0) {
					int tiempoTotal = 0;
					int tiempoEstimado = 0;
					double porcentaje = (100 * contEjercicios) / workoutElegido.getEjercicios().size();
					for (Integer tiempo : tiemposEjercicio) {
						tiempoTotal += tiempo;
					}
					for (Ejercicio ejercicio : workoutElegido.getEjercicios()) {
						for (Serie serie : ejercicio.getSeries()) {
							tiempoEstimado += serie.getCuentaatras();
							tiempoEstimado += ejercicio.getDescanso();
						}
					}
					int cont = 0;
					boolean encontrado = false;
					for (Historico historico : usuarioIniciado.getWorkouts()) {
						if (historico.getNombre().equals(workoutElegido.getNombre())) {

							usuarioIniciado.getWorkouts().get(cont).setPorcentaje(porcentaje);
							usuarioIniciado.getWorkouts().get(cont).setTiempoPrevisto(tiempoEstimado);
							usuarioIniciado.getWorkouts().get(cont).setTiempototal(tiempoTotal);
							usuarioIniciado.getWorkouts().get(cont).setFecha(new Date());
							historico = usuarioIniciado.getWorkouts().get(cont);
							historico.actualizarHistorico(usuarioIniciado, workoutElegido);
							encontrado = true;
						}
						cont++;
					}
					if (porcentaje == 100 && workoutElegido.getNivel() == usuarioIniciado.getNivel()) {
						usuarioIniciado.setNivel(usuarioIniciado.getNivel() + 1);
						usuarioIniciado.actualizarCliente();
					}
					if (!encontrado) {
						Historico historico = new Historico(porcentaje, workoutElegido.getNombre(),
								workoutElegido.getNivel(), tiempoEstimado, tiempoTotal, new Date());
						historico.anadirHistorico(usuarioIniciado, workoutElegido);
						usuarioIniciado.getWorkouts().add(historico);

					}

				}
			}
		});

	}

	public void eliminarLabels(ArrayList<JLabel> labels, JPanel panel) {
		for (JLabel label : labels) {
			panel.remove(label);
			panel.repaint();
			panel.revalidate();
		}
		labels.clear();
	}

	public boolean tieneConexion() {
		try {

			URI uri = new URI("http://www.google.com");
			URL url = uri.toURL();
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("HEAD");
			connection.setConnectTimeout(3000);
			connection.connect();
			return (connection.getResponseCode() == 200);
		} catch (Exception e) {

			return false;
		}
	}
}
