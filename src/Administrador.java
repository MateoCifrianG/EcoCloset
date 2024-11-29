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
	
	        // Crear la tabla para mostrar los productos
	        String[] columnas = {"Nombre", "Marca", "Talla", "Cantidad", "Precio", "Estado"};
	        modeloTabla = new DefaultTableModel(columnas, 0);
	        tablaProductos = new JTable(modeloTabla);
	        JScrollPane scrollTabla = new JScrollPane(tablaProductos);
	
	        // Cargar los productos desde el CSV
	        listaProductos = cargarProductosDesdeCSV("productos.csv");
	        actualizarTabla(listaProductos);
	
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
	
	        // Añadir componentes a la ventana
	        ventana.add(panelSuperior, BorderLayout.NORTH);
	        ventana.add(scrollTabla, BorderLayout.CENTER);
	        ventana.add(botonVolver, BorderLayout.SOUTH); // Añadir el botón al sur
	
	        // Acción para buscar productos
	        botonBuscar.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String busqueda = campoBusqueda.getText();
	                buscarProducto(busqueda);
	            }
	        });
	
	        // Acción para eliminar el producto seleccionado
	        botonEliminar.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                eliminarProductoSeleccionado();
	            }
	        });
	
	        // Mostrar la ventana
	        ventana.setVisible(true);
	    }
	
	    // Método para cargar productos desde un archivo CSV
	    private List<String[]> cargarProductosDesdeCSV(String filePath) {
	        List<String[]> productos = new ArrayList<>();
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] valores = line.split(";"); // Suponiendo que el separador es punto y coma
	                productos.add(valores); // Guardar cada línea como un arreglo de strings
	            }
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(ventana, "Error al leer el archivo CSV: " + e.getMessage());
	        }
	        return productos;
	    }
	
	   
	
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(Administrador::new);
	    }
	}
