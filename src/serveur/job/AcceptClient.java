package server.job;

import java.net.ServerSocket;
import java.net.Socket;
import server.Server;
import server.ihm.IHM;
import java.util.List;
import java.util.ArrayList;
import java.lang.Runtime;


public class AcceptClient implements Runnable
{
	
	private ServerSocket serverSock;
	private Server serv;
	private List<GerantDeClient> listGerantClient;
	private List<Thread> listThreadGerantClient;
	
	public AcceptClient(ServerSocket serverSock, Server s)
	{
		this.serverSock = serverSock;
		this.serv = s;
		this.listGerantClient = new ArrayList<GerantDeClient>();
		this.listThreadGerantClient = new ArrayList<Thread>();
	}
	
	private void sendInfo(GerantDeClient gdc, String s)
	{
		gdc.sendMsg( s);
	}
	
	public void messageReceive(String s, GerantDeClient  gdc)
	{
		for ( GerantDeClient gdcTemp : listGerantClient)
			this.sendInfo( gdcTemp, gdc.getName() + " : " + s);
	}
	
	public void connection(GerantDeClient gdc)
	{
		serv.getIHM().pMessage(IHM.NEW_CLIENT_MESSAGE, gdc.getName());
		
		for ( GerantDeClient gdcTemp : listGerantClient)
			this.sendInfo( gdcTemp, "--> connection : " + gdc.getName() + " ");
	}
	
	public void deconnection(GerantDeClient gdc)
	{
		String name = gdc.getName();
		if (name == null)
			return;
		
		serv.getIHM().pMessage(IHM.QUIT_CLIENT_MESSAGE,name);
		
		for ( GerantDeClient gdcTemp : listGerantClient)
			this.sendInfo( gdcTemp, "--> déconnection : " + name + " ");
	}
	
	public void remove(GerantDeClient  gdc)
	{
		int ind = listGerantClient.indexOf(gdc);
		
		
		this.deconnection( this.listGerantClient.get(ind) );
		
		this.listGerantClient.remove(ind);
		this.listThreadGerantClient.get(ind).interrupt();
		this.listThreadGerantClient.remove(ind);
		
		Runtime.getRuntime().gc();
	}
	
	public void run()
	{
		while (true)
		{
			try
			{
				Socket client = serverSock.accept();
				
				GerantDeClient gdc = new GerantDeClient(client, this);
				Thread tgdc = new Thread(gdc);
				
				listGerantClient.add(gdc);
				listThreadGerantClient.add(tgdc);
				
				tgdc.start();
				Thread.sleep(2000);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}