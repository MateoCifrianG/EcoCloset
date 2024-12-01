 import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class VentanaRegistro {

    private JFrame ventana;
    private JProgressBar barraProgreso;
    private JButton iniciarSesionButton;
    private JLabel mensaje;

    public VentanaRegistro() {
        // CREACIÓN DE LA VENTANA DE REGISTRO
        ventana = new JFrame("Registro");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(400, 400);
        ventana.setLayout(new GridBagLayout());

        // Crear los componentes de la ventana
        JLabel nombreLabel = new JLabel("Nombre: ");
        JTextField nombreField = new JTextField(20);
        JLabel contraseñaLabel = new JLabel("Contraseña: ");
        JPasswordField contraseñaField = new JPasswordField(20);
        iniciarSesionButton = new JButton("Iniciar Sesión");
        JButton registrarButton = new JButton("Registrar");

        // Barra de progreso
        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setStringPainted(true);
        barraProgreso.setVisible(false);

        // Crear un JLabel para mensajes
        mensaje = new JLabel(" ");
        mensaje.setHorizontalAlignment(SwingConstants.CENTER);

        // Crear un GridBagConstraints para posicionar los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Añadir los componentes creados a la ventana
        gbc.gridx = 0;
        gbc.gridy = 0;
        ventana.add(nombreLabel, gbc);
        gbc.gridx = 1;
        ventana.add(nombreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        ventana.add(contraseñaLabel, gbc);
        gbc.gridx = 1;
        ventana.add(contraseñaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        ventana.add(iniciarSesionButton, gbc);

        gbc.gridy = 3;
        ventana.add(registrarButton, gbc);

        gbc.gridy = 4;
        ventana.add(barraProgreso, gbc);

        gbc.gridy = 5;
        ventana.add(mensaje, gbc);
        
     // Crear un ActionListener para el botón de registrar
        registrarButton.addActionListener(e -> {
            // Abre el formulario de registro cuando se hace click en el botón de registrar
            new FormularioRegistro();  // Aquí se crea el formulario de registro
        });

        // Acción del botón
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesionButton.setEnabled(false); // Deshabilitar el botón mientras carga
                barraProgreso.setVisible(true); // Mostrar la barra de progreso
                verificarCredenciales(nombreField.getText(), new String(contraseñaField.getPassword())); // Verificar credenciales
            }
        });

        // Hacer visible la ventana
        ventana.setVisible(true);
    }

    private void verificarCredenciales(String nombre, String contraseña) {
        // Crear un hilo que simula la verificación
        Thread hilo = new Thread(() -> {
            try {
                // Verificar si el usuario es el administrador
                if (nombre.equals("admin") && contraseña.equals("admin")) {
                    // Si es admin, se abre el menú de administrador
                    SwingUtilities.invokeLater(() -> {
                        mensaje.setText("¡Acceso administrador exitoso!");
                        ventana.dispose(); // Cerrar la ventana actual
                        new MenuAdministrador(); // Abrir ventana del administrador
                    });
                } else if (Comprobador.verificarUsuario(nombre, contraseña)) { 
                    // Si no es admin pero las credenciales son correctas, se abre la ventana principal
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(20); 
                        barraProgreso.setValue(i);
                    }
                    SwingUtilities.invokeLater(() -> {
                        mensaje.setText("¡Inicio de sesión exitoso!");
                        abrirVentanaPrincipal(nombre); // Abrir ventana principal
                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        mensaje.setText("Usuario o contraseña incorrectos.");
                        barraProgreso.setVisible(false); // Ocultar barra
                        iniciarSesionButton.setEnabled(true); // Reactivar botón
                    });
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        hilo.start(); // Iniciar el hilo
    }

//p

    // Método para abrir la ventana principal
    public void abrirVentanaPrincipal(String nombreUsuario) {
        ventana.dispose(); // Cerrar la ventana actual
        JOptionPane.showMessageDialog(null, "Bienvenido, " + nombreUsuario + "!");
        new VentanaPrincipal(nombreUsuario);
       
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaRegistro::new);
    }
}
