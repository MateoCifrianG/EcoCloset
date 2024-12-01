import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
	
	//Crear el panel principal
	panelEstadisticas=new JPanel();
	panelEstadisticas.setLayout(new BorderLayout(10, 10));
	panelEstadisticas.setBackground(new Color(255, 255, 255));
	panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	
	//Crear un nuevo panel para el botón
	JPanel panelBoton=new JPanel(new BorderLayout());
	
	//Crear el botón de volver al menú administrador
	JButton botonVolver=new JButton("Volver al Menú");
	botonVolver.setPreferredSize(new Dimension(10, 30)); //Establecer tamaño del botón
	botonVolver.setAlignmentX(Component.CENTER_ALIGNMENT); //Centrar el botón
	
	//Aplicar ActionListener al botón
	botonVolver.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			ventanaEstadisticas.dispose(); //Cerrar la ventana de estadísticas
			new MenuAdministrador(); //Volver al menú administrador
		}
	});
	
	//Agregar el botón al panel del botón
	panelBoton.add(botonVolver, BorderLayout.SOUTH);
	
	//Añadir panel de estadísticas y panel del botón a la ventana
	ventanaEstadisticas.add(panelEstadisticas, BorderLayout.CENTER);
	ventanaEstadisticas.add(panelBoton, BorderLayout.SOUTH);
	
	ventanaEstadisticas.setVisible(true);
	
	
	}
	
	public static void main(String[] args) {
    	new VentanaEstadisticas();
    }
}
