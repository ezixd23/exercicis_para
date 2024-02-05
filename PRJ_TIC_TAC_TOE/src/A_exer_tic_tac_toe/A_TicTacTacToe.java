package A_exer_tic_tac_toe;

public class A_TicTacTacToe {
	
	public static void main (String [] args) {	
		
		/* COMPLETE */
		Shared sharedobject = new Shared();
		Tic tic = new Tic(sharedobject);
		Tac tac = new Tac(sharedobject);
		Toe toe = new Toe(sharedobject);

		tic.start();
		tac.start();
		toe.start();

		try {Thread.sleep(5000);}  catch(InterruptedException ie) {};
		
		tic.stopNow();
		tac.stopNow();
		toe.stopNow();
		
		try {Thread.sleep(100);}  catch(InterruptedException ie) {};
		
		System.out.println("Shutting down");
		System.exit(0);
	}
}


class Shared {
	 /*
	 ...an object that encapsulates three boolean attributes (they may be public) 
	 and nothing more
	 */
	private volatile int whoCanPrint = 1;

	public int getWhoCanPrint() {
		return whoCanPrint;
	}

	public void setWhoCanPrint(int whoCanPrint) {
		this.whoCanPrint = whoCanPrint;
	}
}

class Tic extends Thread {
	// endlessly prints TIC-
	
	private volatile boolean stop = false;
	private Shared sharedObject;
	
	/* COMPLETE */
	public Tic(Shared sharedObject){
		this.sharedObject = sharedObject;
	}
	public  void run () {
		while (!stop) {
			/* COMPLETE */
			while(sharedObject.getWhoCanPrint() != 1){}
			System.out.print("TIC-");
			sharedObject.setWhoCanPrint(2);
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public void stopNow() {stop=true;}
}

class Tac extends Thread {
	// endlessly prints TAC (or tac)
	
	private volatile boolean stop = false;
	private Shared sharedObject;
	// anything else? 
	private boolean tac = false;
	/* COMPLETE */
	public Tac (Shared sharedObject){
		this.sharedObject = sharedObject;
	}
	public  void run () {
		while (!stop) {
			/* COMPLETE */
			while(sharedObject.getWhoCanPrint() != 2){}
			if (!tac)System.out.print("TAC-");
			else  System.out.print("tac-");
			tac=!tac;
			sharedObject.setWhoCanPrint(3);
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	public void stopNow() {stop=true;}
}

class Toe extends Thread {
	// endlessly prints -TOE
	
	private volatile boolean stop = false;
	private Shared sharedObject;

	public Toe(Shared sharedObject){
		this.sharedObject=sharedObject;
	}
	/* COMPLETE */

	public  void run () {
		while (!stop) {
			/* COMPLETE */
			while(sharedObject.getWhoCanPrint() != 3){}
			System.out.println("TOE");
			sharedObject.setWhoCanPrint(1);
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
	public void stopNow() {stop=true;}
}

