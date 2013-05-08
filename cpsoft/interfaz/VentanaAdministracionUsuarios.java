/** Proyecto: MIFISYS
 * Fichero:  VentanaAdministracionUsuarios.java
 * Utilidad: Permite administrar los usuarios del sistema.
 * Autores:  Marcos Mainar Lalmolda       - 550710
 *           Ismael Saad Garcia           - 547942
 *           Sergio Romero Pradas         - 551382
 *           Luis Canales Mayo            - 551072
 *           Jose Javier Colomer Vieitez  - 550372
 *
 */

package interfaz;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionListener;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.JPanel;
import usuarios.*;


public class VentanaAdministracionUsuarios extends JPanel {

	
	private JButton botonNuevo = new JButton("Nuevo");
	private JButton botonEliminar = new JButton("Eliminar");
	static public JTable tabla;
	String[] campos = {"Nombre de usuario"};
	Vector<Usuario> listadoUsuarios; 
	Administrador user;
	
	
	public VentanaAdministracionUsuarios(Administrador u) {
		user = u;
		listadoUsuarios = u.listaUsuarios();

		// Configuración de la tabla
		tabla = new JTable(new Tabla());
		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setRowSelectionAllowed(true);
		JScrollPane scrollPane = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				                                       JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Colocación de los elementos sobre el layout
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		add(scrollPane,c);

		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 5;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = GridBagConstraints.RELATIVE;
		add(botonNuevo,c);
		
		c.gridx++;
		c.gridwidth = GridBagConstraints.REMAINDER;
		add(botonEliminar,c);

		// Manejador de eventos
		ManejadorVentanaAdministracionUsuarios manejador = new ManejadorVentanaAdministracionUsuarios();
		botonNuevo.addActionListener(manejador);
		botonEliminar.addActionListener(manejador);		
	}
	
	
	public class ManejadorVentanaAdministracionUsuarios implements ActionListener {	
		
		// Manejador para todos los widgets
		public void actionPerformed (ActionEvent e) {
			Object obj = e.getSource();
			
			if (obj == botonNuevo) {
				// Se delega la labor a la ventana de creación de usuario
				Interfaz.vNuevoUsuario = new VentanaCreacionNuevoUsuario(user);
			}
			else {
				int seleccion = tabla.getSelectedRow();
				if (seleccion != -1) {
					if (obj == botonEliminar) {
						String nickBorrar = listadoUsuarios.elementAt(seleccion).getNick();
						if (JOptionPane.showConfirmDialog(new JFrame(),
								"¿Está seguro de querer eliminar al usuario " + nickBorrar + "?\n\n" + 
								"Nota: si borra a un usuario se borrarán automáticamente los ficheros que este posea",
								"¿Está seguro...?",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							if (user.borrarUsuario(listadoUsuarios.elementAt(seleccion).getNick())) {
								JOptionPane.showMessageDialog(new JFrame(), "Usuario " + nickBorrar + " eliminado con éxito");
								((VentanaAdministracionUsuarios.Tabla)VentanaAdministracionUsuarios.tabla.getModel()).deleteRow(seleccion);
								((VentanaFicherosUsuario.Tabla)VentanaFicherosUsuario.tabla.getModel()).quitarUsuario(nickBorrar);
							}
							else {
								JOptionPane.showMessageDialog(new JFrame(), "No se ha podido eliminar el usuario");
							}
						}
					}
				}
			}
		}
		
		
	}
	
	
	public class Tabla extends AbstractTableModel {
		// Subclase de la clase AbstractTableModel que adapta dicha clase a las necesidades
		// de la gestión de ficheros
		private String[] columnNames;
		private Vector<Usuario> data;
		
		public Tabla () {
			data = listadoUsuarios;
			columnNames = campos;
		}
		
		
		public void addRow (Usuario u) {
			int indice;
			for (indice = 0; indice < data.size() && u.getNick().compareToIgnoreCase(data.elementAt(indice).getNick()) >= 0; indice++);
			data.add(indice,u);
			fireTableRowsInserted(indice,indice);
		}
		
		
		public void deleteRow (int indice) {
			data.removeElementAt(indice);
			fireTableRowsDeleted(indice, indice);
		}
	
		
        public int getColumnCount() {
            return columnNames.length;
        }

        
        public int getRowCount() {
            return data.size();
        }

        
        public String getColumnName(int col) {
            return columnNames[col];
        }

        
        public Object getValueAt(int row, int col) {
       		return data.elementAt(row).getNick();
        }
        
        
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }        

        
		public boolean isCellEditable (int row, int column) {
			return false;
		}
	
		
	}
	
	
}
