package client.ihm;

import java.awt.Color;

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

	public void printMessage(String message)
	{
		printMessage(message, "");
	}

	public abstract void printMessage(String message, Color color);

	public abstract void printMessage(String message, String style);

	public abstract String askPseudo();
}
