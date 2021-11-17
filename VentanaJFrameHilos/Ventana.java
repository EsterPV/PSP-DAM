package hilosPSP;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;


/**
 * @author ester
 * Creo mi clase Ventana que extiende de JFrame que creará mi interfaz y de Action Listener para 
 * poder interactuar con los botones y los datos no estáticos
 */
public class Ventana extends JFrame implements ActionListener {

	public JButton botonh1, botonh2; //Atributos botón
	public JLabel titulo, labelh1, labelh2; //Tres labels donde mostraré los hilos
	public HiloContador hilo1; //Creo como atributos mis hilos desde donde llamaré al contador
	public HiloContador2 hilo2;


	
	public Ventana() {
		setLayout(null);
		
		botonh1 = new JButton("Detener hilo 1");//Creo botón 1
		botonh1.setBounds(150, 30, 150, 30); //pongo los datos de posición y tamaño
		add(botonh1); //lo añado a mi ventana
		botonh1.addActionListener(this); //Creo mi actionListener que me permitirá poder pulsarlo
		
		botonh2 = new JButton("Detener hilo 2");
		botonh2.setBounds(150, 70, 150, 30);
		add(botonh2);
		botonh2.addActionListener(this);
		
		titulo = new JLabel("Subprograma"); //creo mi label titulo
		titulo.setBounds(20, -30, 100, 100);
		add(titulo);
		
		labelh1 = new JLabel("Hilo1: ");
		labelh1.setBounds(150, 100, 200, 100);
		labelh1.setFont(new Font("Verdana", Font.PLAIN, 20)); //Creo una fuente y un tamaño para los labels de mis hilos
		
		add(labelh1);
		labelh2 = new JLabel("Hilo2: ");
		labelh2.setBounds(150, 130, 200, 100);
		labelh2.setFont(new Font("Verdana", Font.PLAIN, 20));
		
		add(labelh2);
		
		hilo1 = new HiloContador(0); //Creo dos hilos
		hilo2 = new HiloContador2(0);
		
		Timer timer = new Timer (1000, new ActionListener () //creo un timer para poder mostrar el contador en tiempo real en los labels
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	try {
					
		    		String cont1 = String.valueOf(hilo1.getContador()); //convierto mi atributo de hilo1 de Long a String
		    		labelh1.setText("Hilo1: "+cont1); //Muestro el contador
		    		
		    		
		    		String cont2 = String.valueOf(hilo2.getContador());
		    		labelh2.setText("Hilo2: "+cont2);
		   
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		     }
		});
		timer.start(); //inicio el timer
		
	}

	public void run() {
		// TODO Auto-generated method stub

			hilo1.start();//inicio mis hilos
			hilo2.start();
		

		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == botonh1) { //si boton1 está pulsado 
			
					hilo1.stop(); //detengo el hilo
				

			String cont1 = String.valueOf(hilo1.getContador());
			labelh1.setText("Hilo1: "+cont1); //muestro el resultadp
			botonh1.setText("Hilo 1 finalizado"); //cambio el texto del botón
			}else if(e.getSource() == botonh2){//Si botón 2 está pulsado
				
			
					hilo2.stop();
				
				String cont2 = String.valueOf(hilo2.getContador());
				labelh2.setText("Hilo2: "+cont2);
				botonh2.setText("Hilo 2 finalizado");
			}
			
		}
	
	//Clase main
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Ventana v = new Ventana(); //Creo mi objeto ventana
		
		v.setBounds(100, 100, 450, 300); //coloco mi ventana en los ejes x e y y le doy un tamaño
		v.setVisible(true); //hago visible la ventana
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Finalizo la ventana al cerrarla en la equis
		v.run(); //ejecuto mis hilos
		
		
		
		
		
	}
		
	}

