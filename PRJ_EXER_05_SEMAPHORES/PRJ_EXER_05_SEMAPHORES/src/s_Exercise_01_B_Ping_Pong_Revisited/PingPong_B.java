package s_Exercise_01_B_Ping_Pong_Revisited;

import java.util.concurrent.Semaphore;

/***************************/
/*** ALTERNATION PATTERN **/
/**************************/

public class PingPong_B {
    
    public static void main (String [] args) {
       Synchronizer synchro = new Synchronizer();
        
        Ping [] pings = new Ping[10];
        Pong [] pongs = new Pong[10];
        
        for (int i=0; i<pings.length; i++) {
        	pings[i] = new Ping(i,synchro);
        	pongs[i] = new Pong(i,synchro);
        	pings[i].start();
        	pongs[i].start();
        }
        
        
        
        try {
            Thread.sleep(5000);
        }
        catch(InterruptedException ie) {}
        
        for (int i=0; i<pings.length; i++) {
        	pings[i].stop();
        	pongs[i].stop();
        }
    }
    
}

class Synchronizer {
	
	private Semaphore canPing = new Semaphore(1);
	private Semaphore canPong = new Semaphore(0);
	
	public void letMePing () {
		/* COMPLETE */
	}
	
	public void pingDone() {
		/* COMPLETE */
	}
	
	public void letMePong() {
		/* COMPLETE */
	}
	
	public void pongDone() {
		/* COMPLETE */
	}
	
}

class Ping extends Thread {
    
    private Synchronizer synchronizer;
    private int id;
    
    public Ping (int id, Synchronizer synchronizer) {
    	this.id = id;
        this.synchronizer = synchronizer;
    }
    
    public void run () {
        while (true) {
            synchronizer.letMePing();
            System.out.print("PING("+id+")");
            synchronizer.pingDone();
        }
    }
}

class Pong extends Thread {
    
	private Synchronizer synchronizer;
    private int id;
    
    public Pong (int id,Synchronizer synchronizer) {
    	this.synchronizer = synchronizer;
        this.id = id;
    }
    
    public void run () {
        while (true) {
        	synchronizer.letMePong();
        	System.out.println("-pong("+id+")");
        	synchronizer.pongDone();
        }
    }
}