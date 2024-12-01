import java.awt.Color;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        JLabel problemasLabel = new JLabel("<html><u>Problemas al iniciar sesion</u></html>"); 
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
        
        
        //EVENTOS
        
        //Crear un ActionListener para el botón de registrar
        registrarButton.addActionListener(e -> {
        	//Abre el formulario de registro al hacer click en Registrar
        	new FormularioRegistro(); //Aquí se crea el formulario de registro
        });
        
        //Crear un ActionListener para el botón de iniciar sesión
        iniciarSesionButton.addActionListener(e -> {
        	String nombre = nombreField.getText();
        	String contraseña = new String(contraseñaField.getPassword());
        	//CONTINUAR cuando se cree la clase de comprobador
        });
        
        //ActioListener para el JLAbel "Problemas al iniciar sesión"
        problemasLabel.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				// Aquí delegamos la apertura de la ventana de problemas a la clase VentanaProblemas
                new Asistencia(); 
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				problemasLabel.setText("<html><u>Problemas al iniciar sesion</u></html>"); //Cambia el estilo al pasar el ratón				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				problemasLabel.setText("<html><u>Problemas al iniciar sesion</u></html>"); //Restaura el estilo al salir el ratón
				
			}
		});
        	
        
        //Hacer visible la ventana
        ventana.setVisible(true);
	}
	
	
	// Método para abrir la ventana principal
	public void abrirVentanaPrincipal(String nombreUsuario) {
		ventana.dispose(); // se cierra la ventana actual
		new VentanaPrincipal(nombreUsuario); // Pasa el nombre del usuario registrado en la ventana principal
	}
	
	
	public static void main(String[] args) {
		new VentanaRegistro();
		
	}

}
