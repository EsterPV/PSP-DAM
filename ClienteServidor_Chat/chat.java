import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author ester
 * CLASE CHAT DONDE CREO MI VENTANA CLIENTE
 */
public class chat {

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		marcoCliente mimarco = new marcoCliente();
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}

/**
 * @author ester
 *CLASE QUE CREA EL MARCO DEL CHAT 
 */
class marcoCliente extends JFrame{
	laminaMarcoCliente milamina;
	
	public laminaMarcoCliente getMilamina() {
		return milamina;
	}

	public void setMilamina(laminaMarcoCliente milamina) {
		this.milamina = milamina;
	}

	public marcoCliente() {
		// TODO Auto-generated constructor stub
		setBounds(600,400,240,280);
		
		milamina = new laminaMarcoCliente(); //Creo objeto que extiende de jpanel para rellenar mi frame con este
		
		add(milamina);
		
		setVisible(true); //HAGO VISIBLE MI VENTANA
		
		

	}
	

}



/**
 * @author ester
 *clase que crea el contenido de la ventana
 */
class laminaMarcoCliente extends JPanel {
	
	private JTextField campo1;
	private JLabel nick;
	public String nick_usuario;
	private JButton btnEnvio;
	private JTextArea areaChat;
	
	
	/**Getters y Setters de mi JTextArea para poder manipularlo desde el servidor
	 * @return areaChat
	 */
	public JTextArea getAreaChat() {
		return areaChat;
	}

	public void setAreaChat(JTextArea areaChat) {
		this.areaChat = areaChat;
	}


	/**
	 * CONSTRUCTORES DE LA CLASE
	 */
	public laminaMarcoCliente() {
		// TODO Auto-generated constructor stub
		
		nick_usuario=JOptionPane.showInputDialog("Nick: ");
		comprobarRepetidos();
		
		JLabel n_nick = new JLabel("Nick: ");
		
		add(n_nick);
		
		nick = new JLabel();
		
		nick.setText(nick_usuario);
		
		
		add(nick);
		
		areaChat = new JTextArea();
		areaChat.setEditable(false);//Configuramos para que no sea editable
		areaChat.setLineWrap(true);//Configuramos para que haya ajuste de línea
		areaChat.setWrapStyleWord(true);//Configuramos para que el ajuste de línea sea por palabra y no por caracteres
		areaChat.setMargin(new Insets(10 ,50 ,90 ,50));
		add(areaChat);
		//Añadimos el área de texto a una instancia de JScrollPane para que se pueda hacer scroll
		JScrollPane scrollPane = new JScrollPane(areaChat);
		add(scrollPane);
		
		campo1= new JTextField(20);
		
		campo1.addKeyListener(new KeyListener() {
			/**
			 *KEYLISTENER DE TEXTFIELD PARA ENVIAR LOS MENSAJES AL PRESIONAR ENTER
			 */
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	                envioTexto(); //Método que envia el mensaje al servidor
	                campo1.setText(null);//Vacía el textfield una vez enviado el mensaje
	            }
			}
		});
		
		add(campo1);
		
		btnEnvio= new JButton("Enviar");
		
		
		EnviaTexto enviatexto = new EnviaTexto(); //objeto que implementa de action listener
		btnEnvio.addActionListener(enviatexto);//añado el evento escuchador a mi boton
		
	
		
		add(btnEnvio);
	
	
		
	
	}

	//----------------------ACTIONLISTENER DE BOTON ENVIO PARA ENVIAR LOS MENSAJES DEL USUARIO AL SERVIDOR---------------
	/**
	 * @author ester
	 * CLASE ENVIATEXTO, IMPLEMENTA DE ACTIONLISTENER PARA PODER EJECUTAR LOS MÉTODOS DESEADOS AL PULSAR EL BOTON
	 */
	private class EnviaTexto implements ActionListener{
		/**
		 *EVENTO ESCUCHADOR QUE INVOCA EL METODO ENVIOTEXTO
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
				envioTexto();
				campo1.setText(null); //vacía el textfield una vez enviado el mensaje
		}
		
	}
	//---------------------------------------------------------------------------------------------
	
	//Variables donde almaceno la ip y el puerto
	String IP = "localhost";
	int puerto = 9999;
	/**
	 * MÉTODO QUE ENVÍA LOS MENSAJES AL SERVIDOR
	 */
	public void envioTexto() {
		try {
			//creo el socket (IP, Puert0)
			Socket misocket = new Socket(IP, puerto);
			
			//instancio la clase paqueteEnvio
			paqueteEnvio datos = new paqueteEnvio();
			
			datos.setNick(nick.getText());//capto el nick dentro del jtextfield con mi getter de paqueteEnvio
			
		
			datos.setMensaje(campo1.getText()); //campo1 es el nombre de mi jtextfield que lo asigno al getter de mensaje de paqueteenvio
			
			
			ObjectOutputStream paqueteDatos = new ObjectOutputStream(misocket.getOutputStream()); //creo mi flujo de salida de objetos vinculado a misocket
			
		
			paqueteDatos.writeObject(datos); //envio mi clase paqueteenvio(objeto: datos) mediante el flujo de salida
			
			
			
			misocket.close(); //cierro el socket
			
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
	}
	}
	
	
	/**
	 * ENVIO DE LOS NICK USUARIO PARA COMPROBAR SI HAY ALGUNO REPETIDO
	 */
	public void comprobarRepetidos() {
		try {
			Socket misocket = new Socket(IP, puerto); //Creo socket
			paqueteEnvio datos = new paqueteEnvio();
			datos.setNickusuario(nick_usuario);
			
			ObjectOutputStream paqueteDatos = new ObjectOutputStream(misocket.getOutputStream()); //creo mi flujo de salida de objetos vinculado a misocket
			
			paqueteDatos.writeObject(datos); //envio mi clase paqueteenvio(objeto: datos) mediante el flujo de salida
			
			misocket.close(); //cierro el socket
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	

}


/**
 * @author ester
 *CLASE PARA ALMACENAR EL NICK Y EL MENSAJE
 *implementa de serializable para poder ser enviadas a través de la red
 */
class paqueteEnvio implements Serializable {
	/**
	 * Default serial ID y atributos
	 */
	private static final long serialVersionUID = 1L;

	private String nick, mensaje, nickusuario;
	
	/**
	 * @return
	 * Getters y setters de mis atributos
	 */
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}


	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	public String getNickusuario() {
		return nickusuario;
	}

	public void setNickusuario(String nickusuario) {
		this.nickusuario = nickusuario;
	}


	
}
