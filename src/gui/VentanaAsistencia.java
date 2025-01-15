package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaAsistencia {

    private JFrame ventana;
    private JTextField barraBusqueda;
    private DefaultTableModel modeloTabla;
    private JTable tablaAsistencia;

    // Ruta de conexión a la base de datos
    private static final String url = "jdbc:sqlite:resources/db/asistencias.db";

    public VentanaAsistencia() {
        // Creación de la ventana
        ventana = new JFrame("Gestor de Asistencia");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600);
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

        // Creación de la tabla para mostrar las asistencias
        String[] cabecera = {"Usuario", "Correo Electrónico", "Mensaje", "Fecha"};
        modeloTabla = new DefaultTableModel(cabecera, 0);
        tablaAsistencia = new JTable(modeloTabla);
        tablaAsistencia.setRowHeight(120); // Altura de las filas
        JScrollPane scrollTabla = new JScrollPane(tablaAsistencia);

        // Crear el botón para volver al menú
        JButton botonVolver = new JButton("Volver al Menú Administrador");
        botonVolver.addActionListener(e -> ventana.dispose()); // Cerrar la ventana actual

        // Cargar las asistencias desde la base de datos
        List<String[]> listaAsistencias = cargarAsistenciasDesdeBD();
        actualizarTablaAsistencias(listaAsistencias);

        // Añadir componentes a la ventana
        ventana.add(panelSuperior, BorderLayout.NORTH);
        ventana.add(scrollTabla, BorderLayout.CENTER);
        ventana.add(botonVolver, BorderLayout.SOUTH);

        // ActionListener para buscar asistencias
        botonBuscar.addActionListener(e -> {
            String busqueda = barraBusqueda.getText();
            buscarAsistenciaEnBD(busqueda);
        });

        // ActionListener para eliminar asistencias seleccionadas
        botonEliminar.addActionListener(e -> eliminarAsistenciaSeleccionada());

        // Mostrar la ventana
        ventana.setVisible(true);
    }

    // Métodos para conectar la asistencia con la bd 
    private List<String[]> cargarAsistenciasDesdeBD() {
    	List<String[]> asistencia = new ArrayList<>();
        String url = "jdbc:sqlite:resources/db/asistencias.db";
        String consultaSQL = "SELECT * FROM Asistencias";

        try (Connection conexion = DriverManager.getConnection(url);
             PreparedStatement ps = conexion.prepareStatement(consultaSQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String[] usuario = {
                    rs.getString("Id"),
                    rs.getString("Usuario"),
                    rs.getString("Correo"),
                    rs.getString("Mensaje"),
                    rs.getString("Fecha")
                };
                asistencia.add(usuario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ventana, "Error al leer asistencia ");
        }

        return asistencia;
    }

    private void actualizarTablaAsistencias(List<String[]> asistencias) {
        modeloTabla.setRowCount(0); // Vaciar la tabla

        for (String[] asistencia : asistencias) {
            modeloTabla.addRow(asistencia); // Añadir cada asistencia a la tabla
        }
    }

    private void buscarAsistenciaEnBD(String usuario) {
        List<String[]> asistenciasFiltradas = new ArrayList<>();
        String consultaSQL = "SELECT Id, Usuario, Correo, Mensaje, Fecha FROM Asistencias WHERE Usuario LIKE ?";

        try (Connection conexion = DriverManager.getConnection(url);
             PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {

            ps.setString(1, "%" + usuario + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String[] asistencia = {
                    	rs.getString("Id"),
                        rs.getString("Usuario"),
                        rs.getString("Correo"),
                        rs.getString("Mensaje"),
                        rs.getString("Fecha")
                    };
                    asistenciasFiltradas.add(asistencia);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ventana, "Error al buscar asistencias: " + e.getMessage());
        }

        actualizarTablaAsistencias(asistenciasFiltradas);
    }

    //Ayuda con ChatGPT --> dudas en el código, ya que daba errores.
    // File (https://chatgpt.com/share/6787f0ab-7c88-800c-b7d4-dba04dacaa9d)
    // el JOptionPane nos daba error. También nos apoyamos en la ia para hacer este metodo.
    private void eliminarAsistenciaSeleccionada() {
        int filaSeleccionada = tablaAsistencia.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String usuario = (String) modeloTabla.getValueAt(filaSeleccionada, 0);

            int confirmacion = JOptionPane.showConfirmDialog(ventana, 
                "¿Estás seguro de eliminar la asistencia de: " + usuario + "?");

            if (confirmacion == JOptionPane.YES_OPTION) {
                String consultaSQL = "DELETE FROM asistencias WHERE usuario = ?";

                try (Connection conexion = DriverManager.getConnection(url);
                     PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {

                    ps.setString(1, usuario);
                    ps.executeUpdate();

                    modeloTabla.removeRow(filaSeleccionada); // Eliminar de la tabla visual

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(ventana, "Error al eliminar la asistencia: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(ventana, "Por favor, selecciona una asistencia para eliminar.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaAsistencia::new);
    }
}
