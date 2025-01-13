package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RendererAdminColor implements TableCellRenderer{

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		String estado=(String) value;
		JLabel celda=new JLabel();
		
		celda.setOpaque(true);
		celda.setText(estado);
		
		// Configurar la fuente en estilo NORMAL (no negrita)
        celda.setFont(table.getFont().deriveFont(Font.PLAIN));
		
		if(estado.equals("Nuevo")){
			celda.setBackground(Color.decode("#64f595"));
		}
		else if(estado.equals("Usado")){
			celda.setBackground(Color.decode("#f7c25e"));
		}
		else if(estado.equals("Desgastado")){
			celda.setBackground(Color.decode("#f56553"));
		}
		else {
			celda.setBackground(Color.decode("#f2fa89"));
		}
		
		// Asegurarnos de que el fondo de selección y el color de texto se vean bien al seleccionar la celda
        if (isSelected) {
            celda.setBackground(table.getSelectionBackground());
            celda.setOpaque(true); // Necesario para que el color de fondo funcione
        }
		
		return celda;
	}
		
}
