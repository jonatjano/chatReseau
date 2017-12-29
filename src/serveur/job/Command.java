package server.job;

import server.job.AcceptClient;
import server.job.GerantDeClient;

enum Command
{
	HELP("HELP", "commande qui donne de l'aide", "cette commande permet d'obtenir de l'aide");

	private String name = "";
	private String shortDesc = "";
	private String longDesc = "";
	private static AcceptClient accCli;

	private Command(String name, String shortDesc, String longDesc)
	{
		this.name = name;
		this.shortDesc = shortDesc;
		this.longDesc = longDesc;
	}


	public static Command getCommand(String name)
	{
		for(Command command : Command.values())
			if (command.name.equals(name))
				return command;
		
		return null;
	}

	public void exec(GerantDeClient gdc, String s)
	{
		switch(this)
		{
			case HELP:
				help(gdc);
				break;
				
			default:
				error(gdc);
				break;
		}
	}
	
	private static void help(GerantDeClient gdc)
	{
		String sSend = "Voici la liste des Commandes :";
		
		for (Command command : Command.values())
			sSend += "\\n\\t" + command.name + " : " + command.shortDesc + "";
		
		accCli.sendInfo(gdc, AcceptClient.NORMAL_COMMAND_TYPE, sSend);
	}
	
	static void error(GerantDeClient gdc)
	{
		String sSend = "Commande invalide !\\n" +
					   "Faite /help pour la liste des commandes.";
		
		accCli.sendInfo(gdc, AcceptClient.ERROR_COMMAND_TYPE, sSend);
	}

	public static void setAcceptClient(AcceptClient accCli)
	{
		Command.accCli = accCli;
	}

	public String toString()
	{
		return name;
	}

}