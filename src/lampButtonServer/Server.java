package lampButtonServer;

import java.rmi.RemoteException;

public class Server implements ServerInterface {

	@Override
	public void sendMessage(String message) throws RemoteException {
		System.out.println("Message received: " + message);
	}
	
}
