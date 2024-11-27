import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;

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
		
		
		ventanaProblemas.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new Asistencia();
		
	}
}

