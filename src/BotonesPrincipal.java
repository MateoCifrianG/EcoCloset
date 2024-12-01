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
}