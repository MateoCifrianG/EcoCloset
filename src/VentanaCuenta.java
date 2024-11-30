import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaCuenta {

	private JFrame ventanaCuenta;
	private String nombreUsuario;
	
	public VentanaCuenta(String nombreUsuario) {
		
		this.nombreUsuario = nombreUsuario;
		
		// Creación de la ventana
		ventanaCuenta = new JFrame("Cuenta de " + nombreUsuario);
		ventanaCuenta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ventanaCuenta.setSize(500, 400); // Aumentar e tamaño
		ventanaCuenta.setLocationRelativeTo(null); // Centrar la ventana
		
		// Creación de un panel principal
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout()); // Disposición de borde
		
		// Creación de un panel con información del usuario
		JPanel panelInformacion = new JPanel();
		panelInformacion.setLayout(new BoxLayout(panelInformacion, BoxLayout.Y_AXIS)); // Disposición vertical
		panelInformacion.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaciado interno
		panelInformacion.setOpaque(false); // hacer el panel transparente
		
		// Titulo
		JLabel titulo = new JLabel("Bienvenido, " + nombreUsuario);
		titulo.setFont(new Font("Arial", Font.BOLD, 28)); // cambiar fuente y tamaño
		titulo.setForeground(new Color(70, 130, 180)); // Color del texto
		panelInformacion.add(titulo);
		
		// Obtener datos del usuario y mostrarlo
		String datosUsuario = obtenerDatosUsuario();
		JLabel datosLabel = new JLabel("<html>" + datosUsuario.replace("\n", "<br>") + "</html>");
		datosLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente del texto
		datosLabel.setForeground(Color.DARK_GRAY); // Color del texto de los datos
		panelInformacion.add(datosLabel);
		
		//Añadir el panel de informacion al panel principal
		panelPrincipal.add(panelInformacion, BorderLayout.CENTER);
		
		
		
		
		
		
		
		
		
		
		// Añadir Panel Principal a la ventana
		ventanaCuenta.add(panelPrincipal);
		ventanaCuenta.setVisible(true);
		
		
	}
	
	
	
	private String obtenerDatosUsuario() {
		
		StringBuilder datos = new StringBuilder();
        String archivoCSV = "usuarios.csv"; // Asegúrate de usar la ruta correcta

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(";"); // Cambiar a punto y coma como delimitador
                if (valores.length >= 7 && valores[0].equalsIgnoreCase(nombreUsuario)) { // Asegurarse de que hay suficientes columnas
                	datos.append("<br>");
                    datos.append("<strong>Nombre:</strong> ").append(valores[0]).append("<br>").append("<br>");
                    datos.append("<strong>Apellido 1:</strong> ").append(valores[1]).append("<br>").append("<br>");
                    datos.append("<strong>Apellido 2:</strong> ").append(valores[2]).append("<br>").append("<br>");
                    datos.append("<strong>Dirección:</strong> ").append(valores[3]).append("<br>").append("<br>");
                    datos.append("<strong>Fecha de nacimiento:</strong> ").append(valores[4]).append("<br>").append("<br>");
                    datos.append("<strong>Nacionalidad:</strong> ").append(valores[5]).append("<br>").append("<br>");
                    datos.append("<strong>Contraseña:</strong> ").append(valores[6]).append("<br>").append("<br>");
                    break; // Salir del bucle si se ha encontrado el usuario
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al leer los datos del usuario.";
        }

        return datos.length() > 0 ? datos.toString() : "Usuario no encontrado.";
		
	
	}
	
	 public static void main(String[] args) {
	        
	        new VentanaCuenta("usuarioEjemplo");
	    }
	
}	
