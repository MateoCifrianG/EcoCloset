import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GestorUsuarios {
	private JFrame ventana;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JTextField campoBusqueda;
    private List<String[]> listaUsuarios;
    
	public GestorUsuarios() {
		//Creo la ventana del gestor de usuarios
		ventana = new JFrame("Gestor de Usuarios");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800, 600); //Tamaño de la ventana
		ventana.setLayout(new BorderLayout());
		
		//Panel superior con campo de búsqueda y botón de eliminar
		JPanel panelSuperior= new JPanel(new BorderLayout());
		campoBusqueda = new JTextField();
		JButton botonBuscar = new JButton("Buscar");
		JButton botonEliminar = new JButton("Eliminar");
		
		JPanel panelBusqueda = new JPanel(new BorderLayout());
		panelBusqueda.add(new JLabel("Buscar Usuario: "), BorderLayout.WEST);
		panelBusqueda.add(campoBusqueda, BorderLayout.CENTER);
		panelBusqueda.add(botonBuscar, BorderLayout.EAST);
		
		panelSuperior.add(panelBusqueda, BorderLayout.NORTH);
		panelSuperior.add(botonEliminar, BorderLayout.SOUTH);
		
		
		
		
		ventana.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		new GestorUsuarios();

	}

}
