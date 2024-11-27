import java.awt.GridLayout;

import javax.swing.JFrame;

public class Asistencia {
	private JFrame ventanaProblemas;
	
	public Asistencia() {
		//Creo la ventana de Problemas
		ventanaProblemas = new JFrame("Problemas al iniciar sesión");
		ventanaProblemas.setSize(400,400);
		ventanaProblemas.setLayout(new GridLayout());
		
		
		ventanaProblemas.setVisible(true);
	}
}
