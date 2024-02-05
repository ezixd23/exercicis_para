package e1_mpp_AtomicInteger;

import java.util.concurrent.atomic.AtomicInteger;


public class MPP_AI {

	public static final int CAN_PING = 1; // use this value to indicate that "one thread can ping" 
	public static final int CAN_PONG = 2; // use this value to indicate that "one thread can pong"
	public static final int WRITING = 3; // use this value to indicate that "one thread is writing something"
	
	public static void main (String [] args) throws InterruptedException {
		
		AtomicInteger shared = new AtomicInteger(CAN_PING);
		
		Ping [] thePings = new Ping[4]; // values higher than 4 seem to cause contention in some machines...
		Pong [] thePongs = new Pong[4];
		
		for (int i=0; i<thePings.length; i++) {
			thePings[i] = new Ping(i, shared);
			thePongs[i] = new Pong(i, shared);
			thePings[i].start();
			thePongs[i].start();
		}
		
		Thread.sleep(5000);
		
		for (int i=0; i<thePings.length; i++) {
			thePings[i].stop();
			thePongs[i].stop();
		}
		
	}
	
}

class Ping extends Thread {
	
	private int id;
	private AtomicInteger sharedAtomicInt;
	
	public Ping (int id, AtomicInteger shared) {
		this.id = id;
		this.sharedAtomicInt = shared;
	}
	
	public void run ()  {
		while (true) {
			
			/* COMPLETE */ 
			while(!sharedAtomicInt.compareAndSet(1,3)){}

			System.out.print("PING("+id+")");
			try {Thread.sleep(10);} catch(InterruptedException ie ) {}

			// once ping's written, let's give a pong the chance to write
			sharedAtomicInt.set(MPP_AI.CAN_PONG);
		}
	}
}

class Pong extends Thread {
	
	private int id;
	private AtomicInteger sharedAtomicInt;
	
	public Pong (int id, AtomicInteger shared) {
		this.id = id;
		this.sharedAtomicInt = shared;
	}
	
	
	public void run ()  {
		while (true) {
			
			/* COMPLETE */
			while(!sharedAtomicInt.compareAndSet(2,3)){}

			System.out.println("PONG("+id+")");
			try {Thread.sleep(10);} catch(InterruptedException ie ) {}

			/* COMPLETE */
			sharedAtomicInt.set(MPP_AI.CAN_PING);
		}
	}
}