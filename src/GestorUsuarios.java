import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GestorUsuarios {
	private JFrame ventana;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JTextField campoBusqueda;
    private List<String[]> listaUsuarios;
    
	public GestorUsuarios() {
		//Creo la ventana del gestor de usuarios
		ventana = new JFrame("Gestor de Usuarios");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800, 600); //Tamaño de la ventana
		ventana.setLayout(new BorderLayout());
		
		
		ventana.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		new GestorUsuarios();

	}

}
