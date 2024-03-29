package joke_machine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class JokeServiceImplementation 
             extends UnicastRemoteObject
             implements JokeService {

	
	// inner class to keep client state 
	class ClientState {
		String [] currentJoke = null;
		int nextLine = 0;
	}
	
	private ArrayList<String[]> theJokes;
	private Map<Integer, ClientState> clients;
	private int nextId;
	private Random alea;
	
	// Constructor
	protected JokeServiceImplementation() throws RemoteException {
		super();
		
		theJokes = new ArrayList<String[]>();
		clients = new HashMap<Integer, ClientState>();
		nextId = 1;
		alea = new Random();
		
		String line;
		List<String>joke;
		try {
			BufferedReader bur = new BufferedReader(new FileReader("Jokes.txt"));
			line = bur.readLine();
			while (line!=null) {
				joke = new ArrayList<String>();
				while(!line.equals("/*EOJ*/")) {
					joke.add(line);
					line=bur.readLine();
				}
				theJokes.add(joke.toArray(new String[0]));
				line = bur.readLine();
			}
			// when here end of file has been reached...
			bur.close();
		}
		catch (Exception ex) {
			System.out.println("unable to load jokes. Propagating exception");
			throw new RemoteException(ex.getMessage());
		}
	}

	@Override
	public int hello() throws RemoteException {
		/* COMPLETE */
	}

	@Override
	public int joke(int id) throws RemoteException {
		/* COMPLETE */
	}

	@Override
	public String nextLine(int id) throws RemoteException {
		/* COMPLETE */
	}

	@Override
	public void stop(int id) throws RemoteException {
		/* COMPLETE */
	}
	
}
