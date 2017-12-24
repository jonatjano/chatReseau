package client.ihm;

import java.util.Scanner;

import client.Client;

public class IHMCui extends IHM
{
	private Client client;

	private Thread threadIn;
	private Scanner scanIn;

	public IHMCui()
	{
		scanIn = new Scanner(System.in);
	}

	public void printMessage(String message)
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

	public boolean setClient(Client client)
	{
		if (this.client == null)
		{
			this.client = client;
			return true;
		}
		return false;
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
