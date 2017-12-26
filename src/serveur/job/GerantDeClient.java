package server.job;

import java.net.Socket;

public class GerantDeClient implements Runnable
{
	private AcceptClient accClient;
	private Client client;
	
	public GerantDeClient(Socket client, AcceptClient accClient)
	{
		this.client = new Client(client);
		this.accClient = accClient;
	}
	
	public void sendMsg(String s)
	{
		try
		{
			client.sendMsg(s);
		}
		catch(Exception e)
		{
			this.end();
		}
	}
	
	public String getName()
	{
		return client.getName();
	}
	
	public void end()
	{
			this.accClient.remove(this);
			client.end();
			this.client = null;
			this.accClient = null;
	}
	
	public void run()
	{
		boolean end = false;
		
		try
		{
			String name;
			do
			{
				accClient.sendInfo(this, AcceptClient.NAME_REQUEST_CLIENT, null);
				name = client.receiveMsg();
				accClient.nameIsUsed(name);
				
			} while ( accClient.nameIsUsed(name)  || !client.setName(name));
			
			this.accClient.connection(this);
		}
		catch(Exception e)
		{
			end = true;
		}
		
		while (!end)
		{
			try
			{
				String msg = client.receiveMsg();
				this.accClient.messageReceive(msg, this);
			}
			catch(Exception e)
			{
				end = true;
			}
		}
		this.end();
	}
	
}