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

	public static final String NORMAL_COMMAND = "NORMAL_COMMAND";
	public static final String ERROR_COMMAND = "ERROR_COMMAND";


	Client client;

	public MessageHandler(Client client)
	{
		this.client = client;
	}

	void onMessage(String message)
	{
		String messageType = message.substring(0, message.indexOf(":"));
		String messageBody = message.substring(message.indexOf(":") + 1);

		switch (messageType)
		{
			case NAME_REQUEST:
				this.client.getMessageWriter().sendMessage(this.client.getIhm().askPseudo());
				break;
			case NORMAL_MESSAGE:
				this.client.getIhm().printMessage(messageBody.substring(0, messageBody.indexOf(":")) + " : " + messageBody.substring(messageBody.indexOf(":") + 1));
				break;
			case DISCONNECT:
				this.client.getIhm().printMessage("Deconnexion de : " + messageBody);
				break;
			case CONNECT:
				this.client.getIhm().printMessage("Connexion de : " + messageBody);
				break;
			case NORMAL_COMMAND:
				this.client.getIhm().printMessage(messageBody.replaceAll("\\\\n","\n").replaceAll("\\\\t","\t"), NORMAL_COMMAND);
				break;
			case ERROR_COMMAND:
				this.client.getIhm().printMessage(messageBody.replaceAll("\\\\n","\n").replaceAll("\\\\t","\t"), ERROR_COMMAND);
				break;
		}
	}
}
