package es.cic.taller.ejercicio07;

public class Pais {
	private String nombreCompleto;

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	@Override
	public String toString() {
		return nombreCompleto;
	}

}
