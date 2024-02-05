package a_multi_TTT_TS;

import java.util.concurrent.atomic.AtomicInteger;


// 1 => TIC is possible
// 2 => TAC or tac is possible
// 3 => TOE is possible
// 4 => writing in course

public class A_Multi_TTT_TS {
	
	static int INSTANCES = 10;
	
	public static void main (String [] args) throws InterruptedException {	
		AtomicInteger syncTool = new AtomicInteger(/* INITIALIZE */1);
		Tic[] theTics = new Tic[INSTANCES];
		Tac[] theTacs = new Tac[INSTANCES];
		Toe[] theToes = new Toe[INSTANCES];
		
		for (int i=0; i<INSTANCES; i++) {
			theTics[i] = new Tic(i, syncTool);
			theTacs[i] = new Tac(i, syncTool);
			theToes[i] = new Toe(i, syncTool);
			theToes[i].start();
			theTacs[i].start();
			theTics[i].start();
		}
		
		Thread.sleep(5000);
		
		System.exit(0);
	}
}


class Tic extends Thread {
	
	private AtomicInteger syncTool;
	private int id;
	
	public Tic (int id, AtomicInteger st) {
		this.syncTool = st;
		this.id = id;
	}
	
	public  void run () {
		while (true) {
			/* COMPLETE */
			while(!syncTool.compareAndSet(1,4)){Thread.yield();}
			System.out.print("TIC("+id+")-");
			try {Thread.sleep(50);} catch (InterruptedException ie) {}
			/* COMPLETE */
			syncTool.set(2);

		}
	}
}

class Tac extends Thread {
	
	private AtomicInteger syncTool;
	private int id;
	
	private static volatile boolean upperCase = true;  // static. All instance share
	                                                   // the same variable
	
	public Tac (int id, AtomicInteger st) {
		this.syncTool = st;
		this.id = id;
	}
	
	public void run () {
		while (true) {
			/* COMPLETE */
			while(!syncTool.compareAndSet(2,4)){Thread.yield();}
			if (upperCase) System.out.print("TAC("+id+")-");
			else System.out.print("tac("+id+")-");
			/* COMPLETE */
			upperCase= !upperCase;
			syncTool.set(3);
		}
	}
}

class Toe extends Thread {
	
	private AtomicInteger syncTool;
	private int id;
	
	public Toe (int id, AtomicInteger st) {
		this.syncTool = st;
		this.id = id;
	}
	
	public  void run () {
		while (true) {
			/* COMPLETE */
			while(!syncTool.compareAndSet(3,4)){Thread.yield();}
			System.out.println("TOE("+id+")");
			try {Thread.sleep(50);} catch (InterruptedException ie) {}
			syncTool.set(1);
		}
	}
}

