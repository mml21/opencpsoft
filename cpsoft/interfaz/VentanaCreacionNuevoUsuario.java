/** Proyecto: MIFISYS
 * Fichero:  VentanaCreacionNuevoUsuario.java
 * Utilidad: Permite la creación de nuevos usuarios.
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
import usuarios.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class VentanaCreacionNuevoUsuario extends JFrame {

	    // Atributos
        JFrame ventanaCreacionNuevoUsuario;
        Container containerCreacionNuevoUsuario, containerTextoCreacionNuevoUsuario, containerBotonCreacionNuevoUsuario;
        JLabel[] labelCreacionNuevoUsuario = new JLabel[3];
        JTextField textoCreacionNuevoUsuario = new JTextField();
        JButton[] botonCreacionNuevoUsuario = new JButton[2];
        JPasswordField[] textoPassword = new JPasswordField[2];
        GridBagConstraints c = new GridBagConstraints();
        Usuario user;
	
        public VentanaCreacionNuevoUsuario(Usuario u) {

           user = u;
           // Creación de la ventana
           ventanaCreacionNuevoUsuario = new JFrame("Creación de nuevo usuario");
           ventanaCreacionNuevoUsuario.setBounds(200,200,1500,1500);
           ventanaCreacionNuevoUsuario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
           // Contenedor principal, con su layout
           containerCreacionNuevoUsuario = new Container();
           containerCreacionNuevoUsuario.setLayout(new GridBagLayout());
        
            // Contenedor secundario para texto y etiquetas
            containerTextoCreacionNuevoUsuario = new Container();
            containerTextoCreacionNuevoUsuario.setLayout(new GridLayout(3,2));
        
             // Definicin de las etiquetas y campos de texto
             labelCreacionNuevoUsuario[0] = new JLabel("Nick:"); textoCreacionNuevoUsuario = new JTextField(20);
             labelCreacionNuevoUsuario[1] = new JLabel("Password:"); textoPassword[0] = new JPasswordField(20);
             labelCreacionNuevoUsuario[2] = new JLabel("Repita password:"); textoPassword[1] = new JPasswordField(20);
         
            // Lo incorporamos al contenedor secundario
            containerTextoCreacionNuevoUsuario.add(labelCreacionNuevoUsuario[0]); containerTextoCreacionNuevoUsuario.add(textoCreacionNuevoUsuario);
            containerTextoCreacionNuevoUsuario.add(labelCreacionNuevoUsuario[1]); containerTextoCreacionNuevoUsuario.add(textoPassword[0]);
            containerTextoCreacionNuevoUsuario.add(labelCreacionNuevoUsuario[2]); containerTextoCreacionNuevoUsuario.add(textoPassword[1]);
        
            // Contenedor para los botones
            containerBotonCreacionNuevoUsuario = new Container();
            containerBotonCreacionNuevoUsuario.setLayout(new GridBagLayout());
		    c.insets = new Insets(0,50,0,50);
		    c.fill = GridBagConstraints.NONE;
		    c.anchor = GridBagConstraints.CENTER;
		    
		     // Creamos los botones de la ventana y aadimos los actionListeners
             botonCreacionNuevoUsuario[0] = new JButton("Aceptar");
             botonCreacionNuevoUsuario[1] = new JButton("Cancelar");
        
             botonCreacionNuevoUsuario[0].setActionCommand("Aceptar");
             botonCreacionNuevoUsuario[1].setActionCommand("Cancelar");
        
             botonCreacionNuevoUsuario[0].addActionListener(new ManejadorBotonCreacionNuevoUsuario());
             botonCreacionNuevoUsuario[1].addActionListener(new ManejadorBotonCreacionNuevoUsuario());
        
            // Medidas para situarlos en la ventana
            c.gridheight = 10;
            c.gridx = 1;
            c.gridy = 1;
            c.weightx = 1;
            c.weighty = 1;
            containerBotonCreacionNuevoUsuario.add(botonCreacionNuevoUsuario[0],c);
            
            c.gridx++;
            containerBotonCreacionNuevoUsuario.add(botonCreacionNuevoUsuario[1],c);
        
           c.insets = new Insets(10,10,10,10); 
           c.gridx = 1;
           c.gridy = 1;
           containerCreacionNuevoUsuario.add(containerTextoCreacionNuevoUsuario,c);
           c.gridy = 12;
           containerCreacionNuevoUsuario.add(containerBotonCreacionNuevoUsuario,c);
           
           // Finalizamos la ventana
           ventanaCreacionNuevoUsuario.setContentPane(containerCreacionNuevoUsuario);
           ventanaCreacionNuevoUsuario.pack();
           ventanaCreacionNuevoUsuario.setVisible(true);
           ventanaCreacionNuevoUsuario.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
           ventanaCreacionNuevoUsuario.setLocationRelativeTo(null);
           ventanaCreacionNuevoUsuario.setResizable(false);
           
        }

        private class ManejadorBotonCreacionNuevoUsuario implements ActionListener{  
        // Action listener para la ventana de creación de nuevos usuarios
           public void actionPerformed(ActionEvent e) {
              if (e.getActionCommand().equals("Aceptar")) {
                      // Creación de nuevo usuario
        	  		if (String.valueOf(textoPassword[0].getPassword()).compareTo(String.valueOf(textoPassword[1].getPassword())) == 0) {
            	  		boolean exito =  Administrador.crearUsuario(textoCreacionNuevoUsuario.getText(),
            	  				                                    String.valueOf(textoPassword[0].getPassword()));
            	  		if (exito) {
            	  			JOptionPane.showMessageDialog(new JFrame(), "Usuario creado correctamente");
            	  			((VentanaAdministracionUsuarios.Tabla)VentanaAdministracionUsuarios.tabla.getModel()).addRow(
            	  							new Usuario(textoCreacionNuevoUsuario.getText()));
                	  		ventanaCreacionNuevoUsuario.dispose();
                	  		Interfaz.vNuevoUsuario.dispose();	
            	  		}
            	  		else 
            	  		{
            	  			JOptionPane.showMessageDialog(new JFrame(), "El usuario no ha podido ser creado");
            	  		}
        	  		}
        	  		else
        	  		{
        	  			JOptionPane.showMessageDialog(new JFrame(), "Las contraseñas no coinciden");
        	  			textoPassword[0].setText("");
        	  			textoPassword[1].setText("");
        	  		}
              }
              if (e.getActionCommand().equals("Cancelar")) {
                  // Vuelta a la ventana de administración de usuarios
            	  ventanaCreacionNuevoUsuario.dispose();
        	      Interfaz.vNuevoUsuario.dispose();
              }
           }
        }
    
}