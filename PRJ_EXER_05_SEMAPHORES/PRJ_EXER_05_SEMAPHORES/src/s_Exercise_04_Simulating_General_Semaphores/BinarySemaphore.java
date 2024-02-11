package s_Exercise_04_Simulating_General_Semaphores;

import java.util.concurrent.Semaphore;

public class BinarySemaphore extends Semaphore {

    public BinarySemaphore (int initialValue) {
        super(initialValue, true);
        synchronized(this) {
            if (initialValue!=0 && initialValue!=1)
                throw new IllegalArgumentException(initialValue+"");
        }
    }
    
    // acquire has to have the general behaviour thus no redefinition is required
    
    // release has to be redefined so that an exception can be thrown if value is 
    // greater than 0
    
    @Override
    public synchronized void release () {
        if (this.availablePermits()!=0)
            throw new IllegalStateException("release invoked when number of permits was greater than zero");
        super.release();
    } 
    
}
