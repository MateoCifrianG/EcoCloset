import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaCuenta {

	private JFrame ventanaCuenta;
	private String nombreUsuario;
	
	public VentanaCuenta(String nombreUsuario) {
		
		this.nombreUsuario = nombreUsuario;
		
		// Creación de la ventana
		ventanaCuenta = new JFrame("Cuenta de " + nombreUsuario);
		ventanaCuenta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventanaCuenta.setSize(500, 400); // Aumentar e tamaño
		ventanaCuenta.setLocationRelativeTo(null); // Centrar la ventana
		
		// Creación de un panel principal
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout()); // Disposición de borde
		
		// Creación de un panel con información del usuario
		JPanel panelInformacion = new JPanel();
		panelInformacion.setLayout(new BoxLayout(panelInformacion, BoxLayout.Y_AXIS)); // Disposición vertical
		panelInformacion.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaciado interno
		panelInformacion.setOpaque(false); // hacer el panel transparente
		
		// Titulo
		JLabel titulo = new JLabel("Bienvenido, " + nombreUsuario);
		titulo.setFont(new Font("Arial", Font.BOLD, 28)); // cambiar fuente y tamaño
		titulo.setForeground(new Color(70, 130, 180)); // Color del texto
		panelInformacion.add(titulo);
		
		
		
		
		
		
		
		
		
	}
	
	
	
}
