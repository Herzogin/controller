package lampButtonServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server implements ServerInterface {

	public Server() {
		try {
			Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT); 
			registry.bind("Server", this);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void sendMessage(String message) throws RemoteException {
		System.out.println("Message received: " + message);
	}
	
	public static void main(String[] args) {
		new Server();
	}
	
}
