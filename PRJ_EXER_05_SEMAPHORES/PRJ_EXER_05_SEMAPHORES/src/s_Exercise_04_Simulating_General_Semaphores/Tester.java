package s_Exercise_04_Simulating_General_Semaphores;

import java.util.concurrent.Semaphore;

public class Tester {
    
    private static volatile int acquired = 0;
    private static Semaphore mutex = new BinarySemaphore(1);
    private static MyGeneralSemaphore permits = new MyGeneralSemaphore(4);
    
    public static void main (String [] args) {
    
        
        Thread acquirer = new Thread (new Runnable () {
                                public void run () {
                                    while (true)  {
                                       permits.acquire();
                                       System.out.println("        successfully acquired a permit");
                                       try{mutex.acquire();}catch(InterruptedException ie) {}
                                       acquired++;
                                       mutex.release();
                                       try{Thread.sleep(19);}catch(InterruptedException ie) {}
                                    }
                                }
                            }
                          );
        
        Thread releaser = new Thread (new Runnable () {
                                public void run () {
                                    int accqd;
                                    while (true) {
                                        try{mutex.acquire();}catch(InterruptedException ie) {}
                                        if (acquired>0) {
                                            permits.release();
                                            System.out.println("        successfully released a permit");
                                            acquired--;
                                        }
                                        else {
                                            System.out.println("                       no permit can be released now");
                                        }
                                        mutex.release();
                                        try{Thread.sleep(20);}catch(InterruptedException ie) {}
                                    }
                                }
                              }
                          );
        
        Thread stopper = new Thread(
                            new Runnable () {
                                public void run () {
                                    try {Thread.sleep(20000);} catch(InterruptedException ie){}
                                    System.exit(0);
                                }
                            }
                         );
        
        acquirer.start();
        releaser.start();
        stopper.start();
        
        int acqd;
        while (true) {
            acqd = acquired; 
            System.out.println("acquired permits: "+acqd);
            if (acqd<0 || acqd>4) {
                System.out.println("ERROR, ERROR, ERROR: "+acqd);
                System.exit(0);
            }
            try{Thread.sleep(2);}catch(InterruptedException ie) {}
            
        }
        
    }
    
        
    
}
