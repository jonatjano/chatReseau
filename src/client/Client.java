package client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import client.job.MessageReceiver;
import client.job.MessageWriter;
import client.job.MessageHandler;

import client.ihm.IHM;
import client.ihm.IHMCui;
import client.ihm.IHMSwing;

/**
 * @author Jonathan Selle, Adam Bernouy
 * @version 2017-12-23
 */
public class Client
{
	/**
	 * ip de connexion par defaut
	 */
	private static final String DEFAULT_IP = "127.0.0.1";
	/**
	 * port de connexion par defaut
	 */
	private static final int DEFAULT_PORT = 6000;

	/**
	 * socket de connexion au server
	 */
	Socket socket;

	/**
	 * objet gérant la reception de message venant du serveur (ne les traite pas)
	 * @see msgHandler
	 */
	MessageReceiver msgReceiver;
	/**
	 * Thread de msgIn
	 * @see msgReceiver
	 */
	Thread threadReceiver;
	/**
	 * objet gérant l'envoi des messages au serveur
	 */
	MessageWriter msgWriter;
	/**
	 * objet traitant les messages venant du serveur
	 */
	MessageHandler msgHandler;

	/**
	 * IHM du programme
	 */
	static IHM ihm;

	/**
	 * Creer un client en initialisant une Socket de mêmes parametres puis les objets de gestion des messages et l'IHM
	 * @param  ip           l'ip de la Socket à initialiser
	 * @param  port         le port de la Socket à initialiser
	 */
	public Client(String ip, int port)
	{
		try
		{
			socket = new Socket(ip, port);

			msgWriter = new MessageWriter(socket.getOutputStream());

			msgHandler = new MessageHandler(this);

			msgReceiver = new MessageReceiver(socket.getInputStream(), msgHandler);
			// new PrintWriter

			threadReceiver = new Thread(msgReceiver);
			threadReceiver.start();

			//msgWriter.sendMessage(ihm.askPseudo());
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

	public MessageWriter getMessageWriter()
	{
		return msgWriter;
	}

	public IHM getIhm()
	{
		return ihm;
	}

	/**
	 * méthode d'entrée dans le programme
	 * @param args tableau contenant les valeurs passée dans la ligne de commande
	 *             		Il peut avoir plusieurs formes de contenu :
	 *             			{} : un tableau vide, les port et IP par defaut sont utilisés
	 *                      {IP} :
	 *                      	La valeur unique est l'IP du serveur, si elle n'est pas valide elle sera remplacée par l'IP par defaut.
	 *                      	Le port utilisé est le port par defaut
	 *                      {IP, port[, inutilisé]} :
	 *                      	Comme dans le cas d'un argument unique le premier est un IP vérifié et remplacé au besoin.
	 *                      	Le port est lui aussi testé et remplacé par le port par défaut s'il est invalide.
	 *                      	Toute valeurs supplémentaire est ignorée.
	 * @see DEFAULT_IP
	 * @see DEFAULT_PORT
	 */
	public static void main(String[] args)
	{
		ihm = new IHMSwing();

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

		ihm.setClient(new Client(ip, port));
	}
}
