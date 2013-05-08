/** Proyecto: MIFISYS
 * Fichero:  Instalador.java
 * Utilidad: Instala la aplicación.
 * Autores:  Marcos Mainar Lalmolda       - 550710
 *           Ismael Saad Garcia           - 547942
 *           Sergio Romero Pradas         - 551382
 *           Luis Canales Mayo            - 551072
 *           Jose Javier Colomer Vieitez  - 550372
 *
 */


import javax.swing.*;
import bd.BD;
	

public class Instalador {
	
	
    static VentanaInstalador vInstalador;

	
    public static void main(String[] args) {
    	if (BD.conectar()) {
			JOptionPane.showMessageDialog(new JFrame(), "A continuación se va a proceder a la instalación de MIFISYS\n" +
					"\t- Una vez instalada la aplicación podrá ejecutarla mediante el fichero MIFISYS.jar.\n" + 
					"\t- Lea bien la información que se le pedirá durante el proceso.\n" +
					"\t- Asegúrese de que durante el proceso está conectado a Internet.\n" +
					"\t- En caso de duda pángase en contacto con el servicio técnico.\n\n" +
					"Gracias por confiar en OpenCPSoft.");
			JOptionPane.showMessageDialog(new JFrame(), "Elija su contraseña de acceso.\n\n" + 
					"\t- Como administrador debe poseer una contraseña de acceso.\n" + 
					"\t- Esta contraseña es personal e intransferible.\n" + 
					"\t- En caso de perder la contraseña deberá volver a instalar la aplicación.\n"+
					"\t- Su nombre de usuario siempre será \"admin\".");
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() { Instalador.vInstalador =  new VentanaInstalador(); }
			});
    	}
		else {
			JOptionPane.showMessageDialog(new JFrame(), "Error conectando a la base de datos.\n" + 
														"Compruebe que está conectado a Internet.");
			System.exit(0);
		}
    	

    }
}
