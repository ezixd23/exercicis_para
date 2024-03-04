package joke_machine;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class GUIClient extends JFrame {

	// do not modify this part. Modifiable/completable section starts at line 113 
	
	private JPanel contentPane;
	private JButton btnConnect;
	private JButton btnNewJoke;
	private JButton btnStop;
	private JButton btnExit;
	private JScrollPane scrollPane;
	private JTextArea jokeArea;

	private String text = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIClient frame = new GUIClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIClient() {
		setTitle("Joke Client");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 862, 441);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					btnConnect_actionPerformed(arg0);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		btnConnect.setBounds(10, 25, 162, 39);
		contentPane.add(btnConnect);
		
		btnNewJoke = new JButton("New Joke");
		btnNewJoke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					btnNewJoke_actionPerformed(arg0);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		btnNewJoke.setEnabled(false);
		btnNewJoke.setBounds(10, 75, 162, 39);
		contentPane.add(btnNewJoke);
		
		btnStop = new JButton("Stop & disconnect");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					btnStop_actionPerformed(arg0);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		btnStop.setEnabled(false);
		btnStop.setBounds(10, 125, 162, 39);
		contentPane.add(btnStop);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Jokes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		scrollPane.setBounds(198, 25, 626, 366);
		contentPane.add(scrollPane);
		
		jokeArea = new JTextArea();
		jokeArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		jokeArea.setEditable(false);
		scrollPane.setViewportView(jokeArea);
		
		btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnExit_actionPerformed(arg0);
			}
		});
		btnExit.setBounds(10, 352, 162, 39);
		contentPane.add(btnExit);
	}
	
	//-------- modifiable/completable part 
	
	protected void btnConnect_actionPerformed(ActionEvent arg0) throws IOException {
		/* COMPLETE */
		// actions to perform when the user clicks on the connect button...

		this.connect();

		// send a HELLO request and show the reply
		printline("Sending a HELLO request...");
		sendRequest("HELLO");
		String reply = receiveReply();
		printline("\tServer reply: "+reply);

		btnConnect.setEnabled(false);
		btnNewJoke.setEnabled(true);
		btnStop.setEnabled(true);
		btnExit.setEnabled(false);
	}
	
	protected void btnNewJoke_actionPerformed(ActionEvent arg0) throws IOException {
		/* COMPLETE */
		// actions to perform when the user clicks on the new joke button
		printline("Sending a JOKE request...");
		sendRequest("JOKE");
		String reply = receiveReply();
		printline("\tServer reply: "+reply);

		int numLines = Integer.parseInt(reply);
		printline("Server is about to send a joke of "
				+numLines+" lines");
		//System.out.println();
		for (int i=1; i<=numLines; i++) {
			reply=receiveReply();
			printline("\t"+reply);
		}
		printline("\n---- end of joke -----\n");
	}
	
	protected void btnStop_actionPerformed(ActionEvent arg0) throws IOException {
		this.disconnect();
		btnConnect.setEnabled(true);
		btnNewJoke.setEnabled(false);
		btnStop.setEnabled(false);
		btnExit.setEnabled(true);
	}
	
	protected void btnExit_actionPerformed(ActionEvent arg0) {
		try {
			disconnect();
		}
		catch (Exception ex) {}
		System.exit(0);
	}

	private void printline(String line){
		this.text += line + "\n";
		jokeArea.setText(text);
	}
	//---------------------------------------------
	
	//declare your connection and connection related attributes here... 
	private Socket connection;
	private BufferedReader inputChannel;
	private PrintWriter outputChannel;
	private void connect() throws IOException {
        connection = new Socket("localhost", 6768);
        inputChannel = new BufferedReader(
                           new InputStreamReader(
                               connection.getInputStream()));
        outputChannel = new PrintWriter(connection.getOutputStream(), true);
    }
    
    private void disconnect() throws IOException {
        inputChannel.close();
        outputChannel.close();       
        connection.close();
    }
    
    private String receiveReply() throws IOException {
        return inputChannel.readLine();    
    }
    
    private  void sendRequest(String request) throws IOException {
        outputChannel.println(request);
    }
	
}
