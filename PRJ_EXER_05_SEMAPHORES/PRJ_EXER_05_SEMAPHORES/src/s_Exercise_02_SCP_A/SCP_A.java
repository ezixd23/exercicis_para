package s_Exercise_02_SCP_A;

import java.util.concurrent.Semaphore;


public class SCP_A {
    
    public static void main (String [] args) {
        
        /* COMPLETE */
    	Semaphore canCount = new Semaphore(1);
    	Semaphore canPrint =  new Semaphore(0);
        Printer [] printers = new Printer[10];
        Storage storage = new Storage();
        Counter counter = new Counter(storage,canCount, canPrint /* COMPLETE */);
        
        for (int i=0; i<printers.length; i++) {
        	printers[i] = new Printer(i, storage, canPrint, canCount/* COMPLETE */);
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
	private Semaphore canCoount;
	private Semaphore canPrint;
	/* COMPLETE */
	
	public Counter (Storage storage,Semaphore canCoount,Semaphore canPrint /* COMPLETE */) {
		this.storage = storage;
		/* COMPLETE */
		this.canCoount = canCoount;
		this.canPrint = canPrint;
	}
	
   public void run () {
	   while (true) {
		   for (int i=0; i<=9; i++) {
			   /* COMPLETE */
			   canCoount.acquireUninterruptibly();
			   storage.setValue(i);
			   canPrint.release();
		   }
	   }
   }
}

class Printer extends Thread {
	private Storage storage;
	private int id;
	
	/* COMPLETE */
	private Semaphore canCoount;
	private Semaphore canPrint;
	
	public Printer (int id, Storage storage,Semaphore canPrint,Semaphore canCoount /* COMPLETE */) {
		this.storage = storage;
		this.id = id;
		/* COMPLETE */
		this.canPrint = canPrint;
		this.canCoount = canCoount;
	}
	
	public void run () {
		int val;
		while (true) {
			/* COMPLETE */
			canPrint.acquireUninterruptibly();
			val = storage.getValue();
    		for(int i=0;i<=val;i++) {
    			System.out.print(" ");
    		}
    		System.out.println(val);
			canCoount.release();
		}
	  }
}