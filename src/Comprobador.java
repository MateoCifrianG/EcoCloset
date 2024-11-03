import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Comprobador {
    // Ruta del archivo CSV
    private static final String RUTA_CSV = "usuarios.csv";

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

    // Nuevo método para verificar si un usuario es válido
    public static boolean verificarUsuario(String nombreIngresado, String contraseñaIngresada) {
        // Utiliza el método obtenerDatosUsuario para verificar si las credenciales son correctas
        return obtenerDatosUsuario(nombreIngresado, contraseñaIngresada) != null;
    }
}
