import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import domain.Pedido;
import domain.Ropa;
import domain.Usuario;

public class VentanaEstadisticas {
	private JFrame ventanaEstadisticas;
	private JPanel panelEstadisticas;
	
	private List<Ropa> productos;
	private List<Pedido> pedidos;
	private List<Usuario> usuarios;
	
	public VentanaEstadisticas() {
		productos=new ArrayList<>();
		pedidos=new ArrayList<>();
		usuarios=new ArrayList<>();
	
	
	//Crear la ventana
	ventanaEstadisticas=new JFrame("Estadísticas");
	ventanaEstadisticas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	ventanaEstadisticas.setSize(600, 500);
	ventanaEstadisticas.setLocationRelativeTo(null); //Centrar la ventana
	
	//Crear el panel principal
	panelEstadisticas=new JPanel();
	panelEstadisticas.setLayout(new BorderLayout(10, 10));
	panelEstadisticas.setBackground(new Color(255, 255, 255));
	panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	
	//Crear un nuevo panel para el botón
	JPanel panelBoton=new JPanel(new BorderLayout());
	
	//Crear el botón de volver al menú administrador
	JButton botonVolver=new JButton("Volver al Menú");
	botonVolver.setPreferredSize(new Dimension(10, 30)); //Establecer tamaño del botón
	botonVolver.setAlignmentX(Component.CENTER_ALIGNMENT); //Centrar el botón
	
	//Aplicar ActionListener al botón
	botonVolver.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			ventanaEstadisticas.dispose(); //Cerrar la ventana de estadísticas
			new MenuAdministrador(); //Volver al menú administrador
		}
	});
	
	//Agregar el botón al panel del botón
	panelBoton.add(botonVolver, BorderLayout.SOUTH);
	
	//Añadir panel de estadísticas y panel del botón a la ventana
	ventanaEstadisticas.add(panelEstadisticas, BorderLayout.CENTER);
	ventanaEstadisticas.add(panelBoton, BorderLayout.SOUTH);
	
	ventanaEstadisticas.setVisible(true);
	
	
	}
	
	private void cargarDatos() {
		cargarProductos("productos.csv");
		cargarUsuarios("usuarios1.csv");
		
	}
	
	private void cargarProductos(String archivo) {
		try(BufferedReader br=new BufferedReader(new FileReader(archivo))){
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split(";");
				if(partes.length==6) {
					Ropa prenda = new Ropa(partes[0], partes[1], partes[2], Integer.parseInt(partes[3]),
                            Double.parseDouble(partes[4]), partes[5]);
					productos.add(prenda);
					}
				}
			}catch(IOException e) {
				e.printStackTrace();
		}
	}
	
	private void cargarUsuarios(String archivo) {
		try(BufferedReader br = new BufferedReader(new FileReader(archivo))){
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split(";");
				if(partes.length>0) {
					Usuario usuario=new Usuario(partes[0]);
					usuarios.add(usuario);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
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

        JPanel tarjetaProductos = crearTarjeta("Total Productos", String.valueOf(totalProductos), new Color(135, 206, 235)); // --> crear metodo tarjetas
        JPanel tarjetaPedidos = crearTarjeta("Total Pedidos", String.valueOf(totalPedidos), new Color(255, 160, 122));
        JPanel tarjetaUsuarios = crearTarjeta("Total Usuarios", String.valueOf(totalUsuarios), new Color(144, 238, 144));

        panelEstadisticasContenedor.add(tarjetaProductos);
        panelEstadisticasContenedor.add(tarjetaPedidos);
        panelEstadisticasContenedor.add(tarjetaUsuarios);
        
     // Añadir el panel de estadísticas en la parte norte
        panelEstadisticas.add(panelEstadisticasContenedor, BorderLayout.NORTH);
        
     //  mapa para contar la cantidad de prendas por marca
        Map<String, Integer> marcasCount = new HashMap<>();
        for (Ropa producto : productos) {
            marcasCount.put(producto.getMarca(), marcasCount.getOrDefault(producto.getMarca(), 0) + producto.getCantidad());
        }

        // JTable para mostrar las estadísticas de marcas
        String[] columnNames = {"Marca", "Número de Prendas"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // agregar datos a la tabla
        for (Map.Entry<String, Integer> entry : marcasCount.entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }

        // Creamos la tabla
        JTable tableMarcas = new JTable(tableModel);
        tableMarcas.setFillsViewportHeight(true);
        JScrollPane scrollPaneMarcas = new JScrollPane(tableMarcas);
        scrollPaneMarcas.setBorder(BorderFactory.createTitledBorder("Estadísticas de Marcas"));
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
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
    	new VentanaEstadisticas();
    }
}
