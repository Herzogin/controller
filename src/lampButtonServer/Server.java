package lampButtonServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server  extends java.rmi.server.UnicastRemoteObject implements ServerInterface {

	public Server() throws RemoteException {
		super();
	}

	public void start() {
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
	
	public static void main(String[] args) throws RemoteException {
		Server s = new Server();
		s.start();
	}
	
}
