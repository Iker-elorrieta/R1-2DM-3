package modelo;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

import conexion.Conexion;

public class Historico {
	private double porcentaje;
	private String nombre;
	private int nivel;
	private int tiempoPrevisto;
	private int tiempototal;
	private Date fecha;

	public Historico(double porcentaje, String nombre, int nivel, int tiempoPrevisto, int tiempototal, Date fecha) {
		
		this.porcentaje = porcentaje;
		this.nombre = nombre;
		this.nivel = nivel;
		this.tiempoPrevisto = tiempoPrevisto;
		this.tiempototal = tiempototal;
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Historico [porcentaje=" + porcentaje + ", nombre=" + nombre + ", nivel=" + nivel + ", tiempoPrevisto="
				+ tiempoPrevisto + ", tiempototal=" + tiempototal + ", fecha=" + fecha + "]";
	}

	public Historico(Double porcentaje, Date fecha) {
		this.porcentaje = porcentaje;
		this.fecha = fecha;
		// TODO Auto-generated constructor stub
	}

	public double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getTiempoPrevisto() {
		return tiempoPrevisto;
	}

	public void setTiempoPrevisto(int tiempoPrevisto) {
		this.tiempoPrevisto = tiempoPrevisto;
	}

	public int getTiempototal() {
		return tiempototal;
	}

	public void setTiempototal(int tiempototal) {
		this.tiempototal = tiempototal;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void anadirHistorico(Cliente cliente, Workout workoutElegido) {
		Firestore co = null;

		try {
			co = Conexion.conectar();
			DocumentReference workouts = co.collection("Workouts").document(workoutElegido.getId());
			DocumentReference usuarios = co.collection("Usuarios").document(cliente.getId());
			CollectionReference historico = usuarios.collection("Historico");
			Map<String, Object> usuarioNuevo = new HashMap<>();

			usuarioNuevo.put("id_work", workouts);
			usuarioNuevo.put("%ejerCompletados", porcentaje);
			usuarioNuevo.put("fecha_workout", fecha);
			usuarioNuevo.put("tiempo_total", tiempototal);
			usuarioNuevo.put("tiempo_previsto", tiempoPrevisto);

			historico.add(usuarioNuevo);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
