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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
		
		//Crear la tabla para mostrar los usuarios
		String[] columnas= {"Nombre", "Apellido1", "Apellido2", "Dirección", "Fecha de Nacimiento", "Nacionalidad", "Contraseña", "Repetir Contraseña"};
		modeloTabla=new DefaultTableModel(columnas, 0); //Hago una tabla sin filas inicialmente
		tablaUsuarios=new JTable(modeloTabla);
		JScrollPane scrollTabla=new JScrollPane(tablaUsuarios);
		
		//Cargar los usuarios desde el CSV usuarios1 (métodos debajo)
		listaUsuarios=cargarUsuariosDesdeCSV("usuarios1.csv");
		actualizarTabla(listaUsuarios);
		
		//Habilitar la edición de la tabla
		tablaUsuarios.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
		
		//Listener para detectar cambios en la tabla
		modeloTabla.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow(); //Obtener la fila modificada
				int column = e.getColumn(); //Obtener la columna modificada
				
				//Verificar que la fila y la columna sean válidas
				if (row>=0 && column>=0) {
					String nuevoValor=(String) modeloTabla.getValueAt(row, column);
					
					//Actualizar la lista de usuarios
					listaUsuarios.get(row)[column] = nuevoValor;
					
					//Verificar si se ha cambiado la contraseña
					if(column == 6) { //Si la columna es "Contraseña"
						listaUsuarios.get(row)[7] = nuevoValor; //Actualizar "Repetir Contraseña" con el mismo valor
						modeloTabla.setValueAt(nuevoValor, row, 7); //Actualizar la tabla para que refleje este cambio
					}
					
					//Guardar cambios en el archivo CSV
					guardarUsuariosEnCSV("usuarios1.csv");
				}
				
			}
		});
		
		//Crear el botón para volver al menú
		JButton botonVolver=new JButton("Volver al Menú Administrador");
		
		//Añadir acción para el botón volver
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventana.dispose(); //Cerrar la ventana actual
				new MenuAdministrador(); //Aquí llamamos a la clase que representa el menú administrador
			}
		});
		
		
		//Añado los componentes
		ventana.add(panelSuperior, BorderLayout.NORTH);
        ventana.add(scrollTabla, BorderLayout.CENTER);
        ventana.add(botonVolver, BorderLayout.SOUTH); //Añadir el botón abajo
		
		//Acción para buscar usuarios
        botonBuscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String busqueda = campoBusqueda.getText();
				buscarUsuario(busqueda);
				
			}
		});
        
        
		ventana.setVisible(true);
	}
		//Para hacer los métodos nos hemos apoyado en ChatGPT y los apuntes de Programación II
		
	//Método para cargar usuarios desde un csv
		private List<String[]> cargarUsuariosDesdeCSV(String filePath){
			List<String[]> usuarios = new ArrayList<>();
			try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String linea;
	            while ((linea = reader.readLine()) != null) { //mientras la línea no sea nula
	                String[] valores = linea.split(";"); // Suponiendo que el separador es ";"
	                usuarios.add(valores); // Guardar cada línea
	            }
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(ventana, "Error al leer el archivo CSV: " + e.getMessage());
	        }
	        return usuarios; //devuelve la lista de usuarios
		}
		
		//Método para actualizar la tabla con usuarios
		private void actualizarTabla(List<String[]> usuarios) {
			modeloTabla.setRowCount(0); //Eliminamos las filas de la tabla
			for(String[] usuario: usuarios) {
				modeloTabla.addRow(usuario); //Añadir cada usuario a la tabla
			}
		}
		
		//Método para buscar un usuario por nombre
		private void buscarUsuario(String nombre) {
			List<String[]> usuariosFiltrados=new ArrayList<>();
			for(String[] usuario : listaUsuarios) {
				if(usuario[0].toLowerCase().contains(nombre.toLowerCase())
						|| usuario[1].toLowerCase().contains(nombre.toLowerCase())
						|| usuario[2].toLowerCase().contains(nombre.toLowerCase())) {
					usuariosFiltrados.add(usuario); //Añadir usuario si coincide con la búsqueda
				}
			}
			actualizarTabla(listaUsuarios); //Mostrar solo los usuarios filtrados
		}
		
		//Método para eliminar el usuario seleccionado
		private void eliminarUsuarioSeleccionado() {
			int filaSeleccionada=tablaUsuarios.getSelectedRow();
			if(filaSeleccionada>=0) {
				String nombreUsuario = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
				int confirmacion=JOptionPane.showConfirmDialog(ventana, "¿Estás seguro de eliminar el usuario: " + nombreUsuario);
			
				if(confirmacion==JOptionPane.YES_OPTION) {
					//Eliminar el usuario de la lista y del modelo de la tabla
					listaUsuarios.remove(filaSeleccionada);
					modeloTabla.removeRow(filaSeleccionada);
					
					//Guardar los cambios en el CSV
					guardarUsuariosEnCSV("usuarios1.csv");
				}
			}else {
				JOptionPane.showMessageDialog(ventana, "Por favor, selecciona un usuario para eliminar");
			}
		}
	
		// Método para guardar los usuarios modificados en el archivo CSV
	    private void guardarUsuariosEnCSV(String filePath) {
	        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
	            for (String[] usuario : listaUsuarios) {
	                writer.println(String.join(";", usuario)); // Escribir cada usuario de nuevo al archivo
	            }
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(ventana, "Error al guardar el archivo CSV: " + e.getMessage());
	        }
	    }
	    
	public static void main(String[] args) {
		new GestorUsuarios();

	}

}
