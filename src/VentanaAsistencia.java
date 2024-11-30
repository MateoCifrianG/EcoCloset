import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class VentanaAsistencia {

	private JFrame ventana;
	private JTextField campoBusqueda;
	private TableModel modeloTabla;
	private JTable tablaAsistencia;
	
	public VentanaAsistencia() {
		
		// Ceación de la VentanaAsistencia
		ventana = new JFrame("Gestor de Asistencia");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800, 600); // Tamaño de la ventana
		ventana.setLayout(new BorderLayout());
		
		// Panel superior con filtro de búsqueda y botón de eliminar
		JPanel panelSuperior = new JPanel(new BorderLayout());
		JPanel panelBusqueda = new JPanel(new BorderLayout());
		
		campoBusqueda = new JTextField();
		JLabel buscarAsistencia = new JLabel("Buscar Asistencia: ");
		JButton botonBuscar = new JButton("Buscar");
		JButton botonEliminar = new JButton("Eliminar");
		
		panelBusqueda.add(buscarAsistencia, BorderLayout.WEST);
		panelBusqueda.add(campoBusqueda, BorderLayout.CENTER);
		panelBusqueda.add(botonBuscar, BorderLayout.EAST);
		
		panelSuperior.add(panelBusqueda, BorderLayout.NORTH);
		panelSuperior.add(botonEliminar, BorderLayout.SOUTH);
		
		// Creación de la tabla para mostrar las asistencias
		String[] cabecera = {"Usuario", "Correo Electrónico", "Mensaje", "Fecha"};
		modeloTabla = new DefaultTableModel(cabecera, 0); 
		tablaAsistencia = new JTable(modeloTabla);  
		tablaAsistencia.setRowHeight(120); // altura de las filas de la tabla
		JScrollPane scrollTabla = new JScrollPane(tablaAsistencia);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
		
	}
	public static void main(String[] args) {
       new VentanaAsistencia();
    }
}
