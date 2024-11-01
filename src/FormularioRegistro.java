import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class FormularioRegistro {
	private JFrame ventana; 
	
    public FormularioRegistro () {
    	//Aquí creamos la ventana del formulario del registro
    	ventana = new JFrame("Formulario de Registro"); //nombre de ventan
    	ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//se cierra el programa al darle a la x
        ventana.setSize(400, 500); //tamaño de ventana
        ventana.setLayout(new GridBagLayout()); //GridBagLayout lo utilizamos para que todo mantenga su tamaño a pesar de que se maximice o minimice la pantalla.
        
    
        //Crear los componentes del formulario sin añadirlos a la pantalla
        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreField = new JTextField(15);
        
        JLabel apellido1Label = new JLabel("Apellido 1:");
        JTextField apellido1Field = new JTextField(15);
        
        JLabel apellido2Label = new JLabel("Apellido 2:");
        JTextField apellido2Field = new JTextField(15);
        
        JLabel direccionLabel = new JLabel("Dirección:");
        JTextField direccionField = new JTextField(15);
        
        JLabel fechaNacimientoLabel = new JLabel("Fecha de Nacimiento:");
        
        //Crear los JComboBox para día, mes y año:
        
          //Días
        String[] dias = new String[32]; // los 31 días + la opción predeterminada
        dias[0] = "Día";
        for (int i = 1; i <= 31; i++) {
        	dias[i] = Integer.toString(i);
        	
        }
        JComboBox<String> comboDia = new JComboBox<>(dias);
           //Meses
        String[] meses = {"Mes","Enero","Febrero","Marzo","Abril","Mayo","Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        JComboBox<String> comboMes = new JComboBox<>(meses);
        
         	//Años
        String[] anhos = new String[102]; //Aquí los años van de 1970 al 2070
        anhos[0] = "Año";
        for (int i = 1; i < anhos.length; i++) {
        	anhos[i] = Integer.toString(1970 + i);
        }
        
        JComboBox<String> comboAnho = new JComboBox<>(anhos);
        
          //Creamos los label para que aparezcan luego en pantalla

        JLabel nacionalidadLabel = new JLabel("Nacionalidad:");
        JTextField nacionalidadField = new JTextField(15);
                  //Para que la contraseña no se vea es necesario poner asteriscos, para ello se utiliza JPasswordField
        JLabel contraseñaLabel = new JLabel("Contraseña:");
        JPasswordField contraseñaField = new JPasswordField(15);

        JLabel repetirContraseñaLabel = new JLabel("Repetir Contraseña:");
        JPasswordField repetirContraseñaField = new JPasswordField(15);

        JButton registrarButton = new JButton("Registrar");
        
        //Crear un GridBagConstraints para posicionar los componentes
        //Nos hemos apoyado en ChatGPT para hacer el GridBagConstraints ya que no lo hamos dado en clase
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); //Espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; //Para que los componentes ocupen todo el espacio horizontal
        gbc.weightx = 1.0; //Permitir expansión horizontal de los campos
        gbc.weighty=0.1; //Permitir expansión vertical moderada
        
        //Añadir los componentes al formulario
        gbc.gridx=0; //Columna 0
        gbc.gridy=0; //Fila 0
        ventana.add(nombreLabel, gbc);
        
        gbc.gridx=1; //Columna 1
        ventana.add(nombreField, gbc);
        
        gbc.gridx=0;
        gbc.gridy=1;
        ventana.add(apellido1Label, gbc);
        
        gbc.gridx=1;
        ventana.add(apellido1Field, gbc);
        
        gbc.gridx=0;
        gbc.gridy=2;
        ventana.add(apellido2Label, gbc);
        
        gbc.gridx=1;
        ventana.add(apellido2Field, gbc);
        
        gbc.gridx=0;
        gbc.gridy=3;
        ventana.add(direccionLabel, gbc);
        
        gbc.gridx=1;
        ventana.add(direccionField, gbc);
        
        gbc.gridx=0;
        gbc.gridy=4;
        ventana.add(fechaNacimientoLabel, gbc);
        
        
        //Panel para organizar todos los JComboBox:
        JPanel fechaPanel = new JPanel();
        fechaPanel.add(comboDia);
        fechaPanel.add(comboMes);
        fechaPanel.add(comboAnho);
        
        	//Añadimos Fecha
        gbc.gridx= 1;
        ventana.add(fechaPanel, gbc); //Se añade el panel con los JComboBox
        	
        	//Añadimos nacionalidad
        gbc.gridx= 0;
        gbc.gridy= 5;
        ventana.add(nacionalidadLabel, gbc);
        
        gbc.gridx= 1;
        ventana.add(nacionalidadField, gbc);
        
        	//Añadimos contraseña
        gbc.gridx= 0;
        gbc.gridy= 6;
        ventana.add(contraseñaLabel, gbc);
        
        gbc.gridx= 1;
        ventana.add(contraseñaField, gbc);
        
        	//Añadimos repetirContraseña
        gbc.gridx= 0;
        gbc.gridy= 7;
        ventana.add(repetirContraseñaLabel, gbc);
        
        gbc.gridx= 1;
        ventana.add(repetirContraseñaField, gbc);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        ventana.setVisible(true);
        
    }
    
    public static void main(String[] args) {
        new FormularioRegistro();
    }
}