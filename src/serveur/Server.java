package server;

import java.net.ServerSocket;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
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
	
	public IHM getIHM()
	{
		return this.ihm;
	}
	
	private String getIp() throws Exception
	{
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
		
		String ip = in.readLine();
		if (ip.matches("([0-9]{1,3}.){3}[0-9]{0,3}"))
			return ip;
		
		return Inet4Address.getLocalHost().getHostAddress();
	}
	
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
			ihm.pMessage(IHM.SERVER_INFO, this.getIp() + ":" + port);
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
