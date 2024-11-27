import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Asistencia {
	private JFrame ventanaProblemas;
	
	public Asistencia() {
		//Creo la ventana de Problemas
		ventanaProblemas = new JFrame("Problemas al iniciar sesión");
		ventanaProblemas.setSize(400,400);
		ventanaProblemas.setLayout(new GridLayout());
		
		JLabel usuarioLabel = new JLabel("Usuario");
		JTextField usuarioField = new JTextField(20);
		
		JLabel correoLabel = new JLabel("Correo Electrónico:");
		JTextField correoField = new JTextField(20); //Campo para el correo electrónico
		
		JLabel mensajeLabel = new JLabel("Comentario:");
		JTextArea mensajeArea = new JTextArea(5, 20); //5 filas, 20 columnas
		JButton enviarButton = new JButton("Enviar");
		
		ventanaProblemas.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new Asistencia();
		
	}
}

