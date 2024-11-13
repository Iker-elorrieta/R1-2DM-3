package modelo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Ejercicio implements Serializable {

	private static final long serialVersionUID = 1L;
	private int cronometro;
	private String descripcion;
	private int descanso;
	private String nombre;
	private String id;
	private ArrayList<Serie> series;
	private final String campoDescrip = "desc_ejer", campocronometro = "cronometro", campodescanso = "descanso",
			collectionName = "Ejercicios", campoNombre = "nom_ejer";

	public Ejercicio(int cronometro, String descripcion, int descanso, String nombre, String id,
			ArrayList<Serie> series) {
		super();
		this.cronometro = cronometro;
		this.descripcion = descripcion;
		this.descanso = descanso;
		this.nombre = nombre;
		this.id = id;
		this.series = series;
	}

	@Override
	public String toString() {
		return "Ejercicio [cronometro=" + cronometro + ", descripcion=" + descripcion + ", descanso=" + descanso
				+ ", nombre=" + nombre + ", id=" + id + ", series=" + series + "]";
	}

	public Ejercicio() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCronometro() {
		return cronometro;
	}

	public void setCronometro(int cronometro) {
		this.cronometro = cronometro;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDescanso() {
		return descanso;
	}

	public void setDescanso(int descanso) {
		this.descanso = descanso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Serie> getSeries() {
		return series;
	}

	public void setSeries(ArrayList<Serie> series) {
		this.series = series;
	}

	public ArrayList<Ejercicio> mObtenerEjercicios(String coleccionRoot, String nombreWorkout) {
		Firestore co = null;
		ArrayList<Ejercicio> listaEjercicios = new ArrayList<>();
		try {
			co = Conexion.conectar();

			DocumentReference workOutDoc = co.collection(coleccionRoot).document(nombreWorkout);
			ApiFuture<QuerySnapshot> ejerciciosFuture = workOutDoc.collection(collectionName).get();
			QuerySnapshot ejerciciosSnapshot = ejerciciosFuture.get();
			List<QueryDocumentSnapshot> ejercicios = ejerciciosSnapshot.getDocuments();
			for (QueryDocumentSnapshot ejercicio : ejercicios) {

				double descanso = ejercicio.getDouble(campodescanso);
				double cronometro = ejercicio.getDouble(campocronometro);

				Ejercicio ejercicioNuevo = new Ejercicio();
				ejercicioNuevo.setNombre(ejercicio.getString(campoNombre));
				ejercicioNuevo.setId(ejercicio.getId());
				ejercicioNuevo.setDescripcion(ejercicio.getString(campoDescrip));
				ejercicioNuevo.setDescanso((int) descanso);
				ejercicioNuevo.setCronometro((int) cronometro);
				ejercicioNuevo.setSeries(new Serie().mObtenerSeries(coleccionRoot, collectionName,
						ejercicioNuevo.getId(), nombreWorkout));

				listaEjercicios.add(ejercicioNuevo);
			}
			co.close();
		} catch (InterruptedException | ExecutionException | IOException e) {
			System.out.println("Error: Clase Ejercicio, método mObtenerEjercicios");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaEjercicios;
	}
}
