package modelo;

import java.util.Date;

public class Historico {
	private double porcentaje;
	private String nombre;
	private int nivel;
	private int tiempoPrevisto;
	private int tiempototal;
	private Date fecha;
	
	public Historico(double porcentaje, String nombre, int nivel, int tiempoPrevisto, int tiempototal, Date fecha) {
		super();
		this.porcentaje = porcentaje;
		this.nombre = nombre;
		this.nivel = nivel;
		this.tiempoPrevisto = tiempoPrevisto;
		this.tiempototal = tiempototal;
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return "Historico [porcentaje=" + porcentaje + ", nombre=" + nombre + ", nivel=" + nivel + ", tiempoPrevisto="
				+ tiempoPrevisto + ", tiempototal=" + tiempototal + ", fecha=" + fecha + "]";
	}
	public Historico(Double porcentaje, Date fecha) {
		this.porcentaje = porcentaje;
		this.fecha = fecha;
		// TODO Auto-generated constructor stub
	}
	public double getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
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
		return tiempototal;
	}
	public void setTiempototal(int tiempototal) {
		this.tiempototal = tiempototal;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
