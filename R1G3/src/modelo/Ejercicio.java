package modelo;

import java.util.ArrayList;

public class Ejercicio {
	
	private int cronometro;
	private String descripcion;
	private int descanso;
	private String nombre;
	
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
	private ArrayList<Serie> series;
}
