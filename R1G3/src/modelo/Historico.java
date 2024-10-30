package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import conexion.Conexion;

public class Historico {
	private double porcentaje;
	private String nombre;
	private int nivel;
	private int tiempoPrevisto;
	private int tiempototal;
	private Date fecha;
	private final String coleccionClientes = "Usuarios", coleccionWorkouts = "Workouts",
			coleccionHistorico = "Historico", campoID = "id_work", campoPorcentaje = "%ejerCompletados",
			campoFecha = "fecha_workout", campoTiempoTot = "tiempo_total", campoTiempoPrev = "tiempo_previsto",
			campoNombre = "nom_workout", campoNivel = "nivel_workout";

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

	public Historico() {
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
			DocumentReference workouts = co.collection(coleccionWorkouts).document(workoutElegido.getId());
			DocumentReference usuarios = co.collection(coleccionClientes).document(cliente.getId());
			CollectionReference historico = usuarios.collection(coleccionHistorico);
			Map<String, Object> usuarioNuevo = new HashMap<>();

			usuarioNuevo.put(campoID, workouts);
			usuarioNuevo.put(campoPorcentaje, porcentaje);
			usuarioNuevo.put(campoFecha, fecha);
			usuarioNuevo.put(campoTiempoTot, tiempototal);
			usuarioNuevo.put(campoTiempoPrev, tiempoPrevisto);

			historico.add(usuarioNuevo);

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
	}

	public void actualizarHistorico(Cliente usuarioIniciado, Workout workoutElegido) {
		// TODO Auto-generated method stub

	}

	public ArrayList<Historico> obtenerHistorico(QueryDocumentSnapshot usuario)
			throws InterruptedException, ExecutionException {
		ArrayList<Historico> historial = new ArrayList<Historico>();

		List<QueryDocumentSnapshot> historicos = usuario.getReference().collection(coleccionHistorico).get().get()
				.getDocuments();

		for (QueryDocumentSnapshot historico : historicos) {
			Historico nuevoHistorico = new Historico(historico.getDouble(campoPorcentaje),
					historico.getDate(campoFecha));

			double tiempoPrev = historico.getDouble(campoTiempoPrev);
			double tiempoTot = historico.getDouble(campoTiempoTot);
			DocumentReference dirRef = (DocumentReference) historico.getData().get(campoID);
			if (dirRef != null) {
				nuevoHistorico.setNombre(dirRef.get().get().getString(campoNombre));
				double nivelWork = dirRef.get().get().getDouble(campoNivel);
				nuevoHistorico.setNivel((int) nivelWork);
			}

			nuevoHistorico.setTiempoPrevisto((int) tiempoPrev);
			nuevoHistorico.setTiempototal((int) tiempoTot);
			historial.add(nuevoHistorico);
		}
		return historial;
	}
}
