/** Proyecto: MIFISYS
 * Fichero:  Desinstalador.java
 * Utilidad: Desinstala la aplicación borrando los datos de la BD.
 * Autores:  Marcos Mainar Lalmolda       - 550710
 *           Ismael Saad Garcia           - 547942
 *           Sergio Romero Pradas         - 551382
 *           Luis Canales Mayo            - 551072
 *           Jose Javier Colomer Vieitez  - 550372
 *
 */

import javax.swing.*;

import bd.BD;


public class Desinstalador {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (!BD.conectar())
		{
			JOptionPane.showMessageDialog(new JFrame(), "Error conectando a la base de datos.\n" + 
			                                            "Compruebe que está conectado a Internet.");
			System.exit(0);
		}
		if (BD.borrarTabla("Usuario") && BD.borrarTabla("Fichero"))	 
		{
		  JOptionPane.showMessageDialog(new JFrame(), "Aplicación desinstalada correctamente");
		  System.exit(0);
		}
		else 
		{
		  JOptionPane.showMessageDialog(new JFrame(), "No se ha podido desinstalar.\n" + 
				                                      "La aplicación no estaba instalada.");
		  System.exit(0);
		}
	}
}
