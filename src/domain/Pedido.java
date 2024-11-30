package domain;

import java.util.List;

public class Pedido {

	private int id; // Identificativo del pedido
	private Usuario usuario; // Asumimos que cada pedido va asociado a un usuario
	private List<Ropa> prendas; // Lista de prendas que van en el pedido

	//Constructror
	public Pedido(int id, Usuario usuario, List<Ropa> prendas) {

		this.id = id;
		this.usuario = usuario;
		this.prendas = prendas;
	}

	//Getters y Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Ropa> getPrendas() {
		return prendas;
	}

	public void setPrendas(List<Ropa> prendas) {
		this.prendas = prendas;
	}


}
