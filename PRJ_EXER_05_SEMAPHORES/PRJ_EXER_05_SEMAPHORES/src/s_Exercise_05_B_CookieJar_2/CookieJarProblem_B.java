package s_Exercise_05_B_CookieJar_2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.*;

public class CookieJarProblem_B {
    
    public static void main (String [] args) {
        
        CookieJar jar = new CookieJar();
        
        Adelaide ada = new Adelaide(jar);
        Barbara barb = new Barbara (jar);
        Cordelia cord = new Cordelia (jar);
        
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
    // do not modify this class
	private CookieJar jar;
	
	public Adelaide (CookieJar jar) {
		this.jar = jar;
	}
	
    public void run () {
    	while (true) {
    		
    		jar.adaTakes();
    		System.out.println("ADA takes a cookie");
    		
    		try {Thread.sleep(20);} catch(InterruptedException ie) {}
    	}
    }
} 

class Barbara extends Thread {
    // do not modify this class
	private CookieJar jar;
	
	public Barbara (CookieJar jar) {
		this.jar = jar;
	}
	public void run () {
		while (true) {
			
    		jar.barbTakes();;
    		System.out.println("    BARB takes a cookie");
    		
    		try {Thread.sleep(20);} catch(InterruptedException ie) {}
		}
	}
	
}

class Cordelia extends Thread {
    // do not modify this class
	private CookieJar jar;
	
	public Cordelia (CookieJar jar) {
		this.jar = jar;
	}
	
	public void run () {
		while (true) {
			
    		jar.cordTakes();
    		System.out.println("        CORD takes a cookie");
    		
    		try {Thread.sleep(20);} catch(InterruptedException ie) {}
		}
	}
}

class CookieJar {
    private volatile int taken = 0;
   
    /* COMPLETE */
    private Semaphore mutex = new Semaphore(1);
    private volatile int ab = 0;
    private volatile int ac = 0;
    private volatile int bc = 0;
   
    public void adaTakes() {
    	/* COMPLETE */
    	while(true) {
    		mutex.acquireUninterruptibly();
    		taken ++;
    		ab++;
    		ac++;
    		mutex.release();
    	}
    }
    
    public void barbTakes () {
    	/* COMPLETE */
    	while(true) {
    		while(ab<1) {
    			Thread.yield();
    		}
    		mutex.acquireUninterruptibly();
    		taken++;
    		ab=0;
    		bc++;
    		mutex.release();
    	}
    }
    
    public void cordTakes () {
    	/* COMPLETE */
    	while(true) {
    		while(ac<2&&bc<0) {
    			Thread.yield();
    		}
    		mutex.acquireUninterruptibly();
    		taken++;
    		ac=0;
    		bc=0;
    		mutex.release();
    	}
    }

    
    public int getTaken () {return taken;}
}