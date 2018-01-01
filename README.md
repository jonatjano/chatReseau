# chatReseau

Ces instructions sont à faire dans le répertoire chatReseau.

Sous Windows :
	1) pour compiler, il faut lancer compile.bat
	2) pour executer, il faut lancer lancerServ.sh pour le serveur et lancer lancerClient.sh pour le client
	
Sous Linux :
	1) pour compiler, il faut lancer compile.sh
	2) pour executer, il faut lancer lancerServ.sh pour le serveur et lancer lancerClient.sh pour le client

On peut aussi lancer le client et le server à partir des commandes suivantes (dans le répertoire bin)
	java server.Server [port] 
	java client.Client [IP] [port] 
	
Il y a aussi un fichier "config" qui permet de changer le port par default et l'ihm utilisé.