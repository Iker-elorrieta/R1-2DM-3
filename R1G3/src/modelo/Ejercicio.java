package modelo;

import java.util.ArrayList;

public class Ejercicio {

	private int cronometro;
	private String descripcion;
	private int descanso;
	private String nombre;
	private String id;
	private ArrayList<Serie> series;

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

}
