package s_Exercise_05_A_CookieJar_2;

import java.util.concurrent.Semaphore;

public class CookieJarProblem {
    
    public static void main (String [] args) {
        
        /* COMPLETE */
    	Semaphore mutex = new Semaphore(1, true);
        Semaphore adabarbCount = new Semaphore(0);
        Semaphore adacordCount = new Semaphore(0);
        Semaphore barbcordCount = new Semaphore(0);
        CookieJar jar = new CookieJar();
        
        Adelaide ada = new Adelaide(jar, adabarbCount, adacordCount,mutex);
        Barbara barb = new Barbara(jar, adabarbCount,barbcordCount,mutex);
        Cordelia cord = new Cordelia (jar, adacordCount,barbcordCount,mutex);
        
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
    private Semaphore mutex = new Semaphore(1);
    private Semaphore adabarbCount = new Semaphore(0);
    private Semaphore adacord = new Semaphore(0);
    private CookieJar jar;
    public Adelaide (CookieJar jar, 
            Semaphore adaCount,Semaphore adacord, Semaphore mutex) {
		this.jar = jar;
		this.adabarbCount = adaCount;
		this.adacord = adacord;
		this.mutex = mutex;
	}
    /* COMPLETE */
    public void run() {
    	while(true) {
    		mutex.acquireUninterruptibly();
    	jar.getACookie();
    	adabarbCount.release();
    	adacord.release();
    	mutex.release();
    	
    	System.out.println("ADA eats a cookie");
    	}
    	
    }
    
} 

class Barbara extends Thread {
	private Semaphore mutex = new Semaphore(1);
    private Semaphore adabarbCount = new Semaphore(0);
    private Semaphore barbcord = new Semaphore(0);
    private CookieJar jar;
    public Barbara (CookieJar jar, 
            Semaphore adaCount,Semaphore adacord, Semaphore mutex) {
		this.jar = jar;
		this.adabarbCount = adaCount;
		this.barbcord = adacord;
		this.mutex = mutex;
	}
    /* COMPLETE */
    public void run() {
    	while(true) {
    		adabarbCount.acquireUninterruptibly(2);
    	mutex.acquireUninterruptibly();
    	jar.getACookie();
    	barbcord.release();
    	adabarbCount.drainPermits();
    	mutex.release();
    	
    	System.out.println("	BARB eats a cookie");
    	}
    	
    }
}

class Cordelia extends Thread {
	private Semaphore mutex = new Semaphore(1);
    private Semaphore adacordCount = new Semaphore(0);
    private Semaphore barbcord = new Semaphore(0);
    private CookieJar jar;
    public Cordelia (CookieJar jar, 
            Semaphore adaCount,Semaphore adacord, Semaphore mutex) {
		this.jar = jar;
		this.adacordCount = adaCount;
		this.barbcord = adacord;
		this.mutex = mutex;
	}
    /* COMPLETE */
    public void run() {
    	while(true) {
    		adacordCount.acquireUninterruptibly(3);
        	barbcord.acquireUninterruptibly();
        	mutex.acquireUninterruptibly();
        	jar.getACookie();
        	adacordCount.drainPermits();
        	barbcord.drainPermits();
        	mutex.release();
        	
        	System.out.println("		CORD eats a cookie");
    	}
    }
}

class CookieJar {
    private volatile int taken = 0;
    
    public void getACookie () {
        taken++;
    }
    
    public int getTaken () {return taken;}
}