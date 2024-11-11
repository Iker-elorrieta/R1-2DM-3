package modelo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import conexion.Conexion;

public class Historico implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double porcentaje;
	private String nombre;
	private int nivel;
	private int tiempoPrevisto;
	private int tiempoTotal;
	private Date fecha;
	private String id;
	private final String coleccionClientes = "Usuarios", coleccionWorkouts = "Workouts",
			coleccionHistorico = "Historico", campoID = "id_work", campoPorcentaje = "%ejerCompletados",
			campoFecha = "fecha_workout", campoTiempoTot = "tiempo_total", campoTiempoPrev = "tiempo_previsto",
			campoNombre = "nom_workout", campoNivel = "nivel_workout";
	private final String HistoricoFileRoute = "backups/historico.xml";

	public Historico(double porcentaje, String nombre, int nivel, int tiempoPrevisto, int tiempoTotal, Date fecha,
			String id) {

		this.porcentaje = porcentaje;
		this.nombre = nombre;
		this.nivel = nivel;
		this.tiempoPrevisto = tiempoPrevisto;
		this.tiempoTotal = tiempoTotal;
		this.fecha = fecha;
		this.id = id;
	}

	@Override
	public String toString() {
		return "Historico [porcentaje=" + porcentaje + ", nombre=" + nombre + ", nivel=" + nivel + ", tiempoPrevisto="
				+ tiempoPrevisto + ", tiempototal=" + tiempoTotal + ", fecha=" + fecha + "]";
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return tiempoTotal;
	}

	public void setTiempototal(int tiempototal) {
		this.tiempoTotal = tiempototal;
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
			usuarioNuevo.put(campoTiempoTot, tiempoTotal);
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

	public void actualizarHistorico(Cliente cliente) {
		// TODO Auto-generated method stub
		Firestore co = null;

		try {
			co = Conexion.conectar();
			DocumentReference usuarios = co.collection(coleccionClientes).document(cliente.getId())
					.collection(coleccionHistorico).document(id);
			ApiFuture<DocumentSnapshot> query = usuarios.get();
			DocumentSnapshot document = query.get();
			Map<String, Object> historicoActualizado = document.getData();

			historicoActualizado.put(campoTiempoPrev, tiempoPrevisto);
			historicoActualizado.put(campoTiempoTot, tiempoTotal);
			historicoActualizado.put(campoFecha, fecha);
			historicoActualizado.put(campoPorcentaje, porcentaje);

			usuarios.update(historicoActualizado);
			System.out.println("Actualizado");

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

	public ArrayList<Historico> obtenerHistorico(QueryDocumentSnapshot usuario)
			throws InterruptedException, ExecutionException {
		ArrayList<Historico> historial = new ArrayList<Historico>();

		List<QueryDocumentSnapshot> historicos = usuario.getReference().collection(coleccionHistorico).get().get()
				.getDocuments();

		for (QueryDocumentSnapshot historico : historicos) {
			Historico nuevoHistorico = new Historico(historico.getDouble(campoPorcentaje),
					historico.getDate(campoFecha));
			nuevoHistorico.setId(historico.getId());
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

	public ArrayList<Historico> obtenerHistoricoBackups(Cliente cliente) {
		ArrayList<Historico> historico = new ArrayList<Historico>();

		File archivo = new File(HistoricoFileRoute);
		DocumentBuilderFactory dbFactoria = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbBuilder = null;
		try {
			dbBuilder = dbFactoria.newDocumentBuilder();
			Document doc = dbBuilder.parse(archivo);
			doc.getDocumentElement().normalize();
			NodeList usuarios = doc.getElementsByTagName("Usuario");
			for (int i = 0; i < usuarios.getLength(); i++) {
				if (usuarios.item(i).getAttributes().item(0).getTextContent().equals(cliente.getId())) {
					NodeList historicos = usuarios.item(i).getChildNodes();
					for (int o = 0; o < historicos.getLength(); o++) {
						SimpleDateFormat formato = new SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
						NodeList atributos = historicos.item(o).getChildNodes();
						historico.add(new Historico(Double.parseDouble(atributos.item(0).getTextContent()),
								atributos.item(2).getTextContent(),
								Integer.parseInt(atributos.item(3).getTextContent()),
								Integer.parseInt(atributos.item(4).getTextContent()),
								Integer.parseInt(atributos.item(5).getTextContent()),
								formato.parse(atributos.item(1).getTextContent()),
								historicos.item(o).getAttributes().item(0).getTextContent()));
					}
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return historico;

	}

}
