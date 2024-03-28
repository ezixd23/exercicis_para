package Exercise_03;

import java.util.concurrent.atomic.AtomicBoolean;

// NOTE: modify the threads' sleeping time to make things go faster.

public class Turnstile_TENTATIVE_2 extends Thread {


    /* COMPLETE */
    // want[0] is want_p; want[1] is want_q;
    
    private static int nextPrNumber = 0;
    
    private InterruptibleCounter globalCounter;
    private int myCounter;
    private int procNumber; // process number. 0 is p; 1 is q
    private int other; // process number of the other process

    public Turnstile_TENTATIVE_2(String name, InterruptibleCounter gCounter) {
        super(name);

        this.procNumber = Turnstile_TENTATIVE_2.nextPrNumber;
        Turnstile_TENTATIVE_2.nextPrNumber++;
        this.other = (this.procNumber + 1) % 2;
        this.globalCounter = gCounter;
        this.myCounter = 0;
    }

    @Override
    public void run() {
        // allow 20 people to enter, one person each 1/2 second
        for (int i = 1; i <= 20; i++) {
            /* Non-Critical Section */
            try {Thread.sleep(500);} catch (InterruptedException ie){}
            this.myCounter++;
            
            /* COMPLETE: WRITE preprotocol */
           
            /* Critical Section */
            this.globalCounter.increment();
            
            /* COMPLETE: WRITE postprotocol */
            
          
            /* Non-Critical Section */
            System.out.println(this.getName() + ": " + myCounter);
        }
    }

     public static void reset() {
        // this procedure resets the static components of the class
        want[0].set(false);
        want[1].set(false);
        nextPrNumber = 0;
    }

}
