package joke_machine;

import java.rmi.*;

public interface JokeService extends Remote {
	
	public int hello () throws RemoteException;
	// Client invokes hello in order to obtain an id that 
	// it will use in any future interaction.
	
	public int joke (int id) throws RemoteException;
	// Client invokes this method to manifest its intention
	// to get a joke. Parameter is the id that identifies the
	// client. Result is the number of lines of the selected joke
	
	public String nextLine(int id) throws RemoteException;
	// This method is invoked to get a line of a joke. The number 
	// of lines was the result of the previous method. 
	// The parameter identifies the client.
	
	public void stop(int id) throws RemoteException;
	// This method "disconnects" the client (informs the server
	// that no further interaction will follow. 
	// The parameter identifies the client.
}
