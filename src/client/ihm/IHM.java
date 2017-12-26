package client.ihm;

import client.Client;

public abstract class IHM
{
	protected Client client;

	public boolean setClient(Client client)
	{
		if (this.client == null)
		{
			this.client = client;
			return true;
		}
		return false;
	}

	public abstract void printMessage(String message);

	public abstract String askPseudo();
}
