import java.awt.GridBagLayout;
import javax.swing.JFrame;

public class FormularioRegistro {
	private JFrame ventana; 
	
    public FormularioRegistro () {
    	//Aquí creamos la ventana del formulario del registro
    	ventana = new JFrame("Formulario de Registro");
    	ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setSize(400, 500);
        ventana.setLayout(new GridBagLayout()); //GridBagLayout lo utilizamos para que todo mantenga su tamaño a pesar de que se maximice o minimice la pantalla.
    }
}