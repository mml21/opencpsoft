/** Proyecto: MIFISYS
 * Fichero:  VentanaInstalador.java
 * Utilidad: Ventana del instalador de la aplicación.
 * Autores:  Marcos Mainar Lalmolda       - 550710
 *           Ismael Saad Garcia           - 547942
 *           Sergio Romero Pradas         - 551382
 *           Luis Canales Mayo            - 551072
 *           Jose Javier Colomer Vieitez  - 550372
 *
 */



import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import bd.BD;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;


public class VentanaInstalador extends JFrame {

        // Atributos
        JFrame ventanaDeInstalacion;
        Container containerInstalacion, containerTextoInstalacion, containerBotonInstalacion;
        JLabel[] labelInstalacion = new JLabel[3];
        JTextField textoInstalacion = new JTextField();
        JButton[] botonInstalacion = new JButton[2];
        JPasswordField[] textoPassword = new JPasswordField[2];
        GridBagConstraints c = new GridBagConstraints();
        JProgressBar progressBar;
        
        public VentanaInstalador() {
        
            //Creación de la ventana
            ventanaDeInstalacion = new JFrame("Instalación de MIFISYS");
            ventanaDeInstalacion.setBounds(200,200,1500,1500);
            ventanaDeInstalacion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
            // Contenedor principal, con su layout
            containerInstalacion = new Container();
            containerInstalacion.setLayout(new GridBagLayout());
     
            // Contenedor secundario para texto y etiquetas
            containerTextoInstalacion = new Container();
            containerTextoInstalacion.setLayout(new GridLayout(3,2));
            
            //Definición de las etiquetas y campos de texto
            labelInstalacion[0] = new JLabel("Nick:"); 
            JTextField textoInstalacion = new JTextField(20);
            textoInstalacion.setText("admin");
            textoInstalacion.setEditable(false);
            labelInstalacion[1] = new JLabel("Password:"); textoPassword[0] = new JPasswordField(20);
            labelInstalacion[2] = new JLabel("Repita password:"); textoPassword[1] = new JPasswordField(20);
      
            // Lo incorporamos al contenedor secundario
            containerTextoInstalacion.add(labelInstalacion[0]); containerTextoInstalacion.add(textoInstalacion);
            containerTextoInstalacion.add(labelInstalacion[1]); containerTextoInstalacion.add(textoPassword[0]);
            containerTextoInstalacion.add(labelInstalacion[2]); containerTextoInstalacion.add(textoPassword[1]);
            
            // Contenedor para los botones
            containerBotonInstalacion = new Container();
            containerBotonInstalacion.setLayout(new GridBagLayout());
            c.insets = new Insets(0,50,0,50);
            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.CENTER;
            
            // Creamos los botones de la ventana y aadimos los actionListeners
            botonInstalacion[0] = new JButton("Aceptar");
            botonInstalacion[1] = new JButton("Cancelar");
            
            botonInstalacion[0].setActionCommand("Aceptar");
            botonInstalacion[1].setActionCommand("Cancelar");
            
            botonInstalacion[0].addActionListener(new ManejadorInstalador());
            botonInstalacion[1].addActionListener(new ManejadorInstalador());
            
            // Barra de progreso
            progressBar = new JProgressBar(0, 100);
            progressBar.setStringPainted(true);
            
            // Medidas para situarlos en la ventana
            c.gridheight = 10;
            c.gridx = 1;
            c.gridy = 1;
            c.weightx = 1;
            c.weighty = 1;
            containerBotonInstalacion.add(botonInstalacion[0],c);
         
            c.gridx++;
            containerBotonInstalacion.add(botonInstalacion[1],c);
     
            c.insets = new Insets(10,10,10,10); 
            c.gridx = 1;
            c.gridy = 1;
            containerInstalacion.add(containerTextoInstalacion,c);
            c.gridy = 12;
            containerInstalacion.add(containerBotonInstalacion,c);
            c.gridy = 24;
            containerInstalacion.add(progressBar,c);
            
            // Finalizamos la ventana
            ventanaDeInstalacion.setContentPane(containerInstalacion);
            ventanaDeInstalacion.pack();
            ventanaDeInstalacion.setVisible(true);
            ventanaDeInstalacion.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            ventanaDeInstalacion.setLocationRelativeTo(null);
            ventanaDeInstalacion.setResizable(false);
         
        }

        private class ManejadorInstalador implements ActionListener { 
            
            Task task;
            
                        
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Aceptar")) {
                	botonInstalacion[0].setEnabled(false);
                	botonInstalacion[1].setEnabled(false);
                	
                    task = new Task();
                    task.execute();
                }
                if (e.getActionCommand().equals("Cancelar")) {
                      ventanaDeInstalacion.dispose();
                      Instalador.vInstalador.dispose();
                      System.exit(0);
                    }
           }
            
            class Task extends SwingWorker<Void, Void> {
                /*
                 * Main task. Executed in background thread.
                 */
                @Override
                public Void doInBackground() {
                    if (String.valueOf(textoPassword[0].getPassword()).compareTo(String.valueOf(textoPassword[1].getPassword())) == 0) {
                        try {
                        	textoPassword[0].setEnabled(false);
                        	textoPassword[1].setEnabled(false);
                        	
                            progressBar.setValue(0);
                            
                            Instalador.vInstalador.dispose();                       
                            
                            progressBar.setValue(5);

                            BD.crearTablas();

                            progressBar.setValue(40);
                            
                            BD.introducirDatosAdmin(String.valueOf(textoPassword[0].getPassword()));
                            
                            progressBar.setValue(45);

                            if (JOptionPane.showConfirmDialog(new JFrame(),
                                    "Desea que se generen ficheros y usuarios aleatorios?\n" +
                                    "(Este proceso puede llevar unos minutos)\n\n" + 
                                    "Nota: Esto puede ser útil para aprender a utilizar la aplicación.\n",
                                    "Datos aleatorios",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                Instalador.vInstalador.dispose();
                                
                                progressBar.setValue(50);
                                
                                JOptionPane.showMessageDialog(new JFrame(), "Pulse aceptar para comenzar a generar los datos.");
                                
                                progressBar.setValue(55);
                                
                                BD.generarDatosAleatorios();
                            }
                            progressBar.setValue(100);
                            JOptionPane.showMessageDialog(new JFrame(), "Enhorabuena, la instalación ha sido un éxito.\n" +
                            		                                    "Ya puede empezar a utilizar la aplicación.");
                            ventanaDeInstalacion.dispose();
                            System.exit(0);
                        }
                        catch (Exception exc){
                            JOptionPane.showMessageDialog(new JFrame(), "Ha ocurrido un error. Compruebe que está conectado a Internet e inténtelo de nuevo");
                            System.exit(0);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(new JFrame(), "Las contraseñas no coinciden. Vuelva a escribirlas");
                        textoPassword[0].setText("");
                        textoPassword[1].setText("");
                    }
                    return null;
                }
                }

                /*
                 * Executed in event dispatching thread
                 */
                //@Override
                public void done() {
 
                }
            }
	}