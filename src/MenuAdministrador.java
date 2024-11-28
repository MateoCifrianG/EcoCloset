import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuAdministrador {
	private JFrame ventanaMenu;
	
	public MenuAdministrador() {
		//Crear la ventana principal del menú del administrador
		ventanaMenu=new JFrame("Menú Administrador");
		ventanaMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventanaMenu.setSize(400, 300); //Tamaño de la ventana
		ventanaMenu.setLocationRelativeTo(null); //Centrar la ventana en la pantalla
		
		//Establecer un diseño de panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 1, 10, 10)); //Siete botones en vertical
		panel.setBackground(new Color(240, 240, 240)); //Color de fondo
		
		
		ventanaMenu.setVisible(true);
	}
	
	public static void main(String[] args) {
    	new MenuAdministrador();
    }
}
