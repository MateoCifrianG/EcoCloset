package domain;

public class Ropa {

	private String nombre; // Nombre de la prenda
	private String marca; // Marca de la prenda
	private String talla; // Talla de la prenda
	private int cantidad; // Cantidad de la prenda
	private double precio; // Precio de la prenda
	private String estado; // Estado de la prenda

	// Constructor
	public Ropa(String nombre, String marca, String talla, int cantidad, double precio, String estado) {

		this.nombre = nombre;
		this.marca = marca;
		this.talla = talla;
		this.cantidad = cantidad;
		this.precio = precio;
		this.estado = estado;
	}
	
	// Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	// ToString()
	public String toString() {
		return String.format("%s - Talla: &s - Precio: &.2f - Marca: %s", nombre, talla, precio, marca);
	}
	
}
