package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
	
	public class BotonesPrincipal {
	    private VentanaPrincipal ventanaPrincipal;
	
	    public BotonesPrincipal(VentanaPrincipal ventanaPrincipal) {
	        this.ventanaPrincipal = ventanaPrincipal;
	    }
	
	    // Método para obtener el ActionListener de Opción 1
	    public ActionListener getAbrirCuenta() {
	        return new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                ventanaPrincipal.getVentana().dispose(); // Cerrar la ventana actual usando el método getter
	                ventanaPrincipal.abrirVentanaCuenta(); 
	            }
	        };
	    }
	    // Método para obtener el ActionListener de Cerrar Sesión
	    public ActionListener getCerrarSesionListener() {
	        return new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                ventanaPrincipal.getVentana().dispose(); // Cerrar la ventana actual usando el método getter
	                ventanaPrincipal.abrirVentanaRegistro(); // Llama al método para abrir la ventana de registro
	            }
	        };
	    }
	    // Método para obtener el ActionListener para añadir al carrito

	    public ActionListener getAñadirCarritoAction(Ropa ropa) {
	        return new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                ventanaPrincipal.getModeloCarrito().addElement(ropa); // Agregar al carrito
	                JOptionPane.showMessageDialog(ventanaPrincipal.getVentana(), ropa.getNombre() + " añadido al carrito.");
	            }
	        };
	    }
	
}