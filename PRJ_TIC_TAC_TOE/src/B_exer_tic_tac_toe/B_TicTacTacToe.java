package B_exer_tic_tac_toe;

public class B_TicTacTacToe {
	
	public static void main (String [] args) throws InterruptedException {
		
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
	
	/* COMPLETE -private attributes- private? yes, private*/
	private boolean tic = true;
	private boolean tac = false;
	private boolean toe = false;
	public void letMeTic () {
		// invoked by Tic before writing TIC-.
		// if this method returns then it is safe to write TIC-
		
		/* COMPLETE */
		while (!tic){}

	}
	
	public void ticWritten () {
		// invoked by Tic just after printing TIC-

		/* COMPLETE */
		tic=false;
		tac=true;
	}
	
	
	public void letMeTac () {
		// invoked by Tac before writing TAC.
		// if this method returns then it is safe to write TAC

		/* COMPLETE */
		while(!tac){}

	}
	
	public void tacWritten () {
		// invoked by Tac just after printing TAC (or tac)
		
		/* COMPLETE */
		tac=false;
		toe=true;
	}
	
	public void letMeToe () {
		// invoked by Toe before writing -TOE.
		// if this method returns then it is safe to write -TOE

		/* COMPLETE */
		while(!toe){}
	}
	
	public void toeWritten () {
		// invoked by Toe just after printing -TOE

		/* COMPLETE */
		toe=true;
		tic=true;
	}
	
}

class Tic extends Thread {
	
	private volatile boolean stop = false;
	private Shared shared;
	
	public Tic (Shared sh) {
		this.shared = sh;
	}
	
	public  void run () {
		while (!stop) {
			shared.letMeTic();
			
			/* COMPLETE */
			System.out.print("TIC-");
			shared.ticWritten();
		}
	}
	
	public void stopNow() {stop=true;}
}

class Tac extends Thread {
	
	private volatile boolean stop = false;
	private Shared shared;
	// anything else? /* COMPLETE if needed*/
	private boolean tac = false;
	public Tac (Shared sh) {
		this.shared = sh;
	}

	public void run () {
		while (!stop) {
			/* COMPLETE */
			shared.letMeTac();
			if (!tac) System.out.print("TAC-");
			else System.out.print("tac-");
			tac = !tac;
			shared.tacWritten();
		}
	}
	
	public void stopNow() {stop=true;}
}

class Toe extends Thread {
	
	private volatile boolean stop = false;
	private Shared shared;
	
	public Toe (Shared sh) {
		this.shared = sh;
	}
	
	public  void run () {
		while (!stop) {
			/* COMPLETE */
			shared.letMeToe();
			System.out.println("TOE");
			shared.toeWritten();
		}
	}
	
	public void stopNow() {stop=true;}
}

