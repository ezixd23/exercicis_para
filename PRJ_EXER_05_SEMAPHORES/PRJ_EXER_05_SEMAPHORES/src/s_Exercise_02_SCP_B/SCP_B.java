package s_Exercise_02_SCP_B;

import java.util.concurrent.Semaphore;


public class SCP_B {
    
    public static void main (String [] args) {
        Storage storage = new Storage();
        
        Counter counter = new Counter(storage);
        Printer printer = new Printer(storage);
        
        try {
            Thread.sleep(5000);
        }
        catch(InterruptedException ie) {}
        
        counter.stop();
        printer.stop();
        
    }
    
}
class Storage {
    private volatile int value = -1000;
    
    /* COMPLETE */
    
    public int getValue() {
        // block invoker if no value is available
    	/* COMPLETE */
    }
    
    public void setValue(int value) {
        // block invoker if value cannot be set yet
    	/* COMPLETE */
    	
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
    
    public Printer (Storage storage) {
        this.storage = storage;
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
            System.out.println(i);
        }
    }
}