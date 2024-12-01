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
        
        String[] prendas = {"Camisa", "Pantalón", "Vestido", "Chaqueta", "Abrigo", "Falda", "Shorts", "Zapatos"};
        JComboBox<String> prendaComboBox = new JComboBox<>(prendas);
        
        JTextField marcaField = new JTextField();
        
        String[] tallas = {"XS", "S", "M", "L", "XL", "XXL"};
        JComboBox<String> tallaComboBox = new JComboBox<>(tallas);
    }
}