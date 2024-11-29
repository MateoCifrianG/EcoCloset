	import javax.swing.*;
	import javax.swing.table.DefaultTableModel;
	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.*;
	import java.util.ArrayList;
	import java.util.List;
	
	public class Administrador {
	    private JFrame ventana;
	    private JTable tablaProductos;
	    private DefaultTableModel modeloTabla;
	    private JTextField campoBusqueda;
	    private List<String[]> listaProductos;
	
	    public Administrador() {
	        // CREACION DE LA VENTANA DEL ADMINISTRADOR
	        ventana = new JFrame("Administrador de Productos");
	        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        ventana.setSize(800, 600); // Tamaño de la ventana
	        ventana.setLayout(new BorderLayout());
	
	        // Panel superior con campo de búsqueda y botón de eliminar
	        JPanel panelSuperior = new JPanel(new BorderLayout());
	        campoBusqueda = new JTextField();
	        JButton botonBuscar = new JButton("Buscar");
	        JButton botonEliminar = new JButton("Eliminar");
	
	        JPanel panelBusqueda = new JPanel(new BorderLayout());
	        panelBusqueda.add(new JLabel("Buscar Producto: "), BorderLayout.WEST);
	        panelBusqueda.add(campoBusqueda, BorderLayout.CENTER);
	        panelBusqueda.add(botonBuscar, BorderLayout.EAST);
	        
	        panelSuperior.add(panelBusqueda, BorderLayout.NORTH);
	        panelSuperior.add(botonEliminar, BorderLayout.SOUTH);
	
	      
	
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(Administrador::new);
	    }
	}
