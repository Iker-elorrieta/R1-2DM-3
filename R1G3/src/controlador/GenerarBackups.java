package controlador;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import modelo.Cliente;
import modelo.Workout;

public class GenerarBackups {

	
	public static void main(String[] args) {
		escribirUsuariosEnArchivo(new Cliente().mObtenerTodosLosUsuarios());
		escribirWorkoutsEnArchivo(new Workout().mObtenerWorkouts());
	}

	private static final String UsuariosFileRoute = "backups/clientes.dat";
	private static final String WorkoutsFileRoute = "backups/workouts.dat";
	
	static void escribirUsuariosEnArchivo(ArrayList<Cliente> clientes) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(UsuariosFileRoute))){
			output.writeObject(clientes);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	static void escribirWorkoutsEnArchivo(ArrayList<Workout> workouts) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(WorkoutsFileRoute))){
			output.writeObject(workouts);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	/*
	private static void leerWorkoutsDesdeArchivo() throws FileNotFoundException, IOException, ClassNotFoundException {
	ArrayList<Workout> works = new ArrayList<>();
	ArrayList<Ejercicio> ejers = new ArrayList<>();
	ArrayList<Serie> series = new ArrayList<>();
	
	try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(WorkoutsFileRoute))){
		works = (ArrayList<Workout>) input.readObject();
		for(Workout work : works) {
			
			System.out.println(work.getNombre());
			ejers = work.getEjercicios();
			
			for(Ejercicio ejer : ejers) {
				
				System.out.println(" " + ejer.getNombre());
				series = ejer.getSeries();
				
				for(Serie serie : series) {
					
					System.out.println(" "+ serie.getNomSeries());
				}
			}
			System.out.println("\n");
		}
	} catch (FileNotFoundException e) {
		System.out.println("No hay backup. Creando...");
	} catch (IOException | ClassNotFoundException e) {
		e.printStackTrace();
	}
}
	
	private static void leerUsuariosDesdeArchivo() {
		ArrayList<Cliente> clientes = new ArrayList<>();

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(UsuariosFileRoute))) {
			clientes = (ArrayList<Cliente>) ois.readObject();
			for (Cliente nuevoCliente : clientes) {
				System.out.println(nuevoCliente.getEmail());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado, se creará uno nuevo.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}*/
	
}

