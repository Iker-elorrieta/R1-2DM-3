
package controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import modelo.Cliente;
import modelo.Historico;
import modelo.Workout;

public class GenerarBackups {

	private static final String UsuariosFileRoute = "backups/clientes.dat";
	private static final String WorkoutsFileRoute = "backups/workouts.dat";
	private static final String HistoricoFileRoute = "backups/historico.xml";
	private static final String nodoRaiz = "Usuarios";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	static void escribirWorkoutsEnArchivo(ArrayList<Workout> workouts) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(WorkoutsFileRoute))) {

			for (Workout workout : workouts) {
				output.writeObject(workout);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static ArrayList<Workout> leerWorkoutsDesdeArchivo() {
		ArrayList<Workout> workouts = new ArrayList<Workout>();
		try {
			FileInputStream fic = new FileInputStream(WorkoutsFileRoute);
			ObjectInputStream ois = new ObjectInputStream(fic);
			while (fic.getChannel().position() < fic.getChannel().size()) {
				workouts.add((Workout) ois.readObject());

			}
			ois.close();

		} catch (

		FileNotFoundException e) {
			System.out.println("Archivo no encontrado, se creará uno nuevo.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return workouts;
	}

	public static Cliente leerUsuariosDesdeArchivo(String email, String contra) {

		try {
			FileInputStream fic = new FileInputStream(UsuariosFileRoute);
			ObjectInputStream ois = new ObjectInputStream(fic);
			while (fic.getChannel().position() < fic.getChannel().size()) {
				Cliente usuario = (Cliente) ois.readObject();

				if (usuario.getEmail().equals(email) && usuario.getContrasena().equals(contra)) {
					ois.close();
					return usuario;
				}
			}
			ois.close();

		} catch (

		FileNotFoundException e) {
			System.out.println("Archivo no encontrado, se creará uno nuevo.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Historico> obtenerHistoricoBackups() {
		ArrayList<Historico> historico = new ArrayList<Historico>();

		File archivo = new File(HistoricoFileRoute);
		DocumentBuilderFactory dbFactoria = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbBuilder = null;
		try {
			dbBuilder = dbFactoria.newDocumentBuilder();
			Document doc = dbBuilder.parse(archivo);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(nodoRaiz);
			for (int i = 0; i < nList.getLength(); i++) {
				System.out.println(nList.item(i).getNodeName() + " :" + (i + 1));
				NodeList nodo = nList.item(i).getChildNodes();
				for (int o = 0; o < nodo.getLength(); o++) {
					System.out.println(nodo.item(o).getNodeName() + ": " + nodo.item(o).getTextContent());
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
		}
		return historico;

	}
}
