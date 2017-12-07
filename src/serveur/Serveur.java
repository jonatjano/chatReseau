

ServerSocket ss = new ServerSocket(...);
while (true) // on boucle
{
	// attendre patiemment un client
	Socket s  = ss.accept();
	// créer un GerantDeClient pour traiter ce nouveau client
	GerantDeClient gdc = new GerantDeClient(s);
	// mettre ce gérant de client dans une Thread
	Thread tgdc =  new Thread(gdc);
	// lancer la thread qui gérera ce client
	tgdc.start();
	// ... et on recommence...
}
