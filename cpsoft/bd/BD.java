/** Proyecto: MIFISYS
 * Fichero:  BD.java
 * Utilidad: Gestiona el acceso a la base de datos.
 * Autores:  Marcos Mainar Lalmolda       - 550710
 *           Ismael Saad Garcia           - 547942
 *           Sergio Romero Pradas         - 551382
 *           Luis Canales Mayo            - 551072
 *           Jose Javier Colomer Vieitez  - 550372
 *
 */

package bd;

import java.util.Vector;
import java.util.Random;
import fichero.Fichero;
import usuarios.*;
import java.sql.*;
import org.apache.commons.lang.RandomStringUtils; // para las cadenas aleatorias
 

public class BD 
{
	  
	/* Operaciones de la base de datos */
	
	static Connection con;

	public static boolean conectar() 
	/* Pre: Cierto
	 * Post: Conecta a la BD, crea las tablas "Usuario" y "Fichero"
	 *       e introduce los datos del usuario administrador de la
	 *       aplicación. Si esto se realiza correctamente, devuelve
	 *       true. Si hay algún problema, devuelve false.
	 */
	{
	   // Datos para conectar a la BD
       String url ="jdbc:oracle:thin:@den.cps.unizar.es:1521:vicious";
       String usernameMio ="m6921579";
       String passwordMio ="lalala";

       try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         con = DriverManager.getConnection(url, usernameMio, passwordMio);
 	     con.setAutoCommit(true);
 	     return true;
       }
       catch (ClassNotFoundException e) {
         return false;
       }
       catch (SQLException e) {
          return false;
       }
	}


	public static boolean borrarTabla(String nombreTab) 
	/* Pre: Estamos conectados a la BD.
	 * Post: Si existe, borra la tabla de nombre nombreTab de la BD y 
	 *       devuelve true.
	 *       Si no existe dicha tabla o se da algun tipo de error devuelve
	 *       false.
	 */
	{
       try
       {
         Statement stmtBorrar = con.createStatement();
         stmtBorrar.executeQuery("DROP TABLE " + nombreTab + " cascade constraints");
         stmtBorrar.close();
         return true;
       }
       catch (SQLException e) {
    	 return false;
       }

	}


	public static boolean crearTablas() 
	/* Pre: Estamos conectados a la BD. 
	 * Post: Crea las tablas Usuario y Fichero de la aplicación y devuelve true.
	 *       Si ocurre algún problema creando las tablas, devuelve false.
	 */
	{
       borrarTabla("Usuario");
       borrarTabla("Fichero");
       	
       try 
       {
           Statement stmtCrear = con.createStatement();
           stmtCrear.executeQuery("CREATE TABLE Usuario (nick VARCHAR(20) PRIMARY KEY "
                 + "NOT NULL, password VARCHAR(20) NOT NULL)");
           stmtCrear.executeQuery("CREATE TABLE Fichero (nombreFichero VARCHAR(30) PRIMARY KEY "
                 + "NOT NULL, read NUMBER(1) NOT NULL, write NUMBER(1) NOT NULL, execute NUMBER(1) NOT NULL, "
                 + "propietario VARCHAR(20) NOT NULL, CONSTRAINT nick_FK FOREIGN KEY (propietario) "
                 + "REFERENCES Usuario(nick) ON DELETE CASCADE)");         
          stmtCrear.close();
          return true;
       }
       catch (SQLException e) {
    	  System.err.println("SQLException: " + e.getMessage());
          System.out.println("Alguna de las tablas no se ha creado");
          return false;
       }
	}
	
	
	public static boolean generarDatosAleatorios()
	/* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
	 *      estan creadas en la BD.
	 * Post: Genera datos aleatorios de usuarios y ficheros y los introduce
	 *       en las tablas correspondientes de la BD. Si estose realiza 
	 *       correctamente devuelve true. Si ocurre algún error, devuelve false.
	 */
	{
		Vector<Usuario> datosUsuarios = new Vector<Usuario>();
		datosUsuarios = generarDatosUsuarios();
		if (datosUsuarios.size() > 0) return generarDatosFicheros(datosUsuarios);
		else return false;
	}
	
	
	public static Vector<Usuario> generarDatosUsuarios() 
	/* Pre: Estamos conectados a la BD y la tabla "Usuario" esta 
	 *      creada en la BD.
	 * Post: Genera datos aleatorios de usuarios y los introduce
	 *       en la tabla "Usuario" de la BD. Si se introducen correctamente 
	 *       devuelve los datos introducidos almacenados en un vector. 
	 *       Si hay algun error, devuelve null.
	 */           
	{
	  int i = 0;
	  Vector<Usuario> datosUsuarios = new Vector<Usuario>();
	  
      while (i < 100)
	  {
		String pass = RandomStringUtils.randomAlphanumeric(10);
		String nick = RandomStringUtils.randomAlphabetic(6);
		try {
			Usuario user = new Usuario(nick, pass, true);
		    datosUsuarios.add(user);
		    i++;
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	  }
      return datosUsuarios;
	}
	
	
	public static boolean generarDatosFicheros(Vector <Usuario> datosUsuarios) 
	/* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
	 *      estan creadas en la BD.
	 *      datosUsuarios contiene datos de objetos de la clase Usuario
	 *      correctos.
	 * Post: Genera datos aleatorios de ficheros y los introduce
	 *       en la tabla "Fichero" de la BD. Si se introducen correctamente 
	 *       devuelve los datos true. Si hay algun error, devuelve false.
	 */     
	{
      int i = 0;
	  // Semilla para los random
	  Random rand = new Random();
	  while (i < 100)
	  {
	    String nombreFichero = RandomStringUtils.randomAlphabetic(10);
	    int n = (Math.abs(rand.nextInt()) % (datosUsuarios.size() -1));
		String propietario = datosUsuarios.elementAt(n).getNick();
		boolean lect = ((rand.nextInt() % 2) != 0);
		boolean esc = ((rand.nextInt() % 2) != 0);
	    boolean ejec = ((rand.nextInt() % 2) != 0);
		try {
			new Fichero(nombreFichero, lect, esc, ejec, propietario, true);
			i++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	  }
	  return true;
	}
	
	
	public static void introducirDatosAdmin(String passAdmin) throws SQLException
	/* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
	 *      estan creadas en la BD.
	 * Post: Introduce en la BD los datos del administrador del sistema.
	 */   
	{
		PreparedStatement insertarAdmin;
		String cadInsercion = "INSERT into Usuario values (?,?)";
		String nick = "admin";
		String pass = passAdmin;
		insertarAdmin = con.prepareStatement(cadInsercion);
		insertarAdmin.setString(1, nick);
		insertarAdmin.setString(2, pass);
		insertarAdmin.executeUpdate();				
	}
		
	
	  /*               Usuarios                       */

	  public static boolean creaUsuario (Usuario user) throws SQLException 
	  /* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
		 *      estan creadas en la BD.
		 * Post: Crea en la base de datos un nuevo usuario user.
	  */
	  {		  
	    PreparedStatement insertarUsuarios;
	    String cadInsercion = "INSERT into Usuario values (?,?)";
	    insertarUsuarios = con.prepareStatement(cadInsercion);
		insertarUsuarios.setString(1, user.getNick());
		insertarUsuarios.setString(2, user.getPassword());
		insertarUsuarios.executeUpdate();
		return true;
	  }    


	  public static Usuario getUsuario (String nick) 
	  /* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
	   *      estan creadas en la BD.
	   * Post: Si existe en la BD un usuario de nick "nick" devuelve
	   *       un objeto Usuario con los datos de dicho usuario.
	   *       En caso contrario, devuelve null.
	  */
	  {
	    String consultaListado = "SELECT * FROM Usuario WHERE Usuario.nick = ?";	     
		ResultSet resul;				 
	    PreparedStatement getUsuario;
		try 
		{
			getUsuario = con.prepareStatement(consultaListado);
		    getUsuario.setString(1, nick);
			resul = getUsuario.executeQuery();
			if (resul.next())
			{
			  Usuario u = new Usuario(resul.getString("nick"), resul.getString("password"), false);
			  return u;
			}
			else return null;  
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	  }


	  public static boolean setUsuario (Usuario user) 
	  /* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
	   *      estan creadas en la BD.
	   * Post: Si existe en la BD un usuario user actualiza su password y
	   *       devuelve true. En caso contrario, devuelve false.
	  */	  
	  {	
		PreparedStatement setUsuario;
		String cadModif = "UPDATE Usuario SET password = ? where nick = ?";
		try 
		{
		  setUsuario = con.prepareStatement(cadModif);
		  setUsuario.setString(1, user.getPassword());
		  setUsuario.setString(2, user.getNick());
		  setUsuario.executeUpdate();
		  setUsuario.close();
		  return true;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	  }


	  public static boolean borraUsuario (String nick)  
	  /* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
	   *      estan creadas en la BD.
	   * Post: Si existe en la BD un usuario de nick "nick" lo borra de la BD y
	   *       devuelve true. En caso contrario, devuelve false.
	  */
	  {
	    PreparedStatement borrarUsuario;
	    String cadInsercion = "DELETE FROM Usuario WHERE nick = ?";
	    try 
	    {
		  borrarUsuario = con.prepareStatement(cadInsercion);
		  borrarUsuario.setString(1, nick);
		  borrarUsuario.executeUpdate();
		  borrarUsuario.close();
	      return true;  
		} 
	    catch (SQLException e) {
			// TODO Auto-generated catch block
		  e.printStackTrace();
		  return false;
		}

	  }


	  public static boolean existeUsuario (String nick) 
	  /* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
	   *      estan creadas en la BD.
	   * Post: Si existe en la BD un usuario de nick "nick" devuelve true. 
	   *       En caso contrario, devuelve false.
	  */
	  {
	     String consultaListado = "SELECT * FROM Usuario WHERE nick = ?";	     
		 ResultSet resul;
		 
		 try 
		 {
			PreparedStatement existeUsuario = con.prepareStatement(consultaListado);
			existeUsuario.setString(1, nick);
			resul = existeUsuario.executeQuery();
			if (resul.next())
			{
				existeUsuario.close();
				return true;
			}
			else
			{
			  existeUsuario.close();
			  return false;
			}

		 } 
		 catch (SQLException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}  		  
		
	  }


	  public static Vector<Usuario> getListaUsuarios()  
	  /* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
	   *      estan creadas en la BD.
	   * Post: Devuelve un vector con la lista de usuarios almacenados en la
	   *       BD. Si ocurre algún error, devuelve null.
	  */
	  {
	    Vector<Usuario> listaUsuarios = new Vector<Usuario>();			 
		ResultSet resul;
		int i = 0; // Número de filas devueltas por la consulta
		String consultaListado = "SELECT * FROM Usuario ORDER BY nick";
		PreparedStatement listarUsuarios;
		try 
		{
			listarUsuarios = con.prepareStatement(consultaListado);
			resul = listarUsuarios.executeQuery();
			while(resul.next()) 
			{
			  listaUsuarios.add(new Usuario(resul.getString("nick"), resul.getString("password"), false));
			  i++;
			}
					
			listarUsuarios.close();				 
		    return listaUsuarios;	
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	  }


	  /*                Operaciones con ficheros                    */

	  public static boolean creaFichero (Fichero fich) throws SQLException  
	  /* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
		 *      estan creadas en la BD.
		 * Post: Crea en la base de datos un nuevo fichero fich.
	  */
	  {
		PreparedStatement insertarFichero;
	    String cadInsercion = "INSERT into Fichero values (?,?,?,?,?)";
		insertarFichero = con.prepareStatement(cadInsercion);
		insertarFichero.setString(1, fich.getNombre());
		insertarFichero.setBoolean(2, fich.getLectura());
		insertarFichero.setBoolean(3, fich.getEscritura());
		insertarFichero.setBoolean(4, fich.getEjecucion());
		insertarFichero.setString(5, fich.getPropietario());
		insertarFichero.executeUpdate();
		return true;	
	  }


	  public static Fichero getFichero (String nombre) 
	  /* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
	   *      estan creadas en la BD.
	   * Post: Si existe en la BD un fichero de nombre "nombre" devuelve
	   *       un objeto Fichero con los datos de dicho usuario.
	   *       En caso contrario, devuelve null.
	  */
	  {		  
	    String consultaListado = "SELECT * FROM Fichero WHERE nombreFichero = ?";	     
	    ResultSet resul;				 
		PreparedStatement getFichero;
		try {
			getFichero = con.prepareStatement(consultaListado);
			getFichero.setString(1, nombre);
		    resul = getFichero.executeQuery();
		    if (resul.next())
		    {
		      Fichero f = new Fichero(resul.getString("nombreFichero"), resul.getBoolean("read"),
		                    resul.getBoolean("write"), resul.getBoolean("execute"),
		                    resul.getString("propietario"), false);
		      getFichero.close();
		      return f;
		    }
		    else
		    {
		      getFichero.close();
		      return null;
		    }
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}    
	  }


	  public static boolean setFichero (Fichero fich, String nombreViejo) 
	  /* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
	   *      estan creadas en la BD.
	   * Post: Si existe en la BD un fichero fich actualiza sus permisos y
	   *       y su nombre y devuelve true. En caso contrario, devuelve false.
	  */
	  {
	    PreparedStatement setFichero;
		String cadModif = "UPDATE Fichero SET nombreFichero = ?, read = ?, write = ?, execute = ? "
			            + "where nombreFichero = ?";
		try 
		{
		  setFichero = con.prepareStatement(cadModif);
		  setFichero.setString(1, fich.getNombre());
		  setFichero.setBoolean(2, fich.getLectura());
		  setFichero.setBoolean(3, fich.getEscritura());
	   	  setFichero.setBoolean(4, fich.getEjecucion());
		  setFichero.setString(5, nombreViejo);
	      setFichero.executeUpdate();
		  setFichero.close();
		  return true;  
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
		  e.printStackTrace();
		  return false;
		}

	  }
	  

	  public static boolean borraFichero (String nombre) 
	  /* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
	   *      estan creadas en la BD.
	   * Post: Si existe en la BD un fichero de nombre "nombre" lo borra de la BD y
	   *       devuelve true. En caso contrario, devuelve false.
	  */
	  {
        PreparedStatement borrarFichero;
		String cadInsercion = "DELETE FROM Fichero WHERE nombreFichero = ?";
		try 
		{
		  borrarFichero = con.prepareStatement(cadInsercion);
		  borrarFichero.setString(1, nombre);
		  borrarFichero.executeUpdate();
		  borrarFichero.close();
		  return true;  
		} 
		catch (SQLException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
		  return false;
		}
	  }


	  public static boolean existeFichero (String nombre) 
	  /* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
	   *      estan creadas en la BD.
	   * Post: Si existe en la BD un fichero de nombre "nombre" devuelve true. 
	   *       En caso contrario, devuelve false.
	  */
	  {
        String consultaListado = "SELECT propietario FROM Fichero WHERE nombreFichero = ?";	     
		ResultSet resul;
			 
		PreparedStatement existeFichero;
		try 
		{
		  existeFichero = con.prepareStatement(consultaListado);
		  existeFichero.setString(1, nombre);
		  resul = existeFichero.executeQuery();	        
		  if (resul.next())
		  {
			existeFichero.close();
			return true;
	      }
	      else
		  {
			existeFichero.close();
			return false;
		   }
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	  }


	  public static Vector<Fichero> getListaFicheros() 
	  /* Pre: Estamos conectados a la BD y las tablas "Usuario" y "Fichero"
	   *      estan creadas en la BD.
	   * Post: Devuelve un vector con la lista de ficheros almacenados en la
	   *       BD. Si ocurre algún error, devuelve null.
	  */
	  {
		  
	    Vector<Fichero> listaFicheros = new Vector<Fichero>();
	    ResultSet resul;
		int i = 0; // Número de filas devueltas por la consulta
		String consultaListado = "SELECT * FROM Fichero ORDER BY nombreFichero";
		PreparedStatement listarFicheros;
		try {
			listarFicheros = con.prepareStatement(consultaListado);
			resul = listarFicheros.executeQuery();
			while(resul.next()) 
			{
			  listaFicheros.add(new Fichero(resul.getString("nombreFichero"), resul.getBoolean("read"), 
					            resul.getBoolean("write"), resul.getBoolean("execute"), 
					            resul.getString("propietario"), false));
			  i++;
			}
					
			listarFicheros.close();				 
		    return listaFicheros;	
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	  }  	    
}    