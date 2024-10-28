package modelo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class Workout implements Serializable {

	private String campovideo = "video_workout";

	private String campoNivel = "nivel_workout";
	private String collectionName = "Workouts";
	private static final long serialVersionUID = 1L;
	private int nivel;
	private String nombre;
	private int numEjer;
	private String video;
	private ArrayList<Ejercicio> ejercicios;
	private String id;

	public Workout(int nivel, String nombre, int numEjer, String video, ArrayList<Ejercicio> ejercicios, String id) {
		super();
		this.nivel = nivel;
		this.nombre = nombre;
		this.numEjer = numEjer;
		this.video = video;
		this.ejercicios = ejercicios;
		this.id = id;
	}

	public Workout() {
		// TODO Auto-generated constructor stub
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

	public int getNumEjer() {
		return numEjer;
	}

	public void setNumEjer(int numEjer) {
		this.numEjer = numEjer;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public ArrayList<Ejercicio> getEjercicios() {
		return ejercicios;
	}

	public void setEjercicios(ArrayList<Ejercicio> ejercicios) {
		this.ejercicios = ejercicios;
	}

	public ArrayList<Workout> mObtenerWorkouts() {
		Firestore co = null;
		ArrayList<Workout> listaWorkOuts = new ArrayList<Workout>();
		try {
			co = Conexion.conectar();

			ApiFuture<QuerySnapshot> query = co.collection(collectionName).get();
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> workouts = querySnapshot.getDocuments();

			for (QueryDocumentSnapshot workout : workouts) {

				double nivel = workout.getDouble(campoNivel);
				Workout w = new Workout();

				w.setNombre(workout.getId());
				w.setNivel((int) nivel);
				w.setVideo(workout.getString(campovideo));
				w.setEjercicios(new Ejercicio().mObtenerEjercicios(collectionName, w.getNombre()));

				listaWorkOuts.add(w);
			}
			co.close();

		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Error: Clase Contacto, metodo mObtenerContactos");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaWorkOuts;
	}
}
