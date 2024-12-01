package gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import domain.Pedido;
import domain.Ropa;
import domain.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Pedidos {
    private JFrame ventana;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JTextField campoBusqueda;
    private List<Pedido> listaPedidos;

    public Pedidos() {
        // CREACIÓN DE LA VENTANA DE PEDIDOS
        ventana = new JFrame("Administrador de Pedidos");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600);
        ventana.setLayout(new BorderLayout());

        // Panel superior con campo de búsqueda y botón de eliminar
        JPanel panelSuperior = new JPanel(new BorderLayout());
        campoBusqueda = new JTextField();
        JButton botonBuscar = new JButton("Buscar");
        JButton botonEliminar = new JButton("Eliminar");

        JPanel panelBusqueda = new JPanel(new BorderLayout());
        panelBusqueda.add(new JLabel("Buscar Pedido: "), BorderLayout.WEST);
        panelBusqueda.add(campoBusqueda, BorderLayout.CENTER);
        panelBusqueda.add(botonBuscar, BorderLayout.EAST);

        panelSuperior.add(panelBusqueda, BorderLayout.NORTH);
        panelSuperior.add(botonEliminar, BorderLayout.SOUTH);

        // Crear la tabla para mostrar los pedidos
        String[] columnas = {"ID Pedido", "Usuario", "Prendas", "Precio Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Solo la columna "Prendas" será editable
            }
        };

        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setRowHeight(60);
        JScrollPane scrollTabla = new JScrollPane(tablaPedidos);

        // Cargar los pedidos desde el archivo CSV
        listaPedidos = cargarPedidos();
        actualizarTabla(listaPedidos);

        // Crear el botón para volver al menú
        JButton botonVolver = new JButton("Volver al Menú Administrador");
        botonVolver.addActionListener(e -> {
            ventana.dispose();
            new MenuAdministrador(); // Cerrar la ventana actual
        });

        // Añadir componentes a la ventana
        ventana.add(panelSuperior, BorderLayout.NORTH);
        ventana.add(scrollTabla, BorderLayout.CENTER);
        ventana.add(botonVolver, BorderLayout.SOUTH);

        // Acción para buscar pedidos
        botonBuscar.addActionListener(e -> buscarPedido(campoBusqueda.getText()));

        // Acción para eliminar el pedido seleccionado
        botonEliminar.addActionListener(e -> eliminarPedidoSeleccionado());

        // Mostrar la ventana
        ventana.setVisible(true);
    }
    private Ropa buscarPrendaEnCSV(Ropa prenda) {
        String filePath = "productos.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] valores = line.split(";");
                if (valores.length < 6) continue; // Asegúrate de que hay suficientes valores

                String nombre = valores[0];
                String marca = valores[1];
                String talla = valores[2];
                int cantidad = Integer.parseInt(valores[3]);
                double precio = Double.parseDouble(valores[4]);
                String estado = valores[5];

                if (nombre.equals(prenda.getNombre()) && 
                    marca.equals(prenda.getMarca()) && 
                    talla.equals(prenda.getTalla()) && 
                    precio == prenda.getPrecio()) {
                    // Si se encuentra la prenda, devolverla
                    return new Ropa(nombre, marca, talla, cantidad, precio, estado);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(ventana, "Error al leer el archivo de productos.");
        }
        return null; // No se encontró la prenda
    }


    
    private List<Pedido> cargarPedidos() {
        List<Pedido> listaPedidos = new ArrayList<>();
        String filePath = "pedidos.csv"; // Ruta del archivo CSV
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] valores = line.split(";"); // Suponiendo que el separador es punto y coma
                if (valores.length < 8) {
                    System.err.println("Línea inválida en el archivo: " + line);
                    continue; // Saltar líneas inválidas
                }

                int id = Integer.parseInt(valores[0]);
                String usuario = valores[1];
                String nombrePrenda = valores[2];
                String talla = valores[3];
                String estado = valores[4];
                int cantidad = Integer.parseInt(valores[5]);
                double precio = Double.parseDouble(valores[6]);
                String marca = valores[7];

                Ropa prenda = new Ropa(nombrePrenda, marca, talla, cantidad, precio, estado);
                // Buscar si el pedido ya existe
                Pedido pedidoExistente = listaPedidos.stream()
                        .filter(p -> p.getId() == id)
                        .findFirst()
                        .orElse(null);

                if (pedidoExistente != null) {
                    pedidoExistente.getPrendas().add(prenda); // Añadir la prenda al pedido existente
                } else {
                    List<Ropa> prendas = new ArrayList<>();
                    prendas.add(prenda);
                    Pedido nuevoPedido = new Pedido(id, new Usuario(usuario), prendas); // Crear un nuevo pedido
                    listaPedidos.add(nuevoPedido); // Añadir a la lista
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Muestra la traza del error en la consola
            JOptionPane.showMessageDialog(ventana, "Error al leer el archivo de pedidos.");
        }

        return listaPedidos; // Retornar la lista de pedidos
    }

    private void actualizarTabla(List<Pedido> pedidos) {
        modeloTabla.setRowCount(0);
        for (Pedido pedido : pedidos) {
            modeloTabla.addRow(new Object[]{
                pedido.getId(),
                pedido.getUsuario(),
                obtenerTextoPrendas(pedido.getPrendas()),
                String.format("$%.2f", calcularPrecioTotal(pedido))
            });
        }
    }

    private String obtenerTextoPrendas(List<Ropa> prendas) {
        StringBuilder texto = new StringBuilder();
        for (Ropa prenda : prendas) {
            texto.append(prenda.toString()).append("\n");
        }
        return texto.toString();
    }

    private double calcularPrecioTotal(Pedido pedido) {
        double total = 0.0;
        for (Ropa prenda : pedido.getPrendas()) {
            total += prenda.getPrecio();
        }
        return total;
    }
//solucion
    private void buscarPedido(String busqueda) {
        List<Pedido> pedidosFiltrados = new ArrayList<>();
        for (Pedido pedido : listaPedidos) {
            String usuario = (pedido.getUsuario() == null) ? "" : pedido.getUsuario().toString(); // Aseguramos que sea un String
            
            if (String.valueOf(pedido.getId()).contains(busqueda) || usuario.toLowerCase().contains(busqueda.toLowerCase())) {
                pedidosFiltrados.add(pedido);
            }
        }
        actualizarTabla(pedidosFiltrados);
    }


    private void eliminarPedidoSeleccionado() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idPedido = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            Pedido pedidoEliminado = listaPedidos.stream()
                .filter(p -> p.getId() == idPedido)
                .findFirst()
                .orElse(null);

            int confirmacion = JOptionPane.showConfirmDialog(ventana, 
                "¿Estás seguro de eliminar el pedido ID: " + idPedido + "?", 
                "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                // Guardar prendas en el archivo CSV de productos antes de eliminar
                for (Ropa prenda : pedidoEliminado.getPrendas()) {
                    guardarPrendaEnCSV(prenda);
                }

                listaPedidos.removeIf(p -> p.getId() == idPedido);
                modeloTabla.removeRow(filaSeleccionada);

                // Guardar los cambios en el archivo CSV de pedidos
                guardarPedidosEnCSV("pedidos.csv");
            }
        } else {
            JOptionPane.showMessageDialog(ventana, "Por favor, selecciona un pedido para eliminar.");
        }
    }


    private void guardarPrendaEnCSV(Ropa prenda) {
        String filePath = "productos.csv"; 
        Ropa prendaExistente = buscarPrendaEnCSV(prenda);

        if (prendaExistente != null) {
            // Actualizar la cantidad de la prenda existente
            actualizarCantidadPrendaInCSV(prendaExistente, prendaExistente.getCantidad() + 1);
        } else {
            // Agregar la nueva prenda
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
                writer.println(prenda.getNombre() + ";" +
                               prenda.getMarca() + ";" +
                               prenda.getTalla() + ";" +
                               1 + ";" + // Nueva prenda, cantidad inicial 1
                               prenda.getPrecio() + ";" +
                               prenda.getEstado() + ";");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(ventana, "Error al guardar la prenda en el archivo CSV: " + e.getMessage());
            }
        }
    }

    private void guardarPedidosEnCSV(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Pedido pedido : listaPedidos) {
                for (Ropa prenda : pedido.getPrendas()) {
                    writer.println(pedido.getId() + ";" + pedido.getUsuario() + ";" + 
                                   prenda.getNombre() + ";" + prenda.getTalla() + ";" + 
                                   prenda.getEstado() + ";" + prenda.getCantidad() + ";" + 
                                   prenda.getPrecio() + ";" + prenda.getMarca());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(ventana, "Error al guardar el archivo CSV: " + e.getMessage());
        }
    }private void actualizarCantidadPrendaInCSV(Ropa prendaExistente, int nuevaCantidad) {
        String filePath = "productos.csv";
        List<String> lineas = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] valores = line.split(";");
                if (valores.length < 6) {
                    lineas.add(line); // Agregar línea inválida
                    continue;
                }

                // Actualizar la línea si coincide con la prenda
                if (valores[0].equals(prendaExistente.getNombre()) && 
                    valores[1].equals(prendaExistente.getMarca()) && 
                    valores[2].equals(prendaExistente.getTalla()) &&
                    Double.parseDouble(valores[4]) == prendaExistente.getPrecio()) {
                    // Actualizar cantidad
                    lineas.add(valores[0] + ";" + 
                                valores[1] + ";" + 
                                valores[2] + ";" + 
                                nuevaCantidad + ";" + 
                                valores[4] + ";" + 
                                valores[5]);
                } else {
                    lineas.add(line); // Agregar línea sin cambios
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(ventana, "Error al leer el archivo de productos.");
        }

        // Escribir las líneas actualizadas de vuelta al archivo
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (String linea : lineas) {
                writer.println(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(ventana, "Error al guardar el archivo CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Pedidos::new);
    }
}
