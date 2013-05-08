/** Proyecto: MIFISYS
 * Fichero:  VentanaCreacionNuevoFichero.java
 * Utilidad: Permite la creación de nuevos ficheros.
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
import fichero.Fichero;


public class VentanaCreacionNuevoFichero extends JFrame {

	
	    // Atributos
        JFrame ventanaCreacionNuevoFichero;
        Container containerCreacionNuevoFichero, containerTextoCreacionNuevoFichero, 
                  containerPermisosCreacionNuevoFichero, containerBotonCreacionNuevoFichero;
        JLabel[] labelCreacionNuevoFichero = new JLabel[5];
        JTextField textoCreacionNuevoFichero = new JTextField();
        JCheckBox[] marcasCreacionNuevoFichero = new JCheckBox[3];
        JButton[] botonCreacionNuevoFichero = new JButton[2];
        GridBagConstraints c = new GridBagConstraints();
        Usuario user;
	
        
        public VentanaCreacionNuevoFichero(Usuario u) {

           // Creación de la ventana
           ventanaCreacionNuevoFichero = new JFrame("Creación de nuevo fichero");
           user = u;
           ventanaCreacionNuevoFichero.setBounds(200,200,1500,1500);
           ventanaCreacionNuevoFichero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
           // Contenedor principal, con su layout
           containerCreacionNuevoFichero = new Container();
           containerCreacionNuevoFichero.setLayout(new GridBagLayout());
        
           // Contenedor secundario para texto y etiquetas
           containerTextoCreacionNuevoFichero = new Container();
           containerTextoCreacionNuevoFichero.setLayout(new GridLayout(1,2));
        
           // Definición de las etiquetas y campos de texto
           labelCreacionNuevoFichero[0] = new JLabel("Nombre:"); textoCreacionNuevoFichero = new JTextField(20);
             
           // Lo incorporamos al contenedor secundario
           containerTextoCreacionNuevoFichero.add(labelCreacionNuevoFichero[0]); containerTextoCreacionNuevoFichero.add(textoCreacionNuevoFichero);
           
           // Contenedor para los permisos
           containerPermisosCreacionNuevoFichero = new Container();
           containerPermisosCreacionNuevoFichero.setLayout(new GridLayout(4,2));
            
           // Añadimos lo necesario para gestionar permisos
           labelCreacionNuevoFichero[1] = new JLabel("Permisos:");  
           marcasCreacionNuevoFichero[0] = new JCheckBox(); labelCreacionNuevoFichero[2] = new JLabel("Lectura"); 
           marcasCreacionNuevoFichero[1] = new JCheckBox(); labelCreacionNuevoFichero[3] = new JLabel("Escritura");
           marcasCreacionNuevoFichero[2] = new JCheckBox(); labelCreacionNuevoFichero[4] = new JLabel("Ejecución"); 

           // Incorporamos los elementos al contenedor de permisos
           containerPermisosCreacionNuevoFichero.add(labelCreacionNuevoFichero[1]);
           containerPermisosCreacionNuevoFichero.add(new JLabel(""));
           containerPermisosCreacionNuevoFichero.add(marcasCreacionNuevoFichero[0]);
           containerPermisosCreacionNuevoFichero.add(labelCreacionNuevoFichero[2]);
           containerPermisosCreacionNuevoFichero.add(marcasCreacionNuevoFichero[1]);
           containerPermisosCreacionNuevoFichero.add(labelCreacionNuevoFichero[3]);
           containerPermisosCreacionNuevoFichero.add(marcasCreacionNuevoFichero[2]);
           containerPermisosCreacionNuevoFichero.add(labelCreacionNuevoFichero[4]);                 
            
           // Contenedor para los botones
           containerBotonCreacionNuevoFichero = new Container();
           containerBotonCreacionNuevoFichero.setLayout(new GridBagLayout());
           c.insets = new Insets(0,50,0,50);
           c.fill = GridBagConstraints.NONE;
		   c.anchor = GridBagConstraints.CENTER;
		    
		   // Creamos los botones de la ventana y aadimos los actionListeners
           botonCreacionNuevoFichero[0] = new JButton("Aceptar");
           botonCreacionNuevoFichero[1] = new JButton("Cancelar");
        
           botonCreacionNuevoFichero[0].setActionCommand("Aceptar");
           botonCreacionNuevoFichero[1].setActionCommand("Cancelar");
        
           botonCreacionNuevoFichero[0].addActionListener(new ManejadorBotonCreacionNuevoFichero());
           botonCreacionNuevoFichero[1].addActionListener(new ManejadorBotonCreacionNuevoFichero());
        
           // Medidas para situarlos en la ventana
           c.gridheight = 10;
           c.gridx = 1;
           c.gridy = 1;
           c.weightx = 1;
           c.weighty = 1;
           containerBotonCreacionNuevoFichero.add(botonCreacionNuevoFichero[0],c);
            
           c.gridx++;
           containerBotonCreacionNuevoFichero.add(botonCreacionNuevoFichero[1],c);
        
           c.insets = new Insets(10,10,10,10); 
           c.gridx = 1;
           c.gridy = 1;
           containerCreacionNuevoFichero.add(containerTextoCreacionNuevoFichero,c);
           c.gridy = 12;
           containerCreacionNuevoFichero.add(containerPermisosCreacionNuevoFichero,c);
           c.gridy = 24;
           containerCreacionNuevoFichero.add(containerBotonCreacionNuevoFichero,c);
           
           // Finalizamos la ventana
           ventanaCreacionNuevoFichero.setContentPane(containerCreacionNuevoFichero);
           ventanaCreacionNuevoFichero.pack();
           ventanaCreacionNuevoFichero.setVisible(true);
           ventanaCreacionNuevoFichero.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
           ventanaCreacionNuevoFichero.setLocationRelativeTo(null);
           ventanaCreacionNuevoFichero.setResizable(false);
           
        }

        
        private class ManejadorBotonCreacionNuevoFichero implements ActionListener{  
        // Action listener para la ventana de creacion de nuevos ficheros
           public void actionPerformed(ActionEvent e) {
              if (e.getActionCommand().equals("Aceptar")) {
                      // Creación de nuevo fichero
            	  	  try {
            	  		  Fichero f = new Fichero(textoCreacionNuevoFichero.getText(),
            	  			  				  marcasCreacionNuevoFichero[0].isSelected(),
            	  			  				  marcasCreacionNuevoFichero[1].isSelected(),
            	  			  				  marcasCreacionNuevoFichero[2].isSelected(),
            	  			  				  user.getNick(),
            	  			  				  true);
            	  		JOptionPane.showMessageDialog(new JFrame(), "Fichero creado correctamente");
            	  		((VentanaFicherosUsuario.Tabla)VentanaFicherosUsuario.tabla.getModel()).addRow(f);
              	      	ventanaCreacionNuevoFichero.dispose();
              	      	Interfaz.vNuevoFichero.dispose();
            	  	  }
            	  	  catch (Exception exc) {
            	  		JOptionPane.showMessageDialog(new JFrame(), "No se ha podido crear el fichero");
            	  	  }
              }
              if (e.getActionCommand().equals("Cancelar")) {           	  	 
            	      ventanaCreacionNuevoFichero.dispose();
            	      Interfaz.vNuevoFichero.dispose();
              }
           }
        }
        
}