package s_Exercise_02_SCP_C;

import java.util.concurrent.Semaphore;

/* INCORRECT VERSION */
/* INCORRECT VERSION */
/* INCORRECT VERSION */
/* INCORRECT VERSION */

public class SCP_C {
    
    public static void main (String [] args) {
        Storage storage = new Storage();
        
        Counter counter = new Counter(storage);
        Printer [] printers = new Printer[10];
        for (int i=0; i<printers.length; i++) {
        	printers[i] = new Printer(i, storage); // these printers start themselves
        } 
        
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
    private volatile int value = -1000;
    private Semaphore canProduce = new Semaphore(1);
    private Semaphore canConsume = new Semaphore(0);
    
    public int getValue() {
        // block invoker if no value is available
    	int copy;
    	try {canConsume.acquire();} catch(InterruptedException ie) {}
    	copy = this.value;
    	canProduce.release();
    	return copy;
    }
    
    public void setValue(int value) {
        // block invoker if value cannot be set yet
    	try {canProduce.acquire();} catch(InterruptedException ie) {}
    	this.value = value;
    	canConsume.release();
    }
}

class Counter extends Thread {
    private Storage storage;
    
    public Counter (Storage storage) {
        this.storage = storage;
        // once created, instances of Counter start themselves
        this.start();
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
    private Storage storage;
    private int id;
    
    public Printer (int id, Storage storage) {
        this.storage = storage;
        this.id = id;
        // once created, instances of Printer start themselves
        this.start();
    }
    
    public void run () {
        int i;
        while (true) {
            i = storage.getValue();
            for (int n=1; n<=i; n++) {
                System.out.print(" ");
            }
            System.out.println(i+"["+id+"]");
        }
    }
}