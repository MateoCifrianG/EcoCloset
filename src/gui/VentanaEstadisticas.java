package gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import domain.Pedido;
import domain.Ropa;
import domain.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class VentanaEstadisticas {
    private JFrame ventanaEstadisticas;
    private JPanel panelEstadisticas;

    private List<Ropa> productos;
    private List<Pedido> pedidos;
    private List<Usuario> usuarios;

    public VentanaEstadisticas() {
        productos = new ArrayList<>();
        pedidos = new ArrayList<>();
        usuarios = new ArrayList<>();

        // Cargar datos desde archivos CSV
        cargarDatos();

        // Crear la ventana
        ventanaEstadisticas = new JFrame("Estadísticas");
        ventanaEstadisticas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaEstadisticas.setSize(600, 500);
        ventanaEstadisticas.setLocationRelativeTo(null); // Centrar la ventana

        // Crear el panel principal
        panelEstadisticas = new JPanel();
        panelEstadisticas.setLayout(new BorderLayout(10, 10));
        panelEstadisticas.setBackground(new Color(255, 255, 255));
        panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Agregar estadísticas
        agregarEstadisticas();

        // Crear un nuevo panel para el botón
        JPanel panelBoton = new JPanel(new BorderLayout());

        // Crear botón de volver al menú administrador
        JButton botonVolver = new JButton("Volver al Menú");
        botonVolver.setPreferredSize(new Dimension(10, 30)); // Establecer el tamaño del botón
        botonVolver.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el botón

        // Aplicar estilo al botón
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaEstadisticas.dispose(); // Cerrar la ventana de estadísticas
                new MenuAdministrador(); // Volver al menú administrador
            }
        });

        // Agregar botón al panel del botón
        panelBoton.add(botonVolver, BorderLayout.SOUTH);

        // Añadir panel de estadísticas y panel del botón a la ventana
        ventanaEstadisticas.add(panelEstadisticas, BorderLayout.CENTER);
        ventanaEstadisticas.add(panelBoton, BorderLayout.SOUTH);

        ventanaEstadisticas.setVisible(true);
    }

    private void cargarDatos() {
        cargarProductos("productos.csv");
        cargarPedidos("pedidos.csv");
        cargarUsuariosBD();
    }

    private void cargarProductos(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    Ropa prenda = new Ropa(partes[0], partes[1], partes[2], Integer.parseInt(partes[3]),
                            Double.parseDouble(partes[4]), partes[5]);
                    productos.add(prenda);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarPedidos(String archivo) {
        Set<Integer> idsPedidosProcesados = new HashSet<>(); // Almacena los IDs de los pedidos ya procesados

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 7) {
                    int idPedido = Integer.parseInt(partes[0]); // ID del pedido

                    // Verificar si el ID del pedido ya ha sido procesado
                    if (idsPedidosProcesados.contains(idPedido)) {
                        continue; // Si ya se procesó, saltar a la siguiente línea
                    }

                    Usuario usuario = new Usuario(partes[1]);
                    List<Ropa> prendas = new ArrayList<>();

                    // Agregar prendas al pedido
                    for (int i = 2; i < partes.length; i++) { // Asumimos que partes[i] tiene el nombre de la prenda
                        Ropa prenda = buscarPrendaPorNombre(partes[i]); // Método para encontrar prenda por nombre
                        if (prenda != null) {
                            prendas.add(prenda);
                        }
                    }

                    // Crear un pedido con las prendas cargadas
                    Pedido pedido = new Pedido(idPedido, usuario, prendas);
                    pedidos.add(pedido); // Añadir el pedido a la lista
                    idsPedidosProcesados.add(idPedido); // Añadir el ID del pedido al conjunto de procesados
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Método auxiliar para buscar una prenda por su nombre
    private Ropa buscarPrendaPorNombre(String nombrePrenda) {
        for (Ropa prenda : productos) {
            if (prenda.getNombre().equalsIgnoreCase(nombrePrenda)) {
                return prenda;
            }
        }
        return null; // Si no se encuentra la prenda
    }

    private void cargarUsuariosBD() { 
        
        String url = "jdbc:sqlite:resources/db/Usuarios.db";
        String consultaSQL = "SELECT * FROM usuarios";

        try (Connection conexion = DriverManager.getConnection(url);
             PreparedStatement ps = conexion.prepareStatement(consultaSQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getString("nombre"));
                usuarios.add(usuario);                };
                
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(panelEstadisticas, "Error al leer los usuarios de la base de datos: " + e.getMessage());
        }

        
    }

    private void agregarEstadisticas() {
        int totalProductos = productos.size();
        int totalPedidos = pedidos.size(); // Esto ahora refleja solo los pedidos únicos, no por prendas
        int totalUsuarios = usuarios.size();

        // Crear un panel para las estadísticas
        JPanel panelEstadisticasContenedor = new JPanel();
        panelEstadisticasContenedor.setLayout(new GridLayout(1, 3, 10, 10));
        panelEstadisticasContenedor.setBackground(new Color(255, 255, 255));

        JPanel tarjetaProductos = crearTarjeta("Total Productos", String.valueOf(totalProductos), new Color(135, 206, 235));
        JPanel tarjetaPedidos = crearTarjeta("Total Pedidos", String.valueOf(totalPedidos), new Color(255, 160, 122));
        JPanel tarjetaUsuarios = crearTarjeta("Total Usuarios", String.valueOf(totalUsuarios), new Color(144, 238, 144));

        panelEstadisticasContenedor.add(tarjetaProductos);
        panelEstadisticasContenedor.add(tarjetaPedidos);
        panelEstadisticasContenedor.add(tarjetaUsuarios);

        // Añadir el panel de estadísticas en la parte norte
        panelEstadisticas.add(panelEstadisticasContenedor, BorderLayout.NORTH);

        // Crear mapa para contar la cantidad de prendas por marca
        Map<String, Integer> marcasCount = new HashMap<>();
        for (Ropa producto : productos) {
            marcasCount.put(producto.getMarca(), marcasCount.getOrDefault(producto.getMarca(), 0) + producto.getCantidad());
        }

        // Crear un JTable para mostrar las estadísticas de marcas
        String[] columnNames = {"Marca", "Número de Prendas"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Agregar datos a la tabla
        for (Map.Entry<String, Integer> entry : marcasCount.entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }

        // Crear la tabla
        JTable tableMarcas = new JTable(tableModel);
        tableMarcas.setFillsViewportHeight(true);
        JScrollPane scrollPaneMarcas = new JScrollPane(tableMarcas);
        scrollPaneMarcas.setBorder(BorderFactory.createTitledBorder("Estadísticas de Marcas"));

     // Renderizado personalizado para la cabecera
        tableMarcas.getTableHeader().setDefaultRenderer((table, value, isSelected, hasFocus, row, column) -> {
		JLabel cabecera = new JLabel(value.toString());
		
		cabecera.setHorizontalAlignment(SwingConstants.CENTER);
		cabecera.setFont(new Font("Verdana", Font.BOLD, 13));
		cabecera.setBackground(Color.decode("#ff9580"));
		cabecera.setBorder(BorderFactory.createLineBorder(Color.decode("#f2401d"), 2));
		cabecera.setOpaque(true);
		
		return cabecera;
	});
        
        // Crear un panel para el área de búsqueda
        JPanel panelBusqueda = new JPanel(new BorderLayout());

        // Crear un campo de texto para filtrar
        JTextField filtroField = new JTextField();
        filtroField.setToolTipText("Buscar por marca o cantidad");
        filtroField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filtro = filtroField.getText().toLowerCase();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
                tableMarcas.setRowSorter(sorter);

                // Definir el filtro
                if (filtro.trim().length() == 0) {
                    sorter.setRowFilter(null); // Si está vacío, mostrar todo
                } else {
                    // Filtrar por marca o cantidad
                    List<RowFilter<Object, Object>> filters = new ArrayList<>();
                    filters.add(RowFilter.regexFilter("(?i)" + filtro, 0)); // Filtrar por marca (insensible a mayúsculas)
                    try {
                        filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, Integer.parseInt(filtro), 1)); // Filtrar por cantidad
                    } catch (NumberFormatException ex) {
                        // Ignorar si no es un número
                    }
                    sorter.setRowFilter(RowFilter.orFilter(filters)); // Aplicar filtro combinado
                }
            }
        });

        // Añadir el campo de filtro y la tabla al panel de búsqueda
        panelBusqueda.add(filtroField, BorderLayout.NORTH);
        panelBusqueda.add(scrollPaneMarcas, BorderLayout.CENTER);

        // Añadir panel de búsqueda al panel de estadísticas
        panelEstadisticas.add(panelBusqueda, BorderLayout.CENTER);
    }

    private JPanel crearTarjeta(String titulo, String valor, Color color) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(color);
        tarjeta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setForeground(Color.BLACK);
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel labelValor = new JLabel(valor);
        labelValor.setFont(new Font("Arial", Font.BOLD, 24));
        labelValor.setForeground(Color.BLACK);
        labelValor.setAlignmentX(Component.CENTER_ALIGNMENT);

        tarjeta.add(labelTitulo);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio
        tarjeta.add(labelValor);
        
        return tarjeta;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaEstadisticas::new);
    }
}
