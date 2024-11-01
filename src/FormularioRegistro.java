import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FormularioRegistro {
	private JFrame ventana; 
	
    public FormularioRegistro () {
    	//Aquí creamos la ventana del formulario del registro
    	ventana = new JFrame("Formulario de Registro");
    	ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setSize(400, 500);
        ventana.setLayout(new GridBagLayout()); //GridBagLayout lo utilizamos para que todo mantenga su tamaño a pesar de que se maximice o minimice la pantalla.
    
        //Crear los componentes del formulario
        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreField = new JTextField(15);
        
        JLabel apellido1Label = new JLabel("Apellido 1:");
        JTextField apellido1Field = new JTextField(15);
        
        JLabel apellido2Label = new JLabel("Apellido 2:");
        JTextField apellido2Field = new JTextField(15);
        
        JLabel direccionLabel = new JLabel("Dirección:");
        JTextField direccionField = new JTextField(15);
        
        JLabel fechaNacimientoLabel = new JLabel("Fecha de Nacimiento:");
    }
}