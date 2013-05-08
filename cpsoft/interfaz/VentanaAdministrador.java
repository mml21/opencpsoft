/** Proyecto: MIFISYS
 * Fichero:  VentanaAdministrador.java
 * Utilidad: Permite acceder a las funcionalidades del admin.
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import usuarios.*;


import javax.swing.*;


public class VentanaAdministrador extends JFrame {

	
	private JButton botonCambiarPassword = new JButton("Cambiar password");
	private JButton botonSalir = new JButton("Salir");
	Usuario user;
	
	
	public VentanaAdministrador (Usuario u) {

		super("Administrador");
		user = u;
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Ficheros",new VentanaFicherosUsuario(u));
		tabbedPane.add("Administración",new VentanaAdministracionUsuarios(new Administrador(u.getNick())));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	
		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
	
		getContentPane().add(tabbedPane,c);

		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 5;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = GridBagConstraints.RELATIVE;
		getContentPane().add(botonCambiarPassword,c);
	
		c.gridx++;
		c.gridwidth = GridBagConstraints.REMAINDER;
		getContentPane().add(botonSalir,c);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
	    ManejadorVentanaAdministrador manejador = new ManejadorVentanaAdministrador();
	    botonCambiarPassword.addActionListener(manejador);
	    botonSalir.addActionListener(manejador);
	}
	
	
	public class ManejadorVentanaAdministrador implements ActionListener {
		
		
		// Manejador para todos los widgets
		public void actionPerformed (ActionEvent e) {
			Object obj = e.getSource();
			if (obj == botonCambiarPassword) {
				Interfaz.vModificarUsuario = new VentanaModificacionUsuario(user);
			}
			else if (obj == botonSalir) {
				System.exit(0);
			}
		}
		
		
	}
	
}