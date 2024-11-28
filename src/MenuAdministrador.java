import javax.swing.JFrame;

public class MenuAdministrador {
	private JFrame ventanaMenu;
	
	public MenuAdministrador() {
		//Crear la ventana principal del menú del administrador
		ventanaMenu=new JFrame("Menú Administrador");
		ventanaMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventanaMenu.setSize(400, 300); //Tamaño de la ventana
		ventanaMenu.setLocationRelativeTo(null); //Centrar la ventana en la pantalla
		
		
		ventanaMenu.setVisible(true);
	}
	
	public static void main(String[] args) {
    	new MenuAdministrador();
    }
}
