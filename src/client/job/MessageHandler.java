package client.job;

import client.Client;

/**
 * @author Jonathan Selle, Adam Bernouy
 * @version 2017-12-23
 */
public class MessageHandler
{
	private static final String NAME_REQUEST = "NAME_REQUEST";
	private static final String NORMAL_MESSAGE = "NORMAL_MESSAGE";
	private static final String DISCONNECT = "DISCONNECTED";
	private static final String CONNECT = "CONNECTED";

	Client client;

	public MessageHandler(Client client)
	{
		this.client = client;
	}

	void onMessage(String message)
	{
		// String messageType = message.substring(0, message.indexOf(":"));
		// System.out.println(messageType);
		// String messageBody = message.substring(message.indexOf(":"));
		//
		// switch (messageType)
		// {
		// 	case NAME_REQUEST:
		// 		this.client.getIhm().askPseudo();
		// 	break;
		// 	case NORMAL_MESSAGE:
		// 		this.client.getIhm().printMessage(messageBody);
		// 	break;
		// 	case DISCONNECT:
		// 		this.client.getIhm().printMessage("Deconnexion de : " + messageBody);
		// 	break;
		// 	case CONNECT:
		// 		this.client.getIhm().printMessage("Connexion de : " + messageBody);
		// 	break;
		// }

		this.client.getIhm().printMessage(message);

		/*
NAME_REQUEST:
NORMAL_MESSAGE:NOM:msg
DISCONNECTED:NOM
CONNECTED:NOM
		 */
	}
}
