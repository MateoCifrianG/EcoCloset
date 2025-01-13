package gui;
 import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Usuario;

public class Comprobador {
    // Ruta del archivo CSV
    private static final String RUTA_CSV = "usuarios1.csv";

    // Método para verificar y obtener la información del usuario
    public static String[] obtenerDatosUsuario(String nombreIngresado, String contraseñaIngresada) {
        String linea;
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CSV))) {
            while ((linea = br.readLine()) != null) {
                // Separar la línea del archivo por el delimitador ';'
                String[] campos = linea.split(";");

                if (campos.length >= 7) {
                    String nombreUsuario = campos[0].trim();  // Campo del nombre (usuario)
                    String contraseñaUsuario = campos[6].trim();  // Campo de la contraseña

                    // Comparar nombre de usuario y contraseña
                    if (nombreUsuario.equalsIgnoreCase(nombreIngresado.trim()) && contraseñaUsuario.equals(contraseñaIngresada.trim())) {
                        return campos;  // Retorna todos los campos si coincide
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;  // Si no coincide, retorna null
    }
    
    public static boolean obtenerDatosUsuarios(String nombreIngresado, String contraseñaIngresada) {
		
		List<Usuario> lUsuarios = new ArrayList<Usuario>();
		
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
			
			String sql = "SELECT * FROM Usuarios";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) { 
				  
				String nombreUsuario = rs.getString("Nombre");
				String contraseña = rs.getString("Contraseña");	
				
				Usuario usuario = new Usuario(nombreUsuario, contraseña);
				lUsuarios.add(usuario);
			}
							
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		// Comparar nombre de usuario y contraseña
		boolean comprobacion = false;
		
		for (Usuario u : lUsuarios) {
			if (u.getNombre().equals(nombreIngresado) && u.getContraseña().equals(contraseñaIngresada)) {
				comprobacion = true;
			}
		}
		
		return comprobacion; 
		
}

    // Nuevo método para verificar si un usuario es válido
    public static boolean verificarUsuario(String nombreIngresado, String contraseñaIngresada) {
        // Utiliza el método obtenerDatosUsuario para verificar si las credenciales son correctas
        return obtenerDatosUsuario(nombreIngresado, contraseñaIngresada) != null;
    }
}
