package p01_CJ_ThreeSisters_Explicit;

import java.util.concurrent.locks.*;

public class CJ_threeSisters_Explicit {
	
	// Launcher. Do not modify
	public static void main (String [] args) throws InterruptedException {
		ThreeSistersMonitor monitor = new ThreeSistersMonitor();
		Sister ada = new Sister(Sister.Identity.ADA, monitor);
		Sister barb = new Sister(Sister.Identity.BARB, monitor);
		Sister cord = new Sister(Sister.Identity.CORD, monitor);
		
		cord.start();
		barb.start();
		ada.start();
		
		Thread.sleep(10000);
		
		cord.stop();
		barb.stop();
		ada.stop();
	}

}

class ThreeSistersMonitor {
	
	// Add here all the attributes of the monitor
	/* COMPLETE */
	private int AdaBarb = 0;
	private int AdaCord = 0;
	private int BarbCord = 0;

	private Lock lock = new ReentrantLock();
	private Condition barbCanTakes = lock.newCondition();
	private Condition cordCanTakes = lock.newCondition();
	public void adaTakes() {
		/* COMPLETE */
		lock.lock();
		System.out.println("ADA takes a cookie");
		AdaBarb ++;
		AdaCord ++;
		if(AdaBarb>=2) {
			barbCanTakes.signal();
		}
		if(AdaCord>=3 && BarbCord>=1) {
			cordCanTakes.signal();
		}
		lock.unlock();
	}
	
	public void barbTakes() {
		/* COMPLETE */
		lock.lock();
		while(!(AdaBarb>=2)) {
			barbCanTakes.awaitUninterruptibly();
		}
		BarbCord++;
		AdaBarb=0;
		if(AdaCord>=3 && BarbCord>=1) {
			cordCanTakes.signal();
		}
		System.out.println("\tBARB takes a cookie");
		/* COMPLETE */
		lock.unlock();
	}
	
	public void cordTakes() {
		/* COMPLETE */
		lock.lock();
		while(!(AdaCord>=3 && BarbCord>=1)) {
			cordCanTakes.awaitUninterruptibly();
			Thread.yield();
			
		}
		AdaCord=0;
		BarbCord=0;
		
		System.out.println("\t\tCORD takes a cookie");
		/* COMPLETE */
		
		lock.unlock();
	}
}

// -- DO NOT modify class Sister

class Sister extends Thread {
	
	public static enum Identity {ADA, BARB, CORD};
	
	private Identity identity;
	private ThreeSistersMonitor monitor;
	
	public Sister (Identity identity, ThreeSistersMonitor monitor) {
		this.identity = identity;
		this.monitor = monitor;
	}
	
	public void run () {
		while (true) {
			switch (identity) {
				case ADA:
					monitor.adaTakes();
					sleep(10);
					break;
				case BARB: 
					monitor.barbTakes();
					sleep(30);
					break;
				case CORD: 
					monitor.cordTakes();
					sleep(30);
					break;
			}
		}
	} 
	
	private void sleep (int s) {
		try {super.sleep(s);} catch(InterruptedException ie) {}
	}
}
