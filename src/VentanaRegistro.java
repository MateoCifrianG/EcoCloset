import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaRegistro {
	
	private JFrame ventana;
	
	public VentanaRegistro() {
		// CREACIÓN DE LA VENTANA DE REGISTRO
        ventana = new JFrame("Registro");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(400, 400); // Aumentar el tamaño para el nuevo enlace
        ventana.setLayout(new GridBagLayout()); //Para que todo mantenga su tamaño
        
        //Crear los componentes de la ventana
        JLabel nombreLabel = new JLabel("Nombre: ");
        JTextField nombreField = new JTextField(20);
        JLabel contraseñaLabel = new JLabel("Contraseña: ");
        JPasswordField contraseñaField = new JPasswordField(20);
        JButton iniciarSesionButton = new JButton("Iniciar Sesión");
        JButton registrarButton = new JButton("Registrar");
        
        //Crear un JLabel que actue como un enlace
        JLabel problemasLabel = new JLabel("Problemas al iniciar sesion"); 
        problemasLabel.setForeground(Color.BLUE);
        problemasLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //Cuando el cursor se pose sobre el JLabel, el cursor cambiará a una mano
        
        
        
        
        //Hacer visible la ventana
        ventana.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new VentanaRegistro();
		
	}

}
