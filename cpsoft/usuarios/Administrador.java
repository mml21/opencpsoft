/** Proyecto: MIFISYS
 * Fichero:  Administrador.java
 * Utilidad: Operaciones del administrador.
 * Autores:  Marcos Mainar Lalmolda       - 550710
 *           Ismael Saad Garcia           - 547942
 *           Sergio Romero Pradas         - 551382
 *           Luis Canales Mayo            - 551072
 *           Jose Javier Colomer Vieitez  - 550372
 *
 */
 
package usuarios;

import java.util.*;
import bd.*;

public class Administrador extends Usuario {
 

 public Administrador(String nick) {
 /*
  *  Carga el usuario de la BD.
  */
   super(nick);
 }
 
 
 public static boolean crearUsuario(String nick, String password)  
 {
 /*
  *  Almacena una instancia del usuario en la BD. Devuelve true sii
  *  la insercion se ha realizado correctamente, false en caso contrario 
  */
	 
	 try {
		new Usuario(nick, password, true);
		return true;
	 } catch (Exception e) {
		return false;
	 }
 

 }  
 
 
 public boolean borrarUsuario(String nick) 
 {
  /*
   * Devuelve true sii el usuario ha sido borrado con exito,
   * false en caso contrario
   */
	 if (nick.compareTo("admin") != 0) {
		 return BD.borraUsuario(nick);
	 }
	 else {
		 return false;
	 }
	 
 }
 
 
 public Vector<Usuario> listaUsuarios() 
 {
  /*
   * Devuelve en un vector todos los usuarios que hay en la base de datos
   */	 
	 Vector<Usuario> lista;
	 
	 lista = BD.getListaUsuarios();
	 
	 return lista;
 
 }
 
 
}  