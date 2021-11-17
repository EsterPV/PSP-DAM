package hilosPSP;

public class HiloContador2 extends Thread {

	public long contador;

	
	
	public HiloContador2(long contador) {
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
			HiloContador2.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}while(contador!=0);	}
	
}
