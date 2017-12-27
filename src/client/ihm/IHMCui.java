package client.ihm;

import java.util.Scanner;
import java.awt.Color;

import client.Client;

public class IHMCui extends IHM
{
	private Thread threadIn;
	private Scanner scanIn;

	public IHMCui()
	{
		scanIn = new Scanner(System.in);
	}

	public void printMessage(String message, Color color)
	{
		printMessage(message);
	}

	public void printMessage(String message, String style)
	{
		System.out.println(message);
	}

	public String askPseudo()
	{
		String pseudo = "";

		do
		{
			System.out.println("Veuillez entrer votre pseudo");
			pseudo = scanIn.nextLine();
			pseudo = pseudo.replaceAll("[ \t\n]*", "");
		} while (pseudo.equals(""));

		threadIn = new Thread(new InReader());
		threadIn.start();

		return pseudo;
	}

	private class InReader implements Runnable
	{
		public void run()
		{
			while(scanIn.hasNextLine())
			{
				client.getMessageWriter().sendMessage(scanIn.nextLine());
			}
		}
	}
}
