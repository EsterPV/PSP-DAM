package comLaika.ejerciciosJframe;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ejercicio01_psp extends JFrame {

	private JPanel contentPane;
	private JLabel lblPelota;
	public HiloContadorMover hiloMover = new HiloContadorMover(0);
	private JButton btnReanudar;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio01_psp frame = new Ejercicio01_psp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ejercicio01_psp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnFinalizar = new JButton("Detener"); //Creo el botón con el que detengo la pelota
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			
					hiloMover.suspend(); //detengo la pelota
					btnFinalizar.setVisible(false); //hago invisible el boton detener
					btnReanudar.setVisible(true); //muestro el botón reanudar
				
			
			}
			});

		btnFinalizar.setBounds(206, 47, 138, 21);
		contentPane.add(btnFinalizar);
		
		JLabel lblNewLabel = new JLabel("Subprograma");
		lblNewLabel.setFont(new Font("Source Serif Pro Black", Font.BOLD, 14));
		lblNewLabel.setBounds(222, 10, 111, 13);
		contentPane.add(lblNewLabel);
		
		lblPelota = new JLabel("o");
		lblPelota.setFont(new Font("Tahoma", Font.BOLD, 30));
		contentPane.add(lblPelota);
	
		btnReanudar = new JButton("Reanudar"); // Creo mi botón reanudar
		
		btnReanudar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				btnFinalizar.setVisible(true); //Muestro el botón detener
				btnReanudar.setVisible(false); //Escondo el botón reanudar
				try {
					
					hiloMover.resume(); //reanudo el hilo

				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
					System.out.println("heu");
				}
			
			}
		});
		btnReanudar.setBounds(206, 47, 138, 21);
		contentPane.add(btnReanudar);
		hiloMover.start();
	
	}
	
	//Clase hilo que se encargará de mover mi pelota
	public class HiloContadorMover extends Thread {
		public int contador;

		public HiloContadorMover(int contador) {
			super();
			this.contador = contador;
		
		}
		
		
		public int getContador() {
			return contador;
		}


		public void setContador(int contador) {
			this.contador = contador;
		}


		public void run() {
			// TODO Auto-generated method stub
			
			try {
			while(!isInterrupted()) { //Mientras el hilo no esté detenido
				while(contador <= 565) { //mientras no alcance el extremo derecho de mi ventana (mide máximo 565)
				contador=contador+1;
				HiloContadorMover.sleep(1);
				
					lblPelota.setBounds(contador, 100, 33, 31);
				}
			
				while(contador >= 1) { //mientras no alcance el lado izquierdo max(1)
					contador = contador - 1;
					HiloContadorMover.sleep(1);
					lblPelota.setBounds(contador, 100, 33, 31);
				}
			}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Hilo 1 interrumpido");
				
			}

		}
		
	}

}
