package client.job;

import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author Jonathan Selle, Adam Bernouy
 * @version 2017-12-23
 */
public class MessageWriter
{
	private PrintWriter writer;

	/**
	 * permet d'envoyer des messages au serveur
	 * @param  socketOutputStream objet {@link OutputStream} de la {@link Socket} du client
	 */
	public MessageWriter(OutputStream socketOutputStream)
	{
		writer = new PrintWriter(new OutputStreamWriter(socketOutputStream, StandardCharsets.UTF_8), true);
	}

	public void sendMessage(String message)
	{
		writer.println(message);
	}
}
