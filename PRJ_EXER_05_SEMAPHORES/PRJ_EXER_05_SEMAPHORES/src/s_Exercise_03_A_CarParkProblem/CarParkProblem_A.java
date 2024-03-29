package s_Exercise_03_A_CarParkProblem;

import java.util.concurrent.Semaphore;

public class CarParkProblem_A {
    
    private static final long TIME_TO_RUN = 20000L; // 20 seconds or so
    
    public static void main (String [] args) {
        
        CarPark carPark = new CarPark();
        
        Car [] fleet = new Car[10];
        for (int i=0; i<fleet.length; i++) {
            fleet[i] = new Car("car-"+(i+1),carPark);
            fleet[i].start();
        }
        
        Thread stopper = new Thread(
                            new Runnable () {
                                public void run () {
                                    try {Thread.sleep(TIME_TO_RUN);} catch(InterruptedException ie){}
                                    System.exit(0); // forcibly stops everything (stops the JVM)
                                }
                            }
                         );
        stopper.start();
        
        int occpd;
        
        while (true) {
            occpd = carPark.getOccupied();
            System.out.println("Cars currently in car park: "+occpd);
            if (occpd<0 || occpd>4) {
                System.out.println("ERROR, ERROR, ERROR "+occpd);
                System.exit(0);
            }
            try {Thread.sleep(5);} catch(InterruptedException ie){};
        }
    }
}


class CarPark {
    
    private static final int PARKING_SPACES = 4;
   
    private SyncCounter occupied = new SyncCounter(); // this counter is only
    // for testing purposes. Do not take it into account. Do not use it. Do not 
    // base your solution on it. Just ignore it. 
    public int getOccupied () {return occupied.getCounter();} // ignore this too
    
    /* COMPLETE declare and create semaphore(s) here */
    private Semaphore mutex = new Semaphore(PARKING_SPACES);
    
    public void enter () {
    	/* COMPLETE */
        mutex.acquireUninterruptibly();
        occupied.inc(); // do not add code after this line  
    }
    
    public void exit () {
    	occupied.dec(); // do not add code before this line
    	mutex.release();
    }
}

class Car extends Thread {
    // do not modify this class
    private static final long DRIVE = 1;
    private static final long PARK = 5;
    
    private String id;
    private CarPark cp;
    
    public Car (String id, CarPark cp) {
        this.id = id;
        this.cp = cp;
    }
    
    @Override
    public void run () {
        long drivingTime = (long)(Math.random()*DRIVE);
        long parkingTime = (long)(Math.random()*PARK);
        
        while (true) {    
            // drive around for a while
            try{Thread.sleep(drivingTime);} catch(InterruptedException ie){}
            // park
            cp.enter();
            
            // now stay in the parking lot for a while
            try{Thread.sleep(parkingTime);} catch(InterruptedException ie){}
            //exit
            cp.exit();
        }
    }
}

class SyncCounter  { // do not modify this class
	private volatile int counter = 0;
	public synchronized int getCounter () {return counter;}
	public synchronized void inc ()  {counter++;}
	public synchronized void dec () {counter--;}
}
