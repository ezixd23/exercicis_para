package part_D;

import java.util.concurrent.Semaphore;

public class D {
	
	public static void main (String [] args) throws InterruptedException {
		final int INSTANCES = 10;
		
		TIC theTICs [] = new TIC[INSTANCES];
		TAC theTACs [] = new TAC[INSTANCES];
		TOE theTOEs [] = new TOE[INSTANCES];
		
		Synchronizer shared = new Synchronizer();
		
		for (int i = 0; i<INSTANCES; i++) {
			theTICs[i] = new TIC(i, shared);
			theTICs[i].start();
			theTACs[i] = new TAC(i, shared);
			theTACs[i].start();
			theTOEs[i] = new TOE(i, shared);
			theTOEs[i].start();
		}
		
		Thread.sleep(10000);
		
		for (Thread th : theTICs) {th.stop();}
		for (Thread th : theTACs) {th.stop();}
		for (Thread th : theTOEs) {th.stop();}
		
	}
}

class Synchronizer {
	private volatile boolean uppercase = true;
	private volatile int lastTicId = -1;
	private Semaphore sem = new Semaphore(1/* permits */);
	
	// you need something more, now
	/* COMPLETE */
	private volatile int turn = 0;
	private final int canTic = 0;
	private final int canTac = 1;

	private final int canToe = 2;
	
	public void letMeTic (int id) {
		/* COMPLETE */
		while(lastTicId == id){}
		sem.acquireUninterruptibly();
		while(turn != canTic){
			sem.release();
			Thread.yield();
			sem.acquireUninterruptibly();
		}
		lastTicId = id;

	}
	
	public void ticDone () {
		/* COMPLETE */
		turn = 1;
		sem.release();
	}
	
	public void letMeTac () {
		/* COMPLETE */
		while (turn != canTac){}
		sem.acquireUninterruptibly();

	}
	
	public void tacDone ()  {
		/* COMPLETE */
		sem.release();
		turn = 2;
		uppercase = !uppercase;
	}
	
	public void letMeToe (int id) {
		/* COMPLETE */

		sem.acquireUninterruptibly();
		while(!(lastTicId==id && turn != canToe)){
			sem.release();
			Thread.yield();
			sem.acquireUninterruptibly();
		}
	}
	
	public void toeDone () {
		/* COMPLETE */
		turn = 0;
		sem.release();
	}
	
	public boolean nowUppercase () {return this.uppercase;}
}

class TIC extends Thread {
	
	private int id;
	private Synchronizer synchronizer;
	
	public TIC (int id, Synchronizer synchronizer) {
		this.id = id;
		this.synchronizer = synchronizer;
	}
	
	public void run () {
		while (true) {
			synchronizer.letMeTic(id);
			System.out.print("TIC("+id+")-");
			synchronizer.ticDone();
		}
	}
}

class TAC extends Thread {
	
	private int id;
	private Synchronizer synchronizer;
	
	public TAC (int id, Synchronizer synchronizer) {
		this.id = id;
		this.synchronizer = synchronizer;
	}
	
	public void run (){
		while (true) {
			synchronizer.letMeTac();
			if (synchronizer.nowUppercase())
				System.out.print("TAC["+id+"]");
			else 
				System.out.print("tac["+id+"]");
			synchronizer.tacDone();
		}
	}
}

class TOE extends Thread {
	
	private int id;
	private Synchronizer synchronizer;
	
	public TOE (int id, Synchronizer synchronizer) {
		this.id = id;
		this.synchronizer = synchronizer;
	}
	
	public void run () {
		while (true) {
			synchronizer.letMeToe(id);
			System.out.println("-TOE("+id+")");
			synchronizer.toeDone();
		}
	}
}


