import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
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
	
	public marcoCliente() {
		// TODO Auto-generated constructor stub
		setBounds(600,400,250,150);
		
		laminaMarcoCliente milamina = new laminaMarcoCliente(); //Creo objeto que extiende de jpanel para rellenar mi frame con este
		
		add(milamina);
		
		setVisible(true); //HAGO VISIBLE MI VENTANA
		
		

	}
}



//clase que crea el contenido de la ventana, implementa de runnable para poder crear un hilo
class laminaMarcoCliente extends JPanel {
	/**
	 * ENVIO DE LOS NICK USUARIO PARA COMPROBAR SI HAY ALGUNO REPETIDO
	 */
	public void comprobarRepetidos() {
		try {
			Socket misocket = new Socket("localhost", 9999); //Creo socket
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
		botonNuevo = new JButton("Nuevo usuario");
		
		EnviaTexto enviatexto = new EnviaTexto(); //objeto que implementa de action listener
		btnEnvio.addActionListener(enviatexto);//añado el evento escuchador a mi boton
		
		nuevoUsuario nuevoUsuario = new nuevoUsuario(); //objeto que implementa de action listener
		botonNuevo.addActionListener(nuevoUsuario);
		
		add(btnEnvio);
		add(botonNuevo);
		
	
	}

	//-----------ACTION LISTENER DE BOTON NUEVO QUE CREA UNA NUEVA VENTANA DE CLIENTE--------------
	/**
	 * @author ester
	 * CLASE NUEVO USUARIO, IMPLEMENTA DE ACTIONLISTENER PARA PODER EJECUTAR LOS MÉTODOS DESEADOS AL PULSAR EL BOTON
	 */
	private class nuevoUsuario implements ActionListener{
		

		/**
		 *AL PULSAR EL BOTON CREA UNA NUEVA VENTANA CLIENTE
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			marcoCliente mimarco = new marcoCliente();
			mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
	}


	/**
	 * MÉTODO QUE ENVÍA LOS MENSAJES AL SERVIDOR
	 */
	public void envioTexto() {
		try {
			//creo el socket (IP, Puert0)
			Socket misocket = new Socket("localhost", 9999);
			
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
	//---------------------------------------------------------------------------------------
	
	private JTextField campo1;
	
	private JLabel nick;
	
	public String nick_usuario;
	
	private JButton btnEnvio, botonNuevo;
	

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
