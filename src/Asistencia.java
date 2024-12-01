import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Asistencia {
	private JFrame ventanaProblemas;
	
	public Asistencia() {
		//Creo la ventana de Problemas
		ventanaProblemas = new JFrame("Problemas al iniciar sesión");
		ventanaProblemas.setSize(400,400);
		ventanaProblemas.setLayout(new GridBagLayout());
		
		JLabel usuarioLabel = new JLabel("Usuario");
		JTextField usuarioField = new JTextField(20);
		
		JLabel correoLabel = new JLabel("Correo Electrónico:"); 
		JTextField correoField = new JTextField(20); //Campo para el correo electrónico
		
		JLabel mensajeLabel = new JLabel("Comentario:");
		JTextArea mensajeArea = new JTextArea(5, 20); //5 filas, 20 columnas
		JButton enviarButton = new JButton("Enviar");
		
		//Crear un GridBagConstraints para posicionar los componentes
		GridBagConstraints gbc= new GridBagConstraints();
		gbc.insets=new Insets(10, 10, 10, 10); //Espaciado entre componentes
		
		//Añadir los componentes a la ventana de problemas
		gbc.gridx = 0; //Columna 0
		gbc.gridy = 0; //Fila 0
		ventanaProblemas.add(usuarioLabel, gbc);

		gbc.gridx = 1; //Columna 1
		ventanaProblemas.add(usuarioField, gbc);
		
		gbc.gridx = 0; //Columna 0
		gbc.gridy = 1; //Fila 1
		ventanaProblemas.add(correoLabel, gbc);

		gbc.gridx = 1; //Columna 1
		ventanaProblemas.add(correoField, gbc);
		
		gbc.gridx = 0; //Columna 0
		gbc.gridy = 2; //Fila 2
		ventanaProblemas.add(mensajeLabel, gbc);

		gbc.gridx = 1; //Columna 1
		ventanaProblemas.add(new JScrollPane(mensajeArea), gbc); //Añadir JScrollPane para el área de texto
		
		gbc.gridx = 0; //Columna 0
		gbc.gridy = 3; //Fila 3
		gbc.gridwidth = 2; //Ocupa ambas columnas
		ventanaProblemas.add(enviarButton, gbc); //Añadir el botón de enviar
		
		//Action listener para el botón Enviar
		enviarButton.addActionListener(e -> {
			String usuario = usuarioField.getText().trim(); //El trim elimina los espacios en blanco adicionales al inicio y al final del texto 
			String correo = correoField.getText().trim(); //Obtener correo electrónico
			String comentario = mensajeArea.getText().trim();
			
			//Validar que todos los campos están llenos
			if (usuario.isEmpty() || correo.isEmpty() || comentario.isEmpty()) {
				//Mostrar mensaje de error si algún campo está vacío
				JOptionPane.showMessageDialog(ventanaProblemas, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
				return; // Salir del método si hay un campo vacío
			}
			
			String fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			
			//Guardar en el archivo CSV
			guardarProblema(usuario, correo, comentario, fecha);
			
			//Mostrar mensaje éxito
			JOptionPane.showMessageDialog(ventanaProblemas, "Mensaje enviado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			ventanaProblemas.dispose();
		});
		
		
		ventanaProblemas.setVisible(true);
		
	}
	
	//Método para guardar el mensaje en el archivo CSV
	private void guardarProblema(String usuario, String correo, String comentario, String fecha) {
		String lineaRegistro = usuario + ";" + correo + ";" + comentario + ";" + fecha + "\n"; // Formato de línea en CSV
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("asistencia.csv", true))) {
			bw.write(lineaRegistro); //Escribir la linea en el archivo
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Asistencia();
		
	}
}

