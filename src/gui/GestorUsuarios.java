package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class GestorUsuarios {
    private JFrame ventana;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JTextField campoBusqueda;
    private List<String[]> listaUsuarios;
<<<<<<< HEAD
    //prueba
=======
// prueba
>>>>>>> branch 'main' of https://github.com/MateoCifrianG/EcoCloset.git
    public GestorUsuarios() {
        // Creo la ventana del gestor de usuarios
        ventana = new JFrame("Gestor de Usuarios");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600); // Tamaño de la ventana
        ventana.setLayout(new BorderLayout());

        // Panel superior con campo de búsqueda y botón de eliminar
        JPanel panelSuperior = new JPanel(new BorderLayout());
        campoBusqueda = new JTextField();
        JButton botonBuscar = new JButton("Buscar");
        JButton botonEliminar = new JButton("Eliminar");

        JPanel panelBusqueda = new JPanel(new BorderLayout());
        panelBusqueda.add(new JLabel("Buscar Usuario: "), BorderLayout.WEST);
        panelBusqueda.add(campoBusqueda, BorderLayout.CENTER);
        panelBusqueda.add(botonBuscar, BorderLayout.EAST);

        panelSuperior.add(panelBusqueda, BorderLayout.NORTH);
        panelSuperior.add(botonEliminar, BorderLayout.SOUTH);

        // Crear la tabla para mostrar los usuarios
        String[] columnas = {"Nombre", "Apellido1", "Apellido2", "Dirección", "Fecha de Nacimiento", "Nacionalidad", "Contraseña"};
        modeloTabla = new DefaultTableModel(columnas, 0); // Hago una tabla sin filas inicialmente
        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaUsuarios);

        // Cargar los usuarios desde la base de datos
        listaUsuarios = cargarUsuariosDesdeBD();
        actualizarTabla(listaUsuarios);

        // Habilitar la edición de la tabla
        tablaUsuarios.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));

        // Listener para detectar cambios en la tabla
        modeloTabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow(); // Obtener la fila modificada
                int column = e.getColumn(); // Obtener la columna modificada

                // Verificar que la fila y la columna sean válidas
                if (row >= 0 && column >= 0) {
                    String nuevoValor = (String) modeloTabla.getValueAt(row, column);

                    // Actualizar la lista de usuarios
                    listaUsuarios.get(row)[column] = nuevoValor;

                    // Guardar cambios en la base de datos
                    actualizarUsuarioEnBD(listaUsuarios.get(row));
                }
            }
        });

        // Crear el botón para volver al menú
        JButton botonVolver = new JButton("Volver al Menú Administrador");

        // Añadir acción para el botón volver
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose(); // Cerrar la ventana actual
                new MenuAdministrador(); // Aquí llamamos a la clase que representa el menú administrador
            }
        });

        // Añado los componentes
        ventana.add(panelSuperior, BorderLayout.NORTH);
        ventana.add(scrollTabla, BorderLayout.CENTER);
        ventana.add(botonVolver, BorderLayout.SOUTH); // Añadir el botón abajo

        // Acción para buscar usuarios
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String busqueda = campoBusqueda.getText();
                buscarUsuario(busqueda);
            }
        });

        // Acción para eliminar el usuario seleccionado
        botonEliminar.addActionListener(new ActionListener() {

			
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminarUsuarioSeleccionado();
				
			}
		});
        
        
		ventana.setVisible(true);
	}
		//Para hacer los métodos nos hemos apoyado en ChatGPT y los apuntes de Programacion II
    
    // Método para cargar usuarios desde la base de datos
    private List<String[]> cargarUsuariosDesdeBD() {
        List<String[]> usuarios = new ArrayList<>();
        String url = "jdbc:sqlite:resources/db/Usuarios.db";
        String consultaSQL = "SELECT * FROM usuarios";

        try (Connection conexion = DriverManager.getConnection(url);
             PreparedStatement ps = conexion.prepareStatement(consultaSQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String[] usuario = {
                    rs.getString("nombre"),
                    rs.getString("apellido1"),
                    rs.getString("apellido2"),
                    rs.getString("direccion"),
                    rs.getString("fechaNac"),
                    rs.getString("nacionalidad"),
                    rs.getString("contraseña")
                };
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ventana, "Error al leer los usuarios de la base de datos: " + e.getMessage());
        }

        return usuarios;
    }

    // Método para actualizar un usuario en la base de datos
    private void actualizarUsuarioEnBD(String[] usuario) {
        String url = "jdbc:sqlite:resources/db/Usuarios.db";
        String consultaSQL = "UPDATE usuarios SET apellido1 = ?, apellido2 = ?, direccion = ?, fecha_nacimiento = ?, nacionalidad = ?, contrasena = ? WHERE nombre = ?";

        try (Connection conexion = DriverManager.getConnection(url);
             PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {

            ps.setString(1, usuario[1]);
            ps.setString(2, usuario[2]);
            ps.setString(3, usuario[3]);
            ps.setString(4, usuario[4]);
            ps.setString(5, usuario[5]);
            ps.setString(6, usuario[6]);
            ps.setString(7, usuario[0]);

            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ventana, "Error al actualizar el usuario en la base de datos: " + e.getMessage());
        }
    }

    // Método para buscar usuarios por nombre en la base de datos
    private void buscarUsuario(String nombre) {
        List<String[]> usuariosFiltrados = new ArrayList<>();
        String url = "jdbc:sqlite:resources/db/Usuarios.db";
        String consultaSQL = "SELECT * FROM usuarios WHERE nombre LIKE ? OR apellido1 LIKE ? OR apellido2 LIKE ?";

        try (Connection conexion = DriverManager.getConnection(url);
             PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {

            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + nombre + "%");
            ps.setString(3, "%" + nombre + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String[] usuario = {
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("direccion"),
                        rs.getString("fechaNac"),
                        rs.getString("nacionalidad"),
                        rs.getString("contraseña")
                    };
                    usuariosFiltrados.add(usuario);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ventana, "Error al buscar usuarios en la base de datos: " + e.getMessage());
        }

        actualizarTabla(usuariosFiltrados);
    }

    // Método para eliminar el usuario seleccionado de la base de datos
    private void eliminarUsuarioSeleccionado() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombreUsuario = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            int confirmacion = JOptionPane.showConfirmDialog(ventana, "¿Estás seguro de eliminar el usuario: " + nombreUsuario + "?", "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                String url = "jdbc:sqlite:resources/db/Usuarios.db";
                String consultaSQL = "DELETE FROM usuarios WHERE nombre = ?";

                try (Connection conexion = DriverManager.getConnection(url);
                     PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {

                    ps.setString(1, nombreUsuario);
                    ps.executeUpdate();

                    // Eliminar el usuario de la lista y de la tabla
                    listaUsuarios.remove(filaSeleccionada);
                    modeloTabla.removeRow(filaSeleccionada);

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(ventana, "Error al eliminar el usuario de la base de datos: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(ventana, "Por favor, selecciona un usuario para eliminar");
        }
    }

    // Método para actualizar la tabla con usuarios
    private void actualizarTabla(List<String[]> usuarios) {
        modeloTabla.setRowCount(0); // Eliminamos las filas de la tabla
        for (String[] usuario : usuarios) {
            modeloTabla.addRow(usuario); // Añadir cada usuario a la tabla
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestorUsuarios::new);
    }
}