package s_Exercise_02_SCP_A;

import java.util.concurrent.Semaphore;


public class SCP_A {
    
    public static void main (String [] args) {
        
        /* COMPLETE */
        Printer [] printers = new Printer[10];
        Storage storage = new Storage();
        Counter counter = new Counter(storage, /* COMPLETE */);
        
        for (int i=0; i<printers.length; i++) {
        	printers[i] = new Printer(i, storage, /* COMPLETE */);
        	printers[i].start();
        }
        
        counter.start();
        
        try {
            Thread.sleep(5000);
        }
        catch(InterruptedException ie) {}
        
        counter.stop();
        for (int i=0; i<printers.length; i++) {
        	printers[i].stop();
        }
        
    }
    
}
class Storage {
    // do not modify this class
    private volatile int value = -1000;
    
    public int getValue() {return this.value;}
    public void setValue(int value) {this.value = value;}
}

class Counter extends Thread {
	
	private Storage storage;
	
	/* COMPLETE */
	
	public Counter (Storage storage, /* COMPLETE */) {
		this.storage = storage;
		/* COMPLETE */
	}
	
   public void run () {
	   while (true) {
		   for (int i=0; i<=9; i++) {
			   /* COMPLETE */
		   }
	   }
   }
}

class Printer extends Thread {
	private Storage storage;
	private int id;
	
	/* COMPLETE */
	
	public Printer (int id, Storage storage, /* COMPLETE */) {
		this.storage = storage;
		this.id = id;
		/* COMPLETE */
	}
	
	public void run () {
		int val;
		while (true) {
			/* COMPLETE */
		}
	  }
}