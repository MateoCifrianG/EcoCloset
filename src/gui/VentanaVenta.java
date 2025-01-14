package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import domain.Ropa;
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

        añadirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los datos del formulario
                String prenda = (String) prendaComboBox.getSelectedItem();
                String marca = marcaField.getText();
                String talla = (String) tallaComboBox.getSelectedItem();
                int cantidad = (Integer) cantidadComboBox.getSelectedItem(); // Obtener cantidad del JComboBox
                double precio = Double.parseDouble(precioField.getText());
                String estado = (String) estadoComboBox.getSelectedItem();

                // Crear objeto Ropa
                Ropa nuevaRopa = new Ropa(prenda, marca, talla, cantidad, precio, estado);

                // Guardar en el CSV
                guardarEnCSV(nuevaRopa);

                // Mostrar mensaje de confirmación
                JOptionPane.showMessageDialog(frame, "Ropa añadida correctamente.");
                frame.dispose();
                // Limpiar los campos
                marcaField.setText("");
                precioField.setText("");
                prendaComboBox.setSelectedIndex(0); // Restablecer a la primera opción
                tallaComboBox.setSelectedIndex(0); // Restablecer a la primera opción
                cantidadComboBox.setSelectedIndex(0); // Restablecer a la primera opción
                estadoComboBox.setSelectedIndex(0); // Restablecer a la primera opción
            }
        });

        frame.setVisible(true);
    }

    private void guardarEnCSV(Ropa ropa) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.csv", true))) {
            writer.write(ropa.getNombre() + ";" + ropa.getMarca() + ";" + ropa.getTalla() + ";" + ropa.getCantidad() + ";" + ropa.getPrecio() + ";" + ropa.getEstado() );
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error al guardar en CSV.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
