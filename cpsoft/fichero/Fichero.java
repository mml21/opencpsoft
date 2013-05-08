/** Proyecto: MIFISYS
 * Fichero:  Fichero.java
 * Utilidad: Gestiona el acceso a ficheros de la BD de la aplicacion.
 * Autores:  Marcos Mainar Lalmolda       - 550710
 *           Ismael Saad Garcia           - 547942
 *           Sergio Romero Pradas         - 551382
 *           Luis Canales Mayo            - 551072
 *           Jose Javier Colomer Vieitez  - 550372
 *
 */

package fichero;

import bd.*;
 
public class Fichero {
 
 private boolean lectura;
 private boolean escritura;
 private boolean ejecucion;
 private String nombre;      //Nombre del fichero, estructura plana de directorios.
 private String propietario;
 

 public Fichero (String nombre) {
 /*
  * Carga la tupla de la BD a partir de la clave primaria
  */ 
	 
	 /* obtiener un objeto fichero con los campos correctamente rellenados */
	 Fichero f = BD.getFichero(nombre);
	 
	 this.nombre = nombre;
	 this.lectura = f.lectura;
	 this.escritura = f.escritura;
	 this.ejecucion = f.ejecucion;
	 this.propietario = f.propietario;
	 
 }

 public Fichero (String nombre, boolean lec, boolean esc, boolean ejec,
                 String propietario, boolean guardar) throws Exception {
 /*
  * Rellena los campos e inserta o no en la BD en función del parametro guardar,
  * en caso de que no se haya podido insertar en la BD, se lanzará una excepción
  */
	 
	 try{
	
		 this.nombre = nombre;
		 this.lectura = lec;
		 this.escritura = esc;
		 this.ejecucion = ejec;
		 this.propietario = propietario;
		 if (guardar) bd.BD.creaFichero(this);
		 
	 }catch(Exception eSQL){
		 
		 Exception e = new Exception();
		 throw e;
		 
	 }
 }



 public String getNombre() 
 {
 /*
  * Devuelve el string correspondiente al nombre del usuario
  */
	 
   return nombre;
 }
 
 
 public boolean getEscritura() 
 {	 
 /*
  * Devuelve true sii los usuarios distintos al creador tienen permiso de escritura,
  * false en caso contrario 
  */
	 
   return escritura;
 }
 
 
 public boolean getEjecucion() 
 { 
 /*
  * Devuelve true sii los usuarios distintos al creador tienen permiso de ejecucion,
  * false en caso contrario 
  */
	 
   return ejecucion;
 }
 
 
 public boolean getLectura() 
 {
 /*
  * Devuelve true sii los usuarios distintos al creador tienen permiso de lectura,
  * false en caso contrario 
  */
	 
   return lectura;
 }
 
 
 public String getPropietario() 
 {  
 /*
  * Devuelve el string correspondiente al nombre del propietario, el creador, del 
  * fichero 
  */
	 
   return propietario;
 }
 
 
 private boolean setPermisos(boolean lec, boolean esc, boolean ejec) 
 {
 /*
  * Función que modifica los permisos de los usuarios distintos al propietario 
  */	 
	 
	 /* variables para almacenar los datos localmente en caso de ser necesario
	  * recuperarlos debido a algun error */
	 boolean auxLec, auxEsc, auxEjec;
	 
	 auxLec = this.lectura;
	 auxEsc = this.escritura;
	 auxEjec = this.ejecucion;
	 
	 this.lectura = lec;	/* actualizar los valores del objeto */
	 this.escritura = esc;
	 this.ejecucion = ejec;
	 
	 if (BD.setFichero(this,this.nombre)){ /*actualizar los datos en la base de datos*/
		 return true;
	 }else{
		 
		 /* ha habido un error al almacenar los datos en la base,
		  * recuperamos los datos anteriores */
		 
		 this.lectura = auxLec;
		 this.escritura = auxEsc;
		 this.ejecucion = auxEjec;
		 
		 return false; 
		 
	 }
 
 }
 
 
 
 
 public boolean renombrar(String nombre) 
 {
 /*
  * Funcion que modifica el nombre de un fichero 
  */	 
	 String nombreViejo = this.nombre;
	 
	 if (nombre.length() == 0) return false;
	 if (this.nombre.compareTo(nombre) != 0) {
		 if (!BD.existeFichero(nombre)) {
			 this.nombre = nombre;
			 if (BD.setFichero(this,nombreViejo)){ /* Actualización de los datos en la base de datos */
				 return true;
			 }
			 else {
				 
				 /* Ha habido un error al almacenar los datos en la base,
				  * recuperamos los datos anteriores */
				 
				 this.nombre = nombreViejo;
				 return false; 
			 }
		 }
		 else return false;
	 }
	 else return true;
 }
 
 

 public boolean cambiarPermisos(boolean lec, boolean esc, boolean ejec, String nick) 
 {
  /*
   * Devuelve true sii se han podido cambiar los permisos de forma correcta,
   * false en caso contrario
   */
	 
	 if ((this.propietario.compareTo(nick) == 0)) {
		 /* sólo el propietario puede cambiar los permisos */
		 
		 return setPermisos(lec, esc, ejec);
		 
	 }
	 else{
		 return false;
	 } 
 }

 
 public boolean borrarFichero(String nick) 
 {
  /* 
   * Devuelve true sii el fichero se ha borrado de la base de datos 
   * correctamente, false en caso contrario
   */
	 if ((this.propietario.compareTo(nick) == 0) || (nick.compareTo("admin") == 0)) {
		 if (BD.borraFichero(nombre)){
			 
			 /* la eliminación se ha realizado correctamente */
			 return true;
		 }else{
			 
			 /* ha ocurrido algún error al borrar el fichero */
			 return false;
		 }
		 
	 }else{
		 /* los demás usuarios no pueden borrar el fichero */
		 return false;
	 }
	 	 
 }
 
 
 public boolean leer(String nick) 
 {
  /*
   * Devuelve true sii el usuario con nick = 'nick' tiene permiso de lectura
   */
	 
	 if ((this.propietario.compareTo(nick) == 0) || (nick.compareTo("admin") == 0)) {
		 /* el propietario y el administrador tienen todos los permisos */
		 return true;
		 
	 }
	 else{
		 /* hay que comprobar si los demas tienen permisos */
		 return getLectura();
	 }
	 
 }
 
 
 public boolean escribir(String nick) 
 {
  /*
   * Devuelve true sii el usuario con nick = 'nick' tiene permiso de escritura
   */
		 
	 if ((this.propietario.compareTo(nick) == 0) || (nick.compareTo("admin") == 0)) {
		 /* el propietario y el administrador tienen todos los permisos */
		 return true;
		 
	 }
	 else{
		 /* hay que comprobar si los demas tienen permisos */
		 return getEscritura();
	 }
	 
 }
 
 
 public boolean ejecutar(String nick) 
 {
  /*
   * Devuelve true sii el usuario con nick = 'nick' tiene permiso de lectura
   */
		 
	 if ((this.propietario.compareTo(nick) == 0) || (nick.compareTo("admin") == 0)) {
		 /* el propietario y el administrador tienen todos los permisos */	 
		 return true;
		 
	 }
	 else{
		 /* hay que comprobar si los demas tienen permisos */	 
		 return getEjecucion();
	 }

 }
 
 
}  