package modelo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	private String campoNombre = "nom_usuario", campoApellido = "ap_usuario", campoEmail = "email_usuario",
			campoFecha = "fechaNac_usuario", campoNivel = "nivel_usuario", campoEntrenador = "tipo_entrenador",
			campoContrasena = "cont_usuario", collectionName = "Usuarios";
	private String id;
	private String nombre;
	private String apellidos;
	private String email;
	private String contrasena;
	private Date fechaNacimiento;
	private Boolean esEntrenador;
	private int nivel;
	private ArrayList<Historico> historico;

	public ArrayList<Historico> getWorkouts() {
		return historico;
	}

	public void setWorkouts(ArrayList<Historico> workouts) {
		this.historico = workouts;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Boolean getEsEntrenador() {
		return esEntrenador;
	}

	public void setEsEntrenador(Boolean esEntrenador) {
		this.esEntrenador = esEntrenador;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
				+ ", contrasena=" + contrasena + ", fechaNacimiento=" + fechaNacimiento + ", esEntrenador="
				+ esEntrenador + ", nivel=" + nivel + ", Historial: "+historico.get(0).toString() +historico.get(1).toString()+"]";
	}

	public Cliente(String nombre, String apellidos, String email, String contrasena, Date fechaNacimiento,
			Boolean esEntrenador, int nivel, String id, ArrayList<Historico> historico) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
		this.esEntrenador = esEntrenador;
		this.nivel = nivel;
		this.id = id;
		this.historico = historico;
	}

	public Cliente(String nombre, String apellidos, String email, String contrasena, Date fechaNacimiento,
			Boolean esEntrenador, int nivel, String id) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
		this.esEntrenador = esEntrenador;
		this.nivel = nivel;
		this.id = id;
	}

	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	public Cliente(String nombre, String apellidos, String email, String contrasena, Date fechaNacimiento,
			Boolean esEntrenador, int nivel) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
		this.esEntrenador = esEntrenador;
		this.nivel = nivel;

	}

	public Cliente cargarCliente(String email, String contrasena) {
		Cliente cliente = null;
		Firestore co = null;

		try {
			co = Conexion.conectar();
			ApiFuture<QuerySnapshot> query = co.collection("Usuarios").get();
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> usuarios = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot usuario : usuarios) {
				if (usuario.getString(campoEmail).equals(email)
						&& usuario.getString(campoContrasena).equals(contrasena)) {
					double nivel = usuario.getDouble(campoNivel);
					Historico nuevoHistorico = new Historico();
					cliente = new Cliente(usuario.getString(campoNombre), usuario.getString(campoApellido),
							usuario.getString(campoEmail), usuario.getString(campoContrasena),
							usuario.getDate(campoFecha), usuario.getBoolean(campoEntrenador), (int) nivel,
							usuario.getId(), nuevoHistorico.obtenerHistorico(usuario));

				}
			}

		} catch (IOException | InterruptedException | ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			co.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cliente;
	}

	public Boolean anadirCliente() {
		Firestore co = null;
		if (!comprobarClienteRepetido()) {
			try {
				co = Conexion.conectar();

				CollectionReference usuarios = co.collection(collectionName);
				Map<String, Object> usuarioNuevo = new HashMap<>();

				usuarioNuevo.put(campoNombre, nombre);
				usuarioNuevo.put(campoApellido, apellidos);
				usuarioNuevo.put(campoEmail, email);
				usuarioNuevo.put(campoFecha, fechaNacimiento);
				usuarioNuevo.put(campoContrasena, contrasena);
				usuarioNuevo.put(campoNivel, nivel);
				usuarioNuevo.put(campoEntrenador, esEntrenador);
				usuarios.add(usuarioNuevo);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				co.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else {

			return false;
		}
	}

	private boolean comprobarClienteRepetido() {
		Firestore co = null;

		try {
			co = Conexion.conectar();
			ApiFuture<QuerySnapshot> query = co.collection("Usuarios").get();
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> usuarios = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot usuario : usuarios) {
				if (usuario.getString(campoEmail).equals(email)) {

					return true;
				}
			}

		} catch (IOException | InterruptedException | ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			co.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void actualizarCliente() {
		// TODO Auto-generated method stub
		Firestore co = null;

		try {
			co = Conexion.conectar();

			CollectionReference coleccion = co.collection(collectionName);
			DocumentReference conRef = coleccion.document(id);
			ApiFuture<DocumentSnapshot> query = conRef.get();
			DocumentSnapshot document = query.get();

			Map<String, Object> contactoCambios = document.getData();

			contactoCambios.put(campoNombre, nombre);
			contactoCambios.put(campoApellido, apellidos);
			contactoCambios.put(campoEmail, email);
			contactoCambios.put(campoFecha, fechaNacimiento);
			contactoCambios.put(campoContrasena, contrasena);
			contactoCambios.put(campoNivel, nivel);
			contactoCambios.put(campoEntrenador, esEntrenador);

			conRef.update(contactoCambios);
			System.out.println("Modificado");

		} catch (IOException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			co.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Cliente> mObtenerTodosLosUsuarios() {
		Firestore co = null;

		ArrayList<Cliente> listaUsuarios = new ArrayList<Cliente>();

		try {
			co = Conexion.conectar();

			ApiFuture<QuerySnapshot> query = co.collection(collectionName).get();

			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> usuariosFireBase = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot usuarioFireBase : usuariosFireBase) {
				double nivel = usuarioFireBase.getDouble(campoNivel);
				Cliente cliente = new Cliente(usuarioFireBase.getString(campoNombre),
						usuarioFireBase.getString(campoApellido), usuarioFireBase.getString(campoEmail),
						usuarioFireBase.getString(campoContrasena), usuarioFireBase.getDate(campoFecha),
						usuarioFireBase.getBoolean(campoEntrenador), (int) nivel, usuarioFireBase.getId(),
						new Historico().obtenerHistorico(usuarioFireBase));

				listaUsuarios.add(cliente);
			}
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Error: Clase Contacto, metodo mObtenerContactos");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error hola");
			e.printStackTrace();
		}
		try {
			co.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaUsuarios;
	}
}
