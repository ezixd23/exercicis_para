package s_Exercise_04_Simulating_General_Semaphores;

public class MyGeneralSemaphore extends Object {
    
    private volatile int permits;
    
    private BinarySemaphore mutex;
    private BinarySemaphore gate;
    
    public MyGeneralSemaphore (int permits) {
       /* COMPLETE */
    }
    
    public void acquire () { // wait on the semaphore
       /* COMPLETE */
        
    }
    
    public void release () { // release
        
        /* COMPLETE */ 
        
    }
    
}
