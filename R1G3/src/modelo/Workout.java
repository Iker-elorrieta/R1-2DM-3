package modelo;

import java.util.ArrayList;


public class Workout {

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

	
}
