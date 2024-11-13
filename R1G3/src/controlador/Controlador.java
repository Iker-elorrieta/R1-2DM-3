package controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Ejercicio;
import modelo.HiloCronometro;
import modelo.HiloEsperar;
import modelo.HiloRegresivo;
import modelo.Historico;
import modelo.Serie;
import modelo.Workout;
import vista.PanelEjercicio;
import vista.PanelHistorial;
import vista.PanelVistaEjercicio;

public class Controlador {
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	private vista.PanelLogin panelLogin;
	private vista.PanelRegistro panelRegistro;
	private Cliente usuarioIniciado = new Cliente();
	private vista.PanelPerfil panelPerfil;
	private vista.PanelWorkouts panelWorkouts;
	private vista.PanelEjercicio panelEjercicio;
	private vista.PanelVistaEjercicio panelVista;
	private vista.PanelHistorial panelHistorial;
	private ArrayList<Workout> workouts = new ArrayList<Workout>();
	private Workout workoutElegido;
	private HiloCronometro hiloWorkout;
	private HiloCronometro hiloEjercicio;
	private HiloEsperar hiloEsperar;
	private HiloRegresivo hiloSerie, hiloDescanso;
	private int contEjercicios, cronometroParado, contSeries = 0;
	private ArrayList<Component> labelsWorkout = new ArrayList<Component>(), labelsSeries = new ArrayList<Component>(),
			labelsFotos = new ArrayList<Component>();
	private ArrayList<Integer> tiemposEjercicio = new ArrayList<Integer>();
	private String[] mensajes = { "¡No te rindas! Cada esfuerzo cuenta.", "¡Eres más fuerte de lo que crees!",
			"Hoy es un buen día para superarte.", "¡No importa lo lento que vayas, siempre y cuando no te detengas!",
			"Recuerda por qué empezaste. ¡Sigue adelante!", "Cada repetición te acerca a tu meta.",
			"El dolor es temporal, el orgullo es para siempre.", "Tu único límite es tu mente.",
			"No estás compitiendo con nadie, excepto contigo mismo.", "¡La constancia es la clave del éxito!",
			"Si puedes soñarlo, puedes lograrlo.", "Esfuerzo hoy, resultados mañana.",
			"¡El éxito es la suma de pequeños esfuerzos repetidos!", "La única mala sesión es la que no haces.",
			"Confía en el proceso, los resultados llegarán.", "¡Transforma la duda en determinación!",
			"¡Eres más fuerte con cada entrenamiento!", "El cambio no es fácil, pero vale la pena.",
			"¡Paso a paso hacia tus metas!", "Tú tienes el poder de cambiar tu vida." };

	private int workoutIniciado = 0;

	public Controlador(vista.PanelLogin panelLogin) {
		this.panelLogin = panelLogin;
		this.panelLogin.setVisible(true);
		if (!tieneConexion()) {
			this.panelLogin.getBtnRegistrarse().setVisible(false);
		}
		this.inicializarControlador();
	}

	public Controlador() {
		// TODO Auto-generated constructor stub
	}

	private void inicializarControlador() {
		// TODO Auto-generated method stub
		this.panelLogin.getBtnIniciar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tieneConexion()) {
					usuarioIniciado = usuarioIniciado.cargarCliente(panelLogin.getTxtFNombre().getText(),
							panelLogin.getTxtFContrasena().getText());
					crearBackups();

				} else {

					usuarioIniciado = GenerarBackups.leerUsuariosDesdeArchivo(panelLogin.getTxtFNombre().getText(),
							panelLogin.getTxtFContrasena().getText());

				}

				if (usuarioIniciado == null || usuarioIniciado == new Cliente()) {
					JOptionPane.showMessageDialog(null, "El usuario o contraseña no coinciden");
					usuarioIniciado = new Cliente();
				} else {

					panelWorkouts = new vista.PanelWorkouts();
					if (tieneConexion()) {
						workouts = cargarWorkouts();
					} else {
						ArrayList<Historico> historial = new Historico().obtenerHistoricoBackups(usuarioIniciado);
						for (Historico historico : historial) {
							historico.toString();
						}
						usuarioIniciado.setWorkouts(new Historico().obtenerHistoricoBackups(usuarioIniciado));
						workouts = GenerarBackups.leerWorkoutsDesdeArchivo();
						panelWorkouts.getLblPerfil().setVisible(false);
					}
					panelWorkouts.getLblNivel().setText("Nivel: " + usuarioIniciado.getNivel());

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

	protected void crearBackups() {
		// TODO Auto-generated method stub
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", "CrearBackups.jar");
		ProcessBuilder pb2 = new ProcessBuilder("java", "-jar", "CrearHistorico.jar");
		try {
			pb.start();
			pb2.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		}
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
				lblWorkout.setBounds(42, 170 + (40 * i), 349, 22);
				panelWorkouts.getPanelWorkout().add(lblWorkout);

				JLabel lblYoutube = new JLabel("");
				lblYoutube.setToolTipText(String.valueOf(i));
				lblYoutube.setBounds(423, 170 + (40 * i), 46, 39);
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

				JButton btnVerEjercicios = new JButton("Ver Ejercicios");
				btnVerEjercicios.setToolTipText(String.valueOf(i));
				btnVerEjercicios.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						panelVista = new PanelVistaEjercicio();
						DefaultListModel<String> modelo = new DefaultListModel<String>();
						panelVista.setVisible(true);
						panelVista.getListaEjercicios().setModel(modelo);
						for (Ejercicio ejercicio : workouts.get(Integer.parseInt(btnVerEjercicios.getToolTipText()))
								.getEjercicios()) {
							modelo.addElement(ejercicio.getNombre());
						}
						inicializarVistaEjercicios();
					}
				});
				btnVerEjercicios.setFont(new Font("Nirmala UI", Font.PLAIN, 15));
				btnVerEjercicios.setBounds(508, 165 + (40 * i), 130, 39);
				panelWorkouts.getPanelWorkout().add(btnVerEjercicios);

				labelsWorkout.add(lblYoutube);
				labelsWorkout.add(lblWorkout);
				labelsWorkout.add(btnVerEjercicios);
			}
		}
	}

	protected void inicializarVistaEjercicios() {
		// TODO Auto-generated method stub
		panelVista.getBtnCerrar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelVista.dispose();
			}
		});
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
				lblWorkout.setBounds(42, 170 + (40 * o), 349, 22);
				panelWorkouts.getPanelWorkout().add(lblWorkout);

				JLabel lblYoutube = new JLabel("");
				lblYoutube.setToolTipText(String.valueOf(i));
				lblYoutube.setBounds(423, 170 + (40 * o), 46, 39);
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

				JButton btnVerEjercicios = new JButton("Ver Ejercicios");
				btnVerEjercicios.setToolTipText(String.valueOf(i));
				btnVerEjercicios.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						panelVista = new PanelVistaEjercicio();
						DefaultListModel<String> modelo = new DefaultListModel<String>();
						panelVista.setVisible(true);
						panelVista.getListaEjercicios().setModel(modelo);
						for (Ejercicio ejercicio : workouts.get(Integer.parseInt(btnVerEjercicios.getToolTipText()))
								.getEjercicios()) {
							modelo.addElement(ejercicio.getNombre());
						}
						inicializarVistaEjercicios();
					}
				});
				btnVerEjercicios.setFont(new Font("Nirmala UI", Font.PLAIN, 15));
				btnVerEjercicios.setBounds(508, 165 + (40 * o), 130, 39);
				panelWorkouts.getPanelWorkout().add(btnVerEjercicios);

				labelsWorkout.add(btnVerEjercicios);
				labelsWorkout.add(lblYoutube);
				labelsWorkout.add(lblWorkout);
				o++;
			}
		}
	}

	private ArrayList<Workout> cargarWorkouts() {

		return new Workout().mObtenerWorkouts();
	}

	private void inicalizarRegistro() {
		this.panelRegistro.getBtnRegistrarse().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JTextField[] componentes = new JTextField[] { panelRegistro.getTxtFNombre(),
						panelRegistro.getTxtFApellidos(), panelRegistro.getTxtFEmail(),
						panelRegistro.getTxtFContrasena() };

				String mensaje = comprobarError(componentes);
				if (panelRegistro.getDateChooser().getDate() == null) {
					mensaje = "Seleccione una fecha";
				}
				if (mensaje.isEmpty()) {

					Cliente nuevoCliente = new Cliente(componentes[0].getText(), componentes[1].getText(),
							componentes[2].getText(), componentes[3].getText(),
							panelRegistro.getDateChooser().getDate(), false, 0);
					Boolean registrado = nuevoCliente.anadirCliente();
					if (registrado) {
						JOptionPane.showMessageDialog(null, "Usuario Registrado correctamente");
						panelRegistro.dispose();
						panelLogin.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Este email ya esta vinculado a otra cuenta");
					}
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
						panelPerfil.getTxtFApellidos(), panelPerfil.getTxtFEmail(), panelPerfil.getTxtFContrasena() };

				String mensaje = comprobarError(componentes);
				if (panelPerfil.getDateChooser().getDate() == null) {
					mensaje = "Seleccione una fecha";
				}
				if (mensaje.isEmpty()) {
					usuarioIniciado.setNombre(componentes[0].getText());
					usuarioIniciado.setApellidos(componentes[1].getText());
					usuarioIniciado.setEmail(componentes[2].getText());
					usuarioIniciado.setFechaNacimiento(panelPerfil.getDateChooser().getDate());
					usuarioIniciado.setContrasena(componentes[3].getText());

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
				panelPerfil.getDateChooser().setDate(usuarioIniciado.getFechaNacimiento());

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
		panelWorkouts.getLblPerfil().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				panelPerfil = new vista.PanelPerfil();
				panelPerfil.setVisible(true);
				panelPerfil.getTxtFNombre().setText(usuarioIniciado.getNombre());
				panelPerfil.getTxtFApellidos().setText(usuarioIniciado.getApellidos());
				panelPerfil.getTxtFContrasena().setText(usuarioIniciado.getContrasena());
				panelPerfil.getTxtFEmail().setText(usuarioIniciado.getEmail());
				panelPerfil.getDateChooser().setDate(usuarioIniciado.getFechaNacimiento());

				inicializarPerfil();
			}
		});
		panelWorkouts.getBtnHistorial().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelWorkouts.setVisible(false);
				panelHistorial = new PanelHistorial();
				panelHistorial.setVisible(true);
				try {
					rellenarTabla();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				inicializarHistorial();
			}
		});

	}

	protected void inicializarHistorial() {
		// TODO Auto-generated method stub
		panelHistorial.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				panelWorkouts.setVisible(true);

			}

		});
	}

	protected void rellenarTabla() throws ParseException {
		// TODO Auto-generated method stub
		String[] columnas = { "Nombre Workout", "Nivel Workout", "% Ejecicios Completados", "Fecha", "Tiempo Estimado",
				"Tiempo Total" };
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
		ArrayList<Historico> historicoOrdenado = usuarioIniciado.getWorkouts();
		ordenarArrayPorFecha(historicoOrdenado);
		for (Historico historico : historicoOrdenado) {

			Object[] fila = { historico.getWorkoutReferido().getNombre(), historico.getWorkoutReferido().getNivel(),
					historico.getPorcentaje(), formato.format(historico.getFecha()), historico.getTiempoPrevisto(),
					historico.getTiempototal() };
			modelo.addRow(fila);
		}

		panelHistorial.getTablaHistorico().setModel(modelo);
	}

	private void ordenarArrayPorFecha(ArrayList<Historico> historicoOrdenado) {
		// TODO Auto-generated method stub
		historicoOrdenado.sort(new Comparator<Historico>() {
			public int compare(Historico o1, Historico o2) {
				return o2.getFecha().compareTo(o1.getFecha());
			}
		});
	}

	private String comprobarError(JTextField[] componentes) {
		// TODO Auto-generated method stub
		formato.setLenient(false);
		for (JTextField componente : componentes) {
			if (componente.getText().isEmpty()) {
				return "Todos los campos deben estar rellenados";
			}
		}
		if (!componentes[2].getText().contains("@")) {
			return "El email debe tener un formato correcto";
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
					workoutIniciado = 1;
					if (labelsSeries.isEmpty()) {
						for (int i = 0; i < ejercicioActivo.getSeries().size(); i++) {

							JLabel lblSerie = new JLabel("Serie " + (i + 1) + ":");
							lblSerie.setFont(new Font("Nirmala UI", Font.PLAIN, 14));
							lblSerie.setHorizontalAlignment(SwingConstants.CENTER);
							lblSerie.setBounds(257, 125 + (74 * i), 151, 39);

							panelEjercicio.getPanelEjercicios().add(lblSerie);
							labelsSeries.add(lblSerie);

							JLabel lblFoto = new JLabel("");
							lblFoto.setBounds(213, 125 + (74 * i), 76, 39);
							lblFoto.setIcon(new ImageIcon("img/" + ejercicioActivo.getSeries().get(i).getFotoSeries()));
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
					hiloSerie = new HiloRegresivo((JLabel) labelsSeries.get(contSeries),
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
				if (workoutIniciado == 1) {
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
						JOptionPane.showInternalMessageDialog(null,
								"Tiempo total: " + tiempoTotal + "\nPorcentaje completado: " + porcentaje + "\n"
										+ mensajes[new Random().nextInt(mensajes.length)],
								"Resumen", 1);
						if (tieneConexion()) {
							for (Historico historico : usuarioIniciado.getWorkouts()) {
								if (historico.getWorkoutReferido().getNombre().equals(workoutElegido.getNombre())) {

									usuarioIniciado.getWorkouts().get(cont).setPorcentaje(porcentaje);
									usuarioIniciado.getWorkouts().get(cont).setTiempoPrevisto(tiempoEstimado);
									usuarioIniciado.getWorkouts().get(cont).setTiempototal(tiempoTotal);
									usuarioIniciado.getWorkouts().get(cont).setFecha(new Date());
									historico = usuarioIniciado.getWorkouts().get(cont);
									historico.actualizarHistorico(usuarioIniciado);
									encontrado = true;
								}
								cont++;
							}
							if (porcentaje == 100 && workoutElegido.getNivel() == usuarioIniciado.getNivel()) {
								usuarioIniciado.setNivel(usuarioIniciado.getNivel() + 1);
								usuarioIniciado.actualizarCliente();
							}
							if (!encontrado) {
								Historico historico = new Historico(porcentaje, workoutElegido, tiempoEstimado,
										tiempoTotal, new Date(), null);
								historico.anadirHistorico(usuarioIniciado, workoutElegido);
								usuarioIniciado.getWorkouts().add(historico);

							}
						}

					}
					cronometroParado = 0;
					contEjercicios = 0;
					contSeries = 0;
					workoutIniciado = 0;
					eliminarLabels(labelsSeries, panelEjercicio.getPanelEjercicios());
					eliminarLabels(labelsFotos, panelEjercicio.getPanelEjercicios());
				}
			}
		});

	}

	public void eliminarLabels(ArrayList<Component> labels, JPanel panel) {
		for (Component label : labels) {
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
