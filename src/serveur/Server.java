package server;

import java.net.ServerSocket;
import server.job.AcceptClient;
import server.ihm.IHM;
import server.ihm.IHMConsol;


public class Server
{
	
	public static final int DEFAULT_PORT = 6000;
	
	private ServerSocket serverSock;
	private AcceptClient accCli;
	private Thread thAccCli;
	private IHM ihm;
	
	public Server(IHM ihm)
	{
		this(Server.DEFAULT_PORT, ihm);
	}
	
	public Server(int port, IHM ihm)
	{
		try
		{
			this.serverSock = new ServerSocket(port);
			this.accCli = new AcceptClient(serverSock, this);
			this.thAccCli = new Thread(this.accCli);
			this.thAccCli.start();
			
			this.ihm = ihm;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] arg)
	{
		IHM ihm = new IHMConsol();
		
		int port = Server.DEFAULT_PORT;
		if (arg.length >= 1)
		{
			try
			{
				port = Integer.parseInt(arg[1]);
			} 
			catch(Exception e)
			{
				ihm.pError(IHM.PORT_ERROR);
			}
		}
		
		Server s  = new Server(port,ihm);
	}
	
}
