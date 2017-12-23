package client.job;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author Jonathan Selle, Adam Bernouy
 * @version 2017-12-23
 */
public class MessageReceiver implements Runnable
{
	private BufferedReader reader;
	private MessageHandler msgHandler;
	private boolean stop;

	public MessageReceiver(InputStream socketInputStream, MessageHandler msgHandler)
	{
		reader = new BufferedReader(new InputStreamReader(socketInputStream, StandardCharsets.UTF_8));
		stop = false;
		this.msgHandler = msgHandler;
	}

	public void run()
	{
		while (!stop)
		{
			try {
				String line = reader.readLine();
				msgHandler.onMessage(line);
			}
			catch (Exception e) {

			}
		}
	}
}
