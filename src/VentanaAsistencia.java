import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private DefaultTableModel modeloTabla;
	private JTable tablaAsistencia;
	private List<String[]> listaAsistencias;
	 
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
		
		// Creación de la TABLA para mostrar las asistencias
		String[] cabecera = {"Usuario", "Correo Electrónico", "Mensaje", "Fecha"};
		modeloTabla = new DefaultTableModel(cabecera, 0); 
		tablaAsistencia = new JTable(modeloTabla);  
		tablaAsistencia.setRowHeight(120); // altura de las filas de la tabla
		JScrollPane scrollTabla = new JScrollPane(tablaAsistencia);
		
		// Cargar las asistencias desde el CSV
		listaAsistencias = cargarAsistenciasDesdeCSV("asistencias.csv"); 
		actualizarTablaAsistencias(listaAsistencias);
		
		// Habilitar la edicióm de la tabla
		tablaAsistencia.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
		
		// Crear el botñon para volver al menú
		JButton botonVolver = new JButton("Volver al Menú Administrador");
		
		// Añadir ActionListener para el botón volver
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.dispose(); // cerrar la ventana actual
				//FALTA AÑADIR LA VENTANA DE ADMINISTRADOR
				
			}
		});
		
		// Añadir componentes a la ventana
		ventana.add(panelSuperior, BorderLayout.NORTH);
		ventana.add(scrollTabla, BorderLayout.CENTER);
		ventana.add(botonVolver, BorderLayout.SOUTH); // Botón volver abajo
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
		
	}
	
	//MÉTODOS PRIVADOS ---------
	
	// Método para cargar las asistencias desde el un archivo csv
	private List<String[]> cargarAsistenciasDesdeCSV(String filePath) {
		
		List<String[]> asistencias = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			
			String linea;
			while ((linea = reader.readLine()) != null) {
				String[] valores = linea.split(";"); // los datos están separados por un ";"
				asistencias.add(valores); // guardar cada linea como un Array de strings
			}
			
		}catch (IOException e) {
			JOptionPane.showMessageDialog(ventana, "Error al leer el archivo CSV: " + e.getMessage());
		}
		
		return asistencias;
		
	}
	
	// Método para actualizar la tabla con asistencias
	private void actualizarTablaAsistencias(List<String[]> asistencias) {
		modeloTabla.setRowCount(0); // Vaciar la tabla
		 
		for (String[] asistencia : asistencias) {
			modeloTabla.addRow(asistencia); // Añadir cada asistencia a la tabla
		}
	}
	
	// Método para buscar una asistencia por usuario
	private void buscarAsistencia(String usuario) {
		List<String[]> asistenciasFiltradas = new ArrayList<>();
		for (String[] asistencia : listaAsistencias) {
			if (asistencia[0].toLowerCase().contains(usuario.toLowerCase())) {
				asistenciasFiltradas.add(asistencia); // añadir la asistencia si coincide con la busqueda
			}
		}
		actualizarTablaAsistencias(asistenciasFiltradas); // mostrar solo las asistencias filtradas
	}
  	
	
	
	
	
	
	
	public static void main(String[] args) {
       new VentanaAsistencia();
    }
}
