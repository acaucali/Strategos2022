package prueba;

public class Telefono {
	
	private String nombre;
	private int serial;
	private Double peso;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	
	public Telefono() {
		
	}
	
	public Telefono(String nombre, int serial, Double peso) {
		this.nombre = nombre;
		this.serial=serial;
		this.peso=peso;
	}
	
	/*
	public String toString() {
		return "telefono [nombre=" + nombre + ", serial=" + serial + ", peso=" + peso + "]";
	}
	*/
	
	public String toString() {
		return "Nombre: "+nombre+ "\n Serial: "+serial+"\n Peso: "+peso;
	}
	
		
}
