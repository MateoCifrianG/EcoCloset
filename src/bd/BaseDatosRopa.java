package bd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import domain.Ropa;

public class BaseDatosRopa {

	private static final String FILE_PATH = "productos.csv";

	// Método para obtener la lista de ropa
	public List<Ropa> getRopaList() {
		List<Ropa> ropaList = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

			String line;
			while ((line = reader.readLine()) != null) {
				String[] valores = line.split(";");

				if (valores.length != 6) {
					// Verificar que hay suficientes valores
					System.err.println("Formato de línea incorrecto: " + line);
				}

				String nombre = valores[0];
				String marca = valores[1];
				String talla = valores[2];
				int cantidad = Integer.parseInt(valores[3]);
				double precio = Double.parseDouble(valores[4]);
				String estado = valores[5];

				ropaList.add(new Ropa(nombre, marca, talla, cantidad, precio, estado));
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al leer el archivo CSV: " + e.getMessage());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al convertir los valores numéricos: " + e.getMessage());
		}

		return ropaList;

	}
}
