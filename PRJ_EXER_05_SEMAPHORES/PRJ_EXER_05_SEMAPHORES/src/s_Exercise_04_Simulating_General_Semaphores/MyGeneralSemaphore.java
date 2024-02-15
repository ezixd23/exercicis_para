package s_Exercise_04_Simulating_General_Semaphores;

public class MyGeneralSemaphore extends Object {
    
    private volatile int permits;
    
    private BinarySemaphore mutex;
    private BinarySemaphore gate;
    
    public MyGeneralSemaphore (int permits) {
       /* COMPLETE */
    	this.permits = permits;
    	mutex = new BinarySemaphore(1);
    	gate = new BinarySemaphore(0);
    }
    
    public void acquire () { // wait on the semaphore
       /* COMPLETE */
    	 try {
			mutex.acquire();
         permits--;
         if (permits < 0) {
             mutex.release();
             gate.acquire();
         } else {
             mutex.release();
         }
    	 } catch (InterruptedException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    }
    
    public void release () { // release
        
        /* COMPLETE */ 
    	try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        permits++;
        if (permits <= 0) {
            gate.release();
        }
        mutex.release();
    }
    
}
