import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaPrincipal {
	private JFrame ventana;
	
	public VentanaPrincipal() {
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
			
	}
	

	public static void main(String[] args) {
		new VentanaPrincipal();

	}

}
