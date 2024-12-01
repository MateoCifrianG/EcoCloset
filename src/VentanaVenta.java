import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class VentanaVenta {
    private JFrame frame;

    public VentanaVenta() {
        frame = new JFrame("Añadir Ropa - Venta");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(7, 2));
        
        // Crear los campos de entrada
        String[] prendas = {"Camisa", "Pantalón", "Vestido", "Chaqueta", "Abrigo", "Falda", "Shorts", "Zapatos"};
        JComboBox<String> prendaComboBox = new JComboBox<>(prendas);
        
        JTextField marcaField = new JTextField();
        
        String[] tallas = {"XS", "S", "M", "L", "XL", "XXL"};
        JComboBox<String> tallaComboBox = new JComboBox<>(tallas);
        
        // Crear un JComboBox para la cantidad
        Integer[] cantidades = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Opciones de cantidad
        JComboBox<Integer> cantidadComboBox = new JComboBox<>(cantidades);
        
        JTextField precioField = new JTextField();

        String[] estados = {"Nuevo", "Usado"};
        JComboBox<String> estadoComboBox = new JComboBox<>(estados);
        
     // Crear etiquetas
        frame.add(new JLabel("Prenda:"));
        frame.add(prendaComboBox);
        frame.add(new JLabel("Marca:"));
        frame.add(marcaField);
        frame.add(new JLabel("Talla:"));
        frame.add(tallaComboBox);
        frame.add(new JLabel("Cantidad:"));
        frame.add(cantidadComboBox); // Cambiado a JComboBox
        frame.add(new JLabel("Precio:"));
        frame.add(precioField);
        frame.add(new JLabel("Estado:"));
        frame.add(estadoComboBox);
        
     // Botón de añadir
        JButton añadirButton = new JButton("Añadir");
        frame.add(añadirButton);

        
        
    }
}