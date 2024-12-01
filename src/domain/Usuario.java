package domain;

import java.awt.Component;

public class Usuario {

	private String nombre; //Nombre del usuario

	// Constructor
	public Usuario(String nombre) { 
		this.nombre = nombre;
	}

	//Get y Set
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

}
