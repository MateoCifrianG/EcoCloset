package gui;
 import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MenuAdministrador {
	private JFrame ventanaMenu;
	
	public MenuAdministrador() {
		//Crear la ventana principal del menú del administrador
		ventanaMenu=new JFrame("Menú Administrador");
		ventanaMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventanaMenu.setSize(400, 300); //Tamaño de la ventana
		ventanaMenu.setLocationRelativeTo(null); //Centrar la ventana en la pantalla
		
		//Establecer un diseño de panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 1, 10, 10)); //Siete botones en vertical
		panel.setBackground(new Color(240, 240, 240)); //Color de fondo
		 
		//Estilo de los botones
		JButton botonGestionProductos=crearBoton("Gestionar Productos");
		botonGestionProductos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaMenu.dispose(); //Cerrar la ventana del menú
				new Administrador(); //Crear nueva ventana de administrador
			}
		});
		
		JButton botonEstadisticas=crearBoton("Ver Pedidos");
		botonEstadisticas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaMenu.dispose();
				//Crear y mostrar la ventana de estadísticas
				new Pedidos(); //Crear nueva ventana de estadísticas
				
			}
		});
		
		JButton botonGestionUsuarios = crearBoton("Gestionar Usuarios");
		botonGestionUsuarios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Lógica para gestionar usuarios aquí
				ventanaMenu.dispose(); //Cerrar la ventana del menú
				new GestorUsuarios(); //Abrir la ventana para gestionar usuarios
			}
		});
		
		//Crear un botón para mostrar estadísticas
		JButton botonEstadisticasGenerales = crearBoton("Ver Estadísticas");
		botonEstadisticasGenerales.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Crear y mostrar la nueva ventana de estadísticas
				new VentanaEstadisticas(); //Cambia el nombre de la clase si es necesario
			}
		});
		
		//Crear botón para la asistencia
		JButton botonAsistencia=crearBoton("Asistencia");
		botonAsistencia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Abrir la ventana de asistencia
				// new VentanaAsistencia(); //Clase para manejar la asistencia (descomentar al crear la clase)
				
			}
		});
		
		//Crear un botón para ir a la ventana principal
		JButton botonVentanaPrincipal = crearBoton("Ir a Ventana Principal");
		botonVentanaPrincipal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaPrincipal("Administrador"); //Abrir la ventana principal
				
			}
		});
		
		//Crear un botón de salir
		JButton botonSalir = crearBoton("Salir");
		botonSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaMenu.dispose(); //Cerrar la ventana del menú
				new VentanaRegistro(); //Abrir la ventana de registro
			}
		});
		
		//Añadir los botones al panel
		panel.add(botonGestionProductos);
		panel.add(botonEstadisticas);
		panel.add(botonGestionUsuarios);
		panel.add(botonEstadisticasGenerales);
		panel.add(botonAsistencia);
		panel.add(botonVentanaPrincipal);
		panel.add(botonSalir);
		
		//Establecer un borde y un título para el panel
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Opciones del Administrador"));
		
		//Añadir el panel a la ventana
		ventanaMenu.add(panel);
		
		//Mostar ventana principal
		ventanaMenu.setVisible(true);
	}
	
	//Método para crear botones con un estilo uniforme
	private JButton crearBoton(String texto) {
		JButton boton=new JButton(texto);
		boton.setFont(new Font("Arial", Font.BOLD, 16)); //Fuente del botón
		boton.setBackground(new Color(30, 144, 255)); //Color de fondo del botón
		boton.setForeground(Color.WHITE); //Color del texto del botón
		boton.setFocusPainted(false); //No mostrar el borde cuando el botón tiene el foco
		boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); //Espaciado interno
		boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //Cambiar el cursor al pasar por encima
		return boton;
	}
	
	public static void main(String[] args) {
    	SwingUtilities.invokeLater(MenuAdministrador::new);;
    }
}
