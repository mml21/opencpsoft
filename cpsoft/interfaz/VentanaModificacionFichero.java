/** Proyecto: MIFISYS
 * Fichero:  VentanaModificacionFichero.java
 * Utilidad: Permite modificar un fichero.
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
import fichero.*;


public class VentanaModificacionFichero extends JFrame {

	    // Atributos
        JFrame ventanaModificacionFichero;
        Container containerModificacionFichero, containerTextoModificacionFichero, 
                  containerPermisosModificacionFichero, containerBotonModificacionFichero;
        JLabel[] labelModificacionFichero = new JLabel[5];
        JTextField textoModificacionFichero = new JTextField();
        JCheckBox[] marcasModificacionFichero = new JCheckBox[3];
        JButton[] botonModificacionFichero = new JButton[2];
        GridBagConstraints c = new GridBagConstraints();
        Usuario user;
        Fichero fich;
        int indice;
        
	
        public VentanaModificacionFichero(Fichero f, Usuario u, int i) {

           // Creación de la ventana
           ventanaModificacionFichero = new JFrame("Modificación de fichero");
           user = u;
           fich = f;
           indice = i;
           ventanaModificacionFichero.setBounds(200,200,1500,1500);
           ventanaModificacionFichero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
           // Contenedor principal, con su layout
           containerModificacionFichero = new Container();
           containerModificacionFichero.setLayout(new GridBagLayout());
        
            // Contenedor secundario para texto y etiquetas
            containerTextoModificacionFichero = new Container();
            containerTextoModificacionFichero.setLayout(new GridLayout(1,2));
        
             // Definición de las etiquetas y campos de texto
             labelModificacionFichero[0] = new JLabel("Nombre:"); textoModificacionFichero = new JTextField(20);
             
            // Lo incorporamos al contenedor secundario
            containerTextoModificacionFichero.add(labelModificacionFichero[0]); containerTextoModificacionFichero.add(textoModificacionFichero);
           
            // Contenedor para los permisos
            containerPermisosModificacionFichero = new Container();
            containerPermisosModificacionFichero.setLayout(new GridLayout(4,2));
            
             // Añadimos lo necesario para gestionar permisos
             labelModificacionFichero[1] = new JLabel("Permisos:");  
             marcasModificacionFichero[0] = new JCheckBox(); labelModificacionFichero[2] = new JLabel("Lectura"); 
             marcasModificacionFichero[1] = new JCheckBox(); labelModificacionFichero[3] = new JLabel("Escritura");
             marcasModificacionFichero[2] = new JCheckBox(); labelModificacionFichero[4] = new JLabel("Ejecución"); 

            // Incorporamos los elementos al contenedor de permisos
            containerPermisosModificacionFichero.add(labelModificacionFichero[1]);
            containerPermisosModificacionFichero.add(new JLabel(""));
            containerPermisosModificacionFichero.add(marcasModificacionFichero[0]);
            containerPermisosModificacionFichero.add(labelModificacionFichero[2]);
            containerPermisosModificacionFichero.add(marcasModificacionFichero[1]);
            containerPermisosModificacionFichero.add(labelModificacionFichero[3]);
            containerPermisosModificacionFichero.add(marcasModificacionFichero[2]);
            containerPermisosModificacionFichero.add(labelModificacionFichero[4]);                 
            
            // Contenedor para los botones
            containerBotonModificacionFichero = new Container();
            containerBotonModificacionFichero.setLayout(new GridBagLayout());
		    c.insets = new Insets(0,50,0,50);
		    c.fill = GridBagConstraints.NONE;
		    c.anchor = GridBagConstraints.CENTER;
		    
		     // Creamos los botones de la ventana y aadimos los actionListeners
             botonModificacionFichero[0] = new JButton("Aceptar");
             botonModificacionFichero[1] = new JButton("Cancelar");
        
             botonModificacionFichero[0].setActionCommand("Aceptar");
             botonModificacionFichero[1].setActionCommand("Cancelar");
        
             botonModificacionFichero[0].addActionListener(new ManejadorBotonModificacionFichero());
             botonModificacionFichero[1].addActionListener(new ManejadorBotonModificacionFichero());
        
            // Medidas para situarlos en la ventana
            c.gridheight = 10;
            c.gridx = 1;
            c.gridy = 1;
            c.weightx = 1;
            c.weighty = 1;
            containerBotonModificacionFichero.add(botonModificacionFichero[0],c);
            
            c.gridx++;
            containerBotonModificacionFichero.add(botonModificacionFichero[1],c);
        
           c.insets = new Insets(10,10,10,10); 
           c.gridx = 1;
           c.gridy = 1;
           containerModificacionFichero.add(containerTextoModificacionFichero,c);
           c.gridy = 12;
           containerModificacionFichero.add(containerPermisosModificacionFichero,c);
           c.gridy = 24;
           containerModificacionFichero.add(containerBotonModificacionFichero,c);
           
           
           textoModificacionFichero.setText(fich.getNombre());
           marcasModificacionFichero[0].setSelected(fich.getLectura());
           marcasModificacionFichero[1].setSelected(fich.getEscritura());
           marcasModificacionFichero[2].setSelected(fich.getEjecucion());
           
           
           // Finalizamos la ventana
           ventanaModificacionFichero.setContentPane(containerModificacionFichero);
           ventanaModificacionFichero.pack();
           ventanaModificacionFichero.setVisible(true);
           ventanaModificacionFichero.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
           ventanaModificacionFichero.setLocationRelativeTo(null);
           ventanaModificacionFichero.setResizable(false);
        }

        
        private class ManejadorBotonModificacionFichero implements ActionListener{  
        // Action listener para la ventana de modificación de ficheros
           public void actionPerformed(ActionEvent e) {
              if (e.getActionCommand().equals("Aceptar")) {
                      if (fich.cambiarPermisos(marcasModificacionFichero[0].isSelected(), 
                    		  			   marcasModificacionFichero[1].isSelected(),
                    		  			   marcasModificacionFichero[2].isSelected(),
                    		  			   user.getNick()) &&
                          fich.renombrar(textoModificacionFichero.getText())) {
                    	  JOptionPane.showMessageDialog(new JFrame(), "Fichero modificado correctamente");
                    	  ((VentanaFicherosUsuario.Tabla)VentanaFicherosUsuario.tabla.getModel()).updateRow(fich,indice);
                    	  ventanaModificacionFichero.dispose();
                    	  Interfaz.vModificarFichero.dispose();
                      }
                      else {
                    	  JOptionPane.showMessageDialog(new JFrame(), "No se ha podido modificar el fichero");
                      }
              }
              if (e.getActionCommand().equals("Cancelar")) {
            	  ventanaModificacionFichero.dispose();
            	  Interfaz.vModificarFichero.dispose();
              }
           }
        }

}