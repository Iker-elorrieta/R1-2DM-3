package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

import conexion.Conexion;

public class Cliente {

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
	private ArrayList<Workout> workouts;

	public ArrayList<Workout> getWorkouts() {
		return workouts;
	}

	public void setWorkouts(ArrayList<Workout> workouts) {
		this.workouts = workouts;
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

	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
				+ ", contrasena=" + contrasena + ", fechaNacimiento=" + fechaNacimiento + ", esEntrenador="
				+ esEntrenador + ", nivel=" + nivel + "]";
	}

	public Cliente(String nombre, String apellidos, String email, String contrasena, Date fechaNacimiento,
			Boolean esEntrenador, int nivel, String id) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
		this.esEntrenador = esEntrenador;
		this.nivel = nivel;
		this.id = id;
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

	public void anadirContacto() {
		Firestore co = null;

		try {
			co = Conexion.conectar();

			CollectionReference usuarios = co.collection(collectionName);
			Map<String, Object> usuarioNuevo = new HashMap<>();

			usuarioNuevo.put(campoNombre, nombre);
			usuarioNuevo.put(campoApellido, apellidos);
			usuarioNuevo.put(campoEmail, email);
			usuarioNuevo.put(campoFecha, fechaNacimiento);
			usuarioNuevo.put(campoContrasena, contrasena);
			usuarioNuevo.put(campoNivel, String.valueOf(nivel));
			usuarioNuevo.put(campoEntrenador, esEntrenador);
			usuarios.add(usuarioNuevo);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void actualizarContacto() {
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
	}
}
