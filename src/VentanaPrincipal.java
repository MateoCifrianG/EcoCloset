import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import bd.BaseDatosRopa;
import domain.Pedido;
import domain.Ropa;

public class VentanaPrincipal {
	private JFrame ventana;
	private DefaultListModel<Ropa> modeloCarrito;
	private JList<Ropa> listaCarrito; // Lista para mostrar los artículos en el carrito
	private List<Ropa> ropaList;
	private String nombreUsuario;
	private List<Pedido> listaPedidos; // Almacena los pedidos del usuario
	private JPanel listaArticulosPanel;
	
	public VentanaPrincipal(String nombreUsuario) { 
		//creamos la ventana
		ventana = new JFrame("ECOCLOSET");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800, 600);
		
		//Creamos el panel superior y los botones de compra venta.
			
			//Compra
		JPanel panelCompraVenta = new JPanel(new GridLayout(1, 2, 10, 10));
		JButton compra = new JButton("COMPRA");
		compra.setFont(new Font("Courier", Font.BOLD, 20)); 
		
			//Venta
		JButton venta = new JButton("VENTA");
		venta.setFont(new Font("Courier", Font.BOLD, 20));
		panelCompraVenta.add(compra);
		panelCompraVenta.add(venta);
		
		//Añadimos el panel de compra venta a la ventana arriba de todo (norte)
		ventana.add(panelCompraVenta, BorderLayout.NORTH);
		
		//Creamos el panel princial
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		
		//creamos el panel superior
		JPanel buscador = new JPanel(new GridLayout(2, 3, 10, 10));
        buscador.setBackground(new Color(152, 251, 152)); // Azul clarito en rgb
		JLabel vacio = new JLabel();
		JButton filtrar = new JButton("FILTRAR");
		JLabel vacio2 = new JLabel();
		
		// Cambiamos el botón de precio por un JComboBox de rangos de precios
		String[] rangosPrecio = { "Precio", "0-15", "15-30", "30-50", "50-100", "100-200", "200-500", "500-1000", "1000-5000", "5000+" };
		JComboBox<String> comboPrecio = new JComboBox<>(rangosPrecio);
		comboPrecio.setFont(new Font("Courier", Font.BOLD, 15));
			
		// Cambiamos el JComboBox de número por uno de tallas
		String[] tallas = { "Talla", "XS", "S", "M", "L", "XL", "XXL" };
		JComboBox<String> comboTalla = new JComboBox<>(tallas);
		comboTalla.setFont(new Font("Courier", Font.BOLD, 15));
		
		BaseDatosRopa baseDatosRopa = new BaseDatosRopa();
		ropaList = baseDatosRopa.getRopaList(); // Guardamos la lista original
		String[] nombresRopa = new String[ropaList.size() + 1];
		nombresRopa[0] = "Prendas"; // Opción "No seleccionado" para prendas
		for (int i = 0; i < ropaList.size(); i++) {
			nombresRopa[i + 1] = ropaList.get(i).getNombre(); // Rellenar el JComboBox con nombres de prendas
		}
		JComboBox<String> comboPrendas = new JComboBox<>(nombresRopa);
		comboPrendas.setFont(new Font("Courier", Font.BOLD, 15));
		
		
		//Añadimos todo
		buscador.add(vacio);
		buscador.add(filtrar);
		buscador.add(vacio2);
		buscador.add(comboPrecio); // Añadir JComboBox de precios
		buscador.add(comboTalla); 
		buscador.add(comboPrendas);
		
		
		//Panel donde aparecen los artículos
		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(Color.WHITE); //Color del panel inferior
		panelInferior.setLayout(new BorderLayout()); // Usar BorderLayout para añadir artículos y botón
		
		//JPanel para el botón de opciones
		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT)); //Uso FlowLayout para colocar el botón a la izquierda
		panelBoton.setBackground(new Color(152,251,152)); //Color verde
		JButton mostrarMenu = new JButton("Mostrar Opciones");
		JPopupMenu popupMenu = new JPopupMenu();
		
		//Añadir opciones al JPopupMenu
		JMenuItem opcion1=new JMenuItem("Cuenta");
		opcion1.addActionListener(e -> new VentanaCuenta(nombreUsuario)); // se abre la VentanaCuenta
		
		JMenuItem opcion2=new JMenuItem("Cerrar Sesión");
		opcion2.addActionListener(e -> {
			ventana.dispose(); //Cerrar la ventana principal
			new VentanaRegistro(); //Abrir la ventana de registro
		});
		
		
		JMenuItem opcion3=new JMenuItem("Pedidos");
		//Falta el actionListener
		
		JMenuItem opcion4=new JMenuItem("Ayuda");
		//Falta el ActionListener
		
		popupMenu.add(opcion1);
		popupMenu.add(opcion2);
		popupMenu.add(opcion3);
		popupMenu.add(opcion4);
		
		// Acción para mostrar el menú
		mostrarMenu.addActionListener(e -> popupMenu.show(mostrarMenu, 0, mostrarMenu.getHeight()));
		panelBoton.add(mostrarMenu);
		
		
		panelInferior.add(panelBoton, BorderLayout.SOUTH); //Añadir el panel del botón al fondo del panel inferior
		ventana.add(panelInferior);
		
		// Añadir acción para el botón "COMPRA" --> hacer el método
		compra.addActionListener(e -> crearPedido()); // Crear un pedido al pulsar el botón "COMPRA"
			
		// Añadir acción para el botón "FILTRAR" --> hacer el método
		filtrar.addActionListener(e -> filtrarArticulos(comboPrecio, comboTalla, comboPrendas));
		
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
	        modeloCarrito.addElement(ropa); 
	        ropa.setCantidad(ropa.getCantidad() - 1); 	        
	        actualizarStockEnCSV(ropa); // Actualiza el stock en el CSV --> crear el método
	        actualizarTotales(); 
	        JOptionPane.showMessageDialog(ventana, ropa.getNombre() + " ha sido añadido al carrito."); 
	    } else {
	        JOptionPane.showMessageDialog(ventana, "No hay stock disponible para " + ropa.getNombre() + "."); 
	    }
	}
	
	private void actualizarStockEnCSV(Ropa prenda) {
	    String filePath = "productos.csv"; 
	    File inputFile = new File(filePath);
	    File tempFile = new File("temp_ropa.csv");

	    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] valores = line.split(";"); // Suponiendo que el separador es un punto y coma

	            // comprueba que la línea corresponde a la prenda que se está actualizando
	            if (valores[0].equals(prenda.getNombre()) && valores[1].equals(prenda.getMarca())
	                && valores[2].equals(prenda.getTalla())) {
	                int stockActual = Integer.parseInt(valores[3]); // Stock actual
	                if (stockActual > 1) {
	                    // Ssi el stock es mayor que 1, escribe la línea con el stock actualizado
	                    writer.write(valores[0] +	 ";" + valores[1] + ";" + valores[2] + ";"
	                        + (stockActual - 1) + ";" + valores[4] + ";" + valores[5]);
	                } 
	            } else {
	       
	                writer.write(line);
	            }
	            writer.newLine(); 
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
	private void abrirVentanaCuenta() {
		new VentanaCuenta(nombreUsuario); // Abre la ventana de la cuenta pasando el nombre del usuario
	}
	
	// Método para cargar artículos en el panel
	private void cargarArticulos(List<Ropa> lista) {
		listaArticulosPanel.removeAll(); // Limpiar el panel de artículos existente
		for (Ropa ropa : lista) {
			 // Crear un panel para cada artículo
				JPanel articuloPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				articuloPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Añadir un borde para separar los artículos
				
				// Mostrar el nombre, talla, precio y marca
				JLabel nombreLabel = new JLabel(ropa.getNombre() + " - Talla: " + ropa.getTalla() + " - Precio: "+ ropa.getPrecio() + " - Marca: " + ropa.getMarca());
				JButton añadirCarrito = new JButton("Añadir al Carrito");
				añadirCarrito.setBackground(Color.CYAN); // Establece el color de fondo a cyan
				
				// Añadir acción para el botón "Añadir al Carrito"
				añadirCarrito.addActionListener(e -> {
				// Verifica si hay stock disponible antes de añadir al carrito
				if (ropa.getCantidad() > 0) {
					modeloCarrito.addElement(ropa); 
					ropa.setCantidad(ropa.getCantidad() - 1); 
					actualizarTotales(); // Actualizar totales al agregar ropa al carrito --> hacer funcion
					JOptionPane.showMessageDialog(ventana, ropa.getNombre() + " ha sido añadido al carrito."); 
					} else {
					    JOptionPane.showMessageDialog(ventana, "No hay stock disponible para " + ropa.getNombre() + "."); 
					}
				});
				
				articuloPanel.add(nombreLabel);
				articuloPanel.add(Box.createHorizontalGlue()); 
				articuloPanel.add(añadirCarrito);
				listaArticulosPanel.add(articuloPanel); // Añadir el panel del artículo a la lista
		}
		
		listaArticulosPanel.revalidate(); 
		listaArticulosPanel.repaint(); //mostrar los cmabios
	}
	// Método para actualizar totales
			private void actualizarTotales() {
				totalArticulosLabel.setText("Total artículos: " + modeloCarrito.getSize()); // Actualizar el total de artículos
		
				// Calcular el precio total
				double precioTotal = 0;
				for (int i = 0; i < modeloCarrito.getSize(); i++) {
					precioTotal += modeloCarrito.getElementAt(i).getPrecio(); // Sumar el precio de cada artículo
				}
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
			            // Mostrar los pedidos en una nueva ventana
				        mostrarPedidos();
			        }
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
		String nombreUsuario = "usuarioPrueba"; // Esto se podría obtener de otro lado
		new VentanaPrincipal(nombreUsuario);
	}

}
