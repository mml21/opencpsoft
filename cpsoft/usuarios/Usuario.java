/** Proyecto: MIFISYS
 * Fichero:  Usuario.java
 * Utilidad: Operaciones de los usuarios.
 * Autores:  Marcos Mainar Lalmolda       - 550710
 *           Ismael Saad Garcia           - 547942
 *           Sergio Romero Pradas         - 551382
 *           Luis Canales Mayo            - 551072
 *           Jose Javier Colomer Vieitez  - 550372
 *
 */
 
package usuarios;

import java.util.*;
import fichero.*;
import bd.*;

public class Usuario {
 
 private String nick;
 private String password;
 
 
 public Usuario(String nick) {
 /*
  * Constructor que carga la tupla de la base de datos a partir de la clave primaria
  */
  Usuario u = bd.BD.getUsuario(nick);
  if (u != null) {
	  this.nick = nick;
	  this.password = u.password;
  }
 }


 public Usuario (String nick, String password, boolean guardar) throws Exception {
 /*
  * Constructor que rellena los campos del objeto Usuario
  * El parametro guardar indica si hay que insertar o no
  * el usuario en la BD, en caso de error al insertar, se 
  * lanza una excepcion
  */
	 if (guardar && BD.existeUsuario(nick)) throw new Exception();
	 
	 try{
	 
		 this.nick = nick;
		 this.password = password;
		 if (guardar) BD.creaUsuario(this);
		 
	 }
	 catch (Exception eSQL){
		 
		 Exception e = new Exception();
		 throw e;
	 }
 }


 public String getNick() {
   return nick;
 }
 
 
 public String getPassword() {
	 return password;
 }
 
 
 public boolean cambiarPassword(String newPassword) 
 {
 /*
  * Modifica el objeto y actualiza la BD
  */
	 String passAux;
	 
	 passAux = this.password;
	 
	 
	 if (newPassword.length() == 0) return false;
	 this.password = newPassword;
	 if (BD.setUsuario(this)){
		 
		 /*la modificacion se ha hecho correctamente*/
		 return true;
	 }
	 else{
		 /*ha ocurrido algun error, recuperamos el valor*/
		 this.password = passAux;
		 return false;
	 }
	 
 }
 
 
 public boolean comprobarPassword(String password) {
 /*
  * Devuelve true sii el password se corresponde con el del usuario
  */  

	 return (this.password.compareTo(password) == 0);
 }
 

 public Vector<Fichero> listaFicheros() {
 /*
  * Devuelve todos los ficheros existentes en la base de datos
  */
	 Vector<Fichero> lista;
	 
	 lista = BD.getListaFicheros();
	 
	 return lista;
 }
 
 
}  