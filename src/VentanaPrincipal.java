import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaPrincipal {
	private JFrame ventana;
	
	public VentanaPrincipal(String nombreUsuario) { 
		//creamos la ventana
		ventana = new JFrame("ECOCLOSET");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800, 600);
		
		//Creamos el panel superior y los botones de compra venta.
			
			//Compra
		JPanel panelCompraVenta = new JPanel(new GridLayout(1, 2, 10, 10));
		JButton compra = new JButton("COMPRA");
		compra.setFont(new Font("Courier", Font.BOLD, 20));
		
			//Venta
		JButton venta = new JButton("VENTA");
		venta.setFont(new Font("Courier", Font.BOLD, 20));
		panelCompraVenta.add(compra);
		panelCompraVenta.add(venta);
		
		//Añadimos el panel de compra venta a la ventana arriba de todo (norte)
		ventana.add(panelCompraVenta, BorderLayout.NORTH);
		
		//Creamos el panel princial
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		
		//creamos el panel superior
		JPanel buscador = new JPanel(new GridLayout(2, 3, 10, 10));
        buscador.setBackground(new Color(152, 251, 152)); // Azul clarito en rgb
		JLabel vacio = new JLabel();
		JButton filtrar = new JButton("FILTRAR");
		JLabel vacio2 = new JLabel();
		
		// Cambiamos el botón de precio por un JComboBox de rangos de precios
		String[] rangosPrecio = { "Precio", "0-15", "15-30", "30-50", "50-100", "100-200", "200-500", "500-1000", "1000-5000", "5000+" };
		JComboBox<String> comboPrecio = new JComboBox<>(rangosPrecio);
		comboPrecio.setFont(new Font("Courier", Font.BOLD, 15));
			
		// Cambiamos el JComboBox de número por uno de tallas
		String[] tallas = { "Talla", "XS", "S", "M", "L", "XL", "XXL" };
		JComboBox<String> comboTalla = new JComboBox<>(tallas);
		comboTalla.setFont(new Font("Courier", Font.BOLD, 15));
		
		//Añadimos todo
		buscador.add(vacio);
		buscador.add(filtrar);
		buscador.add(vacio2);
		buscador.add(comboPrecio); // Añadir JComboBox de precios
		buscador.add(comboTalla); 
		//Falta la ropa, tenemos que crear la base de datos de todas las prendas para poder meterla.
		
		
		ventana.setVisible(true);
			
	}
	

	public static void main(String[] args) {
		new VentanaPrincipal();

	}

}
