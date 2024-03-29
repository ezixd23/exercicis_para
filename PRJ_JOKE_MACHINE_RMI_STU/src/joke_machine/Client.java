package joke_machine;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Client extends JFrame {

	// BEWARE: modifiable / completable part starts at line 109
	
	private JPanel contentPane;
	private JButton btnConnect;
	private JButton btnNewJoke;
	private JButton btnStop;
	private JButton btnExit;
	private JScrollPane scrollPane;
	private JTextArea jokeArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
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
	public Client() {
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
				btnConnect_actionPerformed(arg0);
			}
		});
		btnConnect.setBounds(10, 25, 162, 39);
		contentPane.add(btnConnect);
		
		btnNewJoke = new JButton("New Joke");
		btnNewJoke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewJoke_actionPerformed(arg0);
			}
		});
		btnNewJoke.setEnabled(false);
		btnNewJoke.setBounds(10, 75, 162, 39);
		contentPane.add(btnNewJoke);
		
		btnStop = new JButton("Stop & disconnect");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStop_actionPerformed(arg0);
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
	
	//------------------------------------------------------
	// Completable/Modifiable part
	//------------------------------------------------------
	
	JokeService js;
	int id, numLines;
	
	protected void btnConnect_actionPerformed(ActionEvent arg0) {
		/* COMPLETE */
		// actions to perform when the user clicks on the connect button...
		
	}
	
	protected void btnNewJoke_actionPerformed(ActionEvent arg0) {
		/* COMPLETE */
		// actions to perform when the user clicks on the new joke button
		
	}
	
	protected void btnStop_actionPerformed(ActionEvent arg0) {
		/* COMPLETE */
		// actions to perform when the user clicks on the disconnect and exit button
		
	}
	
	protected void btnExit_actionPerformed(ActionEvent arg0) {
		System.exit(0);
	}
	
    
	
}
