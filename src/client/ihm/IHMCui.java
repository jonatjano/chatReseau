package client.ihm;

import java.util.Scanner;

import client.Client;

public class IHMCui extends IHM
{
	Thread threadIn;
	Scanner scanIn;

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
