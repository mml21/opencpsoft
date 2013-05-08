/** Proyecto: MIFISYS
 * Fichero:  VentanaFicherosUsuario.java
 * Utilidad: Permite acceder a los ficheros de los usuarios.
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
import fichero.Fichero;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionListener;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionEvent;
import usuarios.*;
import javax.swing.JPanel;
import java.util.Vector;




public class VentanaFicherosUsuario extends JPanel  {

	
	// Variables de clase
	private JButton botonLeer = new JButton("    Leer      ");
	private JButton botonEscribir = new JButton(" Escribir ");
	private JButton botonEjecutar = new JButton(" Ejecutar ");
	private JButton botonNuevo = new JButton("  Nuevo   ");
	private JButton botonModificar = new JButton(" Modificar");
	private JButton botonEliminar = new JButton(" Eliminar ");
	private String[] campos = {"Nombre","Propietario","Lectura","Escritura","Ejecución"};
	private Usuario user;
	static public JTable tabla;
	private Vector<Fichero> listaFicheros;
	private JScrollPane scrollPane;
	
	// Constructor de la clase
	public VentanaFicherosUsuario(Usuario u) {

		user = u;
		// Obtenemos el listado de todos los ficheros,
		listaFicheros = u.listaFicheros();
		
		
		// Metemos los datos en la tabla y configuramos la tabla
		tabla = new JTable(new Tabla());
		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setRowSelectionAllowed(true);
		scrollPane = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				                                       JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// Añadimos tabla y botones al container
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		//Tabla
		add(scrollPane,c);

		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 5;
		c.weightx = 1;
		c.weighty = 1;
		// Botón Escribir
		add(botonEscribir,c);
		
		c.gridx++;
		c.gridwidth = GridBagConstraints.RELATIVE;
		// Botón Leer
		add(botonLeer,c);
		
		c.gridx++;
		c.gridwidth = GridBagConstraints.REMAINDER;
		// Botón Ejecutar
		add(botonEjecutar,c);
			
		c.gridy++;
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.NONE;
		// Botón Nuevo 
		add(botonNuevo,c);
		
		c.gridx++;
		c.gridwidth = GridBagConstraints.RELATIVE;
		// Botón Modificar
		add(botonModificar,c);
		
		c.gridx++;
		c.gridwidth = GridBagConstraints.REMAINDER;
		// Botón Eliminar
		add(botonEliminar,c);
		
		// Asignamos los manejadores
		ManejadorVentanaFicherosUsuario manejador = new ManejadorVentanaFicherosUsuario();
		botonLeer.addActionListener(manejador);
		botonEscribir.addActionListener(manejador);
		botonEjecutar.addActionListener(manejador);
		botonNuevo.addActionListener(manejador);
		botonModificar.addActionListener(manejador);
		botonEliminar.addActionListener(manejador);

	}
	
	
	
	
	public class ManejadorVentanaFicherosUsuario implements ActionListener {
			
		// Manejador para todos los widgets
		public void actionPerformed (ActionEvent e) {
			Object obj = e.getSource();
			int seleccion = tabla.getSelectedRow();
			if (obj == botonNuevo) {
				Interfaz.vNuevoFichero = new VentanaCreacionNuevoFichero(user);
			}
			else if (seleccion != -1) {
				if (obj == botonLeer) {
					if (listaFicheros.elementAt(seleccion).leer(user.getNick())) {
						JOptionPane.showMessageDialog(new JFrame(), "Fichero leído con éxito");
					}
					else {
						JOptionPane.showMessageDialog(new JFrame(), "No tienes permiso de lectura");
					}
				}
				else if (obj == botonEscribir) {
					if (listaFicheros.elementAt(seleccion).escribir(user.getNick())) {
						JOptionPane.showMessageDialog(new JFrame(), "Fichero escrito con éxito");
					}
					else {
						JOptionPane.showMessageDialog(new JFrame(), "No tienes permiso de escritura");
					}
				}
				else if (obj == botonEjecutar) {
					if (listaFicheros.elementAt(seleccion).ejecutar(user.getNick())) {
						JOptionPane.showMessageDialog(new JFrame(), "Fichero ejecutado con éxito");
					}
					else {
						JOptionPane.showMessageDialog(new JFrame(), "No tienes permiso de ejecución");
					}
				}
				else if (obj == botonModificar) {
					if ((listaFicheros.elementAt(seleccion).getPropietario().compareTo(user.getNick()) == 0)) {
						Interfaz.vModificarFichero = 
						 new VentanaModificacionFichero(listaFicheros.elementAt(seleccion),user,seleccion);
					}
					else {
						JOptionPane.showMessageDialog(new JFrame(), "No eres el propietario");
					}
				}
				else if (obj == botonEliminar) {
					String nombreFicheroBorrar = listaFicheros.elementAt(seleccion).getNombre();
					if (listaFicheros.elementAt(seleccion).borrarFichero(user.getNick())) {
						if (JOptionPane.showConfirmDialog(new JFrame(),
								"¿Está seguro de querer eliminar el fichero " + nombreFicheroBorrar + "?",
								"¿Está seguro...?",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							JOptionPane.showMessageDialog(new JFrame(), "Fichero " + nombreFicheroBorrar + " eliminado con éxito");
							((Tabla)tabla.getModel()).deleteRow(seleccion);
						}
					}
					else {
						JOptionPane.showMessageDialog(new JFrame(), "No eres el propietario");
					}
				}
			} 
		}
		
	}

	
	public class Tabla extends AbstractTableModel {
	// Subclase de la clase AbstractTableModel que adapta dicha clase a las necesidades
	// de la gestión de ficheros
		private String[] columnNames;
		private Vector<Fichero> data;
		
		public Tabla () {
			data = listaFicheros;
			columnNames = campos;
		}
		
		public void addRow (Fichero f) {
			int indice;
			for (indice = 0; indice < data.size() && f.getNombre().compareToIgnoreCase(data.elementAt(indice).getNombre()) >= 0; indice++);
			data.add(indice,f);
			fireTableRowsInserted(indice,indice);
		}
		
		
		public void updateRow (Fichero f, int indice) {
			deleteRow(indice);
			addRow(f);
		}
		
		public void deleteRow (int indice) {
			data.removeElementAt(indice);
			fireTableRowsDeleted(indice, indice);
		}
		
		
		public void quitarUsuario (String nick) {
			int indice = 0;
			int aux = data.size();
			for (int i = 0; i < aux; i++) {
				if (data.elementAt(indice).getPropietario().compareTo(nick) == 0) {
					data.removeElementAt(indice);
				}
				else {
					indice++;
				}
			}
			if (aux > 0) fireTableRowsDeleted(0, aux-1);
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
        	if (col == 0)
        		return data.elementAt(row).getNombre();
        	else if (col == 1)
        		return data.elementAt(row).getPropietario();
        	else if (col == 2)
        		return data.elementAt(row).getLectura();
        	else if (col == 3)
        		return data.elementAt(row).getEscritura();
        	else if (col == 4)
        		return data.elementAt(row).getEjecucion();
        	else
        		return null;
        }
        
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        

		public boolean isCellEditable (int row, int column) {
			return false;
		}
	}

	
}




