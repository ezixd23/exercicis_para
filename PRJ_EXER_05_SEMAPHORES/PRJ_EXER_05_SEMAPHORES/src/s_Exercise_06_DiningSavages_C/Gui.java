package s_Exercise_06_DiningSavages_C;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;
import java.awt.Color;
import java.util.concurrent.Semaphore;

public class Gui extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton startButton;
	private JSeparator separator;
	private JLabel lblAvailableServings;
	private JLabel lblWaitingSavages;
	private JLabel lblIdOfSavage;
	private JLabel emptyLabel;
	private JButton refillButton;
	private JLabel numServingsLabel;
	private JLabel numSavagesLabel;
	private JLabel idLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
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
	public Gui() {
		setTitle("COOK OPERATOR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 549, 387);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		
		this.startButton = new JButton("START");
		this.startButton.addActionListener(this);
		
		this.separator = new JSeparator();
		
		this.lblAvailableServings = new JLabel("Available servings:");
		this.lblAvailableServings.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		this.lblWaitingSavages = new JLabel("Waiting savages:");
		this.lblWaitingSavages.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		this.lblIdOfSavage = new JLabel("ID of savage accessing the pot:");
		this.lblIdOfSavage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		this.emptyLabel = new JLabel("POT IS EMPTY");
		this.emptyLabel.setVisible(false);
		this.emptyLabel.setForeground(Color.RED);
		this.emptyLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		this.refillButton = new JButton("REFILL");
		this.refillButton.addActionListener(this);
		this.refillButton.setEnabled(false);
		
		this.numServingsLabel = new JLabel("--");
		this.numServingsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		this.numSavagesLabel = new JLabel("--");
		this.numSavagesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		this.idLabel = new JLabel("--");
		this.idLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(131, Short.MAX_VALUE)
					.addComponent(this.startButton, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
					.addGap(120))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(this.separator, GroupLayout.PREFERRED_SIZE, 507, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(this.lblAvailableServings)
					.addGap(18)
					.addComponent(this.numServingsLabel)
					.addContainerGap(342, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(this.lblWaitingSavages, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(this.numSavagesLabel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(336, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(143)
					.addComponent(this.emptyLabel, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
					.addGap(14)
					.addComponent(this.refillButton)
					.addContainerGap(158, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(this.lblIdOfSavage, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(this.idLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(210, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(39)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(this.lblAvailableServings)
						.addComponent(this.numServingsLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(this.lblWaitingSavages, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.numSavagesLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(49)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(this.lblIdOfSavage, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.idLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(42)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(this.emptyLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.refillButton))
					.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
					.addComponent(this.separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(this.startButton)
					.addContainerGap())
		);
		this.contentPane.setLayout(gl_contentPane);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == this.refillButton) {
			refillButtonActionPerformed(arg0);
		}
		if (arg0.getSource() == this.startButton) {
			btnNewButtonActionPerformed(arg0);
		}
	}
	protected void btnNewButtonActionPerformed(ActionEvent arg0) {
		this.startButton.setEnabled(false);
		
		/* COMPLETE */
	}
	
	private Cook cook;
	
	protected void refillButtonActionPerformed(ActionEvent arg0) {
		/* COMPLETE */
	}
}


