/** Proyecto: MIFISYS
 * Fichero:  VentanaLogin.java
 * Utilidad: Permite iniciar sesión en la aplicación
 * Autores:  Marcos Mainar Lalmolda       - 550710
 *           Ismael Saad Garcia           - 547942
 *           Sergio Romero Pradas         - 551382
 *           Luis Canales Mayo            - 551072
 *           Jose Javier Colomer Vieitez  - 550372
 *
 */

package interfaz;

import java.awt.Container;
import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import usuarios.Usuario;
import interfaz.Interfaz;

public class VentanaLogin extends JFrame {

	    // Atributos
        JFrame ventanaLogin;
        Container containerLogin, containerTextoLogin, containerBotonLogin;
        JLabel[] labelLogin = new JLabel[2];
        JTextField textoNick = new JTextField();
        JPasswordField textoPassword = new JPasswordField();
        JButton[] botonLogin = new JButton[2];
        GridBagConstraints c = new GridBagConstraints();
	
        public VentanaLogin() {

           // Creamos la ventana
           ventanaLogin = new JFrame("Login");
           ventanaLogin.setBounds(200,200,1500,1500);
           ventanaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
           // Establecemos el layout del contenedor principal
           containerLogin = new Container();
           containerLogin.setLayout(new GridBagLayout());
        
           // Contenedor auxiliar con las etiquetas y los campos de texto
           containerTextoLogin = new Container();
           containerTextoLogin.setLayout(new GridLayout(2,2));
        
           // Definición de las etiquetas y los campos de texto
           labelLogin[0] = new JLabel("Nombre de usuario:"); textoNick = new JTextField(20);
           labelLogin[1] = new JLabel("Password:"); textoPassword = new JPasswordField(20);
         
           // Añadimos las etiquetas y los campos de texto
           containerTextoLogin.add(labelLogin[0]); containerTextoLogin.add(textoNick);
           containerTextoLogin.add(labelLogin[1]); containerTextoLogin.add(textoPassword);
        
           // Contenedor auxiliar para los botones
           containerBotonLogin = new Container();
           containerBotonLogin.setLayout(new GridBagLayout());
		   c.insets = new Insets(0,50,0,50);
		   c.fill = GridBagConstraints.NONE;
		   c.anchor = GridBagConstraints.CENTER;
		    
		   // Creamos los botones y aadimos el actionListener
           botonLogin[0] = new JButton("Iniciar sesión");
           botonLogin[1] = new JButton("Salir");
        
           botonLogin[0].setActionCommand("Inicio");
           botonLogin[1].setActionCommand("Salir");
        
           botonLogin[0].addActionListener(new ManejadorBotonLogin());
           botonLogin[1].addActionListener(new ManejadorBotonLogin());
        
           // Situamos los componentes en la ventana
           c.gridheight = 10;
           c.gridx = 1;
           c.gridy = 1;
           c.weightx = 1;
           c.weighty = 1;
           containerBotonLogin.add(botonLogin[0],c);
            
           c.gridx++;
           containerBotonLogin.add(botonLogin[1],c);
        
           c.insets = new Insets(10,10,10,10); 
           c.gridx = 1;
           c.gridy = 1;
           containerLogin.add(containerTextoLogin,c);
           c.gridy = 12;
           containerLogin.add(containerBotonLogin,c);
     
           // Finalizamos la ventana
           ventanaLogin.setContentPane(containerLogin);
           ventanaLogin.pack();
           ventanaLogin.setVisible(true);
           ventanaLogin.setLocationRelativeTo(null);
           ventanaLogin.setDefaultCloseOperation(EXIT_ON_CLOSE);
           ventanaLogin.setResizable(false);
        }

        
        
        private class ManejadorBotonLogin implements ActionListener{ 
        // Action listener para los botones de la ventana de login
           public void actionPerformed(ActionEvent e) {
              if (e.getActionCommand().equals("Inicio")) {
                  // Inicio de la aplicación con los datos que da la ventana
            	  Usuario u = new Usuario(textoNick.getText());
            	  if (u.getNick() != null) {
            		  if (u.comprobarPassword(String.valueOf(textoPassword.getPassword()))) {
            			  if (u.getNick().compareTo("admin") == 0) {
            				  ventanaLogin.dispose();
            				  Interfaz.vLogin.dispose();
            				  Interfaz.vAdmin = new VentanaAdministrador(u);
            			  }
            			  else {
            				  ventanaLogin.dispose();
            				  Interfaz.vLogin.dispose();
            				  Interfaz.vUsuario = new VentanaUsuario(u);
            			  }
            		  }
            		  else {
            			  JOptionPane.showMessageDialog(new JFrame(), "Password incorrecto");
            			  textoPassword.setText("");
            		  }
            	  }
            	  else {
            		  JOptionPane.showMessageDialog(new JFrame(), "El usuario no existe");
            	  }
              }
              if (e.getActionCommand().equals("Salir")) {
                 // Fin de la aplicación
                 System.exit(0);
              }
           }
        }
        
}