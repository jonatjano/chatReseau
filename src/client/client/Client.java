package client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client
{
	private static final String DEFAULT_IP = "127.0.0.1";
	private static final int DEFAULT_PORT = 6000;

	Socket sock;

	public Client(String ip, int port)
	{
		Scanner scIn;
		PrintWriter pwOut;
		try
		{
			sock = new Socket(ip, port);
			scIn = new Scanner(new InputStreamReader(sock.getInputStream()));
			pwOut = new PrintWriter(sock.getOutputStream());
		}
		catch (ConnectException myException) {
			System.out.println("connection refusée");
		}
		catch (UnknownHostException myException)
		{
			System.out.println("hôte inconnu");
			// myException.printStackTrace();
		}
		catch (IOException myException)
		{
			System.out.println("erreur lors de la connection, veuillez réesayer");
			// myException.printStackTrace();
		}


	}

	public static void main(String[] args)
	{
		String ip = DEFAULT_IP;
		int port = DEFAULT_PORT;

		if (args.length == 0)
		{
			System.out.println("Usage : java client <IP> <port>");
			System.out.println("Les IP (" + DEFAULT_IP + ") et port (" + DEFAULT_PORT + ") par defaut seront utilisé");
		}

		if (args.length == 1)
		{
			System.out.println("Usage : java client <IP> <port>");
			System.out.println("Le port (" + port + ") par defaut sera utilisé");
			if (args[0].matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}"))
			{
				ip = args[0];
			}
			else
			{
				System.out.println("L'ip entrée " + args[0] + " n'est pas valide, l'ip par defaut (" + DEFAULT_IP + ") va être utilisée");
			}
		}

		if (args.length >= 2)
		{
			if (args[0].matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}"))
			{
				ip = args[0];
			}
			else
			{
				System.out.println("L'ip entrée " + args[0] + " n'est pas valide, l'ip par defaut (" + DEFAULT_IP + ") va être utilisée");
			}
			try
			{
				port = Integer.parseInt(args[1]);
			}
			catch (Exception e)
			{
				System.out.println("Le port entré " + args[1] + " n'est pas valide, le port par defaut (" + DEFAULT_PORT + ") va être utilisé");
			}
		}

		new Client(ip, port);
	}
}
