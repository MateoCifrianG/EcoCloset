import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Pedidos {
	private JFrame ventana;
	
	public Pedidos() {
		
		//Creamos la ventana 
		ventana = new JFrame("Administrador de Pedidos");
	    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    ventana.setSize(800, 600);
	    ventana.setLayout(new BorderLayout());
	    
	    // Creamos el panel superior con campo de búsqueda y botón de eliminar
	    JPanel panelSuperior = new JPanel(new BorderLayout());
        JTextField campoBusqueda = new JTextField();
        JButton botonBuscar = new JButton("Buscar");
        JButton botonEliminar = new JButton("Eliminar");
        
        JPanel panelBusqueda = new JPanel(new BorderLayout());
        panelBusqueda.add(new JLabel("Buscar Pedido: "), BorderLayout.WEST);
        
        //Añadimos al panel
        panelBusqueda.add(campoBusqueda, BorderLayout.CENTER);
        panelBusqueda.add(botonBuscar, BorderLayout.EAST);
        panelSuperior.add(panelBusqueda, BorderLayout.NORTH);
        panelSuperior.add(botonEliminar, BorderLayout.SOUTH);
        
        ventana.setVisible(true);

	}

}
