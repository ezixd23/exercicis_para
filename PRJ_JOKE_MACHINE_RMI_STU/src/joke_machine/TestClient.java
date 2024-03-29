package joke_machine;

import java.rmi.registry.*;

public class TestClient {
	
	public static void main (String [] args) throws Exception {
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1999);
		JokeService js = (JokeService)registry.lookup("JokeService");
		
		int id, numLines;
		
		System.out.println("saying hello to server...");
		id = js.hello();
		System.out.println("\tServer reply: "+id);
		
		System.out.println("Sending a JOKE request...");
		numLines = js.joke(id);
		System.out.println("Server is ready to send a joke of " +numLines+" lines");
		
		System.out.println();
		for (int i=1; i<=numLines; i++) {
			System.out.println("\t"+js.nextLine(id));
		}
		System.out.println("\n---- end of joke -----\n");
		
		System.out.println("Asking for another joke...");
		numLines = js.joke(id);
		System.out.println("Server is ready to send a joke of " +numLines+" lines");
		
		System.out.println();
		for (int i=1; i<=numLines; i++) {
			System.out.println("\t"+js.nextLine(id));
		}
		System.out.println("\n---- end of joke -----\n");
		
		System.out.println("Sending a STOP request...");
		js.stop(id);
		System.out.println("...and disconnected");
	}

}
