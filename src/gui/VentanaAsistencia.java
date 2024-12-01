package gui;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


public class VentanaAsistencia {

	private JFrame ventana;
	private JTextField barraBusqueda;
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
		
		barraBusqueda = new JTextField();
		JLabel buscarAsistencia = new JLabel("Buscar usuario: ");
		JButton botonBuscar = new JButton("Buscar");
		JButton botonEliminar = new JButton("Eliminar");
		
		panelBusqueda.add(buscarAsistencia, BorderLayout.WEST);
		panelBusqueda.add(barraBusqueda, BorderLayout.CENTER);
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
		
		// ActionListener para buscar las asitencias en el buscador
		botonBuscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String busqueda = barraBusqueda.getText();
				buscarAsistencia(busqueda);
				
			}
		});
		
		// ActionListener para eliminar las asistencias seleccionadas
		botonEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminarAsistenciaSeleccionada();
				
			}
		});
		
		// Mostrar la ventana
		ventana.setVisible(true);
		
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
  	
	// Método para borrar la asistencia seleccionada
	private void eliminarAsistenciaSeleccionada() {
		int filaSeleccionada = tablaAsistencia.getSelectedRow();
		if (filaSeleccionada >= 0) {
			String usuarioAsistencia = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
			int confimacion = JOptionPane.showConfirmDialog(ventana, "¿Estás seguro de eliminar la asistencia de: " + usuarioAsistencia);
			
			if (confimacion == JOptionPane.YES_OPTION) {
				//Eliminar la asistencia de la lista y del modelo de la tabla
				listaAsistencias.remove(filaSeleccionada);
				modeloTabla.removeRow(filaSeleccionada);
				
				// Guardar los cambios en el CSV después de la eliminación
				guardarAsistenciasEnCSV("asistencias.csv");
				
			}
		} else {
			JOptionPane.showMessageDialog(ventana, "Por favor, selecciona una asistencia para eliminar.");
		}
	}
	
	private void guardarAsistenciasEnCSV(String filePath) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
			for (String[] asistencia : listaAsistencias) {
				writer.println(String.join(";", asistencia)); // escribir cada asistencia de nuevo al archivo
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(ventana, "Error al guardar el archivo CSV" + e.getMessage());
		}
	}
	
	
	
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(VentanaAsistencia::new);
    }
}
