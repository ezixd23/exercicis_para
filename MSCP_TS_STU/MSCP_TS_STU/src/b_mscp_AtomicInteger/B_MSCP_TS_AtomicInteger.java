package b_mscp_AtomicInteger;

import java.util.concurrent.atomic.AtomicInteger;


public class B_MSCP_TS_AtomicInteger {
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
    private AtomicInteger storageState;
    private volatile int canStorage = 1;
	private volatile int canRetreive = 2;
	private volatile int none = 3;
    /* Remember that three possibilities must be contemplated:
     	- A new value can be stored
     	- The stored value can be retrieved
     	- An operation is in course, neither storing nor retrieving are allowed
     */
    
    /* COMPLETE */
	public Storage(){
		storageState = new AtomicInteger(canStorage);
	}
    public int getValue() {
		while (!storageState.compareAndSet(canRetreive,none)){}
        return value;
    }

    public void setValue(int value) {
		while (!storageState.compareAndSet(canStorage,none)){}
        this.value = value;
		storageState.set(canRetreive);
    }

	public void valuePrinted(){
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
			value = storage.getValue();
			for (int i=0; i<=value; i++) {System.out.print(" ");};
			System.out.println(value+"["+id+"]");
			storage.valuePrinted();
		}
	}
}