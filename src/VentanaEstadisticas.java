import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import domain.Pedido;
import domain.Ropa;
import domain.Usuario;

public class VentanaEstadisticas {
	private JFrame ventanaEstadisticas;
	private JPanel panelEstadisticas;
	
	private List<Ropa> productos;
	private List<Pedido> pedidos;
	private List<Usuario> usuarios;
	
	public VentanaEstadisticas() {
		productos=new ArrayList<>();
		pedidos=new ArrayList<>();
		usuarios=new ArrayList<>();
	
	
	//Crear la ventana
	ventanaEstadisticas=new JFrame("Estadísticas");
	ventanaEstadisticas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	ventanaEstadisticas.setSize(600, 500);
	ventanaEstadisticas.setLocationRelativeTo(null); //Centrar la ventana
	
	ventanaEstadisticas.setVisible(true);
	
	
	}
	
	public static void main(String[] args) {
    	new VentanaEstadisticas();
    }
}
