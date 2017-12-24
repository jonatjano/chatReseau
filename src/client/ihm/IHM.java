package client.ihm;

import client.Client;

public interface IHM
{
	public boolean setClient(Client client);

	public abstract void printMessage(String message);

	public abstract String askPseudo();
}
