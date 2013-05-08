/** Proyecto: MIFISYS
 * Fichero:  VentanaModificacionUsuario.java
 * Utilidad: Permite modificar un usuario.
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
import usuarios.*;

public class VentanaModificacionUsuario extends JFrame {

	    // Atributos
        JFrame ventanaModificacionUsuario;
        Container containerModificacionUsuario, containerTextoModificacionUsuario, containerBotonModificacionUsuario;
        JLabel[] labelModificacionUsuario = new JLabel[2];
        JPasswordField[] textoPassword = new JPasswordField[2];
        JButton[] botonModificacionUsuario = new JButton[2];
        GridBagConstraints c = new GridBagConstraints();
        Usuario u;
	
        public VentanaModificacionUsuario(Usuario user) {
        	
           u = user;
           // Creación de la ventana
           ventanaModificacionUsuario = new JFrame("Modificación de password del usuario");
           ventanaModificacionUsuario.setBounds(200,200,1500,1500);
           ventanaModificacionUsuario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
           // Contenedor principal, con su layout
           containerModificacionUsuario = new Container();
           containerModificacionUsuario.setLayout(new GridBagLayout());
        
            // Contenedor secundario para texto y etiquetas
            containerTextoModificacionUsuario = new Container();
            containerTextoModificacionUsuario.setLayout(new GridLayout(3,2));
        
             // Definición de las etiquetas y campos de texto
             labelModificacionUsuario[0] = new JLabel("Nuevo password:"); textoPassword[0] = new JPasswordField(20);
             labelModificacionUsuario[1] = new JLabel("Repita password:"); textoPassword[1] = new JPasswordField(20);
         
            // Lo incorporamos al contenedor secundario
            containerTextoModificacionUsuario.add(labelModificacionUsuario[0]); containerTextoModificacionUsuario.add(textoPassword[0]);
            containerTextoModificacionUsuario.add(labelModificacionUsuario[1]); containerTextoModificacionUsuario.add(textoPassword[1]);
     
            // Contenedor para los botones
            containerBotonModificacionUsuario = new Container();
            containerBotonModificacionUsuario.setLayout(new GridBagLayout());
		    c.insets = new Insets(0,50,0,50);
		    c.fill = GridBagConstraints.NONE;
		    c.anchor = GridBagConstraints.CENTER;
		    
		     // Creamos los botones de la ventana y añadimos los actionListeners
             botonModificacionUsuario[0] = new JButton("Aceptar");
             botonModificacionUsuario[1] = new JButton("Cancelar");
        
             botonModificacionUsuario[0].setActionCommand("Aceptar");
             botonModificacionUsuario[1].setActionCommand("Cancelar");
        
             botonModificacionUsuario[0].addActionListener(new ManejadorBotonModificacionUsuario());
             botonModificacionUsuario[1].addActionListener(new ManejadorBotonModificacionUsuario());
        
            // Medidas para situarlos en la ventana
            c.gridheight = 10;
            c.gridx = 1;
            c.gridy = 1;
            c.weightx = 1;
            c.weighty = 1;
            containerBotonModificacionUsuario.add(botonModificacionUsuario[0],c);
            
            c.gridx++;
            containerBotonModificacionUsuario.add(botonModificacionUsuario[1],c);
        
           c.insets = new Insets(10,10,10,10); 
           c.gridx = 1;
           c.gridy = 1;
           containerModificacionUsuario.add(containerTextoModificacionUsuario,c);
           c.gridy = 12;
           containerModificacionUsuario.add(containerBotonModificacionUsuario,c);
           
           // Finalizamos la ventana
           ventanaModificacionUsuario.setContentPane(containerModificacionUsuario);
           ventanaModificacionUsuario.pack();
           ventanaModificacionUsuario.setVisible(true);
           ventanaModificacionUsuario.setLocationRelativeTo(null);
           ventanaModificacionUsuario.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
           ventanaModificacionUsuario.setResizable(false);
           
        }

        private class ManejadorBotonModificacionUsuario implements ActionListener{  
        // Action listener para la ventana de modificación de usuarios
           public void actionPerformed(ActionEvent e) {
              if (e.getActionCommand().equals("Aceptar")) {
                  // Modificación de password de usuario
            	  if (String.valueOf(textoPassword[0].getPassword()).compareTo(String.valueOf(textoPassword[1].getPassword())) == 0) {
            	    boolean exito = u.cambiarPassword(String.valueOf(textoPassword[0].getPassword()));
            	    if (exito) {
        	  			JOptionPane.showMessageDialog(new JFrame(), "Contraseña cambiada correctamente");
            	  		ventanaModificacionUsuario.dispose();
            	  		Interfaz.vModificarUsuario.dispose();	
        	  		}
        	  		else 
        	  		{
        	  			JOptionPane.showMessageDialog(new JFrame(), "La contraseña no ha podido ser cambiada");
        	  		}
            	  }
            	  else {
            		JOptionPane.showMessageDialog(new JFrame(), "Las contraseñas no coinciden");
            		textoPassword[0].setText("");
            		textoPassword[1].setText("");
            	  }
              }
              if (e.getActionCommand().equals("Cancelar")) {
        	      ventanaModificacionUsuario.dispose();
        	      Interfaz.vModificarUsuario.dispose();
              }
           }
        }
}
