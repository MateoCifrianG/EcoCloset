package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import domain.Pedido;
import domain.Ropa;
import domain.Usuario;

public class VentanaPedidos {
	public VentanaPedidos(String nombreUsuario) {
        // mostrar los pedidos de un usuario
        JFrame ventana = new JFrame("Mis Pedidos");
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setSize(600, 400);
        ventana.setLocationRelativeTo(null); // Centra la ventana
        ventana.getContentPane().setBackground(new Color(240, 248, 255)); // Fondo azul clarito
        
        // titulo
        JLabel titulo = new JLabel("Mis Pedidos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24)); // Cambia la fuente y tamaño
        titulo.setForeground(new Color(70, 130, 180)); // color del texto
        ventana.add(titulo, BorderLayout.NORTH);
         
        // cargar y mostrar los pedidos del usuario
        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false); // hacer que el área de texto no sea editable
        areaTexto.setBackground(Color.WHITE); // Fondo del JTextArea blanco
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 14)); // fuente del texto
        areaTexto.setLineWrap(true); // ajustar línea
        areaTexto.setWrapStyleWord(true); // justar palabras
        areaTexto.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1)); // borde del JTextArea
        
        // Definición de CHAT GPT del BoderFactory
//        BorderFactory es una clase en Swing, una biblioteca de Java para crear interfaces gráficas de usuario (GUIs). 
//        Se encuentra en el paquete javax.swing.border y se utiliza para crear objetos de tipo borde (Border), 
//        que pueden ser utilizados para decorar o delimitar los componentes gráficos de una aplicación
        
     // obtener pedidos del usuario y ordenar los pedidos por ID
        List<Pedido> listaPedidosUsuario = cargarPedidos(nombreUsuario);
        Collections.sort(listaPedidosUsuario, Comparator.comparingInt(Pedido::getId));

        // Mostrar los pedidos en el JTextArea
        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : listaPedidosUsuario) { 
            sb.append("ID: ").append(pedido.getId()).append("\n");

            sb.append("Usuario: ").append(pedido.getUsuario()).append("\n");
            sb.append("Prendas:\n"); 

            sb.append("Usuario: ").append(pedido.getUsuario().getNombre()).append("\n");
            sb.append("Prendas:\n");

            for (Ropa prenda : pedido.getPrendas()) {
                sb.append("  - ").append(prenda.toString()).append("\n");
            }
            sb.append("Precio Total: ").append(calcularPrecioTotal(pedido)).append("\n\n");
        } 

        areaTexto.setText(sb.toString()); // poner el texto en el área de texto
        
        // Añadir el área de texto a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237)), "Detalles del Pedido",  // Borde superior
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.DARK_GRAY)); // Estilo del borde

        ventana.add(scrollPane, BorderLayout.CENTER);
        
     // boton para cerrar la ventana
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(70, 130, 180)); // color del botón
        btnCerrar.setForeground(Color.WHITE); // color del texto del botón
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 16)); // fuente del botón
        btnCerrar.addActionListener(e -> ventana.dispose()); // acción del botón

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);
        ventana.add(panelBoton, BorderLayout.SOUTH);

        ventana.setVisible(true);

	}
	
	// Método para cargar pedidos del archivo CSV para el usuario actual
    private List<Pedido> cargarPedidos(String nombreUsuario) {
        List<Pedido> listaPedidosUsuario = new ArrayList<>();
        String filePath = "pedidos.csv"; // 
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Map<Integer, Pedido> pedidosMap = new HashMap<>(); 

            while ((line = reader.readLine()) != null) {
                String[] valores = line.split(";");  
                if (valores.length != 8) continue;
                
                int id = Integer.parseInt(valores[0]);
                String usuario = valores[1];
                String nombrePrenda = valores[2];
                String talla = valores[3];
                String estado = valores[4];
                int cantidad = Integer.parseInt(valores[5]);
                double precio = Double.parseDouble(valores[6]);
                String marca = valores[7];

                // Solo procesar los pedidos del usuario actual
                if (usuario.equals(nombreUsuario)) {
                    Ropa prenda = new Ropa(nombrePrenda, marca, talla, cantidad, precio, estado);
                    
                    // combinar pedidos por ID
                    pedidosMap.computeIfAbsent(id, k -> new Pedido(id, new Usuario(usuario), new ArrayList<>()))
                              .getPrendas().add(prenda); 
                }
            }

            // coger todos los pedidos combinados
            listaPedidosUsuario.addAll(pedidosMap.values());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de pedidos.");
        }

        return listaPedidosUsuario; 
    }

    // método para calcular el precio total de un pedido (
    private double calcularPrecioTotal(Pedido pedido) {
        double total = 0.0;
        for (Ropa prenda : pedido.getPrendas()) {
            total += prenda.getPrecio(); 
        }
        return total;
    }
}
