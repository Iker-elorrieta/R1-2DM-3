package controlador;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import modelo.Cliente;
import modelo.Workout;

public class CrearBackups {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		escribirUsuariosEnArchivo(new Cliente().mObtenerTodosLosUsuarios());
		escribirWorkoutsEnArchivo(new Workout().mObtenerWorkouts());
	}

	private static final String UsuariosFileRoute = "Backups/clientes.dat";
	private static final String WorkoutsFileRoute = "Backups/workouts.dat";

	public static void escribirUsuariosEnArchivo(ArrayList<Cliente> clientes) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(UsuariosFileRoute))) {
			for (Cliente cliente : clientes) {
				output.writeObject(cliente);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void escribirWorkoutsEnArchivo(ArrayList<Workout> workouts) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(WorkoutsFileRoute))) {

			for (Workout workout : workouts) {
				output.writeObject(workout);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
