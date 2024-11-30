import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import domain.Pedido;

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
            sb.append("Usuario: ").append(pedido.getNombreUsuario()).append("\n");
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

        ventana.setVisible(true);

	}
	public static void main(String[] args) {
		

	}

}
