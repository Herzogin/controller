package lampButtonServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
		if (message.equals("press")) {
			LampInterface li;
			try {
				li = (LampInterface) Naming.lookup("rmi://localhost:1099/Server");
				li.changeStatus();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		System.out.println("Message received: " + message);
	}
	
	public static void main(String[] args) throws RemoteException {
		Server s = new Server();
		s.start();
	}
	
}
