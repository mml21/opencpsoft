/** Proyecto: MIFISYS
 * Fichero:  Interfaz.java
 * Utilidad: Gestiona la interfaz gráfica de la aplicación.
 * Autores:  Marcos Mainar Lalmolda       - 550710
 *           Ismael Saad Garcia           - 547942
 *           Sergio Romero Pradas         - 551382
 *           Luis Canales Mayo            - 551072
 *           Jose Javier Colomer Vieitez  - 550372
 *
 */

package interfaz;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bd.BD;

public class Interfaz {

	
	// Declaración de todas las ventanas que se utilizarán
	static VentanaLogin vLogin;
	static VentanaModificacionFichero vModificarFichero;
	static VentanaUsuario vUsuario; 
	static VentanaCreacionNuevoFichero vNuevoFichero;
	static VentanaAdministrador vAdmin;
	static VentanaCreacionNuevoUsuario vNuevoUsuario;
	static VentanaModificacionUsuario vModificarUsuario;
	

	
	public static void main(String args[]) {
		// Conecta a la base de datos y arranca la interfaz
		if (BD.conectar()) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() { vLogin = new VentanaLogin(); }
			});
		}
		else {
			JOptionPane.showMessageDialog(new JFrame(), "Error conectando a la base de datos.\n" + 
														"Compruebe que está conectado a Internet.");
			System.exit(0);
		}
	}
 
}  