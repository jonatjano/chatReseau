package server.ihm;

import server.ihm.IHM;

public class IHMConsol extends IHM
{
	public void pError(int numErr)
	{
		switch (numErr)
		{
			case IHM.PORT_ERROR:
				System.out.println("erreur sur le port");
			
			default:
				System.out.println("erreur inconnue");
		}
		
	}
	
	public void pMessage(int numMsg, String s)
	{
		switch (numMsg)
		{
			case IHM.NORMAL_MESSAGE:
				System.out.println(s);
				break;
				
			case IHM.NEW_CLIENT_MESSAGE:
				System.out.println("--> connection : " + s);
				break;
				
			case IHM.QUIT_CLIENT_MESSAGE:
				System.out.println("--> déconnection : " + s);
				break;
			
			default:
				System.out.println("type de message inconnue");
				break;
		}
		
	}
}