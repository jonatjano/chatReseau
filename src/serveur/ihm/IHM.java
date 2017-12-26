package server.ihm;

public abstract class IHM
{
	
	public static final int PORT_ERROR = 1;
	
	public static final int NORMAL_MESSAGE = 0;
	public static final int NEW_CLIENT_MESSAGE = 1;
	public static final int QUIT_CLIENT_MESSAGE = 2;
	
	public abstract void pError(int numErr);
	
	public abstract void pMessage(int numMsg, String s);
	
	public void pMessage(int numMsg)
	{
		pMessage(numMsg, "");
	}
	
}