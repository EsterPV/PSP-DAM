import java.awt.BorderLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	
	/**
	 * CONSTRUCTORES DE MI CLASE
	 */
	public MarcoServidor() {
		setBounds(1200, 300, 200, 350);
		//creo un panel
		JPanel milamina = new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto = new JTextArea();
		areaOnline = new JLabel();
		
		milamina.add(areatexto, BorderLayout.CENTER);
		milamina.add(areaOnline, BorderLayout.NORTH);
		add(milamina);
		
		setVisible(true);
		
		//creo un hilo
		Thread mihilo = new Thread(this);
		mihilo.start();//ejecuto el hilo
		
	}

	/**
	 * METODO RUN DE RUNNABLE, LO QUE CONTIENE DENTRO LO EJECUTARÁ MI HILO
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try { //creamos un servidor que escuchará la info de los clientes en el puerto 9999
			ServerSocket miservidor = new ServerSocket(9999);
			
			String nick, ip, mensaje, nickusuario; //variables que almacenan el nick la ip y el mensaje del cliente
			
			ArrayList<String> listaUsuarios = new ArrayList<String>(); //lista que va a contener los usuarios
			ArrayList<String> listanombres = new ArrayList<String>(); //lista que contiene también los usuarios pero voy a manipular para no mostrar nombres repetidos
			
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
				JOptionPane.showMessageDialog(null,"Este usuario ya existe, vuelva a iniciar sesión"); //muestro una ventana de error
				System.exit(ABORT); //cierro el servidor
				
			}else {//si no
				if(nickusuario != null) { // si nickusurio no está vacío
					listaUsuarios.add(nickusuario); //añado nuevo nick a la lista
					
				}
			}
			//-----------------------------------------------------------------------------
			
			
			System.out.println(listaUsuarios);
			
			//-----------------MUESTRO LOS USUARIOS ONLINE-----------------------
			if(nick!=null ) { //Si nick de usuario no está vacío
				listanombres.add(nick); //añado los nombres de los usuarios online en una lista
				Set<String> miHashet = new HashSet<String>(listanombres); //creo un hashset para eliminar nombres repetidos
				listanombres.clear(); //limpio la lista
				listanombres.addAll(miHashet); //añado el hashset
				areaOnline.setText("Usuarios online: "+ listanombres.toString());//muestro los usuarios online
			}
			//----------------------------------------------------------------
			
			if(nick!=null || mensaje != null) //si nick o mensaje no están vacíos
				areatexto.append("\n"+ nick+": "+ mensaje);//agrega el texto del mensaje y su usuario en el area de texto del chat
			
			
			
		
			
			
				misocket.close(); //cierro la conexión
			}//cierro While
			
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}