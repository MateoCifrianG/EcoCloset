import java.awt.Color;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
        
        //Crear un GridBagConstraints para posicionar los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); //Espacio entre los componentes
        
        //Ahora añadimos los componentes creados a la ventana
        gbc.gridx = 0; //Columna 0
        gbc.gridy = 0; //Fila 0
        ventana.add(nombreLabel, gbc);
        gbc.gridx = 1; //Columna 1
        ventana.add(nombreField, gbc);
        
        gbc.gridx = 0; //Columna 0
        gbc.gridy = 1; //Fila 1
        ventana.add(contraseñaLabel, gbc);
        gbc.gridx = 1; //Columna 1
        ventana.add(contraseñaField, gbc);
        
        gbc.gridx = 0; //Columna 0
        gbc.gridy = 2; //Fila 2
        gbc.gridwidth = 2; //Ocupa ambas columnas
        ventana.add(iniciarSesionButton, gbc);
        
        gbc.gridy = 3; //Fila 3
        ventana.add(registrarButton, gbc);
        
        //Agregar el JLabel como enlace
        gbc.gridy = 4; //Fila4
        ventana.add(problemasLabel, gbc);
        
        
        
        
        //Hacer visible la ventana
        ventana.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new VentanaRegistro();
		
	}

}
