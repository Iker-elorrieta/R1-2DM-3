package controlador;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import modelo.Cliente;
import modelo.Historico;

public class CrearHistorico {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		escribirUsuariosEnArchivo(new Cliente().mObtenerTodosLosUsuarios());

	}

	private static final String rutaHistorico = "Backups/historico.xml";
	private final static String coleccionClientes = "Usuario";
	private final static String coleccionHistorico = "Historico";
	private final static String campoID = "id";
	private final static String campoPorcentaje = "ejerCompletados";
	private final static String campoFecha = "fecha_workout";
	private final static String campoTiempoTot = "tiempo_total";
	private final static String campoTiempoPrev = "tiempo_previsto";
	private final static String campoNombre = "nom_workout";
	private final static String campoNivel = "nivel_workout";

	public static void escribirUsuariosEnArchivo(ArrayList<Cliente> clientes) {
		try {
			File archivo = new File(rutaHistorico);
			eliminarContenido();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document documento = dBuilder.parse(archivo);
			documento.getDocumentElement().normalize();

			for (Cliente cliente : clientes) {
				Element usuario = documento.createElement(coleccionClientes);
				usuario.setAttribute(campoID, cliente.getId());
				for (Historico historico : cliente.getWorkouts()) {
					Element historicoUsu = documento.createElement(coleccionHistorico);
					historicoUsu.setAttribute(campoID, historico.getId());

					Element porcentaje = documento.createElement(campoPorcentaje);
					porcentaje.appendChild(documento.createTextNode(String.valueOf(historico.getPorcentaje())));
					historicoUsu.appendChild(porcentaje);

					Element pais = documento.createElement(campoFecha);
					pais.appendChild(documento.createTextNode(historico.getFecha().toString()));
					historicoUsu.appendChild(pais);

					Element compania = documento.createElement(campoNombre);
					compania.appendChild(documento.createTextNode(historico.getNombre()));
					historicoUsu.appendChild(compania);

					Element nivel = documento.createElement(campoNivel);
					nivel.appendChild(documento.createTextNode(String.valueOf(historico.getNivel())));
					historicoUsu.appendChild(nivel);

					Element precio = documento.createElement(campoTiempoTot);
					precio.appendChild(documento.createTextNode(String.valueOf(historico.getTiempototal())));
					historicoUsu.appendChild(precio);

					Element anyo = documento.createElement(campoTiempoPrev);
					anyo.appendChild(documento.createTextNode(String.valueOf(historico.getTiempoPrevisto())));
					historicoUsu.appendChild(anyo);

					usuario.appendChild(historicoUsu);
				}

				Element nodoprincipal = documento.getDocumentElement();
				nodoprincipal.appendChild(usuario);

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(documento);
				StreamResult consoleResult = new StreamResult(new File(rutaHistorico));
				transformer.transform(source, consoleResult);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void eliminarContenido() {
		try {
			File archivoXML = new File(rutaHistorico);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(archivoXML);
			doc.getDocumentElement().normalize();
			NodeList listaUsuarios = doc.getElementsByTagName("Usuario");
			for (int i = listaUsuarios.getLength() - 1; i >= 0; i--) {
				Node usuario = listaUsuarios.item(i);
				usuario.getParentNode().removeChild(usuario);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(rutaHistorico));
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}