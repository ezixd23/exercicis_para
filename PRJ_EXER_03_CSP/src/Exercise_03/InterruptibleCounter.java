package Exercise_03;

//do not modify this class

public class InterruptibleCounter {
 
 private int value = 0;
 
 public int getValue () {
     return this.value;
 }
 
 public void increment () {
     int temp;
     
     temp = value; // read value
     simulateHWInterrupt();
     value = temp+1; // write value
 }
 
 private static void simulateHWInterrupt () {
     if (Math.random()>0.50) {
         Thread.yield();
     }
 } 
}
