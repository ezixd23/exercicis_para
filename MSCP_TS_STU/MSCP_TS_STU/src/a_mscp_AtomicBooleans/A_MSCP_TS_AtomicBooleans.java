package a_mscp_AtomicBooleans;

import java.util.concurrent.atomic.AtomicBoolean;


public class A_MSCP_TS_AtomicBooleans {
	public static void main(String[] args) {
		final int NUM_PRINTERS = 10;
		
		Printer[] thePrinters = new Printer[NUM_PRINTERS]; 
		Storage storage = new Storage();
		Counter counter = new Counter(storage);
		
		for (int i=0; i<thePrinters.length; i++) {
			thePrinters[i] = new Printer(i,storage);
			thePrinters[i].start();
		}
		counter.start();
		
		try {
			Thread.sleep(5000);
		}
		catch(InterruptedException ie) {}

		counter.stop();
		for (int i=0; i<thePrinters.length; i++) {
			thePrinters[i].stop();
		}
		
	}
}

class Storage {
    private volatile int value = -1000;
    
    // use these for synchronization purposes
    private AtomicBoolean canStore, canRetrieve;
    
    public Storage () {
    	this.canStore = new AtomicBoolean(true);
    	this.canRetrieve = new AtomicBoolean(false);
    }
    
    /* COMPLETE */
	public void valuePrinted(){
		canStore.set(true);
	}
	public int getValue() {
		while(!canRetrieve.compareAndSet(true,false)){
		}
		return value;
	}

	public void setValue(int value) {
		while(!canStore.compareAndSet(true,false)){
		}
		this.value=value;
		canRetrieve.set(true);
	}
}

class Counter extends Thread {
	private Storage storage;

	public Counter (Storage storage) {
		this.storage = storage;
	}
	
	public void run () {
		int i;
		while (true) {
			for (i=0; i<=9; i++) {
				storage.setValue(i);
			}
		}
	}
}

class Printer extends Thread {
	private int id;
	private Storage storage;
	
	public Printer (int id, Storage storage) {
		this.id = id;
		this.storage = storage;
	}
	
	public void run () {
		int value;
		while (true) {
			value = storage.getValue();
			for (int i=0; i<=value; i++) {System.out.print(" ");};
			System.out.println(value+"["+id+"]");
			storage.valuePrinted();
		}
	}
}