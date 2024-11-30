package bd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import domain.Ropa;

public class BaseDatosRopa {

	// Método para obtener la lista de ropa
	public List<Ropa> getRopaList() {
		List<Ropa> ropaList = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("productos.csv"))) {

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
	
	
	// Método para actualizar la cantidad en el CSV
	public void actualizarCantidadEnCSV (Ropa prenda) { 
		
		List<String> lineas = new ArrayList<String>(); // lista para almacenar las lineas del nuevo archivo
		boolean eliminado = false; // para verificar si se eliminó la prenda
		
		try (BufferedReader reader = new BufferedReader(new FileReader("productos.csv"))) {
			String linea;
			while ((linea = reader.readLine()) != null) {
				String[] valores = linea.split(";"); // csv separado por ";"
				
				// Verificar si la línea corresponde a la prenda que queremos actualizar
				if (valores[0].equals(prenda.getNombre()) && valores[1].equals(prenda.getMarca()) && valores[2].equals(prenda.getTalla())) {
					
					int cantidadActual = Integer.parseInt(valores[3]);
					cantidadActual --; // disminuir la cantidad
					
					// Comprobar si la cantidad llegó a cero
					if (cantidadActual <= 0) {
						eliminado = true; // marcamos que se ha eliminado la prenda
						continue; // no añadimos esta linea al nuevo contenido
					} else {
						valores[3] = String.valueOf(cantidadActual); // actualizar la cantidad
					}
				}
				// Añadir la línea al nuevo contenido si no fue eliminada
				lineas.add(String.join(";", valores));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al leer el archivo CSV para actualizar la antidad.");
		}
		
		// Escribir el nuevo contenido en el archivo CSV
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.csv"))) {
			
			for (String nuevaLinea : lineas) {
				writer.write(nuevaLinea);
				writer.newLine(); // nueva linea
			}
			
			if (eliminado) {
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al leer el archivo CSV.");
		}
		
	}
	
	
	
	
}
