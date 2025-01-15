package domain;

import java.awt.Component;
import java.util.Objects;

public class Usuario {

	private String nombre; // Nombre del usuario
	private String apellido1;
	private String apellido2;
	private String direccion;
	private String fechaNac;
	private String nacionalidad;
	private String contraseña;

	public Usuario(String nombre)
	{
		super();
		this.nombre = nombre;
		
	}
	
	

	public Usuario(String nombre, String apellido1, String apellido2, String direccion, String fechaNac,
			String nacionalidad, String contraseña) {
		super();
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.direccion = direccion;
		this.fechaNac = fechaNac;
		this.nacionalidad = nacionalidad;
		this.contraseña = contraseña;
	}



	public Usuario(String nombre, String contraseña) {
		super();
		this.nombre = nombre;
		this.contraseña = contraseña;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contraseña, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(contraseña, other.contraseña) && Objects.equals(nombre, other.nombre);
	}

}
