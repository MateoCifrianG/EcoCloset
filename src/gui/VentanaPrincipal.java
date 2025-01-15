package gui;
 import javax.swing.*;

import bd.BaseDatosRopa;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import domain.Pedido;
import domain.Ropa;
import domain.Usuario;
import main.Inicializador;

public class VentanaPrincipal {
	private JFrame ventana; // Mantener una referencia a la ventana
	private DefaultListModel<Ropa> modeloCarrito; // Modelo para el carrito
	private JList<Ropa> listaCarrito; // Lista para mostrar los artículos en el carrito
	private String nombreUsuario;
	private JPanel listaArticulosPanel; // Panel para mostrar los artículos
	private List<Ropa> ropaList; // Lista original de ropa
	private List<Pedido> listaPedidos; // Almacena los pedidos del usuario
	private JLabel totalArticulosLabel; // Label para mostrar el total de artículos
	private JLabel totalPrecioLabel; // Label para mostrar el precio total
	
	public VentanaPrincipal getVentana() {
	    return this;
	}
	
	public void dispose() {
        ventana.dispose();
    }
	
	public DefaultListModel<Ropa> getModeloCarrito() {
	    return modeloCarrito;
	}
	
	public VentanaPrincipal(String nombreUsuario) {
		// CREACION DE LA VENTANA
		ventana = new JFrame("ECOCLOSET");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800, 600);
		this.nombreUsuario = nombreUsuario;
		ventana.setLocationRelativeTo(null);

		// Inicializar la lista de pedidos
		listaPedidos = new ArrayList<>();

		// CREACION DE PANEL SUPERIOR Y BOTONES COMPRA VENTA
		JPanel panelCompraVenta = new JPanel(new GridLayout(1, 2, 10, 10));
		JButton compra = new JButton("COMPRA");
		compra.setFont(new Font("Courier", Font.BOLD, 20));
		JButton venta = new JButton("VENTA");
		venta.setFont(new Font("Courier", Font.BOLD, 20));
		panelCompraVenta.add(compra);
		panelCompraVenta.add(venta);

		// SE AÑADE PANEL COMPRA VENTA A LA VENTANA EN POSICION NORTE
		ventana.add(panelCompraVenta, BorderLayout.NORTH);

		// CREACION DE PANEL PRINCIPAL QUE VA A SER DIVIDIDO
		JPanel panelPrincipal = new JPanel(new BorderLayout());

		// CREACION DE PANEL SUPERIOR ADAPTABLE (PANEL FILTRO)
		JPanel buscador = new JPanel(new GridLayout(2, 3, 10, 10));
		buscador.setBackground(new Color(152, 251, 152)); // Azul claro en RGB
		JLabel vacio = new JLabel();
		JButton filtrar = new JButton("FILTRAR");
		JLabel vacio2 = new JLabel();

		// Cambiar el botón de precio por un JComboBox de rangos de precios
		String[] rangosPrecio = { "Precio", "0-15", "15-30", "30-50", "50-100", "100-200", "200-500", "500-1000",
				"1000-5000", "5000+" };
		JComboBox<String> comboPrecio = new JComboBox<>(rangosPrecio);
		comboPrecio.setFont(new Font("Courier", Font.BOLD, 15));

		// Cambiar el JComboBox de número por uno de tallas
		String[] tallas = { "Talla", "XS", "S", "M", "L", "XL", "XXL" };
		JComboBox<String> comboTalla = new JComboBox<>(tallas);
		comboTalla.setFont(new Font("Courier", Font.BOLD, 15));

		// Crear un JComboBox para seleccionar las prendas
		BaseDatosRopa baseDatosRopa = new BaseDatosRopa();
		ropaList = baseDatosRopa.getRopaList(); // Guardamos la lista original
		String[] nombresRopa = new String[ropaList.size() + 1];
		nombresRopa[0] = "Prendas"; // Opción "No seleccionado" para prendas
		for (int i = 0; i < ropaList.size(); i++) {
			nombresRopa[i + 1] = ropaList.get(i).getNombre(); // Rellenar el JComboBox con nombres de prendas
		}
		JComboBox<String> comboPrendas = new JComboBox<>(nombresRopa);
		comboPrendas.setFont(new Font("Courier", Font.BOLD, 15));

		buscador.add(vacio);
		buscador.add(filtrar);
		buscador.add(vacio2);
		buscador.add(comboPrecio); // Añadir JComboBox de precios
		buscador.add(comboTalla); // Añadir JComboBox de tallas
		buscador.add(comboPrendas); // Añadir JComboBox para seleccionar prendas

		// PANEL DONDE APARECEN LOS ARTICULOS
		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(Color.WHITE); // Color del panel inferior
		panelInferior.setLayout(new BorderLayout()); // Usar BorderLayout para añadir artículos y botón

		// Crear un modelo para el carrito
		modeloCarrito = new DefaultListModel<>(); // Inicializa el modelo del carrito

		// Crear un JPanel para mostrar la ropa
		listaArticulosPanel = new JPanel();
		listaArticulosPanel.setLayout(new BoxLayout(listaArticulosPanel, BoxLayout.Y_AXIS)); // Usar BoxLayout para la
																								// lista

		// Cargar todos los artículos inicialmente
		cargarArticulos(ropaList);

		JScrollPane scrollPaneArticulos = new JScrollPane(listaArticulosPanel); // Añadir scroll si hay muchos artículos
		panelInferior.add(scrollPaneArticulos, BorderLayout.CENTER); // Añadir la lista de artículos en el centro

		// Crear un JPanel para mostrar el carrito
		JPanel panelCarrito = new JPanel(new BorderLayout());
		panelCarrito.setBackground(new Color(152, 251, 152));
		JLabel carritoLabel = new JLabel("Carrito:");
		panelCarrito.add(carritoLabel, BorderLayout.NORTH);

		// Crear la lista del carrito
		listaCarrito = new JList<>(modeloCarrito);
		JScrollPane scrollPaneCarrito = new JScrollPane(listaCarrito);
		panelCarrito.add(scrollPaneCarrito, BorderLayout.CENTER);

		// Crear etiquetas para mostrar el total de artículos y el precio total
		totalArticulosLabel = new JLabel("Total artículos: 0");
		totalPrecioLabel = new JLabel("Precio total: 0");
		JPanel panelTotales = new JPanel(new GridLayout(2, 1));
		panelTotales.setBackground(new Color(152, 251, 152));
		panelTotales.add(totalArticulosLabel);
		panelTotales.add(totalPrecioLabel);
		panelCarrito.add(panelTotales, BorderLayout.SOUTH); // Añadir totales al carrito

		// Crear un JSplitPane para permitir la redimensión del panel superior e
		// inferior
		JSplitPane splitPaneVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buscador, panelInferior);
		splitPaneVertical.setDividerLocation(100); // Ajustar la posición inicial del divisor
		splitPaneVertical.setResizeWeight(0); // Peso de redimensionamiento

		// Añadir el panel dividido al panel principal
		panelPrincipal.add(splitPaneVertical, BorderLayout.CENTER); // Panel dividido

		// Crear un JSplitPane para colocar el panel derecho (carrito)
		JSplitPane splitPaneHorizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelPrincipal, panelCarrito);
		splitPaneHorizontal.setDividerLocation(525); // Ajustar la posición del divisor

		// Agregar el JSplitPane al marco (JFrame)
		ventana.getContentPane().add(splitPaneHorizontal);

		// Crear un JPanel para el botón de opciones
		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Usar FlowLayout para colocar el botón a la
		panelBoton.setBackground(new Color(152, 251, 152)); // izquierda
		JButton mostrarMenu = new JButton("Mostrar Opciones");
		JPopupMenu popupMenu = new JPopupMenu();

		// Crear los ActionListeners
		// Crear los ActionListeners
		BotonesPrincipal popupListener = new BotonesPrincipal(this); // Cambié el nombre de la clase a BotonesPrincipal

		// Añadir opciones al JPopupMenu
		JMenuItem opcion1 = new JMenuItem("Cuenta");
		opcion1.addActionListener(e -> abrirVentanaCuenta()); // Abrir la ventana de cuenta

		// Modificar el ActionListener para cerrar sesión
		JMenuItem opcion2 = new JMenuItem("Cerrar Sesion");
		opcion2.addActionListener(e -> {
			ventana.dispose(); // Cerrar la ventana principal
			new VentanaRegistro(); // Abrir la ventana de registro
		});

		JMenuItem opcion3 = new JMenuItem("Pedidos"); // Opción para mostrar pedidos
		opcion3.addActionListener(e -> mostrarPedidos());

		JMenuItem opcion4 = new JMenuItem("Ayuda");
		opcion4.addActionListener(e -> {
			new Asistencia(); // Abrir la ventana de registro
		});
		popupMenu.add(opcion1);
		popupMenu.add(opcion2);
		popupMenu.add(opcion3); // Añadir opción para mostrar pedidos// Añadir opción para mostrar pedidos
		popupMenu.add(opcion4);

		mostrarMenu.addActionListener(e -> popupMenu.show(mostrarMenu, 0, mostrarMenu.getHeight())); // Acción para
																										// mostrar el
																										// menú
		panelBoton.add(mostrarMenu);

		// Añadir el panel del botón al fondo del panel inferior
		panelInferior.add(panelBoton, BorderLayout.SOUTH); // Añadir el panel del botón al fondo del panel inferior

		// Añadir acción para el botón "VENTA"
		venta.addActionListener(e -> new VentanaVenta()); // Acción para abrir la ventana de venta

		// Añadir acción para el botón "COMPRA"
		compra.addActionListener(e -> crearPedido()); // Crear un pedido al pulsar el botón "COMPRA"

		// Añadir acción para el botón "FILTRAR"
		filtrar.addActionListener(e -> filtrarArticulos(comboPrecio, comboTalla, comboPrendas));

		// Hacer visible la ventana
		ventana.setVisible(true);
	}

	// Método para abrir la ventana de pedidos
	private void mostrarPedidos() {
		System.out.println("Mostrando pedidos para: " + nombreUsuario);
		for (Pedido pedido : listaPedidos) {
			System.out.println(pedido); // Suponiendo que Pedido tiene un método toString
		}
		new VentanaPedidos(nombreUsuario); // Crear y mostrar la ventana de pedidos
	}

	public void abrirVentanaRegistro() {
		new VentanaRegistro(); // Abre la ventana de registro
	}

	public void agregarAlCarrito(Ropa ropa) {
		if (ropa.getCantidad() > 0) { // Verifica si hay stock disponible
			modeloCarrito.addElement(ropa); // Agregar ropa al modelo del carrito
			ropa.setCantidad(ropa.getCantidad() - 1); // Resta 1 al stock de la prenda
			actualizarStockEnCSV(ropa); // Actualiza el stock en el CSV
			actualizarTotales(); // Actualizar totales al agregar ropa al carrito
			JOptionPane.showMessageDialog(ventana, ropa.getNombre() + " ha sido añadido al carrito."); // Mensaje de
																										// confirmación
		} else {
			JOptionPane.showMessageDialog(ventana, "No hay stock disponible para " + ropa.getNombre() + "."); // Mensaje
																												// de
																												// error
		}
	}

	private void actualizarStockEnCSV(Ropa prenda) {
		String filePath = "productos.csv"; // Ruta del archivo CSV
		File inputFile = new File(filePath);
		File tempFile = new File("temp_ropa.csv"); // Archivo temporal

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

			String line;
			while ((line = reader.readLine()) != null) {
				String[] valores = line.split(";"); // Suponiendo que el separador es un punto y coma

				// Comprueba si la línea corresponde a la prenda que se está actualizando
				if (valores[0].equals(prenda.getNombre()) && valores[1].equals(prenda.getMarca())
						&& valores[2].equals(prenda.getTalla())) {
					int stockActual = Integer.parseInt(valores[3]); // Stock actual
					if (stockActual > 1) {
						// Si el stock es mayor que 1, escribe la línea con el stock actualizado
						writer.write(valores[0] + ";" + valores[1] + ";" + valores[2] + ";" + (stockActual - 1) + ";"
								+ valores[4] + ";" + valores[5]);
					} // Si el stock es 1, no se escribe la línea (se eliminará)
				} else {
					// Si no es la prenda que se está actualizando, simplemente se copia la línea
					writer.write(line);
				}
				writer.newLine(); // Nueva línea después de cada prenda
			}

		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(ventana, "Error al actualizar el archivo CSV.");
		}

		// Renombrar el archivo temporal al original
		inputFile.delete(); // Elimina el archivo original
		tempFile.renameTo(inputFile); // Renombra el temporal
	}

	// Método para abrir la ventana de la cuenta
	public void abrirVentanaCuenta() {
		new VentanaCuenta(nombreUsuario); // Abre la ventana de la cuenta pasando el nombre del usuario
	}

	// Método para cargar artículos en el panel
	private void cargarArticulos(List<Ropa> lista) {
		listaArticulosPanel.removeAll(); // Limpiar el panel de artículos existente
		for (Ropa ropa : lista) {
			// Crear un panel para cada artículo
			JPanel articuloPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			articuloPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Añadir un borde para separar los
																					// artículos

			// Mostrar el nombre, talla, precio y marca
			JLabel nombreLabel = new JLabel(ropa.getNombre() + " - Talla: " + ropa.getTalla() + " - Precio: "
					+ ropa.getPrecio() + " - Marca: " + ropa.getMarca());
			JButton añadirCarrito = new JButton("Añadir al Carrito");
			añadirCarrito.setBackground(Color.CYAN); // Establece el color de fondo a cyan

			// Añadir acción para el botón "Añadir al Carrito"
			añadirCarrito.addActionListener(e -> {
				// Verifica si hay stock disponible antes de añadir al carrito
				if (ropa.getCantidad() > 0) {
					modeloCarrito.addElement(ropa); // Agregar el artículo al modelo del carrito
					ropa.setCantidad(ropa.getCantidad() - 1); // Resta 1 al stock de la prenda
					actualizarTotales(); // Actualizar totales al agregar ropa al carrito
					JOptionPane.showMessageDialog(ventana, ropa.getNombre() + " ha sido añadido al carrito."); // Mensaje
																												// de
																												// confirmación
				} else {
					JOptionPane.showMessageDialog(ventana, "No hay stock disponible para " + ropa.getNombre() + "."); // Mensaje
																														// de
																														// error
				}
			});

			articuloPanel.add(nombreLabel);
			articuloPanel.add(Box.createHorizontalGlue()); // Añadir un espacio flexible para empujar el botón a la
															// derecha
			articuloPanel.add(añadirCarrito); // Colocar el botón a la derecha
			listaArticulosPanel.add(articuloPanel); // Añadir el panel del artículo a la lista
		}
		listaArticulosPanel.revalidate(); // Actualizar el panel
		listaArticulosPanel.repaint(); // Repaint para mostrar los cambios
	}

	//Método recursivo para calcular el precio total
	public static double precioTotal(DefaultListModel<Ropa> carrito, int indice) {
		//Caso base: si llegamos al final de la lista, el precio total es 0
		if(indice ==  carrito.size()) {
			return 0;
		}
		
		//Sino, suma el precio actual y avanza en la lista
		return carrito.get(indice).getPrecio() + precioTotal(carrito, indice+1);	
	}
	
	
	// Método para actualizar totales
	private void actualizarTotales() {
		totalArticulosLabel.setText("Total artículos: " + modeloCarrito.getSize()); // Actualizar el total de artículos
		
		double precioTotal=precioTotal(modeloCarrito, 0);
		
		totalPrecioLabel.setText("Precio total: " + precioTotal); // Actualizar el precio total
	}

	// Método para crear un pedido
	// Método para crear un pedido
	private void crearPedido() {
		List<Ropa> prendasCompradas = new ArrayList<>();
		for (int i = 0; i < modeloCarrito.getSize(); i++) {
			prendasCompradas.add(modeloCarrito.getElementAt(i)); // Obtener las prendas del carrito
		}

		if (!prendasCompradas.isEmpty()) {
			// Crear un nuevo pedido
			int nuevoId = obtenerSiguienteId(); // Obtenemos el siguiente ID
			Usuario usuarioActual = new Usuario(nombreUsuario); // Crear un objeto Usuario con el nombre actual

			// Crear un nuevo pedido con las prendas compradas
			Pedido nuevoPedido = new Pedido(nuevoId, usuarioActual, prendasCompradas);
			listaPedidos.add(nuevoPedido); // Añadir el pedido a la lista

			// Guardar cada prenda comprada en el CSV
			for (Ropa prenda : prendasCompradas) {
				guardarPrendaEnCSV(nuevoId, usuarioActual.getNombre(), prenda);
				// Actualizar la cantidad de la prenda en el CSV
				BaseDatosRopa baseDatosRopa = new BaseDatosRopa();
				baseDatosRopa.actualizarCantidadEnCSV(prenda); // Aquí se restará el stock y se eliminará si llega a 0
			}

			// Mostrar los pedidos en una nueva ventana
			mostrarPedidos();

			modeloCarrito.clear(); // Limpiar el carrito después de realizar el pedido
			actualizarTotales(); // Actualizar los totales después de vaciar el carrito
		} else {
			JOptionPane.showMessageDialog(ventana, "El carrito está vacío.");
		}
	}

	private int obtenerSiguienteId() {
		String filePath = "pedidos.csv"; // Ruta del archivo CSV
		int maxId = 0; // Variable para almacenar el ID máximo encontrado

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] valores = line.split(";"); // Suponiendo que el separador es un punto y coma
				int id = Integer.parseInt(valores[0]); // El primer valor es el ID
				if (id > maxId) {
					maxId = id; // Actualizar el ID máximo
				}
			}
		} catch (IOException e) { 
			e.printStackTrace();
			JOptionPane.showMessageDialog(ventana, "Error al leer el archivo CSV para obtener el ID.");
		}

		return maxId + 1; // Retornar el siguiente ID
	}

	// Método para guardar el pedido en un archivo CSV
	// Método para guardar el pedido en un archivo CSV
	private void guardarPrendaEnCSV(int id, String nombreUsuario, Ropa prenda) {
		String filePath = "pedidos.csv"; // Ruta del archivo CSV
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
			// Escribir los datos de la prenda en formato CSV
			writer.write(id + ";" + nombreUsuario + ";" + prenda.getNombre() + ";" + prenda.getTalla() + ";"
					+ prenda.getEstado() + ";" + prenda.getCantidad() + ";" + prenda.getPrecio() + ";"
					+ prenda.getMarca() + ";");
			writer.write("\n"); // Nueva línea después de cada prenda
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(ventana, "Error al guardar el pedido en el archivo CSV.");
		} 
	}
	
// BASE DE DATOS sql -------------------------------------------------------------------------------------------------
	
	private void guardarPrendaEnBD (int id, String nombreUsuario, Ropa prenda) {
		//Se abre la conexión y se obtiene el Statement
				try (Connection con = DriverManager.getConnection("jdbc:sqlite:resources/db/pedidos.db");
				     Statement stmt = con.createStatement()) {
					//Se define la plantilla de la sentencia SQL
					String sql = "INSERT INTO Productos (Nombre, Marca, Talla, Cantidad, Precio, Estado) VALUES ('%s', '%s', '%s','%i', '%d '%s');";
					
					stmt.executeUpdate(String.format(sql, prenda.getNombre(), prenda.getTalla(), prenda.getEstado(), 
							prenda.getCantidad(), prenda.getPrecio(), prenda.getMarca())); {					
							
						
					}			
				} catch (Exception ex) { 
					System.err.format("\n* Error al insertar datos de la BD de pedidos: %s", ex.getMessage());
					ex.printStackTrace();						
				}	
	}
	
// ----------------------------------------------------------------------------------------------------------------------

	// Método para filtrar artículos
	// Método para filtrar artículos
	// Método para filtrar artículos
	private void filtrarArticulos(JComboBox<String> comboPrecio, JComboBox<String> comboTalla,
			JComboBox<String> comboPrendas) {
		String precioSeleccionado = (String) comboPrecio.getSelectedItem();
		String tallaSeleccionada = (String) comboTalla.getSelectedItem();
		String prendaSeleccionada = (String) comboPrendas.getSelectedItem();

		List<Ropa> ropaFiltrada = new ArrayList<>();

		// Filtrar prendas según la selección
		for (Ropa ropa : ropaList) {
			boolean coincide = true;

			// Filtrar por precio solo si no está en la opción "Precio"
			if (!precioSeleccionado.equals("Precio")) {
				String[] rangos = precioSeleccionado.split("-");
				int precioMin = Integer.parseInt(rangos[0]);
				int precioMax = rangos.length > 1 ? Integer.parseInt(rangos[1]) : Integer.MAX_VALUE;

				if (ropa.getPrecio() < precioMin || ropa.getPrecio() > precioMax) {
					coincide = false;
				}
			}

			// Filtrar por talla solo si no está en la opción "Talla"
			if (!tallaSeleccionada.equals("Talla") && !ropa.getTalla().equals(tallaSeleccionada)) {
				coincide = false;
			}

			// Filtrar por prenda solo si no está en la opción "Prendas"
			if (!prendaSeleccionada.equals("Prendas") && !ropa.getNombre().equals(prendaSeleccionada)) {
				coincide = false;
			}

			if (coincide) {
				ropaFiltrada.add(ropa); // Añadir a la lista filtrada
			}
		}

		cargarArticulos(ropaFiltrada); // Cargar los artículos filtrados
	}

	public static void main(String[] args) {
		// Se necesita un nombre de usuario para inicializar la ventana principal
		String nombreUsuario = "usuarioPrueba"; // Esto se podría obtener de otro lado
		new VentanaPrincipal(nombreUsuario);
	}

}
