package modelo;

public class Serie {
	private int cuentaatras;
	private String fotoSeries;
	private String nomSeries;
	private int numRepeticiones;
	private String id;

	public Serie(int cuentaatras, String fotoSeries, String nomSeries, int numRepeticiones, String id) {
		super();
		this.cuentaatras = cuentaatras;
		this.fotoSeries = fotoSeries;
		this.nomSeries = nomSeries;
		this.numRepeticiones = numRepeticiones;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCuentaatras() {
		return cuentaatras;
	}

	public void setCuentaatras(int cuentaatras) {
		this.cuentaatras = cuentaatras;
	}

	public String getFotoSeries() {
		return fotoSeries;
	}

	public void setFotoSeries(String fotoSeries) {
		this.fotoSeries = fotoSeries;
	}

	public String getNomSeries() {
		return nomSeries;
	}

	public void setNomSeries(String nomSeries) {
		this.nomSeries = nomSeries;
	}

	public int getNumRepeticiones() {
		return numRepeticiones;
	}

	public void setNumRepeticiones(int numRepeticiones) {
		this.numRepeticiones = numRepeticiones;
	}
}
