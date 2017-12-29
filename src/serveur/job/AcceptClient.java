package server.job;

import java.net.ServerSocket;
import java.net.Socket;
import server.Server;
import server.ihm.IHM;
import server.job.Command;
import java.util.List;
import java.util.ArrayList;
import java.lang.Runtime;


public class AcceptClient implements Runnable
{
	public static final String NAME_REQUEST_CLIENT   = "NAME_REQUEST";
	public static final String RECEIVE_MESSAGE   = "NORMAL_MESSAGE";
	public static final String DISCONNECT_CLIENT = "DISCONNECTED";
	public static final String CONNECT_CLIENT = "CONNECTED";
	public static final String NORMAL_COMMAND_TYPE = "NORMAL_COMMAND";
	public static final String ERROR_COMMAND_TYPE = "ERROR_COMMAND";
	
	
	public static final int WAITING_TIME_CONNECTION = 500;
	
	private ServerSocket serverSock;
	private Server serv;
	private List<GerantDeClient> listGerantClient;
	private List<Thread> listThreadGerantClient;
	
	public AcceptClient(ServerSocket serverSock, Server s)
	{
		this.serverSock = serverSock;
		this.serv = s;
		Command.setAcceptClient(this);
		this.listGerantClient = new ArrayList<GerantDeClient>();
		this.listThreadGerantClient = new ArrayList<Thread>();
	}
	
	void sendInfo(GerantDeClient gdc, String type, String s)
	{
		String sSend = type + ":";
		if (s != null)
			sSend += s;
		
		gdc.sendMsg(sSend);
	}
	
	public void messageReceive(String s, GerantDeClient  gdc)
	{
		if (!s.startsWith("/"))
		{
			for ( GerantDeClient gdcTemp : listGerantClient)
				this.sendInfo( gdcTemp, AcceptClient.RECEIVE_MESSAGE , gdc.getName() + ":" + s);
			
			return;
		}
		if (s.length() <=  1)
			return;
		
		CommandExec(s.substring(1), gdc);
	}
	
	private void CommandExec(String commandString, GerantDeClient gdc)
	{
		String commandName = commandString;
		int indSpace = commandString.indexOf(" ");
		if (indSpace != -1)
			commandName = commandString.substring(0,indSpace);
		
		Command command = Command.getCommand(commandName.toUpperCase());
		if (command != null)
			command.exec(gdc, commandString);
		else
			Command.error(gdc);
	}
	
	public void connection(GerantDeClient gdc)
	{
		serv.getIHM().pMessage(IHM.NEW_CLIENT_MESSAGE, gdc.getName());
		
		for ( GerantDeClient gdcTemp : listGerantClient)
			this.sendInfo( gdcTemp, AcceptClient.CONNECT_CLIENT, gdc.getName());
	}
	
	public void deconnection(GerantDeClient gdc)
	{
		String name = gdc.getName();
		if (name == null)
			return;
		
		serv.getIHM().pMessage(IHM.LEAVE_CLIENT_MESSAGE,name);
		
		for ( GerantDeClient gdcTemp : listGerantClient)
			this.sendInfo( gdcTemp, AcceptClient.DISCONNECT_CLIENT, name);
	}
	
	public boolean nameIsUsed(String name)
	{
		if (name == null)
			return true;
		
		
		for (GerantDeClient gdc : listGerantClient)
			if (gdc.getName() != null && gdc.getName().equals(name))
				return true;
		
		return false;
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
				Thread.sleep(AcceptClient.WAITING_TIME_CONNECTION);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}