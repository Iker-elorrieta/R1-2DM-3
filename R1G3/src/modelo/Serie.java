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

public class Serie implements Serializable {
	private static final long serialVersionUID = 1L;
	private int cuentaatras;
	private String fotoSeries;
	private String nomSeries;
	private int numRepeticiones;
	private String id;
	private String campofoto = "foto_series", camporepeticiones = "num_repeticiones", collectionName = "Series";

	public Serie(int cuentaatras, String fotoSeries, String nomSeries, int numRepeticiones, String id) {
		super();
		this.cuentaatras = cuentaatras;
		this.fotoSeries = fotoSeries;
		this.nomSeries = nomSeries;
		this.numRepeticiones = numRepeticiones;
		this.id = id;
	}

	public Serie() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCuentaatras() {
		return cuentaatras;
	}

	public void setCuentaatras(int cuentaatras) {
		this.cuentaatras = cuentaatras;
	}

	public String getFotoSeries() {
		return fotoSeries;
	}

	public void setFotoSeries(String fotoSeries) {
		this.fotoSeries = fotoSeries;
	}

	public String getNomSeries() {
		return nomSeries;
	}

	public void setNomSeries(String nomSeries) {
		this.nomSeries = nomSeries;
	}

	public int getNumRepeticiones() {
		return numRepeticiones;
	}

	public void setNumRepeticiones(int numRepeticiones) {
		this.numRepeticiones = numRepeticiones;
	}

	public ArrayList<Serie> mObtenerSeries(String coleccionWorkout, String colectionEjercicio, String nombreEjercicio,
			String nombreWorkout) {
		Firestore co = null;
		ArrayList<Serie> listaSeries = new ArrayList<>();
		try {
			co = Conexion.conectar();
			// Como hay un nivel anterior
			DocumentReference workoutDoc = co.collection(coleccionWorkout).document(nombreWorkout);
			DocumentReference ejercicioDoc = workoutDoc.collection(colectionEjercicio).document(nombreEjercicio);
			ApiFuture<QuerySnapshot> seriesFuture = ejercicioDoc.collection(collectionName).get();
			QuerySnapshot seriesSnapshot = seriesFuture.get();
			List<QueryDocumentSnapshot> ejercicios = seriesSnapshot.getDocuments();

			for (QueryDocumentSnapshot serieDoc : ejercicios) {

				double repeticiones = serieDoc.getDouble(camporepeticiones);

				Serie serie = new Serie();
				serie.setNomSeries(serieDoc.getId());
				serie.setNumRepeticiones((int) repeticiones);
				serie.setFotoSeries(serieDoc.getString(campofoto));
				listaSeries.add(serie);
			}
			co.close();
		} catch (InterruptedException | ExecutionException | IOException e) {
			System.out.println("Error: Clase Serie, método mObtenerSeries");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaSeries;
	}
}
