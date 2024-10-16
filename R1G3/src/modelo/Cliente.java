package modelo;

import java.util.Date;

public class Cliente {
	
	private String id;
	private String nombre;
	private String apellidos;
	private String email;
	private String contrasena;
	private Date fechaNacimiento;
	private Boolean esEntrenador;
	private int nivel;
	
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Boolean getEsEntrenador() {
		return esEntrenador;
	}

	public void setEsEntrenador(Boolean esEntrenador) {
		this.esEntrenador = esEntrenador;
	}


	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
				+ ", contrasena=" + contrasena + ", fechaNacimiento=" + fechaNacimiento + ", esEntrenador="
				+ esEntrenador + ", nivel=" + nivel + "]";
	}

	public Cliente(String nombre, String apellidos, String email, String contrasena, Date fechaNacimiento,
			Boolean esEntrenador, int nivel, String id) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
		this.esEntrenador = esEntrenador;
		this.nivel = nivel;
		this.id = id;
	}

}
