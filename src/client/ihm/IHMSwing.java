package client.ihm;

import java.awt.BorderLayout;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

import client.Client;

public class IHMSwing extends IHM implements KeyListener, ActionListener
{
	private JFrame frame;
	private JTextArea recepField;
	private JTextField sendField;
	private JButton sendButton;

	public IHMSwing()
	{
		frame = new JFrame("Client");
		frame.setLayout(new BorderLayout());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		recepField = new JTextArea();
		recepField.setEditable(false);
		frame.add(recepField, BorderLayout.CENTER);

		JPanel botPanel = new JPanel(new BorderLayout());
		{
			sendField = new JTextField();
			sendField.addKeyListener(this);
			botPanel.add(sendField, BorderLayout.CENTER);

			sendButton = new JButton("Envoyer");
			sendButton.addActionListener(this);
			botPanel.add(sendButton, BorderLayout.EAST);
		}
		frame.add(botPanel, BorderLayout.SOUTH);

		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	public void printMessage(String message)
	{
		recepField.append("\n" + message);
	}

	public String askPseudo()
	{
		String s = "";
		do
		{
			s = (String)JOptionPane.showInputDialog(
	                frame,
	                "Veuillez entrer votre pseudo:\n",
	                "Votre pseudo",
	                JOptionPane.PLAIN_MESSAGE
				);
		} while ((s == null) || (s.length() == 0));
		return s;
	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println("a");
		if (e.getSource() == sendButton)
		{
			System.out.println("b");
			String message = sendField.getText().replaceAll("[ \t\n]", "");
			if (!message.equals(""))
			{
				client.getMessageWriter().sendMessage(sendField.getText());
				sendField.setText("");
			}
		}
	}

	public void keyPressed(KeyEvent e)
	{
		// TODO
	}

	public void keyReleased(KeyEvent e)
	{
		// TODO
	}

	public void keyTyped(KeyEvent e)
	{
		// TODO
	}
}
