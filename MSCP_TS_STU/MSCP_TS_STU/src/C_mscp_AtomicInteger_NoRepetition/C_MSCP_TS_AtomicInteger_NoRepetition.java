package C_mscp_AtomicInteger_NoRepetition;

import java.util.concurrent.atomic.AtomicInteger;


public class C_MSCP_TS_AtomicInteger_NoRepetition {
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
    
    // use this for synchronization purposes
	private volatile int canStorage = 1;
	private volatile int canRetreive = 2;
	private volatile int none = 3;
    private AtomicInteger storageState;

	public Storage(){
		storageState = new AtomicInteger(canStorage);
	}
    // use this to keep track of the last id that printed
    private volatile int last = -1;
    
    /* COMPLETE */
	public int getValue(int id) {
		while (id==last) Thread.yield();
		while (!storageState.compareAndSet(canRetreive,none)){Thread.yield();}
		return value;
	}

	public void setValue(int value) {
		while (!storageState.compareAndSet(canStorage,none)){Thread.yield();}
		this.value = value;
		storageState.set(canRetreive);
	}

	public void valuePrinted(int id){
		this.last = id;
		storageState.set(canStorage);
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
			/* COMPLETE */
			value = storage.getValue(id);
			for (int i=0; i<=value; i++) {System.out.print(" ");};
			System.out.println(value+"["+id+"]");
			/* COMPLETE */
			storage.valuePrinted(id);
		}
	}
}