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
				break;

			case IHM.CONF_PORT_ERROR:
				System.out.println("le port specifié dans le fichier de configuration n'est pas valide");

			default:
				System.out.println("erreur inconnue");
		}

	}

	public void pMessage(int numMsg, String s)
	{
		switch (numMsg)
		{
			case IHM.SERVER_INFO:
				System.out.println("--> Serveur ouvert sur l'ip \"" + s.split(":")[0] + "\" et le port " + s.split(":")[1]);
				break;

			case IHM.NORMAL_MESSAGE:
				System.out.println(s);
				break;

			case IHM.NEW_CLIENT_MESSAGE:
				System.out.println("--> connection : " + s);
				break;

			case IHM.LEAVE_CLIENT_MESSAGE:
				System.out.println("--> déconnection : " + s);
				break;

			default:
				System.out.println("type de message inconnue");
				break;
		}

	}
}
