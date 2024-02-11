package s_Exercise_06_DiningSavages_C;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;



public class Savage extends Thread {
	
	private static int nextId = 0;
	private static AtomicInteger waiting = new AtomicInteger(0); // number of waiting savages
	// AtomicInteger to avoid having to use another semaphore to protect this counter
	
	private int id;
	private Semaphore cookSleepingPlace;
	private Semaphore savagesWaitingPlace;
	private Semaphore potMutex;
	private Pot pot;
	private GuiObjects gui;
	
	public Savage (Pot pot, Semaphore potMutex, Semaphore cookSleepingPlace, Semaphore savagesWaitingPlace, GuiObjects gui) {
		
		this.id = nextId; nextId++;
		this.cookSleepingPlace = cookSleepingPlace;
		this.savagesWaitingPlace = savagesWaitingPlace;
		this.potMutex = potMutex;
		this.pot = pot;
		this.gui=gui;
	}
	
	public void run () {
		int w;
		while (true) {
			// do something for a while (chase, dance, smoke, ...)
			try {Thread.sleep(Math.round(5000*Math.random()));} catch(InterruptedException ie) {}
			
			
			// state that you're waiting to access the pot
			w=waiting.incrementAndGet();
			gui.waitingSavages.setText(w+"");
			
			// get exclusive access to the pot/cook system
			try{potMutex.acquire();} catch(InterruptedException ie) {}
			{
				
				// state that you're no longer waiting to access the pot
				w=waiting.decrementAndGet();
				gui.waitingSavages.setText(w+"");
				gui.idOfSavage.setText("#"+id);
				System.out.println("#"+id+" has got access to the pot");
				
				if (pot.getServings()==0) {
					// wake up the cook and wait until he notifies the pot has been refilled
					System.out.println("Savage "+id+" is about to wake up the cook");
					cookSleepingPlace.release();
					try {savagesWaitingPlace.acquire();} catch(InterruptedException ie) {}
				}
				// when here, there's at least one serving
				
				pot.helpYourself();
				// take your time to eat...
				try {Thread.sleep(Math.round(4000*Math.random()));} catch(InterruptedException ie) {}
				System.out.println("Savage "+id+" eats!");
			}
			potMutex.release();
			gui.idOfSavage.setText("--");
		}
	}
}
