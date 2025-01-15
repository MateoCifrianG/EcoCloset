package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< HEAD
=======
import java.sql.Statement;
>>>>>>> branch 'main' of https://github.com/MateoCifrianG/EcoCloset.git

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
		// panelInformacion.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		// // Espaciado interno
		panelInformacion.setOpaque(false); // hacer el panel transparente

		// Titulo
		JLabel titulo = new JLabel("Bienvenido, " + nombreUsuario);
		titulo.setFont(new Font("Arial", Font.BOLD, 28)); // cambiar fuente y tamaño
		titulo.setForeground(new Color(70, 130, 180)); // Color del texto
		panelInformacion.add(titulo);

		// Obtener datos del usuario y mostrarlos
		String datosUsuario = obtenerDatosUsuarioBD(nombreUsuario); 
		JLabel datosLabel = new JLabel("<html>" + datosUsuario.replace("\n", "<br>") + "</html>");
		datosLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente del texto
		datosLabel.setForeground(Color.DARK_GRAY); // Color del texto de los datos
		panelInformacion.add(datosLabel);

		// Añadir el panel de informacion al panel principal
		panelPrincipal.add(panelInformacion, BorderLayout.CENTER);

		// Creación de un panel inferior para el boton de cerrar
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BorderLayout()); // Disposición de borde

		// Creación del boton para cerrar la ventana
		JButton cerrarButton = new JButton("Cerrar");
		cerrarButton.setBackground(new Color(70, 130, 180)); // Color del botón
		cerrarButton.setForeground(Color.WHITE); // Color del texto del botón
		cerrarButton.setFont(new Font("Arial", Font.BOLD, 16)); // Fuente del botón
		// cerrarButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //
		// Espacio interno del botón
		cerrarButton.addActionListener(e -> ventanaCuenta.dispose());

		// Añadir MouseListener
		cerrarButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				cerrarButton.setBackground(new Color(70, 130, 180)); // color original

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				cerrarButton.setBackground(new Color(30, 144, 255)); // color al pasar con el ratón

			}

		});
		
		// Añadir el botón al panel inferior y hacer que ocupe todo el ancho
		panelInferior.add(cerrarButton, BorderLayout.CENTER);
		
		// Añadir el panel inferior al panel principal
		panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

		// Añadir Panel Principal a la ventana
		ventanaCuenta.add(panelPrincipal);
		ventanaCuenta.setVisible(true);

	}

	public String obtenerDatosUsuario() {
		 StringBuilder datos = new StringBuilder();
	        String url = "Usuarios.db"; 
	        String consultaSQL = "SELECT * FROM usuarios WHERE nombre = ?";

	        try (Connection conexion = DriverManager.getConnection(url);
	             PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {
	            
	            ps.setString(1, nombreUsuario);

	           
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    datos.append("<br>");
	                    datos.append("<strong>Nombre:</strong> ").append(rs.getString("nombre")).append("<br><br>");
	                    datos.append("<strong>Apellido 1:</strong> ").append(rs.getString("apellido1")).append("<br><br>");
	                    datos.append("<strong>Apellido 2:</strong> ").append(rs.getString("apellido2")).append("<br><br>");
	                    datos.append("<strong>Dirección:</strong> ").append(rs.getString("direccion")).append("<br><br>");
	                    datos.append("<strong>Fecha de nacimiento:</strong> ").append(rs.getDate("fecha_nacimiento")).append("<br><br>");
	                    datos.append("<strong>Nacionalidad:</strong> ").append(rs.getString("nacionalidad")).append("<br><br>");
	                    datos.append("<strong>Contraseña:</strong> ").append(rs.getString("contrasena")).append("<br><br>");
	                } else {
	                    return "Usuario no encontrado.";
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return "Error al leer los datos del usuario desde la base de datos.";
	        }

<<<<<<< HEAD
	        return datos.toString();
	    }
=======
		try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] valores = linea.split(";"); // Cambiar a punto y coma como delimitador
				if (valores.length >= 7 && valores[0].equalsIgnoreCase(nombreUsuario)) { // Asegurarse de que hay
																							// suficientes columnas
					datos.append("<br>");
					datos.append("<strong>Nombre:</strong> ").append(valores[0]).append("<br>").append("<br>");
					datos.append("<strong>Apellido 1:</strong> ").append(valores[1]).append("<br>").append("<br>");
					datos.append("<strong>Apellido 2:</strong> ").append(valores[2]).append("<br>").append("<br>");
					datos.append("<strong>Dirección:</strong> ").append(valores[3]).append("<br>").append("<br>");
					datos.append("<strong>Fecha de nacimiento:</strong> ").append(valores[4]).append("<br>")
							.append("<br>");
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
	 
	public String obtenerDatosUsuarioBD(String nombre) {
    	
		StringBuilder datos = new StringBuilder();
		
    	// cargar el driver de SQLite para JDBC
    	// se hace una vez en todo el programa
   		try { 
   			Class.forName("org.sqlite.JDBC"); 
   		} catch (ClassNotFoundException e) { 
   			System.out.println("No se ha podido cargar el driver de la base de datos"); 
   		}
   		
   	// conectar a la base de datos
   			try {
   				Connection conn = DriverManager.getConnection("jdbc:sqlite:resources/db/usuarios.db");
   				
   				Statement stmt = conn.createStatement();
   				
   				String sql = "SELECT * FROM Usuarios WHERE Nombre = " + nombre + ";";
   				
   				ResultSet rs = stmt.executeQuery(sql); 				
   				 
   				while (rs.next()) { 
   					String Nombre = rs.getString("Nombre");  
   					String apellido1 = rs.getString("Apellido1");
   					String apellido2 = rs.getString("Apellido2");
   					String direccion = rs.getString("Direccion");  
   					String fecha = rs.getString("FechaNac");
   					String nacionalidad = rs.getString("Nacionalidad");
   					String contraseña = rs.getString("Contraseña");
   				
	   				datos.append("<br>"); 
					datos.append("<strong>Nombre:</strong> ").append(Nombre).append("<br>").append("<br>");
					datos.append("<strong>Apellido 1:</strong> ").append(apellido1).append("<br>").append("<br>");
					datos.append("<strong>Apellido 2:</strong> ").append(apellido2).append("<br>").append("<br>");
					datos.append("<strong>Dirección:</strong> ").append(direccion).append("<br>").append("<br>");
					datos.append("<strong>Fecha de nacimiento:</strong> ").append(fecha).append("<br>").append("<br>");
					datos.append("<strong>Nacionalidad:</strong> ").append(nacionalidad).append("<br>").append("<br>");
					datos.append("<strong>Contraseña:</strong> ").append(contraseña).append("<br>").append("<br>");
					break; // Salir del bucle si se ha encontrado el usuario
   				
   				}	
					
   				stmt.close();
   				
   				System.out.println("Usuario registrado con éxito.");
>>>>>>> branch 'main' of https://github.com/MateoCifrianG/EcoCloset.git

   		        conn.close();
   				
   			
   			}catch (SQLException e1) {
			e1.printStackTrace();
   			}
			return datos.length() > 0 ? datos.toString() : "Usuario no encontrado."; 
			    	 

	}		
// ----------------------------------------------------------------------------------------------------------
}
