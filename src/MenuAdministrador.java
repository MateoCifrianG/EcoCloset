import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
		
		//Estilo de los botones
		JButton botonGestionProductos=crearBoton("Gestionar Productos");
		botonGestionProductos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaMenu.dispose(); //Cerrar la ventana del menú
				//new Administrador(); //Crear nueva ventana de administrados (descomentar al crear la clase de Administrador)
			}
		});
		
		
		ventanaMenu.setVisible(true);
	}
	
	//Método para crear botones con un estilo uniforme
	private JButton crearBoton(String texto) {
		JButton boton=new JButton(texto);
		boton.setFont(new Font("Arial", Font.BOLD, 16)); //Fuente del botón
		boton.setBackground(new Color(30, 144, 255)); //Color de fondo del botón
		boton.setForeground(Color.WHITE); //Color del texto del botón
		boton.setFocusPainted(false); //No mostrar el borde cuando el botón tiene el foco
		boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); //Espaciado interno
		boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //Cambiar el cursor al pasar por encima
		return boton;
	}
	
	public static void main(String[] args) {
    	new MenuAdministrador();
    }
}
