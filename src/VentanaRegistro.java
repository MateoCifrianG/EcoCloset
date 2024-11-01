import java.awt.GridBagLayout;

import javax.swing.JFrame;

public class VentanaRegistro {
	
	private JFrame ventana;
	
	public VentanaRegistro() {
		// CREACIÓN DE LA VENTANA DE REGISTRO
        ventana = new JFrame("Registro");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(400, 400); // Aumentar el tamaño para el nuevo enlace
        ventana.setLayout(new GridBagLayout()); //Para que todo mantenga su tamaño
        
      
	}
	
	
	public static void main(String[] args) {
		
		
		

	}

}
