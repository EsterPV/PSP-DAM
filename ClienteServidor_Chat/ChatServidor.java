import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatServidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MarcoServidor mimarco = new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}


/**
 * @author ester
 * CLASE QUE CREA LA VENTANA SERVIDOR Y EXTIENDE DE RUNNABLE PARA PODER EJECUTAR UN HILO
 */
class MarcoServidor extends JFrame implements Runnable{
	
	/**
	 * DEFAULT SERIAL ID Y ATRIBUTOS
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea areatexto; //Area donde se muestran todos los mensajes de todos los usuarios
	private JLabel areaOnline; //Label que muestra los usuarios conectados
	private JButton nuevoUsuario;
	marcoCliente ventanaCliente;
	
	ArrayList<marcoCliente> chat = new ArrayList<marcoCliente>(); //Arraylist donde almaceno las ventanas cliente que voy abriendo desde el servidor

	/**
	 * CONSTRUCTORES DE MI CLASE
	 */
	public MarcoServidor() {
		setBounds(600, 200, 400, 350);
		//creo un panel
		JPanel milamina = new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto = new JTextArea();
		areaOnline = new JLabel();
		nuevoUsuario = new JButton();
		
		nuevoUsuario.setText("Nuevo Usuario");
		crearUsuario crearUsuario = new crearUsuario();
		nuevoUsuario.addActionListener(crearUsuario);
		
		areatexto.setEditable(false);
		
		milamina.add(areatexto, BorderLayout.CENTER);
		milamina.add(areaOnline, BorderLayout.SOUTH);
		milamina.add(nuevoUsuario, BorderLayout.NORTH);
		
		add(milamina);
		
		
		
		setVisible(true);
		
		//creo un hilo
		Thread mihilo = new Thread(this);
		mihilo.start();//ejecuto el hilo
		
	}
	
	/**
	 * @author ester
	 * CLASE NUEVO USUARIO, IMPLEMENTA DE ACTIONLISTENER PARA PODER EJECUTAR LOS M�TODOS DESEADOS AL PULSAR EL BOTON
	 */
	private class crearUsuario implements ActionListener{
		

		/**
		 *AL PULSAR EL BOTON CREA UNA NUEVA VENTANA CLIENTE
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			ventanaCliente = new marcoCliente();
			chat.add(ventanaCliente);

		}
		
	}

	int puerto= 9999;
	
	/**
	 * METODO RUN DE RUNNABLE, LO QUE CONTIENE DENTRO LO EJECUTAR� MI HILO
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try { //creamos un servidor que escuchar� la info de los clientes en el puerto 9999
			ServerSocket miservidor = new ServerSocket(puerto);
			
			String nick, mensaje, nickusuario; //variables que almacenan el nick la ip y el mensaje del cliente
			
			ArrayList<String> listaUsuarios = new ArrayList<String>(); //lista que va a contener los usuarios
			ArrayList<String> listanombres = new ArrayList<String>(); //lista que contiene tambi�n los usuarios pero voy a manipular para no mostrar nombres repetidos
			
			paqueteEnvio paqueteRecibido; //instanciamos la clase del chat PaqueteEnvio
			while(true) {
			Socket misocket = miservidor.accept(); //Creo socket que acepta las conexiones con el servidor
				
			
			
			ObjectInputStream paqueteDatos = new ObjectInputStream(misocket.getInputStream());//creo mi flujo de datos de objetos de entrada
			
			
			
			paqueteRecibido = (paqueteEnvio) paqueteDatos.readObject(); //Lee el los objetos con el flujo de entrada y lo almacena en el paqueterecibido
			
			
			//almaceno los atributos de mi objeto paqueteEnvio de la clase Chat en las variables creadas
			nick = paqueteRecibido.getNick();
			mensaje = paqueteRecibido.getMensaje();
			nickusuario = paqueteRecibido.getNickusuario();
			
			
			
			//-------------------COMPRUEBA SI HAY NICK DE USUARIO REPETIDOS--------------
			if(listaUsuarios.contains(nickusuario)) { //Si listaUsuarios ya contiene nickusuario
				JOptionPane.showMessageDialog(null,"Este usuario ya existe, vuelva a iniciar sesi�n"); //muestro una ventana de error
				ventanaCliente.hide();
				
				
			}else {//si no
				if(nickusuario != null) { // si nickusurio no est� vac�o
					listaUsuarios.add(nickusuario); //a�ado nuevo nick a la lista
					
				}
			}
			//-----------------------------------------------------------------------------
			
			
//			System.out.println(listaUsuarios);
			
			//-----------------MUESTRO LOS USUARIOS ONLINE-----------------------
			if(nick!=null ) { //Si nick de usuario no est� vac�o
				listanombres.add(nick); //a�ado los nombres de los usuarios online en una lista
				Set<String> miHashet = new HashSet<String>(listanombres); //creo un hashset para eliminar nombres repetidos
				listanombres.clear(); //limpio la lista
				listanombres.addAll(miHashet); //a�ado el hashset
				areaOnline.setText("Usuarios online: "+ listanombres.toString());//muestro los usuarios online
			}
			//----------------------------------------------------------------
			
			
			//-------------------MUESTRO LOS MENSAJES DEL CHAT EN LOS AREATEXT SERVIDOR Y CLIENTE------------
			if(nick!=null || mensaje != null) {
				//si nick o mensaje no est�n vac�os
				areatexto.append("\n"+ nick+": "+ mensaje);//agrega el texto del mensaje y su usuario en el area de texto del chat
				
				
				for(int i=0; i<chat.size(); i++) { //agrego el texto a cada una de os textArea de los clientes recorriendo el arraylist de objetos marcoCliente
					chat.get(i).getMilamina().getAreaChat().append("\n"+ nick+": "+ mensaje);
				}
			}
			//-------------------------------------------------------------------------------
			
			
		
				
			misocket.close(); //cierro la conexi�n
			}//cierro While
			
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}