package server.job;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client
{
	private static final String REG_NAME_CLIENT = "[a-zA-Z0-9_-]{4,}";
	
	private PrintWriter 	out;
	private BufferedReader 	in;
	
	private String name;
	
	
	public Client(Socket s)
	{
		this.name = null;
		
		try
		{
			this.out = new PrintWriter(s.getOutputStream(),true);
			this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	public void sendMsg(String s) throws Exception
	{
		this.out.println(s);
	}
	
	public String receiveMsg() throws Exception
	{
		String s = this.in.readLine();
		
		if (s == null)
			throw new Exception();
		
		return s;
	}
	
	public void end()
	{
		try
		{
			this.out.close();
			this.in.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean setName(String name)
	{
		if (this.name != null)
			return false;
		
		if (!name.matches(Client.REG_NAME_CLIENT))
			return false;
		
		this.name = name;
		return true;
	}
	
	public String getName() { return this.name; }
	
}