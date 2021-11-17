package hilosPSP;

public class HiloContador extends Thread {

	public long contador;

	
	
	public HiloContador(long contador) {
		super();
		this.contador = contador;
	}

	public long getContador() {

		return contador;
		}

	
	
	public void run() {
		// TODO Auto-generated method stub
		
		do {
		
		contador=contador+1;
		try {
			HiloContador.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
		}
		}while(contador!=0);
//		contador++;
	}
	
}
