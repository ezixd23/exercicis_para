package joke_machine;

import java.rmi.registry.*;

public class ServiceLauncher {
	
	public static void main (String [] args) throws Exception {
		Registry registry = LocateRegistry.createRegistry(1999);
		registry.bind("JokeService", new JokeServiceImplementation());
		System.out.println("RMI-based joke service running... ");
	}

}
