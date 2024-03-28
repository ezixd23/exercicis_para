package Exercise_03;

public class MultiLauncher {
	
	public static void main (String [] args) throws InterruptedException{

        boolean result = false;
        int times = 0;
        while (!result) {
            result = launcherProcedure();
            times++;
        }
        
        System.out.println("failure of the mutual exclusion after: "+times+" executions");
    }
	
	private static boolean launcherProcedure () throws InterruptedException {
        // launches an east and a west turnstile
        // returns true if the final value in the global counter is not
        // the expected one (40)

        // reset the static components of the class
        Turnstile_TENTATIVE_2.reset();
        
        
        Turnstile_TENTATIVE_2 west, east;
        
        InterruptibleCounter iCounter = new InterruptibleCounter();

        west = new Turnstile_TENTATIVE_2("West", iCounter);
        east = new Turnstile_TENTATIVE_2("East", iCounter);

        west.start();
        east.start();

        while (west.isAlive() || east.isAlive()) {
                Thread.sleep(500);
                System.out.println("Global counter: "+iCounter.getValue());
        }
        System.out.println();
        System.out.println("FINAL GLOBAL COUNTER: "+iCounter.getValue());

        return iCounter.getValue()!=40;
    }

    
}
