import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class VentanaAsistencia {

	private JFrame ventana;
	private JTextField campoBusqueda;
	
	public VentanaAsistencia() {
		
		//Ceación de la VentanaAsistencia
		ventana = new JFrame("Gestor de Asistencia");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800, 600); // Tamaño de la ventana
		ventana.setLayout(new BorderLayout());
		
	}
}
