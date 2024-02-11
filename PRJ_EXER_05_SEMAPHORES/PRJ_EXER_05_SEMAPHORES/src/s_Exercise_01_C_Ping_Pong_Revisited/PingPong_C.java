package s_Exercise_01_C_Ping_Pong_Revisited;

import java.util.concurrent.Semaphore;

/***************************/
/*** ALTERNATION PATTERN **/
/**************************/

public class PingPong_C {
    
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
	
	private Semaphore binSem = new Semaphore(1);  
	/* COMPLETE */
	private int turn = 0;
	public void letMePing () {
		/* COMPLETE */
	     while (true) {
	            binSem.acquireUninterruptibly();
	            if (turn == 0) {
	                break;
	            } else {
	                binSem.release();
	            }
	        }
	}
	
	public void pingDone() {
		/* COMPLETE */
		turn = 1;
		binSem.release();
		
	}
	
	public void letMePong() {
		/* COMPLETE */
	     while (true) {
	            binSem.acquireUninterruptibly();
	            if (turn == 1) {
	                break;
	            } else {
	                binSem.release();
	            }
	        }
	}
	
	public void pongDone() {
		/* COMPLETE */
		turn = 0;
		binSem.release();
		
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