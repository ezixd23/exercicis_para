package s_Exercise_05_A_CookieJar_2;

import java.util.concurrent.Semaphore;

public class CookieJarProblem {
    
    public static void main (String [] args) {
        
        /* COMPLETE */
        
        cord.start();
        barb.start();
        ada.start();
        
        
        // let them go for a while, then stop everyting
        try {Thread.sleep(5000);}  catch(InterruptedException ie){}
        cord.stop();
        barb.stop();
        ada.stop();
        System.out.println("TOTAL COOKIES TAKEN: "+jar.getTaken());     
    }
}
class Adelaide extends Thread {
    
    /* COMPLETE */
} 

class Barbara extends Thread {
    
    /* COMPLETE */
}

class Cordelia extends Thread {
    
    /* COMPLETE */
}

class CookieJar {
    private volatile int taken = 0;
    
    public void getACookie () {
        taken++;
    }
    
    public int getTaken () {return taken;}
}